package com.ynyes.lyz.strategy.refund;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ynyes.lyz.entity.TdReturnNote;
import com.ynyes.lyz.interfaces.service.TdTbwBackMCancelService;

@Service("deliveryReturnNoteStrategy")
@Transactional
public class DeliveryReturnNoteStrategyImpl implements TdReturnNoteStrategy {

	@Autowired
	@Qualifier("tdReturnNoteStrategy")
	private TdReturnNoteStrategy tdReturnNoteStrategyImpl;

	@Autowired
	private TdTbwBackMCancelService tdTbwBackMCancelService;

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
			return tdTbwBackMCancelService.sendBackCancelToWMS(returnNote);
		}

		return Boolean.TRUE;
	}

}
