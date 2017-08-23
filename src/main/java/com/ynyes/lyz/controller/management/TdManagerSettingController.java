package com.ynyes.lyz.controller.management;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.ynyes.lyz.entity.TdCategoryLimit;
import com.ynyes.lyz.entity.TdCity;
import com.ynyes.lyz.entity.TdCompany;
import com.ynyes.lyz.entity.TdDistrict;
import com.ynyes.lyz.entity.TdMessageType;
import com.ynyes.lyz.entity.TdProductCategory;
import com.ynyes.lyz.entity.TdServiceItem;
import com.ynyes.lyz.entity.TdSetting;
import com.ynyes.lyz.entity.TdSmsAccount;
import com.ynyes.lyz.entity.TdStorage;
import com.ynyes.lyz.entity.TdSubdistrict;
import com.ynyes.lyz.entity.TdUserSuggestionCategory;
import com.ynyes.lyz.entity.TdWareHouse;
import com.ynyes.lyz.service.TdCategoryLimitService;
import com.ynyes.lyz.service.TdCityService;
import com.ynyes.lyz.service.TdCompanyService;
import com.ynyes.lyz.service.TdDistrictService;
import com.ynyes.lyz.service.TdManagerLogService;
import com.ynyes.lyz.service.TdMessageTypeService;
import com.ynyes.lyz.service.TdProductCategoryService;
import com.ynyes.lyz.service.TdServiceItemService;
import com.ynyes.lyz.service.TdSettingService;
import com.ynyes.lyz.service.TdSmsAccountService;
import com.ynyes.lyz.service.TdStorageService;
import com.ynyes.lyz.service.TdSubdistrictService;
import com.ynyes.lyz.service.TdUserSuggestionCategoryService;
import com.ynyes.lyz.service.TdWareHouseService;
import com.ynyes.lyz.util.SiteMagConstant;

/**
 * 后台广告管理控制器
 * 
 * @author Sharon
 */

@Controller
@RequestMapping(value = "/Verwalter/setting")
public class TdManagerSettingController {

	@Autowired
	TdSettingService tdSettingService;

	@Autowired
	TdManagerLogService tdManagerLogService;

	@Autowired
	TdServiceItemService tdServiceItemService;

	@Autowired
	private TdCompanyService tdCompanyService;

	@Autowired
	TdSmsAccountService tdSmsAccountService;

	@Autowired
	private TdCityService tdCityService;

	@Autowired
	private TdDistrictService tdDistrictService;

	@Autowired
	private TdSubdistrictService tdSubdistrictService;

	@Autowired
	TdUserSuggestionCategoryService tdUserSuggestionCategoryService; // zhangji
																		// 2016-1-3
	// 13:37:08

	@Autowired
	TdMessageTypeService tdMessageTypeService; // zhangji 2016-1-3 13:37:23

	@Autowired
	TdStorageService tdStorageService; // zhangji 2016-1-8 9:48:44

	@Autowired
	TdWareHouseService WareHouseService; // 华仔 2016-3-11 17:40:44

	@Autowired
	private TdProductCategoryService tdProductCategoryService;

	@Autowired
	private TdCategoryLimitService tdCategoryLimitService;

	@RequestMapping
	public String setting(Long status, ModelMap map, HttpServletRequest req) {
		String username = (String) req.getSession().getAttribute("manager");
		if (null == username) {
			return "redirect:/Verwalter/login";
		}

		map.addAttribute("setting", tdSettingService.findTopBy());
		map.addAttribute("status", status);

		return "/site_mag/setting_edit";
	}

	@RequestMapping(value = "/save")
	public String orderEdit(TdSetting tdSetting, ModelMap map, HttpServletRequest req) {
		String username = (String) req.getSession().getAttribute("manager");
		if (null == username) {
			return "redirect:/Verwalter/login";
		}

		if (null == tdSetting.getId()) {
			tdManagerLogService.addLog("add", "用户修改系统设置", req);
		} else {
			tdManagerLogService.addLog("edit", "用户修改系统设置", req);
		}

		tdSettingService.save(tdSetting);

		return "redirect:/Verwalter/setting?status=1";
	}

	@RequestMapping(value = "/service/list")
	public String service(String __EVENTTARGET, String __EVENTARGUMENT, String __VIEWSTATE, Long[] listId,
			Integer[] listChkId, Double[] listSortId, ModelMap map, HttpServletRequest req) {
		String username = (String) req.getSession().getAttribute("manager");
		if (null == username) {
			return "redirect:/Verwalter/login";
		}

		if (null != __EVENTTARGET) {
			if (__EVENTTARGET.equalsIgnoreCase("btnDelete")) {
				btnDelete(listId, listChkId);

				tdManagerLogService.addLog("edit", "删除服务", req);
			} else if (__EVENTTARGET.equalsIgnoreCase("btnSave")) {
				btnSave(listId, listSortId);

				tdManagerLogService.addLog("edit", "修改服务", req);
			}
		}

		map.addAttribute("__EVENTTARGET", __EVENTTARGET);
		map.addAttribute("__EVENTARGUMENT", __EVENTARGUMENT);
		map.addAttribute("__VIEWSTATE", __VIEWSTATE);

		map.addAttribute("service_item_list", tdServiceItemService.findAllOrderBySortIdAsc());

		return "/site_mag/service_item_list";
	}

	@RequestMapping(value = "/service/edit")
	public String edit(Long id, String __VIEWSTATE, ModelMap map, HttpServletRequest req) {
		String username = (String) req.getSession().getAttribute("manager");

		if (null == username) {
			return "redirect:/Verwalter/login";
		}

		map.addAttribute("__VIEWSTATE", __VIEWSTATE);

		if (null != id) {
			map.addAttribute("service_item", tdServiceItemService.findOne(id));
		}

		return "/site_mag/service_item_edit";
	}

	@RequestMapping(value = "/service/save", method = RequestMethod.POST)
	public String serviceItemEdit(TdServiceItem tdServiceItem, String __VIEWSTATE, ModelMap map,
			HttpServletRequest req) {
		String username = (String) req.getSession().getAttribute("manager");
		if (null == username) {
			return "redirect:/Verwalter/login";
		}

		map.addAttribute("__VIEWSTATE", __VIEWSTATE);

		tdServiceItemService.save(tdServiceItem);

		tdManagerLogService.addLog("edit", "修改商城服务", req);

		return "redirect:/Verwalter/setting/service/list";
	}

	/**
	 * 子公司维护-列表
	 * 
	 * @param page
	 * @param size
	 * @param __EVENTTARGET
	 * @param __EVENTARGUMENT
	 * @param __VIEWSTATE
	 * @param action
	 * @param listId
	 * @param listChkId
	 * @param map
	 * @param req
	 * @return
	 */
	@RequestMapping(value = "/company/list")
	public String settingCompanyList(Integer page, Integer size, String __EVENTTARGET, String __EVENTARGUMENT,
			String __VIEWSTATE, String action, Long[] listId, Integer[] listChkId, ModelMap map,
			HttpServletRequest req) {
		String username = (String) req.getSession().getAttribute("manager");
		if (null == username) {
			return "redirect:/Verwalter/login";
		}
		if (null != __EVENTTARGET) {
			if (__EVENTTARGET.equalsIgnoreCase("btnDelete")) {
				btnDeleteCompany(listId, listChkId);
				tdManagerLogService.addLog("delete", "删除子公司", req);
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
			;
		}

		map.addAttribute("page", page);
		map.addAttribute("size", size);
		map.addAttribute("action", action);
		map.addAttribute("__EVENTTARGET", __EVENTTARGET);
		map.addAttribute("__EVENTARGUMENT", __EVENTARGUMENT);
		map.addAttribute("__VIEWSTATE", __VIEWSTATE);
		map.addAttribute("company_page", tdCompanyService.findAll(page, size));
		return "/site_mag/company_list";
	}

	@RequestMapping(value = "/company/edit")
	public String cityEdit(Long id, String __VIEWSTATE, ModelMap map, HttpServletRequest req) {
		String username = (String) req.getSession().getAttribute("manager");

		if (null == username) {
			return "redirect:/Verwalter/login";
		}

		map.addAttribute("__VIEWSTATE", __VIEWSTATE);

		if (null != id) {
			map.addAttribute("company", tdCompanyService.findOne(id));
		}

		return "/site_mag/company_edit";
	}

	@RequestMapping(value = "/company/save", method = RequestMethod.POST)
	public String citySave(TdCompany tdCompany, String __VIEWSTATE, ModelMap map, HttpServletRequest req) {
		String username = (String) req.getSession().getAttribute("manager");
		if (null == username) {
			return "redirect:/Verwalter/login";
		}

		map.addAttribute("__VIEWSTATE", __VIEWSTATE);
		String type = null;
		if (null == tdCompany.getId()) {
			type = "add";
		} else {
			type = "edit";
		}
		tdCompanyService.save(tdCompany);

		tdManagerLogService.addLog(type, "修改子公司", req);

		return "redirect:/Verwalter/setting/company/list";
	}

	/**
	 * 城市维护-列表
	 * 
	 * @param page
	 * @param size
	 * @param __EVENTTARGET
	 * @param __EVENTARGUMENT
	 * @param __VIEWSTATE
	 * @param action
	 * @param listId
	 * @param listChkId
	 * @param map
	 * @param req
	 * @return
	 */
	@RequestMapping(value = "/city/list")
	public String settingCityList(Integer page, Integer size, String __EVENTTARGET, String __EVENTARGUMENT,
			String __VIEWSTATE, String action, Long[] listId, Integer[] listChkId, ModelMap map,
			HttpServletRequest req) {
		String username = (String) req.getSession().getAttribute("manager");
		if (null == username) {
			return "redirect:/Verwalter/login";
		}
		if (null != __EVENTTARGET) {
			if (__EVENTTARGET.equalsIgnoreCase("btnDelete")) {
				btnDeleteCity(listId, listChkId);
				tdManagerLogService.addLog("delete", "删除城市", req);
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
			;
		}

		map.addAttribute("page", page);
		map.addAttribute("size", size);
		map.addAttribute("action", action);
		map.addAttribute("__EVENTTARGET", __EVENTTARGET);
		map.addAttribute("__EVENTARGUMENT", __EVENTARGUMENT);
		map.addAttribute("__VIEWSTATE", __VIEWSTATE);
		map.addAttribute("city_page", tdCityService.findAll(page, size));
		return "/site_mag/city_list";
	}

	@RequestMapping(value = "/city/edit")
	public String companyEdit(Long id, String __VIEWSTATE, ModelMap map, HttpServletRequest req) {
		String username = (String) req.getSession().getAttribute("manager");

		if (null == username) {
			return "redirect:/Verwalter/login";
		}

		map.addAttribute("__VIEWSTATE", __VIEWSTATE);

		TdCity city = tdCityService.findOne(id);

		if (null != id) {
			map.addAttribute("city", city);
		}
		map.addAttribute("SMSAccount_list", tdSmsAccountService.findAll());
		map.addAttribute("company_list", tdCompanyService.findAll());

		// 查询所有的一级分类
		List<TdProductCategory> level_one = tdProductCategoryService.findByParentIdIsNullOrderBySortIdAsc();
		map.addAttribute("level_one", level_one);
		// 根据一级分类查找二级分类
		if (null != level_one) {
			for (int i = 0; i < level_one.size(); i++) {
				TdProductCategory category = level_one.get(i);
				// 根据指定的一级分类查找其下所属的二级分类
				List<TdProductCategory> level_two = tdProductCategoryService
						.findByParentIdOrderBySortIdAsc(category.getId());
				map.addAttribute("level_two" + i, level_two);
			}
		}

		// 查找指定城市的限购信息
		if (null != city) {
			List<TdCategoryLimit> limits = tdCategoryLimitService.findBySobId(city.getSobIdCity());
			if (null != limits) {
				for (TdCategoryLimit limit : limits) {
					if (null != limit) {
						map.addAttribute("limit" + limit.getCategoryId(), true);
					}
				}
			}
		}
		return "/site_mag/city_edit";
	}

	@RequestMapping(value = "/city/save", method = RequestMethod.POST)
	public String companySave(TdCity tdCity, String __VIEWSTATE, ModelMap map, HttpServletRequest req, String limit) {
		String username = (String) req.getSession().getAttribute("manager");
		if (null == username) {
			return "redirect:/Verwalter/login";
		}

		map.addAttribute("__VIEWSTATE", __VIEWSTATE);
		String type = null;
		if (null == tdCity.getId()) {
			type = "add";
		} else {
			type = "edit";
		}

		TdCompany tdCompany = tdCompanyService.findOne(tdCity.getCompanyId());
		tdCity.setSobIdCity(tdCompany.getSobIdCompany());

		tdCity = tdCityService.save(tdCity);

		tdManagerLogService.addLog(type, "修改子公司", req);

		/**
		 * 添加分类限购的存储
		 * 
		 * @author DengXiao
		 */
		if (null != limit && !"".equals(limit)) {
			// 拆分limit
			String[] cats = limit.split(",");
			if (null != cats && cats.length > 0) {
				// 删除数据库中该城市的分类限购信息
				tdCategoryLimitService.deleteCityLimits(tdCity.getSobIdCity());
				for (String sId : cats) {
					// 获取分类id
					if (null != sId && !"".equals(sId)) {
						// 获取到分类的id
						Long id = Long.parseLong(sId);
						// 获取指定的分类
						TdProductCategory category = tdProductCategoryService.findOne(id);

						// 创建一个新的limit
						TdCategoryLimit cateLimit = new TdCategoryLimit();
						cateLimit.setSobId(tdCity.getSobIdCity());
						cateLimit.setCategoryId(category.getId());
						cateLimit.setTitle(category.getTitle());
						cateLimit.setParentId(category.getParentId());
						cateLimit.setParentTree(category.getParentTree());
						cateLimit.setLayerCount(category.getLayerCount());
						cateLimit.setInvCategoryId(cateLimit.getInvCategoryId());
						cateLimit.setSortId(category.getSortId());
						cateLimit.setMainNumber(category.getMainNumber());

						tdCategoryLimitService.save(cateLimit);
					}
				}
			}
		}

		return "redirect:/Verwalter/setting/city/list";
	}

	/**
	 * 行政区划-列表
	 * 
	 * @param page
	 * @param size
	 * @param __EVENTTARGET
	 * @param __EVENTARGUMENT
	 * @param __VIEWSTATE
	 * @param action
	 * @param listId
	 * @param listChkId
	 * @param map
	 * @param req
	 * @return
	 */
	@RequestMapping(value = "/district/list")
	public String settingDistrictList(Integer page, Integer size, String __EVENTTARGET, String __EVENTARGUMENT,
			String __VIEWSTATE, String action, Long[] listId, Integer[] listChkId, ModelMap map,
			HttpServletRequest req) {
		String username = (String) req.getSession().getAttribute("manager");
		if (null == username) {
			return "redirect:/Verwalter/login";
		}
		if (null != __EVENTTARGET) {
			if (__EVENTTARGET.equalsIgnoreCase("btnDelete")) {
				btnDeleteDistrict(listId, listChkId);
				tdManagerLogService.addLog("delete", "删除行政区划", req);
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
			;
		}

		map.addAttribute("page", page);
		map.addAttribute("size", size);
		map.addAttribute("action", action);
		map.addAttribute("__EVENTTARGET", __EVENTTARGET);
		map.addAttribute("__EVENTARGUMENT", __EVENTARGUMENT);
		map.addAttribute("__VIEWSTATE", __VIEWSTATE);
		map.addAttribute("district_page", tdDistrictService.findAll(page, size));
		return "/site_mag/district_list";
	}

	@RequestMapping(value = "/district/edit")
	public String districtEdit(Long id, String __VIEWSTATE, ModelMap map, HttpServletRequest req) {
		String username = (String) req.getSession().getAttribute("manager");

		if (null == username) {
			return "redirect:/Verwalter/login";
		}

		map.addAttribute("__VIEWSTATE", __VIEWSTATE);

		if (null != id) {
			map.addAttribute("district", tdDistrictService.findOne(id));
		}
		map.addAttribute("city_list", tdCityService.findAll());
		return "/site_mag/district_edit";
	}

	@RequestMapping(value = "/district/save", method = RequestMethod.POST)
	public String districtSave(TdDistrict tdDistrict, String __VIEWSTATE, ModelMap map, HttpServletRequest req) {
		String username = (String) req.getSession().getAttribute("manager");
		if (null == username) {
			return "redirect:/Verwalter/login";
		}

		map.addAttribute("__VIEWSTATE", __VIEWSTATE);
		String type = null;
		if (null == tdDistrict.getId()) {
			type = "add";
		} else {
			type = "edit";
		}
		tdDistrictService.save(tdDistrict);

		tdManagerLogService.addLog(type, "修改行政区划", req);

		return "redirect:/Verwalter/setting/district/list";
	}

	/**
	 * 行政街道-列表
	 * 
	 * @param page
	 * @param size
	 * @param __EVENTTARGET
	 * @param __EVENTARGUMENT
	 * @param __VIEWSTATE
	 * @param action
	 * @param listId
	 * @param listChkId
	 * @param map
	 * @param req
	 * @return
	 */
	@RequestMapping(value = "/subdistrict/list")
	public String settingSubistrictList(Integer page, Integer size, String __EVENTTARGET, String __EVENTARGUMENT,
			String __VIEWSTATE, String action, Long[] listId, Integer[] listChkId, ModelMap map,
			HttpServletRequest req) {
		String username = (String) req.getSession().getAttribute("manager");
		if (null == username) {
			return "redirect:/Verwalter/login";
		}
		if (null != __EVENTTARGET) {
			if (__EVENTTARGET.equalsIgnoreCase("btnDelete")) {
				btnDeleteSubdistrict(listId, listChkId);
				tdManagerLogService.addLog("delete", "删除行政街道", req);
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
			;
		}

		map.addAttribute("page", page);
		map.addAttribute("size", size);
		map.addAttribute("action", action);
		map.addAttribute("__EVENTTARGET", __EVENTTARGET);
		map.addAttribute("__EVENTARGUMENT", __EVENTARGUMENT);
		map.addAttribute("__VIEWSTATE", __VIEWSTATE);
		map.addAttribute("subdistrict_page", tdSubdistrictService.findAll(page, size));

		Page<TdSubdistrict> findAll = tdSubdistrictService.findAll(page, size);

		List<TdSubdistrict> content = findAll.getContent();
		for (int i = 0; i < content.size(); i++) {
			TdSubdistrict sub = content.get(i);
			if (sub.getCity() == null || sub.getCity().equals("")) {
				Long disId = sub.getDistrictId();
				TdDistrict district = tdDistrictService.findOne(disId);
				if (null != district) {
					Long cityId = district.getCityId();
					String name = tdCityService.findOne(cityId).getCityName();
					sub.setCity(name);
				}
				tdSubdistrictService.save(sub);
			}
		}
		return "/site_mag/subdistrict_list";
	}

	@RequestMapping(value = "/subdistrict/edit")
	public String subdistrictEdit(Long id, String __VIEWSTATE, ModelMap map, HttpServletRequest req) {
		String username = (String) req.getSession().getAttribute("manager");

		if (null == username) {
			return "redirect:/Verwalter/login";
		}

		map.addAttribute("__VIEWSTATE", __VIEWSTATE);

		if (null != id) {
			map.addAttribute("subdistrict", tdSubdistrictService.findOne(id));
		}

		map.addAttribute("district_list", tdDistrictService.findAll());
		return "/site_mag/subdistrict_edit";
	}

	@RequestMapping(value = "/subdistrict/save", method = RequestMethod.POST)
	public String subdistrictSave(TdSubdistrict tdSubdistrict, String __VIEWSTATE, ModelMap map,
			HttpServletRequest req) {
		String username = (String) req.getSession().getAttribute("manager");
		if (null == username) {
			return "redirect:/Verwalter/login";
		}

		map.addAttribute("__VIEWSTATE", __VIEWSTATE);
		String type = null;
		if (null == tdSubdistrict.getId()) {
			type = "add";
		} else {
			type = "edit";
		}
		tdSubdistrictService.save(tdSubdistrict);

		tdManagerLogService.addLog(type, "修改行政街道", req);

		return "redirect:/Verwalter/setting/subdistrict/list";
	}

	/* -------------------------短信账户 begin -------------------------- */
	@RequestMapping(value = "/{type}/list")
	public String settingTypeList(Integer page, @PathVariable String type, Integer size, String __EVENTTARGET,
			String __EVENTARGUMENT, String __VIEWSTATE, String action, Long[] listId, Integer[] listChkId, ModelMap map,
			HttpServletRequest req) {
		String username = (String) req.getSession().getAttribute("manager");
		if (null == username) {
			return "redirect:/Verwalter/login";
		}
		if (null != __EVENTTARGET) {
			if (__EVENTTARGET.equalsIgnoreCase("btnDelete")) {
				btnTypeDelete(type, listId, listChkId);
				switch (type) {
				case "smsAccount":
					tdManagerLogService.addLog("delete", "删除短信账户", req);
					break;
				case "userSuggestionCategory":
					tdManagerLogService.addLog("delete", "删除投诉咨询", req);
					break;
				case "messageType":
					tdManagerLogService.addLog("delete", "删除信息", req);
					break;
				case "storage":
					tdManagerLogService.addLog("delete", "删除仓库", req);
					break;
				default:
					tdManagerLogService.addLog("delete", "删除信息", req);
				}

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
			;
		}

		map.addAttribute("page", page);
		map.addAttribute("size", size);
		map.addAttribute("action", action);
		map.addAttribute("__EVENTTARGET", __EVENTTARGET);
		map.addAttribute("__EVENTARGUMENT", __EVENTARGUMENT);
		map.addAttribute("__VIEWSTATE", __VIEWSTATE);
		switch (type) {
		case "smsAccount":
			map.addAttribute("sms_page", tdSmsAccountService.findAll(page, size));
			return "/site_mag/smsAccount_list";

		case "userSuggestionCategory":
			map.addAttribute("userSuggestionCategory_list", tdUserSuggestionCategoryService.findAll());
			return "/site_mag/user_suggestion_category_list";

		case "messageType":
			map.addAttribute("messageType_list", tdMessageTypeService.findAll());
			return "/site_mag/message_type_list";

		case "storage":
			map.addAttribute("storage_list", tdStorageService.findAll());
			return "/site_mag/storage_list";

		default:
			map.addAttribute("sms_page", tdSmsAccountService.findAll(page, size));
		}

		return "/site_mag/smsAccount_list";
	}

	@RequestMapping(value = "/{type}/edit")
	public String settingTypeEdit(Long id, @PathVariable String type, String __VIEWSTATE, ModelMap map,
			HttpServletRequest req) {
		String username = (String) req.getSession().getAttribute("manager");

		if (null == username) {
			return "redirect:/Verwalter/login";
		}

		map.addAttribute("__VIEWSTATE", __VIEWSTATE);

		switch (type) {

		case "smsAccount":
			if (null != id) {
				map.addAttribute("smsAccount", tdSmsAccountService.findOne(id));
			}
			return "/site_mag/smsAccount_edit";

		case "userSuggestionCategory":
			if (null != id) {
				map.addAttribute("userSuggestionCategory", tdUserSuggestionCategoryService.findOne(id));
			}
			return "/site_mag/user_suggestion_category_edit";

		case "messageType":
			if (null != id) {
				map.addAttribute("messageType", tdMessageTypeService.findOne(id));
			}
			return "/site_mag/message_type_edit";

		case "storage":
			map.addAttribute("city_list", tdCityService.findAll());
			if (null != id) {
				map.addAttribute("storage", tdStorageService.findOne(id));
			}
			return "/site_mag/storage_edit";

		default:
			if (null != id) {
				map.addAttribute("smsAccount", tdSmsAccountService.findOne(id));
			}
		}

		return "/site_mag/smsAccount_edit";
	}

	// 保存短信账户 zhangji
	@RequestMapping(value = "/smsAccount/save", method = RequestMethod.POST)
	public String smsAccountSave(TdSmsAccount tdSmsAccount, String __VIEWSTATE, ModelMap map, HttpServletRequest req) {
		String username = (String) req.getSession().getAttribute("manager");
		if (null == username) {
			return "redirect:/Verwalter/login";
		}

		map.addAttribute("__VIEWSTATE", __VIEWSTATE);
		String type = null;
		if (null == tdSmsAccount.getId()) {
			type = "add";
		} else {
			type = "edit";
		}
		tdSmsAccountService.save(tdSmsAccount);

		tdManagerLogService.addLog(type, "修改短信账户", req);

		return "redirect:/Verwalter/setting/smsAccount/list";
	}

	// 保存投诉咨询类别 zhangji
	@RequestMapping(value = "/userSuggestionCategory/save", method = RequestMethod.POST)
	public String userSuggestionCategorySave(TdUserSuggestionCategory tdUserSuggestionCategory, String __VIEWSTATE,
			ModelMap map, HttpServletRequest req) {
		String username = (String) req.getSession().getAttribute("manager");
		if (null == username) {
			return "redirect:/Verwalter/login";
		}

		map.addAttribute("__VIEWSTATE", __VIEWSTATE);
		String type = null;
		if (null == tdUserSuggestionCategory.getId()) {
			type = "add";
		} else {
			type = "edit";
		}
		tdUserSuggestionCategory.setIsEnable(true);
		tdUserSuggestionCategoryService.save(tdUserSuggestionCategory);

		tdManagerLogService.addLog(type, "修改投诉咨询类别", req);

		return "redirect:/Verwalter/setting/userSuggestionCategory/list";
	}

	// 保存信息类别 zhangji
	@RequestMapping(value = "/messageType/save", method = RequestMethod.POST)
	public String userSuggestionCategorySave(TdMessageType tdMessageType, String __VIEWSTATE, ModelMap map,
			HttpServletRequest req) {
		String username = (String) req.getSession().getAttribute("manager");
		if (null == username) {
			return "redirect:/Verwalter/login";
		}

		map.addAttribute("__VIEWSTATE", __VIEWSTATE);
		String type = null;
		if (null == tdMessageType.getId()) {
			type = "add";
		} else {
			type = "edit";
		}
		tdMessageType.setIsEnable(true);
		tdMessageTypeService.save(tdMessageType);

		tdManagerLogService.addLog(type, "修改信息类别", req);

		return "redirect:/Verwalter/setting/messageType/list";
	}

	// 保存仓库 zhangji
	@RequestMapping(value = "/storage/save", method = RequestMethod.POST)
	public String storageSave(TdStorage tdStorage, String __VIEWSTATE, ModelMap map, HttpServletRequest req) {
		String username = (String) req.getSession().getAttribute("manager");
		if (null == username) {
			return "redirect:/Verwalter/login";
		}

		map.addAttribute("__VIEWSTATE", __VIEWSTATE);
		String type = null;
		if (null == tdStorage.getId()) {
			type = "add";
		} else {
			type = "edit";
		}

		if (null != tdStorage.getCityId()) {
			String cityName = tdCityService.findOne(tdStorage.getCityId()).getCityName();
			tdStorage.setCityName(cityName);
		}
		tdStorageService.save(tdStorage);

		tdManagerLogService.addLog(type, "修改仓库", req);

		return "redirect:/Verwalter/setting/storage/list";
	}

	/*-------------------------各种类别模块 end -----------------------------*/
	@ModelAttribute
	public void getModel(@RequestParam(value = "id", required = false) Long id,
			@RequestParam(value = "serviceItemId", required = false) Long serviceItemId, ModelMap map) {
		if (null != id) {
			map.addAttribute("tdSetting", tdSettingService.findOne(id));
		}

		if (null != serviceItemId) {
			TdServiceItem serviceItem = tdServiceItemService.findOne(serviceItemId);
			map.addAttribute("tdServiceItem", serviceItem);
		}
	}

	private void btnSave(Long[] ids, Double[] sortIds) {
		if (null == ids || null == sortIds || ids.length < 1 || sortIds.length < 1) {
			return;
		}

		for (int i = 0; i < ids.length; i++) {
			Long id = ids[i];

			TdServiceItem e = tdServiceItemService.findOne(id);

			if (null != e) {
				if (sortIds.length > i) {
					e.setSortId(new Double(sortIds[i]));
					tdServiceItemService.save(e);
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

				tdServiceItemService.delete(id);
			}
		}
	}

	private void btnDeleteCompany(Long[] ids, Integer[] chkIds) {
		if (null == ids || null == chkIds || ids.length < 1 || chkIds.length < 1) {
			return;
		}

		for (int chkId : chkIds) {
			if (chkId >= 0 && ids.length > chkId) {
				Long id = ids[chkId];

				tdCompanyService.delete(id);
			}
		}
	}

	private void btnDeleteCity(Long[] ids, Integer[] chkIds) {
		if (null == ids || null == chkIds || ids.length < 1 || chkIds.length < 1) {
			return;
		}

		for (int chkId : chkIds) {
			if (chkId >= 0 && ids.length > chkId) {
				Long id = ids[chkId];

				tdCityService.delete(id);
			}
		}
	}

	private void btnDeleteDistrict(Long[] ids, Integer[] chkIds) {
		if (null == ids || null == chkIds || ids.length < 1 || chkIds.length < 1) {
			return;
		}

		for (int chkId : chkIds) {
			if (chkId >= 0 && ids.length > chkId) {
				Long id = ids[chkId];

				tdDistrictService.delete(id);
			}
		}
	}

	private void btnDeleteSubdistrict(Long[] ids, Integer[] chkIds) {
		if (null == ids || null == chkIds || ids.length < 1 || chkIds.length < 1) {
			return;
		}

		for (int chkId : chkIds) {
			if (chkId >= 0 && ids.length > chkId) {
				Long id = ids[chkId];

				tdSubdistrictService.delete(id);
			}
		}
	}

	/**
	 * 删除短信账户以及其他信息类别
	 * 
	 * @author Zhangji
	 * @param ids
	 * @param chkIds
	 */
	private void btnTypeDelete(String type, Long[] ids, Integer[] chkIds) {
		if (null == ids || null == chkIds || null == type || type.equals("") || ids.length < 1 || chkIds.length < 1) {
			return;
		}

		for (int chkId : chkIds) {
			if (chkId >= 0 && ids.length > chkId) {
				Long id = ids[chkId];
				if (type.equals("smsAccount")) {
					tdSmsAccountService.delete(id);
				} else if (type.equals("userSuggestionCategory")) {
					tdUserSuggestionCategoryService.delete(id);
				} else if (type.equals("messageType")) {
					tdMessageTypeService.delete(id);
				} else if (type.equals("storage")) {
					tdStorageService.delete(id);
				}
			}
		}
	}

	private void btnDeleteWareHouse(Long[] ids, Integer[] chkIds) {
		if (null == ids || null == chkIds || ids.length < 1 || chkIds.length < 1) {
			return;
		}

		for (int chkId : chkIds) {
			if (chkId >= 0 && ids.length > chkId) {
				Long id = ids[chkId];

				WareHouseService.delete(id);
			}
		}
	}

	/**
	 * 仓库-列表
	 * 
	 * @param page
	 * @param size
	 * @param __EVENTTARGET
	 * @param __EVENTARGUMENT
	 * @param __VIEWSTATE
	 * @param action
	 * @param listId
	 * @param listChkId
	 * @param map
	 * @param req
	 * @return
	 * @author 华仔
	 */
	@RequestMapping(value = "/warehouse/list")
	public String settingWareHouseList(Integer page, Integer size, String __EVENTTARGET, String __EVENTARGUMENT,
			String __VIEWSTATE, String action, Long[] listId, Integer[] listChkId, ModelMap map,
			HttpServletRequest req) {
		String username = (String) req.getSession().getAttribute("manager");
		if (null == username) {
			return "redirect:/Verwalter/login";
		}
		if (null != __EVENTTARGET) {
			if (__EVENTTARGET.equalsIgnoreCase("btnDelete")) {
				btnDeleteWareHouse(listId, listChkId);
				tdManagerLogService.addLog("delete", "删除仓库", req);
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
			;
		}

		map.addAttribute("page", page);
		map.addAttribute("size", size);
		map.addAttribute("action", action);
		map.addAttribute("__EVENTTARGET", __EVENTTARGET);
		map.addAttribute("__EVENTARGUMENT", __EVENTARGUMENT);
		map.addAttribute("__VIEWSTATE", __VIEWSTATE);
		map.addAttribute("WareHouse_page", WareHouseService.findAll(page, size));

		return "/site_mag/warehouse_list";
	}

	@RequestMapping(value = "/warehouse/edit")
	public String WareHouseEdit(Long id, String __VIEWSTATE, ModelMap map, HttpServletRequest req) {
		String username = (String) req.getSession().getAttribute("manager");

		if (null == username) {
			return "redirect:/Verwalter/login";
		}

		map.addAttribute("__VIEWSTATE", __VIEWSTATE);

		if (null != id) {
			map.addAttribute("warehouse", WareHouseService.findOne(id));
		}

		map.addAttribute("warehouse_list", WareHouseService.findAll());
		return "/site_mag/warehouse_edit";
	}

	@RequestMapping(value = "/warehouse/save", method = RequestMethod.POST)
	public String WareHouseSave(TdWareHouse tdWareHouse, String __VIEWSTATE, ModelMap map, HttpServletRequest req) {
		String username = (String) req.getSession().getAttribute("manager");
		if (null == username) {
			return "redirect:/Verwalter/login";
		}

		map.addAttribute("__VIEWSTATE", __VIEWSTATE);
		String type = null;
		if (null == tdWareHouse.getId()) {
			type = "add";
			tdWareHouse.setCreatTime(new Date());
			tdWareHouse.setUpdateTime(new Date());
		} else {
			type = "edit";

		}
		WareHouseService.save(tdWareHouse);

		tdManagerLogService.addLog(type, "修改仓库", req);

		return "redirect:/Verwalter/setting/warehouse/list";
	}
}
