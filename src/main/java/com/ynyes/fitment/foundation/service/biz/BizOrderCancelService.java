package com.ynyes.fitment.foundation.service.biz;

import com.ynyes.fitment.core.entity.client.result.ClientResult;
import com.ynyes.fitment.foundation.entity.FitEmployee;
import com.ynyes.fitment.foundation.entity.FitOrderCancel;
import com.ynyes.lyz.entity.TdOrder;

public interface BizOrderCancelService {
	
	FitOrderCancel loadOne(Long id) throws Exception;

	void auditAgree(FitEmployee auditor, FitOrderCancel orderCancel) throws Exception;

	void auditReject(FitEmployee auditor, FitOrderCancel orderCancel) throws Exception;

	void auditCancel(FitOrderCancel orderCancel) throws Exception;

	ClientResult init(FitEmployee employee, TdOrder order) throws Exception;
}
