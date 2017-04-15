package com.ynyes.lyz.strategy.refund;

import com.ynyes.lyz.entity.TdReturnNote;

public interface TdReturnNoteStrategy {

	Boolean doAction(TdReturnNote returnNote) throws Exception;
}
