package com.ynyes.lyz.controller.front;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ynyes.lyz.entity.TdCity;
import com.ynyes.lyz.entity.TdDiySite;
import com.ynyes.lyz.entity.user.TdUser;
import com.ynyes.lyz.service.TdCityService;
import com.ynyes.lyz.service.TdDiySiteService;
import com.ynyes.lyz.service.TdUserService;

@Controller
@RequestMapping(value = "/diysite")
public class TdDiySiteController {

	@Autowired
	private TdUserService tdUserService;

	@Autowired
	private TdDiySiteService tdDiySiteService;
	
	@Autowired
	private TdCityService tdCityService;

	/**
	 * 跳转到附近门店的方法
	 * 
	 * @author dengxiao
	 */
	@RequestMapping
	public String diySiteList(HttpServletRequest req, ModelMap map) {
		String username = (String) req.getSession().getAttribute("username");
		TdUser user = tdUserService.findByUsernameAndIsEnableTrue(username);
		if (null == user) {
			return "redirect:/login";
		}

		// 获取用户的城市id
		Long cityId = user.getCityId();
		TdCity city = tdCityService.findOne(cityId);
		List<TdDiySite> diysite_list = tdDiySiteService.findByRegionIdOrderBySortIdAsc(city.getSobIdCity());
		// 查找到附近所有的门店
		map.addAttribute("diysite_list", diysite_list);
		return "/client/diy_site_list";
	}
	@RequestMapping(value="testWMS")
	@ResponseBody
	public String testWMS()
	{
//		CallWMSImpl iml = new CallWMSImpl();
//		String result = iml.sendMsgToWMS("test","order");
		return "";
	}
}
