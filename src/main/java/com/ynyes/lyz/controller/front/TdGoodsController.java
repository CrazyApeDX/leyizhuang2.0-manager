package com.ynyes.lyz.controller.front;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ynyes.lyz.entity.TdActivity;
import com.ynyes.lyz.entity.TdCartColorPackage;
import com.ynyes.lyz.entity.TdCartGoods;
import com.ynyes.lyz.entity.TdDiySite;
import com.ynyes.lyz.entity.TdDiySiteInventory;
import com.ynyes.lyz.entity.TdGoods;
import com.ynyes.lyz.entity.TdOrder;
import com.ynyes.lyz.entity.TdOrderGoods;
import com.ynyes.lyz.entity.TdPriceListItem;
import com.ynyes.lyz.entity.TdProductCategory;
import com.ynyes.lyz.entity.TdSetting;
import com.ynyes.lyz.entity.TdUserCollect;
import com.ynyes.lyz.entity.TdUserComment;
import com.ynyes.lyz.entity.goods.TdUnableSale;
import com.ynyes.lyz.entity.user.TdUser;
import com.ynyes.lyz.service.TdActivityService;
import com.ynyes.lyz.service.TdCartGoodsService;
import com.ynyes.lyz.service.TdCommonService;
import com.ynyes.lyz.service.TdDiySiteInventoryService;
import com.ynyes.lyz.service.TdGoodsService;
import com.ynyes.lyz.service.TdOrderService;
import com.ynyes.lyz.service.TdProductCategoryService;
import com.ynyes.lyz.service.TdSettingService;
import com.ynyes.lyz.service.TdUnableSaleService;
import com.ynyes.lyz.service.TdUserCollectService;
import com.ynyes.lyz.service.TdUserCommentService;
import com.ynyes.lyz.service.TdUserService;

@Controller
@RequestMapping(value = "/goods")
public class TdGoodsController {

	@Autowired
	private TdCommonService tdCommonService;

	@Autowired
	private TdUserService tdUserService;

	@Autowired
	private TdGoodsService tdGoodsService;

	@Autowired
	private TdUserCommentService tdUserCommentService;

	@Autowired
	private TdUserCollectService tdUserCollectService;

	@Autowired
	private TdActivityService tdActivityService;

	@Autowired
	private TdSettingService tdSettingService;

	@Autowired
	private TdCartGoodsService tdCartGoodsService;

	@Autowired
	private TdOrderService tdOrderService;
	
	@Autowired
	private TdProductCategoryService tdProductCategoryService;
	
	@Autowired
	private TdDiySiteInventoryService tdDiySiteInventoryService;
	
	@Autowired
	private TdUnableSaleService tdUnableSaleService;

	/*
	 *********************************** 普通下单模式的控制器和方法********************************************
	 */

	/**
	 * 跳转到普通下单（一键下单）页面的方法
	 * 
	 * @author dengxiao
	 */
	@RequestMapping(value = "/normal/list")
	public String goodsListNormal(HttpServletRequest req, ModelMap map) {
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
		Long number = tdCartGoodsService.countByUserId(user.getId());
		// 将已选商品的数量（包括调色包）添加到ModelMap中
		map.addAttribute("selected_number", number);
		return "/client/goods_list_normal";
	}

	/**
	 * 普通下单模式下获取商品的方法
	 * 
	 * @author dengxiao
	 */
	@RequestMapping(value = "/normal/get")
	public String normalGetGoods(HttpServletRequest req, ModelMap map, Long categoryId) {
		String username = (String) req.getSession().getAttribute("username");
		TdUser user = tdUserService.findByUsernameAndIsEnableTrue(username);
		if (null == user) {
			return "redirect:/login";
		}
		// 获取指定id分类下的所有商品和其价格
		tdCommonService.getGoodsAndPrice(req, map, categoryId);
		return "/client/normal_goods";
	}
	/*
	 *********************************** 普通下单结束******************************************************
	 */

	/*
	 *********************************** 步骤下单模式的控制器和方法*******************************************
	 */

	/**
	 * 跳转到步骤下单页面的方法
	 * 
	 * @author dengxiao
	 */
	@RequestMapping(value = "/step/list")
	public String goodsListStep(HttpServletRequest req, ModelMap map) {
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
		Long number = tdCartGoodsService.countByUserId(user.getId());
		// 将已选商品的数量（包括调色包）添加到ModelMap中
		map.addAttribute("selected_number", number);
		return "/client/goods_list_step";
	}

	/**
	 * 步骤下单获取指定分类下所有商品的方法
	 * 
	 * @author dengxiao
	 */
	@RequestMapping(value = "/step/get")
	public String stepGetGoods(HttpServletRequest req, ModelMap map, Long categoryId) {
		// 获取指定id分类下的所有商品和其价格
		tdCommonService.getGoodsAndPrice(req, map, categoryId);
		return "/client/sub_step_goods";
	}

	/*
	 *********************************** 步骤下单结束******************************************************
	 */

	/*
	 *********************************** 公共************************************************************
	 */

	/**
	 * 获取调色包的方法
	 * 
	 * @author dengxiao
	 */
	@RequestMapping(value = "/get/color")
	public String getColor(HttpServletRequest req, Long goodsId, ModelMap map) {
		String username = (String) req.getSession().getAttribute("username");
		TdUser user = tdUserService.findByUsernameAndIsEnableTrue(username);
		if (null == user) {
			user = new TdUser();
		}

		// 根据goodsId获取指定的商品
		TdGoods goods = tdGoodsService.findOne(goodsId);
		// 创建一个集合用于存储指定商品特调色的调色包商品
		List<TdGoods> color_package_list = new ArrayList<>();

		// 获取指定商品可选调色包
		if (null != goods && null != goods.getColorPackageSku()) {
			String[] all_color_code = goods.getColorPackageSku().split(",");
			for (int i = 0; i < all_color_code.length; i++) {
				// 获取指定sku的调色包商品
				TdGoods colorGoods = tdGoodsService.findByCode(all_color_code[i]);
				if (null != colorGoods) {
					// 获取指定调色包对于用户的价格
					TdPriceListItem priceListItem = tdCommonService.getGoodsPrice(req, colorGoods);
					if (null != priceListItem && null != priceListItem.getSalePrice()) {
						color_package_list.add(colorGoods);
					}
				}
			}
		}
		// 获取指定调色包对于登陆用户的价格
		for (int i = 0; i < color_package_list.size(); i++) {
			// 获取指定调色包对于用户的价格
			TdPriceListItem priceListItem = tdCommonService.getGoodsPrice(req, color_package_list.get(i));
			// 添加默认单价：第一个调色包的商品
			if (0 == i && null != priceListItem) {
				map.addAttribute("unit_price", priceListItem.getSalePrice());
			}
			// 添加默认库存：第一个调色包的库存
			if (0 == i) {
				map.addAttribute("inventory", color_package_list.get(0).getLeftNumber());
			}
			map.addAttribute("colorPackagePriceListItem" + i, priceListItem);
		}

		List<TdCartColorPackage> colors = tdCommonService.getSelectedColorPackage(req);
		map.addAttribute("select_colors", colors);

		map.addAttribute("goodsId", goodsId);
		map.addAttribute("color_package_list", color_package_list);
		map.addAttribute("select_colors", tdCartGoodsService.findByUserIdAndIsColorTrue(user.getId()));
		return "/client/color_package";
	}

	/**
	 * 添加已选调色包的方法
	 * 增加商品id参数 zp 
	 * @author dengxiao
	 */
	@RequestMapping(value = "/color/add")
	public String colorAdd(String colorName, Long quantity, ModelMap map, HttpServletRequest req,Long goodsId) {
		// 获取登陆用户的信息
		String username = (String) req.getSession().getAttribute("username");
		TdUser user = tdUserService.findByUsername(username);
		if (null == user) {
			user = new TdUser();
		}

		colorName = colorName.trim();
		// 获取指定调色包商品
		TdGoods goods = tdGoodsService.findByCode(colorName);
		// 根据价目表id和调色包商品的id查找到指定的调色包价目表项
		TdPriceListItem priceListItem = tdCommonService.getGoodsPrice(req, goods);
		if (null == priceListItem) {
			priceListItem = new TdPriceListItem();
		}
		//调色商品归属商品
		TdGoods good= tdGoodsService.findOne(goodsId);

		// 创建一个已选商品实体，用于存储各项已选数据
		TdCartGoods cartGoods = new TdCartGoods();
		// ********************************开始设置属性*******************************************
		cartGoods.setQuantity(quantity);
		cartGoods.setPrice(priceListItem.getSalePrice());
		cartGoods.setRealPrice(priceListItem.getRealSalePrice());
		cartGoods.setTotalPrice(cartGoods.getPrice() * cartGoods.getQuantity());
		cartGoods.setRealTotalPrice(cartGoods.getRealPrice() * cartGoods.getQuantity());
		cartGoods.setGoodsId(goods.getId());
		cartGoods.setGoodsCoverImageUri(goods.getCoverImageUri());
		cartGoods.setSku(goods.getCode());
		cartGoods.setGoodsTitle(goods.getTitle());
		cartGoods.setIsColor(true);
		cartGoods.setUserId(user.getId());
		cartGoods.setUsername(username);
		cartGoods.setBrandId(goods.getBrandId());
		cartGoods.setBrandTitle(goods.getBrandTitle());
		if(good!=null){
			cartGoods.setOwnerGoodsSku(good.getCode());
		}
		// ********************************设置属性结束*******************************************

		// 查找到所有的已选商品
		List<TdCartGoods> selected_goods = tdCartGoodsService.findByUserId(user.getId());
		Boolean isHave = false;
		// 创建一个布尔对象用于表示当前添加的调色包是不是已选中已经有了的，其初始值为false，表示没有
		for (int i = 0; i < selected_goods.size(); i++) {
			TdCartGoods cart = selected_goods.get(i);
			if (null != cart && null != cart.getGoodsId()
					&& goods.getId().longValue() == cart.getGoodsId().longValue()) {
				//判断调色商品归属商品sku是否一知
				if(good!=null && good.getCode().equals(cart.getOwnerGoodsSku())){
					isHave = true;
					cart.setQuantity(cart.getQuantity() + cartGoods.getQuantity());
					cart.setPrice(cartGoods.getPrice());
					cart.setRealPrice(cartGoods.getRealPrice());
					cart.setTotalPrice(cart.getPrice() * cart.getQuantity());
					cart.setRealPrice(cart.getRealPrice() * cart.getQuantity());
					cart.setIsColor(true);
					tdCartGoodsService.save(cart);
				}
			}
		}
		// 如果没有包含，则直接保存
		if (!isHave) {
			tdCartGoodsService.save(cartGoods);
		}

		map.addAttribute("inventory", goods.getLeftNumber());
		map.addAttribute("unit_price", priceListItem.getSalePrice());
		map.addAttribute("select_colors", tdCartGoodsService.findByUserIdAndIsColorTrue(user.getId()));
		// 获取所有已选商品的数量
		map.addAttribute("selected_number", tdCartGoodsService.countByUserId(user.getId()));
		return "/client/selected_color_package";
	}

	/**
	 * 删除已选调色包的方法
	 * 
	 * @author dengxiao
	 */
	@RequestMapping(value = "/delete/color")
	@ResponseBody
	public Map<String, Object> deleteColor(HttpServletRequest req, Long colorPackageGoodsId) {
		Map<String, Object> res = new HashMap<>();
		res.put("status", -1);

		String username = (String) req.getSession().getAttribute("username");
		TdUser user = tdUserService.findByUsername(username);
		if (null == user) {
			user = new TdUser();
		}

		// 获取所有已经选择的调色包
		List<TdCartGoods> all_color = tdCartGoodsService.findByUserIdAndIsColorTrue(user.getId());

		// 遍历已选调色包，找到指定id的调色包
		for (int i = 0; i < all_color.size(); i++) {
			TdCartGoods cartGoods = all_color.get(i);
			if (null != cartGoods && null != cartGoods.getGoodsId()
					&& cartGoods.getGoodsId().longValue() == colorPackageGoodsId.longValue()) {
				tdCartGoodsService.delete(cartGoods.getId());
			}
		}

		res.put("selected_number", tdCartGoodsService.countByUserId(user.getId()));
		res.put("status", 0);
		return res;
	}

	/**
	 * 将商品加入已选的方法
	 * 
	 * @author dengxiao
	 */
	@RequestMapping(value = "/add/cart")
	@ResponseBody
	// params的格式为：goodsId + quantity - goodsId +quantity - ......
	public Map<String, Object> addCart(HttpServletRequest req, String params) {
		Map<String, Object> res = new HashMap<>();
		res.put("status", -1);
		// 获取登陆用户的信息
		String username = (String) req.getSession().getAttribute("username");
		TdUser user = tdUserService.findByUsername(username);
		if (null == user) {
			user = new TdUser();
		}

		// 获取所有的已选商品
		List<TdCartGoods> selected_goods = tdCartGoodsService.findByUserId(user.getId());

		// param的格式为：goodsId+quantity
		String[] param = params.split("\\-");
		for (int i = 0; i < param.length; i++) {
			String item = param[i];
			String[] goodsId_quantity = item.split("\\+");
			// 获取指定商品
			TdGoods goods = tdGoodsService.findOne(Long.parseLong(goodsId_quantity[0]));
			// 获取指定商品对于用户的价格
			TdPriceListItem priceListItem = tdCommonService.getGoodsPrice(req, goods);
			// 创建一个实体用于存储拆分好的goodsId和quantity
			TdCartGoods cartGoods = new TdCartGoods(Long.parseLong(goodsId_quantity[0]),
					Long.parseLong(goodsId_quantity[1]));
			cartGoods.setGoodsTitle(goods.getTitle());
			cartGoods.setGoodsCoverImageUri(goods.getCoverImageUri());
			cartGoods.setIsWallAccessory(goods.getIsWallAccessory());
			if (null != priceListItem) {
				cartGoods.setPrice(priceListItem.getSalePrice());
				cartGoods.setRealPrice(priceListItem.getRealSalePrice());
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
			Boolean isHave = false;
			// 遍历已选商品的集合，判断新的商品是否已选
			for (int j = 0; j < selected_goods.size(); j++) {
				TdCartGoods tdCartGoods = selected_goods.get(j);
				if (null != tdCartGoods && null != tdCartGoods.getGoodsId()
						&& tdCartGoods.getGoodsId() == Long.parseLong(goodsId_quantity[0])) {
					// 在goodsId相同的情况下就代表已经被选择了，修改被选数量即可
					tdCartGoods.setPrice(cartGoods.getPrice());
					tdCartGoods.setQuantity(tdCartGoods.getQuantity() + Long.parseLong(goodsId_quantity[1]));
					isHave = true;
					tdCartGoods.setTotalPrice(tdCartGoods.getPrice() * tdCartGoods.getQuantity());
					tdCartGoods.setRealPrice(cartGoods.getRealPrice());
					tdCartGoods.setRealTotalPrice(tdCartGoods.getRealPrice() * tdCartGoods.getQuantity());
					tdCartGoods.setIsColor(cartGoods.getIsColor());
					tdCartGoodsService.save(tdCartGoods);
				}
			}
			// 新的商品没有在已选中找到，则将其添加进入已选
			if (!isHave) {
				cartGoods.setTotalPrice(cartGoods.getPrice() * cartGoods.getQuantity());
				cartGoods.setRealTotalPrice(cartGoods.getRealPrice() * cartGoods.getQuantity());
				tdCartGoodsService.save(cartGoods);
			}
		}
		Long number = tdCartGoodsService.countByUserId(user.getId());
		res.put("selected_number", number);
		res.put("stauts", 0);
		return res;
	}

	/**
	 * 查看商品详情的方法（跳转到详情页）
	 * 
	 * @author dengxiao
	 */
	@RequestMapping(value = "/detail/{goodsId}")
	public String goodsDetail(HttpServletRequest req, ModelMap map, @PathVariable Long goodsId) {
		String username = (String) req.getSession().getAttribute("username");
		TdUser user = tdUserService.findByUsernameAndIsEnableTrue(username);
		if (null == user) {
			return "redirect:/login";
		}

		TdGoods goods = tdGoodsService.findOne(goodsId);

		tdCommonService.addUserRecentVisit(req, map, goodsId);

		// 根据门店信息获取用户的价目表
		TdPriceListItem priceListItem = tdCommonService.getGoodsPrice(req, goods);
		map.addAttribute("priceListItem", priceListItem);

		// 获取评论
		List<TdUserComment> user_comment_list = tdUserCommentService.findByGoodsIdAndIsShowable(goodsId);
		map.addAttribute("user_comment_list", user_comment_list);

		// 查询是否收藏该商品
		Boolean isCollect = false;
		TdUserCollect collect = tdUserCollectService.findByUsernameAndGoodsId(username, goodsId);
		if (null != collect) {
			isCollect = true;
		}

		// 获取用户的门店
		TdDiySite diySite = tdCommonService.getDiySite(req);
		List<TdActivity> activity_list = tdActivityService
				.findByGoodsNumberContainingAndDiySiteIdsContainingAndBeginDateBeforeAndFinishDateAfter(
						goods.getId() + "_", diySite.getId() + ",", new Date());

		// 获取客服电话
		List<TdSetting> all = tdSettingService.findAll();
		if (null != all && all.size() >= 1) {
			map.addAttribute("phone", all.get(0).getTelephone());
		}
		//获取商品单店库存
		TdDiySiteInventory  diySiteInventory = tdDiySiteInventoryService.findByGoodsCodeAndRegionIdAndDiySiteIdIsNull(goods.getCode(), diySite.getRegionId());
		
		
		map.addAttribute("diySiteInventory", diySiteInventory);
		map.addAttribute("activity_list", activity_list);
		map.addAttribute("isCollect", isCollect);
		//只判断是否有值 有值不刷新
		req.getSession().setAttribute("noRefresh", "1");
		return "/client/goods_detail";
	}
	
	/**
	 * 判断是否刷新(没用)
	 * 
	 * @author zp
	 */
	@RequestMapping(value = "/listRefresh")
	@ResponseBody
	public Map<String, Object> listRefresh(HttpServletRequest req, Long goodsId) {  
		Map<String, Object> res = new HashMap<>();
		Object obj= req.getSession().getAttribute("noRefresh");
		//1 刷新 2 不刷新
		if(obj==null){
			res.put("type", 1);
		}else{
			res.put("type", 2);
			req.getSession().removeAttribute("noRefresh");
		}
		return res;
	}
	
	/**
	 * 添加收藏的方法
	 *  
	 * @author dengxiao
	 */
	@RequestMapping(value = "/operate/collection")
	@ResponseBody
	public Map<String, Object> addCollection(HttpServletRequest req, Long goodsId) {
		Map<String, Object> res = new HashMap<>();
		res.put("status", -1);
		// 获取登陆用户名
		String username = (String) req.getSession().getAttribute("username");
		// 查找指定商品是否收藏
		TdUserCollect userCollect = tdUserCollectService.findByUsernameAndGoodsId(username, goodsId);
		if (null == userCollect) {
			// 如果没有收藏则添加收藏
			userCollect = new TdUserCollect();
			TdGoods goods = tdGoodsService.findOne(goodsId);
			userCollect.setUsername(username);
			userCollect.setGoodsId(goods.getId());
			userCollect.setGoodsTitle(goods.getTitle());
			userCollect.setGoodsCoverImageUri(goods.getCoverImageUri());
			userCollect.setCollectTime(new Date());
			tdUserCollectService.save(userCollect);
		} else {
			// 如果已经收藏则取消收藏
			tdUserCollectService.delete(userCollect);
		}
		res.put("status", 0);
		return res;
	}

	/**
	 * 跳转到商品搜索页面的方法
	 * 修改商品查询方法  按价格查询暂时隐藏 添加单店库存 zp
	 * @author dengxiao
	 */
	@RequestMapping(value = "/search")
	// param为【排序字段】-【规则1】-【规则2】-【规则3】
	public String goodsSearch(HttpServletRequest req, ModelMap map, String keywords, String param) {
		String username = (String) req.getSession().getAttribute("username");
		TdUser user = tdUserService.findByUsernameAndIsEnableTrue(username);
		if (null == user) {
			return "redirect:/login";
		}
		map.addAttribute("user", user);
		if(StringUtils.isBlank(keywords)){
			return "/client/goods_search";
		}
		//查询一级分类 
		List<String> categoryTitle=new ArrayList<String>();
		TdProductCategory  productCategory= tdProductCategoryService.findByTitle(keywords);
		if(productCategory!=null){
			 //分类存在只查找分类
			 keywords=null;
			 List<TdProductCategory> productCategoryList= tdProductCategoryService.findByParentIdOrderBySortIdAsc(productCategory.getId());
			 if(productCategoryList!=null && productCategoryList.size()>0){
				 for (TdProductCategory tdProductCategory : productCategoryList) {
					 categoryTitle.add(tdProductCategory.getTitle());
				}
			 }
		}
		
//		// 获取用户的门店
		TdDiySite diySite = tdCommonService.getDiySite(req);

		if (null == param) {
			param = "0-0-0-0";
		}

		// 拆分排序字段
		String[] strs = param.split("-");
		String sortFiled = strs[0];
		String rule1 = strs[1];
		String rule2 = strs[2];
		String rule3 = strs[3];
		//当期排序规则号
		int y= Integer.valueOf(sortFiled)+1;

		// 新建一个集合用于存储用户的搜索结果
		List<TdGoods> goods_list = new ArrayList<>();

		// 新建一个集合用于存储显示结果
		List<TdGoods> visible_list = new ArrayList<>();

//		if ("0".equals(sortFiled)) {
//			if ("0".equals(rule1)) {
//				goods_list = tdGoodsService.searchGoodsOrderBySortIdAsc(keywords,cateId);
//				rule1 = "1";
//			} else if ("1".equals(rule1)) {
//				goods_list = tdGoodsService.searchGoodsOrderBySortIdDesc(keywords,cateId);
//				rule1 = "0";
//			}
//		} else if ("1".equals(sortFiled)) {
//			if ("0".equals(rule2)) {
//				goods_list = tdGoodsService.searchGoodsOrderBySalePriceAsc(keywords, diySite.getPriceListId(),cateId);
//				rule2 = "1";
//			} else if ("1".equals(rule2)) {
//				goods_list = tdGoodsService.searchGoodsOrderBySalePriceDesc(keywords, diySite.getPriceListId(),cateId);
//				rule2 = "0";
//			}
//		} else if ("2".equals(sortFiled)) {
//			if ("0".equals(rule3)) {
//				goods_list = tdGoodsService.searchGoodsOrderBySoldNumberAsc(keywords,cateId);
//				rule3 = "1";
//			} else if ("1".equals(rule3)) {
//				goods_list = tdGoodsService.searchGoodsOrderBySoldNumberDesc(keywords,cateId);
//				rule3 = "0";
//			}
//		}
		//查询数据
		goods_list=tdGoodsService.searchGoodsList(keywords, sortFiled, strs[y], categoryTitle);
		//修改排序规则
		if(y==1){
			rule1=( "0".equals(rule1)?"1":"0");
		}else if(y==2){
			rule2=( "0".equals(rule2)?"1":"0");
		}else if(y==3){
			rule3=( "0".equals(rule3)?"1":"0");
		}

		// 遍历集合，获取指定商品的价目表项
		if (null != goods_list) {
			for (int i = 0; i < goods_list.size(); i++) {
				TdGoods goods = goods_list.get(i);
				if (null != goods) {
					// 根据商品的id和价目表id获取指定商品的价目表项
					TdPriceListItem priceListItem = tdCommonService.getGoodsPrice(req, goods);
					if (null != priceListItem) {
						// 判断该件商品是否参与促销
						priceListItem.setIsPromotion(tdCommonService.isJoinActivity(req, goods));
						map.addAttribute("priceListItem" + goods.getId(), priceListItem);
						
						List<TdUnableSale> unable = tdUnableSaleService.findByDiySiteIdAndGoodsId(diySite.getId(), goods.getId());
	
						if (null == unable || unable.size() == 0) {
							visible_list.add(goods);
						}
						//设置商品单店库存
						TdDiySiteInventory diySiteInventory = tdDiySiteInventoryService.findByGoodsCodeAndRegionIdAndDiySiteIdIsNull(goods.getCode(), diySite.getRegionId());
						if(diySiteInventory!=null){
							map.addAttribute("goodInventory" + goods.getId(), diySiteInventory.getInventory());
						}
					}
				}
				
				
			}
		}
		
		
		
		map.addAttribute("selected_rule", Long.parseLong(sortFiled));
		map.addAttribute("rule1", rule1);
		map.addAttribute("rule2", rule2);
		map.addAttribute("rule3", rule3);
		map.addAttribute("keywords", keywords);
		map.addAttribute("goods_list", visible_list);

		Long number = tdCartGoodsService.countByUserId(user.getId());
		// 将已选商品的数量（包括调色包）添加到ModelMap中
		map.addAttribute("selected_number", number);

		return "/client/goods_search";
	}

	/**
	 * 在已选页面删除已选商品/调色包的方法
	 * 
	 * @author dengxiao
	 */
	@RequestMapping(value = "/delete/selected")
	@ResponseBody
	public Map<String, Object> deleteSelected(HttpServletRequest req, Long goodsId) {
		Map<String, Object> res = new HashMap<>();
		res.put("status", -1);

		String username = (String) req.getSession().getAttribute("username");
		TdUser user = tdUserService.findByUsername(username);
		if (null == user) {
			user = new TdUser();
		}

		Double price = 0.0;

		// 获取所有的已选商品
		List<TdCartGoods> selected_goods = tdCartGoodsService.findByUserId(user.getId());

		// 遍历所有的已选商品，查找出指定id的已选
		if (null != selected_goods) {
			for (int i = 0; i < selected_goods.size(); i++) {
				TdCartGoods cartGoods = selected_goods.get(i);
				if (null != cartGoods) {
					if (null != cartGoods.getGoodsId() && cartGoods.getGoodsId().longValue() == goodsId.longValue()) {
						tdCartGoodsService.delete(cartGoods.getId());
						res.put("status", 0);
					} else {
						price += cartGoods.getTotalPrice();
					}
				}
			}
		}

		res.put("all_price", price);

		return res;
	}

	/**
	 * 刷新已选商品页面的方法
	 * 
	 * @author DengXiao
	 */
	@RequestMapping(value = "/select/refresh")
	public String goodsSelectRefresh(HttpServletRequest req, ModelMap map) {
		// 获取登录用户的信息
		String username = (String) req.getSession().getAttribute("username");
		TdUser user = tdUserService.findByUsername(username);
		// 获取所有的已选商品
		if (null != user) {
			List<TdCartGoods> selected_goods = tdCartGoodsService.findByUserId(user.getId());
			map.addAttribute("all_selected", selected_goods);
		}
		return "/client/selected_goods_and_color";
	}

	/**
	 * 立即购买某件商品的方法
	 * 
	 * @author dengxiao
	 */
	@RequestMapping(value = "/buy/now")
	public String buyNow(HttpServletRequest req, ModelMap map, Long goodsId, Long quantity) {
		String username = (String) req.getSession().getAttribute("username");
		TdUser user = tdUserService.findByUsernameAndIsEnableTrue(username);
		if (null == user) {
			return "redirect:/login";
		}
		// 查找到指定的商品
		TdGoods goods = tdGoodsService.findOne(goodsId);

		// 获取指定商品的价目表项
		TdPriceListItem priceListItem = tdCommonService.getGoodsPrice(req, goods);

		// 生成购物车项
		TdCartGoods cartGoods = new TdCartGoods();
		cartGoods.setBrandId(goods.getBrandId());
		cartGoods.setBrandTitle(goods.getBrandTitle());
		cartGoods.setGoodsTitle(goods.getTitle());
		cartGoods.setGoodsCoverImageUri(goods.getCoverImageUri());
		cartGoods.setGoodsId(goods.getId());
		cartGoods.setSku(goods.getCode());
		cartGoods.setIsColor(goods.getIsColorPackage());
		cartGoods.setPrice(priceListItem.getSalePrice());
		cartGoods.setRealPrice(priceListItem.getRealSalePrice());
		cartGoods.setUserId(user.getId());
		cartGoods.setUsername(username);
		cartGoods.setQuantity(quantity);

		// 获取所有的已选
		List<TdCartGoods> cartGoods_list = tdCartGoodsService.findByUsername(username);
		Boolean isHave = false;
		for (TdCartGoods cart : cartGoods_list) {
			if (null != cart && null != cart.getGoodsId()
					&& cart.getGoodsId().longValue() == cartGoods.getGoodsId().longValue()) {
				isHave = true;
				cart.setPrice(cartGoods.getPrice());
				cart.setRealPrice(cartGoods.getRealPrice());
				cart.setQuantity(cart.getQuantity() + cartGoods.getQuantity());
				cart.setTotalPrice(cart.getPrice() * cart.getQuantity());
				cart.setRealTotalPrice(cart.getRealPrice() * cart.getQuantity());
				tdCartGoodsService.save(cart);
				break;
			}
		}

		if (!isHave) {

			cartGoods.setTotalPrice(cartGoods.getPrice() * cartGoods.getQuantity());
			cartGoods.setRealTotalPrice(cartGoods.getRealPrice() * cartGoods.getQuantity());
			tdCartGoodsService.save(cartGoods);
		}

		return "redirect:/user/selected";
	}

	@RequestMapping(value = "/again")
	public String getGoodsAgain(HttpServletRequest req, ModelMap map, Long orderId) {
		// 判断用户是否登录
		String username = (String) req.getSession().getAttribute("username");
		TdUser user = tdUserService.findByUsernameAndIsEnableTrue(username);
		if (null == user) {
			return "redirect:/login";
		}

		// 获取订单中所有的商品
		TdOrder order = tdOrderService.findOne(orderId);
		if (null != order) {
			// 判断是券订单还是实物订单
			Boolean isCoupon = order.getIsCoupon();

			// 获取订单的订单商品
			List<TdOrderGoods> goodsList = order.getOrderGoodsList();
			if (null != goodsList && goodsList.size() > 0) {
				for (TdOrderGoods orderGoods : goodsList) {
					if (null != orderGoods) {
						// 获取指定商品
						TdGoods goods = tdGoodsService.findOne(orderGoods.getGoodsId());

						// 获取价目表信息
						TdPriceListItem priceListItem = tdCommonService.getGoodsPrice(req, goods);
						
						if (priceListItem == null)
						{
							continue;
						}

						// 判断用户的购物车中是否已经存在该件商品
						TdCartGoods existCart = tdCartGoodsService.findByUsernameAndGoodsId(username,
								orderGoods.getGoodsId());

						if (null == existCart) {
							// 生成已选项
							TdCartGoods cart = new TdCartGoods();

							cart.setUsername(username);
							cart.setUserId(user.getId());
							cart.setGoodsId(orderGoods.getGoodsId());
							cart.setGoodsTitle(orderGoods.getGoodsTitle());
							cart.setGoodsCoverImageUri(orderGoods.getGoodsCoverImageUri());
							cart.setQuantity(orderGoods.getQuantity());
							cart.setSku(orderGoods.getSku());
							cart.setIsWallAccessory(orderGoods.getIsWallAccessory());
							// 设置实时价格

							if (null != isCoupon && isCoupon) {
								cart.setPrice(priceListItem.getCouponPrice());
								cart.setRealPrice(priceListItem.getCouponRealPrice());
								cart.setIsCoupon(true);
							} else {
								cart.setPrice(priceListItem.getSalePrice());
								cart.setRealPrice(priceListItem.getRealSalePrice());
							}

							cart.setBrandTitle(orderGoods.getBrandTitle());
							cart.setBrandId(orderGoods.getBrandId());
							cart.setTotalPrice(cart.getPrice() * cart.getQuantity());
							cart.setRealTotalPrice(cart.getRealPrice() * cart.getQuantity());
							cart.setSaleType(0);
							cart.setType(orderGoods.getType());

							tdCartGoodsService.save(cart);
						} else {
							existCart.setQuantity(existCart.getQuantity() + orderGoods.getQuantity());
							// 设置实时价格
							existCart.setPrice(priceListItem.getSalePrice());
							existCart.setRealPrice(priceListItem.getRealSalePrice());

							existCart.setTotalPrice(existCart.getPrice() * existCart.getQuantity());
							existCart.setRealTotalPrice(existCart.getRealPrice() * existCart.getQuantity());
							existCart.setSaleType(0);
							existCart.setType(orderGoods.getType());

							tdCartGoodsService.save(existCart);
						}
					}
				}
			}
		}

		return "redirect:/user/selected";
	}

	/*
	 *********************************** 公共结束************************************************************
	 */

}
