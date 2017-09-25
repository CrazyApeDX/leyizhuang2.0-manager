package com.ynyes.lyz.strategy.refund;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import com.ynyes.lyz.entity.TdReturnNote;

@Component
public class ReturnNoteStrategyFactory {

	@Autowired
	@Qualifier("deliveryReturnNoteStrategy")
	private TdReturnNoteStrategy deliveryStrategy;

	@Autowired
	@Qualifier("diySiteReturnNoteStrategy")
	private TdReturnNoteStrategy diySiteStrategy;

	public TdReturnNoteStrategy build(TdReturnNote returnNote) {
		if (returnNote.getDeliverTypeTitle().equalsIgnoreCase("送货上门")) {
			return deliveryStrategy;
		} else {
			return diySiteStrategy;
		}
	}
}
