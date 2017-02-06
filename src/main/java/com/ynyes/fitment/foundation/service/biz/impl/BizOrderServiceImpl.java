package com.ynyes.fitment.foundation.service.biz.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ynyes.fitment.core.constant.AuditStatus;
import com.ynyes.fitment.foundation.entity.FitCartGoods;
import com.ynyes.fitment.foundation.entity.FitEmployee;
import com.ynyes.fitment.foundation.entity.FitOrder;
import com.ynyes.fitment.foundation.entity.FitOrderGoods;
import com.ynyes.fitment.foundation.service.FitOrderGoodsService;
import com.ynyes.fitment.foundation.service.FitOrderService;
import com.ynyes.fitment.foundation.service.biz.BizCartGoodsService;
import com.ynyes.fitment.foundation.service.biz.BizOrderService;

@Service
@Transactional
public class BizOrderServiceImpl implements BizOrderService {

	@Autowired
	private BizCartGoodsService bizCartGoodsService;

	@Autowired
	private FitOrderService fitOrderService;

	@Autowired
	private FitOrderGoodsService fitOrderGoodsService;

	@Override
	public FitOrder initOrder(String receiver, String receiverMobile, String baseAddress, String detailAddress,
			FitEmployee employee) throws Exception {
		List<FitCartGoods> cartGoodsList = this.bizCartGoodsService.loadEmployeeCart(employee.getId());
		List<FitOrderGoods> orderGoodsList = new ArrayList<>();
		for (FitCartGoods cartGoods : cartGoodsList) {
			if (null != cartGoods) {
				FitOrderGoods orderGoods = new FitOrderGoods().init(cartGoods);
				orderGoods = fitOrderGoodsService.save(orderGoods);
				orderGoodsList.add(orderGoods);
				this.bizCartGoodsService.clearCartWithId(cartGoods.getId());
			}
		}

		FitOrder order = new FitOrder();
		order.setCompanyId(employee.getCompanyId());
		order.setEmployeeId(employee.getId());
		order.setEmployeeName(employee.getName());
		if (employee.getIsMain()) {
			order.setAuditorId(employee.getId());
			order.setAuditorName(employee.getName());
			order.setAuditTime(new Date());
		}

		order.setStatus(employee.getIsMain() ? AuditStatus.AUDIT_SUCCESS : AuditStatus.WAIT_AUDIT);
		order.setOrderGoodsList(orderGoodsList);
		order.setReceiveName(receiver);
		order.setReceivePhone(receiverMobile);
		order.setReceiveAddress(baseAddress + detailAddress);

		return this.fitOrderService.save(order.initOrderNumber().initPrice());
	}

	@Override
	public FitOrder auditOrder(Long orderId, String action) throws Exception {
		FitOrder order = this.findOne(orderId);
		if (null != order) {
			switch (action) {
			case "AGREE":
				order.setStatus(AuditStatus.AUDIT_SUCCESS);
				order.setAuditTime(new Date());
				break;
			case "REJECT":
				order.setStatus(AuditStatus.AUDIT_FAILURE);
				order.setAuditTime(new Date());
				break;
			case "DELETE":
				if (order.getStatus().equals(AuditStatus.AUDIT_FAILURE)) {
					order.setIsDelete(true);
				}
				break;
			}
		}
		return this.fitOrderService.save(order);
	}

	@Override
	public FitOrder findOne(Long id) throws Exception {
		return this.fitOrderService.findOne(id);
	}

	@Override
	public Page<FitOrder> loadOrderByEmployeeId(Long employeeId, Integer page, Integer size) throws Exception {
		return this.fitOrderService.findByEmployeeId(employeeId, page, size);
	}

	@Override
	public Page<FitOrder> loadOrderByCompanyId(Long companyId, Integer page, Integer size) throws Exception {
		return this.fitOrderService.findByCompanyId(companyId, page, size);
	}

}
