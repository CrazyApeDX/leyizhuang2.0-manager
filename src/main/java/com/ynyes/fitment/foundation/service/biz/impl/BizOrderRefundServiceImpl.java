package com.ynyes.fitment.foundation.service.biz.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.ArrayUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ynyes.fitment.core.constant.AuditStatus;
import com.ynyes.fitment.foundation.entity.FitEmployee;
import com.ynyes.fitment.foundation.entity.FitOrderRefund;
import com.ynyes.fitment.foundation.entity.FitOrderRefundGoods;
import com.ynyes.fitment.foundation.service.FitOrderRefundGoodsService;
import com.ynyes.fitment.foundation.service.FitOrderRefundService;
import com.ynyes.fitment.foundation.service.biz.BizOrderRefundService;
import com.ynyes.lyz.entity.TdOrder;
import com.ynyes.lyz.service.TdOrderService;

@Service
@Transactional
public class BizOrderRefundServiceImpl implements BizOrderRefundService {

	@Autowired
	private TdOrderService tdOrderService;

	@Autowired
	private FitOrderRefundService fitOrderRefundService;

	@Autowired
	private FitOrderRefundGoodsService fitOrderRefundGoodsService;

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

					this.sendToWms();
					this.sendToEbs();

				} else {
					orderRefund.setStatus(AuditStatus.WAIT_AUDIT);
				}

				return this.fitOrderRefundService.save(orderRefund);
			}
		}
	}

	private void sendToWms() {

	}

	private void sendToEbs() {

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
	}

	@Override
	public void auditReject(FitEmployee auditor, FitOrderRefund orderRefund) throws Exception {
		orderRefund.setStatus(AuditStatus.AUDIT_SUCCESS);
		orderRefund.setAuditTime(new Date());
		orderRefund.setAuditorId(auditor.getId());
		orderRefund.setAuditorMobile(auditor.getMobile());
		orderRefund.setAuditorName(auditor.getName());
		this.fitOrderRefundService.save(orderRefund);
	}

}
