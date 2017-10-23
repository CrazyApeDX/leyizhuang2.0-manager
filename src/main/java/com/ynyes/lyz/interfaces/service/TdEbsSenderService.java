package com.ynyes.lyz.interfaces.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

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
import com.alibaba.fastjson.JSONObject;
import com.beust.jcommander.internal.Lists;
import com.common.util.DateUtil;
import com.ynyes.lyz.entity.TdAllocation;
import com.ynyes.lyz.entity.TdAllocationCallRecord;
import com.ynyes.lyz.entity.TdAllocationDetail;
import com.ynyes.lyz.entity.TdDiySite;
import com.ynyes.lyz.interfaces.entity.TdCashReciptInf;
import com.ynyes.lyz.interfaces.entity.TdCashRefundInf;
import com.ynyes.lyz.interfaces.entity.TdOrderCouponInf;
import com.ynyes.lyz.interfaces.entity.TdOrderGoodsInf;
import com.ynyes.lyz.interfaces.entity.TdOrderInf;
import com.ynyes.lyz.interfaces.entity.TdOrderReceiveInf;
import com.ynyes.lyz.interfaces.entity.TdReturnTimeInf;
import com.ynyes.lyz.repository.TdAllocationCallRecordRepo;
import com.ynyes.lyz.repository.TdDiySiteRepo;

import cn.com.leyizhuang.ebs.entity.dto.AllocationDetail;
import cn.com.leyizhuang.ebs.entity.dto.AllocationHeader;
import cn.com.leyizhuang.ebs.entity.dto.AllocationReceive;
import cn.com.leyizhuang.ebs.entity.dto.CashReceipt;
import cn.com.leyizhuang.ebs.entity.dto.CashRefund;
import cn.com.leyizhuang.ebs.entity.dto.Order;
import cn.com.leyizhuang.ebs.entity.dto.OrderCoupon;
import cn.com.leyizhuang.ebs.entity.dto.OrderGood;
import cn.com.leyizhuang.ebs.entity.dto.StorePickUp;
import cn.com.leyizhuang.ebs.entity.dto.StoreReturn;

@Service
public class TdEbsSenderService {

    private static final Logger LOGGER = LoggerFactory.getLogger(TdEbsSenderService.class);

    // 配置请求的超时设置
    private final RequestConfig REQUEST_CONFIG = RequestConfig.custom().setConnectionRequestTimeout(5000).setConnectTimeout(1000)
            .setSocketTimeout(5000).build();

    @Value("${deploy.ebs.newUrl}")
    private String ebsUrl;

    @Autowired
    private TdAllocationCallRecordRepo tdAllocationCallRecordRepo;

    @Autowired
    private TdDiySiteRepo tdDiySiteRepo;
    
    @Autowired
	private TdOrderReceiveInfService tdOrderReceiveInfService;
    
    @Autowired
	private TdReturnTimeInfService tdReturnTimeInfService;
    
	@Autowired
	private TdCashReciptInfService tdCashReciptInfService;

	@Autowired
	private TdCashRefundInfService tdCashRefundInfService;
    
    private ExecutorService executorService = Executors.newFixedThreadPool(10);
    
    
    /**
	 * 发送【订单（订单头、订单产品、订单券）】信息到EBS，并保存发送结果
	 * 
	 * @param tdCashRefundInf
	 * @return
	 */
	public Map<String, Object> sendOrderToEbs(TdOrderInf orderInf,List<TdOrderGoodsInf> goodsInfs,List<TdOrderCouponInf> couponInfs) {
		LOGGER.info("sendOrderToEbs, tdOrderInf=" + orderInf);
		LOGGER.info("sendOrderToEbs,goodsInfs=" + goodsInfs );
		LOGGER.info("sendOrderToEbs,couponInfs=" + couponInfs );
		
		Order order = new Order();
		order.setAttribute1(toString(orderInf.getAttribute1()));
		order.setAttribute2(toString(orderInf.getAttribute2()));
		order.setAttribute3(toString(orderInf.getAttribute3()));
		order.setAttribute4(toString(orderInf.getAttribute4()));
		order.setAttribute5(toString(orderInf.getAttribute5()));
		order.setCity(toString(orderInf.getCity()));
		order.setCompanyDeliveryFee(toString(orderInf.getCompanyDeliveryFee()));
		order.setCouponFlag(toString(orderInf.getCouponFlag()));
		order.setDeliverTypeTitle(toString(orderInf.getDeliverTypeTitle()));
		order.setDeliveryFee(toString(orderInf.getDeliveryFee()));
		order.setDisctrict(toString(orderInf.getDisctrict()));
		order.setDiySiteCode(toString(orderInf.getDiySiteCode()));
		order.setDiySiteId(toString(orderInf.getDiySiteId()));
		order.setDiySiteName(toString(orderInf.getDiySiteName()));
		order.setDiySitePhone(toString(orderInf.getDiySitePhone()));
		order.setIsonlinepay(toString(orderInf.getIsonlinepay()));
		order.setMainOrderNumber(toString(orderInf.getMainOrderNumber()));
		order.setOrderDate(DateFormatUtils.format(orderInf.getOrderDate(), DateUtil.FORMAT_DATETIME));
		order.setOrderHeaderId(toString(orderInf.getHeaderId()));
		order.setOrderNumber(toString(orderInf.getOrderNumber()));
		order.setOrderTypeId(toString(orderInf.getOrderTypeId()));
		order.setPayAmt(toString(orderInf.getPayAmt()));
		order.setPayDate(DateFormatUtils.format(orderInf.getPayDate(), DateUtil.FORMAT_DATETIME));
		order.setPayType(toString(orderInf.getPayType()));
		order.setPrepayAmt(toString(orderInf.getPrepayAmt()));
		order.setProductType(toString(orderInf.getProductType()));
		order.setProvince(toString(orderInf.getProvince()));
		order.setRecAmt(toString(orderInf.getRecAmt()));
		order.setSellerName(toString(orderInf.getSellerName()));
		order.setSellerPhone(toString(orderInf.getSellerPhone()));
		order.setShippingAddress(toString(orderInf.getShippingAddress()));
		order.setShippingName(toString(orderInf.getShippingName()));
		order.setShippingPhone(toString(orderInf.getShippingPhone()));
		order.setSobId(toString(orderInf.getSobId()));
		order.setUserid(toString(orderInf.getUserid()));
		order.setUsername(toString(orderInf.getUsername()));
		order.setUserphone(toString(orderInf.getUserphone()));
		
		List<OrderGood> orderGoods = new ArrayList<>();
		if(null != goodsInfs && goodsInfs.size()>0){
			for (TdOrderGoodsInf orderGoodsInf : goodsInfs) {
				OrderGood orderGood = new OrderGood();
				orderGood.setAttribute1(toString(orderGoodsInf.getAttribute1()));
				orderGood.setAttribute2(toString(orderGoodsInf.getAttribute2()));
				orderGood.setAttribute3(toString(orderGoodsInf.getAttribute3()));
				orderGood.setAttribute4(toString(orderGoodsInf.getAttribute4()));
				orderGood.setAttribute5(toString(orderGoodsInf.getAttribute5()));
				orderGood.setDeliveryFee("");
				orderGood.setGiftFlag(toString(orderGoodsInf.getGiftFlag()));
				orderGood.setGoodsId(toString(orderGoodsInf.getGoodsId()));
				orderGood.setGoodsSubTitle(toString(orderGoodsInf.getGoodsSubTitle()));
				orderGood.setGoodsTitle(toString(orderGoodsInf.getGoodsTitle()));
				orderGood.setHyPrice(toString(orderGoodsInf.getHyPrice()));
				orderGood.setJxPrice(toString(orderGoodsInf.getJxPrice()));
				orderGood.setLsPrice(toString(orderGoodsInf.getLsPrice()));
				orderGood.setOrderHeaderId(toString(orderGoodsInf.getOrderHeaderId()));
				orderGood.setOrderLineId(toString(orderGoodsInf.getOrderLineId()));
				orderGood.setProBreakFlag("");
				orderGood.setPromotion(toString(orderGoodsInf.getPromotion()));
				orderGood.setQuantity(toString(orderGoodsInf.getQuantity()));
				orderGood.setSku(toString(orderGoodsInf.getSku()));
				orderGoods.add(orderGood);
			}
		}
		
		List<OrderCoupon> orderCoupons = new ArrayList<>();
		if(null != couponInfs && couponInfs.size()>0){
			for (TdOrderCouponInf orderCouponInf : couponInfs) {
				OrderCoupon orderCoupon = new OrderCoupon();
				orderCoupon.setAttribute1(toString(orderCouponInf.getAttribute1()));
				orderCoupon.setAttribute2(toString(orderCouponInf.getAttribute2()));
				orderCoupon.setAttribute3(toString(orderCouponInf.getAttribute3()));
				orderCoupon.setAttribute4(toString(orderCouponInf.getAttribute4()));
				orderCoupon.setAttribute5(toString(orderCouponInf.getAttribute5()));
				orderCoupon.setCouponTypeId(toString(orderCouponInf.getCouponTypeId()));
				orderCoupon.setHistoryFlag(toString(orderCouponInf.getHistoryFlag()));
				orderCoupon.setLineId(toString(orderCouponInf.getLineId()));
				orderCoupon.setOrderHeaderId(toString(orderCouponInf.getOrderHeaderId()));
				orderCoupon.setPrice(toString(orderCouponInf.getPrice()));
				orderCoupon.setPromotion(toString(orderCouponInf.getPromotion()));
				orderCoupon.setQuantity(toString(orderCouponInf.getQuantity()));
				orderCoupon.setSku(toString(orderCouponInf.getSku()));
				orderCoupons.add(orderCoupon);
			}
		}
		String orderJson = JSON.toJSONString(order);
		String orderGoodsJson = JSON.toJSONString(orderGoods);
		String orderCouponsJson = JSON.toJSONString(orderCoupons);
		List<NameValuePair> parameters = new ArrayList<NameValuePair>();
		parameters.add(new BasicNameValuePair("orderJson", orderJson));
		parameters.add(new BasicNameValuePair("orderGoodsJson", orderGoodsJson));
		parameters.add(new BasicNameValuePair("orderCouponsJson", orderCouponsJson));
		
		Map<String, Object> result = this.postToEbs(this.ebsUrl + "callOrder", parameters);

		if (!(Boolean) result.get("success")) {
			JSONObject content = new JSONObject();
			content.put("orderJson", orderJson);
			content.put("orderGoodsJson", orderGoodsJson);
			content.put("orderCouponsJson", orderCouponsJson);
			result.put("content", JSON.toJSONString(content));
		}

		LOGGER.info("sendOrderToEbs, result=" + result);
		return result;
	}

    /**
     * 异步发送【调拨单(出库)】信息到EBS，并保存发送结果
     * 
     * @param allocation
     */
    public void sendAllocationToEBSAndRecord(final TdAllocation allocation) {
        executorService.execute(new Runnable() {
            public void run() {
                Map<String, Object> result = sendAllocationToEBS(allocation);
                if (!(Boolean) result.get("success")) {
            		TdAllocationCallRecord tdAllocationCallRecord = new TdAllocationCallRecord();
                    Date now = new Date();
                    tdAllocationCallRecord.setAllocationId(allocation.getId());
                    tdAllocationCallRecord.setContent((String) result.get("content"));
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
        });
    }

    /**
     * 异步发送【调拨单(入库)】信息到EBS，并保存发送结果
     * 
     * @param allocation
     */
    public void sendAllocationReceivedToEBSAndRecord(final TdAllocation allocation) {
        executorService.execute(new Runnable() {
            public void run() {
                Map<String, Object> result = sendAllocationReceivedToEBS(allocation);
                if (!(Boolean) result.get("success")) {
            		TdAllocationCallRecord tdAllocationCallRecord = new TdAllocationCallRecord();
                    Date now = new Date();
                    tdAllocationCallRecord.setAllocationId(allocation.getId());
                    tdAllocationCallRecord.setContent((String) result.get("content"));
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
        });
    }
    /**
	 * 异步发送【门店自提】信息到EBS，并保存发送结果
	 * 
	 * @param tdOrderReceiveInf
	 */
	public void sendStorePickUpToEbsAndRecord(final TdOrderReceiveInf tdOrderReceiveInf) {
		executorService.execute(new Runnable() {
			public void run() {
				Map<String, Object> result = sendStorePickUpToEbs(tdOrderReceiveInf);
				if (!(Boolean) result.get("success")) {
            		tdOrderReceiveInf.setSendFlag(1);
            		tdOrderReceiveInf.setErrorMsg((String) result.get("msg"));
				} else {
					tdOrderReceiveInf.setSendFlag(0);
				}
				tdOrderReceiveInfService.save(tdOrderReceiveInf);
			}
		});
	}
	/**
	 * 异步发送【门店退货】信息到EBS，并保存发送结果
	 * 
	 * @param tdReturnTimeInf
	 */
	public void sendStoreReturnEbsAndRecord(final TdReturnTimeInf tdReturnTimeInf) {
		executorService.execute(new Runnable() {
			public void run() {
				Map<String, Object> result = sendStoreReturnToEbs(tdReturnTimeInf);
				if (!(Boolean) result.get("success")) {
            		tdReturnTimeInf.setSendFlag(1);
					tdReturnTimeInf.setErrorMsg((String) result.get("msg"));
				} else {
					tdReturnTimeInf.setSendFlag(0);
				}
				tdReturnTimeInfService.save(tdReturnTimeInf);
			}
		});
	}
    /**
     * 发送【调拨单(出库)】信息到EBS
     * 
     * @param tdOrderReceiveInf
     */
    public Map<String, Object> sendAllocationToEBS(TdAllocation allocation) {
        LOGGER.info("sendAllocationToEBS, allocation=" + allocation);

        String header = this.genHeaderJson(allocation);
        String details = this.genDetailJson(allocation);
        List<NameValuePair> parameters = new ArrayList<NameValuePair>();
        parameters.add(new BasicNameValuePair("allcationHeaderJson", header));
        parameters.add(new BasicNameValuePair("allocationDetailsJson", details));
        Map<String, Object> result = this.postToEbs(this.ebsUrl + "callAllocation", parameters);

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
     * 发送【调拨单(入库)】信息到EBS
     * 
     * @param tdReturnTimeInf
     */
    public Map<String, Object> sendAllocationReceivedToEBS(TdAllocation allocation) {
        LOGGER.info("sendAllocationReceivedToEBS, allocation=" + allocation);

        String allocationReceiveJson = this.genReceiveJson(allocation);
        List<NameValuePair> parameters = new ArrayList<NameValuePair>();
        parameters.add(new BasicNameValuePair("allocationReceiveJson", allocationReceiveJson));
        Map<String, Object> result = this.postToEbs(this.ebsUrl + "callAllocationReceive", parameters);

        if (!(Boolean) result.get("success")) {
            JSONObject content = new JSONObject();
            content.put("allocationReceiveJson", allocationReceiveJson);
            result.put("content", JSON.toJSONString(content));
        }

        LOGGER.info("sendAllocationReceivedToEBS, result=" + result);
        return result;
    }
    /**
	 * 发送【到店自提】信息到EBS，并保存发送结果
	 * 
	 * @param tdOrderReceiveInf
	 */
	public Map<String, Object> sendStorePickUpToEbs(final TdOrderReceiveInf tdOrderReceiveInf) {
		LOGGER.info("sendStorePickUpToEbs, tdOrderReceiveInf=" + tdOrderReceiveInf);

		StorePickUp storePickUp = new StorePickUp();
		storePickUp.setOrderHeaderId(toString(tdOrderReceiveInf.getHeaderId()));
		storePickUp.setOrderNumber(toString(tdOrderReceiveInf.getOrderNumber()));
		storePickUp.setReceiveDate(DateFormatUtils.format(tdOrderReceiveInf.getReceiveDate(), DateUtil.FORMAT_DATETIME));
		storePickUp.setSobId(toString(tdOrderReceiveInf.getSobId()));
	
		
		String storePickUpJson = JSON.toJSONString(storePickUp);
		List<NameValuePair> parameters = new ArrayList<NameValuePair>();
		parameters.add(new BasicNameValuePair("storePickUpJson", storePickUpJson));
		Map<String, Object> result = this.postToEbs(this.ebsUrl + "callStorePickUp", parameters);

		if (!(Boolean) result.get("success")) {
			JSONObject content = new JSONObject();
			content.put("storePickUpJson", storePickUpJson);
			result.put("content", JSON.toJSONString(content));
		}

		LOGGER.info("sendStorePickUpToEbs, result=" + result);
		return result;
	}
	/**
	 * 发送【到店退货】信息到EBS，并保存发送结果
	 * 
	 * @param tdReturnTimeInf
	 */
	public Map<String, Object> sendStoreReturnToEbs(final TdReturnTimeInf tdReturnTimeInf) {
		LOGGER.info("sendStoreReturnToEbs, tdReturnTimeInf=" + tdReturnTimeInf);

		StoreReturn storeReturn = new StoreReturn();
		storeReturn.setRtHeaderId(toString(tdReturnTimeInf.getRtHeaderId()));
		storeReturn.setReturnNumber(toString(tdReturnTimeInf.getReturnNumber()));
		storeReturn.setReturnDate(DateFormatUtils.format(tdReturnTimeInf.getReturnDate(), DateUtil.FORMAT_DATETIME));
		storeReturn.setSobId(toString(tdReturnTimeInf.getSobId()));

		String storeReturnJson = JSON.toJSONString(storeReturn);
		List<NameValuePair> parameters = new ArrayList<NameValuePair>();
		parameters.add(new BasicNameValuePair("storeReturnJson", storeReturnJson));
		Map<String, Object> result = this.postToEbs(this.ebsUrl + "callStoreReturn", parameters);

		if (!(Boolean) result.get("success")) {
			JSONObject content = new JSONObject();
			content.put("storeReturnJson", storeReturnJson);
			result.put("content", JSON.toJSONString(content));
		}

		LOGGER.info("sendStoreReturnToEbs, result=" + result);
		return result;
	}
    /**
     * 发送失败调拨单头到EBS
     * 
     * @param record
     * @return
     */
    public Map<String, Object> sendFaildAllocationToEBS(TdAllocationCallRecord record) {
        LOGGER.info("sendFaildAllocationToEBS, record=" + record);
        Map<String, Object> result;

        List<NameValuePair> parameters = new ArrayList<NameValuePair>();
        JSONObject jsonParams = JSON.parseObject(record.getContent());
        if (record.getType() == 3) {
            parameters.add(new BasicNameValuePair("allocationReceiveJson", jsonParams.getString("allocationReceiveJson")));
            result = this.postToEbs(this.ebsUrl + "callAllocationReceive", parameters);

        } else {
            parameters.add(new BasicNameValuePair("allcationHeaderJson", jsonParams.getString("allcationHeaderJson")));
            parameters.add(new BasicNameValuePair("allocationDetailsJson", jsonParams.getString("allocationDetailsJson")));
            result = this.postToEbs(this.ebsUrl + "callAllocation", parameters);
        }
        LOGGER.info("sendFaildAllocationToEBS, result=" + result);
        return result;
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

    private Map<String, Object> postToEbs(String url, List<NameValuePair> parameters) {
        Map<String, Object> result = Maps.newHashMap();
        HttpPost httppost = new HttpPost(url);
        httppost.setConfig(REQUEST_CONFIG);
        CloseableHttpClient httpclient = HttpClientBuilder.create().build();

        try {
            httppost.setEntity(new UrlEncodedFormEntity(parameters, "UTF-8"));
            CloseableHttpResponse response = httpclient.execute(httppost);
            LOGGER.info("postToEbs, response=" + response.toString());
            if (response.getStatusLine().getStatusCode() == 200) {
                HttpEntity entity = response.getEntity();
                String jsonResult = EntityUtils.toString(entity, "utf-8");
                LOGGER.info("postToEbs, jsonResult=" + jsonResult);
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
        return result;
    }

    private String toString(Object obj) {
        if (obj == null) {
            return "";
        } else {
            return String.valueOf(obj);
        }
    }
    
    /**
	 * 异步发送【收款】信息到EBS，并保存发送结果
	 * 
	 * @param tdCashReciptInf
	 */
	public void sendCashReciptToEbsAndRecord(final TdCashReciptInf tdCashReciptInf) {
		executorService.execute(new Runnable() {
			public void run() {
				Map<String, Object> result = sendCashReciptToEbs(tdCashReciptInf);
				if (!(Boolean) result.get("success")) {
					tdCashReciptInf.setSendFlag(1);
					tdCashReciptInf.setErrorMsg((String) result.get("msg"));
				} else {
					tdCashReciptInf.setSendFlag(0);
				}
				tdCashReciptInfService.save(tdCashReciptInf);
			}
		});
	}

	/**
	 * 异步发送【退款】信息到EBS，并保存发送结果
	 * 
	 * @param tdCashRefundInf
	 */
	public void sendCashRefundToEbsAndRecord(final TdCashRefundInf tdCashRefundInf) {
		executorService.execute(new Runnable() {
			public void run() {
				Map<String, Object> result = sendCashRefundToEbs(tdCashRefundInf);
				if (!(Boolean) result.get("success")) {
					tdCashRefundInf.setSendFlag(1);
					tdCashRefundInf.setErrorMsg((String) result.get("msg"));
				} else {
					tdCashRefundInf.setSendFlag(0);
				}
				tdCashRefundInfService.save(tdCashRefundInf);
			}
		});
	}
	
	/**
	 * 发送【收款】信息到EBS，并保存发送结果
	 * 
	 * @param tdCashReciptInf
	 * @return
	 */
	public Map<String, Object> sendCashReciptToEbs(TdCashReciptInf tdCashReciptInf) {
		LOGGER.info("sendCashReciptToEbs, tdCashReciptInf=" + tdCashReciptInf);

		CashReceipt cashReceipt = new CashReceipt();
		cashReceipt.setAmount(toString(tdCashReciptInf.getAmount()));
		cashReceipt.setDescription("");
		cashReceipt.setDiySiteCode(toString(tdCashReciptInf.getDiySiteCode()));
		cashReceipt.setOrderHeaderId(toString(tdCashReciptInf.getOrderHeaderId()));
		cashReceipt.setOrderNumber(toString(tdCashReciptInf.getOrderNumber()));
		cashReceipt.setProductType(toString(tdCashReciptInf.getProductType()));
		cashReceipt.setReceiptClass(toString(tdCashReciptInf.getReceiptClass()));
		cashReceipt.setReceiptDate(DateFormatUtils.format(tdCashReciptInf.getReceiptDate(), DateUtil.FORMAT_DATETIME));
		cashReceipt.setReceiptId(toString(tdCashReciptInf.getReceiptId()));
		cashReceipt.setReceiptNumber(toString(tdCashReciptInf.getReceiptNumber()));
		cashReceipt.setReceiptType(toString(tdCashReciptInf.getReceiptType()));
		cashReceipt.setSobId(toString(tdCashReciptInf.getSobId()));
		cashReceipt.setUserid(toString(tdCashReciptInf.getUserid()));
		cashReceipt.setUsername(toString(tdCashReciptInf.getUsername()));
		cashReceipt.setUserphone(toString(tdCashReciptInf.getUserphone()));

		String allocationReceiveJson = JSON.toJSONString(cashReceipt);
		List<NameValuePair> parameters = new ArrayList<NameValuePair>();
		parameters.add(new BasicNameValuePair("cashReceiptJson", allocationReceiveJson));
		Map<String, Object> result = this.postToEbs(this.ebsUrl + "callCashReceipt", parameters);

		if (!(Boolean) result.get("success")) {
			JSONObject content = new JSONObject();
			content.put("cashReceiptJson", allocationReceiveJson);
			result.put("content", JSON.toJSONString(content));
		}

		LOGGER.info("sendCashReciptToEbs, result=" + result);
		return result;
	}

	/**
	 * 发送【退款】信息到EBS，并保存发送结果
	 * 
	 * @param tdCashRefundInf
	 * @return
	 */
	public Map<String, Object> sendCashRefundToEbs(TdCashRefundInf tdCashRefundInf) {
		LOGGER.info("sendCashRefundToEbs, tdCashRefundInf=" + tdCashRefundInf);

		CashRefund cashRefund = new CashRefund();
		cashRefund.setAmount(toString(tdCashRefundInf.getAmount()));
		cashRefund.setDescription(toString(tdCashRefundInf.getDescription()));
		cashRefund.setDiySiteCode(toString(tdCashRefundInf.getDiySiteCode()));
		cashRefund.setOrderHeaderId(toString(tdCashRefundInf.getOrderHeaderId()));
		cashRefund.setProductType(toString(tdCashRefundInf.getProductType()));
		cashRefund.setRefundClass(toString(tdCashRefundInf.getRefundClass()));
		cashRefund.setRefundDate(DateFormatUtils.format(tdCashRefundInf.getRefundDate(), DateUtil.FORMAT_DATETIME));
		cashRefund.setRefundId(toString(tdCashRefundInf.getRefundId()));
		cashRefund.setRefundNumber(toString(tdCashRefundInf.getRefundNumber()));
		cashRefund.setRefundType(toString(tdCashRefundInf.getRefundType()));
		cashRefund.setReturnNumber(toString(tdCashRefundInf.getReturnNumber()));
		cashRefund.setRtHeaderId(toString(tdCashRefundInf.getRtHeaderId()));
		cashRefund.setSobId(toString(tdCashRefundInf.getSobId()));
		cashRefund.setUserid(toString(tdCashRefundInf.getUserid()));
		cashRefund.setUsername(toString(tdCashRefundInf.getUsername()));
		cashRefund.setUserphone(toString(tdCashRefundInf.getUserphone()));

		String allocationReceiveJson = JSON.toJSONString(cashRefund);
		List<NameValuePair> parameters = new ArrayList<NameValuePair>();
		parameters.add(new BasicNameValuePair("cashRefundJson", allocationReceiveJson));
		Map<String, Object> result = this.postToEbs(this.ebsUrl + "callCashRefund", parameters);

		if (!(Boolean) result.get("success")) {
			JSONObject content = new JSONObject();
			content.put("cashRefundJson", allocationReceiveJson);
			result.put("content", JSON.toJSONString(content));
		}

		LOGGER.info("sendCashRefundToEbs, result=" + result);
		return result;
	}
}
