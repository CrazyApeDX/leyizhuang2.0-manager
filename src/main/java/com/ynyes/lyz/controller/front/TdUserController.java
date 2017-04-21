package com.ynyes.lyz.controller.front;

import static org.apache.commons.lang3.StringUtils.leftPad;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
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
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ynyes.lyz.entity.TdActivity;
import com.ynyes.lyz.entity.TdActivityGift;
import com.ynyes.lyz.entity.TdActivityGiftList;
import com.ynyes.lyz.entity.TdArticle;
import com.ynyes.lyz.entity.TdArticleCategory;
import com.ynyes.lyz.entity.TdBalanceLog;
import com.ynyes.lyz.entity.TdCartGoods;
import com.ynyes.lyz.entity.TdCity;
import com.ynyes.lyz.entity.TdCoupon;
import com.ynyes.lyz.entity.TdDeliveryInfo;
import com.ynyes.lyz.entity.TdDeliveryInfoDetail;
import com.ynyes.lyz.entity.TdDistrict;
import com.ynyes.lyz.entity.TdDiySite;
import com.ynyes.lyz.entity.TdDiySiteInventory;
import com.ynyes.lyz.entity.TdGeoInfo;
import com.ynyes.lyz.entity.TdGoods;
import com.ynyes.lyz.entity.TdOrder;
import com.ynyes.lyz.entity.TdOrderGoods;
import com.ynyes.lyz.entity.TdOwnMoneyRecord;
import com.ynyes.lyz.entity.TdPayType;
import com.ynyes.lyz.entity.TdPriceListItem;
import com.ynyes.lyz.entity.TdRecharge;
import com.ynyes.lyz.entity.TdReturnNote;
import com.ynyes.lyz.entity.TdReturnReason;
import com.ynyes.lyz.entity.TdSetting;
import com.ynyes.lyz.entity.TdShippingAddress;
import com.ynyes.lyz.entity.TdSubdistrict;
import com.ynyes.lyz.entity.TdUserCollect;
import com.ynyes.lyz.entity.TdUserRecentVisit;
import com.ynyes.lyz.entity.TdUserSuggestion;
import com.ynyes.lyz.entity.TdUserSuggestionCategory;
import com.ynyes.lyz.entity.TdWareHouse;
import com.ynyes.lyz.entity.user.CreditChangeType;
import com.ynyes.lyz.entity.user.TdUser;
import com.ynyes.lyz.entity.user.TdUserCreditLog;
import com.ynyes.lyz.interfaces.service.TdInterfaceService;
import com.ynyes.lyz.interfaces.utils.INFConstants;
import com.ynyes.lyz.service.TdActivityService;
import com.ynyes.lyz.service.TdArticleCategoryService;
import com.ynyes.lyz.service.TdArticleService;
import com.ynyes.lyz.service.TdBalanceLogService;
import com.ynyes.lyz.service.TdCartGoodsService;
import com.ynyes.lyz.service.TdCityService;
import com.ynyes.lyz.service.TdCommonService;
import com.ynyes.lyz.service.TdCouponService;
import com.ynyes.lyz.service.TdDeliveryInfoDetailService;
import com.ynyes.lyz.service.TdDeliveryInfoService;
import com.ynyes.lyz.service.TdDistrictService;
import com.ynyes.lyz.service.TdDiySiteInventoryService;
import com.ynyes.lyz.service.TdDiySiteService;
import com.ynyes.lyz.service.TdGeoInfoService;
import com.ynyes.lyz.service.TdGoodsService;
import com.ynyes.lyz.service.TdOrderGoodsService;
import com.ynyes.lyz.service.TdOrderService;
import com.ynyes.lyz.service.TdOwnMoneyRecordService;
import com.ynyes.lyz.service.TdPayTypeService;
import com.ynyes.lyz.service.TdPriceCountService;
import com.ynyes.lyz.service.TdReChargeService;
import com.ynyes.lyz.service.TdReturnNoteService;
import com.ynyes.lyz.service.TdReturnReasonService;
import com.ynyes.lyz.service.TdSettingService;
import com.ynyes.lyz.service.TdShippingAddressService;
import com.ynyes.lyz.service.TdSubdistrictService;
import com.ynyes.lyz.service.TdUserCollectService;
import com.ynyes.lyz.service.TdUserCreditLogService;
import com.ynyes.lyz.service.TdUserRecentVisitService;
import com.ynyes.lyz.service.TdUserService;
import com.ynyes.lyz.service.TdUserSuggestionCategoryService;
import com.ynyes.lyz.service.TdUserSuggestionService;
import com.ynyes.lyz.service.TdWareHouseService;
import com.ynyes.lyz.util.ClientConstant;
import com.ynyes.lyz.util.MD5;

@Controller
@RequestMapping(value = "/user")
public class TdUserController {

	@Autowired
	private TdUserService tdUserService;

	@Autowired
	private TdUserRecentVisitService tdUserRecentVisitService;

	@Autowired
	private TdUserCollectService tdUserCollectService;

	@Autowired
	private TdOrderService tdOrderService;

	@Autowired
	private TdCommonService tdCommonService;

	@Autowired
	private TdUserSuggestionCategoryService tdUserSuggestionCategoryService;

	@Autowired
	private TdUserSuggestionService tdUserSuggestionService;

	@Autowired
	private TdDistrictService tdDistrictService;

	@Autowired
	private TdSubdistrictService tdSubdistrictService;

	@Autowired
	private TdCityService tdCityService;

	@Autowired
	private TdShippingAddressService tdShippingAddressService;

	@Autowired
	private TdDiySiteService tdDiySiteService;

	@Autowired
	private TdBalanceLogService tdBalanceLogService;

	@Autowired
	private TdGoodsService tdGoodsService;

	@Autowired
	private TdCouponService tdCouponService;

	@Autowired
	private TdSettingService tdSettingService;

	@Autowired
	private TdArticleCategoryService tdArticleCategoryService;

	@Autowired
	private TdArticleService tdArticleService;

	@Autowired
	private TdCartGoodsService tdCartGoodsService;

	// 退货单Service Max
	@Autowired
	private TdReturnNoteService tdReturnNoteService;

	@Autowired
	private TdOrderGoodsService tdOrderGoodsService;

	@Autowired
	private TdDeliveryInfoDetailService tdDeliveryInfoDetailService;

	@Autowired
	private TdGeoInfoService tdGeoInfoService;

	@Autowired
	private TdPriceCountService tdPriceCountService;

	@Autowired
	private TdDeliveryInfoService tdDeliveryInfoService;

	@Autowired
	private TdWareHouseService TdWareHouseService;

	@Autowired
	private TdActivityService tdActivityService;

	@Autowired
	private TdPayTypeService tdPayTypeService;

	@Autowired
	private TdInterfaceService tdInterfaceService;

	@Autowired
	private TdReChargeService tdReChargeService;

	@Autowired
	private TdDiySiteInventoryService tdDiySiteInventoryService;

	@Autowired
	private TdReturnReasonService tdReturnReasonService;

	@Autowired
	private TdOwnMoneyRecordService tdOwnMoneyRecordService;

	@Autowired
	private TdUserCreditLogService tdUserCreditLogService;

	/**
	 * 跳转到个人中心的方法（后期会进行修改，根据不同的角色，跳转的页面不同）
	 * 
	 * @author dengxiao
	 */
	@RequestMapping
	public String userCenter(HttpServletRequest req, ModelMap map) {
		String username = (String) req.getSession().getAttribute("username");
		TdUser user = tdUserService.findByUsernameAndIsEnableTrue(username);
		if (null == user) {
			return "redirect:/login";
		}
		map.addAttribute("user", user);

		// 获取浏览记录
		List<TdUserRecentVisit> recent_list = tdUserRecentVisitService.findByUsername(username);
		map.addAttribute("recent_list", recent_list);

		// 获取收藏记录
		List<TdUserCollect> collect_list = tdUserCollectService.findByUsername(username);
		map.addAttribute("collect_list", collect_list);

		// 获取已选
		Long number = tdCartGoodsService.countByUserId(user.getId());
		map.addAttribute("number", number);

		// // 获取用户的等级
		// TdUserLevel level =
		// tdUserLevelService.findOne(user.getUserLevelId());
		// map.addAttribute("level", level);

		// 获取客服电话
		List<TdSetting> all = tdSettingService.findAll();
		if (null != all && all.size() >= 1) {
			map.addAttribute("phone", all.get(0).getTelephone());
		}

		return "/client/user_center";
	}

	/**
	 * 查看我的订单的方法
	 * 
	 * @author dengxiao
	 */
	@RequestMapping(value = "/order/{typeId}")
	public String orderList(HttpServletRequest req, ModelMap map, @PathVariable Long typeId) {
		String username = (String) req.getSession().getAttribute("username");
		TdUser user = tdUserService.findByUsernameAndIsEnableTrue(username);
		if (null == user) {
			return "redirect:/login";
		}

		/*
		 * 根据登录用户端的身份查询出不同的订单
		 * 
		 * @author DengXiao
		 * 
		 * @date 2016年3月29日
		 */
		// if (0L == user.getUserType().longValue()) {
		// // 查找所有的订单
		// Page<TdOrder> all_order_page =
		// tdOrderService.findByUsernameAndStatusIdNotOrderByOrderTimeDesc(username,
		// 0,
		// 20);
		// map.addAttribute("all_order_list", all_order_page.getContent());
		//
		// // 查找所有待支付的订单
		// List<TdOrder> unpayed_order_list =
		// tdOrderService.findByUsernameAndStatusId(username, 2L);
		// map.addAttribute("unpayed_order_list", unpayed_order_list);
		//
		// // 查找所有待发货的订单
		// List<TdOrder> undeliver_order_list =
		// tdOrderService.findByUsernameAndStatusId(username, 3L);
		// map.addAttribute("undeliver_order_list", undeliver_order_list);
		//
		// // 查找所有待收货的订单
		// List<TdOrder> unsignin_order_list =
		// tdOrderService.findByUsernameAndStatusId(username, 4L);
		// map.addAttribute("unsignin_order_list", unsignin_order_list);
		//
		// // 查找所有待评价的订单
		// List<TdOrder> uncomment_order_list =
		// tdOrderService.findByUsernameAndStatusId(username, 5L);
		//
		// map.addAttribute("uncomment_order_list", uncomment_order_list);
		// map.addAttribute("user_type", 0);
		// } else if (1L == user.getUserType().longValue()) {
		//
		// // 查询所有的归属销顾为自己的订单
		// Page<TdOrder> all_order_page =
		// tdOrderService.findBySellerIdAndStatusIdNotOrderByOrderTimeDesc(user.getId(),
		// 0, 20);
		// map.addAttribute("all_order_list", all_order_page.getContent());
		//
		// // 查询指定销顾下待支付的订单
		// List<TdOrder> unpayed_order_list = tdOrderService
		// .findBySellerIdAndStatusIdOrderByOrderTimeDesc(user.getId(), 2L);
		// map.addAttribute("unpayed_order_list", unpayed_order_list);
		//
		// // 查询指定销顾待发货的订单
		// List<TdOrder> undeliver_order_list = tdOrderService
		// .findBySellerIdAndStatusIdOrderByOrderTimeDesc(user.getId(), 3L);
		// map.addAttribute("undeliver_order_list", undeliver_order_list);
		//
		// // 查询所有待收货的订单
		// List<TdOrder> unsignin_order_list = tdOrderService
		// .findBySellerIdAndStatusIdOrderByOrderTimeDesc(user.getId(), 4L);
		// map.addAttribute("unsignin_order_list", unsignin_order_list);
		//
		// // 查找所有未评价的订单
		// List<TdOrder> uncomment_order_list = tdOrderService
		// .findBySellerIdAndStatusIdOrderByOrderTimeDesc(user.getId(), 5L);
		// map.addAttribute("uncomment_order_list", uncomment_order_list);
		// map.addAttribute("user_type", 1);
		// } else if (2L == user.getUserType().longValue()) {
		// // 获取用户的门店
		// TdDiySite diySite = tdCommonService.getDiySite(req);
		// if (null != diySite) {
		// // 获取门店所有的订单
		// Page<TdOrder> all_order_page = tdOrderService
		// .findByDiySiteIdAndStatusIdNotOrderByOrderTimeDesc(diySite.getId(),
		// 0, 20);
		// map.addAttribute("all_order_list", all_order_page.getContent());
		// // 获取门店所有待支付的订单
		// List<TdOrder> unpayed_order_list = tdOrderService
		// .findByDiySiteIdAndStatusIdOrderByOrderTimeDesc(diySite.getId(), 2L);
		// map.addAttribute("unpayed_order_list", unpayed_order_list);
		// // 获取门店所有待发货的订单
		// List<TdOrder> undeliver_order_list = tdOrderService
		// .findByDiySiteIdAndStatusIdOrderByOrderTimeDesc(diySite.getId(), 3L);
		// map.addAttribute("undeliver_order_list", undeliver_order_list);
		// // 获取门店所有待收货的订单
		// List<TdOrder> unsign_order_list = tdOrderService
		// .findByDiySiteIdAndStatusIdOrderByOrderTimeDesc(diySite.getId(), 4L);
		// map.addAttribute("unsign_order_list", unsign_order_list);
		// // 获取门店所有未评价的订单
		// List<TdOrder> uncomment_order_list = tdOrderService
		// .findByDiySiteIdAndStatusIdOrderByOrderTimeDesc(diySite.getId(), 5L);
		//
		// map.addAttribute("uncomment_order_list", uncomment_order_list);
		// map.addAttribute("user_type", 2);
		// }
		// }
		Page<TdOrder> orderPage = this.tdOrderService.findByOrderTypeAndUser(typeId, user, 0);
		String key = this.tdOrderService.getKey(typeId);
		map.addAttribute(key, orderPage);
		map.addAttribute("user_type", user.getUserType());
		map.addAttribute("typeId", typeId);
		return "/client/user_order_list";
	}

	@RequestMapping(value = "/order/load/page")
	public String orderLoadPage(HttpServletRequest request, ModelMap map, Long currentOrderType,
			Integer currentPageNumber, String keywords) {
		String username = (String) request.getSession().getAttribute("username");
		TdUser user = tdUserService.findByUsernameAndIsEnableTrue(username);
		if (null == user) {
			return "redirect:/login";
		}
		Page<TdOrder> orderPage = null;
		if (null == keywords) {
			orderPage = this.tdOrderService.orderSearch(user, keywords, currentPageNumber);
		} else {
			orderPage = this.tdOrderService.findByOrderTypeAndUser(currentOrderType, user, currentPageNumber);
		}
		map.addAttribute("orderPage", orderPage);
		return "/client/user_order_page_data";
	}

	/**
	 * 模糊查询指定订单的方法（异步刷新）
	 * 
	 * @author DengXiao
	 */
	@RequestMapping(value = "/order/search")
	public String userOrderSearch(HttpServletRequest req, ModelMap map, String keywords) {
		String username = (String) req.getSession().getAttribute("username");
		TdUser user = tdUserService.findByUsernameAndIsEnableTrue(username);
		if (null != user) {
			Page<TdOrder> all_order_list = this.tdOrderService.orderSearch(user, keywords, 0);
			// 获取用户的身份
			map.addAttribute("all_order_list", all_order_list);
		}
		return "/client/user_all_order";
	}

	/**
	 * 跳转到我的收藏页面的方法
	 * 
	 * @author dengxiao
	 */
	@RequestMapping(value = "/collect")
	public String userCollect(HttpServletRequest req, ModelMap map) {
		String username = (String) req.getSession().getAttribute("username");
		TdUser user = tdUserService.findByUsernameAndIsEnableTrue(username);
		if (null == user) {
			return "redirect:/login";
		}

		List<TdUserCollect> collect_list = tdUserCollectService.findByUsernameOrderByCollectTimeDesc(username);
		map.addAttribute("collect_list", collect_list);
		TdDiySite diySite = tdCommonService.getDiySite(req);
		// 查找收藏的商品当前的价格
		if (null != collect_list && null != diySite) {
			for (int i = 0; i < collect_list.size(); i++) {
				TdUserCollect userCollect = collect_list.get(i);
				if (null != userCollect) {
					// 调用公共方法查询价格
					TdGoods goods = tdGoodsService.findOne(userCollect.getGoodsId());
					TdPriceListItem priceListItem = tdCommonService.getGoodsPrice(req, goods);
					// TdPriceListItem priceListItem = tdPriceListItemService
					// .findByPriceListIdAndGoodsId(diySite.getPriceListId(),
					// userCollect.getGoodsId());
					map.addAttribute("priceListItem" + i, priceListItem);
				}
			}
		}
		return "/client/user_collect_list";
	}

	/**
	 * 删除收藏商品的方法
	 * 
	 * @author dengxiao
	 */
	@RequestMapping(value = "/delete/collect")
	@ResponseBody
	public Map<String, Object> deleteCollect(HttpServletRequest req, Long id) {
		Map<String, Object> res = new HashMap<>();
		res.put("status", -1);
		// 查找到指定id的用户收藏
		TdUserCollect collect = tdUserCollectService.findOne(id);
		if (null == collect) {
			res.put("messag", "为查找到指定id的收藏");
			return res;
		}
		tdUserCollectService.delete(collect);
		res.put("status", 0);
		return res;
	}

	/**
	 * 跳转到浏览记录的方法
	 * 
	 * @author dengxiao
	 */
	@RequestMapping(value = "/recent")
	// param为【排序字段】-【规则1】-【规则2】-【规则3】
	public String userRecent(HttpServletRequest req, ModelMap map, String param) {
		String username = (String) req.getSession().getAttribute("username");
		TdUser user = tdUserService.findByUsernameAndIsEnableTrue(username);
		if (null == user) {
			return "redirect:/login";
		}

		if (null == param) {
			param = "0-0-0-0";
		}

		// 拆分排序字段
		String[] strs = param.split("-");
		String sortFiled = strs[0];
		String rule1 = strs[1];
		String rule2 = strs[2];
		String rule3 = strs[3];

		// 新建一个集合用于接收用户的浏览记录
		List<TdUserRecentVisit> recent_list = new ArrayList<>();

		// 获取用户归属门店的信息
		TdDiySite diySite = tdCommonService.getDiySite(req);

		if ("0".equals(sortFiled)) {
			if ("0".equals(rule1)) {
				recent_list = tdUserRecentVisitService.findByUsernameOrderByVisitTimeDesc(username);
				rule1 = "1";
			} else if ("1".equals(rule1)) {
				recent_list = tdUserRecentVisitService.findByUsernameOrderByVisitTimeAsc(username);
				rule1 = "0";
			}
		} else if ("1".equals(sortFiled)) {
			if ("0".equals(rule2)) {
				recent_list = tdUserRecentVisitService.findByUsernameOrderBySalePriceDesc(username,
						diySite.getPriceListId());
				rule2 = "1";
			} else if ("1".equals(rule2)) {
				recent_list = tdUserRecentVisitService.findByUsernameOrderBySalePriceAsc(username,
						diySite.getPriceListId());
				rule2 = "0";
			}
		} else if ("2".equals(sortFiled)) {
			if ("0".equals(rule3)) {
				recent_list = tdUserRecentVisitService.findByUsernameOrderBySoldNumberDesc(username);
				rule3 = "1";
			} else if ("1".equals(rule3)) {
				recent_list = tdUserRecentVisitService.findByUsernameOrderBySoldNumberAsc(username);
				rule3 = "0";
			}
		}

		map.addAttribute("selected_rule", Long.parseLong(sortFiled));
		map.addAttribute("rule1", rule1);
		map.addAttribute("rule2", rule2);
		map.addAttribute("rule3", rule3);

		// 遍历所有的浏览记录，获取所有浏览记录的价格
		for (int i = 0; i < recent_list.size(); i++) {
			TdUserRecentVisit recentVisit = recent_list.get(i);
			if (null != recentVisit) {
				// 获取指定商品的价目表项
				TdGoods goods = tdGoodsService.findOne(recentVisit.getGoodsId());
				TdPriceListItem priceListItem = tdCommonService.getGoodsPrice(req, goods);
				// TdPriceListItem priceListItem = tdPriceListItemService
				// .findByPriceListIdAndGoodsId(diySite.getPriceListId(),recentVisit.getGoodsId()
				// );
				map.addAttribute("priceListItem" + i, priceListItem);
			}
		}
		map.addAttribute("recent_list", recent_list);
		return "/client/user_recent_list";
	}

	/**
	 * 删除我的浏览记录的方法
	 * 
	 * @author dengxiao
	 */
	@RequestMapping(value = "/delete/recent")
	@ResponseBody
	public Map<String, Object> deleteRecent(HttpServletRequest req, Long id) {
		Map<String, Object> res = new HashMap<>();
		res.put("status", -1);
		// 查找到指定id的浏览记录
		TdUserRecentVisit recentVisit = tdUserRecentVisitService.findOne(id);
		if (null == recentVisit) {
			res.put("messag", "为查找到指定id的收藏");
			return res;
		}
		tdUserRecentVisitService.delete(recentVisit);
		res.put("status", 0);
		return res;
	}

	/**
	 * 跳转到我的已选页面的方法 增加单店库存 zp
	 * 
	 * @author dengxiao
	 */
	@RequestMapping(value = "/selected")
	public String mySelected(HttpServletRequest req, ModelMap map, String history) {
		String username = (String) req.getSession().getAttribute("username");
		TdUser user = tdUserService.findByUsernameAndIsEnableTrue(username);
		if (null == user) {
			return "redirect:/login";
		}

		Double total_price = 0.0;

		// TdDiySite diySite = tdCommonService.getDiySite(req);

		// 获取所有已选的商品
		List<TdCartGoods> all_selected = tdCartGoodsService.findByUserId(user.getId());
		List<TdCartGoods> visitSelected = new ArrayList<>();
		for (int i = 0; i < all_selected.size(); i++) {
			TdCartGoods cartGoods = all_selected.get(i);
			// 获取已选商品的库存
			if (null != cartGoods) {
				TdGoods goods = tdGoodsService.findOne(cartGoods.getGoodsId());
				if (null != goods) {
					// 2017-04-10修改：判断指定商品是否已经下载，如果已经下架则不会显示
					if (null != goods.getIsOnSale() && !goods.getIsOnSale()) {
						this.tdCartGoodsService.delete(cartGoods.getId());
					} else {
						visitSelected.add(cartGoods);
						total_price += cartGoods.getTotalPrice();
					}
				}

			}
		}
		map.addAttribute("all_selected", visitSelected);
		map.addAttribute("selected_number", tdCartGoodsService.countByUserId(user.getId()));
		map.addAttribute("totalPrice", total_price);
		map.addAttribute("history", history);

		return "/client/user_selected";
	}

	/**
	 * 更改已选数量的方法 可以手动修改数量 zp
	 * 
	 * @param operation
	 *            0:减一 1:加一 2手动修改数量
	 * @author dengxiao
	 */
	@RequestMapping(value = "/selected/change/quantity")
	public String selectedChangeQuantity(HttpServletRequest req, ModelMap map, Long operation, Long type, Long id,
			Long quantity) {
		String username = (String) req.getSession().getAttribute("username");
		TdUser user = tdUserService.findByUsernameAndIsEnableTrue(username);
		// 避免“空指针异常”
		if (null == user) {
			user = new TdUser();
		}
		// 设置默认值
		if (quantity == null) {
			quantity = 0L;
		}

		List<TdCartGoods> selected_goods = tdCartGoodsService.findByUserId(user.getId());
		Double total_price = 0.0;
		// 操作已选商品的情况
		if (0 == type) {
			for (int i = 0; i < selected_goods.size(); i++) {
				TdCartGoods cartGoods = selected_goods.get(i);
				if (null != cartGoods && null != cartGoods.getGoodsId()
						&& cartGoods.getGoodsId().longValue() == id.longValue()) {
					if (0L == operation) {
						cartGoods.setQuantity(cartGoods.getQuantity() - 1);
					}
					if (1L == operation) {
						cartGoods.setQuantity(cartGoods.getQuantity() + 1);
					}
					if (2L == operation) {
						cartGoods.setQuantity(quantity);
					}
					tdCartGoodsService.save(cartGoods);
				}
			}
		}
		TdDiySite diySite = tdCommonService.getDiySite(req);
		// 获取所有已选的商品
		for (int i = 0; i < selected_goods.size(); i++) {
			TdCartGoods cartGoods = selected_goods.get(i);
			// 获取已选商品的库存
			if (null != cartGoods) {
				TdGoods goods = tdGoodsService.findOne(cartGoods.getGoodsId());
				if (null != goods) {
					// 查询商品单店库存
					TdDiySiteInventory diySiteInventory = tdDiySiteInventoryService
							.findByGoodsCodeAndRegionIdAndDiySiteIdIsNull(goods.getCode(), diySite.getRegionId());
					Long goodsInventory = 0L;
					// 设置单店库存
					if (diySiteInventory != null) {
						goodsInventory = diySiteInventory.getInventory();
					}
					map.addAttribute("goods" + i, goodsInventory);
					// 如果已选数量大于了最大库存，则消减已选数量
					/*
					 * if (null != cartGoods.getQuantity() &&
					 * cartGoods.getQuantity() > goodsInventory) {
					 * cartGoods.setQuantity(goodsInventory); }
					 */
					cartGoods.setTotalPrice(cartGoods.getQuantity() * cartGoods.getPrice());
					cartGoods.setRealTotalPrice(cartGoods.getRealPrice() * cartGoods.getQuantity());
					cartGoods = tdCartGoodsService.save(cartGoods);
					total_price += cartGoods.getTotalPrice();
				}
			}
		}
		map.addAttribute("selected_number", tdCartGoodsService.countByUserId(user.getId()));
		map.addAttribute("all_selected", selected_goods);
		map.addAttribute("totalPrice", total_price);
		return "/client/selected_goods_and_color";
	}

	/**
	 * 跳转到咨询投诉页面的方法
	 * 
	 * @auhor dengxiao
	 */
	@RequestMapping(value = "/suggestion")
	public String userSuggestion(HttpServletRequest req, ModelMap map) {
		String username = (String) req.getSession().getAttribute("username");
		TdUser user = tdUserService.findByUsernameAndIsEnableTrue(username);
		if (null == user) {
			return "redirect:/login";
		}

		// 查询得到所有能用的咨询分类
		List<TdUserSuggestionCategory> category_list = tdUserSuggestionCategoryService.findAll();
		TdSetting tdSetting = tdSettingService.findTopBy();
		map.addAttribute("telphone", tdSetting.getTelephone());
		map.addAttribute("category_list", category_list);
		map.addAttribute("username", username);
		return "/client/user_suggestion";
	}

	/**
	 * 保存咨询投诉的方法
	 * 
	 * @author dengxiao
	 */
	@RequestMapping(value = "/suggestion/save")
	@ResponseBody
	public Map<String, Object> userSuggestionSave(HttpServletRequest req, String phone, String suggestion,
			Long categoryId) {
		Map<String, Object> res = new HashMap<>();
		res.put("status", -1);

		// 判断用户是否登陆
		String username = (String) req.getSession().getAttribute("username");
		// 获取登陆用户的信息
		TdUser user = tdUserService.findByUsernameAndIsEnableTrue(username);
		if (null == user) {
			res.put("status", -2);
			return res;
		}

		// 创建一个实体用于存储咨询/投诉的信息
		TdUserSuggestion userSuggestion = new TdUserSuggestion();
		userSuggestion.setCategoryId(categoryId);
		userSuggestion.setContent(suggestion);
		userSuggestion.setUserId(user.getId());
		userSuggestion.setIsAnswered(false);
		userSuggestion.setCreateTime(new Date());
		userSuggestion.setParentId(0L);
		userSuggestion.setIsDelete(false);
		tdUserSuggestionService.save(userSuggestion);

		res.put("status", 0);
		return res;
	}

	/**
	 * 跳转到用户信息界面的方法
	 * 
	 * @author dengxiao
	 */
	@RequestMapping(value = "/info")
	public String userInfo(HttpServletRequest req, ModelMap map) {
		String username = (String) req.getSession().getAttribute("username");
		TdUser user = tdUserService.findByUsernameAndIsEnableTrue(username);
		if (null == user) {
			return "redirect:/login";
		}
		map.addAttribute("user", user);
		return "/client/user_info";
	}

	/**
	 * 修改登陆用户生日或性别的方法
	 * 
	 * @author dengxiao
	 */
	@RequestMapping(value = "/change")
	@ResponseBody
	public Map<String, Object> userChangeInfo(HttpServletRequest req, Long type, String param) {
		Map<String, Object> res = new HashMap<>();
		res.put("status", -1);

		// 判断用户是否登陆
		String username = (String) req.getSession().getAttribute("username");
		TdUser user = tdUserService.findByUsernameAndIsEnableTrue(username);
		if (null == user) {
			res.put("status", -2);
			return res;
		}

		// 如果是修改性别
		if (0L == type) {
			user.setSex(param);
		}

		// 如果是修改生日
		if (1L == type) {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			Date birthday = null;
			try {
				birthday = sdf.parse(param + " 00:00:00");
			} catch (ParseException e) {
				e.printStackTrace();
				return res;
			}
			user.setBirthday(birthday);
		}

		tdUserService.save(user);
		res.put("status", 0);
		return res;
	}

	/**
	 * 跳转到修改密码界面的方法
	 * 
	 * @author dengxiao
	 */
	@RequestMapping(value = "/edit/password")
	public String userEditPassword(HttpServletRequest req, ModelMap map) {
		String username = (String) req.getSession().getAttribute("username");
		TdUser user = tdUserService.findByUsernameAndIsEnableTrue(username);
		if (null == user) {
			return "redirect:/login";
		}
		return "/client/edit_password";
	}

	/**
	 * 验证并保存修改密码的方法
	 * 
	 * @author dengxiao
	 */
	@RequestMapping(value = "/edit/password/save")
	@ResponseBody
	public Map<String, Object> userEditPasswordSave(HttpServletRequest req, String oldPassword, String newPassword) {
		Map<String, Object> res = new HashMap<>();
		res.put("status", -1);

		// 判断是否登陆
		String username = (String) req.getSession().getAttribute("username");
		TdUser user = tdUserService.findByUsernameAndIsEnableTrue(username);
		if (null == user) {
			res.put("status", -2);
			return res;
		}

		if (null == oldPassword || !MD5.md5(oldPassword, 32).equals(user.getPassword())) {
			res.put("message", "亲，您输入的原密码不正确");
			return res;
		}

		if (MD5.md5(oldPassword, 32).equals(user.getPassword())) {
			user.setPassword(MD5.md5(newPassword, 32));
			tdUserService.save(user);
			res.put("status", 0);
		}

		return res;
	}

	/**
	 * 跳转到收货页面的方法
	 * 
	 * @author dengxiao
	 */
	@RequestMapping(value = "/address")
	public String userAddress(HttpServletRequest req, ModelMap map) {
		String username = (String) req.getSession().getAttribute("username");
		TdUser user = tdUserService.findByUsernameAndIsEnableTrue(username);
		if (null == user) {
			return "redirect:/login";
		}
		List<TdShippingAddress> address_list = user.getShippingAddressList();
		// 遍历集合获取默认收货地址
		if (null != address_list) {
			for (TdShippingAddress shippingAddress : address_list) {
				if (null != shippingAddress && null != shippingAddress.getIsDefaultAddress()
						&& shippingAddress.getIsDefaultAddress()) {
					map.addAttribute("default", shippingAddress);
				}
			}
		}
		// 倒叙排列
		Collections.reverse(address_list);
		map.addAttribute("address_list", address_list);
		return "/client/user_address";
	}

	/**
	 * 跳转到新增收货页面的方法
	 * 
	 * @param returnPage
	 *            后面添加的参数 值为1时设置sesssion中returnPage的值为1 其他条件则为0
	 * @author dengxiao
	 */
	@RequestMapping(value = "/address/{type}")
	public String userAddressAdd(HttpServletRequest req, ModelMap map, @PathVariable Long type, Long id,
			String receiver, String receiverMobile, String detailAddress, String returnPage, Long realUserId) {
		// 判断用户是否登陆
		String username = (String) req.getSession().getAttribute("username");
		TdUser user = tdUserService.findByUsernameAndIsEnableTrue(username);
		if (null == user) {
			return "redirect:/login";
		}
		if (null != returnPage && "1".equals(returnPage)) {
			req.getSession().setAttribute("returnPage", "1");
		} else {
			req.getSession().setAttribute("returnPage", "0");
		}
		if (realUserId != null) {
			map.addAttribute("realUserId", realUserId);
		}

		// type的值代表了当前进行的操作：1. type==0代表进行的是增加收货地址的操作；2. type==1代表进行的是修改收货地址的操作
		if (0L == type) {
			TdShippingAddress address = new TdShippingAddress();
			address.setCity(user.getCityName());
			// 默认联系人电话为会员的电话号码
			address.setReceiverMobile(username);
			map.addAttribute("address", address);
		}

		if (1L == type) {
			// 获取指定id的收货地址
			TdShippingAddress address = tdShippingAddressService.findOne(id);
			map.addAttribute("address", address);
			map.addAttribute("addressId", id);
			// 设置行政区域
			req.getSession().setAttribute("new_district", address.getDisctrict());
			req.getSession().setAttribute("new_district_id", address.getDistrictId());
			// 设置行政街道
			req.getSession().setAttribute("new_subdistrict", address.getSubdistrict());
			req.getSession().setAttribute("new_subdistrict_id", address.getSubdistrictId());
		}
		map.addAttribute("city", user.getCityName());
		map.addAttribute("operation", type);
		return "/client/add_address_base";
	}

	/**
	 * 获取（指定城市/行政区划）下的所有（行政区划/行政街道）的方法
	 * 
	 * @author dengxiao
	 */
	@RequestMapping(value = "/address/get")
	public String addressGet(HttpServletRequest req, Long type, Long id, ModelMap map) {

		// type的值表示不同的操作：0. 获取指定id的城市的所有下属行政区划；1. 获取指定id的行政区划的所有下属行政街道
		// ;3.选择行政街道完毕，存储信息
		if (0 == type) {
			// 获取当前登陆的用户
			String username = (String) req.getSession().getAttribute("username");
			TdUser user = tdUserService.findByUsernameAndIsEnableTrue(username);
			if (null != user) {
				// 获取用户城市下的所有行政区划
				TdCity city = tdCityService.findBySobIdCity(user.getCityId());
				List<TdDistrict> region_list = tdDistrictService.findByCityIdOrderBySortIdAsc(city.getId());
				map.addAttribute("region_list", region_list);
				// status的值为1表示下一步是选择行政区划】
				map.addAttribute("status", 1);
			}
		}

		if (1 == type) {
			// 获取指定的行政区划
			TdDistrict district = tdDistrictService.findOne(id);
			// 获取指定行政区划下的所有行政街道
			if (null != district) {
				List<TdSubdistrict> region_list = tdSubdistrictService
						.findByDistrictIdOrderBySortIdAsc(district.getId());
				map.addAttribute("region_list", region_list);
				// status的值为2表示下一步是选择行政街道
				map.addAttribute("status", 2);
				req.getSession().setAttribute("new_district", district.getName());
				req.getSession().setAttribute("new_district_id", id);
				// 删除session中的行政街道
				req.getSession().setAttribute("new_subdistrict", null);
				req.getSession().setAttribute("new_subdistrict_id", null);
			}
		}

		if (2 == type) {
			// 获取指定的行政街道
			TdSubdistrict subdistrict = tdSubdistrictService.findOne(id);
			// 存储行政街道
			if (null != subdistrict) {
				req.getSession().setAttribute("new_subdistrict", subdistrict.getName());
				req.getSession().setAttribute("new_subdistrict_id", id);
			}
		}

		return "/client/add_address_detail";
	}

	/**
	 * 保存新增的收货地址的方法 增加收货地址限制
	 * 
	 * @author dengxiao
	 */
	@RequestMapping(value = "/address/add/save")
	@ResponseBody
	public Map<String, Object> userAddressAddSave(HttpServletRequest req, String receiver, String receiverMobile,
			String detailAddress, Long operation, Long addressId, Long realUserId) {
		Map<String, Object> res = new HashMap<>();
		res.put("status", -1);

		// 判断用户是否登陆
		String username = (String) req.getSession().getAttribute("username");
		TdUser user = tdUserService.findByUsernameAndIsEnableTrue(username);
		if (null == user) {
			res.put("status", -2);
			return res;
		}
		if (realUserId != null) {
			user = tdUserService.findOne(realUserId);
		}

		if (null == user) {
			res.put("status", -2);
			return res;
		}

		String district = (String) req.getSession().getAttribute("new_district");
		String subdistrict = (String) req.getSession().getAttribute("new_subdistrict");
		Long district_id = (Long) req.getSession().getAttribute("new_district_id");
		Long subdistrict_id = (Long) req.getSession().getAttribute("new_subdistrict_id");

		if (null == receiver || "".equals(receiver)) {
			res.put("message", "亲，请添加收货人姓名");
			return res;
		}

		if (null == receiverMobile || "".equals(receiverMobile)) {
			res.put("message", "亲，请添加收件人联系电话");
			return res;
		}

		if (null == district || "".equals(district)) {
			res.put("message", "亲，请选择收件地址的行政区划");
			return res;
		}

		if (null == subdistrict || "".equals(subdistrict)) {
			res.put("message", "亲，请选择收件地址的行政街道");
			return res;
		}

		if (null == detailAddress || "".equals(detailAddress)) {
			res.put("message", "亲，请添加详细地址");
			return res;
		}
		// 判断是否超过最大限制数量
		if (tdSettingService.checkMaxShipping(res, user, operation)) {
			return res;
		}
		// 过滤特殊字符
		receiver = com.ynyes.lyz.util.StringUtils.StringFilter(receiver);
		receiverMobile = com.ynyes.lyz.util.StringUtils.StringNumberFilter(receiverMobile);
		detailAddress = com.ynyes.lyz.util.StringUtils.StringFilter(detailAddress);

		TdShippingAddress address = null;

		TdCity tdCity = tdCityService.findByCityName(user.getCityName());
		if (0L == operation) {
			address = new TdShippingAddress();
			address.setIsDefaultAddress(false);
		} else if (1L == operation) {
			address = tdShippingAddressService.findOne(addressId);
		}
		address.setProvince(tdCity.getProvince());
		address.setCity(tdCity.getCityName());
		address.setCityId(tdCity.getId());
		address.setDisctrict(district);
		address.setSubdistrict(subdistrict);
		address.setDetailAddress(detailAddress);
		address.setReceiverName(receiver);
		address.setReceiverMobile(receiverMobile);
		address.setDistrictId(district_id);
		address.setSubdistrictId(subdistrict_id);
		// 获取登陆用户的收货地址
		List<TdShippingAddress> address_list = user.getShippingAddressList();
		if (null == address_list || 0 == address_list.size()) {
			address_list = new ArrayList<>();
			address.setIsDefaultAddress(true);
		}

		address = tdShippingAddressService.save(address);
		address_list.add(address);
		user.setShippingAddressList(address_list);
		tdUserService.save(user);

		// 清除session中的行政区划和行政街道
		req.getSession().setAttribute("new_district", null);
		req.getSession().setAttribute("new_district_id", null);
		req.getSession().setAttribute("new_subdistrict", null);
		req.getSession().setAttribute("new_subdistrict_id", null);

		// status 0:查看收货地址页面 1 选择收货地址页面
		res.put("status", 0);
		// returnPage 值为1时修改返回状态res.status=1 回到选择收货地址页面
		String returnPage = (String) req.getSession().getAttribute("returnPage");
		if (null != returnPage && "1".equals(returnPage)) {
			res.put("status", 1);
		}
		return res;
	}

	/**
	 * 操作用户收货地址删除/设为默认的方法
	 * 
	 * @author dengxiao
	 */
	@RequestMapping(value = "/address/operate")
	@ResponseBody
	public Map<String, Object> userAddressOperate(HttpServletRequest req, Long id, Long type) {
		Map<String, Object> res = new HashMap<>();
		res.put("status", -1);

		// 判断用户是否登陆
		String username = (String) req.getSession().getAttribute("username");
		TdUser user = tdUserService.findByUsernameAndIsEnableTrue(username);
		if (null == user) {
			res.put("status", -2);
			return res;
		}

		// type的值为1的时候执行删除操作
		if (1L == type) {
			// 判断指定id的收货地址是否为默认地址
			TdShippingAddress address = tdShippingAddressService.findOne(id);
			// 创建一个布尔变量用于判断是否删除的是默认的
			Boolean isDefault = false;
			if (null != address && null != address.getIsDefaultAddress() && address.getIsDefaultAddress()) {
				isDefault = true;
			}

			// 直接进行删除操作
			tdShippingAddressService.delete(id);
			// 如果删除的是默认地址，则把剩余收货地址的第一个（默认）改变为默认收货地址
			if (isDefault) {
				// 因为之前做了删除操作，所以要重新获取用户的信息
				user = tdUserService.findByUsernameAndIsEnableTrue(username);
				List<TdShippingAddress> address_list = user.getShippingAddressList();
				if (null != address_list) {
					for (int i = 0; i < address_list.size(); i++) {
						if (0 == i) {
							address_list.get(i).setIsDefaultAddress(true);
							tdShippingAddressService.save(address_list.get(i));
						}
					}
				}
			}
		}

		// type的值为2的时候执行设为默认操作
		if (2L == type) {
			// 获取用户所有的收货地址
			List<TdShippingAddress> address_list = user.getShippingAddressList();
			if (null != address_list) {
				for (int i = 0; i < address_list.size(); i++) {
					if (null != address_list.get(i) && null != address_list.get(i).getIsDefaultAddress()
							&& address_list.get(i).getIsDefaultAddress()) {
						address_list.get(i).setIsDefaultAddress(false);
					}
					if (null != address_list.get(i) && null != address_list.get(i).getId()
							&& address_list.get(i).getId().equals(id)) {
						address_list.get(i).setIsDefaultAddress(true);
					}
				}
			}
			user.setShippingAddressList(address_list);
			tdUserService.save(user);
		}

		res.put("status", 0);
		return res;
	}

	/**
	 * 跳转到用户门店归属的页面
	 * 
	 * @author dengxiao
	 */
	@RequestMapping(value = "/diysite")
	public String userDiysite(HttpServletRequest req, ModelMap map) {
		String username = (String) req.getSession().getAttribute("username");
		TdUser user = tdUserService.findByUsernameAndIsEnableTrue(username);
		if (null == user) {
			return "redirect:/login";
		}
		map.addAttribute("user", user);

		// 虚拟门店的用户不能够更改门店
		Long customerId = user.getCustomerId();
		Long cityId = user.getCityId();

		TdDiySite site = tdDiySiteService.findByRegionIdAndCustomerId(cityId, customerId);
		if (null != site && (site.getStatus() != null && site.getStatus() == 2)) {
			map.addAttribute("isSelected", false);
		}

		// 获取用户所在城市的所有行政区划
		TdCity city = tdCityService.findBySobIdCity(user.getCityId());
		List<TdDistrict> district_list = tdDistrictService.findByCityIdOrderBySortIdAsc(city.getId());
		// 遍历集合，获取第一项行政区划下的所有行政街道
		if (null != district_list) {
			for (int i = 0; i < district_list.size(); i++) {
				if (0 == i) {
					TdDistrict district = district_list.get(i);
					// 获取指定行政区划下的所有门店
					if (null != district) {
						List<TdDiySite> all_site = tdDiySiteService.findByDisctrictIdOrderBySortIdAsc(district.getId());
						map.addAttribute("all_site", all_site);
					}
				}
			}
		}
		map.addAttribute("district_list", district_list);
		return "/client/user_diy_site";
	}

	/**
	 * 根据选择的行政区划获取其下属的所有门店信息
	 * 
	 * @author dengxiao
	 */
	@RequestMapping(value = "/diysite/get")
	public String getDiySite(ModelMap map, Long districtId) {
		// 根据行政区划的id获取指定行政区划下的所有门店
		List<TdDiySite> all_site = tdDiySiteService.findByDisctrictIdOrderBySortIdAsc(districtId);
		map.addAttribute("all_site", all_site);
		return "/client/site_in_district";
	}

	/**
	 * 用户选择门店的方法
	 * 
	 * @author dengxiao
	 */
	@RequestMapping(value = "/select/diysite")
	@ResponseBody
	public Map<String, Object> selectDiySite(HttpServletRequest req, Long id) {
		Map<String, Object> res = new HashMap<>();
		res.put("status", -1);

		// 判断用户是否登陆
		String username = (String) req.getSession().getAttribute("username");
		TdUser user = tdUserService.findByUsernameAndIsEnableTrue(username);
		if (null == user) {
			res.put("status", -2);
			return res;
		}

		// 获取指定id的门店信息
		TdDiySite diySite = tdDiySiteService.findOne(id);
		// 更改用户的归属门店
		user.setUpperDiySiteId(diySite.getId());
		user.setDiyName(diySite.getTitle());
		user.setCustomerId(diySite.getCustomerId());
		tdUserService.save(user);

		// 清空用户的购物车
		List<TdCartGoods> cartList = tdCartGoodsService.findByUsername(username);
		if (null != cartList && cartList.size() > 0) {
			for (TdCartGoods cartGoods : cartList) {
				tdCartGoodsService.delete(cartGoods.getId());
			}
		}

		res.put("status", 0);
		return res;
	}

	/**
	 * 跳转到我的财富的方法
	 * 
	 * @author dengxiao
	 */
	@RequestMapping(value = "/fortune")
	public String userFortune(HttpServletRequest req, ModelMap map) {
		String username = (String) req.getSession().getAttribute("username");
		TdUser user = tdUserService.findByUsernameAndIsEnableTrue(username);
		if (null == user) {
			return "redirect:/login";
		}

		// 检验当前优惠券是否存在过期的
		tdCommonService.checkUserCoupon(req);

		List<TdCoupon> no_product_coupon_list = tdCouponService
				.findByUsernameAndIsUsedFalseAndIsOutDateFalseAndTypeCategoryIdNotOrderByGetTimeDesc(username, 3L,
						user.getCityName());
		List<TdCoupon> product_coupon_list = tdCouponService
				.findByUsernameAndIsUsedFalseAndIsOutDateFalseAndTypeCategoryIdOrderByGetTimeDesc(username, 3L,
						user.getCityName());

		Double no_product_total = 0.0;

		// 获取现金券的总额
		for (TdCoupon coupon : no_product_coupon_list) {
			if (null != coupon && null != coupon.getPrice()) {
				no_product_total += coupon.getPrice();
			}
		}

		// 产品全张数
		Long totalNummber = 0L;
		for (TdCoupon tdCoupon : product_coupon_list) {
			if (null != tdCoupon) {
				if (null == tdCoupon.getGetNumber()) {
					tdCoupon.setGetNumber(1L);
				}
				totalNummber += tdCoupon.getGetNumber();
			}
		}

		map.addAttribute("user", user);
		map.addAttribute("no_product_total", no_product_total);
		map.addAttribute("product_coupon_list", product_coupon_list);
		map.addAttribute("totalNumber", totalNummber);

		return "/client/user_fortune";
	}

	@RequestMapping(value = "/credit")
	public String userCredit(HttpServletRequest request, ModelMap map) {
		String username = (String) request.getSession().getAttribute("username");
		TdUser user = tdUserService.findByUsernameAndIsEnableTrue(username);
		if (null == user) {
			return "redirect:/login";
		}

		Page<TdUserCreditLog> logPage = this.tdUserCreditLogService.findBySellerIdOrderByChangeTimeDesc(user.getId(), 0,
				20);
		map.addAttribute("logPage", logPage);
		map.addAttribute("user", user);
		return "/client/user_credit";
	}

	/**
	 * 跳转到我的钱包的方法
	 * 
	 * @author dengxiao
	 */
	@RequestMapping(value = "/wallet")
	public String userWallet(HttpServletRequest req, ModelMap map) {
		String username = (String) req.getSession().getAttribute("username");
		TdUser user = tdUserService.findByUsernameAndIsEnableTrue(username);
		if (null == user) {
			return "redirect:/login";
		}

		// 查询到登陆用户所有的钱包操作记录
		Page<TdBalanceLog> balance_log_page = tdBalanceLogService
				.findByUserIdAndIsSuccessTrueOrderByIdDesc(user.getId(), 0, ClientConstant.MAXRECENTNUM);
		map.addAttribute("balance_log_page", balance_log_page);
		map.addAttribute("user", user);
		return "/client/user_wallet";
	}

	/**
	 * 跳转到充值页面的方法
	 * 
	 * @author dengxiao
	 */
	@RequestMapping(value = "/recharge")
	public String userRecharge(HttpServletRequest req, ModelMap map) {
		String username = (String) req.getSession().getAttribute("username");
		TdUser user = tdUserService.findByUsernameAndIsEnableTrue(username);
		if (null == user) {
			return "redirect:/login";
		}

		// 获取所有的在线支付方式
		List<TdPayType> payType_list = tdPayTypeService.findByIsOnlinePayTrueAndIsEnableTrueOrderBySortIdAsc();

		map.addAttribute("payType_list", payType_list);
		return "/client/user_recharge";
	}

	/**
	 * 跳转到现金卷使用说明页面
	 * 
	 * @author tangjunmao
	 */
	@RequestMapping(value = "/coupon/cash/guide")
	public String toCashCouponGuide(ModelMap map) {
		TdSetting setting = tdSettingService.findTopBy();
		map.addAttribute("cashCouponGuide", setting.getCashCouponGuide());
		return "/client/user_cash_coupon_guide";
	}

	/**
	 * 跳转到产品卷使用说明页面
	 * 
	 * @author tangjunmao
	 */
	@RequestMapping(value = "/coupon/goods/guide")
	public String toGoodsCouponGuide(ModelMap map) {
		TdSetting setting = tdSettingService.findTopBy();
		map.addAttribute("goodsCouponGuide", setting.getGoodsCouponGuide());
		return "/client/user_goods_coupon_guide";
	}

	/**
	 * 跳转到用户指定优惠券的方法
	 * 
	 * @author dengxiao
	 */
	@RequestMapping(value = "/coupon/{type}")
	public String userProductCoupon(HttpServletRequest req, ModelMap map, @PathVariable Long type) {
		String username = (String) req.getSession().getAttribute("username");
		TdUser user = tdUserService.findByUsernameAndIsEnableTrue(username);
		if (null == user) {
			return "redirect:/login";
		}

		if (0L == type) {
			// 获取所有未使用且未过期的现金券
			List<TdCoupon> no_product_unused_list = tdCouponService
					.findByUsernameAndIsUsedFalseAndIsOutDateFalseAndTypeCategoryIdNotOrderByGetTimeDesc(username, 3L,
							user.getCityName());
			map.addAttribute("no_product_unused_list", no_product_unused_list);
			// 获取最近一个月内已过期的现金券
			List<TdCoupon> no_product_out_date_list = tdCouponService
					.findByUsernameAndIsUsedFalseAndIsOutDateTrueAndTypeCategoryIdNotAndExpireTimeBetweenOrderByExpireTimeDesc(
							username, 3L, user.getCityName());
			map.addAttribute("no_product_out_date_list", no_product_out_date_list);
			// 获取最近一个月内已使用的现金券
			List<TdCoupon> no_product_used_list = tdCouponService
					.findByUsernameAndIsUsedTrueAndTypeCategoryIdNotAndUseTimeBetweenOrderByUseTimeDesc(username, 3L,
							user.getCityName());
			map.addAttribute("no_product_used_list", no_product_used_list);
			return "/client/user_cash_coupon";
		} else {
			// 获取所有未使用且未过期的产品券
			List<TdCoupon> product_unused_list = tdCouponService
					.findByUsernameAndIsUsedFalseAndIsOutDateFalseAndTypeCategoryIdOrderByGetTimeDesc(username, 3L,
							user.getCityName());
			map.addAttribute("product_unused_list", product_unused_list);
			// 获取最近一个月内已过期的产品券
			List<TdCoupon> product_out_date_list = tdCouponService
					.findByUsernameAndIsUsedFalseAndIsOutDateTrueAndTypeCategoryIdAndExpireTimeBetweenOrderByExpireTimeDesc(
							username, 3L, user.getCityName());
			map.addAttribute("product_out_date_list", product_out_date_list);
			// 获取最近一个月内已经使用的产品券
			List<TdCoupon> product_used_list = tdCouponService
					.findByUsernameAndIsUsedTrueAndTypeCategoryIdAndUseTimeBetweenOrderByUseTimeDesc(username, 3L);
			map.addAttribute("product_used_list", product_used_list);
			return "/client/user_product_coupon";
		}
	}

	/**
	 * 取消订单的方法
	 * 
	 * @author dengxiao
	 */
	@RequestMapping(value = "/order/cancel")
	@ResponseBody
	public Map<String, Object> userOrderCancel(Long orderId, HttpServletRequest req) {
		Map<String, Object> res = new HashMap<>();
		res.put("status", -1);

		// 查询登陆用户
		String username = (String) req.getSession().getAttribute("username");
		TdUser user = tdUserService.findByUsernameAndIsEnableTrue(username);
		if (null == user) {
			res.put("message", "未能成功获取到登陆用户的信息");
			return res;
		}

		// 查询到指定的订单
		TdOrder order = tdOrderService.findOne(orderId);
		Long originStatus = new Long(order.getStatusId());
		if (null != order.getStatusId()) {
			if (4L == order.getStatusId()) {
				res.put("message", "已出库的订单不能取消");
				return res;
			}
			if (order.getStatusId() != 2L && order.getStatusId() != 3) {
				res.put("message", "订单状态错误,刷新后提交");
				return res;
			}
			if (null != order.getBackOtherPay() && order.getBackOtherPay() != 0) {
				res.put("message", "已收款的订单不能够取消");
				return res;
			}

			if (null != order.getPosPay() && order.getPosPay() != 0) {
				res.put("message", "已收款的订单不能够取消");
				return res;
			}

			if (null != order.getCashPay() && order.getCashPay() != 0) {
				res.put("message", "已收款的订单不能够取消");
				return res;
			}
		}
		// 首先判断订单是不是运费单
		String orderNumber = order.getOrderNumber();
		String newOrderNumber = "";
		// 通过计算得到订单的数字部分
		for (int i = 0; i < orderNumber.length(); i++) {
			if (orderNumber.charAt(i) >= 48 && orderNumber.charAt(i) <= 57) {
				newOrderNumber += orderNumber.charAt(i);
			}
		}

		Long realUserId = order.getRealUserId();
		TdUser realUser = null;
		if (realUserId == null) {
			realUser = tdUserService.findByUsername(order.getUsername());
		} else {
			realUser = tdUserService.findOne(realUserId);
		}

		// 根据问题跟踪表-20160120第55号（序号），一个分单取消的时候，与其相关联的所有分单也取消掉
		List<TdOrder> list = tdOrderService.findByOrderNumberContaining(newOrderNumber);
		Double totalPrice = 0d;
		// 进行遍历操作
		if (null != list && list.size() > 0) {
			for (TdOrder subOrder : list) {
				if (null != subOrder) {
					totalPrice += subOrder.getTotalPrice();
					// 设置订单状态为取消状态，同时记录已退货属性
					Long statusId = subOrder.getStatusId();
					if (null != statusId && 3L == statusId.longValue()) {

						// 修改2016-09-12：只有配送单才增加库存
						// 增加库存
						tdDiySiteInventoryService.changeGoodsInventory(subOrder, 1L, req, "退货", true);

						// 通知物流
						TdReturnNote returnNote = tdCommonService.MakeReturnNote(subOrder, 0L, "");
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

		// // 获取第三方支付的金额（主单的金额）
		// Double otherPay = order.getOtherPay();
		// // 生成打款通知
		// // 根据退款方式和退货金额生成一个资金退还申请单据
		// TdCashReturnNote note = new TdCashReturnNote();
		// note.setCreateTime(new Date());
		// note.setMoney(otherPay);
		// note.setTypeId(order.getPayTypeId());
		// note.setTypeTitle(order.getPayTypeTitle());
		// note.setOrderNumber(order.getOrderNumber());
		// note.setMainOrderNumber(order.getMainOrderNumber());
		// note.setReturnNoteNumber(null);
		// note.setUserId(user.getId());
		// note.setUsername(user.getUsername());
		// note.setIsOperated(false);
		// tdCashReturnNoteService.save(note);

		order.setStatusId(7L);
		order.setCancelTime(new Date());
		order.setIsRefund(true);
		tdOrderService.save(order);

		if (!(null != order.getIsOnlinePay() && order.getIsOnlinePay()) && 3L == originStatus) {
			TdUser seller = tdUserService.findOne(order.getSellerId());
			// 2017-02-13 取消订单的时候增加信用额度
			tdUserService.repayCredit(CreditChangeType.CANCEL, seller, totalPrice, order.getMainOrderNumber());
		}

		res.put("status", 0);
		return res;

	}

	/**
	 * 用户确认收货的方法
	 * 
	 * @author dengxiao
	 */
	@RequestMapping(value = "/confirm/accipt")
	@ResponseBody
	public Map<String, Object> userConfirmAccipt(Long id) {
		Map<String, Object> res = new HashMap<>();
		res.put("status", -1);
		TdOrder order = tdOrderService.findOne(id);
		if (null == order) {
			res.put("message", "未查找到指定的订单");
			return res;
		}
		order.setStatusId(5L);
		tdOrderService.save(order);

		res.put("status", 0);
		return res;
	}

	@RequestMapping(value = "/can/return")
	@ResponseBody
	public Map<String, Object> returnCheck(HttpServletRequest req, ModelMap map, Long id) {
		Map<String, Object> res = new HashMap<>();
		res.put("status", -1);

		TdOrder order = tdOrderService.findOne(id);
		String mainOrderNumber = order.getMainOrderNumber();
		List<TdOwnMoneyRecord> ownList = tdOwnMoneyRecordService.findByOrderNumberAndIsOwnAndIsPayed(mainOrderNumber,
				true, false);
		if (null != ownList && ownList.size() > 0) {
			return res;
		}

		res.put("status", 0);
		return res;
	}

	/**
	 * 跳转到订单详情的方法 增加退货单信息 计算实付款zp
	 * 
	 * @author dengxiao
	 */
	@RequestMapping(value = "/order/detail/{id}")
	public String userOrderDetail(HttpServletRequest req, ModelMap map, @PathVariable Long id) {
		String username = (String) req.getSession().getAttribute("username");
		TdUser user = tdUserService.findByUsernameAndIsEnableTrue(username);
		if (null == user) {
			return "redirect:/login";
		}
		// 获取指定id的订单信息
		TdOrder order = tdOrderService.findOne(id);
		map.addAttribute("order", order);

		// 获取配送的信息
		if (null != order) {
			String orderNumber = order.getOrderNumber();
			List<TdDeliveryInfoDetail> delivery_list = tdDeliveryInfoDetailService.findBySubOrderNumber(orderNumber);
			if (null != delivery_list && delivery_list.size() > 0) {
				List<TdDeliveryInfo> deliveryInfo = tdDeliveryInfoService
						.findDistinctTaskNoByTaskNo(delivery_list.get(0).getTaskNo());
				if (null != deliveryInfo && deliveryInfo.size() > 0) {
					TdDeliveryInfo info = deliveryInfo.get(0);
					if (null != info) {
						// 查找配送员的一系列信息
						TdUser opUser = tdUserService.findByOpUser(info.getDriver());
						map.addAttribute("opUser", opUser);
						List<TdGeoInfo> geoInfo_list = tdGeoInfoService.findByOpUserOrderByTimeDesc(info.getDriver());
						TdUser tdUser = tdUserService.findByOpUser(info.getDriver());
						if (null != geoInfo_list && geoInfo_list.size() > 0) {
							TdGeoInfo geoInfo = geoInfo_list.get(0);
							map.addAttribute("geoInfo", geoInfo);
							map.addAttribute("tdUser", tdUser);
						}
					}
				}
			}
		}
		// 仓库
		if (null != order) {
			List<TdDeliveryInfo> deliveryList = tdDeliveryInfoService
					.findByOrderNumberOrderByBeginDtDesc(order.getMainOrderNumber());
			if (null != deliveryList && deliveryList.size() > 0) {
				List<TdWareHouse> wareHouseList = TdWareHouseService
						.findBywhNumberOrderBySortIdAsc(deliveryList.get(0).getWhNo());
				if (null != wareHouseList && wareHouseList.size() > 0) {
					map.addAttribute("tdWareHouse", wareHouseList.get(0));
				}
			}
		}
		// 退货单信息
		if (order != null && (order.getStatusId() == 9 || order.getStatusId() == 10)) {
			List<TdReturnNote> returnNoteList = tdReturnNoteService.findByOrderNumberContaining(order.getOrderNumber());
			if (returnNoteList != null && returnNoteList.size() > 0) {
				map.addAttribute("returnNote", returnNoteList.get(0));
			}
		}

		// 代付款状态的订单 使用产品劵只记录了产品劵id
		String productCouponIds = order.getProductCouponId();
		// 判断不为空
		if (StringUtils.isNotBlank(productCouponIds)) {
			String couponTitles = "";
			String[] coupons = productCouponIds.split(",");
			for (String couponStrId : coupons) {
				Long couponId = Long.parseLong(couponStrId);
				TdCoupon coupon = tdCouponService.findOne(couponId);
				if (coupon != null && StringUtils.isNotBlank(coupon.getTypeTitle())) {
					couponTitles += coupon.getTypeTitle() + ", ";
				}
			}
			map.addAttribute("couponTitles", couponTitles);
		}

		// 计算实付款
		Double totolPayment = 0.0;
		// 预存款
		if (order.getActualPay() != null) {
			totolPayment += order.getActualPay();
		}
		// 第三方支付
		if (order.getOtherPay() != null) {
			totolPayment += order.getOtherPay();
		}
		// 支付现金
		if (order.getCashPay() != null) {
			totolPayment += order.getCashPay();
		}
		// 支付POS
		if (order.getPosPay() != null) {
			totolPayment += order.getPosPay();
		}
		// 门店其他
		if (order.getBackOtherPay() != null) {
			totolPayment += order.getBackOtherPay();
		}
		// 保存实付款
		map.addAttribute("totolPayment", totolPayment);

		map.addAttribute("orderId", id);
		return "/client/user_order_detail";
	}

	@RequestMapping("/order/map")
	public String showTheSender(ModelMap map, HttpServletRequest request, Long oid) {
		String username = (String) request.getSession().getAttribute("username");
		TdUser user = tdUserService.findByUsernameAndIsEnableTrue(username);
		if (null == user) {
			return "redirect:/login";
		}
		TdOrder order = null;
		TdUser tdUser = null;
		if (oid != null) {
			order = tdOrderService.findOne(oid);
		}
		if (order != null && order.getMainOrderNumber() != null) {
			List<TdDeliveryInfo> deliveryInfos = tdDeliveryInfoService
					.findByOrderNumberOrderByBeginDtDesc(order.getMainOrderNumber());
			if (deliveryInfos != null && deliveryInfos.size() > 0) {
				TdDeliveryInfo deliveryInfo = deliveryInfos.get(0);
				if (deliveryInfo != null && deliveryInfo.getDriver() != null) {
					tdUser = tdUserService.findByOpUser(deliveryInfo.getDriver());
					map.addAttribute("user", tdUser);
				}
				if (deliveryInfo != null && deliveryInfo.getWhNo() != null) {
					List<TdWareHouse> wareHouseList = TdWareHouseService
							.findBywhNumberOrderBySortIdAsc(deliveryInfo.getWhNo());
					if (wareHouseList != null && wareHouseList.size() > 0) {
						map.addAttribute("whName", wareHouseList.get(0).getWhName());
					}
				}
			}
		}
		if (tdUser != null) {
			List<TdGeoInfo> geoInfos = tdGeoInfoService.findByOpUserOrderByTimeDesc(tdUser.getOpUser());
			if (geoInfos != null && geoInfos.size() > 0) {
				map.addAttribute("map_x", geoInfos.get(geoInfos.size() - 1).getLatitude());
				map.addAttribute("map_y", geoInfos.get(geoInfos.size() - 1).getLongitude());
			}
		}
		return "/client/user_order_detail_map";
	}

	/**
	 * 用户修改归属门店并且保存的方法 修改门店后清空导购 zp
	 * 
	 * @author dengxiao
	 */
	@RequestMapping(value = "/diy/save")
	@ResponseBody
	public Map<String, Object> userDiySave(HttpServletRequest req, Long id) {
		Map<String, Object> res = new HashMap<>();
		res.put("status", -1);
		// 获取登陆用户的信息
		String username = (String) req.getSession().getAttribute("username");
		TdUser user = tdUserService.findByUsernameAndIsEnableTrue(username);
		if (null == user) {
			res.put("message", "未获取到登陆用户的信息，操作失败");
			return res;
		}

		// 获取指定id的门店信息
		TdDiySite site = tdDiySiteService.findOne(id);
		if (null == site) {
			res.put("message", "未获取到指定门店的信息，操作失败");
			return res;
		}

		user.setUpperDiySiteId(site.getId());
		user.setDiyName(site.getTitle());
		user.setCustomerId(site.getCustomerId());
		// 修改门店后清空导购
		user.setSellerId(null);
		user.setSellerName(null);
		user.setReferPhone(null);

		tdUserService.save(user);
		res.put("status", 0);
		res.put("user", user);
		return res;
	}

	/**
	 * 跳转到提现页面的方法
	 * 
	 * @author dengxiao
	 */
	@RequestMapping(value = "/deposit")
	public String userDeposit(HttpServletRequest req, ModelMap map) {
		String username = (String) req.getSession().getAttribute("username");
		TdUser user = tdUserService.findByUsernameAndIsEnableTrue(username);
		if (null == user) {
			return "redirect:/login";
		}
		return "/client/user_deposit";
	}

	/**
	 * 跳转到优惠券使用说明的方法
	 * 
	 * @author dengxiao
	 */
	@RequestMapping(value = "/coupon/description")
	public String userCouponDescription(HttpServletRequest req, ModelMap map) {
		String username = (String) req.getSession().getAttribute("username");
		TdUser user = tdUserService.findByUsernameAndIsEnableTrue(username);
		if (null == user) {
			return "redirect:/login";
		}

		// 获取优惠券使用说明的文章分类
		TdArticleCategory category = tdArticleCategoryService.findByTitle("优惠券使用规则");
		// 获取指定的文章类别id获取所有的文章，按照排序号正序排序
		if (null != category) {
			List<TdArticle> article_list = tdArticleService.findByCategoryIdOrderBySortIdAsc(category.getId());
			if (null != article_list && article_list.size() > 0) {
				map.addAttribute("article", article_list.get(0));
			}
		}

		return "/client/coupon_description";
	}

	/**
	 * 删除订单的方法
	 * 
	 * @author dengxiao
	 */
	@RequestMapping(value = "/order/delete")
	@ResponseBody
	public Map<String, Object> orderDelete(Long orderId) {
		Map<String, Object> res = new HashMap<>();
		TdOrder order = tdOrderService.findOne(orderId);
		order.setStatusId(8L);
		tdOrderService.save(order);
		res.put("status", 0);
		return res;
	}

	/**
	 * 申请退货
	 * 
	 * @author Max
	 */
	@RequestMapping(value = "/order/return", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> orderReturn(Long id, String remark, Long turnType, HttpServletRequest req) {
		Map<String, Object> res = new HashMap<>();
		res.put("code", 1);

		String username = (String) req.getSession().getAttribute("username");

		if (null == username) {
			res.put("message", "请重新登录");
			return res;
		}

		if (null != id) {
			TdOrder order = tdOrderService.findOne(id);

			if (null != order && order.getStatusId() != null && order.getStatusId() != 9L) {
				TdReturnNote returnNote = new TdReturnNote();

				// 退货单编号
				Date current = new Date();
				SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmm");
				String curStr = sdf.format(current);
				Random random = new Random();

				returnNote.setReturnNumber("T" + curStr + leftPad(Integer.toString(random.nextInt(999)), 3, "0"));

				// 添加订单信息
				returnNote.setOrderNumber(order.getOrderNumber());

				// add MDJ
				returnNote.setShoppingAddress(order.getShippingAddress());
				returnNote.setSellerRealName(order.getSellerRealName());
				// end add MDJ

				// 支付方式
				returnNote.setPayTypeId(order.getPayTypeId());
				returnNote.setPayTypeTitle(order.getPayTypeTitle());
				// 门店信息
				if (null != order.getDiySiteId()) {
					TdDiySite diySite = tdDiySiteService.findOne(order.getDiySiteId());
					returnNote.setDiySiteId(order.getDiySiteId());
					returnNote.setDiyCode(diySite.getStoreCode());
					returnNote.setDiySiteTel(diySite.getServiceTele());
					returnNote.setDiySiteTitle(diySite.getTitle());
					returnNote.setDiySiteAddress(diySite.getAddress());
				}

				// 退货信息
				returnNote.setUsername(username);
				returnNote.setRemarkInfo(remark);

				// 退货方式
				if ("门店自提".equals(order.getDeliverTypeTitle())) {
					returnNote.setTurnType(1L);
					turnType = 1L;
				} else {
					returnNote.setTurnType(2L);
					turnType = 2L;
				}
				// returnNote.setTurnType(turnType);
				// 原订单配送方式
				if ("门店自提".equals(order.getDeliverTypeTitle())) {
					returnNote.setStatusId(2L); // 门店自提单-门店到店退货 待验货
				} else {
					returnNote.setStatusId(1L); // 送货上门单 物流取货 待取货
				}

				returnNote.setDeliverTypeTitle(order.getDeliverTypeTitle());
				returnNote.setOrderTime(new Date());

				returnNote.setTurnPrice(order.getTotalGoodsPrice());
				List<TdOrderGoods> orderGoodsList = new ArrayList<>();
				if (null != order.getOrderGoodsList()) {
					for (TdOrderGoods oGoods : order.getOrderGoodsList()) {
						TdOrderGoods orderGoods = new TdOrderGoods();

						orderGoods.setBrandId(oGoods.getBrandId());
						orderGoods.setBrandTitle(oGoods.getBrandTitle());
						orderGoods.setGoodsId(oGoods.getGoodsId());
						orderGoods.setGoodsSubTitle(oGoods.getGoodsSubTitle());
						orderGoods.setSku(oGoods.getSku());
						orderGoods.setGoodsCoverImageUri(oGoods.getGoodsCoverImageUri());
						orderGoods.setGoodsColor(oGoods.getGoodsColor());
						orderGoods.setGoodsCapacity(oGoods.getGoodsCapacity());
						orderGoods.setGoodsVersion(oGoods.getGoodsVersion());
						orderGoods.setGoodsSaleType(oGoods.getGoodsSaleType());
						orderGoods.setGoodsTitle(oGoods.getGoodsTitle());

						orderGoods.setPrice(oGoods.getPrice());
						orderGoods.setQuantity(oGoods.getQuantity());

						orderGoods.setDeliveredQuantity(oGoods.getDeliveredQuantity());
						orderGoods.setPoints(oGoods.getPoints());
						// tdOrderGoodsService.save(orderGoods);
						// 添加商品信息
						orderGoodsList.add(orderGoods);

						// 订单商品设置退货为True
						oGoods.setIsReturnApplied(true);
						// 更新订单商品信息是否退货状态
						tdOrderGoodsService.save(oGoods);
					}
				}

				returnNote.setReturnGoodsList(orderGoodsList);
				tdOrderGoodsService.save(orderGoodsList);
				// 保存退货单
				tdReturnNoteService.save(returnNote);

				tdInterfaceService.initReturnOrder(returnNote, INFConstants.INF_RETURN_ORDER_SUB_INT);
				tdInterfaceService.initReturnCouponInfByOrder(order, INFConstants.INF_RETURN_ORDER_SUB_INT);

				order.setStatusId(9L);
				order.setIsRefund(true);
				tdOrderService.save(order);

				res.put("code", 0);
				res.put("message", "提交退货成功");
				return res;
			}
		}

		res.put("message", "参数错误");

		return res;

	}

	/**
	 * 跳转到退货界面的控制器
	 * 
	 * @author DengXiao
	 */
	@RequestMapping(value = "/order/return")
	public String userOrderReturn(HttpServletRequest req, ModelMap map, Long orderId) {
		String username = (String) req.getSession().getAttribute("username");
		TdUser user = tdUserService.findByUsernameAndIsEnableTrue(username);
		if (null == user) {
			return "redirect:/login";
		}

		// 查询到指定id的订单
		TdOrder order = tdOrderService.findOne(orderId);

		// 获取退货单价
		Map<Long, Double> returnUnitPrice = tdPriceCountService.getReturnUnitPrice(order);

		List<TdOrderGoods> all_goods = new ArrayList<>();

		if (null != order && null != order.getOrderGoodsList()) {
			all_goods.addAll(order.getOrderGoodsList());
		}

		if (null != order && null != order.getPresentedList()) {
			all_goods.addAll(order.getPresentedList());
		}

		if (null != returnUnitPrice && null != all_goods && all_goods.size() > 0) {
			for (TdOrderGoods goods : all_goods) {
				if (null != goods) {
					Long goodsId = goods.getGoodsId();
					if (null != goodsId) {
						Double unit = returnUnitPrice.get(goodsId);
						map.addAttribute("unit" + goodsId, unit);
						Double lsPrice = 0d;
						if (goods.getGiftPrice() != null) {
							lsPrice = goods.getGiftPrice();
						} else {
							lsPrice = goods.getPrice();
						}
						map.addAttribute("price" + goodsId, lsPrice);
					}
				}
			}
		}
		map.addAttribute("order", order);
		map.addAttribute("all_goods", all_goods);

		TdSetting setting = tdSettingService.findTopBy();
		map.addAttribute("telphone", setting.getTelephone());

		// 查找所有的退货原因
		List<TdReturnReason> reason_list = tdReturnReasonService.findAll();
		map.addAttribute("reason_list", reason_list);
		return "/client/user_return";
	}

	@RequestMapping(value = "/return/check")
	@ResponseBody
	public Map<String, Object> userReturnCheck(Long orderId, String infos, Long turnType, String remark) {
		Map<String, Object> res = new HashMap<>();
		res.put("status", -1);
		// 根据订单号查找订单
		TdOrder order = tdOrderService.findOne(orderId);
		// 判断订单是否已经退货
		if (null != order && !(null != order.getIsRefund() && order.getIsRefund())) {
			TdReturnNote returnNote = new TdReturnNote();

			// 退货单编号
			Date current = new Date();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmm");
			String curStr = sdf.format(current);
			Random random = new Random();

			returnNote.setReturnNumber("T" + curStr + leftPad(Integer.toString(random.nextInt(999)), 3, "0"));

			// 添加订单信息
			returnNote.setOrderNumber(order.getOrderNumber());

			// add MDJ
			returnNote.setShoppingAddress(order.getShippingAddress());
			returnNote.setSellerRealName(order.getSellerRealName());
			// end add MDJ

			// 支付方式
			returnNote.setPayTypeId(order.getPayTypeId());
			returnNote.setPayTypeTitle(order.getPayTypeTitle());
			// 门店信息
			if (null != order.getDiySiteId()) {
				TdDiySite diySite = tdDiySiteService.findOne(order.getDiySiteId());
				returnNote.setDiySiteId(order.getDiySiteId());
				returnNote.setDiyCode(diySite.getStoreCode());
				returnNote.setDiySiteTel(diySite.getServiceTele());
				returnNote.setDiySiteTitle(diySite.getTitle());
				returnNote.setDiySiteAddress(diySite.getAddress());
			}

			// 退货信息
			returnNote.setUsername(order.getUsername());
			returnNote.setRemarkInfo(remark);

			// 退货方式
			if ("门店自提".equals(order.getDeliverTypeTitle())) {
				returnNote.setTurnType(1L);
				turnType = 1L;
			} else {
				returnNote.setTurnType(2L);
				turnType = 2L;
			}
			// returnNote.setTurnType(turnType);
			// 原订单配送方式
			if ("门店自提".equals(order.getDeliverTypeTitle())) {
				returnNote.setStatusId(2L); // 门店自提单-门店到店退货 待验货
			} else {
				returnNote.setStatusId(1L); // 送货上门单 物流取货 待取货
			}

			returnNote.setDeliverTypeTitle(order.getDeliverTypeTitle());
			returnNote.setOrderTime(new Date());

			Double totalGoodsPrice = 0.00;
			List<TdOrderGoods> orderGoodsList = new ArrayList<>();
			// 开始解析infos字符串
			if (null != infos) {
				String[] singles = infos.split(",");
				if (null != singles && singles.length > 0) {
					for (String single : singles) {
						if (null != single && !"".equals(single)) {
							String[] param = single.split("-");
							if (null != param && param.length == 4) {
								String sId = param[0];
								Long goodsId = null;
								String sQuantity = param[1];
								Long quantity = 0L;
								String sUnit = param[2];
								Double unit = 0.00;
								String sPrice = param[3];
								Double price = 0.00;

								if (null != sQuantity && !"".equals(sQuantity)) {
									quantity = Long.parseLong(sQuantity);
								}
								if (null != sUnit && !"".equals(sUnit)) {
									unit = Double.parseDouble(sUnit);
								}
								if (null != sPrice && !"".equals(sPrice)) {
									price = Double.parseDouble(sPrice);
								}
								if (null != sId) {
									goodsId = Long.parseLong(sId);
									TdGoods oGoods = tdGoodsService.findOne(goodsId);
									if (null != oGoods) {
										TdOrderGoods orderGoods = new TdOrderGoods();
										orderGoods.setBrandId(oGoods.getBrandId());
										orderGoods.setBrandTitle(oGoods.getBrandTitle());
										orderGoods.setGoodsId(oGoods.getId());
										orderGoods.setGoodsSubTitle(oGoods.getSubTitle());
										orderGoods.setSku(oGoods.getCode());
										orderGoods.setGoodsCoverImageUri(oGoods.getCoverImageUri());
										orderGoods.setGoodsTitle(oGoods.getTitle());
										orderGoods.setPrice(price);
										orderGoods.setQuantity(quantity);
										orderGoods.setReturnNoteNumber(returnNote.getReturnNumber());
										orderGoods.setSubOrderNumber(order.getOrderNumber());
										// 如果退货单价为0，则判断是否使用了电子产品券
										if (0.00 == unit.doubleValue()) {
											String orderNumber = returnNote.getOrderNumber();
											if (orderNumber.contains("HR")) {
												orderNumber = orderNumber.replace("HR", "XN");
											} else if (orderNumber.contains("YR")) {
												orderNumber = orderNumber.replace("YR", "XN");
											} else if (orderNumber.contains("LYZ")) {
												orderNumber = orderNumber.replace("LYZ", "XN");
											}
											List<TdCoupon> coupons = tdCouponService
													.findByOrderNumberAndSkuAndIsUsedTrueAndIsBuyTrue(orderNumber,
															orderGoods.getSku());
											if (null != coupons && coupons.size() > 0) {
												if (null == coupons.get(0).getBuyPrice()) {
													unit = 0.00;
												} else {
													unit = coupons.get(0).getBuyPrice();
												}
											}
										}
										orderGoods.setReturnUnitPrice(unit);
										// orderGoods.setDeliveredQuantity(oGoods.getDeliveredQuantity());
										// orderGoods.setPoints(oGoods.getPoints());
										// tdOrderGoodsService.save(orderGoods);
										// 添加商品信息
										orderGoodsList.add(orderGoods);

										// 订单商品设置退货为True
										// oGoods.setIsReturnApplied(true);
										// 更新订单商品信息是否退货状态
										tdOrderGoodsService.save(orderGoods);
										totalGoodsPrice += (unit * quantity);
									}
								}
							}
						}
					}
				}
			}

			// 获取订单的总价
			Double main_order_goods_price = order.getTotalGoodsPrice();
			if (null == main_order_goods_price) {
				main_order_goods_price = 0.00;
			}

			if (totalGoodsPrice > main_order_goods_price) {
				totalGoodsPrice = main_order_goods_price;
			}

			returnNote.setTurnPrice(totalGoodsPrice);
			returnNote.setReturnGoodsList(orderGoodsList);
			order.setStatusId(9L);
			order.setIsRefund(true);

			// 2016-07-20存储退钱退券明细信息
			returnNote = this.getReturnDetail(orderId, infos, returnNote);

			tdOrderService.save(order);
			tdReturnNoteService.save(returnNote);
			tdInterfaceService.initReturnOrder(returnNote, INFConstants.INF_RETURN_ORDER_SUB_INT);
			tdInterfaceService.initReturnCouponInfByOrder(order, INFConstants.INF_RETURN_ORDER_SUB_INT);
			tdInterfaceService.sendReturnOrderByAsyn(returnNote);
			// tdCommonService.sendBackToWMS(returnNote);
		}

		res.put("status", 0);
		return res;
	}

	/**
	 * 根据已选查看赠品和小辅料的方法
	 * 
	 * @author DengXiao
	 */
	@RequestMapping(value = "/show/gift")
	public String showGift(HttpServletRequest req, ModelMap map) {

		// 创建一个虚拟订单
		TdOrder order = new TdOrder();
		// 获取登录的用户
		String username = (String) req.getSession().getAttribute("username");
		// 获取用户所有的已选商品
		List<TdCartGoods> all_selected = tdCartGoodsService.findByUsername(username);

		order = tdCommonService.getPresent(req, order);

		// 获取促销赠品
		map.addAttribute("present", order.getPresentedList());

		// 获取小辅料赠品
		List<TdOrderGoods> gift = this.getGift(req, all_selected);
		map.addAttribute("gift", gift);

		return "/client/gift_list";
	}

	/**
	 * 根据已选获取赠品的方法 记录活动编号
	 * 
	 * @author DengXiao
	 */
	public List<TdOrderGoods> getPresent(List<TdCartGoods> all_selected, HttpServletRequest req) {
		List<TdOrderGoods> presentList = new ArrayList<>();
		// 为了避免脏数据刷新，创建一个map用于存储已选【id：数量】
		Map<Long, Long> selected_map = new HashMap<>();

		Long giftType = 0L;

		for (TdCartGoods cartGoods : all_selected) {
			Long id = cartGoods.getGoodsId();
			Long quantity = cartGoods.getQuantity();
			Boolean isCoupon = cartGoods.getIsCoupon();
			if (null != isCoupon && isCoupon) {
				giftType = 1L;
			}
			selected_map.put(id, quantity);
		}

		// 获取用户的门店
		TdDiySite diySite = tdCommonService.getDiySite(req);
		// 获取用户门店所能参加的活动
		List<TdActivity> activity_list = tdActivityService
				.findByDiySiteIdsContainingAndBeginDateBeforeAndFinishDateAfterAndGiftTypeOrderBySortIdAsc(
						diySite.getId() + "", new Date(), giftType);
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
			// 创建一个存储顺序的集合
			List<Long> sortList = new ArrayList<>();
			// --------------------------------------------------------------------------------------------

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
						// 获取倍数关系
						for (Long goodsId : cost.keySet()) {
							Long quantity = cost.get(goodsId);
							Long goods_quantity = selected_map.get(goodsId);
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
						Long min = Collections.min(mutipuls);

						// 改变剩下的商品的数量
						for (Long goodsId : cost.keySet()) {
							Long quantity = cost.get(goodsId);
							Long leftNum = selected_map.get(goodsId) - (quantity * min);
							selected_map.put(goodsId, leftNum);
						}

						if (isFloat) {
							floatCount = floatCount * min;
							for (Long id : sortList) {
								// 获取指定商品剩余的数量
								Long leftNumber = selected_map.get(id);
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
											TdPriceListItem priceListItem = tdCommonService.getGoodsPrice(req, goods);
											TdOrderGoods orderGoods = new TdOrderGoods();
											orderGoods.setBrandId(goods.getBrandId());
											orderGoods.setBrandTitle(goods.getBrandTitle());
											orderGoods.setGoodsCoverImageUri(goods.getCoverImageUri());
											orderGoods.setGoodsId(goods.getId());
											orderGoods.setGoodsTitle(goods.getTitle());
											orderGoods.setGoodsSubTitle(goods.getSubTitle());
											orderGoods.setPrice(0.0);
											if (null == priceListItem) {
												orderGoods.setGiftPrice(0.00);
											} else {
												orderGoods.setGiftPrice(priceListItem.getPrice());
											}
											orderGoods.setQuantity(quantity * min);
											orderGoods.setSku(goods.getCode());
											// 记录活动id
											orderGoods.setActivityId(activity.getId().toString());
											// 创建一个布尔变量用于表示赠品是否已经在队列中
											Boolean isHave = false;
											for (TdOrderGoods single : presentList) {
												if (null != single && null != single.getGoodsId()
														&& single.getGoodsId() == orderGoods.getGoodsId()) {
													isHave = true;
													single.setQuantity(single.getQuantity() + orderGoods.getQuantity());
													// 记录活动id
													single.setActivityId(orderGoods.getActivityId() + ","
															+ activity.getId().toString());
												}
											}

											if (!isHave) {
												presentList.add(orderGoods);
											}
										}
									}
								}
							}
						}
					}
				}
			}
		}
		return presentList;
	}

	/**
	 * 根据已选获取小辅料 记录活动id 小辅料活动暂时不记录 zp
	 * 
	 * @author DengXiao
	 */
	public List<TdOrderGoods> getGift(HttpServletRequest req, List<TdCartGoods> all_selected) {
		List<TdOrderGoods> giftGoodsList = new ArrayList<>();

		Long giftType = 0L;
		for (TdOrderGoods goods : giftGoodsList) {
			if (null != goods && null != goods.getIsCoupon() && goods.getIsCoupon()) {
				giftType = 1L;
				break;
			}
		}

		// 获取已选【分类：数量】组
		Map<Long, Long> group = tdCommonService.getGroup(req);
		// 获取已选能够参加的活动
		List<TdActivityGift> activities = tdCommonService.getActivityGiftBySelected(req, giftType);
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
						// //记录活动id
						// goods.setActivityId(activity.getId().toString());
						// 创建一个布尔变量用于判断此件商品是否已经加入了小辅料
						Boolean isHave = false;
						for (TdOrderGoods orderGoods : giftGoodsList) {
							if (null != orderGoods && null != orderGoods.getGoodsId()
									&& orderGoods.getGoodsId().longValue() == gift.getGoodsId().longValue()) {
								isHave = true;
								orderGoods.setQuantity(orderGoods.getQuantity() + goods.getQuantity());
								// //记录活动id
								// orderGoods.setActivityId(orderGoods.getActivityId()+","+activity.getId().toString());
							}
						}
						if (!isHave) {
							giftGoodsList.add(goods);
						}
						// 消减数量
						group.put(categoryId, group.get(categoryId) - quantity);
					}
				}
			}
		}
		return giftGoodsList;
	}

	@RequestMapping(value = "/seller/get")
	public String userSellerGet(HttpServletRequest req, ModelMap map, Long diyId) {
		TdDiySite diySite = tdDiySiteService.findOne(diyId);
		if (null != diySite) {
			List<TdUser> user_list = tdUserService.findByCityIdAndCustomerIdAndUserTypeOrCityIdAndCustomerIdAndUserType(
					diySite.getRegionId(), diySite.getCustomerId());
			map.addAttribute("user_list", user_list);
		}
		return "/client/user_seller_info";
	}

	@RequestMapping(value = "/seller/search")
	public String userSellerSearch(HttpServletRequest req, ModelMap map, Long diyId, String keywords) {
		TdDiySite diySite = tdDiySiteService.findOne(diyId);
		if (null != diySite) {
			List<TdUser> user_list = tdUserService
					.findByCustomerIdAndCityIdAndUserTypeAndUsernameContainingOrCustomerIdAndCityIdAndUserTypeAndRealNameContainingOrCustomerIdAndCityIdAndUserTypeAndUsernameContainingOrCustomerIdAndCityIdAndUserTypeAndRealNameContainingOrderBySortIdAsc(
							diySite.getCustomerId(), diySite.getRegionId(), keywords);
			map.addAttribute("user_list", user_list);
		}
		return "/client/user_seller_info";
	}

	@RequestMapping(value = "/seller/select")
	@ResponseBody
	public Map<String, Object> userSellerSelect(HttpServletRequest req, ModelMap map, Long sellerId) {
		Map<String, Object> res = new HashMap<>();
		res.put("status", -1);
		// 获取指定的销顾
		TdUser seller = tdUserService.findOne(sellerId);
		if (null != seller) {
			// 获取当前登录用户
			String username = (String) req.getSession().getAttribute("username");
			TdUser user = tdUserService.findByUsername(username);
			if (null != user) {
				user.setSellerId(seller.getId());
				user.setSellerName(seller.getRealName());
				user.setReferPhone(seller.getUsername());
				tdUserService.save(user);

				res.put("name", seller.getRealName());
			} else {
				res.put("message", "未能成功获取到登录用户的信息");
				return res;
			}
		} else {
			res.put("message", "未能成功获取到导购信息");
			return res;
		}
		res.put("status", 0);
		return res;
	}

	/**
	 * 退换货
	 * 
	 * @author zp
	 */
	@RequestMapping(value = "/return/list")
	public String returnList(HttpServletRequest req, ModelMap map, Integer page, Integer size) {
		String username = (String) req.getSession().getAttribute("username");
		TdUser user = tdUserService.findByUsernameAndIsEnableTrue(username);
		if (null == user) {
			return "redirect:/login";
		}
		// 设置默认值
		if (null == page || page <= 0) {
			page = 0;
		}
		if (null == size || size <= 0) {
			size = 20;
		}

		// 分页查询退货单
		Page<TdReturnNote> returnNoteList = tdReturnNoteService.findByUsername(username, "用户取消订单，退货", page, size);
		// List<TdReturnNote>
		// returnNoteList=tdReturnNoteService.findByUsername(username);

		map.addAttribute("all_return_list", returnNoteList.getContent());
		return "/client/user_return_list";
	}

	/**
	 * 退货单详情
	 * 
	 * @author zp
	 */
	@RequestMapping(value = "/return/detail/{id}")
	public String userReturnDetail(HttpServletRequest req, ModelMap map, @PathVariable Long id) {
		String username = (String) req.getSession().getAttribute("username");
		TdUser user = tdUserService.findByUsernameAndIsEnableTrue(username);
		if (null == user) {
			return "redirect:/login";
		}
		// 获取指定id的退货单信息
		TdReturnNote returnNote = tdReturnNoteService.findOne(id);
		map.addAttribute("returnNote", returnNote);

		// 获取配送的信息
		if (null != returnNote) {
			// 查找配送员的一系列信息
			TdUser opUser = tdUserService.findByOpUser(returnNote.getDriver());
			map.addAttribute("opUser", opUser);
			List<TdGeoInfo> geoInfo_list = tdGeoInfoService.findByOpUserOrderByTimeDesc(returnNote.getDriver());
			TdUser tdUser = tdUserService.findByOpUser(returnNote.getDriver());
			if (null != geoInfo_list && geoInfo_list.size() > 0) {
				TdGeoInfo geoInfo = geoInfo_list.get(0);
				map.addAttribute("geoInfo", geoInfo);
				map.addAttribute("tdUser", tdUser);
			}
		}
		// 获取导购 可能不准确 真是姓名可能重复
		if (null != returnNote) {
			List<TdUser> userList = tdUserService.findByRealName(returnNote.getSellerRealName());
			if (userList != null && userList.size() > 0) {
				map.addAttribute("sellerUser", userList.get(0));
			}
		}

		// 退货单仓库暂时没有记录 后面增加
		// // 仓库
		// if (null != returnNote) {
		// List<TdDeliveryInfo> deliveryList = tdDeliveryInfoService
		// .findByOrderNumberOrderByBeginDtDesc(order.getMainOrderNumber());
		// if (null != deliveryList && deliveryList.size() > 0) {
		// List<TdWareHouse> wareHouseList = TdWareHouseService
		// .findBywhNumberOrderBySortIdAsc(deliveryList.get(0).getWhNo());
		// if (null != wareHouseList && wareHouseList.size() > 0) {
		// map.addAttribute("tdWareHouse", wareHouseList.get(0));
		// }
		// }
		// }

		map.addAttribute("returnId", id);
		return "/client/user_return_detail";
	}

	/**
	 * 模糊查询指定退货单的方法（异步刷新）
	 * 
	 * @author zp
	 */
	@RequestMapping(value = "/return/search")
	public String userReturnSearch(HttpServletRequest req, ModelMap map, String keywords) {
		String username = (String) req.getSession().getAttribute("username");
		TdUser user = tdUserService.findByUsernameAndIsEnableTrue(username);
		if (null != user) {
			List<TdReturnNote> returnList = tdReturnNoteService
					.findByReturnNumberContainingOrorderNumberContaining(keywords, "用户取消订单，退货", username);
			map.addAttribute("all_return_list", returnList);
		}
		return "/client/user_all_return";
	}

	@RequestMapping(value = "/create/recharge", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> createRecharge(HttpServletRequest req, Double money, String title) {
		Map<String, Object> res = new HashMap<>();
		res.put("status", -1);

		String username = (String) req.getSession().getAttribute("username");
		TdUser user = tdUserService.findByUsername(username);
		if (null == user) {
			res.put("message", "未成功获取到登录用户的信息");
			return res;
		}

		if (null == money) {
			res.put("message", "未成功获取到充值金额信息");
			return res;
		}

		if (null == title) {
			res.put("message", "未成功获取到充值方式信息");
			return res;
		}

		TdPayType payType = tdPayTypeService.findByTitleAndIsEnableTrue(title);
		if (null == payType) {
			res.put("message", "未成功获取到充值方式信息");
			return res;
		}

		// 生成充值单号
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmssSSS");
		Date now = new Date();
		String sDate = sdf.format(now);
		Random random = new Random();
		Integer suiji = random.nextInt(900) + 100;
		String orderNum = sDate + suiji;

		// 生成充值单
		TdRecharge recharge = new TdRecharge();
		recharge.setNumber("CZ" + orderNum);
		recharge.setUserId(user.getId());
		recharge.setUsername(user.getUsername());
		recharge.setCreateTime(new Date());
		recharge.setTotalPrice(money);
		recharge.setTypeId(payType.getId());
		recharge.setTypeTitle(payType.getTitle());
		recharge.setStatusId(1L);
		tdReChargeService.save(recharge);

		res.put("number", recharge.getNumber());

		res.put("status", 0);
		return res;
	}

	@RequestMapping(value = "/return/detail/get", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> userReturnDetailGet(HttpServletRequest req, ModelMap map, Long orderId, String params) {
		Map<String, Object> res = new HashMap<>();

		List<String> infos = new ArrayList<>();

		TdOrder order = tdOrderService.findOne(orderId);
		if (null != order && null != params && !"".equals(params)) {
			Map<String, Object> result = tdPriceCountService.countCouponCondition(orderId);

			Boolean useProCoupon = (Boolean) result.get("useProCoupon");
			Boolean useCashCoupon = (Boolean) result.get("useCashCoupon");

			Double cashTotal = (Double) result.get("cashTotal");
			// 获取订单使用的总不可提现余额
			Double unCashBalanceUsed = order.getUnCashBalanceUsed();
			// 获取用户使用的可提现余额
			Double cashBalanceUsed = order.getCashBalanceUsed();
			// 获取用户第三方支付的金额
			Double otherPay = order.getOtherPay();
			// 获取用户支付的现金
			Double cashPay = order.getCashPay();
			// 获取其他支付方式的金额
			Double backOtherPay = order.getBackOtherPay();
			if (null == cashPay) {
				cashPay = 0.00;
			}
			// 获取用户支付的POS
			Double posPay = order.getPosPay();
			if (null == posPay) {
				posPay = 0.00;
			}
			if (null == backOtherPay) {
				backOtherPay = 0.00;
			}

			// Double all_off_line = posPay + cashPay + backOtherPay;

			// 2016-07-05修改：以现金的方式归还第三方支付的钱，POS和现金
			Double all_cash_return = 0.00;

			Map<Long, Double> price_difference = new HashMap<>();

			// 2016-06-26修改：需要获取用户使用赠送的产品券和购买的产品券的集合
			Map<Long, ArrayList<TdCoupon>> buy_pro_coupon_condition = new HashMap<>();
			Map<Long, ArrayList<TdCoupon>> send_pro_coupon_condition = new HashMap<>();

			String productCouponId = order.getProductCouponId();

			if (null != productCouponId && !"".equalsIgnoreCase(productCouponId)) {
				String[] sIds = productCouponId.split(",");
				if (null != sIds && sIds.length > 0) {
					for (String sId : sIds) {
						if (null != sId && !"".equalsIgnoreCase(sId)) {
							Long couponId = Long.parseLong(sId);
							TdCoupon coupon = tdCouponService.findOne(couponId);
							if (null != coupon) {
								Long goodsId = coupon.getGoodsId();
								if (null != coupon.getIsBuy() && coupon.getIsBuy()) {
									ArrayList<TdCoupon> list = buy_pro_coupon_condition.get(goodsId);
									if (null == list) {
										list = new ArrayList<>();
									}
									list.add(coupon);
									buy_pro_coupon_condition.put(goodsId, list);
								} else {
									ArrayList<TdCoupon> list = send_pro_coupon_condition.get(goodsId);
									if (null == list) {
										list = new ArrayList<>();
									}
									list.add(coupon);
									send_pro_coupon_condition.put(goodsId, list);
								}
							}
						}
					}
				}
			}
			// -----------------------------修改结束--------------------------------

			// 修改：2016-06-26 计算这些商品使用的指定产品现金券的金额，这部分金额是不会退还的
			Map<Long, ArrayList<TdCoupon>> cash__pro_coupon_condition = new HashMap<>();
			String cashCouponId = order.getCashCouponId();
			if (null != cashCouponId && !"".equalsIgnoreCase(cashCouponId)) {
				String[] sCashIds = cashCouponId.split(",");
				if (null != sCashIds && sCashIds.length > 0) {
					for (String sId : sCashIds) {
						if (null != sId && !"".equalsIgnoreCase(sId)) {
							Long id = Long.parseLong(sId);
							TdCoupon coupon = tdCouponService.findOne(id);
							if (null != coupon && null != coupon.getTypeCategoryId()
									&& 2L == coupon.getTypeCategoryId().longValue()) {
								Long goodsId = coupon.getGoodsId();
								ArrayList<TdCoupon> cash_coupon = cash__pro_coupon_condition.get(goodsId);
								if (null == cash_coupon) {
									cash_coupon = new ArrayList<>();
								}
								cash_coupon.add(coupon);
								cash__pro_coupon_condition.put(goodsId, cash_coupon);
							}
						}
					}
				}
			}
			// -----------------------------修改结束-----------------------------------

			// 开始拆分退货参数
			String[] param = params.split(",");
			if (null != param && param.length > 0) {
				for (String group : param) {
					if (null != group && !"".equals(group)) {
						String[] singles = group.split("-");
						// 判断singles是否为一个正确的参数
						if (null != singles && singles.length == 3) {
							String sGoodsId = singles[0];
							Long goodsId = null;
							String sNumber = singles[1];
							Long number = 0L;
							String sUnit = singles[2];
							Double unit = 0.00;
							if (null != sGoodsId && !"".equals(sGoodsId)) {
								goodsId = Long.parseLong(sGoodsId);
							}
							if (null != sNumber && !"".equals(sNumber)) {
								number = Long.parseLong(sNumber);
							}
							if (null != sUnit && !"".equals(sUnit)) {
								unit = Double.parseDouble(sUnit);
							}

							// 计算该商品的退货总额
							Double total = number * unit;
							if (null != goodsId) {
								TdGoods goods = tdGoodsService.findOne(goodsId);

								// 2016-06-24修改：需要排除是退货部分使用的指定产品现金券的价格
								Double sub_coupon_price = 0.00;
								ArrayList<TdCoupon> coupon_list = cash__pro_coupon_condition.get(goodsId);
								List<TdCoupon> delete_coupon = new ArrayList<>();
								if (null != coupon_list) {
									for (int i = 0; i < number; i++) {
										if (coupon_list.size() > i) {
											TdCoupon tdCoupon = coupon_list.get(i);
											Double realPrice = tdCoupon.getRealPrice();
											sub_coupon_price += realPrice;
											delete_coupon.add(tdCoupon);
										}
									}

									for (TdCoupon tdCoupon : delete_coupon) {
										coupon_list.remove(tdCoupon);
									}
									delete_coupon = null;
									cash__pro_coupon_condition.put(goodsId, coupon_list);
								}

								// total -= sub_coupon_price;
								Double record = price_difference.get(goodsId);
								if (null == record) {
									record = 0.00;
								}
								record += sub_coupon_price;
								price_difference.put(goodsId, record);
								// --------------------修改结束-----------------------

								// 开始退还产品券
								if (total > 0 || (null != result.get("pro" + goodsId)
										&& (Integer) result.get("pro" + goodsId) > 0)) {
									if (useProCoupon) {
										// 查找本产品是否使用了产品券
										Integer useNumber = (Integer) result.get("pro" + goodsId);
										if (null != useNumber && useNumber > 0) {
											// 开始计算退还几张券
											for (int i = 0; i < useNumber; i++) {
												if (number > 0) {
													infos.add(goods.getTitle() + "【产品券】*1");
													result.put("pro" + goodsId,
															((Integer) result.get("pro" + goodsId) - 1));
													number--;
													total -= unit;
													result.put("pro" + goodsId,
															((Integer) result.get("pro" + goodsId) - 1));
												}
											}
										}
									}
								}
								// 开始退还通用现金券
								if (total > 0) {
									if (useCashCoupon) {
										// 声明一个变量用来表示退还的通用现金券的面额
										Double cashPrice = 0.00;
										if (cashTotal > total) {
											cashPrice = total;
										} else {
											cashPrice = cashTotal;
										}

										if (cashPrice > 0) {
											BigDecimal bd = new BigDecimal(cashPrice);
											cashPrice = bd.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
											infos.add(cashPrice + "元【通用现金券】*1");
										}
										total -= cashPrice;
										cashTotal -= cashPrice;
										result.put("cashTotal", cashTotal - cashPrice);

									}
								}
								// 如果还有金额没有退还，再退还不可提现余额
								if (total > 0) {
									// 定义实际退还的不可提现余额
									Double uncashBalance = 0.00;

									if (null == unCashBalanceUsed) {
										unCashBalanceUsed = 0.00;
									}

									if (total < unCashBalanceUsed) {
										// 需要退还的金额小于用户使用的总的不可提现余额，则直接退还退还的总额
										uncashBalance = total;
									} else {
										// 如果需要退还的金额大于等于用户使用的不可提现余额，则只能够退还用户使用的不可提现余额
										uncashBalance = unCashBalanceUsed;
									}
									if (uncashBalance > 0) {
										BigDecimal bd = new BigDecimal(uncashBalance);
										uncashBalance = bd.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
										infos.add(uncashBalance + "元【不可提现预存款】");
									}
									// // 开始退还不可提现余额
									// 判断是否剩余部分金额需要退还
									total -= uncashBalance;
									unCashBalanceUsed -= uncashBalance;
								}

								// 如果还有剩余的金额没有退还，则开始退还可提现余额
								if (total > 0) {
									if (null == cashBalanceUsed) {
										cashBalanceUsed = 0.00;
									}
									// 定义一个变量用于获取用户使用的可提现余额
									Double cashBalance = 0.00;
									// 如果需要退还的金额小于用户使用的可提现余额，则将所有的金额以可提现余额的形式退还
									if (total < cashBalanceUsed) {
										cashBalance = total;
									} else {
										cashBalance = cashBalanceUsed;
									}
									if (cashBalance > 0) {
										BigDecimal bd = new BigDecimal(cashBalance);
										cashBalance = bd.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
										infos.add(cashBalance + "元【可提现预存款】");
									}
									total -= cashBalance;
									cashBalanceUsed -= cashBalance;
								}
								// 如果还剩余部分金额没有退还，则需要根据支付方式进行第三方退还
								if (total > 0) {

									// 判断用户是否使用了第三方支付
									if (null != otherPay && otherPay > 0.00) {
										// 获取用户的支付方式
										// String payTypeTitle =
										// order.getPayTypeTitle();
										// 定义一个变量表示退款金额数
										Double otherReturn = 0.00;
										if (total < otherPay) {
											otherReturn = total;
										} else {
											otherReturn = otherPay;
										}

										if (otherReturn > 0.00) {
											all_cash_return += otherReturn;
											// infos.add(otherReturn + "元【" +
											// payTypeTitle + "】");
											otherPay -= otherReturn;
										}
										// ----------在此处理退款申请单的一系列操作动作-------------------

									} else {
										if (null == cashPay) {
											cashPay = 0.00;
										}

										Double cashReturn = 0.00;

										if (total < cashPay) {
											cashReturn = total;
										} else {
											cashReturn = cashPay;
										}

										if (cashReturn > 0.00) {
											all_cash_return += cashReturn;
											// infos.add(cashReturn + "元【现金】");
											total -= cashReturn;
											cashPay -= cashReturn;
										}
										// 退还现金后还剩余未退还的金额，就退还POS
										if (total > 0) {
											Double posReturn = 0.00;

											if (total < posPay) {
												posReturn = total;
											} else {
												posReturn = posPay;
											}

											if (posReturn > 0.00) {
												all_cash_return += posReturn;
												// infos.add(posReturn +
												// "元【POS退还】");
												posPay -= posReturn;
												total -= posReturn;
											}
										}
										// 如果还有没退还的金额，则退还其他方式支付的金额
										if (total > 0) {
											Double otherReturn = 0.00;

											if (total < backOtherPay) {
												otherReturn = total;
											} else {
												otherReturn = backOtherPay;
											}

											if (otherReturn > 0.00) {
												all_cash_return += otherReturn;
												// infos.add(posReturn +
												// "元【POS退还】");
												backOtherPay -= otherReturn;
												total -= otherReturn;
											}
										}
									}
								}
							}
						}
					}
				}
			}
			if (all_cash_return > 0) {
				/*
				 * if (all_cash_return > all_off_line) { all_cash_return =
				 * all_off_line; }
				 */
				BigDecimal bd = new BigDecimal(all_cash_return);
				all_cash_return = bd.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
				infos.add(all_cash_return + "元【现金】");
			}
		}
		res.put("infos", infos);
		return res;
	}

	private TdReturnNote getReturnDetail(Long orderId, String params, TdReturnNote returnNote) {
		StringBuffer sb = new StringBuffer("");

		TdOrder order = tdOrderService.findOne(orderId);
		if (null != order && null != params && !"".equals(params)) {
			Map<String, Object> result = tdPriceCountService.countCouponCondition(orderId);

			Boolean useProCoupon = (Boolean) result.get("useProCoupon");
			Boolean useCashCoupon = (Boolean) result.get("useCashCoupon");

			Double cashTotal = (Double) result.get("cashTotal");
			// 获取订单使用的总不可提现余额
			Double unCashBalanceUsed = order.getUnCashBalanceUsed();
			// 获取用户使用的可提现余额
			Double cashBalanceUsed = order.getCashBalanceUsed();
			// 获取用户第三方支付的金额
			Double otherPay = order.getOtherPay();
			// 获取用户支付的现金
			Double cashPay = order.getCashPay();
			// 获取用户其他支付
			Double backOtherPay = order.getBackOtherPay();
			if (null == cashPay) {
				cashPay = 0.00;
			}
			// 获取用户支付的POS
			Double posPay = order.getPosPay();
			if (null == posPay) {
				posPay = 0.00;
			}
			if (null == backOtherPay) {
				backOtherPay = 0.00;
			}

			/* Double all_off_line = posPay + cashPay + backOtherPay; */

			// 2016-07-05修改：以现金的方式归还第三方支付的钱，POS和现金还有其他
			Double all_cash_return = 0.00;

			Map<Long, Double> price_difference = new HashMap<>();

			// 2016-06-26修改：需要获取用户使用赠送的产品券和购买的产品券的集合
			Map<Long, ArrayList<TdCoupon>> buy_pro_coupon_condition = new HashMap<>();
			Map<Long, ArrayList<TdCoupon>> send_pro_coupon_condition = new HashMap<>();

			String productCouponId = order.getProductCouponId();

			if (null != productCouponId && !"".equalsIgnoreCase(productCouponId)) {
				String[] sIds = productCouponId.split(",");
				if (null != sIds && sIds.length > 0) {
					for (String sId : sIds) {
						if (null != sId && !"".equalsIgnoreCase(sId)) {
							Long couponId = Long.parseLong(sId);
							TdCoupon coupon = tdCouponService.findOne(couponId);
							if (null != coupon) {
								Long goodsId = coupon.getGoodsId();
								if (null != coupon.getIsBuy() && coupon.getIsBuy()) {
									ArrayList<TdCoupon> list = buy_pro_coupon_condition.get(goodsId);
									if (null == list) {
										list = new ArrayList<>();
									}
									list.add(coupon);
									buy_pro_coupon_condition.put(goodsId, list);
								} else {
									ArrayList<TdCoupon> list = send_pro_coupon_condition.get(goodsId);
									if (null == list) {
										list = new ArrayList<>();
									}
									list.add(coupon);
									send_pro_coupon_condition.put(goodsId, list);
								}
							}
						}
					}
				}
			}
			// -----------------------------修改结束--------------------------------

			// 修改：2016-06-26 计算这些商品使用的指定产品现金券的金额，这部分金额是不会退还的
			Map<Long, ArrayList<TdCoupon>> cash__pro_coupon_condition = new HashMap<>();
			String cashCouponId = order.getCashCouponId();
			if (null != cashCouponId && !"".equalsIgnoreCase(cashCouponId)) {
				String[] sCashIds = cashCouponId.split(",");
				if (null != sCashIds && sCashIds.length > 0) {
					for (String sId : sCashIds) {
						if (null != sId && !"".equalsIgnoreCase(sId)) {
							Long id = Long.parseLong(sId);
							TdCoupon coupon = tdCouponService.findOne(id);
							if (null != coupon && null != coupon.getTypeCategoryId()
									&& 2L == coupon.getTypeCategoryId().longValue()) {
								Long goodsId = coupon.getGoodsId();
								ArrayList<TdCoupon> cash_coupon = cash__pro_coupon_condition.get(goodsId);
								if (null == cash_coupon) {
									cash_coupon = new ArrayList<>();
								}
								cash_coupon.add(coupon);
								cash__pro_coupon_condition.put(goodsId, cash_coupon);
							}
						}
					}
				}
			}
			// -----------------------------修改结束-----------------------------------

			// 开始拆分退货参数
			String[] param = params.split(",");
			if (null != param && param.length > 0) {
				for (String group : param) {
					if (null != group && !"".equals(group)) {
						String[] singles = group.split("-");
						// 判断singles是否为一个正确的参数
						if (null != singles && singles.length == 4) {
							String sGoodsId = singles[0];
							Long goodsId = null;
							String sNumber = singles[1];
							Long number = 0L;
							String sUnit = singles[2];
							Double unit = 0.00;
							if (null != sGoodsId && !"".equals(sGoodsId)) {
								goodsId = Long.parseLong(sGoodsId);
							}
							if (null != sNumber && !"".equals(sNumber)) {
								number = Long.parseLong(sNumber);
							}
							if (null != sUnit && !"".equals(sUnit)) {
								unit = Double.parseDouble(sUnit);
							}

							// 计算该商品的退货总额
							Double total = number * unit;
							if (null != goodsId) {
								TdGoods goods = tdGoodsService.findOne(goodsId);

								// 2016-06-24修改：需要排除是退货部分使用的指定产品现金券的价格
								Double sub_coupon_price = 0.00;
								ArrayList<TdCoupon> coupon_list = cash__pro_coupon_condition.get(goodsId);
								List<TdCoupon> delete_coupon = new ArrayList<>();
								if (null != coupon_list) {
									for (int i = 0; i < number; i++) {
										if (coupon_list.size() > i) {
											TdCoupon tdCoupon = coupon_list.get(i);
											Double realPrice = tdCoupon.getRealPrice();
											sub_coupon_price += realPrice;
											delete_coupon.add(tdCoupon);
										}
									}

									for (TdCoupon tdCoupon : delete_coupon) {
										coupon_list.remove(tdCoupon);
									}
									delete_coupon = null;
									cash__pro_coupon_condition.put(goodsId, coupon_list);
								}

								// total -= sub_coupon_price;
								Double record = price_difference.get(goodsId);
								if (null == record) {
									record = 0.00;
								}
								record += sub_coupon_price;
								price_difference.put(goodsId, record);
								// --------------------修改结束-----------------------

								// 开始退还产品券
								if (total > 0 || (null != result.get("pro" + goodsId)
										&& (Integer) result.get("pro" + goodsId) > 0)) {
									if (useProCoupon) {
										// 查找本产品是否使用了产品券
										Integer useNumber = (Integer) result.get("pro" + goodsId);
										if (null != useNumber && useNumber > 0) {
											// 开始计算退还几张券
											for (int i = 0; i < useNumber; i++) {
												if (number > 0) {
													sb.append(goods.getTitle() + "【产品券】*1");
													result.put("pro" + goodsId,
															((Integer) result.get("pro" + goodsId) - 1));
													number--;
													total -= unit;
													result.put("pro" + goodsId,
															((Integer) result.get("pro" + goodsId) - 1));
												}
											}
										}
									}
								}
								// 开始退还通用现金券
								if (total > 0) {
									if (useCashCoupon) {
										// 声明一个变量用来表示退还的通用现金券的面额
										Double cashPrice = 0.00;
										if (cashTotal > total) {
											cashPrice = total;
										} else {
											cashPrice = cashTotal;
										}

										if (cashPrice > 0) {
											BigDecimal bd = new BigDecimal(cashPrice);
											cashPrice = bd.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
											sb.append(cashPrice + "元【通用现金券】*1");
										}
										total -= cashPrice;
										cashTotal -= cashPrice;
										result.put("cashTotal", cashTotal - cashPrice);

									}
								}
								// 如果还有金额没有退还，再退还不可提现余额
								if (total > 0) {
									// 定义实际退还的不可提现余额
									Double uncashBalance = 0.00;

									if (null == unCashBalanceUsed) {
										unCashBalanceUsed = 0.00;
									}

									if (total < unCashBalanceUsed) {
										// 需要退还的金额小于用户使用的总的不可提现余额，则直接退还退还的总额
										uncashBalance = total;
									} else {
										// 如果需要退还的金额大于等于用户使用的不可提现余额，则只能够退还用户使用的不可提现余额
										uncashBalance = unCashBalanceUsed;
									}
									if (uncashBalance > 0) {
										BigDecimal bd = new BigDecimal(uncashBalance);
										uncashBalance = bd.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
										sb.append(uncashBalance + "元【不可提现预存款】");
									}
									// // 开始退还不可提现余额
									// 判断是否剩余部分金额需要退还
									total -= uncashBalance;
									unCashBalanceUsed -= uncashBalance;
								}

								// 如果还有剩余的金额没有退还，则开始退还可提现余额
								if (total > 0) {
									if (null == cashBalanceUsed) {
										cashBalanceUsed = 0.00;
									}
									// 定义一个变量用于获取用户使用的可提现余额
									Double cashBalance = 0.00;
									// 如果需要退还的金额小于用户使用的可提现余额，则将所有的金额以可提现余额的形式退还
									if (total < cashBalanceUsed) {
										cashBalance = total;
									} else {
										cashBalance = cashBalanceUsed;
									}
									if (cashBalance > 0) {
										BigDecimal bd = new BigDecimal(cashBalance);
										cashBalance = bd.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
										sb.append(cashBalance + "元【可提现预存款】");
									}
									total -= cashBalance;
									cashBalanceUsed -= cashBalance;
								}
								// 如果还剩余部分金额没有退还，则需要根据支付方式进行第三方退还
								if (total > 0) {

									// 判断用户是否使用了第三方支付
									if (null != otherPay && otherPay > 0.00) {
										// 获取用户的支付方式
										// String payTypeTitle =
										// order.getPayTypeTitle();
										// 定义一个变量表示退款金额数
										Double otherReturn = 0.00;
										if (total < otherPay) {
											otherReturn = total;
										} else {
											otherReturn = otherPay;
										}

										if (otherReturn > 0.00) {
											all_cash_return += otherReturn;
											// infos.add(otherReturn + "元【" +
											// payTypeTitle + "】");
											otherPay -= otherReturn;
										}
										// ----------在此处理退款申请单的一系列操作动作-------------------

									} else {
										if (null == cashPay) {
											cashPay = 0.00;
										}

										Double cashReturn = 0.00;

										if (total < cashPay) {
											cashReturn = total;
										} else {
											cashReturn = cashPay;
										}

										if (cashReturn > 0.00) {
											all_cash_return += cashReturn;
											// infos.add(cashReturn + "元【现金】");
											total -= cashReturn;
											cashPay -= cashReturn;
										}
										// 退还现金后还剩余未退还的金额，就退还POS
										if (total > 0) {
											Double posReturn = 0.00;

											if (total < posPay) {
												posReturn = total;
											} else {
												posReturn = posPay;
											}

											if (posReturn > 0.00) {
												all_cash_return += posReturn;
												// infos.add(posReturn +
												// "元【POS退还】");
												posPay -= posReturn;
												total -= posReturn;
											}
										}
										// 如果还有没退还的金额，则退还其他方式的支付金额
										if (total > 0) {
											Double otherReturn = 0.00;

											if (total < backOtherPay) {
												otherReturn = total;
											} else {
												otherReturn = backOtherPay;
											}

											if (otherReturn > 0.00) {
												all_cash_return += otherReturn;
												// infos.add(posReturn +
												// "元【POS退还】");
												backOtherPay -= otherReturn;
												total -= otherReturn;
											}
										}
									}
								}
							}
						}
					}
				}
			}
			if (all_cash_return > 0) {
				/*
				 * if (all_cash_return > all_off_line) { all_cash_return =
				 * all_off_line; }
				 */
				BigDecimal bd = new BigDecimal(all_cash_return);
				all_cash_return = bd.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
				sb.append(all_cash_return + "元【现金】");
			}
		}
		returnNote.setReturnDetail(sb.toString());
		return returnNote;
	}
}
