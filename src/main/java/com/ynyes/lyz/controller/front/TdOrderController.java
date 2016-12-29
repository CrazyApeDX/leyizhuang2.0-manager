package com.ynyes.lyz.controller.front;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.ynyes.lyz.entity.TdBrand;
import com.ynyes.lyz.entity.TdCity;
import com.ynyes.lyz.entity.TdCoupon;
import com.ynyes.lyz.entity.TdDistrict;
import com.ynyes.lyz.entity.TdDiySite;
import com.ynyes.lyz.entity.TdDiySiteInventory;
import com.ynyes.lyz.entity.TdGoods;
import com.ynyes.lyz.entity.TdOrder;
import com.ynyes.lyz.entity.TdOrderGoods;
import com.ynyes.lyz.entity.TdPayType;
import com.ynyes.lyz.entity.TdPriceListItem;
import com.ynyes.lyz.entity.TdShippingAddress;
import com.ynyes.lyz.entity.TdSubdistrict;
import com.ynyes.lyz.entity.user.TdUser;
import com.ynyes.lyz.service.TdBrandService;
import com.ynyes.lyz.service.TdCityService;
import com.ynyes.lyz.service.TdCommonService;
import com.ynyes.lyz.service.TdCouponService;
import com.ynyes.lyz.service.TdDistrictService;
import com.ynyes.lyz.service.TdDiySiteInventoryService;
import com.ynyes.lyz.service.TdDiySiteService;
import com.ynyes.lyz.service.TdGoodsService;
import com.ynyes.lyz.service.TdOrderGoodsService;
import com.ynyes.lyz.service.TdOrderService;
import com.ynyes.lyz.service.TdPayTypeService;
import com.ynyes.lyz.service.TdPriceCountService;
import com.ynyes.lyz.service.TdSettingService;
import com.ynyes.lyz.service.TdShippingAddressService;
import com.ynyes.lyz.service.TdSubdistrictService;
import com.ynyes.lyz.service.TdUpstairsSettingService;
import com.ynyes.lyz.service.TdUserService;
import com.ynyes.lyz.service.basic.settlement.ISettlementService;

@Controller
@RequestMapping(value = "/order")
public class TdOrderController {

	@Autowired
	private TdUserService tdUserService;

	@Autowired
	private TdShippingAddressService tdShippingAddressService;

	@Autowired
	private TdCommonService tdCommonService;

	@Autowired
	private TdPayTypeService tdPayTypeService;

	@Autowired
	private TdDiySiteService tdDiySiteService;

	@Autowired
	private TdCouponService tdCouponService;

	@Autowired
	private TdSubdistrictService tdSubdistrictService;

	@Autowired
	private TdCityService tdCityService;

	@Autowired
	private TdDistrictService tdDistrictService;

	@Autowired
	private TdOrderService tdOrderService;

	@Autowired
	private TdBrandService tdBrandService;

	@Autowired
	private TdOrderGoodsService tdOrderGoodsService;

	@Autowired
	private TdPriceCountService tdPriceCouintService;

	@Autowired
	private TdGoodsService tdGoodsService;

	@Autowired
	private TdSettingService tdSettingService;

	@Autowired
	private TdDiySiteInventoryService tdDiySiteInventoryService;

	@Autowired
	private ISettlementService settlementService;

	@Autowired
	private TdUpstairsSettingService tdUpstairsSettingService;

	/**
	 * 清空部分信息的控制器
	 * 
	 * @author dengxiao
	 */
	@RequestMapping(value = "/clear")
	public String clearInfomations(HttpServletRequest req) {
		req.getSession().setAttribute("order_temp", null);
		req.getSession().setAttribute("maxCash", null);
		req.getSession().setAttribute("maxCoupon", null);
		req.getSession().setAttribute("usedNow", null);
		return "redirect:/order";
	}

	/**
	 * 跳转到填写订单的页面
	 * 
	 * @author dengxiao
	 */
	@RequestMapping
	public String writeOrderInfo(HttpServletRequest req, ModelMap map, Long id, Long realUserId, Long count,
			RedirectAttributes attr) {
		String username = (String) req.getSession().getAttribute("username");

		// 参数由调用函数检查
		TdUser user = tdUserService.findByUsernameAndIsEnableTrue(username);

		if (null == user) {
			return "redirect:/login";
		}

		map.addAttribute("user", user);

		TdOrder order_temp = null;

		// 生成虚拟订单
		if (null != id) {
			order_temp = tdOrderService.findOne(id);
		} else {
			order_temp = (TdOrder) req.getSession().getAttribute("order_temp");
		}

		// 如果session中没有虚拟订单，则通过方法生成一个
		if (null == order_temp) {
			try {
				order_temp = tdCommonService.createVirtual(req, realUserId);
			} catch (Exception e) {
				e.printStackTrace();
			}
			// 开始赠送优惠券
			tdPriceCouintService.sendCoupon(order_temp);
		} else {
			order_temp = tdOrderService.findOne(order_temp.getId());
		}

		if (null == order_temp.getUpstairsType()) {
			order_temp.setUpstairsType("不上楼");
		}
		if (null == order_temp.getFloor()) {
			order_temp.setFloor(1L);
		}
		if (null == order_temp.getUpstairsFee()) {
			order_temp.setUpstairsFee(0d);
		}
		if (null == order_temp.getUpstairsBalancePayed()) {
			order_temp.setUpstairsBalancePayed(0d);
		}
		if (null == order_temp.getUpstairsOtherPayed()) {
			order_temp.setUpstairsOtherPayed(0d);
		}

		// 判断此单是否拥有商品
		List<TdOrderGoods> goodsList = order_temp.getOrderGoodsList();
		if (null == goodsList || !(goodsList.size() > 0)) {
			tdOrderService.delete(order_temp);
			return "redirect:/";
		}
		// 判断商品数量是否为0
		for (TdOrderGoods tdOrderGoods : goodsList) {
			if (tdOrderGoods.getQuantity() < 1L) {
				attr.addAttribute("msg", "亲,商品数量不能小于1");
				return "redirect:/prompt";
			}
		}

		// 修改订单商品价格
		if (changeOrderGoodsNewPrice(order_temp, req)) {
			attr.addAttribute("msg", "亲,你购买的商品已经下架");
			return "redirect:/prompt";
		}

		order_temp = tdPriceCouintService.checkCouponIsUsed(order_temp);

		if (null != realUserId) {
			order_temp.setIsSellerOrder(true);
		}

		// 获取真实用户
		TdUser realUser = tdUserService.findOne(order_temp.getRealUserId());
		// 判断具有真实用户的情况，导购和门店默认为真实用户的归属导购和门店
		if (null != realUser) {
			order_temp.setRealUserRealName(realUser.getRealName());
			order_temp.setRealUserId(realUser.getId());
			order_temp.setRealUserUsername(realUser.getUsername());
			// 获取真实用户的归属导购（在此不需要获取真实用户的门店，因为真实用户的门店和当前登录的销顾的门店肯定一致）
			Long sellerId = realUser.getSellerId();
			if (null != sellerId && null != order_temp.getIsSellerOrder() && !order_temp.getIsSellerOrder()) {
				TdUser seller = tdUserService.findOne(sellerId);
				if (null != seller) {
					order_temp.setHaveSeller(true);
					order_temp.setSellerId(seller.getId());
					order_temp.setSellerRealName(seller.getRealName());
					order_temp.setSellerUsername(seller.getUsername());
				}
			}
		}

		Map<String, Object> results = null;
		// 计算价格和最大优惠券使用金额
		if (null != realUser) {
			results = tdPriceCouintService.countPrice(order_temp, realUser);
		} else {
			results = tdPriceCouintService.countPrice(order_temp, user);
		}

		// 如果计算的结果不为NULL，就获取一系列的值
		if (null != results) {
			TdOrder order_count = (TdOrder) results.get("result");
			Double max = (Double) results.get("max");
			Boolean isCoupon = (Boolean) results.get("isCoupon");

			// 得到订单的金额
			if (null != order_count) {
				order_temp = order_count;
				map.addAttribute("order", order_temp);
			}

			// 获取该单能够使用的最大预存款
			if (null != max) {
				map.addAttribute("max", max);
			}

			if (null != isCoupon) {
				map.addAttribute("isCoupon", isCoupon);
			}

		}

		// 获取已选的所有品牌的id
		List<Long> brandIds = tdCommonService.getBrandId(user.getId(), order_temp);

		// 创建一个集合存储用户所能够使用的现金券
		List<TdCoupon> no_product_coupon_list = new ArrayList<>();

		// 创建一个集合存储用户所能够使用的产品券
		List<TdCoupon> product_coupon_list = new ArrayList<>();

		if (!(null != order_temp.getIsCoupon() && order_temp.getIsCoupon())) {
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
		List<TdOrderGoods> selected = order_temp.getOrderGoodsList();
		if (null != selected) {
			for (TdOrderGoods goods : selected) {
				if (null != goods) {
					// 查找能使用的产品券
					List<TdCoupon> p_coupon_list = null;
					if (null != realUser) {
						p_coupon_list = tdCouponService
								.findByUsernameAndIsUsedFalseAndTypeCategoryId3LAndIsOutDateFalseAndGoodsIdOrderByGetTimeDesc(
										realUser.getUsername(), goods.getGoodsId(), order_temp.getDiySiteName());
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

		String productCouponId = order_temp.getProductCouponId();
		String cashCouponId = order_temp.getCashCouponId();

		if (null != productCouponId && !"".equals(productCouponId)) {
			String[] strings = productCouponId.split(",");
			if (null != strings) {
				map.addAttribute("product_used", strings.length);
			}
		}

		if (null != cashCouponId && !"".equals(cashCouponId)) {
			String[] strings = cashCouponId.split(",");
			if (null != strings) {
				map.addAttribute("no_product_used", strings.length);
			}
		}

		// 将虚拟订单添加到session中
		req.getSession().setAttribute("order_temp", order_temp);
		tdOrderService.save(order_temp);

		// 清空已选
		if (null == id) {
			tdCommonService.clear(req);
		}

		// 再次计算促销和价格
		if (null != count) {
			tdCommonService.rePresent(req, order_temp, true);
			tdPriceCouintService.countPrice(order_temp, realUser);
		}

		List<TdOrderGoods> orderGoodsList = order_temp.getOrderGoodsList();
		List<TdOrderGoods> orderGiftList = order_temp.getGiftGoodsList();
		List<TdOrderGoods> orderPresentList = order_temp.getPresentedList();

		// 计算商品总数
		Integer totalGoods = 0;
		if (orderGoodsList != null && orderGoodsList.size() > 0) {
			totalGoods += orderGoodsList.size();
		}
		if (orderGiftList != null && orderGiftList.size() > 0) {
			totalGoods += orderGiftList.size();
		}
		if (orderPresentList != null && orderPresentList.size() > 0) {
			totalGoods += orderPresentList.size();
		}

		// 计算上楼费
		Double countUpstairsFee = tdUpstairsSettingService.countUpstairsFee(order_temp);
		order_temp.setUpstairsFee(countUpstairsFee);

		map.addAttribute("totalGoods", totalGoods);
		map.addAttribute("order", order_temp);
		map.addAttribute("no_product_coupon_list", no_product_coupon_list);
		map.addAttribute("product_coupon_list", product_coupon_list);
		map.addAttribute("flag", "Y");
		return "/client/order_pay";

	}

	/**
	 * 跳转到查看已选商品的方法
	 * 
	 * @author dengxiao
	 */
	@RequestMapping(value = "/selected")
	public String orderSelected(HttpServletRequest req, ModelMap map) {
		String username = (String) req.getSession().getAttribute("username");
		TdUser user = tdUserService.findByUsernameAndIsEnableTrue(username);
		if (null == user) {
			return "redirect:/login";
		}
		if (null != req.getSession().getAttribute("order_temp")) {
			TdOrder order = (TdOrder) req.getSession().getAttribute("order_temp");
			order = tdOrderService.findOne(order.getId());
			if (null != order) {
				if (null == order.getPresentedList()) {
					order.setPresentedList(new ArrayList<TdOrderGoods>());
				}

				if (null == order.getGiftGoodsList()) {
					order.setGiftGoodsList(new ArrayList<TdOrderGoods>());
				}
			}

			map.addAttribute("order", order);
		}
		return "/client/order_list";
	}

	/**
	 * 对用户填写的留言信息进行存储的方法
	 * 
	 * @author dengxiao
	 */
	@RequestMapping(value = "/remark/save")
	@ResponseBody
	public Map<String, Object> remarkSave(HttpServletRequest req, String remark) {
		Map<String, Object> res = new HashMap<>();
		res.put("status", -1);
		TdOrder order = (TdOrder) req.getSession().getAttribute("order_temp");

		if (null != order) {
			if (remark.contains("&")) {
				res.put("message", "不能输入特殊符号&");
				return res;
			} else if (remark.contains("'")) {
				res.put("message", "不能输入特殊符号'");
				return res;
			} else {
				order.setRemark(remark);
				tdOrderService.save(order);
			}
		}
		req.getSession().setAttribute("order_temp", order);

		res.put("status", 0);
		return res;
	}

	/**
	 * 跳转到选择配送方式的页面的方法
	 * 
	 * @author dengxiao
	 * 
	 */
	@RequestMapping(value = "/delivery")
	public String selectDelivery(HttpServletRequest req, ModelMap map) {
		String username = (String) req.getSession().getAttribute("username");
		TdUser user = tdUserService.findByUsername(username);
		if (null == user) {
			return "redirect:/login";
		}

		// 获取用户的城市
		Long cityId = user.getCityId();
		TdCity city = tdCityService.findBySobIdCity(cityId);

		SimpleDateFormat hh = new SimpleDateFormat("HH");
		SimpleDateFormat mm = new SimpleDateFormat("mm");
		SimpleDateFormat yyyyMMdd = new SimpleDateFormat("yyyy-MM-dd");

		// 获取虚拟订单的信息
		TdOrder order = (TdOrder) req.getSession().getAttribute("order_temp");

		// 获取当前选择的门店
		Long diySiteId = order.getDiySiteId();
		// 获取当前选择的配送方式（0. 送货上门；1. 门店自提）
		String deliverTypeTitle = order.getDeliverTypeTitle();
		Long deliveryId = null;
		if ("送货上门".equals(deliverTypeTitle)) {
			deliveryId = 1L;
		}
		if ("门店自提".equals(deliverTypeTitle)) {
			deliveryId = 2L;
		}
		// 获取当前选择的配送时间
		String deliveryDate = order.getDeliveryDate();
		// 获取当前选择的配送时间段的id
		Long deliveryDetailId = order.getDeliveryDetailId();

		// 获取配送时间的限制（时间选择机制：如果是上午下单，最早的配送时间是当前下午，如果是下午下单，最早配送时间是第二天的上午）
		Date now = new Date();
		String h = hh.format(now);
		String m = mm.format(now);
		Long hour = Long.parseLong(h);
		Long minute = Long.parseLong(m);

		Date limitDate = now;

		Long delay = city.getDelayHour();
		if (null == delay) {
			delay = 0L;
		}

		Long tempHour = hour + delay;
		if (24 <= tempHour) {
			limitDate = new Date(now.getTime() + (1000 * 60 * 60 * 24));
			tempHour -= 24;
			limitDate = new Date(now.getTime() + (1000 * 60 * 60 * 24));
			tempHour = 9L;
		}

		// 判断能否当天配送
		if (tempHour > city.getFinishHour() || (tempHour == city.getFinishHour() && minute > city.getFinishMinute())) {
			limitDate = new Date(now.getTime() + (1000 * 60 * 60 * 24));
			tempHour = 9L;
		}

		// 获取限制时间
		map.addAttribute("limitHour", tempHour);

		map.addAttribute("limitDay", yyyyMMdd.format(limitDate));

		String earlyDate = yyyyMMdd.format(limitDate) + " " + tempHour + ":30-" + (tempHour + 1) + ":30";

		// 获取指定城市下所有的门店
		List<TdDiySite> diy_list = tdDiySiteService.findByRegionIdOrderBySortIdAsc(city.getSobIdCity());
		// 获取默认门店
		TdDiySite diySite = tdDiySiteService.findOne(diySiteId);

		// 判断能否更改门店或销顾（如果当前订单的haveSeller为true，则不能修改）
		if (null != order && null != order.getIsSellerOrder() && order.getIsSellerOrder()) {
			map.addAttribute("canChangeSeller", false);
		}

		map.addAttribute("seller", order.getSellerRealName());
		map.addAttribute("diySite", diySite);
		map.addAttribute("diy_list", diy_list);
		map.addAttribute("earlyDate", earlyDate);
		map.addAttribute("diySiteId", diySiteId);
		map.addAttribute("deliveryId", deliveryId);
		map.addAttribute("deliveryDate", deliveryDate);
		map.addAttribute("deliveryDetailId", deliveryDetailId);

		map.addAttribute("isCoupon", order.getIsCoupon());

		return "/client/order_delivery";
	}

	/**
	 * 获取销顾信息或者门店信息的方法
	 * 
	 * @author DengXiao
	 */
	@RequestMapping(value = "/get/info")
	public String orderGetInfo(HttpServletRequest req, ModelMap map, Long type, String diySiteName) {
		// 获取登陆用户的信息
		String username = (String) req.getSession().getAttribute("username");
		TdUser user = tdUserService.findByUsernameAndIsEnableTrue(username);
		if (null != user) {
			// 获取用户的所在城市
			Long cityId = user.getCityId();
			if (0L == type.longValue()) {
				// 查找该地区下所有的门店
				List<TdDiySite> info_list = tdDiySiteService.findByRegionIdOrderBySortIdAsc(cityId);
				map.addAttribute("info_list", info_list);
			} else if (1L == type.longValue()) {
				// 根据门店查询导购
				TdDiySite diySite = tdDiySiteService.findByTitleAndIsEnableTrue(cityId, diySiteName);
				List<TdUser> info_list = null;
				if (null != diySite) {// 查询门店下面的导购
					info_list = tdUserService.findByCityIdAndCustomerIdAndUserTypeOrCityIdAndCustomerIdAndUserType(
							diySite.getRegionId(), diySite.getCustomerId());

				} else {// 以前的逻辑查询城市下面的所有导购
					info_list = tdUserService.findByCityIdAndUserTypeOrCityIdAndUserTypeOrderBySortIdAsc(cityId);
				}
				map.addAttribute("info_list", info_list);
			}
		}
		map.addAttribute("operation_type", type);
		return "/client/order_delivery_list";
	}

	/**
	 * 根据关键词搜说销顾或者门店的方法
	 * 
	 * @author DengXiao
	 */
	@RequestMapping(value = "/search/info")
	public String orderSearchInfo(HttpServletRequest req, ModelMap map, String keywords, Long type,
			String diySiteName) {
		// 获取当前登陆用户的信息
		String username = (String) req.getSession().getAttribute("username");
		TdUser user = tdUserService.findByUsernameAndIsEnableTrue(username);
		if (null != user) {
			// 获取用户的所在城市
			Long cityId = user.getCityId();
			if (0L == type.longValue()) {
				List<TdDiySite> info_list = tdDiySiteService.findByRegionIdAndTitleContainingOrderBySortIdAsc(cityId,
						keywords);
				map.addAttribute("info_list", info_list);
			} else if (1L == type.longValue()) {
				// 根据门店查询导购
				TdDiySite diySite = tdDiySiteService.findByTitleAndIsEnableTrue(cityId, diySiteName);
				List<TdUser> info_list = null;
				if (diySite != null) {
					info_list = tdUserService
							.findByCityIdAndRealNameContainingAndUserTypeOrCityIdAndRealNameContainingAndUserType(
									cityId, keywords, diySite.getCustomerId());
				} else {
					info_list = tdUserService
							.findByCityIdAndRealNameContainingAndUserTypeOrCityIdAndRealNameContainingAndUserType(
									cityId, keywords, null);
				}

				map.addAttribute("info_list", info_list);
			}
		}
		map.addAttribute("operation_type", type);
		return "/client/seller_diy_info";
	}

	/**
	 * 选择销顾或者门店的方法
	 * 
	 * @author DengXiao
	 */
	@RequestMapping(value = "/select/info")
	@ResponseBody
	public Map<String, Object> orderSelectInfo(HttpServletRequest req, Long id, Long type) {
		Map<String, Object> res = new HashMap<>();
		res.put("status", -1);

		// 获取当前登陆用户的信息
		String username = (String) req.getSession().getAttribute("username");
		TdUser user = tdUserService.findByUsernameAndIsEnableTrue(username);
		if (null == user) {
			res.put("message", "信息获取失败，请稍后重试");
			return res;
		}

		// 获取虚拟订单的信息
		TdOrder order = (TdOrder) req.getSession().getAttribute("order_temp");

		if (null == id || null == type) {
			res.put("message", "信息获取失败，请稍后重试");
			return res;
		}

		if (0L == type.longValue()) {
			// 查找指定的门店
			TdDiySite diySite = tdDiySiteService.findOne(id);
			if (null == diySite) {
				res.put("message", "获取信息失败，请稍后重试");
				return res;
			}
			order.setDiySiteCode(diySite.getStoreCode());
			order.setDiySiteId(diySite.getId());
			order.setDiySiteName(diySite.getTitle());
			order.setDiySitePhone(diySite.getServiceTele());

			// 判断此店是不是用户的默认门店
			Long upperDiySiteId = user.getUpperDiySiteId();
			if (null != upperDiySiteId && upperDiySiteId == diySite.getId()) {
				// 销顾自动变更为用户的默认导购
				order.setSellerId(user.getSellerId());
				order.setSellerRealName(user.getSellerName());
				order.setSellerUsername(user.getReferPhone());
				res.put("sellerName", user.getSellerName());
			} else {
				// 默认为所有销顾中的第一个
				List<TdUser> seller_list = tdUserService
						.findByCityIdAndCustomerIdAndUserTypeOrCityIdAndCustomerIdAndUserType(diySite.getRegionId(),
								diySite.getCustomerId());
				if (null != seller_list && seller_list.size() > 0) {
					TdUser the_seller = seller_list.get(0);
					order.setSellerId(the_seller.getId());
					order.setSellerUsername(the_seller.getUsername());
					order.setSellerRealName(the_seller.getRealName());
					res.put("sellerName", the_seller.getRealName());
				} else {
					order.setSellerId(null);
					order.setSellerUsername(null);
					order.setSellerRealName(null);
					res.put("sellerName", "");
				}
			}

			order.setProductCoupon("");
			order.setProductCouponId("");

			tdOrderService.save(order);
			req.getSession().setAttribute("order_temp", order);

			res.put("diyTitle", diySite.getTitle());
			res.put("diyId", diySite.getId());
		} else if (1L == type.longValue()) {
			// 根据id查找到指定的销售顾问
			TdUser seller = tdUserService.findOne(id);
			// 判断指定销顾是否存在
			if (null == seller) {
				res.put("message", "获取信息失败，请稍后重试");
				return res;
			}
			// 如果销顾存在，那么就需要改变该笔订单的销顾信息和门店信息了
			order.setSellerId(seller.getId());
			order.setSellerUsername(seller.getUsername());
			order.setSellerRealName(seller.getRealName());

			// 获取指定销顾所属的门店
			TdDiySite diySite = tdDiySiteService.findByRegionIdAndCustomerId(seller.getCityId(),
					seller.getCustomerId());
			if (null == diySite) {
				res.put("message", "获取信息失败，请稍后重试");
				return res;
			}
			order.setDiySiteCode(diySite.getStoreCode());
			order.setDiySiteId(diySite.getId());
			order.setDiySiteName(diySite.getTitle());
			order.setDiySitePhone(diySite.getServiceTele());
			tdOrderService.save(order);
			req.getSession().setAttribute("order_temp", order);

			res.put("diyTitle", diySite.getTitle());
			res.put("sellerName", seller.getRealName());
			res.put("diyId", diySite.getId());
		}

		res.put("status", 0);
		return res;
	}

	/**
	 * 确定销顾的方法(新增的一个控制器2016-2-26)
	 * 
	 * @author DengXiao
	 */
	@RequestMapping(value = "/select/seller")
	@ResponseBody
	public Map<String, Object> selectSeller(HttpServletRequest req, Long userId) {
		Map<String, Object> res = new HashMap<>();
		res.put("status", -1);
		// 判断参数是否传递正确
		if (null == userId) {
			res.put("message", "获取信息失败，请稍后重试");
			return res;
		}

		// 根据id查找到指定的销售顾问
		TdUser user = tdUserService.findOne(userId);
		// 判断指定销顾是否存在
		if (null == user) {
			res.put("message", "获取信息失败，请稍后重试");
			return res;
		}

		// 获取虚拟订单的信息
		TdOrder order = (TdOrder) req.getSession().getAttribute("order_temp");

		// 如果销顾存在，那么就需要改变该笔订单的销顾信息和门店信息了
		order.setSellerId(user.getId());
		order.setSellerUsername(user.getUsername());
		order.setSellerRealName(user.getRealName());

		// 获取指定销顾所属的门店
		TdDiySite diySite = tdDiySiteService.findByRegionIdAndCustomerId(user.getCityId(), user.getCustomerId());
		if (null == diySite) {
			res.put("message", "获取信息失败，请稍后重试");
			return res;
		}

		order.setDiySiteCode(diySite.getStoreCode());
		order.setDiySiteId(diySite.getId());
		order.setDiySiteName(diySite.getTitle());
		order.setDiySitePhone(diySite.getServiceTele());
		tdOrderService.save(order);
		req.getSession().setAttribute("order_temp", order);

		res.put("diySiteId", diySite.getId());
		res.put("status", 0);
		return res;
	}

	/**
	 * 存储新的配送方式的信息的方法
	 * 
	 * @author dengxiao
	 */
	@RequestMapping(value = "/delivery/save")
	public String saveDelivery(HttpServletRequest req, Long type, String date, Long detailTime, Long diySite) {
		String username = (String) req.getSession().getAttribute("username");
		TdUser user = tdUserService.findByUsernameAndIsEnableTrue(username);
		if (null == user) {
			return "redirect:/login";
		}

		// 获取虚拟订单的信息
		TdOrder order = (TdOrder) req.getSession().getAttribute("order_temp");
		if (1L == type) {
			order.setDeliverTypeTitle("送货上门");
			// 判断当前支付方式是否是“门店自提”
			String title = order.getPayTypeTitle();
			if (null != title && "到店支付".equals(title)) {
				// 此时将支付方式更改为到店支付
				TdPayType payType = null;
				// 导购代下单
				TdUser realUser = null;
				if (user.getUserType() == 1L || user.getUserType() == 2L) {
					realUser = tdUserService.findOne(order.getRealUserId());
				}

				if (null != user.getIsCashOnDelivery() && !user.getIsCashOnDelivery() || ((realUser != null
						&& realUser.getIsCashOnDelivery() != null && !realUser.getIsCashOnDelivery()))) {
					payType = tdPayTypeService.findByTitleAndIsEnableTrue("支付宝");
				} else {
					payType = tdPayTypeService.findByTitleAndIsEnableTrue("货到付款");
				}
				if (null != payType) {
					order.setPayTypeId(payType.getId());
					order.setPayTypeTitle(payType.getTitle());
				}
			}
		}
		if (2L == type) {
			order.setDeliverTypeTitle("门店自提");
			// 判断当前支付方式是否是“货到付款”
			String title = order.getPayTypeTitle();
			if (null != title && "货到付款".equals(title)) {
				// 此时将支付方式更改为到店支付
				TdPayType payType = tdPayTypeService.findByTitleAndIsEnableTrue("到店支付");
				if (null != payType) {
					order.setPayTypeId(payType.getId());
					order.setPayTypeTitle(payType.getTitle());
				}
			}
			order.setUpstairsType("不上楼");
			order.setFloor(1L);
			order.setUpstairsFee(0d);
			order.setUpstairsBalancePayed(0d);
			order.setUpstairsOtherPayed(0d);
		}

		order.setDeliveryDate(date);
		order.setDeliveryDetailId(detailTime);

		// 获取指定的门店
		TdDiySite tdDiySite = tdDiySiteService.findOne(diySite);
		if (null == tdDiySite) {
			tdDiySite = new TdDiySite();
		}
		order.setDiySiteId(tdDiySite.getId());
		order.setDiySiteName(tdDiySite.getTitle());
		order.setDiySitePhone(tdDiySite.getServiceTele());
		order.setDiySiteCode(tdDiySite.getStoreCode());

		req.getSession().setAttribute("order_temp", order);
		tdOrderService.save(order);

		return "redirect:/order";
	}

	/**
	 * 跳转到上楼费的控制器
	 * 
	 * @author dengxiao
	 */
	@RequestMapping(value = "/upstairs", produces = "text/html;charset=utf-8", method = RequestMethod.GET)
	public String orderUpstairsGet(HttpServletRequest req, ModelMap map) {
		String username = (String) req.getSession().getAttribute("username");
		TdUser user = tdUserService.findByUsernameAndIsEnableTrue(username);
		if (null == user) {
			return "redirect:/login";
		}
		// 从session获取临时订单
		TdOrder order = (TdOrder) req.getSession().getAttribute("order_temp");
		map.addAttribute("order", order);
		return "/client/order_upstairs";
	}

	@RequestMapping(value = "/upstairs", produces = "text/html;charset=utf-8", method = RequestMethod.POST)
	public String orderUpstairsPost(HttpServletRequest req, String type, Long floor) {
		String username = (String) req.getSession().getAttribute("username");
		TdUser user = tdUserService.findByUsernameAndIsEnableTrue(username);
		if (null == user) {
			return "redirect:/login";
		}
		// 从session获取临时订单
		TdOrder order = (TdOrder) req.getSession().getAttribute("order_temp");
		order.setUpstairsType(type);
		order.setFloor(floor);
		Double upstairsFee = tdUpstairsSettingService.countUpstairsFee(order);
		order.setUpstairsFee(upstairsFee);
		tdOrderService.save(order);
		return "redirect:/order";
	}

	/**
	 * 跳转到选择支付方式的页面的方法
	 * 
	 * @author dengxiao
	 */
	@RequestMapping(value = "/paytype")
	public String selectPayType(HttpServletRequest req, ModelMap map) {
		String username = (String) req.getSession().getAttribute("username");
		TdUser user = tdUserService.findByUsernameAndIsEnableTrue(username);
		if (null == user) {
			return "redirect:/login";
		}

		// 从session获取临时订单
		TdOrder order = (TdOrder) req.getSession().getAttribute("order_temp");
		if (null == order) {
			order = new TdOrder();
		}
		// 导购代下单
		TdUser realUser = null;
		if (user.getUserType() == 1L || user.getUserType() == 2L) {
			realUser = tdUserService.findOne(order.getRealUserId());
		}
		map.addAttribute("payTypeId", order.getPayTypeId());

		// 获取所有的在线支付方式
		List<TdPayType> pay_type_list = tdPayTypeService.findByIsOnlinePayTrueAndIsEnableTrueOrderBySortIdAsc();

		// 获取配送方式
		String deliveryType = order.getDeliverTypeTitle();

		// 如果订单不是券订单，才能够使用线下支付的方式
		if (!(null != order.getIsCoupon() && order.getIsCoupon())) {
			if ("送货上门".equals(deliveryType)) {
				// 查询是否具有货到付款的支付方式
				if ((user.getIsCashOnDelivery() == null || user.getIsCashOnDelivery()) && (realUser == null
						|| realUser.getIsCashOnDelivery() == null || realUser.getIsCashOnDelivery())) {
					TdPayType payType = tdPayTypeService.findByTitleAndIsEnableTrue("货到付款");
					map.addAttribute("cashOndelivery", payType);
				}
			}

			if ("门店自提".equals(deliveryType)) {
				// 查找是否具有到店支付的支付方式
				TdPayType payType = tdPayTypeService.findByTitleAndIsEnableTrue("到店支付");
				pay_type_list.add(payType);
			}
		}
		map.addAttribute("pay_type_list", pay_type_list);
		return "/client/order_pay_type";
	}

	/**
	 * 跳转到选择优惠券的页面
	 * 
	 * @author dengxiao
	 */
	@RequestMapping(value = "/coupon/{type}")
	public String selectCoupon(HttpServletRequest req, ModelMap map, @PathVariable Long type) {
		// 根据type的值不同跳转到不同的页面：0. 现金券；1. 产品券
		String username = (String) req.getSession().getAttribute("username");
		TdUser user = tdUserService.findByUsernameAndIsEnableTrue(username);
		if (null == user) {
			return "redirect:/login";
		}

		// 获取虚拟订单
		TdOrder order = (TdOrder) req.getSession().getAttribute("order_temp");

		Boolean isCoupon = order.getIsCoupon();

		String realUsername = "";
		if (null != user.getUserType() && (user.getUserType().equals(1L) || user.getUserType().equals(2L))
				&& null != order) {
			realUsername = order.getRealUserUsername();
		} else {
			realUsername = username;
		}

		// 获取订单的真实用户
		Long realUserId = order.getRealUserId();
		TdUser realUser = tdUserService.findOne(realUserId);
		if (null == realUser) {
			realUser = new TdUser();
		}

		// 获取所有已选的商品
		List<TdOrderGoods> selected_list = order.getOrderGoodsList();

		// 使用一个集合存储所有的品牌
		List<TdBrand> brand_list = new ArrayList<>();

		Map<Long, List<TdCoupon>> cash_coupon_map = new HashMap<>();

		// 获取所有的品牌
		List<Long> brandIds = tdCommonService.getBrandId(realUser.getId(), order);
		if (null != brandIds && brandIds.size() > 0) {
			for (int i = 0; i < brandIds.size(); i++) {
				Long brandId = brandIds.get(i);
				// 根据品牌id查找通用优惠券
				if (null != brandId) {
					if (!(null != isCoupon && isCoupon)) {
						List<TdCoupon> cash_coupon_brand = tdCouponService
								.findByUsernameAndIsUsedFalseAndTypeCategoryIdAndIsOutDateFalseAndBrandIdOrderByGetTimeDesc(
										realUsername, 1L, brandId, user.getCityName());
						if (null != cash_coupon_brand && cash_coupon_brand.size() > 0) {
							cash_coupon_map.put(brandId, cash_coupon_brand);
						}
					} else {
						cash_coupon_map.put(brandId, new ArrayList<TdCoupon>());
					}
					TdBrand brand = tdBrandService.findOne(brandId);
					if (null != brand) {
						brand_list.add(brand);
					}
				}
			}
		}

		// 创建一个集合用于存储当前用户能够在此单使用的产品券
		List<TdCoupon> product_coupon_list = new ArrayList<>();
		// 遍历已选，一个一个的查找用户能够使用的产品券或指定产品现金券
		for (TdOrderGoods cartGoods : selected_list) {
			if (null != cartGoods) {
				List<TdCoupon> product_coupon_by_goodsId = tdCouponService
						.findByUsernameAndIsUsedFalseAndTypeCategoryId3LAndIsOutDateFalseAndGoodsIdOrderByGetTimeDesc(
								realUsername, cartGoods.getGoodsId(), order.getDiySiteName());
				if (null != product_coupon_by_goodsId && product_coupon_by_goodsId.size() > 0) {
					product_coupon_list.addAll(product_coupon_by_goodsId);
				}

				List<TdCoupon> no_product_coupon_by_goodsId = tdCouponService
						.findByUsernameAndIsUsedFalseAndTypeCategoryId2LAndIsOutDateFalseAndGoodsIdOrderByGetTimeDesc(
								realUsername, cartGoods.getGoodsId(), user.getCityName());
				if (null != no_product_coupon_by_goodsId && no_product_coupon_by_goodsId.size() > 0) {
					Long brandId = cartGoods.getBrandId();
					List<TdCoupon> list = cash_coupon_map.get(brandId);
					if (null == list) {
						list = new ArrayList<>();
					}
					list.addAll(no_product_coupon_by_goodsId);
					cash_coupon_map.put(brandId, list);
				}
			}
		}

		// 使用的现金券的id
		String cashCouponId = order.getCashCouponId();
		// 拆分现金券id
		String[] cash_ids = cashCouponId.split(",");
		List<Long> no_product_used = new ArrayList<>();
		if (null != cash_ids) {
			for (String id : cash_ids) {
				if (null != id && !"".equals(id.trim())) {
					no_product_used.add(Long.parseLong(id));
				}
			}
		}

		// 使用产品券的id
		String productCouponId = order.getProductCouponId();
		// 拆分产品券id
		String[] product_ids = productCouponId.split(",");
		List<Long> product_used = new ArrayList<>();
		if (null != product_ids) {
			for (String id : product_ids) {
				if (null != id && !"".equals(id.trim())) {
					product_used.add(Long.parseLong(id));
				}
			}
		}

		// 跳转到选择现金券的页面
		if (0L == type) {
			for (Long brandId : cash_coupon_map.keySet()) {
				if (null != brandId) {
					List<TdCoupon> list = cash_coupon_map.get(brandId);
					if (null != list) {
						map.addAttribute("coupons" + brandId, list);
					}
				}
			}
			map.addAttribute("brand_list", brand_list);
			map.addAttribute("no_product_used", no_product_used);
			return "/client/order_cash_coupon";
		} else {
			map.addAttribute("product_coupon_list", product_coupon_list);
			map.addAttribute("product_used", product_used);
			return "/client/order_product_coupon";
		}
	}

	/**
	 * 选择/取消选择优惠券的方法
	 * 
	 * @author dengxiao
	 */
	@RequestMapping(value = "/operate/coupon")
	@ResponseBody
	public Map<String, Object> operateCoupon(HttpServletRequest req, Long id, Long type, Long status) {
		Map<String, Object> res = new HashMap<>();
		res.put("status", -1);

		// 获取虚拟订单
		TdOrder order = (TdOrder) req.getSession().getAttribute("order_temp");

		String productCouponId = order.getProductCouponId();
		if (null == productCouponId) {
			productCouponId = "";
		}
		String cashCouponId = order.getCashCouponId();
		if (null == cashCouponId) {
			cashCouponId = "";
		}

		// 获取指定id的优惠券
		TdCoupon coupon = tdCouponService.findOne(id);
		if (null == coupon) {
			res.put("message", "未找到指定的优惠券");
			return res;
		}

		// 判断当前优惠券是否过期或是被使用
		if ((null != coupon.getIsOutDate() && coupon.getIsOutDate())
				|| (null != coupon.getIsUsed() && coupon.getIsUsed())) {
			res.put("message", "此优惠券已被使用或已过期");
			return res;
		}

		// 获取每个品牌的优惠券的最大使用额度和当前使用额度
		Map<Long, Double[]> permit = tdPriceCouintService.getPermit(order);
		if (null == permit) {
			res.put("message", "操作优惠券失败");
			return res;
		}

		Long brandId = coupon.getBrandId();
		TdBrand brand = tdBrandService.findOne(brandId);
		// 获取该品牌的优惠券【可使用限额，已使用限额】的队列
		Double[] permits = permit.get(brandId);
		if (null == permits) {
			permits = new Double[2];
			permits[0] = 0.00;
			permits[1] = 0.00;
		}
		if (null == permits[0]) {
			permits[0] = 0.00;
		}
		if (null == permits[1]) {
			permits[1] = 0.00;
		}

		if (0L == type) {
			if (0L == status) {
				if (null == brandId) {
					res.put("message", "未找到指定优惠券的信息");
					return res;
				}
				// 第一种情况，不能使用优惠券（限额为0）
				if (0.00 == permits[0].doubleValue()) {
					res.put("message", "本单不能使用" + brand.getTitle() + "公司<br>的优惠券");
					return res;
				}
				// 第二种情况，不能使用优惠券（使用额已经大于限额）
				if (permits[1] >= permits[0]) {
					res.put("message", "您所能使用的" + brand.getTitle() + "公司<br>的优惠券最大限额为" + permits[0] + "元");
					return res;
					// 第三种情况，使用的是指定产品现金券，并且使用数目 = 产品数量 - 产品券使用数量
				} else if (coupon.getTypeCategoryId().longValue() == 2L) {
					// 获取订单的已选商品
					List<TdOrderGoods> goodsList = order.getOrderGoodsList();
					for (TdOrderGoods orderGoods : goodsList) {
						if (null != orderGoods && null != orderGoods.getGoodsId()
								&& orderGoods.getGoodsId().longValue() == coupon.getGoodsId().longValue()) {
							// 获取产品券使用情况和优惠券使用情况
							Long couponNumber = orderGoods.getCouponNumber();
							Long cashNumber = orderGoods.getCashNumber();
							Long quantity = orderGoods.getQuantity();
							if (null == couponNumber) {
								couponNumber = 0L;
							}
							if (null == cashNumber) {
								cashNumber = 0L;
							}
							if (null == quantity) {
								quantity = 0L;
							}

							if (quantity - couponNumber - cashNumber <= 0L) {
								res.put("message", "您不能使用更多对于<br>该件产品的优惠券了");
								return res;
							}
						}
					}
				}

				// 创建一个布尔变量用于判断该张券是否在当前订单使用过，以应对网络条件不好的情况下，同一张券在一张订单中多次使用的情况
				Boolean isHave = false;

				if (null != cashCouponId && !"".equals(cashCouponId)) {
					String[] strings = cashCouponId.split(",");
					if (null != strings && strings.length > 0) {
						for (String sId : strings) {
							if (null != sId) {
								Long theId = Long.parseLong(sId);
								if (null != theId && theId.longValue() == coupon.getId().longValue()) {
									isHave = true;
								}
							}
						}
					}
				}

				// 指定的券在本单中没有使用时，才能够添加成功
				if (!isHave) {
					cashCouponId += coupon.getId() + ",";
					order.setCashCouponId(cashCouponId);
					req.getSession().setAttribute("order_temp", order);
					tdOrderService.save(order);
					if (coupon.getTypeCategoryId().longValue() == 2L) {
						// 获取订单的已选商品
						List<TdOrderGoods> goodsList = order.getOrderGoodsList();
						for (TdOrderGoods orderGoods : goodsList) {
							if (null != orderGoods && null != orderGoods.getGoodsId()
									&& orderGoods.getGoodsId().longValue() == coupon.getGoodsId().longValue()) {
								if (null == orderGoods.getCashNumber()) {
									orderGoods.setCashNumber(0L);
								}
								orderGoods.setCashNumber(orderGoods.getCashNumber() + 1L);
								tdOrderGoodsService.save(orderGoods);
							}
						}
					}

					// 计算当前现金券的实际使用价值
					if (permits[1] + coupon.getPrice() > permits[0]) {
						coupon.setRealPrice(permits[0] - permits[1]);
					} else {
						coupon.setRealPrice(coupon.getPrice());
					}
					tdCouponService.save(coupon);
				}
			}
			if (1L == status) {
				if (!"".equals(cashCouponId)) {
					// 拆分当前使用的现金券
					String[] strings = cashCouponId.split(",");
					// 创建一个新的变量用于存储删减后的现金券使用情况
					String ids = "";
					// 遍历现金券，当id与当前获取的优惠券的id不相同，添加到新的使用记录中
					if (null != strings) {
						for (String sCouponId : strings) {
							if (null != sCouponId) {
								Long couponId = Long.valueOf(sCouponId);
								if (null != couponId && couponId.longValue() != coupon.getId().longValue()) {
									ids += (sCouponId + ",");
								}
							}
							cashCouponId = ids;
						}
						order.setCashCouponId(cashCouponId);
						req.getSession().setAttribute("order_temp", order);
						tdOrderService.save(order);
					}

					if (coupon.getTypeCategoryId().longValue() == 2L) {
						// 获取订单的已选商品
						List<TdOrderGoods> goodsList = order.getOrderGoodsList();
						for (TdOrderGoods orderGoods : goodsList) {
							if (null != orderGoods && null != orderGoods.getGoodsId()
									&& orderGoods.getGoodsId().longValue() == coupon.getGoodsId().longValue()) {
								if (null == orderGoods.getCashNumber()) {
									orderGoods.setCashNumber(0L);
								}
								orderGoods.setCashNumber(orderGoods.getCashNumber() - 1L);
								tdOrderGoodsService.save(orderGoods);
							}
						}
					}
				}
			}
		}

		if (1L == type) {
			// 获取订单的已选商品
			List<TdOrderGoods> goodsList = order.getOrderGoodsList();
			if (null == goodsList) {
				res.put("message", "未检索到订单的商品信息");
				return res;
			}
			if (0L == status) {
				// 遍历订单商品，查找到与产品券对应的
				for (TdOrderGoods orderGoods : goodsList) {
					if (null != orderGoods && null != orderGoods.getGoodsId()
							&& orderGoods.getGoodsId().longValue() == coupon.getGoodsId().longValue()) {
						// 获取此件商品的产品券使用数量
						Long couponNumber = orderGoods.getCouponNumber();

						if (null == couponNumber) {
							couponNumber = 0L;
						}

						// 如果使用的产品券已经等于商品的数量，那么就不能够再使用了
						if (orderGoods.getQuantity() - orderGoods.getCashNumber()
								- orderGoods.getCouponNumber() <= 0L) {
							res.put("message", "您不能使用更多对于<br>该件产品的优惠券了");
							return res;
						} else {
							// 创建一个布尔变量用于判断该张券是否在当前订单使用过，以应对网络条件不好的情况下，同一张券在一张订单中多次使用的情况
							Boolean isHave = false;
							if (null != productCouponId && !"".equals(productCouponId)) {
								String[] strings = productCouponId.split(",");
								if (null != strings && strings.length > 0) {
									for (String sId : strings) {
										if (null != sId) {
											Long theId = Long.parseLong(sId);
											if (null != theId && theId.longValue() == coupon.getId().longValue()) {
												isHave = true;
											}
										}
									}
								}
							}

							if (!isHave) {
								orderGoods.setCouponNumber(couponNumber + 1L);
								tdOrderGoodsService.save(orderGoods);
								productCouponId += coupon.getId() + ",";
								order.setProductCouponId(productCouponId);
								coupon.setRealPrice(orderGoods.getPrice());
								req.getSession().setAttribute("order_temp", order);
								// 存储产品券的实际使用价值
								coupon.setRealPrice(orderGoods.getPrice());

								// 判断是否为购买的产品券，如果是，则记录
								if (null != coupon.getIsBuy() && coupon.getIsBuy()) {
									if (null == order.getBuyCouponId()) {
										order.setBuyCouponId("");
									}
									order.setBuyCouponId(order.getBuyCouponId() + coupon.getId() + ",");
								}
								tdCouponService.save(coupon);
							}
						}
					}
				}
			}
			if (1L == status) {
				// 遍历已选商品，查找到与指定产品券所对应的那一个
				for (TdOrderGoods orderGoods : goodsList) {
					if (null != orderGoods && null != orderGoods.getGoodsId()
							&& orderGoods.getGoodsId().longValue() == coupon.getGoodsId().longValue()) {
						// 获取该件商品使用产品券的数量
						Long couponNumber = orderGoods.getCouponNumber();
						if (null == couponNumber) {
							couponNumber = 0L;
						} else {
							orderGoods.setCouponNumber(orderGoods.getCouponNumber() - 1L);
						}
						tdOrderGoodsService.save(orderGoods);
						if (!"".equals(productCouponId)) {
							String[] strings = productCouponId.split(",");
							// 创建一个变量用于存储新的产品券使用情况
							String ids = "";
							String buyIds = "";
							for (String sCouponId : strings) {
								if (null != sCouponId) {
									Long couponId = Long.valueOf(sCouponId);
									if (null != couponId && couponId.longValue() != coupon.getId().longValue()) {
										ids += (sCouponId + ",");
										TdCoupon tempCoupon = tdCouponService.findOne(couponId);
										if (null != tempCoupon && null != tempCoupon.getIsBuy()
												&& tempCoupon.getIsBuy()) {
											buyIds += (sCouponId + ",");
										}
									}
									productCouponId = ids;
								}
							}
							order.setBuyCouponId(buyIds);
						}
						order.setProductCouponId(productCouponId);
						req.getSession().setAttribute("order_temp", order);
						tdOrderService.save(order);
					}
				}

			}
		}

		Map<String, Object> results = null;
		Long realUserId = order.getRealUserId();
		TdUser realUser = tdUserService.findOne(realUserId);

		// 计算价格和最大优惠券使用金额
		if (null != realUser) {
			results = tdPriceCouintService.countPrice(order, realUser);
		} else {
			TdUser user = tdUserService.findByUsername(order.getUsername());
			results = tdPriceCouintService.countPrice(order, user);
		}

		// 如果计算的结果不为NULL，就获取一系列的值
		if (null != results) {
			TdOrder order_count = (TdOrder) results.get("result");
			order = order_count;
		}

		// 将虚拟订单添加到session中
		req.getSession().setAttribute("order_temp", order);

		res.put("status", 0);
		return res;
	}

	/**
	 * 确认选择的支付方式的方法（最后跳转回到填写订单的页面）
	 * 
	 * @author dengxiao
	 */
	@RequestMapping(value = "/confirm/paytype")
	public String confirmPayType(HttpServletRequest req, Long id) {
		String username = (String) req.getSession().getAttribute("username");
		TdUser user = tdUserService.findByUsernameAndIsEnableTrue(username);
		if (null == user) {
			return "redirect:/login";
		}
		if (null != id) {
			TdPayType payType = tdPayTypeService.findOne(id);
			TdOrder order = (TdOrder) req.getSession().getAttribute("order_temp");
			order.setPayTypeTitle(payType.getTitle());
			order.setPayTypeId(payType.getId());
			req.getSession().setAttribute("order_temp", order);
			tdOrderService.save(order);
		}
		return "redirect:/order";
	}

	/**
	 * 添加收货地址的方法
	 * 
	 * @author dengxiao
	 */
	@RequestMapping(value = "/add/address")
	public String orderAddAddress(HttpServletRequest req, ModelMap map) {
		String username = (String) req.getSession().getAttribute("username");
		TdUser user = tdUserService.findByUsernameAndIsEnableTrue(username);
		if (null == user) {
			return "redirect:/login";
		}
		TdOrder order = (TdOrder) req.getSession().getAttribute("order_temp");
		// 获取真实用户的id
		Long realUserId = order.getRealUserId();
		// 获取真实用户
		TdUser realUser = tdUserService.findOne(realUserId);
		if (null != realUser) {
			// 获取用户的城市
			Long cityId = realUser.getCityId();
			TdCity city = tdCityService.findBySobIdCity(cityId);
			if (null != cityId) {
				// 查找指定城市下的所有行政区划
				List<TdDistrict> district_list = tdDistrictService.findByCityIdOrderBySortIdAsc(city.getId());
				map.addAttribute("district_list", district_list);
				if (null != district_list && district_list.size() > 0) {
					// 默认行政区划为集合的第一个
					TdDistrict district = district_list.get(0);
					// 根据指定的行政区划查找其下的行政街道
					if (null != district) {
						List<TdSubdistrict> subdistrict_list = tdSubdistrictService
								.findByDistrictIdOrderBySortIdAsc(district.getId());
						map.addAttribute("subdistrict_list", subdistrict_list);
					}
				}
			}
			map.addAttribute("user", realUser);
		}
		return "/client/order_add_address";
	}

	/**
	 * 选择行政区划而改变了行政街道的方法
	 * 
	 * @author dengxiao
	 */
	@RequestMapping(value = "/change/district")
	public String changeDistrict(ModelMap map, Long districtId) {
		List<TdSubdistrict> subdistrict_list = tdSubdistrictService.findByDistrictIdOrderBySortIdAsc(districtId);
		map.addAttribute("subdistrict_list", subdistrict_list);
		return "/client/order_address_subdistrict";
	}

	/**
	 * 保存新的收货地址的方法
	 * 
	 * @author dengxiao
	 */
	@RequestMapping(value = "/new/address")
	@ResponseBody
	public Map<String, Object> orderNewAddress(HttpServletRequest req, ModelMap map, String receiveName,
			String receiveMobile, Long district, Long subdistrict, String detail) {
		Map<String, Object> res = new HashMap<>();
		res.put("status", -1);
		String username = (String) req.getSession().getAttribute("username");
		TdUser user = tdUserService.findByUsernameAndIsEnableTrue(username);
		if (null == user) {
			res.put("status", -2);
			return res;
			// return "redirect:/login";
		}

		TdOrder order = (TdOrder) req.getSession().getAttribute("order_temp");
		// 获取真实用户的id
		Long realUserId = order.getRealUserId();
		// 获取真实用户
		TdUser realUser = tdUserService.findOne(realUserId);

		if (null != realUser) {
			// 判断是否超过最大限制数量
			if (tdSettingService.checkMaxShipping(res, realUser, 0L)) {
				return res;
			}

			// 过滤特殊字符
			receiveName = com.ynyes.lyz.util.StringUtils.StringFilter(receiveName);
			receiveMobile = com.ynyes.lyz.util.StringUtils.StringNumberFilter(receiveMobile);
			detail = com.ynyes.lyz.util.StringUtils.StringFilter(detail);

			TdDistrict tdDistrict = tdDistrictService.findOne(district);
			TdSubdistrict tdSubdistrict = tdSubdistrictService.findOne(subdistrict);

			// 将用户所有的收货地址都设置为非默认
			List<TdShippingAddress> shippingAddressList = realUser.getShippingAddressList();
			if (null != shippingAddressList) {
				for (TdShippingAddress address : shippingAddressList) {
					if (null != address) {
						address.setIsDefaultAddress(false);
						tdShippingAddressService.save(address);
					}
				}
			}

			TdShippingAddress address = new TdShippingAddress();
			address.setCity(user.getCityName());
			address.setCityId(user.getCityId());
			address.setDetailAddress(detail);
			address.setDisctrict(tdDistrict.getName());
			address.setSubdistrict(tdSubdistrict.getName());
			address.setDistrictId(district);
			address.setSubdistrictId(subdistrict);
			address.setIsDefaultAddress(true);
			address.setReceiverMobile(receiveMobile);
			address.setReceiverName(receiveName);
			address.setSortId(1.0);
			address = tdShippingAddressService.save(address);

			List<TdShippingAddress> addressList = realUser.getShippingAddressList();
			if (null == addressList) {
				addressList = new ArrayList<>();
			}

			addressList.add(address);
			tdUserService.save(user);

			// Add by Shawn
			order.setProvince(address.getProvince());
			order.setCity(address.getCity());
			order.setDisctrict(address.getDisctrict());
			order.setSubdistrict(address.getSubdistrict());
			order.setDetailAddress(address.getDetailAddress());

			order.setShippingAddress(
					address.getCity() + address.getDisctrict() + address.getSubdistrict() + address.getDetailAddress());
			order.setShippingName(address.getReceiverName());
			order.setShippingPhone(address.getReceiverMobile());
			order.setDeliverFee(tdSubdistrict.getDeliveryFee());
			req.getSession().setAttribute("order_temp", order);
			tdOrderService.save(order);
		}
		res.put("status", 0);
		return res;
	}

	/**
	 * 选择新的收货地址的方法
	 * 
	 * @author dengxiao
	 */
	@RequestMapping(value = "/change/address")
	public String changeAddress(HttpServletRequest req, ModelMap map) {
		String username = (String) req.getSession().getAttribute("username");
		TdUser user = tdUserService.findByUsername(username);
		if (null == user) {
			return "redirect:/login";
		}
		TdOrder order = (TdOrder) req.getSession().getAttribute("order_temp");
		// 获取订单的真实用户id
		Long realUserId = order.getRealUserId();
		// 获取真实用户
		TdUser realUser = tdUserService.findOne(realUserId);
		if (null != realUser) {
			List<TdShippingAddress> address_list = realUser.getShippingAddressList();
			// 倒叙排列
			Collections.reverse(address_list);
			TdShippingAddress shipDefault = null;
			if (address_list != null && address_list.size() > 0) {
				for (TdShippingAddress ship : address_list) {
					if (ship.getIsDefaultAddress() != null && ship.getIsDefaultAddress()) {
						shipDefault = ship;
					}
				}
			}
			if (shipDefault != null) {
				address_list.remove(shipDefault);
				address_list.add(0, shipDefault);
			}
			map.addAttribute("address_list", address_list);
			map.addAttribute("realUserId", realUserId);
		}
		return "/client/order_change_address";
	}

	/**
	 * 确认选择收货地址的方法
	 * 
	 * @author dengxiao
	 */
	@RequestMapping(value = "/address/check/{id}")
	public String addressCheck(HttpServletRequest req, ModelMap map, @PathVariable Long id) {
		String username = (String) req.getSession().getAttribute("username");
		TdUser user = tdUserService.findByUsernameAndIsEnableTrue(username);
		if (null == user) {
			return "redirect:/login";
		}
		TdShippingAddress address = tdShippingAddressService.findOne(id);
		TdOrder order = (TdOrder) req.getSession().getAttribute("order_temp");

		// Add by Shawn
		if (order != null && address != null) {
			order.setProvince(address.getProvince());
			order.setCity(address.getCity());
			order.setDisctrict(address.getDisctrict());
			order.setSubdistrict(address.getSubdistrict());
			order.setDetailAddress(address.getDetailAddress());

			order.setShippingAddress(
					address.getCity() + address.getDisctrict() + address.getSubdistrict() + address.getDetailAddress());
			order.setShippingName(address.getReceiverName());
			order.setShippingPhone(address.getReceiverMobile());
			tdOrderService.save(order);
			req.getSession().setAttribute("order_temp", order);
		}
		return "redirect:/order";
	}

	/**
	 * 进行支付的方法
	 * 
	 * @author dengxiao
	 */
	@RequestMapping(value = "/check")
	@ResponseBody
	public Map<String, Object> checkOrder(HttpServletRequest req, ModelMap map, Long id) {
		// System.err.println("进入支付控制器");
		Map<String, Object> res = new HashMap<>();
		res.put("status", -1);

		// 获取登陆用户
		String username = (String) req.getSession().getAttribute("username");
		TdUser user = tdUserService.findByUsernameAndIsEnableTrue(username);
		if (null == user) {
			res.put("message", "未找到指定的用户");
			return res;
		}

		// 获取虚拟订单
		// System.err.println("开始获取虚拟订单");
		TdOrder order_temp = (TdOrder) req.getSession().getAttribute("order_temp");

		// add by Shawn
		// 解决内存溢出的bug
		if (null == order_temp || "".equals((order_temp.getOrderNumber()))) {
			res.put("message", "未找到虚拟订单");
			return res;
		}

		// 2016-07-11修改：增加验证——判断是否输入了预约日期
		String deliveryDate = order_temp.getDeliveryDate();
		if (null == deliveryDate || "".equals(deliveryDate)) {
			res.put("message", "请在\"配送方式\"中填写预约时间");
			return res;
		}

		// 判断是否为代下单
		if (null != order_temp && null != order_temp.getIsSellerOrder() && order_temp.getIsSellerOrder()) {
			order_temp.setUsername(order_temp.getRealUserUsername());
			order_temp.setUserId(order_temp.getRealUserId());
		}

		if (null == order_temp.getSellerId() || null == order_temp.getSellerRealName()
				|| null == order_temp.getSellerUsername()) {
			res.put("message", "请在\"配送方式\"中选择服务导购");
			return res;
		}

		// System.err.println("获取虚拟订单中的地址信息");
		String address = order_temp.getShippingAddress();
		String shippingName = order_temp.getShippingName();
		String shippingPhone = order_temp.getShippingPhone();

		// System.err.println("判断是否填写收货地址");
		// 修改不为空 zp
		if ((null == address || null == shippingName || null == shippingPhone)) {
			if (!"门店自提".equals(order_temp.getDeliverTypeTitle())
					&& !(order_temp.getIsCoupon() != null && order_temp.getIsCoupon())) {
				res.put("message", "请填写收货地址");
				return res;
			}
		}

		// 如果是货到付款的情况下，判断用户是否具有货到付款的资格
		if ("货到付款".equalsIgnoreCase(order_temp.getPayTypeTitle())) {
			Boolean isCashOnDelivery = user.getIsCashOnDelivery();
			if (null != isCashOnDelivery && !isCashOnDelivery) {
				res.put("message", "&nbsp;&nbsp;您不允许货到付款<br>请选择其他支付方式");
				return res;
			}
		}

		// 判断是否在有效时间之内
		if (null != order_temp.getValidTime() && new Date().after(order_temp.getValidTime())) {
			res.put("message", "超过有效支付时间请刷新订单重新支付");
			// 修改订单中的商品价格为最新价格
			changeOrderToNewPrice(req, order_temp);
			tdPriceCouintService.countPrice(order_temp, user);
			// 修改有效支付时间
			orderValidTimeSet(req, order_temp);
			return res;
		}

		// System.err.println("开始判断用户是否属于线上支付");
		// 判断用户是否是线下付款
		Boolean isOnline = false;
		Long payTypeId = order_temp.getPayTypeId();
		TdPayType payType = tdPayTypeService.findOne(payTypeId);
		if (null != payType && payType.getIsOnlinePay()) {
			isOnline = true;
		}

		// 获取真实用户
		Long realUserId = order_temp.getRealUserId();
		TdUser realUser = tdUserService.findOne(realUserId);

		if (isOnline) {
			// 判断是否还有未支付的金额
			if ((order_temp.getTotalPrice() + order_temp.getUpstairsLeftFee()) > 0) {
				// 修改有效支付时间
				orderValidTimeSet(req, order_temp);
				// status的值为3代表需要通过第三方支付
				res.put("status", 3);
				res.put("title", payType.getTitle());
				res.put("order_number", order_temp.getOrderNumber());
				return res;
			} else {
				// 设置支付方式为其他
				order_temp.setPayTypeId(0L);
				order_temp.setPayTypeTitle("其它");
				if (null != order_temp.getIsSellerOrder() && order_temp.getIsSellerOrder()) {
					order_temp.setUserId(order_temp.getRealUserId());
					order_temp.setUsername(order_temp.getRealUserUsername());
				}
			}
		} else {
			if (order_temp.getTotalPrice().doubleValue() == 0.00) {
				// 设置支付方式为其他
				order_temp.setPayTypeId(0L);
				order_temp.setPayTypeTitle("其它");
			}
			if (null != order_temp.getIsSellerOrder() && order_temp.getIsSellerOrder()) {
				order_temp.setUserId(order_temp.getRealUserId());
				order_temp.setUsername(order_temp.getRealUserUsername());
			}
		}
		// 获取用户的不可体现余额
		Double unCashBalance = realUser.getUnCashBalance();
		if (null == unCashBalance) {
			unCashBalance = 0.00;
		}

		// 获取用户的可提现余额
		Double cashBalance = realUser.getCashBalance();
		if (null == cashBalance) {
			cashBalance = 0.00;
		}

		Double balance = realUser.getBalance();
		if (null == balance) {
			balance = 0.00;
		}

		// 如果用户的不可提现余额大于或等于订单的预存款使用额，则表示改单用的全部都是不可提现余额
		if (unCashBalance >= order_temp.getActualPay()) {
			// 修改：2016-08-26余额在拆单时扣减
			// realUser.setUnCashBalance(realUser.getUnCashBalance() -
			// order_temp.getActualPay());
			order_temp.setUnCashBalanceUsed(order_temp.getActualPay());
		} else {
			// realUser.setCashBalance(
			// realUser.getCashBalance() + realUser.getUnCashBalance() -
			// order_temp.getActualPay());
			// realUser.setUnCashBalance(0.0);
			order_temp.setUnCashBalanceUsed(realUser.getUnCashBalance());
			order_temp.setCashBalanceUsed(order_temp.getActualPay() - realUser.getUnCashBalance());
		}
		// realUser.setBalance(realUser.getBalance() -
		// order_temp.getActualPay());
		// tdUserService.save(realUser);

		req.getSession().setAttribute("order_temp", order_temp);
		tdOrderService.save(order_temp);

		res.put("status", 0);
		res.put("message", "支付成功");
		return res;
	}

	/**
	 * 确认下单并拆单的方法
	 * 
	 * @author dengxiao
	 */
	@RequestMapping(value = "/pay")
	public String orderPay(HttpServletRequest req) {
		String username = (String) req.getSession().getAttribute("username");
		TdUser user = tdUserService.findByUsernameAndIsEnableTrue(username);
		if (null == user) {
			return "redirect:/login";
		}
		TdOrder order = (TdOrder) req.getSession().getAttribute("order_temp");
		Long realUserId = order.getRealUserId();
		TdUser realUser = tdUserService.findOne(realUserId);
		Double balance = realUser.getBalance();
		Double actualPay = null == order.getActualPay() ? 0d : order.getActualPay();
		if (balance < actualPay) {
			order.setTotalPrice(order.getTotalPrice() + order.getActualPay());
			order.setActualPay(0d);
			order.setCashBalanceUsed(0d);
			order.setUnCashBalanceUsed(0d);
			tdOrderService.save(order);
			return "/client/pay_failure";
		} else {
			order.setStatusId(3L);
			if (null != order && null != order.getOrderNumber()) {
				if (order.getOrderNumber().contains("XN")) {
					// 门店自提 确认发货时扣库存
					if (!"门店自提".equals(order.getDeliverTypeTitle())) {
						// 拆单钱先去扣减库存
						tdDiySiteInventoryService.changeGoodsInventory(order, 2L, req, "发货", null);
					}

					// tdCommonService.dismantleOrder(req);
					try {
						settlementService.disminlate(req, order);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
			return "redirect:/user/order/0";
		}
	}

	/**
	 * 跳转到使用预存款的界面的方法
	 * 
	 * @author DengXiao
	 */
	@RequestMapping(value = "/user/balance")
	public String orderUserBalance(HttpServletRequest req, ModelMap map, Double max) {
		// 获取登录用户的信息
		String username = (String) req.getSession().getAttribute("username");
		TdUser user = tdUserService.findByUsernameAndIsEnableTrue(username);
		if (null == user) {
			return "redirect:/login";
		}
		// 获取当前订单信息
		TdOrder order = (TdOrder) req.getSession().getAttribute("order_temp");
		// 获取真实用户
		Long realUserId = order.getRealUserId();
		TdUser realUser = tdUserService.findOne(realUserId);
		if (null != realUser) {
			map.addAttribute("user", realUser);
		}
		map.addAttribute("order", order);
		map.addAttribute("max", max);
		return "/client/order_balance";
	}

	/**
	 * 确定使用预存款的方法
	 * 
	 * @author DengXiao
	 */
	@RequestMapping(value = "/balance/confirm")
	public String orderBalanceConfirm(HttpServletRequest req, ModelMap map, Double used) {
		// 获取登录用户的信息
		String username = (String) req.getSession().getAttribute("username");
		TdUser user = tdUserService.findByUsernameAndIsEnableTrue(username);
		if (null == user) {
			return "redirect:/login";
		}

		// 获取虚拟订单
		TdOrder order = (TdOrder) req.getSession().getAttribute("order_temp");
		
		// 清空原来的预存款支付金额，重新算价
		order.setTotalPrice(order.getTotalPrice() + order.getActualPay());
		order.setActualPay(0d);
		order.setUpstairsBalancePayed(0d);
		order.getUpstairsLeftFee();

		// 四舍五入used
		if (null != used) {
			BigDecimal b = new BigDecimal(used);
			used = b.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
			if (used > order.getTotalPrice()) {
				order.setActualPay(
						new BigDecimal(order.getTotalPrice()).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue());
				Double left = used - order.getTotalPrice();
				left = new BigDecimal(left).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
				if (left > order.getUpstairsFee()) {
					order.setUpstairsBalancePayed(order.getUpstairsFee());
				} else {
					order.setUpstairsBalancePayed(left);
				}
			} else {
				order.setActualPay(used);
			}

			order.getUpstairsLeftFee();
			req.getSession().setAttribute("order_temp", order);
			tdOrderService.save(order);
		}
		return "redirect:/order";
	}

	/**
	 * 验证用户是否是店长或者销顾的方法
	 * 
	 * @author DengXiao
	 */
	@RequestMapping(value = "/check/user/status")
	@ResponseBody
	public Map<String, Object> checkUserStatus(HttpServletRequest req, ModelMap map) {
		Map<String, Object> res = new HashMap<>();
		res.put("status", -1);

		// 获取登录用户的信息
		String username = (String) req.getSession().getAttribute("username");
		TdUser user = tdUserService.findByUsernameAndIsEnableTrue(username);
		if (null == user) {
			res.put("message", "获取信息失败");
			return res;
		}

		// 获取登录用户的身份状态
		Long userType = user.getUserType();
		if (null == userType) {
			res.put("message", "获取信息失败");
			return res;
		}

		// 判断用户是否为会员用户
		if (0L == userType.longValue()) {
			res.put("check", true);
		} else {
			res.put("check", false);
		}

		res.put("status", 0);
		return res;
	}

	/**
	 * 确认代下单的方法
	 * 
	 * @author DengXiao
	 */
	@RequestMapping(value = "/seller/operation")
	@ResponseBody
	public Map<String, Object> orderSellerOperation(HttpServletRequest req, ModelMap map, Long realUserId) {
		Map<String, Object> res = new HashMap<>();
		res.put("status", -1);
		// 获取被代下单的用户的信息
		TdUser realUser = tdUserService.findOne(realUserId);
		if (null == realUser) {
			res.put("message", "未获取到指定用户的信息");
			return res;
		}
		// 获取真实用户绑定的销顾
		Long sellerId = realUser.getSellerId();
		// 查询到销顾的信息
		TdUser seller = tdUserService.findOne(sellerId);
		// 生成虚拟订单
		TdOrder order = null;
		try {
			order = tdCommonService.createVirtual(req, realUserId);
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			tdPriceCouintService.countPrice(order, realUser);
		} catch (Exception e) {
			e.printStackTrace();
		}

		// 设置真实用户信息
		order.setUsername(realUser.getUsername());
		order.setUserId(realUser.getId());

		// 修改订单的默认收货地址
		List<TdShippingAddress> addressList = realUser.getShippingAddressList();
		TdShippingAddress defaultAddress = null;
		for (TdShippingAddress tdShippingAddress : addressList) {
			if (null != tdShippingAddress && null != tdShippingAddress.getIsDefaultAddress()
					&& tdShippingAddress.getIsDefaultAddress()) {
				defaultAddress = tdShippingAddress;
			}
		}
		// add Mdj
		if (defaultAddress == null) {
			res.put("message", "请让用户(" + realUser.getRealName() + ")填写默认地址");
			return res;
		}

		order.setProvince(defaultAddress.getProvince());
		order.setCity(defaultAddress.getCity());
		order.setDisctrict(defaultAddress.getDisctrict());
		order.setSubdistrict(defaultAddress.getSubdistrict());
		order.setDetailAddress(defaultAddress.getDetailAddress());

		order.setShippingAddress(defaultAddress.getCity() + defaultAddress.getDisctrict()
				+ defaultAddress.getSubdistrict() + defaultAddress.getDetailAddress());
		order.setShippingName(defaultAddress.getReceiverName());
		order.setShippingPhone(defaultAddress.getReceiverMobile());

		// 设置销顾信息
		if (null != seller) {
			order.setSellerId(seller.getId());
			order.setSellerRealName(seller.getRealName());
			order.setSellerUsername(seller.getUsername());
		}
		order.setIsSellerOrder(true);
		tdOrderService.save(order);

		// 清空已选
		tdCommonService.clear(req);

		res.put("status", 0);
		res.put("message", "代下单成功");
		return res;
	}

	/**
	 * 获取登录用户所在门店的所有会员（异步刷新）
	 * 
	 * @author DengXiao
	 */
	@RequestMapping(value = "/get/user/infomation")
	public String getUserInfomation(HttpServletRequest req, ModelMap map) {
		// 获取登录用户的信息
		String username = (String) req.getSession().getAttribute("username");
		TdUser user = tdUserService.findByUsernameAndIsEnableTrue(username);

		if (null != user) {
			List<TdUser> user_list = tdUserService
					.findByCityIdAndCustomerIdAndUserTypeOrderBySortIdAsc(user.getCityId(), user.getCustomerId());
			map.addAttribute("user_list", user_list);
		}
		return "/client/order_user_info";
	}

	/**
	 * 根据关键词搜索用户的方法
	 * 
	 * @author DengXiao
	 */
	@RequestMapping(value = "/change/user/info")
	public String orderChangeUserInfo(HttpServletRequest req, ModelMap map, String keywords) {
		// 获取登录用户的信息
		String username = (String) req.getSession().getAttribute("username");
		TdUser user = tdUserService.findByUsernameAndIsEnableTrue(username);

		if (null != user) {
			user.getCityId();
			user.getCustomerId();
			List<TdUser> user_list = tdUserService
					.findByCityIdAndCustomerIdAndUserTypeAndRealNameContainingOrderBySortIdAsc(user.getCityId(),
							user.getCustomerId(), keywords);
			map.addAttribute("user_list", user_list);
		}
		return "/client/order_user_info";
	}

	// 增加判断库存
	@RequestMapping(value = "/coupon/confirm")
	@ResponseBody
	public Map<String, Object> confirm(HttpServletRequest req, ModelMap map) {
		Map<String, Object> res = new HashMap<>();
		res.put("status", -1);

		// 获取指定的订单
		TdOrder order = (TdOrder) req.getSession().getAttribute("order_temp");
		if (null != order) {
			if ((order.getIsCoupon() != null && !order.getIsCoupon()) || order.getIsCoupon() == null
					|| !"门店自提".equals(order.getDeliverTypeTitle())) {
				TdOrder tdOrder = tdOrderService.findOne(order.getId());
				Long regionId = null;
				Long diysiteId = tdOrder.getDiySiteId();
				TdDiySite tdDiySite = tdDiySiteService.findOne(diysiteId);
				String deliverTypeTitleTemp = order.getDeliverTypeTitle();
				if (null != deliverTypeTitleTemp && !"".equals(deliverTypeTitleTemp)) {
					if ("送货上门".equals(deliverTypeTitleTemp) && tdDiySite.getIsHomeDelivery() == false) {
						res.put("status", -2);
						res.put("message", tdDiySite.getTitle() + "门店" + "不允许送货上门,请选择门店自提");
						return res;
					}
				} else {
					res.put("status", -2);
					res.put("message", "请选择配送方式");
					return res;
				}
				if (tdDiySite != null) {
					regionId = tdDiySite.getRegionId();
				}
				// 判断库存
				Map<String, Long> inventoryMap = new HashMap<String, Long>();
				// 商品列表
				List<TdOrderGoods> orderGoodsList = tdOrder.getOrderGoodsList();
				for (TdOrderGoods tdOrderGoods : orderGoodsList) {
					if (tdOrderGoods != null) {
						String sku = tdOrderGoods.getSku();
						// 判断是否存在 存在累加 不存在默认为0
						Long quantity = inventoryMap.get(sku) == null ? 0L : inventoryMap.get(sku);
						inventoryMap.put(sku, quantity + tdOrderGoods.getQuantity());

						// 增加非华润产品不能进行门店自提
						String brandTitle = tdOrderGoods.getBrandTitle();
						if (brandTitle != null && !brandTitle.equalsIgnoreCase("华润")
								&& tdOrder.getDeliverTypeTitle().equals("门店自提")) {
							res.put("status", -3);
							return res;
						}
					}
				}
				// 赠品列表
				List<TdOrderGoods> giftGoodsList = tdOrder.getGiftGoodsList();
				for (TdOrderGoods giftGoods : giftGoodsList) {
					if (giftGoods != null) {
						String sku = giftGoods.getSku();
						// 判断是否存在 存在累加 不存在默认为0
						Long quantity = inventoryMap.get(sku) == null ? 0L : inventoryMap.get(sku);
						inventoryMap.put(sku, quantity + giftGoods.getQuantity());
					}
				}
				// 小辅料列表
				List<TdOrderGoods> presentedList = tdOrder.getPresentedList();
				for (TdOrderGoods presented : presentedList) {
					if (presented != null) {
						String sku = presented.getSku();
						// 判断是否存在 存在累加 不存在默认为0
						Long quantity = inventoryMap.get(sku) == null ? 0L : inventoryMap.get(sku);
						inventoryMap.put(sku, quantity + presented.getQuantity());
					}
				}

				for (String sku : inventoryMap.keySet()) {
					TdDiySiteInventory diySiteInventory = null;
					// 判断用户的配送方式
					String deliverTypeTitle = order.getDeliverTypeTitle();
					if ("送货上门".equalsIgnoreCase(deliverTypeTitle)) {
						diySiteInventory = tdDiySiteInventoryService.findByGoodsCodeAndRegionIdAndDiySiteIdIsNull(sku,
								regionId);
						if (diySiteInventory == null || inventoryMap.get(sku) == null
								|| diySiteInventory.getInventory() < inventoryMap.get(sku)) {
							res.put("status", -2);
							res.put("message", sku + "商品仓库库存不足，请重新下单");
							return res;
						}
						// TdDiySiteInventory diySiteInventory =
						// tdDiySiteInventoryService
						// .findByGoodsCodeAndRegionIdAndDiySiteIdIsNull(sku,
						// regionId);
						// if (diySiteInventory == null || inventoryMap.get(sku)
						// == null
						// || diySiteInventory.getInventory() <
						// inventoryMap.get(sku)) {
						// res.put("status", -2);
						// return res;
					} else {
						diySiteInventory = tdDiySiteInventoryService.findByGoodsCodeAndDiySiteId(sku, diysiteId);
						if (diySiteInventory == null || inventoryMap.get(sku) == null
								|| diySiteInventory.getInventory() < inventoryMap.get(sku)) {
							res.put("status", -2);
							res.put("message", sku + "商品在" + tdDiySite.getTitle() + "<br>库存不足，请重新下单");
							return res;
						}
					}
				}
				// 再存一次 后面改
				req.getSession().setAttribute("order_temp", tdOrder);
			}
			// 获取用户的支付方式
			Long payTypeId = order.getPayTypeId();
			TdPayType payType = tdPayTypeService.findOne(payTypeId);

			Boolean isCoupon = false;
			// 判断订单是否为电子券单
			if (null != order.getIsCoupon() && order.getIsCoupon()) {
				isCoupon = true;
			}

			if (isCoupon && null != payType && null != payType.getIsOnlinePay() && !payType.getIsOnlinePay()
					&& null != order.getTotalPrice() && order.getTotalPrice().longValue() != 0L) {
				res.put("status", -1);
				return res;
			}

		}
		res.put("status", 0);
		return res;
	}

	/**
	 * 对用户填写的其他收入进行存储的方法
	 * 
	 * @author zp
	 */
	@RequestMapping(value = "/otherIncome/save")
	@ResponseBody
	public Map<String, Object> otherIncomeSave(HttpServletRequest req, Double otherIncome) {
		Map<String, Object> res = new HashMap<>();
		res.put("status", -1);
		// 不能小于0或者为空
		if (null == otherIncome || otherIncome <= 0) {
			res.put("status", -1);
		}

		TdOrder order = (TdOrder) req.getSession().getAttribute("order_temp");

		if (null != order) {
			order.setOtherIncome(otherIncome);
			tdOrderService.save(order);
		}

		req.getSession().setAttribute("order_temp", order);

		res.put("status", 0);
		res.put("otherIncome", otherIncome);
		return res;
	}

	/**
	 * 修改订单商品的价格为最新价格
	 */
	private void changeOrderToNewPrice(HttpServletRequest req, TdOrder order) {

		List<TdOrderGoods> orderGoods = order.getOrderGoodsList();

		if (null != orderGoods && orderGoods.size() > 0) {// 检查非空
			for (TdOrderGoods tdOrderGood : orderGoods) {
				TdGoods good = tdGoodsService.findOne(tdOrderGood.getGoodsId());
				// 获取指定商品的价目表项
				TdPriceListItem priceListItem = tdCommonService.getGoodsPrice(req, good);
				if (null != priceListItem) {// 检查非空
					// 修改订单商品中的价格为最新价格
					tdOrderGood.setPrice(priceListItem.getSalePrice());
					tdOrderGood.setRealPrice(priceListItem.getRealSalePrice());
					tdOrderGoodsService.save(tdOrderGood);
				}
			}
		}
	}

	/**
	 * 设置有效支付时间
	 * 
	 * @param order
	 *            订单
	 */
	private void orderValidTimeSet(HttpServletRequest req, TdOrder order) {
		if (null != order) {// 检查非空
			long currentTime = System.currentTimeMillis() + 6 * 60 * 60 * 1000;
			Date date = new Date(currentTime);
			order.setValidTime(date);
			req.getSession().setAttribute("order_temp", order);
			tdOrderService.save(order);
		}
	}

	/**
	 * 修改订单商品价格
	 * 
	 * @param order
	 * @param req
	 * @author zp
	 */
	private Boolean changeOrderGoodsNewPrice(TdOrder order, HttpServletRequest req) {
		List<TdOrderGoods> goodsList = order.getOrderGoodsList();
		if (goodsList != null && goodsList.size() > 0) {
			for (TdOrderGoods tdOrderGoods : goodsList) {
				TdGoods goods = tdGoodsService.findOne(tdOrderGoods.getGoodsId());
				TdPriceListItem price = tdCommonService.getGoodsPrice(req, goods);
				if (price == null) {
					return true;
				}
				tdOrderGoods.setPrice(price.getSalePrice());
				tdOrderGoods.setRealPrice(price.getRealSalePrice());
			}

		}
		return false;
	}

}
