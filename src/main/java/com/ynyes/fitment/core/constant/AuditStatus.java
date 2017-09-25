package com.ynyes.fitment.core.constant;

/**
 * 装饰公司订单审核状态 
 * @author dengxiao
 */
public enum AuditStatus {

	// 等待审核
	WAIT_AUDIT, 
	// 审核失败
	AUDIT_FAILURE, 
	// 审核成功
	AUDIT_SUCCESS, 
	// 作废
	CANCEL
}
