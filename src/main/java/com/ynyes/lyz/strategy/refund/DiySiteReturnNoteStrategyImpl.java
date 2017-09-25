package com.ynyes.lyz.strategy.refund;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ynyes.lyz.entity.TdReturnNote;

@Service("diySiteReturnNoteStrategy")
@Transactional
public class DiySiteReturnNoteStrategyImpl implements TdReturnNoteStrategy {

	@Autowired
	@Qualifier("tdReturnNoteStrategy")
	private TdReturnNoteStrategy tdReturnNoteStrategyImpl;
	
	@Override
	public Boolean doAction(TdReturnNote returnNote) throws Exception {
		return tdReturnNoteStrategyImpl.doAction(returnNote);
	}

}
