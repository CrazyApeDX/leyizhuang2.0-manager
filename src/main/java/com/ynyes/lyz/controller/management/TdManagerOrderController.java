package com.ynyes.lyz.controller.management;

import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ynyes.lyz.entity.TdCity;
import com.ynyes.lyz.entity.TdDeliveryInfo;
import com.ynyes.lyz.entity.TdDeliveryType;
import com.ynyes.lyz.entity.TdDiySite;
import com.ynyes.lyz.entity.TdDiySiteInventory;
import com.ynyes.lyz.entity.TdManager;
import com.ynyes.lyz.entity.TdManagerDiySiteRole;
import com.ynyes.lyz.entity.TdManagerRole;
import com.ynyes.lyz.entity.TdOrder;
import com.ynyes.lyz.entity.TdOrderData;
import com.ynyes.lyz.entity.TdOrderGoods;
import com.ynyes.lyz.entity.TdOwnMoneyRecord;
import com.ynyes.lyz.entity.TdPayType;
import com.ynyes.lyz.entity.TdPriceList;
import com.ynyes.lyz.entity.TdReturnNote;
import com.ynyes.lyz.entity.TdShippingAddress;
import com.ynyes.lyz.entity.TdWareHouse;
import com.ynyes.lyz.entity.user.CreditChangeType;
import com.ynyes.lyz.entity.user.TdUser;
import com.ynyes.lyz.interfaces.entity.TdOrderReceiveInf;
import com.ynyes.lyz.interfaces.service.TdEbsSenderService;
import com.ynyes.lyz.interfaces.service.TdInterfaceService;
import com.ynyes.lyz.interfaces.utils.INFConstants;
import com.ynyes.lyz.service.TdArticleService;
import com.ynyes.lyz.service.TdCityService;
import com.ynyes.lyz.service.TdCommonService;
import com.ynyes.lyz.service.TdDeliveryInfoService;
import com.ynyes.lyz.service.TdDeliveryTypeService;
import com.ynyes.lyz.service.TdDistrictService;
import com.ynyes.lyz.service.TdDiySiteInventoryService;
import com.ynyes.lyz.service.TdDiySiteRoleService;
import com.ynyes.lyz.service.TdDiySiteService;
import com.ynyes.lyz.service.TdGoodsService;
import com.ynyes.lyz.service.TdManagerLogService;
import com.ynyes.lyz.service.TdManagerRoleService;
import com.ynyes.lyz.service.TdManagerService;
import com.ynyes.lyz.service.TdOrderDataService;
import com.ynyes.lyz.service.TdOrderService;
import com.ynyes.lyz.service.TdOwnMoneyRecordService;
import com.ynyes.lyz.service.TdPayTypeService;
import com.ynyes.lyz.service.TdPriceCountService;
import com.ynyes.lyz.service.TdPriceListService;
import com.ynyes.lyz.service.TdProductCategoryService;
import com.ynyes.lyz.service.TdSettingService;
import com.ynyes.lyz.service.TdShippingAddressService;
import com.ynyes.lyz.service.TdSubdistrictService;
import com.ynyes.lyz.service.TdUserService;
import com.ynyes.lyz.service.TdWareHouseService;
import com.ynyes.lyz.util.SiteMagConstant;

/**
 * 后台首页控制器
 * 
 * @author Sharon
 */

@Controller
@RequestMapping(value = "/Verwalter/order")
public class TdManagerOrderController {

	@Autowired
	TdProductCategoryService tdProductCategoryService;

	@Autowired
	TdArticleService tdArticleService;

	@Autowired
	TdGoodsService tdGoodsService;

	@Autowired
	TdPayTypeService tdPayTypeService;

	@Autowired
	TdDeliveryTypeService tdDeliveryTypeService;

	@Autowired
	TdDiySiteService tdDiySiteService;

	@Autowired
	TdOrderService tdOrderService;

	@Autowired
	TdUserService tdUserService;

	@Autowired
	TdManagerLogService tdManagerLogService;

	@Autowired
	TdSettingService tdSettingService;

	@Autowired
	TdShippingAddressService tdShippingAddressService;

	@Autowired
	TdCityService tdCityService;

	@Autowired
	private TdDistrictService tdDistrictService;

	@Autowired
	private TdSubdistrictService tdSubdistrictService;

	@Autowired
	private TdPriceListService tdPriceListService;

	@Autowired
	private TdManagerService tdManagerService;

	@Autowired
	private TdManagerRoleService tdManagerRoleService;

	@Autowired
	private TdOwnMoneyRecordService tdOwnMoneyRecordService;

	@Autowired
	private TdDeliveryInfoService tdDeliveryInfoService;

	@Autowired
	private TdCommonService tdCommonService;

	@Autowired
	private TdWareHouseService tdWareHouseService;

	@Autowired
	private TdPriceCountService tdPriceCountService;

	@Autowired
	private TdDiySiteRoleService tdDiySiteRoleService;

	@Autowired
	private TdInterfaceService tdInterfaceService;

	@Autowired
	private TdDiySiteInventoryService tdDiySiteInventoryService;

	// @Autowired
	// private TdOrderReceiveInfService tdOrderReceiveInfService;

	@Autowired
	TdEbsSenderService tdEbsSenderService;

	@Autowired
	private TdOrderDataService tdOrderDataService;

	/**
	 * @author lc
	 * @注释：下载
	 */
	public Boolean download(HSSFWorkbook wb, String exportUrl, HttpServletResponse resp, String fileName) {
		String filename = "table";
		try {
			filename = new String(fileName.getBytes("GBK"), "ISO-8859-1");
		} catch (UnsupportedEncodingException e1) {
			System.out.println("下载文件名格式转换错误！");
		}
		try {
			OutputStream os;
			try {
				os = resp.getOutputStream();
				try {

					resp.reset();
					resp.setHeader("Content-Disposition", "attachment; filename=" + filename + ".xls");
					resp.setContentType("application/octet-stream; charset=utf-8");
					wb.write(os);
					os.flush();
				} finally {
					if (os != null) {
						os.close();
					}
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return true;
	}

	// 城市选择
	@RequestMapping(value = "/city", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> citySelect() {
		Map<String, Object> map = new ModelMap();
		map.put("code", 0);
		map.put("city", tdCityService.findAll());
		map.put("code", 1);
		return map;
	}

	// 行政区划选择
	@RequestMapping(value = "/district", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> districtSelect(Long cityId) {
		Map<String, Object> map = new ModelMap();
		map.put("code", 0);
		map.put("district", tdDistrictService.findByCityIdOrderBySortIdAsc(cityId));
		map.put("code", 1);
		return map;
	}

	// 行政街道选择
	@RequestMapping(value = "/subdistrict", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> subdistrictSelect(Long districtId) {
		Map<String, Object> map = new ModelMap();
		map.put("code", 0);
		map.put("subdistrict", tdSubdistrictService.findByDistrictIdOrderBySortIdAsc(districtId));
		map.put("code", 1);
		return map;
	}

	// 订单设置
	@RequestMapping(value = "/setting/{type}/list")
	public String setting(@PathVariable String type, Integer page, Integer size, String keywords, String __EVENTTARGET,
			String __EVENTARGUMENT, String __VIEWSTATE, Long[] listId, Integer[] listChkId, Double[] listSortId,
			ModelMap map, HttpServletRequest req) {
		String username = (String) req.getSession().getAttribute("manager");
		if (null == username) {
			return "redirect:/Verwalter/login";
		}

		if (null != __EVENTTARGET) {
			if (__EVENTTARGET.equalsIgnoreCase("btnDelete")) {
				btnDelete(type, listId, listChkId);

				if (type.equalsIgnoreCase("pay")) {
					tdManagerLogService.addLog("delete", "删除支付方式", req);
				} else if (type.equalsIgnoreCase("delivery")) {
					tdManagerLogService.addLog("delete", "删除配送方式", req);
				} else if (type.equalsIgnoreCase("diysite")) {
					tdManagerLogService.addLog("delete", "删除门店", req);
				} else if (type.equalsIgnoreCase("codDistrict")) {
					tdManagerLogService.addLog("delete", "删除货到付款地区", req);
				}
			} else if (__EVENTTARGET.equalsIgnoreCase("btnSave")) {
				btnSave(type, listId, listSortId);

				if (type.equalsIgnoreCase("pay")) {
					tdManagerLogService.addLog("edit", "修改支付方式", req);
				} else if (type.equalsIgnoreCase("delivery")) {
					tdManagerLogService.addLog("edit", "修改配送方式", req);
				} else if (type.equalsIgnoreCase("diysite")) {
					tdManagerLogService.addLog("edit", "修改门店", req);
				} else if (type.equalsIgnoreCase("codDistrict")) {
					tdManagerLogService.addLog("edit", "修改货到付款地区", req);
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
		map.addAttribute("keywords", keywords);
		map.addAttribute("__EVENTTARGET", __EVENTTARGET);
		map.addAttribute("__EVENTARGUMENT", __EVENTARGUMENT);
		map.addAttribute("__VIEWSTATE", __VIEWSTATE);

		if (null != type) {
			if (type.equalsIgnoreCase("pay")) // 支付方式
			{
				if (null == keywords) {
					map.addAttribute("pay_type_page", tdPayTypeService.findAllOrderBySortIdAsc(page, size));
				} else {
					map.addAttribute("pay_type_page", tdPayTypeService.searchAllOrderBySortIdAsc(keywords, page, size));
				}

				return "/site_mag/pay_type_list";
			} else if (type.equalsIgnoreCase("delivery")) // 配送方式
			{
				if (null == keywords) {
					map.addAttribute("delivery_type_page", tdDeliveryTypeService.findAllOrderBySortIdAsc(page, size));
				} else {
					map.addAttribute("delivery_type_page",
							tdDeliveryTypeService.searchAllOrderBySortIdAsc(keywords, page, size));
				}

				return "/site_mag/delivery_type_list";
			} else if (type.equalsIgnoreCase("diysite")) // 门店
			{
				if (null == keywords) {
					map.addAttribute("diy_site_page", tdDiySiteService.findAllOrderBySortIdAsc(page, size));
				} else {
					map.addAttribute("diy_site_page", tdDiySiteService.searchAllOrderBySortIdAsc(keywords, page, size));
				}

				return "/site_mag/diy_site_list";
			}
			// else if (type.equalsIgnoreCase("codDistrict")) // 货到付款地区
			// {
			// if (null == keywords)
			// {
			// map.addAttribute("cod_district_page",
			// tdShippingAddressService.findByIsCod(page, size));
			// }
			// else
			// {
			// map.addAttribute("cod_district_page",
			// tdShippingAddressService.searchBykeywords(keywords, page, size));
			// }
			//
			// return "/site_mag/cod_district_list";
			// }
		}

		return "/site_mag/pay_type_list";
	}

	// 欠款审核
	// 增加搜索功能 增加城市和门店权限 zp
	@RequestMapping(value = "/own/list")
	public String ownList(HttpServletRequest req, Integer page, Long isEnable, Integer size, ModelMap map,
			String __EVENTTARGET, String __EVENTARGUMENT, String __VIEWSTATE, Long[] listChkId, Long isPayed,
			String keywords, String diyCode, String city, Long ispassed) {
		String username = (String) req.getSession().getAttribute("manager");
		if (null == username) {
			return "redirect:/Verwalter/login";
		}
		TdManager tdManager = tdManagerService.findByUsernameAndIsEnableTrue(username);
		TdManagerRole tdManagerRole = null;
		if (null != tdManager && null != tdManager.getRoleId()) {
			tdManagerRole = tdManagerRoleService.findOne(tdManager.getRoleId());
			if (tdManagerRole == null) {
				return "redirect:/Verwalter/login";
			}
			if (tdManagerRole.getTitle().equalsIgnoreCase("门店")) {
				diyCode = tdManager.getDiyCode();
			}
		}

		// 查询用户管辖门店权限
		TdManagerDiySiteRole diySiteRole = tdDiySiteRoleService.findByTitle(tdManagerRole.getTitle());
		// 获取管理员管辖城市
		List<TdCity> cityList = new ArrayList<TdCity>();
		// 获取管理员管辖门店
		List<TdDiySite> diyList = new ArrayList<TdDiySite>();

		// 管理员获取管辖的城市和门店
		tdDiySiteRoleService.userRoleCityAndDiy(cityList, diyList, diySiteRole, tdManagerRole, tdManager);

		if (null == page || page < 0) {
			page = 0;
		}

		if (null != __EVENTTARGET) {
			if (__EVENTTARGET.equalsIgnoreCase("btnPage")) {
				if (null != __EVENTARGUMENT) {
					page = Integer.parseInt(__EVENTARGUMENT);
				}
			}
		}

		if (null == size || size <= 0) {
			size = SiteMagConstant.pageSize;
		}

		if (null != __EVENTTARGET) {
			if (__EVENTTARGET.equalsIgnoreCase("btnPage")) {
				if (null != __EVENTARGUMENT) {
					page = Integer.parseInt(__EVENTARGUMENT);
				}
			} else if (__EVENTTARGET.equalsIgnoreCase("btnEnable")) {
				if (null != listChkId) {
					btnEnale(listChkId);
				}
			} else if (__EVENTTARGET.equalsIgnoreCase("btnNotEnable")) {
				if (null != listChkId) {
					btnNotEnale(listChkId);
				}
			} else if (__EVENTTARGET.equalsIgnoreCase("statusId")) {
				page = 0;
			} else if (__EVENTTARGET.equalsIgnoreCase("payLeft")) {
				page = 0;
			} else if (__EVENTTARGET.equalsIgnoreCase("btnSearch")) {
				page = 0;
			}
		}

		// 修改城市刷新门店列表
		if (StringUtils.isNotBlank(city)) {
			// 需要删除的diy
			List<TdDiySite> diyRemoveList = new ArrayList<TdDiySite>();
			for (TdDiySite tdDiySite : diyList) {
				if (!city.equals(tdDiySite.getCity())) {
					diyRemoveList.add(tdDiySite);
					if (tdDiySite.getStoreCode().equals(diyCode)) {
						diyCode = null;
					}
				}
			}
			diyList.removeAll(diyRemoveList);
		}

		// 权限门店
		List<String> roleDiyCodes = new ArrayList<String>();
		if (diyList != null && diyList.size() > 0) {
			for (TdDiySite diy : diyList) {
				roleDiyCodes.add(diy.getStoreCode());
			}
		}

		// 搜索条件城市 数据库里面没有城市 转换为门code查询
		List<String> cityDiyCodes = new ArrayList<String>();
		TdCity tdCity = tdCityService.findByCityName(city);
		if (tdCity != null) {
			List<TdDiySite> diySiteList = tdDiySiteService.findByCityId(tdCity.getId());
			if (diySiteList != null && diySiteList.size() > 0) {
				for (TdDiySite tdDiySite : diySiteList) {
					if (roleDiyCodes.contains(tdDiySite.getStoreCode())) {
						cityDiyCodes.add(tdDiySite.getStoreCode());
					}
				}
			} else {
				// 城市下面没有门店 默认为null 查询不到任何门店
				cityDiyCodes.add("null");
			}
		}
		Page<TdOwnMoneyRecord> ownMoneyPage = tdOwnMoneyRecordService.searchOwnList(diyCode, keywords, isEnable,
				isPayed, ispassed, roleDiyCodes, cityDiyCodes, size, page);

		map.addAttribute("own_page", ownMoneyPage);

		List<TdOwnMoneyRecord> ownList = ownMoneyPage.getContent();
		// 循环获取用户名称
		Map<String, Object> nameMap = new HashMap<String, Object>();
		if (ownList != null && ownList.size() > 0) {
			for (TdOwnMoneyRecord own : ownList) {
				String ownUsername = own.getUsername();
				if (nameMap.containsKey(ownUsername)) {
					continue;
				} else {
					TdUser tdUser = tdUserService.findByUsername(ownUsername);
					if (null != tdUser && StringUtils.isNotBlank(ownUsername)) {
						nameMap.put(ownUsername, tdUser.getRealName());
					}
				}
			}
		}

		map.addAttribute("name_map", nameMap);

		// 用户管辖的门店和城市
		map.addAttribute("diySiteList", diyList);
		map.addAttribute("cityList", cityList);
		map.addAttribute("page", page);
		map.addAttribute("size", size);
		map.addAttribute("__EVENTTARGET", __EVENTTARGET);
		map.addAttribute("__EVENTARGUMENT", __EVENTARGUMENT);
		map.addAttribute("__VIEWSTATE", __VIEWSTATE);
		map.addAttribute("statusId", isEnable);
		map.addAttribute("payLeft", isPayed);
		map.addAttribute("ispassed", ispassed);
		map.addAttribute("keywords", keywords);
		map.addAttribute("cityName", city);
		map.addAttribute("diyCode", diyCode);
		return "/site_mag/own_list";
	}

	// 订单设置编辑
	@RequestMapping(value = "/setting/{type}/edit")
	public String edit(@PathVariable String type, Long id, String __VIEWSTATE, ModelMap map, HttpServletRequest req) {
		String username = (String) req.getSession().getAttribute("manager");
		if (null == username) {
			return "redirect:/Verwalter/login";
		}

		map.addAttribute("__VIEWSTATE", __VIEWSTATE);

		if (null != type) {
			if (type.equalsIgnoreCase("pay")) // 支付方式
			{
				if (null != id) {
					map.addAttribute("pay_type", tdPayTypeService.findOne(id));
				}

				return "/site_mag/pay_type_edit";
			} else if (type.equalsIgnoreCase("delivery")) // 配送方式
			{
				if (null != id) {
					map.addAttribute("delivery_type", tdDeliveryTypeService.findOne(id));
				}

				return "/site_mag/delivery_type_edit";
			} else if (type.equalsIgnoreCase("diysite")) // 门店
			{
				map.addAttribute("price_list", tdPriceListService.findAll());
				if (null != id) {
					TdDiySite tdDiySite = tdDiySiteService.findOne(id);
					List<TdPriceList> price_list = tdPriceListService.findBySobId(tdDiySite.getRegionId());
					if (null != price_list) {
						for (int i = 0; i < price_list.size(); i++) {
							TdPriceList priceList = price_list.get(i);
							Date nowTime = new Date();
							if (priceList.getActiveFlag().equalsIgnoreCase("N")) {
								price_list.remove(priceList);
							}
							if (priceList.getStartDateActive() != null) {
								if (priceList.getStartDateActive().getTime() > nowTime.getTime()) {
									price_list.remove(priceList);
								}
							}
							if (priceList.getEndDateActive() != null) {
								if (priceList.getEndDateActive().getTime() < nowTime.getTime()) {
									price_list.remove(priceList);
								}
							}
						}
					}
					map.addAttribute("choose_price_list", tdPriceListService.findBySobId(tdDiySite.getRegionId()));

					map.addAttribute("diy_site", tdDiySiteService.findOne(id));
				}

				return "/site_mag/diy_site_edit";
			} else if (type.equalsIgnoreCase("codDistrict")) // 货到付款地区
			{
				if (null != id) {
					map.addAttribute("cod_district", tdShippingAddressService.findOne(id));
				}

				return "/site_mag/cod_district_edit";
			}
		}

		return "/site_mag/pay_type_edit";
	}

	@RequestMapping(value = "/edit")
	public String orderEdit(Long id, Long statusId, String __VIEWSTATE, ModelMap map, HttpServletRequest req) {
		String username = (String) req.getSession().getAttribute("manager");
		if (null == username) {
			return "redirect:/Verwalter/login";
		}

		map.addAttribute("__VIEWSTATE", __VIEWSTATE);
		map.addAttribute("statusId", statusId);
		if (null != id) {
			TdOrder order = tdOrderService.findOne(id);
			map.addAttribute("order", tdOrderService.findOne(id));
			// 仓库
			if (null != order) {
				List<TdDeliveryInfo> deliveryList = tdDeliveryInfoService
						.findByOrderNumberOrderByBeginDtDesc(order.getMainOrderNumber());
				if (null != deliveryList && deliveryList.size() > 0) {
					List<TdWareHouse> wareHouseList = tdWareHouseService
							.findBywhNumberOrderBySortIdAsc(deliveryList.get(0).getWhNo());
					if (null != wareHouseList && wareHouseList.size() > 0) {
						map.addAttribute("tdWareHouse", wareHouseList.get(0));
					}
				}
			}
			// 查询配送信息
			List<TdDeliveryInfo> info_list = tdDeliveryInfoService
					.findByOrderNumberOrderByBeginDtDesc(order.getMainOrderNumber());
			if (null != info_list && info_list.size() != 0) {
				TdDeliveryInfo info = info_list.get(0);
				if (null != info) {
					String opUser = info.getDriver();
					TdUser deliveryUser = tdUserService.findByOpUser(opUser);
					map.addAttribute("deliveryUser", deliveryUser);
				}

			}

			// 到店支付 是否收款
			Boolean isown = false;

			// 判断是否是线上支付
			TdPayType payType = tdPayTypeService.findOne(order.getPayTypeId());
			if (payType != null && payType.getIsOnlinePay()) {
				isown = true;
			}

			// 查询收款信息
			List<TdOwnMoneyRecord> ownList = tdOwnMoneyRecordService
					.findByOrderNumberIgnoreCase(order.getMainOrderNumber());
			// 判断为空
			if (ownList != null && ownList.size() > 0) {
				// 写了循环其实还是只有一条数据
				for (TdOwnMoneyRecord own : ownList) {
					// 欠款为0时表示门店已收款
					if (own.getOwned() != null && own.getOwned() == 0) {
						isown = true;
					}
				}
			}
			// 订单价格为0 不需要收款
			Double ownPrice = order.getTotalPrice();
			if (ownPrice != null && ownPrice == 0) {
				isown = true;
			}
			map.addAttribute("ownPrice", ownPrice);
			map.addAttribute("isown", isown);
		}

		return "/site_mag/order_edit";
	}

	@RequestMapping(value = "/save")
	public String orderEdit(TdOrder tdOrder, Long statusId, String __VIEWSTATE, ModelMap map, HttpServletRequest req) {
		String username = (String) req.getSession().getAttribute("manager");
		if (null == username) {
			return "redirect:/Verwalter/login";
		}

		map.addAttribute("__VIEWSTATE", __VIEWSTATE);
		map.addAttribute("statusId", statusId);

		tdOrderService.save(tdOrder);

		tdManagerLogService.addLog("edit", "修改订单", req);

		return "redirect:/Verwalter/order/list/" + statusId;
	}

	// 订单列表
	// 修改权限设置
	@RequestMapping(value = "/list/{statusId}")
	public String goodsListDialog(String keywords, @PathVariable Long statusId, Integer page, Integer size,
			String __EVENTTARGET, String __EVENTARGUMENT, String __VIEWSTATE, Long[] listId, Integer[] listChkId,
			ModelMap map, HttpServletRequest req, String orderStartTime, String orderEndTime, String realName,
			String sellerRealName, String shippingAddress, String shippingPhone, String deliveryTime, String userPhone,
			Long orderStatusId, String shippingName, String sendTime, String diyCode, String city,
			String deliveryType) {
		String username = (String) req.getSession().getAttribute("manager");

		if (null == username) {
			return "redirect:/Verwalter/login";
		}

		TdManager tdManager = tdManagerService.findByUsernameAndIsEnableTrue(username);
		TdManagerRole tdManagerRole = null;
		if (null != tdManager && null != tdManager.getRoleId()) {
			tdManagerRole = tdManagerRoleService.findOne(tdManager.getRoleId());
		}
		if (tdManagerRole == null) {
			return "redirect:/Verwalter/login";
		}

		// 获取管理员管辖城市
		List<TdCity> cityList = new ArrayList<TdCity>();
		// 获取管理员管辖门店
		List<TdDiySite> diyList = new ArrayList<TdDiySite>();

		// 管理员获取管辖的城市和门店
		tdDiySiteRoleService.userRoleCityAndDiy(cityList, diyList, username);

		if (null == page || page < 0) {
			page = 0;
		}

		if (null == size || size <= 0) {
			size = SiteMagConstant.pageSize;
		}

		if (null != __EVENTTARGET) {
			if (__EVENTTARGET.equalsIgnoreCase("btnCancel")) {
				btnCancel(listId, listChkId);
				tdManagerLogService.addLog("cancel", "取消订单", req);
			} else if (__EVENTTARGET.equalsIgnoreCase("btnConfirm")) {
				btnConfirm(listId, listChkId);
				tdManagerLogService.addLog("confirm", "确认订单", req);
			} else if (__EVENTTARGET.equalsIgnoreCase("btnDelete")) {
				btnDelete(listId, listChkId);
				tdManagerLogService.addLog("delete", "删除订单", req);
			} else if (__EVENTTARGET.equalsIgnoreCase("btnPage")) {
				if (null != __EVENTARGUMENT) {
					page = Integer.parseInt(__EVENTARGUMENT);
				}
			} else if (__EVENTTARGET.equals("btnSearch")) {
				page = 0;
			}
		}
		// 修改城市刷新门店列表
		if (StringUtils.isNotBlank(city)) {
			// 需要删除的diy
			List<TdDiySite> diyRemoveList = new ArrayList<TdDiySite>();
			for (TdDiySite tdDiySite : diyList) {
				if (!city.equals(tdDiySite.getCity())) {
					diyRemoveList.add(tdDiySite);
					if (tdDiySite.getStoreCode().equals(diyCode)) {
						diyCode = null;
					}
				}
			}
			diyList.removeAll(diyRemoveList);
		}

		if (null != statusId) {
			List<String> usernameList = new ArrayList<String>();
			Boolean isNotFindUser = false;
			if (StringUtils.isNotBlank(realName)) { // 根据会员真实姓名查询用户名
				List<TdUser> userList = tdUserService.findByRealName(realName);
				if (null != userList && userList.size() > 0) {
					for (TdUser tdUser : userList) {
						usernameList.add(tdUser.getUsername());
					}
				} else {
					isNotFindUser = true;
				}
			}
			if (!isNotFindUser) {
				// 城市名称列表
				List<String> roleCityNames = new ArrayList<String>();
				if (cityList != null && cityList.size() > 0) {
					for (TdCity tdCity : cityList) {
						roleCityNames.add(tdCity.getCityName());
					}
				}
				// 门店
				List<String> roleDiyCodes = new ArrayList<String>();
				if (diyList != null && diyList.size() > 0) {
					for (TdDiySite diy : diyList) {
						roleDiyCodes.add(diy.getStoreCode());
					}
				}
				// 订单的city字段为收货人地址
				// 搜索条件城市 数据库里面没有城市 转换为门code查询
				List<String> cityDiyCodes = new ArrayList<String>();
				TdCity tdCity = tdCityService.findByCityName(city);
				if (tdCity != null) {
					List<TdDiySite> diySiteList = tdDiySiteService.findByCityId(tdCity.getId());
					if (diySiteList != null && diySiteList.size() > 0) {
						for (TdDiySite tdDiySite : diySiteList) {
							if (roleDiyCodes.contains(tdDiySite.getStoreCode())) {
								cityDiyCodes.add(tdDiySite.getStoreCode());
							}
						}
					} else {
						// 城市下面没有门店 默认为null 查询不到任何门店
						cityDiyCodes.add("null");
					}
				}

				Page<TdOrder> orderPage = tdOrderService.findAllAddConditionDeliveryType(keywords, orderStartTime,
						orderEndTime, usernameList, sellerRealName, shippingAddress, shippingPhone, deliveryTime,
						userPhone, shippingName, sendTime, statusId, diyCode, city, deliveryType, cityDiyCodes,
						roleDiyCodes, size, page, false);

				map.addAttribute("order_page", orderPage);
			}
		}

		@SuppressWarnings("unchecked")
		Page<TdOrder> orders1 = (Page<TdOrder>) map.get("order_page");
		if (orders1 != null) {
			List<TdOrder> orderlist = orders1.getContent();
			Map<String, String> nameMap = getUserRealNameFormTdOder(orderlist);
			if (nameMap != null) {
				map.addAttribute("name_map", nameMap);
			}
		}
		// 用户管辖的门店和城市
		map.addAttribute("diySiteList", diyList);
		map.addAttribute("cityList", cityList);
		// 参数注回
		map.addAttribute("orderNumber", keywords);
		map.addAttribute("orderStartTime", orderStartTime);
		map.addAttribute("orderEndTime", orderEndTime);
		map.addAttribute("realName", realName);
		map.addAttribute("sellerRealName", sellerRealName);
		map.addAttribute("shippingAddress", shippingAddress);
		map.addAttribute("shippingPhone", shippingPhone);
		map.addAttribute("deliveryTime", deliveryTime);
		map.addAttribute("userPhone", userPhone);
		map.addAttribute("shippingName", shippingName);
		map.addAttribute("sendTime", sendTime);
		map.addAttribute("statusId", statusId);
		map.addAttribute("diyCode", diyCode);
		map.addAttribute("cityname", city);

		map.addAttribute("page", page);
		map.addAttribute("size", size);
		map.addAttribute("keywords", keywords);
		map.addAttribute("statusId", statusId);
		map.addAttribute("__EVENTTARGET", __EVENTTARGET);
		map.addAttribute("__EVENTARGUMENT", __EVENTARGUMENT);
		map.addAttribute("__VIEWSTATE", __VIEWSTATE);
		map.addAttribute("deliveryType", deliveryType);

		return "/site_mag/order_list";
	}

	@RequestMapping(value = "/setting/pay/save", method = RequestMethod.POST)
	public String save(TdPayType tdPayType, ModelMap map, HttpServletRequest req) {
		String username = (String) req.getSession().getAttribute("manager");
		if (null == username) {
			return "redirect:/Verwalter/login";
		}

		if (null == tdPayType.getId()) {
			tdManagerLogService.addLog("add", "新增支付方式", req);
		} else {
			tdManagerLogService.addLog("edit", "修改支付方式", req);
		}
		tdPayTypeService.save(tdPayType);

		return "redirect:/Verwalter/order/setting/pay/list";
	}

	@RequestMapping(value = "/setting/codDistrict/save", method = RequestMethod.POST)
	public String codDistrictsave(String province, String city, String disctrict, Long codDistrictId, ModelMap map,
			HttpServletRequest req) {
		String username = (String) req.getSession().getAttribute("manager");
		if (null == username) {
			return "redirect:/Verwalter/login";
		}

		if (null == codDistrictId) {
			tdManagerLogService.addLog("add", "新增货到付款地区", req);
			TdShippingAddress tdShippingAddress = new TdShippingAddress();
			if (null != province) {
				tdShippingAddress.setProvince(province);
			}
			if (null != city) {
				tdShippingAddress.setCity(city);
			}
			if (null != disctrict) {
				tdShippingAddress.setDisctrict(disctrict);
			}
			// tdShippingAddress.setIsCod(true);
			tdShippingAddressService.save(tdShippingAddress);
		} else {
			tdManagerLogService.addLog("edit", "修改货到付款地区", req);

			TdShippingAddress tdShippingAddress = tdShippingAddressService.findOne(codDistrictId);
			if (null != province) {
				tdShippingAddress.setProvince(province);
			}
			if (null != city) {
				tdShippingAddress.setCity(city);
			}
			if (null != disctrict) {
				tdShippingAddress.setDisctrict(disctrict);
			}

			tdShippingAddressService.save(tdShippingAddress);
		}

		return "redirect:/Verwalter/order/setting/codDistrict/list";
	}

	@RequestMapping(value = "/setting/delivery/save", method = RequestMethod.POST)
	public String save(TdDeliveryType tdDeliveryType, ModelMap map, HttpServletRequest req) {
		String username = (String) req.getSession().getAttribute("manager");
		if (null == username) {
			return "redirect:/Verwalter/login";
		}

		if (null == tdDeliveryType.getId()) {
			tdManagerLogService.addLog("add", "新增配送方式", req);
		} else {
			tdManagerLogService.addLog("edit", "修改配送方式", req);
		}

		tdDeliveryTypeService.save(tdDeliveryType);

		return "redirect:/Verwalter/order/setting/delivery/list";
	}

	@RequestMapping(value = "/setting/diysite/save", method = RequestMethod.POST)
	public String save(TdDiySite tdDiySite, String[] hid_photo_name_show360, ModelMap map, HttpServletRequest req) {
		String username = (String) req.getSession().getAttribute("manager");
		if (null == username) {
			return "redirect:/Verwalter/login";
		}

		if (tdDiySite.getCityId() != null) {
			TdCity tdCity = tdCityService.findOne(tdDiySite.getCityId());
			tdDiySite.setCity(tdCity.getCityName());
			tdDiySite.setRegionId(tdCity.getSobIdCity());
		}

		if (null == tdDiySite.getId()) {
			tdManagerLogService.addLog("add", "新增门店", req);
		} else {
			tdManagerLogService.addLog("edit", "修改门店", req);
		}

		tdDiySiteService.save(tdDiySite);

		return "redirect:/Verwalter/order/setting/diysite/list";
	}

	@RequestMapping(value = "/dialog/contact")
	public String addressDialog(ModelMap map, HttpServletRequest req) {
		String username = (String) req.getSession().getAttribute("manager");
		if (null == username) {
			return "redirect:/Verwalter/login";
		}

		return "/site_mag/dialog_contact";
	}

	@RequestMapping(value = "/dialog/delivery")
	public String sendDialog(String orderNumber, ModelMap map, HttpServletRequest req) {
		String username = (String) req.getSession().getAttribute("manager");
		if (null == username) {
			return "redirect:/Verwalter/login";
		}

		if (null != orderNumber && !orderNumber.isEmpty()) {
			map.addAttribute("order", tdOrderService.findByOrderNumber(orderNumber));
		}

		map.addAttribute("delivery_type_list", tdDeliveryTypeService.findByIsEnableTrue());

		return "/site_mag/dialog_delivery";
	}

	@RequestMapping(value = "/dialog/print")
	public String printDialog(String orderNumber, ModelMap map, HttpServletRequest req) {
		String username = (String) req.getSession().getAttribute("manager");
		if (null == username) {
			return "redirect:/Verwalter/login";
		}

		if (null != orderNumber && !orderNumber.isEmpty()) {
			TdOrder order = tdOrderService.findByOrderNumber(orderNumber);
			map.addAttribute("order", order);
			map.addAttribute("now", new Date());
			map.addAttribute("manager", req.getSession().getAttribute("manager"));

			if (null != order) {
				// map.addAttribute("user",
				// tdUserService.findByUsernameAndIsEnabled(order.getUsername()));
			}
		}

		return "/site_mag/dialog_order_print";
	}

	@RequestMapping(value = "/param/edit", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> paramEdit(String orderNumber, String type, String data, String name, String address,
			String postal, String mobile, String expressNumber, Long deliverTypeId, ModelMap map,
			HttpServletRequest req) {

		Map<String, Object> res = new HashMap<String, Object>();

		res.put("code", 1);

		String username = (String) req.getSession().getAttribute("manager");
		if (null == username) {
			res.put("message", "请重新登录");
			return res;
		}

		if (null != orderNumber && !orderNumber.isEmpty() && null != type && !type.isEmpty()) {
			TdOrder order = tdOrderService.findByOrderNumber(orderNumber);

			// 修改备注
			if (type.equalsIgnoreCase("editMark")) {
				order.setRemarkInfo(data);
			}
			// 修改商品总金额
			else if (type.equalsIgnoreCase("editTotalGoodsPrice")) {
				double goodsPrice = Double.parseDouble(data);
				order.setTotalGoodsPrice(goodsPrice);
				order.setTotalPrice(goodsPrice + order.getPayTypeFee() + order.getDeliverFee());
			}
			// 修改配送费用
			else if (type.equalsIgnoreCase("editDeliveryPrice")) {
				double deliveryPrice = Double.parseDouble(data);
				order.setDeliverFee(deliveryPrice);
				order.setTotalPrice(deliveryPrice + order.getPayTypeFee() + order.getTotalGoodsPrice());
			}
			// 修改支付手续费
			else if (type.equalsIgnoreCase("editPayPrice")) {
				double payPrice = Double.parseDouble(data);
				order.setPayTypeFee(payPrice);
				order.setTotalPrice(payPrice + order.getTotalGoodsPrice() + order.getDeliverFee());
			}
			// 修改联系方式
			else if (type.equalsIgnoreCase("editContact")) {
				order.setShippingName(name);
				order.setShippingAddress(address);
				order.setShippingPhone(mobile);
				order.setPostalCode(postal);
			}
			// 确认订单
			else if (type.equalsIgnoreCase("orderConfirm")) {
				if (order.getStatusId().equals(1L)) {
					order.setStatusId(2L);
					order.setCheckTime(new Date());
				}
			}
			// 确认付款
			else if (type.equalsIgnoreCase("orderPay")) {
				if (order.getStatusId().equals(2L)) {
					order.setStatusId(3L);
					order.setPayTime(new Date());

				}
			}
			// 货到付款确认付款
			else if (type.equalsIgnoreCase("orderPayOffline")) {
				if (order.getStatusId().equals(2L) && !order.getIsOnlinePay()) {
					order.setStatusId(5L);
					order.setPayTime(new Date());
				}

			}
			// 确认发货
			else if (type.equalsIgnoreCase("orderDelivery")) {
				if (order.getStatusId().equals(3L)) {
					if (order.getDeliverTypeTitle() != null && order.getDeliverTypeTitle().equalsIgnoreCase("门店自提")) {

						// 判断库存
						Map<String, Long> inventoryMap = new HashMap<String, Long>();

						Long siteId = order.getDiySiteId();

						// 商品列表
						List<TdOrderGoods> orderGoodsList = order.getOrderGoodsList();
						for (TdOrderGoods tdOrderGoods : orderGoodsList) {
							if (tdOrderGoods != null) {
								String sku = tdOrderGoods.getSku();
								// 判断是否存在 存在累加 不存在默认为0
								Long quantity = inventoryMap.get(sku) == null ? 0L : inventoryMap.get(sku);
								inventoryMap.put(sku, quantity + tdOrderGoods.getQuantity());
							}
						}
						// 赠品列表
						List<TdOrderGoods> giftGoodsList = order.getGiftGoodsList();
						for (TdOrderGoods giftGoods : giftGoodsList) {
							if (giftGoods != null) {
								String sku = giftGoods.getSku();
								// 判断是否存在 存在累加 不存在默认为0
								Long quantity = inventoryMap.get(sku) == null ? 0L : inventoryMap.get(sku);
								inventoryMap.put(sku, quantity + giftGoods.getQuantity());
							}
						}
						// 小辅料列表
						List<TdOrderGoods> presentedList = order.getPresentedList();
						for (TdOrderGoods presented : presentedList) {
							if (presented != null) {
								String sku = presented.getSku();
								// 判断是否存在 存在累加 不存在默认为0
								Long quantity = inventoryMap.get(sku) == null ? 0L : inventoryMap.get(sku);
								inventoryMap.put(sku, quantity + presented.getQuantity());
							}
						}

						for (String sku : inventoryMap.keySet()) {
							TdDiySiteInventory diySiteInventory = tdDiySiteInventoryService
									.findByGoodsCodeAndDiySiteId(sku, siteId);
							if (diySiteInventory == null || inventoryMap.get(sku) == null
									|| diySiteInventory.getInventory() < inventoryMap.get(sku)) {
								res.put("message", "商品编码为" + sku + "的库存不足\n,不能确认发货");
								return res;
							}
						}
						tdDiySiteInventoryService.changeGoodsInventory(order, 2L, req, "发货", null);
						order.setStatusId(4L);
						order.setSendTime(new Date());
						TdOrderReceiveInf orderReceiveInf = tdInterfaceService.initOrderReceiveByOrder(order);

						// 异步发送到ebs：门店自提
						tdEbsSenderService.sendStorePickUpToEbsAndRecord(orderReceiveInf);
					}

				}
			}
			// 确认收货
			else if (type.equalsIgnoreCase("orderReceive")) {
				if (order.getStatusId().equals(4L)) {
					order.setStatusId(5L);
					order.setReceiveTime(new Date());

					/*
					 * if (order.getDeliverTypeTitle().equalsIgnoreCase("门店自提"))
					 * { // add send receive time to ebs TdOrderReceiveInf
					 * orderReceiveInf =
					 * tdInterfaceService.initOrderReceiveByOrder(order); if
					 * (orderReceiveInf != null) {
					 * tdInterfaceService.ebsWithObject(orderReceiveInf,
					 * INFTYPE.ORDERRECEIVEINF); } }
					 */
				}
			}
			// 确认完成
			else if (type.equalsIgnoreCase("orderFinish")) {
				if (order.getStatusId().equals(5L)) {
					order.setStatusId(6L);
					order.setFinishTime(new Date());

					// tdUserService.addTotalSpend(order.getUsername(),
					// order.getTotalPrice());
				}
			}
			// 确认取消
			else if (type.equalsIgnoreCase("orderCancel")) {
				if (order.getStatusId().equals(1L) || order.getStatusId().equals(2L) || order.getStatusId().equals(3L)) // zhangji
				{
					this.cancelRelativeOrderBySubOrder(order, username, req);
					order.setStatusId(7L);
					order.setCancelTime(new Date());
				}
			}
			tdOrderService.save(order);
			tdManagerLogService.addLog("edit", "修改订单", req);

			res.put("code", 0);
			res.put("message", "修改成功!");
			return res;
		}

		res.put("message", "参数错误!");
		return res;
	}

	/*---------------------------------------导入表格 begin -------------------------------*/

	/**************
	 * -----------------------------导入表格 end-----------------------------------
	 */

	@ModelAttribute
	public void getModel(@RequestParam(value = "payTypeId", required = false) Long payTypeId,
			@RequestParam(value = "deliveryTypeId", required = false) Long deliveryTypeId,
			@RequestParam(value = "diySiteId", required = false) Long diySiteId, Model model) {
		if (null != payTypeId) {
			model.addAttribute("tdPayType", tdPayTypeService.findOne(payTypeId));
		}

		if (null != deliveryTypeId) {
			model.addAttribute("tdDeliveryType", tdDeliveryTypeService.findOne(deliveryTypeId));
		}

		if (null != diySiteId) {
			model.addAttribute("tdDiySite", tdDiySiteService.findOne(diySiteId));
		}
	}

	@RequestMapping(value = "/own/edit")
	public String ownEdit(Long id, String __VIEWSTATE, ModelMap map, HttpServletRequest req) {
		String username = (String) req.getSession().getAttribute("manager");
		if (null == username) {
			return "redirect:/Verwalter/login";
		}

		map.addAttribute("__VIEWSTATE", __VIEWSTATE);
		if (null != id) {
			TdOwnMoneyRecord own = tdOwnMoneyRecordService.findOne(id);
			String mainOrderNumber = own.getOrderNumber();
			
			{
				TdOrderData orderData = tdOrderDataService.findByMainOrderNumber(mainOrderNumber);
				if (null != orderData) {
					map.addAttribute("agencyRefund", orderData.getAgencyRefund());
				}
			}
			
			System.out.println(mainOrderNumber.substring(2, mainOrderNumber.length()));
			List<TdOrder> orderList = tdOrderService
					.findByOrderNumberContaining(mainOrderNumber.substring(5, mainOrderNumber.length()));
			map.addAttribute("orderList", orderList);
			if (null != orderList && orderList.size() > 0) {
				map.addAttribute("order", orderList.get(0));
				Double total = 0d;
				for (TdOrder tdOrder : orderList) {
					total += tdOrder.getTotalPrice();
				}
				map.addAttribute("totalPrice", total);
			}
			map.addAttribute("consult", own);
			// 仓库
			if (null != orderList) {
				List<TdDeliveryInfo> deliveryList = tdDeliveryInfoService
						.findByOrderNumberOrderByBeginDtDesc(mainOrderNumber);
				if (null != deliveryList && deliveryList.size() > 0) {
					List<TdWareHouse> wareHouseList = tdWareHouseService
							.findBywhNumberOrderBySortIdAsc(deliveryList.get(0).getWhNo());
					if (null != wareHouseList && wareHouseList.size() > 0) {
						map.addAttribute("tdWareHouse", wareHouseList.get(0));
					}
				}
			}
		}
		return "/site_mag/own_edit";
	}

	@RequestMapping(value = "/own/save")
	@ResponseBody
	public Map<String, Object> ownSave(Long id, Long type, HttpServletRequest req) {
		Map<String, Object> map = new HashMap<String, Object>();
		Long[] ids = { id };
		if (type == 1) {
			btnEnale(ids);
		} else {
			btnNotEnale(ids);
		}
		map.put("code", 0);
		return map;
	}

	/**
	 * 欠款 还款
	 * 
	 * @param id
	 *            欠款id
	 * @param money
	 *            现金金额
	 * @param pos
	 *            pos金额
	 * @param req
	 * @return
	 */
	@RequestMapping(value = "/own/money")
	@ResponseBody
	public Map<String, Object> ownMoney(Long id, Double money, Double pos, Double other, String realPayTime,
			Long serialNumber, HttpServletRequest req) {
		Map<String, Object> res = new HashMap<String, Object>();
		String username = (String) req.getSession().getAttribute("manager");
		// 判断登录
		if (null == username) {
			res.put("message", "请重新登录");
			res.put("code", -1);
			return res;
		}
		// 查询欠款记录
		TdOwnMoneyRecord own = tdOwnMoneyRecordService.findOne(id);
		if (own == null) {
			res.put("message", "未找到欠款记录");
			res.put("code", -1);
			return res;
		}
		// 判断欠款状态
		if (own.getPayed() != null && own.getIsPayed()) {
			res.put("message", "欠款已还清");
			res.put("code", -1);
			return res;
		}
		// 为空设置默认值
		if (money == null) {
			money = 0.0;
		}
		if (pos == null) {
			pos = 0.0;
		}
		if (null == other) {
			other = 0.00;
		}
		// 判断是否还清
		if (!own.getOwned().equals((money + pos + other))) {
			res.put("message", "必须一次性还清");
			res.put("code", -1);
			return res;
		}
		if (null != serialNumber) {
			own.setSerialNumber(serialNumber);
		}
		// 设置值 并保存
		own.setBackMoney(money);
		own.setBackPos(pos);
		own.setBackOther(other);
		// 还款后增加欠款和收款
		own.setPayed(own.getPayed() + money + pos + other);
		own.setOwned(own.getOwned() - money - pos - other);
		own.setIsPayed(true);
		own.setOwnTime(new Date());

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date date = null;
		try {
			date = sdf.parse(realPayTime);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		own.setRealPayTime(date);
		tdOwnMoneyRecordService.save(own);

		// 查找TdOrderData，如果存在，则设置TdOrderData的值
		TdOrderData orderData = tdOrderDataService.findByMainOrderNumber(own.getOrderNumber());
		orderData.setSellerCash(own.getBackMoney());
		orderData.setSellerPos(own.getBackPos());
		orderData.setSellerOther(own.getBackOther());
		orderData.setDue(orderData.countDue());
		tdOrderDataService.save(orderData);

		// 修改订单收款金额
		tdOrderService.modifyOrderPay(money, pos, other, own.getOrderNumber());

		// 2017-02-13：增加信用额度
		String mainOrderNumber = own.getOrderNumber();
		TdOrder subOrder = tdOrderService.findByMainOrderNumberIgnoreCase(mainOrderNumber).get(0);
		// if (subOrder.getIsSellerOrder()) {
		TdUser seller = this.tdUserService.findOne(subOrder.getSellerId());
		this.tdUserService.repayCredit(CreditChangeType.REPAY, seller, (money + pos + other), mainOrderNumber);
		// }
		// 收款发ebs
		tdInterfaceService.initCashReciptByTdOwnMoneyRecord(own, INFConstants.INF_RECEIPT_TYPE_DIYSITE_INT);

		res.put("code", 0);
		res.put("message", "已还款");
		return res;
	}

	/**
	 * 到店支付 门店收款
	 * 
	 * @param id
	 *            订单id
	 * @param money
	 *            现金金额
	 * @param pos
	 *            pos金额
	 * @param req
	 * @return
	 */
	@RequestMapping(value = "/backMoney")
	@ResponseBody
	public Map<String, Object> backMoney(Long id, Double money, Double pos, Double other, String realPayTime,
			Long serialNumber, HttpServletRequest req) {
		Map<String, Object> res = new HashMap<String, Object>();
		String username = (String) req.getSession().getAttribute("manager");
		// 判断登录
		if (null == username) {
			res.put("message", "请重新登录");
			res.put("code", -1);
			return res;
		}

		// 查询订单是否存在
		TdOrder order = tdOrderService.findOne(id);
		if (order == null) {
			res.put("message", "未找到订单");
			res.put("code", -1);
			return res;
		}

		// 查询收款记录
		List<TdOwnMoneyRecord> ownList = tdOwnMoneyRecordService
				.findByOrderNumberIgnoreCase(order.getMainOrderNumber());
		if (ownList != null && ownList.size() > 0) {
			res.put("message", "已有收款记录不能在收款");
			res.put("code", -1);
			return res;
		}

		// 为空设置默认值
		if (money == null) {
			money = 0.0;
		}
		if (pos == null) {
			pos = 0.0;
		}
		if (null == other) {
			other = 0.00;
		}
		Double ownPrice = order.getTotalPrice();
		// 判断是否还清
		if (!ownPrice.equals((money + pos + other))) {
			res.put("message", "必须一次性交清不允许欠款");
			res.put("code", -1);
			return res;
		}
		// 保存收款记录
		TdOwnMoneyRecord rec = new TdOwnMoneyRecord();
		if (null != serialNumber) {
			rec.setSerialNumber(serialNumber);
		}
		rec.setCreateTime(new Date());
		rec.setOrderNumber(order.getMainOrderNumber());
		rec.setDiyCode(order.getDiySiteCode());
		rec.setOwned(0.0);
		rec.setPayed(money + pos + other);
		rec.setBackMoney(money);
		rec.setBackPos(pos);
		rec.setBackOther(other);
		rec.setUsername(order.getUsername());
		rec.setIsOwn(false);
		rec.setIsEnable(false);
		rec.setIsPayed(false);
		rec.setSortId(99L);
		rec.setPayTime(new Date());

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date date = null;
		try {
			date = sdf.parse(realPayTime);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		rec.setRealPayTime(date);
		tdOwnMoneyRecordService.save(rec);

		// 门店收款保存到订单
		order.setCashPay(money);
		order.setPosPay(pos);
		order.setBackOtherPay(other);
		tdOrderService.save(order);

		// 2017-02-13：增加信用额度
		TdUser seller = this.tdUserService.findOne(order.getSellerId());
		this.tdUserService.repayCredit(CreditChangeType.REPAY, seller, (money + pos + other),
				order.getMainOrderNumber());

		// 记录收款并发ebs
		tdInterfaceService.initCashReciptByTdOwnMoneyRecord(rec, INFConstants.INF_RECEIPT_TYPE_DIYSITE_INT);

		res.put("code", 0);
		res.put("message", "已收款");
		return res;
	}

	@RequestMapping(value = "/delivery/fee/change", method = RequestMethod.POST, produces = "application/json;charset=utf-8")
	@ResponseBody
	public Map<String, Object> deliveryFeeChange(Long id, Double fee) {
		Map<String, Object> res = new HashMap<>();

		TdOrder order = tdOrderService.findOne(id);
		order.setIsFixedDeliveryFee(true);
		order.setDeliverFee(fee);
		tdOrderService.save(order);
		String username = order.getUsername();
		TdUser user = tdUserService.findByUsername(username);
		tdPriceCountService.countPrice(order, user);
		res.put("status", 0);
		return res;
	}

	/**
	 * 根据问题跟踪表-20160120第55号（序号），一个分单取消的时候，与其相关联的所有分单也取消掉 req 记录库存用
	 * 
	 * @param order
	 */
	private void cancelRelativeOrderBySubOrder(TdOrder order, String username, HttpServletRequest req) {

		Long realUserId = order.getRealUserId();
		TdUser realUser = null;
		if (realUserId == null) {
			realUser = tdUserService.findByUsername(order.getUsername());
		} else {
			realUser = tdUserService.findOne(realUserId);
		}

		String orderNumberTemp = order.getOrderNumber();
		String newOrderNumber = "";
		// 通过计算得到订单的数字部分
		for (int i = 0; i < orderNumberTemp.length(); i++) {
			if (orderNumberTemp.charAt(i) >= 48 && orderNumberTemp.charAt(i) <= 57) {
				newOrderNumber += orderNumberTemp.charAt(i);
			}
		}
		List<TdOrder> list = tdOrderService.findByOrderNumberContaining(newOrderNumber);
		// 进行遍历操作
		if (null != list && list.size() > 0) {
			for (TdOrder subOrder : list) {
				if (null != subOrder) {
					// 设置订单状态为取消状态，同时记录已退货属性
					Long statusId = subOrder.getStatusId();
					if (null != statusId && 3L == statusId.longValue()) {

						// 增加库存
						tdDiySiteInventoryService.changeGoodsInventory(subOrder, 1L, req, "退货", null);

						// 通知物流
						if (StringUtils.isNotBlank(order.getRemarkInfo())) {
							order.setRemarkInfo(order.getRemarkInfo() + "管理员取消订单：" + username);
						} else {
							order.setRemarkInfo("管理员取消订单：" + username);
						}
						TdReturnNote returnNote = tdCommonService.MakeReturnNote(order, 1L, username);
						tdCommonService.sendBackMsgToWMS(returnNote);

						// 在此进行资金和优惠券的退还
						tdPriceCountService.cashAndCouponBack(subOrder, realUser, returnNote);
					}
					subOrder.setStatusId(7L);
					subOrder.setCancelTime(new Date());
					subOrder.setIsRefund(true);
					tdOrderService.save(subOrder);
				}
			}
		}
	}

	private void btnSave(String type, Long[] ids, Double[] sortIds) {
		if (null == type || type.isEmpty()) {
			return;
		}

		if (null == ids || null == sortIds || ids.length < 1 || sortIds.length < 1) {
			return;
		}

		for (int i = 0; i < ids.length; i++) {
			Long id = ids[i];

			if (type.equalsIgnoreCase("pay")) {
				TdPayType e = tdPayTypeService.findOne(id);

				if (null != e) {
					if (sortIds.length > i) {
						e.setSortId(new Double(sortIds[i]));
						tdPayTypeService.save(e);
					}
				}
			} else if (type.equalsIgnoreCase("delivery")) {
				TdDeliveryType e = tdDeliveryTypeService.findOne(id);

				if (null != e) {
					if (sortIds.length > i) {
						e.setSortId(new Double(sortIds[i]));
						tdDeliveryTypeService.save(e);
					}
				}
			} else if (type.equalsIgnoreCase("diysite")) {
				TdDiySite e = tdDiySiteService.findOne(id);

				if (null != e) {
					if (sortIds.length > i) {
						e.setSortId(new Double(sortIds[i]));
						tdDiySiteService.save(e);
					}
				}
			} else if (type.equalsIgnoreCase("codDistrict")) {
				TdShippingAddress e = tdShippingAddressService.findOne(id);

				if (null != e) {
					if (sortIds.length > i) {
						e.setSortId(new Double(sortIds[i]));
						tdShippingAddressService.save(e);
					}
				}
			}
		}
	}

	private void btnDelete(String type, Long[] ids, Integer[] chkIds) {
		if (null == type || type.isEmpty()) {
			return;
		}

		if (null == ids || null == chkIds || ids.length < 1 || chkIds.length < 1) {
			return;
		}

		for (int chkId : chkIds) {
			if (chkId >= 0 && ids.length > chkId) {
				Long id = ids[chkId];

				if (type.equalsIgnoreCase("pay")) {
					tdPayTypeService.delete(id);
				} else if (type.equalsIgnoreCase("delivery")) {
					tdDeliveryTypeService.delete(id);
				} else if (type.equalsIgnoreCase("diysite")) {
					tdDiySiteService.delete(id);
				} else if (type.equalsIgnoreCase("codDistrict")) {
					tdShippingAddressService.delete(id);
				}
			}
		}
	}

	private void btnConfirm(Long[] ids, Integer[] chkIds) {
		if (null == ids || null == chkIds || ids.length < 1 || chkIds.length < 1) {
			return;
		}

		for (int chkId : chkIds) {
			if (chkId >= 0 && ids.length > chkId) {
				Long id = ids[chkId];

				TdOrder tdOrder = tdOrderService.findOne(id);

				// 只有待确认(1L)订单能进行确认，确认后状态为待发货(3L)
				if (tdOrder.getStatusId().equals(1L)) {
					tdOrder.setStatusId(3L);
					tdOrder.setCheckTime(new Date()); // 确认时间
					tdOrderService.save(tdOrder);
				}
			}
		}
	}

	private void btnCancel(Long[] ids, Integer[] chkIds) {
		if (null == ids || null == chkIds || ids.length < 1 || chkIds.length < 1) {
			return;
		}

		for (int chkId : chkIds) {
			if (chkId >= 0 && ids.length > chkId) {
				Long id = ids[chkId];

				TdOrder tdOrder = tdOrderService.findOne(id);

				// 只有待确认(1L)、待付款(2L)订单能进行删除，确认后状态为已取消(7L)
				if (tdOrder.getStatusId().equals(1L) || tdOrder.getStatusId().equals(2L)) {
					tdOrder.setStatusId(7L);
					tdOrder.setCancelTime(new Date()); // 取消时间
					tdOrderService.save(tdOrder);
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

				TdOrder tdOrder = tdOrderService.findOne(id);

				// 只有已取消(7L)订单能进行删除
				if (tdOrder.getStatusId().equals(7L)) {
					tdOrderService.delete(tdOrder);
				}
			}
		}
	}

	private void btnEnale(Long[] chkIds) {
		if (null == chkIds || chkIds.length < 1) {
			return;
		}

		for (Long chkId : chkIds) {
			if (chkId >= 0) {
				TdOwnMoneyRecord ownMoneyRecord = tdOwnMoneyRecordService.findOne(chkId);
				// 已审核过就不能修改
				if (ownMoneyRecord != null && (ownMoneyRecord.getIsEnable() == null || !ownMoneyRecord.getIsEnable())) {

					/*
					 * //修改订单实际付款 逻辑修改 TdOrder
					 * order=tdOrderService.findByOrderNumber(ownMoneyRecord.
					 * getOrderNumber());
					 * order.setActualPay(order.getActualPay()==null?0:order.
					 * getActualPay()+ownMoneyRecord.getPayed());
					 * tdOrderService.save(order);
					 */

					ownMoneyRecord.setIsEnable(true);
					ownMoneyRecord.setIspassed(true);
					// 收款时间 门店审核通过时间 (配送员收款时间 门店审核通过时间)
					ownMoneyRecord.setPayTime(new Date());
					tdOwnMoneyRecordService.save(ownMoneyRecord);
					// 修改订单收款金额
					tdOrderService.modifyOrderPay(ownMoneyRecord.getMoney(), ownMoneyRecord.getPos(),
							ownMoneyRecord.getBackOther(), ownMoneyRecord.getOrderNumber());

					tdInterfaceService.initCashReciptByTdOwnMoneyRecord(ownMoneyRecord,
							INFConstants.INF_RECEIPT_TYPE_DELIVER_INT);

					// 2017-03-24修改：只有当审核同意的时候才归还信用额度
					String orderNumber = ownMoneyRecord.getOrderNumber();
					List<TdOrder> orderList = tdOrderService.findByMainOrderNumberIgnoreCase(orderNumber);
					if (null != orderList && orderList.size() > 0) {
						TdOrder order = orderList.get(0);
						if (null != order.getIsSellerOrder() && order.getIsSellerOrder()) {
							TdUser seller = tdUserService.findOne(order.getSellerId());
							tdUserService.repayCredit(CreditChangeType.REPAY, seller, ownMoneyRecord.getPayed(),
									order.getMainOrderNumber());
						}
					}
				}
			}
		}
	}

	private void btnNotEnale(Long[] chkIds) {
		if (null == chkIds || chkIds.length < 1) {
			return;
		}

		for (Long chkId : chkIds) {
			if (chkId >= 0) {
				TdOwnMoneyRecord ownMoneyRecord = tdOwnMoneyRecordService.findOne(chkId);
				if (ownMoneyRecord != null && (ownMoneyRecord.getIsEnable() == null || !ownMoneyRecord.getIsEnable())) {
					ownMoneyRecord.setIsEnable(true);
					ownMoneyRecord.setIspassed(false);
					tdOwnMoneyRecordService.save(ownMoneyRecord);
				}
			}
		}
	}

	private Map<String, String> getUserRealNameFormTdOder(List<TdOrder> orders) {

		Map<String, String> map = new HashMap<>();
		for (TdOrder tdOrder : orders) {
			String username = tdOrder.getRealUserUsername();
			if (username == null) {
				username = tdOrder.getUsername();
			}
			if (map.containsKey(username)) {
				continue;
			} else {
				TdUser tdUser = tdUserService.findByUsername(username);
				if (null != tdUser && StringUtils.isNotBlank(username)) {
					map.put(username, tdUser.getRealName());
				}
			}
		}

		return map;
	}
}
