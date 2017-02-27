package com.ynyes.lyz.controller.management;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ynyes.lyz.entity.TdCity;
import com.ynyes.lyz.entity.TdDeliveryFeeChangeLog;
import com.ynyes.lyz.entity.TdDeliveryInfo;
import com.ynyes.lyz.entity.TdDiySite;
import com.ynyes.lyz.entity.TdManager;
import com.ynyes.lyz.entity.TdManagerRole;
import com.ynyes.lyz.entity.TdOrder;
import com.ynyes.lyz.entity.TdOwnMoneyRecord;
import com.ynyes.lyz.entity.TdPayType;
import com.ynyes.lyz.entity.TdWareHouse;
import com.ynyes.lyz.entity.delivery.TdOrderDeliveryFeeDetail;
import com.ynyes.lyz.entity.user.TdUser;
import com.ynyes.lyz.service.TdArticleService;
import com.ynyes.lyz.service.TdCityService;
import com.ynyes.lyz.service.TdDeliveryFeeChangeLogService;
import com.ynyes.lyz.service.TdDeliveryInfoService;
import com.ynyes.lyz.service.TdDeliveryTypeService;
import com.ynyes.lyz.service.TdDiySiteRoleService;
import com.ynyes.lyz.service.TdDiySiteService;
import com.ynyes.lyz.service.TdGoodsService;
import com.ynyes.lyz.service.TdManagerLogService;
import com.ynyes.lyz.service.TdManagerRoleService;
import com.ynyes.lyz.service.TdManagerService;
import com.ynyes.lyz.service.TdOrderDeliveryFeeDetailService;
import com.ynyes.lyz.service.TdOrderService;
import com.ynyes.lyz.service.TdOwnMoneyRecordService;
import com.ynyes.lyz.service.TdPayTypeService;
import com.ynyes.lyz.service.TdPriceCountService;
import com.ynyes.lyz.service.TdProductCategoryService;
import com.ynyes.lyz.service.TdSettingService;
import com.ynyes.lyz.service.TdShippingAddressService;
import com.ynyes.lyz.service.TdUserService;
import com.ynyes.lyz.service.TdWareHouseService;
import com.ynyes.lyz.util.SiteMagConstant;

@Controller
@RequestMapping(value = "/Verwalter/delivery/fee/change")
public class TdManagerDeliveryFeeChangeController {

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
	private TdManagerService tdManagerService;

	@Autowired
	private TdManagerRoleService tdManagerRoleService;

	@Autowired
	private TdOwnMoneyRecordService tdOwnMoneyRecordService;

	@Autowired
	private TdDeliveryInfoService tdDeliveryInfoService;

	@Autowired
	private TdWareHouseService tdWareHouseService;

	@Autowired
	private TdPriceCountService tdPriceCountService;

	@Autowired
	private TdDiySiteRoleService tdDiySiteRoleService;
	
	@Autowired
	private TdDeliveryFeeChangeLogService tdDeliveryFeeChangeLogService;
	
	@Autowired
	private TdOrderDeliveryFeeDetailService tdOrderDeliveryFeeDetailService;

	@RequestMapping(value = "/list")
	public String goodsListDialog(String keywords, Integer page, Integer size, String __EVENTTARGET,
			String __EVENTARGUMENT, String __VIEWSTATE, Long[] listId, Integer[] listChkId, ModelMap map,
			HttpServletRequest req, String orderStartTime, String orderEndTime, String realName, String sellerRealName,
			String shippingAddress, String shippingPhone, String deliveryTime, String userPhone, Long orderStatusId,
			String shippingName, String sendTime, String diyCode, String city) {
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

		Long statusId = 2L;

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
			if (__EVENTTARGET.equalsIgnoreCase("btnPage")) {
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

				map.addAttribute("order_page",
						tdOrderService.findAll(keywords, orderStartTime, orderEndTime, usernameList, sellerRealName,
								shippingAddress, shippingPhone, deliveryTime, userPhone, shippingName, sendTime,
								statusId, diyCode, city, cityDiyCodes, roleDiyCodes, size, page));
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

		return "/site_mag/delivery_fee_change_list";
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

		return "/site_mag/delivery_fee_change_edit";
	}

	@RequestMapping(value = "/save", method = RequestMethod.POST, produces = "application/json;charset=utf-8")
	@ResponseBody
	public Map<String, Object> deliveryFeeChange(HttpServletRequest request, Long id, Double fee) {
		Map<String, Object> res = new HashMap<>();
		String manager = (String) request.getSession().getAttribute("manager");
		
		TdOrder order = tdOrderService.findOne(id);
		
		Double origin = order.getDeliverFee();
		
		order.setIsFixedDeliveryFee(true);
		order.setDeliverFee(fee);
		tdOrderService.save(order);
		String username = order.getUsername();
		TdUser user = tdUserService.findByUsername(username);
		tdPriceCountService.countPrice(order, user);
		TdOrderDeliveryFeeDetail  detail = tdOrderDeliveryFeeDetailService.findByMainOrderNumber(order.getOrderNumber());
		if(null != detail){
			detail.setIsCustomerDeliveryFeeModified(true);
			detail.setConsumerDeliveryFeeFinal(fee);;
			tdOrderDeliveryFeeDetailService.save(detail);
		}
		
		TdDeliveryFeeChangeLog log = new TdDeliveryFeeChangeLog();
		log.setManager(manager);
		log.setOrderNumber(order.getOrderNumber());
		log.setOldFee(origin);
		log.setNewFee(fee);
		log.setOperationTime(new Date());
		tdDeliveryFeeChangeLogService.save(log);
		
		res.put("status", 0);
		return res;
	}
	
	@RequestMapping(value = "/log/list", produces = "text/html;charset=utf-8")
	public String logList(Integer page, Integer size, String keywords, String __EVENTTARGET, String __EVENTARGUMENT,
			String __VIEWSTATE, HttpServletRequest req, ModelMap map) {

		String username = (String) req.getSession().getAttribute("manager");
		if (null == username) {
			return "redirect:/Verwalter/login";
		}
		
    	if (null != __EVENTTARGET) {
			if (__EVENTTARGET.equalsIgnoreCase("btnPage")) {
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

		if (null != keywords) {
			keywords = keywords.trim();
		}
		
		Page<TdDeliveryFeeChangeLog> logPage = null;
		if (StringUtils.isEmpty(keywords)) {
			logPage = tdDeliveryFeeChangeLogService.findAll(page, size);
		} else {
			logPage = tdDeliveryFeeChangeLogService.findByKeywords(keywords, page, size);
		}
		
		map.addAttribute("logPage", logPage);
		map.addAttribute("page", page);
		map.addAttribute("size", size);
		map.addAttribute("keywords", keywords);
		
		return "/site_mag/delivery_fee_change_log_list";
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
