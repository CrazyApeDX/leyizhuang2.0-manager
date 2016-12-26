package com.ynyes.fitment.foundation.service;

import java.util.Date;

import com.ynyes.fitment.core.entity.client.result.ClientResult;
import com.ynyes.fitment.foundation.entity.FitEmployee;

public interface FitEmployeeService {

	FitEmployee addMainEmployee(Long managerId, String password, String mobile, String name, Boolean frozen,
			Date frozenEndTime) throws Exception;

	FitEmployee addSubEmployee(Long mainId, String mobile, String password, String name) throws Exception;

	ClientResult login(String mobile, String password) throws Exception;
}
