package com.ynyes.lyz.service;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.ModelMap;

import com.ynyes.lyz.entity.TdDiySiteInventory;
import com.ynyes.lyz.entity.TdGoods;
import com.ynyes.lyz.entity.TdPriceListItem;
import com.ynyes.lyz.entity.TdProductCategory;
import com.ynyes.lyz.entity.user.TdUser;

/**
 * 优惠券商品服务类
 * 
 * @author 作者：DengXiao
 * @version 创建时间：2016年4月27日上午9:53:18
 */

@Service
@Transactional
public class TdCouponGoodsService {

	@Autowired
	private TdGoodsService tdGoodsService;

	@Autowired
	private TdProductCategoryService tdProductCategoryService;

	@Autowired
	private TdCommonService tdCommonService;

	@Autowired
	private TdUserService tdUserService;

	@Autowired
	private TdDiySiteInventoryService tdDiySiteInventoryService;

	/**
	 * 获取登录用户所能购买的所有券商品的id
	 * 
	 * @author 作者：DengXiao
	 * @version 创建时间：2016年4月27日上午10:12:58
	 */
	public List<Long> getCouponGoodsId(HttpServletRequest req) {
		List<Long> ids = new ArrayList<>();

		// 查找所有的券商品
		List<TdGoods> coupon_goods_list = tdGoodsService.findByIsCouponTrue();
		if (null != coupon_goods_list && coupon_goods_list.size() > 0) {
			for (TdGoods goods : coupon_goods_list) {
				TdPriceListItem priceListItem = tdCommonService.getGoodsPrice(req, goods);
				if (null != priceListItem) {
					ids.add(goods.getId());
				}
			}
		}
		return ids;
	}

	/**
	 * 根据限定的商品id范围查找其中所包含的分类id
	 * 
	 * @author 作者：DengXiao
	 * @version 创建时间：2016年4月27日上午10:22:33
	 */
	public List<Long> getCouponGoodsCategoryId(HttpServletRequest req) {
		List<Long> goods_ids = this.getCouponGoodsId(req);
		List<Long> category_ids = tdGoodsService.findCategoryIdByIds(goods_ids);
		return category_ids;
	}

	/**
	 * 查找所有的分类
	 * 
	 * @author 作者：DengXiao
	 * @version 创建时间：2016年4月27日上午11:02:03
	 */
	public void getAllCouponCategory(HttpServletRequest req, ModelMap map) {
		// 获取限定范围内的一级分类
		List<Long> category_ids = this.getCouponGoodsCategoryId(req);
		List<Long> level_one_ids = tdProductCategoryService.findParentIdAndIdIn(category_ids);
		List<TdProductCategory> level_one_categories = tdProductCategoryService
				.findByIdInGroupByIdOrderBySortIdAsc(level_one_ids);
		map.addAttribute("level_one_categories", level_one_categories);

		// 遍历一级分类，一次查找其中的二级分类
		if (null != level_one_categories && level_one_categories.size() > 0) {
			for (int i = 0; i < level_one_categories.size(); i++) {
				TdProductCategory level_one = level_one_categories.get(i);
				if (null != level_one) {
					List<TdProductCategory> level_two_categories = tdProductCategoryService
							.findByParentIdAndIdInOrderBySortIdAsc(level_one.getId(), category_ids);
					map.addAttribute("level_two_categories" + i, level_two_categories);
				}
			}
		}
	}

	/**
	 * 获取指定分类下的优惠券商品
	 * 
	 * @author 作者：DengXiao
	 * @version 创建时间：2016年4月27日上午11:54:13
	 */
	public void getGoodsAndPrice(HttpServletRequest req, ModelMap map, Long cateGoryId) {

		// 获取门店
		String username = (String) req.getSession().getAttribute("username");
		TdUser tdUser = tdUserService.findByUsername(username);
		Long siteId = 0L;
		if (tdUser != null) {
			siteId = tdUser.getUpperDiySiteId();
		}

		// 创建一个集合存储有价格的商品
		List<TdGoods> actual_goods = new ArrayList<>();
		// 查找指定二级分类下的所有商品
		List<TdGoods> goods_list = tdGoodsService
				.findByCategoryIdAndIsOnSaleTrueAndIsCouponNotTrueOrderBySortIdAsc(cateGoryId);
		for (int i = 0; i < goods_list.size(); i++) {
			TdGoods goods = goods_list.get(i);
			if (null != goods) {
				// 查找指定商品的价格
				TdPriceListItem priceListItem = tdCommonService.getGoodsPrice(req, goods);
				if (null != priceListItem && null != priceListItem.getCouponPrice()
						&& null != priceListItem.getCouponRealPrice()) {
					actual_goods.add(goods);
					// 开始判断此件商品是否参加活动
					priceListItem.setIsPromotion(tdCommonService.isJoinActivity(req, goods));
					map.addAttribute("priceListItem" + i, priceListItem);
					List<TdDiySiteInventory> inventories = tdDiySiteInventoryService.findByDiySiteId(siteId);
					if (inventories.size() == 1) {
						map.addAttribute("goodInventory" + i, inventories.get(0).getInventory());
					} else {
						map.addAttribute("goodInventory" + i, 0);
					}
				} else {
					actual_goods.add(null);
				}
			}
			map.addAttribute("some_goods", actual_goods);
		}
	}
}
