package com.ynyes.fitment.foundation.service.biz.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ynyes.fitment.foundation.entity.FitEmployee;
import com.ynyes.fitment.foundation.service.FitEmployeeService;
import com.ynyes.fitment.foundation.service.biz.BizEmployeeService;
import com.ynyes.lyz.util.MD5;

@Service
@Transactional
public class BizEmployeeServiceImpl implements BizEmployeeService {

	@Autowired
	private FitEmployeeService fitEmployeeService;
	
	@Override
	public Boolean changePassword(FitEmployee employee, String oldPassword, String newPassword) throws Exception {
		if (employee.getPassword().equals(MD5.md5(oldPassword, 32))) {
			employee.setPassword(MD5.md5(newPassword, 32));
			this.fitEmployeeService.update(employee);
			return true;
		} else {
			return false;
		}
	}

}
