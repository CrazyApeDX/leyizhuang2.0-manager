package com.ynyes.lyz.interfaces.service;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.transaction.Transactional;
import javax.xml.namespace.QName;
import javax.xml.rpc.ParameterMode;
import javax.xml.soap.SOAPElement;
import javax.xml.soap.SOAPFactory;

import org.apache.axis.client.Call;
import org.apache.axis.encoding.XMLType;
import org.apache.axis.message.SOAPHeaderElement;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ynyes.fitment.foundation.entity.FitCompany;
import com.ynyes.fitment.foundation.service.FitCompanyService;
import com.ynyes.lyz.entity.TdCashReturnNote;
import com.ynyes.lyz.entity.TdCoupon;
import com.ynyes.lyz.entity.TdDiySite;
import com.ynyes.lyz.entity.TdOrder;
import com.ynyes.lyz.entity.TdOrderGoods;
import com.ynyes.lyz.entity.TdOwnMoneyRecord;
import com.ynyes.lyz.entity.TdPayType;
import com.ynyes.lyz.entity.TdPriceList;
import com.ynyes.lyz.entity.TdPriceListItem;
import com.ynyes.lyz.entity.TdRecharge;
import com.ynyes.lyz.entity.TdReturnNote;
import com.ynyes.lyz.entity.TdSetting;
import com.ynyes.lyz.entity.user.TdUser;
import com.ynyes.lyz.interfaces.entity.TdCashReciptInf;
import com.ynyes.lyz.interfaces.entity.TdCashRefundInf;
import com.ynyes.lyz.interfaces.entity.TdOrderCouponInf;
import com.ynyes.lyz.interfaces.entity.TdOrderGoodsInf;
import com.ynyes.lyz.interfaces.entity.TdOrderInf;
import com.ynyes.lyz.interfaces.entity.TdOrderReceiveInf;
import com.ynyes.lyz.interfaces.entity.TdReturnCouponInf;
import com.ynyes.lyz.interfaces.entity.TdReturnGoodsInf;
import com.ynyes.lyz.interfaces.entity.TdReturnOrderInf;
import com.ynyes.lyz.interfaces.entity.TdReturnTimeInf;
import com.ynyes.lyz.interfaces.utils.EnumUtils.INFTYPE;
import com.ynyes.lyz.interfaces.utils.INFConstants;
import com.ynyes.lyz.interfaces.utils.InterfaceConfigure;
import com.ynyes.lyz.interfaces.utils.StringTools;
import com.ynyes.lyz.service.TdCouponService;
import com.ynyes.lyz.service.TdDiySiteService;
import com.ynyes.lyz.service.TdOrderService;
import com.ynyes.lyz.service.TdPayTypeService;
import com.ynyes.lyz.service.TdPriceCountService;
import com.ynyes.lyz.service.TdPriceListItemService;
import com.ynyes.lyz.service.TdPriceListService;
import com.ynyes.lyz.service.TdReturnNoteService;
import com.ynyes.lyz.service.TdSettingService;

@Service
@Transactional
public class TdInterfaceService {

	@Autowired
	private TdOrderInfService tdOrderInfService;

	@Autowired
	private TdOrderGoodsInfService tdOrderGoodsInfService;

	@Autowired
	private TdDiySiteService tdDiySiteService;

	@Autowired
	private TdPayTypeService tdPayTypeService;

	@Autowired
	private TdCouponService tdCouponService;

	@Autowired
	private TdOrderCouponInfService tdOrderCouponInfService;

	@Autowired
	private TdOrderReceiveInfService tdOrderReceiveInfService;

	@Autowired
	private TdReturnOrderInfService tdReturnOrderInfService;

	@Autowired
	private TdReturnGoodsInfService tdReturnGoodsInfService;

	@Autowired
	private TdReturnCouponInfService tdReturnCouponInfService;

	@Autowired
	private TdReturnTimeInfService tdReturnTimeInfService;

	@Autowired
	private TdCashReciptInfService tdCashReciptInfService;

	@Autowired
	private TdCashRefundInfService tdCashRefundInfService;

	@Autowired
	private TdOrderService tdOrderService;

	@Autowired
	private TdReturnNoteService tdReturnNoteService;

	@Autowired
	private TdPriceCountService tdPriceCountService;

	@Autowired
	private TdSettingService tdSettingService;

	@Autowired
	private TdPriceListService tdPriceListService;

	@Autowired
	private TdPriceListItemService tdPriceListItemService;

	@Autowired
	private FitCompanyService fitCompanyService;

	private Call call;

	// private org.apache.axis.client.Service wbService = new
	// org.apache.axis.client.Service();

	/**
	 * 访问WebService的服务端
	 * 
	 * @return Call
	 */
	public Call getCall() {
		if (call != null) {
			return call;
		}
		try {
			String AUTH_PREFIX = "wsse";
			String AUTH_NS = "http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-secext-1.0.xsd";
			SOAPFactory soapFactory = SOAPFactory.newInstance();
			SOAPElement wsSecHeaderElm = soapFactory.createElement("Security", AUTH_PREFIX, AUTH_NS);
			SOAPElement userNameTokenElm = soapFactory.createElement("UsernameToken", AUTH_PREFIX, AUTH_NS);
			SOAPElement userNameElm = soapFactory.createElement("Username", AUTH_PREFIX, AUTH_NS);
			SOAPElement passwdElm = soapFactory.createElement("Password", AUTH_PREFIX, AUTH_NS);
			passwdElm.setAttribute("Type",
					"http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-username-token-profile-1.0#PasswordText");

			userNameElm.addTextNode("runpeng");
			passwdElm.addTextNode("A11111");

			userNameTokenElm.addChildElement(userNameElm);
			userNameTokenElm.addChildElement(passwdElm);
			wsSecHeaderElm.addChildElement(userNameTokenElm);
			SOAPHeaderElement soapHeaderElement = new SOAPHeaderElement(wsSecHeaderElm);
			org.apache.axis.client.Service wbService = new org.apache.axis.client.Service();
			call = (Call) wbService.createCall();
			call.setTimeout(new Integer(60000));
			QName EBSQName = new QName(
					"http://xmlns.oracle.com/apps/cux/soaprovider/plsql/cux_app_webservice_pkg/get_xml/", "GET_XML");
			call.setOperationName(EBSQName);
			call.setEncodingStyle("UTF-8");
			call.setTargetEndpointAddress(InterfaceConfigure.EBS_WS_URL);
			call.setReturnType(XMLType.XSD_STRING);
			call.addHeader(soapHeaderElement);
			QName TableQName = new QName("STRTABLE");
			QName TypeQName = new QName("STRTYPE");
			QName XMLQName = new QName("XML");
			call.addParameter(TableQName, XMLType.XSD_STRING, ParameterMode.IN);
			call.addParameter(TypeQName, XMLType.XSD_STRING, ParameterMode.IN);
			call.addParameter(XMLQName, XMLType.XSD_STRING, ParameterMode.IN);
			return call;
		} catch (Exception e) {
			System.out.println("EBS:MDJ" + e.getMessage());
			return null;
		}
	}

	public void sendReturnOrderByAsyn(TdReturnNote returnNote) {
		sendEbsReturnOrderThread ebsReturnOrderThread = new sendEbsReturnOrderThread(returnNote);
		ebsReturnOrderThread.start();
	}

	class sendEbsReturnOrderThread extends Thread {
		TdReturnNote returnNote;

		@Override
		public void run() {
			sendEbsReturnOrder(returnNote);
		}

		public sendEbsReturnOrderThread(TdReturnNote returnNote) {
			this.returnNote = returnNote;
		}
	}

	/**
	 * 根据退货单给ebs发送销退单
	 * 
	 * @param returnNote
	 * 
	 */
	private void sendEbsReturnOrder(TdReturnNote returnNote) {
		if (returnNote == null) {
			return;
		}
		TdReturnOrderInf returnOrderInf = tdReturnOrderInfService.findByReturnNumber(returnNote.getReturnNumber());
		if (returnOrderInf == null) {
			return;
		}
		Boolean isSendSuccess = false;
		// 退单头
		String returnOrderInfXml = this.XmlWithObject(returnOrderInf, INFTYPE.RETURNORDERINF);
		if (returnOrderInfXml != null) {
			Object[] orderInf = { INFConstants.INF_RT_ORDER_STR, "1", returnOrderInfXml };
			String result = this.ebsWsInvoke(orderInf);
			if (StringUtils.isBlank(result)) {
				isSendSuccess = true;
				returnOrderInf.setSendFlag(0);
			} else {
				returnOrderInf.setSendFlag(1);
				returnOrderInf.setErrorMsg(result);
			}
			tdReturnOrderInfService.save(returnOrderInf);
		}
		// 行
		List<TdReturnGoodsInf> returnGoodsInfs = tdReturnGoodsInfService
				.findByRtHeaderId(returnOrderInf.getRtHeaderId());
		String returnGoodsInfXml = this.XmlWithObject(returnGoodsInfs, INFTYPE.RETURNGOODSINF);
		if (returnGoodsInfXml != null && isSendSuccess) {
			Object[] orderGoodsInf = { INFConstants.INF_RT_ORDER_GOODS_STR, "1", returnGoodsInfXml };
			String reuslt = this.ebsWsInvoke(orderGoodsInf);
			for (int i = 0; i < returnGoodsInfs.size(); i++) {
				if (StringUtils.isBlank(reuslt)) {
					returnGoodsInfs.get(i).setSendFlag(0);
				} else {
					returnGoodsInfs.get(i).setSendFlag(1);
					returnGoodsInfs.get(i).setErrorMsg(reuslt);
				}
			}
			tdReturnGoodsInfService.save(returnGoodsInfs);
		}
		// 券
		List<TdReturnCouponInf> returnCouponInfs = tdReturnCouponInfService
				.findByRtHeaderId(returnOrderInf.getRtHeaderId());
		String returnCouponInfXml = this.XmlWithObject(returnCouponInfs, INFTYPE.RETURNCOUPONINF);
		if (returnCouponInfXml != null && isSendSuccess) {
			Object[] orderInf = { INFConstants.INF_RT_ORDER_COUPONS_STR, "1", returnCouponInfXml };
			String reuslt = this.ebsWsInvoke(orderInf);
			for (int i = 0; i < returnCouponInfs.size(); i++) {
				if (StringUtils.isBlank(reuslt)) {
					returnCouponInfs.get(i).setSendFlag(0);
				} else {
					returnCouponInfs.get(i).setSendFlag(1);
					returnCouponInfs.get(i).setErrorMsg(reuslt);
				}
			}
			tdReturnCouponInfService.save(returnCouponInfs);
		}

	}

	/** -=-=-=-=-=-=- 生成实体类 -=-=-=-=-=-=- **/

	/**
	 * 根据订单生成销售订单相关数据
	 * 
	 * @param tdOrder
	 */
	public TdOrderInf initOrderInf(TdOrder tdOrder) {
		if (tdOrder == null) {
			return null;
		}
		TdOrderInf orderInf = tdOrderInfService.findByOrderNumber(tdOrder.getOrderNumber());
		if (orderInf != null) {
			return null;
		}
		orderInf = new TdOrderInf();
		Long SobId = 0L;
		if (!tdOrder.getOrderNumber().contains("FIT")) {
			TdDiySite diySite = tdDiySiteService.findOne(tdOrder.getDiySiteId());
			if (diySite != null) {
				SobId = diySite.getRegionId();
			}
		} else {
			try {
				FitCompany company = fitCompanyService.findOne(tdOrder.getDiySiteId());
				if (null != company) {
					SobId = company.getSobId();
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		// orderInf.setHeaderId(tdOrder.getId());
		String payTypeTitle = tdOrder.getPayTypeTitle();
		orderInf.setSobId(SobId);
		orderInf.setOrderNumber(tdOrder.getOrderNumber());
		orderInf.setOrderDate(tdOrder.getOrderTime());
		orderInf.setMainOrderNumber(tdOrder.getMainOrderNumber());
		orderInf.setProductType(StringTools.getProductStrByOrderNumber(tdOrder.getOrderNumber()));
		orderInf.setOrderTypeId(4);
		orderInf.setUserid(tdOrder.getRealUserId());
		orderInf.setUsername(tdOrder.getRealUserRealName());
		orderInf.setUserphone(tdOrder.getRealUserUsername());
		orderInf.setDiySiteId(tdOrder.getDiySiteId());
		orderInf.setDiySiteCode(tdOrder.getDiySiteCode());
		orderInf.setDiySiteName(tdOrder.getDiySiteName());
		orderInf.setDiySitePhone(tdOrder.getDiySitePhone());
		orderInf.setProvince(tdOrder.getProvince());
		orderInf.setCity(tdOrder.getCity());
		orderInf.setDisctrict(tdOrder.getDisctrict());
		orderInf.setShippingName(tdOrder.getShippingName());
		orderInf.setShippingPhone(tdOrder.getShippingPhone());
		orderInf.setDeliverTypeTitle(tdOrder.getDeliverTypeTitle());
		orderInf.setIsonlinepay(booleanStrByPayTypeId(tdOrder.getPayTypeId()));
		orderInf.setPayType(("微信支付".equalsIgnoreCase(payTypeTitle) ? "微信" : payTypeTitle));
		orderInf.setPayDate(tdOrder.getPayTime());
		orderInf.setPayAmt(booleanByStr(orderInf.getIsonlinepay()) ? tdOrder.getTotalPrice() : 0);
		if (tdOrder.getOrderNumber().contains("FIT")) {
			orderInf.setCreditAmt(tdOrder.getCashBalanceUsed() + tdOrder.getUnCashBalanceUsed());
			orderInf.setPrepayAmt(0d);
		} else {
			orderInf.setPrepayAmt(tdOrder.getCashBalanceUsed() + tdOrder.getUnCashBalanceUsed());
		}
		if ("支付宝".equalsIgnoreCase(payTypeTitle) || "银行卡".equalsIgnoreCase(payTypeTitle)
				|| "微信支付".equalsIgnoreCase(payTypeTitle)) {
			orderInf.setRecAmt(0.0);
		} else {
			orderInf.setRecAmt(tdOrder.getTotalPrice());
		}

		String cashCouponId = (null == tdOrder.getCashCouponId()) ? "" : tdOrder.getCashCouponId();
		String productCouponId = (null == tdOrder.getProductCouponId()) ? "" : tdOrder.getProductCouponId();
		String couponIdsStr = cashCouponId + productCouponId;
		if (!couponIdsStr.equals("")) {
			orderInf.setCouponFlag('Y');
		} else {
			orderInf.setCouponFlag('N');
			;
		}
		Double deliverFee = tdOrder.getDeliverFee();
		if (null != deliverFee && deliverFee > 0d) {
			orderInf.setDeliveryFee(deliverFee);
		} else {
			orderInf.setDeliveryFee(0d);
		}

		tdOrderInfService.save(orderInf);

		// 商品TdOrderGoodsInf
		List<TdOrderGoods> goodsList = tdOrder.getOrderGoodsList();
		if (goodsList != null && goodsList.size() > 0) {
			for (TdOrderGoods tdOrderGoods : goodsList) {
				TdOrderGoodsInf goodsInf = new TdOrderGoodsInf();
				goodsInf.setOrderHeaderId(orderInf.getHeaderId());
				goodsInf.setGoodsId(tdOrderGoods.getGoodsId());
				goodsInf.setGoodsTitle(tdOrderGoods.getGoodsTitle());
				goodsInf.setGoodsSubTitle(tdOrderGoods.getGoodsSubTitle());
				goodsInf.setSku(tdOrderGoods.getSku());
				goodsInf.setQuantity(tdOrderGoods.getQuantity());
				// goodsInf.setJxPrice(tdOrderGoods.getRealPrice());
				// //针对JX商：b2c到店自提不传，
				goodsInf.setLsPrice(tdOrderGoods.getPrice());
				goodsInf.setGiftFlag("N");
				goodsInf.setPromotion(tdOrderGoods.getActivityId());
				tdOrderGoodsInfService.save(goodsInf);
			}
		}
		// 小辅料TdOrderGoodsInf
		List<TdOrderGoods> gifList = tdOrder.getGiftGoodsList();
		if (gifList != null && gifList.size() > 0) {
			for (TdOrderGoods tdOrderGoods : gifList) {
				TdOrderGoodsInf goodsInf = new TdOrderGoodsInf();
				goodsInf.setOrderHeaderId(orderInf.getHeaderId());
				goodsInf.setGoodsId(tdOrderGoods.getGoodsId());
				goodsInf.setGoodsTitle(tdOrderGoods.getGoodsTitle());
				goodsInf.setGoodsSubTitle(tdOrderGoods.getGoodsSubTitle());
				goodsInf.setSku(tdOrderGoods.getSku());
				goodsInf.setQuantity(tdOrderGoods.getQuantity());
				// goodsInf.setJxPrice(tdOrderGoods.getRealPrice());
				goodsInf.setLsPrice(this.getGoodsPrice(SobId, tdOrderGoods));
				goodsInf.setGiftFlag("Y");
				goodsInf.setPromotion(tdOrderGoods.getActivityId());
				tdOrderGoodsInfService.save(goodsInf);
			}
		}
		// 赠品TdOrderGoodsInf
		List<TdOrderGoods> presentedList = tdOrder.getPresentedList();
		if (presentedList != null && presentedList.size() > 0) {
			for (TdOrderGoods tdOrderGoods : presentedList) {
				TdOrderGoodsInf goodsInf = new TdOrderGoodsInf();
				goodsInf.setOrderHeaderId(orderInf.getHeaderId());
				goodsInf.setGoodsId(tdOrderGoods.getGoodsId());
				goodsInf.setGoodsTitle(tdOrderGoods.getGoodsTitle());
				goodsInf.setGoodsSubTitle(tdOrderGoods.getGoodsSubTitle());
				goodsInf.setSku(tdOrderGoods.getSku());
				goodsInf.setQuantity(tdOrderGoods.getQuantity());
				// goodsInf.setJxPrice(tdOrderGoods.getRealPrice());
				goodsInf.setLsPrice(tdOrderGoods.getGiftPrice());
				goodsInf.setGiftFlag("Y");
				goodsInf.setPromotion(tdOrderGoods.getActivityId());
				tdOrderGoodsInfService.save(goodsInf);
			}
		}

		String[] couponIds = couponIdsStr.split(",");
		for (String string : couponIds) {
			if (org.apache.commons.lang3.StringUtils.isNotBlank(string)) {
				Long couponId = Long.parseLong(string);
				TdCoupon tdCoupon = tdCouponService.findOne(couponId);
				if (tdCoupon != null) {
					// 优惠券
					TdOrderCouponInf couponInf = new TdOrderCouponInf();
					couponInf.setOrderHeaderId(orderInf.getHeaderId());
					couponInf.setCouponTypeId(StringTools.coupontypeWithCoupon(tdCoupon));
					couponInf.setSku(null == tdCoupon.getSku() ? null : tdCoupon.getSku().trim());
					couponInf.setQuantity(1L);
					if (null != tdCoupon.getTypeCategoryId()) {
						Long type = tdCoupon.getTypeCategoryId();
						if (type.longValue() == 1L) {
							couponInf.setPrice(tdCoupon.getRealPrice());
						} else if (type.longValue() == 2L) {
							couponInf.setPrice(tdCoupon.getRealPrice());
						} else if (type.longValue() == 3L) {
							if (null != tdCoupon.getIsBuy() && tdCoupon.getIsBuy()) {
								couponInf.setPrice(tdCoupon.getBuyPrice());
							} else {
								couponInf.setPrice(0.00);
							}
						}
					}
					couponInf.setHistoryFlag(StringTools.getHistoryFlagByCouponType(tdCoupon.getTypeCategoryId()));
					couponInf.setAttribute1(tdCoupon.getSign());
					tdOrderCouponInfService.save(couponInf);
				}
			}
		}

		// 获取满金额赠金额
		if (tdOrder.getActivitySubPrice() != null && tdOrder.getActivitySubPrice() > 0) {
			TdOrderCouponInf couponInf = new TdOrderCouponInf();
			couponInf.setOrderHeaderId(orderInf.getHeaderId());
			couponInf.setCouponTypeId(3);
			couponInf.setSku(null);
			couponInf.setQuantity(1L);
			couponInf.setPrice(tdOrder.getActivitySubPrice());
			couponInf.setHistoryFlag("N");
			tdOrderCouponInfService.save(couponInf);
		}

		// 收款(在线支付根据订单来判断支付多少钱)
		/*
		 * if (tdOrder.getOtherPay()!= null && tdOrder.getOtherPay() != 0) {
		 * TdCashReciptInf cashReciptInf = new TdCashReciptInf();
		 * cashReciptInf.setSobId(SobId);
		 * cashReciptInf.setReceiptNumber(tdOrder.getOrderNumber());
		 * cashReciptInf.setUserid(tdOrder.getRealUserId());
		 * cashReciptInf.setUsername(tdOrder.getRealUserRealName());
		 * cashReciptInf.setUserphone(tdOrder.getRealUserUsername());
		 * cashReciptInf.setDiySiteCode(tdOrder.getDiySiteCode());
		 * cashReciptInf.setReceiptClass(StringTools.productClassStrByBoolean(
		 * tdOrder.getIsCoupon()));
		 * cashReciptInf.setOrderHeaderId(orderInf.getHeaderId());
		 * cashReciptInf.setOrderNumber(tdOrder.getOrderNumber());
		 * cashReciptInf.setProductType(StringTools.getProductStrByOrderNumber(
		 * tdOrder.getOrderNumber()));
		 * cashReciptInf.setReceiptType(tdOrder.getPayTypeTitle());
		 * cashReciptInf.setReceiptDate(new Date());
		 * cashReciptInf.setAmount(tdOrder.getOtherPay());
		 * tdCashReciptInfService.save(cashReciptInf); }
		 */

		/* 如果在线上支付的上楼费，就需要传递给EBS */
		// if (tdOrder.getUpstairsBalancePayed() > 0) {
		// TdCashReciptInf cashReciptInf = new TdCashReciptInf();
		// cashReciptInf.setSobId(SobId);
		// cashReciptInf.setReceiptNumber(tdOrder.getOrderNumber());
		// cashReciptInf.setUserid(tdOrder.getRealUserId());
		// cashReciptInf.setUsername(tdOrder.getRealUserRealName());
		// cashReciptInf.setUserphone(tdOrder.getRealUserUsername());
		// cashReciptInf.setDiySiteCode(tdOrder.getDiySiteCode());
		// cashReciptInf.setReceiptClass(StringTools.productClassStrByBoolean(tdOrder.getIsCoupon()));
		// cashReciptInf.setOrderHeaderId(orderInf.getHeaderId());
		// cashReciptInf.setOrderNumber(tdOrder.getOrderNumber());
		// cashReciptInf.setProductType(StringTools.getProductStrByOrderNumber(tdOrder.getOrderNumber()));
		// cashReciptInf.setReceiptType(tdOrder.getPayTypeTitle());
		// cashReciptInf.setReceiptDate(new Date());
		// cashReciptInf.setAmount(tdOrder.getOtherPay());
		// tdCashReciptInfService.save(cashReciptInf);
		//
		// }

		if (tdOrder.getUpstairsBalancePayed() > 0) {
			TdCashReciptInf cashReciptInf = tdCashReciptInfService
					.findByOrderNumberAndReceiptClassAndReceiptType(tdOrder.getMainOrderNumber(), "上楼费", "预收款");
			if (null == cashReciptInf) {
				if (tdOrder.getUpstairsBalancePayed() > 0) {
					cashReciptInf = new TdCashReciptInf();
					cashReciptInf.setSobId(SobId);
					cashReciptInf.setReceiptNumber(tdOrder.getMainOrderNumber().replace("XN", "UPB"));
					cashReciptInf.setUserid(tdOrder.getRealUserId());
					cashReciptInf.setUsername(tdOrder.getRealUserRealName());
					cashReciptInf.setUserphone(tdOrder.getRealUserUsername());
					cashReciptInf.setDiySiteCode(tdOrder.getDiySiteCode());
					cashReciptInf.setReceiptClass("上楼费");
					cashReciptInf.setOrderNumber(tdOrder.getMainOrderNumber());
					cashReciptInf.setProductType("UPSTAIRS");
					cashReciptInf.setReceiptType("预收款");
					cashReciptInf.setReceiptDate(new Date());
					cashReciptInf.setAmount(tdOrder.getUpstairsBalancePayed());
					tdCashReciptInfService.save(cashReciptInf);
				}
			}
		}
		if (tdOrder.getUpstairsOtherPayed() > 0) {
			TdCashReciptInf cashReciptInf = tdCashReciptInfService.findByOrderNumberAndReceiptClassAndReceiptType(
					tdOrder.getMainOrderNumber(), "上楼费", tdOrder.getPayTypeTitle());
			if (null == cashReciptInf) {
				if (tdOrder.getUpstairsBalancePayed() > 0) {
					cashReciptInf = new TdCashReciptInf();
					cashReciptInf.setSobId(SobId);
					cashReciptInf.setReceiptNumber(tdOrder.getMainOrderNumber().replace("XN", "UPO"));
					cashReciptInf.setUserid(tdOrder.getRealUserId());
					cashReciptInf.setUsername(tdOrder.getRealUserRealName());
					cashReciptInf.setUserphone(tdOrder.getRealUserUsername());
					cashReciptInf.setDiySiteCode(tdOrder.getDiySiteCode());
					cashReciptInf.setReceiptClass("上楼费");
					cashReciptInf.setOrderNumber(tdOrder.getMainOrderNumber());
					cashReciptInf.setProductType("UPSTAIRS");
					cashReciptInf.setReceiptType(tdOrder.getPayTypeTitle());
					cashReciptInf.setReceiptDate(new Date());
					cashReciptInf.setAmount(tdOrder.getUpstairsOtherPayed());
					tdCashReciptInfService.save(cashReciptInf);
				}
			}
		}
		return orderInf;
	}

	/**
	 * 获取商品零售价
	 * 
	 * @param sobId
	 * @param goods
	 * @return
	 */
	public Double getGoodsPrice(Long sobId, TdOrderGoods goods) {

		if (null == goods) {
			return 0d;
		}

		String productFlag = goods.getBrandTitle();

		if (null == productFlag) {
			return 0d;
		}

		String priceType = null;

		// 零售价
		if (productFlag.equalsIgnoreCase("华润")) {
			priceType = "LS";
		}
		// 乐意装价
		else if (productFlag.equalsIgnoreCase("乐易装")) {
			priceType = "LYZ";
		}
		// 莹润价
		else if (productFlag.equalsIgnoreCase("莹润")) {
			priceType = "YR";
		}
		// 不支持的价格
		else {
			return 0d;
		}

		List<TdPriceList> priceList_list = tdPriceListService
				.findBySobIdAndPriceTypeAndStartDateActiveAndEndDateActive(sobId, priceType, new Date(), new Date());

		if (null == priceList_list || priceList_list.size() == 0 || priceList_list.size() > 1) {
			return 0d;
		}

		// 价目表ID
		Long list_header_id = 0L;
		list_header_id = priceList_list.get(0).getListHeaderId();

		List<TdPriceListItem> priceItemList = tdPriceListItemService
				.findValidItemByPriceListHeaderIdAndGoodsCode(list_header_id, goods.getSku());

		if (null == priceItemList || priceItemList.size() == 0 || priceItemList.size() > 1) {
			return 0d;
		}

		return priceItemList.get(0).getPrice();
	}

	/**
	 * 生成销售单的收货时间，收到货的时间
	 * 
	 * @param tdOrder
	 * @return
	 */
	public TdOrderReceiveInf initOrderReceiveByOrder(TdOrder tdOrder) {
		TdOrderReceiveInf orderReceiveInf = new TdOrderReceiveInf();
		if (tdOrder == null || tdOrder.getDeliverTypeTitle() == null) {
			return null;
		}
		if (tdOrder.getDeliverTypeTitle().equalsIgnoreCase("门店自提")) {
			TdDiySite diySite = tdDiySiteService.findOne(tdOrder.getDiySiteId());
			if (diySite != null) {
				orderReceiveInf.setSobId(diySite.getRegionId());
			}
			TdOrderInf tdOrderInf = tdOrderInfService.findByOrderNumber(tdOrder.getOrderNumber());
			if (null != tdOrderInf) {
				orderReceiveInf.setHeaderId(tdOrderInf.getHeaderId());
			}
			orderReceiveInf.setOrderNumber(tdOrder.getOrderNumber());
			orderReceiveInf.setReceiveDate(new Date());
			orderReceiveInf.setDeliverTypeTitle("门店自提");
			return tdOrderReceiveInfService.save(orderReceiveInf);
		}
		return null;
	}

	/**
	 * 订单支付后，支付宝，微信，银联的收款
	 * 
	 * @param tdOrder
	 * @return
	 */
	public TdCashReciptInf initCashReciptByOrder(TdOrder tdOrder) {
		if (tdOrder == null) {
			return null;
		}

		if (tdOrder.getOtherPay() != null && tdOrder.getOtherPay() != 0) {
			TdOrderInf orderInf = tdOrderInfService.findByOrderNumber(tdOrder.getOrderNumber());
			if (orderInf == null) {
				return null;
			}

			TdDiySite diySite = tdDiySiteService.findOne(tdOrder.getDiySiteId());
			Long SobId = 0L;
			if (diySite != null) {
				SobId = diySite.getRegionId();
			}
			TdCashReciptInf cashReciptInf = new TdCashReciptInf();
			cashReciptInf.setSobId(SobId);
			cashReciptInf.setReceiptNumber(tdOrder.getOrderNumber());
			cashReciptInf.setUserid(tdOrder.getRealUserId());
			cashReciptInf.setUsername(tdOrder.getRealUserRealName());
			cashReciptInf.setUserphone(tdOrder.getRealUserUsername());
			cashReciptInf.setDiySiteCode(tdOrder.getDiySiteCode());
			cashReciptInf.setReceiptClass(StringTools.productClassStrByBoolean(tdOrder.getIsCoupon()));
			cashReciptInf.setOrderHeaderId(orderInf.getHeaderId());
			cashReciptInf.setOrderNumber(tdOrder.getOrderNumber());
			cashReciptInf.setProductType(StringTools.getProductStrByOrderNumber(tdOrder.getOrderNumber()));
			cashReciptInf.setReceiptType(tdOrder.getPayTypeTitle());
			cashReciptInf.setReceiptDate(new Date());
			cashReciptInf.setAmount(tdOrder.getOtherPay());
			return tdCashReciptInfService.save(cashReciptInf);
		}
		return null;
	}

	/**
	 * 订单支付后，支付宝，微信，银联的收款
	 * 
	 * @param tdOrder
	 * @return
	 */
	public TdCashReciptInf initCashReciptByReCharge(TdRecharge tdRecharge, TdUser tdUser) {
		if (tdRecharge == null) {
			return null;
		}

		if (tdRecharge.getTotalPrice() != null && tdRecharge.getTotalPrice() != 0) {
			TdDiySite diySite = tdDiySiteService.findOne(tdUser.getUpperDiySiteId());
			Long SobId = 0L;
			String storeCode = null;
			if (diySite != null) {
				SobId = diySite.getRegionId();
				storeCode = diySite.getStoreCode();
			}
			TdCashReciptInf cashReciptInf = new TdCashReciptInf();
			cashReciptInf.setSobId(SobId);
			cashReciptInf.setUserid(tdUser.getId());
			cashReciptInf.setUsername(tdUser.getRealName());
			cashReciptInf.setUserphone(tdUser.getUsername());
			cashReciptInf.setReceiptNumber(StringTools.getUniqueNoWithHeader("RC"));
			cashReciptInf.setDiySiteCode(storeCode);
			if(tdRecharge.getTypeTitle().equals("信用额度")){
				cashReciptInf.setReceiptClass("信用额度");
				cashReciptInf.setProductType("CREDIT");
			}else if(tdRecharge.getTypeTitle().equals("CRM积分")){
				cashReciptInf.setReceiptClass("CRM积分");
				cashReciptInf.setProductType("CRM");
			}else{
				cashReciptInf.setReceiptClass("预收款");
				cashReciptInf.setProductType("PREPAY");
			}
			cashReciptInf.setReceiptType(tdRecharge.getTypeTitle());
			cashReciptInf.setReceiptDate(new Date());
			cashReciptInf.setAmount(tdRecharge.getTotalPrice());
			return tdCashReciptInfService.save(cashReciptInf);
		}
		return null;
	}

	/**
	 * 配送员或者门店收款
	 * 
	 * @param ownMoneyRecord
	 * @param type
	 *            3代表门店 4代表配送员
	 * @return
	 */
	public TdCashReciptInf initCashReciptByTdOwnMoneyRecord(TdOwnMoneyRecord ownMoneyRecord, Integer type) {
		if (ownMoneyRecord == null) {
			return null;
		}
		List<TdOrder> subOrders = tdOrderService.findByMainOrderNumberIgnoreCase(ownMoneyRecord.getOrderNumber());
		if (subOrders == null || subOrders.size() < 1) {
			return null;
		}
		for (TdOrder tdOrder : subOrders) {
			if (tdOrder == null) {
				return null;
			}
			Double allTotalPay = tdOrder.getAllTotalPay() == null ? 0.00 : tdOrder.getAllTotalPay(); // 总单应支付金额
			if (allTotalPay == 0) {
				allTotalPay = 1d;
			}
			Double totalPrice = tdOrder.getTotalPrice() == null ? 0.00 : tdOrder.getTotalPrice(); // 分单应收金额

			// 新增：2016-09-13门店收取现金可能是负的
			Double money = ownMoneyRecord.getBackMoney();
			Double pos = ownMoneyRecord.getBackPos();
			Double backOther = ownMoneyRecord.getBackOther();
			if (null == money) {
				money = 0.00;
			}
			if (null == pos) {
				pos = 0.00;
			}
			if (null == backOther) {
				backOther = 0.00;
			}

			if (money < 0) {
				if (money + pos >= 0) {
					pos += money;
					money = 0.00;
				} else {
					backOther += (money + pos);
					money = 0.00;
					pos = 0.00;
				}
			}

			// 配送现金
			if (INFConstants.INF_RECEIPT_TYPE_DELIVER_INT == type && ownMoneyRecord.getMoney() > 0) {
				Double amount = totalPrice * ownMoneyRecord.getMoney() / (allTotalPay);
				TdCashReciptInf cashReciptInf = this.initCashReceiptInfWithOrderAndReceiptTypeAndMoney(tdOrder,
						TdCashReciptInf.RECEIPT_TYPE_DELIVER_CASH, amount);
				if (cashReciptInf != null) {
					ebsWithObject(cashReciptInf, INFTYPE.CASHRECEIPTINF);
				}
			}
			// 配送pos
			if (INFConstants.INF_RECEIPT_TYPE_DELIVER_INT == type && ownMoneyRecord.getPos() > 0) {
				Double amount = totalPrice * ownMoneyRecord.getPos() / allTotalPay;
				TdCashReciptInf cashReciptInf = this.initCashReceiptInfWithOrderAndReceiptTypeAndMoney(tdOrder,
						TdCashReciptInf.RECEIPT_TYPE_DELIVER_POS, amount);
				if (cashReciptInf != null) {
					ebsWithObject(cashReciptInf, INFTYPE.CASHRECEIPTINF);
				}
			}
			// 门店现金
			if (INFConstants.INF_RECEIPT_TYPE_DIYSITE_INT == type && money > 0) {
				Double amount = totalPrice * money / allTotalPay;
				TdCashReciptInf cashReciptInf = this.initCashReceiptInfWithOrderAndReceiptTypeAndMoney(tdOrder,
						TdCashReciptInf.RECEIPT_TYPE_DIYSITE_CASH, amount);
				if (cashReciptInf != null) {
					ebsWithObject(cashReciptInf, INFTYPE.CASHRECEIPTINF);
				}
			}
			// 门店pos
			if (INFConstants.INF_RECEIPT_TYPE_DIYSITE_INT == type && pos > 0) {
				Double amount = totalPrice * pos / allTotalPay;
				TdCashReciptInf cashReciptInf = this.initCashReceiptInfWithOrderAndReceiptTypeAndMoney(tdOrder,
						TdCashReciptInf.RECEIPT_TYPE_DIYSITE_POS, amount);
				if (cashReciptInf != null) {
					ebsWithObject(cashReciptInf, INFTYPE.CASHRECEIPTINF);
				}
			}
			// 新增：2016-08-25抛出门店其他还款金额
			if (INFConstants.INF_RECEIPT_TYPE_DIYSITE_INT == type && backOther > 0) {
				Double amount = totalPrice * backOther / allTotalPay;
				TdCashReciptInf cashReciptInf = this.initCashReceiptInfWithOrderAndReceiptTypeAndMoney(tdOrder,
						TdCashReciptInf.RECEIPT_TYPE_DIYSITE_OHTER, amount);
				if (cashReciptInf != null) {
					ebsWithObject(cashReciptInf, INFTYPE.CASHRECEIPTINF);
				}
			}

		}

		return null;
	}

	public TdCashReciptInf initCashReceiptInfWithOrderAndReceiptTypeAndMoney(TdOrder tdOrder, String receiptTtpe,
			Double amount) {
		TdOrderInf orderInf = tdOrderInfService.findByOrderNumber(tdOrder.getOrderNumber());
		if (orderInf == null) {
			return null;
		}

		TdDiySite diySite = tdDiySiteService.findOne(tdOrder.getDiySiteId());
		Long SobId = 0L;
		if (diySite != null) {
			SobId = diySite.getRegionId();
		}
		TdCashReciptInf cashReciptInf = new TdCashReciptInf();
		cashReciptInf.setSobId(SobId);
		cashReciptInf.setReceiptNumber(StringTools.getUniqueNoWithHeader("RC"));
		cashReciptInf.setUserid(tdOrder.getRealUserId());
		cashReciptInf.setUsername(tdOrder.getRealUserRealName());
		cashReciptInf.setUserphone(tdOrder.getRealUserUsername());
		cashReciptInf.setDiySiteCode(tdOrder.getDiySiteCode());
		cashReciptInf.setReceiptClass(StringTools.productClassStrByBoolean(tdOrder.getIsCoupon()));
		cashReciptInf.setOrderHeaderId(orderInf.getHeaderId());
		cashReciptInf.setOrderNumber(tdOrder.getOrderNumber());
		cashReciptInf.setProductType(StringTools.getProductStrByOrderNumber(tdOrder.getOrderNumber()));
		cashReciptInf.setReceiptType(receiptTtpe);
		cashReciptInf.setReceiptDate(new Date());
		cashReciptInf.setAmount(amount);
		return tdCashReciptInfService.save(cashReciptInf);
	}

	/**
	 * 订单退款
	 * 
	 * @param cashReturnNote
	 * @return
	 */
	public TdCashRefundInf initCashRefundInf(TdCashReturnNote cashReturnNote) {
		if (cashReturnNote == null) {
			return null;
		}
		TdOrderInf tdOrderInf = tdOrderInfService.findByOrderNumber(cashReturnNote.getOrderNumber());
		if (tdOrderInf == null) {
			return null;
		}

		TdReturnOrderInf returnOrderInf = tdReturnOrderInfService.findByOrderNumber(cashReturnNote.getOrderNumber());
		if (returnOrderInf == null) {
			return null;
		}

		TdCashRefundInf cashRefundInf = new TdCashRefundInf();
		cashRefundInf.setSobId(tdOrderInf.getSobId());
		cashRefundInf.setRefundNumber(StringTools.getUniqueNoWithHeader("RT"));
		cashRefundInf.setUserid(tdOrderInf.getUserid());
		cashRefundInf.setUsername(tdOrderInf.getUsername());
		cashRefundInf.setUserphone(tdOrderInf.getUserphone());
		cashRefundInf.setDiySiteCode(tdOrderInf.getDiySiteCode());
		cashRefundInf.setRefundClass("订单");
		cashRefundInf.setRtHeaderId(returnOrderInf.getRtHeaderId());
		cashRefundInf.setReturnNumber(cashReturnNote.getReturnNoteNumber());
		cashRefundInf.setOrderHeaderId(tdOrderInf.getHeaderId());
		cashRefundInf.setProductType(StringTools.getProductStrByOrderNumber(tdOrderInf.getOrderNumber()));
		cashRefundInf.setRefundType(cashReturnNote.getTypeTitle());
		cashRefundInf.setRefundDate(new Date());
		cashRefundInf.setAmount(cashReturnNote.getMoney());
		cashRefundInf.setDescription("订单退款");
		return tdCashRefundInfService.save(cashRefundInf);

	}

	/**
	 * 到店退货单退货时间表,收到货的时间
	 * 
	 * @param returnNote
	 * @return
	 */
	public TdReturnTimeInf initReturnTimeByReturnNote(TdReturnNote returnNote) {
		TdReturnOrderInf returnOrderInf = tdReturnOrderInfService.findByReturnNumber(returnNote.getReturnNumber());
		if (returnOrderInf == null) {
			return null;
		}
		TdReturnTimeInf timeInf = new TdReturnTimeInf();
		TdDiySite diySite = tdDiySiteService.findOne(returnNote.getDiySiteId());
		if (diySite != null) {
			timeInf.setSobId(diySite.getRegionId());
		}
		timeInf.setRtHeaderId(returnOrderInf.getRtHeaderId());
		timeInf.setReturnNumber(returnOrderInf.getOrderNumber());
		timeInf.setReturnDate(new Date());
		return tdReturnTimeInfService.save(timeInf);
	}

	/**
	 * 根据退货单生成相应的销退单
	 * 
	 * @param returnNote
	 *            退货单
	 * @param type
	 *            退货或者取消订单
	 */
	public void initReturnOrder(TdReturnNote returnNote, Integer type) {
		if (returnNote == null) {
			return;
		}

		TdReturnOrderInf returnOrderInf = tdReturnOrderInfService.findByReturnNumber(returnNote.getReturnNumber());
		TdOrderInf tdOrderInf = tdOrderInfService.findByOrderNumber(returnNote.getOrderNumber());
		if (returnOrderInf != null || tdOrderInf == null) {
			return;
		}

		returnOrderInf = new TdReturnOrderInf();
		TdDiySite diySite = tdDiySiteService.findOne(returnNote.getDiySiteId());
		if (diySite != null) {
			returnOrderInf.setSobId(diySite.getRegionId());
		}

		returnOrderInf.setReturnNumber(returnNote.getReturnNumber());
		returnOrderInf.setReturnDate(returnNote.getOrderTime());
		TdOrder order = tdOrderService.findByOrderNumber(returnNote.getOrderNumber());
		returnOrderInf.setOrderHeaderId(tdOrderInf.getHeaderId());
		returnOrderInf.setOrderNumber(returnNote.getOrderNumber());
		returnOrderInf.setProdectType(StringTools.getProductStrByOrderNumber(returnNote.getOrderNumber()));
		returnOrderInf.setDiySiteCode(returnNote.getDiyCode());
		// returnOrderInf.setRefundType(returnNote);
		returnOrderInf.setAuditDate(returnNote.getCheckTime());
		returnOrderInf.setRefundAmount(0.0);
		returnOrderInf.setRtFullFlag("A");
		returnOrderInf.setUserid(tdOrderInf.getUserid());
		returnOrderInf.setUsername(tdOrderInf.getUsername());
		returnOrderInf.setUserphone(tdOrderInf.getUserphone());
		returnOrderInf.setOrderTypeId(tdOrderInf.getOrderTypeId());
		returnOrderInf.setDeliverTypeTitle(tdOrderInf.getDeliverTypeTitle());

		Map<String, Object> map = tdPriceCountService.getBalanceAndCouponWithReturnNoteAndOrder(order, returnNote);
		Double returnBalance = (Double) map.get(INFConstants.kBalance);

		@SuppressWarnings("unchecked")
		List<TdCoupon> coupons = (List<TdCoupon>) map.get(INFConstants.kCouponList);
		@SuppressWarnings({ "unchecked", "unused" })
		Map<String, Double> priceDifference = (Map<String, Double>) map.get(INFConstants.kPrcieDif);

		returnOrderInf.setPrepayAmt(returnBalance);
		returnOrderInf.setAuditDate(new Date());
		if (type == INFConstants.INF_RETURN_ORDER_CANCEL_INT) {
			returnOrderInf.setStatus("订单取消");
		} else if (type == INFConstants.INF_RETURN_ORDER_SUB_INT) {
			returnOrderInf.setStatus("销售退货");
		}
		returnOrderInf.setCouponFlag('N');
		tdReturnOrderInfService.save(returnOrderInf);
		// 退货单商品
		List<TdOrderGoods> goodsList = returnNote.getReturnGoodsList();
		if (goodsList != null && goodsList.size() > 0) {
			for (TdOrderGoods tdOrderGoods : goodsList) {
				Integer usedCashProCouponCount = 0; // 电子产品券使用数量
				Integer usedProCouponCount = 0; // 产品券数量
				Double usedCashPrice = 0d; // 现金券金额
				Long goodsQuantity = tdOrderGoods.getQuantity(); // 退货数量
				Double singlePrice = tdOrderGoods.getReturnUnitPrice(); // 退货平摊单价

				if (type == INFConstants.INF_RETURN_ORDER_SUB_INT && coupons != null && coupons.size() > 0) {
					for (TdCoupon coupon : coupons) {
						if (null != coupon.getSku() && coupon.getSku().equalsIgnoreCase(tdOrderGoods.getSku())
								&& coupon.getIsBuy() != null && coupon.getIsBuy()) {
							usedCashProCouponCount++;
							usedCashPrice += coupon.getPrice();
						}
						if (null != coupon.getSku() && coupon.getSku().equalsIgnoreCase(tdOrderGoods.getSku())
								&& (coupon.getIsBuy() == null || coupon.getIsBuy() == false)) {
							usedProCouponCount++;
						}
					}
				}
				if (singlePrice == null) {
					singlePrice = 0d;
				}
				if (goodsQuantity == null) {
					goodsQuantity = 0l;
				}

				// 抵用电子产品券的商品
				if (usedCashProCouponCount + usedProCouponCount <= goodsQuantity
						&& usedCashProCouponCount + usedProCouponCount > 0) {
					TdReturnGoodsInf goodsInf = new TdReturnGoodsInf();
					goodsInf.setRtHeaderId(returnOrderInf.getRtHeaderId());
					goodsInf.setSku(tdOrderGoods.getSku());
					goodsInf.setQuantity(usedCashProCouponCount.longValue() + usedProCouponCount.longValue());
					goodsInf.setLsPrice(tdOrderGoods.getPrice());
					goodsInf.setLsSharePrice(tdOrderGoods.getPrice());
					tdReturnGoodsInfService.save(goodsInf);
				}
				if (goodsQuantity - (usedCashProCouponCount + usedProCouponCount) > 0) {
					TdReturnGoodsInf goodsInf = new TdReturnGoodsInf();
					goodsInf.setRtHeaderId(returnOrderInf.getRtHeaderId());
					goodsInf.setSku(tdOrderGoods.getSku());
					goodsInf.setQuantity(goodsQuantity - (usedCashProCouponCount + usedProCouponCount));
					goodsInf.setLsPrice(tdOrderGoods.getPrice());
					goodsInf.setLsSharePrice(singlePrice);
					tdReturnGoodsInfService.save(goodsInf);
				}
			}
		}
		// 退单的券和抵扣券
		if (type == INFConstants.INF_RETURN_ORDER_SUB_INT && coupons != null && coupons.size() > 0) {
			returnOrderInf.setCouponFlag('Y');
			tdReturnOrderInfService.save(returnOrderInf);
			for (TdCoupon tdCoupon : coupons) {
				TdReturnCouponInf returnCouponInf = new TdReturnCouponInf();
				returnCouponInf.setRtHeaderId(returnOrderInf.getRtHeaderId());
				returnCouponInf.setCouponTypeId(StringTools.coupontypeWithCoupon(tdCoupon));
				returnCouponInf.setSku(tdCoupon.getSku());
				returnCouponInf.setPrice(tdCoupon.getBuyPrice());
				returnCouponInf.setQuantity(1L);
				returnCouponInf.setAttribute1(tdCoupon.getSign());
				tdReturnCouponInfService.save(returnCouponInf);
			}
			// 2017-1-9: 双元要求不再传差价券给ebs
			// if (priceDifference != null && priceDifference.size() > 0) {
			// for (Map.Entry<String, Double> entry :
			// priceDifference.entrySet()) {
			// TdReturnCouponInf returnCouponInf = new TdReturnCouponInf();
			// returnCouponInf.setRtHeaderId(returnOrderInf.getRtHeaderId());
			// returnCouponInf.setCouponTypeId(2);
			// returnCouponInf.setSku(entry.getKey());
			// returnCouponInf.setPrice(entry.getValue());
			// returnCouponInf.setQuantity(1L);
			// tdReturnCouponInfService.save(returnCouponInf);
			// }
			// }
		}
	}

	/**
	 * 根据订单生成退货时的优惠券
	 * 
	 * @param tdOrder
	 * @param type
	 */
	public void initReturnCouponInfByOrder(TdOrder tdOrder, Integer type) {
		// type 0:取消订单 1:其他退货
		TdOrderInf tdOrderInf = tdOrderInfService.findByOrderNumber(tdOrder.getOrderNumber());
		TdReturnOrderInf returnOrderInf = tdReturnOrderInfService.findByOrderNumber(tdOrder.getOrderNumber());
		if (tdOrderInf == null || returnOrderInf == null) {
			return;
		}
		List<TdOrderCouponInf> couponInfs = tdOrderCouponInfService.findByorderHeaderId(tdOrderInf.getHeaderId());
		if (type == INFConstants.INF_RETURN_ORDER_CANCEL_INT) {
			if (couponInfs != null && couponInfs.size() > 0) {
				for (TdOrderCouponInf tdOrderCouponInf : couponInfs) {
					TdReturnCouponInf returnCouponInf = new TdReturnCouponInf();
					returnCouponInf.setRtHeaderId(returnOrderInf.getRtHeaderId());
					returnCouponInf.setCouponTypeId(tdOrderCouponInf.getCouponTypeId());
					returnCouponInf.setSku(tdOrderCouponInf.getSku());
					returnCouponInf.setPrice(tdOrderCouponInf.getPrice());
					returnCouponInf.setQuantity(1L);
					returnCouponInf.setAttribute1(tdOrderCouponInf.getAttribute1());
				}
			}
		} else if (type == INFConstants.INF_RETURN_ORDER_SUB_INT) {
			List<TdCoupon> coupons = tdCouponService.findByTypeIdAndOrderId(3l, tdOrder.getId());
			if (coupons != null && coupons.size() > 0) {
				for (TdCoupon tdCoupon : coupons) {
					TdReturnCouponInf returnCouponInf = new TdReturnCouponInf();
					returnCouponInf.setRtHeaderId(returnOrderInf.getRtHeaderId());
					returnCouponInf.setCouponTypeId(StringTools.coupontypeWithCoupon(tdCoupon));
					returnCouponInf.setSku(tdCoupon.getSku());
					returnCouponInf.setPrice(tdCoupon.getPrice());
					returnCouponInf.setQuantity(1L);
					returnCouponInf.setAttribute1(tdCoupon.getSign());
				}
			}
		}
	}

	public Boolean booleanByStr(String YN) {
		if (YN == null) {
			return false;
		}
		if (YN.equalsIgnoreCase("Y")) {
			return true;
		} else {
			return false;
		}
	}

	public String booleanStrByPayTypeId(Long payTypeId) {
		if (payTypeId == null) {
			return null;
		}
		TdPayType payType = tdPayTypeService.findOne(payTypeId);
		if (payType != null && payType.getIsOnlinePay() == true) {
			return "Y";
		}
		return "N";
	}

	/** -=-=-=-=-=-=- 生成XML -=-=-=-=-=-=- **/

	public String XmlByOrder(TdOrder tdOrder, INFTYPE type) {
		TdOrderInf tdOrderInf = tdOrderInfService.findByOrderNumber(tdOrder.getOrderNumber());
		List<String> stringList = new ArrayList<String>();
		switch (type) {
		case ORDERINF: {
			String orderInfXml = XMLWithEntity(tdOrderInf, INFTYPE.ORDERINF);
			stringList.add(orderInfXml);
			break;
		}
		case ORDERGOODSINF: {
			List<TdOrderGoodsInf> goodsInfs = tdOrderGoodsInfService.findByOrderHeaderId(tdOrderInf.getHeaderId());
			for (TdOrderGoodsInf tdOrderGoodsInf : goodsInfs) {
				String goodsInfXml = XMLWithEntity(tdOrderGoodsInf, INFTYPE.ORDERGOODSINF);
				if (org.apache.commons.lang3.StringUtils.isNotBlank(goodsInfXml)) {
					stringList.add(goodsInfXml);
				}
			}
			break;
		}
		case ORDERCOUPONINF: {
			List<TdOrderCouponInf> couponInfs = tdOrderCouponInfService.findByorderHeaderId(tdOrderInf.getHeaderId());
			for (TdOrderCouponInf tdOrderCouponInf : couponInfs) {
				String couponInfXml = XMLWithEntity(tdOrderCouponInf, INFTYPE.ORDERCOUPONINF);
				if (org.apache.commons.lang3.StringUtils.isNotBlank(couponInfXml)) {
					stringList.add(couponInfXml);
				}
			}
			break;
		}
		case CASHRECEIPTINF: {
			List<TdCashReciptInf> cashReciptInfs = tdCashReciptInfService.findByOrderHeaderId(tdOrderInf.getHeaderId());
			for (TdCashReciptInf tdCashReciptInf : cashReciptInfs) {
				String cashReciptInfXml = XMLWithEntity(tdCashReciptInf, INFTYPE.CASHRECEIPTINF);
				if (org.apache.commons.lang3.StringUtils.isNotBlank(cashReciptInfXml)) {
					stringList.add(cashReciptInfXml);
				}
			}
			break;
		}
		default:
			break;
		}
		return XmlWithTables(stringList);

	}

	/**
	 * 根据TdReturnNote生成相关xml
	 * 
	 * @param returnNote
	 * @param type
	 * @return
	 */
	public String XmlWithReturnNote(TdReturnNote returnNote, INFTYPE type) {
		if (returnNote == null || type == null) {
			return null;
		}
		TdReturnOrderInf returnOrderInf = tdReturnOrderInfService.findByReturnNumber(returnNote.getReturnNumber());
		if (returnOrderInf == null) {
			return null;
		}
		List<String> entityListStr = new ArrayList<String>();

		switch (type) {
		case RETURNORDERINF: {
			String entityStr = this.XMLWithEntity(returnOrderInf, INFTYPE.RETURNORDERINF);
			entityListStr.add(entityStr);
			break;
		}
		case RETURNGOODSINF: {
			List<TdReturnGoodsInf> returnGoodsInfs = tdReturnGoodsInfService
					.findByRtHeaderId(returnOrderInf.getRtHeaderId());
			if (returnGoodsInfs == null || returnGoodsInfs.size() < 1) {
				return null;
			}
			for (TdReturnGoodsInf tdReturnGoodsInf : returnGoodsInfs) {
				String entityStr = this.XMLWithEntity(tdReturnGoodsInf, INFTYPE.RETURNGOODSINF);
				entityListStr.add(entityStr);
			}
			break;
		}
		case RETURNCOUPONINF: {
			List<TdReturnCouponInf> returnCouponInfs = tdReturnCouponInfService
					.findByRtHeaderId(returnOrderInf.getRtHeaderId());
			if (returnCouponInfs == null || returnCouponInfs.size() < 1) {
				return null;
			}
			for (TdReturnCouponInf tdReturnCouponInf : returnCouponInfs) {
				String entityStr = this.XMLWithEntity(tdReturnCouponInf, INFTYPE.RETURNCOUPONINF);
				entityListStr.add(entityStr);
			}
			break;
		}
		default:
			break;
		}

		return XmlWithTables(entityListStr);
	}

	/**
	 * 根据对象生成完整XML
	 * 
	 * @param object
	 * @param type
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public String XmlWithObject(Object object, INFTYPE type) {
		if (object == null || type == null) {
			return null;
		}

		List<String> entityListStr = new ArrayList<String>();

		switch (type) {
		case ORDERINF: {
			if (!object.getClass().equals(TdOrderInf.class)) {
				return null;
			}
			String orderInfXml = XMLWithEntity(object, INFTYPE.ORDERINF);
			entityListStr.add(orderInfXml);
			break;
		}
		case ORDERGOODSINF: {
			if (!object.getClass().equals(ArrayList.class)) {
				return null;
			}
			List<TdOrderGoodsInf> goodsInfs = (List<TdOrderGoodsInf>) object;
			for (TdOrderGoodsInf tdOrderGoodsInf : goodsInfs) {
				String goodsInfXml = XMLWithEntity(tdOrderGoodsInf, INFTYPE.ORDERGOODSINF);
				if (org.apache.commons.lang3.StringUtils.isNotBlank(goodsInfXml)) {
					entityListStr.add(goodsInfXml);
				}
			}
			break;
		}
		case ORDERCOUPONINF: {
			if (!object.getClass().equals(ArrayList.class)) {
				return null;
			}
			List<TdOrderCouponInf> couponInfs = (List<TdOrderCouponInf>) object;
			for (TdOrderCouponInf tdOrderCouponInf : couponInfs) {
				String couponInfXml = XMLWithEntity(tdOrderCouponInf, INFTYPE.ORDERCOUPONINF);
				if (org.apache.commons.lang3.StringUtils.isNotBlank(couponInfXml)) {
					entityListStr.add(couponInfXml);
				}
			}
			break;
		}
		case CASHRECEIPTINF: {
			if (!object.getClass().equals(ArrayList.class)) {
				return null;
			}
			List<TdCashReciptInf> cashReciptInfs = (List<TdCashReciptInf>) object;
			for (TdCashReciptInf tdCashReciptInf : cashReciptInfs) {
				String cashReciptInfXml = XMLWithEntity(tdCashReciptInf, INFTYPE.CASHRECEIPTINF);
				if (org.apache.commons.lang3.StringUtils.isNotBlank(cashReciptInfXml)) {
					entityListStr.add(cashReciptInfXml);
				}
			}
			break;
		}
		case RETURNORDERINF: {
			if (!object.getClass().equals(TdReturnOrderInf.class)) {
				return null;
			}
			String entityStr = this.XMLWithEntity(object, INFTYPE.RETURNORDERINF);
			entityListStr.add(entityStr);
			break;

		}
		case RETURNGOODSINF: {
			if (!object.getClass().equals(ArrayList.class)) {
				return null;
			}
			List<TdReturnGoodsInf> returnGoodsInfs = (List<TdReturnGoodsInf>) object;
			if (returnGoodsInfs == null || returnGoodsInfs.size() < 1) {
				return null;
			}
			for (TdReturnGoodsInf tdReturnGoodsInf : returnGoodsInfs) {
				String entityStr = this.XMLWithEntity(tdReturnGoodsInf, INFTYPE.RETURNGOODSINF);
				entityListStr.add(entityStr);
			}
			break;
		}
		case RETURNCOUPONINF: {
			if (!object.getClass().equals(ArrayList.class)) {
				return null;
			}
			List<TdReturnCouponInf> returnCouponInfs = (List<TdReturnCouponInf>) object;
			if (returnCouponInfs == null || returnCouponInfs.size() < 1) {
				return null;
			}
			for (TdReturnCouponInf tdReturnCouponInf : returnCouponInfs) {
				String entityStr = this.XMLWithEntity(tdReturnCouponInf, INFTYPE.RETURNCOUPONINF);
				entityListStr.add(entityStr);
			}
			break;
		}
		default:
			break;
		}

		return XmlWithTables(entityListStr);
	}

	/**
	 * 生成xml的table
	 * 
	 * @param entity
	 * @param type
	 * @return
	 */
	public String XMLWithEntity(Object entity, INFTYPE type) {
		String xml = null;
		if (entity == null) {
			return "XMLWITHENTITY:null";
		}
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:MM:ss");
		switch (type) {
		case ORDERINF: {
			TdOrderInf object = (TdOrderInf) entity;
			String payDate = sdf.format(object.getPayDate());
			xml = "<TABLE>" + "<SOB_ID>" + object.getSobId() + "</SOB_ID>" + "<ORDER_HEADER_ID>" + object.getHeaderId()
					+ "</ORDER_HEADER_ID>" + "<ORDER_NUMBER>" + object.getOrderNumber() + "</ORDER_NUMBER>"
					+ "<ORDER_DATE>" + object.getOrderDate() + "</ORDER_DATE>" + "<MAIN_ORDER_NUMBER>"
					+ object.getMainOrderNumber() + "</MAIN_ORDER_NUMBER>" + "<PRODUCT_TYPE>" + object.getProductType()
					+ "</PRODUCT_TYPE>" + "<ORDER_TYPE_ID>" + object.getOrderTypeId() + "</ORDER_TYPE_ID>" + "<USERID>"
					+ object.getUserid() + "</USERID>" + "<USERNAME>" + object.getUsername() + "</USERNAME>"
					+ "<USERPHONE>" + object.getUserphone() + "</USERPHONE>" + "<DIY_SITE_ID>" + object.getDiySiteId()
					+ "</DIY_SITE_ID>" + "<DIY_SITE_CODE>" + object.getDiySiteCode() + "</DIY_SITE_CODE>"
					+ "<DIY_SITE_NAME>" + object.getDiySiteName() + "</DIY_SITE_NAME>" + "<DIY_SITE_PHONE>"
					+ object.getDiySitePhone() + "</DIY_SITE_PHONE>" + "<PROVINCE>" + object.getProvince()
					+ "</PROVINCE>" + "<CITY>" + object.getCity() + "</CITY>" + "<DISCTRICT>" + object.getDisctrict()
					+ "</DISCTRICT>" + "<SHIPPING_NAME>" + object.getShippingName() + "</SHIPPING_NAME>"
					+ "<SHIPPING_PHONE>" + object.getShippingPhone() + "</SHIPPING_PHONE>" + "<DELIVER_TYPE_TITLE>"
					+ object.getDeliverTypeTitle() + "</DELIVER_TYPE_TITLE>" + "<ISONLINEPAY>" + object.getIsonlinepay()
					+ "</ISONLINEPAY>" + "<PAY_TYPE>" + object.getPayType() + "</PAY_TYPE>" + "<PAY_DATE>" + payDate
					+ "</PAY_DATE>" + "<PAY_AMT>" + object.getPayAmt() + "</PAY_AMT>" + "<PREPAY_AMT>"
					+ object.getPrepayAmt() + "</PREPAY_AMT>" + "<REC_AMT>" + object.getRecAmt() + "</REC_AMT>"
					+ "<ATTRIBUTE1>" + object.getAttribute1() + "</ATTRIBUTE1>" + "<ATTRIBUTE2>"
					+ object.getAttribute2() + "</ATTRIBUTE2>" + "<ATTRIBUTE3>" + object.getAttribute3()
					+ "</ATTRIBUTE3>" + "<ATTRIBUTE4>" + object.getAttribute4() + "</ATTRIBUTE4>" + "<ATTRIBUTE5>"
					+ object.getAttribute5() + "</ATTRIBUTE5>" + "<COUPON_FLAG>" + object.getCouponFlag()
					+ "</COUPON_FLAG>" + "<DELIVERY_FEE>" + object.getDeliveryFee() + "</DELIVERY_FEE>" + "<CREDIT_AMT>"
					+ object.getCreditAmt() + "</CREDIT_AMT>" + "</TABLE>";
			break;
		}
		case ORDERGOODSINF: {
			TdOrderGoodsInf object = (TdOrderGoodsInf) entity;
			xml = "<TABLE><ORDER_HEADER_ID>" + object.getOrderHeaderId() + "</ORDER_HEADER_ID>" + "<ORDER_LINE_ID>"
					+ object.getOrderLineId() + "</ORDER_LINE_ID>" + "<GOODS_ID>" + object.getGoodsId() + "</GOODS_ID>"
					+ "<GOODS_TITLE>" + object.getGoodsTitle() + "</GOODS_TITLE>" + "<GOODS_SUB_TITLE>"
					+ object.getGoodsSubTitle() + "</GOODS_SUB_TITLE>" + "<SKU>" + object.getSku() + "</SKU>"
					+ "<QUANTITY>" + object.getQuantity() + "</QUANTITY>" + "<JX_PRICE>" + object.getJxPrice()
					+ "</JX_PRICE>" + "<LS_PRICE>" + object.getLsPrice() + "</LS_PRICE>" + "<GIFT_FLAG>"
					+ object.getGiftFlag() + "</GIFT_FLAG>" + "<PROMOTION>" + object.getPromotion() + "</PROMOTION>"
					+ "<ATTRIBUTE1>" + object.getAttribute1() + "</ATTRIBUTE1>" + "<ATTRIBUTE2>"
					+ object.getAttribute2() + "</ATTRIBUTE2>" + "<ATTRIBUTE3>" + object.getAttribute3()
					+ "</ATTRIBUTE3>" + "<ATTRIBUTE4>" + object.getAttribute4() + "</ATTRIBUTE4>" + "<ATTRIBUTE5>"
					+ object.getAttribute5() + "</ATTRIBUTE5></TABLE>";
			break;
		}
		case ORDERCOUPONINF: {
			TdOrderCouponInf object = (TdOrderCouponInf) entity;
			xml = "<TABLE><ORDER_HEADER_ID>" + object.getOrderHeaderId() + "</ORDER_HEADER_ID>" + "<LINE_ID>"
					+ object.getLineId() + "</LINE_ID>" + "<COUPON_TYPE_ID>" + object.getCouponTypeId()
					+ "</COUPON_TYPE_ID>" + "<SKU>" + object.getSku() + "</SKU>" + "<QUANTITY>" + object.getQuantity()
					+ "</QUANTITY>" + "<PRICE>" + object.getPrice() + "</PRICE>" + "<HISTORY_FLAG>"
					+ object.getHistoryFlag() + "</HISTORY_FLAG>" + "<PROMOTION>" + object.getPromotion()
					+ "</PROMOTION>"
					// 原预留字段1，现在发送券标识
					+ "<ATTRIBUTE1>" + object.getAttribute1() + "</ATTRIBUTE1>" + "<ATTRIBUTE2>"
					+ object.getAttribute2() + "</ATTRIBUTE2>" + "<ATTRIBUTE3>" + object.getAttribute3()
					+ "</ATTRIBUTE3>" + "<ATTRIBUTE4>" + object.getAttribute4() + "</ATTRIBUTE4>" + "<ATTRIBUTE5>"
					+ object.getAttribute5() + "</ATTRIBUTE5></TABLE>";
			break;
		}
		case ORDERRECEIVEINF: {
			TdOrderReceiveInf object = (TdOrderReceiveInf) entity;
			String receiveDate = sdf.format(object.getReceiveDate());
			xml = "<TABLE><SOB_ID>" + object.getSobId() + "</SOB_ID>" + "<ORDER_HEADER_ID>" + object.getHeaderId()
					+ "</ORDER_HEADER_ID>" + "<ORDER_NUMBER>" + object.getOrderNumber() + "</ORDER_NUMBER>"
					+ "<RECEIVE_DATE>" + receiveDate + "</RECEIVE_DATE>" + "<DELIVER_TYPE_TITLE>"
					+ object.getDeliverTypeTitle() + "</DELIVER_TYPE_TITLE>" + "<ATTRIBUTE1>" + object.getAttribute1()
					+ "</ATTRIBUTE1>" + "<ATTRIBUTE2>" + object.getAttribute2() + "</ATTRIBUTE2>" + "<ATTRIBUTE3>"
					+ object.getAttribute3() + "</ATTRIBUTE3>" + "<ATTRIBUTE4>" + object.getAttribute4()
					+ "</ATTRIBUTE4>" + "<ATTRIBUTE5>" + object.getAttribute5() + "</ATTRIBUTE5></TABLE>";
			break;
		}
		case RETURNORDERINF: {
			TdReturnOrderInf object = (TdReturnOrderInf) entity;
			xml = "<TABLE><SOB_ID>" + object.getSobId() + "</SOB_ID>" + "<RT_HEADER_ID>" + object.getRtHeaderId()
					+ "</RT_HEADER_ID>" + "<RETURN_NUMBER>" + object.getReturnNumber() + "</RETURN_NUMBER>"
					+ "<RETURN_DATE>" + object.getReturnDate() + "</RETURN_DATE>" + "<RT_FULL_FLAG>"
					+ object.getRtFullFlag() + "</RT_FULL_FLAG>" + "<ORDER_HEADER_ID>" + object.getOrderHeaderId()
					+ "</ORDER_HEADER_ID>" + "<ORDER_NUMBER>" + object.getOrderNumber() + "</ORDER_NUMBER>"
					+ "<PRODUCT_TYPE>" + object.getProdectType() + "</PRODUCT_TYPE>" + "<DIY_SITE_CODE>"
					+ object.getDiySiteCode() + "</DIY_SITE_CODE>" + "<REFUND_TYPE>" + object.getRefundType()
					+ "</REFUND_TYPE>" + "<AUDIT_DATE>" + object.getAuditDate() + "</AUDIT_DATE>" + "<REFUND_AMOUNT>"
					+ object.getRefundAmount() + "</REFUND_AMOUNT>" + "<PREPAY_AMT>" + object.getPrepayAmt()
					+ "</PREPAY_AMT>" + "<STATUS>" + object.getStatus() + "</STATUS>" + "<ATTRIBUTE1>"
					+ object.getAttribute1() + "</ATTRIBUTE1>" + "<ATTRIBUTE2>" + object.getAttribute2()
					+ "</ATTRIBUTE2>" + "<ATTRIBUTE3>" + object.getAttribute3() + "</ATTRIBUTE3>" + "<ATTRIBUTE4>"
					+ object.getAttribute4() + "</ATTRIBUTE4>" + "<ATTRIBUTE5>" + object.getAttribute5()
					+ "</ATTRIBUTE5>" + "<USERID>" + object.getUserid() + "</USERID>" + "<USERNAME>"
					+ object.getUsername() + "</USERNAME>" + "<USERPHONE>" + object.getUserphone() + "</USERPHONE>"
					+ "<ORDER_TYPE_ID> " + object.getOrderTypeId() + "</ORDER_TYPE_ID>" + "<DELIVER_TYPE_TITLE>"
					+ object.getDeliverTypeTitle() + "</DELIVER_TYPE_TITLE>" + "<COUPON_FLAG>" + object.getCouponFlag()
					+ "</COUPON_FLAG></TABLE>";
			break;
		}
		case RETURNGOODSINF: {
			TdReturnGoodsInf object = (TdReturnGoodsInf) entity;
			xml = "<TABLE><RT_HEADER_ID>" + object.getRtHeaderId() + "</RT_HEADER_ID>" + "<RT_LINE_ID>"
					+ object.getRtLineId() + "</RT_LINE_ID>" + "<SKU>" + object.getSku() + "</SKU>" + "<QUANTITY>"
					+ object.getQuantity() + "</QUANTITY>" + "<JX_PRICE>" + object.getJxPrice() + "</JX_PRICE>"
					+ "<LS_PRICE>" + object.getLsPrice() + "</LS_PRICE>" + "<LS_SHARE_PRICE>" + object.getLsSharePrice()
					+ "</LS_SHARE_PRICE>" + "<ATTRIBUTE1>" + object.getAttribute1() + "</ATTRIBUTE1>" + "<ATTRIBUTE2>"
					+ object.getAttribute2() + "</ATTRIBUTE2>" + "<ATTRIBUTE3>" + object.getAttribute3()
					+ "</ATTRIBUTE3>" + "<ATTRIBUTE4>" + object.getAttribute4() + "</ATTRIBUTE4>" + "<ATTRIBUTE5>"
					+ object.getAttribute5() + "</ATTRIBUTE5></TABLE>";
			break;
		}
		case RETURNCOUPONINF: {
			TdReturnCouponInf object = (TdReturnCouponInf) entity;
			xml = "<TABLE><RT_HEADER_ID>" + object.getRtHeaderId() + "</RT_HEADER_ID>" + "<LINE_ID>"
					+ object.getLineId() + "</LINE_ID>" + "<COUPON_TYPE_ID>" + object.getCouponTypeId()
					+ "</COUPON_TYPE_ID>" + "<SKU>" + object.getSku() + "</SKU>" + "<QUANTITY>" + object.getQuantity()
					+ "</QUANTITY>" + "<PRICE>" + object.getPrice() + "</PRICE>" + "<ATTRIBUTE1>"
					+ object.getAttribute1() + "</ATTRIBUTE1>" + "<ATTRIBUTE2>" + object.getAttribute2()
					+ "</ATTRIBUTE2>" + "<ATTRIBUTE3>" + object.getAttribute3() + "</ATTRIBUTE3>" + "<ATTRIBUTE4>"
					+ object.getAttribute4() + "</ATTRIBUTE4>" + "<ATTRIBUTE5>" + object.getAttribute5()
					+ "</ATTRIBUTE5></TABLE>";
			break;
		}
		case RETURNTIMEINF: {
			TdReturnTimeInf object = (TdReturnTimeInf) entity;
			String returnDate = sdf.format(object.getReturnDate());
			xml = "<TABLE><SOB_ID>" + object.getSobId() + "</SOB_ID>" + "<RT_HEADER_ID>" + object.getRtHeaderId()
					+ "</RT_HEADER_ID>" + "<RETURN_NUMBER>" + object.getReturnNumber() + "</RETURN_NUMBER>"
					+ "<RETURN_DATE>" + returnDate + "</RETURN_DATE>" + "<ATTRIBUTE1>" + object.getAttribute1()
					+ "</ATTRIBUTE1>" + "<ATTRIBUTE2>" + object.getAttribute2() + "</ATTRIBUTE2>" + "<ATTRIBUTE3>"
					+ object.getAttribute3() + "</ATTRIBUTE3>" + "<ATTRIBUTE4>" + object.getAttribute4()
					+ "</ATTRIBUTE4>" + "<ATTRIBUTE5>" + object.getAttribute5() + "</ATTRIBUTE5></TABLE>";
			break;
		}
		case CASHRECEIPTINF: {
			TdCashReciptInf object = (TdCashReciptInf) entity;
			String receiveDate = sdf.format(object.getReceiptDate());
			xml = "<TABLE><SOB_ID>" + object.getSobId() + "</SOB_ID>" + "<RECEIPT_ID>" + object.getReceiptId()
					+ "</RECEIPT_ID>" + "<RECEIPT_NUMBER>" + object.getReceiptNumber() + "</RECEIPT_NUMBER>"
					+ "<USERID>" + object.getUserid() + "</USERID>" + "<USERNAME>" + object.getUsername()
					+ "</USERNAME>" + "<USERPHONE>" + object.getUserphone() + "</USERPHONE>" + "<DIY_SITE_CODE>"
					+ object.getDiySiteCode() + "</DIY_SITE_CODE>" + "<RECEIPT_CLASS>" + object.getReceiptClass()
					+ "</RECEIPT_CLASS>" + "<ORDER_HEADER_ID>" + object.getOrderHeaderId() + "</ORDER_HEADER_ID>"
					+ "<ORDER_NUMBER>" + object.getOrderNumber() + "</ORDER_NUMBER>" + "<PRODUCT_TYPE>"
					+ object.getProductType() + "</PRODUCT_TYPE>" + "<RECEIPT_TYPE>" + object.getReceiptType()
					+ "</RECEIPT_TYPE>" + "<RECEIPT_DATE>" + receiveDate + "</RECEIPT_DATE>" + "<AMOUNT>"
					+ object.getAmount() + "</AMOUNT>" + "<ATTRIBUTE1>" + object.getAttribute1() + "</ATTRIBUTE1>"
					+ "<ATTRIBUTE2>" + object.getAttribute2() + "</ATTRIBUTE2>" + "<ATTRIBUTE3>"
					+ object.getAttribute3() + "</ATTRIBUTE3>" + "<ATTRIBUTE4>" + object.getAttribute4()
					+ "</ATTRIBUTE4>" + "<ATTRIBUTE5>" + object.getAttribute5() + "</ATTRIBUTE5></TABLE>";
			break;
		}
		case CASHREFUNDINF: {
			TdCashRefundInf object = (TdCashRefundInf) entity;
			String receiveDate = sdf.format(object.getRefundDate());
			xml = "<TABLE><SOB_ID>" + object.getSobId() + "</SOB_ID>" + "<REFUND_ID>" + object.getRefundId()
					+ "</REFUND_ID>" + "<REFUND_NUMBER>" + object.getRefundNumber() + "</REFUND_NUMBER>" + "<USERID>"
					+ object.getUserid() + "</USERID>" + "<USERNAME>" + object.getUsername() + "</USERNAME>"
					+ "<USERPHONE>" + object.getUserphone() + "</USERPHONE>" + "<DIY_SITE_CODE>"
					+ object.getDiySiteCode() + "</DIY_SITE_CODE>" + "<REFUND_CLASS>" + object.getRefundClass()
					+ "</REFUND_CLASS>" + "<RT_HEADER_ID>" + object.getRtHeaderId() + "</RT_HEADER_ID>"
					+ "<RETURN_NUMBER>" + object.getReturnNumber() + "</RETURN_NUMBER>" + "<ORDER_HEADER_ID>"
					+ object.getOrderHeaderId() + "</ORDER_HEADER_ID>" + "<PRODUCT_TYPE>" + object.getProductType()
					+ "</PRODUCT_TYPE>" + "<REFUND_TYPE>" + object.getRefundType() + "</REFUND_TYPE>" + "<REFUND_DATE>"
					+ receiveDate + "</REFUND_DATE>" + "<AMOUNT>" + object.getAmount() + "</AMOUNT>" + "<DESCRIPTION>"
					+ object.getDescription() + "</DESCRIPTION>" + "<ATTRIBUTE1>" + object.getAttribute1()
					+ "</ATTRIBUTE1>" + "<ATTRIBUTE2>" + object.getAttribute2() + "</ATTRIBUTE2>" + "<ATTRIBUTE3>"
					+ object.getAttribute3() + "</ATTRIBUTE3>" + "<ATTRIBUTE4>" + object.getAttribute4()
					+ "</ATTRIBUTE4>" + "<ATTRIBUTE5>" + object.getAttribute5() + "</ATTRIBUTE5></TABLE>";
			break;
		}
		default:
			break;
		}
		return xml;
	}

	/**
	 * 生成完整的XML
	 * 
	 * @param Tables
	 * @return
	 */
	public String XmlWithTables(List<String> Tables) {
		if (Tables == null) {
			return null;
		}
		String xmlEnd = "<ERP>";
		if (Tables.size() > 0) {
			for (String string : Tables) {
				xmlEnd += string;
			}
			xmlEnd += "</ERP>";
			xmlEnd = xmlEnd.replace("null", "");
		} else {
			xmlEnd = null;
		}

		return xmlEnd;
	}

	/** -=-=-=-=-=-=- 单个实体类发送EBS -=-=-=-=-=-=- **/

	/**
	 * 通过相关的实体进行单一的发送给EBS
	 * 
	 * @param object
	 * @param type
	 */
	public String ebsWithObject(Object object, INFTYPE type) {
		if (object == null) {
			return "对象不能为空";
		}
		String result = new String();
		switch (type) {
		case ORDERRECEIVEINF: {
			if (!object.getClass().equals(TdOrderReceiveInf.class)) {
				return "不是TdOrderReceiveInf,发送失败";
			}
			TdOrderReceiveInf orderReceiveInf = (TdOrderReceiveInf) object;
			String orderInfXML = this.XMLWithEntity(orderReceiveInf, INFTYPE.ORDERRECEIVEINF);
			orderInfXML = "<ERP>" + orderInfXML + "</ERP>";
			orderInfXML = orderInfXML.replace("null", "");
			Object[] orderInfParam = { INFConstants.INF_ORDER_RECEIVE_STR, "1", orderInfXML };
			result = this.ebsWsInvoke(orderInfParam);
			break;
		}

		case CASHRECEIPTINF: {
			if (!object.getClass().equals(TdCashReciptInf.class)) {
				return "不是TdCashReciptInf,发送失败";
			}
			TdCashReciptInf cashReceiveInf = (TdCashReciptInf) object;
			String cashReceiveInfXML = this.XMLWithEntity(cashReceiveInf, INFTYPE.CASHRECEIPTINF);
			cashReceiveInfXML = "<ERP>" + cashReceiveInfXML + "</ERP>";
			cashReceiveInfXML = cashReceiveInfXML.replace("null", "");
			Object[] orderInf = { INFConstants.INF_CASH_RECEIPTS_STR, "1", cashReceiveInfXML };
			result = this.ebsWsInvoke(orderInf);
			break;
		}

		case CASHREFUNDINF: {
			if (!object.getClass().equals(TdCashRefundInf.class)) {
				return "不是TdCashRefundInf,发送失败";
			}
			TdCashRefundInf cashRefundInf = (TdCashRefundInf) object;
			String cashRefundInfXML = this.XMLWithEntity(cashRefundInf, INFTYPE.CASHREFUNDINF);
			cashRefundInfXML = "<ERP>" + cashRefundInfXML + "</ERP>";
			cashRefundInfXML = cashRefundInfXML.replace("null", "");
			Object[] orderInf = { INFConstants.INF_CASH_REFUND_STR, "1", cashRefundInfXML };
			result = this.ebsWsInvoke(orderInf);
			break;
		}
		case RETURNTIMEINF: {
			if (!object.getClass().equals(TdReturnTimeInf.class)) {
				return "不是TdReturnTimeInf,发送失败";
			}
			String returnTimeXml = this.XMLWithEntity(object, INFTYPE.RETURNTIMEINF);
			returnTimeXml = "<ERP>" + returnTimeXml + "</ERP>";
			returnTimeXml = returnTimeXml.replace("null", "");
			Object[] orderInf = { INFConstants.INF_ORDER_RETMD_STR, "1", returnTimeXml };
			result = this.ebsWsInvoke(orderInf);
			break;
		}
		default:
			break;
		}
		return result;
	}

	/**
	 * 根据需要传送的数组向ebs的WebService发送数据
	 * 
	 * @param params
	 *            ：[接口表名,1,传送XML]
	 * @return 发送结果：空代表成功，非空是代表错误信息
	 */
	public String ebsWsInvoke(Object[] params) {
		if (!this.canSend()) {
			return "后台设置了时间限制传送";
		}
		String result = new String();
		try {
			String resultXml = (String) getCall().invoke(params);
			result = StringTools.interfaceMessage(resultXml);
		} catch (Exception e) {
			result = e.getMessage();
			this.smsSend(INFTYPE.EBSWEBSERVICE);
		}
		if (result != null) {
			this.smsSend(INFTYPE.EBSWEBSERVICE);
		}
		return result;
	}

	/** -=-=-=-=-=-=- 重传EBS -=-=-=-=-=-=- **/
	public void reSendByIdAndType(Long id, INFTYPE type) {
		switch (type) {
		case RETURNORDERINF: {
			TdReturnOrderInf returnOrderInf = tdReturnOrderInfService.findOne(id);
			TdReturnNote returnNote = tdReturnNoteService.findByReturnNumber(returnOrderInf.getReturnNumber());
			this.sendReturnOrderByAsyn(returnNote);
			break;
		}
		default:
			break;
		}
	}

	/** ----------------- 短信接口 -------------------- **/
	/** ----------------- 接口传输错误时发送 -------------------- **/
	public void smsSend(INFTYPE infType) {
		String errorService = "";
		String phone = "qwe";
		switch (infType) {
		case WMSWEBSERVICE:
			errorService = "WMS:";
			break;
		case EBSWEBSERVICE:
			errorService = "EBS:";
			break;

		default:
			break;
		}
		String info = errorService + "接口数据传输失败，请查看";
		String content = null;
		try {
			content = URLEncoder.encode(info, "GB2312");
		} catch (Exception e) {
			e.printStackTrace();
		}
		TdSetting tdSetting = tdSettingService.findTopBy();
		if (tdSetting == null || StringUtils.isBlank(tdSetting.getInfPhone())) {
			return;
		}
		phone = tdSetting.getInfPhone();
		// 成都账号
		String enCode = "C11535";
		String enPass = "2XiQj1";
		String userName = "cd001";

		String url = "http://www.mob800.com/interface/Send.aspx?enCode=" + enCode + "&enPass=" + enPass + "&userName="
				+ userName + "&mob=" + phone + "&msg=" + content;

		try {
			URL u = new URL(url);
			URLConnection connection = u.openConnection();
			HttpURLConnection httpConn = (HttpURLConnection) connection;
			httpConn.setRequestProperty("Content-type", "text/html");
			httpConn.setRequestProperty("Accept-Charset", "utf-8");
			httpConn.setRequestProperty("contentType", "utf-8");
			InputStream inputStream = null;
			InputStreamReader inputStreamReader = null;
			BufferedReader reader = null;
			StringBuffer resultBuffer = new StringBuffer();
			String tempLine = null;

			try {
				inputStream = httpConn.getInputStream();
				inputStreamReader = new InputStreamReader(inputStream);
				reader = new BufferedReader(inputStreamReader);

				while ((tempLine = reader.readLine()) != null) {
					resultBuffer.append(tempLine);
				}

			} finally {
				if (reader != null) {
					reader.close();
				}
				if (inputStreamReader != null) {
					inputStreamReader.close();
				}
				if (inputStream != null) {
					inputStream.close();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/** ----------------- EBS接口是否传送 -------------------- **/
	private Boolean canSend() {
		Date nowDate = new Date();
		TdSetting tdSetting = tdSettingService.findTopBy();
		if (tdSetting == null) {
			return true;
		}
		if (tdSetting.getStartDate() == null) {
			return true;
		} else if (tdSetting.getStartDate().getTime() > nowDate.getTime()) {
			return false;
		}

		if (tdSetting.getEndDate() == null) {
			return true;
		} else if (tdSetting.getEndDate().getTime() < nowDate.getTime()) {
			return false;
		}

		return true;
	}

}
