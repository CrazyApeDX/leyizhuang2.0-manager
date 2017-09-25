package com.ynyes.lyz.controller.management;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ynyes.lyz.entity.TdReturnReason;
import com.ynyes.lyz.service.TdManagerLogService;
import com.ynyes.lyz.service.TdReturnReasonService;
import com.ynyes.lyz.util.SiteMagConstant;

/**
 * <p>标题：TdManagerReturnReasonController.java</p>
 * <p> 描述：后台管理系统与退货原因相关的控制器</p>
 * <p>公司：http://www.ynsite.com</p>
 * @author 作者：DengXiao
 * @version 版本：上午10:54:53
 */
@Controller
@RequestMapping(value = "/Verwalter/returnReason")
public class TdManagerReturnReasonController {

	@Autowired
	private TdReturnReasonService tdReturnReasonService;

	@Autowired
	private TdManagerLogService tdManagerLogService;

	@RequestMapping(value = "/list")
	public String returnReasonList(Integer page, Integer size, String keywords, String __EVENTTARGET,
			String __EVENTARGUMENT, String __VIEWSTATE, Long[] listId, Integer[] listChkId, Double[] listSortId,
			ModelMap map, HttpServletRequest req) {
		String username = (String) req.getSession().getAttribute("manager");
		if (null == username) {
			return "redirect:/Verwalter/login";
		}

		if (null != __EVENTTARGET) {
			if (__EVENTTARGET.equalsIgnoreCase("btnDelete")) {
				btnDelete(listId, listChkId);
				tdManagerLogService.addLog("delete", "用户删除退货原因", req);
			} else if (__EVENTTARGET.equalsIgnoreCase("btnSave")) {
				btnSave(listId, listSortId);
				tdManagerLogService.addLog("edit", "用户修改退货原因", req);
			} else if (__EVENTTARGET.equalsIgnoreCase("btnPage")) {
				if (null != __EVENTARGUMENT) {
					page = Integer.parseInt(__EVENTARGUMENT);
				}
			}
		}
		
		if (null == page || page < 0) {
			page = 0;
		}

		if (null == size || size <= 0) {
			size = SiteMagConstant.pageSize;
		}

		map.addAttribute("page", page);
		map.addAttribute("size", size);
		map.addAttribute("__EVENTTARGET", __EVENTTARGET);
		map.addAttribute("__EVENTARGUMENT", __EVENTARGUMENT);
		map.addAttribute("__VIEWSTATE", __VIEWSTATE);

		map.addAttribute("reason_page", tdReturnReasonService.findAll(page, size));

		
		return "/site_mag/return_reason_list";
	}
	
	@RequestMapping(value = "/edit")
	public String orderEdit(Long id, String __VIEWSTATE, ModelMap map, HttpServletRequest req) {
		String username = (String) req.getSession().getAttribute("manager");
		if (null == username) {
			return "redirect:/Verwalter/login";
		}

		map.addAttribute("__VIEWSTATE", __VIEWSTATE);

		if (null != id) {
			map.addAttribute("reason", tdReturnReasonService.findOne(id));
		}
		return "/site_mag/return_reason_edit";
	}
	
	@RequestMapping(value = "/save")
	public String orderEdit(TdReturnReason reason, String __VIEWSTATE, ModelMap map, HttpServletRequest req) {
		String username = (String) req.getSession().getAttribute("manager");
		if (null == username) {
			return "redirect:/Verwalter/login";
		}

		map.addAttribute("__VIEWSTATE", __VIEWSTATE);

		String type = null;

		if (null == reason.getId()) {
			type = "add";
		} else {
			type = "edit";
		}

		tdReturnReasonService.save(reason);

		tdManagerLogService.addLog(type, "用户修改退货原因", req);

		return "redirect:/Verwalter/returnReason/list";
	}

	private void btnSave(Long[] ids, Double[] sortIds) {
		if (null == ids || null == sortIds || ids.length < 1 || sortIds.length < 1) {
			return;
		}

		for (int i = 0; i < ids.length; i++) {
			Long id = ids[i];

			TdReturnReason e = tdReturnReasonService.findOne(id);

			if (null != e) {
				if (sortIds.length > i) {
					e.setSortId(new Double(sortIds[i]));
					tdReturnReasonService.save(e);
				}
			}
		}
	}

	private void btnDelete(Long[] ids, Integer[] chkIds) {
		if (null == ids || null == chkIds || ids.length < 1 || chkIds.length < 1) {
			return;
		}

		for (int chkId : chkIds) {
			if (chkId >= 0 && ids.length > chkId) {
				Long id = ids[chkId];

				tdReturnReasonService.delete(id);
			}
		}
	}
}
