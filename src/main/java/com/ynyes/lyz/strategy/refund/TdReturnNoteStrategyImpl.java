package com.ynyes.lyz.strategy.refund;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ynyes.lyz.entity.TdOrder;
import com.ynyes.lyz.entity.TdReturnNote;
import com.ynyes.lyz.service.TdOrderService;
import com.ynyes.lyz.service.TdReturnNoteService;

@Service("tdReturnNoteStrategy")
@Transactional
public class TdReturnNoteStrategyImpl implements TdReturnNoteStrategy  {

	@Autowired
	private TdReturnNoteService tdReturnNoteService;
	
	@Autowired
	private TdOrderService tdOrderService;
	
	@Override
	public Boolean doAction(TdReturnNote returnNote) throws Exception {
		String orderNumber = returnNote.getOrderNumber();
		TdOrder order = tdOrderService.findByOrderNumber(orderNumber);
		if (null == order) {
			throw new IllegalArgumentException("查询不到退单对应的订单");
		}
		returnNote.setStatusId(6L);
		tdReturnNoteService.save(returnNote);
		order.setStatusId(6L);
		tdOrderService.save(order);
		return Boolean.TRUE;
	}

}
