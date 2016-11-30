package com.ynyes.lyz.task;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.ynyes.lyz.interfaces.entity.TdCashReciptInf;
import com.ynyes.lyz.interfaces.entity.TdOrderCouponInf;
import com.ynyes.lyz.interfaces.entity.TdOrderGoodsInf;
import com.ynyes.lyz.interfaces.entity.TdOrderInf;
import com.ynyes.lyz.interfaces.entity.TdReturnCouponInf;
import com.ynyes.lyz.interfaces.entity.TdReturnGoodsInf;
import com.ynyes.lyz.interfaces.entity.TdReturnOrderInf;
import com.ynyes.lyz.interfaces.service.TdCashReciptInfService;
import com.ynyes.lyz.interfaces.service.TdInterfaceService;
import com.ynyes.lyz.interfaces.service.TdOrderCouponInfService;
import com.ynyes.lyz.interfaces.service.TdOrderGoodsInfService;
import com.ynyes.lyz.interfaces.service.TdOrderInfService;
import com.ynyes.lyz.interfaces.service.TdReturnCouponInfService;
import com.ynyes.lyz.interfaces.service.TdReturnGoodsInfService;
import com.ynyes.lyz.interfaces.service.TdReturnOrderInfService;
import com.ynyes.lyz.interfaces.utils.INFConstants;
import com.ynyes.lyz.interfaces.utils.StringTools;
import com.ynyes.lyz.interfaces.utils.EnumUtils.INFTYPE;

/**
 * 标题：IntefaceTask.java 描述：接口重传定时任务
 * 
 * @author 作者：CrazyDX
 * @version 版本：2016年9月19日上午9:07:54
 */
@Component
public class IntefaceTask {

	@Autowired
	private TdOrderInfService tdOrderInfService;

	@Autowired
	private TdOrderGoodsInfService tdOrderGoodsInfService;

	@Autowired
	private TdOrderCouponInfService tdOrderCouponInfService;

	@Autowired
	private TdInterfaceService tdInterfaceService;

	@Autowired
	private TdCashReciptInfService tdCashReciptInfService;

	@Autowired
	private TdReturnOrderInfService tdReturnOrderInfService;

	@Autowired
	private TdReturnGoodsInfService tdReturnGoodsInfService;

	@Autowired
	private TdReturnCouponInfService tdReturnCouponInfService;

	// 每天晚上一点，重传订单信息的任务
	@Scheduled(cron = "0 0 1 * * ?")
	public void sendOrderInfAgain() {
		// 查询到所有的成都的传递失败的订单信息
		List<TdOrderInf> orderInfList = tdOrderInfService.findBySendFlagAndSobId(1, 2121L);
		for (TdOrderInf item : orderInfList) {
			if (null != item) {
				if (null != item.getErrorMsg() && item.getErrorMsg().contains("重复")) {
					item.setSendFlag(0);
					item.setErrorMsg("");
				}

				if (item.getSendFlag().longValue() == 0L) {
					String orderInfXML = tdInterfaceService.XmlWithObject(item, INFTYPE.ORDERINF);
					Object[] orderInfObject = { INFConstants.INF_ORDER_STR, "1", orderInfXML };
					String resultStr = tdInterfaceService.ebsWsInvoke(orderInfObject);
					if (StringUtils.isBlank(resultStr)) {
						item.setSendFlag(0);
						tdOrderInfService.save(item);

						List<TdOrderGoodsInf> goodsInfs = tdOrderGoodsInfService
								.findByOrderHeaderId(item.getHeaderId());
						String orderGoodsInfXML = tdInterfaceService.XmlWithObject(goodsInfs, INFTYPE.ORDERGOODSINF);
						if (StringUtils.isNotBlank(orderGoodsInfXML)) {
							Object[] orderGoodsInf = { INFConstants.INF_ORDER_GOODS_STR, "1", orderGoodsInfXML };
							String resultStr1 = tdInterfaceService.ebsWsInvoke(orderGoodsInf);
							for (int i = 0; i < goodsInfs.size(); i++) {
								if (StringUtils.isBlank(resultStr1)) {
									goodsInfs.get(i).setSendFlag(0);
								} else {
									goodsInfs.get(i).setSendFlag(1);
									goodsInfs.get(i).setErrorMsg(resultStr1);
								}
							}

							tdOrderGoodsInfService.save(goodsInfs);
						}
						// 券
						List<TdOrderCouponInf> couponInfs = tdOrderCouponInfService
								.findByorderHeaderId(item.getHeaderId());
						String orderCouponInfXML = tdInterfaceService.XmlWithObject(couponInfs, INFTYPE.ORDERCOUPONINF);
						if (StringUtils.isNotBlank(orderCouponInfXML)) {
							Object[] orderCouponInf = { INFConstants.INF_ORDER_COUPON_STR, "1", orderCouponInfXML };
							String result = tdInterfaceService.ebsWsInvoke(orderCouponInf);
							for (int i = 0; i < couponInfs.size(); i++) {
								if (StringUtils.isBlank(result)) {
									couponInfs.get(i).setSendFlag(0);
								} else {
									couponInfs.get(i).setSendFlag(1);
									couponInfs.get(i).setErrorMsg(result);
								}
							}
							tdOrderCouponInfService.save(couponInfs);
						}
						// 收款
						List<TdCashReciptInf> cashReciptInfs = tdCashReciptInfService
								.findByOrderHeaderId(item.getHeaderId());
						String cashreciptInfXML = tdInterfaceService.XmlWithObject(cashReciptInfs,
								INFTYPE.CASHRECEIPTINF);
						if (StringUtils.isNotBlank(cashreciptInfXML)) {
							Object[] cashreciptInf = { INFConstants.INF_CASH_RECEIPTS_STR, "1", cashreciptInfXML };
							try {
								String object = (String) tdInterfaceService.getCall().invoke(cashreciptInf);
								String resultStr1 = StringTools.interfaceMessage(object);
								for (int i = 0; i < cashReciptInfs.size(); i++) {
									if (StringUtils.isBlank(resultStr1)) {
										cashReciptInfs.get(i).setSendFlag(0);
									} else {
										cashReciptInfs.get(i).setSendFlag(1);
										cashReciptInfs.get(i).setErrorMsg(resultStr1);
									}
								}
							} catch (Exception e) {
								e.printStackTrace();
								for (int i = 0; i < cashReciptInfs.size(); i++) {
									cashReciptInfs.get(i).setSendFlag(1);
									cashReciptInfs.get(i).setErrorMsg(e.getMessage());
								}
							}
							tdCashReciptInfService.save(cashReciptInfs);
						}
					} else {
						item.setSendFlag(1);
						item.setErrorMsg(resultStr);
					}
				}
			}
		}
	}

	// 每天晚上一点三十分，重传退货单信息的任务
	@Scheduled(cron = "0 30 1 * * ?")
	public void sendReturnOrderInfAgain() {
		List<TdReturnOrderInf> returnOrderInfService = tdReturnOrderInfService.findBySobIdAndSendFlag(2121L, 1);
		for (TdReturnOrderInf item : returnOrderInfService) {
			if (null != item) {
				if (null != item.getErrorMsg() && item.getErrorMsg().contains("重复")) {
					item.setErrorMsg("");
					item.setSendFlag(0);
					tdReturnOrderInfService.save(item);
					continue;
				}

				String returnOrderInfXml = tdInterfaceService.XmlWithObject(item, INFTYPE.RETURNORDERINF);
				if (returnOrderInfXml != null) {
					Object[] orderInf = { INFConstants.INF_RT_ORDER_STR, "1", returnOrderInfXml };
					String result = tdInterfaceService.ebsWsInvoke(orderInf);
					if (StringUtils.isBlank(result)) {
						item.setSendFlag(0);
						// 行
						List<TdReturnGoodsInf> returnGoodsInfs = tdReturnGoodsInfService
								.findByRtHeaderId(item.getRtHeaderId());
						String returnGoodsInfXml = tdInterfaceService.XmlWithObject(returnGoodsInfs,
								INFTYPE.RETURNGOODSINF);
						if (returnGoodsInfXml != null) {
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
								.findByRtHeaderId(item.getRtHeaderId());
						String returnCouponInfXml = tdInterfaceService.XmlWithObject(returnCouponInfs,
								INFTYPE.RETURNCOUPONINF);
						if (returnCouponInfXml != null) {
							Object[] orderCouponInf = { INFConstants.INF_RT_ORDER_COUPONS_STR, "1",
									returnCouponInfXml };
							String reuslt = tdInterfaceService.ebsWsInvoke(orderCouponInf);
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
					} else {
						item.setSendFlag(1);
						item.setErrorMsg(result);
					}
					tdReturnOrderInfService.save(item);
				}
			}
		}
	}

}
