package com.ynyes.lyz.controller.management;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ynyes.lyz.entity.TdBrand;
import com.ynyes.lyz.entity.TdManager;
import com.ynyes.lyz.entity.TdManagerRole;
import com.ynyes.lyz.service.TdBrandService;
import com.ynyes.lyz.service.TdManagerLogService;
import com.ynyes.lyz.service.TdManagerRoleService;
import com.ynyes.lyz.service.TdManagerService;
import com.ynyes.lyz.service.TdProductCategoryService;
import com.ynyes.lyz.util.SiteMagConstant;

/**
 * 后台用户管理控制器
 * 
 * @author Sharon
 */

@Controller
@RequestMapping(value = "/Verwalter/brand")
public class TdManagerBrandController {

	@Autowired
	TdBrandService tdBrandService;

	@Autowired
	TdProductCategoryService tdProductCategoryService;

	@Autowired
	TdManagerLogService tdManagerLogService;

	@Autowired
	TdManagerRoleService tdManagerRoleService;

	@Autowired
	TdManagerService tdManagerService;

	@RequestMapping(value = "/list")
	public String setting(Integer page, Integer size, String keywords, String __EVENTTARGET, String __EVENTARGUMENT,
			String __VIEWSTATE, Long[] listId, Integer[] listChkId, Double[] listSortId, ModelMap map,
			HttpServletRequest req) {
		String username = (String) req.getSession().getAttribute("manager");
		if (null == username) {
			return "redirect:/Verwalter/login";
		}
		// 管理员角色
		TdManager tdManager = tdManagerService.findByUsernameAndIsEnableTrue(username);
		TdManagerRole tdManagerRole = null;

		if (null != tdManager.getRoleId()) {
			tdManagerRole = tdManagerRoleService.findOne(tdManager.getRoleId());
		}

		if (null != tdManagerRole) {
			map.addAttribute("tdManagerRole", tdManagerRole);
		}

		if (null != __EVENTTARGET) {
			if (__EVENTTARGET.equalsIgnoreCase("btnPage")) {
				if (null != __EVENTARGUMENT) {
					page = Integer.parseInt(__EVENTARGUMENT);
				}
			} else if (__EVENTTARGET.equalsIgnoreCase("btnDelete")) {
				btnDelete(listId, listChkId);
				tdManagerLogService.addLog("delete", "删除品牌", req);
			} else if (__EVENTTARGET.equalsIgnoreCase("btnSave")) {
				btnSave(listId, listSortId);
				tdManagerLogService.addLog("edit", "修改品牌", req);
			}
		}

		if (null == page || page < 0) {
			page = 0;
		}

		if (null == size || size <= 0) {
			size = SiteMagConstant.pageSize;
			;
		}

		if (null != keywords) {
			keywords = keywords.trim();
		}

		map.addAttribute("page", page);
		map.addAttribute("size", size);
		map.addAttribute("keywords", keywords);
		map.addAttribute("__EVENTTARGET", __EVENTTARGET);
		map.addAttribute("__EVENTARGUMENT", __EVENTARGUMENT);
		map.addAttribute("__VIEWSTATE", __VIEWSTATE);

		Page<TdBrand> brandPage = null;

		if (null == keywords || "".equalsIgnoreCase(keywords)) {
			brandPage = tdBrandService.findAllOrderBySortIdAsc(page, size);
		} else {
			brandPage = tdBrandService.searchAndOrderBySortIdAsc(keywords, page, size);
		}

		map.addAttribute("brand_page", brandPage);

		return "/site_mag/brand_list";
	}

	@RequestMapping(value = "/check/{type}")
	@ResponseBody
	public Map<String, String> brandCheck(String param, @PathVariable Long type, Long id) {
		Map<String, String> res = new HashMap<String, String>();
		res.put("status", "n");
		if (0L == type) {
			TdBrand brand = null;
			if (null != id) {
				brand = tdBrandService.findOne(id);
				if (null != brand && null != brand.getTitle() && param.equals(brand.getTitle())) {
					res.put("status", "y");
					return res;
				} else {
					brand = tdBrandService.findByTitle(param);
					if (null != brand) {
						res.put("info", "该品牌已存在");
						return res;
					}
				}
			} else {
				brand = tdBrandService.findByTitle(param);
				if (null != brand) {
					res.put("info", "该品牌已存在");
					return res;
				}
			}
		}
		if (1L == type) {
			TdBrand brand = null;
			if (null != id) {
				brand = tdBrandService.findOne(id);
				if (null != brand && null != brand.getShortName() && param.equals(brand.getShortName())) {
					res.put("status", "y");
					return res;
				} else {
					brand = tdBrandService.findByShortName(param);
					if (null != brand) {
						res.put("info", "该别名已存在");
						return res;
					}
				}
			} else {
				brand = tdBrandService.findByShortName(param);
				if (null != brand) {
					res.put("info", "该别名已存在");
					return res;
				}
			}
		}
		res.put("status", "y");
		return res;
	}

	@RequestMapping(value = "/edit")
	public String orderEdit(Long id, String __VIEWSTATE, ModelMap map, HttpServletRequest req) {
		String username = (String) req.getSession().getAttribute("manager");
		if (null == username) {
			return "redirect:/Verwalter/login";
		}

		map.addAttribute("category_list", tdProductCategoryService.findAll());

		map.addAttribute("__VIEWSTATE", __VIEWSTATE);

		if (null != id) {
			map.addAttribute("brand", tdBrandService.findOne(id));
		}
		return "/site_mag/brand_edit";
	}

	@RequestMapping(value = "/save")
	public String orderEdit(TdBrand tdBrand, String __VIEWSTATE, ModelMap map, HttpServletRequest req) {
		String username = (String) req.getSession().getAttribute("manager");
		if (null == username) {
			return "redirect:/Verwalter/login";
		}

		map.addAttribute("__VIEWSTATE", __VIEWSTATE);

		if (null == tdBrand.getId()) {
			tdManagerLogService.addLog("add", "用户修改品牌", req);
		} else {
			tdManagerLogService.addLog("edit", "用户修改品牌", req);
		}

		tdBrandService.save(tdBrand);

		return "redirect:/Verwalter/brand/list";
	}

	@ModelAttribute
	public void getModel(@RequestParam(value = "id", required = false) Long id, Model model) {
		if (null != id) {
			model.addAttribute("tdBrand", tdBrandService.findOne(id));
		}
	}

	private void btnSave(Long[] ids, Double[] sortIds) {
		if (null == ids || null == sortIds || ids.length < 1 || sortIds.length < 1) {
			return;
		}

		for (int i = 0; i < ids.length; i++) {
			Long id = ids[i];

			TdBrand e = tdBrandService.findOne(id);

			if (null != e) {
				if (sortIds.length > i) {
					e.setSortId(new Double(sortIds[i]));
					tdBrandService.save(e);
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

				tdBrandService.delete(id);
			}
		}
	}
}
