package com.ynyes.lyz.interfaces.controller;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

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
import com.ynyes.lyz.interfaces.service.TdCashReciptInfService;
import com.ynyes.lyz.interfaces.service.TdCashRefundInfService;
import com.ynyes.lyz.interfaces.service.TdInterfaceService;
import com.ynyes.lyz.interfaces.service.TdOrderCouponInfService;
import com.ynyes.lyz.interfaces.service.TdOrderGoodsInfService;
import com.ynyes.lyz.interfaces.service.TdOrderInfService;
import com.ynyes.lyz.interfaces.service.TdOrderReceiveInfService;
import com.ynyes.lyz.interfaces.service.TdReturnCouponInfService;
import com.ynyes.lyz.interfaces.service.TdReturnGoodsInfService;
import com.ynyes.lyz.interfaces.service.TdReturnOrderInfService;
import com.ynyes.lyz.interfaces.service.TdReturnTimeInfService;
import com.ynyes.lyz.interfaces.utils.EnumUtils.INFTYPE;
import com.ynyes.lyz.interfaces.utils.INFConstants;

@Controller
@RequestMapping(value = "/ebs")
public class TdEbsResendController {
	@Autowired
	private TdInterfaceService tdInterfaceService;

	@Autowired
	private TdOrderInfService tdOrderInfService;

	@Autowired
	private TdOrderGoodsInfService tdOrderGoodsInfService;

	@Autowired
	private TdOrderCouponInfService tdOrderCouponInfService;

	@Autowired
	private TdCashRefundInfService tdCashRefundInfService;

	@Autowired
	private TdCashReciptInfService tdCashReciptInfService;

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

	/**
	 * 收款重传
	 * 
	 * @param orderNumber
	 *            分单号
	 */
	@RequestMapping(value = "/cashReceipt")
	public void resendCashReceipt(String orderNumber) {
		if (orderNumber == null) {
			return;
		}
		List<TdCashReciptInf> cashReciptInfs = tdCashReciptInfService.findByOrderNumber(orderNumber);
		if (cashReciptInfs != null && cashReciptInfs.size() > 0) {
			for (int i = 0; i < cashReciptInfs.size(); i++) {
				TdCashReciptInf cashReciptInf = cashReciptInfs.get(i);
				if (cashReciptInf.getSendFlag() != null && cashReciptInf.getSendFlag() == 0) {
					continue;
				}
				String resultStr = tdInterfaceService.ebsWithObject(cashReciptInf, INFTYPE.CASHRECEIPTINF);
				if (StringUtils.isBlank(resultStr)) {
					cashReciptInf.setSendFlag(0);
				} else {
					cashReciptInf.setSendFlag(0);
					cashReciptInf.setErrorMsg(resultStr);
				}
			}
			tdCashReciptInfService.save(cashReciptInfs);

		}
	}

	/**
	 * 退款重传
	 * 
	 * @param returnNumber
	 *            退货单号
	 */
	@RequestMapping(value = "/cashRefund")
	public void resendCashRefund(String returnNumber) {
		if (returnNumber == null) {
			return;
		}
		List<TdCashRefundInf> cashRefundInfs = tdCashRefundInfService.findByReturnNumber(returnNumber);
		if (cashRefundInfs != null && cashRefundInfs.size() > 0) {
			for (int i = 0; i < cashRefundInfs.size(); i++) {
				TdCashRefundInf cashRefundInf = cashRefundInfs.get(i);
				if (cashRefundInf.getSendFlag() != null && cashRefundInf.getSendFlag() == 0) {
					continue;
				}
				String resultStr = tdInterfaceService.ebsWithObject(cashRefundInf, INFTYPE.CASHREFUNDINF);
				if (StringUtils.isBlank(resultStr)) {
					cashRefundInf.setSendFlag(0);
				} else {
					cashRefundInf.setSendFlag(0);
					cashRefundInf.setErrorMsg(resultStr);
				}
			}
			tdCashRefundInfService.save(cashRefundInfs);
		}
	}

	/**
	 * 重传订单
	 * 
	 * @param orderNumber
	 *            分单号
	 */
	@RequestMapping(value = "/order")
	public void resendOrder(String orderNumber) {
		TdOrderInf orderInf = tdOrderInfService.findByOrderNumber(orderNumber);
		if (orderInf == null || (orderInf.getSendFlag() != null && orderInf.getSendFlag() == 0)) {
			return;
		}
		Boolean isOrderInfSucceed = false;

		String orderInfXML = tdInterfaceService.XmlWithObject(orderInf, INFTYPE.ORDERINF);
		Object[] orderInfObject = { INFConstants.INF_ORDER_STR, "1", orderInfXML };
		String resultStr = tdInterfaceService.ebsWsInvoke(orderInfObject);
		if (org.apache.commons.lang3.StringUtils.isBlank(resultStr)) {
			isOrderInfSucceed = true;
			orderInf.setSendFlag(0);
		} else {
			orderInf.setSendFlag(1);
			orderInf.setErrorMsg(resultStr);
		}
		tdOrderInfService.save(orderInf);
		// 商品
		List<TdOrderGoodsInf> goodsInfs = tdOrderGoodsInfService.findByOrderHeaderId(orderInf.getHeaderId());
		String orderGoodsInfXML = tdInterfaceService.XmlWithObject(goodsInfs, INFTYPE.ORDERGOODSINF);
		if (org.apache.commons.lang3.StringUtils.isNotBlank(orderGoodsInfXML) && isOrderInfSucceed) {
			Object[] orderGoodsInf = { INFConstants.INF_ORDER_GOODS_STR, "1", orderGoodsInfXML };
			String resultStr1 = tdInterfaceService.ebsWsInvoke(orderGoodsInf);
			for (int i = 0; i < goodsInfs.size(); i++) {
				if (org.apache.commons.lang3.StringUtils.isBlank(resultStr1)) {
					goodsInfs.get(i).setSendFlag(0);
				} else {
					goodsInfs.get(i).setSendFlag(1);
					goodsInfs.get(i).setErrorMsg(resultStr1);
				}
			}

			tdOrderGoodsInfService.save(goodsInfs);
		}
		// 券
		List<TdOrderCouponInf> couponInfs = tdOrderCouponInfService.findByorderHeaderId(orderInf.getHeaderId());
		String orderCouponInfXML = tdInterfaceService.XmlWithObject(couponInfs, INFTYPE.ORDERCOUPONINF);
		if (org.apache.commons.lang3.StringUtils.isNotBlank(orderCouponInfXML) && isOrderInfSucceed) {
			Object[] orderCouponInf = { INFConstants.INF_ORDER_COUPON_STR, "1", orderCouponInfXML };
			String result = tdInterfaceService.ebsWsInvoke(orderCouponInf);
			for (int i = 0; i < couponInfs.size(); i++) {
				if (org.apache.commons.lang3.StringUtils.isBlank(result)) {
					couponInfs.get(i).setSendFlag(0);
				} else {
					couponInfs.get(i).setSendFlag(1);
					couponInfs.get(i).setErrorMsg(result);
				}
			}
			tdOrderCouponInfService.save(couponInfs);
		}
	}

	/**
	 * 重传订单商品
	 * 
	 * @param orderNumber
	 */
	@RequestMapping(value = "/orderGoods")
	public void resendOrderGoods(String orderNumber) {
		TdOrderInf orderInf = tdOrderInfService.findByOrderNumber(orderNumber);
		if (orderInf == null) {
			return;
		}
		List<TdOrderGoodsInf> goodsInfs = tdOrderGoodsInfService.findByOrderHeaderId(orderInf.getHeaderId());
		String orderGoodsInfXML = tdInterfaceService.XmlWithObject(goodsInfs, INFTYPE.ORDERGOODSINF);
		if (org.apache.commons.lang3.StringUtils.isNotBlank(orderGoodsInfXML)
				&& (goodsInfs.get(0).getSendFlag() == null || goodsInfs.get(0).getSendFlag() == 1)) {
			Object[] orderGoodsInf = { INFConstants.INF_ORDER_GOODS_STR, "1", orderGoodsInfXML };
			String resultStr1 = tdInterfaceService.ebsWsInvoke(orderGoodsInf);
			for (int i = 0; i < goodsInfs.size(); i++) {
				if (org.apache.commons.lang3.StringUtils.isBlank(resultStr1)) {
					goodsInfs.get(i).setSendFlag(0);
				} else {
					goodsInfs.get(i).setSendFlag(1);
					goodsInfs.get(i).setErrorMsg(resultStr1);
				}
			}

			tdOrderGoodsInfService.save(goodsInfs);
		}
	}

	/**
	 * 重传订单券
	 * 
	 * @param orderNumber
	 *            分单号
	 */
	@RequestMapping(value = "/orderCoupon")
	public void resendOrderCoupon(String orderNumber) {
		TdOrderInf orderInf = tdOrderInfService.findByOrderNumber(orderNumber);
		if (orderInf == null) {
			return;
		}
		List<TdOrderCouponInf> couponInfs = tdOrderCouponInfService.findByorderHeaderId(orderInf.getHeaderId());
		String orderCouponInfXML = tdInterfaceService.XmlWithObject(couponInfs, INFTYPE.ORDERCOUPONINF);
		if (org.apache.commons.lang3.StringUtils.isNotBlank(orderCouponInfXML)
				&& (couponInfs.get(0).getSendFlag() == null || couponInfs.get(0).getSendFlag() == 1)) {
			Object[] orderCouponInf = { INFConstants.INF_ORDER_COUPON_STR, "1", orderCouponInfXML };
			String result = tdInterfaceService.ebsWsInvoke(orderCouponInf);
			for (int i = 0; i < couponInfs.size(); i++) {
				if (org.apache.commons.lang3.StringUtils.isBlank(result)) {
					couponInfs.get(i).setSendFlag(0);
				} else {
					couponInfs.get(i).setSendFlag(1);
					couponInfs.get(i).setErrorMsg(result);
				}
			}
			tdOrderCouponInfService.save(couponInfs);
		}
	}

	/**
	 * 重传到店自提单收货时间表
	 * 
	 * @param orderNumber
	 */
	@RequestMapping(value = "/orderReceipt")
	public void resendOrderReceipt(String orderNumber) {
		List<TdOrderReceiveInf> orderReceiveInfs = tdOrderReceiveInfService.findByOrderNumber(orderNumber);
		if (orderReceiveInfs != null && orderReceiveInfs.size() > 0) {
			for (int i = 0; i < orderReceiveInfs.size(); i++) {
				TdOrderReceiveInf orderReceiveInf = orderReceiveInfs.get(i);
				if (orderReceiveInf.getSendFlag() == null || orderReceiveInf.getSendFlag() == 1) {
					String resultStr = tdInterfaceService.ebsWithObject(orderReceiveInf, INFTYPE.ORDERRECEIVEINF);
					if (org.apache.commons.lang3.StringUtils.isBlank(resultStr)) {
						orderReceiveInf.setSendFlag(0);
					} else {
						orderReceiveInf.setSendFlag(1);
						orderReceiveInf.setErrorMsg(resultStr);
					}
				}
			}
			tdOrderReceiveInfService.save(orderReceiveInfs);
		}
	}

	/**
	 * 重传退货单
	 * 
	 * @param orderNumber
	 *            退货单号
	 */
	@RequestMapping(value = "/returnOrder")
	public void resendReturnOrder(String returnNumber) {
		TdReturnOrderInf returnOrderInf = tdReturnOrderInfService.findByReturnNumber(returnNumber);
		if (returnOrderInf == null || returnOrderInf.getSendFlag() == null || returnOrderInf.getSendFlag() == 0) {
			return;
		}
		Boolean isSendSuccess = false;
		// 退单头
		String returnOrderInfXml = tdInterfaceService.XmlWithObject(returnOrderInf, INFTYPE.RETURNORDERINF);
		if (returnOrderInfXml != null) {
			Object[] orderInf = { INFConstants.INF_RT_ORDER_STR, "1", returnOrderInfXml };
			String result = tdInterfaceService.ebsWsInvoke(orderInf);
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
		String returnGoodsInfXml = tdInterfaceService.XmlWithObject(returnGoodsInfs, INFTYPE.RETURNGOODSINF);
		if (returnGoodsInfXml != null && isSendSuccess) {
			Object[] orderGoodsInf = { INFConstants.INF_RT_ORDER_GOODS_STR, "1", returnGoodsInfXml };
			String reuslt = tdInterfaceService.ebsWsInvoke(orderGoodsInf);
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
		String returnCouponInfXml = tdInterfaceService.XmlWithObject(returnCouponInfs, INFTYPE.RETURNCOUPONINF);
		if (returnCouponInfXml != null && isSendSuccess) {
			Object[] orderInf = { INFConstants.INF_RT_ORDER_COUPONS_STR, "1", returnCouponInfXml };
			String reuslt = tdInterfaceService.ebsWsInvoke(orderInf);
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

	/**
	 * 重传退货单商品
	 * 
	 * @param returnNumber
	 *            退货单号
	 */
	@RequestMapping(value = "/returnGoods")
	public void resendReturnGoods(String returnNumber) {
		TdReturnOrderInf returnOrderInf = tdReturnOrderInfService.findByReturnNumber(returnNumber);
		if (returnOrderInf == null) {
			return;
		}
		List<TdReturnGoodsInf> returnGoodsInfs = tdReturnGoodsInfService
				.findByRtHeaderId(returnOrderInf.getRtHeaderId());
		String returnGoodsInfXml = tdInterfaceService.XmlWithObject(returnGoodsInfs, INFTYPE.RETURNGOODSINF);
		if (returnGoodsInfXml != null
				&& (returnGoodsInfs.get(0).getSendFlag() == null || returnGoodsInfs.get(0).getSendFlag() == 1)) {
			Object[] orderGoodsInf = { INFConstants.INF_RT_ORDER_GOODS_STR, "1", returnGoodsInfXml };
			String reuslt = tdInterfaceService.ebsWsInvoke(orderGoodsInf);
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
	}

	/**
	 * 重传退货单优惠券
	 * 
	 * @param returnNumber
	 *            退货单号
	 */
	@RequestMapping(value = "/returnCoupon")
	public void resendReturnCoupon(String returnNumber) {
		TdReturnOrderInf returnOrderInf = tdReturnOrderInfService.findByReturnNumber(returnNumber);
		if (returnOrderInf == null) {
			return;
		}

		List<TdReturnCouponInf> returnCouponInfs = tdReturnCouponInfService
				.findByRtHeaderId(returnOrderInf.getRtHeaderId());
		String returnCouponInfXml = tdInterfaceService.XmlWithObject(returnCouponInfs, INFTYPE.RETURNCOUPONINF);
		if (returnCouponInfXml != null
				&& (returnCouponInfs.get(0).getSendFlag() == null || returnCouponInfs.get(0).getSendFlag() == 1)) {
			Object[] orderInf = { INFConstants.INF_RT_ORDER_COUPONS_STR, "1", returnCouponInfXml };
			String reuslt = tdInterfaceService.ebsWsInvoke(orderInf);
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

	/**
	 * 重传退货单时间
	 * 
	 * @param returnNumber
	 *            退货单号
	 */
	@RequestMapping(value = "/returnTime")
	public void resendReturnTime(String returnNumber) {
		List<TdReturnTimeInf> returnTimeInfs = tdReturnTimeInfService.findByReturnNumber(returnNumber);
		if (returnTimeInfs != null && returnTimeInfs.size() > 0) {
			for (int i = 0; i < returnTimeInfs.size(); i++) {
				TdReturnTimeInf returnTimeInf = returnTimeInfs.get(i);
				if (returnTimeInf.getSendFlag() == null || returnTimeInf.getSendFlag() == 1) {
					String resultStr = tdInterfaceService.ebsWithObject(returnTimeInf, INFTYPE.RETURNTIMEINF);
					if (org.apache.commons.lang3.StringUtils.isBlank(resultStr)) {
						returnTimeInf.setSendFlag(0);
					} else {
						returnTimeInf.setSendFlag(1);
						returnTimeInf.setErrorMsg(resultStr);
					}
				}
			}
			tdReturnTimeInfService.save(returnTimeInfs);
		}
	}

}
