package com.ynyes.lyz.controller.management;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ynyes.lyz.entity.TdActivity;
import com.ynyes.lyz.entity.TdActivityGift;
import com.ynyes.lyz.entity.TdDiySite;
import com.ynyes.lyz.entity.TdDiySiteList;
import com.ynyes.lyz.entity.TdGoods;
import com.ynyes.lyz.entity.TdGoodsCombination;
import com.ynyes.lyz.entity.TdGoodsGift;
import com.ynyes.lyz.service.TdActivityGiftService;
import com.ynyes.lyz.service.TdActivityService;
import com.ynyes.lyz.service.TdCityService;
import com.ynyes.lyz.service.TdDiySiteListService;
import com.ynyes.lyz.service.TdDiySiteService;
import com.ynyes.lyz.service.TdGoodsService;
import com.ynyes.lyz.service.TdManagerLogService;
import com.ynyes.lyz.service.TdProductCategoryService;
import com.ynyes.lyz.util.SiteMagConstant;

@Controller
@RequestMapping("/Verwalter/activity")
public class TdManagerActivityCotroller {
	@Autowired
	private TdManagerLogService tdManagerLogService;

	@Autowired
	private TdCityService tdCityService;

	@Autowired
	private TdDiySiteService TdDiySiteService;

	@Autowired
	private TdActivityService tdActivityService;

	@Autowired
	private TdActivityGiftService tdActivityGiftService;

	@Autowired
	private TdProductCategoryService tdProductCategoryService;

	@Autowired
	private TdGoodsService tdGoodsService;

	@Autowired
	private TdDiySiteListService tdDiySiteListService;

	@RequestMapping("/list")
	public String activityList(Integer page, Integer size, Long categoryId, String saleType, String property,
			String __EVENTTARGET, String __EVENTARGUMENT, String __VIEWSTATE, String keywords, Long[] listId,
			Integer[] listChkId, Long[] listSortId, ModelMap map, HttpServletRequest req) {
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

			case "btnSave":
				btnSave(listId, listSortId, username);
				tdManagerLogService.addLog("edit", "用户修改商品", req);
				break;

			case "btnDelete":
				btnDelete(listId, listChkId);
				tdManagerLogService.addLog("delete", "用户删除商品", req);
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
		map.addAttribute("categoryId", categoryId);
		map.addAttribute("property", property);

		map.addAttribute("activity_page", tdActivityService.findAll(page, size));
		return "/site_mag/activity_list";
	}

	@RequestMapping("/add")
	public String activityEdit(ModelMap map, HttpServletRequest req) {
		String username = (String) req.getSession().getAttribute("manager");
		if (null == username) {
			return "redirect:/Verwalter/login";
		}
		map.addAttribute("city_list", tdCityService.findAll());
		return "/site_mag/activity_edit";
	}

	@RequestMapping(value = "/edit")
	public String goodsEdit(Long fns, Long pid, Long id, String __EVENTTARGET, String __EVENTARGUMENT,
			String __VIEWSTATE, ModelMap map, HttpServletRequest req) {
		String username = (String) req.getSession().getAttribute("manager");
		if (null == username) {
			return "redirect:/Verwalter/login";
		}

		map.addAttribute("__EVENTTARGET", __EVENTTARGET);
		map.addAttribute("__EVENTARGUMENT", __EVENTARGUMENT);
		map.addAttribute("__VIEWSTATE", __VIEWSTATE);

		if (null != id) {
			TdActivity tActivity = tdActivityService.findOne(id);

			if (null != tActivity) {
				map.addAttribute("activity", tActivity);
				map.addAttribute("city_list", tdCityService.findAll());
				Page<TdDiySite> diySitePage = TdDiySiteService
						.findByCityIdAndIsEnableTrueOrderBySortIdAsc(tActivity.getCityId(), 0, 100000);
				map.addAttribute("diysite_list", diySitePage.getContent());
			}
		}

		if (null != fns) {
			map.addAttribute("fns", fns);
		}
		return "/site_mag/activity_edit";
	}
     //增加判断商品是否存在
	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public String save(TdActivity tdActivity, Long[] diySiteIds, String __EVENTTARGET, String __EVENTARGUMENT,
			String __VIEWSTATE, String menuId, String channelId, ModelMap map, Boolean isRecommendIndex,
			Boolean isRecommendType, Boolean isHot, Boolean isNew, Boolean isSpecialPrice, HttpServletRequest req) {
		String username = (String) req.getSession().getAttribute("manager");
		if (null == username) {
			return "redirect:/Verwalter/login";
		}

		String type = null;
		String goodsAndNumber = "";
		String giftAndNumber = "";

		if (diySiteIds != null) {
			List<TdDiySiteList> siteLists = new ArrayList<TdDiySiteList>();
			for (Long id : diySiteIds) {
				TdDiySiteList siteList = new TdDiySiteList();
				TdDiySite diySite = TdDiySiteService.findOne(id);
				siteList.setSiteId(id.toString());
				siteList.setTitle(diySite.getTitle());
				// siteList.setCity(tdCityService.findBySobIdCity(diySite.getCityId()).getCityName());
				siteList.setInfo(diySite.getInfo());
				tdDiySiteListService.save(siteList);
				siteLists.add(siteList);
			}
			tdActivity.setSiteList(siteLists);
		}
		List<TdGoodsCombination> comb_List = tdActivity.getCombList();
		for (TdGoodsCombination tdGoodsCombination : comb_List) {
			//判断商品是否存在
			TdGoods goods=null;
			if(tdGoodsCombination.getGoodsId()!=null){
				goods= tdGoodsService.findOne(tdGoodsCombination.getGoodsId());
			}
			if(goods!=null){
				goodsAndNumber += tdGoodsCombination.getGoodsId() + "_" + tdGoodsCombination.getNumber() + ",";
			}
			
		}

		tdActivity.setGoodsNumber(goodsAndNumber);

		List<TdGoodsGift> gift_List = tdActivity.getGiftList();

		if (null != gift_List && gift_List.size() > 0) {
			for (TdGoodsGift tdGoodsGift : gift_List) {
				//判断商品是否存在
				TdGoods goods=null;
				if(tdGoodsGift.getGoodsId()!=null){
					goods= tdGoodsService.findOne(tdGoodsGift.getGoodsId());
				}
				if(goods!=null){
					giftAndNumber += tdGoodsGift.getGoodsId() + "_" + tdGoodsGift.getNumber() + ",";
				}
			}
		}
		tdActivity.setGiftNumber(giftAndNumber);

		if (null == tdActivity.getId()) {
			type = "add";
		} else {
			type = "edit";
		}

		tdActivityService.save(tdActivity);
		// tdGoodsService.save(tdGoods, username);
		//
		tdManagerLogService.addLog(type, "用户修改活动", req);

		// 保存成功提示
		Long fns = 1L;

		return "redirect:/Verwalter/activity/edit?fns=" + fns + "&id=" + tdActivity.getId();
	}

	@RequestMapping(value = "/gift/list")
	public String giftList(Integer page, Integer size, Long categoryId, String saleType, String property,
			String __EVENTTARGET, String __EVENTARGUMENT, String __VIEWSTATE, String keywords, Long[] listId,
			Integer[] listChkId, Double[] listSortId, ModelMap map, HttpServletRequest req) {
		String username = (String) req.getSession().getAttribute("manager");
		if (username == null) {
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

			case "btnSave":
				btnGiftSave(listId, listSortId, username);
				tdManagerLogService.addLog("edit", "用户修改商品", req);
				break;

			case "btnDelete":
				btnGiftDelete(listId, listChkId);
				tdManagerLogService.addLog("delete", "用户删除商品", req);
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
		map.addAttribute("categoryId", categoryId);
		map.addAttribute("property", property);

		map.addAttribute("activity_gift_Page", tdActivityGiftService.findAllOrderByCreatTime(page, size));

		return "/site_mag/activity_gift_list";
	}

	@RequestMapping(value = "/gift/edit")
	public String giftEdit(Long pid, Long id, String __EVENTTARGET, String __EVENTARGUMENT, String __VIEWSTATE,
			ModelMap map, HttpServletRequest req) {
		String username = (String) req.getSession().getAttribute("manager");
		if (null == username) {
			return "redirect:/Verwalter/login";
		}

		map.addAttribute("__EVENTTARGET", __EVENTTARGET);
		map.addAttribute("__EVENTARGUMENT", __EVENTARGUMENT);
		map.addAttribute("__VIEWSTATE", __VIEWSTATE);

		if (null != id) {
			TdActivityGift tActivity = tdActivityGiftService.findOne(id);

			if (null != tActivity) {
				map.addAttribute("activity_gift", tActivity);

				Page<TdDiySite> diySitePage = TdDiySiteService
						.findByCityIdAndIsEnableTrueOrderBySortIdAsc(tActivity.getCityId(), 0, 100000);
				map.addAttribute("diysite_list", diySitePage.getContent());
			}
		}
		map.addAttribute("city_list", tdCityService.findAll());
		map.addAttribute("category_list", tdProductCategoryService.findAll());

		return "/site_mag/activity_gift_edit";
	}

	@RequestMapping(value = "/gift/save", method = RequestMethod.POST)
	public String giftsave(TdActivityGift tdActivityGift, Long[] diySiteIds, String[] hid_photo_name_show360,
			String __EVENTTARGET, String __EVENTARGUMENT, String __VIEWSTATE, String menuId, String channelId,
			ModelMap map, Boolean isRecommendIndex, Boolean isRecommendType, Boolean isHot, Boolean isNew,
			Boolean isSpecialPrice, HttpServletRequest req) {
		String username = (String) req.getSession().getAttribute("manager");
		if (null == username) {
			return "redirect:/Verwalter/login";
		}

		String type = null;

		if (diySiteIds != null) {
			List<TdDiySiteList> siteLists = new ArrayList<TdDiySiteList>();
			for (Long id : diySiteIds) {
				TdDiySiteList siteList = new TdDiySiteList();
				TdDiySite diySite = TdDiySiteService.findOne(id);
				siteList.setSiteId(id.toString());
				siteList.setTitle(diySite.getTitle());
				// siteList.setCity(tdCityService.findBySobIdCity(diySite.getCityId()).getCityName());
				siteList.setInfo(diySite.getInfo());
				tdDiySiteListService.save(siteList);
				siteLists.add(siteList);
			}
			// tdActivity.setSiteList(siteLists);
		}

		if (null == tdActivityGift.getId()) {
			type = "add";
		} else {
			type = "edit";
		}
		// tdActivityGift.setCityId(4L);
		tdActivityGiftService.save(tdActivityGift);
		// tdGoodsService.save(tdGoods, username);
		//
		tdManagerLogService.addLog(type, "用户修改小辅料活动", req);

		return "redirect:/Verwalter/activity/gift/list";
	}

	@RequestMapping(value = "/gift/dialog")
	public String giftDialog(String keywords, Long categoryId, Integer page, Integer size, Integer total,
			String __EVENTTARGET, String __EVENTARGUMENT, String __VIEWSTATE, ModelMap map, HttpServletRequest req) {
		String username = (String) req.getSession().getAttribute("manager");
		if (null == username) {
			return "redirect:/Verwalter/login";
		}
		if (null != __EVENTTARGET) {
			if (__EVENTTARGET.equalsIgnoreCase("btnPage")) {
				if (null != __EVENTARGUMENT) {
					page = Integer.parseInt(__EVENTARGUMENT);
				}
			} else if (__EVENTTARGET.equalsIgnoreCase("btnSearch")) {

			} else if (__EVENTTARGET.equalsIgnoreCase("categoryId")) {

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

		Page<TdGoods> goodsPage = null;

		if (null == keywords || "".equalsIgnoreCase(keywords)) {
			goodsPage = tdGoodsService.findByIsGiftOrderBySortIdAsc(page, size);
		} else {
			goodsPage = tdGoodsService.searchAndOrderBySortIdAsc(keywords, page, size);
		}

		map.addAttribute("goods_page", goodsPage);

		// 参数注回
		map.addAttribute("category_list", tdProductCategoryService.findAll());
		map.addAttribute("page", page);
		map.addAttribute("size", size);
		map.addAttribute("total", total);
		map.addAttribute("keywords", keywords);
		map.addAttribute("categoryId", categoryId);
		map.addAttribute("__EVENTTARGET", __EVENTTARGET);
		map.addAttribute("__EVENTARGUMENT", __EVENTARGUMENT);
		map.addAttribute("__VIEWSTATE", __VIEWSTATE);
		return "/site_mag/dialog_goods_gift_list";
	}

	/**
	 * 图片地址字符串整理，多张图片用,隔开
	 */
	// private String parsePicUris(String[] uris) {
	// if (null == uris || 0 == uris.length) {
	// return null;
	// }
	//
	// String res = "";
	//
	// for (String item : uris) {
	// String uri = item.substring(item.indexOf("|") + 1, item.indexOf("|", 2));
	//
	// if (null != uri) {
	// res += uri;
	// res += ",";
	// }
	// }
	//
	// return res;
	// }

	/**
	 * 显示门店
	 * 
	 * @param type
	 * @param keywords
	 * @param categoryId
	 * @param page
	 * @param size
	 * @param total
	 * @param __EVENTTARGET
	 * @param __EVENTARGUMENT
	 * @param __VIEWSTATE
	 * @param map
	 * @param req
	 * @return
	 */
	@RequestMapping(value = "/list/dialog")
	public String goodsListDialog(String keywords, Long categoryId, Integer page, Long regionId, Integer size,
			Integer total, String __EVENTTARGET, String __EVENTARGUMENT, String __VIEWSTATE, ModelMap map,
			HttpServletRequest req) {
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

		Page<TdDiySite> diySitePage = null;

		diySitePage = TdDiySiteService.findByCityIdAndIsEnableTrueOrderBySortIdAsc(regionId, page, size);

		map.addAttribute("diySite_page", diySitePage);

		// 参数注回
		map.addAttribute("page", page);
		map.addAttribute("size", size);
		map.addAttribute("total", total);
		map.addAttribute("keywords", keywords);
		map.addAttribute("categoryId", categoryId);
		map.addAttribute("__EVENTTARGET", __EVENTTARGET);
		map.addAttribute("__EVENTARGUMENT", __EVENTARGUMENT);
		map.addAttribute("__VIEWSTATE", __VIEWSTATE);

		return "/site_mag/dialog_diysite_list";
	}

	@RequestMapping(value = "/diysite/list/show", method = RequestMethod.POST)
	public String showDiysiteList(Long regionId, ModelMap map, HttpServletRequest request) {
		Page<TdDiySite> diySitePage = TdDiySiteService.findByCityIdAndIsEnableTrueOrderBySortIdAsc(regionId, 0, 100000);
		map.addAttribute("diysite_list", diySitePage.getContent());
		return "/site_mag/activity_diysite_list_detail";
	}

	@RequestMapping(value = "/check/name", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, String> checkName(String param) {
		Map<String, String> map = new HashMap<String, String>();
		map.put("status", "n");
		TdActivity activity = tdActivityService.findByName(param);

		if (activity != null) {
			map.put("info", "活动名存在");
			return map;
		}
		map.put("status", "y");

		return map;
	}

	/**
	 * 修改商品
	 * 
	 * @param cid
	 * @param ids
	 * @param sortIds
	 * @param username
	 */
	private void btnSave(Long[] ids, Long[] sortIds, String username) {
		if (null == ids || null == sortIds || ids.length < 1 || sortIds.length < 1) {
			return;
		}

		for (int i = 0; i < ids.length; i++) {
			Long id = ids[i];

			TdActivity activity = tdActivityService.findOne(id);

			if (sortIds.length > i) {
				activity.setSortId(new Double(sortIds[i]));
				tdActivityService.save(activity);
			}
		}
	}

	/**
	 * 删除商品
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

				tdActivityService.delete(id);
			}
		}
	}

	private void btnGiftSave(Long[] ids, Double[] sortIds, String username) {
		if (null == ids || null == sortIds || ids.length < 1 || sortIds.length < 1) {
			return;
		}

		for (int i = 0; i < ids.length; i++) {
			Long id = ids[i];

			TdActivityGift activity = tdActivityGiftService.findOne(id);

			if (sortIds.length > i) {
				activity.setSortId(new Double(sortIds[i]));
				tdActivityGiftService.save(activity);
			}
		}
	}

	/**
	 * 删除商品
	 * 
	 * @param ids
	 * @param chkIds
	 */
	private void btnGiftDelete(Long[] ids, Integer[] chkIds) {
		if (null == ids || null == chkIds || ids.length < 1 || chkIds.length < 1) {
			return;
		}

		for (int chkId : chkIds) {
			if (chkId >= 0 && ids.length > chkId) {
				Long id = ids[chkId];

				tdActivityGiftService.delete(id);
			}
		}
	}

}
