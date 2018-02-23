package com.ynyes.lyz.controller.management;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ynyes.lyz.entity.TdCategoryLimit;
import com.ynyes.lyz.entity.TdCity;
import com.ynyes.lyz.entity.TdCoupon;
import com.ynyes.lyz.entity.TdDiySite;
import com.ynyes.lyz.entity.TdGoods;
import com.ynyes.lyz.entity.TdMemberRating;
import com.ynyes.lyz.entity.TdOrder;
import com.ynyes.lyz.entity.TdOrderGoods;
import com.ynyes.lyz.entity.TdPhotoOrderInfo;
import com.ynyes.lyz.entity.TdPhotoOrderInfo.Status;
import com.ynyes.lyz.entity.TdPriceListItem;
import com.ynyes.lyz.entity.TdSetting;
import com.ynyes.lyz.entity.TdShippingAddress;
import com.ynyes.lyz.entity.TdSmsAccount;
import com.ynyes.lyz.entity.user.TdUser;
import com.ynyes.lyz.service.TdCategoryLimitService;
import com.ynyes.lyz.service.TdCityService;
import com.ynyes.lyz.service.TdCommonService;
import com.ynyes.lyz.service.TdCouponService;
import com.ynyes.lyz.service.TdDiySiteService;
import com.ynyes.lyz.service.TdGoodsService;
import com.ynyes.lyz.service.TdMemberRatingService;
import com.ynyes.lyz.service.TdOrderService;
import com.ynyes.lyz.service.TdPhotoOrderGoodsInfoService;
import com.ynyes.lyz.service.TdPhotoOrderInfoService;
import com.ynyes.lyz.service.TdPriceCountService;
import com.ynyes.lyz.service.TdSettingService;
import com.ynyes.lyz.service.TdShippingAddressService;
import com.ynyes.lyz.service.TdSmsAccountService;
import com.ynyes.lyz.service.TdUserService;
import com.ynyes.lyz.util.SiteMagConstant;

@Controller
@RequestMapping("/Verwalter/photo/order")
public class TdManagerPhotoOrderController {
	
	@Autowired
	private  TdPhotoOrderInfoService tdPhotoOrderInfoService;
	
	@Autowired
	private  TdPhotoOrderGoodsInfoService tdPhotoOrderGoodsInfoService;
	
	@Autowired
	private  TdShippingAddressService tdShippingAddressService;
	
	@Autowired
	private TdCommonService tdCommonService;
	
	@Autowired
	private TdGoodsService tdGoodsService;
	
	@Autowired
	private TdUserService tdUserService;
	
	@Autowired
	private TdDiySiteService tdDiySiteService;
	
	@Autowired
	private TdOrderService tdOrderService;
	
	@Autowired
	private TdPriceCountService tdPriceCouintService;
	
	@Autowired
	private TdCouponService tdCouponService;
	
	@Autowired
	private TdSettingService tdSettingService;
	
	@Autowired
	private TdCityService tdRegionService;
	
	@Autowired
	private TdSmsAccountService tdSmsAccountService;
	
	@Autowired
	private TdMemberRatingService tdMemberRatingService;
	
	@Autowired
	private TdCategoryLimitService tdCategoryLimitService;
	
	private final Logger LOG = LoggerFactory.getLogger(TdManagerPhotoOrderController.class);
	
	/**
	 * 查询出所有的拍照订单记录
	 * @param page
	 * @param size
	 * @param keywords
	 * @param listId
	 * @param listChkId
	 * @param __EVENTTARGET
	 * @param __EVENTARGUMENT
	 * @param __VIEWSTATE
	 * @param listSortId
	 * @param map
	 * @param req
	 * @param managerId 默认为 0
	 * @return
	 */
	@RequestMapping(value="/list")
	public String photoOrderList(Integer page, Integer size,  String keywords,String status,String cityInfo,
			Long[] listId,Integer[] listChkId,String __EVENTTARGET, String __EVENTARGUMENT, String __VIEWSTATE,
			Long[] listSortId, ModelMap map, HttpServletRequest req){
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
				//btnDelete(listId, listChkId);
				
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
		cityInfo = "成都市";
		Page<TdPhotoOrderInfo> photoOrderage = tdPhotoOrderInfoService.findAllAddCondition(page, size,keywords,status,cityInfo);
		
		
		// 参数注回
		map.addAttribute("page", page);
		map.addAttribute("size", size);
		map.addAttribute("keywords", keywords);
		map.addAttribute("__EVENTTARGET", __EVENTTARGET);
		map.addAttribute("__EVENTARGUMENT", __EVENTARGUMENT);
		map.addAttribute("__VIEWSTATE", __VIEWSTATE);
		map.addAttribute("photoOrderPage", photoOrderage);
		map.addAttribute("status", status);
		map.addAttribute("cityInfo",cityInfo);
		return "/site_mag/photo_order_list";
		
	} 
	@RequestMapping(value="/list/zz")
	public String photoOrderListZz(Integer page, Integer size,  String keywords,String status,String cityInfo,
			Long[] listId,Integer[] listChkId,String __EVENTTARGET, String __EVENTARGUMENT, String __VIEWSTATE,
			Long[] listSortId, ModelMap map, HttpServletRequest req){
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
				//btnDelete(listId, listChkId);
				
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
		cityInfo = "郑州市";
		Page<TdPhotoOrderInfo> photoOrderage = tdPhotoOrderInfoService.findAllAddCondition(page, size,keywords,status,cityInfo);
		
		
		// 参数注回
		map.addAttribute("page", page);
		map.addAttribute("size", size);
		map.addAttribute("keywords", keywords);
		map.addAttribute("__EVENTTARGET", __EVENTTARGET);
		map.addAttribute("__EVENTARGUMENT", __EVENTARGUMENT);
		map.addAttribute("__VIEWSTATE", __VIEWSTATE);
		map.addAttribute("photoOrderPage", photoOrderage);
		map.addAttribute("status", status);
		map.addAttribute("cityInfo",cityInfo);
		return "/site_mag/photo_order_list_zz";
		
	} 
	
	/**
	 * 跳转到下单页面
	 * @return
	 */
	@RequestMapping(value = "/toprocess/{handleType}")
	public String toProcess(HttpServletRequest req,ModelMap map,@RequestParam("id") Long id,@PathVariable Long handleType){
		String username = (String) req.getSession().getAttribute("manager");
		if (null == username) {
			return "redirect:/Verwalter/login";
		}
		TdPhotoOrderInfo photoOrderInfo = tdPhotoOrderInfoService.findOne(id);
		TdShippingAddress shippingAddress = tdShippingAddressService.findOne(photoOrderInfo.getShippingAddressId());
		map.addAttribute("photoOrder", photoOrderInfo);
		map.addAttribute("shippingAddress",shippingAddress);
		map.addAttribute("type", handleType);
		if(null == photoOrderInfo.getPhotoUri()){
			map.addAttribute("photoUrl", null);
		} else {
			String[] photoUrl = photoOrderInfo.getPhotoUri().split(",");
			map.addAttribute("photoUrl", photoUrl);
		}
		if(photoOrderInfo.getSellerid() != null){
		   TdUser seller = tdUserService.findOne(photoOrderInfo.getSellerid());
		   map.addAttribute("seller", seller);
		}
		
		if(handleType == 1){//为查看状态
			map.addAttribute("photoOrderGoods", tdPhotoOrderGoodsInfoService.findByPhotoOrderIdEquals(photoOrderInfo.getId()));
		}
		
		return "/site_mag/photo_order_by_manager";
	}
	
	/**
	 * 选择商品弹出框
	 * @param keywords
	 * @param page
	 * @param size
	 * @param map
	 * @param username
	 * @return
	 */
	@RequestMapping(value = "/dialog")
	public String getDialog(HttpServletRequest req,String __EVENTTARGET, String __EVENTARGUMENT, String __VIEWSTATE,String keywords, Integer page, Integer size, ModelMap map,String username ) {
		//管理员
		String manager = (String) req.getSession().getAttribute("manager");
		if (null == manager) {
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
				//btnDelete(listId, listChkId);
				
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
		Page<TdGoods> goods_page = null;
		if (null != keywords) {
			goods_page = tdGoodsService.findByCodeContainingOrTitleContainingAndIsOnSaleIsTrueOrderBySortIdAsc(keywords, page,
					size);
		} else {
			// goods_page = tdGoodsService.findAll(page, size);
		}

		//客户
		TdUser user = tdUserService.findByUsername(username);
		
		if(user != null){
			//所属门店
			TdDiySite diySite = tdDiySiteService.findOne(user.getUpperDiySiteId());
			
			List<TdGoods> goodsList = new ArrayList<TdGoods>();
			//查询会员等级
			TdMemberRating tdMemberRating = this.tdMemberRatingService.findById(user.getMemberRatingId());
			
			if (null != goods_page) {
//				List<TdGoods> tdGoodsList = goods_page.getContent();
				for (int i = 0; i < goods_page.getContent().size(); i++) {
					TdGoods goods = goods_page.getContent().get(i);
					TdPriceListItem priceListItem = null;
					//查询产品分类
					TdCategoryLimit tdCategoryLimit = this.tdCategoryLimitService.findBySobIdAndCategoryId(user.getCityId(), goods.getCategoryId());
					
					if(null != tdCategoryLimit && null != tdCategoryLimit.getParentId() 
							&& tdCategoryLimit.getParentId().equals(170L)
							&& user.getUserType().equals(0L)){
						if (null != tdMemberRating && tdMemberRating.getRatingNum() >= 2) {
							Long listHeaderId = tdMemberRating.getHrListHeaderId();
							if (goods.getBrandId().equals(1L)) {
								listHeaderId = tdMemberRating.getLyzListHeaderId();
							} else if (goods.getBrandId().equals(3L)) {
								listHeaderId = tdMemberRating.getYrListHeaderId();
							}
							priceListItem = tdCommonService.getGoodsZGPrice(goods, listHeaderId);
						}
					} else {
						// 根据门店、商品、价格类型查询商品价格信息
						priceListItem = tdCommonService.secondGetGoodsPrice(diySite, goods, "ZY");
					}
					
					if(priceListItem != null){
						goods_page.getContent().get(i).setSalePrice(priceListItem.getSalePrice());
//						goodsList.add(goods);
					} else {
						goods_page.getContent().remove(i);
						i--;
					}
				}
			}

			map.addAttribute("page", page);
			map.addAttribute("size", size);
			map.addAttribute("keywords", keywords);
			map.addAttribute("__EVENTTARGET", __EVENTTARGET);
			map.addAttribute("__EVENTARGUMENT", __EVENTARGUMENT);
			map.addAttribute("__VIEWSTATE", __VIEWSTATE);
			map.addAttribute("goods_page", goods_page);
		}
		return "/site_mag/buy_goods_dialog";
	}
	
//	@RequestMapping(value = "/save/goods")
//	public void photoOrderSaveGoods(HttpServletRequest req,Long photoOrderId,Long[] goodsIds){
//		
//		
//	}
	
	/**
	 * 创建订单
	 * 
	 * @author panjie
	 */
	@RequestMapping(value = "/create/order")
	@ResponseBody
	public Map<String, Object> createOrder(HttpServletRequest req,String username,Long photoOrderId,
			 String ids,String numbers,String remark) {
		Map<String, Object> res = new HashMap<>();
		req.getSession().setAttribute("username", username);
		String mangername = (String) req.getSession().getAttribute("manager");
		
		// 客户
		TdUser user = tdUserService.findByUsername(username);
		Long realUserId = user.getId();
		
		if (null == mangername) {
			res.put("status", -2);
			res.put("message", "请重新登录");
			return res;
		}

		TdOrder orderTemp = null;

		// 通过方法生成一个虚拟订单
		try {
			orderTemp = tdCommonService.createVirtual(req, realUserId, ids,numbers,photoOrderId,remark);
		} catch (Exception e) {
			e.printStackTrace();
			LOG.error(e.getMessage());
			res.put("status", -1);
			res.put("message", "亲,系统出错拉！");
			return res;
		}
		

		if (null == orderTemp.getUpstairsType()) {
			orderTemp.setUpstairsType("不上楼");
		}
		if (null == orderTemp.getFloor()) {
			orderTemp.setFloor(1L);
		}
		if (null == orderTemp.getUpstairsFee()) {
			orderTemp.setUpstairsFee(0d);
		}
		if (null == orderTemp.getUpstairsBalancePayed()) {
			orderTemp.setUpstairsBalancePayed(0d);
		}
		if (null == orderTemp.getUpstairsOtherPayed()) {
			orderTemp.setUpstairsOtherPayed(0d);
		}

		// 判断此单是否拥有商品
		List<TdOrderGoods> goodsList = orderTemp.getOrderGoodsList();
		if (null == goodsList || !(goodsList.size() > 0)) {
			tdOrderService.delete(orderTemp);
			res.put("status", -1);
			res.put("message", "订单无商品");
			return res;
		}
		// 判断商品数量是否为0
		for (TdOrderGoods tdOrderGoods : goodsList) {
			if (tdOrderGoods.getQuantity() < 1L) {
				res.put("status", -1);
				res.put("message", "亲,商品数量不能小于1");
				return res;
			}
		}

		// 修改订单商品价格
		if (changeOrderGoodsNewPrice(orderTemp, req, user)) {
			res.put("status", -1);
			res.put("message", "亲,你购买的商品已经下架");
			return res;
		}

		orderTemp = tdPriceCouintService.checkCouponIsUsed(orderTemp);

		

		// 获取真实用户
		TdUser realUser = tdUserService.findOne(orderTemp.getRealUserId());
		// 判断具有真实用户的情况，导购和门店默认为真实用户的归属导购和门店
		if (null != realUser) {
			orderTemp.setRealUserRealName(realUser.getRealName());
			orderTemp.setRealUserId(realUser.getId());
			orderTemp.setRealUserUsername(realUser.getUsername());
			// 获取真实用户的归属导购（在此不需要获取真实用户的门店，因为真实用户的门店和当前登录的销顾的门店肯定一致）
			// 2017-08-22: 在订单归属导购为空的时候，才设置默认导购为订单归属导购
			if (null == orderTemp.getSellerId()) {
				Long sellerId = realUser.getSellerId();
				if (null != sellerId && null != orderTemp.getIsSellerOrder() && !orderTemp.getIsSellerOrder()) {
					TdUser seller = tdUserService.findOne(sellerId);
					if (null != seller) {
						orderTemp.setHaveSeller(true);
						orderTemp.setSellerId(seller.getId());
						orderTemp.setSellerRealName(seller.getRealName());
						orderTemp.setSellerUsername(seller.getUsername());
					}
				}
			}
		}

		Map<String, Object> results = null;
		// 计算价格和最大优惠券使用金额
		if (null != realUser) {
			results = tdPriceCouintService.countPrice(orderTemp, realUser);
		} else {
			results = tdPriceCouintService.countPrice(orderTemp, user);
		}

		// 如果计算的结果不为NULL，就获取一系列的值
		if (null != results) {
			TdOrder order_count = (TdOrder) results.get("result");

			// 得到订单的金额
			if (null != order_count) {
				orderTemp = order_count;
			}
		}

		// 获取已选的所有品牌的id
		List<Long> brandIds = tdCommonService.getBrandId(user.getId(), orderTemp);

		// 创建一个集合存储用户所能够使用的现金券
		List<TdCoupon> no_product_coupon_list = new ArrayList<>();

		// 创建一个集合存储用户所能够使用的产品券
		List<TdCoupon> product_coupon_list = new ArrayList<>();

		if (!(null != orderTemp.getIsCoupon() && orderTemp.getIsCoupon())) {
			// 遍历所有的品牌，查找用户对于当前订单可以使用的现金券
			for (Long brandId : brandIds) {
				List<TdCoupon> coupon_list = null;
				if (null != realUser) {
					coupon_list = tdCouponService
							.findByUsernameAndIsUsedFalseAndTypeCategoryIdAndIsOutDateFalseAndBrandIdOrderByGetTimeDesc(
									realUser.getUsername(), 1L, brandId, realUser.getCityName());
				}
				if (null != coupon_list) {
					no_product_coupon_list.addAll(coupon_list);
				}
			}
		}
		// 遍历所有已选，查找用户对于当前订单可以使用的指定商品现金券和产品券
		List<TdOrderGoods> selected = orderTemp.getOrderGoodsList();
		if (null != selected) {
			for (TdOrderGoods goods : selected) {
				if (null != goods) {
					// 查找能使用的产品券
					List<TdCoupon> p_coupon_list = null;
					if (null != realUser) {
						p_coupon_list = tdCouponService
								.findByUsernameAndIsUsedFalseAndTypeCategoryId3LAndIsOutDateFalseAndGoodsIdOrderByGetTimeDesc(
										realUser.getUsername(), goods.getGoodsId(), orderTemp.getDiySiteName());
					}
					if (null != p_coupon_list) {
						product_coupon_list.addAll(p_coupon_list);
					}

					// 查找能使用的指定商品现金券
					List<TdCoupon> c_coupon_list = null;
					if (null != realUser) {
						c_coupon_list = tdCouponService
								.findByUsernameAndIsUsedFalseAndTypeCategoryId2LAndIsOutDateFalseAndGoodsIdOrderByGetTimeDesc(
										realUser.getUsername(), goods.getGoodsId(), realUser.getCityName());
					}
					if (null != c_coupon_list) {
						no_product_coupon_list.addAll(c_coupon_list);
					}
				}
			}
		}

		tdOrderService.save(orderTemp);
		
		// 清空已选
		if ( null != ids && !"".equals(ids)) {
			tdCommonService.clear(req, ids);
		}
		
		res.put("status", 0);
		res.put("message", "提交订单成功！");
		
		String phone = "";
		if(orderTemp.getIsSellerOrder()){
			// 导购代下单 短信发给导购
			phone = orderTemp.getSellerUsername();
		}else{
			// 订单提交成功 给客户发送短信通知
			phone = user.getUsername();
		}
		
		if (null != phone && !"".equalsIgnoreCase(phone)) {
				this.sendSmsCaptcha(req, phone, 
						"亲爱的用户，您的拍照订单已生成，订单号: "+orderTemp.getOrderNumber()+"，请尽快前往乐易装APP支付订单", 
							user.getCityName());
		}
		
		return res;

	}
	
	/**
	 * 修改订单商品价格
	 * 
	 * @param order
	 * @param req
	 * @author zp
	 */
	private Boolean changeOrderGoodsNewPrice(TdOrder order, HttpServletRequest req, TdUser user) {
		//查询会员等级
		TdMemberRating tdMemberRating = this.tdMemberRatingService.findById(user.getMemberRatingId());
		
		List<TdOrderGoods> goodsList = order.getOrderGoodsList();
		if (goodsList != null && goodsList.size() > 0) {
			for (TdOrderGoods tdOrderGoods : goodsList) {
				TdGoods goods = tdGoodsService.findOne(tdOrderGoods.getGoodsId());
				// TdPriceListItem price = tdCommonService.getGoodsPrice(req,
				// goods);
				TdDiySite diySite = tdCommonService.getDiySite(req);
				// String custType = "";
				// // 判断门店是经销还是直营
				// if (null != diySite) {
				// String custTypeName = diySite.getCustTypeName();
				// if ("经销商".equals(custTypeName)) {
				// custType = "JX";
				// }
				// if ("直营".equals(custTypeName)) {
				// custType = "ZY";
				// }
				// }
				
				TdPriceListItem price = null;
				//查询产品分类
				TdCategoryLimit tdCategoryLimit = this.tdCategoryLimitService.findBySobIdAndCategoryId(user.getCityId(), goods.getCategoryId());
				
				if(null != tdCategoryLimit && null != tdCategoryLimit.getParentId() 
						&& tdCategoryLimit.getParentId().equals(170L)
						&& user.getUserType().equals(0L)){
					if (null != tdMemberRating && tdMemberRating.getRatingNum() >= 2) {
						Long listHeaderId = tdMemberRating.getHrListHeaderId();
						if (goods.getBrandId().equals(1L)) {
							listHeaderId = tdMemberRating.getLyzListHeaderId();
						} else if (goods.getBrandId().equals(3L)) {
							listHeaderId = tdMemberRating.getYrListHeaderId();
						}
						price = tdCommonService.getGoodsZGPrice(goods, listHeaderId);
					}
				} else {
					// 根据门店、商品、价格类型查询商品价格信息
					price = tdCommonService.secondGetGoodsPrice(diySite, goods, "ZY");
				}

				if (price == null) {
					return true;
				}
				tdOrderGoods.setPrice(price.getSalePrice());
				tdOrderGoods.setRealPrice(price.getRealSalePrice());
			}

		}
		return false;
	}
	
	/**
	 * 短信接口
	 * @param req
	 * @param phone
	 * @param message
	 * @param cityInfo
	 */
	private void sendSmsCaptcha(HttpServletRequest req, String phone, String message, String cityInfo) {
		String content = null;
		try {
			content = URLEncoder.encode(message, "GB2312");
		} catch (Exception e) {
			e.printStackTrace();
		}

		TdCity region = tdRegionService.findByCityName(cityInfo);
		if (null != region) {
		
			TdSmsAccount account = tdSmsAccountService.findOne(region.getSmsAccountId());
			String url = "http://www.mob800.com/interface/Send.aspx?enCode=" + account.getEncode() + "&enPass="
					+ account.getEnpass() + "&userName=" + account.getUserName() + "&mob=" + phone + "&msg=" + content;
			try {
				URL u = new URL(url);
				URLConnection connection = u.openConnection();
				HttpURLConnection httpConn = (HttpURLConnection) connection;
				httpConn.setRequestProperty("Content-type", "text/html");
				httpConn.setRequestProperty("Accept-Charset", "utf-8");
				httpConn.setRequestProperty("contentType", "utf-8");
				InputStream inputStream = null;
				InputStreamReader inputStreamReader = null;
				BufferedReader reader = null;
				StringBuffer resultBuffer = new StringBuffer();
				String tempLine = null;
	
				try {
					inputStream = httpConn.getInputStream();
					inputStreamReader = new InputStreamReader(inputStream);
					reader = new BufferedReader(inputStreamReader);
	
					while ((tempLine = reader.readLine()) != null) {
						resultBuffer.append(tempLine);
					}
	
				} finally {
					if (reader != null) {
						reader.close();
					}
					if (inputStreamReader != null) {
						inputStreamReader.close();
					}
					if (inputStream != null) {
						inputStream.close();
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * 作废订单
	 */
	@RequestMapping(value = "/invalid/order")
	@ResponseBody
	public Map<String,Object> invalidOrder(Long id){
		Map<String,Object> res = new HashMap<>();
		
		if(id == null){
			res.put("status", -1);
			res.put("msg","订单不存在");
			return res;
		}
		
		TdPhotoOrderInfo photoOrderInfo = tdPhotoOrderInfoService.findOne(id);
		
		photoOrderInfo.setStatus(Status.INVALID);
		tdPhotoOrderInfoService.save(photoOrderInfo);
		
		res.put("status", 0);
		return res;
	}
}
