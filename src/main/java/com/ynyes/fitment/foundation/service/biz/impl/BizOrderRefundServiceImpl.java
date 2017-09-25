package com.ynyes.fitment.foundation.service.biz.impl;

import static org.apache.commons.lang3.StringUtils.leftPad;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

import org.apache.commons.lang3.ArrayUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ynyes.fitment.core.constant.AuditStatus;
import com.ynyes.fitment.foundation.entity.FitCompany;
import com.ynyes.fitment.foundation.entity.FitEmployee;
import com.ynyes.fitment.foundation.entity.FitOrderRefund;
import com.ynyes.fitment.foundation.entity.FitOrderRefundGoods;
import com.ynyes.fitment.foundation.service.FitCompanyService;
import com.ynyes.fitment.foundation.service.FitOrderRefundGoodsService;
import com.ynyes.fitment.foundation.service.FitOrderRefundService;
import com.ynyes.fitment.foundation.service.biz.BizCreditChangeLogService;
import com.ynyes.fitment.foundation.service.biz.BizOrderRefundService;
import com.ynyes.lyz.entity.TdGoods;
import com.ynyes.lyz.entity.TdOrder;
import com.ynyes.lyz.entity.TdOrderGoods;
import com.ynyes.lyz.entity.TdReturnNote;
import com.ynyes.lyz.interfaces.entity.TdOrderInf;
import com.ynyes.lyz.interfaces.entity.TdReturnGoodsInf;
import com.ynyes.lyz.interfaces.entity.TdReturnOrderInf;
import com.ynyes.lyz.interfaces.service.TdInterfaceService;
import com.ynyes.lyz.interfaces.service.TdOrderInfService;
import com.ynyes.lyz.interfaces.service.TdReturnGoodsInfService;
import com.ynyes.lyz.interfaces.service.TdReturnOrderInfService;
import com.ynyes.lyz.interfaces.utils.INFConstants;
import com.ynyes.lyz.interfaces.utils.StringTools;
import com.ynyes.lyz.service.TdCommonService;
import com.ynyes.lyz.service.TdDiySiteInventoryService;
import com.ynyes.lyz.service.TdGoodsService;
import com.ynyes.lyz.service.TdOrderGoodsService;
import com.ynyes.lyz.service.TdOrderService;
import com.ynyes.lyz.service.TdReturnNoteService;

@Service
@Transactional
public class BizOrderRefundServiceImpl implements BizOrderRefundService {

	@Autowired
	private TdOrderService tdOrderService;

	@Autowired
	private FitOrderRefundService fitOrderRefundService;

	@Autowired
	private FitOrderRefundGoodsService fitOrderRefundGoodsService;

	@Autowired
	private FitCompanyService fitCompanyService;

	@Autowired
	private TdDiySiteInventoryService tdDiySiteInventoryService;

	@Autowired
	private TdGoodsService tdGoodsService;

	@Autowired
	private BizCreditChangeLogService bizCreditChangeLogService;

	@Autowired
	private TdCommonService tdCommonService;

	@Autowired
	private TdOrderGoodsService tdOrderGoodsService;

	@Autowired
	private TdReturnNoteService tdReturnNoteService;

	@Autowired
	private TdInterfaceService tdInterfaceService;

	@Autowired
	private TdOrderInfService tdOrderInfService;

	@Autowired
	private TdReturnOrderInfService tdReturnOrderInfService;

	@Autowired
	private TdReturnGoodsInfService tdReturnGoodsInfService;

	@Override
	public FitOrderRefund init(String receiverName, String receiverMobile, String receiverAddress,
			String receiverAddressDetail, FitEmployee employee, String data, Long orderId) throws Exception {
		if (null == data || data.length() == 0 || null == employee || null == orderId) {
			return null;
		} else {
			List<FitOrderRefundGoods> orderGoodsList = new ArrayList<>();
			Double totalCredit = 0d;
			String[] strings = data.split(",");
			if (ArrayUtils.isEmpty(strings)) {
				return null;
			} else {
				for (String single : strings) {
					String[] infos = single.split("-");
					Long goodsId = Long.parseLong(infos[0]);
					Long quantity = Long.parseLong(infos[1]);
					Double unit = Double.parseDouble(infos[2]);

					FitOrderRefundGoods orderRefundGoods = this.fitOrderRefundGoodsService.init(goodsId, quantity,
							unit);
					orderGoodsList.add(orderRefundGoods);
					totalCredit += orderRefundGoods.getTotalPrice();
				}

				TdOrder order = this.tdOrderService.findOne(orderId);

				FitOrderRefund orderRefund = new FitOrderRefund();
				orderRefund.setEmployeeId(employee.getId());
				orderRefund.setEmployeeMobile(employee.getMobile());
				orderRefund.setEmployeeName(employee.getName());
				orderRefund.setCompanyId(employee.getCompanyId());
				orderRefund.setCompanyName(employee.getCompanyTitle());
				orderRefund.setOrderNumber(order.getOrderNumber());
				orderRefund.setOrderId(order.getId());
				orderRefund.setRefundTime(new Date());
				orderRefund.setCredit(totalCredit);

				orderRefund.setReceiverName(receiverName);
				orderRefund.setReceiverMobile(receiverMobile);
				orderRefund.setReceiverAddress(receiverAddressDetail);
				orderRefund.setReceiverAddressDetail(receiverAddressDetail);

				orderRefund.setOrderGoodsList(orderGoodsList);

				if (employee.getIsMain()) {
					orderRefund.setAuditorId(employee.getId());
					orderRefund.setAuditorMobile(employee.getMobile());
					orderRefund.setAuditorName(employee.getName());
					orderRefund.setAuditTime(new Date());
					orderRefund.setStatus(AuditStatus.AUDIT_SUCCESS);

					order.setIsRefund(true);
					this.tdOrderService.save(order);

					this.actWithRefund(orderRefund);

				} else {
					orderRefund.setStatus(AuditStatus.WAIT_AUDIT);
				}

				return this.fitOrderRefundService.save(orderRefund);
			}
		}
	}

	private void actWithRefund(FitOrderRefund orderRefund) throws Exception {
		this.send(orderRefund);
	}

	@Override
	public void refundSomething(FitOrderRefund orderRefund) {
		Long companyId = orderRefund.getCompanyId();
		FitCompany company = this.fitCompanyService.findOne(companyId);
		this.repayInventory(orderRefund, company);
		try {
			this.repayCredit(orderRefund, company);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	private void repayInventory(FitOrderRefund orderRefund, FitCompany company) {
		List<FitOrderRefundGoods> orderGoodsList = orderRefund.getOrderGoodsList();
		for (FitOrderRefundGoods orderGoods : orderGoodsList) {
			Long quantity = orderGoods.getQuantity();
			Long goodsId = orderGoods.getGoodsId();
			TdGoods goods = tdGoodsService.findOne(goodsId);
			tdDiySiteInventoryService.repayCityInventory(company.getSobId(), goods, quantity,
					orderRefund.getOrderNumber(), orderRefund.getEmployeeMobile().replace("FIT", ""), "配送退货");
		}
	}

	private void repayCredit(FitOrderRefund orderRefund, FitCompany company) throws Exception {
		company.setCredit(company.getCredit() + orderRefund.getCredit());
		this.fitCompanyService.save(company);
		bizCreditChangeLogService.refundLog(company, orderRefund);
	}

	private void send(FitOrderRefund orderRefund) {
		TdReturnNote returnNote = this.MakeReturnNote(orderRefund);
		tdCommonService.sendBackMsgToWMS(returnNote);
	}

	private TdReturnNote MakeReturnNote(FitOrderRefund orderRefund) {
		TdReturnNote returnNote = new TdReturnNote();

		// 退货单编号
		Date current = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmm");
		String curStr = sdf.format(current);
		Random random = new Random();

		returnNote.setReturnNumber("T" + curStr + leftPad(Integer.toString(random.nextInt(999)), 3, "0"));

		// 添加订单信息
		returnNote.setOrderNumber(orderRefund.getOrderNumber());

		// add MDJ
		returnNote.setShoppingAddress(orderRefund.getReceiverAddressDetail() + "(" + orderRefund.getReceiverName() + ":"
				+ orderRefund.getReceiverMobile() + ")");
		returnNote.setSellerRealName(orderRefund.getAuditorName());
		// end add MDJ

		// 支付方式
		returnNote.setPayTypeId(6L);
		returnNote.setPayTypeTitle("其他");
		// 门店信息
		if (null != orderRefund.getCompanyId()) {
			FitCompany company = this.fitCompanyService.findOne(orderRefund.getCompanyId());
			returnNote.setDiySiteId(company.getId());
			returnNote.setDiyCode(company.getCode());
			returnNote.setDiySiteTel(orderRefund.getAuditorMobile());
			returnNote.setDiySiteTitle(company.getName());
			returnNote.setDiySiteAddress(orderRefund.getReceiverAddressDetail() + "(" + orderRefund.getReceiverName()
					+ ":" + orderRefund.getReceiverMobile() + ")");
		}

		// 退货信息
		returnNote.setUsername(orderRefund.getEmployeeMobile());
		returnNote.setRemarkInfo("用户退货");

		returnNote.setTurnType(2L);

		returnNote.setStatusId(5L);

		returnNote.setDeliverTypeTitle("送货上门");

		returnNote.setOrderTime(new Date());

		returnNote.setTurnPrice(orderRefund.getCredit());
		List<TdOrderGoods> orderGoodsList = new ArrayList<>();

		// 商品
		if (null != orderRefund.getOrderGoodsList()) {
			for (FitOrderRefundGoods goods : orderRefund.getOrderGoodsList()) {

				TdGoods oGoods = tdGoodsService.findOne(goods.getGoodsId());

				TdOrderGoods orderGoods = new TdOrderGoods();

				orderGoods.setBrandId(oGoods.getBrandId());
				orderGoods.setBrandTitle(oGoods.getBrandTitle());
				orderGoods.setGoodsId(oGoods.getId());
				orderGoods.setGoodsSubTitle(oGoods.getSubTitle());
				orderGoods.setSku(oGoods.getCode());
				orderGoods.setGoodsCoverImageUri(oGoods.getCoverImageUri());
				orderGoods.setGoodsColor("");
				orderGoods.setGoodsCapacity("");
				orderGoods.setGoodsVersion("");
				orderGoods.setGoodsSaleType(0);
				orderGoods.setGoodsTitle(oGoods.getTitle());

				orderGoods.setPrice(goods.getPrice());
				orderGoods.setRealPrice(goods.getRealPrice());
				orderGoods.setQuantity(goods.getQuantity());

				orderGoods.setDeliveredQuantity(goods.getQuantity());
				orderGoods.setPoints(0L);

				Double unit = orderGoods.getRealPrice();

				if (null == unit || 0.00 == unit.doubleValue()) {
					String orderNumber = returnNote.getOrderNumber();
					if (orderNumber.contains("HRFIT")) {
						orderNumber = orderNumber.replace("HRFIT", "XNFIT");
					} else if (orderNumber.contains("YRFIT")) {
						orderNumber = orderNumber.replace("YRFIT", "XNFIT");
					} else if (orderNumber.contains("LYZFIT")) {
						orderNumber = orderNumber.replace("LYZFIT", "XNFIT");
					} else if (orderNumber.contains("HR")) {
						orderNumber = orderNumber.replace("HR", "XN");
					} else if (orderNumber.contains("YR")) {
						orderNumber = orderNumber.replace("YR", "XN");
					} else if (orderNumber.contains("LYZ")) {
						orderNumber = orderNumber.replace("LYZ", "XN");
					}
				}

				orderGoods.setReturnUnitPrice(unit);
				// tdOrderGoodsService.save(orderGoods);
				// 添加商品信息
				orderGoodsList.add(orderGoods);

				// 订单商品设置退货为True
				// oGoods.setIsReturnApplied(true);
				// // 更新订单商品信息是否退货状态
				// tdOrderGoodsService.save(oGoods);
			}
		}

		returnNote.setReturnGoodsList(orderGoodsList);
		tdOrderGoodsService.save(orderGoodsList);
		this.initReturnOrder(returnNote, INFConstants.INF_RETURN_ORDER_SUB_INT,
				fitCompanyService.findOne(orderRefund.getCompanyId()).getSobId());
		tdInterfaceService.sendReturnOrderByAsyn(returnNote);
		return tdReturnNoteService.save(returnNote);
	}

	private void initReturnOrder(TdReturnNote returnNote, Integer type, Long sobId) {
		if (returnNote == null) {
			return;
		}

		TdReturnOrderInf returnOrderInf = tdReturnOrderInfService.findByReturnNumber(returnNote.getReturnNumber());
		TdOrderInf tdOrderInf = tdOrderInfService.findByOrderNumber(returnNote.getOrderNumber());
		if (returnOrderInf != null || tdOrderInf == null) {
			return;
		}

		returnOrderInf = new TdReturnOrderInf();
		returnOrderInf.setSobId(sobId);
		returnOrderInf.setReturnNumber(returnNote.getReturnNumber());
		returnOrderInf.setReturnDate(returnNote.getOrderTime());
		returnOrderInf.setOrderHeaderId(tdOrderInf.getHeaderId());
		returnOrderInf.setOrderNumber(returnNote.getOrderNumber());
		returnOrderInf.setProdectType(StringTools.getProductStrByOrderNumber(returnNote.getOrderNumber()));
		returnOrderInf.setDiySiteCode(returnNote.getDiyCode());
		returnOrderInf.setAuditDate(returnNote.getCheckTime());
		returnOrderInf.setRefundAmount(0.0);
		returnOrderInf.setRtFullFlag("A");
		returnOrderInf.setUserid(tdOrderInf.getUserid());
		returnOrderInf.setUsername(tdOrderInf.getUsername());
		returnOrderInf.setUserphone(tdOrderInf.getUserphone());
		returnOrderInf.setOrderTypeId(tdOrderInf.getOrderTypeId());
		returnOrderInf.setDeliverTypeTitle(tdOrderInf.getDeliverTypeTitle());
		returnOrderInf.setAttribute1(returnNote.getTurnPrice() + "");

		returnOrderInf.setPrepayAmt(0d);
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
				Long goodsQuantity = tdOrderGoods.getQuantity(); // 退货数量
				Double singlePrice = tdOrderGoods.getReturnUnitPrice(); // 退货平摊单价

				if (singlePrice == null) {
					singlePrice = 0d;
				}
				if (goodsQuantity == null) {
					goodsQuantity = 0l;
				}

				TdReturnGoodsInf goodsInf = new TdReturnGoodsInf();
				goodsInf.setRtHeaderId(returnOrderInf.getRtHeaderId());
				goodsInf.setSku(tdOrderGoods.getSku());
				goodsInf.setQuantity(goodsQuantity);
				goodsInf.setLsPrice(tdOrderGoods.getRealPrice());
				goodsInf.setLsSharePrice(singlePrice);
				tdReturnGoodsInfService.save(goodsInf);
			}
		}
	}

	@Override
	public Boolean validateRepeatAudit(Long orderId, AuditStatus status) throws Exception {
		return this.fitOrderRefundService.countByOrderIdAndStatus(orderId, status) > 0L;
	}

	@Override
	public FitOrderRefund loadOne(Long id) throws Exception {
		return this.fitOrderRefundService.findOne(id);
	}

	@Override
	public void auditAgree(FitEmployee auditor, FitOrderRefund orderRefund) throws Exception {
		orderRefund.setStatus(AuditStatus.AUDIT_SUCCESS);
		orderRefund.setAuditTime(new Date());
		orderRefund.setAuditorId(auditor.getId());
		orderRefund.setAuditorMobile(auditor.getMobile());
		orderRefund.setAuditorName(auditor.getName());
		this.fitOrderRefundService.save(orderRefund);
		this.actWithRefund(orderRefund);
	}

	@Override
	public void auditReject(FitEmployee auditor, FitOrderRefund orderRefund) throws Exception {
		orderRefund.setStatus(AuditStatus.AUDIT_FAILURE);
		orderRefund.setAuditTime(new Date());
		orderRefund.setAuditorId(auditor.getId());
		orderRefund.setAuditorMobile(auditor.getMobile());
		orderRefund.setAuditorName(auditor.getName());
		this.fitOrderRefundService.save(orderRefund);
	}

	@Override
	public FitOrderRefund findByOrderNumberAndStatus(String orderNumber, AuditStatus status) {
		return this.fitOrderRefundService.findByOrderNumberAndStatus(orderNumber, status);
	}

}
