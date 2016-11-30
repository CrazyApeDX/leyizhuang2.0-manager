package com.ynyes.lyz.controller.management;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ynyes.lyz.entity.TdManager;
import com.ynyes.lyz.service.TdManagerService;

/**
 * <p>标题：TdManagerPasswordController.java</p>
 * <p>描述：后台用户修改密码的方法</p>
 * @author 作者：CrazyDX
 * @version 版本：2016年9月21日下午1:42:30
 */
@Controller
@RequestMapping(value = "/Verwalter/manager/password")
public class TdManagerPasswordController {
	
	@Autowired
	private TdManagerService tdManagerService;

	@RequestMapping
	public String passwordChangeView(HttpServletRequest req, ModelMap map) {
		String username = (String) req.getSession().getAttribute("manager");
		if (null == username) {
			return "redirect:/Verwalter/login";
		}
		return "/site_mag/manager_password";
	}
	
	@RequestMapping(value = "/edit", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> checkInfo(HttpServletRequest req, ModelMap map, String old, String theNew) {
		Map<String, Object> res = new HashMap<>();
		res.put("status", -1);
		String username = (String) req.getSession().getAttribute("manager");
		TdManager manager = tdManagerService.findByUsernameAndIsEnableTrue(username);
		
		if (manager.getPassword().equals(old)) {
			manager.setPassword(theNew);
			tdManagerService.save(manager);
		} else {
			res.put("message", "原密码不正确，操作失败");
			return res;
		}
		
		res.put("status", 0);
		return res;
	}
}
