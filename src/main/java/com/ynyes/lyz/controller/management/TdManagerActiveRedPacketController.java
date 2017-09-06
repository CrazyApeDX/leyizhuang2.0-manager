package com.ynyes.lyz.controller.management;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.ynyes.lyz.entity.TdActiveRedPacket;
import com.ynyes.lyz.service.TdActiveRedPacketService;
import com.ynyes.lyz.service.TdCityService;
import com.ynyes.lyz.service.TdManagerLogService;
import com.ynyes.lyz.util.SiteMagConstant;

@Controller
@RequestMapping("/Verwalter/redPacket")
public class TdManagerActiveRedPacketController {
	
	@Autowired
	private TdActiveRedPacketService tdActiveRedPacketService;
	
	@Autowired
	private TdCityService tdCityService;
	

	@RequestMapping("/list")
	public String redPacketList(Integer page, Integer size,  String keywords,
			Long[] listId,Integer[] listChkId,String __EVENTTARGET, String __EVENTARGUMENT, String __VIEWSTATE,
			Long[] listSortId, ModelMap map, HttpServletRequest req) {
		String username = (String) req.getSession().getAttribute("manager");
		if (null == username) {
			return "redirect:/Verwalter/login";
		}

		if (null == page || page < 0) {
			page = 0;
		}

		if (null == size || size <= 0) {
			size = SiteMagConstant.pageSize;
		}

		if (null != keywords) {
			keywords = keywords.trim();
		}
		if (null != __EVENTTARGET) {
			switch (__EVENTTARGET) {
			case "lbtnViewTxt":
			case "lbtnViewImg":
				__VIEWSTATE = __EVENTTARGET;
				break;

			case "btnDelete":
				btnDelete(listId, listChkId);
				
				break;

			case "btnPage":
				if (null != __EVENTARGUMENT) {
					page = Integer.parseInt(__EVENTARGUMENT);
				}
				break;
			}
		}

		if (null != __EVENTTARGET && __EVENTTARGET.equalsIgnoreCase("lbtnViewTxt")
				|| null != __EVENTTARGET && __EVENTTARGET.equalsIgnoreCase("lbtnViewImg")) {
			__VIEWSTATE = __EVENTTARGET;
		}
		// 参数注回
		map.addAttribute("page", page);
		map.addAttribute("size", size);
		map.addAttribute("keywords", keywords);
		map.addAttribute("__EVENTTARGET", __EVENTTARGET);
		map.addAttribute("__EVENTARGUMENT", __EVENTARGUMENT);
		map.addAttribute("__VIEWSTATE", __VIEWSTATE);
		map.addAttribute("redpacket_page", tdActiveRedPacketService.findAll(page, size));
		return "/site_mag/activity_redpacket_list";
	}
	
	@RequestMapping("/add")
	public String redPacketAdd(ModelMap map, HttpServletRequest req) {
		String username = (String) req.getSession().getAttribute("manager");
		if (null == username) {
			return "redirect:/Verwalter/login";
		}
		map.addAttribute("city_list", tdCityService.findAll());
		return "/site_mag/activity_redpacket_edit";
	}
	
	@RequestMapping("/edit")
	public String redPacketEdit(Long fns,Long id,ModelMap map, HttpServletRequest req) {
		String username = (String) req.getSession().getAttribute("manager");
		if (null == username) {
			return "redirect:/Verwalter/login";
		}
		map.addAttribute("city_list", tdCityService.findAll());
		map.addAttribute("activity", tdActiveRedPacketService.findOne(id));
		if (null != fns) {
			map.addAttribute("fns", fns);
		}
		return "/site_mag/activity_redpacket_edit";
	}
	
	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public String redPacketSave(TdActiveRedPacket redpacket,HttpServletRequest req){
		
		String username = (String) req.getSession().getAttribute("manager");
		if (null == username) {
			return "redirect:/Verwalter/login";
		}
		
		String type = "";
		if (null == redpacket.getId()) {
			type = "add";
		} else {
			type = "edit";
		}
		
		tdActiveRedPacketService.save(redpacket);
		
		//tdManagerLogService.addLog(type, "用户修改红包活动", req);
		
		// 保存成功提示
		Long fns = 1L;
		
		return "redirect:/Verwalter/redPacket/edit?fns=" + fns + "&id=" + redpacket.getId();
	}
	
	/**
	 * 删除红包
	 * 
	 * @param ids
	 * @param chkIds
	 */
	private void btnDelete(Long[] ids, Integer[] chkIds) {
		if (null == ids || null == chkIds || ids.length < 1 || chkIds.length < 1) {
			return;
		}

		for (int chkId : chkIds) {
			if (chkId >= 0 && ids.length > chkId) {
				Long id = ids[chkId];

				tdActiveRedPacketService.delete(id);
			}
		}
	}
}
