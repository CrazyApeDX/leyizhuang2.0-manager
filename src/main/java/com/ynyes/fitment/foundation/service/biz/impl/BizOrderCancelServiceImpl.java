package com.ynyes.fitment.foundation.service.biz.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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
import com.ynyes.fitment.foundation.service.biz.BizOrderCancelService;
import com.ynyes.lyz.entity.TdOrder;
import com.ynyes.lyz.entity.TdOrderGoods;
import com.ynyes.lyz.service.TdOrderService;

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

	@Override
	public void auditAgree(FitEmployee auditor, FitOrderCancel orderCancel) throws Exception {
		orderCancel.setStatus(AuditStatus.AUDIT_SUCCESS);
		orderCancel.setAuditTime(new Date());
		orderCancel.setAuditorId(auditor.getId());
		orderCancel.setAuditorMobile(auditor.getMobile());
		orderCancel.setAuditorName(auditor.getName());
		this.fitOrderCancelService.save(orderCancel);
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
			fitOrderCancelService.save(orderCancel);

			// 在此增加装饰公司取消订单处理
			this.actWithCancel(null);

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
				credit += order.getUnCashBalanceUsed();
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
		this.repayCredit(company, credit);
		this.cancelOrder(mainOrderNumber);
		
	}

	private void repayCredit(FitCompany company, Double credit) throws Exception {
		company.setCredit(company.getCredit() + credit);
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
			}
		}
		
		this.sendWMS();
	}

	private void sendWMS() throws Exception {

	}

}
