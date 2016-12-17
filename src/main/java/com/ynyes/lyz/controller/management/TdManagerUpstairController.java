package com.ynyes.lyz.controller.management;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.ynyes.lyz.entity.TdCity;
import com.ynyes.lyz.entity.TdManager;
import com.ynyes.lyz.entity.upstairs.TdUpstairsSetting;
import com.ynyes.lyz.service.TdCityService;
import com.ynyes.lyz.service.TdManagerLogService;
import com.ynyes.lyz.service.TdManagerService;
import com.ynyes.lyz.service.TdUpstairsSettingService;

/**
 * @author yanle
 * 上楼费控制器
 *
 */
@Controller
@RequestMapping("/Verwalter/upstair/fee")
public class TdManagerUpstairController {

	@Autowired
	private TdUpstairsSettingService tdUpstairsSettingService;
	
	@Autowired
	private TdCityService tdCityService;
	
	@Autowired
	private TdManagerService tdManagerService;
	
	@Autowired
	private TdManagerLogService tdManagerLogService;


	@RequestMapping(value = "/edit/{sobId}", produces = "text/html;charset=utf-8")
	public String upstairFeeEdit(HttpServletRequest request, ModelMap map, @PathVariable("sobId") Long sobId) {
		String username = (String) request.getSession().getAttribute("manager");
		if (null == username) {
			return "redirect:/Verwalter/login";
		}
		TdManager manager = tdManagerService.findByUsernameAndIsEnableTrue(username);
		if (manager == null)
		{
			return "redirect:/Verwalter/login";
		}
		TdCity city = tdCityService.findBySobIdCity(sobId);
		if (null != city) {
			map.addAttribute("cityName", city.getCityName());
		}
		TdUpstairsSetting tdUpstairsSetting = tdUpstairsSettingService.findBySobIdCity(sobId);

		map.addAttribute("tdUpstairsSetting", tdUpstairsSetting);
		map.addAttribute("sobId", sobId);
		
		return "/site_mag/upstair_fee_edit";
	}
	
	@RequestMapping(value = "/save",method=RequestMethod.POST)
	public String upstairFeeSave(TdUpstairsSetting tdUpstairsSetting,HttpServletRequest req){
		String username = (String) req.getSession().getAttribute("manager");
		if (null == username) {
			return "redirect:/Verwalter/login";
		}
		TdManager manager = tdManagerService.findByUsernameAndIsEnableTrue(username);
		if (manager == null)
		{
			return "redirect:/Verwalter/login";
		}
		tdUpstairsSettingService.save(tdUpstairsSetting);
		tdManagerLogService.addLog("edit", "运费配置", req);
		return "redirect:/Verwalter/setting/city/list";
	}
}
