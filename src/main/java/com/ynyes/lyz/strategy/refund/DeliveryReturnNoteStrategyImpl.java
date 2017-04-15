package com.ynyes.lyz.strategy.refund;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ynyes.lyz.entity.TdReturnNote;

@Service("deliveryReturnNoteStrategy")
@Transactional
public class DeliveryReturnNoteStrategyImpl implements TdReturnNoteStrategy {

	@Autowired
	@Qualifier("tdReturnNoteStrategy")
	private TdReturnNoteStrategy tdReturnNoteStrategyImpl;

	@Override
	public Boolean doAction(TdReturnNote returnNote) throws Exception {
		if (this.sendToWMS(returnNote)) {
			return tdReturnNoteStrategyImpl.doAction(returnNote);
		} else {
			return Boolean.FALSE;
		}
	}

	protected Boolean sendToWMS(TdReturnNote returnNote) {
		if (returnNote.getStatusId().equals(2L)) {
			// send
			return Boolean.TRUE;
		}

		return Boolean.TRUE;
	}

}
