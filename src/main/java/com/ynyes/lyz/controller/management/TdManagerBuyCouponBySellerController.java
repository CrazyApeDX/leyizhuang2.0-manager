package com.ynyes.lyz.controller.management;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ibm.icu.math.BigDecimal;
import com.ynyes.lyz.entity.TdActivity;
import com.ynyes.lyz.entity.TdActivityGift;
import com.ynyes.lyz.entity.TdActivityGiftList;
import com.ynyes.lyz.entity.TdBrand;
import com.ynyes.lyz.entity.TdCity;
import com.ynyes.lyz.entity.TdCoupon;
import com.ynyes.lyz.entity.TdDiySite;
import com.ynyes.lyz.entity.TdGoods;
import com.ynyes.lyz.entity.TdManager;
import com.ynyes.lyz.entity.TdManagerRole;
import com.ynyes.lyz.entity.TdOrder;
import com.ynyes.lyz.entity.TdOrderGoods;
import com.ynyes.lyz.entity.TdOwnMoneyRecord;
import com.ynyes.lyz.entity.TdPriceList;
import com.ynyes.lyz.entity.TdPriceListItem;
import com.ynyes.lyz.entity.TdProductCategory;
import com.ynyes.lyz.entity.user.TdUser;
import com.ynyes.lyz.interfaces.entity.TdCashReciptInf;
import com.ynyes.lyz.interfaces.service.TdCashReciptInfService;
import com.ynyes.lyz.interfaces.service.TdInterfaceService;
import com.ynyes.lyz.interfaces.utils.EnumUtils.INFTYPE;
import com.ynyes.lyz.interfaces.utils.StringTools;
import com.ynyes.lyz.service.TdActivityGiftService;
import com.ynyes.lyz.service.TdActivityService;
import com.ynyes.lyz.service.TdBrandService;
import com.ynyes.lyz.service.TdCityService;
import com.ynyes.lyz.service.TdCommonService;
import com.ynyes.lyz.service.TdCouponService;
import com.ynyes.lyz.service.TdDiySiteService;
import com.ynyes.lyz.service.TdGoodsService;
import com.ynyes.lyz.service.TdManagerRoleService;
import com.ynyes.lyz.service.TdManagerService;
import com.ynyes.lyz.service.TdOrderGoodsService;
import com.ynyes.lyz.service.TdOrderService;
import com.ynyes.lyz.service.TdOwnMoneyRecordService;
import com.ynyes.lyz.service.TdPriceListItemService;
import com.ynyes.lyz.service.TdPriceListService;
import com.ynyes.lyz.service.TdProductCategoryService;
import com.ynyes.lyz.service.TdUserService;
import com.ynyes.lyz.util.SiteMagConstant;

/**
 * 门店员工买产品券的控制器
 * 
 * @author DengXiao
 */
@Controller
@RequestMapping(value = "/Verwalter/buy/coupon/by/seller")
public class TdManagerBuyCouponBySellerController {

	@Autowired
	private TdUserService tdUserService;

	@Autowired
	private TdGoodsService tdGoodsService;

	@Autowired
	private TdPriceListService tdPriceListService;

	@Autowired
	private TdPriceListItemService tdPriceListItemService;

//	@Autowired
//	private TdCouponModuleService tdCouponModuleService;

	@Autowired
	private TdOrderGoodsService tdOrderGoodsService;

	@Autowired
	private TdDiySiteService tdDiySiteService;

	@Autowired
	private TdActivityService tdActivityService;

	@Autowired
	private TdCouponService tdCouponService;

	@Autowired
	private TdProductCategoryService tdProductCategoryService;

	@Autowired
	private TdActivityGiftService tdActivityGiftService;

	@Autowired
	private TdOrderService tdOrderService;

	@Autowired
	private TdBrandService tdBrandService;

	@Autowired
	private TdCommonService tdCommonService;

	@Autowired
	private TdInterfaceService tdInterfaceService;

	@Autowired
	private TdCashReciptInfService tdCashReciptInfService;

	@Autowired
	private TdOwnMoneyRecordService tdOwnMoneyRecordService;
	
	@Autowired
	private TdCityService tdCityService;
	
	@Autowired
	private TdManagerService tdManagerService;
	
	@Autowired
	private TdManagerRoleService tdManagerRoleService;

	@RequestMapping
	public String index(HttpServletRequest req, ModelMap map) {
		String username = (String) req.getSession().getAttribute("manager");
		if (null == username) {
			return "redirect:/Verwalter/login";
		}

		return "/site_mag/buy_coupon_by_seller";
	}

	@RequestMapping(value = "/get/name", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> getRealName(String username, Long type) {
		Map<String, Object> res = new HashMap<>();
		res.put("status", -1);
		TdUser user = tdUserService.findByUsername(username);
		if (0L == type.longValue()) {
			if (null != user && null != user.getUserType() && 0L == user.getUserType().longValue()) {
				res.put("message", user.getRealName());
			} else {
				res.put("message", "用户手机号码不正确，未查询到正确的用户信息");
				return res;
			}
		} else if (1L == type.longValue()) {
			if (null != user && null != user.getUserType() && 0L != user.getUserType().longValue()
					&& 5L != user.getUserType().longValue()) {
				res.put("message", user.getRealName());
			} else {
				res.put("message", "用户手机号码不正确，未查询到正确的销顾信息");
				return res;
			}
		}

		res.put("status", 0);
		return res;
	}

	@RequestMapping(value = "/dialog")
	public String getDialog(String keywords, Integer page, Integer size, ModelMap map, String username) {
		TdUser user = tdUserService.findByUsername(username);
		if (null != user) {
			if (null == page || page < 0) {
				page = 0;
			}

			if (null == size || size <= 0) {
				size = SiteMagConstant.pageSize;
			}

			if (null != keywords) {
				keywords = keywords.trim();
			}

			Page<TdGoods> goods_page = null;
			if (null != keywords) {
				goods_page = tdGoodsService.findByCodeContainingOrTitleContainingOrSubTitleContaining(keywords, page,
						size);
			} else {
				// goods_page = tdGoodsService.findAll(page, size);
			}

			if (null != goods_page) {
				for (int i = 0; i < goods_page.getContent().size(); i++) {
					TdGoods goods = goods_page.getContent().get(i);
					Double price = tdCommonService.getPrice(goods.getId(), username);
					goods.setSalePrice(price);
				}
			}

			map.addAttribute("goods_page", goods_page);
		}
		return "/site_mag/buy_coupon_dialog";
	}
	
	@RequestMapping(value = "/dialog/user")
	public String getDialogUser(String keywords, Integer page, Integer size, ModelMap map, String username) {
		if (null == page || page < 0) {
			page = 0;
		}

		if (null == size || size <= 0) {
			size = SiteMagConstant.pageSize;
		}

		if (null != keywords) {
			keywords = keywords.trim();
		}

		Page<TdUser> user_page = null;
		if (null != keywords) {
			user_page = tdUserService.findByUsernameContainingOrRealNameContainingAndUserType(keywords, page,
					size,0L);
		} else {
			// goods_page = tdGoodsService.findAll(page, size);
		}

		map.addAttribute("user_page", user_page);
		return "/site_mag/buy_coupon_dialog_user";
	}
	
	@RequestMapping(value = "/dialog/seller")
	public String getDialogSeller(HttpServletRequest request,String keywords, Integer page, Integer size, ModelMap map, String username) {
		
		String adminUser = (String) request.getSession().getAttribute("manager");
		TdManager tdManager = tdManagerService.findByUsernameAndIsEnableTrue(adminUser);
		TdManagerRole tdManagerRole = null;
		if (null != tdManager && null != tdManager.getRoleId())
		{
			tdManagerRole = tdManagerRoleService.findOne(tdManager.getRoleId());
		}
		TdUser user = tdUserService.findByUsername(username); 
		String diyCode = null;
		if (tdManagerRole.getTitle().equalsIgnoreCase("门店")||tdManagerRole.getTitle().equalsIgnoreCase("郑州门店")) 
		{
        	diyCode=tdManager.getDiyCode();
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

		Page<TdUser> seller_page = null;
		List<Long> userTypeList = new ArrayList<>();
		userTypeList.add(1L);
		userTypeList.add(2L);
		if (null != keywords) {
			if(null != diyCode){
				seller_page = tdUserService.findByUsernameContainingOrRealNameContainingAndDiyCodeAndUserTypeIn(keywords, page,
						size,diyCode,userTypeList);
			}else{
				
				seller_page = tdUserService.findByUsernameContainingOrRealNameContainingAndCityAndUserTypeIn(keywords, page,
						size,user.getCityName(),userTypeList);
			}
			
		} else {
			// goods_page = tdGoodsService.findAll(page, size);
		}

		map.addAttribute("seller_page", seller_page);
		return "/site_mag/buy_coupon_dialog_seller";
	}


	@RequestMapping(value = "/count", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> countPrice(HttpServletRequest req, String username, String sellerUsername, Long[] ids,
			Long[] numbers, Long[] coupons, String remark) {
		Map<String, Object> res = new HashMap<>();
		res.put("status", -1);

		Double total = 0.00;
		Double totalGoods = 0.00;
		Double totalCoupon = 0.00;
		TdUser user = tdUserService.findByUsername(username);
		if (null == user || user.getUserType().longValue() != 0L) {
			res.put("message", "用户不存在");
			return res;
		}

		TdUser seller = tdUserService.findByUsername(sellerUsername);
		if (null == seller || seller.getUserType().longValue() == 0L || seller.getUserType().longValue() == 5L) {
			res.put("message", "销顾不存在");
			return res;
		}

		if (remark.contains("&")) {
			res.put("message", "备注不能输入特殊符号&");
			return res;
		} else if (remark.contains("'")) {
			res.put("message", "备注不能输入特殊符号'");
			return res;
		}

		Map<Long, Object[]> param = new HashMap<>();

		Long siteId = seller.getUpperDiySiteId();
		TdDiySite site = tdDiySiteService.findOne(siteId);

		StringBuffer cashCouponId = new StringBuffer();
		List<TdOrderGoods> orderGoodsList = new ArrayList<>();

		// 获取用户的城市
		Long cityId = user.getCityId();
		TdCity city = tdCityService.findBySobIdCity(cityId);

		String cityShortName = null;
		switch (city.getCityName()) {
		case "成都市":
			cityShortName = "CD_";
			break;
		case "郑州市":
			cityShortName = "ZZ_";
			break;
		}

		// 创建订单号
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmssSSS");
		Date now = new Date();
		String sDate = sdf.format(now);
		Random random = new Random();
		Integer suiji = random.nextInt(900) + 100;
		String orderNum = cityShortName + "XN" + sDate + suiji;

		// 创建订单
		TdOrder order = new TdOrder();
		order.setOrderNumber(orderNum);
		order.setUserId(user.getId());
		order.setDiySiteId(site.getId());
		order.setDiySiteCode(site.getStoreCode());
		order.setDiySiteName(site.getTitle());
		order.setDiySitePhone(site.getServiceTele());
		order.setOrderTime(new Date());
		order.setPayTime(new Date());
		order.setSendTime(new Date());
		order.setReceiveTime(new Date());
		// order.setStatusId(5L);
		order.setUsername(sellerUsername);
		order.setRemark(remark);

		for (int i = 0; i < ids.length; i++) {
			Long id = ids[i];
			// 获取指定的商品
			TdGoods goods = tdGoodsService.findOne(id);
			// 获取商品的价格
			Double price = tdCommonService.getPrice(goods.getId(), username);
			if (null == price) {
				res.put("message", "id为" + id + "的商品无法查询到其价格");
				return res;
			}
			// 查找券模板
//			TdCouponModule module = tdCouponModuleService.findByGoodsIdAndCityIdAndType(goods.getId(), user.getCityId(),
//					1L);
			TdPriceListItem priceListItem = tdCommonService.getGoodsPrice(city.getSobIdCity(), goods.getId());
			Double subPrice = 0.00;
//			if (null != module && null != module.getPrice()) {
//				subPrice = module.getPrice();
//			}
			subPrice = priceListItem.getCouponPrice() - priceListItem.getCouponRealPrice();
			total += (price * numbers[i]) - (subPrice * coupons[i]);
			totalGoods += (price * numbers[i]);
			totalCoupon += (subPrice * coupons[i]);

			Object[] var = new Object[2];
			var[0] = numbers[i];
			var[1] = (price * numbers[i]) - (subPrice * coupons[i]);
			param.put(goods.getId(), var);

			// 创建订单商品
			TdOrderGoods orderGoods = new TdOrderGoods();
			orderGoods.setGoodsId(goods.getId());
			orderGoods.setGoodsTitle(goods.getTitle());
			orderGoods.setGoodsSubTitle(goods.getSubTitle());
			orderGoods.setGoodsCoverImageUri(goods.getCoverImageUri());
			orderGoods.setSku(goods.getCode());
			orderGoods.setPrice(price);
			orderGoods.setRealPrice(this.getRealPrice(goods, username));
			orderGoods.setQuantity(numbers[i]);
			orderGoods.setCashNumber(coupons[i]);
			orderGoods.setBrandId(goods.getBrandId());
			orderGoods.setBrandTitle(goods.getBrandTitle());
			orderGoods.setType(1L);
			orderGoods.setCouponMoney(subPrice * coupons[i]);
			orderGoods.setCashNumber(coupons[i]);
			orderGoods.setIsWallAccessory(goods.getIsWallAccessory());
			tdOrderGoodsService.save(orderGoods);
			orderGoodsList.add(orderGoods);
		}

		order.setOrderGoodsList(orderGoodsList);
		order.setTotalGoodsPrice(totalGoods);
		order.setTotalPrice(total);
		order.setCashCouponId(cashCouponId.toString());
		order.setCashCoupon(totalCoupon);
		order.setIsCoupon(true);
		order.setSellerId(seller.getId());
		order.setSellerUsername(sellerUsername);
		order.setSellerRealName(seller.getRealName());
		order.setRealUserUsername(user.getUsername());
		order.setRealUserId(user.getId());
		order.setRealUserRealName(user.getRealName());
		order.setUsername(user.getUsername());

		order = this.getPresent(username, order);
		order = this.getGift(username, order);
		Double activitySubPrice = null == order.getActivitySubPrice() ? 0d : order.getActivitySubPrice();
		order.setActivitySubPrice(activitySubPrice);
		order.setTotalPrice(order.getTotalPrice() - activitySubPrice);

		// 计算满减分摊价
		if (activitySubPrice > 0d) {
			this.subActivityPriceShare(order, param);
		}

		req.getSession().setAttribute("MANAGER_ORDER", order);

		res.put("total", order.getTotalPrice());

		/* 2016-12-08修改：获取用户的预存款总额 */
		res.put("balance", user.getBalance());

		res.put("status", 0);
		return res;
	}
	
	@RequestMapping(value = "/get/present")
	public String getPresent(HttpServletRequest req, Model model,String username, String sellerUsername, Long[] ids,
			Long[] numbers, Long[] coupons, String remark) {
		String errMsg = null;
		Double total = 0.00;
		Double totalGoods = 0.00;
		Double totalCoupon = 0.00;
		TdUser user = tdUserService.findByUsername(username);
		if (null == user || user.getUserType().longValue() != 0L) {
			errMsg="用户不存在";
			return "/site_mag/buy_coupon_dialog_present";
		}

		TdUser seller = tdUserService.findByUsername(sellerUsername);
		if (null == seller || seller.getUserType().longValue() == 0L || seller.getUserType().longValue() == 5L) {
			errMsg="销顾不存在";
			return "/site_mag/buy_coupon_dialog_present";
		}

		Map<Long, Object[]> param = new HashMap<>();

		Long siteId = seller.getUpperDiySiteId();
		TdDiySite site = tdDiySiteService.findOne(siteId);

		StringBuffer cashCouponId = new StringBuffer();
		List<TdOrderGoods> orderGoodsList = new ArrayList<>();

		// 获取用户的城市
		Long cityId = user.getCityId();
		TdCity city = tdCityService.findBySobIdCity(cityId);

		String cityShortName = null;
		switch (city.getCityName()) {
		case "成都市":
			cityShortName = "CD_";
			break;
		case "郑州市":
			cityShortName = "ZZ_";
			break;
		}

		// 创建订单号
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmssSSS");
		Date now = new Date();
		String sDate = sdf.format(now);
		Random random = new Random();
		Integer suiji = random.nextInt(900) + 100;
		String orderNum = cityShortName + "XN" + sDate + suiji;

		// 创建订单
		TdOrder order = new TdOrder();
		order.setOrderNumber(orderNum);
		order.setUserId(user.getId());
		order.setDiySiteId(site.getId());
		order.setDiySiteCode(site.getStoreCode());
		order.setDiySiteName(site.getTitle());
		order.setDiySitePhone(site.getServiceTele());
		order.setOrderTime(new Date());
		order.setPayTime(new Date());
		order.setSendTime(new Date());
		order.setReceiveTime(new Date());
		// order.setStatusId(5L);
		order.setUsername(sellerUsername);
		order.setRemark(remark);

		for (int i = 0; i < ids.length; i++) {
			Long id = ids[i];
			// 获取指定的商品
			TdGoods goods = tdGoodsService.findOne(id);
			// 获取商品的价格
			Double price = tdCommonService.getPrice(goods.getId(), username);
			if (null == price) {
				errMsg = "id为" + id + "的商品无法查询到其价格";
				return "/site_mag/buy_coupon_dialog_present";
			}
			// 查找券模板
//			TdCouponModule module = tdCouponModuleService.findByGoodsIdAndCityIdAndType(goods.getId(), user.getCityId(),
//					1L);
			TdPriceListItem priceListItem = tdCommonService.getGoodsPrice(city.getSobIdCity(), goods.getId());
			Double subPrice = 0.00;
//			if (null != module && null != module.getPrice()) {
//				subPrice = module.getPrice();
//			}
			subPrice = priceListItem.getCouponPrice() - priceListItem.getCouponRealPrice();
			total += (price * numbers[i]) - (subPrice * coupons[i]);
			totalGoods += (price * numbers[i]);
			totalCoupon += (subPrice * coupons[i]);

			Object[] var = new Object[2];
			var[0] = numbers[i];
			var[1] = (price * numbers[i]) - (subPrice * coupons[i]);
			param.put(goods.getId(), var);

			// 创建订单商品
			TdOrderGoods orderGoods = new TdOrderGoods();
			orderGoods.setGoodsId(goods.getId());
			orderGoods.setGoodsTitle(goods.getTitle());
			orderGoods.setGoodsSubTitle(goods.getSubTitle());
			orderGoods.setGoodsCoverImageUri(goods.getCoverImageUri());
			orderGoods.setSku(goods.getCode());
			orderGoods.setPrice(price);
			orderGoods.setRealPrice(this.getRealPrice(goods, username));
			orderGoods.setQuantity(numbers[i]);
			orderGoods.setCashNumber(coupons[i]);
			orderGoods.setBrandId(goods.getBrandId());
			orderGoods.setBrandTitle(goods.getBrandTitle());
			orderGoods.setType(1L);
			orderGoods.setCouponMoney(subPrice * coupons[i]);
			orderGoods.setCashNumber(coupons[i]);
			orderGoods.setIsWallAccessory(goods.getIsWallAccessory());
			//tdOrderGoodsService.save(orderGoods);
			orderGoodsList.add(orderGoods);
		}

		order.setOrderGoodsList(orderGoodsList);
		order.setTotalGoodsPrice(totalGoods);
		order.setTotalPrice(total);
		order.setCashCouponId(cashCouponId.toString());
		order.setCashCoupon(totalCoupon);
		order.setIsCoupon(true);
		order.setSellerId(seller.getId());
		order.setSellerUsername(sellerUsername);
		order.setSellerRealName(seller.getRealName());
		order.setRealUserUsername(user.getUsername());
		order.setRealUserId(user.getId());
		order.setRealUserRealName(user.getRealName());
		order.setUsername(user.getUsername());

		order = this.getPresent(username, order);
		order = this.getGift(username, order);
		Double activitySubPrice = null == order.getActivitySubPrice() ? 0d : order.getActivitySubPrice();
		order.setActivitySubPrice(activitySubPrice);
		order.setTotalPrice(order.getTotalPrice() - activitySubPrice);

		// 计算满减分摊价
		if (activitySubPrice > 0d) {
			this.subActivityPriceShare(order, param);
		}
		model.addAttribute("errMsg",errMsg);
		model.addAttribute("presentList",order.getPresentedList());
		req.setAttribute("errMsg", errMsg);
		return "/site_mag/buy_coupon_dialog_present";
		
	}

	@RequestMapping(value = "/create/order", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> createOrder(HttpServletRequest req, ModelMap map, String username, String sellerUsername,
			Long[] ids, Long[] numbers, Long[] coupons, Double pos, Double cash, Double other, String realPayTime) {
		Map<String, Object> res = new HashMap<>();
		res.put("status", -1);

		TdOrder order = (TdOrder) req.getSession().getAttribute("MANAGER_ORDER");

		pos = null == pos ? 0 : pos;
		cash = null == cash ? 0 : cash;
		other = null == other ? 0 : other;

		order.setPosPay(pos);
		order.setCashPay(cash);
		order.setBackOtherPay(other);

		tdOrderService.save(order);

		// 在此生成ownMoneyRecord
		TdOwnMoneyRecord own = new TdOwnMoneyRecord();
		own.setCreateTime(new Date());
		own.setOrderNumber(order.getOrderNumber());
		own.setPayed(pos + cash + other);
		own.setSortId(99l);
		own.setUsername(order.getUsername());
		own.setDiyCode(order.getDiySiteCode());
		own.setBackMoney(cash);
		own.setBackOther(other);
		own.setBackPos(pos);
		own.setPayTime(new Date());
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date date = null;
		try {
			date = sdf.parse(realPayTime);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		own.setRealPayTime(date);
		tdOwnMoneyRecordService.save(own);

		this.dismantleOrder(order, username);

		res.put("status", 0);
		return res;
	}

	// 获取指定商品的券价格
	private Double getRealPrice(TdGoods goods, String username) {
		TdUser user = tdUserService.findByUsername(username);
		Long sobId = null;
		if (null != user) {
			sobId = user.getCityId();
		}
		if (null == goods) {
			return null;
		}

		if (null == goods.getInventoryItemId()) {
			return null;
		}

		String productFlag = goods.getBrandTitle();

		if (null == productFlag) {
			return null;
		}

		String priceType = null;

		// 零售价
		if (productFlag.equalsIgnoreCase("华润")) {
			priceType = "LS";
		}
		// 乐意装价
		else if (productFlag.equalsIgnoreCase("乐易装")) {
			priceType = "LYZ";
		}
		// 莹润价
		else if (productFlag.equalsIgnoreCase("莹润")) {
			priceType = "YR";
		}
		// 不支持的价格
		else {
			return null;
		}

		List<TdPriceList> priceList_list = tdPriceListService
				.findBySobIdAndPriceTypeAndStartDateActiveAndEndDateActive(sobId, priceType, new Date(), new Date());

		if (null == priceList_list || priceList_list.size() == 0 || priceList_list.size() > 1) {
			return null;
		}

		// 价目表ID
		Long list_header_id = 0L;
		list_header_id = priceList_list.get(0).getListHeaderId();

		List<TdPriceListItem> priceItemList = tdPriceListItemService
				.findByListHeaderIdAndInventoryItemIdAndStartDateActiveAndEndDateActive(list_header_id,
						goods.getInventoryItemId(), new Date(), new Date());

		if (null == priceItemList || priceItemList.size() == 0 || priceItemList.size() > 1) {
			return null;
		}

		return priceItemList.get(0).getCouponRealPrice();
	}

	// 获取赠品的方法
	public TdOrder getPresent(String username, TdOrder order) {

		// 获取赠品列表
		List<TdOrderGoods> presentedList = order.getPresentedList();

		if (null == presentedList) {
			presentedList = new ArrayList<>();
		}

		Long giftType = 0L;

		if (null != order.getIsCoupon() && order.getIsCoupon()) {
			giftType = 1L;
		}

		// 获取用户的门店
		TdUser user = tdUserService.findByUsername(username);
		TdDiySite diySite = tdDiySiteService.findOne(user.getUpperDiySiteId());
		// 获取用户门店所能参加的活动
		List<TdActivity> activity_list = tdActivityService
				.findByDiySiteIdsContainingAndBeginDateBeforeAndFinishDateAfterAndGiftTypeOrderBySortIdAsc(
						diySite.getId() + "", new Date(), giftType);
		// 为了避免脏数据刷新，创建一个map用于存储已选【id：数量】
		Map<Long, Long> selected_map = new HashMap<>();

		for (TdOrderGoods cartGoods : order.getOrderGoodsList()) {
			Long id = cartGoods.getGoodsId();
			Long quantity = cartGoods.getQuantity();

			selected_map.put(id, quantity);
		}
		for (TdActivity activity : activity_list) {
			// 创建一个布尔变量表示已选商品能否参加指定的活动
			Boolean isJoin = true;
			// -------------------------------2016-05-20
			// 09:45:15------------------------------------------
			// 创建一个布尔变量用于判断该活动是存在浮动商品
			Boolean isFloat = false;
			// 获取该活动的最低购买量
			Long totalNumber = activity.getTotalNumber();
			if (null == totalNumber) {
				totalNumber = 0L;
			}
			// 创建一个变量用于表示实际购买量
			Long realBuy = 0L;
			// 创建一个变量用于表示浮动量
			Long floatCount = 0L;

			// 创建一个变量用于表示最低购买金额
			Double totalPrice = activity.getTotalPrice();

			// 创建一个变量用于表示立减金额
			Double subPrice = activity.getSubPrice();
			if (subPrice == null) {
				subPrice = 0d;
			}

			// 创建一个存储顺序的集合
			List<Long> sortList = new ArrayList<>();
			// --------------------------------------------------------------------------------------------

			Boolean isCombo = activity.getIsCombo();
			Boolean isEnoughMoney = activity.getIsEnoughMoney();

			String buyCouponId = order.getBuyCouponId();
			if (null != buyCouponId && !"".equals(buyCouponId)) {
				presentedList = new ArrayList<>();

				List<TdOrderGoods> orderGoodsList = order.getOrderGoodsList();
				if (null != orderGoodsList && orderGoodsList.size() > 0) {
					for (TdOrderGoods orderGoods : orderGoodsList) {
						Long id = orderGoods.getGoodsId();
						Long quantity = orderGoods.getQuantity();
						selected_map.put(id, quantity);
					}
				}

				String[] ids = buyCouponId.split(",");
				if (null != ids && ids.length > 0) {
					for (String sid : ids) {
						if (null != sid && !"".equals(sid)) {
							Long id = Long.parseLong(sid);
							TdCoupon coupon = tdCouponService.findOne(id);
							if (null != coupon) {
								Long goodsId = coupon.getGoodsId();
								if (null != goodsId) {
									selected_map.put(goodsId, selected_map.get(goodsId) - 1);
								}
							}
						}
					}
				}
			}

			// 组合促销的方法
			if (null == isEnoughMoney || !isEnoughMoney) {

				order = this.comboEnoughNumber(username, activity, selected_map, isJoin, realBuy, sortList, totalNumber,
						isFloat, floatCount, presentedList, order, subPrice, isCombo, true);
			} else {
				// 阶梯促销的方法
				order = this.comboEnoughPrice(username, activity, selected_map, isJoin, sortList, totalPrice, subPrice,
						presentedList, order, isCombo, true);

			}

		}
		order.setPresentedList(presentedList);
		return order;

	}

	// 促销方案满数量赠送的方法
	public TdOrder comboEnoughNumber(String username, TdActivity activity, Map<Long, Long> selected_map, Boolean isJoin,
			Long realBuy, List<Long> sortList, Long totalNumber, Boolean isFloat, Long floatCount,
			List<TdOrderGoods> presentedList, TdOrder order, Double subPrice, Boolean isCombo, Boolean isSave) {
		// 获取该活动所需要的商品及其数量的列表
		Map<Long, Long> cost = new HashMap<>();
		String goodsAndNumber = activity.getGoodsNumber();
		if (null != goodsAndNumber) {
			// 拆分列表，使其成为【商品id_数量】的个体
			String[] item = goodsAndNumber.split(",");
			if (null != item) {
				for (String each_item : item) {
					if (null != each_item) {
						// 拆分个体以获取id和数量的属性
						String[] param = each_item.split("_");
						// 当个体不为空且长度为2的时候才是正确的数据
						if (null != param && param.length == 2) {
							Long id = Long.parseLong(param[0]);
							Long quantity = Long.parseLong(param[1]);
							cost.put(id, quantity);
							Long buyQuantity = selected_map.get(id);
							if (null == buyQuantity) {
								buyQuantity = 0L;
							}
							if (buyQuantity < quantity) {
								isJoin = false;
							}
							realBuy += buyQuantity;
							sortList.add(id);
						}
					}
				}

				// 如果实际购买量小于最低购买量，则也不能参加活动
				if (realBuy < totalNumber) {
					isJoin = false;
				}

				if (isJoin) {

					// -------------------------------2016-05-20
					// 09:45:15------------------------------------------
					// 判断活动是否具有浮动商品
					Long LimitNumber = 0L;
					for (Long quantity : cost.values()) {
						if (null != quantity) {
							LimitNumber += quantity;
						}
					}
					if (LimitNumber < totalNumber) {
						isFloat = true;
						floatCount = totalNumber - LimitNumber;
					}
					// --------------------------------------------------------------------------------------------

					// 判断参与促销的倍数（表示同一个活动可以参加几次）
					List<Long> mutipuls = new ArrayList<>();

					Long min = 1L;

					Boolean allZero = true;
					Long allNumber = 0L;

					// 满数量组合促销的方法
					if (null == isCombo || isCombo) {
						// 获取倍数关系
						for (Long goodsId : cost.keySet()) {
							Long quantity = cost.get(goodsId);
							if (!quantity.equals(0L)) {
								allZero = false;
							}
							Long goods_quantity = null == selected_map.get(goodsId) ? 0L : selected_map.get(goodsId);
							allNumber += goods_quantity;
							if (null == quantity || 0L == quantity.longValue()) {
								mutipuls.add(1L);
							} else {
								Long mutiplu = goods_quantity / quantity;
								mutipuls.add(mutiplu);
							}
						}

						if (isFloat) {
							Long totalNumberMutiplu = 1L;
							if (0L != totalNumber.longValue()) {
								totalNumberMutiplu = realBuy / totalNumber;
							}
							mutipuls.add(totalNumberMutiplu);
						}

						// 集合中最小的数字即为倍数
						min = Collections.min(mutipuls);

						if (allZero) {
							min = allNumber / totalNumber;
						}
					}

					// 改变剩下的商品的数量
					for (Long goodsId : cost.keySet()) {
						Long quantity = cost.get(goodsId);
						if (null == quantity) {
							quantity = 0L;
						}
						if (null == min) {
							min = 0L;
						}
						if (null != selected_map.get(goodsId)) {
							Long leftNum = selected_map.get(goodsId) - (quantity * min);
							selected_map.put(goodsId, leftNum);
						}
					}

					if (isFloat) {
						floatCount = floatCount * min;
						for (Long id : sortList) {
							// 获取指定商品剩余的数量
							Long leftNumber = selected_map.get(id);
							if (null == leftNumber) {
								leftNumber = 0L;
							}
							if (leftNumber < floatCount) {
								selected_map.put(id, 0L);
								floatCount -= leftNumber;
							} else {
								selected_map.put(id, leftNumber - floatCount);
								floatCount = 0L;
							}

							if (0L == floatCount.longValue()) {
								break;
							}
						}
					}

					// 获取赠品队列
					String giftNumber = activity.getGiftNumber();
					if (null != giftNumber) {
						String[] group = giftNumber.split(",");
						if (null != group) {
							for (String each_item : group) {
								if (null != each_item) {
									// 拆分个体以获取id和数量的属性
									String[] param = each_item.split("_");
									// 当个体不为空且长度为2的时候才是正确的数据
									if (null != param && param.length == 2) {
										Long id = Long.parseLong(param[0]);
										Long quantity = Long.parseLong(param[1]);
										// 查找到指定id的商品
										TdGoods goods = tdGoodsService.findOne(id);
										// 查找指定商品的价格
										TdOrderGoods orderGoods = new TdOrderGoods();
										orderGoods.setBrandId(goods.getBrandId());
										orderGoods.setBrandTitle(goods.getBrandTitle());
										orderGoods.setGoodsCoverImageUri(goods.getCoverImageUri());
										orderGoods.setGoodsId(goods.getId());
										orderGoods.setGoodsTitle(goods.getTitle());
										orderGoods.setGoodsSubTitle(goods.getSubTitle());
										orderGoods.setPrice(0.0);
										orderGoods.setGiftPrice(tdCommonService.getPrice(goods.getId(), username));
										orderGoods.setQuantity(quantity * min);
										orderGoods.setSku(goods.getCode());
										// 记录活动id
										orderGoods.setActivityId(activity.getId().toString() + "_" + quantity * min);
										// 修改订单商品归属活动
										tdOrderGoodsService.updateOrderGoodsActivity(order, cost, activity.getId(), min,
												1L);
										// 创建一个布尔变量用于表示赠品是否已经在队列中
										Boolean isHave = false;
										for (TdOrderGoods single : presentedList) {
											if (null != single && null != single.getGoodsId()
													&& single.getGoodsId() == orderGoods.getGoodsId()) {
												isHave = true;
												single.setQuantity(single.getQuantity() + orderGoods.getQuantity());
												// 记录活动id
												single.setActivityId(single.getActivityId() + ","
														+ activity.getId().toString() + "_" + min);
											}
										}

										if (!isHave && isSave) {
											presentedList.add(orderGoods);
										}
										if (isSave) {
											tdOrderGoodsService.save(orderGoods);
										}
									}
								}
							}
						}
					}
					Double activitySubPrice = order.getActivitySubPrice();
					if (null == activitySubPrice) {
						activitySubPrice = 0.00;
					}
					order.setActivitySubPrice(activitySubPrice + (subPrice * min));
				}
			}
		}
		return order;
	}

	public TdOrder comboEnoughPrice(String username, TdActivity activity, Map<Long, Long> selected_map, Boolean isJoin,
			List<Long> sortList, Double totalPrice, Double subPrice, List<TdOrderGoods> presentedList, TdOrder order,
			Boolean isCombo, Boolean isSave) {

		// 创建一个变量用于表示实际参与促销的商品的总价值
		Double totalCost = 0.00;

		// 生成一个价格集合
		List<Double> priceList = new ArrayList<>();

		// 获取该活动所需要的商品及其数量的列表
		Map<Long, Long> cost = new HashMap<>();
		String goodsAndNumber = activity.getGoodsNumber();
		if (null != goodsAndNumber) {
			// 拆分列表，使其成为【商品id_数量】的个体
			String[] item = goodsAndNumber.split(",");
			if (null != item) {
				for (String each_item : item) {
					if (null != each_item) {
						// 拆分个体以获取id和数量的属性
						String[] param = each_item.split("_");
						// 当个体不为空且长度为2的时候才是正确的数据
						if (null != param && param.length == 2) {
							Long id = Long.parseLong(param[0]);
							Long buyQuantity = selected_map.get(id);

							if (buyQuantity == null) {
								buyQuantity = 0l;
							}
							// 记录参加活动的id和数量 接口用
							cost.put(id, buyQuantity);
							// 获取指定的商品
							TdGoods goods = tdGoodsService.findOne(id);

							// 获取该件商品的价格
							Double price = tdCommonService.getPrice(goods.getId(), username);

							totalCost += (price * buyQuantity);

							sortList.add(id);
							priceList.add(price);
						}
					}
				}

				// 判断是否可以参与促销
				if (totalCost >= totalPrice) {
					isJoin = true;
				}

				if (isJoin) {
					Long min = 1L;

					if (null == isCombo || isCombo) {
						// 开始计算倍数关系
						if (0 != totalPrice) {
							min = (long) (totalCost / totalPrice);
						}
					}

					// 获取实际消耗金额
					Double realNeed = totalPrice * min;

					// 开始计算消耗
					for (int i = 0; i < sortList.size(); i++) {
						if (realNeed > 0) {
							// 获取商品的id
							Long goodsId = sortList.get(i);
							// 获取商品的单价
							Double price = priceList.get(i);

							// 计算消耗的数量
							Long needNumber = 0L;
							if (null != price && 0.00 != price.doubleValue()) {
								needNumber = (long) (realNeed / price);
							}
							// 获取该件商品的实际购买量
							Long realNumber = selected_map.get(goodsId);
							if (price * needNumber >= realNeed) {
								if (realNumber < needNumber) {
									realNeed -= (realNumber * price);
									selected_map.put(goodsId, 0L);
								} else {
									realNeed -= (needNumber * price);
									selected_map.put(goodsId, realNumber - needNumber);
								}
							} else {
								needNumber += 1L;
								if (realNumber < needNumber) {
									realNeed -= (realNumber * price);
									selected_map.put(goodsId, 0L);
								} else {
									realNeed -= (needNumber * price);
									selected_map.put(goodsId, realNumber - needNumber);
								}
							}
						}
					}
					// 不应该显示赠品为0的赠品 zp
					if (min > 0) {
						// 获取赠品队列
						String giftNumber = activity.getGiftNumber();
						if (null != giftNumber) {
							String[] group = giftNumber.split(",");
							if (null != group) {
								for (String each_item : group) {
									if (null != each_item) {
										// 拆分个体以获取id和数量的属性
										String[] param = each_item.split("_");
										// 当个体不为空且长度为2的时候才是正确的数据
										if (null != param && param.length == 2) {
											Long id = Long.parseLong(param[0]);
											Long quantity = Long.parseLong(param[1]);
											// 查找到指定id的商品
											TdGoods goods = tdGoodsService.findOne(id);
											// 查找指定商品的价格
											TdOrderGoods orderGoods = new TdOrderGoods();
											orderGoods.setBrandId(goods.getBrandId());
											orderGoods.setBrandTitle(goods.getBrandTitle());
											orderGoods.setGoodsCoverImageUri(goods.getCoverImageUri());
											orderGoods.setGoodsId(goods.getId());
											orderGoods.setGoodsTitle(goods.getTitle());
											orderGoods.setGoodsSubTitle(goods.getSubTitle());
											orderGoods.setPrice(0.0);
											orderGoods.setGiftPrice(tdCommonService.getPrice(goods.getId(), username));
											orderGoods.setQuantity(quantity * min);
											orderGoods.setSku(goods.getCode());
											// 记录活动id
											orderGoods.setActivityId(
													"M" + activity.getId().toString() + "_" + quantity * min);
											// 修改订单商品归属活动
											tdOrderGoodsService.updateOrderGoodsActivity(order, cost, activity.getId(),
													min, 3L);
											// 创建一个布尔变量用于表示赠品是否已经在队列中
											Boolean isHave = false;
											for (TdOrderGoods single : presentedList) {
												if (null != single && null != single.getGoodsId()
														&& single.getGoodsId() == orderGoods.getGoodsId()) {
													isHave = true;
													single.setQuantity(single.getQuantity() + orderGoods.getQuantity());
													// 记录活动id
													single.setActivityId(single.getActivityId() + ","
															+ activity.getId().toString() + "_" + min);
												}
											}

											if (!isHave && isSave) {
												presentedList.add(orderGoods);
											}
											if (isSave) {
												tdOrderGoodsService.save(orderGoods);
											}
										}
									}
								}
							}
						}
						Double activitySubPrice = order.getActivitySubPrice();
						if (null == activitySubPrice) {
							activitySubPrice = 0.00;
						}
						order.setActivitySubPrice(activitySubPrice + (subPrice * min));
					}
				}
			}
		}

		return order;
	}

	public TdOrder getGift(String username, TdOrder order) {
		// 获取已选【分类：数量】组
		Map<Long, Long> group = this.getGroup(username, order);
		List<TdOrderGoods> giftGoodsList = order.getGiftGoodsList();
		if (null == giftGoodsList) {
			giftGoodsList = new ArrayList<>();
		}

		Long giftType = 0L;
		if (null != order && null != order.getIsCoupon() && order.getIsCoupon()) {
			giftType = 1L;
		}

		// 获取已选能够参加的活动
		List<TdActivityGift> activities = this.getActivityGiftBySelected(username, order, giftType);
		for (TdActivityGift activity : activities) {
			Long categoryId = activity.getCategoryId();
			Long quantity = activity.getBuyNumber();
			// 判断是否满足条件
			if (null != group.get(categoryId) && group.get(categoryId) >= quantity) {

				// 添加小辅料赠品
				List<TdActivityGiftList> giftList = activity.getGiftList();
				if (null != giftList) {
					for (int i = 0; i < giftList.size(); i++) {
						TdActivityGiftList gift = giftList.get(i);
						TdGoods tdGoods = tdGoodsService.findOne(gift.getGoodsId());
						TdOrderGoods goods = new TdOrderGoods();
						goods.setBrandId(tdGoods.getBrandId());
						goods.setBrandTitle(tdGoods.getBrandTitle());
						goods.setPrice(0.00);
						goods.setQuantity(gift.getNumber());
						goods.setGoodsTitle(tdGoods.getTitle());
						goods.setGoodsSubTitle(tdGoods.getSubTitle());
						goods.setGoodsId(tdGoods.getId());
						goods.setGoodsCoverImageUri(tdGoods.getCoverImageUri());
						goods.setSku(tdGoods.getCode());
						// 记录活动id
						goods.setActivityId("A" + activity.getId().toString() + "_" + gift.getNumber());
						// 修改订单商品归属活动
						// 和活动商品同一
						Map<Long, Long> cost = new HashMap<Long, Long>();
						cost.put(categoryId, quantity);
						tdOrderGoodsService.updateOrderGoodsActivity(order, cost, activity.getId(), gift.getNumber(),
								2L);
						// 创建一个布尔变量用于判断此件商品是否已经加入了小辅料
						Boolean isHave = false;
						for (TdOrderGoods orderGoods : giftGoodsList) {
							if (null != orderGoods && null != orderGoods.getGoodsId()
									&& orderGoods.getGoodsId().longValue() == gift.getGoodsId().longValue()) {
								isHave = true;
								orderGoods.setQuantity(orderGoods.getQuantity() + goods.getQuantity());
								// 记录活动id
								orderGoods.setActivityId(orderGoods.getActivityId() + ",A" + activity.getId().toString()
										+ "_" + gift.getNumber());
							}
						}
						if (!isHave) {
							giftGoodsList.add(goods);
						}
						tdOrderGoodsService.save(goods);
						// 消减数量
						group.put(categoryId, group.get(categoryId) - quantity);
					}
				}
			}
		}
		order.setGiftGoodsList(giftGoodsList);
		return order;
	}

	public Map<Long, Long> getGroup(String username, TdOrder order) {
		Map<Long, Long> group = new HashMap<>();
		// 获取已选商品（整合后）
		for (TdOrderGoods cartGoods : order.getOrderGoodsList()) {
			// 获取已选的商品
			if (null != cartGoods) {
				TdGoods goods = tdGoodsService.findOne(cartGoods.getGoodsId());
				// 获取已选商品的分类id
				Long categoryId = goods.getCategoryId();
				// 获取指定的分类
				TdProductCategory category = tdProductCategoryService.findOne(categoryId);
				// 获取指定分类的父类
				if (null != category) {
					Long parentId = category.getParentId();
					if (null != parentId) {
						// 判断是否已经添加进入到map中
						if (null == group.get(parentId)) {
							group.put(parentId, cartGoods.getQuantity());
						} else {
							group.put(parentId, (group.get(parentId) + cartGoods.getQuantity()));
						}
					}
				}
			}
		}
		return group;
	}

	public List<TdActivityGift> getActivityGiftBySelected(String username, TdOrder order, Long giftType) {
		// 创建一个集合用于存储当前已选的所能参加的小辅料活动
		List<TdActivityGift> join = new ArrayList<>();
		// 获取当前用户
		TdUser user = tdUserService.findByUsernameAndIsEnableTrue(username);
		// 获取已选【类别：数量】组
		Map<Long, Long> category_quantity = this.getGroup(username, order);

		// 遍历map
		for (Long categoryId : category_quantity.keySet()) {

			// 根据分类id获取小辅料赠送活动
			List<TdActivityGift> activityGift_list = tdActivityGiftService
					.findByCategoryIdAndIsUseableTrueAndBeginTimeBeforeAndEndTimeAfterAndGiftTypeOrderBySortIdAsc(
							categoryId, new Date(), giftType);
			// 将参加的活动添加到join中
			if (null != activityGift_list) {
				for (TdActivityGift activityGift : activityGift_list) {
					if (null != activityGift && !join.contains(activityGift)) {
						// 判断是否在活动的门店内
						String[] diySiteIds = activityGift.getDiySiteIds().split(",");
						for (String diySiteId : diySiteIds) {
							if (diySiteId.equals(user.getUpperDiySiteId().toString())) {
								join.add(activityGift);
							}
						}
					}
				}
			}
		}

		// 进行内部排序
		join = this.compareTheGiftList(join);
		return join;
	}

	public List<TdActivityGift> compareTheGiftList(List<TdActivityGift> list) {
		// 自定义比较规则
		Comparator<TdActivityGift> compartor = new Comparator<TdActivityGift>() {
			public int compare(TdActivityGift a1, TdActivityGift a2) {
				if (a1.getSortId() - a2.getSortId() > 0) {
					return 1;
				} else if (a1.getSortId() - a2.getSortId() == 0) {
					return 0;
				} else {
					return -1;
				}
			}
		};
		Collections.sort(list, compartor);
		return list;
	}

	public void dismantleOrder(TdOrder order_temp, String username) {

		// 创建一个map用于存储拆单后的所有订单
		Map<Long, TdOrder> order_map = new HashMap<>();

		// 获取所有的品牌
		List<TdBrand> brand_list = tdBrandService.findAll();
		if (null != brand_list) {
			for (TdBrand brand : brand_list) {
				TdOrder order = new TdOrder();
				order.setOrderNumber(order_temp.getOrderNumber().replace("XN", brand.getShortName()));

				order.setRemark(order_temp.getRemark());
				order.setDiySiteId(order_temp.getDiySiteId());
				order.setDiySiteCode(order_temp.getDiySiteCode());
				order.setDiySiteName(order_temp.getDiySiteName());
				order.setDiySitePhone(order_temp.getDiySitePhone());
				order.setOrderGoodsList(new ArrayList<TdOrderGoods>());
				order.setTotalGoodsPrice(0.00);
				order.setTotalPrice(0.00);
				order.setLimitCash(0.00);
				order.setCashCoupon(0.00);
				order.setProductCoupon("");
				order.setCashCouponId("");
				order.setProductCouponId("");
				order.setStatusId(5L);
				order.setUserId(order_temp.getRealUserId());
				order.setUsername(order_temp.getRealUserUsername());
				order.setOrderTime(order_temp.getOrderTime());
				order.setPayTime(order_temp.getPayTime());
				order.setDeliveryTime(order_temp.getDeliveryTime());
				// 设置销顾信息
				order.setSellerId(order_temp.getSellerId());
				order.setSellerRealName(order_temp.getSellerRealName());
				order.setSellerUsername(order_temp.getSellerUsername());
				// 设置真实用户信息
				order.setIsSellerOrder(true);
				order.setHaveSeller(true);
				order.setRealUserId(order_temp.getRealUserId());
				order.setRealUserRealName(order_temp.getRealUserRealName());
				order.setRealUserUsername(order_temp.getRealUserUsername());
				// 设置主单号
				order.setMainOrderNumber(order_temp.getOrderNumber());
				// 设置实际应该支付的总额
				order.setAllTotalPay(order_temp.getTotalPrice());
				// 设置是否是电子券
				order.setIsCoupon(order_temp.getIsCoupon());

				order.setSendTime(order_temp.getSendTime());
				order.setReceiveTime(order_temp.getReceiveTime());
				// 设置其他收入
				order.setOtherIncome(order_temp.getOtherIncome());
				order_map.put(brand.getId(), order);

			}
		}

		List<TdOrderGoods> goodsList = order_temp.getOrderGoodsList();
		// 对已选商品进行拆单
		for (TdOrderGoods orderGoods : goodsList) {
			if (null != orderGoods) {
				Long brandId = orderGoods.getBrandId();
				TdOrder order = order_map.get(brandId);
				List<TdOrderGoods> orderGoodsList = order.getOrderGoodsList();
				if (null == orderGoodsList) {
					orderGoodsList = new ArrayList<>();
				}
				orderGoodsList.add(orderGoods);
				order.setOrderGoodsList(orderGoodsList);
				order.setTotalGoodsPrice(
						order.getTotalGoodsPrice() + (orderGoods.getPrice() * orderGoods.getQuantity()));
				order.setTotalPrice(order.getTotalPrice() + (orderGoods.getPrice() * orderGoods.getQuantity()));
			}
		}

		List<TdOrderGoods> presentedList = order_temp.getPresentedList();
		if (null == presentedList) {
			presentedList = new ArrayList<>();
		}
		// 对赠品进行拆单
		for (TdOrderGoods orderGoods : presentedList) {
			if (null != orderGoods) {
				Long brandId = orderGoods.getBrandId();
				TdOrder order = order_map.get(brandId);
				List<TdOrderGoods> orderGoodsList = order.getPresentedList();
				if (null == orderGoodsList) {
					orderGoodsList = new ArrayList<>();
				}
				orderGoodsList.add(orderGoods);
				order.setPresentedList(orderGoodsList);
				tdOrderService.save(order);
			}
		}

		List<TdOrderGoods> giftGoodsList = order_temp.getGiftGoodsList();
		if (null == giftGoodsList) {
			giftGoodsList = new ArrayList<>();
		}
		// 对赠送的小辅料进行拆单
		for (TdOrderGoods orderGoods : giftGoodsList) {
			if (null != orderGoods) {
				Long brandId = orderGoods.getBrandId();
				TdOrder order = order_map.get(brandId);
				List<TdOrderGoods> orderGoodsList = order.getGiftGoodsList();
				if (null == orderGoodsList) {
					orderGoodsList = new ArrayList<>();
				}
				orderGoodsList.add(orderGoods);
				order.setGiftGoodsList(orderGoodsList);
				tdOrderService.save(order);
			}
		}

		// 计算现金券使用额
		for (TdOrder order : order_map.values()) {
			if (null != order && null != order.getOrderGoodsList() && order.getOrderGoodsList().size() > 0) {
				List<TdOrderGoods> orderGoodsList = order.getOrderGoodsList();
				Double sub = 0.00;
				for (TdOrderGoods orderGoods : orderGoodsList) {
					Double couponMoney = orderGoods.getCouponMoney();
					if (null != couponMoney) {
						sub += couponMoney;
					}
				}
				order.setCashCoupon(sub);
				order.setTotalPrice(order.getTotalPrice() - sub);
			}
		}

		// 重新计算促销。目的是将满减信息正确的存储到分单上
		for (Long brandId : order_map.keySet()) {
			TdOrder order = order_map.get(brandId);
			if (null != order) {
				order = this.rePresent(username, order, false);
				order.setTotalPrice(order.getTotalPrice() - order.getActivitySubPrice());
				order_map.put(brandId, order);
			}
		}

		// 获取原单总价
		Double totalPrice = order_temp.getTotalPrice();

		if (null == totalPrice) {
			totalPrice = 0.00;
		}

		// 获取原订单使用门店POS
		Double unCashBalanceUsed = order_temp.getPosPay();

		// 获取原订单使用门店现金
		Double cashBalanceUsed = order_temp.getCashPay();

		// 获取原订单门店其他
		Double otherPay = order_temp.getBackOtherPay();

		if (null == unCashBalanceUsed) {
			unCashBalanceUsed = 0.00;
		}
		if (null == cashBalanceUsed) {
			cashBalanceUsed = 0.00;
		}
		if (null == otherPay) {
			otherPay = 0.00;
		}

		// totalPrice = totalPrice + cashBalanceUsed + unCashBalanceUsed;

		// 遍历当前生成的订单
		for (TdOrder order : order_map.values()) {
			if (null != order) {
				Double price = order.getTotalPrice();
				if (null == price || 0.00 == price) {
					order.setPosPay(0.00);
					order.setCashPay(0.00);
					order.setBackOtherPay(0.00);
				} else {
					// add MDJ
					Double point;
					if (totalPrice == 0) {
						point = 1.0;
					} else {
						point = price / totalPrice;
					}
					// 比例不能大于1
					if (point > 1) {
						point = 1.0;
					}

					if (null != point) {
						DecimalFormat df = new DecimalFormat("#.00");
						String scale2_uncash = df.format(unCashBalanceUsed * point);
						String scale2_cash = df.format(cashBalanceUsed * point);
						String scale2_other = df.format(otherPay * point);
						if (null == scale2_uncash) {
							scale2_uncash = "0.00";
						}
						if (null == scale2_cash) {
							scale2_cash = "0.00";
						}
						if (null == scale2_other) {
							scale2_other = "0.00";
						}
						order.setPosPay(Double.parseDouble(scale2_uncash));
						order.setCashPay(Double.parseDouble(scale2_cash));
						order.setBackOtherPay(Double.parseDouble(scale2_other));

					}
				}
			}
		}

		// 遍历存储
		for (TdOrder order : order_map.values()) {
			for (TdOrderGoods string : order.getOrderGoodsList()) {
				System.err.println(string);
			}
			if (null != order && ((order.getOrderGoodsList() != null && order.getOrderGoodsList().size() > 0)
					|| (order.getPresentedList() != null && order.getPresentedList().size() > 0)
					|| (order.getGiftGoodsList() != null && order.getGiftGoodsList().size() > 0))) {
				BigDecimal b = new BigDecimal(order.getTotalPrice());
				order.setTotalPrice(b.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue());
				if (null != order_temp.getIsCoupon() && order_temp.getIsCoupon()) {
					order.setStatusId(5L);
				}
				order = tdOrderService.save(order);

				TdUser user = tdUserService.findByUsername(username);

				Double cash = new Double(order.getCashPay() == null ? 0d : order.getCashPay());
				Double pos = new Double(order.getPosPay() == null ? 0d : order.getPosPay());
				Double other = new Double(order.getBackOtherPay() == null ? 0d : order.getBackOtherPay());

				if (cash < 0) {
					if (cash + pos >= 0) {
						pos += cash;
						cash = 0.00;
					} else {
						other += (cash + pos);
						cash = 0.00;
						pos = 0.00;
					}
				}

				// 抛单给EBS
				if (null != pos && pos > 0) {
					TdCashReciptInf inf = new TdCashReciptInf();
					inf.setSobId(user.getCityId());
					TdCashReciptInf cashReciptInf = new TdCashReciptInf();
					cashReciptInf.setSobId(user.getCityId());
					cashReciptInf.setReceiptNumber("RC" + this.getTimestamp());
					cashReciptInf.setUserid(order.getRealUserId());
					cashReciptInf.setUsername(order.getRealUserRealName());
					cashReciptInf.setUserphone(order.getRealUserUsername());
					cashReciptInf.setDiySiteCode(order.getDiySiteCode());
					cashReciptInf.setReceiptClass(StringTools.productClassStrByBoolean(order.getIsCoupon()));
					cashReciptInf.setOrderHeaderId(order.getId());
					cashReciptInf.setOrderNumber(order.getOrderNumber());
					cashReciptInf.setProductType(StringTools.getProductStrByOrderNumber(order.getOrderNumber()));
					cashReciptInf.setReceiptType("门店POS");
					cashReciptInf.setReceiptDate(new Date());
					cashReciptInf.setAmount(pos);
					tdCashReciptInfService.save(cashReciptInf);
					String resultStr = tdInterfaceService.ebsWithObject(cashReciptInf, INFTYPE.CASHRECEIPTINF);
					if (StringUtils.isBlank(resultStr)) {
						cashReciptInf.setSendFlag(0);
					} else {
						cashReciptInf.setSendFlag(1);
						cashReciptInf.setErrorMsg(resultStr);
					}
					tdCashReciptInfService.save(cashReciptInf);
				}
				if (null != cash && cash > 0) {
					TdCashReciptInf inf = new TdCashReciptInf();
					inf.setSobId(user.getCityId());
					TdCashReciptInf cashReciptInf = new TdCashReciptInf();
					cashReciptInf.setSobId(user.getCityId());
					cashReciptInf.setReceiptNumber("RC" + this.getTimestamp());
					cashReciptInf.setUserid(order.getRealUserId());
					cashReciptInf.setUsername(order.getRealUserRealName());
					cashReciptInf.setUserphone(order.getRealUserUsername());
					cashReciptInf.setDiySiteCode(order.getDiySiteCode());
					cashReciptInf.setReceiptClass(StringTools.productClassStrByBoolean(order.getIsCoupon()));
					cashReciptInf.setOrderHeaderId(order.getId());
					cashReciptInf.setOrderNumber(order.getOrderNumber());
					cashReciptInf.setProductType(StringTools.getProductStrByOrderNumber(order.getOrderNumber()));
					cashReciptInf.setReceiptType("门店现金");
					cashReciptInf.setReceiptDate(new Date());
					cashReciptInf.setAmount(cash);
					tdCashReciptInfService.save(cashReciptInf);
					String resultStr = tdInterfaceService.ebsWithObject(cashReciptInf, INFTYPE.CASHRECEIPTINF);
					if (StringUtils.isBlank(resultStr)) {
						cashReciptInf.setSendFlag(0);
					} else {
						cashReciptInf.setSendFlag(1);
						cashReciptInf.setErrorMsg(resultStr);
					}
					tdCashReciptInfService.save(cashReciptInf);
				}
				if (null != other && other > 0) {
					TdCashReciptInf inf = new TdCashReciptInf();
					inf.setSobId(user.getCityId());
					TdCashReciptInf cashReciptInf = new TdCashReciptInf();
					cashReciptInf.setSobId(user.getCityId());
					cashReciptInf.setReceiptNumber("RC" + this.getTimestamp());
					cashReciptInf.setUserid(order.getRealUserId());
					cashReciptInf.setUsername(order.getRealUserRealName());
					cashReciptInf.setUserphone(order.getRealUserUsername());
					cashReciptInf.setDiySiteCode(order.getDiySiteCode());
					cashReciptInf.setReceiptClass(StringTools.productClassStrByBoolean(order.getIsCoupon()));
					cashReciptInf.setOrderHeaderId(order.getId());
					cashReciptInf.setOrderNumber(order.getOrderNumber());
					cashReciptInf.setProductType(StringTools.getProductStrByOrderNumber(order.getOrderNumber()));
					cashReciptInf.setReceiptType("门店其他");
					cashReciptInf.setReceiptDate(new Date());
					cashReciptInf.setAmount(other);
					tdCashReciptInfService.save(cashReciptInf);
					String resultStr = tdInterfaceService.ebsWithObject(cashReciptInf, INFTYPE.CASHRECEIPTINF);
					if (StringUtils.isBlank(resultStr)) {
						cashReciptInf.setSendFlag(0);
					} else {
						cashReciptInf.setSendFlag(1);
						cashReciptInf.setErrorMsg(resultStr);
					}
					tdCashReciptInfService.save(cashReciptInf);
				}
			}
		}

		Boolean isSend = true;

		if (null != order_temp.getIsCoupon() && order_temp.getIsCoupon()) {
			isSend = false;
		}

		if (!isSend) {
			tdCommonService.getCoupon(order_temp, "门店");
		}

		// 删除虚拟订单
		order_temp.setGiftGoodsList(null);
		order_temp.setPresentedList(null);
		order_temp.setOrderGoodsList(null);

		tdOrderService.delete(order_temp);
	}

	public TdOrder rePresent(String username, TdOrder order, Boolean isSave) {
		// 获取用户的已选
		List<TdOrderGoods> all_selected = order.getOrderGoodsList();

		// 获取赠品列表
		List<TdOrderGoods> presentedList = order.getPresentedList();

		order.setActivitySubPrice(0.00);
		if (presentedList == null)
			presentedList = new ArrayList<>();

		Long giftType = 0L;

		if (null != order.getIsCoupon() && order.getIsCoupon()) {
			giftType = 1L;
		}

		// 获取用户的门店
		TdUser user = tdUserService.findByUsername(username);
		TdDiySite diySite = tdDiySiteService.findOne(user.getUpperDiySiteId());
		// 获取用户门店所能参加的活动
		List<TdActivity> activity_list = tdActivityService
				.findByDiySiteIdsContainingAndBeginDateBeforeAndFinishDateAfterAndGiftTypeOrderBySortIdAsc(
						diySite.getId() + "", new Date(), giftType);
		// 为了避免脏数据刷新，创建一个map用于存储已选【id：数量】
		Map<Long, Long> selected_map = new HashMap<>();

		for (TdOrderGoods cartGoods : all_selected) {
			Long id = cartGoods.getGoodsId();
			Long quantity = cartGoods.getQuantity();

			selected_map.put(id, quantity);
		}

		// 根据使用的产品券要核减已选商品
		String sProductCouponIds = order.getProductCouponId();
		if (null != sProductCouponIds && !"".equalsIgnoreCase(sProductCouponIds)) {
			String[] sIds = sProductCouponIds.split(",");
			for (String sId : sIds) {
				if (null != sId && !"".equalsIgnoreCase(sId)) {
					Long id = Long.parseLong(sId);
					TdCoupon coupon = tdCouponService.findOne(id);
					Long goodsId = coupon.getGoodsId();
					if (null != selected_map.get(goodsId) && selected_map.get(goodsId) > 0) {
						selected_map.put(goodsId, selected_map.get(goodsId) - 1);
					}
				}
			}
		}

		for (TdActivity activity : activity_list) {
			// 创建一个布尔变量表示已选商品能否参加指定的活动
			Boolean isJoin = true;
			// -------------------------------2016-05-20
			// 09:45:15------------------------------------------
			// 创建一个布尔变量用于判断该活动是存在浮动商品
			Boolean isFloat = false;
			// 获取该活动的最低购买量
			Long totalNumber = activity.getTotalNumber();
			if (null == totalNumber) {
				totalNumber = 0L;
			}
			// 创建一个变量用于表示实际购买量
			Long realBuy = 0L;
			// 创建一个变量用于表示浮动量
			Long floatCount = 0L;

			// 创建一个变量用于表示最低购买金额
			Double totalPrice = activity.getTotalPrice();

			// 创建一个变量用于表示立减金额
			Double subPrice = activity.getSubPrice();
			if (subPrice == null) {
				subPrice = 0d;
			}

			// 创建一个存储顺序的集合
			List<Long> sortList = new ArrayList<>();
			// --------------------------------------------------------------------------------------------

			Boolean isCombo = activity.getIsCombo();
			Boolean isEnoughMoney = activity.getIsEnoughMoney();

			String buyCouponId = order.getBuyCouponId();
			if (null != buyCouponId && !"".equals(buyCouponId)) {
				// presentedList = new ArrayList<>();

				List<TdOrderGoods> orderGoodsList = order.getOrderGoodsList();
				if (null != orderGoodsList && orderGoodsList.size() > 0) {
					for (TdOrderGoods orderGoods : orderGoodsList) {
						Long id = orderGoods.getGoodsId();
						Long quantity = orderGoods.getQuantity();
						selected_map.put(id, quantity);
					}
				}

				String[] ids = buyCouponId.split(",");
				if (null != ids && ids.length > 0) {
					for (String sid : ids) {
						if (null != sid && !"".equals(sid)) {
							Long id = Long.parseLong(sid);
							TdCoupon coupon = tdCouponService.findOne(id);
							if (null != coupon) {
								Long goodsId = coupon.getGoodsId();
								if (null != goodsId) {
									selected_map.put(goodsId, selected_map.get(goodsId) - 1);
								}
							}
						}
					}
				}
			}

			// 组合促销的方法
			if (null == isEnoughMoney || !isEnoughMoney) {

				order = this.comboEnoughNumber(username, activity, selected_map, isJoin, realBuy, sortList, totalNumber,
						isFloat, floatCount, presentedList, order, subPrice, isCombo, false);
			} else {
				// 阶梯促销的方法
				order = this.comboEnoughPrice(username, activity, selected_map, isJoin, sortList, totalPrice, subPrice,
						presentedList, order, isCombo, false);

			}

		}
		// 修改2016-08-25：为了不出现重复满赠的情况，不保存新订单的信息
		order.setPresentedList(presentedList);

		if (!(null != isSave && !isSave)) {
			order = tdOrderService.save(order);
		}
		return order;
	}

	private String getTimestamp() {
		int i = (int) (Math.random() * 900) + 100;
		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
		String format = sdf.format(date);
		return format + i;
	}

	private void subActivityPriceShare(TdOrder order, Map<Long, Object[]> param) {
		Double activitySubPrice = order.getActivitySubPrice();
		Double totalPrice = order.getTotalPrice();
		Double realTotal = totalPrice + activitySubPrice;
		List<TdOrderGoods> orderGoodsList = order.getOrderGoodsList();
		for (TdOrderGoods orderGoods : orderGoodsList) {
			Long goodsId = orderGoods.getGoodsId();
			Object[] var = param.get(goodsId);
			Long number = (Long) var[0];
			Double goodsTotal = (Double) var[1];

			DecimalFormat df = new DecimalFormat("#.00");

			Double point = goodsTotal / realTotal;
			Double shareSub = activitySubPrice * point;
			Double shareUnit = Double.valueOf(df.format(shareSub / number));

			orderGoods.setShareUnit(shareUnit);
			tdOrderGoodsService.save(orderGoods);
			// orderGoods.setPrice(orderGoods.getPrice() - shareUnit);
		}
	}
}
