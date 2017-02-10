package com.ynyes.fitment.foundation.service.biz;

import com.ynyes.fitment.foundation.entity.FitEmployee;

public interface BizEmployeeService {

	Boolean changePassword(FitEmployee employee, String oldPassword, String newPassword) throws Exception;
}
