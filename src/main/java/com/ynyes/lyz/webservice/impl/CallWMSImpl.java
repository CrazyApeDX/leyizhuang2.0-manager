/**
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements. See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership. The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
// START SNIPPET: service
package com.ynyes.lyz.webservice.impl;

import java.io.IOException;
import java.io.StringReader;
import java.io.UnsupportedEncodingException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.jws.WebService;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.apache.commons.lang3.StringUtils;
import org.apache.geronimo.mail.util.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import com.ynyes.lyz.entity.TdBackDetail;
import com.ynyes.lyz.entity.TdBackMain;
import com.ynyes.lyz.entity.TdCity;
import com.ynyes.lyz.entity.TdDeliveryInfo;
import com.ynyes.lyz.entity.TdDeliveryInfoDetail;
import com.ynyes.lyz.entity.TdDiySiteInventory;
import com.ynyes.lyz.entity.TdDiySiteInventoryLog;
import com.ynyes.lyz.entity.TdGoods;
import com.ynyes.lyz.entity.TdOrder;
import com.ynyes.lyz.entity.TdReturnNote;
import com.ynyes.lyz.entity.TdTbwRecd;
import com.ynyes.lyz.entity.TdTbwRecm;
import com.ynyes.lyz.interfaces.entity.TdTbOmD;
import com.ynyes.lyz.interfaces.entity.TdTbOmM;
import com.ynyes.lyz.interfaces.entity.TdTbwBackRecD;
import com.ynyes.lyz.interfaces.entity.TdTbwBackRecM;
import com.ynyes.lyz.interfaces.entity.TdTbwWasted;
import com.ynyes.lyz.interfaces.entity.TdTbwWholeSepDirection;
import com.ynyes.lyz.interfaces.service.TdTbOmDService;
import com.ynyes.lyz.interfaces.service.TdTbOmMService;
import com.ynyes.lyz.interfaces.service.TdTbwBackRecDService;
import com.ynyes.lyz.interfaces.service.TdTbwBackRecMService;
import com.ynyes.lyz.interfaces.service.TdTbwWastedService;
import com.ynyes.lyz.service.TdBackDetailService;
import com.ynyes.lyz.service.TdBackMainService;
import com.ynyes.lyz.service.TdCityService;
import com.ynyes.lyz.service.TdDeliveryInfoDetailService;
import com.ynyes.lyz.service.TdDeliveryInfoService;
import com.ynyes.lyz.service.TdDiySiteInventoryLogService;
import com.ynyes.lyz.service.TdDiySiteInventoryService;
import com.ynyes.lyz.service.TdGoodsService;
import com.ynyes.lyz.service.TdOrderService;
import com.ynyes.lyz.service.TdReturnNoteService;
import com.ynyes.lyz.service.TdTbwRecdService;
import com.ynyes.lyz.service.TdTbwRecmService;
import com.ynyes.lyz.service.TdTbwWholeSepDirectionService;
import com.ynyes.lyz.webservice.ICallWMS;

@WebService
public class CallWMSImpl implements ICallWMS {

	@Autowired
	private TdDeliveryInfoService tdDeliveryInfoService;

	@Autowired
	private TdDeliveryInfoDetailService tdDeliveryInfoDetailService;

	@Autowired
	private TdOrderService tdOrderService;

	@Autowired
	private TdBackMainService tdBackMainService;

	@Autowired
	private TdBackDetailService tdBackDetailService;

	@Autowired
	private TdReturnNoteService tdReturnNoteService;

	@Autowired
	private TdDiySiteInventoryService tdDiySiteInventoryService;

	@Autowired
	private TdDiySiteInventoryLogService tdDiySiteInventoryLogService;

	@Autowired
	private TdTbwRecdService tdTbwRecdService;

	@Autowired
	private TdTbwRecmService tdTbwRecmService;

	@Autowired
	private TdTbwBackRecMService tdTbwBackRecMService;

	@Autowired
	private TdTbwBackRecDService TdTbwBackRecDService;

	@Autowired
	private TdTbOmDService tdTbOmDService;

	@Autowired
	private TdTbOmMService tdTbOmMService;

	@Autowired
	private TdGoodsService tdGoodsService;

	@Autowired
	private TdCityService tdCityService;

	@Autowired
	private TdTbwWastedService tdTbwWastedService;
	
	@Autowired
	private TdTbwWholeSepDirectionService tdTbwWholeSepDirectionService;

	public String GetWMSInfo(String STRTABLE, String STRTYPE, String XML) {
		System.out.println("MDJ:WMSInfo called：" + STRTABLE);

		if (null == STRTABLE || STRTABLE.isEmpty() || STRTABLE.equals("?")) {
			return "<RESULTS><STATUS><CODE>1</CODE><MESSAGE>STRTABLE参数错误</MESSAGE></STATUS></RESULTS>";
		}

		if (null == XML || XML.isEmpty() || XML.equals("?")) {
			return "<RESULTS><STATUS><CODE>1</CODE><MESSAGE>XML参数错误</MESSAGE></STATUS></RESULTS>";
		}

		String XMLStr = XML.trim();

		XMLStr = XML.replace("\n", "");

		byte[] decoded = Base64.decode(XMLStr);

		String decodedXML = null;

		try {
			decodedXML = new String(decoded, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			System.out.println("UnsupportedEncodingException for decodedXML");
			e.printStackTrace();
		}

		if (null == decodedXML || decodedXML.isEmpty()) {
			return "<RESULTS><STATUS><CODE>1</CODE><MESSAGE>解密后XML数据为空</MESSAGE></STATUS></RESULTS>";
		}

		System.out.println(decodedXML);

		// 解析XML
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder = null;
		try {
			builder = factory.newDocumentBuilder();
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
			return "<RESULTS><STATUS><CODE>1</CODE><MESSAGE>解密后xml参数错误</MESSAGE></STATUS></RESULTS>";
		}

		Document document = null;

		InputSource is = new InputSource(new StringReader(decodedXML));

		try {
			document = builder.parse(is);
		} catch (SAXException | IOException e) {
			e.printStackTrace();
			return "<RESULTS><STATUS><CODE>1</CODE><MESSAGE>解密后xml格式不对</MESSAGE></STATUS></RESULTS>";
		}
		NodeList nodeList = document.getElementsByTagName("TABLE");

		if (STRTABLE.equalsIgnoreCase("tbw_send_task_m")) // 配送出库主档
		{
			for (int i = 0; i < nodeList.getLength(); i++) {
				String c_task_no = null;// 任务编号
				String c_begin_dt = null;// 开始时间
				String c_end_dt = null;// 结束时间
				String c_wh_no = null;// 仓库编号
				String c_op_status = null;// 操作状态(初始、作业中、完成、结案)
				String c_op_user = null;// 作业人员
				String c_modified_userno = null;// 修改人员
				String c_owner_no = null;// 委托业主
				String c_reserved1 = null;// 分单号
				String c_Driver = null;// 送货员
				Long cCompanyId = null;// 公司id
				String cTaskType = null;// 任务类型

				Node node = nodeList.item(i);
				NodeList childNodeList = node.getChildNodes();

				for (int idx = 0; idx < childNodeList.getLength(); idx++) {
					Node childNode = childNodeList.item(idx);

					if (childNode.getNodeType() == Node.ELEMENT_NODE) {
						// 比较字段名
						if (childNode.getNodeName().equalsIgnoreCase("c_task_no")) {
							// 有值
							if (null != childNode.getChildNodes().item(0)) {
								c_task_no = childNode.getChildNodes().item(0).getNodeValue();
							}
							// 空值
							else {
								c_task_no = null;
							}
						} else if (childNode.getNodeName().equalsIgnoreCase("c_begin_dt")) {
							if (null != childNode.getChildNodes().item(0)) {
								c_begin_dt = childNode.getChildNodes().item(0).getNodeValue();
							}
						} else if (childNode.getNodeName().equalsIgnoreCase("c_end_dt")) {
							if (null != childNode.getChildNodes().item(0)) {
								c_end_dt = childNode.getChildNodes().item(0).getNodeValue();
							}
						} else if (childNode.getNodeName().equalsIgnoreCase("c_wh_no")) {
							if (null != childNode.getChildNodes().item(0)) {
								c_wh_no = childNode.getChildNodes().item(0).getNodeValue();
							}
						} else if (childNode.getNodeName().equalsIgnoreCase("c_op_status")) {
							if (null != childNode.getChildNodes().item(0)) {
								c_op_status = childNode.getChildNodes().item(0).getNodeValue();
							}
						} else if (childNode.getNodeName().equalsIgnoreCase("c_op_user")) {
							if (null != childNode.getChildNodes().item(0)) {
								c_op_user = childNode.getChildNodes().item(0).getNodeValue();
							}
						} else if (childNode.getNodeName().equalsIgnoreCase("c_modified_userno")) {
							if (null != childNode.getChildNodes().item(0)) {
								c_modified_userno = childNode.getChildNodes().item(0).getNodeValue();
							}
						} else if (childNode.getNodeName().equalsIgnoreCase("c_owner_no")) {
							if (null != childNode.getChildNodes().item(0)) {
								c_owner_no = childNode.getChildNodes().item(0).getNodeValue();
							}
						} else if (childNode.getNodeName().equalsIgnoreCase("c_reserved1")) {
							if (null != childNode.getChildNodes().item(0)) {
								c_reserved1 = childNode.getChildNodes().item(0).getNodeValue();
							}
						} else if (childNode.getNodeName().equalsIgnoreCase("c_Driver")) {
							if (null != childNode.getChildNodes().item(0)) {
								c_Driver = childNode.getChildNodes().item(0).getNodeValue();
							}
						} else if (childNode.getNodeName().equalsIgnoreCase("c_company_id")) {
							if (null != childNode.getChildNodes().item(0)) {
								cCompanyId = Long.parseLong(childNode.getChildNodes().item(0).getNodeValue());
							}
						} else if (childNode.getNodeName().equalsIgnoreCase("c_task_type")) {
							if (null != childNode.getChildNodes().item(0)) {
								cTaskType = childNode.getChildNodes().item(0).getNodeValue();
							}
						}
					}
				}

				// 保存 修改
				TdDeliveryInfo tdDeliveryInfo = tdDeliveryInfoService.findByTaskNo(c_task_no);
				if (tdDeliveryInfo != null) {
					return "<RESULTS><STATUS><CODE>1</CODE>编号:" + c_task_no
							+ " 已存在<MESSAGE></MESSAGE></STATUS></RESULTS>";
				}
				tdDeliveryInfo = new TdDeliveryInfo();
				tdDeliveryInfo.setTaskNo(c_task_no);
				tdDeliveryInfo.setWhNo(c_wh_no);
				tdDeliveryInfo.setDriver(c_Driver);

				SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
				if (c_begin_dt != null) {
					try {
						Date startdate = sdf.parse(c_begin_dt);
						tdDeliveryInfo.setBeginDt(startdate);
					} catch (ParseException e) {
						e.printStackTrace();
					}
				}
				if (c_end_dt != null) {
					try {
						Date enddate = sdf.parse(c_end_dt);
						tdDeliveryInfo.setEndDt(enddate);
					} catch (ParseException e) {
						e.printStackTrace();
					}
				}

				tdDeliveryInfo.setOrderNumber(c_reserved1);
				tdDeliveryInfo.setOpStatus(c_op_status);
				tdDeliveryInfo.setOpUser(c_op_user);
				tdDeliveryInfo.setModifiedUserno(c_modified_userno);
				tdDeliveryInfo.setOwnerNo(c_owner_no);
				tdDeliveryInfo.setcCompanyId(cCompanyId);
				tdDeliveryInfo.setcTaskType(cTaskType);
				if (cTaskType != null && cTaskType.contains("退货")) {
					if (cCompanyId == null) {
						return "<RESULTS><STATUS><CODE>1</CODE>采购退货城市id不能为空<MESSAGE></MESSAGE></STATUS></RESULTS>";
					}
					TdCity tdCity = tdCityService.findBySobIdCity(cCompanyId);
					if (tdCity == null) {
						return "<RESULTS><STATUS><CODE>1</CODE>app不存在城市id为:" + cCompanyId
								+ "的城市<MESSAGE></MESSAGE></STATUS></RESULTS>";
					}
					List<TdDeliveryInfoDetail> infoDetails = tdDeliveryInfoDetailService.findByTaskNo(c_task_no);
					if (infoDetails == null || infoDetails.size() < 1) {
						return "<RESULTS><STATUS><CODE>1</CODE>未找到任务编号为" + c_task_no
								+ " 的明细，请先传明细，在主档<MESSAGE></MESSAGE></STATUS></RESULTS>";
					}
					for (TdDeliveryInfoDetail infoDetail : infoDetails) {
						String cGcode = infoDetail.getgCode();
						TdGoods tdGoods = tdGoodsService.findByCode(cGcode);
						TdDiySiteInventory inventory = tdDiySiteInventoryService
								.findByGoodsCodeAndRegionIdAndDiySiteIdIsNull(cGcode, cCompanyId);
						if (inventory == null) {
							inventory = new TdDiySiteInventory();
							inventory.setGoodsCode(tdGoods.getCode());
							inventory.setGoodsId(tdGoods.getId());
							inventory.setCategoryId(tdGoods.getCategoryId());
							inventory.setCategoryIdTree(tdGoods.getCategoryIdTree());
							inventory.setCategoryTitle(tdGoods.getCategoryTitle());
							inventory.setGoodsTitle(tdGoods.getTitle());
							inventory.setRegionId(cCompanyId);
							inventory.setRegionName(tdCity.getCityName());
						}
						Double doubleFromStr = infoDetail.getBackNumber();
						doubleFromStr = doubleFromStr * 100;
						Long cRecQty = doubleFromStr.longValue();
						cRecQty = cRecQty / 100;
						inventory.setInventory(inventory.getInventory() - cRecQty);
						tdDiySiteInventoryService.save(inventory);
						tdDiySiteInventoryLogService.saveChangeLog(inventory, -cRecQty, null, null,
								TdDiySiteInventoryLog.CHANGETYPE_CITY_CG_SUB);
					}
				}

				tdDeliveryInfoService.save(tdDeliveryInfo);
			}
			return "<RESULTS><STATUS><CODE>0</CODE><MESSAGE></MESSAGE></STATUS></RESULTS>";
		}
		if (STRTABLE.equalsIgnoreCase("tbw_send_task_Driver")) // 配送出库修改配送员
		{
			for (int i = 0; i < nodeList.getLength(); i++) {
				String c_task_no = null;// 任务编号
				String c_begin_dt = null;// 开始时间
				String c_end_dt = null;// 结束时间
				String c_wh_no = null;// 仓库编号
				String c_op_status = null;// 操作状态(初始、作业中、完成、结案)
				String c_op_user = null;// 作业人员
				String c_modified_userno = null;// 修改人员
				String c_owner_no = null;// 委托业主
				String c_reserved1 = null;// 分单号
				String c_Driver = null;// 送货员

				Node node = nodeList.item(i);
				NodeList childNodeList = node.getChildNodes();

				for (int idx = 0; idx < childNodeList.getLength(); idx++) {
					Node childNode = childNodeList.item(idx);

					if (childNode.getNodeType() == Node.ELEMENT_NODE) {
						// 比较字段名
						if (childNode.getNodeName().equalsIgnoreCase("c_task_no")) {
							// 有值
							if (null != childNode.getChildNodes().item(0)) {
								c_task_no = childNode.getChildNodes().item(0).getNodeValue();
							}
							// 空值
							else {
								c_task_no = null;
							}
						} else if (childNode.getNodeName().equalsIgnoreCase("c_begin_dt")) {
							if (null != childNode.getChildNodes().item(0)) {
								c_begin_dt = childNode.getChildNodes().item(0).getNodeValue();
							}
						} else if (childNode.getNodeName().equalsIgnoreCase("c_end_dt")) {
							if (null != childNode.getChildNodes().item(0)) {
								c_end_dt = childNode.getChildNodes().item(0).getNodeValue();
							}
						} else if (childNode.getNodeName().equalsIgnoreCase("c_wh_no")) {
							if (null != childNode.getChildNodes().item(0)) {
								c_wh_no = childNode.getChildNodes().item(0).getNodeValue();
							}
						} else if (childNode.getNodeName().equalsIgnoreCase("c_op_status")) {
							if (null != childNode.getChildNodes().item(0)) {
								c_op_status = childNode.getChildNodes().item(0).getNodeValue();
							}
						} else if (childNode.getNodeName().equalsIgnoreCase("c_op_user")) {
							if (null != childNode.getChildNodes().item(0)) {
								c_op_user = childNode.getChildNodes().item(0).getNodeValue();
							}
						} else if (childNode.getNodeName().equalsIgnoreCase("c_modified_userno")) {
							if (null != childNode.getChildNodes().item(0)) {
								c_modified_userno = childNode.getChildNodes().item(0).getNodeValue();
							}
						} else if (childNode.getNodeName().equalsIgnoreCase("c_owner_no")) {
							if (null != childNode.getChildNodes().item(0)) {
								c_owner_no = childNode.getChildNodes().item(0).getNodeValue();
							}
						} else if (childNode.getNodeName().equalsIgnoreCase("c_reserved1")) {
							if (null != childNode.getChildNodes().item(0)) {
								c_reserved1 = childNode.getChildNodes().item(0).getNodeValue();
							}
						} else if (childNode.getNodeName().equalsIgnoreCase("c_Driver")) {
							if (null != childNode.getChildNodes().item(0)) {
								c_Driver = childNode.getChildNodes().item(0).getNodeValue();
							}
						}

					}
				}

				// 保存 修改
				TdDeliveryInfo tdDeliveryInfo = tdDeliveryInfoService.findByTaskNo(c_task_no);
				if (tdDeliveryInfo == null) {
					tdDeliveryInfo = new TdDeliveryInfo();
				}
				tdDeliveryInfo.setTaskNo(c_task_no);
				tdDeliveryInfo.setWhNo(c_wh_no);
				tdDeliveryInfo.setDriver(c_Driver);

				SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
				if (c_begin_dt != null) {
					try {
						Date startdate = sdf.parse(c_begin_dt);
						tdDeliveryInfo.setBeginDt(startdate);
					} catch (ParseException e) {
						e.printStackTrace();
					}
				}
				if (c_end_dt != null) {
					try {
						Date enddate = sdf.parse(c_end_dt);
						tdDeliveryInfo.setEndDt(enddate);
					} catch (ParseException e) {
						e.printStackTrace();
					}
				}

				tdDeliveryInfo.setOrderNumber(c_reserved1);
				tdDeliveryInfo.setOpStatus(c_op_status);
				tdDeliveryInfo.setOpUser(c_op_user);
				tdDeliveryInfo.setModifiedUserno(c_modified_userno);
				tdDeliveryInfo.setOwnerNo(c_owner_no);
				tdDeliveryInfoService.save(tdDeliveryInfo);
			}
			return "<RESULTS><STATUS><CODE>0</CODE><MESSAGE></MESSAGE></STATUS></RESULTS>";
		} else if (STRTABLE.equalsIgnoreCase("tbw_send_task_d")) // 配送出库明细
		{
			for (int i = 0; i < nodeList.getLength(); i++) {
				String c_task_no = null;// 任务编号
				String c_begin_dt = null;// 开始时间
				String c_end_dt = null;// 结束时间
				String c_wh_no = null;// 仓库编号
				String c_op_status = null;// 操作状态(初始、作业中、完成、结案)
				String c_op_user = null;// 作业人员
				String c_modified_userno = null;// 修改人员
				String c_owner_no = null;// 委托业主
				String c_gcode = null;// 商品编号
				Double c_d_ack_qty = null; // 实回数量
				Double c_d_request_qty = null;// 请求数量
				String c_reserved1 = null;// 分单号

				Node node = nodeList.item(i);
				NodeList childNodeList = node.getChildNodes();

				for (int idx = 0; idx < childNodeList.getLength(); idx++) {
					Node childNode = childNodeList.item(idx);

					if (childNode.getNodeType() == Node.ELEMENT_NODE) {
						// 比较字段名
						if (childNode.getNodeName().equalsIgnoreCase("c_task_no")) {
							// 有值
							if (null != childNode.getChildNodes().item(0)) {
								c_task_no = childNode.getChildNodes().item(0).getNodeValue();
							}
							// 空值
							else {
								c_task_no = null;
							}
						} else if (childNode.getNodeName().equalsIgnoreCase("c_reserved1")) {
							if (null != childNode.getChildNodes().item(0)) {
								c_reserved1 = childNode.getChildNodes().item(0).getNodeValue();
							}
						} else if (childNode.getNodeName().equalsIgnoreCase("c_begin_dt")) {
							if (null != childNode.getChildNodes().item(0)) {
								c_begin_dt = childNode.getChildNodes().item(0).getNodeValue();
							}
						} else if (childNode.getNodeName().equalsIgnoreCase("c_end_dt")) {
							if (null != childNode.getChildNodes().item(0)) {
								c_end_dt = childNode.getChildNodes().item(0).getNodeValue();
							}
						} else if (childNode.getNodeName().equalsIgnoreCase("c_wh_no")) {
							if (null != childNode.getChildNodes().item(0)) {
								c_wh_no = childNode.getChildNodes().item(0).getNodeValue();
							}
						} else if (childNode.getNodeName().equalsIgnoreCase("c_op_status")) {
							if (null != childNode.getChildNodes().item(0)) {
								c_op_status = childNode.getChildNodes().item(0).getNodeValue();
							}
						} else if (childNode.getNodeName().equalsIgnoreCase("c_op_user")) {
							if (null != childNode.getChildNodes().item(0)) {
								c_op_user = childNode.getChildNodes().item(0).getNodeValue();
							}
						} else if (childNode.getNodeName().equalsIgnoreCase("c_modified_userno")) {
							if (null != childNode.getChildNodes().item(0)) {
								c_modified_userno = childNode.getChildNodes().item(0).getNodeValue();
							}
						} else if (childNode.getNodeName().equalsIgnoreCase("c_owner_no")) {
							if (null != childNode.getChildNodes().item(0)) {
								c_owner_no = childNode.getChildNodes().item(0).getNodeValue();
							}
						} else if (childNode.getNodeName().equalsIgnoreCase("c_gcode")) {
							if (null != childNode.getChildNodes().item(0)) {
								c_gcode = childNode.getChildNodes().item(0).getNodeValue();
							}
						} else if (childNode.getNodeName().equalsIgnoreCase("c_d_ack_qty")) {
							if (null != childNode.getChildNodes().item(0)) {
								c_d_ack_qty = Double.parseDouble(childNode.getChildNodes().item(0).getNodeValue());
							}
						} else if (childNode.getNodeName().equalsIgnoreCase("c_d_request_qty")) {
							if (null != childNode.getChildNodes().item(0)) {
								String string = childNode.getChildNodes().item(0).getNodeValue();
								c_d_request_qty = Double.parseDouble(string);
							}
						}

					}
				}
				// 保存 修改
				TdDeliveryInfoDetail infoDetail = new TdDeliveryInfoDetail();
				infoDetail.setTaskNo(c_task_no);
				infoDetail.setWhNo(c_wh_no);
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
				if (c_begin_dt != null) {
					try {
						Date startdate = sdf.parse(c_begin_dt);
						infoDetail.setBeginDt(startdate);
					} catch (ParseException e) {
						e.printStackTrace();
					}
				}
				if (c_end_dt != null) {
					try {
						Date enddate = sdf.parse(c_end_dt);
						infoDetail.setEndDt(enddate);
					} catch (ParseException e) {
						e.printStackTrace();
					}
				}
				infoDetail.setOpStatus(c_op_status);
				infoDetail.setOpUser(c_op_user);
				infoDetail.setModifiedUserno(c_modified_userno);
				infoDetail.setOwnerNo(c_owner_no);
				infoDetail.setgCode(c_gcode);
				infoDetail.setRequstNumber(c_d_request_qty);
				infoDetail.setBackNumber(c_d_ack_qty);
				infoDetail.setSubOrderNumber(c_reserved1);
				TdGoods tdGoods = tdGoodsService.findByCode(c_gcode);
				if (tdGoods == null) {
					return "<RESULTS><STATUS><CODE>1</CODE><MESSAGE>编码为" + c_gcode
							+ "的商品不存在</MESSAGE></STATUS></RESULTS>";
				}
				tdDeliveryInfoDetailService.save(infoDetail);
				if (c_reserved1 != null) {
					TdOrder tdOrder = tdOrderService.findByOrderNumber(c_reserved1);
					if (null == tdOrder) {
						return "<RESULTS><STATUS><CODE>1</CODE><MESSAGE>单号为" + c_reserved1
								+ "的单据不存在</MESSAGE></STATUS></RESULTS>";
					}
					List<TdOrder> orders = tdOrderService.findByMainOrderNumberIgnoreCase(tdOrder.getMainOrderNumber());
					if (null != orders && orders.size() > 0) {
						for (TdOrder order : orders) {
							if (order != null && order.getStatusId() != null && order.getStatusId() == 3L) {
								order.setStatusId(4L);
								order.setSendTime(new Date());
								tdOrderService.save(order);
							}
						}
					}
				}
			}
			return "<RESULTS><STATUS><CODE>0</CODE><MESSAGE></MESSAGE></STATUS></RESULTS>";
		} else if (STRTABLE.equalsIgnoreCase("tbw_back_m"))// 退货入库单 主档
		{
			for (int i = 0; i < nodeList.getLength(); i++) {
				// 仓库编号
				String c_wh_no = null;
				// 委托业主
				String c_owner_no = null;
				// 返配验收单号
				String c_rec_no = null;
				// 打印次数
				Integer c_print_times = null;
				// 返配单号
				String c_back_no = null;
				// 返配单类型（一般返配）
				String c_back_type = null;
				// 返配单分类(存储型、越库型)
				String c_back_class = null;
				// 客户信息
				String c_customer_no = null;
				// 月台
				String c_plat_no = null;
				// 验收人员
				String c_rec_user = null;
				// 操作工具(表单,pda,电子标签)
				String c_op_tools = null;
				// 操作状态(初始、作业中、完成、结案)
				String c_op_status = null;
				// 备注
				String c_note = null;
				// 建立人员
				String c_mk_userno = null;
				// 修改人员
				String c_modified_userno = null;
				// 门店退单
				String c_po_no = null;
				// 开始操作时间
				String c_begin_dt = null;
				// 结束操作时间
				String c_end_dt = null;
				// 建立时间
				String c_mk_dt = null;
				// 修改时间
				String c_modified_dt = null;

				// 配送人员
				String driver = null;

				Node node = nodeList.item(i);
				NodeList childNodeList = node.getChildNodes();

				for (int idx = 0; idx < childNodeList.getLength(); idx++) {
					Node childNode = childNodeList.item(idx);

					if (childNode.getNodeType() == Node.ELEMENT_NODE) {
						// 比较字段名
						if (childNode.getNodeName().equalsIgnoreCase("c_wh_no")) {
							// 有值
							if (null != childNode.getChildNodes().item(0)) {
								c_wh_no = childNode.getChildNodes().item(0).getNodeValue();
							}
							// 空值
							else {
								c_wh_no = null;
							}
						} else if (childNode.getNodeName().equalsIgnoreCase("c_owner_no")) {
							if (null != childNode.getChildNodes().item(0)) {
								c_owner_no = childNode.getChildNodes().item(0).getNodeValue();
							}
						} else if (childNode.getNodeName().equalsIgnoreCase("c_driver")) {
							if (null != childNode.getChildNodes().item(0)) {
								driver = childNode.getChildNodes().item(0).getNodeValue();
							}
						} else if (childNode.getNodeName().equalsIgnoreCase("c_rec_no")) {
							if (null != childNode.getChildNodes().item(0)) {
								c_rec_no = childNode.getChildNodes().item(0).getNodeValue();
							}
						} else if (childNode.getNodeName().equalsIgnoreCase("c_print_times")) {
							if (null != childNode.getChildNodes().item(0)) {
								c_print_times = Integer.parseInt(childNode.getChildNodes().item(0).getNodeValue());
							}
						} else if (childNode.getNodeName().equalsIgnoreCase("c_back_no")) {
							if (null != childNode.getChildNodes().item(0)) {
								c_back_no = childNode.getChildNodes().item(0).getNodeValue();
							}
						} else if (childNode.getNodeName().equalsIgnoreCase("c_back_type")) {
							if (null != childNode.getChildNodes().item(0)) {
								c_back_type = childNode.getChildNodes().item(0).getNodeValue();
							}
						} else if (childNode.getNodeName().equalsIgnoreCase("c_back_class")) {
							if (null != childNode.getChildNodes().item(0)) {
								c_back_class = childNode.getChildNodes().item(0).getNodeValue();
							}
						} else if (childNode.getNodeName().equalsIgnoreCase("c_customer_no")) {
							if (null != childNode.getChildNodes().item(0)) {
								c_customer_no = childNode.getChildNodes().item(0).getNodeValue();
							}
						} else if (childNode.getNodeName().equalsIgnoreCase("c_plat_no")) {
							if (null != childNode.getChildNodes().item(0)) {
								c_plat_no = childNode.getChildNodes().item(0).getNodeValue();
							}
						} else if (childNode.getNodeName().equalsIgnoreCase("c_rec_user")) {
							if (null != childNode.getChildNodes().item(0)) {
								c_rec_user = childNode.getChildNodes().item(0).getNodeValue();
							}
						} else if (childNode.getNodeName().equalsIgnoreCase("c_op_tools")) {
							if (null != childNode.getChildNodes().item(0)) {
								c_op_tools = childNode.getChildNodes().item(0).getNodeValue();
							}
						} else if (childNode.getNodeName().equalsIgnoreCase("c_note")) {
							if (null != childNode.getChildNodes().item(0)) {
								c_note = childNode.getChildNodes().item(0).getNodeValue();
							}
						} else if (childNode.getNodeName().equalsIgnoreCase("c_mk_userno")) {
							if (null != childNode.getChildNodes().item(0)) {
								c_mk_userno = childNode.getChildNodes().item(0).getNodeValue();
							}
						} else if (childNode.getNodeName().equalsIgnoreCase("c_modified_userno")) {
							if (null != childNode.getChildNodes().item(0)) {
								c_modified_userno = childNode.getChildNodes().item(0).getNodeValue();
							}
						} else if (childNode.getNodeName().equalsIgnoreCase("c_po_no")) {
							if (null != childNode.getChildNodes().item(0)) {
								c_po_no = childNode.getChildNodes().item(0).getNodeValue();
							}
						} else if (childNode.getNodeName().equalsIgnoreCase("c_begin_dt")) {
							if (null != childNode.getChildNodes().item(0)) {
								c_begin_dt = childNode.getChildNodes().item(0).getNodeValue();
							}
						} else if (childNode.getNodeName().equalsIgnoreCase("c_end_dt")) {
							if (null != childNode.getChildNodes().item(0)) {
								c_end_dt = childNode.getChildNodes().item(0).getNodeValue();
							}
						} else if (childNode.getNodeName().equalsIgnoreCase("c_mk_dt")) {
							if (null != childNode.getChildNodes().item(0)) {
								c_mk_dt = childNode.getChildNodes().item(0).getNodeValue();
							}
						} else if (childNode.getNodeName().equalsIgnoreCase("c_modified_dt")) {
							if (null != childNode.getChildNodes().item(0)) {
								c_modified_dt = childNode.getChildNodes().item(0).getNodeValue();
							}
						}
					}
				}

				// 保存 修改
				TdBackMain tdBackMain = tdBackMainService.findByRecNo(c_rec_no);
				if (tdBackMain == null) {
					tdBackMain = new TdBackMain();
					tdBackMain.setRecNo(c_rec_no);
				}
				tdBackMain.setOwnerNo(c_owner_no);
				tdBackMain.setWhNo(c_wh_no);
				tdBackMain.setPrintTimes(c_print_times);
				tdBackMain.setBackClass(c_back_class);
				tdBackMain.setBackNo(c_back_no);
				tdBackMain.setBackType(c_back_type);
				tdBackMain.setCustomerNo(c_customer_no);
				tdBackMain.setPlatNo(c_plat_no);
				tdBackMain.setRecUser(c_rec_user);
				tdBackMain.setOpTools(c_op_tools);
				tdBackMain.setOpStatus(c_op_status);
				tdBackMain.setNote(c_note);
				tdBackMain.setMkUserno(c_mk_userno);
				tdBackMain.setModifiedUserno(c_modified_userno);
				tdBackMain.setPoNo(c_po_no);
				tdBackMain.setDriver(driver);
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
				if (c_begin_dt != null) {
					try {
						Date c_begin = sdf.parse(c_begin_dt);
						tdBackMain.setBeginDt(c_begin);
					} catch (ParseException e) {
						e.printStackTrace();
					}
				}
				if (c_end_dt != null) {
					try {
						Date enddate = sdf.parse(c_end_dt);
						tdBackMain.setEndDt(enddate);
					} catch (ParseException e) {
						e.printStackTrace();
					}
				}
				if (c_mk_dt != null) {
					try {
						Date c_mk = sdf.parse(c_mk_dt);
						tdBackMain.setMkDt(c_mk);
					} catch (ParseException e) {
						e.printStackTrace();
					}
				}
				if (c_modified_dt != null) {
					try {
						Date c_modified = sdf.parse(c_modified_dt);
						tdBackMain.setModifiedDt(c_modified);
					} catch (ParseException e) {
						e.printStackTrace();
					}
				}
				tdBackMainService.save(tdBackMain);

				TdReturnNote tdReturnNote = tdReturnNoteService.findByReturnNumber(c_po_no);
				if (tdReturnNote != null) {
					// tdReturnNote.setStatusId(5L);
					//
					// TdOrder order =
					// tdOrderService.findByOrderNumber(tdReturnNote.getOrderNumber());
					// if (order != null)
					// {
					// if (order.getStatusId() == 9 || order.getStatusId() == 10
					// || order.getStatusId() == 11 || order.getStatusId() ==
					// 12)
					// {
					// order.setStatusId(12L);
					// tdOrderService.save(order);
					// }
					// TdUser tdUser =
					// tdUserService.findByUsername(order.getUsername());
					// tdPriceCountService.cashAndCouponBack(order, tdUser);
					// }
					tdReturnNote.setDriver(driver);
					tdReturnNoteService.save(tdReturnNote);
				}

				// if (c_reserved1 != null)
				// {
				// TdOrder tdOrder =
				// tdOrderService.findByOrderNumber(c_reserved1);
				// if (tdOrder != null && tdOrder.getStatusId() != null &&
				// tdOrder.getStatusId() == 3L) {
				// tdOrder.setStatusId(4L);
				// tdOrderService.save(tdOrder);
				// }
				// }
			}
			return "<RESULTS><STATUS><CODE>0</CODE><MESSAGE></MESSAGE></STATUS></RESULTS>";
		} else if (STRTABLE.equalsIgnoreCase("tbw_back_d"))// 退货入库单 详细
		{
			for (int i = 0; i < nodeList.getLength(); i++) {
				// 委托业主
				String ownerNo = null;

				// 任务编号
				String recNo = null;

				// 任务id
				String recId = null;

				// 商品编号
				String gcode = null;

				// 包装数量
				String packQty = null;

				// 价格
				String price = null;

				// 验收赠品数量
				String giftQty = null;

				// 验收赠品不良品数量
				String badQty = null;

				// 验收数量
				String recQty = null;

				// 作业人员
				String recUser = null;

				// 月台
				String platNo = null;

				// 操作工具(表单,pda,电子标签)
				String opTools = null;

				// 状态（初始、作业中、完成、结案）
				String opStatus = null;

				// 预留
				String reserved1 = null;

				// 预留
				String reserved2 = null;

				// 预留
				String reserved3 = null;

				// 预留
				String reserved4 = null;

				// 预留
				String reserved5 = null;

				// 备注
				String note = null;

				// 建立人员
				String mkUserno = null;

				// 修改人员
				String modifiedUserno = null;

				// 建立时间
				String c_mk_dt = null;

				// 修改时间
				String c_modified_dt = null;

				Node node = nodeList.item(i);
				NodeList childNodeList = node.getChildNodes();

				for (int idx = 0; idx < childNodeList.getLength(); idx++) {
					Node childNode = childNodeList.item(idx);

					if (childNode.getNodeType() == Node.ELEMENT_NODE) {
						// 比较字段名
						if (childNode.getNodeName().equalsIgnoreCase("c_owner_no")) {
							// 有值
							if (null != childNode.getChildNodes().item(0)) {
								ownerNo = childNode.getChildNodes().item(0).getNodeValue();
							}
							// 空值
							else {
								ownerNo = null;
							}
						} else if (childNode.getNodeName().equalsIgnoreCase("c_rec_no")) {
							if (null != childNode.getChildNodes().item(0)) {
								recNo = childNode.getChildNodes().item(0).getNodeValue();
							}
						} else if (childNode.getNodeName().equalsIgnoreCase("c_rec_id")) {
							if (null != childNode.getChildNodes().item(0)) {
								recId = childNode.getChildNodes().item(0).getNodeValue();
							}
						} else if (childNode.getNodeName().equalsIgnoreCase("c_gcode")) {
							if (null != childNode.getChildNodes().item(0)) {
								gcode = childNode.getChildNodes().item(0).getNodeValue();
							}
						} else if (childNode.getNodeName().equalsIgnoreCase("c_pack_qty")) {
							if (null != childNode.getChildNodes().item(0)) {
								packQty = childNode.getChildNodes().item(0).getNodeValue();
							}
						} else if (childNode.getNodeName().equalsIgnoreCase("c_price")) {
							if (null != childNode.getChildNodes().item(0)) {
								price = childNode.getChildNodes().item(0).getNodeValue();
							}
						} else if (childNode.getNodeName().equalsIgnoreCase("c_gift_qty")) {
							if (null != childNode.getChildNodes().item(0)) {
								giftQty = childNode.getChildNodes().item(0).getNodeValue();
							}
						} else if (childNode.getNodeName().equalsIgnoreCase("c_bad_qty")) {
							if (null != childNode.getChildNodes().item(0)) {
								badQty = childNode.getChildNodes().item(0).getNodeValue();
							}
						} else if (childNode.getNodeName().equalsIgnoreCase("c_rec_qty")) {
							if (null != childNode.getChildNodes().item(0)) {
								recQty = childNode.getChildNodes().item(0).getNodeValue();
							}
						} else if (childNode.getNodeName().equalsIgnoreCase("c_rec_user")) {
							if (null != childNode.getChildNodes().item(0)) {
								recUser = childNode.getChildNodes().item(0).getNodeValue();
							}
						} else if (childNode.getNodeName().equalsIgnoreCase("c_plat_no")) {
							if (null != childNode.getChildNodes().item(0)) {
								platNo = childNode.getChildNodes().item(0).getNodeValue();
							}
						} else if (childNode.getNodeName().equalsIgnoreCase("c_op_tools")) {
							if (null != childNode.getChildNodes().item(0)) {
								opTools = childNode.getChildNodes().item(0).getNodeValue();
							}
						} else if (childNode.getNodeName().equalsIgnoreCase("c_op_status")) {
							if (null != childNode.getChildNodes().item(0)) {
								opStatus = childNode.getChildNodes().item(0).getNodeValue();
							}
						} else if (childNode.getNodeName().equalsIgnoreCase("c_reserved1")) {
							if (null != childNode.getChildNodes().item(0)) {
								reserved1 = childNode.getChildNodes().item(0).getNodeValue();
							}
						} else if (childNode.getNodeName().equalsIgnoreCase("c_reserved2")) {
							if (null != childNode.getChildNodes().item(0)) {
								reserved2 = childNode.getChildNodes().item(0).getNodeValue();
							}
						} else if (childNode.getNodeName().equalsIgnoreCase("c_reserved3")) {
							if (null != childNode.getChildNodes().item(0)) {
								reserved3 = childNode.getChildNodes().item(0).getNodeValue();
							}
						} else if (childNode.getNodeName().equalsIgnoreCase("c_reserved4")) {
							if (null != childNode.getChildNodes().item(0)) {
								reserved4 = childNode.getChildNodes().item(0).getNodeValue();
							}
						} else if (childNode.getNodeName().equalsIgnoreCase("c_reserved5")) {
							if (null != childNode.getChildNodes().item(0)) {
								reserved5 = childNode.getChildNodes().item(0).getNodeValue();
							}
						} else if (childNode.getNodeName().equalsIgnoreCase("c_note")) {
							if (null != childNode.getChildNodes().item(0)) {
								note = childNode.getChildNodes().item(0).getNodeValue();
							}
						} else if (childNode.getNodeName().equalsIgnoreCase("c_mk_userno")) {
							if (null != childNode.getChildNodes().item(0)) {
								mkUserno = childNode.getChildNodes().item(0).getNodeValue();
							}
						} else if (childNode.getNodeName().equalsIgnoreCase("c_mk_dt")) {
							if (null != childNode.getChildNodes().item(0)) {
								c_mk_dt = childNode.getChildNodes().item(0).getNodeValue();
							}
						} else if (childNode.getNodeName().equalsIgnoreCase("c_modified_userno")) {
							if (null != childNode.getChildNodes().item(0)) {
								c_mk_dt = childNode.getChildNodes().item(0).getNodeValue();
							}
						} else if (childNode.getNodeName().equalsIgnoreCase("c_modified_dt")) {
							if (null != childNode.getChildNodes().item(0)) {
								c_modified_dt = childNode.getChildNodes().item(0).getNodeValue();
							}
						}
					}
				}

				// 保存 修改
				TdBackDetail tdBackDetail = new TdBackDetail();
				tdBackDetail.setOwnerNo(ownerNo);
				tdBackDetail.setRecNo(recNo);
				tdBackDetail.setRecId(recId);
				tdBackDetail.setGcode(gcode);
				tdBackDetail.setPackQty(packQty);
				tdBackDetail.setPrice(price);
				tdBackDetail.setGiftQty(giftQty);
				tdBackDetail.setBadQty(badQty);
				tdBackDetail.setRecQty(recQty);
				tdBackDetail.setRecUser(recUser);
				tdBackDetail.setPlatNo(platNo);
				tdBackDetail.setOpTools(opTools);
				tdBackDetail.setOpStatus(opStatus);
				tdBackDetail.setReserved1(reserved1);
				tdBackDetail.setReserved2(reserved2);
				tdBackDetail.setReserved3(reserved3);
				tdBackDetail.setReserved4(reserved4);
				tdBackDetail.setReserved5(reserved5);
				tdBackDetail.setNote(note);
				tdBackDetail.setMkUserno(mkUserno);
				tdBackDetail.setMkDt(c_mk_dt);
				tdBackDetail.setModifiedUserno(modifiedUserno);
				tdBackDetail.setModifiedDt(c_modified_dt);
				tdBackDetailService.save(tdBackDetail);

			}
			return "<RESULTS><STATUS><CODE>0</CODE><MESSAGE></MESSAGE></STATUS></RESULTS>";
		} else if (STRTABLE.equalsIgnoreCase("tbw_rec_d")) // 城市采购入库明细
		{
			for (int i = 0; i < nodeList.getLength(); i++) {
				// 委托业主
				String ownerNo = null;
				// 任务编号
				String recNo = null;
				// 任务id
				Long recId = null;
				// 商品编号
				String gcode = null;
				// 包装数量
				Long packQty = null;
				// 价格
				String price = null;
				// 品质(良品、不良品、赠品)
				String itemType = null;
				// 验收赠品数量
				String giftQty = null;
				// 验收赠品不良品数量
				String badQty = null;
				// 验收数量(红冲数量)
				String recQty = null;
				// 作业人员
				String recUserno = null;
				// 月台
				String platNo = null;
				// 操作工具(表单,pda,电子标签)
				String opTools = null;
				// 状态（初始、作业中、完成、结案）
				String opStatus = null;
				// 预留
				String reserved1 = null;
				// 预留
				String reserved2 = null;
				// 预留
				String reserved3 = null;
				// 预留
				String reserved4 = null;
				// 预留
				String reserved5 = null;
				// 备注
				String note = null;
				// 建立人员
				String mkUserno = null;
				// 建立时间
				String mkDt = null;
				// 修改人员
				String modifiedUserno = null;
				// 修改时间
				String modifiedDt = null;
				// 分播数量
				String c_dps_qty = null;
				// 有明细转到主档
				// //城市sobId
				// Long cCompanyId = null;//分公司Id

				Node node = nodeList.item(i);
				NodeList childNodeList = node.getChildNodes();

				for (int idx = 0; idx < childNodeList.getLength(); idx++) {
					Node childNode = childNodeList.item(idx);
					if (childNode.getNodeType() == Node.ELEMENT_NODE) {
						if (childNode.getNodeName().equalsIgnoreCase("c_owner_no")) {
							if (null != childNode.getChildNodes().item(0)) {
								ownerNo = childNode.getChildNodes().item(0).getNodeValue();
							}
						}
						if (childNode.getNodeName().equalsIgnoreCase("c_rec_no")) {
							if (null != childNode.getChildNodes().item(0)) {
								recNo = childNode.getChildNodes().item(0).getNodeValue();
							}
						}
						if (childNode.getNodeName().equalsIgnoreCase("c_rec_id")) {
							if (null != childNode.getChildNodes().item(0)) {
								recId = Long.parseLong(childNode.getChildNodes().item(0).getNodeValue());
							}
						}
						if (childNode.getNodeName().equalsIgnoreCase("c_gcode")) {
							if (null != childNode.getChildNodes().item(0)) {
								gcode = childNode.getChildNodes().item(0).getNodeValue();
							}
						}
						if (childNode.getNodeName().equalsIgnoreCase("c_pack_qty")) {
							if (null != childNode.getChildNodes().item(0)) {
								packQty = Long.parseLong(childNode.getChildNodes().item(0).getNodeValue());
							}
						}
						if (childNode.getNodeName().equalsIgnoreCase("c_price")) {
							if (null != childNode.getChildNodes().item(0)) {
								price = childNode.getChildNodes().item(0).getNodeValue();
							}
						}
						if (childNode.getNodeName().equalsIgnoreCase("c_item_type")) {
							if (null != childNode.getChildNodes().item(0)) {
								recNo = childNode.getChildNodes().item(0).getNodeValue();
							}
						}
						if (childNode.getNodeName().equalsIgnoreCase("c_gift_qty")) {
							if (null != childNode.getChildNodes().item(0)) {
								giftQty = childNode.getChildNodes().item(0).getNodeValue();
							}
						}
						if (childNode.getNodeName().equalsIgnoreCase("c_bad_qty")) {
							if (null != childNode.getChildNodes().item(0)) {
								badQty = childNode.getChildNodes().item(0).getNodeValue();
							}
						}
						if (childNode.getNodeName().equalsIgnoreCase("c_rec_qty")) {
							if (null != childNode.getChildNodes().item(0)) {
								recQty = childNode.getChildNodes().item(0).getNodeValue();
							}
						}
						if (childNode.getNodeName().equalsIgnoreCase("c_rec_userno")) {
							if (null != childNode.getChildNodes().item(0)) {
								recUserno = childNode.getChildNodes().item(0).getNodeValue();
							}
						}
						if (childNode.getNodeName().equalsIgnoreCase("c_plat_no")) {
							if (null != childNode.getChildNodes().item(0)) {
								platNo = childNode.getChildNodes().item(0).getNodeValue();
							}
						}
						if (childNode.getNodeName().equalsIgnoreCase("c_op_tools")) {
							if (null != childNode.getChildNodes().item(0)) {
								opTools = childNode.getChildNodes().item(0).getNodeValue();
							}
						}
						if (childNode.getNodeName().equalsIgnoreCase("c_op_status")) {
							if (null != childNode.getChildNodes().item(0)) {
								opStatus = childNode.getChildNodes().item(0).getNodeValue();
							}
						}
						if (childNode.getNodeName().equalsIgnoreCase("c_reserved1")) {
							if (null != childNode.getChildNodes().item(0)) {
								reserved1 = childNode.getChildNodes().item(0).getNodeValue();
							}
						}
						if (childNode.getNodeName().equalsIgnoreCase("c_reserved2")) {
							if (null != childNode.getChildNodes().item(0)) {
								reserved2 = childNode.getChildNodes().item(0).getNodeValue();
							}
						}
						if (childNode.getNodeName().equalsIgnoreCase("c_reserved3")) {
							if (null != childNode.getChildNodes().item(0)) {
								reserved3 = childNode.getChildNodes().item(0).getNodeValue();
							}
						}
						if (childNode.getNodeName().equalsIgnoreCase("c_reserved4")) {
							if (null != childNode.getChildNodes().item(0)) {
								reserved4 = childNode.getChildNodes().item(0).getNodeValue();
							}
						}
						if (childNode.getNodeName().equalsIgnoreCase("c_reserved5")) {
							if (null != childNode.getChildNodes().item(0)) {
								reserved5 = childNode.getChildNodes().item(0).getNodeValue();
							}
						}
						if (childNode.getNodeName().equalsIgnoreCase("c_note")) {
							if (null != childNode.getChildNodes().item(0)) {
								note = childNode.getChildNodes().item(0).getNodeValue();
							}
						}
						if (childNode.getNodeName().equalsIgnoreCase("c_mk_userno")) {
							if (null != childNode.getChildNodes().item(0)) {
								mkUserno = childNode.getChildNodes().item(0).getNodeValue();
							}
						}
						if (childNode.getNodeName().equalsIgnoreCase("c_mk_dt")) {
							if (null != childNode.getChildNodes().item(0)) {
								mkDt = childNode.getChildNodes().item(0).getNodeValue();
							}
						}
						if (childNode.getNodeName().equalsIgnoreCase("c_modified_userno")) {
							if (null != childNode.getChildNodes().item(0)) {
								modifiedUserno = childNode.getChildNodes().item(0).getNodeValue();
							}
						}
						if (childNode.getNodeName().equalsIgnoreCase("c_modified_dt")) {
							if (null != childNode.getChildNodes().item(0)) {
								modifiedDt = childNode.getChildNodes().item(0).getNodeValue();
							}
						}
						if (childNode.getNodeName().equalsIgnoreCase("c_dps_qty")) {
							if (null != childNode.getChildNodes().item(0)) {
								c_dps_qty = childNode.getChildNodes().item(0).getNodeValue();
							}
						}
					}
				}
				// 保存 修改
				TdTbwRecd recd = new TdTbwRecd();
				recd.setOwnerNo(ownerNo);
				recd.setRecNo(recNo);
				recd.setRecId(recId);
				recd.setGcode(gcode);
				recd.setPackQty(packQty);
				recd.setPrice(price);
				recd.setItemType(itemType);
				recd.setGiftQty(giftQty);
				recd.setBadQty(badQty);
				recd.setRecQty(recQty);
				recd.setMkUserno(mkUserno);
				recd.setRecUserno(recUserno);
				recd.setPlatNo(platNo);
				recd.setOpTools(opTools);
				recd.setOpStatus(opStatus);
				recd.setReserved1(reserved1);
				recd.setReserved2(reserved2);
				recd.setReserved3(reserved3);
				recd.setReserved4(reserved4);
				recd.setReserved5(reserved5);
				recd.setNote(note);
				recd.setMkUserno(mkUserno);
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
				if (mkDt != null) {
					try {
						Date md_dt = sdf.parse(mkDt);
						recd.setMkDt(md_dt);
					} catch (ParseException e) {
						e.printStackTrace();
					}
				}
				recd.setModifiedUserno(modifiedUserno);
				if (modifiedDt != null) {
					try {
						Date modified_dt = sdf.parse(modifiedDt);
						recd.setMkDt(modified_dt);
					} catch (ParseException e) {
						e.printStackTrace();
					}
				}
				recd.setDpsQty(c_dps_qty);
				recd.setInitTime(new Date());
				TdGoods tdGoods = tdGoodsService.findByCodeAndStatus(gcode, 1l);
				if (StringUtils.isBlank(recQty)) {
					return "<RESULTS><STATUS><CODE>1</CODE><MESSAGE>商品验收数量不能为空</MESSAGE></STATUS></RESULTS>";
				}
				if (tdGoods == null) {
					return "<RESULTS><STATUS><CODE>1</CODE><MESSAGE>商品编码为：" + gcode
							+ "的商品不存在或者不可用</MESSAGE></STATUS></RESULTS>";
				}
				Long count = this.tdTbwRecdService.countByGcodeAndRecNo(gcode, recNo);
				if (count > 0) {
					return "<RESULTS><STATUS><CODE>1</CODE><MESSAGE>该数据已经成功接收，请勿重复传输</MESSAGE></STATUS></RESULTS>";
				}
				
				tdTbwRecdService.save(recd);
			}
			return "<RESULTS><STATUS><CODE>0</CODE><MESSAGE></MESSAGE></STATUS></RESULTS>";
		} else if (STRTABLE.equalsIgnoreCase("tbw_rec_m"))// 城市采购入库主档
		{
			for (int i = 0; i < nodeList.getLength(); i++) {
				// 仓库编号
				String cWhNo = null;
				// 委托业主
				String cOwnerNo = null;
				// 验收单号(当类型是红冲时为:红冲单号)
				String cRecNo = null;
				// 打印次数
				Integer cPrintTimes = null;
				// 验收汇总
				String cGatherRecNo = null;
				// 进货汇总
				String cGatherNo = null;
				// 进货单分类(存储型、越库型)
				String cInClass = null;
				// 进货单号(当类型是红冲时为:验收单号)
				String cInNo = null;
				// 进货单类型(一般订单，紧急订单,红冲多，红冲少)
				String cInType = null;
				// 供应商编码
				String cProviderNo = null;
				// 月台
				String cPlatNo = null;
				// 验收人员
				String cRecUserno = null;
				// 操作工具(表单,pda,电子标签)
				String cOpTools = null;
				// 操作状态(初始、作业中、完成、结案)
				String cOpStatus = null;
				// 开始操作时间
				String cBeginDt = null;
				// 结束操作时间
				String cEndDt = null;
				// 备注
				String cNote = null;
				// 建立人员
				String cMkUserno = null;
				// 建立时间
				String cMkDt = null;
				// 修改人员
				String cModifiedUserno = null;
				// 修改时间
				String cModifiedDt = null;
				// 采购单号
				String cPoNo = null;
				// 城市sobId
				Long cCompanyId = null;// 分公司Id

				Node node = nodeList.item(i);
				NodeList childNodeList = node.getChildNodes();

				// 遍历所有TABLE中的字段
				for (int idx = 0; idx < childNodeList.getLength(); idx++) {
					Node childNode = childNodeList.item(idx);

					if (childNode.getNodeType() == Node.ELEMENT_NODE) {
						if (childNode.getNodeName().equalsIgnoreCase("c_wh_no")) {
							if (null != childNode.getChildNodes().item(0)) {
								cWhNo = childNode.getChildNodes().item(0).getNodeValue();
							}
						} else if (childNode.getNodeName().equalsIgnoreCase("c_owner_no")) {
							if (null != childNode.getChildNodes().item(0)) {
								cOwnerNo = childNode.getChildNodes().item(0).getNodeValue();
							}
						} else if (childNode.getNodeName().equalsIgnoreCase("c_rec_no")) {
							if (null != childNode.getChildNodes().item(0)) {
								cRecNo = childNode.getChildNodes().item(0).getNodeValue();
							}
						} else if (childNode.getNodeName().equalsIgnoreCase("c_wh_no")) {
							if (null != childNode.getChildNodes().item(0)) {
								cWhNo = childNode.getChildNodes().item(0).getNodeValue();
							}
						} else if (childNode.getNodeName().equalsIgnoreCase("c_print_times")) {
							if (null != childNode.getChildNodes().item(0)) {
								cPrintTimes = Integer.parseInt(childNode.getChildNodes().item(0).getNodeValue());
							}
						} else if (childNode.getNodeName().equalsIgnoreCase("c_gather_rec_no")) {
							if (null != childNode.getChildNodes().item(0)) {
								cGatherRecNo = childNode.getChildNodes().item(0).getNodeValue();
							}
						} else if (childNode.getNodeName().equalsIgnoreCase("c_gather_no")) {
							if (null != childNode.getChildNodes().item(0)) {
								cGatherNo = childNode.getChildNodes().item(0).getNodeValue();
							}
						} else if (childNode.getNodeName().equalsIgnoreCase("c_in_class")) {
							if (null != childNode.getChildNodes().item(0)) {
								cInClass = childNode.getChildNodes().item(0).getNodeValue();
							}
						} else if (childNode.getNodeName().equalsIgnoreCase("c_in_no")) {
							if (null != childNode.getChildNodes().item(0)) {
								cInNo = childNode.getChildNodes().item(0).getNodeValue();
							}
						} else if (childNode.getNodeName().equalsIgnoreCase("c_in_type")) {
							if (null != childNode.getChildNodes().item(0)) {
								cInType = childNode.getChildNodes().item(0).getNodeValue();
							}
						} else if (childNode.getNodeName().equalsIgnoreCase("c_provider_no")) {
							if (null != childNode.getChildNodes().item(0)) {
								cProviderNo = childNode.getChildNodes().item(0).getNodeValue();
							}
						} else if (childNode.getNodeName().equalsIgnoreCase("c_plat_no")) {
							if (null != childNode.getChildNodes().item(0)) {
								cPlatNo = childNode.getChildNodes().item(0).getNodeValue();
							}
						} else if (childNode.getNodeName().equalsIgnoreCase("c_rec_userno")) {
							if (null != childNode.getChildNodes().item(0)) {
								cRecUserno = childNode.getChildNodes().item(0).getNodeValue();
							}
						} else if (childNode.getNodeName().equalsIgnoreCase("c_op_tools")) {
							if (null != childNode.getChildNodes().item(0)) {
								cOpTools = childNode.getChildNodes().item(0).getNodeValue();
							}
						} else if (childNode.getNodeName().equalsIgnoreCase("c_op_status")) {
							if (null != childNode.getChildNodes().item(0)) {
								cOpStatus = childNode.getChildNodes().item(0).getNodeValue();
							}
						} else if (childNode.getNodeName().equalsIgnoreCase("c_begin_dt")) {
							if (null != childNode.getChildNodes().item(0)) {
								cBeginDt = childNode.getChildNodes().item(0).getNodeValue();
							}
						} else if (childNode.getNodeName().equalsIgnoreCase("c_end_dt")) {
							if (null != childNode.getChildNodes().item(0)) {
								cEndDt = childNode.getChildNodes().item(0).getNodeValue();
							}
						} else if (childNode.getNodeName().equalsIgnoreCase("c_note")) {
							if (null != childNode.getChildNodes().item(0)) {
								cNote = childNode.getChildNodes().item(0).getNodeValue();
							}
						} else if (childNode.getNodeName().equalsIgnoreCase("c_mk_userno")) {
							if (null != childNode.getChildNodes().item(0)) {
								cMkUserno = childNode.getChildNodes().item(0).getNodeValue();
							}
						} else if (childNode.getNodeName().equalsIgnoreCase("c_mk_dt")) {
							if (null != childNode.getChildNodes().item(0)) {
								cMkDt = childNode.getChildNodes().item(0).getNodeValue();
							}
						} else if (childNode.getNodeName().equalsIgnoreCase("c_modified_userno")) {
							if (null != childNode.getChildNodes().item(0)) {
								cModifiedUserno = childNode.getChildNodes().item(0).getNodeValue();
							}
						} else if (childNode.getNodeName().equalsIgnoreCase("c_modified_dt")) {
							if (null != childNode.getChildNodes().item(0)) {
								cModifiedDt = childNode.getChildNodes().item(0).getNodeValue();
							}
						} else if (childNode.getNodeName().equalsIgnoreCase("c_po_no")) {
							if (null != childNode.getChildNodes().item(0)) {
								cPoNo = childNode.getChildNodes().item(0).getNodeValue();
							}
						} else if (childNode.getNodeName().equalsIgnoreCase("c_company_id")) {
							if (null != childNode.getChildNodes().item(0)) {
								cCompanyId = Long.parseLong(childNode.getChildNodes().item(0).getNodeValue());
							}
						}
					}
				}
				// 保存修改
				TdTbwRecm recm = tdTbwRecmService.findByCRecNo(cRecNo);
				if (recm == null) {
					recm = new TdTbwRecm();
				}
				recm.setcWhNo(cWhNo);
				recm.setcOwnerNo(cOwnerNo);
				recm.setcRecNo(cRecNo);
				recm.setcPrintTimes(cPrintTimes);
				recm.setcGatherRecNo(cGatherRecNo);
				recm.setcGatherNo(cGatherNo);
				recm.setcInClass(cInClass);
				recm.setcInNo(cInNo);
				recm.setcInType(cInType);
				recm.setcProviderNo(cProviderNo);
				recm.setcPlatNo(cPlatNo);
				recm.setcRecUserno(cRecUserno);
				recm.setcOpTools(cOpTools);
				recm.setcOpStatus(cOpStatus);
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
				if (cBeginDt != null) {
					try {
						Date c_beginDt = sdf.parse(cBeginDt);
						recm.setcBeginDt(c_beginDt);
					} catch (ParseException e) {
						e.printStackTrace();
					}
				}
				if (cEndDt != null) {
					try {
						Date c_endDt = sdf.parse(cEndDt);
						recm.setcEndDt(c_endDt);
					} catch (ParseException e) {
						e.printStackTrace();
					}
				}
				recm.setcNote(cNote);
				recm.setcMkUserno(cMkUserno);
				if (cMkDt != null) {
					try {
						Date c_mkD = sdf.parse(cMkDt);
						recm.setcMkDt(c_mkD);
					} catch (ParseException e) {
						e.printStackTrace();
					}
				}
				recm.setcModifiedUserno(cModifiedUserno);
				if (cModifiedDt != null) {
					try {
						Date c_modifi = sdf.parse(cModifiedDt);
						recm.setcModifiedDt(c_modifi);
					} catch (ParseException e) {
						e.printStackTrace();
					}
				}
				recm.setcPoNo(cPoNo);
				recm.setcCompanyId(cCompanyId);
				recm.setInitTime(new Date());
				tdTbwRecmService.save(recm);
				if (cCompanyId == null) {
					return "<RESULTS><STATUS><CODE>1</CODE><MESSAGE>城市编码不存在</MESSAGE></STATUS></RESULTS>";
				}
				List<TdTbwRecd> tbwRecds = tdTbwRecdService.findByRecNo(cRecNo);
				if (tbwRecds == null || tbwRecds.size() < 1) {
					return "<RESULTS><STATUS><CODE>1</CODE><MESSAGE>" + cRecNo + "：未找到明细</MESSAGE></STATUS></RESULTS>";
				}
				for (TdTbwRecd tdTbwRecd : tbwRecds) {
					String gcode = tdTbwRecd.getGcode();
					TdDiySiteInventory inventory = tdDiySiteInventoryService
							.findByGoodsCodeAndRegionIdAndDiySiteIdIsNull(gcode, cCompanyId);
					if (inventory == null) {
						inventory = new TdDiySiteInventory();
						TdCity city = tdCityService.findOne(cCompanyId);
						TdGoods goods = tdGoodsService.findByCode(gcode);
						inventory.setRegionId(city.getId());
						inventory.setRegionName(city.getCityName());
						inventory.setGoodsId(goods.getId());
						inventory.setGoodsTitle(goods.getTitle());
						inventory.setGoodsCode(goods.getCode());
						inventory.setInventory(0L);
						inventory.setCategoryId(goods.getCategoryId());
						inventory.setCategoryTitle(goods.getCategoryTitle());
						inventory.setCategoryIdTree(goods.getCategoryIdTree());
						
//						return "<RESULTS><STATUS><CODE>1</CODE><MESSAGE>城市编码为：" + cCompanyId + "的城市不存在或SKU为" + gcode
//								+ "的商品不存在</MESSAGE></STATUS></RESULTS>";
					}
					Double.parseDouble(tdTbwRecd.getRecQty());
					Double cRecQty = Double.parseDouble(tdTbwRecd.getRecQty());
					cRecQty = cRecQty * 100;
					Long recQtyFromDouble = cRecQty.longValue();
					recQtyFromDouble = recQtyFromDouble / 100;
					inventory.setInventory(inventory.getInventory() + recQtyFromDouble);
					tdDiySiteInventoryService.save(inventory);
					tdDiySiteInventoryLogService.saveChangeLog(inventory, recQtyFromDouble, null, null,
							TdDiySiteInventoryLog.CHANGETYPE_CITY_ADD);
				}
			}

			return "<RESULTS><STATUS><CODE>0</CODE><MESSAGE></MESSAGE></STATUS></RESULTS>";
			
		}else if(STRTABLE.equalsIgnoreCase("tbw_whole_sep_direction")){ //整转零
			for (int i = 0; i < nodeList.getLength(); i++) {
				// 仓库编号
				String cWhNo = null;
				// 委托业主
				String cOwnerNo = null;
				// 指示编号
				String cDirectNo = null;
				// 批示ID
				Long cDirectId = null;
				//任务类型
				String cTaskType = null;
				//作业类型
				String cOpType = null;
				//源储位
				String cSLocationNo = null;
				//源储位ID
				Long cSLocationId = null;
				//源容器内部编号
				String cSContainerSerno = null;
				//源容器板号
				String cSContainerNo = null;
				//目的储位
				String cDLocationNo = null;
				//目的储位ID
				Long cDLocationId = null;
				//目的容器内部板号
				String cDContainerSerno = null;
				//目的容器
				String cDContainerNo = null;
				//商品编号
				String cGcode = null;
				
				String cDGcode = null;
				//库存属性
				Long cStockattrId = null;
				//包装数量
				Long cPackQty = null;
				//源单号
				String cSourceNo = null;
				//数量
				Double cQty = null;
				
				Double cInQty = null;
				//波次
				String cWaveNo = null;
				//状态（初始、已分配）
				String cStatus = null;
				//任务编号
				String cTaskNo = null;
				//任务id
				Long cTaskId = null;
				//预留1
				String cReserved1 = null;
				//预留2
				String cReserved2 = null;
				//预留3
				String cReserved3 = null;
				//预留4
				String cReserved4 = null;
				//预留5
				String cReserved5 = null;
				//备注
				String cNote = null;
				//开始操作时间
				String cBeginDt = null;
				// 结束操作时间
				String cEndDt = null;
				// 建立人员
				String cMkUserno = null;
				// 建立时间
				String cMkDt = null;
				//修改人员
				String cModifiedUserno = null;
				//修改时间
				String cModifiedDt = null;
				//分公司ID
				Long cCompanyId = null;
			
				Node node = nodeList.item(i);
				NodeList childNodeList = node.getChildNodes();
				// 遍历所有TABLE中的字段
				for (int idx = 0; idx < childNodeList.getLength(); idx++) {
					Node childNode = childNodeList.item(idx);

					if (childNode.getNodeType() == Node.ELEMENT_NODE) {
						if (childNode.getNodeName().equalsIgnoreCase("c_wh_no")) {
							if (null != childNode.getChildNodes().item(0)) {
								cWhNo = childNode.getChildNodes().item(0).getNodeValue();
							}
						} else if (childNode.getNodeName().equalsIgnoreCase("c_owner_no")) {
							if (null != childNode.getChildNodes().item(0)) {
								cOwnerNo = childNode.getChildNodes().item(0).getNodeValue();
							}
						} else if (childNode.getNodeName().equalsIgnoreCase("c_direct_no")) {
							if (null != childNode.getChildNodes().item(0)) {
								cDirectNo = childNode.getChildNodes().item(0).getNodeValue();
							}
						} else if (childNode.getNodeName().equalsIgnoreCase("c_direct_id")) {
							if (null != childNode.getChildNodes().item(0)) {
								cDirectId = Long.parseLong(childNode.getChildNodes().item(0).getNodeValue());
							}
						} else if (childNode.getNodeName().equalsIgnoreCase("c_task_type")) {
							if (null != childNode.getChildNodes().item(0)) {
								cTaskType = childNode.getChildNodes().item(0).getNodeValue();
							}
						} else if (childNode.getNodeName().equalsIgnoreCase("c_op_type")) {
							if (null != childNode.getChildNodes().item(0)) {
								cOpType = childNode.getChildNodes().item(0).getNodeValue();
							}
						} else if (childNode.getNodeName().equalsIgnoreCase("c_s_location_no")) {
							if (null != childNode.getChildNodes().item(0)) {
								cSLocationNo = childNode.getChildNodes().item(0).getNodeValue();
							}
						} else if (childNode.getNodeName().equalsIgnoreCase("c_s_location_id")) {
							if (null != childNode.getChildNodes().item(0)) {
								cSLocationId = Long.parseLong(childNode.getChildNodes().item(0).getNodeValue());
							}
						} else if (childNode.getNodeName().equalsIgnoreCase("c_s_container_serno")) {
							if (null != childNode.getChildNodes().item(0)) {
								cSContainerSerno = childNode.getChildNodes().item(0).getNodeValue();
							}
						} else if (childNode.getNodeName().equalsIgnoreCase("c_s_container_no")) {
							if (null != childNode.getChildNodes().item(0)) {
								cSContainerNo = childNode.getChildNodes().item(0).getNodeValue();
							}
						} else if (childNode.getNodeName().equalsIgnoreCase("c_d_location_no")) {
							if (null != childNode.getChildNodes().item(0)) {
								cDLocationNo = childNode.getChildNodes().item(0).getNodeValue();
							}
						} else if (childNode.getNodeName().equalsIgnoreCase("c_d_location_id")) {
							if (null != childNode.getChildNodes().item(0)) {
								cDLocationId = Long.parseLong(childNode.getChildNodes().item(0).getNodeValue());
							}
						} else if (childNode.getNodeName().equalsIgnoreCase("c_d_container_serno")) {
							if (null != childNode.getChildNodes().item(0)) {
								cDContainerSerno = childNode.getChildNodes().item(0).getNodeValue();
							}
						} else if (childNode.getNodeName().equalsIgnoreCase("c_d_container_no")) {
							if (null != childNode.getChildNodes().item(0)) {
								cDContainerNo = childNode.getChildNodes().item(0).getNodeValue();
							}
						} else if (childNode.getNodeName().equalsIgnoreCase("c_gcode")) {
							if (null != childNode.getChildNodes().item(0)) {
								cGcode = childNode.getChildNodes().item(0).getNodeValue();
							}
						} else if (childNode.getNodeName().equalsIgnoreCase("c_d_gcode")) {
							if (null != childNode.getChildNodes().item(0)) {
								cDGcode = childNode.getChildNodes().item(0).getNodeValue();
							}
						} else if (childNode.getNodeName().equalsIgnoreCase("c_stockattr_id")) {
							if (null != childNode.getChildNodes().item(0)) {
								cStockattrId = Long.parseLong(childNode.getChildNodes().item(0).getNodeValue());
							}
						} else if (childNode.getNodeName().equalsIgnoreCase("c_pack_qty")) {
							if (null != childNode.getChildNodes().item(0)) {
								cPackQty = Long.parseLong(childNode.getChildNodes().item(0).getNodeValue());
							}
						} else if (childNode.getNodeName().equalsIgnoreCase("c_source_no")) {
							if (null != childNode.getChildNodes().item(0)) {
								cSourceNo = childNode.getChildNodes().item(0).getNodeValue();
							}
						} else if (childNode.getNodeName().equalsIgnoreCase("c_qty")) {
							if (null != childNode.getChildNodes().item(0)) {
								cQty = Double.parseDouble(childNode.getChildNodes().item(0).getNodeValue());
							}
						} else if (childNode.getNodeName().equalsIgnoreCase("c_in_qty")) {
							if (null != childNode.getChildNodes().item(0)) {
								cInQty = Double.parseDouble(childNode.getChildNodes().item(0).getNodeValue());
							}
						} else if (childNode.getNodeName().equalsIgnoreCase("c_wave_no")) {
							if (null != childNode.getChildNodes().item(0)) {
								cWaveNo = childNode.getChildNodes().item(0).getNodeValue();
							}
						} else if (childNode.getNodeName().equalsIgnoreCase("c_status")) {
							if (null != childNode.getChildNodes().item(0)) {
								cStatus = childNode.getChildNodes().item(0).getNodeValue();
							}
						} else if (childNode.getNodeName().equalsIgnoreCase("c_task_no")) {
							if (null != childNode.getChildNodes().item(0)) {
								cTaskNo = childNode.getChildNodes().item(0).getNodeValue();
							}
						}else if (childNode.getNodeName().equalsIgnoreCase("c_task_id")) {
							if (null != childNode.getChildNodes().item(0)) {
								cTaskId = Long.parseLong(childNode.getChildNodes().item(0).getNodeValue());
							}
						}else if (childNode.getNodeName().equalsIgnoreCase("c_reserved1")) {
							if (null != childNode.getChildNodes().item(0)) {
								cReserved1 = childNode.getChildNodes().item(0).getNodeValue();
							}
						}else if (childNode.getNodeName().equalsIgnoreCase("c_reserved2")) {
							if (null != childNode.getChildNodes().item(0)) {
								cReserved2 = childNode.getChildNodes().item(0).getNodeValue();
							}
						}else if (childNode.getNodeName().equalsIgnoreCase("c_reserved3")) {
							if (null != childNode.getChildNodes().item(0)) {
								cReserved3 = childNode.getChildNodes().item(0).getNodeValue();
							}
						}else if (childNode.getNodeName().equalsIgnoreCase("c_reserved4")) {
							if (null != childNode.getChildNodes().item(0)) {
								cReserved4 = childNode.getChildNodes().item(0).getNodeValue();
							}
						}else if (childNode.getNodeName().equalsIgnoreCase("c_reserved5")) {
							if (null != childNode.getChildNodes().item(0)) {
								cReserved5 = childNode.getChildNodes().item(0).getNodeValue();
							}
						}else if (childNode.getNodeName().equalsIgnoreCase("c_note")) {
							if (null != childNode.getChildNodes().item(0)) {
								cNote = childNode.getChildNodes().item(0).getNodeValue();
							}
						}else if (childNode.getNodeName().equalsIgnoreCase("c_begin_dt")) {
							if (null != childNode.getChildNodes().item(0)) {
								cBeginDt = childNode.getChildNodes().item(0).getNodeValue();
							}
						}else if (childNode.getNodeName().equalsIgnoreCase("c_end_dt")) {
							if (null != childNode.getChildNodes().item(0)) {
								cEndDt = childNode.getChildNodes().item(0).getNodeValue();
							}
						}else if (childNode.getNodeName().equalsIgnoreCase("c_mk_userno")) {
							if (null != childNode.getChildNodes().item(0)) {
								cMkUserno = childNode.getChildNodes().item(0).getNodeValue();
							}
						}else if (childNode.getNodeName().equalsIgnoreCase("c_mk_dt")) {
							if (null != childNode.getChildNodes().item(0)) {
								cMkDt = childNode.getChildNodes().item(0).getNodeValue();
							}
						}else if (childNode.getNodeName().equalsIgnoreCase("c_modified_userno")) {
							if (null != childNode.getChildNodes().item(0)) {
								cModifiedDt = childNode.getChildNodes().item(0).getNodeValue();
							}
						}else if (childNode.getNodeName().equalsIgnoreCase("c_modified_dt")) {
							if (null != childNode.getChildNodes().item(0)) {
								cModifiedDt = childNode.getChildNodes().item(0).getNodeValue();
							}
						}
						else if (childNode.getNodeName().equalsIgnoreCase("c_company_id")) {
							if (null != childNode.getChildNodes().item(0)) {
								cCompanyId = Long.parseLong(childNode.getChildNodes().item(0).getNodeValue());
							}
						}
					}
				}
				
				TdTbwWholeSepDirection tdTbwWholeSepDirection = tdTbwWholeSepDirectionService.findByCOwnerNoAndCDirectNoAndCDirectIdAndCTaskType(cOwnerNo, cDirectNo, cDirectId, cTaskType);
				if(null == tdTbwWholeSepDirection){
					tdTbwWholeSepDirection = new TdTbwWholeSepDirection();
				}
				tdTbwWholeSepDirection.setcWhNo(cWhNo);
				tdTbwWholeSepDirection.setcOwnerNo(cOwnerNo);
				tdTbwWholeSepDirection.setcDirectNo(cDirectNo);
				tdTbwWholeSepDirection.setcDirectId(cDirectId);
				tdTbwWholeSepDirection.setcTaskType(cTaskType);
				tdTbwWholeSepDirection.setcOpType(cOpType);
				tdTbwWholeSepDirection.setcSLocationNo(cSLocationNo);
				tdTbwWholeSepDirection.setcSLocationId(cSLocationId);
				tdTbwWholeSepDirection.setcSContainerSerno(cSContainerSerno);
				tdTbwWholeSepDirection.setcSContainerNo(cSContainerNo);
				tdTbwWholeSepDirection.setcDLocationNo(cDLocationNo);
				tdTbwWholeSepDirection.setcDLocationId(cDLocationId);
				tdTbwWholeSepDirection.setcDContainerSerno(cDContainerSerno);
				tdTbwWholeSepDirection.setcDContainerNo(cDContainerNo);
				tdTbwWholeSepDirection.setcGcode(cGcode);
				tdTbwWholeSepDirection.setcDGcode(cDGcode);
				tdTbwWholeSepDirection.setcStockattrId(cStockattrId);
				tdTbwWholeSepDirection.setcPackQty(cPackQty);
				tdTbwWholeSepDirection.setcSourceNo(cSourceNo);
				tdTbwWholeSepDirection.setcQty(cQty);
				tdTbwWholeSepDirection.setcInQty(cInQty);
				tdTbwWholeSepDirection.setcWaveNo(cWaveNo);
				tdTbwWholeSepDirection.setcStatus(cStatus);
				tdTbwWholeSepDirection.setcTaskNo(cTaskNo);
				tdTbwWholeSepDirection.setcTaskId(cTaskId);
				tdTbwWholeSepDirection.setcReserved1(cReserved1);
				tdTbwWholeSepDirection.setcReserved2(cReserved2);
				tdTbwWholeSepDirection.setcReserved3(cReserved3);
				tdTbwWholeSepDirection.setcReserved4(cReserved4);
				tdTbwWholeSepDirection.setcReserved5(cReserved5);
				tdTbwWholeSepDirection.setcNote(cNote);
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
				if (null != cBeginDt) {
					try {
						Date c_beginDt = sdf.parse(cBeginDt);
						tdTbwWholeSepDirection.setcBeginDt(c_beginDt);
					} catch (ParseException e) {
						e.printStackTrace();
					}
				}
				if (null != cEndDt) {
					try {
						Date c_endDt = sdf.parse(cEndDt);
						tdTbwWholeSepDirection.setcEndDt(c_endDt);
					} catch (ParseException e) {
						e.printStackTrace();
					}
				}
				tdTbwWholeSepDirection.setcMkUserno(cMkUserno);
				if (null != cMkDt) {
					try {
						Date c_mkDt = sdf.parse(cMkDt);
						tdTbwWholeSepDirection.setcEndDt(c_mkDt);
					} catch (ParseException e) {
						e.printStackTrace();
					}
				}
				tdTbwWholeSepDirection.setcModifiedUserno(cModifiedUserno);
				if (null != cModifiedDt) {
					try {
						Date c_modifiedDt = sdf.parse(cModifiedDt);
						tdTbwWholeSepDirection.setcEndDt(c_modifiedDt);
					} catch (ParseException e) {
						e.printStackTrace();
					}
				}
				if (null != cCompanyId) {
					tdTbwWholeSepDirection.setcCompanyId(cCompanyId);
				}
			

				tdTbwWholeSepDirectionService.save(tdTbwWholeSepDirection);
				
				TdDiySiteInventory inventory = null;
				TdDiySiteInventory dInventory = null;
				Long cPackQtyTemp = null;
				Double cInQtyTemp = null;
				if(null != cPackQty){
					cPackQtyTemp = cPackQty;
				}else{
					return "<RESULTS><STATUS><CODE>1</CODE><MESSAGE>cPackQty不能为空!</MESSAGE></STATUS></RESULTS>";
				}
				if(null != cInQty){
					cInQtyTemp = cInQty;
				}else{
					return "<RESULTS><STATUS><CODE>1</CODE><MESSAGE>cInQty不能为空!</MESSAGE></STATUS></RESULTS>";
				}
				
				if(null!=cCompanyId){
					if(null != cGcode){
						inventory = tdDiySiteInventoryService.findByGoodsCodeAndRegionIdAndDiySiteIdIsNull(cGcode, cCompanyId);
						if (null != inventory) {
							inventory.setInventory(inventory.getInventory() - cPackQtyTemp);
						}else{
							return "<RESULTS><STATUS><CODE>1</CODE><MESSAGE>城市编码为：" + cCompanyId + "的城市不存在或SKU为" + cGcode
									+ "的商品不存在</MESSAGE></STATUS></RESULTS>";
						}
					}else{
						return "<RESULTS><STATUS><CODE>1</CODE><MESSAGE>商品编码Gcode不能不为空！</MESSAGE></STATUS></RESULTS>";
					}
					if(null != cDGcode){
						dInventory = tdDiySiteInventoryService.findByGoodsCodeAndRegionIdAndDiySiteIdIsNull(cDGcode,cCompanyId);
						if(null != dInventory){
							dInventory.setInventory(dInventory.getInventory() + cInQtyTemp.longValue());
						}else{
							TdGoods tdGoods = this.tdGoodsService.findByCode(cDGcode);
							if (null != tdGoods) {
								dInventory = new TdDiySiteInventory();
								TdCity city = tdCityService.findOne(cCompanyId);
								dInventory.setRegionId(city.getId());
								dInventory.setRegionName(city.getCityName());
								dInventory.setGoodsId(tdGoods.getId());
								dInventory.setGoodsTitle(tdGoods.getTitle());
								dInventory.setGoodsCode(tdGoods.getCode());
								dInventory.setInventory(cInQtyTemp.longValue());
								dInventory.setCategoryId(tdGoods.getCategoryId());
								dInventory.setCategoryTitle(tdGoods.getCategoryTitle());
								dInventory.setCategoryIdTree(tdGoods.getCategoryIdTree());
							} else{
								return "<RESULTS><STATUS><CODE>1</CODE><MESSAGE>城市编码为：" + cCompanyId + "的城市不存在或SKU为" + cDGcode
										+ "的商品不存在</MESSAGE></STATUS></RESULTS>";
							}
						}
					}else{
						return "<RESULTS><STATUS><CODE>1</CODE><MESSAGE>商品编码cDGcode不能不为空！</MESSAGE></STATUS></RESULTS>";
					}
					tdDiySiteInventoryService.save(inventory);
					tdDiySiteInventoryLogService.saveChangeLog(inventory, -cPackQtyTemp, null, null,
							TdDiySiteInventoryLog.CHANGETYPE_TURN_ZERO);
					tdDiySiteInventoryService.save(dInventory);
					tdDiySiteInventoryLogService.saveChangeLog(dInventory, cInQtyTemp.longValue(), null, null,
							TdDiySiteInventoryLog.CHANGETYPE_TURN_ZERO);
				}else{
					return "<RESULTS><STATUS><CODE>1</CODE><MESSAGE>城市编码不能为空！</MESSAGE></STATUS></RESULTS>";
				}
			}
			return "<RESULTS><STATUS><CODE>0</CODE><MESSAGE></MESSAGE></STATUS></RESULTS>";
		}else if (STRTABLE.equalsIgnoreCase("tbw_back_rec_d"))// 城市采购退货明细
		{
			for (int i = 0; i < nodeList.getLength(); i++) {
				String cOwnerNo = null;// 委托业主
				String cRecNo = null;// 任务编号
				Long cRecId = null;// 任务id
				String cGcode = null;// 商品编号
				String cPackQty = null;// 包装数量 :long
				Double cPrice = null;// 价格 :long
				String cGiftQty = null;// 验收赠品数量 :long
				String cBadQty = null;// 验收不良品数量:long
				String cRecQty = null;// 验收数量:long
				String cRecUser = null;// 作业人员
				String cPlatNo = null;// 月台
				String cOpTools = null;// 操作工具(表单,pda,电子标签)
				String cOpStatus = null;// 状态（初始、作业中、完成、结案)
				String cReserved1 = null;// 预留1
				String cReserved2 = null;// 预留2
				String cReserved3 = null;// 预留3
				String cReserved4 = null;// 预留4
				String cReserved5 = null;// 预留5
				String cNote = null;// 备注
				String cMkUserno = null;// 建立人员
				String cMkDt = null;// 建立时间
				String cModifiedUserno = null;// 修改人员
				String cModifiedDt = null;// 修改时间
				// Long cCompanyId = null;//分公司Id

				Node node = nodeList.item(i);
				NodeList childNodeList = node.getChildNodes();
				// 遍历所有TABLE中的字段
				for (int idx = 0; idx < childNodeList.getLength(); idx++) {
					Node childNode = childNodeList.item(idx);
					if (childNode.getNodeName().equalsIgnoreCase("c_owner_no")) {
						if (null != childNode.getChildNodes().item(0)) {
							cOwnerNo = childNode.getChildNodes().item(0).getNodeValue();
						}
					} else if (childNode.getNodeName().equalsIgnoreCase("c_rec_no")) {
						if (null != childNode.getChildNodes().item(0)) {
							cRecNo = childNode.getChildNodes().item(0).getNodeValue();
						}
					} else if (childNode.getNodeName().equalsIgnoreCase("c_rec_id")) {
						if (null != childNode.getChildNodes().item(0)) {
							cRecId = Long.parseLong(childNode.getChildNodes().item(0).getNodeValue());
						}
					} else if (childNode.getNodeName().equalsIgnoreCase("c_gcode")) {
						if (null != childNode.getChildNodes().item(0)) {
							cGcode = childNode.getChildNodes().item(0).getNodeValue();
						}
					} else if (childNode.getNodeName().equalsIgnoreCase("c_pack_qty")) {
						if (null != childNode.getChildNodes().item(0)) {
							cPackQty = childNode.getChildNodes().item(0).getNodeValue();
						}
					} else if (childNode.getNodeName().equalsIgnoreCase("c_price")) {
						if (null != childNode.getChildNodes().item(0)) {
							cPrice = Double.parseDouble(childNode.getChildNodes().item(0).getNodeValue());
						}
					} else if (childNode.getNodeName().equalsIgnoreCase("c_gift_qty")) {
						if (null != childNode.getChildNodes().item(0)) {
							cGiftQty = childNode.getChildNodes().item(0).getNodeValue();
						}
					} else if (childNode.getNodeName().equalsIgnoreCase("c_bad_qty")) {
						if (null != childNode.getChildNodes().item(0)) {
							cBadQty = childNode.getChildNodes().item(0).getNodeValue();
						}
					} else if (childNode.getNodeName().equalsIgnoreCase("c_rec_qty")) {
						if (null != childNode.getChildNodes().item(0)) {
							cRecQty = childNode.getChildNodes().item(0).getNodeValue();
						}
					} else if (childNode.getNodeName().equalsIgnoreCase("c_rec_user")) {
						if (null != childNode.getChildNodes().item(0)) {
							cRecUser = childNode.getChildNodes().item(0).getNodeValue();
						}
					} else if (childNode.getNodeName().equalsIgnoreCase("c_plat_no")) {
						if (null != childNode.getChildNodes().item(0)) {
							cPlatNo = childNode.getChildNodes().item(0).getNodeValue();
						}
					} else if (childNode.getNodeName().equalsIgnoreCase("c_op_tools")) {
						if (null != childNode.getChildNodes().item(0)) {
							cOpTools = childNode.getChildNodes().item(0).getNodeValue();
						}
					} else if (childNode.getNodeName().equalsIgnoreCase("c_op_status")) {
						if (null != childNode.getChildNodes().item(0)) {
							cOpStatus = childNode.getChildNodes().item(0).getNodeValue();
						}
					} else if (childNode.getNodeName().equalsIgnoreCase("c_reserved1")) {
						if (null != childNode.getChildNodes().item(0)) {
							cReserved1 = childNode.getChildNodes().item(0).getNodeValue();
						}
					} else if (childNode.getNodeName().equalsIgnoreCase("c_reserved2")) {
						if (null != childNode.getChildNodes().item(0)) {
							cReserved2 = childNode.getChildNodes().item(0).getNodeValue();
						}
					} else if (childNode.getNodeName().equalsIgnoreCase("c_reserved3")) {
						if (null != childNode.getChildNodes().item(0)) {
							cReserved3 = childNode.getChildNodes().item(0).getNodeValue();
						}
					} else if (childNode.getNodeName().equalsIgnoreCase("c_reserved4")) {
						if (null != childNode.getChildNodes().item(0)) {
							cReserved4 = childNode.getChildNodes().item(0).getNodeValue();
						}
					} else if (childNode.getNodeName().equalsIgnoreCase("c_reserved5")) {
						if (null != childNode.getChildNodes().item(0)) {
							cReserved5 = childNode.getChildNodes().item(0).getNodeValue();
						}
					} else if (childNode.getNodeName().equalsIgnoreCase("c_note")) {
						if (null != childNode.getChildNodes().item(0)) {
							cNote = childNode.getChildNodes().item(0).getNodeValue();
						}
					} else if (childNode.getNodeName().equalsIgnoreCase("c_mk_userno")) {
						if (null != childNode.getChildNodes().item(0)) {
							cMkUserno = childNode.getChildNodes().item(0).getNodeValue();
						}
					} else if (childNode.getNodeName().equalsIgnoreCase("c_mk_dt")) {
						if (null != childNode.getChildNodes().item(0)) {
							cMkDt = childNode.getChildNodes().item(0).getNodeValue();
						}
					} else if (childNode.getNodeName().equalsIgnoreCase("c_modified_userno")) {
						if (null != childNode.getChildNodes().item(0)) {
							cModifiedUserno = childNode.getChildNodes().item(0).getNodeValue();
						}
					} else if (childNode.getNodeName().equalsIgnoreCase("c_modified_dt")) {
						if (null != childNode.getChildNodes().item(0)) {
							cModifiedDt = childNode.getChildNodes().item(0).getNodeValue();
						}
					}
				}
				TdTbwBackRecD tbwBackRecD = new TdTbwBackRecD();
				tbwBackRecD.setcOwnerNo(cOwnerNo);
				tbwBackRecD.setcOwnerNo(cOwnerNo);
				tbwBackRecD.setcRecNo(cRecNo);
				tbwBackRecD.setcRecId(cRecId);
				tbwBackRecD.setcGcode(cGcode);
				tbwBackRecD.setcPackQty(cPackQty);
				tbwBackRecD.setcPrice(cPrice);
				tbwBackRecD.setcGiftQty(cGiftQty);
				tbwBackRecD.setcBadQty(cBadQty);
				tbwBackRecD.setcRecQty(cRecQty);
				tbwBackRecD.setcRecUser(cRecUser);
				tbwBackRecD.setcPlatNo(cPlatNo);
				tbwBackRecD.setcOpTools(cOpTools);
				tbwBackRecD.setcOpStatus(cOpStatus);
				tbwBackRecD.setcReserved1(cReserved1);
				tbwBackRecD.setcReserved2(cReserved2);
				tbwBackRecD.setcReserved3(cReserved3);
				tbwBackRecD.setcReserved4(cReserved4);
				tbwBackRecD.setcReserved5(cReserved5);
				tbwBackRecD.setcNote(cNote);
				tbwBackRecD.setcMkUserno(cMkUserno);
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
				if (cMkDt != null) {
					try {
						Date c_MkDt = sdf.parse(cMkDt);
						tbwBackRecD.setcMkDt(c_MkDt);
					} catch (ParseException e) {
						e.printStackTrace();
					}
				}
				tbwBackRecD.setcModifiedUserno(cModifiedUserno);
				if (cModifiedDt != null) {
					try {
						Date c_ModifiedDt = sdf.parse(cModifiedDt);
						tbwBackRecD.setcModifiedDt(c_ModifiedDt);
					} catch (ParseException e) {
						e.printStackTrace();
					}
				}
				TdTbwBackRecDService.save(tbwBackRecD);
				TdGoods tdGoods = tdGoodsService.findByCodeAndStatus(cGcode, 1l);
				if (cRecQty == null) {
					return "<RESULTS><STATUS><CODE>1</CODE><MESSAGE>商品验收数量不能为空</MESSAGE></STATUS></RESULTS>";
				}
				if (tdGoods == null) {
					return "<RESULTS><STATUS><CODE>1</CODE><MESSAGE>商品编码为：" + cGcode
							+ "的商品不存在或者不可用</MESSAGE></STATUS></RESULTS>";
				}

			}
			return "<RESULTS><STATUS><CODE>0</CODE><MESSAGE></MESSAGE></STATUS></RESULTS>";
		} else if (STRTABLE.equalsIgnoreCase("tbw_back_rec_m"))// 城市采购退货主档
		{
			for (int i = 0; i < nodeList.getLength(); i++) {
				String cWhNo = null;// 仓库编号
				String cOwnerNo = null;// 委托业主
				String cRecNo = null;// 返配验收单号
				Long cPrintTimes = null;// 打印次数
				String cBackNo = null;// 返配单号
				String cBackType = null;// 返配单类型（一般返配
				String cBackClass = null;// 返配单分类(存储型、越库型)
				String cCustomerNo = null;// 客户信息
				String cPlatNo = null;// 月台
				String cRecUser = null;// 验收人员
				String cOpTools = null;// 操作工具(表单,pda,电子标签)
				String cOpStatus = null;// 操作状态(初始、作业中、完成、结案)
				String cBeginDt = null;// 开始操作时间
				String cEndDt = null;// 结束操作时间
				String cNote = null;// 备注
				String cMkUserno = null;// 建立人员
				String cMkDt = null;// 建立时间
				String cModifiedUserno = null;// 修改人员
				String cModifiedDt = null;// 修改时间
				String cPoNo = null;// 门店退单
				Long cCompanyId = null;// 分公司Id

				Node node = nodeList.item(i);
				NodeList childNodeList = node.getChildNodes();
				// 遍历所有TABLE中的字段
				for (int idx = 0; idx < childNodeList.getLength(); idx++) {
					Node childNode = childNodeList.item(idx);
					if (childNode.getNodeName().equalsIgnoreCase("c_wh_no")) {
						if (null != childNode.getChildNodes().item(0)) {
							cWhNo = childNode.getChildNodes().item(0).getNodeValue();
						}
					} else if (childNode.getNodeName().equalsIgnoreCase("c_owner_no")) {
						if (null != childNode.getChildNodes().item(0)) {
							cOwnerNo = childNode.getChildNodes().item(0).getNodeValue();
						}
					} else if (childNode.getNodeName().equalsIgnoreCase("c_rec_no")) {
						if (null != childNode.getChildNodes().item(0)) {
							cRecNo = childNode.getChildNodes().item(0).getNodeValue();
						}
					} else if (childNode.getNodeName().equalsIgnoreCase("c_print_times")) {
						if (null != childNode.getChildNodes().item(0)) {
							cPrintTimes = Long.parseLong(childNode.getChildNodes().item(0).getNodeValue());
						}
					} else if (childNode.getNodeName().equalsIgnoreCase("c_back_no")) {
						if (null != childNode.getChildNodes().item(0)) {
							cBackNo = childNode.getChildNodes().item(0).getNodeValue();
						}
					} else if (childNode.getNodeName().equalsIgnoreCase("c_back_type")) {
						if (null != childNode.getChildNodes().item(0)) {
							cBackType = childNode.getChildNodes().item(0).getNodeValue();
						}
					} else if (childNode.getNodeName().equalsIgnoreCase("c_back_class")) {
						if (null != childNode.getChildNodes().item(0)) {
							cBackClass = childNode.getChildNodes().item(0).getNodeValue();
						}
					} else if (childNode.getNodeName().equalsIgnoreCase("c_customer_no")) {
						if (null != childNode.getChildNodes().item(0)) {
							cCustomerNo = childNode.getChildNodes().item(0).getNodeValue();
						}
					} else if (childNode.getNodeName().equalsIgnoreCase("c_plat_no")) {
						if (null != childNode.getChildNodes().item(0)) {
							cPlatNo = childNode.getChildNodes().item(0).getNodeValue();
						}
					} else if (childNode.getNodeName().equalsIgnoreCase("c_rec_user")) {
						if (null != childNode.getChildNodes().item(0)) {
							cRecUser = childNode.getChildNodes().item(0).getNodeValue();
						}
					} else if (childNode.getNodeName().equalsIgnoreCase("c_op_tools")) {
						if (null != childNode.getChildNodes().item(0)) {
							cOpTools = childNode.getChildNodes().item(0).getNodeValue();
						}
					} else if (childNode.getNodeName().equalsIgnoreCase("c_op_status")) {
						if (null != childNode.getChildNodes().item(0)) {
							cOpStatus = childNode.getChildNodes().item(0).getNodeValue();
						}
					} else if (childNode.getNodeName().equalsIgnoreCase("c_begin_dt")) {
						if (null != childNode.getChildNodes().item(0)) {
							cBeginDt = childNode.getChildNodes().item(0).getNodeValue();
						}
					} else if (childNode.getNodeName().equalsIgnoreCase("c_end_dt")) {
						if (null != childNode.getChildNodes().item(0)) {
							cEndDt = childNode.getChildNodes().item(0).getNodeValue();
						}
					} else if (childNode.getNodeName().equalsIgnoreCase("c_note")) {
						if (null != childNode.getChildNodes().item(0)) {
							cNote = childNode.getChildNodes().item(0).getNodeValue();
						}
					} else if (childNode.getNodeName().equalsIgnoreCase("c_mk_userno")) {
						if (null != childNode.getChildNodes().item(0)) {
							cMkUserno = childNode.getChildNodes().item(0).getNodeValue();
						}
					} else if (childNode.getNodeName().equalsIgnoreCase("c_mk_dt")) {
						if (null != childNode.getChildNodes().item(0)) {
							cMkDt = childNode.getChildNodes().item(0).getNodeValue();
						}
					} else if (childNode.getNodeName().equalsIgnoreCase("c_modified_userno")) {
						if (null != childNode.getChildNodes().item(0)) {
							cModifiedUserno = childNode.getChildNodes().item(0).getNodeValue();
						}
					} else if (childNode.getNodeName().equalsIgnoreCase("c_modified_dt")) {
						if (null != childNode.getChildNodes().item(0)) {
							cModifiedDt = childNode.getChildNodes().item(0).getNodeValue();
						}
					} else if (childNode.getNodeName().equalsIgnoreCase("c_po_no")) {
						if (null != childNode.getChildNodes().item(0)) {
							cPoNo = childNode.getChildNodes().item(0).getNodeValue();
						}
					} else if (childNode.getNodeName().equalsIgnoreCase("c_company_id")) {
						if (null != childNode.getChildNodes().item(0)) {
							cCompanyId = Long.parseLong(childNode.getChildNodes().item(0).getNodeValue());
						}
					}
				}
				TdTbwBackRecM tbwBackRecM = tdTbwBackRecMService.findByCRecNo(cRecNo);
				if (tbwBackRecM == null) {
					tbwBackRecM = new TdTbwBackRecM();
				}
				tbwBackRecM.setcWhNo(cWhNo);
				tbwBackRecM.setcOwnerNo(cOwnerNo);
				tbwBackRecM.setcRecNo(cRecNo);
				tbwBackRecM.setcPrintTimes(cPrintTimes);
				tbwBackRecM.setcBackNo(cBackNo);
				tbwBackRecM.setcBackType(cBackType);
				tbwBackRecM.setcBackClass(cBackClass);
				tbwBackRecM.setcCustomerNo(cCustomerNo);
				tbwBackRecM.setcPlatNo(cPlatNo);
				tbwBackRecM.setcRecUser(cRecUser);
				tbwBackRecM.setcOpTools(cOpTools);
				tbwBackRecM.setcOpStatus(cOpStatus);
				tbwBackRecM.setcBeginDt(DateFromString(cBeginDt));
				tbwBackRecM.setcEndDt(DateFromString(cEndDt));
				tbwBackRecM.setcNote(cNote);
				tbwBackRecM.setcMkUserno(cMkUserno);
				tbwBackRecM.setcMkDt(DateFromString(cMkDt));
				tbwBackRecM.setcModifiedUserno(cModifiedUserno);
				tbwBackRecM.setcModifiedDt(DateFromString(cModifiedDt));
				tbwBackRecM.setcPoNo(cPoNo);
				tbwBackRecM.setcCompanyId(cCompanyId);
				tdTbwBackRecMService.save(tbwBackRecM);
				List<TdTbwBackRecD> tbwBackRecDs = TdTbwBackRecDService.findByCRecNo(cRecNo);
				for (TdTbwBackRecD tdTbwBackRecD : tbwBackRecDs) {
					String cGcode = tdTbwBackRecD.getcGcode();
					TdDiySiteInventory inventory = tdDiySiteInventoryService
							.findByGoodsCodeAndRegionIdAndDiySiteIdIsNull(cGcode, cCompanyId);
					if (inventory == null) {
						return "<RESULTS><STATUS><CODE>1</CODE><MESSAGE>城市编码为：" + cCompanyId
								+ "的城市不存在</MESSAGE></STATUS></RESULTS>";
					}

					Double doubleFromStr = Double.parseDouble(tdTbwBackRecD.getcRecQty());
					doubleFromStr = doubleFromStr * 100;
					Long cRecQty = doubleFromStr.longValue();
					cRecQty = cRecQty / 100;
					inventory.setInventory(inventory.getInventory() - cRecQty);
					tdDiySiteInventoryService.save(inventory);
					tdDiySiteInventoryLogService.saveChangeLog(inventory, -cRecQty, null, null,
							TdDiySiteInventoryLog.CHANGETYPE_CITY_SUB);
				}

			}
			return "<RESULTS><STATUS><CODE>0</CODE><MESSAGE></MESSAGE></STATUS></RESULTS>";
		} else if (STRTABLE.equalsIgnoreCase("tbw_om_d"))// 城市调拨明细
		{
			for (int i = 0; i < nodeList.getLength(); i++) {
				String cOwnerNo = null;// 委托业主
				String cOmNo = null;// 调拨单号
				Long cOmId = null;// 调拨ID
				String cOmType = null;// 调拨单类型(一般调拨)
				String cGcode = null;// 商品
				String cOwnerGcode = null;// 委托业主商品
				Long cPackQty = null;// 包装数量
				Double cQty = null;// 数量
				Double cWaveQty = null;// 定位量
				Double cAckQty = null;// 实际出货数量
				Double cCheckQty = null;// 实际收货数量
				Double cPrice = null;// 商品单价
				Double cTaxRate = null;// 税率
				String cOpStatus = null;// 作业状态(初始，定位，发单，完成)
				String cItemType = null;// 品质（良品、不良品、赠品）
				String cReserved1 = null;// 预留1
				String cReserved2 = null;// 预留2
				String cReserved3 = null;// 预留3
				String cReserved4 = null;// 预留4
				String cReserved5 = null;// 预留5
				String cNote = null;// 备注
				String cOutUserno = null;// 出货人员
				String cOutDt = null;// 出货时间
				String cCheckUserno = null;// 收货人员
				String cCheckDt = null;// 收货时间
				String cProduceDt = null;// 生产日期
				String cMkUserno = null;// 建立人员
				String cMkDt = null;// 建立时间
				String cModifiedUserno = null;// 修改人员
				String cModifiedDt = null;// 修改时间
				String cUploadStatus = null;// 上传状态
				Node node = nodeList.item(i);
				NodeList childNodeList = node.getChildNodes();
				// 遍历所有TABLE中的字段
				for (int idx = 0; idx < childNodeList.getLength(); idx++) {
					Node childNode = childNodeList.item(idx);
					if (childNode.getNodeName().equalsIgnoreCase("c_owner_no")) {
						if (null != childNode.getChildNodes().item(0)) {
							cOwnerNo = childNode.getChildNodes().item(0).getNodeValue();
						}
					} else if (childNode.getNodeName().equalsIgnoreCase("c_om_no")) {
						if (null != childNode.getChildNodes().item(0)) {
							cOmNo = childNode.getChildNodes().item(0).getNodeValue();
						}
					} else if (childNode.getNodeName().equalsIgnoreCase("c_om_id")) {
						if (null != childNode.getChildNodes().item(0)) {
							cOmId = Long.parseLong(childNode.getChildNodes().item(0).getNodeValue());
						}
					} else if (childNode.getNodeName().equalsIgnoreCase("c_om_type")) {
						if (null != childNode.getChildNodes().item(0)) {
							cOmType = childNode.getChildNodes().item(0).getNodeValue();
						}
					} else if (childNode.getNodeName().equalsIgnoreCase("c_gcode")) {
						if (null != childNode.getChildNodes().item(0)) {
							cGcode = childNode.getChildNodes().item(0).getNodeValue();
						}
					} else if (childNode.getNodeName().equalsIgnoreCase("c_owner_gcode")) {
						if (null != childNode.getChildNodes().item(0)) {
							cOwnerGcode = childNode.getChildNodes().item(0).getNodeValue();
						}
					} else if (childNode.getNodeName().equalsIgnoreCase("c_pack_qty")) {
						if (null != childNode.getChildNodes().item(0)) {
							cPackQty = Long.parseLong(childNode.getChildNodes().item(0).getNodeValue());
						}
					} else if (childNode.getNodeName().equalsIgnoreCase("c_qty")) {
						if (null != childNode.getChildNodes().item(0)) {
							cQty = Double.parseDouble(childNode.getChildNodes().item(0).getNodeValue());
						}
					} else if (childNode.getNodeName().equalsIgnoreCase("c_wave_qty")) {
						if (null != childNode.getChildNodes().item(0)) {
							cWaveQty = Double.parseDouble(childNode.getChildNodes().item(0).getNodeValue());
						}
					} else if (childNode.getNodeName().equalsIgnoreCase("c_ack_qty")) {
						if (null != childNode.getChildNodes().item(0)) {
							cAckQty = Double.parseDouble(childNode.getChildNodes().item(0).getNodeValue());
						}
					} else if (childNode.getNodeName().equalsIgnoreCase("c_check_qty")) {
						if (null != childNode.getChildNodes().item(0)) {
							cCheckQty = Double.parseDouble(childNode.getChildNodes().item(0).getNodeValue());
						}
					} else if (childNode.getNodeName().equalsIgnoreCase("c_price")) {
						if (null != childNode.getChildNodes().item(0)) {
							cPrice = Double.parseDouble(childNode.getChildNodes().item(0).getNodeValue());
						}
					} else if (childNode.getNodeName().equalsIgnoreCase("c_tax_rate")) {
						if (null != childNode.getChildNodes().item(0)) {
							cTaxRate = Double.parseDouble(childNode.getChildNodes().item(0).getNodeValue());
						}
					} else if (childNode.getNodeName().equalsIgnoreCase("c_op_status")) {
						if (null != childNode.getChildNodes().item(0)) {
							cOpStatus = childNode.getChildNodes().item(0).getNodeValue();
						}
					} else if (childNode.getNodeName().equalsIgnoreCase("c_item_type")) {
						if (null != childNode.getChildNodes().item(0)) {
							cItemType = childNode.getChildNodes().item(0).getNodeValue();
						}
					} else if (childNode.getNodeName().equalsIgnoreCase("c_reserved1")) {
						if (null != childNode.getChildNodes().item(0)) {
							cReserved1 = childNode.getChildNodes().item(0).getNodeValue();
						}
					} else if (childNode.getNodeName().equalsIgnoreCase("c_reserved2")) {
						if (null != childNode.getChildNodes().item(0)) {
							cReserved2 = childNode.getChildNodes().item(0).getNodeValue();
						}
					} else if (childNode.getNodeName().equalsIgnoreCase("c_reserved3")) {
						if (null != childNode.getChildNodes().item(0)) {
							cReserved3 = childNode.getChildNodes().item(0).getNodeValue();
						}
					} else if (childNode.getNodeName().equalsIgnoreCase("c_reserved4")) {
						if (null != childNode.getChildNodes().item(0)) {
							cReserved4 = childNode.getChildNodes().item(0).getNodeValue();
						}
					} else if (childNode.getNodeName().equalsIgnoreCase("c_reserved5")) {
						if (null != childNode.getChildNodes().item(0)) {
							cReserved5 = childNode.getChildNodes().item(0).getNodeValue();
						}
					} else if (childNode.getNodeName().equalsIgnoreCase("c_note")) {
						if (null != childNode.getChildNodes().item(0)) {
							cNote = childNode.getChildNodes().item(0).getNodeValue();
						}
					} else if (childNode.getNodeName().equalsIgnoreCase("c_out_userno")) {
						if (null != childNode.getChildNodes().item(0)) {
							cOutUserno = childNode.getChildNodes().item(0).getNodeValue();
						}
					} else if (childNode.getNodeName().equalsIgnoreCase("c_out_dt")) {
						if (null != childNode.getChildNodes().item(0)) {
							cOutDt = childNode.getChildNodes().item(0).getNodeValue();
						}
					} else if (childNode.getNodeName().equalsIgnoreCase("c_check_userno")) {
						if (null != childNode.getChildNodes().item(0)) {
							cCheckUserno = childNode.getChildNodes().item(0).getNodeValue();
						}
					} else if (childNode.getNodeName().equalsIgnoreCase("c_check_dt")) {
						if (null != childNode.getChildNodes().item(0)) {
							cCheckDt = childNode.getChildNodes().item(0).getNodeValue();
						}
					} else if (childNode.getNodeName().equalsIgnoreCase("c_produce_dt")) {
						if (null != childNode.getChildNodes().item(0)) {
							cProduceDt = childNode.getChildNodes().item(0).getNodeValue();
						}
					} else if (childNode.getNodeName().equalsIgnoreCase("c_mk_userno")) {
						if (null != childNode.getChildNodes().item(0)) {
							cMkUserno = childNode.getChildNodes().item(0).getNodeValue();
						}
					} else if (childNode.getNodeName().equalsIgnoreCase("c_mk_dt")) {
						if (null != childNode.getChildNodes().item(0)) {
							cMkDt = childNode.getChildNodes().item(0).getNodeValue();
						}
					} else if (childNode.getNodeName().equalsIgnoreCase("c_modified_userno")) {
						if (null != childNode.getChildNodes().item(0)) {
							cModifiedUserno = childNode.getChildNodes().item(0).getNodeValue();
						}
					} else if (childNode.getNodeName().equalsIgnoreCase("c_modified_dt")) {
						if (null != childNode.getChildNodes().item(0)) {
							cModifiedDt = childNode.getChildNodes().item(0).getNodeValue();
						}
					} else if (childNode.getNodeName().equalsIgnoreCase("c_upload_status")) {
						if (null != childNode.getChildNodes().item(0)) {
							cUploadStatus = childNode.getChildNodes().item(0).getNodeValue();
						}
					}
				}
				TdTbOmD tbOmD = new TdTbOmD();
				tbOmD.setcOwnerNo(cOwnerNo);
				tbOmD.setcOmNo(cOmNo);
				tbOmD.setcOmId(cOmId);
				tbOmD.setcOmType(cOmType);
				tbOmD.setcGcode(cGcode);
				tbOmD.setcOwnerGcode(cOwnerGcode);
				tbOmD.setcPackQty(cPackQty);
				tbOmD.setcQty(cQty);
				tbOmD.setcWaveQty(cWaveQty);
				tbOmD.setcAckQty(cAckQty);
				tbOmD.setcCheckQty(cCheckQty);
				tbOmD.setcPrice(cPrice);
				tbOmD.setcTaxRate(cTaxRate);
				tbOmD.setcOpStatus(cOpStatus);
				tbOmD.setcItemType(cItemType);
				tbOmD.setcReserved1(cReserved1);
				tbOmD.setcReserved2(cReserved2);
				tbOmD.setcReserved3(cReserved3);
				tbOmD.setcReserved4(cReserved4);
				tbOmD.setcReserved5(cReserved5);
				tbOmD.setcNote(cNote);
				tbOmD.setcOutUserno(cOutUserno);
				tbOmD.setcOutDt(DateFromString(cOutDt));
				tbOmD.setcCheckUserno(cCheckUserno);
				tbOmD.setcCheckDt(DateFromString(cCheckDt));
				tbOmD.setcProduceDt(DateFromString(cProduceDt));
				tbOmD.setcMkUserno(cMkUserno);
				tbOmD.setcMkDt(DateFromString(cMkDt));
				tbOmD.setcModifiedUserno(cModifiedUserno);
				tbOmD.setcModifiedDt(DateFromString(cModifiedDt));
				tbOmD.setcUploadStatus(cUploadStatus);
				TdGoods tdGoods = tdGoodsService.findByCodeAndStatus(cGcode, 1l);
				if (cWaveQty == null) {
					return "<RESULTS><STATUS><CODE>1</CODE><MESSAGE>商品验收数量不能为空</MESSAGE></STATUS></RESULTS>";
				}
				if (tdGoods == null) {
					return "<RESULTS><STATUS><CODE>1</CODE><MESSAGE>商品编码为：" + cGcode
							+ "的商品不存在或者不可用</MESSAGE></STATUS></RESULTS>";
				}
				try {
					tdTbOmDService.save(tbOmD);
				} catch (Exception e) {
					return "<RESULTS><STATUS><CODE>1</CODE><MESSAGE>" + e.getMessage()
							+ "</MESSAGE></STATUS></RESULTS>";
				}
			}

			return "<RESULTS><STATUS><CODE>0</CODE><MESSAGE></MESSAGE></STATUS></RESULTS>";
		} else if (STRTABLE.equalsIgnoreCase("tbw_om_m"))// 城市调拨主档
		{
			for (int i = 0; i < nodeList.getLength(); i++) {
				String cWhNo = null;// 仓库
				String cOwnerNo = null;// 委托业主
				String cOmNo = null;// 调拨单号
				String cOmType = null;// 调拨单类型(一般调拨)
				String cDWhNo = null;// 目的仓
				String cPoType = null;// 原单号类型(一般调拨)
				String cPoNo = null;// 原单号
				String cAddress = null;// 地址
				String cPostCode = null;// 邮编
				Double cTotalMoney = null;// 总金额
				Double cTotalTax = null;// 总税额
				String cUmoutDt = null;// 退货日期
				String cOpStatus = null;// 作业状态(初始，定位，发单，完成)
				String cCreateFlag = null;// 创建识标（WMS,ERP）
				String cNote = null;// 备注
				String cMkUserno = null;// 建立人
				String cMkDt = null;// 建立时间
				String cModifiedUserno = null;// 修改人
				String cModifiedDt = null;// 修改时间
				String cUploadStatus = null;// 上传状态
				String cUploadFilename = null;// 上传文档
				Long cCompanyId = null; // 城市id；

				Node node = nodeList.item(i);
				NodeList childNodeList = node.getChildNodes();
				// 遍历所有TABLE中的字段
				for (int idx = 0; idx < childNodeList.getLength(); idx++) {
					Node childNode = childNodeList.item(idx);
					if (childNode.getNodeName().equalsIgnoreCase("c_wh_no")) {
						if (null != childNode.getChildNodes().item(0)) {
							cWhNo = childNode.getChildNodes().item(0).getNodeValue();
						}
					} else if (childNode.getNodeName().equalsIgnoreCase("c_owner_no")) {
						if (null != childNode.getChildNodes().item(0)) {
							cOwnerNo = childNode.getChildNodes().item(0).getNodeValue();
						}
					} else if (childNode.getNodeName().equalsIgnoreCase("c_om_no")) {
						if (null != childNode.getChildNodes().item(0)) {
							cOmNo = childNode.getChildNodes().item(0).getNodeValue();
						}
					} else if (childNode.getNodeName().equalsIgnoreCase("c_om_type")) {
						if (null != childNode.getChildNodes().item(0)) {
							cOmType = childNode.getChildNodes().item(0).getNodeValue();
						}
					} else if (childNode.getNodeName().equalsIgnoreCase("c_d_wh_no")) {
						if (null != childNode.getChildNodes().item(0)) {
							cDWhNo = childNode.getChildNodes().item(0).getNodeValue();
						}
					} else if (childNode.getNodeName().equalsIgnoreCase("c_po_type")) {
						if (null != childNode.getChildNodes().item(0)) {
							cPoType = childNode.getChildNodes().item(0).getNodeValue();
						}
					} else if (childNode.getNodeName().equalsIgnoreCase("c_po_no")) {
						if (null != childNode.getChildNodes().item(0)) {
							cPoNo = childNode.getChildNodes().item(0).getNodeValue();
						}
					} else if (childNode.getNodeName().equalsIgnoreCase("c_address")) {
						if (null != childNode.getChildNodes().item(0)) {
							cAddress = childNode.getChildNodes().item(0).getNodeValue();
						}
					} else if (childNode.getNodeName().equalsIgnoreCase("c_post_code")) {
						if (null != childNode.getChildNodes().item(0)) {
							cPostCode = childNode.getChildNodes().item(0).getNodeValue();
						}
					} else if (childNode.getNodeName().equalsIgnoreCase("c_total_money")) {
						if (null != childNode.getChildNodes().item(0)) {
							cTotalMoney = Double.parseDouble(childNode.getChildNodes().item(0).getNodeValue());
						}
					} else if (childNode.getNodeName().equalsIgnoreCase("c_total_tax")) {
						if (null != childNode.getChildNodes().item(0)) {
							cTotalTax = Double.parseDouble(childNode.getChildNodes().item(0).getNodeValue());
						}
					} else if (childNode.getNodeName().equalsIgnoreCase("c_umout_dt")) {
						if (null != childNode.getChildNodes().item(0)) {
							cUmoutDt = childNode.getChildNodes().item(0).getNodeValue();
						}
					} else if (childNode.getNodeName().equalsIgnoreCase("c_op_status")) {
						if (null != childNode.getChildNodes().item(0)) {
							cOpStatus = childNode.getChildNodes().item(0).getNodeValue();
						}
					} else if (childNode.getNodeName().equalsIgnoreCase("c_create_flag")) {
						if (null != childNode.getChildNodes().item(0)) {
							cCreateFlag = childNode.getChildNodes().item(0).getNodeValue();
						}
					} else if (childNode.getNodeName().equalsIgnoreCase("c_note")) {
						if (null != childNode.getChildNodes().item(0)) {
							cNote = childNode.getChildNodes().item(0).getNodeValue();
						}
					} else if (childNode.getNodeName().equalsIgnoreCase("c_mk_userno")) {
						if (null != childNode.getChildNodes().item(0)) {
							cMkUserno = childNode.getChildNodes().item(0).getNodeValue();
						}
					} else if (childNode.getNodeName().equalsIgnoreCase("c_mk_dt")) {
						if (null != childNode.getChildNodes().item(0)) {
							cMkDt = childNode.getChildNodes().item(0).getNodeValue();
						}
					} else if (childNode.getNodeName().equalsIgnoreCase("c_modified_userno")) {
						if (null != childNode.getChildNodes().item(0)) {
							cModifiedUserno = childNode.getChildNodes().item(0).getNodeValue();
						}
					} else if (childNode.getNodeName().equalsIgnoreCase("c_modified_dt")) {
						if (null != childNode.getChildNodes().item(0)) {
							cModifiedDt = childNode.getChildNodes().item(0).getNodeValue();
						}
					} else if (childNode.getNodeName().equalsIgnoreCase("c_upload_status")) {
						if (null != childNode.getChildNodes().item(0)) {
							cUploadStatus = childNode.getChildNodes().item(0).getNodeValue();
						}
					} else if (childNode.getNodeName().equalsIgnoreCase("c_upload_filename")) {
						if (null != childNode.getChildNodes().item(0)) {
							cUploadFilename = childNode.getChildNodes().item(0).getNodeValue();
						}
					} else if (childNode.getNodeName().equalsIgnoreCase("c_company_id")) {
						if (null != childNode.getChildNodes().item(0)) {
							cCompanyId = Long.parseLong(childNode.getChildNodes().item(0).getNodeValue());
						}
					}
				}
				TdTbOmM tbOmM = tdTbOmMService.findByCOmNo(cOmNo);
				if (tbOmM == null) {
					tbOmM = new TdTbOmM();
				}
				tbOmM.setcWhNo(cWhNo);
				tbOmM.setcOwnerNo(cOwnerNo);
				tbOmM.setcOmNo(cOmNo);
				tbOmM.setcOmType(cOmType);
				tbOmM.setcDWhNo(cDWhNo);
				tbOmM.setcPoType(cPoType);
				tbOmM.setcPoNo(cPoNo);
				tbOmM.setcAddress(cAddress);
				tbOmM.setcPostCode(cPostCode);
				tbOmM.setcTotalMoney(cTotalMoney);
				tbOmM.setcTotalTax(cTotalTax);
				tbOmM.setcUmoutDt(DateFromString(cUmoutDt));
				tbOmM.setcOpStatus(cOpStatus);
				tbOmM.setcCreateFlag(cCreateFlag);
				tbOmM.setcNote(cNote);
				tbOmM.setcMkUserno(cMkUserno);
				tbOmM.setcMkDt(DateFromString(cMkDt));
				tbOmM.setcModifiedUserno(cModifiedUserno);
				tbOmM.setcModifiedDt(DateFromString(cModifiedDt));
				tbOmM.setcUploadStatus(cUploadStatus);
				tbOmM.setcUploadFilename(cUploadFilename);
				tbOmM.setcCompanyId(cCompanyId);
				tdTbOmMService.save(tbOmM);
				if (StringUtils.isBlank(cUploadStatus)
						|| (!cUploadStatus.equalsIgnoreCase("in") && !cUploadStatus.equalsIgnoreCase("out"))) {
					return "<RESULTS><STATUS><CODE>1</CODE><MESSAGE>c_upload_status：" + cUploadStatus
							+ " 为空或者不属于\"in\"且不属于\"out\"</MESSAGE></STATUS></RESULTS>";
				}
				List<TdTbOmD> tbOmDs = tdTbOmDService.findByCOmNo(cOmNo);
				for (TdTbOmD tdTbOmD : tbOmDs) {
					String cGcode = tdTbOmD.getcGcode();
					TdGoods tdGoods = tdGoodsService.findByCode(cGcode);
					if (tdGoods == null) {
						return "<RESULTS><STATUS><CODE>1</CODE><MESSAGE>商品编码为：" + cGcode
								+ "的商品不存在或者不可用</MESSAGE></STATUS></RESULTS>";
					}
					TdCity tdCity = tdCityService.findBySobIdCity(cCompanyId);
					if (tdCity == null) {
						return "<RESULTS><STATUS><CODE>1</CODE><MESSAGE>城市编码为：" + cCompanyId
								+ "的城市不存在</MESSAGE></STATUS></RESULTS>";
					}
					TdDiySiteInventory inventory = tdDiySiteInventoryService
							.findByGoodsCodeAndRegionIdAndDiySiteIdIsNull(cGcode, cCompanyId);
					if (inventory == null) {
						inventory = new TdDiySiteInventory();
						inventory.setGoodsCode(tdGoods.getCode());
						inventory.setGoodsId(tdGoods.getId());
						inventory.setCategoryId(tdGoods.getCategoryId());
						inventory.setCategoryIdTree(tdGoods.getCategoryIdTree());
						inventory.setCategoryTitle(tdGoods.getCategoryTitle());
						inventory.setGoodsTitle(tdGoods.getTitle());
						inventory.setRegionId(cCompanyId);
						inventory.setRegionName(tdCity.getCityName());
					}

					Double doubleFromStr = tdTbOmD.getcWaveQty();
					doubleFromStr = doubleFromStr * 100;
					Long cRecQty = doubleFromStr.longValue();
					cRecQty = cRecQty / 100;
					if (cUploadStatus.equalsIgnoreCase("in")) {
						inventory.setInventory(inventory.getInventory() + cRecQty);
						tdDiySiteInventoryService.save(inventory);
						tdDiySiteInventoryLogService.saveChangeLog(inventory, cRecQty, null, null,
								TdDiySiteInventoryLog.CHANGETYPE_CITY_YC_ADD);
					} else if (cUploadStatus.equalsIgnoreCase("out")) {
						inventory.setInventory(inventory.getInventory() - cRecQty);
						tdDiySiteInventoryService.save(inventory);
						tdDiySiteInventoryLogService.saveChangeLog(inventory, -cRecQty, null, null,
								TdDiySiteInventoryLog.CHANGETYPE_CITY_BS_SUB);
					}
				}
			}

			return "<RESULTS><STATUS><CODE>0</CODE><MESSAGE></MESSAGE></STATUS></RESULTS>";
		} else if (STRTABLE.equalsIgnoreCase("tbw_waste_view"))// 报损报溢
		{
			for (int i = 0; i < nodeList.getLength(); i++) {
				String cWhNo = null;// 单号
				String cOwnerNo = null;// 委托业主
				String cWasteNo = null;// 损溢单号
				Long cWasteId = null;// 损溢ID
				String cWasteType = null;// 损溢单类型(一般报损、一般报溢)
				String cGcode = null;// 商品
				String cOwnerGcode = null;// 委托业主商品
				String cLocationNo = null;// 储位编号
				Long cStockattrId = null;// 商品属性ID
				Long cPackQty = null;// 包装数量
				Double cQty = null;// 数量
				String cLotNo = null;// 批号
				String cSpec = null;// 规格
				String cProduceDt = null;// 产的日期
				String cExpireDt = null;// 到期日
				Double cWaveQty = null;// 定位量
				String cItemType = null;// 品质(良品、不良品、赠品)
				Double cAckQty = null;// 实际报损数量
				Double cPrice = null;// 商品单价
				Double cTaxRate = null;// 税率
				String cOpStatus = null;// 作业状态(初始，定位，发单，完成)
				String cReserved1 = null;// 预留1
				String cReserved2 = null;// 预留2
				String cReserved3 = null;// 预留3
				String cReserved4 = null;// 预留4
				String cReserved5 = null;// 预留5
				String cNote = null;// 备注
				String cMkUserno = null;// 建立人员
				String cMkDt = null;// 建立时间
				String cModifiedUserno = null;// 修改人员
				String cModifiedDt = null;// 修改时间
				String cUploadStatus = null;// 上传状态
				Long cCompanyId = null;// 公司id
				Node node = nodeList.item(i);
				NodeList childNodeList = node.getChildNodes();
				// 遍历所有TABLE中的字段
				for (int idx = 0; idx < childNodeList.getLength(); idx++) {
					Node childNode = childNodeList.item(idx);
					if (childNode.getNodeName().equalsIgnoreCase("c_wh_no")) {
						if (null != childNode.getChildNodes().item(0)) {
							cWhNo = childNode.getChildNodes().item(0).getNodeValue();
						}
					} else if (childNode.getNodeName().equalsIgnoreCase("c_owner_no")) {
						if (null != childNode.getChildNodes().item(0)) {
							cOwnerNo = childNode.getChildNodes().item(0).getNodeValue();
						}
					} else if (childNode.getNodeName().equalsIgnoreCase("c_waste_no")) {
						if (null != childNode.getChildNodes().item(0)) {
							cWasteNo = childNode.getChildNodes().item(0).getNodeValue();
						}
					} else if (childNode.getNodeName().equalsIgnoreCase("c_waste_id")) {
						if (null != childNode.getChildNodes().item(0)) {
							cWasteId = Long.parseLong(childNode.getChildNodes().item(0).getNodeValue());
						}
					} else if (childNode.getNodeName().equalsIgnoreCase("c_waste_type")) {
						if (null != childNode.getChildNodes().item(0)) {
							cWasteType = childNode.getChildNodes().item(0).getNodeValue();
						}
					} else if (childNode.getNodeName().equalsIgnoreCase("c_gcode")) {
						if (null != childNode.getChildNodes().item(0)) {
							cGcode = childNode.getChildNodes().item(0).getNodeValue();
						}
					} else if (childNode.getNodeName().equalsIgnoreCase("c_owner_gcode")) {
						if (null != childNode.getChildNodes().item(0)) {
							cOwnerGcode = childNode.getChildNodes().item(0).getNodeValue();
						}
					} else if (childNode.getNodeName().equalsIgnoreCase("c_location_no")) {
						if (null != childNode.getChildNodes().item(0)) {
							cLocationNo = childNode.getChildNodes().item(0).getNodeValue();
						}
					} else if (childNode.getNodeName().equalsIgnoreCase("c_stockattr_id")) {
						if (null != childNode.getChildNodes().item(0)) {
							cStockattrId = Long.parseLong(childNode.getChildNodes().item(0).getNodeValue());
						}
					} else if (childNode.getNodeName().equalsIgnoreCase("c_pack_qty")) {
						if (null != childNode.getChildNodes().item(0)) {
							cPackQty = Long.parseLong(childNode.getChildNodes().item(0).getNodeValue());
						}
					} else if (childNode.getNodeName().equalsIgnoreCase("c_qty")) {
						if (null != childNode.getChildNodes().item(0)) {
							cQty = Double.parseDouble(childNode.getChildNodes().item(0).getNodeValue());
						}
					} else if (childNode.getNodeName().equalsIgnoreCase("c_lot_no")) {
						if (null != childNode.getChildNodes().item(0)) {
							cLotNo = childNode.getChildNodes().item(0).getNodeValue();
						}
					} else if (childNode.getNodeName().equalsIgnoreCase("c_spec")) {
						if (null != childNode.getChildNodes().item(0)) {
							cSpec = childNode.getChildNodes().item(0).getNodeValue();
						}
					} else if (childNode.getNodeName().equalsIgnoreCase("c_produce_dt")) {
						if (null != childNode.getChildNodes().item(0)) {
							cProduceDt = childNode.getChildNodes().item(0).getNodeValue();
						}
					} else if (childNode.getNodeName().equalsIgnoreCase("c_expire_dt")) {
						if (null != childNode.getChildNodes().item(0)) {
							cExpireDt = childNode.getChildNodes().item(0).getNodeValue();
						}
					} else if (childNode.getNodeName().equalsIgnoreCase("c_wave_qty")) {
						if (null != childNode.getChildNodes().item(0)) {
							cWaveQty = Double.parseDouble(childNode.getChildNodes().item(0).getNodeValue());
						}
					} else if (childNode.getNodeName().equalsIgnoreCase("c_item_type")) {
						if (null != childNode.getChildNodes().item(0)) {
							cItemType = childNode.getChildNodes().item(0).getNodeValue();
						}
					} else if (childNode.getNodeName().equalsIgnoreCase("c_ack_qty")) {
						if (null != childNode.getChildNodes().item(0)) {
							cAckQty = Double.parseDouble(childNode.getChildNodes().item(0).getNodeValue());
						}
					} else if (childNode.getNodeName().equalsIgnoreCase("c_price")) {
						if (null != childNode.getChildNodes().item(0)) {
							cPrice = Double.parseDouble(childNode.getChildNodes().item(0).getNodeValue());
						}
					} else if (childNode.getNodeName().equalsIgnoreCase("c_tax_rate")) {
						if (null != childNode.getChildNodes().item(0)) {
							cTaxRate = Double.parseDouble(childNode.getChildNodes().item(0).getNodeValue());
						}
					} else if (childNode.getNodeName().equalsIgnoreCase("c_op_status")) {
						if (null != childNode.getChildNodes().item(0)) {
							cOpStatus = childNode.getChildNodes().item(0).getNodeValue();
						}
					} else if (childNode.getNodeName().equalsIgnoreCase("c_reserved1")) {
						if (null != childNode.getChildNodes().item(0)) {
							cReserved1 = childNode.getChildNodes().item(0).getNodeValue();
						}
					} else if (childNode.getNodeName().equalsIgnoreCase("c_reserved2")) {
						if (null != childNode.getChildNodes().item(0)) {
							cReserved2 = childNode.getChildNodes().item(0).getNodeValue();
						}
					} else if (childNode.getNodeName().equalsIgnoreCase("c_reserved3")) {
						if (null != childNode.getChildNodes().item(0)) {
							cReserved3 = childNode.getChildNodes().item(0).getNodeValue();
						}
					} else if (childNode.getNodeName().equalsIgnoreCase("c_reserved4")) {
						if (null != childNode.getChildNodes().item(0)) {
							cReserved4 = childNode.getChildNodes().item(0).getNodeValue();
						}
					} else if (childNode.getNodeName().equalsIgnoreCase("c_reserved5")) {
						if (null != childNode.getChildNodes().item(0)) {
							cReserved5 = childNode.getChildNodes().item(0).getNodeValue();
						}
					} else if (childNode.getNodeName().equalsIgnoreCase("c_note")) {
						if (null != childNode.getChildNodes().item(0)) {
							cNote = childNode.getChildNodes().item(0).getNodeValue();
						}
					} else if (childNode.getNodeName().equalsIgnoreCase("c_mk_userno")) {
						if (null != childNode.getChildNodes().item(0)) {
							cMkUserno = childNode.getChildNodes().item(0).getNodeValue();
						}
					} else if (childNode.getNodeName().equalsIgnoreCase("c_mk_dt")) {
						if (null != childNode.getChildNodes().item(0)) {
							cMkDt = childNode.getChildNodes().item(0).getNodeValue();
						}
					} else if (childNode.getNodeName().equalsIgnoreCase("c_modified_userno")) {
						if (null != childNode.getChildNodes().item(0)) {
							cModifiedUserno = childNode.getChildNodes().item(0).getNodeValue();
						}
					} else if (childNode.getNodeName().equalsIgnoreCase("c_modified_dt")) {
						if (null != childNode.getChildNodes().item(0)) {
							cModifiedDt = childNode.getChildNodes().item(0).getNodeValue();
						}
					} else if (childNode.getNodeName().equalsIgnoreCase("c_upload_status")) {
						if (null != childNode.getChildNodes().item(0)) {
							cUploadStatus = childNode.getChildNodes().item(0).getNodeValue();
						}
					} else if (childNode.getNodeName().equalsIgnoreCase("C_COMPANY_ID")) {
						if (null != childNode.getChildNodes().item(0)) {
							cCompanyId = Long.parseLong(childNode.getChildNodes().item(0).getNodeValue());
						}
					}
				}
				if (cWasteNo == null) {
					return "<RESULTS><STATUS><CODE>1</CODE><MESSAGE>损溢单号不能为空</MESSAGE></STATUS></RESULTS>";
				}
				if (cWasteId == null) {
					return "<RESULTS><STATUS><CODE>1</CODE><MESSAGE>损溢单号id不能为空</MESSAGE></STATUS></RESULTS>";
				}

				TdTbwWasted tbwWasted = tdTbwWastedService.findByCWasteNoAndCWasteId(cWasteNo, cWasteId);
				if (tbwWasted != null) {
					return "<RESULTS><STATUS><CODE>1</CODE><MESSAGE>损溢单号：" + cWasteNo
							+ " 已存在</MESSAGE></STATUS></RESULTS>";
				}
				tbwWasted = new TdTbwWasted();
				tbwWasted.setcWhNo(cWhNo);
				tbwWasted.setcOwnerNo(cOwnerNo);
				tbwWasted.setcWasteNo(cWasteNo);
				tbwWasted.setcWasteId(cWasteId);
				tbwWasted.setcWasteType(cWasteType);
				tbwWasted.setcGcode(cGcode);
				tbwWasted.setcOwnerGcode(cOwnerGcode);
				tbwWasted.setcLocationNo(cLocationNo);
				tbwWasted.setcStockattrId(cStockattrId);
				tbwWasted.setcPackQty(cPackQty);
				tbwWasted.setcQty(cQty);
				tbwWasted.setcLotNo(cLotNo);
				tbwWasted.setcSpec(cSpec);
				tbwWasted.setcProduceDt(DateFromString(cProduceDt));
				tbwWasted.setcExpireDt(DateFromString(cExpireDt));
				tbwWasted.setcWaveQty(cWaveQty);
				tbwWasted.setcItemType(cItemType);
				tbwWasted.setcAckQty(cAckQty);
				tbwWasted.setcPrice(cPrice);
				tbwWasted.setcTaxRate(cTaxRate);
				tbwWasted.setcOpStatus(cOpStatus);
				tbwWasted.setcReserved1(cReserved1);
				tbwWasted.setcReserved2(cReserved2);
				tbwWasted.setcReserved3(cReserved3);
				tbwWasted.setcReserved4(cReserved4);
				tbwWasted.setcReserved5(cReserved5);
				tbwWasted.setcNote(cNote);
				tbwWasted.setcMkUserno(cMkUserno);
				tbwWasted.setcMkDt(DateFromString(cMkDt));
				tbwWasted.setcModifiedUserno(cModifiedUserno);
				tbwWasted.setcModifiedDt(DateFromString(cModifiedDt));
				tbwWasted.setcUploadStatus(cUploadStatus);
				tbwWasted.setcCompanyId(cCompanyId);
				tdTbwWastedService.save(tbwWasted);
				TdGoods tdGoods = tdGoodsService.findByCode(cGcode);
				if (tdGoods == null) {
					return "<RESULTS><STATUS><CODE>1</CODE><MESSAGE>商品编码为：" + cGcode
							+ "的商品不存在或者不可用</MESSAGE></STATUS></RESULTS>";
				}
				TdCity tdCity = tdCityService.findBySobIdCity(cCompanyId);
				if (tdCity == null) {
					return "<RESULTS><STATUS><CODE>1</CODE><MESSAGE>城市编码为：" + cCompanyId
							+ "的城市不存在</MESSAGE></STATUS></RESULTS>";
				}
				TdDiySiteInventory inventory = tdDiySiteInventoryService
						.findByGoodsCodeAndRegionIdAndDiySiteIdIsNull(cGcode, cCompanyId);
				if (inventory == null) {
					inventory = new TdDiySiteInventory();
					inventory.setGoodsCode(tdGoods.getCode());
					inventory.setGoodsId(tdGoods.getId());
					inventory.setCategoryId(tdGoods.getCategoryId());
					inventory.setCategoryIdTree(tdGoods.getCategoryIdTree());
					inventory.setCategoryTitle(tdGoods.getCategoryTitle());
					inventory.setGoodsTitle(tdGoods.getTitle());
					inventory.setRegionId(cCompanyId);
					inventory.setRegionName(tdCity.getCityName());
				}
				Double doubleFromStr = tbwWasted.getcQty();
				doubleFromStr = doubleFromStr * 100;
				Long cRecQty = doubleFromStr.longValue();
				cRecQty = cRecQty / 100;
				if (cWasteType.contains("报溢")) {
					inventory.setInventory(inventory.getInventory() + cRecQty);
					tdDiySiteInventoryService.save(inventory);
					tdDiySiteInventoryLogService.saveChangeLog(inventory, cRecQty, cWasteNo, null,
							TdDiySiteInventoryLog.CHANGETYPE_CITY_YC_ADD);
				} else {
					inventory.setInventory(inventory.getInventory() - cRecQty);
					tdDiySiteInventoryService.save(inventory);
					tdDiySiteInventoryLogService.saveChangeLog(inventory, -cRecQty, cWasteNo, null,
							TdDiySiteInventoryLog.CHANGETYPE_CITY_BS_SUB);
				}
			}

			return "<RESULTS><STATUS><CODE>0</CODE><MESSAGE></MESSAGE></STATUS></RESULTS>";
		}
		return "<RESULTS><STATUS><CODE>1</CODE><MESSAGE>不支持该表数据传输：" + STRTABLE + "</MESSAGE></STATUS></RESULTS>";
	}

	private Date DateFromString(String dateString) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		if (dateString != null) {
			try {
				Date date = sdf.parse(dateString);
				return date;
			} catch (ParseException e) {
				e.printStackTrace();
				return new Date(0);
			}
		}
		return new Date(0);
	}
	// private TdRequisitionGoods
	// saveRequisitionGoodsFromOrderGoods(TdOrderGoods orderGoods)
	// {
	// TdRequisitionGoods requisitionGoods = new TdRequisitionGoods();
	// requisitionGoods.setGoodsCode(orderGoods.getSku());
	// requisitionGoods.setGoodsTitle(orderGoods.getGoodsTitle());
	// requisitionGoods.setPrice(orderGoods.getPrice());
	// requisitionGoods.setQuantity(orderGoods.getQuantity());
	// requisitionGoods.setSubOrderNumber(tdOrder.getOrderNumber());
	// requisitionGoods.setOrderNumber(mainOrderNumber);
	// tdRequisitionGoodsService.save(requisitionGoods);
	// }

}
// END SNIPPET: service
