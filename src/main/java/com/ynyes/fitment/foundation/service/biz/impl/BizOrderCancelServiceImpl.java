package com.ynyes.fitment.foundation.service.biz.impl;

import static org.apache.commons.lang3.StringUtils.leftPad;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ynyes.fitment.core.constant.AuditStatus;
import com.ynyes.fitment.core.entity.client.result.ClientResult;
import com.ynyes.fitment.core.entity.client.result.ClientResult.ActionCode;
import com.ynyes.fitment.foundation.entity.FitCompany;
import com.ynyes.fitment.foundation.entity.FitEmployee;
import com.ynyes.fitment.foundation.entity.FitOrderCancel;
import com.ynyes.fitment.foundation.entity.FitOrderCancelGoods;
import com.ynyes.fitment.foundation.service.FitCompanyService;
import com.ynyes.fitment.foundation.service.FitOrderCancelGoodsService;
import com.ynyes.fitment.foundation.service.FitOrderCancelService;
import com.ynyes.fitment.foundation.service.biz.BizCreditChangeLogService;
import com.ynyes.fitment.foundation.service.biz.BizOrderCancelService;
import com.ynyes.lyz.entity.TdCity;
import com.ynyes.lyz.entity.TdDiySite;
import com.ynyes.lyz.entity.TdGoods;
import com.ynyes.lyz.entity.TdOrder;
import com.ynyes.lyz.entity.TdOrderGoods;
import com.ynyes.lyz.entity.TdReturnNote;
import com.ynyes.lyz.interfaces.entity.TdOrderInf;
import com.ynyes.lyz.interfaces.entity.TdReturnOrderInf;
import com.ynyes.lyz.interfaces.service.TdInterfaceService;
import com.ynyes.lyz.interfaces.service.TdOrderInfService;
import com.ynyes.lyz.interfaces.service.TdReturnOrderInfService;
import com.ynyes.lyz.interfaces.utils.INFConstants;
import com.ynyes.lyz.interfaces.utils.StringTools;
import com.ynyes.lyz.service.TdCityService;
import com.ynyes.lyz.service.TdCommonService;
import com.ynyes.lyz.service.TdDiySiteInventoryService;
import com.ynyes.lyz.service.TdDiySiteService;
import com.ynyes.lyz.service.TdGoodsService;
import com.ynyes.lyz.service.TdOrderGoodsService;
import com.ynyes.lyz.service.TdOrderService;
import com.ynyes.lyz.service.TdReturnNoteService;

@Service
@Transactional
public class BizOrderCancelServiceImpl implements BizOrderCancelService {

	@Autowired
	private FitOrderCancelService fitOrderCancelService;

	@Autowired
	private TdOrderService tdOrderService;

	@Autowired
	private FitOrderCancelGoodsService fitOrderCancelGoodsService;

	@Autowired
	private FitCompanyService fitCompanyService;

	@Autowired
	private TdCityService tdCityService;

	@Autowired
	private TdGoodsService tdGoodsService;

	@Autowired
	private TdDiySiteInventoryService tdDiySiteInventoryService;

	@Autowired
	private TdCommonService tdCommonService;

	@Autowired
	private TdDiySiteService tdDiySiteService;

	@Autowired
	private TdOrderGoodsService tdOrderGoodsService;

	@Autowired
	private TdInterfaceService tdInterfaceService;

	@Autowired
	private TdReturnNoteService tdReturnNoteService;

	@Autowired
	private TdReturnOrderInfService tdReturnOrderInfService;

	@Autowired
	private TdOrderInfService tdOrderInfService;

	@Autowired
	private BizCreditChangeLogService bizCreditChangeLogService;

	@Override
	public void auditAgree(FitEmployee auditor, FitOrderCancel orderCancel) throws Exception {
		orderCancel.setStatus(AuditStatus.AUDIT_SUCCESS);
		orderCancel.setAuditTime(new Date());
		orderCancel.setAuditorId(auditor.getId());
		orderCancel.setAuditorMobile(auditor.getMobile());
		orderCancel.setAuditorName(auditor.getName());
		this.fitOrderCancelService.save(orderCancel);
		// 在此增加装饰公司取消订单处理
		this.actWithCancel(orderCancel);
	}

	@Override
	public void auditReject(FitEmployee auditor, FitOrderCancel orderCancel) throws Exception {
		orderCancel.setStatus(AuditStatus.AUDIT_FAILURE);
		orderCancel.setAuditTime(new Date());
		orderCancel.setAuditorId(auditor.getId());
		orderCancel.setAuditorMobile(auditor.getMobile());
		orderCancel.setAuditorName(auditor.getName());
		this.fitOrderCancelService.save(orderCancel);
	}

	@Override
	public void auditCancel(FitOrderCancel orderCancel) throws Exception {
		orderCancel.setStatus(AuditStatus.CANCEL);
		this.fitOrderCancelService.save(orderCancel);
	}

	@Override
	public ClientResult init(FitEmployee employee, TdOrder order) throws Exception {
		if (employee.getIsMain()) {
			FitOrderCancel orderCancel = new FitOrderCancel();
			orderCancel.setOrderId(order.getId());
			orderCancel.setEmployeeId(employee.getId());
			orderCancel.setEmployeeMobile(employee.getMobile());
			orderCancel.setEmployeeName(employee.getName());
			orderCancel.setAuditorId(employee.getId());
			orderCancel.setAuditorMobile(employee.getMobile());
			orderCancel.setAuditorName(employee.getName());
			orderCancel.setCompanyId(order.getDiySiteId());
			orderCancel.setCompanyName(order.getDiySiteName());
			orderCancel.setOrderNumber(order.getMainOrderNumber());
			orderCancel.setStatus(AuditStatus.AUDIT_SUCCESS);
			orderCancel.setCancelTime(new Date());
			orderCancel.setAuditTime(new Date());
			orderCancel = this.initOrderCancelGoods(orderCancel, order);
			orderCancel = fitOrderCancelService.save(orderCancel);

			// 在此增加装饰公司取消订单处理
			this.actWithCancel(orderCancel);

			return new ClientResult(ActionCode.SUCCESS, null);
		} else {
			FitOrderCancel orderCancel = new FitOrderCancel();
			orderCancel.setOrderId(order.getId());
			orderCancel.setEmployeeId(employee.getId());
			orderCancel.setEmployeeMobile(employee.getMobile());
			orderCancel.setEmployeeName(employee.getName());
			orderCancel.setCompanyId(order.getDiySiteId());
			orderCancel.setCompanyName(order.getDiySiteName());
			orderCancel.setOrderNumber(order.getMainOrderNumber());
			orderCancel.setStatus(AuditStatus.WAIT_AUDIT);
			orderCancel.setCancelTime(new Date());
			orderCancel = this.initOrderCancelGoods(orderCancel, order);
			fitOrderCancelService.save(orderCancel);

			return new ClientResult(ActionCode.FAILURE, "您的申请已提交，请等待审核");
		}
	}

	private FitOrderCancel initOrderCancelGoods(FitOrderCancel orderCancel, TdOrder subOrder) throws Exception {
		List<FitOrderCancelGoods> orderCancelGoodsList = new ArrayList<>();
		List<TdOrder> orderList = tdOrderService.findByMainOrderNumberIgnoreCase(subOrder.getMainOrderNumber());
		Double credit = 0d;
		if (CollectionUtils.isNotEmpty(orderList)) {
			for (TdOrder order : orderList) {
				credit += order.getCredit();
				List<TdOrderGoods> orderGoodsList = order.getOrderGoodsList();
				if (CollectionUtils.isNotEmpty(orderGoodsList)) {
					for (TdOrderGoods orderGoods : orderGoodsList) {
						FitOrderCancelGoods orderCancelGoods = new FitOrderCancelGoods();
						orderCancelGoods.setGoodsId(orderGoods.getGoodsId());
						orderCancelGoods.setGoodsTitle(orderGoods.getGoodsTitle());
						orderCancelGoods.setGoodsSku(orderGoods.getSku());
						orderCancelGoods.setGoodsCoverImageUri(orderGoods.getGoodsCoverImageUri());
						orderCancelGoods.setQuantity(orderGoods.getQuantity());
						orderCancelGoods.setPrice(orderGoods.getPrice());
						orderCancelGoods.setRealPrice(orderGoods.getRealPrice());
						orderCancelGoods.setTotalPrice(orderCancelGoods.getPrice() * orderCancelGoods.getQuantity());
						orderCancelGoods.setRealTotalPrice(orderGoods.getRealPrice() * orderGoods.getQuantity());
						orderCancelGoods = this.fitOrderCancelGoodsService.save(orderCancelGoods);
						orderCancelGoodsList.add(orderCancelGoods);
					}
				}
			}
		}
		orderCancel.setCredit(credit);
		orderCancel.setOrderGoodsList(orderCancelGoodsList);
		return orderCancel;
	}

	@Override
	public FitOrderCancel loadOne(Long id) throws Exception {
		return this.fitOrderCancelService.findOne(id);
	}

	private void actWithCancel(FitOrderCancel orderCancel) throws Exception {
		FitCompany company = this.fitCompanyService.findOne(orderCancel.getCompanyId());
		Double credit = orderCancel.getCredit();
		String mainOrderNumber = orderCancel.getOrderNumber();
		this.repayCredit(company, credit, orderCancel);
		this.cancelOrder(mainOrderNumber);

	}

	private void repayCredit(FitCompany company, Double credit, FitOrderCancel orderCancel) throws Exception {
		company.setCredit(company.getCredit() + credit);
		bizCreditChangeLogService.cancelLog(company, orderCancel);
		this.fitCompanyService.save(company);
	}

	private void cancelOrder(String mainOrderNumber) throws Exception {
		List<TdOrder> orderList = tdOrderService.findByMainOrderNumberIgnoreCase(mainOrderNumber);
		if (CollectionUtils.isNotEmpty(orderList)) {
			for (TdOrder order : orderList) {
				order.setStatusId(7L);
				order.setCancelTime(new Date());
				order.setIsRefund(true);
				tdOrderService.save(order);

				this.repayInventory(order);
				// 通知物流
				TdReturnNote returnNote = this.MakeReturnNote(order, 0L, "");
				tdCommonService.sendBackMsgToWMS(returnNote);
			}
		}
	}

	private void repayInventory(TdOrder order) throws Exception {
		TdCity city = tdCityService.findByCityName(order.getCity());
		List<TdOrderGoods> orderGoodsList = order.getOrderGoodsList();
		for (TdOrderGoods orderGoods : orderGoodsList) {
			Long goodsId = orderGoods.getGoodsId();
			Long quantity = orderGoods.getQuantity();

			TdGoods goods = tdGoodsService.findOne(goodsId);
			tdDiySiteInventoryService.repayCityInventory(city.getSobIdCity(), goods, quantity, order.getOrderNumber(),
					order.getRealUserUsername().replace("FIT", ""), "配送退货");
		}
	}

	private TdReturnNote MakeReturnNote(TdOrder order, Long type, String msg) {
		TdReturnNote returnNote = new TdReturnNote();

		// 退货单编号
		Date current = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmm");
		String curStr = sdf.format(current);
		Random random = new Random();

		returnNote.setReturnNumber("T" + curStr + leftPad(Integer.toString(random.nextInt(999)), 3, "0"));

		// 添加订单信息
		returnNote.setOrderNumber(order.getOrderNumber());

		// add MDJ
		returnNote.setShoppingAddress(order.getShippingAddress());
		returnNote.setSellerRealName(order.getSellerRealName());
		// end add MDJ

		// 支付方式
		returnNote.setPayTypeId(order.getPayTypeId());
		returnNote.setPayTypeTitle(order.getPayTypeTitle());
		// 门店信息
		if (null != order.getDiySiteId()) {
			TdDiySite diySite = tdDiySiteService.findOne(order.getDiySiteId());
			if (diySite != null) {
				returnNote.setDiySiteId(order.getDiySiteId());
				returnNote.setDiyCode(diySite.getStoreCode());
				returnNote.setDiySiteTel(diySite.getServiceTele());
				returnNote.setDiySiteTitle(diySite.getTitle());
				returnNote.setDiySiteAddress(diySite.getAddress());
			}
		}

		// 退货信息
		returnNote.setUsername(order.getUsername());
		if (type == 0L) {
			returnNote.setRemarkInfo("用户取消订单，退货");
		} else if (type == 1L) {
			returnNote.setRemarkInfo("管理员 " + msg + " 取消订单,退货");
		}

		if (org.apache.commons.lang3.StringUtils.isNotBlank(order.getDeliverTypeTitle())
				&& "门店自提".equals(order.getDeliverTypeTitle())) {
			returnNote.setTurnType(1L);
		} else {
			returnNote.setTurnType(2L);
		}

		returnNote.setStatusId(5L);

		returnNote.setDeliverTypeTitle(order.getDeliverTypeTitle());

		returnNote.setOrderTime(new Date());

		returnNote.setTurnPrice(order.getTotalGoodsPrice());
		List<TdOrderGoods> orderGoodsList = new ArrayList<>();

		// 商品
		if (null != order.getOrderGoodsList()) {
			for (TdOrderGoods oGoods : order.getOrderGoodsList()) {
				TdOrderGoods orderGoods = new TdOrderGoods();

				orderGoods.setBrandId(oGoods.getBrandId());
				orderGoods.setBrandTitle(oGoods.getBrandTitle());
				orderGoods.setGoodsId(oGoods.getGoodsId());
				orderGoods.setGoodsSubTitle(oGoods.getGoodsSubTitle());
				orderGoods.setSku(oGoods.getSku());
				orderGoods.setGoodsCoverImageUri(oGoods.getGoodsCoverImageUri());
				orderGoods.setGoodsColor(oGoods.getGoodsColor());
				orderGoods.setGoodsCapacity(oGoods.getGoodsCapacity());
				orderGoods.setGoodsVersion(oGoods.getGoodsVersion());
				orderGoods.setGoodsSaleType(oGoods.getGoodsSaleType());
				orderGoods.setGoodsTitle(oGoods.getGoodsTitle());

				orderGoods.setPrice(oGoods.getPrice());
				orderGoods.setQuantity(oGoods.getQuantity());

				orderGoods.setDeliveredQuantity(oGoods.getDeliveredQuantity());
				orderGoods.setPoints(oGoods.getPoints());

				Double unit = oGoods.getRealPrice();

				if (null == unit || 0.00 == unit.doubleValue()) {
					String orderNumber = returnNote.getOrderNumber();
					if (orderNumber.contains("HRFIT")) {
						orderNumber = orderNumber.replace("HRFIT", "XN");
					} else if (orderNumber.contains("YRFIT")) {
						orderNumber = orderNumber.replace("YRFIT", "XN");
					} else if (orderNumber.contains("LYZFIT")) {
						orderNumber = orderNumber.replace("LYZFIT", "XN");
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
				oGoods.setIsReturnApplied(true);
				// 更新订单商品信息是否退货状态
				tdOrderGoodsService.save(oGoods);
			}
		}

		returnNote.setReturnGoodsList(orderGoodsList);
		tdOrderGoodsService.save(orderGoodsList);
		this.initReturnOrder(returnNote, INFConstants.INF_RETURN_ORDER_CANCEL_INT);
		tdInterfaceService.initReturnCouponInfByOrder(order, INFConstants.INF_RETURN_ORDER_CANCEL_INT);
		tdInterfaceService.sendReturnOrderByAsyn(returnNote);
		return tdReturnNoteService.save(returnNote);
	}

	private void initReturnOrder(TdReturnNote returnNote, Integer type) {
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
			}
		}
	}

}
