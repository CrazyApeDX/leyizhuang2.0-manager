package com.ynyes.fitment.web.controller.management;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

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
import com.ynyes.lyz.entity.TdCity;
import com.ynyes.lyz.service.TdDiySiteRoleService;
import com.ynyes.lyz.util.MD5;

@Controller
@RequestMapping(value = "/Verwalter/fitment/employee")
public class FitManagementEmployeeController {

	@Autowired
	private FitEmployeeService fitEmployeeService;

	@Autowired
	private FitCompanyService fitCompanyService;
	
	
	
	@Autowired
	private TdDiySiteRoleService tdDiySiteRoleService;

	@RequestMapping(value = "/list", produces = "text/html;charset=utf-8")
	public String employeeList(HttpServletRequest req,ModelMap map, Integer page, Integer size, String __EVENTTARGET, String __EVENTARGUMENT,
			String __VIEWSTATE,String keyWords,String cityTitle,String companyTitle,String isMain) {
		String username = (String) req.getSession().getAttribute("manager");

		if (null == username) {
			return "redirect:/Verwalter/login";
		}
		
		// 获取管理员管辖城市
		List<TdCity> cityList = tdDiySiteRoleService.getUserRoleCity(username);
		//获取fit公司
		List<FitCompany> companyList = new ArrayList<FitCompany>();
		try {
			companyList = fitCompanyService.findAll();
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		
		if (null != __EVENTTARGET) {
			switch (__EVENTTARGET) {
			case "btnPage":
				if (null != __EVENTARGUMENT) {
					page = Integer.parseInt(__EVENTARGUMENT);
				}
				break;
			}
		}
		
		Page<FitEmployee> employeePage = null;
		page = null == page ? Global.DEFAULT_PAGE : page;
		size = null == size ? Global.DEFAULT_SIZE : size;
		try {
			employeePage = this.fitEmployeeService.findAllAddConditionDeliveryType(page, size, keyWords, cityTitle, companyTitle, isMain);
		} catch (Exception e) {
			e.printStackTrace();
		}
		map.addAttribute("cityList", cityList);
		map.addAttribute("companyList", companyList);
		map.addAttribute("employeePage", employeePage);
		map.addAttribute("page", page);
		map.addAttribute("size", size);
		map.addAttribute("keyWords", keyWords);
		map.addAttribute("cityTitle", cityTitle);
		map.addAttribute("companyTitle", companyTitle);
		map.addAttribute("isMain", isMain);
		
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
				employee.setPassword((null == oldPassword || "".equalsIgnoreCase(oldPassword))
						? MD5.md5(Global.DEFAULT_PASSWORD, 32) : MD5.md5(oldPassword, 32));
			} else {
				FitEmployee em = fitEmployeeService.findOne(employee.getId());
				if (null == oldPassword || "".equals(oldPassword)) {
					employee.setPassword(em.getPassword());
				} else {
					employee.setPassword(MD5.md5(oldPassword, 32));
				}
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
