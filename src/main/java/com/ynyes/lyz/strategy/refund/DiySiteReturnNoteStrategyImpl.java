package com.ynyes.lyz.strategy.refund;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ynyes.lyz.entity.TdReturnNote;

@Service
@Transactional
public class DiySiteReturnNoteStrategyImpl implements TdReturnNoteStrategy {

	@Autowired
	private TdReturnNoteStrategyImpl origin;
	
	@Override
	public Boolean doAction(TdReturnNote returnNote) throws Exception {
		return origin.doAction(returnNote);
	}

}
