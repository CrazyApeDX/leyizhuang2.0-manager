package com.ynyes.lyz.controller.management;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ynyes.lyz.entity.TdDiySite;
import com.ynyes.lyz.entity.user.TdUser;
import com.ynyes.lyz.service.TdDiySiteService;
import com.ynyes.lyz.service.TdUserService;

/**
 * <p>标题：TdManagerChangeDiySite.java</p>
 * <p>描述：迁移门店会员的控制器</p>
 * @author 作者：CrazyDX
 * @version 版本：2016年8月31日下午1:58:45
 */
@Controller
@RequestMapping(value = "/Verwalter/change/diy")
public class TdManagerChangeDiySite {

	@Autowired
	private TdDiySiteService tdDiySiteService;
	
	@Autowired
	private TdUserService tdUserService;
	
	@RequestMapping
	public String changeDiy(HttpServletRequest req, ModelMap map) {
		String manager = (String) req.getSession().getAttribute("manager");
		if (null == manager) {
			return "redirect:/Verwalter/login";
		}
		// 查询到所有的门店
		List<TdDiySite> diy_site_list = tdDiySiteService.findAll();
		map.addAttribute("diy_site_list", diy_site_list);
		return "/site_mag/change_diy_site";
	}
	
	@RequestMapping(value = "/save", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> save(Long start, Long end) {
		Map<String, Object> res = new HashMap<>();
		res.put("status", -1);
		
		Integer userCount = null;
		Integer changeCount = 0;
		
		List<TdUser> user_list = tdUserService.findByUpperDiySiteId(start);
		if (null != user_list) {
			userCount = user_list.size();
		}
		
		TdDiySite site = tdDiySiteService.findOne(end);
		if (null != site) {
			for (TdUser user : user_list) {
				user.setUpperDiySiteId(site.getId());
				user.setDiyName(site.getTitle());
				user.setCustomerId(site.getCustomerId());
				tdUserService.save(user);
				changeCount++;
			}
			
			res.put("message", "操作成功，共计" + userCount + "名会员，转移" + changeCount + "名会员");
		} else {
			res.put("message", "未能成功获取到目标门店的信息");
			return res;
		}
		
		res.put("status", 0);
		return res;
	}
}
