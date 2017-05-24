package com.ynyes.lyz.controller.management;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ynyes.fitment.core.constant.Global;
import com.ynyes.lyz.entity.TdDiySiteAccount;
import com.ynyes.lyz.entity.TdOrder;
import com.ynyes.lyz.entity.user.TdUser;
import com.ynyes.lyz.service.TdDiySiteAccountService;
import com.ynyes.lyz.service.TdDiySiteService;
import com.ynyes.lyz.service.TdUserService;

@Controller
@RequestMapping(value = "/Verwalter/diySiteAccount")
public class TdManagerDiySiteAccountController {
	
	@Autowired
	private TdDiySiteAccountService tdDiySiteAccountService;
	
	@Autowired
	private TdDiySiteService tdDiySiteService;
	
	@Autowired
	private TdUserService tdUserService;
	
	@RequestMapping(value = "/diySite/list")
	public String goodsList(Integer page, Integer size, String __EVENTTARGET,
			String __EVENTARGUMENT, String __VIEWSTATE, String keywords,  ModelMap map, HttpServletRequest req) {
		String username = (String) req.getSession().getAttribute("manager");
		if (null == username) {
			return "redirect:/Verwalter/login";
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
		String custTypeName = "经销商";
		page = null == page ? Global.DEFAULT_PAGE : page;
		size = null == size ? Global.DEFAULT_SIZE : size;
		if (null == keywords) {
			keywords = "";
		}
		
		map.addAttribute("diy_site_page", tdDiySiteService.findByCustTypeName(custTypeName, keywords, page, size));
		map.addAttribute("page", page);
		map.addAttribute("size", size);
		map.addAttribute("keywords", keywords);
		map.addAttribute("__EVENTTARGET", __EVENTTARGET);
		map.addAttribute("__EVENTARGUMENT", __EVENTARGUMENT);
		map.addAttribute("__VIEWSTATE", __VIEWSTATE);
		// 文字列表模式
		return "/site_mag/diySite_jx_list";
	}
	
	@RequestMapping(value = "/setting/edit", produces = "text/html;charset=utf-8")
	public String companyEdit(Long id, String diySiteName, ModelMap map) {
		
		List<TdUser> userList = null;
		userList = tdUserService.findByUpperDiySiteIdAndIsEnableTrue(id);
		TdDiySiteAccount diySiteAccount = tdDiySiteAccountService.findByDiySiteId(id);
		map.addAttribute("userList", userList);
		map.addAttribute("diySiteName", diySiteName);
		map.addAttribute("id", id);
		map.addAttribute("diySiteAccount", diySiteAccount);
	 return "/site_mag/diySiteAccount_edit";
	}
	
	
	@RequestMapping(value = "/setting/save")
	public String save(Long diySiteId, String diySiteTitle, String user, String __VIEWSTATE, ModelMap map, HttpServletRequest req) {
		String username = (String) req.getSession().getAttribute("manager");
		if (null == username) {
			return "redirect:/Verwalter/login";
		}

		map.addAttribute("__VIEWSTATE", __VIEWSTATE);
		
		TdDiySiteAccount tdDiySiteAccount = new TdDiySiteAccount();
		tdDiySiteAccount.setDiySiteId(diySiteId);
		tdDiySiteAccount.setDiySiteTitle(diySiteTitle);
		String[] userInfo = user.trim().split(",");
		if (2==userInfo.length) {
			tdDiySiteAccount.setUserId(Long.parseLong(userInfo[0]));
			tdDiySiteAccount.setUsername(userInfo[1]);
		}
		tdDiySiteAccountService.save(tdDiySiteAccount);
			
		return "redirect:/Verwalter/diySiteAccount/diySite/list";
	}
	
	

}
