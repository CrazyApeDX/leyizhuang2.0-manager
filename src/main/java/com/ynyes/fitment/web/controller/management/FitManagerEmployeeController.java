package com.ynyes.fitment.web.controller.management;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ynyes.fitment.core.constant.Global;
import com.ynyes.fitment.foundation.entity.FitCompany;
import com.ynyes.fitment.foundation.entity.FitEmployee;
import com.ynyes.fitment.foundation.service.FitCompanyService;
import com.ynyes.fitment.foundation.service.FitEmployeeService;
import com.ynyes.lyz.util.MD5;

@Controller
@RequestMapping(value = "/Verwalter/fitment/employee")
public class FitManagerEmployeeController {

	@Autowired
	private FitEmployeeService fitEmployeeService;

	@Autowired
	private FitCompanyService fitCompanyService;

	@RequestMapping(value = "/list", produces = "text/html;charset=utf-8")
	public String employeeList(ModelMap map, Integer page, Integer size) {
		Page<FitEmployee> employeePage = null;
		page = null == page ? Global.deafultPage : page;
		size = null == size ? Global.defaultSize : size;
		try {
			employeePage = this.fitEmployeeService.findAll(page, size);
		} catch (Exception e) {
			e.printStackTrace();
		}
		map.addAttribute("employeePage", employeePage);
		map.addAttribute("page", page);
		map.addAttribute("size", size);
		return "/fitment/management/employee_list";
	}

	@RequestMapping(value = "/edit/{id}", method = RequestMethod.GET, produces = "text/html;charset=utf-8")
	public String employeeEdit(@PathVariable Long id, ModelMap map) {
		FitEmployee employee = null;
		List<FitCompany> companyList = null;
		try {
			employee = this.fitEmployeeService.findOne(id);
			companyList = this.fitCompanyService.findAll();
		} catch (Exception e) {
			e.printStackTrace();
		}
		map.addAttribute("employee", employee);
		map.addAttribute("companyList", companyList);
		return "/fitment/management/employee_edit";
	}

	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public String companySave(FitEmployee employee, String oldPassword) {
		try {
			if (null == employee.getId()) {
				employee.setPassword(
						null == oldPassword ? MD5.md5(Global.defaultPassword, 32) : MD5.md5(oldPassword, 32));
			} else {
				employee.setPassword(null == oldPassword ? null : MD5.md5(oldPassword, 32));
			}
			this.fitEmployeeService.managerSaveEmployee(employee);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "redirect:/Verwalter/fitment/employee/list";
	}
	
	@RequestMapping(value = "/delete/{id}", method = RequestMethod.GET)
	public String companyDelete(@PathVariable Long id) {
		try {
			this.fitEmployeeService.delete(id);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "redirect:/Verwalter/fitment/employee/list";
	}

	@RequestMapping(value = "/mobile/validate/{id}", method = RequestMethod.POST, produces = "application/json;charset=utf-8")
	@ResponseBody
	public Map<String, String> companyCodeValidate(@PathVariable Long id, String param) {
		Map<String, String> res = new HashMap<>();
		res.put("status", "n");
		try {
			Boolean validate = this.fitEmployeeService.validateRepeatEmployeeByMobile(param, id);
			if (validate) {
				res.put("info", "该手机号码的员工已经存在");
			} else {
				res.put("status", "y");
			}
		} catch (Exception e) {
			res.put("info", "出现意外的错误，请稍后重试");
			e.printStackTrace();
		}
		return res;
	}
}
