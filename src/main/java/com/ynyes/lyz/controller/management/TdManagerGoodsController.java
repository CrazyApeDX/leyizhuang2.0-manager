package com.ynyes.lyz.controller.management;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
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
import com.ynyes.lyz.entity.TdDiySite;
import com.ynyes.lyz.entity.TdDiySiteInventory;
import com.ynyes.lyz.entity.TdDiySiteInventoryLog;
import com.ynyes.lyz.entity.TdGoods;
import com.ynyes.lyz.entity.TdManager;
import com.ynyes.lyz.entity.TdManagerRole;
import com.ynyes.lyz.entity.TdPriceChangeLog;
import com.ynyes.lyz.entity.TdPriceList;
import com.ynyes.lyz.entity.TdProductCategory;
import com.ynyes.lyz.service.TdArticleService;
import com.ynyes.lyz.service.TdBrandService;
import com.ynyes.lyz.service.TdCityService;
import com.ynyes.lyz.service.TdDiySiteInventoryLogService;
import com.ynyes.lyz.service.TdDiySiteInventoryService;
import com.ynyes.lyz.service.TdDiySiteService;
import com.ynyes.lyz.service.TdGoodsService;
import com.ynyes.lyz.service.TdManagerLogService;
import com.ynyes.lyz.service.TdManagerRoleService;
import com.ynyes.lyz.service.TdManagerService;
import com.ynyes.lyz.service.TdPriceChangeLogService;
import com.ynyes.lyz.service.TdPriceListService;
import com.ynyes.lyz.service.TdProductCategoryService;
import com.ynyes.lyz.service.TdProductService;
import com.ynyes.lyz.util.SiteMagConstant;

/**
 * 后台首页控制器
 * 
 * @author Sharon
 */

@Controller
@RequestMapping(value = "/Verwalter/goods")
public class TdManagerGoodsController extends TdManagerBaseController{

	@Autowired
	TdProductCategoryService tdProductCategoryService;

	@Autowired
	TdArticleService tdArticleService;

	@Autowired
	TdGoodsService tdGoodsService;

	// @Autowired
	// TdWarehouseService tdWarehouseService;

	@Autowired
	TdManagerLogService tdManagerLogService;

	@Autowired
	TdBrandService tdBrandService;

	// @Autowired
	// TdParameterService tdParameterService;

	@Autowired
	TdProductService tdProductService;

	@Autowired
	TdPriceChangeLogService tdPriceChangeLogService;

	@Autowired // zhangji 2015-12-30 16:26:29
	TdPriceListService tdPriceListService;
	
	@Autowired
	private TdManagerService tdManagerService;
	
	@Autowired
	private TdManagerRoleService tdManagerRoleService;
	
	@Autowired
	private TdDiySiteInventoryService tdDiySiteInventoryService;
	
	@Autowired
	private TdCityService tdCityService;
	
	@Autowired
	private TdDiySiteService tdDiySiteService;
	
	@Autowired
	private TdDiySiteInventoryLogService tdDiySiteInventoryLogService;

	@RequestMapping(value = "/refresh")
	public String refreshCategorg() 
	{
		List<TdProductCategory> parentIdIsNullCategory = tdProductCategoryService.findByParentIdNotNullOrderBySortIdAsc();
		for (TdProductCategory tdProductCategory : parentIdIsNullCategory) 
		{
			List<TdGoods> tdGood_list = tdGoodsService.findByInvCategoryId(tdProductCategory.getInvCategoryId());
			if (tdGood_list == null || tdGood_list.size() <= 0) {
				continue;
			}
			for (int i = 0; i < tdGood_list.size(); i++) {
				TdGoods tdGoods = tdGood_list.get(i);
				tdGoods.setCategoryId(tdProductCategory.getId());
				tdGoodsService.save(tdGoods, "1");
			}
		}
		return "redirect:/Verwalter/goods/list";
	}

	@RequestMapping(value = "/edit/parameter/{categoryId}", method = RequestMethod.POST)
	public String parameter(@PathVariable Long categoryId, ModelMap map, HttpServletRequest req) {
		String username = (String) req.getSession().getAttribute("manager");
		if (null == username) {
			return "redirect:/Verwalter/login";
		}

		TdProductCategory tpc = tdProductCategoryService.findOne(categoryId);

		if (null != tpc) {
			Long pcId = tpc.getParamCategoryId();

			if (null != pcId) {
				// map.addAttribute("param_list",
				// tdParameterService.findByCategoryTreeContaining(pcId));

				// 查找产品列表
				map.addAttribute("product_list", tdProductService.findByProductCategoryTreeContaining(categoryId));

				// 查找品牌
				map.addAttribute("brand_list", tdBrandService.findAll());
			}

		}
		return "/site_mag/goods_category_param_list";
	}

	@RequestMapping(value = "/price", method = RequestMethod.GET)
	public String chgPrice(Long goodsId, ModelMap map, HttpServletRequest req) {
		String username = (String) req.getSession().getAttribute("manager");
		if (null == username) {
			return "redirect:/Verwalter/login";
		}

		if (null != goodsId) {
			TdGoods goods = tdGoodsService.findOne(goodsId);

			map.addAttribute("goods", goods);
		}

		return "/site_mag/dialog_goods_change_price";
	}

	@RequestMapping(value = "/price/log", method = RequestMethod.GET)
	public String chgPriceLog(Long goodsId, Integer page, Integer size, String __EVENTTARGET, String __EVENTARGUMENT,
			String __VIEWSTATE, ModelMap map, HttpServletRequest req) {
		String username = (String) req.getSession().getAttribute("manager");

		if (null == username) {
			return "redirect:/Verwalter/login";
		}

		if (null != __EVENTTARGET) {
			switch (__EVENTTARGET) {
			case "btnPage":
				if (null != __EVENTARGUMENT) {
					page = Integer.parseInt(__EVENTARGUMENT);
				}
				break;
			}
		}

		if (null == page || page < 0) {
			page = 0;
		}

		if (null == size || size < 0) {
			size = SiteMagConstant.pageSize;
		}

		if (null != goodsId) {
			Page<TdPriceChangeLog> logPage = tdPriceChangeLogService.findByGoodsIdOrderByIdDesc(goodsId, page, size);

			map.addAttribute("price_log_page", logPage);
			map.addAttribute("goodsId", goodsId);
		}

		map.addAttribute("page", page);
		map.addAttribute("size", size);

		return "/site_mag/dialog_goods_price_log";
	}

	@RequestMapping(value = "/price/set", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> setPrice(Long goodsId, Double outPrice, ModelMap map, HttpServletRequest req) {
		Map<String, Object> res = new HashMap<String, Object>();
		res.put("code", 1);

		String username = (String) req.getSession().getAttribute("manager");

		if (null == username) {
			res.put("message", "请重新登录");
			return res;
		}

		if (null == goodsId) {
			res.put("message", "商品ID不存在");
			return res;
		}

		if (null == outPrice) {
			res.put("message", "价格不存在");
			return res;
		}

		TdGoods goods = tdGoodsService.findOne(goodsId);

		goods.setOutFactoryPrice(outPrice);

		goods = tdGoodsService.save(goods, username);

		tdManagerLogService.addLog("edit", "用户修改商品价格：" + goods.getTitle(), req);

		res.put("code", 0);

		return res;
	}

	@RequestMapping(value = "/list")
	public String goodsList(Integer page, Integer size, Long categoryId, String property, Long brandId, String __EVENTTARGET,
			String __EVENTARGUMENT, String __VIEWSTATE, String keywords, Long[] listId, Integer[] listChkId,
			Double[] listSortId, ModelMap map, HttpServletRequest req) {
		String username = (String) req.getSession().getAttribute("manager");
		if (null == username) {
			return "redirect:/Verwalter/login";
		}
		TdManager tdManager = tdManagerService.findByUsernameAndIsEnableTrue(username);
		TdManagerRole tdManagerRole = null;
		if (null != tdManager && null != tdManager.getRoleId())
		{
			tdManagerRole = tdManagerRoleService.findOne(tdManager.getRoleId());
		}
		if (tdManagerRole == null)
		{
			return "redirect:/Verwalter/login";
		}

		if (null == page || page < 0) {
			page = 0;
		}

		if (null == size || size <= 0) {
			size = SiteMagConstant.pageSize;
			;
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

			case "btnSave":
				btnSave(listId, listSortId, username);
				tdManagerLogService.addLog("edit", "用户修改商品", req);
				break;

			case "btnDelete":
				btnDelete(listId, listChkId);
				tdManagerLogService.addLog("delete", "用户删除商品", req);
				break;

			case "btnPage":
				if (null != __EVENTARGUMENT) {
					page = Integer.parseInt(__EVENTARGUMENT);
				}
				break;

			case "btnOnSale":
				if (null != __EVENTARGUMENT) {
					Long goodsId = Long.parseLong(__EVENTARGUMENT);

					if (null != goodsId) {
						TdGoods goods = tdGoodsService.findOne(goodsId);

						if (null != goods) {
							if (null == goods.getIsOnSale() || !goods.getIsOnSale()) {
								goods.setIsOnSale(true);
								tdManagerLogService.addLog("delete", "用户上架商品:" + goods.getTitle(), req);
							} else {
								goods.setIsOnSale(false);
								tdManagerLogService.addLog("delete", "用户下架商品:" + goods.getTitle(), req);
							}
							tdGoodsService.save(goods, username);
						}
					}
				}
				break;
			}
		}

		if (null != __EVENTTARGET && __EVENTTARGET.equalsIgnoreCase("lbtnViewTxt")
				|| null != __EVENTTARGET && __EVENTTARGET.equalsIgnoreCase("lbtnViewImg")) {
			__VIEWSTATE = __EVENTTARGET;
		}

		map.addAttribute("category_list", tdProductCategoryService.findAll());

		Page<TdGoods> goodsPage = null;

		if (null == categoryId) {
			if (null == keywords || "".equalsIgnoreCase(keywords)) {
				goodsPage = tdGoodsService.findAllOrderBySortIdAsc(page, size);
			} else {
				goodsPage = tdGoodsService.searchAndOrderBySortIdAsc(keywords, page, size);
			}
		} else {
			if (null == keywords || "".equalsIgnoreCase(keywords)) {
				goodsPage = tdGoodsService.findByCategoryIdTreeContainingOrderBySortIdAsc(categoryId, page, size);
			} else {
				goodsPage = tdGoodsService.searchAndFindByCategoryIdOrderBySortIdAsc(keywords, categoryId, page, size);
			}
		}

		if (tdManagerRole.getTitle().equalsIgnoreCase("门店"))
		{
			map.addAttribute("diy_site_manager", tdManager.getId());
		}
		map.addAttribute("content_page", goodsPage);
		
		map.addAttribute("brand_list",tdBrandService.findAll());

		// 参数注回
		map.addAttribute("page", page);
		map.addAttribute("size", size);
		map.addAttribute("keywords", keywords);
		map.addAttribute("__EVENTTARGET", __EVENTTARGET);
		map.addAttribute("__EVENTARGUMENT", __EVENTARGUMENT);
		map.addAttribute("__VIEWSTATE", __VIEWSTATE);
		map.addAttribute("categoryId", categoryId);
		map.addAttribute("brandId", property);
		List<TdGoods> findByCategoryIdIsNull = tdGoodsService.findByCategoryIdIsNull();
		map.addAttribute("left_goods", findByCategoryIdIsNull.size());

		// 图片列表模式
		if (null != __VIEWSTATE && __VIEWSTATE.equals("lbtnViewImg")) {
			return "/site_mag/goods_pic_list";
		}

		// 文字列表模式
		return "/site_mag/goods_txt_list";
	}

	@RequestMapping(value = "/setting/goodsleft/numbers")
	@ResponseBody
	public Map<String, Object> setGoodsLeftNumbers(Integer page,Long defualtInventory)
	{
		Map<String, Object> map=new HashMap<String, Object>();
		String res=null;
		if (page == null)
		{
			page = 0;
		}
		if(defualtInventory==null || defualtInventory<0){
			res="初始库存必须是大于等于〇的数字";
		}
		Integer sum=0;
		List<TdCity> cityList = tdCityService.findAll();
		for (TdCity tdCity : cityList)
		{
			List<TdGoods> goods = tdGoodsService.findBySobId(tdCity.getSobIdCity());
			sum = this.setInventoryByCity(tdCity, 0,goods,defualtInventory,sum);
			List<TdDiySite> diySites = tdDiySiteService.findByCityId(tdCity.getId());
			for (TdDiySite tdDiySite : diySites)
			{
				sum = setInventoryByDiySite(tdDiySite,page,goods,defualtInventory,sum);
			}
		}
		res="本次运行一共添加库存记录"+sum+"条";
		map.put("res", res);
		return map;
	}
	
	/**
	 * 设置城市库存，初始库存为0
	 * @param site
	 * @param page
	 */
	public Integer setInventoryByCity(TdCity city,int page,List<TdGoods>goods,Long defualtInventory,Integer sum)
	{
		if (city == null || city.getSobIdCity() == null)
		{
			return sum;
		}
		List<Long> goodIds = tdDiySiteInventoryService.findGoodsIdByRegionIdAndDiySiteIdIsNull(city.getSobIdCity());
		for (TdGoods tdGoods : goods) 
		{
			if (!goodIds.contains(tdGoods.getId()))
			{
				TdDiySiteInventory inventory = new TdDiySiteInventory();
				inventory.setInventory(defualtInventory);
				inventory.setGoodsCode(tdGoods.getCode());
				inventory.setGoodsId(tdGoods.getId());
				inventory.setCategoryId(tdGoods.getCategoryId());
				inventory.setCategoryIdTree(tdGoods.getCategoryIdTree());
				inventory.setCategoryTitle(tdGoods.getCategoryTitle());
				inventory.setGoodsTitle(tdGoods.getTitle());
				inventory.setRegionId(city.getSobIdCity());
				inventory.setRegionName(city.getCityName());
				tdDiySiteInventoryService.save(inventory);
				sum++;
			}
		}
		return sum;
	}
	/**
	 * 设置门店库存，初始库存为0
	 * @param site
	 * @param page
	 */
	public Integer setInventoryByDiySite(TdDiySite site,int page,List<TdGoods> goods,Long defualtInventory,Integer sum)
	{
		if (site == null || site.getCity() == null)
		{
			return sum;
		}
		List<TdDiySiteInventory> inventories = new ArrayList<>();
		List<Long> existGoodsId = tdDiySiteInventoryService.findGoodsIdByDiySiteId(site.getId());
		for (TdGoods tdGoods : goods)
		{
			if (existGoodsId.contains(tdGoods.getId()))
			{
				continue;
			}
			TdDiySiteInventory inventory = new TdDiySiteInventory();
			inventory.setInventory(defualtInventory);
			inventory.setDiySiteId(site.getId());
			inventory.setDiySiteName(site.getTitle());
			inventory.setGoodsCode(tdGoods.getCode());
			inventory.setGoodsId(tdGoods.getId());
			inventory.setCategoryId(tdGoods.getCategoryId());
			inventory.setCategoryIdTree(tdGoods.getCategoryIdTree());
			inventory.setCategoryTitle(tdGoods.getCategoryTitle());
			inventory.setDiyCode(site.getStoreCode());
			inventory.setGoodsTitle(tdGoods.getTitle());
			inventory.setRegionId(site.getRegionId());
			inventory.setRegionName(site.getCity());
			//tdDiySiteInventoryService.save(inventory);
			inventories.add(inventory);
			sum++;
		}
		tdDiySiteInventoryService.save(inventories);
		return sum;
	}
	
	/**
	 * 隐藏saleType（团购和抢购）修改查询方法   zp
	 * 
	 */
	@RequestMapping(value = "/list", method = RequestMethod.POST)
	public String goodsListPost(Integer page, Integer size, Long categoryId, String property, Long brandId, String saleType,
			String __EVENTTARGET, String __EVENTARGUMENT, String __VIEWSTATE, String keywords, Long[] listId,
			Integer[] listChkId, Double[] listSortId, ModelMap map, HttpServletRequest req) 
	{
		String username = (String) req.getSession().getAttribute("manager");
		if (null == username) {
			return "redirect:/Verwalter/login";
		}

		if (null == page || page < 0) {
			page = 0;
		}

		if (null == size || size <= 0) {
			size = SiteMagConstant.pageSize;
			;
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

			case "btnSave":
				btnSave(listId, listSortId, username);
				tdManagerLogService.addLog("edit", "用户修改商品", req);
				break;

			case "btnDelete":
				btnDelete(listId, listChkId);
				tdManagerLogService.addLog("delete", "用户删除商品", req);
				break;

			case "btnPage":
				if (null != __EVENTARGUMENT) {
					page = Integer.parseInt(__EVENTARGUMENT);
				}
				break;

			case "btnOnSale":
				if (null != __EVENTARGUMENT) {
					Long goodsId = Long.parseLong(__EVENTARGUMENT);

					if (null != goodsId) {
						TdGoods goods = tdGoodsService.findOne(goodsId);

						if (null != goods) {
							if (null == goods.getIsOnSale() || !goods.getIsOnSale()) {
								goods.setIsOnSale(true);
								goods.setOnSaleTime(new Date());
								tdManagerLogService.addLog("delete", "用户上架商品:" + goods.getTitle(), req);
							} else {
								goods.setIsOnSale(false);
								tdManagerLogService.addLog("delete", "用户下架商品:" + goods.getTitle(), req);
							}
							tdGoodsService.save(goods, username);
						}
					}
				}
				break;
			}
		}

		if (null != __EVENTTARGET && __EVENTTARGET.equalsIgnoreCase("lbtnViewTxt")
				|| null != __EVENTTARGET && __EVENTTARGET.equalsIgnoreCase("lbtnViewImg")) {
			__VIEWSTATE = __EVENTTARGET;
		}

		map.addAttribute("category_list", tdProductCategoryService.findAll());

		Page<TdGoods> goodsPage = null;

		Boolean isOnSale=null;
		if ("isOnSale".equalsIgnoreCase(property)) {
			isOnSale=true;
		}else if("isNotOnSale".equalsIgnoreCase(property)){
			isOnSale=false;
		}
		
		//查询商品
		goodsPage=tdGoodsService.searchGoodsList(keywords, brandId, categoryId, page, size,isOnSale);
		
		
		map.addAttribute("content_page", goodsPage);

		// 参数注回
		map.addAttribute("page", page);
		map.addAttribute("size", size);
		map.addAttribute("keywords", keywords);
		map.addAttribute("__EVENTTARGET", __EVENTTARGET);
		map.addAttribute("__EVENTARGUMENT", __EVENTARGUMENT);
		map.addAttribute("__VIEWSTATE", __VIEWSTATE);
		map.addAttribute("categoryId", categoryId);
		map.addAttribute("property", property);
		map.addAttribute("brand_list",tdBrandService.findAll());
		map.addAttribute("brandId", brandId);

		// 图片列表模式
		if (null != __VIEWSTATE && __VIEWSTATE.equals("lbtnViewImg")) {
			return "/site_mag/goods_pic_list";
		}

		// 文字列表模式
		return "/site_mag/goods_txt_list";
	}

	@RequestMapping(value = "/list/dialog/{type}")
	public String goodsListDialog(@PathVariable String type, String keywords, Long categoryId, Integer page,
			Long priceId, Integer size, Integer total, String __EVENTTARGET, String __EVENTARGUMENT, String __VIEWSTATE,
			ModelMap map, HttpServletRequest req,Long cityId) {
		String username = (String) req.getSession().getAttribute("manager");
		if (null == username) {
			return "redirect:/Verwalter/login";
		}
		if (null != __EVENTTARGET) {
			if (__EVENTTARGET.equalsIgnoreCase("btnPage")) {
				if (null != __EVENTARGUMENT) {
					page = Integer.parseInt(__EVENTARGUMENT);
				}
			} else if (__EVENTTARGET.equalsIgnoreCase("btnSearch")) {

			} else if (__EVENTTARGET.equalsIgnoreCase("categoryId")) {

			}
		}

		if (null == page || page < 0) {
			page = 0;
		}

		if (null == size || size <= 0) {
			size = SiteMagConstant.pageSize;
			;
		}

		if (null != keywords) {
			keywords = keywords.trim();
		}

		Page<TdGoods> goodsPage = null;
		//查询条件 价目表头id列表 
		List<Long> priceListIdList=new ArrayList<Long>();
		//添加一个不存在的Id 避免空值报错
		priceListIdList.add(-1L);
		//查询条件 商品类型id列表 
		List<Long> categoryIdList=new ArrayList<Long>();
		//添加一个不存在的Id 避免空值报错
		categoryIdList.add(-1L);
		//判断是否选择城市
		if(cityId==null){//不选择城市就不
//			List<TdPriceList> priceList= tdPriceListService.findAll();
//			if(priceList!=null && priceList.size()>0){
//				//循环价目表头添加到查询条件中
//				for (TdPriceList tdPriceList : priceList) {
//					priceListIdList.add(tdPriceList.getListHeaderId());
//				}
//			}
		}else{
			//查询选择的城市
			TdCity city= tdCityService.findOne(cityId);
			if(city!=null){
				List<TdPriceList> priceList= tdPriceListService.findByCityId(city.getSobIdCity());
				if(priceList!=null && priceList.size()>0){
					//循环价目表头添加到查询条件中
					for (TdPriceList tdPriceList : priceList) {
						priceListIdList.add(tdPriceList.getListHeaderId());
					}
				}
			}
		}
		
		if (null == categoryId || categoryId==-1){
			List<TdProductCategory> productCategoryList= tdProductCategoryService.findAll();
			if(productCategoryList!=null && productCategoryList.size()>0){
				//循环商品类型id添加到查询条件中
				for (TdProductCategory tdProductCategory : productCategoryList) {
					categoryIdList.add(tdProductCategory.getId());
				}
			}
		}else{
			TdProductCategory productCategory= tdProductCategoryService.findOne(categoryId);
			//1级目录
			if(productCategory!=null &&productCategory.getParentId()==null){
				//查询2级目录
				 List<TdProductCategory> productCategoryList= tdProductCategoryService.findByParentIdOrderBySortIdAsc(productCategory.getId());
				//循环商品类型id添加到查询条件中
				 for (TdProductCategory tdProductCategory : productCategoryList) {
					 categoryIdList.add(tdProductCategory.getId());
				}
			}else{//2级目录
				 categoryIdList.add(productCategory.getId());
			}
		}
		//关键字查询 为空查询全部
		if(null == keywords || "".equalsIgnoreCase(keywords)){
			keywords="";
		}
		
		goodsPage = tdGoodsService.queryAllOrderBySortIdAsc(priceListIdList, categoryIdList, keywords, page, size);
			

		map.addAttribute("goods_page", goodsPage);

		// 参数注回
		map.addAttribute("category_list", tdProductCategoryService.findAll());
		map.addAttribute("page", page);
		map.addAttribute("size", size);
		map.addAttribute("total", total);
		map.addAttribute("keywords", keywords);
		map.addAttribute("categoryId", categoryId);
		map.addAttribute("__EVENTTARGET", __EVENTTARGET);
		map.addAttribute("__EVENTARGUMENT", __EVENTARGUMENT);
		map.addAttribute("__VIEWSTATE", __VIEWSTATE);

		if (null != type && type.equalsIgnoreCase("gift")) {
			return "/site_mag/dialog_goods_gift_list";
		} else if (null != type && type.equalsIgnoreCase("price")) {
			if (null != priceId) {
				TdPriceList pricelist = tdPriceListService.findOne(priceId);
				map.addAttribute("pricelist", pricelist);
			}
			return "/site_mag/dialog_price_list";
		}else if (null != type && type.equalsIgnoreCase("module")){
			return "/site_mag/dialog_module_list";
		}

		return "/site_mag/dialog_goods_combination_list";
	}

	@RequestMapping(value = "/edit")
	public String goodsEdit(Long pid, Long id, String __EVENTTARGET, String __EVENTARGUMENT, String __VIEWSTATE,
			ModelMap map, HttpServletRequest req) {
		String username = (String) req.getSession().getAttribute("manager");
		if (null == username) {
			return "redirect:/Verwalter/login";
		}

		map.addAttribute("__EVENTTARGET", __EVENTTARGET);
		map.addAttribute("__EVENTARGUMENT", __EVENTARGUMENT);
		map.addAttribute("__VIEWSTATE", __VIEWSTATE);

		map.addAttribute("category_list", tdProductCategoryService.findAll());

		if (null != id) {
			TdGoods tdGoods = tdGoodsService.findOne(id);

			if (null != tdGoods) {
				// 参数列表
				TdProductCategory tpc = tdProductCategoryService.findOne(tdGoods.getCategoryId());

				if (null != tpc && null != tpc.getParamCategoryId()) {
					// map.addAttribute("param_list",
					// tdParameterService.findByCategoryTreeContaining(tpc.getParamCategoryId()));
				}

				// 查找产品列表
				map.addAttribute("product_list",
						tdProductService.findByProductCategoryTreeContaining(tdGoods.getCategoryId()));

				// 查找品牌
				map.addAttribute("brand_list", tdBrandService.findAll());

				// 查找出所有的调色包产品，按照排序号正序排序
				List<TdGoods> color_list = tdGoodsService.findByIsColorPackageTrueOrderBySortIdAsc();
				map.addAttribute("color_list", color_list);

				// 获取指定商品可调色调色包
				String colorPackageSku = tdGoods.getColorPackageSku();

				List<String> package_sku = new ArrayList<>();

				// 拆分这个字段
				if (null != colorPackageSku && !"".equals(colorPackageSku)) {
					String[] skus = colorPackageSku.split(",");
					if (null != skus && skus.length > 0) {
						for (String sku : skus) {
							if (null != sku && !"".equals(sku)) {
								package_sku.add(colorPackageSku);
							}
						}
					}
				}

				map.addAttribute("package_sku", package_sku);
				// map.addAttribute("warehouse_list",
				// tdWarehouseService.findAll());

				if (null != tdGoods.getProductId()) {
					map.addAttribute("product", tdProductService.findOne(tdGoods.getProductId()));
				}

				map.addAttribute("goods", tdGoods);
			}
		}
		TdManager tdManager = tdManagerService.findByUsernameAndIsEnableTrue(username);
		TdManagerRole tdManagerRole = null;
		if (null != tdManager && null != tdManager.getRoleId())
		{
			tdManagerRole = tdManagerRoleService.findOne(tdManager.getRoleId());
		}
		if (tdManagerRole == null)
		{
			return "redirect:/Verwalter/login";
		}
		if (tdManagerRole.getTitle().equalsIgnoreCase("门店"))
		{
			map.addAttribute("diy_site_manager", tdManager.getId());
		}
		return "/site_mag/goods_edit";
	}

	@RequestMapping(value = "/copy")
	public String goodsCopy(TdGoods tdGoods, String __VIEWSTATE, ModelMap map, HttpServletRequest req) {
		String username = (String) req.getSession().getAttribute("manager");
		if (null == username) {
			return "redirect:/Verwalter/login";
		}

		map.addAttribute("__VIEWSTATE", __VIEWSTATE);

		if (null != tdGoods) {
			TdGoods newGoods = new TdGoods();

			newGoods.setAfterMarketService(tdGoods.getAfterMarketService());
			newGoods.setAveragePoints(tdGoods.getAveragePoints());
			// newGoods.setBrandId(tdGoods.getBrandId());
			newGoods.setBrandTitle(tdGoods.getBrandTitle());
			newGoods.setCategoryId(tdGoods.getCategoryId());
			newGoods.setCategoryIdTree(tdGoods.getCategoryIdTree());
			newGoods.setCategoryTitle(tdGoods.getCategoryTitle());
			newGoods.setCode(tdGoods.getCode());
			newGoods.setCombList(null);
			newGoods.setConfiguration(tdGoods.getConfiguration());
			newGoods.setCostPrice(tdGoods.getCostPrice());
			newGoods.setCoverImageHeight(tdGoods.getCoverImageHeight());
			newGoods.setCoverImageWidth(tdGoods.getCoverImageWidth());
			newGoods.setCoverImageUri(tdGoods.getCoverImageUri());
			newGoods.setCreateTime(new Date());
			newGoods.setDeliveryArea(tdGoods.getDeliveryArea());
			newGoods.setDetail(tdGoods.getDetail());
			newGoods.setFlashSaleImage(tdGoods.getFlashSaleImage());
			newGoods.setFlashSaleLeftNumber(tdGoods.getFlashSaleLeftNumber());
			newGoods.setFlashSalePrice(tdGoods.getFlashSalePrice());
			newGoods.setFlashSaleSoldNumber(tdGoods.getFlashSaleSoldNumber());
			newGoods.setFlashSaleStartTime(tdGoods.getFlashSaleStartTime());
			newGoods.setFlashSaleStopTime(tdGoods.getFlashSaleStopTime());
			newGoods.setGroupSaleImage(tdGoods.getGroupSaleImage());
			newGoods.setGroupSaleLeftNumber(tdGoods.getGroupSaleLeftNumber());
			newGoods.setGroupSalePrice(tdGoods.getGroupSalePrice());
			newGoods.setGroupSaleSoldNumber(tdGoods.getGroupSaleSoldNumber());
			newGoods.setGroupSaleStartTime(tdGoods.getGroupSaleStartTime());
			newGoods.setGroupSaleStopTime(tdGoods.getGroupSaleStopTime());
			newGoods.setIncludePrice(tdGoods.getIncludePrice());
			newGoods.setIsFlashSale(tdGoods.getIsFlashSale());
			newGoods.setIsGroupSale(tdGoods.getIsGroupSale());
			newGoods.setIsHot(tdGoods.getIsHot());
			newGoods.setIsNew(tdGoods.getIsNew());
			newGoods.setIsOnSale(tdGoods.getIsOnSale());
			newGoods.setIsRecommendIndex(tdGoods.getIsRecommendIndex());
			newGoods.setIsRecommendType(tdGoods.getIsRecommendType());
			newGoods.setIsSpecialPrice(tdGoods.getIsSpecialPrice());
			newGoods.setLeftNumber(tdGoods.getLeftNumber());
			newGoods.setMarketPrice(tdGoods.getMarketPrice());
			newGoods.setName(tdGoods.getName());
			newGoods.setNumberDecType(tdGoods.getNumberDecType());
			newGoods.setOnSaleTime(tdGoods.getOnSaleTime());
			newGoods.setOutFactoryPrice(tdGoods.getOutFactoryPrice());
			newGoods.setParamValueCollect(tdGoods.getParamValueCollect());
			newGoods.setPointLimited(tdGoods.getPointLimited());
			newGoods.setPriceUnit(tdGoods.getPriceUnit());
			// newGoods.setProductId(tdGoods.getProductId());
			newGoods.setPromotion(tdGoods.getPromotion());
			newGoods.setReturnPoints(tdGoods.getReturnPoints());
			newGoods.setReturnPrice(tdGoods.getReturnPrice());
			newGoods.setSalePrice(tdGoods.getSalePrice());
			newGoods.setSaleType(tdGoods.getSaleType());
			newGoods.setSelectOneValue(tdGoods.getSelectOneValue());
			newGoods.setSelectThreeValue(tdGoods.getSelectThreeValue());
			newGoods.setSelectTwoValue(tdGoods.getSelectTwoValue());
			newGoods.setSeoDescription(tdGoods.getSeoDescription());
			newGoods.setSeoKeywords(tdGoods.getSeoKeywords());
			newGoods.setSeoTitle(tdGoods.getSeoTitle());
			newGoods.setService(tdGoods.getService());
			newGoods.setShowPictures(tdGoods.getShowPictures());
			newGoods.setSoldNumber(tdGoods.getSoldNumber());
			newGoods.setSortId(tdGoods.getSortId());
			newGoods.setSubTitle(tdGoods.getSubTitle());
			newGoods.setTags(tdGoods.getTags());
			newGoods.setTitle(tdGoods.getTitle());
			newGoods.setTotalComb(0);
			newGoods.setTotalComments(0L);
			newGoods.setTotalGift(0);
			newGoods.setUserLevelLimit(tdGoods.getUserLevelLimit());
			newGoods.setVideoUri(tdGoods.getVideoUri());
			newGoods.setWarehouseId(tdGoods.getWarehouseId());
			newGoods.setWarehouseTitle(tdGoods.getWarehouseTitle());

			tdGoodsService.save(newGoods, username);
			tdManagerLogService.addLog("add", "用户复制商品", req);
		}

		return "redirect:/Verwalter/goods/list";
	}

	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public String save(TdGoods tdGoods, String[] hid_photo_name_show360, String __EVENTTARGET, String __EVENTARGUMENT,
			String __VIEWSTATE, String menuId, String channelId, ModelMap map, Boolean isRecommendIndex,
			Long isColorful, Long isColorPackage, Boolean isRecommendType, Boolean isHot, Boolean isNew,
			Boolean isSpecialPrice, HttpServletRequest req) {
		String username = (String) req.getSession().getAttribute("manager");
		if (null == username) {
			return "redirect:/Verwalter/login";
		}

		String uris = parsePicUris(hid_photo_name_show360);

		tdGoods.setShowPictures(uris);

		String type = null;

		if (null == tdGoods.getId()) {
			type = "add";
		} else {
			type = "edit";
		}

		if (null != isColorful && isColorful.longValue() == 0L) {
			tdGoods.setIsColorful(true);
		}

		if (null != isColorful && isColorful.longValue() == 1L) {
			tdGoods.setIsColorful(false);
		}

		if (null != isColorPackage && isColorPackage.longValue() == 0L) {
			tdGoods.setIsColorPackage(true);
		}

		if (null != isColorPackage && isColorPackage.longValue() == 1L) {
			tdGoods.setIsColorPackage(false);
		}

		/**
		 * @author lc
		 * @注释：推荐类型修改
		 */
		if (null != isRecommendIndex && isRecommendIndex) {
			tdGoods.setIsRecommendIndex(true);
		} else {
			tdGoods.setIsRecommendIndex(false);
		}
		if (null != isRecommendType && isRecommendType) {
			tdGoods.setIsRecommendType(true);
		} else {
			tdGoods.setIsRecommendType(false);
		}
		if (null != isHot && isHot) {
			tdGoods.setIsHot(true);
		} else {
			tdGoods.setIsHot(false);
		}
		if (null != isNew && isNew) {
			tdGoods.setIsNew(true);
		} else {
			tdGoods.setIsNew(false);
		}
		if (null != isSpecialPrice && isSpecialPrice) {
			tdGoods.setIsSpecialPrice(true);
		} else {
			tdGoods.setIsSpecialPrice(false);
		}

		tdGoodsService.save(tdGoods, username);

		tdManagerLogService.addLog(type, "用户修改商品", req);

		return "redirect:/Verwalter/goods/list?__EVENTTARGET=" + __EVENTTARGET + "&__EVENTARGUMENT=" + __EVENTARGUMENT
				+ "&__VIEWSTATE=" + __VIEWSTATE;
	}

	/**
	 * 
	 * 库存管理日志
	 * 
	 * @param req
	 * @param map
	 * @param page
	 * @param size
	 * @param action
	 * @param listId
	 * @param listChkId
	 * @return
	 */
	@RequestMapping(value = "/inventory/log")
	public String inventoryLog(HttpServletRequest req, ModelMap map, Integer page, Integer size, String __EVENTTARGET,
			String __EVENTARGUMENT, String __VIEWSTATE, String keywords,Long regionId, Long siteId, String action, Long[] listId, Integer[] listChkId) {
		String username = (String) req.getSession().getAttribute("manager");
		if (null == username) {
			return "redirect:/Verwalter/login";
		}
		TdManager tdManager = tdManagerService.findByUsernameAndIsEnableTrue(username);
		TdManagerRole tdManagerRole = null;
		String diyCode = null;
		if (null != tdManager && null != tdManager.getRoleId())
		{
			tdManagerRole = tdManagerRoleService.findOne(tdManager.getRoleId());
			if (tdManagerRole == null)
			{
				return "redirect:/Verwalter/login";
			}
			if (tdManagerRole.getTitle().equalsIgnoreCase("门店")) 
			{
				diyCode = tdManager.getDiyCode();
			}
			if (tdManagerRole.getTitle().equalsIgnoreCase("郑州门店")) 
			{
				diyCode = tdManager.getDiyCode();
			}
		}
		if (null != __EVENTTARGET) {
			if (__EVENTTARGET.equalsIgnoreCase("btnDelete")) {
				btnDeleteLog(listId, listChkId);
				tdManagerLogService.addLog("delete", "删除管理日志", req);
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
		List<TdCity> cityList= tdCityService.findAll();
		/*List<TdDiySite> diySiteList= tdDiySiteService.findAll();*/
		map.addAttribute("city_list", cityList);
		map.addAttribute("regionId",regionId);
		map.addAttribute("siteId", siteId);
		map.addAttribute("page", page);
		map.addAttribute("size", size);
		map.addAttribute("action", action);
		map.addAttribute("keywords",keywords);
		map.addAttribute("__EVENTTARGET", __EVENTTARGET);
		map.addAttribute("__EVENTARGUMENT", __EVENTARGUMENT);
		map.addAttribute("__VIEWSTATE", __VIEWSTATE);
		
		List<TdDiySite> diysite_list = new ArrayList<>();
		if (regionId != null)
		{
			diysite_list = tdDiySiteService.findByRegionIdAndIsEnableOrderBySortIdAsc(regionId);
		}
		else
		{
			diysite_list = tdDiySiteService.findAll();
		}
		map.addAttribute("site_list", diysite_list);
		if (siteId != null && siteId != -1)
		{
			map.addAttribute("log_page", tdDiySiteInventoryLogService.findBySiteIdAndKeywords(siteId, keywords, page, size));
		}
		else if (regionId != null)
		{
			if (siteId != null && siteId == -1)
			{
				map.addAttribute("log_page", tdDiySiteInventoryLogService.findByRegionIdOnlyAndKeywords(regionId,keywords, page, size));
			}
			else
			{
				map.addAttribute("log_page", tdDiySiteInventoryLogService.findByRegionIdAndKeywords(regionId,keywords, page, size));
			}
		}
		else
		{
			if (siteId != null && siteId == -1)
			{
				map.addAttribute("log_page", tdDiySiteInventoryLogService.findCityInventoryAndKeywords(keywords, page,size));
			}
			else
				map.addAttribute("log_page", tdDiySiteInventoryLogService.findAll(keywords, page,size));
		}
		/*map.addAttribute("log_page", tdDiySiteInventoryLogService.findAll(page, size));*/
		TdDiySite diySite = tdDiySiteService.findByStoreCode(diyCode);
		if (diySite != null)
		{
			map.addAttribute("log_page", tdDiySiteInventoryLogService.findBySiteIdAndKeywords(diySite.getId(), keywords, page, size));
			map.addAttribute("is_diy_site_bool",true);
			diysite_list.clear();
			diysite_list.add(diySite);
			map.addAttribute("site_list", diysite_list);
		}

		return "site_mag/inventory_log";
	}
	/**
	 *  库存列表
	 * @param req
	 * @param map
	 * @param page
	 * @param size
	 * @param __EVENTTARGET
	 * @param __EVENTARGUMENT
	 * @param __VIEWSTATE
	 * @param action
	 * @param listId
	 * @param listChkId
	 * @return
	 */
	@RequestMapping(value = "/inventory/list")
	public String inventoryList(HttpServletRequest req, ModelMap map, Integer page, Integer size, String __EVENTTARGET,String __EVENTARGUMENT, String __VIEWSTATE, String keywords,Long regionId, Long siteId, Long[] listId, Integer[] listChkId,Long[] listInventory)
	{
		String username = (String) req.getSession().getAttribute("manager");
		if (null == username)
		{
			return "redirect:/Verwalter/login";
		}
		
		TdManager tdManager = tdManagerService.findByUsernameAndIsEnableTrue(username);
		TdManagerRole tdManagerRole = null;
		String diyCode = null;
		if (null != tdManager && null != tdManager.getRoleId())
		{
			tdManagerRole = tdManagerRoleService.findOne(tdManager.getRoleId());
			if (tdManagerRole == null)
			{
				return "redirect:/Verwalter/login";
			}
			if (tdManagerRole.getTitle().equalsIgnoreCase("门店")) 
			{
				diyCode = tdManager.getDiyCode();
			}
			if (tdManagerRole.getTitle().equalsIgnoreCase("郑州门店")) 
			{
				diyCode = tdManager.getDiyCode();
			}
		}
		
		if (null != __EVENTTARGET) 
		{
			if (__EVENTTARGET.equalsIgnoreCase("btnDelete"))
			{
//				btnDeleteLog(listId, listChkId);
				tdManagerLogService.addLog("delete", "删除管理日志", req);
			} 
			else if (__EVENTTARGET.equalsIgnoreCase("btnPage")) 
			{
				if (null != __EVENTARGUMENT) 
				{
					page = Integer.parseInt(__EVENTARGUMENT);
				}
			}
			else if (__EVENTTARGET.equalsIgnoreCase("btnInventory")) 
			{
				if (diyCode == null)
				{
					this.btnChangeInventory(listChkId,listId,listInventory,req);
				}
			}
		}

		if (null == page || page < 0) 
		{
			page = 0;
		}

		if (null == size || size <= 0) 
		{
			size = SiteMagConstant.pageSize;
		}

		map.addAttribute("page", page);
		map.addAttribute("size", size);
		map.addAttribute("regionId",regionId);
		map.addAttribute("siteId", siteId);
		map.addAttribute("keywords",keywords);
		map.addAttribute("__EVENTTARGET", __EVENTTARGET);
		map.addAttribute("__EVENTARGUMENT", __EVENTARGUMENT);
		map.addAttribute("__VIEWSTATE", __VIEWSTATE);
		map.addAttribute("city_list", tdCityService.findAll());
		List<TdDiySite> diysite_list = new ArrayList<>();
		if (regionId != null)
		{
			diysite_list = tdDiySiteService.findByRegionIdAndIsEnableOrderBySortIdAsc(regionId);
		}
		else
		{
			diysite_list = tdDiySiteService.findAll();
		}
		
		if (siteId != null && siteId != -1)
		{
			map.addAttribute("inventory_page", tdDiySiteInventoryService.findBySiteIdAndKeywords(siteId, keywords, page, size));
		}
		else if (regionId != null)
		{
			if (siteId != null && siteId == -1)
			{
				map.addAttribute("inventory_page", tdDiySiteInventoryService.findByRegionIdOnlyAndKeywords(regionId,keywords, page, size));
			}
			else
			{
				map.addAttribute("inventory_page", tdDiySiteInventoryService.findByRegionIdAndKeywords(regionId,keywords, page, size));
			}
		}
		else
		{
			if (siteId != null && siteId == -1)
			{
				map.addAttribute("inventory_page", tdDiySiteInventoryService.findCityInventoryAndKeywords(keywords, page,size));
			}
			else
				map.addAttribute("inventory_page", tdDiySiteInventoryService.findAll(keywords, page,size));
		}
		
		map.addAttribute("site_list", diysite_list);
		
		TdDiySite diySite = tdDiySiteService.findByStoreCode(diyCode);
		if (diySite != null)
		{
			map.addAttribute("inventory_page", tdDiySiteInventoryService.findBySiteIdAndKeywords(diySite.getId(), keywords, page, size));
			map.addAttribute("is_diy_site_bool",true);
			diysite_list.clear();
			diysite_list.add(diySite);
			map.addAttribute("site_list", diysite_list);
		}
		
		
		return "site_mag/inventory_list";
	}
	
	/**
	 *库存可用量报表
	 * @param req
	 * @param map
	 * @param response
	 * @param cityCode
	 * @param diyCode
	 * @param keywords
	 * @param type 1：城市可用量 2：门店可用量
	 * @return
	 */
	@RequestMapping(value = "/diysiteDowndata")
	@ResponseBody
	public String diysiteDowndata(HttpServletRequest req, ModelMap map,HttpServletResponse response, 
			Long cityCode, Long diyCode,String keywords,Integer type) {

		String username = (String) req.getSession().getAttribute("manager");
		if (null == username) {
			return "redirect:/Verwalter/login";
		}
		
		Map<Long, String> brandMap = new HashMap<>();
		
		List<TdGoods> goodsList=tdGoodsService.findAll();
		
		for (TdGoods tdGoods : goodsList)
		{
			brandMap.put(tdGoods.getId(), tdGoods.getBrandTitle());
		}
		
		
		if(type!=null){
			
			if(type==1){
				//报表数据
		    	List<TdDiySiteInventory> diySiteInventoryList = tdDiySiteInventoryService.searchList(cityCode,null,keywords,type);
				// 第一步，创建一个webbook，对应一个Excel文件
				HSSFWorkbook wb = new HSSFWorkbook();
				// 第二步，在webbook中添加一个sheet,对应Excel文件中的sheet
				HSSFSheet sheet = wb.createSheet("城市库存报表");
				// 第三步，在sheet中添加表头第0行,注意老版本poi对Excel的行数列数有限制short
				// 列宽
				int[] widths = { 13, 18, 13, 13, 13, 15, 13, 11, 19, 11, 15, 25, 13, 13, 13, 40, 40 };
				sheetColumnWidth(sheet, widths);

				// 第四步，创建单元格，并设置值表头 设置表头居中
				HSSFCellStyle style = wb.createCellStyle();
				style.setAlignment(HSSFCellStyle.ALIGN_CENTER); // 创建一个居中格式
				style.setWrapText(true);
				
				HSSFRow row = sheet.createRow((int) 0);

				String[] cellValues = { "城市","品牌","商品编码", "商品名称", "数量"};
				cellDates(cellValues, style, row);
				Integer i = 0;
				
				for (TdDiySiteInventory diySite : diySiteInventoryList) {
					row = sheet.createRow((int) i + 1);
					row.createCell(0).setCellValue(objToString(diySite.getRegionName()));
					row.createCell(1).setCellValue(objToString(brandMap.get(diySite.getGoodsId())));
					row.createCell(2).setCellValue(objToString(diySite.getGoodsCode()));
					row.createCell(3).setCellValue(objToString(diySite.getGoodsTitle()));
					row.createCell(4).setCellValue(objToString(diySite.getInventory()));
					i++;
				}

				String exportAllUrl = SiteMagConstant.backupPath;
				download(wb, exportAllUrl, response, "城市库存报表");
			}else if(type==2){
				//报表数据
				List<TdDiySiteInventory> diySiteInventoryList = tdDiySiteInventoryService.searchList(null,diyCode,keywords,type);
				// 第一步，创建一个webbook，对应一个Excel文件
				HSSFWorkbook wb = new HSSFWorkbook();
				// 第二步，在webbook中添加一个sheet,对应Excel文件中的sheet
				HSSFSheet sheet = wb.createSheet("门店库存报表");
				// 第三步，在sheet中添加表头第0行,注意老版本poi对Excel的行数列数有限制short
				// 列宽
				int[] widths = { 13, 18, 13, 13, 13, 15, 13, 11, 19, 11, 15, 25, 13, 13, 13, 40, 40 };
				sheetColumnWidth(sheet, widths);

				// 第四步，创建单元格，并设置值表头 设置表头居中
				HSSFCellStyle style = wb.createCellStyle();
				style.setAlignment(HSSFCellStyle.ALIGN_CENTER); // 创建一个居中格式
				style.setWrapText(true);

				// 优惠券名称、金额、领卷时间、领用用户、是否使用、使用的时间、使用订单号
				HSSFRow row = sheet.createRow((int) 0);

				String[] cellValues = { "门店名称","品牌","商品编码", "商品名称", "数量"};
				cellDates(cellValues, style, row);
				Integer i = 0;
				for (TdDiySiteInventory diySite : diySiteInventoryList) {
					row = sheet.createRow((int) i + 1);
					row.createCell(0).setCellValue(objToString(diySite.getDiySiteName()));
					row.createCell(1).setCellValue(objToString(brandMap.get(diySite.getGoodsId())));
					row.createCell(2).setCellValue(objToString(diySite.getGoodsCode()));
					row.createCell(3).setCellValue(objToString(diySite.getGoodsTitle()));
					row.createCell(4).setCellValue(objToString(diySite.getInventory()));
					i++;
				}

				String exportAllUrl = SiteMagConstant.backupPath;
				download(wb, exportAllUrl, response, "门店库存报表");

			}
		}

		return "";
	}
	
	/**
	 * 库存变更报表
	 * @param req
	 * @param map
	 * @param response
	 * @param cityCode
	 * @param diyCode
	 * @param keywords
	 * @param type 1：城市 2：门店
	 * @param begindata
	 * @param enddata
	 * @return
	 */
	@RequestMapping(value = "/diysiteLogDowndata")
	@ResponseBody
	public String diysiteLogDowndata(HttpServletRequest req, ModelMap map,HttpServletResponse response, 
			Long cityCode, Long diyCode,String keywords,Integer type,String begindata,String enddata) {

		String username = (String) req.getSession().getAttribute("manager");
		if (null == username) {
			return "redirect:/Verwalter/login";
		}
		if(type!=null){
			Date begin = stringToDate(begindata,null);
			Date end = stringToDate(enddata,null);
			//设置默认时间
			if(null==begin){
				begin=getStartTime();
			}
			if(null==end){
				end=getEndTime();
			}
			if(type==1){
				//报表数据
		    	List<TdDiySiteInventoryLog> diySiteInventoryList = tdDiySiteInventoryLogService.searchList(cityCode,null,keywords,begin,end,type);
				// 第一步，创建一个webbook，对应一个Excel文件
				HSSFWorkbook wb = new HSSFWorkbook();
				// 第二步，在webbook中添加一个sheet,对应Excel文件中的sheet
				HSSFSheet sheet = wb.createSheet("城市库存明细报表");
				// 第三步，在sheet中添加表头第0行,注意老版本poi对Excel的行数列数有限制short
				// 列宽
				int[] widths = { 13, 26, 10, 10, 10, 22, 24, 16, 20, 11, 15, 25, 13, 13, 13, 40, 40 };
				sheetColumnWidth(sheet, widths);

				// 第四步，创建单元格，并设置值表头 设置表头居中
				HSSFCellStyle style = wb.createCellStyle();
				style.setAlignment(HSSFCellStyle.ALIGN_CENTER); // 创建一个居中格式
				style.setWrapText(true);

				// 优惠券名称、金额、领卷时间、领用用户、是否使用、使用的时间、使用订单号
				HSSFRow row = sheet.createRow((int) 0);

				String[] cellValues = { "产品编号","产品名称", "所属城市", "变更数量","可用量余额","变更时间","单据号","变更类型"};
				cellDates(cellValues, style, row);
				Integer i = 0;
				for (TdDiySiteInventoryLog diySite : diySiteInventoryList) {
					row = sheet.createRow((int) i + 1);
					row.createCell(0).setCellValue(objToString(diySite.getGoodsSku()));
					row.createCell(1).setCellValue(objToString(diySite.getGoodsTitle()));
					row.createCell(2).setCellValue(objToString(diySite.getRegionName()));
					row.createCell(3).setCellValue(objToString(diySite.getChangeValue()));
					row.createCell(4).setCellValue(objToString(diySite.getAfterChange()));
					row.createCell(5).setCellValue(objToString(diySite.getChangeDate()));
					row.createCell(6).setCellValue(objToString(diySite.getOrderNumber()));
					row.createCell(7).setCellValue(objToString(diySite.getChangeType()));
					i++;
				}

				String exportAllUrl = SiteMagConstant.backupPath;
				download(wb, exportAllUrl, response, "城市库存明细报表");
			}else if(type==2){
				//报表数据
		    	List<TdDiySiteInventoryLog> diySiteInventoryList = tdDiySiteInventoryLogService.searchList(null,diyCode,keywords,begin,end,type);
				// 第一步，创建一个webbook，对应一个Excel文件
				HSSFWorkbook wb = new HSSFWorkbook();
				// 第二步，在webbook中添加一个sheet,对应Excel文件中的sheet
				HSSFSheet sheet = wb.createSheet("门店库存明细报表");
				// 第三步，在sheet中添加表头第0行,注意老版本poi对Excel的行数列数有限制short
				// 列宽
				int[] widths = { 13, 18, 13, 13, 13, 15, 24, 11, 19, 11, 15, 25, 13, 13, 13, 40, 40 };
				sheetColumnWidth(sheet, widths);

				// 第四步，创建单元格，并设置值表头 设置表头居中
				HSSFCellStyle style = wb.createCellStyle();
				style.setAlignment(HSSFCellStyle.ALIGN_CENTER); // 创建一个居中格式
				style.setWrapText(true);

				// 优惠券名称、金额、领卷时间、领用用户、是否使用、使用的时间、使用订单号
				HSSFRow row = sheet.createRow((int) 0);

				String[] cellValues = { "产品编号","产品名称", "所属门店", "变更数量","可用量余额","变更时间","单据号","变更类型"};
				cellDates(cellValues, style, row);
				Integer i = 0;
				for (TdDiySiteInventoryLog diySite : diySiteInventoryList) {
					row = sheet.createRow((int) i + 1);
					row.createCell(0).setCellValue(objToString(diySite.getGoodsSku()));
					row.createCell(1).setCellValue(objToString(diySite.getGoodsTitle()));
					row.createCell(2).setCellValue(objToString(diySite.getDiySiteTitle()));
					row.createCell(3).setCellValue(objToString(diySite.getChangeValue()));
					row.createCell(4).setCellValue(objToString(diySite.getAfterChange()));
					row.createCell(5).setCellValue(objToString(diySite.getChangeDate()));
					row.createCell(6).setCellValue(objToString(diySite.getOrderNumber()));
					row.createCell(7).setCellValue(objToString(diySite.getChangeType()));
					i++;
				}

				String exportAllUrl = SiteMagConstant.backupPath;
				download(wb, exportAllUrl, response, "门店库存明细报表");

			}
		}

		return "";
	}
	
	
	private void btnChangeInventory(Integer listCheckId[],Long[] ids,Long[] listInventory,HttpServletRequest req)
	{
		if (null == ids || null == listInventory || ids.length < 1 || listInventory.length < 1 || ids.length != listInventory.length || listCheckId == null || listCheckId.length < 1)
		{
			return;
		}
		for (int i = 0; i < listCheckId.length; i++)
		{
			
			Integer changeIndex = listCheckId[i];
			TdDiySiteInventory diySiteInventory = tdDiySiteInventoryService.findOne(ids[changeIndex]);
			if (diySiteInventory != null)
			{
				if (listInventory[changeIndex] != diySiteInventory.getInventory()) 
				{
					Long changeValue = listInventory[changeIndex] - diySiteInventory.getInventory();
					diySiteInventory.setInventory(listInventory[changeIndex]);
					tdDiySiteInventoryLogService.saveChangeLog(diySiteInventory, changeValue, null, req,"管理员修改");
					//设置剩余库存
					tdDiySiteInventoryService.save(diySiteInventory);
				}
			}
		}
	}
	/**
	 * 删除库存日志
	 * 
	 * @param ids
	 * @param chkIds
	 */
	private void btnDeleteLog(Long[] ids, Integer[] chkIds) {
		if (null == ids || null == chkIds || ids.length < 1 || chkIds.length < 1) {
			return;
		}

		for (int chkId : chkIds) {
			if (chkId >= 0 && ids.length > chkId) {
				Long id = ids[chkId];

				tdDiySiteInventoryLogService.delete(id);
			}
		}
	}

	@ModelAttribute
	public void getModel(@RequestParam(value = "id", required = false) Long id, Model model) {
		if (id != null) {
			TdGoods goods = tdGoodsService.findOne(id);
			model.addAttribute("tdGoods", goods);
		}
	}

	/**
	 * 图片地址字符串整理，多张图片用,隔开
	 * 
	 * @param params
	 * @return
	 */
	private String parsePicUris(String[] uris) {
		if (null == uris || 0 == uris.length) {
			return null;
		}

		String res = "";

		for (String item : uris) {
			String uri = item.substring(item.indexOf("|") + 1, item.indexOf("|", 2));

			if (null != uri) {
				res += uri;
				res += ",";
			}
		}

		return res;
	}

	/**
	 * 修改商品
	 * 
	 * @param cid
	 * @param ids
	 * @param sortIds
	 * @param username
	 */
	private void btnSave(Long[] ids, Double[] sortIds, String username) {
		if (null == ids || null == sortIds || ids.length < 1 || sortIds.length < 1) {
			return;
		}

		for (int i = 0; i < ids.length; i++) {
			Long id = ids[i];

			TdGoods goods = tdGoodsService.findOne(id);

			if (sortIds.length > i) {
				goods.setSortId(new Double(sortIds[i]));
				tdGoodsService.save(goods, username);
			}
		}
	}

	/**
	 * 删除商品
	 * 
	 * @param ids
	 * @param chkIds
	 */
	private void btnDelete(Long[] ids, Integer[] chkIds) {
		if (null == ids || null == chkIds || ids.length < 1 || chkIds.length < 1) {
			return;
		}

		for (int chkId : chkIds) {
			if (chkId >= 0 && ids.length > chkId) {
				Long id = ids[chkId];

				tdGoodsService.delete(id);
			}
		}
	}
	
	private String objToString(Object obj){
		if(obj==null){
			return "";
		}
		return obj.toString();
	}
	
	

}
