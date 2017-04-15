package com.ynyes.lyz.strategy.refund;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ynyes.lyz.entity.TdReturnNote;

@Component
public class ReturnNoteStrategyFactory {

	@Autowired
	private DeliveryReturnNoteStrategyImpl deliveryStrategy;

	@Autowired
	private DiySiteReturnNoteStrategyImpl diySiteStrategy;

	public TdReturnNoteStrategy build(TdReturnNote returnNote) {
		if (returnNote.getDeliverTypeTitle().equalsIgnoreCase("送货上门")) {
			return deliveryStrategy;
		} else {
			return diySiteStrategy;
		}
	}
}
