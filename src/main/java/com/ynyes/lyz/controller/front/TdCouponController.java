package com.ynyes.lyz.controller.front;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ynyes.lyz.entity.TdCartGoods;
import com.ynyes.lyz.entity.TdCoupon;
import com.ynyes.lyz.entity.TdGoods;
import com.ynyes.lyz.entity.TdPriceListItem;
import com.ynyes.lyz.entity.user.TdUser;
import com.ynyes.lyz.service.TdCartGoodsService;
import com.ynyes.lyz.service.TdCommonService;
import com.ynyes.lyz.service.TdCouponGoodsService;
import com.ynyes.lyz.service.TdCouponService;
import com.ynyes.lyz.service.TdGoodsService;
import com.ynyes.lyz.service.TdUserService;

/**
 * 抢券
 * 
 * @author Max
 *
 */
@Controller
public class TdCouponController {

	@Autowired
	private TdUserService tdUserService;

	@Autowired
	private TdCouponService tdCouponService;

	@Autowired
	private TdGoodsService tdGoodsService;

	@Autowired
	private TdCouponGoodsService tdCouponGoodsService;

	@Autowired
	private TdCommonService tdCommonService;

	@Autowired
	private TdCartGoodsService tdCartGoodsService;

	/**
	 * 抢券
	 * 
	 * @author Max
	 * 
	 */
	@RequestMapping(value = "/coupon/list")
	public String couponList(HttpServletRequest req, ModelMap map) {
		String username = (String) req.getSession().getAttribute("username");
		if (null == username) {
			return "redirect:/login";
		}
		TdUser user = tdUserService.findByUsername(username);
		if (null == user) {
			return "redirect:/login";
		}
		// 查找可领取现金券
		map.addAttribute("couponList",
				tdCouponService.findByCityNameAndTypeIdAndTypeCategoryId(user.getCityName(), 2L, 1L, new Date()));
		// 可领取产品券
		map.addAttribute("coupon_list",
				tdCouponService.findByCityNameAndTypeIdAndTypeCategoryId(user.getCityName(), 2L, 3L, new Date()));

		return "/client/coupon_list";
	}

	/**
	 * 领券
	 * 
	 * @author Max
	 */
	@RequestMapping(value = "/coupon/grant", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> grantCoupon(Long id, HttpServletRequest req) {
		Map<String, Object> res = new HashMap<String, Object>();
		res.put("code", "0");
		String username = (String) req.getSession().getAttribute("username");

		if (null == username) {
			res.put("msg", "请重新登录");
			return res;
		}

		TdUser user = tdUserService.findByUsername(username);

		if (null != id) {

			TdCoupon coupon = tdCouponService.findOne(id);

			if (null == coupon) {
				res.put("msg", "参数错误");
				return res;
			}
			if (null == coupon.getLeftNumber() || coupon.getLeftNumber() == 0) {
				res.put("msg", "次优惠券已领完");
				return res;
			}

			// 新创建会员领用券
			TdCoupon tdCoupon = new TdCoupon();
			// 会员领取信息
			tdCoupon.setUsername(user.getUsername());
			tdCoupon.setMobile(user.getUsername());
			tdCoupon.setGetNumber(1L);
			tdCoupon.setIsOutDate(false);
			tdCoupon.setIsUsed(false);
			tdCoupon.setGetTime(new Date());
			// 优惠券基本信息
			tdCoupon.setIsDistributted(true);
			tdCoupon.setPrice(coupon.getPrice());
			tdCoupon.setAddTime(coupon.getAddTime());
			tdCoupon.setTypePicUri(coupon.getTypePicUri());
			tdCoupon.setExpireTime(coupon.getExpireTime());

			tdCoupon.setBrandId(coupon.getBrandId());
			tdCoupon.setBrandTitle(coupon.getBrandTitle());
			tdCoupon.setTypeDescription(coupon.getTypeDescription());
			tdCoupon.setGoodsId(coupon.getGoodsId());
			tdCoupon.setGoodsName(coupon.getGoodsName());
			tdCoupon.setPicUri(coupon.getPicUri());
			tdCoupon.setTypeId(coupon.getTypeId());
			tdCoupon.setTypeTitle(coupon.getTypeTitle());
			tdCoupon.setTypeCategoryId(coupon.getTypeCategoryId());
			tdCoupon.setCityName(user.getCityName());
			TdGoods good = tdGoodsService.findOne(coupon.getGoodsId());
			if (good != null) {
				tdCoupon.setSku(good.getCode());
			}
			// 保存领取
			tdCouponService.save(tdCoupon);

			// 更新剩余量
			coupon.setLeftNumber(coupon.getLeftNumber() - 1);
			tdCouponService.save(coupon);

			res.put("code", 1);
			res.put("msg", "领券成功");
			return res;
		}
		res.put("msg", "参数错误");

		return res;
	}

	@RequestMapping(value = "/coupon/normal/list")
	public String couponNormalList(HttpServletRequest req, ModelMap map) {
		String username = (String) req.getSession().getAttribute("username");
		TdUser user = tdUserService.findByUsernameAndIsEnableTrue(username);
		if (null == user) {
			return "redirect:/login";
		}

		tdCommonService.setHeader(req, map);
		// tdCommonService.getCategory(req, map);
		// 新的方法查找三级菜单
		// tdCommonService.getCategoryTemp(req, map);
		tdCommonService.thirdGetCategory(req, map);
		map.addAttribute("user", user);
		return "/client/coupon_normal_list";
	}

	@RequestMapping(value = "/coupon/normal/get")
	public String couponNormalGet(HttpServletRequest req, ModelMap map, Long categoryId) {
		// 获取指定id分类下的所有商品和其价格
		tdCouponGoodsService.getGoodsAndPrice(req, map, categoryId);
		return "/client/normal_coupon_goods";
	}

	@RequestMapping(value = "/coupon/add/cart")
	@ResponseBody
	public Map<String, Object> couponAddCart(HttpServletRequest req, String params) {
		Map<String, Object> res = new HashMap<>();
		res.put("status", -1);

		res.put("status", 0);
		return res;
	}

	@RequestMapping(value = "/coupon/check")
	@ResponseBody
	public Map<String, Object> couponCheck(HttpServletRequest req, String params) {
		Map<String, Object> res = new HashMap<>();
		res.put("status", -1);

		// 获取当前登录用户
		String username = (String) req.getSession().getAttribute("username");
		TdUser user = tdUserService.findByUsername(username);
		if (null != user && null != user.getUserType() && user.getUserType().longValue() == 0L) {
			res.put("status", 0);
		}

		return res;
	}

	@RequestMapping(value = "/coupon/order/before")
	public String couponOrderBefore(HttpServletRequest req, ModelMap map, Long realUserId, String params) {
		String username = (String) req.getSession().getAttribute("username");
		TdUser user = tdUserService.findByUsernameAndIsEnableTrue(username);
		if (null == user) {
			return "redirect:/login";
		}

		// 清空用户的购物车
		tdCommonService.clear(req);
		// 新生成购物车项
		if (null != params) {
			String[] param = params.split("\\-");
			for (int i = 0; i < param.length; i++) {
				String item = param[i];
				String[] goodsId_quantity = item.split("_");
				// 获取指定商品
				TdGoods goods = tdGoodsService.findOne(Long.parseLong(goodsId_quantity[0]));
				// 获取指定商品对于用户的价格
				TdPriceListItem priceListItem = tdCommonService.getGoodsPrice(req, goods);
				// 创建一个实体用于存储拆分好的goodsId和quantity
				TdCartGoods cartGoods = new TdCartGoods(Long.parseLong(goodsId_quantity[0]),
						Long.parseLong(goodsId_quantity[1]));
				cartGoods.setGoodsTitle(goods.getTitle());
				cartGoods.setGoodsCoverImageUri(goods.getCoverImageUri());
				if (null != priceListItem) {
					cartGoods.setPrice(priceListItem.getCouponPrice());
					cartGoods.setRealPrice(priceListItem.getCouponRealPrice());
					cartGoods.setSku(goods.getCode());
					cartGoods.setUserId(user.getId());
					cartGoods.setUsername(username);
					cartGoods.setBrandId(goods.getBrandId());
					cartGoods.setBrandTitle(goods.getBrandTitle());
					cartGoods.setIsCoupon(goods.getIsCoupon());
				}
				if (null != goods.getIsColorPackage() && goods.getIsColorPackage()) {
					cartGoods.setIsColor(true);
				}
				cartGoods.setIsCoupon(true);
				tdCartGoodsService.save(cartGoods);
			}
		}
		if (null != realUserId) {
			return "redirect:/order?realUserId=" + realUserId;
		}
		return "redirect:/order";
	}

}
