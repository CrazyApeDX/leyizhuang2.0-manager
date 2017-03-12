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

import com.ynyes.fitment.foundation.entity.FitCompany;
import com.ynyes.fitment.foundation.entity.FitPriceHeader;
import com.ynyes.fitment.foundation.entity.FitPriceLine;
import com.ynyes.fitment.foundation.service.FitCompanyService;
import com.ynyes.fitment.foundation.service.FitPriceHeaderService;
import com.ynyes.fitment.foundation.service.FitPriceLineService;
import com.ynyes.lyz.entity.TdBrand;
import com.ynyes.lyz.entity.TdDiySite;
import com.ynyes.lyz.entity.TdDiySiteInventory;
import com.ynyes.lyz.entity.TdGoods;
import com.ynyes.lyz.entity.TdGoodsLimit;
import com.ynyes.lyz.entity.TdLyzParameter;
import com.ynyes.lyz.entity.TdPriceList;
import com.ynyes.lyz.entity.TdPriceListItem;
import com.ynyes.lyz.entity.TdProductCategory;
import com.ynyes.lyz.interfaces.entity.TdDiySiteInventoryEbs;
import com.ynyes.lyz.interfaces.service.TdDiySiteInventoryEbsService;
import com.ynyes.lyz.service.TdBrandService;
import com.ynyes.lyz.service.TdDiySiteInventoryLogService;
import com.ynyes.lyz.service.TdDiySiteInventoryService;
import com.ynyes.lyz.service.TdDiySiteService;
import com.ynyes.lyz.service.TdGoodsLimitService;
import com.ynyes.lyz.service.TdGoodsService;
import com.ynyes.lyz.service.TdLyzParameterService;
import com.ynyes.lyz.service.TdPriceListItemService;
import com.ynyes.lyz.service.TdPriceListService;
import com.ynyes.lyz.service.TdProductCategoryService;
import com.ynyes.lyz.webservice.ICallEBS;

@WebService
public class CallEBSImpl implements ICallEBS {

	@Autowired
	private TdDiySiteService tdDiySiteService;

	@Autowired
	private FitCompanyService fitCompanyService;

	@Autowired
	private TdPriceListService tdPriceListService;

	@Autowired
	private FitPriceHeaderService fitPriceHeaderService;

	@Autowired
	private TdPriceListItemService tdPriceListItemService;

	@Autowired
	private FitPriceLineService fitPriceLineService;

	@Autowired
	private TdGoodsService tdGoodsService;

	@Autowired
	private TdLyzParameterService tdLyzParameterService;

	@Autowired
	private TdGoodsLimitService tdGoodsLimitService;

	@Autowired
	private TdBrandService tdBrandService;

	@Autowired
	private TdProductCategoryService tdProductCategoryService;

	@Autowired
	private TdDiySiteInventoryEbsService tdDiySiteInventoryEbsService;

	@Autowired
	private TdDiySiteInventoryService tdDiySiteInventoryService;

	@Autowired
	private TdDiySiteInventoryLogService tdDiySiteInventoryLogService;

	public String GetErpInfo(String STRTABLE, String STRTYPE, String XML) {
		System.out.println("getErpInfo called：" + XML);

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

		if (STRTABLE.equalsIgnoreCase("CUXAPP_AR_CUST_STORES_OUT")) // 门店表
		{
			for (int i = 0; i < nodeList.getLength(); i++) {
				Long sob_id = null;// 分公司ID
				Long customer_id = null;// 客户id（分公司下唯一）
				String customer_number = null;// 客户编码（分公司下唯一）
				String customer_name = null;// 客户名称
				String store_code = null;// 门店编码（会员信息绑定在门店编码上）
				String cust_type_code = null;// 类型CODE（JX,ZY）
				String cust_type_name = null;// 类型名称（经销商，直营）
				String address = null;// 地址
				String dept_code = null;// 区域编码
				String dept_desc = null;// 区域描述
				String enabled_flag = null;// 是否生效
				String attribute1 = null;// 是否是装饰公司

				Node node = nodeList.item(i);
				NodeList childNodeList = node.getChildNodes();

				for (int idx = 0; idx < childNodeList.getLength(); idx++) {
					Node childNode = childNodeList.item(idx);

					if (childNode.getNodeType() == Node.ELEMENT_NODE) {
						// 比较字段名
						if (childNode.getNodeName().equalsIgnoreCase("SOB_ID")) {
							// 有值
							if (null != childNode.getChildNodes().item(0)) {
								sob_id = Long.parseLong(childNode.getChildNodes().item(0).getNodeValue());
							}
							// 空值
							else {
								sob_id = null;
							}
						} else if (childNode.getNodeName().equalsIgnoreCase("CUSTOMER_ID")) {
							if (null != childNode.getChildNodes().item(0)) {
								customer_id = Long.parseLong(childNode.getChildNodes().item(0).getNodeValue());
							}
						} else if (childNode.getNodeName().equalsIgnoreCase("CUSTOMER_NUMBER")) {
							if (null != childNode.getChildNodes().item(0)) {
								customer_number = childNode.getChildNodes().item(0).getNodeValue();
							}
						} else if (childNode.getNodeName().equalsIgnoreCase("CUSTOMER_NAME")) {
							if (null != childNode.getChildNodes().item(0)) {
								customer_name = childNode.getChildNodes().item(0).getNodeValue();
							}
						} else if (childNode.getNodeName().equalsIgnoreCase("STORE_CODE")) {
							if (null != childNode.getChildNodes().item(0)) {
								store_code = childNode.getChildNodes().item(0).getNodeValue();
							}
						} else if (childNode.getNodeName().equalsIgnoreCase("CUST_TYPE_CODE")) {
							if (null != childNode.getChildNodes().item(0)) {
								cust_type_code = childNode.getChildNodes().item(0).getNodeValue();
							}
						} else if (childNode.getNodeName().equalsIgnoreCase("CUST_TYPE_NAME")) {
							if (null != childNode.getChildNodes().item(0)) {
								cust_type_name = childNode.getChildNodes().item(0).getNodeValue();
							}
						} else if (childNode.getNodeName().equalsIgnoreCase("ADDRESS")) {
							if (null != childNode.getChildNodes().item(0)) {
								address = childNode.getChildNodes().item(0).getNodeValue();
							}
						} else if (childNode.getNodeName().equalsIgnoreCase("DEPT_CODE")) {
							if (null != childNode.getChildNodes().item(0)) {
								dept_code = childNode.getChildNodes().item(0).getNodeValue();
							}
						} else if (childNode.getNodeName().equalsIgnoreCase("DEPT_DESC")) {
							if (null != childNode.getChildNodes().item(0)) {
								dept_desc = childNode.getChildNodes().item(0).getNodeValue();
							}
						} else if (childNode.getNodeName().equalsIgnoreCase("ENABLED_FLAG")) {
							if (null != childNode.getChildNodes().item(0)) {
								enabled_flag = childNode.getChildNodes().item(0).getNodeValue();
							}
						} else if (childNode.getNodeName().equalsIgnoreCase("ATTRIBUTE1")) {
							if (null != childNode.getChildNodes().item(0)) {
								attribute1 = childNode.getChildNodes().item(0).getNodeValue();
							}
						}
					}
				}

				// 保存 修改
				// TdDiySite diySite =
				// tdDiySiteService.findByCustomerIdAndregionId(customer_id,
				// sob_id);

				if (null == attribute1 || attribute1.equals("N")) {

					TdDiySite diySite = tdDiySiteService.findByRegionIdAndCustomerId(sob_id, customer_id);

					if (diySite == null) {
						diySite = new TdDiySite();
					}

					diySite.setRegionId(sob_id);
					diySite.setCustomerId(customer_id);

					if (cust_type_code.equalsIgnoreCase("JX")) {
						diySite.setIsDirect(false);
					} else {
						diySite.setIsDirect(true);
					}

					if (enabled_flag.equalsIgnoreCase("Y")) {
						diySite.setIsEnable(true);
					} else {
						diySite.setIsEnable(false);
					}
					diySite.setTitle(customer_name);
					diySite.setAddress(address);
					diySite.setCustomerNumber(customer_number);
					diySite.setCustTypeName(cust_type_name);
					diySite.setStoreCode(store_code);
					diySite.setDeptCode(dept_code);
					diySite.setDeptDesc(dept_desc);
					tdDiySiteService.save(diySite);
				} else {
					FitCompany company = fitCompanyService.findByCode(store_code);
					company = null == company ? new FitCompany() : company;
					company.setName(customer_name);
					company.setCode(store_code);
					company.setSobId(sob_id);
					if (enabled_flag.equalsIgnoreCase("Y")) {
						company.setFrozen(false);
					} else {
						company.setFrozen(true);
					}
					try {
						fitCompanyService.save(company);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
			return "<RESULTS><STATUS><CODE>0</CODE><MESSAGE></MESSAGE></STATUS></RESULTS>";
		} else if (STRTABLE.equalsIgnoreCase("CUXAPP_OM_PRICE_LIST_H_OUT"))// TdPriceList
		{
			// 遍历所有TABLE结构
			for (int i = 0; i < nodeList.getLength(); i++) {
				Long list_header_id = null;// 价目表ID
				Long sob_id = null;// 分公司ID
				String name = null;// 价目表名称
				String active_flag = null;// 有效标识 Y有效， N无效
				String description = null;// 描述
				String start_date_active = null;// 生效日期
				String end_date_active = null;// 失效日期
				String price_type = null;// 价目表类型
				String price_type_DESC = null;// 价目表类型描述

				Node node = nodeList.item(i);
				NodeList childNodeList = node.getChildNodes();

				// 遍历所有TABLE中的字段
				for (int idx = 0; idx < childNodeList.getLength(); idx++) {
					Node childNode = childNodeList.item(idx);

					if (childNode.getNodeType() == Node.ELEMENT_NODE) {
						// 比较字段名
						if (childNode.getNodeName().equalsIgnoreCase("list_header_id")) {
							// 有值
							if (null != childNode.getChildNodes().item(0)) {
								list_header_id = Long.parseLong(childNode.getChildNodes().item(0).getNodeValue());
							}
						} else if (childNode.getNodeName().equalsIgnoreCase("sob_id")) {
							if (null != childNode.getChildNodes().item(0)) {
								sob_id = Long.parseLong(childNode.getChildNodes().item(0).getNodeValue());
							}
						} else if (childNode.getNodeName().equalsIgnoreCase("name")) {
							if (null != childNode.getChildNodes().item(0)) {
								name = childNode.getChildNodes().item(0).getNodeValue();
							}
						} else if (childNode.getNodeName().equalsIgnoreCase("active_flag")) {
							if (null != childNode.getChildNodes().item(0)) {
								active_flag = childNode.getChildNodes().item(0).getNodeValue();
							}
						} else if (childNode.getNodeName().equalsIgnoreCase("description")) {
							if (null != childNode.getChildNodes().item(0)) {
								description = childNode.getChildNodes().item(0).getNodeValue();
							}
						} else if (childNode.getNodeName().equalsIgnoreCase("start_date_active")) {
							if (null != childNode.getChildNodes().item(0)) {
								start_date_active = childNode.getChildNodes().item(0).getNodeValue();
							}
						} else if (childNode.getNodeName().equalsIgnoreCase("end_date_active")) {
							if (null != childNode.getChildNodes().item(0)) {
								end_date_active = childNode.getChildNodes().item(0).getNodeValue();
							}
						} else if (childNode.getNodeName().equalsIgnoreCase("price_type")) {
							if (null != childNode.getChildNodes().item(0)) {
								price_type = childNode.getChildNodes().item(0).getNodeValue();
							}
						} else if (childNode.getNodeName().equalsIgnoreCase("price_type_DESC")) {
							if (null != childNode.getChildNodes().item(0)) {
								price_type_DESC = childNode.getChildNodes().item(0).getNodeValue();
							}
						}
					}
				}
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				if (price_type.contains("CB_")) {
					FitPriceHeader header = fitPriceHeaderService.findByEbsId(list_header_id);
					if (null == header) {
						header = new FitPriceHeader();
						header.setEbsId(list_header_id);
					}
					header.setSobId(sob_id);
					header.setTitle(name);
					if (start_date_active != null) {
						try {
							Date startdate = sdf.parse(start_date_active);
							header.setStartTime(startdate);
						} catch (ParseException e) {
							e.printStackTrace();
						}
					}
					if (StringUtils.isNotBlank(end_date_active)) {
						try {
							Date enddate = sdf.parse(end_date_active);
							header.setEndTime(enddate);
						} catch (ParseException e) {
							e.printStackTrace();
						}
					} else {
						header.setEndTime(null);
					}
					header.setIsEnable("Y".equalsIgnoreCase(active_flag) ? true : false);
					header.setEbsNumber(description);
					header.setPriceType(price_type.replace("CB_", ""));
					fitPriceHeaderService.save(header);
				} else {
					TdPriceList tdPriceList = tdPriceListService.findByListHeaderId(list_header_id);
					if (tdPriceList == null) {
						tdPriceList = new TdPriceList();
						tdPriceList.setListHeaderId(list_header_id);
					}
					tdPriceList.setCityId(sob_id);
					tdPriceList.setName(name);
					tdPriceList.setActiveFlag(active_flag);
					tdPriceList.setDescription(description);

					if (start_date_active != null) {
						try {
							Date startdate = sdf.parse(start_date_active);
							tdPriceList.setStartDateActive(startdate);
						} catch (ParseException e) {
							e.printStackTrace();
						}
					}
					if (StringUtils.isNotBlank(end_date_active)) {
						try {
							Date enddate = sdf.parse(end_date_active);
							tdPriceList.setEndDateActive(enddate);
						} catch (ParseException e) {
							e.printStackTrace();
						}
					} else {
						tdPriceList.setEndDateActive(null);
					}
					tdPriceList.setPriceType(price_type);
					tdPriceList.setPriceTypeDesc(price_type_DESC);
					tdPriceListService.save(tdPriceList);
				}
			}

			return "<RESULTS><STATUS><CODE>0</CODE><MESSAGE></MESSAGE></STATUS></RESULTS>";
		} else if (STRTABLE.equalsIgnoreCase("CUXAPP_OM_PRICE_LIST_L_OUT")) // TdPriceListItem
		{
			for (int i = 0; i < nodeList.getLength(); i++) {
				Long list_header_id = null;// 价目表头ID
				Long list_line_id = null;// 价目表行ID
				Long inventory_item_id = null;// 产品ID
				String description = null;// 描述
				String item_num = null;// 产品编号
				String item_desc = null;// 物料描述
				String product_uom_code = null;// 物料单位
				Double price = null;// 价格-零售价
				String start_date_active = null;// 生效日期
				String end_date_active = null;// 失效日期
				Double attribute1 = null; // 价格-会员价
				// add for 产品券价目表
				Double attribute2 = null; // 产品券会员价
				Double attribute3 = null; // 产品券零售价

				Node node = nodeList.item(i);
				NodeList childNodeList = node.getChildNodes();
				for (int idx = 0; idx < childNodeList.getLength(); idx++) {
					Node childNode = childNodeList.item(idx);

					if (childNode.getNodeType() == Node.ELEMENT_NODE) {
						// 比较字段名
						if (childNode.getNodeName().equalsIgnoreCase("list_header_id")) {
							// 有值
							if (null != childNode.getChildNodes().item(0)) {
								list_header_id = Long.parseLong(childNode.getChildNodes().item(0).getNodeValue());
							}
						} else if (childNode.getNodeName().equalsIgnoreCase("list_line_id")) {
							if (null != childNode.getChildNodes().item(0)) {
								list_line_id = Long.parseLong(childNode.getChildNodes().item(0).getNodeValue());
							}
						} else if (childNode.getNodeName().equalsIgnoreCase("inventory_item_id")) {
							if (null != childNode.getChildNodes().item(0)) {
								inventory_item_id = Long.parseLong(childNode.getChildNodes().item(0).getNodeValue());
							}
						} else if (childNode.getNodeName().equalsIgnoreCase("description")) {
							if (null != childNode.getChildNodes().item(0)) {
								description = childNode.getChildNodes().item(0).getNodeValue();
							}
						} else if (childNode.getNodeName().equalsIgnoreCase("item_num")) {
							if (null != childNode.getChildNodes().item(0)) {
								item_num = childNode.getChildNodes().item(0).getNodeValue();
							}
						} else if (childNode.getNodeName().equalsIgnoreCase("item_desc")) {
							if (null != childNode.getChildNodes().item(0)) {
								item_desc = childNode.getChildNodes().item(0).getNodeValue();
							}
						} else if (childNode.getNodeName().equalsIgnoreCase("product_uom_code")) {
							if (null != childNode.getChildNodes().item(0)) {
								product_uom_code = childNode.getChildNodes().item(0).getNodeValue();
							}
						} else if (childNode.getNodeName().equalsIgnoreCase("price")) {
							if (null != childNode.getChildNodes().item(0)) {
								price = Double.parseDouble(childNode.getChildNodes().item(0).getNodeValue());
							}
						} else if (childNode.getNodeName().equalsIgnoreCase("attribute1")) {
							if (null != childNode.getChildNodes().item(0)) {
								attribute1 = Double.parseDouble(childNode.getChildNodes().item(0).getNodeValue());
							}
						} else if (childNode.getNodeName().equalsIgnoreCase("start_date_active")) {
							if (null != childNode.getChildNodes().item(0)) {
								start_date_active = childNode.getChildNodes().item(0).getNodeValue();
							}
						} else if (childNode.getNodeName().equalsIgnoreCase("end_date_active")) {
							if (null != childNode.getChildNodes().item(0)) {
								end_date_active = childNode.getChildNodes().item(0).getNodeValue();
							}
						} else if (childNode.getNodeName().equalsIgnoreCase("ATTRIBUTE2")) {
							if (null != childNode.getChildNodes().item(0)) {
								attribute2 = Double.parseDouble(childNode.getChildNodes().item(0).getNodeValue());
							}
						} else if (childNode.getNodeName().equalsIgnoreCase("ATTRIBUTE3")) {
							if (null != childNode.getChildNodes().item(0)) {
								attribute3 = Double.parseDouble(childNode.getChildNodes().item(0).getNodeValue());
							}
						}
					}
				}
				// 保存
				// asdasdas
				TdPriceList priceList = tdPriceListService.findByListHeaderId(list_header_id);
				if (null != priceList) {
					TdPriceListItem tdPriceListItem = tdPriceListItemService.findByListLineId(list_line_id);
					if (tdPriceListItem == null) {
						tdPriceListItem = new TdPriceListItem();
						tdPriceListItem.setListLineId(list_line_id);
					}
					tdPriceListItem.setPriceListId(list_header_id);
					tdPriceListItem.setDescription(description);
					tdPriceListItem.setItemNum(item_num);
					tdPriceListItem.setItemDesc(item_desc);
					tdPriceListItem.setProductUomCode(product_uom_code);
					tdPriceListItem.setPrice(price);
					tdPriceListItem.setSalePrice(price);
					tdPriceListItem.setGoodsId(inventory_item_id);
					tdPriceListItem.setPriceListName(description);
					tdPriceListItem.setGoodsTitle(item_desc);
					tdPriceListItem.setRealSalePrice(attribute1);
					tdPriceListItem.setCouponRealPrice(attribute2);
					tdPriceListItem.setCouponPrice(attribute3);
					SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
					if (start_date_active != null) {
						try {
							Date startdate = sdf.parse(start_date_active);
							tdPriceListItem.setStartDateActive(startdate);
						} catch (ParseException e) {
							e.printStackTrace();
						}
					}
					if (StringUtils.isNotBlank(end_date_active)) {
						try {
							Date enddate = sdf.parse(end_date_active);
							tdPriceListItem.setEndDateActive(enddate);
						} catch (ParseException e) {
							e.printStackTrace();
						}
					} else {
						tdPriceListItem.setEndDateActive(null);
					}
					tdPriceListItemService.save(tdPriceListItem);
				} else {
					FitPriceLine line = fitPriceLineService.findByEbsId(list_line_id);
					if (null == line) {
						line = new FitPriceLine();
						line.setEbsId(list_line_id);
					}
					line.setHeaderId(list_header_id);
					line.setEbsNumber(description);

					TdGoods goods = tdGoodsService.findByCode(item_num);
					line.setGoodsSku(goods.getCode());
					line.setGoodsId(goods.getId());
					line.setPrice(price);
					line.setRealPrice(attribute1);
					SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
					if (start_date_active != null) {
						try {
							Date startdate = sdf.parse(start_date_active);
							line.setStartTime(startdate);
						} catch (ParseException e) {
							e.printStackTrace();
						}
					}
					if (StringUtils.isNotBlank(end_date_active)) {
						try {
							Date enddate = sdf.parse(end_date_active);
							line.setEndTime(enddate);
						} catch (ParseException e) {
							e.printStackTrace();
						}
					} else {
						line.setEndTime(null);
					}
					fitPriceLineService.save(line);
				}
			}
			return "<RESULTS><STATUS><CODE>0</CODE><MESSAGE></MESSAGE></STATUS></RESULTS>";
		} else if (STRTABLE.equalsIgnoreCase("CUXAPP_INV_ITEMS_OUT"))// TdGoods
		{
			for (int i = 0; i < nodeList.getLength(); i++) {
				Long inventory_item_id = null; // 物料ID
				String item_code = null;// 物料编号
				String item_description = null;// 物料描述
				String item_barcode = null;// 产品条码
				Long inv_category_id = null;// 库存分类ID
				Long brad_category_id = null;// 品牌分类ID
				Long product_category_id = null;// 物理分类ID
				String item_type_name = null;// 物料类型名称
				String item_type_code = null;// 物料类型CODE
				Integer inventory_item_status = null;// 物料状态 0 失效，1 有效
				String product_flag = null;// 产品标识 LYZ乐易装,HR华润，YR银润
				Double attribute1 = null;// 采购价
				String unit_name = null;// 单位名称

				Node node = nodeList.item(i);
				NodeList childNodeList = node.getChildNodes();
				for (int idx = 0; idx < childNodeList.getLength(); idx++) {
					Node childNode = childNodeList.item(idx);

					if (childNode.getNodeType() == Node.ELEMENT_NODE) {
						// 比较字段名
						if (childNode.getNodeName().equalsIgnoreCase("inventory_item_id")) {
							// 有值
							if (null != childNode.getChildNodes().item(0)) {
								inventory_item_id = Long.parseLong(childNode.getChildNodes().item(0).getNodeValue());
							}
						} else if (childNode.getNodeName().equalsIgnoreCase("item_code")) {
							if (null != childNode.getChildNodes().item(0)) {
								item_code = childNode.getChildNodes().item(0).getNodeValue();
							}
						} else if (childNode.getNodeName().equalsIgnoreCase("unit_name")) {
							if (null != childNode.getChildNodes().item(0)) {
								unit_name = childNode.getChildNodes().item(0).getNodeValue();
							}
						} else if (childNode.getNodeName().equalsIgnoreCase("item_description")) {
							if (null != childNode.getChildNodes().item(0)) {
								item_description = childNode.getChildNodes().item(0).getNodeValue();
							}
						} else if (childNode.getNodeName().equalsIgnoreCase("item_barcode")) {
							if (null != childNode.getChildNodes().item(0)) {
								item_barcode = childNode.getChildNodes().item(0).getNodeValue();
							}
						} else if (childNode.getNodeName().equalsIgnoreCase("item_type_name")) {
							if (null != childNode.getChildNodes().item(0)) {
								item_type_name = childNode.getChildNodes().item(0).getNodeValue();
							}
						} else if (childNode.getNodeName().equalsIgnoreCase("item_type_code")) {
							if (null != childNode.getChildNodes().item(0)) {
								item_type_code = childNode.getChildNodes().item(0).getNodeValue();
							}
						} else if (childNode.getNodeName().equalsIgnoreCase("inv_category_id")) {
							if (null != childNode.getChildNodes().item(0)) {
								inv_category_id = Long.parseLong(childNode.getChildNodes().item(0).getNodeValue());
							}
						} else if (childNode.getNodeName().equalsIgnoreCase("brad_category_id")) {
							if (null != childNode.getChildNodes().item(0)) {
								brad_category_id = Long.parseLong(childNode.getChildNodes().item(0).getNodeValue());
							}
						} else if (childNode.getNodeName().equalsIgnoreCase("inventory_item_status")) {
							if (null != childNode.getChildNodes().item(0)) {
								inventory_item_status = Integer
										.parseInt(childNode.getChildNodes().item(0).getNodeValue());
							}
						} else if (childNode.getNodeName().equalsIgnoreCase("product_category_id")) {
							if (null != childNode.getChildNodes().item(0)) {
								product_category_id = Long.parseLong(childNode.getChildNodes().item(0).getNodeValue());
							}
						} else if (childNode.getNodeName().equalsIgnoreCase("product_flag")) {
							if (null != childNode.getChildNodes().item(0)) {
								product_flag = childNode.getChildNodes().item(0).getNodeValue();
							}
						} else if (childNode.getNodeName().equalsIgnoreCase("attribute1")) {
							if (null != childNode.getChildNodes().item(0)) {
								attribute1 = Double.parseDouble(childNode.getChildNodes().item(0).getNodeValue());
							}
						}
					}
				}
				// 保存 修改
				TdGoods tdGoods = null;
				if (inventory_item_id != null) {
					tdGoods = tdGoodsService.findByinventoryItemId(inventory_item_id);
				}
				if (tdGoods == null) {
					tdGoods = new TdGoods();
					tdGoods.setInventoryItemId(inventory_item_id);
				}
				tdGoods.setCode(item_code);
				tdGoods.setName(item_description);
				tdGoods.setTitle(item_description);
				tdGoods.setSubTitle(item_description);
				tdGoods.setItemBarcode(item_barcode);
				tdGoods.setBradCategoryId(brad_category_id);
				tdGoods.setProductCategoryId(product_category_id);
				tdGoods.setItemTypeName(item_type_name);
				tdGoods.setItemTypeCode(item_type_code);
				tdGoods.setUnitName(unit_name);
				tdGoods.setProductFlag(product_flag);
				tdGoods.setInvCategoryId(inv_category_id);

				if (inventory_item_status == 0) {
					tdGoods.setIsOnSale(false);
					tdGoods.setInventoryItemStatus(0L);
				} else {
					tdGoods.setIsOnSale(true);
					tdGoods.setInventoryItemStatus(1L);
				}

				TdBrand tdBrand = tdBrandService.findByShortName(product_flag);
				if (tdBrand != null) {
					tdGoods.setBrandId(tdBrand.getId());
					tdGoods.setBrandTitle(tdBrand.getTitle());
				}
				tdGoods.setAttribute1(attribute1);
				tdGoodsService.save(tdGoods, "数据导入");
			}
			return "<RESULTS><STATUS><CODE>0</CODE><MESSAGE></MESSAGE></STATUS></RESULTS>";
		} else if (STRTABLE.equalsIgnoreCase("CUXAPP_INV_ITEM_CATES_OUT"))// TdLyzParameter
																			// 电商和物流系统物料类别接口表
		{
			for (int i = 0; i < nodeList.getLength(); i++) {
				Long category_id = null; // 类别ID
				String concatenated_segments = null;// 物料类别组合
				String category_set_name = null;// 类别集名称
				String segment1 = null;// 一级分类
				String segment2 = null;// 二级分类

				Node node = nodeList.item(i);
				NodeList childNodeList = node.getChildNodes();
				for (int idx = 0; idx < childNodeList.getLength(); idx++) {
					Node childNode = childNodeList.item(idx);

					if (childNode.getNodeType() == Node.ELEMENT_NODE) {
						// 比较字段名
						if (childNode.getNodeName().equalsIgnoreCase("category_id")) {
							// 有值
							if (null != childNode.getChildNodes().item(0)) {
								category_id = Long.parseLong(childNode.getChildNodes().item(0).getNodeValue());
							}
						} else if (childNode.getNodeName().equalsIgnoreCase("concatenated_segments")) {
							if (null != childNode.getChildNodes().item(0)) {
								concatenated_segments = childNode.getChildNodes().item(0).getNodeValue();
							}
						} else if (childNode.getNodeName().equalsIgnoreCase("category_set_name")) {
							if (null != childNode.getChildNodes().item(0)) {
								category_set_name = childNode.getChildNodes().item(0).getNodeValue();
							}
						} else if (childNode.getNodeName().equalsIgnoreCase("segment1")) {
							if (null != childNode.getChildNodes().item(0)) {
								segment1 = childNode.getChildNodes().item(0).getNodeValue();
							}
						} else if (childNode.getNodeName().equalsIgnoreCase("segment2")) {
							if (null != childNode.getChildNodes().item(0)) {
								segment2 = childNode.getChildNodes().item(0).getNodeValue();
							}
						}

					}
				}
				TdLyzParameter tdLyzParameter = tdLyzParameterService.findByCategoryId(category_id);
				if (tdLyzParameter == null) {
					tdLyzParameter = new TdLyzParameter();
					tdLyzParameter.setCategoryId(category_id);
					// 添加分类
					TdProductCategory parentprodcut = tdProductCategoryService.findByTitle(segment1); // 一级分类
					if (parentprodcut == null) {
						parentprodcut = new TdProductCategory();
						parentprodcut.setTitle(segment1);
						parentprodcut.setSortId(99.0);
						parentprodcut.setLayerCount(1L);
						parentprodcut.setInvCategoryId(0L);
						parentprodcut = tdProductCategoryService.save(parentprodcut);
						parentprodcut.setParentTree("[" + parentprodcut.getId() + "]");// 保存后才能拿到id
						tdProductCategoryService.save(parentprodcut);
					}
					TdProductCategory prodcut = tdProductCategoryService.findByTitleAndParentId(segment2,
							parentprodcut.getId()); // 二级分类
					if (prodcut == null) {
						prodcut = new TdProductCategory();
						prodcut.setParentId(parentprodcut.getId());
						prodcut.setTitle(segment2);
						prodcut.setSortId(99.0);
						prodcut.setLayerCount(1L);
						prodcut.setInvCategoryId(category_id);
						prodcut = tdProductCategoryService.save(prodcut);// 保存后才能拿到id
						prodcut.setParentTree(parentprodcut.getParentTree() + ",[" + parentprodcut.getId() + "]");
						tdProductCategoryService.save(prodcut);
					}
				}
				tdLyzParameter.setConcatenatedSegments(concatenated_segments);
				tdLyzParameter.setCategorySetName(category_set_name);
				tdLyzParameter.setSegment1(segment1);
				tdLyzParameter.setSegment2(segment2);
				tdLyzParameterService.save(tdLyzParameter);

			}
			return "<RESULTS><STATUS><CODE>0</CODE><MESSAGE></MESSAGE></STATUS></RESULTS>";
		} else if (STRTABLE.equalsIgnoreCase("CUXAPP_INV_ITEMS_LIMIT_OUT"))// TdGoodsLimit
		{
			for (int i = 0; i < nodeList.getLength(); i++) {
				Long limit_id = null;// id
				Long SOB_ID = null; // 分公司ID
				Long customer_id = null;// 客户id
				String customer_number = null;// 客户编码
				String customer_name = null;// 客户名称
				Long inventory_item_id = null;// 物料ID
				String item_code = null;// 物料编号
				String item_description = null;// 物理物料描述
				String start_date_active = null;// 生效日期
				String end_date_active = null;// 失效日期

				Node node = nodeList.item(i);
				NodeList childNodeList = node.getChildNodes();
				for (int idx = 0; idx < childNodeList.getLength(); idx++) {
					Node childNode = childNodeList.item(idx);

					if (childNode.getNodeType() == Node.ELEMENT_NODE) {
						// 比较字段名
						if (childNode.getNodeName().equalsIgnoreCase("limit_id")) {
							// 有值
							if (null != childNode.getChildNodes().item(0)) {
								limit_id = Long.parseLong(childNode.getChildNodes().item(0).getNodeValue());
							}
						} else if (childNode.getNodeName().equalsIgnoreCase("iteSOB_IDm_code")) {
							if (null != childNode.getChildNodes().item(0)) {
								SOB_ID = Long.parseLong(childNode.getChildNodes().item(0).getNodeValue());
							}
						} else if (childNode.getNodeName().equalsIgnoreCase("customer_id")) {
							if (null != childNode.getChildNodes().item(0)) {
								customer_id = Long.parseLong(childNode.getChildNodes().item(0).getNodeValue());
							}
						} else if (childNode.getNodeName().equalsIgnoreCase("customer_number")) {
							if (null != childNode.getChildNodes().item(0)) {
								customer_number = childNode.getChildNodes().item(0).getNodeValue();
							}
						} else if (childNode.getNodeName().equalsIgnoreCase("customer_name")) {
							if (null != childNode.getChildNodes().item(0)) {
								customer_name = childNode.getChildNodes().item(0).getNodeValue();
							}
						} else if (childNode.getNodeName().equalsIgnoreCase("item_code")) {
							if (null != childNode.getChildNodes().item(0)) {
								item_code = childNode.getChildNodes().item(0).getNodeValue();
							}
						} else if (childNode.getNodeName().equalsIgnoreCase("item_description")) {
							if (null != childNode.getChildNodes().item(0)) {
								item_description = childNode.getChildNodes().item(0).getNodeValue();
							}
						} else if (childNode.getNodeName().equalsIgnoreCase("inventory_item_id")) {
							if (null != childNode.getChildNodes().item(0)) {
								inventory_item_id = Long.parseLong(childNode.getChildNodes().item(0).getNodeValue());
							}
						} else if (childNode.getNodeName().equalsIgnoreCase("start_date_active")) {
							if (null != childNode.getChildNodes().item(0)) {
								start_date_active = childNode.getChildNodes().item(0).getNodeValue();
							}
						} else if (childNode.getNodeName().equalsIgnoreCase("end_date_active")) {
							if (null != childNode.getChildNodes().item(0)) {
								end_date_active = childNode.getChildNodes().item(0).getNodeValue();
							}
						}
					}
				}
				// 保存
				TdGoodsLimit tdGoodsLimit = tdGoodsLimitService.findByLimitId(limit_id);
				if (tdGoodsLimit == null) {
					tdGoodsLimit = new TdGoodsLimit();
					tdGoodsLimit.setLimitId(limit_id);
				}
				tdGoodsLimit.setSobId(SOB_ID);
				tdGoodsLimit.setCustomerId(customer_id);
				tdGoodsLimit.setCustomerNumber(customer_number);
				tdGoodsLimit.setCustomerName(customer_name);
				tdGoodsLimit.setInventoryItemId(inventory_item_id);
				tdGoodsLimit.setItemCode(item_code);
				tdGoodsLimit.setItemDescription(item_description);
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				if (start_date_active != null) {
					try {
						Date startdate = sdf.parse(start_date_active);
						tdGoodsLimit.setBeginDate(startdate);
					} catch (ParseException e) {
						e.printStackTrace();
					}
				}
				if (end_date_active != null) {
					try {
						Date enddate = sdf.parse(end_date_active);
						tdGoodsLimit.setFinishDate(enddate);
					} catch (ParseException e) {
						e.printStackTrace();
					}
				}
				tdGoodsLimitService.save(tdGoodsLimit);
			}
			return "<RESULTS><STATUS><CODE>0</CODE><MESSAGE></MESSAGE></STATUS></RESULTS>";
		} // CUXAPP_QP_LIST_ASSIGNS_OUT
		else if (STRTABLE.equalsIgnoreCase("CUXAPP_QP_LIST_ASSIGNS_OUT"))// 把价目表绑定到门店
		{
			for (int i = 0; i < nodeList.getLength(); i++) {
				Long list_header_id = null;// id
				Long sob_id = null; // 分公司ID
				Long customer_id = null;// 客户id
				String name = null;// 价目表名称

				Node node = nodeList.item(i);
				NodeList childNodeList = node.getChildNodes();
				for (int idx = 0; idx < childNodeList.getLength(); idx++) {
					Node childNode = childNodeList.item(idx);

					if (childNode.getNodeType() == Node.ELEMENT_NODE) {
						// 比较字段名
						if (childNode.getNodeName().equalsIgnoreCase("list_header_id")) {
							// 有值
							if (null != childNode.getChildNodes().item(0)) {
								list_header_id = Long.parseLong(childNode.getChildNodes().item(0).getNodeValue());
							}
						} else if (childNode.getNodeName().equalsIgnoreCase("sob_id")) {
							if (null != childNode.getChildNodes().item(0)) {
								sob_id = Long.parseLong(childNode.getChildNodes().item(0).getNodeValue());
							}
						} else if (childNode.getNodeName().equalsIgnoreCase("customer_id")) {
							if (null != childNode.getChildNodes().item(0)) {
								customer_id = Long.parseLong(childNode.getChildNodes().item(0).getNodeValue());
							}
						} else if (childNode.getNodeName().equalsIgnoreCase("name")) {
							if (null != childNode.getChildNodes().item(0)) {
								name = childNode.getChildNodes().item(0).getNodeValue();
							}
						}
					}
				}
				// 保存
				// TdDiySite tdDiySite =
				// tdDiySiteService.findByCustomerIdAndSobId(customer_id,
				// sob_id);
				TdDiySite tdDiySite = tdDiySiteService.findByRegionIdAndCustomerId(sob_id, customer_id);
				if (tdDiySite == null) {
					return "<RESULTS><STATUS><CODE>1</CODE><MESSAGE>该门店不存在，无法添加价目表</MESSAGE></STATUS></RESULTS>";
				}

				tdDiySite.setPriceListId(list_header_id);
				tdDiySite.setPriceListName(name);
				tdDiySiteService.save(tdDiySite);
			}
			return "<RESULTS><STATUS><CODE>0</CODE><MESSAGE></MESSAGE></STATUS></RESULTS>";
		} else if (STRTABLE.equalsIgnoreCase("CUXAPP_INV_STORE_TRANS_OUT"))// ebs库存修改
		{
			for (int i = 0; i < nodeList.getLength(); i++) {
				Long sobId = null;// 分公司ID
				String transId = null;// 事务唯一ID
				String transType = null;// 事务类型 "出货单","退货单","盘点入库","盘点出库"
				String transNumber = null;// 门店事务编号
				Long customerId = null;// 门店客户ID
				String customerNumber = null;// 门店客户编号
				String diySiteCode = null;// 门店编号(门店仓库)
				String shipDate = null;// 事务时间
				String itemCode = null;// 物料编号,SKU
				Long quantity = null;// 数量 "正数"入库，"负数"出库
				String ebsToAppFlag = null;//
				String appErrorMessage = null;//
				String creationDate = null;//
				Long lastUpdatedBy = null;//
				String lastUpdateDate = null;//
				String attribute1 = null;//
				String attribute2 = null;//
				String attribute3 = null;//
				String attribute4 = null;//
				String attribute5 = null;//

				Node node = nodeList.item(i);
				NodeList childNodeList = node.getChildNodes();
				for (int idx = 0; idx < childNodeList.getLength(); idx++) {
					Node childNode = childNodeList.item(idx);
					if (childNode.getNodeType() == Node.ELEMENT_NODE) {
						if (childNode.getNodeName().equalsIgnoreCase("SOB_ID")) {
							// 有值
							if (null != childNode.getChildNodes().item(0)) {
								sobId = Long.parseLong(childNode.getChildNodes().item(0).getNodeValue());
							}
						} else if (childNode.getNodeName().equalsIgnoreCase("TRANS_ID")) {
							// 有值
							if (null != childNode.getChildNodes().item(0)) {
								transId = childNode.getChildNodes().item(0).getNodeValue();
							}
						} else if (childNode.getNodeName().equalsIgnoreCase("TRANS_TYPE")) {
							if (null != childNode.getChildNodes().item(0)) {
								transType = childNode.getChildNodes().item(0).getNodeValue();
							}
						} else if (childNode.getNodeName().equalsIgnoreCase("TRANS_NUMBER")) {
							if (null != childNode.getChildNodes().item(0)) {
								transNumber = childNode.getChildNodes().item(0).getNodeValue();
							}
						} else if (childNode.getNodeName().equalsIgnoreCase("CUSTOMER_ID")) {
							if (null != childNode.getChildNodes().item(0)) {
								customerId = Long.parseLong(childNode.getChildNodes().item(0).getNodeValue());
							}
						} else if (childNode.getNodeName().equalsIgnoreCase("CUSTOMER_NUMBER")) {
							if (null != childNode.getChildNodes().item(0)) {
								customerNumber = childNode.getChildNodes().item(0).getNodeValue();
							}
						} else if (childNode.getNodeName().equalsIgnoreCase("DIY_SITE_CODE")) {
							if (null != childNode.getChildNodes().item(0)) {
								diySiteCode = childNode.getChildNodes().item(0).getNodeValue();
							}
						} else if (childNode.getNodeName().equalsIgnoreCase("SHIP_DATE")) {
							if (null != childNode.getChildNodes().item(0)) {
								shipDate = childNode.getChildNodes().item(0).getNodeValue();
							}
						} else if (childNode.getNodeName().equalsIgnoreCase("ITEM_CODE")) {
							if (null != childNode.getChildNodes().item(0)) {
								itemCode = childNode.getChildNodes().item(0).getNodeValue();
							}
						} else if (childNode.getNodeName().equalsIgnoreCase("QUANTITY")) {
							if (null != childNode.getChildNodes().item(0)) {
								quantity = Long.parseLong(childNode.getChildNodes().item(0).getNodeValue());
							}
						} else if (childNode.getNodeName().equalsIgnoreCase("EBS_TO_APP_FLAG")) {
							if (null != childNode.getChildNodes().item(0)) {
								ebsToAppFlag = childNode.getChildNodes().item(0).getNodeValue();
							}
						} else if (childNode.getNodeName().equalsIgnoreCase("APP_ERROR_MESSAGE")) {
							if (null != childNode.getChildNodes().item(0)) {
								appErrorMessage = childNode.getChildNodes().item(0).getNodeValue();
							}
						} else if (childNode.getNodeName().equalsIgnoreCase("CREATION_DATE")) {
							if (null != childNode.getChildNodes().item(0)) {
								creationDate = childNode.getChildNodes().item(0).getNodeValue();
							}
						} else if (childNode.getNodeName().equalsIgnoreCase("LAST_UPDATED_BY")) {
							if (null != childNode.getChildNodes().item(0)) {
								lastUpdatedBy = Long.parseLong(childNode.getChildNodes().item(0).getNodeValue());
							}
						} else if (childNode.getNodeName().equalsIgnoreCase("LAST_UPDATE_DATE")) {
							if (null != childNode.getChildNodes().item(0)) {
								lastUpdateDate = childNode.getChildNodes().item(0).getNodeValue();
							}
						} else if (childNode.getNodeName().equalsIgnoreCase("ATTRIBUTE1")) {
							if (null != childNode.getChildNodes().item(0)) {
								attribute1 = childNode.getChildNodes().item(0).getNodeValue();
							}
						} else if (childNode.getNodeName().equalsIgnoreCase("ATTRIBUTE2")) {
							if (null != childNode.getChildNodes().item(0)) {
								attribute2 = childNode.getChildNodes().item(0).getNodeValue();
							}
						} else if (childNode.getNodeName().equalsIgnoreCase("ATTRIBUTE3")) {
							if (null != childNode.getChildNodes().item(0)) {
								attribute3 = childNode.getChildNodes().item(0).getNodeValue();
							}
						} else if (childNode.getNodeName().equalsIgnoreCase("ATTRIBUTE4")) {
							if (null != childNode.getChildNodes().item(0)) {
								attribute4 = childNode.getChildNodes().item(0).getNodeValue();
							}
						} else if (childNode.getNodeName().equalsIgnoreCase("ATTRIBUTE5")) {
							if (null != childNode.getChildNodes().item(0)) {
								attribute5 = childNode.getChildNodes().item(0).getNodeValue();
							}
						}
					}
				}

				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

				TdDiySiteInventoryEbs diySiteInventoryEbs = tdDiySiteInventoryEbsService.findByTransId(transId);
				if (diySiteInventoryEbs == null) {
					diySiteInventoryEbs = new TdDiySiteInventoryEbs();
				}
				diySiteInventoryEbs.setSobId(sobId);
				diySiteInventoryEbs.setTransId(Long.parseLong(transId));
				diySiteInventoryEbs.setTransType(transType);
				diySiteInventoryEbs.setTransNumber(transNumber);
				diySiteInventoryEbs.setCustomerId(customerId);
				diySiteInventoryEbs.setCustomerNumber(customerNumber);
				diySiteInventoryEbs.setDiySiteCode(diySiteCode);

				if (shipDate != null) {
					try {
						Date ship_date = sdf.parse(shipDate);
						diySiteInventoryEbs.setShipDate(ship_date);
					} catch (ParseException e) {
						e.printStackTrace();
					}
				}
				diySiteInventoryEbs.setItemCode(itemCode);
				diySiteInventoryEbs.setQuantity(quantity);
				diySiteInventoryEbs.setEbsToAppFlag(ebsToAppFlag);
				diySiteInventoryEbs.setAppErrorMessage(appErrorMessage);

				if (creationDate != null) {
					try {
						Date creation_date = sdf.parse(creationDate);
						diySiteInventoryEbs.setCreationDate(creation_date);
					} catch (ParseException e) {
						e.printStackTrace();
					}
				}

				diySiteInventoryEbs.setLastUpdatedBy(lastUpdatedBy);

				if (lastUpdateDate != null) {
					try {
						Date last_update_date = sdf.parse(lastUpdateDate);
						diySiteInventoryEbs.setCreationDate(last_update_date);
					} catch (ParseException e) {
						e.printStackTrace();
					}
				}
				diySiteInventoryEbs.setAttribute1(attribute1);
				diySiteInventoryEbs.setAttribute2(attribute2);
				diySiteInventoryEbs.setAttribute3(attribute3);
				diySiteInventoryEbs.setAttribute4(attribute4);
				diySiteInventoryEbs.setAttribute5(attribute5);
				if (diySiteInventoryEbs.getId() != null) {
					return "<RESULTS><STATUS><CODE>1</CODE><MESSAGE>事物编码重复：" + transId
							+ "</MESSAGE></STATUS></RESULTS>";
				}

				TdGoods tdGoods = tdGoodsService.findByCodeAndStatus(itemCode, 1l);

				TdDiySite site = tdDiySiteService.findByStoreCode(diySiteCode);

				if (tdGoods == null) {
					return "<RESULTS><STATUS><CODE>1</CODE><MESSAGE>商品编码为：" + itemCode
							+ "的商品不存在或者不可用</MESSAGE></STATUS></RESULTS>";
				}
				if (site == null) {
					return "<RESULTS><STATUS><CODE>1</CODE><MESSAGE>门店编码为：" + diySiteCode
							+ "的门店不存在或者不可用</MESSAGE></STATUS></RESULTS>";
				}

				tdDiySiteInventoryEbsService.save(diySiteInventoryEbs);

				TdDiySiteInventory inventory = tdDiySiteInventoryService.findByGoodsCodeAndDiySiteId(itemCode,
						site.getId());
				if (inventory == null) {
					inventory = new TdDiySiteInventory();
					inventory.setInventory(quantity);
					inventory.setDiySiteId(site.getId());
					inventory.setDiySiteName(site.getTitle());
					inventory.setDiyCode(diySiteInventoryEbs.getDiySiteCode());
					inventory.setGoodsCode(tdGoods.getCode());
					inventory.setGoodsId(tdGoods.getId());
					inventory.setCategoryId(tdGoods.getCategoryId());
					inventory.setCategoryIdTree(tdGoods.getCategoryIdTree());
					inventory.setCategoryTitle(tdGoods.getCategoryTitle());
					inventory.setDiyCode(site.getStoreCode());
					inventory.setGoodsTitle(tdGoods.getTitle());
					inventory.setRegionId(site.getRegionId());
					inventory.setRegionName(site.getCity());
					tdDiySiteInventoryLogService.saveChangeLog(inventory, inventory.getInventory(), transNumber, null,
							transType);
					tdDiySiteInventoryService.save(inventory);
				} else {
					inventory.setInventory(inventory.getInventory() + quantity);
					tdDiySiteInventoryLogService.saveChangeLog(inventory, quantity, transNumber, null, transType);
					tdDiySiteInventoryService.save(inventory);
				}
			}
			return "<RESULTS><STATUS><CODE>0</CODE><MESSAGE></MESSAGE></STATUS></RESULTS>";
		}

		return "<RESULTS><STATUS><CODE>1</CODE><MESSAGE>不支持该表数据传输：" + STRTABLE + "</MESSAGE></STATUS></RESULTS>";
	}

}
// END SNIPPET: service
