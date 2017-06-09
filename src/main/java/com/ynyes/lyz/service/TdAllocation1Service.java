package com.ynyes.lyz.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.transaction.Transactional;
import javax.transaction.Transactional.TxType;

import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.testng.collections.Maps;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.beust.jcommander.internal.Lists;
import com.common.util.DateUtil;
import com.ynyes.lyz.entity.AllocationTypeEnum;
import com.ynyes.lyz.entity.TdAllocation;
import com.ynyes.lyz.entity.TdAllocationCallRecord;
import com.ynyes.lyz.entity.TdAllocationDetail;
import com.ynyes.lyz.entity.TdAllocationTrail;
import com.ynyes.lyz.entity.TdDiySite;
import com.ynyes.lyz.entity.TdDiySiteInventory;
import com.ynyes.lyz.entity.TdDiySiteInventoryLog;
import com.ynyes.lyz.repository.TdAllocationCallRecordRepo;
import com.ynyes.lyz.repository.TdAllocationDetailRepo;
import com.ynyes.lyz.repository.TdAllocationRepo;
import com.ynyes.lyz.repository.TdAllocationTrailRepo;
import com.ynyes.lyz.repository.TdDiySiteInventoryLogRepo;
import com.ynyes.lyz.repository.TdDiySiteInventoryRepo;
import com.ynyes.lyz.repository.TdDiySiteRepo;
import com.ynyes.lyz.repository.TdManagerRepo;

import cn.com.leyizhuang.ebs.entity.dto.AllocationDetail;
import cn.com.leyizhuang.ebs.entity.dto.AllocationHeader;
import cn.com.leyizhuang.ebs.entity.dto.AllocationReceive;

@Service
public class TdAllocation1Service {

	private static final Logger LOGGER = LoggerFactory.getLogger(TdAllocation1Service.class);

	// 配置请求的超时设置
	private final RequestConfig REQUEST_CONFIG = RequestConfig.custom().setConnectionRequestTimeout(5000).setConnectTimeout(1000)
			.setSocketTimeout(5000).build();

	@Value("${deploy.ebs.newUrl}")
	private String ebsUrl;

	@Autowired
	private TdAllocationRepo tdAllocationRepo;

	@Autowired
	private TdAllocationDetailRepo tdAllocationDetailRepo;

	@Autowired
	private TdAllocationTrailRepo tdAllocationTrailRepo;

	@Autowired
	private TdDiySiteRepo tdDiySiteRepo;

	@Autowired
	private TdDiySiteInventoryRepo tdDiySiteInventoryRepo;

	@Autowired
	private TdDiySiteInventoryLogRepo tdDiySiteInventoryLogRepo;

	@Autowired
	TdManagerRepo tdManagerRepo;

	@Autowired
	private TdAllocationCallRecordRepo tdAllocationCallRecordRepo;

	private ExecutorService executorService = Executors.newFixedThreadPool(10);

	/**
	 * 获取一条门店调拨记录，包括调拨产品明细和调拨轨迹
	 * 
	 * @param id
	 * @return
	 */
	public TdAllocation findOne(Long id) {
		if (null == id) {
			return null;
		}
		TdAllocation allocation = tdAllocationRepo.findOne(id);
		allocation.setDetails(tdAllocationDetailRepo.findByAllocationId(id));
		allocation.setTrails(tdAllocationTrailRepo.findByAllocationId(id));
		return allocation;
	}

	/**
	 * 获取一条门店调拨记录
	 * 
	 * @param id
	 * @return
	 */
	public TdAllocation get(Long id) {
		if (null == id) {
			return null;
		}
		return tdAllocationRepo.findOne(id);
	}

	/**
	 * 调拨单出库
	 * 
	 * @param id
	 * @param detailsJson
	 * @param username
	 * @throws Exception
	 */
	@Transactional(value = TxType.REQUIRED)
	public void send(TdAllocation allocation, String realNums, String username) throws Exception {
		List<Long> nums = JSONArray.parseArray(realNums, Long.class);
		List<TdAllocationDetail> details = tdAllocationDetailRepo.findByAllocationId(allocation.getId());
		boolean changeNumFlag = false;
		for (int i = 0; i < details.size(); i++) {
			TdAllocationDetail detail = details.get(i);
			if (detail.getNum() != nums.get(i)) {
				detail.setRealNum(nums.get(i));
				changeNumFlag = true;
			}
		}

		// 修改本地
		Date now = new Date();
		for (TdAllocationDetail detail : tdAllocationDetailRepo.findByAllocationId(allocation.getId())) {
			int count = tdDiySiteInventoryRepo.updateInventory(allocation.getAllocationFrom(), detail.getGoodId(),
					-detail.getRealNum());
			if (count > 0) {
				TdDiySiteInventory tdDiySiteInventory = tdDiySiteInventoryRepo.findByGoodsIdAndDiySiteId(detail.getGoodId(),
						allocation.getAllocationFrom());
				if (tdDiySiteInventory != null && tdDiySiteInventory.getInventory() != null
						&& tdDiySiteInventory.getInventory() >= 0) {
					TdDiySiteInventoryLog tdDiySiteInventoryLog = new TdDiySiteInventoryLog();
					tdDiySiteInventoryLog.setAfterChange(tdDiySiteInventory.getInventory());
					tdDiySiteInventoryLog.setChangeDate(now);
					tdDiySiteInventoryLog.setChangeType("门店调拨出库");
					tdDiySiteInventoryLog.setChangeValue(-detail.getRealNum());
					tdDiySiteInventoryLog.setDiySiteId(allocation.getAllocationFrom());
					tdDiySiteInventoryLog.setDiySiteTitle(allocation.getAllocationFromName());
					tdDiySiteInventoryLog.setGoodsId(detail.getGoodId());
					tdDiySiteInventoryLog.setGoodsSku(detail.getGoodSku());
					tdDiySiteInventoryLog.setGoodsTitle(detail.getGoodTitle());
					tdDiySiteInventoryLog.setManager(username);
					tdDiySiteInventoryLog.setRegionId(tdDiySiteInventory.getRegionId());
					tdDiySiteInventoryLog.setRegionName(allocation.getCityName());
					tdDiySiteInventoryLog.setOrderNumber(allocation.getNumber());
					tdDiySiteInventoryLog.setDescription("门店调拨");
					tdDiySiteInventoryLogRepo.save(tdDiySiteInventoryLog);
				} else {
					throw new RuntimeException(detail.getGoodId() + "产品库存不足，不允许调拨");
				}
			} else {
				throw new RuntimeException(detail.getGoodId() + "产品库存不足，不允许调拨");
			}
		}
		if (changeNumFlag) {
			tdAllocationDetailRepo.save(details);
		}

		allocation.setUpdatedBy(username);
		allocation.setUpdatedTime(now);
		allocation.setStatus(AllocationTypeEnum.SENT.getValue());
		allocation = tdAllocationRepo.save(allocation);

		TdAllocationTrail trail = new TdAllocationTrail();
		trail.setAllocationId(allocation.getId());
		trail.setOperatedBy(username);
		trail.setOperatedTime(now);
		trail.setOperation(AllocationTypeEnum.SENT.getName());
		tdAllocationTrailRepo.save(trail);

		// 异步调用EBS，通知库存变化
		allocation.setDetails(details);
		final TdAllocation allot = allocation;
		executorService.execute(new Runnable() {
			public void run() {
				sendAllocation(allot);
			}
		});
	}

	/**
	 * 调拨单入库
	 * 
	 * @param id
	 * @param username
	 */
	@Transactional(value = TxType.REQUIRED)
	public void receive(TdAllocation allocation, String username) {
		// 修改本地
		Date now = new Date();
		for (TdAllocationDetail detail : tdAllocationDetailRepo.findByAllocationId(allocation.getId())) {
			// 更新库存表
			int count = tdDiySiteInventoryRepo.updateInventory(allocation.getAllocationTo(), detail.getGoodId(),
					detail.getRealNum());
			TdDiySiteInventory tdDiySiteInventory;
			// 无此商品库存
			if (count == 0) {
				tdDiySiteInventory = this.copyTdDiySiteInventoryFromAnother(
						tdDiySiteInventoryRepo.findByGoodsIdAndDiySiteId(detail.getGoodId(), allocation.getAllocationFrom()));
				tdDiySiteInventory.setDiySiteId(allocation.getAllocationTo());
				tdDiySiteInventory.setDiyCode(tdManagerRepo.findByUsernameAndIsEnableTrue(username).getDiyCode());
				tdDiySiteInventory.setDiySiteName(allocation.getAllocationToName());
				tdDiySiteInventory.setInventory(detail.getRealNum());
				tdDiySiteInventoryRepo.save(tdDiySiteInventory);
			} else {
				tdDiySiteInventory = tdDiySiteInventoryRepo.findByGoodsIdAndDiySiteId(detail.getGoodId(),
						allocation.getAllocationTo());
			}

			TdDiySiteInventoryLog tdDiySiteInventoryLog = new TdDiySiteInventoryLog();
			tdDiySiteInventoryLog.setAfterChange(tdDiySiteInventory.getInventory());
			tdDiySiteInventoryLog.setChangeDate(now);
			tdDiySiteInventoryLog.setChangeType("门店调拨入库");
			tdDiySiteInventoryLog.setChangeValue(detail.getRealNum());
			tdDiySiteInventoryLog.setDiySiteId(allocation.getAllocationTo());
			tdDiySiteInventoryLog.setDiySiteTitle(allocation.getAllocationToName());
			tdDiySiteInventoryLog.setGoodsId(detail.getGoodId());
			tdDiySiteInventoryLog.setGoodsSku(detail.getGoodSku());
			tdDiySiteInventoryLog.setGoodsTitle(detail.getGoodTitle());
			tdDiySiteInventoryLog.setManager(username);
			tdDiySiteInventoryLog.setRegionId(tdDiySiteInventory.getRegionId());
			tdDiySiteInventoryLog.setRegionName(allocation.getCityName());
			tdDiySiteInventoryLog.setOrderNumber(allocation.getNumber());
			tdDiySiteInventoryLog.setDescription("门店调拨");
			tdDiySiteInventoryLogRepo.save(tdDiySiteInventoryLog);
		}

		allocation.setUpdatedBy(username);
		allocation.setUpdatedTime(now);
		allocation.setStatus(AllocationTypeEnum.ENTERED.getValue());
		allocation = tdAllocationRepo.save(allocation);

		for (TdAllocationDetail detail : tdAllocationDetailRepo.findByAllocationId(allocation.getId())) {
			tdDiySiteInventoryRepo.updateInventory(allocation.getAllocationFrom(), detail.getGoodId(), detail.getRealNum());
		}

		TdAllocationTrail trail = new TdAllocationTrail();
		trail.setAllocationId(allocation.getId());
		trail.setOperatedBy(username);
		trail.setOperatedTime(now);
		trail.setOperation(AllocationTypeEnum.ENTERED.getName());
		tdAllocationTrailRepo.save(trail);

		// 异步调用EBS，通知库存变化
		final TdAllocation allot = allocation;
		executorService.execute(new Runnable() {
			public void run() {
				sendAllocationReceived(allot);
			}
		});
	}

	/**
	 * 异步发送调拨单(出库)到EBS，如果发送失败，记录到本地表中，待后续重发
	 * 
	 * @param allocation
	 */
	public void sendAllocation(TdAllocation allocation) {
		Map<String, Object> result = this.sendAllocationToEBS(allocation);
		if (!(Boolean) result.get("success")) {
			TdAllocationCallRecord tdAllocationCallRecord = new TdAllocationCallRecord();
			Date now = new Date();
			tdAllocationCallRecord.setAllocationId(allocation.getId());
			tdAllocationCallRecord.setContent((String) result.get("xml"));
			tdAllocationCallRecord.setCreatedTime(now);
			tdAllocationCallRecord.setMsg((String) result.get("msg"));
			tdAllocationCallRecord.setNumber(allocation.getNumber());
			tdAllocationCallRecord.setStatus(1);
			tdAllocationCallRecord.setTimes(1);
			tdAllocationCallRecord.setType(1);
			tdAllocationCallRecord.setUpdatedTime(now);
			tdAllocationCallRecordRepo.save(tdAllocationCallRecord);
		}
	}

	/**
	 * 异步发送调拨单(入库)到EBS，如果发送失败，记录到本地表中，待后续重发
	 * 
	 * @param allocation
	 */
	public void sendAllocationReceived(TdAllocation allocation) {
		Map<String, Object> result = this.sendAllocationReceivedToEBS(allocation);
		if (!(Boolean) result.get("success")) {
			TdAllocationCallRecord tdAllocationCallRecord = new TdAllocationCallRecord();
			Date now = new Date();
			tdAllocationCallRecord.setAllocationId(allocation.getId());
			tdAllocationCallRecord.setContent((String) result.get("xml"));
			tdAllocationCallRecord.setCreatedTime(now);
			tdAllocationCallRecord.setMsg((String) result.get("msg"));
			tdAllocationCallRecord.setNumber(allocation.getNumber());
			tdAllocationCallRecord.setStatus(1);
			tdAllocationCallRecord.setTimes(1);
			tdAllocationCallRecord.setType(3);
			tdAllocationCallRecord.setUpdatedTime(now);
			tdAllocationCallRecordRepo.save(tdAllocationCallRecord);
		}
	}

	/**
	 * 将失败的调拨记录重新发送到EBS
	 * 
	 */
	@Transactional(value = TxType.NOT_SUPPORTED)
	public void resendAllAllocation() {
		// 失败的调拨单ID集合
		List<Long> faildIds = Lists.newArrayList();

		// 查找所有失败的调拨单出库记录
		List<TdAllocationCallRecord> headerRecords = tdAllocationCallRecordRepo.findByType(1);
		LOGGER.info("Resend allocaton header record, count=" + headerRecords.size());
		for (TdAllocationCallRecord record : headerRecords) {
			Map<String, Object> result = this.sendFaildAllocationToEBS(record);
			if ((Boolean) result.get("success")) {
				tdAllocationCallRecordRepo.delete(record.getId());
			} else {
				Date now = new Date();
				record.setUpdatedTime(now);
				record.setMsg((String) result.get("msg"));
				record.setTimes(record.getTimes() + 1);
				tdAllocationCallRecordRepo.save(record);
				faildIds.add(record.getAllocationId());
			}
		}

		// 查找所有失败的调拨入库记录
		List<TdAllocationCallRecord> receiveRecords = tdAllocationCallRecordRepo.findByType(3);
		LOGGER.info("Resend allocaton receive record, count=" + receiveRecords.size());
		for (TdAllocationCallRecord record : receiveRecords) {
			if (!faildIds.contains(record.getAllocationId())) {
				Map<String, Object> result = this.sendFaildAllocationToEBS(record);
				if ((Boolean) result.get("success")) {
					tdAllocationCallRecordRepo.delete(record.getId());
				} else {
					Date now = new Date();
					record.setUpdatedTime(now);
					record.setMsg((String) result.get("msg"));
					record.setTimes(record.getTimes() + 1);
					tdAllocationCallRecordRepo.save(record);
					faildIds.add(record.getAllocationId());
				}
			}
		}
		LOGGER.info("Resend allocaton done, faild allocation ids=" + faildIds);
	}

	/**
	 * 发送调拨单出库信息到EBS
	 * 
	 * @param allocation
	 * @return
	 */
	private Map<String, Object> sendAllocationToEBS(TdAllocation allocation) {
		LOGGER.info("sendAllocationToEBS, allocation=" + allocation);
		Map<String, Object> result = Maps.newHashMap();

		HttpPost httppost = new HttpPost(this.ebsUrl + "callAllocation");
		httppost.setConfig(REQUEST_CONFIG);
		String header = this.genHeaderJson(allocation);
		String details = this.genDetailJson(allocation);
		List<NameValuePair> parameters = new ArrayList<NameValuePair>();
		parameters.add(new BasicNameValuePair("allcationHeaderJson", header));
		parameters.add(new BasicNameValuePair("allocationDetailsJson", details));
		CloseableHttpClient httpclient = HttpClientBuilder.create().build();

		try {
			httppost.setEntity(new UrlEncodedFormEntity(parameters));
			CloseableHttpResponse response = httpclient.execute(httppost);
			LOGGER.info("sendAllocationToEBS, response=" + response.toString());
			if (response.getStatusLine().getStatusCode() == 200) {
				HttpEntity entity = response.getEntity();
				String jsonResult = EntityUtils.toString(entity, "utf-8");
				LOGGER.info("sendAllocationToEBS, jsonResult=" + jsonResult);
				JSONObject ebsResult = JSON.parseObject(jsonResult);
				if ("0".equals(ebsResult.getString("code"))) {
					result.put("success", true);
				} else {
					result.put("success", false);
					result.put("msg", ebsResult.getString("message"));
				}
			} else {
				result.put("success", false);
				result.put("msg", "Http code:" + response.getStatusLine().getStatusCode());
			}
		} catch (Exception e) {
			result.put("success", false);
			result.put("msg", e.getMessage());
		}

		if (!(Boolean) result.get("success")) {
			JSONObject content = new JSONObject();
			content.put("allcationHeaderJson", header);
			content.put("allocationDetailsJson", details);
			result.put("content", JSON.toJSONString(content));
		}

		LOGGER.info("sendAllocationToEBS, result=" + result);
		return result;
	}

	/**
	 * 发送调拨单入库到EBS
	 * 
	 * @param allocation
	 * @return
	 */
	private Map<String, Object> sendAllocationReceivedToEBS(TdAllocation allocation) {
		LOGGER.info("sendAllocationReceivedToEBS, allocation=" + allocation);
		Map<String, Object> result = Maps.newHashMap();

		HttpPost httppost = new HttpPost(this.ebsUrl + "callAllocationRecieve");
		httppost.setConfig(REQUEST_CONFIG);
		String allocationReceiveJson = this.genReceiveJson(allocation);
		List<NameValuePair> parameters = new ArrayList<NameValuePair>();
		parameters.add(new BasicNameValuePair("allocationReceiveJson", allocationReceiveJson));
		CloseableHttpClient httpclient = HttpClientBuilder.create().build();

		try {
			httppost.setEntity(new UrlEncodedFormEntity(parameters));
			CloseableHttpResponse response = httpclient.execute(httppost);
			LOGGER.info("sendAllocationReceivedToEBS, response=" + response.toString());
			if (response.getStatusLine().getStatusCode() == 200) {
				HttpEntity entity = response.getEntity();
				String jsonResult = EntityUtils.toString(entity, "utf-8");
				LOGGER.info("sendAllocationReceivedToEBS, jsonResult=" + jsonResult);
				JSONObject ebsResult = JSON.parseObject(jsonResult);
				if ("0".equals(ebsResult.getString("code"))) {
					result.put("success", true);
				} else {
					result.put("success", false);
					result.put("msg", ebsResult.getString("message"));
				}
			} else {
				result.put("success", false);
				result.put("msg", "Http code:" + response.getStatusLine().getStatusCode());
			}
		} catch (Exception e) {
			result.put("success", false);
			result.put("msg", e.getMessage());
		}

		if (!(Boolean) result.get("success")) {
			JSONObject content = new JSONObject();
			content.put("allocationReceiveJson", allocationReceiveJson);
			result.put("content", JSON.toJSONString(content));
		}

		LOGGER.info("sendAllocationReceivedToEBS, result=" + result);
		return result;
	}

	/**
	 * 发送失败调拨单头到EBS
	 * 
	 * @param record
	 * @return
	 */
	private Map<String, Object> sendFaildAllocationToEBS(TdAllocationCallRecord record) {
		LOGGER.info("sendFaildAllocationToEBS, record=" + record);
		Map<String, Object> result = Maps.newHashMap();

		String url;
		List<NameValuePair> parameters = new ArrayList<NameValuePair>();
		JSONObject jsonParams = JSON.parseObject(record.getContent());
		if (record.getType() == 3) {
			url = this.ebsUrl + "callAllocationRecieve";
			parameters.add(new BasicNameValuePair("allocationReceiveJson", jsonParams.getString("allocationReceiveJson")));
		} else {
			url = this.ebsUrl + "callAllocation";
			parameters.add(new BasicNameValuePair("allcationHeaderJson", jsonParams.getString("allcationHeaderJson")));
			parameters.add(new BasicNameValuePair("allocationDetailsJson", jsonParams.getString("allocationDetailsJson")));
		}

		HttpPost httppost = new HttpPost(url);
		CloseableHttpClient httpclient = HttpClientBuilder.create().build();

		try {
			httppost.setEntity(new UrlEncodedFormEntity(parameters));
			CloseableHttpResponse response = httpclient.execute(httppost);
			LOGGER.info("sendFaildAllocationToEBS, response=" + response.toString());
			if (response.getStatusLine().getStatusCode() == 200) {
				HttpEntity entity = response.getEntity();
				String jsonResult = EntityUtils.toString(entity, "utf-8");
				LOGGER.info("sendFaildAllocationToEBS, jsonResult=" + jsonResult);
				JSONObject ebsResult = JSON.parseObject(jsonResult);
				if ("0".equals(ebsResult.getString("code"))) {
					result.put("success", true);
				} else {
					result.put("success", false);
					result.put("msg", ebsResult.getString("message"));
				}
			} else {
				result.put("success", false);
				result.put("msg", "Http code:" + response.getStatusLine().getStatusCode());
			}
		} catch (Exception e) {
			result.put("success", false);
			result.put("msg", e.getMessage());
		}

		LOGGER.info("sendFaildAllocationToEBS, result=" + result);
		return result;
	}

	/**
	 * 从门店库存记录创建一条新的门店库存记录
	 * 
	 * @param from
	 * @return
	 */
	private TdDiySiteInventory copyTdDiySiteInventoryFromAnother(TdDiySiteInventory from) {
		TdDiySiteInventory tdDiySiteInventory = new TdDiySiteInventory();
		tdDiySiteInventory.setCategoryId(from.getCategoryId());
		tdDiySiteInventory.setCategoryIdTree(from.getCategoryIdTree());
		tdDiySiteInventory.setCategoryTitle(from.getCategoryTitle());
		tdDiySiteInventory.setGoodsCode(from.getGoodsCode());
		tdDiySiteInventory.setGoodsId(from.getGoodsId());
		tdDiySiteInventory.setGoodsTitle(from.getGoodsTitle());
		tdDiySiteInventory.setRegionId(from.getRegionId());
		tdDiySiteInventory.setRegionName(from.getRegionName());
		return tdDiySiteInventory;
	}

	/**
	 * 生成调拨单头Json
	 * 
	 * @param allocation
	 * @return
	 */
	private String genHeaderJson(TdAllocation allocation) {
		TdDiySite from = tdDiySiteRepo.findOne(allocation.getAllocationFrom());
		TdDiySite to = tdDiySiteRepo.findOne(allocation.getAllocationTo());

		AllocationHeader header = new AllocationHeader();
		header.setSobId(toString(to.getRegionId()));
		header.setHeaderId(toString(allocation.getId()));
		header.setOrderNumber(allocation.getNumber());
		header.setOrderDate(DateFormatUtils.format(allocation.getCreatedTime(), DateUtil.FORMAT_DATETIME));
		header.setApproveDate(DateFormatUtils.format(allocation.getUpdatedTime(), DateUtil.FORMAT_DATETIME));
		header.setProductType("HR");
		header.setLyDiySiteCode(from.getStoreCode());
		header.setLyDiySiteName(from.getTitle());
		header.setMdDiySiteCode(to.getStoreCode());
		header.setMdDiySiteName(to.getStoreCode());
		header.setComments(allocation.getComment());
		return JSON.toJSONString(header);
	}

	/**
	 * 生成调拨单明细JSON
	 * 
	 * @param allocation
	 * @return
	 */
	private String genDetailJson(TdAllocation allocation) {
		List<AllocationDetail> allocationDetails = Lists.newArrayList();
		for (TdAllocationDetail detail : allocation.getDetails()) {
			AllocationDetail allocationDetail = new AllocationDetail();
			allocationDetail.setHeaderId(toString(allocation.getId()));
			allocationDetail.setLineId(toString(detail.getId()));
			allocationDetail.setGoodsTitle(detail.getGoodTitle());
			allocationDetail.setSku(detail.getGoodSku());
			allocationDetail.setQuantity(toString(detail.getRealNum()));
			allocationDetails.add(allocationDetail);
		}
		return JSON.toJSONString(allocationDetails);
	}

	/**
	 * 生成调拨单头入库JSON
	 * 
	 * @param allocation
	 * @return
	 */
	private String genReceiveJson(TdAllocation allocation) {
		TdDiySite to = tdDiySiteRepo.findOne(allocation.getAllocationTo());

		AllocationReceive receive = new AllocationReceive();
		receive.setSobId(toString(to.getRegionId()));
		receive.setHeaderId(toString(allocation.getId()));
		receive.setOrderNumber(allocation.getNumber());
		receive.setReceiveDate(DateFormatUtils.format(allocation.getUpdatedTime(), DateUtil.FORMAT_DATETIME));
		return JSON.toJSONString(receive);
	}

	private String toString(Object obj) {
		if (obj == null) {
			return "";
		} else {
			return String.valueOf(obj);
		}
	}
}
