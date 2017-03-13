package com.ynyes.fitment.foundation.service.biz;

import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Page;

import com.ynyes.fitment.foundation.entity.FitCartGoods;
import com.ynyes.fitment.foundation.entity.FitEmployee;
import com.ynyes.fitment.foundation.entity.FitOrder;
import com.ynyes.lyz.entity.TdOrder;

public interface BizOrderService {

	FitOrder initOrder(List<FitCartGoods> cartGoodsList, String receiver, String receiverMobile, String baseAddress,
			String detailAddress, FitEmployee employee, String deliveryDate, Long deliveryTime, String remark)
			throws Exception;

	FitOrder auditOrder(Long orderId, FitEmployee auditor, String action) throws Exception;

	FitOrder findOne(Long id) throws Exception;

	Page<FitOrder> loadOrderByEmployeeId(Long employeeId, Integer page, Integer size) throws Exception;

	Page<FitOrder> loadOrderByCompanyId(Long companyId, Integer page, Integer size) throws Exception;

	FitOrder loadDeliveryTime(FitOrder order) throws Exception;

	FitOrder saveRemark(FitOrder order, String remark) throws Exception;

	FitOrder saveDelivery(FitOrder order, String deliveryDate, Long deliveryTime, Long floor) throws Exception;

	TdOrder transformer(FitOrder order) throws Exception;

	Boolean validateEnoughCredit(FitOrder order) throws Exception;

	Boolean validateEnoughInventory(FitOrder order) throws Exception;

	void finishOrder(FitOrder order) throws Exception;

	Map<String, Object> loadDeliveryTimeInforBaseNow(FitEmployee employee);

	FitOrder checkDeliveryInfo(FitOrder order, FitEmployee fitEmployee) throws Exception;
}
