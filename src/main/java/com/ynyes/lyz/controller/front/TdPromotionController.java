package com.ynyes.lyz.controller.front;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ynyes.lyz.entity.TdActivity;
import com.ynyes.lyz.entity.TdCartGoods;
import com.ynyes.lyz.entity.TdDiySite;
import com.ynyes.lyz.entity.TdGoods;
import com.ynyes.lyz.entity.TdPriceListItem;
import com.ynyes.lyz.entity.user.TdUser;
import com.ynyes.lyz.service.TdActivityService;
import com.ynyes.lyz.service.TdCartGoodsService;
import com.ynyes.lyz.service.TdCommonService;
import com.ynyes.lyz.service.TdGoodsService;
import com.ynyes.lyz.service.TdUserService;

@Controller
@RequestMapping(value = "/promotion")
public class TdPromotionController {

	@Autowired
	private TdUserService tdUserService;

	@Autowired
	private TdCommonService tdCommonService;

	@Autowired
	private TdCartGoodsService tdCartGoodsService;

	@Autowired
	private TdActivityService tdActivityService;

	@Autowired
	private TdGoodsService tdGoodsService;

	/**
	 * 跳转到活动促销页面的方法
	 * 
	 * @author dengxiao
	 */
	@RequestMapping(value = "/list")
	public String promotionList(HttpServletRequest req, ModelMap map) {
		String username = (String) req.getSession().getAttribute("username");
		TdUser user = tdUserService.findByUsernameAndIsEnableTrue(username);
		if (null == user) {
			return "redirect:/login";
		}
		// 获取用户所在门店
		TdDiySite diySite = tdCommonService.getDiySite(req);

		// 获取该门店参与的所有未过期的活动
//		List<TdActivity> activities = tdActivityService
//				.findByDiySiteIdsContainingAndBeginDateBeforeAndFinishDateAfterAndGiftTypeOrderBySortIdAsc(
//						diySite.getId() + "", new Date(), 1L);
		List<TdActivity> activities = tdActivityService.findByDiySiteIdsContainingAndBeginDateBeforeAndFinishDateAfterOrderBySortIdAsc(diySite.getId() + "", new Date());
		map.addAttribute("activity_list", activities);

		return "/client/promotion_list";
	}

	/**
	 * 添加促销商品的方法
	 * 
	 * @author dengxiao
	 */
	@RequestMapping(value = "/add")
	@ResponseBody
	public Map<String, Object> addActivity(HttpServletRequest req, Long activityId) {
		Map<String, Object> res = new HashMap<>();
		res.put("status", -1);

		String username = (String) req.getSession().getAttribute("username");
		TdUser user = tdUserService.findByUsernameAndIsEnableTrue(username);
		if (null == user) {
			return res;
		}

		// 获取指定id的活动
		TdActivity activity = tdActivityService.findOne(activityId);
		if (null != activity) {
			String goodsNumber = activity.getGoodsNumber();
			// 拆分
			if (null != goodsNumber) {
				String[] goods_number = goodsNumber.split(",");
				for (String single : goods_number) {
					// 继续拆分
					if (null != single) {
						String[] param = single.split("_");
						if (null != param && param.length == 2) {
							Long goodsId = Long.parseLong(param[0]);
							Long quantity = Long.parseLong(param[1]);
							// 获取所有已选的商品
							List<TdCartGoods> selected_goods = tdCartGoodsService.findByUserId(user.getId());
							// 创建一个布尔值用于判断此件商品是否已加入已选
							Boolean isExist = false;
							for (int i = 0; i < selected_goods.size(); i++) {
								TdCartGoods cartGoods = selected_goods.get(i);
								if (null != cartGoods && null != cartGoods.getGoodsId()
										&& cartGoods.getGoodsId().longValue() == goodsId.longValue()) {
									isExist = true;
									cartGoods.setQuantity(cartGoods.getQuantity() + quantity);
									cartGoods.setTotalPrice(cartGoods.getPrice() * cartGoods.getQuantity());
									cartGoods.setRealPrice(cartGoods.getRealPrice() * cartGoods.getQuantity());
									tdCartGoodsService.save(cartGoods);
								}
							}
							if (!isExist) {
								TdGoods goods = tdGoodsService.findOne(goodsId);
								TdPriceListItem priceListItem = tdCommonService.getGoodsPrice(req, goods);
								TdCartGoods cartGoods = new TdCartGoods();
								cartGoods.setGoodsCoverImageUri(goods.getCoverImageUri());
								cartGoods.setGoodsId(goodsId);
								cartGoods.setGoodsTitle(goods.getTitle());
								cartGoods.setSku(goods.getCode());
								cartGoods.setBrandId(goods.getBrandId());
								cartGoods.setBrandTitle(goods.getBrandTitle());
								cartGoods.setIsColor(goods.getIsColorful());
								cartGoods.setUserId(user.getId());
								cartGoods.setUsername(username);
								if (null != priceListItem) {
									cartGoods.setPrice(priceListItem.getSalePrice());
									cartGoods.setRealPrice(priceListItem.getRealSalePrice());
								} else {
									cartGoods.setPrice(0.00);
									cartGoods.setRealPrice(0.00);
								}
								cartGoods.setQuantity(quantity);
								cartGoods.setTotalPrice(cartGoods.getPrice() * cartGoods.getQuantity());
								cartGoods.setRealTotalPrice(cartGoods.getRealPrice() * cartGoods.getQuantity());
								cartGoods.setIsWallAccessory(goods.getIsWallAccessory());
								tdCartGoodsService.save(cartGoods);
							}
						}
					}
				}
			}
		}
		res.put("status", 0);
		return res;
	}
}
