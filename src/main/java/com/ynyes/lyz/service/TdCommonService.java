package com.ynyes.lyz.service;

import static org.apache.commons.lang3.StringUtils.leftPad;

import java.io.UnsupportedEncodingException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;
import javax.xml.namespace.QName;

import org.apache.cxf.jaxws.endpoint.dynamic.JaxWsDynamicClientFactory;
import org.apache.geronimo.mail.util.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.ModelMap;

import com.ibm.icu.math.BigDecimal;
import com.ibm.icu.util.Calendar;
import com.ynyes.lyz.entity.TdActivity;
import com.ynyes.lyz.entity.TdActivityGift;
import com.ynyes.lyz.entity.TdActivityGiftList;
import com.ynyes.lyz.entity.TdBalanceLog;
import com.ynyes.lyz.entity.TdBrand;
import com.ynyes.lyz.entity.TdCartColorPackage;
import com.ynyes.lyz.entity.TdCartGoods;
import com.ynyes.lyz.entity.TdCashReturnNote;
import com.ynyes.lyz.entity.TdCategoryLimit;
import com.ynyes.lyz.entity.TdCity;
import com.ynyes.lyz.entity.TdCoupon;
import com.ynyes.lyz.entity.TdCouponModule;
import com.ynyes.lyz.entity.TdDeposit;
import com.ynyes.lyz.entity.TdDiySite;
import com.ynyes.lyz.entity.TdDiySiteInventory;
import com.ynyes.lyz.entity.TdGoods;
import com.ynyes.lyz.entity.TdInterfaceErrorLog;
import com.ynyes.lyz.entity.TdOrder;
import com.ynyes.lyz.entity.TdOrderGoods;
import com.ynyes.lyz.entity.TdPayType;
import com.ynyes.lyz.entity.TdPriceList;
import com.ynyes.lyz.entity.TdPriceListItem;
import com.ynyes.lyz.entity.TdProductCategory;
import com.ynyes.lyz.entity.TdRequisition;
import com.ynyes.lyz.entity.TdRequisitionGoods;
import com.ynyes.lyz.entity.TdReturnNote;
import com.ynyes.lyz.entity.TdShippingAddress;
import com.ynyes.lyz.entity.TdUserRecentVisit;
import com.ynyes.lyz.entity.TdWareHouse;
import com.ynyes.lyz.entity.user.TdUser;
import com.ynyes.lyz.interfaces.entity.TdCashReciptInf;
import com.ynyes.lyz.interfaces.entity.TdCashRefundInf;
import com.ynyes.lyz.interfaces.entity.TdOrderCouponInf;
import com.ynyes.lyz.interfaces.entity.TdOrderGoodsInf;
import com.ynyes.lyz.interfaces.entity.TdOrderInf;
import com.ynyes.lyz.interfaces.service.TdCashReciptInfService;
import com.ynyes.lyz.interfaces.service.TdCashRefundInfService;
import com.ynyes.lyz.interfaces.service.TdInterfaceService;
import com.ynyes.lyz.interfaces.service.TdOrderCouponInfService;
import com.ynyes.lyz.interfaces.service.TdOrderGoodsInfService;
import com.ynyes.lyz.interfaces.service.TdOrderInfService;
import com.ynyes.lyz.interfaces.utils.EnumUtils.INFTYPE;
import com.ynyes.lyz.interfaces.utils.INFConstants;
import com.ynyes.lyz.interfaces.utils.InterfaceConfigure;
import com.ynyes.lyz.interfaces.utils.StringTools;
import com.ynyes.lyz.service.basic.settlement.ISettlementService;
import com.ynyes.lyz.util.ClientConstant;
import com.ynyes.lyz.util.StringUtils;

@Service
@Transactional
public class TdCommonService {

	static private String wmsUrl = InterfaceConfigure.WMS_WS_URL;
	private JaxWsDynamicClientFactory WMSDcf = JaxWsDynamicClientFactory.newInstance();
	private org.apache.cxf.endpoint.Client WMSClient = WMSDcf.createClient(wmsUrl);
	private QName WMSName = new QName("http://tempuri.org/", "GetErpInfo");

	@Autowired
	private TdUserService tdUserService;

	@Autowired
	private TdProductCategoryService tdProductCategoryService;

	@Autowired
	private TdGoodsService tdGoodsService;

	@Autowired
	private TdPriceListItemService tdPriceListItemService;

	@Autowired
	private TdDiySiteService tdDiySiteService;

	@Autowired
	private TdUserRecentVisitService tdUserRecentVisitService;

	@Autowired
	private TdCouponService tdCouponService;

	@Autowired
	private TdActivityService tdActivityService;

	@Autowired
	private TdActivityGiftService tdActivityGiftService;

	@Autowired
	private TdCartGoodsService tdCartGoodsService;

	@Autowired
	private TdOrderService tdOrderService;

	@Autowired
	private TdPayTypeService tdPayTypeService;

	@Autowired
	private TdOrderGoodsService tdOrderGoodsService;

	@Autowired
	private TdBrandService tdBrandService;

	@Autowired
	private TdPriceListService tdPriceListService;

	@Autowired
	private TdRequisitionService tdRequisitionService;

	@Autowired
	private TdRequisitionGoodsService tdRequisitionGoodsService;

	@Autowired
	private TdInterfaceErrorLogService tdInterfaceErrorLogService;

	@Autowired
	private TdReturnNoteService tdReturnNoteService;

	@Autowired
	private TdWareHouseService tdWareHouseService;

	@Autowired
	private TdCityService tdCityService;

	@Autowired
	private TdDiySiteInventoryService tdDiySiteInventoryService;

	@Autowired
	private TdCategoryLimitService tdCategoryLimitService;

	@Autowired
	private TdInterfaceService tdInterfaceService;

	// @Autowired
	// private TdBalanceLogService tdBalanceLogService;

	@Autowired
	private TdOrderGoodsInfService tdOrderGoodsInfService;

	@Autowired
	private TdOrderCouponInfService tdOrderCouponInfService;

	@Autowired
	private TdCashReciptInfService tdCashReciptInfService;

	@Autowired
	private TdOrderInfService tdOrderInfService;

	@Autowired
	private TdCashReturnNoteService tdCashReturnNoteService;

	@Autowired
	private TdBalanceLogService tdBalanceLogService;

	@Autowired
	private TdCashRefundInfService tdCashRefundInfService;

	@Autowired
	private TdCouponModuleService tdCouponModuleService;

	@Autowired
	private TdPriceCountService tdPriceCountService;

	@Autowired
	private ISettlementService settlementService;

	@Autowired
	private TdUpstairsSettingService tdUpstairsSettingService;

	/**
	 * 根据仓库编号获取仓库名
	 * 
	 * @param name
	 * @return
	 */
	public String changeName(String name) {
		// 郑州公司 11 总仓
		// 天荣中转仓 1101 分仓
		// 五龙口中转仓 1102 分仓
		// 东大中转仓 1103 分仓
		// 百姓中转仓 1104 分仓
		// 主仓库 1105 分仓

		List<TdWareHouse> wareHouses = tdWareHouseService.findBywhNumberOrderBySortIdAsc(name);
		if (wareHouses != null && wareHouses.size() > 0) {
			return wareHouses.get(0).getWhName();
		} else {
			return "未知编号：" + name;
		}
	}

	/**
	 * 取消订单产生的退货单
	 * 
	 * @param order
	 * @param type
	 *            1:用户取消订单 2：管理员取消订单
	 * @param msg
	 * @return
	 */
	public TdReturnNote MakeReturnNote(TdOrder order, Long type, String msg) {
		TdReturnNote returnNote = new TdReturnNote();

		Map<Long, Double> res = tdPriceCountService.getReturnUnitPrice(order);

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
			if (diySite != null) {
				returnNote.setDiySiteId(order.getDiySiteId());
				returnNote.setDiyCode(diySite.getStoreCode());
				returnNote.setDiySiteTel(diySite.getServiceTele());
				returnNote.setDiySiteTitle(diySite.getTitle());
				returnNote.setDiySiteAddress(diySite.getAddress());
			}
		}

		// 退货信息
		returnNote.setUsername(order.getUsername());
		if (type == 0L) {
			returnNote.setRemarkInfo("用户取消订单，退货");
		} else if (type == 1L) {
			returnNote.setRemarkInfo("管理员 " + msg + " 取消订单,退货");
		}

		if (org.apache.commons.lang3.StringUtils.isNotBlank(order.getDeliverTypeTitle())
				&& "门店自提".equals(order.getDeliverTypeTitle())) {
			returnNote.setTurnType(1L);
		} else {
			returnNote.setTurnType(2L);
		}

		returnNote.setStatusId(5L);

		returnNote.setDeliverTypeTitle(order.getDeliverTypeTitle());

		returnNote.setOrderTime(new Date());

		returnNote.setTurnPrice(order.getTotalGoodsPrice());
		List<TdOrderGoods> orderGoodsList = new ArrayList<>();

		// 商品
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

				Double unit = res.get(oGoods.getGoodsId());

				if (null == unit || 0.00 == unit.doubleValue()) {
					String orderNumber = returnNote.getOrderNumber();
					if (orderNumber.contains("HRFIT")) {
						orderNumber = orderNumber.replace("HRFIT", "XN");
					} else if (orderNumber.contains("YRFIT")) {
						orderNumber = orderNumber.replace("YRFIT", "XN");
					} else if (orderNumber.contains("LYZFIT")) {
						orderNumber = orderNumber.replace("LYZFIT", "XN");
					} else if (orderNumber.contains("HR")) {
						orderNumber = orderNumber.replace("HR", "XN");
					} else if (orderNumber.contains("YR")) {
						orderNumber = orderNumber.replace("YR", "XN");
					} else if (orderNumber.contains("LYZ")) {
						orderNumber = orderNumber.replace("LYZ", "XN");
					}
					List<TdCoupon> coupons = tdCouponService
							.findByOrderNumberAndSkuAndIsUsedTrueAndIsBuyTrue(orderNumber, orderGoods.getSku());
					if (null != coupons && coupons.size() > 0) {
						unit = coupons.get(0).getBuyPrice();
					}
				}

				orderGoods.setReturnUnitPrice(unit);
				// tdOrderGoodsService.save(orderGoods);
				// 添加商品信息
				orderGoodsList.add(orderGoods);

				// 订单商品设置退货为True
				oGoods.setIsReturnApplied(true);
				// 更新订单商品信息是否退货状态
				tdOrderGoodsService.save(oGoods);
			}
		}
		// 小辅料赠送
		if (null != order.getGiftGoodsList()) {
			for (TdOrderGoods oGoods : order.getGiftGoodsList()) {
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
		// 促销活动赠送
		if (null != order.getPresentedList()) {
			for (TdOrderGoods oGoods : order.getPresentedList()) {
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
		Double otherPay = order.getOtherPay();
		if (otherPay != null && otherPay > 0) {
			// 生成打款通知
			// 根据退款方式和退货金额生成一个资金退还申请单据
			TdCashReturnNote note = new TdCashReturnNote();
			note.setCreateTime(new Date());
			note.setMoney(otherPay);
			note.setTypeId(order.getPayTypeId());
			note.setTypeTitle(order.getPayTypeTitle());
			note.setOrderNumber(order.getOrderNumber());
			note.setMainOrderNumber(order.getMainOrderNumber());
			note.setReturnNoteNumber(null);
			note.setUserId(order.getRealUserId());
			note.setUsername(order.getUsername());
			note.setIsOperated(false);
			note.setReturnNoteNumber(returnNote.getReturnNumber());
			tdCashReturnNoteService.save(note);
		}

		returnNote.setReturnGoodsList(orderGoodsList);
		tdOrderGoodsService.save(orderGoodsList);
		tdInterfaceService.initReturnOrder(returnNote, INFConstants.INF_RETURN_ORDER_CANCEL_INT);
		tdInterfaceService.initReturnCouponInfByOrder(order, INFConstants.INF_RETURN_ORDER_CANCEL_INT);
		tdInterfaceService.sendReturnOrderByAsyn(returnNote);
		return tdReturnNoteService.save(returnNote);
	}

	/**
	 * 获取登陆用户信息的方法
	 * 
	 * @author dengxiao
	 */
	public void setHeader(HttpServletRequest req, ModelMap map) {
		String username = (String) req.getSession().getAttribute("username");
		if (null != username) {
			map.addAttribute("username", username);
			map.addAttribute("user", tdUserService.findByUsernameAndIsEnableTrue(username));
		}
	}

	/**
	 * 获取登陆用户所属门店信息的方法
	 * 
	 * @author dengxiao
	 */
	public TdDiySite getDiySite(HttpServletRequest req) {
		// 获取到登陆用户的用户名
		String username = (String) req.getSession().getAttribute("username");
		// 通过用户名查找到用户资料
		TdUser user = tdUserService.findByUsernameAndIsEnableTrue(username);
		// 获取登陆用户的门店信息
		TdDiySite diySite = tdDiySiteService.findByRegionIdAndCustomerId(user.getCityId(), user.getCustomerId());
		if (null == diySite) {
			diySite = new TdDiySite();
		}
		return diySite;
	}

	/**
	 * 查找一个商品价目表项的方法
	 * 
	 * @author dengxiao
	 */
	public TdPriceListItem getGoodsPrice(HttpServletRequest req, TdGoods goods) {
		// 获取登陆用户的门店
		TdDiySite diySite = this.getDiySite(req);
		// 获取sobId
		Long sobId = diySite.getRegionId();

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

		return priceItemList.get(0);
	}

	// public List<TdGoods> getTdGoodsByDiySiteCode(TdDiySite diySite) {
	// List<TdGoods> tdGoodsList = new ArrayList<TdGoods>();
	// Long sobId = diySite.getRegionId();
	// String priceType = "LYZ";
	// List<TdPriceList> priceList_list = tdPriceListService
	// .findBySobIdAndPriceTypeAndStartDateActiveAndEndDateActive(sobId,
	// priceType, new Date(), new Date());
	// return tdGoodsList;
	// }

	/**
	 * 查找三级分类的方法并查找指定三级分类下的所有商品及其价目表的方法
	 * 
	 * @author dengxiao
	 */
	@Deprecated
	public void getCategory(HttpServletRequest req, ModelMap map) {
		// 查找到所有的一级分类
		List<TdProductCategory> level_one_categories = tdProductCategoryService.findByParentIdIsNullOrderBySortIdAsc();
		map.addAttribute("level_one_categories", level_one_categories);
		// 遍历一级分类用于查找所有的二级分类
		for (int i = 0; i < level_one_categories.size(); i++) {
			// 获取指定的一级分类
			TdProductCategory one_category = level_one_categories.get(i);
			// 根据指定的一级分类查找到该分类下所有的二级分类
			List<TdProductCategory> level_two_categories = tdProductCategoryService
					.findByParentIdOrderBySortIdAsc(one_category.getId());
			map.addAttribute("level_two_categories" + i, level_two_categories);
			// 遍历二级分类查找其下所有的商品
			for (int j = 0; j < level_two_categories.size(); j++) {
				TdProductCategory two_category = level_two_categories.get(j);
				List<TdGoods> goods_list = tdGoodsService
						.findByCategoryIdAndIsOnSaleTrueOrderBySortIdAsc(two_category.getId());
				List<TdGoods> putaway = new ArrayList<>();
				// 遍历所有的商品，查询在指定城市的商品的价格
				for (int k = 0; k < goods_list.size(); k++) {
					TdGoods goods = goods_list.get(k);
					if (null != goods) {
						TdPriceListItem priceListItem = this.getGoodsPrice(req, goods);
						if (null != priceListItem) {
							putaway.add(goods);
							// 开始判断此件商品是否参加活动
							priceListItem.setIsPromotion(this.isJoinActivity(req, goods));
							map.addAttribute("priceListItem" + i + "_" + j + "_" + k, priceListItem);
						} else {
							putaway.add(null);
						}
					}
				}
				map.addAttribute("goods_list" + i + "_" + j, putaway);
			}
		}
	}

	/**
	 * 新方法获取二级分类
	 * 
	 * @author dengxiao
	 */
	@Deprecated
	public void getCategoryTemp(HttpServletRequest req, ModelMap map) {
		// 查找到所有的一级分类
		List<TdProductCategory> level_one_categories = tdProductCategoryService.findByParentIdIsNullOrderBySortIdAsc();
		map.addAttribute("level_one_categories", level_one_categories);
		// 遍历一级分类用于查找所有的二级分类
		for (int i = 0; i < level_one_categories.size(); i++) {
			// 获取指定的一级分类
			TdProductCategory one_category = level_one_categories.get(i);
			// 根据指定的一级分类查找到该分类下所有的二级分类
			List<TdProductCategory> level_two_categories = tdProductCategoryService
					.findByParentIdOrderBySortIdAsc(one_category.getId());
			map.addAttribute("level_two_categories" + i, level_two_categories);

		}
	}

	/**
	 * 第三次修改，获取二级分类
	 * 
	 * @author DengXiao
	 */
	public void thirdGetCategory(HttpServletRequest req, ModelMap map) {
		// 获取用户城市编号
		String username = (String) req.getSession().getAttribute("username");
		TdUser user = tdUserService.findByUsername(username);
		Long sobId = 0L;
		if (null != user) {
			sobId = user.getCityId();
		}

		// 查找指定的一级分类
		List<TdCategoryLimit> level_one_categories = tdCategoryLimitService
				.findBySobIdAndParentIdIsNullOrderBySortIdAsc(sobId);
		map.addAttribute("level_one_categories", level_one_categories);

		if (null != level_one_categories && level_one_categories.size() > 0) {
			for (int i = 0; i < level_one_categories.size(); i++) {
				TdCategoryLimit one = level_one_categories.get(i);
				if (0 == i && null != one) {
					map.addAttribute("one_level_category_id", one.getId());
				}
				if (null != one) {
					List<TdCategoryLimit> level_two_categories = tdCategoryLimitService
							.findBySobIdAndParentIdOrderBySortIdAsc(sobId, one.getCategoryId());
					map.addAttribute("level_two_categories" + i, level_two_categories);
				}
			}
		}
	}

	/**
	 * 获取指定分类下的所有商品并且获取商品的价格
	 * 
	 * @author dengxiao
	 */
	public void getGoodsAndPrice(HttpServletRequest req, ModelMap map, Long cateGoryId) {

		// 获取门店
		String username = (String) req.getSession().getAttribute("username");
		TdUser tdUser = tdUserService.findByUsername(username);
		// Long siteId = 0L;
		Long sobId = 0L;
		if (tdUser != null) {
			// siteId = tdUser.getUpperDiySiteId();
			sobId = tdUser.getCityId();
		}

		TdDiySite diySite = this.getDiySite(req);

		// 创建一个集合存储有价格的商品
		List<TdGoods> actual_goods = new ArrayList<>();
		// 查找指定二级分类下的所有商品
		// List<TdGoods> goods_list = tdGoodsService
		// .findByCategoryIdAndIsOnSaleTrueAndIsCouponNotTrueOrderBySortIdAsc(cateGoryId);
		List<TdGoods> goods_list = tdGoodsService.findGoodsByCategoryIdWithoutUnSale(cateGoryId, diySite.getId());
		if (null != goods_list && goods_list.size() > 0) {
			for (int i = 0; i < goods_list.size(); i++) {
				TdGoods goods = goods_list.get(i);
				if (null != goods) {
					// 查找指定商品的价格
					TdPriceListItem priceListItem = this.getGoodsPrice(req, goods);
					if (null != priceListItem && null != priceListItem.getSalePrice()
							&& null != priceListItem.getRealSalePrice()) {
						actual_goods.add(goods);
						// 开始判断此件商品是否参加活动
						priceListItem.setIsPromotion(this.isJoinActivity(req, goods));
						map.addAttribute("priceListItem" + i, priceListItem);
						// TdDiySiteInventory inventory =
						// tdDiySiteInventoryService.findByGoodsCodeAndDiySiteId(goods.getCode(),siteId);
						TdDiySiteInventory inventory = tdDiySiteInventoryService
								.findByGoodsCodeAndRegionIdAndDiySiteIdIsNull(goods.getCode(), sobId);
						if (inventory != null) {
							map.addAttribute("goodInventory" + i, inventory.getInventory());
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

	/**
	 * 获取所有已选调色包的方法
	 * 
	 * @author dengxiao
	 */
	public List<TdCartColorPackage> getSelectedColorPackage(HttpServletRequest req) {
		@SuppressWarnings("unchecked")
		List<TdCartColorPackage> all_color = (ArrayList<TdCartColorPackage>) req.getSession().getAttribute("all_color");
		if (null == all_color) {
			return new ArrayList<TdCartColorPackage>();
		}
		return all_color;
	}

	/**
	 * 获取指定id商品和添加登陆用户浏览记录的方法
	 * 
	 * @author dengxiao
	 */
	public void addUserRecentVisit(HttpServletRequest req, ModelMap map, Long goodsId) {
		// 获取登陆用户的用户名
		String username = (String) req.getSession().getAttribute("username");
		TdUser user = tdUserService.findByUsernameAndIsEnableTrue(username);
		// 获取指定id的商品的信息
		TdGoods goods = tdGoodsService.findOne(goodsId);
		map.addAttribute("goods", goods);
		// 添加浏览记录
		TdUserRecentVisit visit = new TdUserRecentVisit();
		visit.setUsername(username);
		visit.setUserId(user.getId());
		visit.setGoodsId(goods.getId());
		visit.setGoodsTitle(goods.getTitle());
		visit.setGoodsCoverImageUri(goods.getCoverImageUri());
		visit.setVisitTime(new Date());
		visit.setSku(goods.getCode());
		// 默认排序号1
		visit.setSortId(1.00);

		// 查看是否有重复的浏览记录
		TdUserRecentVisit user_visit = tdUserRecentVisitService.findByGoodsIdAndUserId(goodsId, user.getId());
		// 如果有此件商品的浏览记录，则删除它
		if (null != user_visit) {
			tdUserRecentVisitService.delete(user_visit);
		}

		// 查找当前用户所有的浏览记录
		List<TdUserRecentVisit> all_visit = tdUserRecentVisitService.findByUserIdOrderByVisitTimeAsc(user.getId());
		// 查询当前存储的浏览记录数量是否大于最大数量
		if (null != all_visit && all_visit.size() == ClientConstant.MAXRECENTNUM) {
			tdUserRecentVisitService.delete(all_visit.get(0));
		}
		// 存储新的浏览记录
		tdUserRecentVisitService.save(visit);
	}

	/**
	 * 判断登陆用户的优惠券是否有过期的，并将过期优惠券状态改变的方法
	 * 
	 * @author dengxiao
	 */
	public void checkUserCoupon(HttpServletRequest req) {
		String username = (String) req.getSession().getAttribute("username");
		// 获取登陆用户所有未过期且未使用的优惠券
		List<TdCoupon> coupon_list = tdCouponService
				.findByUsernameAndIsUsedFalseAndIsOutDateFalseOrderByGetTimeDesc(username);
		for (TdCoupon coupon : coupon_list) {
			if (null != coupon.getExpireTime()) {
				// 获取过期时间的毫秒值
				Long finish = coupon.getExpireTime().getTime();
				// 获取当前时间的毫秒值
				Long now = new Date().getTime();
				// 判断优惠券是否过期
				if (now > finish) {
					coupon.setIsOutDate(true);
					tdCouponService.save(coupon);
				}
			}
		}
	}

	/**
	 * 获取参与某些活动的所有商品及其对应的价格
	 * 
	 * @author dengxiao
	 */
	public List<Map<TdGoods, Double>> getPromotionGoodsAndPrice(HttpServletRequest req, List<TdActivity> activities) {
		// 创建一个集合用户存储参与活动的商品及其价格
		List<Map<TdGoods, Double>> promotion_list = new ArrayList<>();
		// 创建一个集合存储所有的参加活动的商品id
		List<Long> ids = new ArrayList<>();

		TdDiySite diySite = this.getDiySite(req);

		// 获取参与活动的商品
		for (TdActivity activity : activities) {
			if (null != activity && null != activity.getGoodsNumber()) {
				// 此all_goods_number的格式为【id_number,id_number...】
				String all_goods_number = activity.getGoodsNumber();
				// 拆分all_goods_number
				String[] goods_number = all_goods_number.split(",");
				// 遍历，再拆分
				for (String item : goods_number) {
					// 拆分item，获取参与活动的商品id
					String[] param = item.split("_");
					if (null != param) {
						// 创建一个布尔变量判断商品id是否重复
						Boolean isRepeat = false;
						for (Long id : ids) {
							if (Long.parseLong(param[0]) == id) {
								isRepeat = true;
							}
						}
						// 在不重复的情况下将商品的id添加到ids中
						if (!isRepeat) {
							ids.add(Long.parseLong(param[0]));
						}
					}
				}
			}
		}

		// 遍历ids
		for (Long id : ids) {
			// 根据id获取指定的商品
			TdGoods goods = tdGoodsService.findOne(id);
			// 获取此件商品的价格
			if (null != goods) {
				TdPriceListItem priceList = tdPriceListItemService.findByPriceListIdAndGoodsId(diySite.getPriceListId(),
						goods.getId());
				// 创建一个Map集合存储【商品,价格】
				if (null != priceList) {
					Map<TdGoods, Double> pair = new HashMap<>();
					// 判断此件商品是否已经添加
					pair.put(goods, priceList.getSalePrice());
					promotion_list.add(pair);
				}
			}
		}
		return promotion_list;
	}

	/**
	 * 对一个存储了TdActivity的集合进行内部排序的方法（按照sortId正序排序）
	 * 
	 * @author dengxiao
	 */
	public List<TdActivity> compareTheList(List<TdActivity> list) {
		// 自定义比较规则
		Comparator<TdActivity> compartor = new Comparator<TdActivity>() {
			public int compare(TdActivity a1, TdActivity a2) {
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

	/**
	 * 将一个优惠券集合按照失效时间正序排序
	 * 
	 * @author dengxiao
	 */
	public List<TdCoupon> compareTheCoupon(List<TdCoupon> list) {
		// 自定义比较规则
		Comparator<TdCoupon> compartor = new Comparator<TdCoupon>() {
			public int compare(TdCoupon a1, TdCoupon a2) {
				if (a1.getExpireTime().getTime() - a2.getExpireTime().getTime() > 0) {
					return 1;
				} else if (a1.getExpireTime().getTime() - a2.getExpireTime().getTime() == 0) {
					return 0;
				} else {
					return -1;
				}
			}
		};
		Collections.sort(list, compartor);
		return list;
	}

	/**
	 * 获取已选商品能够得到的小辅料 增加活动id zp
	 * 
	 * @author dengxiao
	 */
	public TdOrder getGift(HttpServletRequest req, TdOrder order) {
		// 获取已选【分类：数量】组
		Map<Long, Long> group = this.getGroup(req);
		List<TdOrderGoods> giftGoodsList = order.getGiftGoodsList();
		if (null == giftGoodsList) {
			giftGoodsList = new ArrayList<>();
		}

		Long giftType = 0L;
		if (null != order && null != order.getIsCoupon() && order.getIsCoupon()) {
			giftType = 1L;
		}

		// 获取已选能够参加的活动
		List<TdActivityGift> activities = this.getActivityGiftBySelected(req, giftType);
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
		order = tdOrderService.save(order);
		return order;
	}

	/**
	 * 获取已选商品能够参加的小辅料赠送活动
	 * 
	 * @author dengxiao
	 */
	public List<TdActivityGift> getActivityGiftBySelected(HttpServletRequest req, Long giftType) {
		// 创建一个集合用于存储当前已选的所能参加的小辅料活动
		List<TdActivityGift> join = new ArrayList<>();
		// 获取当前用户
		String username = (String) req.getSession().getAttribute("username");
		TdUser user = tdUserService.findByUsernameAndIsEnableTrue(username);
		// 获取已选【类别：数量】组
		Map<Long, Long> category_quantity = this.getGroup(req);

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

	/**
	 * 对一个存储了TdActivityGift的集合进行内部排序的方法（按照sortId正序排序）
	 * 
	 * @author dengxiao
	 */
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

	/**
	 * 根据已选获取【类别id：数量】组
	 * 
	 * @author dengxiao
	 */
	public Map<Long, Long> getGroup(HttpServletRequest req) {
		Map<Long, Long> group = new HashMap<>();
		String username = (String) req.getSession().getAttribute("username");
		// 获取已选商品（整合后）
		List<TdCartGoods> all_selected = tdCartGoodsService.findByUsername(username);
		for (TdCartGoods cartGoods : all_selected) {
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
				// if (null == group.get(categoryId)) {
				// group.put(categoryId, cartGoods.getQuantity());
				// } else {
				// group.put(categoryId, (group.get(categoryId) +
				// cartGoods.getQuantity()));
				// }
			}
		}
		return group;
	}

	/**
	 * 清空已选的方法
	 * 
	 * @author dengxiao
	 */
	public void clear(HttpServletRequest req) {
		String username = (String) req.getSession().getAttribute("username");
		TdUser user = tdUserService.findByUsernameAndIsEnableTrue(username);
		if (null == user) {
			user = new TdUser();
		}

		// 删除用户的已选
		List<TdCartGoods> list = tdCartGoodsService.findByUsername(user.getUsername());
		tdCartGoodsService.deleteAll(list);
	}

	/**
	 * 根据已选生成虚拟订单
	 * 
	 * @author dengxiao
	 * @throws Exception
	 */
	public TdOrder createVirtual(HttpServletRequest req, Long realUserId) throws Exception {
		// 获取登陆用户的信息
		String username = (String) req.getSession().getAttribute("username");
		TdUser user = tdUserService.findByUsername(username);

		if (null == user) {
			user = new TdUser();
		}

		// 生成一个订单表示虚拟订单
		TdOrder virtual = new TdOrder();

		// 获取当前用户所有的已选
		List<TdCartGoods> select_goods = tdCartGoodsService.findByUsername(username);

		TdShippingAddress defaultAddress = new TdShippingAddress();
		// 获取默认的收货地址
		List<TdShippingAddress> addressList = null;
		if (user.getUserType().equals(0L)) {
			addressList = user.getShippingAddressList();
		} else {
			TdUser realUser = tdUserService.findOne(realUserId);
			if (null != realUser) {
				addressList = realUser.getShippingAddressList();
			}
		}
		if (null != addressList) {
			for (TdShippingAddress address : addressList) {
				if (null != address && null != address.getIsDefaultAddress() && address.getIsDefaultAddress()) {
					defaultAddress = address;
				}
			}
		}

		// 默认的配送方式1（1代表送货上门，2代表门店自提）
		String delivery = "送货上门";
		// 默认门店为用户的归属门店
		TdDiySite defaultDiy = this.getDiySite(req);

		TdUser seller = null;
		// 获取用户的导购
		if (1L == user.getUserType().longValue() || 2L == user.getUserType().longValue()) {
			// 如果当前登录账户是销顾或者店长，则该单的seller是他自己
			seller = user;
		} else {
			Long id = user.getSellerId();
			seller = tdUserService.findOne(id);
			if (null == seller) {
				seller = new TdUser();
			}
		}

		// //默认的配送日期：第二天的的上午11:30——12:30
		// Calendar cal = Calendar.getInstance();
		// cal.add(Calendar.DATE, 1);
		// Date date = cal.getTime();
		// SimpleDateFormat sdf_ymd = new SimpleDateFormat("yyyy-MM-dd");
		// String order_deliveryDate = sdf_ymd.format(date);
		// Long order_deliveryDeatilId = 11L;

		// 获取配送时间的限制（时间选择机制：如果是上午下单，最早的配送时间是当前下午，如果是下午下单，最早配送时间是第二天的上午）
		// 获取用户的城市
		Long cityId = user.getCityId();
		TdCity city = tdCityService.findBySobIdCity(cityId);

		SimpleDateFormat hh = new SimpleDateFormat("HH");
		SimpleDateFormat mm = new SimpleDateFormat("mm");
		SimpleDateFormat yyyyMMdd = new SimpleDateFormat("yyyy-MM-dd");
		Date now1 = new Date();
		String h = hh.format(now1);
		String m = mm.format(now1);
		Long hour = Long.parseLong(h);
		Long minute = Long.parseLong(m);

		Date limitDate = now1;

		Long delay = city.getDelayHour();
		if (null == delay) {
			delay = 0L;
		}

		Long tempHour = hour + delay;
		if (24 <= tempHour) {
			// limitDate = new Date(now1.getTime() + (1000 * 60 * 60 * 24));
			// tempHour -= 24;
			limitDate = new Date(now1.getTime() + (1000 * 60 * 60 * 24));
			tempHour = 9L;
		}

		// 判断能否当天配送
		if (tempHour > city.getFinishHour() || (tempHour == city.getFinishHour() && minute > city.getFinishMinute())) {
			limitDate = new Date(now1.getTime() + (1000 * 60 * 60 * 24));
			tempHour = 9L;
		}
		String order_deliveryDate = yyyyMMdd.format(limitDate);
		Long order_deliveryDeatilId = tempHour;

		String cityShortName = null;
		switch (city.getCityName()) {
		case "成都市":
			cityShortName = "CD_";
			break;
		case "郑州市":
			cityShortName = "ZZ_";
			break;
		}

		// 以下代码用于生成订单编号
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmssSSS");
		Date now = new Date();
		String sDate = sdf.format(now);
		Random random = new Random();
		Integer suiji = random.nextInt(900) + 100;
		String orderNum = sDate + suiji;
		// 订单编号生成结束

		// 获取默认的支付方式
		// TdPayType defaultType = new TdPayType();
		// List<TdPayType> payTypeList =
		// tdPayTypeService.findAllOrderBySortIdAsc();
		// if (null != payTypeList) {
		// for (TdPayType type : payTypeList) {
		// if (null != type && null != type.getTitle() &&
		// !"到店支付".equals(type.getTitle())) {
		// if (!(null != user.getIsCashOnDelivery() &&
		// !user.getIsCashOnDelivery()
		// && "货到付款".equalsIgnoreCase(type.getTitle()))) {
		// defaultType = type;
		// break;
		// }
		// }
		// }
		// }
		TdPayType defaultType = null;
		// 导购代下单
		TdUser realUser = tdUserService.findOne(realUserId);
		// 要导购和会员都允许货到付款才可以选择货到付款
		if ((user.getIsCashOnDelivery() == null || user.getIsCashOnDelivery())
				&& (realUser == null || realUser.getIsCashOnDelivery() == null || realUser.getIsCashOnDelivery())) {
			defaultType = tdPayTypeService.findByTitleAndIsEnableTrue("货到付款");
		} else {
			// 默认选择一个线上支付方式
			List<TdPayType> payList = tdPayTypeService.findByIsOnlinePayTrueAndIsEnableTrueOrderBySortIdAsc();
			if (payList != null && payList.size() > 0) {
				defaultType = payList.get(0);
			}
		}
		// 没有支付方式 避免报错
		if (defaultType == null) {
			defaultType = new TdPayType();
		}

		virtual.setUsername(user.getUsername());
		virtual.setUserId(user.getId());

		virtual.setOrderNumber(cityShortName + "XN" + orderNum);
		virtual.setOrderTime(new Date());

		// Add by Shawn
		virtual.setProvince(defaultAddress.getProvince());
		virtual.setCity(null == defaultAddress.getCity() ? user.getCityName() : defaultAddress.getCity());
		virtual.setDisctrict(defaultAddress.getDisctrict());
		virtual.setSubdistrict(defaultAddress.getSubdistrict());
		virtual.setDetailAddress(defaultAddress.getDetailAddress());

		virtual.setShippingAddress(defaultAddress.getCity() + defaultAddress.getDisctrict()
				+ defaultAddress.getSubdistrict() + defaultAddress.getDetailAddress());
		virtual.setShippingName(defaultAddress.getReceiverName());
		virtual.setShippingPhone(defaultAddress.getReceiverMobile());
		virtual.setOrderGoodsList(new ArrayList<TdOrderGoods>());
		virtual.setTotalGoodsPrice(0.00);
		virtual.setTotalPrice(0.00);
		virtual.setProductCouponId("");
		virtual.setProductCoupon("");
		virtual.setCashCoupon(0.00);
		virtual.setLimitCash(0.00);
		virtual.setCashCouponId("");
		virtual.setStatusId(2L);
		virtual.setDeliverTypeTitle(delivery);
		virtual.setDiySiteId(defaultDiy.getId());
		virtual.setDiySiteCode(defaultDiy.getStoreCode());
		virtual.setDiySiteName(defaultDiy.getTitle());
		virtual.setDiySitePhone(defaultDiy.getServiceTele());

		virtual.setPayTypeId(defaultType.getId());
		virtual.setPayTypeTitle(defaultType.getTitle());
		virtual.setDeliveryDate(order_deliveryDate);
		virtual.setDeliveryDetailId(order_deliveryDeatilId);

		virtual.setSellerId(seller.getId());
		virtual.setSellerRealName(seller.getRealName());
		virtual.setSellerUsername(seller.getUsername());

		virtual.setIsUsedBalance(true);
		virtual.setRealUserId(user.getId());
		virtual.setRealUserRealName(user.getRealName());
		virtual.setRealUserUsername(user.getUsername());

		if (user.getUserType().equals(1L) || user.getUserType().equals(2L)) {
			virtual.setIsSellerOrder(true);
			// TdUser realUser = tdUserService.findOne(realUserId);
			if (null != realUser) {
				virtual.setRealUserId(realUser.getId());
				virtual.setRealUserRealName(realUser.getRealName());
				virtual.setRealUserUsername(realUser.getUsername());
			}
		}

		// 遍历所有的已选商品，生成虚拟订单
		for (TdCartGoods cart : select_goods) {
			TdOrderGoods goods = new TdOrderGoods();
			goods.setGoodsId(cart.getGoodsId());
			goods.setGoodsTitle(cart.getGoodsTitle());
			goods.setGoodsCoverImageUri(cart.getGoodsCoverImageUri());
			goods.setSku(cart.getSku());
			goods.setPrice(cart.getPrice());
			goods.setRealPrice(cart.getRealPrice());
			goods.setQuantity(cart.getQuantity());
			goods.setBrandId(cart.getBrandId());
			goods.setBrandTitle(cart.getBrandTitle());
			goods.setCouponNumber(0L);
			goods.setCashNumber(0L);
			goods.setIsCoupon(cart.getIsCoupon());
			goods.setOwnerGoodsSku(cart.getOwnerGoodsSku());
			goods.setIsWallAccessory(cart.getIsWallAccessory());
			if (null != goods.getIsCoupon() && goods.getIsCoupon()) {
				virtual.setIsCoupon(true);
			}
			List<TdOrderGoods> goodsList = virtual.getOrderGoodsList();
			goodsList.add(goods);
			tdOrderGoodsService.save(goods);
			tdOrderService.save(virtual);
		}
		virtual = this.getPresent(req, virtual);
		virtual = this.getGift(req, virtual);

		// 获取运费
		// Double fee = 0.00;
		// TdSubdistrict subdistrict =
		// tdSubdistrictService.findOne(defaultAddress.getSubdistrictId());
		// if (null == subdistrict) {
		// subdistrict = new TdSubdistrict();
		// }
		// fee = subdistrict.getDeliveryFee();
		// if (null == fee) {
		// fee = 0.00;
		// }

		// fee = settlementService.countOrderDeliveryFee(user, virtual);
		Map<String, Double> depiveryFeeMap = new HashMap<>();
		depiveryFeeMap = settlementService.countOrderDeliveryFee(user, virtual);

		virtual.setDeliverFee(depiveryFeeMap.get("user_delivery_fee"));
		virtual.setCompanyDeliveryFee(depiveryFeeMap.get("company_delivery_fee"));

		// 券订单不能使用线下支付的方式
		if (null != virtual.getIsCoupon() && virtual.getIsCoupon()) {
			// 获取第一个线上支付的支付方式
			TdPayType top = tdPayTypeService.findTopByIsOnlinePayTrueAndIsEnableTrueOrderBySortIdAsc();
			virtual.setPayTypeId(top.getId());
			virtual.setPayTypeTitle(top.getTitle());
		}

		// 计算上楼费
		virtual.setUpstairsFee(tdUpstairsSettingService.countUpstairsFee(virtual));
		tdOrderService.save(virtual);

		return virtual;
	}

	/**
	 * 查找用户已选获得的赠品 增加活动id zp
	 * 
	 * @author dengxiao
	 */
	public TdOrder getPresent(HttpServletRequest req, TdOrder order) {
		String username = (String) req.getSession().getAttribute("username");
		// 获取用户的已选
		List<TdCartGoods> all_selected = tdCartGoodsService.findByUsername(username);

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
		Long diySiteId = order.getDiySiteId();
		TdDiySite diySite = tdDiySiteService.findOne(diySiteId);
		if (null == diySite) {
			diySite = this.getDiySite(req);
		}
		// 获取用户门店所能参加的活动
		List<TdActivity> activity_list = tdActivityService
				.findByDiySiteIdsContainingAndBeginDateBeforeAndFinishDateAfterAndGiftTypeOrderBySortIdAsc(
						diySite.getId() + "", new Date(), giftType);
		// 为了避免脏数据刷新，创建一个map用于存储已选【id：数量】
		Map<Long, Long> selected_map = new HashMap<>();

		for (TdCartGoods cartGoods : all_selected) {
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

				order = this.comboEnoughNumber(req, activity, selected_map, isJoin, realBuy, sortList, totalNumber,
						isFloat, floatCount, presentedList, order, subPrice, isCombo, true);
			} else {
				// 阶梯促销的方法
				order = this.comboEnoughPrice(req, activity, selected_map, isJoin, sortList, totalPrice, subPrice,
						presentedList, order, isCombo, true);

			}

		}
		order.setPresentedList(presentedList);
		order = tdOrderService.save(order);
		return order;

	}

	/**
	 * 支付结束拆单的方法
	 * 
	 * @author dengxiao
	 */
	// @Deprecated
	public void dismantleOrder(HttpServletRequest req) {

		// 获取虚拟订单
		TdOrder order_temp = (TdOrder) req.getSession().getAttribute("order_temp");
		if (null == order_temp) {
			order_temp = new TdOrder();
		}
		if (null != order_temp.getId()) {
			order_temp = tdOrderService.findOne(order_temp.getId());
		}

		// 创建一个map用于存储拆单后的所有订单
		Map<Long, TdOrder> order_map = new HashMap<>();

		Double deliveryFee = order_temp.getDeliverFee();

		// 获取所有的品牌
		List<TdBrand> brand_list = tdBrandService.findAll();
		if (null != brand_list) {
			for (TdBrand brand : brand_list) {
				TdOrder order = new TdOrder();
				order.setOrderNumber(order_temp.getOrderNumber().replace("XN", brand.getShortName()));

				// Add by Shawn
				order.setProvince(order_temp.getProvince());
				order.setCity(order_temp.getCity());
				order.setDisctrict(order_temp.getDisctrict());
				order.setSubdistrict(order_temp.getSubdistrict());
				order.setDetailAddress(order_temp.getDetailAddress());

				// add MDJ 物流需要支付时间
				order.setPayTime(new Date());

				order.setDiySiteId(order_temp.getDiySiteId());
				order.setDiySiteCode(order_temp.getDiySiteCode());
				order.setDiySiteName(order_temp.getDiySiteName());
				order.setDiySitePhone(order_temp.getDiySitePhone());
				order.setShippingAddress(order_temp.getShippingAddress());
				order.setShippingName(order_temp.getShippingName());
				order.setShippingPhone(order_temp.getShippingPhone());
				order.setDeliverFee(0.00);
				order.setDeliverTypeTitle(order_temp.getDeliverTypeTitle());
				order.setDeliveryDate(order_temp.getDeliveryDate());
				order.setDeliveryDetailId(order_temp.getDeliveryDetailId());
				order.setOrderGoodsList(new ArrayList<TdOrderGoods>());
				order.setTotalGoodsPrice(0.00);
				order.setTotalPrice(0.00);
				order.setLimitCash(0.00);
				order.setCashCoupon(0.00);
				order.setProductCoupon("");
				order.setCashCouponId("");
				order.setProductCouponId("");
				order.setStatusId(3L);
				order.setUserId(order_temp.getUserId());
				order.setUsername(order_temp.getUsername());
				order.setPayTypeId(order_temp.getPayTypeId());
				order.setPayTypeTitle(order_temp.getPayTypeTitle());
				order.setOrderTime(order_temp.getOrderTime());
				order.setRemark(order_temp.getRemark());
				// 设置销顾信息
				order.setSellerId(order_temp.getSellerId());
				order.setSellerRealName(order_temp.getSellerRealName());
				order.setSellerUsername(order_temp.getSellerUsername());
				// 设置真实用户信息
				order.setIsSellerOrder(order_temp.getIsSellerOrder());
				order.setHaveSeller(order_temp.getHaveSeller());
				order.setRealUserId(order_temp.getRealUserId());
				order.setRealUserRealName(order_temp.getRealUserRealName());
				order.setRealUserUsername(order_temp.getRealUserUsername());
				// 设置主单号
				order.setMainOrderNumber(order_temp.getOrderNumber());
				// 设置实际总支付的预存款额度
				order.setAllActualPay(order_temp.getActualPay());
				// 设置实际应该支付的总额
				order.setAllTotalPay(order_temp.getTotalPrice());
				// 设置是否是电子券
				order.setIsCoupon(order_temp.getIsCoupon());
				// 设置其他收入
				order.setOtherIncome(order_temp.getOtherIncome());
				order_map.put(brand.getId(), order);

			}
		}

		List<TdOrderGoods> goodsList = order_temp.getOrderGoodsList();
		// 对已选商品进行拆单
		for (TdOrderGoods orderGoods : goodsList) {
			if (null != orderGoods) {
				// System.err.println("GoodsTitle:" +
				// orderGoods.getGoodsTitle());
				// System.err.println("GoodsBrandId:" +
				// orderGoods.getBrandId());
				Long brandId = orderGoods.getBrandId();
				TdOrder order = order_map.get(brandId);
				List<TdOrderGoods> orderGoodsList = order.getOrderGoodsList();
				if (null == orderGoodsList) {
					orderGoodsList = new ArrayList<>();
				}
				orderGoodsList.add(orderGoods);
				// System.err.println("OrderNumber:" + order.getOrderNumber());
				// System.err.println("OrderGoods:");
				// for (TdOrderGoods goods : orderGoodsList) {
				// System.err.println("order_orderGoods:" +
				// goods.getGoodsTitle());
				// }
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

		// 获取使用的现金券
		String cashCouponId = order_temp.getCashCouponId();
		// 获取使用的产品券
		String productCouponId = order_temp.getProductCouponId();

		// 开始拆分现金券
		if (null != cashCouponId && !"".equals(cashCouponId)) {
			String[] coupons = cashCouponId.split(",");
			if (null != coupons && coupons.length > 0) {
				for (String sId : coupons) {
					if (null != sId && !"".equals(sId)) {
						Long id = Long.parseLong(sId);
						if (null != id) {
							TdCoupon coupon = tdCouponService.findOne(id);
							if (null != coupon) {
								Long brandId = coupon.getBrandId();
								if (null != brandId) {
									TdOrder order = order_map.get(brandId);
									if (null != order) {
										order.setCashCouponId(order.getCashCouponId() + coupon.getId() + ",");
										if (null == coupon.getRealPrice()) {
											coupon.setRealPrice(0.00);
										}
										order.setCashCoupon(order.getCashCoupon() + coupon.getRealPrice());
										order.setTotalPrice(order.getTotalPrice() - coupon.getRealPrice());
									}
								}
							}
						}
					}
				}
			}
		}

		// 开始拆分产品券
		if (null != productCouponId && !productCouponId.isEmpty()) {
			String[] coupons = productCouponId.split(",");
			if (null != coupons && coupons.length > 0) {
				for (String sId : coupons) {
					if (null != sId && !sId.isEmpty()) {
						Long id = Long.parseLong(sId);
						if (null != id) {
							TdCoupon coupon = tdCouponService.findOne(id);
							if (null != coupon) {
								Long brandId = coupon.getBrandId();
								if (null != brandId) {
									TdOrder order = order_map.get(brandId);
									if (null != order) {
										order.setProductCouponId(order.getProductCouponId() + coupon.getId() + ",");
										// 遍历已选，找到指定产品券对应的产品
										List<TdOrderGoods> orderGoodsList = order.getOrderGoodsList();
										if (null != orderGoodsList && orderGoodsList.size() > 0) {
											for (TdOrderGoods orderGoods : orderGoodsList) {
												if (null != orderGoods && null != orderGoods.getGoodsId()
														&& null != coupon.getGoodsId() && orderGoods.getGoodsId()
																.longValue() == coupon.getGoodsId().longValue()) {
													// Double price =
													// orderGoods.getPrice();
													// if (null == price) {
													// price = 0.00;
													// }
													order.setProductCoupon(
															order.getProductCoupon() + (orderGoods.getGoodsTitle() + "【"
																	+ orderGoods.getSku() + "】*1,"));
												}
											}
										}
										order.setTotalPrice(order.getTotalPrice() - coupon.getRealPrice());
									}
								}
							}
						}
					}
				}
			}
		}

		// 重新计算促销。目的是将满减信息正确的存储到分单上
		for (Long brandId : order_map.keySet()) {
			TdOrder order = order_map.get(brandId);
			if (null != order) {
				order = this.rePresent(req, order, false);
				order.setTotalPrice(order.getTotalPrice() - order.getActivitySubPrice());
				order_map.put(brandId, order);
			}
		}

		// 查询是否存在乐易装的品牌
		if (null != order_temp.getDeliverTypeTitle() && !"门店自提".equals(order_temp.getDeliverTypeTitle())) {
			TdBrand brand = tdBrandService.findByTitle("乐易装");
			if (null != brand) {
				Long brandId = brand.getId();
				TdOrder order = order_map.get(brandId);
				// 运费放置在乐易装的订单上
				order.setDeliverFee(order_temp.getDeliverFee());
				if (null == order.getTotalPrice()) {
					order.setTotalPrice(0.00);
				}
				order.setTotalPrice(order.getTotalPrice() + order.getDeliverFee());
			}
		}

		// 获取原单总价
		Double totalPrice = order_temp.getTotalPrice();

		if (null == totalPrice) {
			totalPrice = 0.00;
		}

		// 获取原订单使用不可提现的金额
		Double unCashBalanceUsed = order_temp.getUnCashBalanceUsed();

		// 获取原订单使用不可提现的金额
		Double cashBalanceUsed = order_temp.getCashBalanceUsed();

		// 获取原订单第三方支付的金额
		Double otherPay = order_temp.getOtherPay();

		if (null == unCashBalanceUsed) {
			unCashBalanceUsed = 0.00;
		}
		if (null == cashBalanceUsed) {
			cashBalanceUsed = 0.00;
		}
		if (null == otherPay) {
			otherPay = 0.00;
		}

		totalPrice = totalPrice + cashBalanceUsed + unCashBalanceUsed;

		// 记录拆单预存款 用于记录分单号时剩余的预存款
		Double remainCash = 0.0;
		Double remainUnCash = 0.0;

		// 记录预存款使用
		TdUser user = tdUserService.findOne(order_temp.getRealUserId());

		// 遍历当前生成的订单
		for (TdOrder order : order_map.values()) {
			if (null != order) {
				Double price = order.getTotalPrice();
				if (null == price || price.equals(0d)) {
					order.setUnCashBalanceUsed(0.00);
					order.setCashBalanceUsed(0.00);
					order.setOtherPay(0.00);
				} else {
					// add MDJ
					Double point;
					if (totalPrice.equals(0)) {
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
						order.setUnCashBalanceUsed(Double.parseDouble(scale2_uncash));
						order.setCashBalanceUsed(Double.parseDouble(scale2_cash));
						order.setOtherPay(Double.parseDouble(scale2_other));
						order.setActualPay(order.getUnCashBalanceUsed() + order.getCashBalanceUsed());
						order.setTotalPrice(
								order.getTotalPrice() - order.getUnCashBalanceUsed() - order.getCashBalanceUsed());

						// 不可提现预存款
						if (!".00".equals(scale2_uncash)) {
							user.setUnCashBalance(user.getUnCashBalance() - Double.valueOf(scale2_uncash));
							TdBalanceLog balanceLog = new TdBalanceLog();
							balanceLog.setUserId(order.getRealUserId());
							balanceLog.setUsername(order.getUsername());
							balanceLog.setMoney(Double.valueOf(scale2_uncash));
							balanceLog.setType(3L);
							balanceLog.setCreateTime(new Date());
							balanceLog.setFinishTime(new Date());
							balanceLog.setIsSuccess(true);
							balanceLog.setReason("订单支付使用");
							balanceLog.setBalance(user.getUnCashBalance());
							balanceLog.setBalanceType(2L);
							balanceLog.setOperator(order.getUsername());
							balanceLog.setOrderNumber(order.getOrderNumber());
							try {
								balanceLog.setOperatorIp(InetAddress.getLocalHost().getHostAddress());
							} catch (UnknownHostException e) {
								System.out.println("获取ip地址报错");
								e.printStackTrace();
							}
							balanceLog.setDiySiteId(user.getUpperDiySiteId());
							balanceLog.setCityId(user.getCityId());
							balanceLog.setCashLeft(user.getCashBalance());
							balanceLog.setUnCashLeft(user.getUnCashBalance());
							balanceLog.setAllLeft(user.getBalance());
							tdBalanceLogService.save(balanceLog);
							remainUnCash += Double.valueOf(scale2_uncash);
							tdUserService.save(user);
						}
						// 可提现预存款
						if (!".00".equals(scale2_cash)) {
							user.setCashBalance(user.getCashBalance() - Double.valueOf(scale2_cash));
							TdBalanceLog balanceLog = new TdBalanceLog();
							balanceLog.setUserId(order.getRealUserId());
							balanceLog.setUsername(order.getUsername());
							balanceLog.setMoney(Double.valueOf(scale2_cash));
							balanceLog.setType(3L);
							balanceLog.setCreateTime(new Date());
							balanceLog.setFinishTime(new Date());
							balanceLog.setIsSuccess(true);
							balanceLog.setReason("订单支付使用");
							balanceLog.setBalance(user.getCashBalance());
							balanceLog.setBalanceType(1L);
							balanceLog.setOperator(order.getUsername());
							balanceLog.setOrderNumber(order.getOrderNumber());
							try {
								balanceLog.setOperatorIp(InetAddress.getLocalHost().getHostAddress());
							} catch (UnknownHostException e) {
								System.out.println("获取ip地址报错");
								e.printStackTrace();
							}
							balanceLog.setDiySiteId(user.getUpperDiySiteId());
							balanceLog.setCityId(user.getCityId());
							balanceLog.setCashLeft(user.getCashBalance());
							balanceLog.setUnCashLeft(user.getUnCashBalance());
							balanceLog.setAllLeft(user.getBalance());
							tdBalanceLogService.save(balanceLog);
							remainCash += Double.valueOf(scale2_cash);
							tdUserService.save(user);
						}

					}
				}
			}
		}

		// add by Shawn
		List<TdOrder> orderList = new ArrayList<TdOrder>();

		List<TdOrder> ebsOrderList = new ArrayList<TdOrder>();

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

				// 修改：2016-09-27 为了保证传递给EBS的数据正常，去掉使用现金券的价值
				// order.setAllTotalPay(order.getAllTotalPay() -
				// order_temp.getCashCoupon());

				order = tdOrderService.save(order);
				// 增加一个判定，只有配送单才会抛到WMS去——dengxiao
				if ("送货上门".equals(order.getDeliverTypeTitle())) {
					orderList.add(order);
				}

				ebsOrderList.add(order);
			}
		}

		Boolean isSend = true;

		if (null != order_temp.getIsCoupon() && order_temp.getIsCoupon()) {
			isSend = false;
		}

		if (!isSend) {
			this.getCoupon(order_temp, "无");
		}

		// 删除虚拟订单
		order_temp.setGiftGoodsList(null);
		order_temp.setPresentedList(null);
		order_temp.setOrderGoodsList(null);

		tdOrderService.delete(order_temp);

		// 清空session中的虚拟订单
		req.getSession().setAttribute("order_temp", null);

		// 子线程 抛单给WMS
		if (isSend) {
			SendRequisitionToWmsThread requsitThread = new SendRequisitionToWmsThread(orderList,
					order_temp.getOrderNumber(), deliveryFee);
			requsitThread.start();
		}
		sendEbsThread ebsThread = new sendEbsThread(ebsOrderList);
		ebsThread.start();
	}

	public void sendEbs(List<TdOrder> ebsOrderList) {
		sendEbsThread ebsThread = new sendEbsThread(ebsOrderList);
		ebsThread.start();
	}

	public void sendWms(List<TdOrder> wmsOrderList, String mainOrderNumber, Double deliveryFee) {
		SendRequisitionToWmsThread requsitThread = new SendRequisitionToWmsThread(wmsOrderList, mainOrderNumber,
				deliveryFee);
		requsitThread.start();
	}

	/**
	 * 获取已选商品的品牌id集合
	 * 
	 * @author dengxiao
	 */
	public List<Long> getBrandId(Long userId, TdOrder order) {
		// 创建一个集合用于存储品牌的Id
		List<Long> brandIds = new ArrayList<>();

		List<TdOrderGoods> selected = order.getOrderGoodsList();
		// 获取所有的已选
		if (null != selected) {
			for (TdOrderGoods cart : selected) {
				if (null != cart && null != cart.getBrandId()) {
					Long brandId = cart.getBrandId();
					if (!brandIds.contains(brandId)) {
						brandIds.add(brandId);
					}
				}
			}
		}
		return brandIds;
	}

	/**
	 * 计算能够使用的最大额度的预存款的方法
	 * 
	 * @author dengxiao
	 */
	public Double getMaxCash(HttpServletRequest req, ModelMap map, TdOrder order) {
		String username = (String) req.getSession().getAttribute("username");
		TdUser user = tdUserService.findByUsernameAndIsEnableTrue(username);
		if (null == user) {
			return 0.0;
		}
		Double max = 0.00;
		// 获取用户的预存款
		Double balance = user.getBalance();

		String title = order.getDeliverTypeTitle();

		if (null != title && "门店自提".equals(title)) {
			if (null != balance && null != order.getTotalPrice()) {
				if (balance > order.getTotalPrice()) {
					max = order.getTotalPrice();
				} else {
					max = balance;
				}
			}
		}

		if (null != title && !"门店自提".equals(title)) {
			if (null != balance && null != order.getTotalPrice() && null != order.getDeliverFee()) {
				if (balance > (order.getTotalPrice() + order.getDeliverFee())) {
					max = (order.getTotalPrice() + order.getDeliverFee());
				} else {
					max = balance;
				}
			}
		}
		return max;
	}

	/**
	 * 判断指定商品是否参加活动
	 * 
	 * @author dengxiao
	 */
	public Boolean isJoinActivity(HttpServletRequest req, TdGoods goods) {
		TdDiySite diySite = this.getDiySite(req);
		// 创建一个boolean值，默认为false，表示没有参加活动
		Boolean isJoin = false;
		List<TdActivity> activities = tdActivityService
				.findByGoodsNumberContainingAndDiySiteIdsContainingAndBeginDateBeforeAndFinishDateAfter(
						goods.getId() + "_", diySite.getId() + ",", new Date());
		if (null != activities && activities.size() > 0) {
			isJoin = true;
		}

		return isJoin;
	}

	/**
	 * 计算每种品牌能够使用的现金优惠券的限额
	 * 
	 * @author dengxiao
	 */
	public Map<Long, Double> getMaxCoupon(TdOrder order, Long userId) {
		Map<Long, Double> map = new HashMap<>();
		DecimalFormat df = new DecimalFormat("###.00");
		if (null == order || null == userId) {
			return map;
		}
		List<TdOrderGoods> orderGoodsList = order.getOrderGoodsList();
		if (null == order || null == userId) {
			return map;
		}
		List<Long> brandIds = this.getBrandId(userId, order);
		if (null != brandIds) {
			for (Long brandId : brandIds) {
				if (null != brandId) {
					Double max = 0.00;
					for (TdOrderGoods goods : orderGoodsList) {
						if (null != goods && null != goods.getBrandId()
								&& goods.getBrandId().longValue() == brandId.longValue()) {
							max = (goods.getPrice() - goods.getRealPrice()) * goods.getQuantity();
						}
					}
					max = new Double(df.format(max));
					map.put(brandId, max);
				}
			}
		}
		return map;
	}

	/**
	 * 创建当前使用额度
	 * 
	 * @author dengxiao
	 */
	public Map<Long, Double> getUsedNow(TdOrder order, Long userId) {
		Map<Long, Double> map = new HashMap<>();
		if (null == order || null == userId) {
			return map;
		}
		if (null == order || null == userId) {
			return map;
		}
		List<Long> brandIds = this.getBrandId(userId, order);
		if (null != brandIds) {
			for (Long brand : brandIds) {
				if (null != brand) {
					map.put(brand, 0.00);
				}
			}
		}
		return map;
	}

	public static String getIp(HttpServletRequest request) {
		if (request == null)
			return "";
		String ip = request.getHeader("X-Requested-For");
		if (StringUtils.isEmpty(ip) || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("X-Forwarded-For");
		}
		if (StringUtils.isEmpty(ip) || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("Proxy-Client-IP");
		}
		if (StringUtils.isEmpty(ip) || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("WL-Proxy-Client-IP");
		}
		if (StringUtils.isEmpty(ip) || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("HTTP_CLIENT_IP");
		}
		if (StringUtils.isEmpty(ip) || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("HTTP_X_FORWARDED_FOR");
		}
		if (StringUtils.isEmpty(ip) || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getRemoteAddr();
		}
		return ip;
	}

	/**
	 * 多线程
	 * 
	 * @author mdj
	 *
	 */
	// TODO 多线程 wms
	class SendRequisitionToWmsThread extends Thread {
		List<TdOrder> orderList;
		String mainOrderNumber;
		Double deliveryFee;

		// 构造函数
		SendRequisitionToWmsThread(List<TdOrder> orderList, String mainOrderNumber, Double deliveryFee) {
			this.orderList = orderList;
			this.mainOrderNumber = mainOrderNumber;
			this.deliveryFee = deliveryFee;
		}

		public void run() {
			sendMsgToWMS(orderList, mainOrderNumber, deliveryFee);
		}
	}

	class sendEbsThread extends Thread {
		List<TdOrder> orderList;

		sendEbsThread(List<TdOrder> orderList) {
			this.orderList = orderList;
		}

		public void run() {
			sendOrderToEBS(orderList);
		}
	}

	// 传销售单给EBS
	private void sendOrderToEBS(List<TdOrder> orderList) {
		for (TdOrder tdOrder : orderList) {

			// 单头是否传成功：默认失败
			Boolean isOrderInfSucceed = false;
			// 单头
			TdOrderInf orderInf = tdInterfaceService.initOrderInf(tdOrder);
			String orderInfXML = tdInterfaceService.XmlWithObject(orderInf, INFTYPE.ORDERINF);
			Object[] orderInfObject = { INFConstants.INF_ORDER_STR, "1", orderInfXML };
			String resultStr = tdInterfaceService.ebsWsInvoke(orderInfObject);
			if (org.apache.commons.lang3.StringUtils.isBlank(resultStr)) {
				isOrderInfSucceed = true;
				orderInf.setSendFlag(0);
			} else {
				orderInf.setSendFlag(1);
				orderInf.setErrorMsg(resultStr);
			}
			tdOrderInfService.save(orderInf);
			// 商品
			List<TdOrderGoodsInf> goodsInfs = tdOrderGoodsInfService.findByOrderHeaderId(orderInf.getHeaderId());
			String orderGoodsInfXML = tdInterfaceService.XmlWithObject(goodsInfs, INFTYPE.ORDERGOODSINF);
			if (org.apache.commons.lang3.StringUtils.isNotBlank(orderGoodsInfXML) && isOrderInfSucceed) {
				Object[] orderGoodsInf = { INFConstants.INF_ORDER_GOODS_STR, "1", orderGoodsInfXML };
				String resultStr1 = tdInterfaceService.ebsWsInvoke(orderGoodsInf);
				for (int i = 0; i < goodsInfs.size(); i++) {
					if (org.apache.commons.lang3.StringUtils.isBlank(resultStr1)) {
						goodsInfs.get(i).setSendFlag(0);
					} else {
						goodsInfs.get(i).setSendFlag(1);
						goodsInfs.get(i).setErrorMsg(resultStr1);
					}
				}

				tdOrderGoodsInfService.save(goodsInfs);
			}
			// 券
			List<TdOrderCouponInf> couponInfs = tdOrderCouponInfService.findByorderHeaderId(orderInf.getHeaderId());
			String orderCouponInfXML = tdInterfaceService.XmlWithObject(couponInfs, INFTYPE.ORDERCOUPONINF);
			if (org.apache.commons.lang3.StringUtils.isNotBlank(orderCouponInfXML) && isOrderInfSucceed) {
				Object[] orderCouponInf = { INFConstants.INF_ORDER_COUPON_STR, "1", orderCouponInfXML };
				String result = tdInterfaceService.ebsWsInvoke(orderCouponInf);
				for (int i = 0; i < couponInfs.size(); i++) {
					if (org.apache.commons.lang3.StringUtils.isBlank(result)) {
						couponInfs.get(i).setSendFlag(0);
					} else {
						couponInfs.get(i).setSendFlag(1);
						couponInfs.get(i).setErrorMsg(result);
					}
				}
				tdOrderCouponInfService.save(couponInfs);
			}
			// 收款
			List<TdCashReciptInf> cashReciptInfs = tdCashReciptInfService.findByOrderHeaderId(orderInf.getHeaderId());
			String cashreciptInfXML = tdInterfaceService.XmlWithObject(cashReciptInfs, INFTYPE.CASHRECEIPTINF);
			if (org.apache.commons.lang3.StringUtils.isNotBlank(cashreciptInfXML) && isOrderInfSucceed) {
				Object[] cashreciptInf = { INFConstants.INF_CASH_RECEIPTS_STR, "1", cashreciptInfXML };
				try {
					String object = (String) tdInterfaceService.getCall().invoke(cashreciptInf);
					String resultStr1 = StringTools.interfaceMessage(object);
					for (int i = 0; i < cashReciptInfs.size(); i++) {
						if (org.apache.commons.lang3.StringUtils.isBlank(resultStr1)) {
							cashReciptInfs.get(i).setSendFlag(0);
						} else {
							cashReciptInfs.get(i).setSendFlag(1);
							cashReciptInfs.get(i).setErrorMsg(resultStr1);
						}
					}
				} catch (Exception e) {
					e.printStackTrace();
					for (int i = 0; i < cashReciptInfs.size(); i++) {
						cashReciptInfs.get(i).setSendFlag(1);
						cashReciptInfs.get(i).setErrorMsg(e.getMessage());
					}
				}
				tdCashReciptInfService.save(cashReciptInfs);
			}
		}
	}

	// TODO 要货单
	private void sendMsgToWMS(List<TdOrder> orderList, String mainOrderNumber, Double deliveryFee) {

		if (orderList.size() <= 0) {
			return;
		}

		if (mainOrderNumber == null || mainOrderNumber.equalsIgnoreCase("")) {
			return;
		}

		TdRequisition requisition = SaveRequisiton(orderList, mainOrderNumber);
		requisition.setDeliveryFee(deliveryFee);

		Object[] objects = null;
		if (requisition != null && null != requisition.getRequisiteGoodsList()) {
			for (TdRequisitionGoods requisitionGoods : requisition.getRequisiteGoodsList()) {
				String xmlGoodsEncode = XMLMakeAndEncode(requisitionGoods, 2);
				try {
					objects = WMSClient.invoke(WMSName, "td_requisition_goods", "1", xmlGoodsEncode);
				} catch (Exception e) {
					e.printStackTrace();
					writeErrorLog(mainOrderNumber, requisitionGoods.getSubOrderNumber(), e.getMessage());
				}
				String result = "";
				if (objects != null) {
					for (Object object : objects) {
						result += object;
					}
				}
				String errorMsg = chectResult1(result);
				if (errorMsg != null) {
					writeErrorLog(mainOrderNumber, requisitionGoods.getSubOrderNumber(), errorMsg);
					tdInterfaceService.smsSend(INFTYPE.WMSWEBSERVICE);
				}
			}
			String xmlEncode = XMLMakeAndEncode(requisition, 1);
			try {
				objects = WMSClient.invoke(WMSName, "td_requisition", "1", xmlEncode);
			} catch (Exception e) {
				e.printStackTrace();
				writeErrorLog(mainOrderNumber, "无", e.getMessage());
				tdInterfaceService.smsSend(INFTYPE.WMSWEBSERVICE);
				// return "发送异常";
			}
			String result = null;
			if (objects != null) {
				for (Object object : objects) {
					result += object;
				}
			}
			String errorMsg = chectResult1(result);
			if (errorMsg != null) {
				writeErrorLog(mainOrderNumber, "无", errorMsg);
				tdInterfaceService.smsSend(INFTYPE.WMSWEBSERVICE);
			} else {
				// 根据乐易装的要求，当成功将信息发送至WMS时，保留提示信息
				for (TdOrder subOrder : orderList) {
					if (null != subOrder) {
						subOrder.setRemarkInfo("物流已受理");
					}
				}
				tdOrderService.save(orderList);
			}
		}
	}

	/**
	 * 要货单重传
	 * 
	 * @param requisition
	 * @return
	 */
	public Map<String, String> sendWmsMst(TdRequisition requisition) {
		String mainOrderNumber = null;
		Boolean isSuccess = true;
		Map<String, String> map = new HashMap<>();
		Object[] objects = null;
		if (requisition != null && null != requisition.getRequisiteGoodsList()) {
			mainOrderNumber = requisition.getOrderNumber();
			if (mainOrderNumber != null) {
				mainOrderNumber = mainOrderNumber.replace("XN", "");
			}
			for (TdRequisitionGoods requisitionGoods : requisition.getRequisiteGoodsList()) {
				String xmlGoodsEncode = XMLMakeAndEncode(requisitionGoods, 2);
				System.err.println("MDJWS:Detail:invoke" + mainOrderNumber);
				try {
					objects = WMSClient.invoke(WMSName, "td_requisition_goods", "1", xmlGoodsEncode);
				} catch (Exception e) {
					e.printStackTrace();
					isSuccess = false;
					System.out.println("MDJWMS: " + mainOrderNumber + " 发送失败");
					map.put(requisitionGoods.getGoodsCode(),
							requisitionGoods.getOrderNumber() + requisitionGoods.getSubOrderNumber() + "失败");
					tdInterfaceService.smsSend(INFTYPE.WMSWEBSERVICE);
				}
				String result = "";
				if (objects != null) {
					for (Object object : objects) {
						result += object;
					}
				}
				String errorMsg = chectResult1(result);
				if (errorMsg != null) {
					isSuccess = false;
					map.put(requisitionGoods.getGoodsCode(), requisitionGoods.getOrderNumber()
							+ requisitionGoods.getSubOrderNumber() + "失败-code:" + errorMsg);
					tdInterfaceService.smsSend(INFTYPE.WMSWEBSERVICE);
				} else {
					map.put(requisitionGoods.getGoodsCode(),
							requisitionGoods.getOrderNumber() + requisitionGoods.getSubOrderNumber() + "成功");
				}
			}
			String xmlEncode = XMLMakeAndEncode(requisition, 1);
			System.err.println("MDJWS:Main:invoke" + mainOrderNumber);
			try {
				objects = WMSClient.invoke(WMSName, "td_requisition", "1", xmlEncode);
			} catch (Exception e) {
				e.printStackTrace();
				isSuccess = false;
				map.put(requisition.getOrderNumber(), "失败");
				tdInterfaceService.smsSend(INFTYPE.WMSWEBSERVICE);
			}
			String result = null;
			if (objects != null) {
				for (Object object : objects) {
					result += object;
				}
			}
			String errorMsg = chectResult1(result);
			if (errorMsg != null) {
				isSuccess = false;
				map.put(requisition.getOrderNumber(), "失败:" + errorMsg);
				tdInterfaceService.smsSend(INFTYPE.WMSWEBSERVICE);
			} else {
				map.put(requisition.getOrderNumber(), "成功");
			}

			List<TdOrder> orders = tdOrderService.findByOrderNumberContaining(mainOrderNumber);
			if (isSuccess) {
				for (int i = 0; i < orders.size(); i++) {
					orders.get(i).setRemark("重发成功");
				}
			}
		}
		return map;
	}

	/**
	 * 保存要货单
	 * 
	 * @param orderList
	 * @param mainOrderNumber
	 * @return
	 */
	private TdRequisition SaveRequisiton(List<TdOrder> orderList, String mainOrderNumber) {
		if (orderList.size() <= 0) {
			return null;
		}
		TdOrder order = orderList.get(0);

		TdRequisition requisition = tdRequisitionService.findByOrderNumber(mainOrderNumber);
		if (requisition == null) {
			requisition = new TdRequisition();
			requisition.setDiySiteId(order.getDiySiteId());
			requisition.setDiyCode(order.getDiySiteCode());
			requisition.setDiySiteTitle(order.getDiySiteName());
			requisition.setCustomerName(order.getRealUserRealName());
			requisition.setCustomerId(order.getUserId());
			requisition.setOrderNumber(mainOrderNumber);
			requisition.setReceiveName(order.getShippingName());
			requisition.setReceiveAddress(order.getShippingAddress());
			requisition.setRemarkInfo(order.getRemark());
			requisition.setDiySiteTel(order.getDiySitePhone());
			requisition.setOrderTime(order.getOrderTime());
			requisition.setUpstairsAll(order.getUpstairsFee());
			requisition.setUpstairsLeft(order.getUpstairsLeftFee());

			// add by Shawn
			if (null == order.getAllTotalPay()) {
				order.setAllTotalPay(0.0);
			}

			if (null == order.getAllActualPay()) {
				order.setAllActualPay(0.0);
			}
			if (null == order.getTotalPrice()) {
				order.setTotalPrice(0.0);
			}

			// Double left = order.getTotalPrice() - order.getAllActualPay();
			// add MDJ totalPrice修改后，改变
			Double left = 0.00;
			String payTypeTitle = order.getPayTypeTitle();
			// Add by Shawn
			requisition.setProvince(order.getProvince());
			requisition.setCity(order.getCity());
			requisition.setDisctrict(order.getDisctrict());
			requisition.setSubdistrict(order.getSubdistrict());
			requisition.setDetailAddress(order.getDetailAddress());
			requisition.setDiySiteTitle(order.getDiySiteName());

			requisition.setReceivePhone(order.getShippingPhone());
			requisition.setTotalPrice(order.getAllTotalPay());
			requisition.setTypeId(1L);
			String dayTime = order.getDeliveryDate();
			if (org.apache.commons.lang3.StringUtils.isNotBlank(dayTime) && order.getDeliveryDetailId() != null) {
				dayTime = dayTime + " " + order.getDeliveryDetailId() + ":30";
				requisition.setDeliveryTime(dayTime);
			}

			requisition.setSellerRealName(order.getSellerRealName());
			requisition.setSellerTel(order.getSellerUsername());

			List<TdRequisitionGoods> requisitionGoodsList = new ArrayList<>();
			for (TdOrder tdOrder : orderList) {

				if (null != tdOrder && null != tdOrder.getTotalPrice()) {
					left += tdOrder.getTotalPrice();
				}

				if (null != tdOrder.getOrderGoodsList()) {
					for (TdOrderGoods orderGoods : tdOrder.getOrderGoodsList()) {
						TdRequisitionGoods requisitionGoods = new TdRequisitionGoods();
						requisitionGoods.setGoodsCode(orderGoods.getSku());
						requisitionGoods.setGoodsTitle(orderGoods.getGoodsTitle());
						requisitionGoods.setPrice(orderGoods.getPrice());
						requisitionGoods.setQuantity(orderGoods.getQuantity());
						requisitionGoods.setSubOrderNumber(tdOrder.getOrderNumber());
						requisitionGoods.setOrderNumber(mainOrderNumber);
						tdRequisitionGoodsService.save(requisitionGoods);
						requisitionGoodsList.add(requisitionGoods);
					}
				}

				if (null != tdOrder.getGiftGoodsList()) {
					for (TdOrderGoods orderGoods : tdOrder.getGiftGoodsList()) {
						TdRequisitionGoods requisitionGoods = new TdRequisitionGoods();
						requisitionGoods.setGoodsCode(orderGoods.getSku());
						requisitionGoods.setGoodsTitle(orderGoods.getGoodsTitle());
						requisitionGoods.setPrice(orderGoods.getPrice());
						requisitionGoods.setQuantity(orderGoods.getQuantity());
						requisitionGoods.setSubOrderNumber(tdOrder.getOrderNumber());
						requisitionGoods.setOrderNumber(mainOrderNumber);
						tdRequisitionGoodsService.save(requisitionGoods);
						requisitionGoodsList.add(requisitionGoods);
					}
				}

				if (null != tdOrder.getPresentedList()) {
					for (TdOrderGoods orderGoods : tdOrder.getPresentedList()) {
						TdRequisitionGoods requisitionGoods = new TdRequisitionGoods();
						requisitionGoods.setGoodsCode(orderGoods.getSku());
						requisitionGoods.setGoodsTitle(orderGoods.getGoodsTitle());
						requisitionGoods.setPrice(orderGoods.getPrice());
						requisitionGoods.setQuantity(orderGoods.getQuantity());
						requisitionGoods.setSubOrderNumber(tdOrder.getOrderNumber());
						requisitionGoods.setOrderNumber(mainOrderNumber);
						tdRequisitionGoodsService.save(requisitionGoods);
						requisitionGoodsList.add(requisitionGoods);
					}
				}
			}
			if ("支付宝".equalsIgnoreCase(payTypeTitle) || "银行卡".equalsIgnoreCase(payTypeTitle)
					|| "微信支付".equalsIgnoreCase(payTypeTitle)) {
				requisition.setLeftPrice(0.0);
			} else {
				requisition.setLeftPrice(left.compareTo(0.0) < 0 ? 0.0 : left);
			}
			requisition.setRequisiteGoodsList(requisitionGoodsList);
			requisition.setGoodsQuantity(requisitionGoodsList.size());
			requisition = tdRequisitionService.save(requisition);
		}
		System.out.println("MDJ:WS:Requisition:" + requisition.getOrderNumber());
		return requisition;
	}

	/**
	 * 计算正常订单总价，获取总价的方法
	 * 
	 * @author dengxiao
	 */
	public Double getOrderPice(TdOrder order) {
		Double totalPrice = 0.00;
		List<TdOrderGoods> goodsList = order.getOrderGoodsList();
		if (null != goodsList) {
			for (TdOrderGoods orderGoods : goodsList) {
				if (null != orderGoods) {
					Double price = orderGoods.getPrice();
					Long quantity = orderGoods.getQuantity();
					if (null != price && null != quantity) {
						totalPrice += price * quantity;
					}
				}
			}
		}

		return totalPrice;
	}

	/**
	 * 根据传进来的类型返回相应的XML
	 * 
	 * @param object
	 * @param type
	 *            1：tdRequisition 2：tdRequisitionGoods
	 * @return
	 */
	private String XMLMakeAndEncode(Object object, Integer type) {
		String encodeXML = null;

		if (type.equals(1)) {
			TdRequisition requisition = (TdRequisition) object;
			String xmlStr = "<ERP>" + "<TABLE>" + "<id>" + requisition.getId() + "</id>" + "<cancel_time>"
					+ requisition.getSellerRealName() + "</cancel_time>" + "<check_time></check_time>"
					+ "<diy_site_address></diy_site_address>" + "<diy_site_id>" + requisition.getDiyCode()
					+ "</diy_site_id>" + "<diy_site_tel>" + requisition.getDiySiteTel() + "</diy_site_tel>"
					+ "<manager_remark_info></manager_remark_info>" + "<remark_info>" + requisition.getRemarkInfo()
					+ "</remark_info>" + "<requisition_number></requisition_number>" + "<status_id></status_id>"
					+ "<type_id>" + requisition.getTypeId() + "</type_id>" + "<customer_name>"
					+ requisition.getCustomerName() + "</customer_name>" + "<customer_id>" + requisition.getCustomerId()
					+ "</customer_id>" + "<delivery_time>" + requisition.getDeliveryTime() + "</delivery_time>"
					+ "<order_number>" + requisition.getOrderNumber() + "</order_number>" + "<receive_address>"
					+ requisition.getReceiveAddress() + "</receive_address>" + "<receive_name>"
					+ requisition.getReceiveName() + "</receive_name>" + "<receive_phone>"
					+ requisition.getReceivePhone() + "</receive_phone>" + "<total_price>" + requisition.getTotalPrice()
					+ "</total_price>" + "<city>" + requisition.getCity() + "</city>" + "<detail_address>"
					+ requisition.getDetailAddress() + "</detail_address>" + "<disctrict>" + requisition.getDisctrict()
					+ "</disctrict>" + "<province>" + requisition.getProvince() + "</province>" + "<subdistrict>"
					+ requisition.getSubdistrict() + "</subdistrict>" + "<order_time>" + requisition.getOrderTime()
					+ "</order_time>" + "<sub_order_number>" + requisition.getLeftPrice() + "</sub_order_number>"
					+ "<seller_tel>" + requisition.getSellerTel() + "</seller_tel>" + "<goods_quantity>"
					+ requisition.getGoodsQuantity() + "</goods_quantity>" + "<upstairs_all>"
					+ requisition.getUpstairsAll() + "</upstairs_all>" + "<upstairs_left>"
					+ requisition.getUpstairsLeft() + "</upstairs_left>" + "<DELIVERY_FEE>"
					+ requisition.getDeliveryFee() + "</DELIVERY_FEE></TABLE>" + "</ERP>";
			xmlStr = xmlStr.replace("null", "");

			byte[] bs = xmlStr.getBytes();
			byte[] encodeByte = Base64.encode(bs);
			try {
				encodeXML = new String(encodeByte, "UTF-8");
			} catch (UnsupportedEncodingException e1) {
				System.err.println("MDJ_WMS:XML 编码出错!");
				return "FAILED";
			}
		} else if (type.equals(2)) {
			TdRequisitionGoods requisitionGoods = (TdRequisitionGoods) object;
			String xmlStr = "<ERP>" + "<TABLE>" + "<id>" + requisitionGoods.getId() + "</id>" + "<goods_code>"
					+ requisitionGoods.getGoodsCode() + "</goods_code>" + "<goods_title>"
					+ requisitionGoods.getGoodsTitle() + "</goods_title>" + "<price>" + requisitionGoods.getPrice()
					+ "</price>" + "<quantity>" + requisitionGoods.getQuantity() + "</quantity>"
					+ "<td_requisition_id></td_requisition_id>" + "<order_number>" + requisitionGoods.getOrderNumber()
					+ "</order_number>" + "<sub_order_number>" + requisitionGoods.getSubOrderNumber()
					+ "</sub_order_number>" + "</TABLE>" + "</ERP>";

			byte[] bs = xmlStr.getBytes();
			byte[] encodeByte = Base64.encode(bs);
			try {
				encodeXML = new String(encodeByte, "UTF-8");
			} catch (UnsupportedEncodingException e1) {
				System.err.println("MDJ_WMS:XML 编码出错!");
				return "FAILED";
			}
		}

		else if (type.equals(3)) {
			TdReturnNote returnNote = (TdReturnNote) object;
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:MM:ss");
			String orderDate = sdf.format(returnNote.getOrderTime());
			String returnDate = null;
			if (returnNote.getReturnTime() != null) {
				returnDate = sdf.format(returnNote.getReturnTime());
			}

			Long statusId = returnNote.getStatusId();
			if (returnNote.getRemarkInfo().equalsIgnoreCase("拒签退货")) {
				statusId = 1L;
			}

			String xmlStr = "<ERP>" + "<TABLE>" + "<id>" + returnNote.getId() + "</id>" + "<cancel_time>"
					+ returnNote.getCancelTime() + "</cancel_time>" + "<check_time>" + returnNote.getCheckTime()
					+ "</check_time>" + "<diy_site_address>" + returnNote.getDiySiteAddress() + "</diy_site_address>"
					+ "<diy_site_id>" + returnNote.getDiySiteId() + "</diy_site_id>" + "<diy_site_tel>"
					+ returnNote.getDiySiteTel() + "</diy_site_tel>" + "<diy_site_title>" + returnNote.getDiySiteTitle()
					+ "</diy_site_title>" + "<manager_remark_info>" + returnNote.getManagerRemarkInfo()
					+ "</manager_remark_info>" + "<order_number>" + returnNote.getOrderNumber() + "</order_number>"
					+ "<order_time>" + orderDate + "</order_time>" + "<pay_type_id>" + returnNote.getPayTypeId()
					+ "</pay_type_id>" + "<pay_type_title>" + returnNote.getPayTypeTitle() + "</pay_type_title>"
					+ "<remark_info>" + returnNote.getRemarkInfo() + "</remark_info>" + "<return_number>"
					+ returnNote.getReturnNumber() + "</return_number>" + "<return_time>" + returnDate
					+ "</return_time>" + "<sort_id>" + returnNote.getSortId() + "</sort_id>" + "<status_id>" + statusId
					+ "</status_id>" + "<username>" + returnNote.getUsername() + "</username>" + "<deliver_type_title>"
					+ returnNote.getDeliverTypeTitle() + "</deliver_type_title>" + "<turn_price>"
					+ returnNote.getTurnPrice() + "</turn_price>" + "<turn_type>" + returnNote.getTurnType()
					+ "</turn_type>" + "<shopping_address>" + returnNote.getShoppingAddress() + "</shopping_address>"
					+ "<seller_real_name>" + returnNote.getSellerRealName() + "</seller_real_name>" + "</TABLE>"
					+ "</ERP>";

			xmlStr = xmlStr.replace("null", "");
			byte[] bs = xmlStr.getBytes();
			byte[] encodeByte = Base64.encode(bs);
			try {
				encodeXML = new String(encodeByte, "UTF-8");
			} catch (UnsupportedEncodingException e1) {
				System.err.println("MDJ_WMS:XML 编码出错!");
				return "FAILED";
			}
		} else if (type.equals(4)) // 单品退货表头
		{
			TdReturnNote returnNote = (TdReturnNote) object;

			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:MM:ss");
			String date = sdf.format(returnNote.getOrderTime());
			String xmlStr = "<ERP>" + "<TABLE>" + "<id>" + returnNote.getId() + "</id>" + "<cancel_time>"
					+ returnNote.getCancelTime() + "</cancel_time>" + "<check_time>" + returnNote.getCheckTime()
					+ "</check_time>" + "<diy_site_address>" + returnNote.getDiySiteAddress() + "</diy_site_address>"
					+ "<diy_site_id>" + returnNote.getDiyCode() + "</diy_site_id>" + "<diy_site_tel>"
					+ returnNote.getDiySiteTel() + "</diy_site_tel>" + "<diy_site_title>" + returnNote.getDiySiteTitle()
					+ "</diy_site_title>" + "<manager_remark_info>" + returnNote.getManagerRemarkInfo()
					+ "</manager_remark_info>" + "<order_number>" + returnNote.getOrderNumber() + "</order_number>"
					+ "<order_time>" + date + "</order_time>" + "<pay_type_id>" + returnNote.getPayTypeId()
					+ "</pay_type_id>" + "<pay_type_title>" + returnNote.getPayTypeTitle() + "</pay_type_title>"
					+ "<remark_info>" + returnNote.getRemarkInfo() + "</remark_info>" + "<return_number>"
					+ returnNote.getReturnNumber() + "</return_number>" + "<return_time>" + returnNote.getReturnTime()
					+ "</return_time>" + "<sort_id>" + returnNote.getSortId() + "</sort_id>" + "<status_id>"
					+ returnNote.getStatusId() + "</status_id>" + "<username>" + returnNote.getUsername()
					+ "</username>" + "<deliver_type_title>" + returnNote.getDeliverTypeTitle()
					+ "</deliver_type_title>" + "<turn_price>" + returnNote.getTurnPrice() + "</turn_price>"
					+ "<turn_type>" + returnNote.getTurnType() + "</turn_type>" + "<shopping_address>"
					+ returnNote.getShoppingAddress() + "</shopping_address>" + "<seller_real_name>"
					+ returnNote.getSellerRealName() + "</seller_real_name>" + "</TABLE>" + "</ERP>";
			xmlStr = xmlStr.replace("null", "");
			byte[] bs = xmlStr.getBytes();
			byte[] encodeByte = Base64.encode(bs);
			try {
				encodeXML = new String(encodeByte, "UTF-8");
			} catch (UnsupportedEncodingException e1) {
				System.err.println("MDJ_WMS:XML 编码出错!");
				return "FAILED";
			}
		} else if (type.equals(5)) // 单品退货：明细
		{
			TdOrderGoods requisitionGoods = (TdOrderGoods) object;
			String xmlStr = "<ERP>" + "<TABLE>" + "<id>" + requisitionGoods.getId() + "</id>" + "<goods_code>"
					+ requisitionGoods.getSku() + "</goods_code>" + "<goods_title>" + requisitionGoods.getGoodsTitle()
					+ "</goods_title>" + "<price>" + requisitionGoods.getPrice() + "</price>" + "<quantity>"
					+ requisitionGoods.getQuantity() + "</quantity>" + "<td_requisition_id></td_requisition_id>"
					+ "<order_number>" + requisitionGoods.getReturnNoteNumber() + "</order_number>"
					+ "<sub_order_number>" + requisitionGoods.getSubOrderNumber() + "</sub_order_number>" + "</TABLE>"
					+ "</ERP>";

			byte[] bs = xmlStr.getBytes();
			byte[] encodeByte = Base64.encode(bs);
			try {
				encodeXML = new String(encodeByte, "UTF-8");
			} catch (UnsupportedEncodingException e1) {
				System.err.println("MDJ_WMS:XML 编码出错!");
				return "FAILED";
			}
		}
		return encodeXML;
	}

	private void writeErrorLog(String orderNumber, String subOrderNumber, String errorMsg) {
		TdInterfaceErrorLog errorLog = new TdInterfaceErrorLog();
		errorLog.setErrorMsg(errorMsg);
		errorLog.setOrderNumber(orderNumber);
		errorLog.setSubOrderNumber(subOrderNumber);
		tdInterfaceErrorLogService.save(errorLog);
	}

	/**
	 * 判断接口返回状态
	 * 
	 * @param resultStr
	 * @return
	 */

	public String chectResult1(String resultStr) {
		// "<RESULTS><STATUS><CODE>1</CODE><MESSAGE>XML参数错误</MESSAGE></STATUS></RESULTS>";
		if (!resultStr.contains("<CODE>") || !resultStr.contains("</CODE>") || !resultStr.contains("<MESSAGE>")
				|| !resultStr.contains("</MESSAGE>")) {
			return "返回XML格式错误错误:" + resultStr;
		}
		String regEx = "<CODE>([\\s\\S]*?)</CODE>";
		Pattern pat = Pattern.compile(regEx);
		Matcher mat = pat.matcher(resultStr);

		if (mat.find()) {
			System.out.println("CODE is :" + mat.group(0));
			String code = mat.group(0).replace("<CODE>", "");
			code = code.replace("</CODE>", "").trim();
			if (Integer.parseInt(code) == 0) {
				return null;
			} else {
				String errorMsg = "<MESSAGE>([\\s\\S]*?)</MESSAGE>";
				pat = Pattern.compile(errorMsg);
				mat = pat.matcher(resultStr);
				if (mat.find()) {
					System.out.println("ERRORMSG is :" + mat.group(0));
					String msg = mat.group(0).replace("<MESSAGE>", "");
					msg = msg.replace("</MESSAGE>", "").trim();
					return msg;
				}
			}
		}
		return null;
	}

	// TODO Client
	public void sendBackMsgToWMS(TdReturnNote note) {
		if (null == note) {
			return;
		}
		Object[] objects = null;
		note.setSendWmsTime(new Date());
		tdReturnNoteService.save(note);
		String xmlGoodsEncode = XMLMakeAndEncode(note, 3);
		try {
			objects = WMSClient.invoke(WMSName, "td_return_note", "1", xmlGoodsEncode);
		} catch (Exception e) {
			e.printStackTrace();
		}
		String result = "";
		if (objects != null) {
			for (Object object : objects) {
				result += object;
			}
		}
		String errorMsg = chectResult1(result);

		if (errorMsg != null) {
			writeErrorLog(note.getOrderNumber(), "退货单", errorMsg);
		}
	}

	public void sendBackToWMS(TdReturnNote note) {
		if (null == note) {
			return;
		}
		note.setSendWmsTime(new Date());
		tdReturnNoteService.save(note);
		Object[] objects = null;
		System.err.println(note.getReturnNumber());
		if (note != null && note.getReturnGoodsList() != null) {
			for (TdOrderGoods orderGoods : note.getReturnGoodsList()) {
				String xmlReturnGoodsEncode = XMLMakeAndEncode(orderGoods, 5);
				try {
					objects = WMSClient.invoke(WMSName, "td_requisition_goods_return", "1", xmlReturnGoodsEncode);
				} catch (Exception e) {
					e.printStackTrace();
					writeErrorLog(note.getReturnNumber() + ":" + orderGoods.getSku(), "退货单", e.getMessage());
					tdInterfaceService.smsSend(INFTYPE.WMSWEBSERVICE);
				}
				String result = "";
				if (objects != null) {
					for (Object object : objects) {
						result += object;
					}
				}
				String errorMsg = chectResult1(result);

				if (errorMsg != null) {
					writeErrorLog(note.getReturnNumber() + ":" + orderGoods.getSku(), "退货单", errorMsg);
					tdInterfaceService.smsSend(INFTYPE.WMSWEBSERVICE);
				}
			}
			String xmlReturnEncode = XMLMakeAndEncode(note, 4);
			try {
				objects = WMSClient.invoke(WMSName, "td_return_note_return", "1", xmlReturnEncode);
			} catch (Exception e) {
				e.printStackTrace();
				writeErrorLog(note.getReturnNumber(), "退货单", e.getMessage());
				tdInterfaceService.smsSend(INFTYPE.WMSWEBSERVICE);
			}
			String result = "";
			if (objects != null) {
				for (Object object : objects) {
					result += object;
				}
			}
			String errorMsg = chectResult1(result);

			if (errorMsg != null) {
				writeErrorLog(note.getOrderNumber(), "退货单", errorMsg);
				tdInterfaceService.smsSend(INFTYPE.WMSWEBSERVICE);
			}
		}
	}

	/**
	 * 退货单重传
	 * 
	 * @param note
	 * @return
	 */
	public Map<String, String> testSendBackMsgToWMS(TdReturnNote note) {
		Map<String, String> map = new HashMap<>();
		map.put("result", "失败");
		if (null == note) {
			return null;
		}
		Object[] objects = null;

		String xmlGoodsEncode = XMLMakeAndEncode(note, 3);
		try {
			objects = WMSClient.invoke(WMSName, "td_return_note", "1", xmlGoodsEncode);
		} catch (Exception e) {
			e.printStackTrace();
			map.put(note.getOrderNumber() + "：errorMSG", "发送失败");
		}
		String result = "";
		if (objects != null) {
			for (Object object : objects) {
				result += object;
			}
		}
		String errorMsg = chectResult1(result);

		if (errorMsg != null) {
			map.put(note.getOrderNumber() + "：errorMSG", errorMsg);
		} else {
			map.put(note.getOrderNumber() + "：MSG", "成功");
		}

		map.put("result", "成功");
		return map;
	}

	/**
	 * 电子券订单支付完成之后获得优惠券的方法
	 * 
	 * @author 作者：DengXiao
	 * @version 创建时间：2016年4月27日下午4:06:40
	 */
	public void getCoupon(TdOrder order, String type) {

		if (null == order) {
			return;
		}

		TdUser user = tdUserService.findByUsername(order.getRealUserUsername());

		Boolean isCoupon = order.getIsCoupon();
		if (null == isCoupon || !isCoupon) {
			return;
		}

		// 获取所有购买的商品
		List<TdOrderGoods> orderGoodsList = order.getOrderGoodsList();
		// 获取所有的赠品
		List<TdOrderGoods> presentedList = order.getPresentedList();
		// 获取所有的小辅料
		List<TdOrderGoods> giftGoodsList = order.getGiftGoodsList();

		Long realUserId = order.getRealUserId();

		TdUser realUser = tdUserService.findOne(realUserId);
		if (null == realUser) {
			return;
		}

		// 获取城市
		TdCity city = tdCityService.findBySobIdCity(realUser.getCityId());
		if (null == city) {
			return;
		}

		Calendar cal = Calendar.getInstance();
		cal.setTime(new Date());
		cal.add(Calendar.YEAR, 3);
		Date expiredTime = cal.getTime();

		if (null != orderGoodsList && orderGoodsList.size() > 0) {
			for (TdOrderGoods orderGoods : orderGoodsList) {
				if (null != orderGoods) {
					Long goodsId = orderGoods.getGoodsId();
					Long quantity = orderGoods.getQuantity();
					if (null == goodsId || null == quantity) {
						continue;
					}

					Long brandId = orderGoods.getBrandId();
					TdBrand brand = tdBrandService.findOne(brandId);

					// 购买的数量为多少就赠送多少
					for (int i = 0; i < quantity; i++) {
						TdCoupon coupon = new TdCoupon();
						coupon.setTypeId(4L);
						coupon.setTypeCategoryId(3L);
						coupon.setBrandId(orderGoods.getBrandId());
						coupon.setBrandTitle(orderGoods.getBrandTitle());
						coupon.setGoodsId(orderGoods.getGoodsId());
						coupon.setPicUri(orderGoods.getGoodsCoverImageUri());
						coupon.setGoodsName(orderGoods.getGoodsTitle());
						coupon.setTypeTitle(orderGoods.getSku() + "产品券");
						coupon.setTypeDescription(orderGoods.getSku() + "产品券");
						coupon.setTypePicUri(orderGoods.getGoodsCoverImageUri());
						coupon.setPrice(orderGoods.getPrice());
						coupon.setIsDistributted(true);
						coupon.setGetTime(new Date());
						coupon.setUsername(realUser.getUsername());
						coupon.setIsUsed(false);
						coupon.setIsOutDate(false);
						coupon.setExpireTime(expiredTime);
						coupon.setMobile(realUser.getUsername());
						coupon.setSku(orderGoods.getSku());
						coupon.setCityId(city.getId());
						coupon.setCityName(city.getCityName());

						coupon.setDiySiteCode(order.getDiySiteCode());
						coupon.setDiySiteTital(order.getDiySiteName());

						coupon.setSellerId(order.getSellerId());
						coupon.setSellerRealName(order.getSellerRealName());
						coupon.setSellerUsername(order.getSellerUsername());

						coupon.setIsBuy(true);

						coupon.setSellerId(order.getSellerId());
						coupon.setSellerRealName(order.getSellerRealName());
						coupon.setSellerUsername(order.getSellerUsername());

						// 2016-06-24修改
						// if (null != unit_map.get(goodsId)) {
						// unit_map.put(goodsId, 0.00);
						// }
						// coupon.setBuyPrice(unit_map.get(goodsId));

						Double shareUnit = orderGoods.getShareUnit();
						if (null == shareUnit) {
							shareUnit = 0d;
						}
						if (i < orderGoods.getCashNumber()) {
							// 查找出指定产品现金券模板
							TdCouponModule module = tdCouponModuleService.findByGoodsIdAndCityIdAndType(goodsId,
									user.getCityId(), 1L);
							if (null != module) {
								coupon.setBuyPrice(orderGoods.getPrice() - module.getPrice() - shareUnit);
							} else {
								coupon.setBuyPrice(orderGoods.getPrice() - shareUnit);
							}
						} else {
							coupon.setBuyPrice(orderGoods.getPrice() - shareUnit);
						}

						coupon.setCouponOrderNumber(order.getOrderNumber().replaceAll("XN", brand.getShortName()));

						coupon.setTradePrice(orderGoods.getPrice());

						coupon.setSign(type);

						tdCouponService.save(coupon);
					}
				}
			}
		}
		if (null != presentedList && presentedList.size() > 0) {
			for (TdOrderGoods orderGoods : presentedList) {
				if (null != orderGoods) {
					Long goodsId = orderGoods.getGoodsId();
					Long quantity = orderGoods.getQuantity();
					if (null == goodsId || null == quantity) {
						continue;
					}

					Long brandId = orderGoods.getBrandId();
					TdBrand brand = tdBrandService.findOne(brandId);

					// 购买的数量为多少就赠送多少
					for (int i = 0; i < quantity; i++) {
						TdCoupon coupon = new TdCoupon();
						coupon.setTypeId(4L);
						coupon.setTypeCategoryId(3L);
						coupon.setBrandId(orderGoods.getBrandId());
						coupon.setBrandTitle(orderGoods.getBrandTitle());
						coupon.setGoodsId(orderGoods.getGoodsId());
						coupon.setPicUri(orderGoods.getGoodsCoverImageUri());
						coupon.setGoodsName(orderGoods.getGoodsTitle());
						coupon.setTypeTitle(orderGoods.getSku() + "产品券");
						coupon.setTypeDescription(orderGoods.getSku() + "产品券");
						coupon.setTypePicUri(orderGoods.getGoodsCoverImageUri());
						coupon.setPrice(orderGoods.getPrice());
						coupon.setIsDistributted(true);
						coupon.setGetTime(new Date());
						coupon.setUsername(realUser.getUsername());
						coupon.setIsUsed(false);
						coupon.setIsOutDate(false);
						coupon.setExpireTime(expiredTime);
						coupon.setMobile(realUser.getUsername());
						coupon.setSku(orderGoods.getSku());
						coupon.setCityId(city.getId());
						coupon.setCityName(city.getCityName());

						coupon.setDiySiteCode(order.getDiySiteCode());
						coupon.setDiySiteTital(order.getDiySiteName());

						coupon.setSellerId(order.getSellerId());
						coupon.setSellerRealName(order.getSellerRealName());
						coupon.setSellerUsername(order.getSellerUsername());

						coupon.setBuyPrice(0.00);

						coupon.setIsBuy(false);

						coupon.setCouponOrderNumber(order.getOrderNumber().replaceAll("XN", brand.getShortName()));

						coupon.setTradePrice(this.getPrice(coupon.getGoodsId(), coupon.getUsername()));

						tdCouponService.save(coupon);
					}
				}
			}
		}
		if (null != giftGoodsList && giftGoodsList.size() > 0) {
			for (TdOrderGoods orderGoods : giftGoodsList) {
				if (null != orderGoods) {
					Long goodsId = orderGoods.getGoodsId();
					Long quantity = orderGoods.getQuantity();
					if (null == goodsId || null == quantity) {
						continue;
					}

					Long brandId = orderGoods.getBrandId();
					TdBrand brand = tdBrandService.findOne(brandId);

					// 购买的数量为多少就赠送多少
					for (int i = 0; i < quantity; i++) {
						TdCoupon coupon = new TdCoupon();
						coupon.setTypeId(4L);
						coupon.setTypeCategoryId(3L);
						coupon.setBrandId(orderGoods.getBrandId());
						coupon.setBrandTitle(orderGoods.getBrandTitle());
						coupon.setGoodsId(orderGoods.getGoodsId());
						coupon.setPicUri(orderGoods.getGoodsCoverImageUri());
						coupon.setGoodsName(orderGoods.getGoodsTitle());
						coupon.setTypeTitle(orderGoods.getSku() + "产品券");
						coupon.setTypeDescription(orderGoods.getSku() + "产品券");
						coupon.setTypePicUri(orderGoods.getGoodsCoverImageUri());
						coupon.setPrice(orderGoods.getPrice());
						coupon.setIsDistributted(true);
						coupon.setGetTime(new Date());
						coupon.setUsername(realUser.getUsername());
						coupon.setIsUsed(false);
						coupon.setIsOutDate(false);
						coupon.setExpireTime(expiredTime);
						coupon.setMobile(realUser.getUsername());
						coupon.setSku(orderGoods.getSku());
						coupon.setCityId(city.getId());
						coupon.setCityName(city.getCityName());

						coupon.setDiySiteCode(order.getDiySiteCode());
						coupon.setDiySiteTital(order.getDiySiteName());

						coupon.setSellerId(order.getSellerId());
						coupon.setSellerRealName(order.getSellerRealName());
						coupon.setSellerUsername(order.getSellerUsername());

						coupon.setBuyPrice(0.00);

						coupon.setIsBuy(false);

						coupon.setCouponOrderNumber(order.getOrderNumber().replaceAll("XN", brand.getShortName()));

						coupon.setTradePrice(this.getPrice(coupon.getGoodsId(), coupon.getUsername()));

						tdCouponService.save(coupon);
					}
				}
			}
		}
	}

	// 促销方案满数量赠送的方法
	public TdOrder comboEnoughNumber(HttpServletRequest req, TdActivity activity, Map<Long, Long> selected_map,
			Boolean isJoin, Long realBuy, List<Long> sortList, Long totalNumber, Boolean isFloat, Long floatCount,
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
										TdPriceListItem priceListItem = this.getGoodsPrice(req, goods);
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

	// 组合促销满金额赠的方法
	public TdOrder comboEnoughPrice(HttpServletRequest req, TdActivity activity, Map<Long, Long> selected_map,
			Boolean isJoin, List<Long> sortList, Double totalPrice, Double subPrice, List<TdOrderGoods> presentedList,
			TdOrder order, Boolean isCombo, Boolean isSave) {

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
							TdPriceListItem priceListItem = this.getGoodsPrice(req, goods);
							Double price = 0.00;
							if (null != priceListItem && null != priceListItem.getSalePrice()) {
								price = priceListItem.getSalePrice();
							}

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
											TdPriceListItem priceListItem = this.getGoodsPrice(req, goods);
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

	public TdOrder rePresent(HttpServletRequest req, TdOrder order, Boolean isSave) {
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
		// TdDiySite diySite = this.getDiySite(req);
		Long diySiteId = order.getDiySiteId();
		TdDiySite diySite = tdDiySiteService.findOne(diySiteId);
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

				order = this.comboEnoughNumber(req, activity, selected_map, isJoin, realBuy, sortList, totalNumber,
						isFloat, floatCount, presentedList, order, subPrice, isCombo, isSave);
			} else {
				// 阶梯促销的方法
				order = this.comboEnoughPrice(req, activity, selected_map, isJoin, sortList, totalPrice, subPrice,
						presentedList, order, isCombo, isSave);

			}

		}
		// 修改2016-08-25：为了不出现重复满赠的情况，不保存新订单的信息
		order.setPresentedList(presentedList);

		if (!(null != isSave && !isSave)) {
			order = tdOrderService.save(order);
		}
		return order;

	}

	/**
	 * 计算券单价的方法
	 * 
	 * @author 作者：DengXiao
	 * @version 版本：2016年6月24日上午11:50:52
	 */
	public Map<Long, Double> countCouponOrderUnit(HttpServletRequest req, TdOrder order) {
		Map<Long, Double> result = new HashMap<>();

		if (null != order && null != order.getIsCoupon() && order.getIsCoupon()) {
			// 获取订单的商品总价值
			Double totalGoodsPrice = order.getTotalGoodsPrice();
			if (null == totalGoodsPrice) {
				totalGoodsPrice = 0.00;
			}
			// 获取订单价格
			Double totalPrice = order.getTotalPrice();
			if (null == totalPrice) {
				totalPrice = 0.00;
			}
			// 获取订单商品
			List<TdOrderGoods> orderGoodsList = order.getOrderGoodsList();
			if (null != orderGoodsList && orderGoodsList.size() > 0) {
				for (TdOrderGoods orderGoods : orderGoodsList) {
					Long goodsId = orderGoods.getGoodsId();
					Long quantity = orderGoods.getQuantity();
					TdGoods goods = tdGoodsService.findOne(goodsId);
					TdPriceListItem priceListItem = this.getGoodsPrice(req, goods);
					Double goodsPrice = priceListItem.getCouponPrice() * quantity;
					if (0.00 != totalGoodsPrice.doubleValue()) {
						Double point = goodsPrice / totalGoodsPrice;
						Double realGoodsPrice = totalPrice * point;
						Double realUnit = realGoodsPrice / quantity;
						BigDecimal b = new BigDecimal(realUnit);
						realUnit = b.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
						result.put(goodsId, realUnit);
					}
				}
			}
		}
		return result;
	}

	public TdCashRefundInf createCashRefundInfoAccordingToDeposit(TdDeposit deposit, TdUser user, String desc,
			String type) {
		if (null == deposit) {
			return null;
		} else {
			TdCashRefundInf cashRefundInf = new TdCashRefundInf();
			cashRefundInf.setSobId(user.getCityId());
			cashRefundInf.setRefundNumber(deposit.getNumber());
			cashRefundInf.setUserid(user.getId());
			cashRefundInf.setUsername(user.getUsername());
			cashRefundInf.setUserphone(user.getUsername());
			cashRefundInf.setDiySiteCode(user.getDiyCode());
			if (desc.equals("信用额度")) {
				cashRefundInf.setRefundClass("信用额度");
				/*
				 * } else if (desc.equals("CRM积分")) {
				 * cashRefundInf.setRefundClass("CRM积分");
				 */
			} else {
				cashRefundInf.setRefundClass("预收款");
			}
			cashRefundInf.setRtHeaderId(null);
			cashRefundInf.setReturnNumber(null);
			cashRefundInf.setOrderHeaderId(null);
			cashRefundInf.setProductType(type);
			cashRefundInf.setRefundType(desc);
			cashRefundInf.setAmount(deposit.getMoney());
			cashRefundInf.setDescription(desc);
			cashRefundInf.setRefundDate(new Date());
			tdCashRefundInfService.save(cashRefundInf);
			return cashRefundInf;
		}
	}

	// 获取指定商品的券价格
	public Double getPrice(Long goodsId, String username) {
		TdUser user = tdUserService.findByUsername(username);
		Long sobId = null;
		if (null != user) {
			sobId = user.getCityId();
		}

		TdGoods goods = tdGoodsService.findOne(goodsId);

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

		return priceItemList.get(0).getCouponPrice();
	}
}
