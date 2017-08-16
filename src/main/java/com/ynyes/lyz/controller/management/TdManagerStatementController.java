package com.ynyes.lyz.controller.management;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ynyes.fitment.foundation.entity.FitCompany;
import com.ynyes.fitment.foundation.service.FitCompanyService;
import com.ynyes.lyz.entity.DeliveryCheckReport;
import com.ynyes.lyz.entity.FitGoodsInOut;
import com.ynyes.lyz.entity.GarmentFranchisorReport;
import com.ynyes.lyz.entity.TdActiveUser;
import com.ynyes.lyz.entity.TdAgencyFund;
import com.ynyes.lyz.entity.TdCity;
import com.ynyes.lyz.entity.TdDiySite;
import com.ynyes.lyz.entity.TdGoodsInOut;
import com.ynyes.lyz.entity.TdGoodsInOutFranchisees;
import com.ynyes.lyz.entity.TdManager;
import com.ynyes.lyz.entity.TdManagerDiySiteRole;
import com.ynyes.lyz.entity.TdManagerRole;
import com.ynyes.lyz.entity.TdOrder;
import com.ynyes.lyz.entity.TdOrderGoods;
import com.ynyes.lyz.entity.TdOwn;
import com.ynyes.lyz.entity.TdReceipt;
import com.ynyes.lyz.entity.TdReserveOrder;
import com.ynyes.lyz.entity.TdReturnReport;
import com.ynyes.lyz.entity.TdSales;
import com.ynyes.lyz.entity.TdSalesDetail;
import com.ynyes.lyz.entity.TdSalesDetailForFranchiser;
import com.ynyes.lyz.entity.TdSalesForContinuousBuy;
import com.ynyes.lyz.entity.TdSetting;
import com.ynyes.lyz.entity.TdSubOwn;
import com.ynyes.lyz.entity.TdWareHouse;
import com.ynyes.lyz.entity.delivery.TdDeliveryFeeHead;
import com.ynyes.lyz.entity.delivery.TdOrderDeliveryFeeDetail;
import com.ynyes.lyz.entity.delivery.TdOrderDeliveryFeeDetailStatement;
import com.ynyes.lyz.entity.user.TdUser;
import com.ynyes.lyz.service.FitGoodsInOutService;
import com.ynyes.lyz.service.TdActiveUserService;
import com.ynyes.lyz.service.TdAgencyFundService;
import com.ynyes.lyz.service.TdCityService;
import com.ynyes.lyz.service.TdDeliveryCheckReportService;
import com.ynyes.lyz.service.TdDeliveryFeeHeadService;
import com.ynyes.lyz.service.TdDiySiteRoleService;
import com.ynyes.lyz.service.TdDiySiteService;
import com.ynyes.lyz.service.TdGarmentFranchisorReportService;
import com.ynyes.lyz.service.TdGatheringService;
import com.ynyes.lyz.service.TdGoodsInOutFranchiseesService;
import com.ynyes.lyz.service.TdGoodsInOutService;
import com.ynyes.lyz.service.TdManagerRoleService;
import com.ynyes.lyz.service.TdManagerService;
import com.ynyes.lyz.service.TdOrderDeliveryFeeDetailService;
import com.ynyes.lyz.service.TdOrderDeliveryFeeDetailStatementService;
import com.ynyes.lyz.service.TdOrderGoodsService;
import com.ynyes.lyz.service.TdOrderService;
import com.ynyes.lyz.service.TdOwnService;
import com.ynyes.lyz.service.TdReceiptService;
import com.ynyes.lyz.service.TdReserveOrderService;
import com.ynyes.lyz.service.TdReturnReportService;
import com.ynyes.lyz.service.TdSalesDetailForFranchiserService;
import com.ynyes.lyz.service.TdSalesDetailService;
import com.ynyes.lyz.service.TdSalesForActiveUserService;
import com.ynyes.lyz.service.TdSalesForContinuousBuyService;
import com.ynyes.lyz.service.TdSalesService;
import com.ynyes.lyz.service.TdSettingService;
import com.ynyes.lyz.service.TdSubOwnService;
import com.ynyes.lyz.service.TdUserService;
import com.ynyes.lyz.service.TdWareHouseService;
import com.ynyes.lyz.service.basic.settlement.ISettlementService;
import com.ynyes.lyz.util.SiteMagConstant;


@Controller
@RequestMapping("/Verwalter/statement")
public class TdManagerStatementController extends TdManagerBaseController {
	
	@Autowired
	TdGoodsInOutService tdGoodsInOutService;
	@Autowired
	TdManagerService tdManagerService;
	@Autowired
	TdManagerRoleService tdManagerRoleService;
	@Autowired
	TdWareHouseService tdWareHouseService;
	@Autowired
	TdUserService tdUserService;
	@Autowired
	TdDiySiteService tdDiySiteService;
	@Autowired
	TdCityService tdCityService;
	@Autowired
	TdAgencyFundService tdAgencyFundService;
	@Autowired
	TdGatheringService tdGatheringService;
	@Autowired
	TdReceiptService tdReceiptService;
	@Autowired
	TdSalesDetailService tdSalesDetailService;
	@Autowired
	TdReturnReportService tdReturnReportService;
	@Autowired
	TdDiySiteRoleService tdDiySiteRoleService;
	@Autowired
	TdSalesService tdSalesService;
	@Autowired
	TdOwnService tdOwnService;
	@Autowired
	TdReserveOrderService tdReserveOrderService;
	@Autowired
	TdSubOwnService tdSubOwnService;
	@Autowired
	TdSalesForActiveUserService tdSalesForActiveUserService;
	@Autowired
	TdActiveUserService tdActiveUserService;
	@Autowired
	TdDeliveryCheckReportService tdDeliveryCheckReportService;
	
	@Autowired
	TdGarmentFranchisorReportService tdGarmentFranchisorReportService;
	
	@Autowired
	TdSalesForContinuousBuyService tdSalesForContinuousBuyService;
	
	@Autowired
	TdOrderDeliveryFeeDetailService tdOrderDeliveryFeeDetailService;
	
	@Autowired
	TdOrderService tdOrderService;
	
	@Autowired
	TdOrderGoodsService tdOrderGoodsService;
	
	@Autowired
	TdDeliveryFeeHeadService tdDeliveryFeeHeadService;
	
	@Autowired 
	ISettlementService settlementService;
	
	@Autowired
	TdSettingService tdSettingService;
	
	@Autowired
	
	TdOrderDeliveryFeeDetailStatementService tdOrderDeliveryFeeDetailStatementService;
	
	@Autowired
	FitGoodsInOutService fitGoodsInOutService;
	
	@Autowired 
	FitCompanyService fitCompanyService;
	
	@Autowired
	TdSalesDetailForFranchiserService tdSalesDetailForFranchiserService;
	
	@Autowired
	
	TdGoodsInOutFranchiseesService tdGoodsInOutFranchiseesService;

	private static final Logger LOGGER = LoggerFactory.getLogger(TdManagerStatementController.class);
	 
	
	
    /*
	 * 报表下载
	 */
	@RequestMapping(value = "/downdata",method = RequestMethod.GET)
	@ResponseBody
	public String dowmDataGoodsInOut(HttpServletRequest req,ModelMap map,String begindata,String enddata,HttpServletResponse response,String diyCode,String cityName,Long statusId,String code) throws Exception
	{
		LOGGER.info("dowmDataGoodsInOut, begindata=" + begindata + ", enddata=" + enddata + ", diyCode=" + diyCode + ", cityName=" + cityName + ", statusId=" + statusId);
		//检查登录
		String username = (String) req.getSession().getAttribute("manager");
		if (null == username) {
			LOGGER.warn("dowmDataGoodsInOut, no login!");
			return "redirect:/Verwalter/login";
		}
		
		//检查权限
		TdManager tdManager = tdManagerService.findByUsernameAndIsEnableTrue(username);
		TdManagerRole tdManagerRole = null;
		if (null != tdManager && null != tdManager.getRoleId())
		{
			tdManagerRole = tdManagerRoleService.findOne(tdManager.getRoleId());
		}
		if (tdManagerRole == null)
		{
			LOGGER.warn("dowmDataGoodsInOut, permission denied!");
			return "redirect:/Verwalter/login";
		}
		
		Date begin = new Date();
		Date end = new Date();
		if(statusId==9){
			
		}else{
			 begin = stringToDate(begindata,null);
			 end = stringToDate(enddata,null);
			//设置默认时间
			if(null==begin){
				begin=getStartTime();
			}
			if(null==end){
				end=getEndTime();
			}
		}
		
		//门店管理员只能查询归属门店
		if (tdManagerRole.getTitle().equalsIgnoreCase("门店")||tdManagerRole.getTitle().equalsIgnoreCase("郑州门店")) 
			{
	        	diyCode=tdManager.getDiyCode();
	        	cityName=null;
			}

		LOGGER.info("dowmDataGoodsInOut, start export excel...");
		//获取到导出的excel
		HSSFWorkbook wb=acquireHSSWorkBook(statusId, begin, end, diyCode, cityName, username,tdDiySiteRoleService.userRoleDiyId(tdManagerRole, tdManager),code);
		LOGGER.info("dowmDataGoodsInOut, start download excel...");

		String exportAllUrl = SiteMagConstant.backupPath;
		if (statusId==7) {
			downloadForSale(wb, exportAllUrl, response, acquireFileName(statusId));
		}else{
			 download(wb, exportAllUrl, response,acquireFileName(statusId));
		}
		LOGGER.info("dowmDataGoodsInOut, download complete!");
        return "";
	}
	
	/**
	 * 报表 展示
	 * 选择城市 刷新门店列表
	 * @param keywords 订单号
	 * @param page 当前页
	 * @param size 每页显示行数
	 * @param __EVENTTARGET
	 * @param __EVENTARGUMENT
	 * @param __VIEWSTATE
	 * @param map 
	 * @param req
	 * @param orderStartTime 开始时间
	 * @param orderEndTime 结束时间
	 * @param diyCode 门店编号
	 * @param cityName 城市名称
	 * @param statusId 0:出退货报表 1:代收款报表2：收款报表3：销售明细报表4：退货报表5：领用记录报表
	 *
	 */
	@RequestMapping(value = "/goodsInOut/list/{statusId}")
	public String goodsListDialog(String keywords,@PathVariable Long statusId, Integer page, Integer size,
			String __EVENTTARGET, String __EVENTARGUMENT, String __VIEWSTATE,
			ModelMap map, HttpServletRequest req,String orderStartTime,String orderEndTime,String diyCode,String cityName,
			String oldOrderStartTime,String oldOrderEndTime,String code,Long sobId) {
		
		String username = (String) req.getSession().getAttribute("manager");
		//判断是否登陆
		if (null == username)
		{
			return "redirect:/Verwalter/login";
		}

		TdManager tdManager = tdManagerService.findByUsernameAndIsEnableTrue(username);
		TdManagerRole tdManagerRole = null;
		if (null != tdManager && null != tdManager.getRoleId())
		{
			tdManagerRole = tdManagerRoleService.findOne(tdManager.getRoleId());
		}
		//判断是否有权限
		if (tdManagerRole == null)
		{
			return "redirect:/Verwalter/login";
		}
		//查询用户管辖门店权限
    	TdManagerDiySiteRole diySiteRole= tdDiySiteRoleService.findByTitle(tdManagerRole.getTitle());
    	//获取管理员管辖城市
    	List<TdCity> cityList= new ArrayList<TdCity>();
    	//获取管理员管辖门店
    	List<TdDiySite> diyList=new ArrayList<TdDiySite>(); 
    	
    	//管理员获取管辖的城市和门店
    	tdDiySiteRoleService.userRoleCityAndDiy(cityList, diyList, diySiteRole, tdManagerRole, tdManager);
    	List<Long> sobIdList = new ArrayList<>();
    	if(null !=cityName && !"".equals(cityName)){
    		for (int i = 0; i < cityList.size(); i++) {
				if(cityList.get(i).getCityName().equals(cityName)){
					sobIdList.add(cityList.get(i).getSobIdCity());
				}
			}
    	}else{
    		for (TdCity city : cityList) {
    			sobIdList.add(city.getSobIdCity());
    		}
    	}
    	List<FitCompany> companyList = new ArrayList<>(); 
    	if(statusId==16){
    		try {
				companyList = fitCompanyService.findFitCompanyBySobId(sobIdList);
				System.out.println(companyList);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
    	}
    	
    	
		if (null == page || page < 0) {
			page = 0;
		}

		if (null == size || size <= 0) {
			size = SiteMagConstant.pageSize;
		}

		if (null != __EVENTTARGET) {
			if (__EVENTTARGET.equalsIgnoreCase("btnCancel"))
			{
			} 
			else if (__EVENTTARGET.equalsIgnoreCase("btnConfirm"))
			{
			}
			else if (__EVENTTARGET.equalsIgnoreCase("btnDelete"))
			{
			}
			else if (__EVENTTARGET.equalsIgnoreCase("btnPage")) 
			{
				if (null != __EVENTARGUMENT) 
				{
					page = Integer.parseInt(__EVENTARGUMENT);
				}
			}else if(__EVENTTARGET.equals("btnSearch")){
				page=0;
			}
		}
		
		//修改城市刷新门店列表
		if(StringUtils.isNotBlank(cityName)){
			//需要删除的diy
			List<TdDiySite> diyRemoveList=new ArrayList<TdDiySite>(); 
			for (TdDiySite tdDiySite : diyList) {
				if(!cityName.equals(tdDiySite.getCity())){
					diyRemoveList.add(tdDiySite);
					if(tdDiySite.getStoreCode().equals(diyCode)){
						diyCode=null;
					}
				}
			}
			diyList.removeAll(diyRemoveList);
		}
		
		Date begin=null;
		Date end=null;
		try {//字符串转换为时间
			begin=stringToDate(orderStartTime,null);
			end=stringToDate(orderEndTime,null);
		} catch (Exception e) {
			System.out.println(e);
		}
		if(begin==null){//如果为空设置为默认时间
			begin=getStartTime();
			orderStartTime=dateToString(begin,null);
		}
		if(end==null){//如果为空设置为默认时间
			end=getEndTime();
			orderEndTime=dateToString(end,null);
		}
		
		/*if(!orderStartTime.equals(oldOrderStartTime) || !orderEndTime.equals(oldOrderEndTime)){
			//调用存储过程查询数据
			callProcedure(statusId, __EVENTTARGET, begin, end, username);
		}*/
		
		//根据报表类型 查询相应数据到map
		//addOrderListToMap(map, statusId, keywords, begin, end, diyCode, cityName, username, size, page,tdDiySiteRoleService.userRoleDiyId(tdManagerRole, tdManager));
	
		
    	//城市和门店信息
    	map.addAttribute("diySiteList",diyList);
		map.addAttribute("cityList", cityList);
		map.addAttribute("fit_company_ist",companyList);
		
		// 参数注回
		map.addAttribute("orderNumber", keywords);
		map.addAttribute("orderStartTime", orderStartTime);
		map.addAttribute("orderEndTime", orderEndTime);
		map.addAttribute("diyCode", diyCode);
		map.addAttribute("cityName", cityName);
		map.addAttribute("oldOrderStartTime", orderStartTime);
		map.addAttribute("oldOrderEndTime", orderEndTime);

		map.addAttribute("page", page);
		map.addAttribute("size", size);
		map.addAttribute("keywords", keywords);
		map.addAttribute("statusId", statusId);
		map.addAttribute("__EVENTTARGET", __EVENTTARGET);
		map.addAttribute("__EVENTARGUMENT", __EVENTARGUMENT);
		map.addAttribute("__VIEWSTATE", __VIEWSTATE);
		map.addAttribute("code",code);

		if(statusId==9){
			return "/site_mag/statement_list_reserve_order";
		}else if(statusId==16){
			return "/site_mag/fit_goods_in_out";
		}
		return "/site_mag/statement_list";
	}

	

	private String changeName(List<TdWareHouse> wareHouses,String number)
	{
//		郑州公司	11	总仓
//		天荣中转仓	1101	分仓
//		五龙口中转仓	1102	分仓
//		东大中转仓	1103	分仓
//		百姓中转仓	1104	分仓
//		主仓库	1105	分仓
		for (TdWareHouse tdWareHouse : wareHouses) {
			if(tdWareHouse.getWhNumber().equals(number)){
				return tdWareHouse.getWhName();
			}
		}
		return number;
	}
	
	/**
	 * 根据报表类型 调用相应的存储过程 插入数据
	 * @param statusId 报表类型
	 * @param __EVENTTARGET
	 * @param begin 开始时间
	 * @param end 结算时间
	 * @param username 当前用户
	 */
	@SuppressWarnings("unused")
	private void callProcedure(Long statusId,String __EVENTTARGET,Date begin,Date end,String username){
		try {//调用存储过程 报错
			if(null != __EVENTTARGET && __EVENTTARGET.equalsIgnoreCase("btnPage")){
				return;
			}else if(statusId==0){//出退货报表
//				tdGoodsInOutService.callinsertGoodsInOutInitial(begin, end,username);
			}else if(statusId==1){//代收款报表
//				tdAgencyFundService.callInsertAgencyFund(begin, end,username);
			}else if(statusId==2){//收款报表
//				tdReceiptService.callInsertReceipt(begin, end, username);
			}else if(statusId==3){//销售明细报表
//				tdSalesDetailService.callInsertSalesDetail(begin, end, username);
			}else if(statusId==4){//退货报表
				tdReturnReportService.callInsertReturnReport(begin, end, username);
			}
		} catch (Exception e) {
			System.out.println(e);
		}
	}
	
	/**
	 * 根据报表类型 查询相应数据到map
	 * 增加门店id查询
	 * @param map 
	 * @param statusId 报表类型
	 * @param keywords 订单号
	 * @param begin 开始时间
	 * @param end 结算时间
	 * @param diySiteCode 门店编号
	 * @param cityName 城市名称
	 * @param username 当前用户
	 * @param roleDiyIds 门店id集合
	 * @param size
	 * @param page
	 */
	@SuppressWarnings("unused")
	private void addOrderListToMap(ModelMap map,Long statusId,String keywords,Date begin,Date end,String diySiteCode,String cityName,String username,
			int size,int page,List<String> roleDiyIds){
		if(statusId==0){//出退货报表
//			map.addAttribute("order_page",tdGoodsInOutService.searchList(keywords,begin, end, diySiteCode, cityName,username, size, page,roleDiyIds));
		}else if(statusId==1){//代收款报表
			map.addAttribute("order_page",tdAgencyFundService.searchList(keywords,begin, end,cityName ,diySiteCode ,username, size, page,roleDiyIds));
		}else if(statusId==2){//收款报表
//			map.addAttribute("order_page",tdGatheringService.searchList(keywords,begin, end,cityName ,diySiteCode ,username, size, page,roleDiyIds));
		}else if(statusId==3){//销售明细报表
//			map.addAttribute("order_page",tdSalesDetailService.queryPageList(begin, end, cityName, diySiteCode, roleDiyIds, size, page));
		}else if(statusId==4){//退货报表
			map.addAttribute("order_page",tdReturnReportService.searchList(keywords,begin, end,cityName ,diySiteCode ,username, size, page,roleDiyIds));
		}

	}
	/**
	 * 根据报表类型获取报表名
	 * @param statusId
	 * @return
	 */
	private String acquireFileName(Long statusId){
		String fileName="";
		if(statusId==0){
			fileName="配送单出退货报表";
		}else if(statusId==1){
			fileName= "代收款报表";
		}else if(statusId==2){
			fileName= "收款报表";
		}else if(statusId==3){
			fileName= "销售明细报表";
		}else if(statusId==4){
			fileName= "退货报表";
		}else if(statusId==5){
			fileName= "领用记录报表";
		}else if(statusId==6) {
			fileName="自提单出退货报表";
		}else if(statusId==7){
			fileName="PK销量报表";
		}else if(statusId==8){
			fileName="欠款报表";
		}else if(statusId==9){
			fileName="未提货报表";
		}else if(statusId==10){
			fileName="活跃会员报表";
		}else if(statusId==11){
			fileName="配送考核报表";
		}else if(statusId==12){
			fileName="加盟商对账报表";
		}else if(statusId==13){
			fileName="会员连续购买记录";
		}else if(statusId==14){
			fileName="乐易装运费报表";
		}else if(statusId==15){
			fileName="乐易装运费报表(备用)";
		}else if(statusId==16){
			fileName="装饰公司销售明细报表";
		}else if(statusId==17){
			fileName="预存款变更商品明细表";
		}else if(statusId==18){
			fileName="商品出退货报表";
		}
		return fileName;
	}
	/**
	 * 根据报表状态 设置相应的报表
	 * @param statusId
	 * @param begin 开始时间
	 * @param end 结算时间
	 * @param diyCode 门店编号
	 * @param cityName 城市名称
	 * @param username 当前用户
	 * @return
	 * @throws Exception 
	 */
	private HSSFWorkbook acquireHSSWorkBook(Long statusId,Date begin,Date end,String diyCode,String cityName,String username,List<String> roleDiyIds,String code) throws Exception{
		HSSFWorkbook wb= new HSSFWorkbook();  
		if(statusId==0){//配送单出退货明细报表
			wb=goodsInOutWorkBook(begin, end, diyCode, cityName, username,roleDiyIds);
		}else if(statusId==1){//代收款报表
			wb=agencyFundWorkBook(begin, end, diyCode, cityName, username,roleDiyIds);
		}else if(statusId==2){//收款报表
			wb=payWorkBook(begin, end, diyCode, cityName, username,roleDiyIds);
		}else if(statusId==3){//销售明细报表
			wb=salesDetailWorkBook(begin, end, diyCode, cityName, username,roleDiyIds);
		}else if(statusId==4){//退货报表
			wb=returnWorkBook(begin, end, diyCode, cityName, username,roleDiyIds);
		}else if(statusId==6) {//自提单出退货明细报表
			wb=goodsInOutTakeWorkBook(begin, end, diyCode, cityName, username, roleDiyIds);
		}else if(statusId==7){//销量报表
			wb=SalesWorkBook(begin, end, diyCode, cityName, username, roleDiyIds);
		}else if(statusId==8){//欠款报表
			wb=OwnWorkBook(begin, end, diyCode, cityName, username, roleDiyIds);
		}else if(statusId==9){//未提货报表
			wb=ReserveOrderBook(diyCode, cityName, username, roleDiyIds);
		}else if(statusId==10){//活跃会员报表
			wb=salesForActiveUserBook(begin, end, diyCode, cityName, username, roleDiyIds);
		}else if(statusId==11){//配送考核报表
			wb=deliveryCheckBook(begin, end, diyCode, cityName, username, roleDiyIds);
		}else if(statusId==12){//加盟商对账报表
			wb=garmentFranchisor(begin, end, diyCode, cityName, username, roleDiyIds);
		}else if(statusId==13){//会员连续月份购买报表
			wb=continuousBuyBook(begin,end,diyCode,cityName,username,roleDiyIds);
		}else if(statusId==14){//乐易装华润运费报表
			wb=LyzHrDeliveryFeeBook(begin,end,diyCode,cityName,username,roleDiyIds);
		}else if(statusId==15){//乐易装华润运费报表（备用）
			wb=LyzHrDeliveryFeeBookBackUp(begin,end,diyCode,cityName,username,roleDiyIds);
		}else if(statusId==16){//装饰公司出退货报表
			wb=fitGoodsInOutBook(begin,end,code,cityName,username);
		}else if(statusId==17){//预存款变更商品明细表
			wb=salesDetailForFranchiser(begin, end, diyCode, cityName, username,roleDiyIds);
		}else if(statusId==18){//商品出退货报表
			wb=franchiseesGoodsInOutWorkBook(begin, end, diyCode, cityName, username,roleDiyIds);
		}
		return wb;
	}
	
	
	

	

	private HSSFWorkbook fitGoodsInOutBook(Date begin, Date end, String code, String cityName, String username) {
		
		// 第一步，创建一个webbook，对应一个Excel文件 
        HSSFWorkbook wb = new HSSFWorkbook();  
        
        List<FitGoodsInOut> goodsInOutList=fitGoodsInOutService.queryDownList(begin, end, cityName, code);
        
        int maxRowNum = 60000;
        int maxSize=0;
        if(goodsInOutList!=null){
        	maxSize=goodsInOutList.size();
        }
        int sheets = maxSize/maxRowNum+1;
        
    	//写入excel文件数据信息
		for(int i=0;i<sheets;i++){
			
			// 第二步，在webbook中添加一个sheet,对应Excel文件中的sheet  
	        HSSFSheet sheet = wb.createSheet("第"+(i+1)+"页");  
	        // 第三步，在sheet中添加表头第0行,注意老版本poi对Excel的行数列数有限制short  
	        //列宽
	        int[] widths={25,25,25,20,20,15,15,15,15,15,15,15,15,10,10,10,10,10,15,15,15,15,15,15,15,15,15,30,50};
	        sheetColumnWidth(sheet,widths);
	        
	        // 第四步，创建单元格，并设置值表头 设置表头居中  
	        HSSFCellStyle style = wb.createCellStyle();  
	        style.setAlignment(HSSFCellStyle.ALIGN_CENTER); // 创建一个居中格式
	        style.setWrapText(true);


	       	//设置标题
	        HSSFRow row = sheet.createRow((int) 0); 
	        
	        String[] cellValues={"装饰公司名称","主单号","分单号","下单日期","出&退货日期","配送状态","订单状态","采购经理","客户编号","工头姓名","工头电话","产品编号",
	        		"产品名称","数量","零售单价","零售总价","结算单价","结算总价","品牌","商品父分类","商品子分类","配送方式","中转仓",
	        		"配送人员","配送人员电话","收货人姓名","收货人电话","收货人地址","订单备注"};
			cellDates(cellValues, style, row);
			
			for(int j=0;j<maxRowNum;j++)
	        {
				if(j+i*maxRowNum>=maxSize){
					break;
				}
				FitGoodsInOut fitGoodsInOut= goodsInOutList.get(j+i*maxRowNum);
	        	row = sheet.createRow((int) j + 1);
	        	//门店名称
	        	row.createCell(0).setCellValue(objToString(fitGoodsInOut.getDiySiteName()));
	        	//主单号
	        	if(null != fitGoodsInOut.getMainOrderNumber()){
	        		row.createCell(1).setCellValue(objToString(fitGoodsInOut.getMainOrderNumber()));
	        	}else{
	        		row.createCell(1).setCellValue("");
	        	}
	        	
	            //分单号
	        	if(null != fitGoodsInOut.getOrderNumber()){
	        		row.createCell(2).setCellValue(objToString(fitGoodsInOut.getOrderNumber()));
	        	}else{
	        		row.createCell(2).setCellValue("");
	        	}
	            
	            //下单日期
	            if(null != fitGoodsInOut.getOrderTime()){
	            	row.createCell(3).setCellValue(objToString(fitGoodsInOut.getOrderTime()));
	            }else{
	            	row.createCell(3).setCellValue("");
	            }
	            
	            //出&退货日期
	            if(null != fitGoodsInOut.getSalesTime()){
	            	row.createCell(4).setCellValue(objToString(fitGoodsInOut.getSalesTime()));
	            }else{
	            	row.createCell(4).setCellValue("");
	            }
	            
	            //配送状态
	            if(fitGoodsInOut.getStatusId()>=4 && fitGoodsInOut.getStatusId() !=7 && fitGoodsInOut.getStatusId() !=8){
	            	row.createCell(5).setCellValue("已出货");	
	            }else{
	            	row.createCell(5).setCellValue("未出货");	
	            }
	            //订单状态
	            if(null != fitGoodsInOut.getStatusId()){
	            	switch (fitGoodsInOut.getStatusId()) {
		            case 1:
		            	row.createCell(6).setCellValue("待审核");
		            	break;
		            case 2:
		            	row.createCell(6).setCellValue("待付款");
		            	break;
		            case 3:
		            	row.createCell(6).setCellValue("待出库");
		            	break;
					case 4:
						row.createCell(6).setCellValue("待签收");
						break;
					case 5:
						row.createCell(6).setCellValue("待评价");
					case 6:
						row.createCell(6).setCellValue("已完成");
						break;
					case 7:
						row.createCell(6).setCellValue("已取消");
						break;
					case 8:
						row.createCell(6).setCellValue("已删除");
						break;
					case 9:
						row.createCell(6).setCellValue("退货中");
						break;
					case 10:
						row.createCell(6).setCellValue("退货确认");
						break;
					case 11:
						row.createCell(6).setCellValue("退货取消");
						break;
					case 12:
						row.createCell(6).setCellValue("退货完成");
						break;
					default:
						break;
					}
	            }else{
	            	row.createCell(6).setCellValue("");
	            }
	            
	            
	            //采购经理	
	            if(null != fitGoodsInOut.getSellerRealName()){
	            	row.createCell(7).setCellValue(objToString(fitGoodsInOut.getSellerRealName()));
	            }else{
	            	row.createCell(7).setCellValue("");
	            }
	            
	            //客户编号
	            if(null != fitGoodsInOut.getRealUserUsername()){
	            	row.createCell(8).setCellValue(objToString(fitGoodsInOut.getRealUserUsername()));
	            }else{
	            	row.createCell(8).setCellValue("");
	            }
	           
	            //工头姓名
	            if(null != fitGoodsInOut.getRealUserRealName()){
	            	row.createCell(9).setCellValue(objToString(fitGoodsInOut.getRealUserRealName()));
	            }else{
	            	row.createCell(9).setCellValue("");
	            }
	            
	            //工头电话
	            if(null != fitGoodsInOut.getRealUserUsername()){
	            	row.createCell(10).setCellValue(objToString(fitGoodsInOut.getRealUserUsername()));
	            }else{
	            	row.createCell(10).setCellValue("");
	            }
	            
	            //产品编号
	            if(null != fitGoodsInOut.getSku()){
	            	row.createCell(11).setCellValue(objToString(fitGoodsInOut.getSku()));
	            }else{
	            	row.createCell(11).setCellValue("");
	            }
	            
	            //产品名称
	            if(null != fitGoodsInOut.getGoodsTitle()){
	            	row.createCell(12).setCellValue(objToString(fitGoodsInOut.getGoodsTitle()));
	            }else{
	            	row.createCell(12).setCellValue("");
	            }
	            
	            //数量
	            if(null != fitGoodsInOut.getQuantity()){
	            	row.createCell(13).setCellValue(objToString(fitGoodsInOut.getQuantity()));
	            }else{
	            	row.createCell(13).setCellValue("");
	            }
	            
	            
	            //零售单价
	            if(null != fitGoodsInOut.getPrice()){
	            	row.createCell(14).setCellValue(objToString(fitGoodsInOut.getPrice()));
	            }else{
	            	row.createCell(14).setCellValue("");
	            }
	           
	            //零售总价
	            if(null != fitGoodsInOut.getPrice() && null != fitGoodsInOut.getQuantity()){
	            	row.createCell(15).setCellValue(objToString(fitGoodsInOut.getPrice()*fitGoodsInOut.getQuantity()));
	            }else{
	            	row.createCell(15).setCellValue("");
	            }
	            
	            //结算单价
	            if(null != fitGoodsInOut.getRealPrice()){
	            	row.createCell(16).setCellValue(objToString(fitGoodsInOut.getRealPrice()));
	            }else{
	            	row.createCell(16).setCellValue("");
	            }
	            
	            
	            //结算总价
	            if(null !=fitGoodsInOut.getRealPrice() && null != fitGoodsInOut.getQuantity() ){
	            	row.createCell(17).setCellValue(objToString(fitGoodsInOut.getRealPrice()*fitGoodsInOut.getQuantity()));
	            }else{
	            	row.createCell(17).setCellValue("");
	            }
	            
	            //品牌
	            if(null != fitGoodsInOut.getBrandTitle()){
	            	row.createCell(18).setCellValue(objToString(fitGoodsInOut.getBrandTitle()));
	            }else{
	            	row.createCell(18).setCellValue("");
	            }
	            
	            //商品父分类
	            if(null != fitGoodsInOut.getGoodsParentTypeTitle()){
	            	row.createCell(19).setCellValue(objToString(fitGoodsInOut.getGoodsParentTypeTitle()));
	            }else{
	            	row.createCell(19).setCellValue("");
	            }
	            
	            //商品子分类
	            if(null != fitGoodsInOut.getGoodsTypeTitle()){
	            	row.createCell(20).setCellValue(objToString(fitGoodsInOut.getGoodsTypeTitle()));
	            }else{
	            	row.createCell(20).setCellValue("");
	            }
	            
	            //配送方式	　
	            if(null != fitGoodsInOut.getDeliverTypeTitle()){
	            	row.createCell(21).setCellValue(objToString(fitGoodsInOut.getDeliverTypeTitle()));
	            }else{
	            	row.createCell(21).setCellValue("");
	            }
	            
	            //中转仓
	            if(null != fitGoodsInOut.getWhName()){
	            	row.createCell(22).setCellValue(objToString(fitGoodsInOut.getWhName()));
	            }else{
	            	row.createCell(22).setCellValue("");
	            }
	            
	            //配送人员
	            if(null != fitGoodsInOut.getDeliverRealName()){
	            	row.createCell(23).setCellValue(objToString(fitGoodsInOut.getDeliverRealName()));
	            }else{
	            	row.createCell(23).setCellValue("");
	            }
	            
	            //配送人员电话
	            if(null != fitGoodsInOut.getDeliverUsername()){
	            	row.createCell(24).setCellValue(objToString(fitGoodsInOut.getDeliverUsername()));
	            }else{
	            	row.createCell(24).setCellValue(objToString(""));
	            }
	            //收货人姓名
	            if(null != fitGoodsInOut.getShippingName()){
	            	row.createCell(25).setCellValue(objToString(fitGoodsInOut.getShippingName()));
	            }else{
	            	row.createCell(25).setCellValue(objToString(""));
	            }
	            //收货人电话
	            if(null != fitGoodsInOut.getShippingPhone()){
	            	row.createCell(26).setCellValue(objToString(fitGoodsInOut.getShippingPhone()));
	            }else{
	            	row.createCell(26).setCellValue(objToString(""));
	            }
	            //收货人地址
	            if(null != fitGoodsInOut.getShippingAddress()){
	            	row.createCell(27).setCellValue(objToString(fitGoodsInOut.getShippingAddress()));
	            }else{
	            	row.createCell(27).setCellValue(objToString(""));
	            }
	            
	            //订单备注
	            if(null != fitGoodsInOut.getRemark()){
	            	row.createCell(28).setCellValue(objToString(fitGoodsInOut.getRemark()));
	            }else{
	            	row.createCell(28).setCellValue(objToString(""));
	            }
	        	
			}
		}
		return wb;
	}

	/**
	 * 出退货明细报表
	 * 查询条件增加管理员管辖门店
	 * @param begin 开始时间
	 * @param end 结束时间
	 * @param diyCode 门店编号
	 * @param cityName 城市编号
 	 * @param username 当前用户
	 * @return
	 */
	private HSSFWorkbook goodsInOutWorkBook(Date begin,Date end,String diyCode,String cityName,String username,List<String> roleDiyIds){
		// 第一步，创建一个webbook，对应一个Excel文件 
        HSSFWorkbook wb = new HSSFWorkbook();  
        
 
        // 第五步，设置值  
        List<TdGoodsInOut> goodsInOutList=tdGoodsInOutService.queryDownList(begin, end, cityName, diyCode, roleDiyIds);
        
//        long startTimne = System.currentTimeMillis();
        
        //excel单表最大行数是65535
        int maxRowNum = 60000;
        int maxSize=0;
        if(goodsInOutList!=null){
        	maxSize=goodsInOutList.size();
        }
        int sheets = maxSize/maxRowNum+1;
        
		//写入excel文件数据信息
		for(int i=0;i<sheets;i++){
			
			// 第二步，在webbook中添加一个sheet,对应Excel文件中的sheet  
	        HSSFSheet sheet = wb.createSheet("第"+(i+1)+"页");  
	        // 第三步，在sheet中添加表头第0行,注意老版本poi对Excel的行数列数有限制short  
	        //列宽
	        int[] widths={13,25,25,18,18,13,18,18,13,13,
	        		18,9,9,9,9,9,13,13,13,18,
	        		13,13,13,13,20,20};
	        sheetColumnWidth(sheet,widths);
	        
	        // 第四步，创建单元格，并设置值表头 设置表头居中  
	        HSSFCellStyle style = wb.createCellStyle();  
	        style.setAlignment(HSSFCellStyle.ALIGN_CENTER); // 创建一个居中格式
	        style.setWrapText(true);


	       	//设置标题
	        HSSFRow row = sheet.createRow((int) 0); 
	        
	        String[] cellValues={"门店名称","主单号","分单号","下单日期","出&退货日期","订单状态","导购","客户编号","客户名称","客户电话","产品编号",
	        		"产品名称","数量","结算单价","结算总价","品牌","商品父分类","商品子分类","配送方式","中转仓",
	        		"配送人员","配送人员电话","收货人姓名","收货人电话","收货人地址","订单备注"};
			cellDates(cellValues, style, row);
			
			for(int j=0;j<maxRowNum;j++)
	        {
				if(j+i*maxRowNum>=maxSize){
					break;
				}
				TdGoodsInOut goodsInOut= goodsInOutList.get(j+i*maxRowNum);
	        	row = sheet.createRow((int) j + 1);
	        	//门店名称
	        	row.createCell(0).setCellValue(objToString(goodsInOut.getDiySiteName()));

	        	//代付款订单没有主单号  分单号显示到主单号位置
	        	if(goodsInOut.getStatusId() != null && goodsInOut.getStatusId().equals(2L)){
	        		row.createCell(1).setCellValue(objToString(goodsInOut.getOrderNumber()));
	        	}else{
	        		row.createCell(1).setCellValue(objToString(goodsInOut.getMainOrderNumber()));
	        		row.createCell(2).setCellValue(objToString(goodsInOut.getOrderNumber()));
	        	} 
	        	//下单时间
	        	row.createCell(3).setCellValue(objToString(goodsInOut.getOrderTime()));
	        	//确认时间
	        	row.createCell(4).setCellValue(objToString(goodsInOut.getSalesTime()));
	        	//订单状态
	        	row.createCell(5).setCellValue(orderStatus(goodsInOut.getStatusId()));
	        	//导购
				row.createCell(6).setCellValue(objToString(goodsInOut.getSellerRealName()));
				//会员编号ID
				row.createCell(7).setCellValue(objToString(goodsInOut.getUserId()));
				//客户名称
				row.createCell(8).setCellValue(objToString(goodsInOut.getRealName()));
				//客户电话
				row.createCell(9).setCellValue(objToString(replacePhoneNumberWithStar(goodsInOut.getUsername())));
				//产品编号
				row.createCell(10).setCellValue(objToString(goodsInOut.getSku()));
				//产品名称
				row.createCell(11).setCellValue(objToString(goodsInOut.getGoodsTitle()));
				//产品数量
				row.createCell(12).setCellValue(objToString(goodsInOut.getQuantity()));
				//产品价格
				row.createCell(13).setCellValue(objToString(goodsInOut.getPrice()));
				//产品总价
				row.createCell(14).setCellValue(objToString((goodsInOut.getPrice()*goodsInOut.getQuantity()*100)/100));
	        	//现金卷
	            //row.createCell(15).setCellValue(objToString(goodsInOut.getCashCoupon()));
	          	//品牌
				row.createCell(15).setCellValue(objToString(goodsInOut.getBrandTitle()));
	    		//商品父分类
				row.createCell(16).setCellValue(objToString(goodsInOut.getGoodsParentTypeTitle()));
				//商品子分类
	        	row.createCell(17).setCellValue(objToString(goodsInOut.getGoodsTypeTitle()));
				//配送方式
	        	row.createCell(18).setCellValue(objToString(goodsInOut.getDeliverTypeTitle()));
				//中转仓
	            row.createCell(19).setCellValue(objToString(goodsInOut.getWhName()));
	    		//配送人员
	        	row.createCell(20).setCellValue(objToString(goodsInOut.getDeliverRealName()));
	        	//配送人员电话
	        	row.createCell(21).setCellValue(objToString(goodsInOut.getDeliverUsername()));
	        	//收货人姓名 收货人电话 收货人地址
	        	if(!"门店自提".equals(goodsInOut.getDeliverTypeTitle())){
	        		row.createCell(22).setCellValue(objToString(goodsInOut.getShippingName()));
	        		row.createCell(23).setCellValue(objToString(goodsInOut.getShippingPhone()));
	        		row.createCell(24).setCellValue(objToString(goodsInOut.getShippingAddress()));
	        	}
	        	//订单备注
	        	row.createCell(25).setCellValue(objToString(goodsInOut.getRemark()));
//	            System.out.println("正在生成excel文件的 sheet"+(i+1)+"第"+(j+1)+"行");
			}
//			System.out.println("正在生成excel文件的 sheet"+(i+1));
		}
        
        
//        long endTime = System.currentTimeMillis();
//        System.out.println("用时="+((endTime-startTimne)/1000)+"秒");
        return wb;
        
	}
	
	/**
	 * 代收款报表(1.1修改)
	 * @param begin 开始时间
	 * @param end 结束时间
	 * @param diyCode 门店编号
	 * @param cityName 城市编号
 	 * @param username 当前用户
	 * @return
	 */
	private HSSFWorkbook agencyFundWorkBook(Date begin,Date end,String diyCode,String cityName,String username,List<String> roleDiyIds){
		// 第一步，创建一个webbook，对应一个Excel文件 
        HSSFWorkbook wb = new HSSFWorkbook();  
        //List<TdAgencyFund> agencyFundList = tdAgencyFundService.searchAgencyFund(begin, end, cityName, diyCode,username,roleDiyIds);
        
        // 修改代收款报表逻辑和呈现方式  ----- 2016-11-14 10:43 ----- 闫乐
        
        String warehouse = null;
        /*if(username.equalsIgnoreCase("dafengcang")||username.equalsIgnoreCase("hangtiancang")||username.equalsIgnoreCase("jitoucang")){
        	warehouse = 
        }*/
        switch (username) {
		case "dafengcang":
			warehouse = "1301";
			break;
		case "hangtiancang":
			warehouse = "1302";
			break;
		case "jitoucang":
			warehouse = "1303";
			break;

		default:
			warehouse = "%";
			break;
		}
        
        List<TdAgencyFund> agencyFundList = tdAgencyFundService.queryDownList(begin, end, cityName, diyCode,username,roleDiyIds,warehouse);
  
        
        
        //excel单表最大行数是65535
        int maxRowNum = 60000;
        int maxSize=0;
        if(agencyFundList!=null){
        	maxSize=agencyFundList.size();
        }
        int sheets = maxSize/maxRowNum+1;
		//写入excel文件数据信息
		for(int i=0;i<sheets;i++){
			
			// 第二步，在webbook中添加一个sheet,对应Excel文件中的sheet  
	        HSSFSheet sheet = wb.createSheet("第"+(i+1)+"页");  
	        // 第三步，在sheet中添加表头第0行,注意老版本poi对Excel的行数列数有限制short  
	        //列宽
	        int[] widths={13,25,13,13,18,13,13,9,9,9,
	        		9,9,13,13,13,13,13,25,18,18,
	        		25};
	        sheetColumnWidth(sheet,widths);
	        
	        // 第四步，创建单元格，并设置值表头 设置表头居中  
	        HSSFCellStyle style = wb.createCellStyle();  
	        style.setAlignment(HSSFCellStyle.ALIGN_CENTER); // 创建一个居中格式
	        style.setWrapText(true);


	       	//设置标题
	        HSSFRow row = sheet.createRow((int) 0); 
	        //版本1.1
	        String[] cellValues={"门店名称","主单号","订单状态","导购","订单日期","客户编号","客户名称","客户电话","需代收金额","实收现金","实收pos",
	        		"实收总金额","欠款","仓库名称","配送人员","配送人电话","收货人","收货人电话","收货人地址","预约配送时间","实际配送时间",
	        		"备注信息","是否审核","是否通过"};
			cellDates(cellValues, style, row);
			
			for(int j=0;j<maxRowNum;j++)
	        {
				if(j+i*maxRowNum>=maxSize){
					break;
				}
				TdAgencyFund agencyFund= agencyFundList.get(j+i*maxRowNum);
				
				row = sheet.createRow((int) j + 1);
				//门店名称
				row.createCell(0).setCellValue(objToString(agencyFund.getDiySiteName()));
				//主单号
				row.createCell(1).setCellValue(objToString(agencyFund.getMainOrderNumber()));
				//订单状态
				String statusStr = orderStatus(agencyFund.getStatusId());
				row.createCell(2).setCellValue(objToString(statusStr));
				//导购
				row.createCell(3).setCellValue(objToString(agencyFund.getSellerRealName()));
				//订单日期
				row.createCell(4).setCellValue(objToString(dateToString(agencyFund.getOrderTime(), null)));
				//客户编号
				row.createCell(5).setCellValue(objToString(agencyFund.getUserId()));
				//客户名称
				row.createCell(6).setCellValue(objToString(agencyFund.getRealUserRealName()));
				//客户电话
				row.createCell(7).setCellValue(objToString(replacePhoneNumberWithStar(agencyFund.getRealUserUsername())));
				//需代收金额
				if (null != agencyFund.getPayPrice() && !"".equals(agencyFund.getPayPrice())) {
					row.createCell(8).setCellValue(objToString(agencyFund.getPayPrice()));
				}else{
					agencyFund.setPayPrice(0.0);
					row.createCell(8).setCellValue(0.0);
				}
				//实收现金
				if (null != agencyFund.getPayMoney() && !"".equals(agencyFund.getPayMoney())) {
					row.createCell(9).setCellValue(objToString(agencyFund.getPayMoney()));
				}else{
					agencyFund.setPayMoney(0.0);
					row.createCell(9).setCellValue(0.0);
				}
				
				//实收pos
				if (null != agencyFund.getPayPos() && !"".equals(agencyFund.getPayPos())) {
					row.createCell(10).setCellValue(objToString(agencyFund.getPayPos()));
				}else{
					agencyFund.setPayPos(0.0);
					row.createCell(10).setCellValue(objToString(0.0));
				}
				
				//实收总金额
				row.createCell(11).setCellValue(objToString(agencyFund.getPayMoney()+agencyFund.getPayPos()));
				//欠款
				row.createCell(12).setCellValue(objToString(agencyFund.getPayPrice()-agencyFund.getPayMoney()-agencyFund.getPayPos()));
				//仓库名称
				row.createCell(13).setCellValue(objToString(agencyFund.getWhName()));
				//配送人员
				row.createCell(14).setCellValue(objToString(agencyFund.getDeliveryName()));
				//配送人电话
				row.createCell(15).setCellValue(objToString(agencyFund.getDeliveryPhone()));
				//收货人
				row.createCell(16).setCellValue(objToString(agencyFund.getShippingName()));
				//收货人电话
				row.createCell(17).setCellValue(objToString(agencyFund.getShippingPhone()));
				//收货人地址
				row.createCell(18).setCellValue(objToString(agencyFund.getShippingAddress()));
				//预约配送时间
				String dayTime = agencyFund.getDeliveryDate();
				dayTime = dayTime + " " + agencyFund.getDeliveryDetailId() + ":30";
				row.createCell(19).setCellValue(objToString(dayTime));
				//实际配送时间
				row.createCell(20).setCellValue(objToString(dateToString(agencyFund.getDeliveryTime(), null)));
				//备注信息
				row.createCell(21).setCellValue(objToString(agencyFund.getRemark()));
				if (null !=agencyFund.getIsEnable() && (agencyFund.getIsEnable()==true)) {
					row.createCell(22).setCellValue("已审核");
					if(null != agencyFund.getIsPassed() && agencyFund.getIsPassed()==true){
						row.createCell(23).setCellValue("已通过");
					}else{
						row.createCell(23).setCellValue("未通过");
					}
				}else{
					row.createCell(22).setCellValue("未审核");
					row.createCell(23).setCellValue("未通过");
				}
				
	        }
		}
        return wb;
	}
	
	/**
	 * 收款报表
	 * @param begin 开始时间
	 * @param end 结束时间
	 * @param diyCode 门店编号
	 * @param cityName 城市编号
 	 * @param username 当前用户

	 */
	private HSSFWorkbook payWorkBook(Date begin,Date end,String diyCode,String cityName,String username,List<String> roleDiyIds){
		// 第一步，创建一个webbook，对应一个Excel文件 
        HSSFWorkbook wb = new HSSFWorkbook();  
        
 
        // 第五步，设置值  
        //List<TdReceipt> receiptList = tdReceiptService.searchReceipt(begin, end, cityName, diyCode, username, roleDiyIds);
        List<TdReceipt> receiptList = tdReceiptService.queryDownList(begin, end, cityName, diyCode, username, roleDiyIds);
//        long startTimne = System.currentTimeMillis();
        
        //excel单表最大行数是65535
        int maxRowNum = 60000;
        int maxSize=0;
        if(receiptList!=null){
        	maxSize=receiptList.size();
        }
        int sheets = maxSize/maxRowNum+1;
        
		//写入excel文件数据信息
		for(int i=0;i<sheets;i++){
			
			// 第二步，在webbook中添加一个sheet,对应Excel文件中的sheet  
	        HSSFSheet sheet = wb.createSheet("第"+(i+1)+"页");  
	        // 第三步，在sheet中添加表头第0行,注意老版本poi对Excel的行数列数有限制short  
	        //列宽
	        int[] widths={18,13,25,18,18,13,13,20,18,18,18,
	        		18,18,18,18,18,18,18};
	        sheetColumnWidth(sheet,widths);
	        
	        // 第四步，创建单元格，并设置值表头 设置表头居中  
	        HSSFCellStyle style = wb.createCellStyle();  
	        style.setAlignment(HSSFCellStyle.ALIGN_CENTER); // 创建一个居中格式
	        style.setWrapText(true);


	       	//设置标题
	        HSSFRow row = sheet.createRow((int) 0); 
	        
	        String[] cellValues={"门店名称","付款类型","主单号","会员编号","会员名称","会员电话","导购","下单时间","配送方式","收款时间&退款时间","预存款","第三方支付","门店现金",
					"门店POS","门店其他","汇总","门店真实收款时间","POS刷卡流水号"};
			cellDates(cellValues, style, row);
			
			for(int j=0;j<maxRowNum;j++)
	        {
				if(j+i*maxRowNum>=maxSize){
					break;
				}
				TdReceipt receipt = receiptList.get(j+i*maxRowNum);
	        	row = sheet.createRow((int) j + 1);
	        	if (null != receipt.getDiySiteName())
	        	{//门店名称
	            	row.createCell(0).setCellValue(receipt.getDiySiteName());
	    		}
	        	if (null != receipt.getReceiptType())
	        	{//付款类型
	            	row.createCell(1).setCellValue(receipt.getReceiptType());
	    		}
	        	if (null != receipt.getMainOrderNumber())
	        	{//主单号
	            	row.createCell(2).setCellValue(receipt.getMainOrderNumber());
	    		}
	        	/*if (null != receipt.getOrderNumber())
	        	{//分单号
	            	row.createCell(3).setCellValue(receipt.getOrderNumber());
	    		}*/
	        	if (null != receipt.getUserId())//会员编号
	        	{
	            	row.createCell(3).setCellValue(receipt.getUserId());
	    		}
	        	if (null != receipt.getRealUserRealName())
	        	{//会员名称
	            	row.createCell(4).setCellValue(receipt.getRealUserRealName());
	    		}
	        	if (null != receipt.getUsername()) {
	        		//会员电话
	        		row.createCell(5).setCellValue(replacePhoneNumberWithStar(receipt.getUsername()));
				}
	        	if (null != receipt.getSellerRealName())
	        	{//导购
	            	row.createCell(6).setCellValue(receipt.getSellerRealName());
	    		}
	        	if (null != receipt.getOrderTime())
	        	{//订单日期
	        		Date orderTime = receipt.getOrderTime();
	        		String orderTimeStr = orderTime.toString();
	            	row.createCell(7).setCellValue(orderTimeStr);
	    		}
	        	if (null != receipt.getDeliverTypeTitle())
	        	{//配送方式
	            	row.createCell(8).setCellValue(receipt.getDeliverTypeTitle());
	    		}
	        	if (null != receipt.getReceiptTime())
	        	{//收款&退款时间
	            	row.createCell(9).setCellValue(receipt.getReceiptTime());
	    		}
	        	if (null != receipt.getActualPay())
	        	{//预存款
	            	row.createCell(10).setCellValue(receipt.getActualPay());
	    		}
	        	if (null != receipt.getOtherPay())
	        	{//第三方支付
	            	row.createCell(11).setCellValue(receipt.getOtherPay());
	    		}
	        	if (null != receipt.getDiyCash())
	        	{//门店现金
	            	row.createCell(12).setCellValue(receipt.getDiyCash());
	    		}
	        	if (null != receipt.getDiyPos())
	        	{//门店POS
	            	row.createCell(13).setCellValue(receipt.getDiyPos());
	    		}
	        	if(null != receipt.getDiyOther()){
	        		row.createCell(14).setCellValue(receipt.getDiyOther());
	        	}
	        	
	        	if(null!= receipt.getSummary()){//汇总 
	        			row.createCell(15).setCellValue(receipt.getSummary());
	        			
	        	}
	        	//门店收款时间
	        	if(null !=receipt.getRealPayTime()){
	        		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd") ;
	        		row.createCell(16).setCellValue(sdf.format(receipt.getRealPayTime()).toString());
	        	}
	        	//POS刷卡流水号
	        	if(null != receipt.getSerialNumber()){
	        		row.createCell(17).setCellValue(receipt.getSerialNumber());
	        	}
	        	
//	            System.out.println("正在生成excel文件的 sheet"+(i+1)+"第"+(j+1)+"行");
			}
//			System.out.println("正在生成excel文件的 sheet"+(i+1));
		}
        
        
//        long endTime = System.currentTimeMillis();
//        System.out.println("用时="+((endTime-startTimne)/1000)+"秒");
        return wb;
	}
	/**
	 * 销售细报表
	 * @param begin 开始时间
	 * @param end 结束时间
	 * @param diyCode 门店编号
	 * @param cityName 城市编号
 	 * @param username 当前用户
	 * @return
	 */
	private HSSFWorkbook salesDetailWorkBook(Date begin,Date end,String diyCode,String cityName,String username,List<String> roleDiyIds){
		// 第一步，创建一个webbook，对应一个Excel文件 
        HSSFWorkbook wb = new HSSFWorkbook();  
        
 
        // 第五步，设置值  
        List<TdSalesDetail> salesDetailList= tdSalesDetailService.queryDownList(begin, end, cityName, diyCode, roleDiyIds);
//      long startTimne = System.currentTimeMillis();

        //excel单表最大行数是65535
        int maxRowNum = 60000;
        int maxSize=0;
        if(salesDetailList!=null){
        	maxSize=salesDetailList.size();
        }
        int sheets = maxSize/maxRowNum+1;
        
		//写入excel文件数据信息
		for(int i=0;i<sheets;i++){
			
			// 第二步，在webbook中添加一个sheet,对应Excel文件中的sheet  
	        HSSFSheet sheet = wb.createSheet("第"+(i+1)+"页");  
	        // 第三步，在sheet中添加表头第0行,注意老版本poi对Excel的行数列数有限制short  
	        //列宽
	        int[] widths={13,25,25,18,13,18,18,18,13,13,18,
	        		9,9,9,9,9,13,13,13,18,13,
	        		13,13,13,20,20};
	        sheetColumnWidth(sheet,widths);
	        
	        // 第四步，创建单元格，并设置值表头 设置表头居中  
	        HSSFCellStyle style = wb.createCellStyle();  
	        style.setAlignment(HSSFCellStyle.ALIGN_CENTER); // 创建一个居中格式
	        style.setWrapText(true);


	       	//设置标题
	        HSSFRow row = sheet.createRow((int) 0); 
	        
	        String[] cellValues={"门店名称","主单号","分单号","下单日期","订单状态","导购","客户编号","客户名称","客户电话","产品编号","产品名称",
	        		"数量","结算单价","结算总价","品牌","商品父分类","商品子分类","配送方式","中转仓","配送人员",
	        		"配送人员电话","收货人姓名","收货人电话","收货人地址","订单备注"};
			cellDates(cellValues, style, row);
			
			for(int j=0;j<maxRowNum;j++)
	        {
				if(j+i*maxRowNum>=maxSize){
					break;
				}
				TdSalesDetail salesDetail= salesDetailList.get(j+i*maxRowNum);
	        	row = sheet.createRow((int) j + 1);
	        	//门店名称
	        	row.createCell(0).setCellValue(objToString(salesDetail.getDiySiteName()));

	        	//代付款订单没有主单号  分单号显示到主单号位置
	        	if(salesDetail.getStatusId() != null && salesDetail.getStatusId().equals(2L)){
	        		row.createCell(1).setCellValue(objToString(salesDetail.getOrderNumber()));
	        	}else{
	        		row.createCell(1).setCellValue(objToString(salesDetail.getMainOrderNumber()));
	        		row.createCell(2).setCellValue(objToString(salesDetail.getOrderNumber()));
	        	} 
	        	//下单时间
	        	row.createCell(3).setCellValue(objToString(salesDetail.getOrderTime()));
	        	//订单状态
	        	row.createCell(4).setCellValue(orderStatus(salesDetail.getStatusId()));
	        	//导购
				row.createCell(5).setCellValue(objToString(salesDetail.getSellerRealName()));
				//客户编号
				row.createCell(6).setCellValue(objToString(salesDetail.getUserId()));
				//客户名称
				row.createCell(7).setCellValue(objToString(salesDetail.getRealName()));
				//客户电话
				row.createCell(8).setCellValue(objToString(replacePhoneNumberWithStar(salesDetail.getUsername())));
				//产品编号
				row.createCell(9).setCellValue(objToString(salesDetail.getSku()));
				//产品名称
				row.createCell(10).setCellValue(objToString(salesDetail.getGoodsTitle()));
				//产品数量
				row.createCell(11).setCellValue(objToString(salesDetail.getQuantity()));
				//产品价格
				row.createCell(12).setCellValue(objToString(salesDetail.getPrice()));
				//产品总价
				row.createCell(13).setCellValue((salesDetail.getTotalPrice()*100)/100);
	        	//现金卷
	            //row.createCell(14).setCellValue(objToString(salesDetail.getCashCoupon()));
	          	//品牌
				row.createCell(14).setCellValue(objToString(salesDetail.getBrandTitle()));
	    		//商品父分类
				row.createCell(15).setCellValue(objToString(salesDetail.getGoodsParentTypeTitle()));
				//商品子分类
	        	row.createCell(16).setCellValue(objToString(salesDetail.getGoodsTypeTitle()));
				//配送方式
	        	row.createCell(17).setCellValue(objToString(salesDetail.getDeliverTypeTitle()));
				//中转仓
	            row.createCell(18).setCellValue(objToString(salesDetail.getWhName()));
	    		//配送人员
	        	row.createCell(19).setCellValue(objToString(salesDetail.getDeliverRealName()));
	        	//配送人员电话
	        	row.createCell(20).setCellValue(objToString(salesDetail.getDeliverUsername()));
	        	//收货人姓名
	        	if(!"门店自提".equals(salesDetail.getDeliverTypeTitle())){
	        		row.createCell(21).setCellValue(objToString(salesDetail.getShippingName()));
	        	}
	        	//收货人电话
	        	if(!"门店自提".equals(salesDetail.getDeliverTypeTitle())){
	        		row.createCell(22).setCellValue(objToString(salesDetail.getShippingPhone()));
	        	}
	        	//收货人地址
	        	if(!"门店自提".equals(salesDetail.getDeliverTypeTitle())){
	        		row.createCell(23).setCellValue(objToString(salesDetail.getShippingAddress()));
	        	}
	        	//订单备注
	        	row.createCell(24).setCellValue(objToString(salesDetail.getRemark()));
				
//	            System.out.println("正在生成excel文件的 sheet"+(i+1)+"第"+(j+1)+"行");
			}
//			System.out.println("正在生成excel文件的 sheet"+(i+1));
		}
        
        
//        long endTime = System.currentTimeMillis();
//        System.out.println("用时="+((endTime-startTimne)/1000)+"秒");
        return wb;
        
	}
	/**
	 * 退货报表
	 * @param begin 开始时间
	 * @param end 结束时间
	 * @param diyCode 门店编号
	 * @param cityName 城市编号
 	 * @param username 当前用户
	 * @return
	 */
	private HSSFWorkbook returnWorkBook(Date begin,Date end,String diyCode,String cityName,String username,List<String> roleDiyIds){
		// 第一步，创建一个webbook，对应一个Excel文件 
        HSSFWorkbook wb = new HSSFWorkbook();  
        
 
        // 第五步，设置值  
		List<TdReturnReport> returnReportList = tdReturnReportService.searchReturnReport(begin, end, cityName, diyCode, username,roleDiyIds);
		List<TdWareHouse> wareHouseList = tdWareHouseService.findAll();
//        long startTimne = System.currentTimeMillis();
        
        //excel单表最大行数是65535
        int maxRowNum = 60000;
        int maxSize=0;
        if(returnReportList!=null){
        	maxSize=returnReportList.size();
        }
        int sheets = maxSize/maxRowNum+1;
        
		//写入excel文件数据信息
		for(int i=0;i<sheets;i++){
			
			// 第二步，在webbook中添加一个sheet,对应Excel文件中的sheet  
	        HSSFSheet sheet = wb.createSheet("第"+(i+1)+"页");  
	        // 第三步，在sheet中添加表头第0行,注意老版本poi对Excel的行数列数有限制short  
	        //列宽
	        int[] widths={13,25,20,13,13,13,13,13,13,13};
	        sheetColumnWidth(sheet,widths);
	        
	        // 第四步，创建单元格，并设置值表头 设置表头居中  
	        HSSFCellStyle style = wb.createCellStyle();  
	        style.setAlignment(HSSFCellStyle.ALIGN_CENTER); // 创建一个居中格式
	        style.setWrapText(true);


	       	//设置标题
	        HSSFRow row = sheet.createRow((int) 0); 
	        
	        String[] cellValues={"退货门店","原订单号","退货单号","退货单状态","品牌","商品类别","导购","订单日期","退货日期","客户名称",
					"客户电话","产品编号","产品名称","退货数量","退货单价","退货金额","客户备注","中转仓","配送人员","配送人电话",
			"退货地址"};
			cellDates(cellValues, style, row);
			
			for(int j=0;j<maxRowNum;j++)
	        {
				if(j+i*maxRowNum>=maxSize){
					break;
				}
				TdReturnReport returnReport= returnReportList.get(j+i*maxRowNum);
	        	row = sheet.createRow((int) j + 1);
	        	if (returnReport.getDiySiteName() != null)
				{//退货门店
					row.createCell(0).setCellValue(returnReport.getDiySiteName());
				}
				if (returnReport.getOrderNumber() != null)
				{//原订单号
					row.createCell(1).setCellValue(returnReport.getOrderNumber());
					row.createCell(9).setCellValue(returnReport.getRealName());//客户名称 
					row.createCell(10).setCellValue(replacePhoneNumberWithStar(returnReport.getUsername()));// 客户电话
					row.createCell(6).setCellValue(returnReport.getSellerRealName());//导购
					//					        	row.createCell(16).setCellValue(returnReport.getCashCoupon());//退现金卷金额
					//					            row.createCell(17).setCellValue(returnReport.getProductCoupon());//退产品卷金额
					row.createCell(20).setCellValue(returnReport.getShippingAddress());//退货地址
					row.createCell(18).setCellValue(returnReport.getDeliverRealName());//配送人员
					row.createCell(19).setCellValue(returnReport.getDeliverUsername());//配送人员电话

				}
				if (returnReport.getReturnNumber() != null)
				{//退货单号
					row.createCell(2).setCellValue(returnReport.getReturnNumber());
				}
				if (returnReport.getStatusId() != null)
				{//退货单状态
					if(returnReport.getStatusId().equals(1L)){
						row.createCell(3).setCellValue("确认退货单");
					}
					if(returnReport.getStatusId().equals(2L)){
						row.createCell(3).setCellValue("通知物流");
					}
					if(returnReport.getStatusId().equals(3L)){
						row.createCell(3).setCellValue("验货确认");
					}
					if(returnReport.getStatusId().equals(4L)){
						row.createCell(3).setCellValue("确认退款");
					}
					if(returnReport.getStatusId().equals(5L)){
						row.createCell(3).setCellValue("已完成");
					}
				}
				if (returnReport.getBrandTitle() != null)
				{//品牌
					row.createCell(4).setCellValue(returnReport.getBrandTitle());
				}
				if (returnReport.getCategoryTitle() != null)
				{//商品类别
					row.createCell(5).setCellValue(returnReport.getCategoryTitle());
				}
				if (returnReport.getOrderTime() != null)
				{//订单日期
					row.createCell(7).setCellValue(returnReport.getOrderTime().toString());
				}
				if (returnReport.getCancelTime() != null)
				{//退货日期
					row.createCell(8).setCellValue(returnReport.getCancelTime().toString());
				}
				if (returnReport.getSku() != null)
				{//产品编号
					row.createCell(11).setCellValue(returnReport.getSku());
				}
				if (returnReport.getGoodsTitle() != null)
				{//产品名称
					row.createCell(12).setCellValue(returnReport.getGoodsTitle());
				}
				if (returnReport.getQuantity() != null)
				{//退货数量
					row.createCell(13).setCellValue(returnReport.getQuantity());
				}
				if (returnReport.getPrice() != null)
				{//退货单价
					row.createCell(14).setCellValue(returnReport.getPrice());
				}

				if (returnReport.getQuantity() != null && returnReport.getPrice() != null)
				{//退货总价
					row.createCell(15).setCellValue(returnReport.getQuantity()*returnReport.getPrice());
				}

				if(returnReport.getRemarkInfo() != null){//客户备注
					row.createCell(16).setCellValue(returnReport.getRemarkInfo());
				}
				if(returnReport.getWhNo() != null){//中转仓
					row.createCell(17).setCellValue(changeName(wareHouseList,returnReport.getWhNo()));
				}
//	            System.out.println("正在生成excel文件的 sheet"+(i+1)+"第"+(j+1)+"行");
			}
//			System.out.println("正在生成excel文件的 sheet"+(i+1));
			
		}
        
        
//        long endTime = System.currentTimeMillis();
//        System.out.println("用时="+((endTime-startTimne)/1000)+"秒");
        return wb;
	}
	
	/**
	 * 自提单出退货报表
	 * @param begin 开始时间
	 * @param end 结束时间
	 * @param diyCode 门店编号
	 * @param cityName 城市编号
	 * @param username 当前用户
	 * @param roleDiyIds
	 * @return
	 */
	private HSSFWorkbook goodsInOutTakeWorkBook(Date begin,Date end,String diyCode,String cityName,String username,List<String> roleDiyIds){
		// 第一步，创建一个webbook，对应一个Excel文件 
        HSSFWorkbook wb = new HSSFWorkbook();  
        
 
        // 第五步，设置值  
        List<TdGoodsInOut> goodsInOutList=tdGoodsInOutService.queryDownListTake(begin, end, cityName, diyCode, roleDiyIds);
        
//        long startTimne = System.currentTimeMillis();
        
        //excel单表最大行数是65535
        int maxRowNum = 60000;
        int maxSize=0;
        if(goodsInOutList!=null){
        	maxSize=goodsInOutList.size();
        }
        int sheets = maxSize/maxRowNum+1;
        
		//写入excel文件数据信息
		for(int i=0;i<sheets;i++){
			
			// 第二步，在webbook中添加一个sheet,对应Excel文件中的sheet  
	        HSSFSheet sheet = wb.createSheet("第"+(i+1)+"页");  
	        // 第三步，在sheet中添加表头第0行,注意老版本poi对Excel的行数列数有限制short  
	        //列宽
	        int[] widths={13,25,25,18,18,13,18,18,13,13,
	        		18,9,9,9,9,9,13,13,13,18,
	        		13,13,13,13,20,20};
	        sheetColumnWidth(sheet,widths);
	        
	        // 第四步，创建单元格，并设置值表头 设置表头居中  
	        HSSFCellStyle style = wb.createCellStyle();  
	        style.setAlignment(HSSFCellStyle.ALIGN_CENTER); // 创建一个居中格式
	        style.setWrapText(true);


	       	//设置标题
	        HSSFRow row = sheet.createRow((int) 0); 
	        
	        String[] cellValues={"门店名称","主单号","分单号","下单日期","确认日期","订单状态","导购","客户编号","客户名称","客户电话","产品编号",
	        		"产品名称","数量","结算单价","结算总价","品牌","商品父分类","商品子分类","订单备注"};
			cellDates(cellValues, style, row);
			
			for(int j=0;j<maxRowNum;j++)
	        {
				if(j+i*maxRowNum>=maxSize){
					break;
				}
				TdGoodsInOut goodsInOut= goodsInOutList.get(j+i*maxRowNum);
	        	row = sheet.createRow((int) j + 1);
	        	//门店名称
	        	row.createCell(0).setCellValue(objToString(goodsInOut.getDiySiteName()));

	        	//代付款订单没有主单号  分单号显示到主单号位置
	        	if(goodsInOut.getStatusId() != null && goodsInOut.getStatusId().equals(2L)){
	        		row.createCell(1).setCellValue(objToString(goodsInOut.getOrderNumber()));
	        	}else{
	        		row.createCell(1).setCellValue(objToString(goodsInOut.getMainOrderNumber()));
	        		row.createCell(2).setCellValue(objToString(goodsInOut.getOrderNumber()));
	        	} 
	        	//下单时间
	        	row.createCell(3).setCellValue(objToString(goodsInOut.getOrderTime()));
	        	//确认时间
	        	row.createCell(4).setCellValue(objToString(goodsInOut.getSalesTime()));
	        	//订单状态
	        	row.createCell(5).setCellValue(orderStatus(goodsInOut.getStatusId()));
	        	//导购
				row.createCell(6).setCellValue(objToString(goodsInOut.getSellerRealName()));
				//客户编号
				row.createCell(7).setCellValue(objToString(goodsInOut.getUserId()));
				//客户名称
				row.createCell(8).setCellValue(objToString(goodsInOut.getRealName()));
				//客户电话
				row.createCell(9).setCellValue(objToString(replacePhoneNumberWithStar(goodsInOut.getUsername())));
				//产品编号
				row.createCell(10).setCellValue(objToString(goodsInOut.getSku()));
				//产品名称
				row.createCell(11).setCellValue(objToString(goodsInOut.getGoodsTitle()));
				//产品数量
				row.createCell(12).setCellValue(objToString(goodsInOut.getQuantity()));
				//产品价格
				row.createCell(13).setCellValue(objToString(goodsInOut.getPrice()));
				//产品总价
				row.createCell(14).setCellValue(objToString((goodsInOut.getPrice() * goodsInOut.getQuantity() *100)/100));
	        	//现金卷
	            //row.createCell(15).setCellValue(objToString(goodsInOut.getCashCoupon()));
	          	//品牌
				row.createCell(15).setCellValue(objToString(goodsInOut.getBrandTitle()));
	    		//商品父分类
				row.createCell(16).setCellValue(objToString(goodsInOut.getGoodsParentTypeTitle()));
				//商品子分类
	        	row.createCell(17).setCellValue(objToString(goodsInOut.getGoodsTypeTitle()));
				//配送方式
	        	/*row.createCell(18).setCellValue(objToString(goodsInOut.getDeliverTypeTitle()));
				//中转仓
	            row.createCell(19).setCellValue(objToString(goodsInOut.getWhName()));
	    		//配送人员
	        	row.createCell(20).setCellValue(objToString(goodsInOut.getDeliverRealName()));
	        	//配送人员电话
	        	row.createCell(21).setCellValue(objToString(goodsInOut.getDeliverUsername()));
	        	//收货人姓名 收货人电话 收货人地址
	        	if(!"门店自提".equals(goodsInOut.getDeliverTypeTitle())){
	        		row.createCell(22).setCellValue(objToString(goodsInOut.getShippingName()));
	        		row.createCell(23).setCellValue(objToString(goodsInOut.getShippingPhone()));
	        		row.createCell(24).setCellValue(objToString(goodsInOut.getShippingAddress()));
	        	}*/
	        	//订单备注
	        	row.createCell(18).setCellValue(objToString(goodsInOut.getRemark()));
//	            System.out.println("正在生成excel文件的 sheet"+(i+1)+"第"+(j+1)+"行");
			}
//			System.out.println("正在生成excel文件的 sheet"+(i+1));
		}
        
        
//        long endTime = System.currentTimeMillis();
//        System.out.println("用时="+((endTime-startTimne)/1000)+"秒");
        return wb;
        
	}
	
	
	/**
	 * 销量报表
	 * @param begin 开始时间
	 * @param end 结束时间
	 * @param diyCode 门店编号
	 * @param cityName 城市编号
	 * @param username 当前用户
	 * @param roleDiyIds
	 * @return
	 */
	private HSSFWorkbook SalesWorkBook(Date begin,Date end,String diyCode,String cityName,String username,List<String> roleDiyIds){
		// 第一步，创建一个webbook，对应一个Excel文件 
        HSSFWorkbook wb = new HSSFWorkbook();  
        
 
        // 第五步，设置值  
        List<TdSales> tdSalesList = tdSalesService.queryDownList(begin, end, cityName, diyCode, roleDiyIds);
        System.out.println("总共有"+tdSalesList.size()+"条记录");
        //excel单表最大行数是65535
        int maxRowNum = 60000;
        int maxSize=0;	
        if(tdSalesList!=null){
        	maxSize=tdSalesList.size();
        }
        int sheets = maxSize/maxRowNum+1;
        
		//写入excel文件数据信息
		for(int i=0;i<sheets;i++){
			
			// 第二步，在webbook中添加一个sheet,对应Excel文件中的sheet  
	        HSSFSheet sheet = wb.createSheet("第"+(i+1)+"页");  
	        // 第三步，在sheet中添加表头第0行,注意老版本poi对Excel的行数列数有限制short  
	        //列宽
	        int[] widths={13,25,25,18,18,13,18,18,18,13,13,
	        		18,9,9,9,9,9,13,13,13,18,
	        		13,13,13,13,20,20};
	        sheetColumnWidth(sheet,widths);
	        
	        // 第四步，创建单元格，并设置值表头 设置表头居中  
	        HSSFCellStyle style = wb.createCellStyle();  
	        style.setAlignment(HSSFCellStyle.ALIGN_CENTER); // 创建一个居中格式
	        style.setWrapText(true);
	        DecimalFormat df = new DecimalFormat("0.00");

	       	//设置标题
	        HSSFRow row = sheet.createRow((int) 0); 
	        
	        String[] cellValues={"城市","品牌","门店","项目","主单号","分单号","订单日期","客户编号","客户电话","客户姓名","客户类型","销顾电话",
	        		"销顾姓名","商品编码","商品名称","赠品标识","商品单价","商品数量","商品总价","产品现金券单价","产品现金券数量","产品现金券总额","产品券单价","产品券数量","产品券总额","汇总"};
			cellDates(cellValues, style, row);
			
			for(int j=0;j<maxRowNum;j++)
	        {
				if(j+i*maxRowNum>=maxSize){
					break;
				}
				TdSales sales= tdSalesList.get(j+i*maxRowNum);
	        	row = sheet.createRow((int) j + 1);
	        	
	        	//城市名称
	        	row.createCell(0).setCellValue(objToString(sales.getCityName()));
	        	
	        	
	        	//品牌
	        	
	        	if(sales.getOrderNumber().contains("HR")){
	        		row.createCell(1).setCellValue("华润");
	        	}else if(sales.getOrderNumber().contains("YR")){
	        		row.createCell(1).setCellValue("莹润");
	        	}else if(sales.getOrderNumber().contains("LYZ")){
	        		row.createCell(1).setCellValue("乐易装");
	        	}
	        	
	        	//门店名称
	        	row.createCell(2).setCellValue(objToString(sales.getDiySiteName()));

	        	//销量项目
	        	row.createCell(3).setCellValue(objToString(sales.getStype()));

	        	//主单号
	        	row.createCell(4).setCellValue(objToString(sales.getMainOrderNumber()));
	        	
	        	//分单号
	        	row.createCell(5).setCellValue(objToString(sales.getOrderNumber()));
	        	
	        	//订单日期
	        	row.createCell(6).setCellValue(objToString(sales.getOrderTime()));
	        	//会员编号
	        	row.createCell(7).setCellValue(objToString(sales.getUserId()));
	        	//会员电话
				row.createCell(8).setCellValue(objToString(replacePhoneNumberWithStar(sales.getUsername())));
				
				//会员名称
				row.createCell(9).setCellValue(objToString(sales.getRealName()));
				
				if(null!=sales.getIdentityType()){
					if(sales.getIdentityType()==true){
						row.createCell(10).setCellValue("零售");
					}else{
						row.createCell(10).setCellValue("会员");
					}
				}else{
					row.createCell(10).setCellValue("未知");
				}
				
				//销顾电话
				row.createCell(11).setCellValue(objToString(sales.getSellerUsername()));
				
				//销顾姓名
				row.createCell(12).setCellValue(objToString(sales.getSellerRealName()));
				
				//产品编码
				row.createCell(13).setCellValue(objToString(sales.getSku()));
				
				//产品名称
				row.createCell(14).setCellValue(objToString(sales.getGoodsTitle()));
				
				//赠品标识
				if(null != sales.getIsGift()){
					if(sales.getIsGift().equalsIgnoreCase("N")){
						row.createCell(15).setCellValue("否");
					}else {
						row.createCell(15).setCellValue("是");
					}
				}else{
					row.createCell(15).setCellValue("否");
				}
				
				//商品单价
				if(null==sales.getGoodsPrice()){
					sales.setGoodsPrice(0.0);
					row.createCell(16).setCellValue(objToString(0));
				}else{
					row.createCell(16).setCellValue(objToString(sales.getGoodsPrice()));
				}
	        	//商品数量
				if(null == sales.getGoodsQuantity()){
					sales.setGoodsQuantity(0L);
					row.createCell(17).setCellValue(objToString(0));
				}else{
					 row.createCell(17).setCellValue(objToString(sales.getGoodsQuantity()));
				}
	          	//商品总价
	            
				row.createCell(18).setCellValue(objToString(df.format(sales.getGoodsPrice()*sales.getGoodsQuantity())));
				
				//产品现金券单价
				if(null==sales.getCashCouponPrice()){
					sales.setCashCouponPrice(0.0);
					row.createCell(19).setCellValue(objToString(sales.getCashCouponPrice()));
				}else{
					row.createCell(19).setCellValue(objToString(sales.getCashCouponPrice()));
				}
	    		
				//产品现金券数量
				if(null == sales.getCashCouponQuantity()){
					sales.setCashCouponQuantity(0L);
					row.createCell(20).setCellValue(objToString(sales.getCashCouponQuantity()));
				}else{
					row.createCell(20).setCellValue(objToString(sales.getCashCouponQuantity()));
				}
				
				//产品现金券总额
	        	row.createCell(21).setCellValue(objToString(df.format(sales.getCashCouponPrice()*sales.getCashCouponQuantity())));
	        	
	        	//产品券单价
	        	if(null == sales.getProductCouponPrice()){
	        		sales.setCashCouponPrice(0.0);
	        		 row.createCell(22).setCellValue(objToString(sales.getProductCouponPrice()));
	        	}else{
	        		 row.createCell(22).setCellValue(objToString(sales.getProductCouponPrice()));
	        	}
				
	           
	    		
	            //产品券数量
	        	if(null == sales.getProductCouponQuantity()){
	        		sales.setCashCouponQuantity(0L);
	        		
	        	}else{
	        		row.createCell(23).setCellValue(objToString(sales.getProductCouponQuantity()));
	        	}
	        	
	        	//产品券总额
	        	row.createCell(24).setCellValue(objToString(df.format(sales.getProductCouponPrice()*sales.getProductCouponQuantity())));
	        	//汇总
	        	row.createCell(25).setCellValue(objToString(df.format(sales.getGoodsPrice()*(sales.getGoodsQuantity()-sales.getProductCouponQuantity())-sales.getCashCouponPrice()*sales.getCashCouponQuantity())));
//	            System.out.println("正在生成excel文件的 sheet"+(i+1)+"第"+(j+1)+"行");
			}
//			System.out.println("正在生成excel文件的 sheet"+(i+1));
		}
        System.out.println(new Date());
        
//        long endTime = System.currentTimeMillis();
//        System.out.println("用时="+((endTime-startTimne)/1000)+"秒");
        return wb;
        
	}
	
	/**
	 * 欠款报表
	 * 
	 * @param begin 开始时间
	 * @param end 结束时间
	 * @param diyCode 门店编号
	 * @param cityName 城市编号
	 * @param username 当前用户
	 * @param roleDiyIds
	 * @return
	 */
	private HSSFWorkbook OwnWorkBook(Date begin,Date end,String diyCode,String cityName,String username,List<String> roleDiyIds){
		// 第一步，创建一个webbook，对应一个Excel文件 
        HSSFWorkbook wb = new HSSFWorkbook();  
        
 
        // 第五步，设置值  
        List<TdOwn> tdOwnList = tdOwnService.queryDownList(begin, end, cityName, diyCode, roleDiyIds);
        for (int i = 0; i < tdOwnList.size(); i++) {
			if(!(tdOwnList.get(i).getOwned()>0.0)){
				tdOwnList.remove(i);
			}
		}
       // System.out.println(tdOwnList);
//        long startTimne = System.currentTimeMillis();
        
        //excel单表最大行数是65535
        int maxRowNum = 60000;
        int maxSize=0;
        if(tdOwnList!=null){
        	maxSize=tdOwnList.size();
        }
        int sheets = maxSize/maxRowNum+1;
        
		//写入excel文件数据信息
		for(int i=0;i<sheets;i++){
			
			// 第二步，在webbook中添加一个sheet,对应Excel文件中的sheet  
	        HSSFSheet sheet = wb.createSheet("第"+(i+1)+"页");  
	        // 第三步，在sheet中添加表头第0行,注意老版本poi对Excel的行数列数有限制short  
	        //列宽
	        int[] widths={13,25,25,18,18,13,18,18,13,13,
	        		18,9,9,9,9,9,13,13,13,18,
	        		13,13,13,13,20,20};
	        sheetColumnWidth(sheet,widths);
	        
	        // 第四步，创建单元格，并设置值表头 设置表头居中  
	        HSSFCellStyle style = wb.createCellStyle();  
	        style.setAlignment(HSSFCellStyle.ALIGN_CENTER); // 创建一个居中格式
	        style.setWrapText(true);

	       	//设置标题
	        HSSFRow row = sheet.createRow((int) 0); 
	        
	        String[] cellValues={"门店","主单号","订单日期","销顾姓名","销顾电话","客户编号",
	        		"客户电话","客户姓名","华润欠款","其它欠款","出货仓库","配送员姓名","配送员电话","收货地址","订单备注"};
			cellDates(cellValues, style, row);
			
			for(int j=0;j<maxRowNum;j++)
	        {
				if(j+i*maxRowNum>=maxSize){
					break;
				}
				TdOwn own= tdOwnList.get(j+i*maxRowNum);
					row = sheet.createRow((int) j + 1);
		        	
		        	//门店名称
		        	row.createCell(0).setCellValue(objToString(own.getDiySiteName()));
		        	
		        	//主单号
		        	row.createCell(1).setCellValue(objToString(own.getMainOrderNumber()));

		        	//订单日期
		        	row.createCell(2).setCellValue(objToString(own.getOrderTime()));

		        	//销顾姓名
		        	row.createCell(3).setCellValue(objToString(own.getSellerRealName()));
		        	
		        	//销顾电话
		        	row.createCell(4).setCellValue(objToString(own.getSellerUsername()));
		        	//客户编号
		        	row.createCell(5).setCellValue(objToString(own.getUserId()));
		        	//客户电话
		        	row.createCell(6).setCellValue(objToString(replacePhoneNumberWithStar(own.getUsername())));
		        	
		        	//客户姓名
					row.createCell(7).setCellValue(objToString(own.getRealUserRealName()));
					
					
					
					List<TdSubOwn> tdSubOwnList = tdSubOwnService.queryDownListDetail(own.getMainOrderNumber());
					
					Double HRTotalPrice = 0.0;//华润订单总金额
					Double OtherTotalPrice = 0.0;//其它品牌订单总金额
					Double HRotherPay = 0.0;//华润第三方支付
					Double elseOtherPay = 0.0;//其它品牌第三方支付
					Double HROwn = 0.0;//华润欠款
					Double otherOwn = 0.0;//其它品牌欠款
					Double deliveryReceipt = 0.0;//总单配送代收
					Double diyReceipt = 0.0; //门店收款总额
					for (TdSubOwn tdSubOwn : tdSubOwnList) {
						if(tdSubOwn.getOrderNumber().contains("HR")){
							HRTotalPrice+=tdSubOwn.getTotalPrice();
							HRotherPay+=tdSubOwn.getOtherPay();
						}else if(tdSubOwn.getOrderNumber().contains("LYZ") || tdSubOwn.getOrderNumber().contains("YR")||tdSubOwn.getOrderNumber().contains("YF")){
							OtherTotalPrice+=tdSubOwn.getTotalPrice();
							elseOtherPay+=tdSubOwn.getOtherPay();
						}
						deliveryReceipt = tdSubOwn.getMoney()+tdSubOwn.getPos();
						diyReceipt = tdSubOwn.getBackMoney()+tdSubOwn.getBackPos()+tdSubOwn.getBackOther();
					}
					
					if((deliveryReceipt+diyReceipt)<=(OtherTotalPrice-elseOtherPay)){
						HROwn = HRTotalPrice-HRotherPay;
						otherOwn = OtherTotalPrice-elseOtherPay-deliveryReceipt-diyReceipt; 
					}else{
						HROwn = HRTotalPrice+OtherTotalPrice-HRotherPay-elseOtherPay-deliveryReceipt-diyReceipt;
						otherOwn = 0.0;
					}
					
					//华润欠款
					
					row.createCell(8).setCellValue(objToString(HROwn));
					
					
					//其它品牌欠款
					row.createCell(9).setCellValue(objToString(otherOwn));
					
					//出货仓库
					row.createCell(10).setCellValue(objToString(own.getWhName()));
					
					//配送员姓名
					row.createCell(11).setCellValue(objToString(own.getDuRealName()));
					
					//配送员电话
					row.createCell(12).setCellValue(objToString(own.getDuUsername()));
					
					//收货地址
					if(own.getDeliveryTypeTitle().equals("送货上门")){
						row.createCell(13).setCellValue(objToString(own.getShippingAddress()));
					}
					//订单备注
					
					row.createCell(14).setCellValue(objToString(own.getRemark()));
				
//	            System.out.println("正在生成excel文件的 sheet"+(i+1)+"第"+(j+1)+"行");
			}
//			System.out.println("正在生成excel文件的 sheet"+(i+1));
		}
        System.out.println(new Date());
        
//        long endTime = System.currentTimeMillis();
//        System.out.println("用时="+((endTime-startTimne)/1000)+"秒");
        return wb;
        
	}


    /**
     * 未提货报表
     * @param diyCode
     * @param cityName
     * @param username
     * @param roleDiyIds
     * @return
     */
	private HSSFWorkbook ReserveOrderBook( String diyCode, String cityName, String username,
			List<String> roleDiyIds) {
		// 第一步，创建一个webbook，对应一个Excel文件 
        HSSFWorkbook wb = new HSSFWorkbook();  
        
 
        // 第五步，设置值  
        List<TdReserveOrder> tdReserveOrderList = tdReserveOrderService.queryDownList(cityName, diyCode, roleDiyIds);
//        long startTimne = System.currentTimeMillis();
        
        //excel单表最大行数是65535
        int maxRowNum = 60000;
        int maxSize=0;
        if(tdReserveOrderList!=null){
        	maxSize=tdReserveOrderList.size();
        }
        int sheets = maxSize/maxRowNum+1;
        
		//写入excel文件数据信息
		for(int i=0;i<sheets;i++){
			
			// 第二步，在webbook中添加一个sheet,对应Excel文件中的sheet  
	        HSSFSheet sheet = wb.createSheet("第"+(i+1)+"页");  
	        // 第三步，在sheet中添加表头第0行,注意老版本poi对Excel的行数列数有限制short  
	        //列宽
	        int[] widths={13,25,25,18,18,13,18,18,13,13,
	        		18,9,9,9,9,9,13,13,13,18,
	        		13,13,13,13,20,20};
	        sheetColumnWidth(sheet,widths);
	        
	        // 第四步，创建单元格，并设置值表头 设置表头居中  
	        HSSFCellStyle style = wb.createCellStyle();  
	        style.setAlignment(HSSFCellStyle.ALIGN_CENTER); // 创建一个居中格式
	        style.setWrapText(true);

	       	//设置标题
	        HSSFRow row = sheet.createRow((int) 0); 
	        
	        String[] cellValues={"城市","未提货类型","门店","券订单号","确认日期","会员编号","会员姓名","会员电话","销顾姓名",
	        		"商品编码","数量","单价","总额"};
			cellDates(cellValues, style, row);
			
			for(int j=0;j<maxRowNum;j++)
	        {
				if(j+i*maxRowNum>=maxSize){
					break;
				}
				TdReserveOrder	 tdReserveOrder= tdReserveOrderList.get(j+i*maxRowNum);
					row = sheet.createRow((int) j + 1);
		        	
		        	//城市
		        	row.createCell(0).setCellValue(objToString(tdReserveOrder.getCity()));
		        	
		        	//未提货类型
		        	row.createCell(1).setCellValue(objToString(tdReserveOrder.getReserveType()));
		        	
		        	//门店
		        	
		        	row.createCell(2).setCellValue(objToString(tdReserveOrder.getDiySiteName()));
		        	
		        	//券订单号
		        	row.createCell(3).setCellValue(objToString(tdReserveOrder.getCouponOrderNumber()));

		        	//确认日期
		        	row.createCell(4).setCellValue(objToString(tdReserveOrder.getGetTime()));
		        	//会员编号
		        	row.createCell(5).setCellValue(objToString(tdReserveOrder.getUserId()));
		        	//会员姓名
		        	row.createCell(6).setCellValue(objToString(tdReserveOrder.getRealUserRealName()));
		        	
		        	//会员电话
		        	row.createCell(7).setCellValue(objToString(replacePhoneNumberWithStar(tdReserveOrder.getUsername())));
		        	
		        	//销顾姓名
					row.createCell(8).setCellValue(objToString(tdReserveOrder.getSellerRealName()));
					
					//商品编码
					row.createCell(9).setCellValue(objToString(tdReserveOrder.getSku()));
					
					//数量
					row.createCell(10).setCellValue(objToString(tdReserveOrder.getQuantity()));
					
					//单价
					row.createCell(11).setCellValue(objToString(tdReserveOrder.getBuyPrice()));
					
					//总额
					row.createCell(12).setCellValue(objToString(tdReserveOrder.getQuantity()*tdReserveOrder.getBuyPrice()));
					
					
				
//	            System.out.println("正在生成excel文件的 sheet"+(i+1)+"第"+(j+1)+"行");
			}
//			System.out.println("正在生成excel文件的 sheet"+(i+1));
		}
        System.out.println(new Date());
        
//        long endTime = System.currentTimeMillis();
//        System.out.println("用时="+((endTime-startTimne)/1000)+"秒");
        return wb;
	}
	
	
	/**
	 * 活跃会员报表
	 * @param diyCode
	 * @param cityName
	 * @param username
	 * @param roleDiyIds
	 * @return
	 */
	private HSSFWorkbook salesForActiveUserBook(Date begin,Date end,String diyCode,String cityName,String username,List<String> roleDiyIds){
		// 第一步，创建一个webbook，对应一个Excel文件 
        HSSFWorkbook wb = new HSSFWorkbook();  
        
 
        // 第五步，设置值  
        List<TdActiveUser> saleForActiveUserList = tdActiveUserService.queryDownList(begin, end, cityName, diyCode, roleDiyIds);
        //System.out.println(tdSalesList);
//        long startTimne = System.currentTimeMillis();
        
        //excel单表最大行数是65535
        int maxRowNum = 60000;
        int maxSize=0;
        if(saleForActiveUserList!=null){
        	maxSize=saleForActiveUserList.size();
        }
        int sheets = maxSize/maxRowNum+1;
        
		//写入excel文件数据信息
		for(int i=0;i<sheets;i++){
			
			// 第二步，在webbook中添加一个sheet,对应Excel文件中的sheet  
	        HSSFSheet sheet = wb.createSheet("第"+(i+1)+"页");  
	        // 第三步，在sheet中添加表头第0行,注意老版本poi对Excel的行数列数有限制short  
	        //列宽
	        int[] widths={13,25,25,18,18,13,18,18,13,13,
	        		18,9,9,9,9,9,13,13,13,18,
	        		13,13,13,13,20,20};
	        sheetColumnWidth(sheet,widths);
	        
	        // 第四步，创建单元格，并设置值表头 设置表头居中  
	        HSSFCellStyle style = wb.createCellStyle();  
	        style.setAlignment(HSSFCellStyle.ALIGN_CENTER); // 创建一个居中格式
	        style.setWrapText(true);

	       	//设置标题
	        HSSFRow row = sheet.createRow((int) 0); 
	        
	        String[] cellValues={"城市","门店","销顾电话",
	        		"销顾姓名","活跃会员数"};
			cellDates(cellValues, style, row);
			
			for(int j=0;j<maxRowNum;j++)
	        {
				if(j+i*maxRowNum>=maxSize){
					break;
				}
				TdActiveUser sales= saleForActiveUserList.get(j+i*maxRowNum);
	        	row = sheet.createRow((int) j + 1);
	        	
				// 城市名称
				row.createCell(0).setCellValue(objToString(sales.getCityName()));

				// 门店名称
				row.createCell(1).setCellValue(objToString(sales.getDiySiteName()));

				// 销顾电话
				row.createCell(2).setCellValue(objToString(sales.getSellerUsername()));

				// 销顾姓名
				row.createCell(3).setCellValue(objToString(sales.getSellerRealName()));

				// 活跃会员数
				row.createCell(4).setCellValue(objToString((sales.getSalesSummary().intValue())));
				
//	            System.out.println("正在生成excel文件的 sheet"+(i+1)+"第"+(j+1)+"行");
			}
//			System.out.println("正在生成excel文件的 sheet"+(i+1));
		}
        System.out.println(new Date());
        
//        long endTime = System.currentTimeMillis();
//        System.out.println("用时="+((endTime-startTimne)/1000)+"秒");
        return wb;
        
	}
	
	
	/**
	 * 配送考核报表
	 * @param begin
	 * @param end
	 * @param diyCode
	 * @param cityName
	 * @param username
	 * @param roleDiyIds
	 * @return
	 */
	private HSSFWorkbook deliveryCheckBook(Date begin, Date end, String diyCode, String cityName, String username,
			List<String> roleDiyIds) {
		
		//创建工作簿
		HSSFWorkbook wb = new HSSFWorkbook();
		
		List<DeliveryCheckReport> deliveryCheckList = tdDeliveryCheckReportService.queryDownList(begin, end, cityName, diyCode, roleDiyIds);
		
		 int maxRowNum = 60000;
	        int maxSize=0;
	        if(deliveryCheckList!=null){
	        	maxSize=deliveryCheckList.size();
	        }
	        int sheets = maxSize/maxRowNum+1;
		
	        
			//写入excel文件数据信息
			for(int i=0;i<sheets;i++){
				
				// 第二步，在webbook中添加一个sheet,对应Excel文件中的sheet  
		        HSSFSheet sheet = wb.createSheet("第"+(i+1)+"页");  
		        // 第三步，在sheet中添加表头第0行,注意老版本poi对Excel的行数列数有限制short  
		        //列宽
		        int[] widths={13,13,18,25,15,13,25,25,25,25,
		        		25,25 };
		        sheetColumnWidth(sheet,widths);
		        
		        // 第四步，创建单元格，并设置值表头 设置表头居中  
		        HSSFCellStyle style = wb.createCellStyle();  
		        style.setAlignment(HSSFCellStyle.ALIGN_CENTER); // 创建一个居中格式
		        style.setWrapText(true);

		       	//设置标题
		        HSSFRow row = sheet.createRow((int) 0); 
		        
		        String[] cellValues={"配送仓","配送员","配送员电话","主单号","订单归属门店","订单导购","收货地址","下单时间","出货时间",
		        		"代收款(系统计算)","备注信息","操作时间" };
				cellDates(cellValues, style, row);
				
				for(int j=0;j<maxRowNum;j++)
		        {
					if(j+i*maxRowNum>=maxSize){
						break;
					}
					DeliveryCheckReport deliveryCheckReport= deliveryCheckList.get(j+i*maxRowNum);
		        	row = sheet.createRow((int) j + 1);
		        	
		        	//仓库名称
		        	if (null != deliveryCheckReport.getWhName()) {
		        		row.createCell(0).setCellValue(objToString(deliveryCheckReport.getWhName()));
					}else{
						row.createCell(0).setCellValue(objToString("NULL"));
					}
					
		        	//配送员
		        	
		        	if (null != deliveryCheckReport.getDistributionName()) {
		        		row.createCell(1).setCellValue(objToString(deliveryCheckReport.getDistributionName()));
					}else{
						row.createCell(1).setCellValue(objToString("NULL"));
					}
		        	
		        	//配送员电话
		        	
		        	if (null != deliveryCheckReport.getDistributionPhone()) {
		        		row.createCell(2).setCellValue(objToString(deliveryCheckReport.getDistributionPhone()));
					}else{
						row.createCell(2).setCellValue(objToString("NULL"));
					}
		        	
		        	//主单号
		        	
		        	if (null != deliveryCheckReport.getMainOrderNumber()) {
		        		row.createCell(3).setCellValue(objToString(deliveryCheckReport.getMainOrderNumber()));
					}else{
						row.createCell(3).setCellValue(objToString("NULL"));
					}
		        	
		        	//门店名称
		        	
		        	if (null != deliveryCheckReport.getDiySiteName()) {
		        		row.createCell(4).setCellValue(objToString(deliveryCheckReport.getDiySiteName()));
					}else{
						row.createCell(4).setCellValue(objToString("NULL"));
					}
		        	
		        	//销顾
		        	
		        	if (null != deliveryCheckReport.getSellerRealName()) {
		        		row.createCell(5).setCellValue(objToString(deliveryCheckReport.getSellerRealName()));
					}else{
						row.createCell(5).setCellValue(objToString("NULL"));
					}
		        	
		        	//送货地址
		        	
		        	if (null != deliveryCheckReport.getShippingAddress()) {
		        		row.createCell(6).setCellValue(objToString(deliveryCheckReport.getShippingAddress()));
					}else{
						row.createCell(6).setCellValue(objToString("NULL"));
					}
		        	
		        	//下单时间
		        	
		        	if (null != deliveryCheckReport.getOrderTime()) {
		        		row.createCell(7).setCellValue(objToString(deliveryCheckReport.getOrderTime()));
					}else{
						row.createCell(7).setCellValue(objToString("NULL"));
					}
		        	
		        	//出货时间
		        	
		        	if (null != deliveryCheckReport.getSendTime()) {
		        		row.createCell(8).setCellValue(objToString(deliveryCheckReport.getSendTime()));
					}else{
						row.createCell(8).setCellValue(objToString("NULL"));
					}
		        	
		        	//代收款，系统计算
		        	if (null != deliveryCheckReport.getAgencyFund()) {
		        		row.createCell(9).setCellValue(objToString(deliveryCheckReport.getAgencyFund()));
					}else{
						row.createCell(9).setCellValue(objToString("NULL"));
					}
		        	
		        	//备注信息
		        	
		        	if (null != deliveryCheckReport.getRemark()) {
		        		row.createCell(10).setCellValue(objToString(deliveryCheckReport.getRemark()));
					}else{
						row.createCell(10).setCellValue(objToString("NULL"));
					}
		        	
		        	//操作时间
		        	if (null != deliveryCheckReport.getOperationTime()) {
		        		row.createCell(11).setCellValue(objToString(deliveryCheckReport.getOperationTime()));
					}else{
						row.createCell(11).setCellValue(objToString(""));
					}
		        	
//		            System.out.println("正在生成excel文件的 sheet"+(i+1)+"第"+(j+1)+"行");
				}
			}
	        
		return wb;
	}
	
	
	/**
	 * @param begin 开始时间
	 * @param end 结束时间
	 * @param diyCode 门店编码
	 * @param cityName	城市名称
	 * @param username	管理员用户名
	 * @param roleDiyIds  授权管理门店的id
	 * @return
	 */
	private HSSFWorkbook garmentFranchisor(Date begin, Date end, String diyCode, String cityName, String username,
			List<String> roleDiyIds) {
		
		//创建工作簿
		HSSFWorkbook wb = new HSSFWorkbook();
		//查询要写入excel的行记录
		List<GarmentFranchisorReport> garmengFranchisorList = tdGarmentFranchisorReportService.queryDownList(begin, end, cityName, diyCode, roleDiyIds);
		
		int maxRowNum = 60000;
		int maxSize = 0;
		if (garmengFranchisorList != null) {
			maxSize = garmengFranchisorList.size();
		}
		int sheets = maxSize / maxRowNum + 1;

		// 写入excel文件数据信息
		for (int i = 0; i < sheets; i++) {

			// 第二步，在webbook中添加一个sheet,对应Excel文件中的sheet
			HSSFSheet sheet = wb.createSheet("第" + (i + 1) + "页");
			// 第三步，在sheet中添加表头第0行,注意老版本poi对Excel的行数列数有限制short
			// 列宽
			int[] widths = { 18, 18, 18, 18, 20, 18,18, 20, 18, 30, 30, 25, 25, 18, 18, 18, 18, 18, 18 };
			sheetColumnWidth(sheet, widths);

			// 第四步，创建单元格，并设置值表头 设置表头居中
			HSSFCellStyle style = wb.createCellStyle();
			style.setAlignment(HSSFCellStyle.ALIGN_CENTER); // 创建一个居中格式
			style.setWrapText(true);

			// 设置标题
			HSSFRow row = sheet.createRow((int) 0);

			String[] cellValues = { "城市", "门店名称", "仓库名称", "配送员名称", "配送员电话", "会员编号","会员姓名", "会员电话", "导购姓名", "主单号", "分单号",
					"订单时间", "配送时间", "订单状态", "预存款", "第三方支付支付宝", "第三方支付银联", "现金支付", "现金券总额", "总金额" };
			cellDates(cellValues, style, row);

			for (int j = 0; j < maxRowNum; j++) {
				if (j + i * maxRowNum >= maxSize) {
					break;
				}
				GarmentFranchisorReport garmentFranchisorReport = garmengFranchisorList.get(j + i * maxRowNum);
				row = sheet.createRow((int) j + 1);

				// 城市名称

				if (null != garmentFranchisorReport.getCityName()) {
					row.createCell(0).setCellValue(objToString(garmentFranchisorReport.getCityName()));
				} else {
					row.createCell(0).setCellValue(" ");
				}

				// 门店名称

				if (null != garmentFranchisorReport.getDiySiteName()) {
					row.createCell(1).setCellValue(objToString(garmentFranchisorReport.getDiySiteName()));
				} else {
					row.createCell(1).setCellValue(" ");
				}

				// 仓库名称
				if (null != garmentFranchisorReport.getWhName()) {
					row.createCell(2).setCellValue(objToString(garmentFranchisorReport.getWhName()));
				} else {
					row.createCell(2).setCellValue(objToString(" "));
				}

				// 配送员姓名
				if (null != garmentFranchisorReport.getDeliveryName()) {
					row.createCell(3).setCellValue(objToString(garmentFranchisorReport.getDeliveryName()));
				} else {
					row.createCell(3).setCellValue(objToString(" "));
				}

				// 配送员电话
				if (null != garmentFranchisorReport.getDeliveryPhone()) {
					row.createCell(4).setCellValue(objToString(garmentFranchisorReport.getDeliveryPhone()));
				} else {
					row.createCell(4).setCellValue(objToString(" "));
				}
				
				//会员编号
				if (null != garmentFranchisorReport.getUserId()) {
					row.createCell(5).setCellValue(objToString(garmentFranchisorReport.getUserId()));
				} else {
					row.createCell(5).setCellValue(objToString(" "));
				}
				
				// 会员姓名
				if (null != garmentFranchisorReport.getRealUserRealName()) {
					row.createCell(6).setCellValue(objToString(garmentFranchisorReport.getRealUserRealName()));
				} else {
					row.createCell(6).setCellValue(objToString(" "));
				}

				// 会员电话
				if (null != garmentFranchisorReport.getRealUserUsername()) {
					row.createCell(7).setCellValue(
							objToString(replacePhoneNumberWithStar(garmentFranchisorReport.getRealUserUsername())));
				} else {
					row.createCell(7).setCellValue(objToString(" "));
				}

				// 导购姓名
				if (null != garmentFranchisorReport.getSellerRealName()) {
					row.createCell(8).setCellValue(objToString(garmentFranchisorReport.getSellerRealName()));
				} else {
					row.createCell(8).setCellValue(objToString(" "));
				}

				// 主单号
				if (null != garmentFranchisorReport.getMainOrderNumber()) {
					row.createCell(9).setCellValue(objToString(garmentFranchisorReport.getMainOrderNumber()));
				} else {
					row.createCell(9).setCellValue(objToString(" "));
				}

				// 分单号
				if (null != garmentFranchisorReport.getOrderNumber()) {
					row.createCell(10).setCellValue(objToString(garmentFranchisorReport.getOrderNumber()));
				} else {
					row.createCell(10).setCellValue(objToString(" "));
				}

				// 订单时间
				if (null != garmentFranchisorReport.getOrderTime()) {
					row.createCell(11).setCellValue(objToString(garmentFranchisorReport.getOrderTime()));
				} else {
					row.createCell(11).setCellValue(objToString(" "));
				}

				// 配送时间
				if (null != garmentFranchisorReport.getDeliveryTime()) {
					row.createCell(12).setCellValue(objToString(garmentFranchisorReport.getDeliveryTime()));
				} else {
					row.createCell(12).setCellValue(objToString(" "));
				}

				// 订单状态
				if (null != garmentFranchisorReport.getStatusId()) {
					switch (garmentFranchisorReport.getStatusId()) {
					case 4:
						row.createCell(13).setCellValue("待签收");
						break;
					case 5:
						if (garmentFranchisorReport.getOrderNumber().contains("T")) {
							row.createCell(13).setCellValue("退货已完成");
						} else {
							row.createCell(13).setCellValue("待评价");
						}
						break;
					case 6:
						row.createCell(13).setCellValue("已完成");
						break;
					case 7:
						row.createCell(13).setCellValue("已取消");
						break;
					case 8:
						row.createCell(13).setCellValue("已删除");
						break;
					case 9:
						row.createCell(13).setCellValue("退货中");
						break;
					case 10:
						row.createCell(13).setCellValue("退货确认");
						break;
					case 11:
						row.createCell(13).setCellValue("退货取消");
						break;
					case 12:
						row.createCell(13).setCellValue("退货完成");
						break;
					default:
						break;
					}
				} else {
					row.createCell(13).setCellValue(objToString(" "));
				}

				// 预存款
				if (null != garmentFranchisorReport.getActualPay()) {
					row.createCell(14).setCellValue(objToString(garmentFranchisorReport.getActualPay()));
				} else {
					garmentFranchisorReport.setActualPay(0.0);
					row.createCell(14).setCellValue(objToString("0"));
				}

				// 第三方支付支付宝及第三方支付银联
				if (null != garmentFranchisorReport.getPayTypeId() && garmentFranchisorReport.getPayTypeId() == 3) {
					row.createCell(15).setCellValue(objToString(garmentFranchisorReport.getOtherPay()));
					row.createCell(16).setCellValue("0");
				} else if (null != garmentFranchisorReport.getPayTypeId()
						&& garmentFranchisorReport.getPayTypeId() == 5) {
					row.createCell(15).setCellValue("0");
					row.createCell(16).setCellValue(objToString(garmentFranchisorReport.getOtherPay()));
				} else {
					row.createCell(15).setCellValue("0");
					row.createCell(16).setCellValue("0");
				}

				// 现金支付
				if (null != garmentFranchisorReport.getCashPay()) {
					row.createCell(17).setCellValue(objToString(garmentFranchisorReport.getCashPay()));
				} else {
					garmentFranchisorReport.setCashPay(0.0);
					row.createCell(17).setCellValue(objToString("0"));
				}

				// 现金券总额
				if (null != garmentFranchisorReport.getCashCoupon()) {
					row.createCell(18).setCellValue(objToString(garmentFranchisorReport.getCashCoupon()));
				} else {
					row.createCell(18).setCellValue(objToString("0"));
				}

				// 总额
				row.createCell(19).setCellValue(objToString(garmentFranchisorReport.getActualPay()
						+ garmentFranchisorReport.getOtherPay() + garmentFranchisorReport.getCashCoupon()));

			}
		}

		return wb;
	}
	
	
	/**
	 * @param begin 开始时间
	 * @param end 结束时间
	 * @param diyCode 门店编码
	 * @param cityName	城市名称
	 * @param username	管理员用户名
	 * @param roleDiyIds  授权管理门店的id
	 * @return
	 * @throws ParseException 
	 */
	private HSSFWorkbook continuousBuyBook(Date begin, Date end, String diyCode, String cityName, String username,
			List<String> roleDiyIds) throws ParseException {

		// 创建工作簿
		HSSFWorkbook wb = new HSSFWorkbook();

		String beginStr = null;
		String endStr = null;
		// 判断空值
		if (begin == null) {
			beginStr = "201609";
		} else {
			beginStr = dateToString(begin);
		}
		if (end == null) {
			endStr = dateToString(new Date());
		} else {
			endStr = dateToString(end);
		}
		// 查询要写入excel的行记录
		List<TdSalesForContinuousBuy> buyList = tdSalesForContinuousBuyService.queryDownList(beginStr, endStr, cityName,
				diyCode, roleDiyIds);

		int maxRowNum = 60000;
		int maxSize = 0;
		if (buyList != null) {
			maxSize = buyList.size();
		}
		int indicator = 0;
		int sheets = maxSize / maxRowNum + 1;

		// 写入excel文件数据信息
		for (int i = 0; i < sheets; i++) {

			// 第二步，在webbook中添加一个sheet,对应Excel文件中的sheet
			HSSFSheet sheet = wb.createSheet("第" + (i + 1) + "页");
			// 第三步，在sheet中添加表头第0行,注意老版本poi对Excel的行数列数有限制short
			// 列宽
			int[] widths = { 18, 18, 18, 18, 20, 18, 20, 18, 30, 30, 25, 25, 18, 18, 18, 18, 18, 18 };
			sheetColumnWidth(sheet, widths);

			// 第四步，创建单元格，并设置值表头 设置表头居中
			HSSFCellStyle style = wb.createCellStyle();
			style.setAlignment(HSSFCellStyle.ALIGN_CENTER); // 创建一个居中格式
			style.setWrapText(true);

			// 设置标题
			HSSFRow row = sheet.createRow((int) 0);

			// 月份数组
			List<String> listStr = getMonth(beginStr, endStr);

			// 拼接标题栏
			String[] cellVal01 = { "城市", "门店名称", "销顾编号", "销顾名称","会员编号", "会员名称", "会员编号", "会员电话" };
			String[] cellVal02 = (String[]) listStr.toArray(new String[listStr.size()]);
			String[] cellValues = concat(cellVal01, cellVal02);

			cellDates(cellValues, style, row);

			for (int j = 0; j < maxRowNum; j++) {
				if (j + i * maxRowNum >= maxSize) {
					break;
				}
				TdSalesForContinuousBuy continuousBuy = buyList.get(j + i * maxRowNum);
				row = sheet.createRow((int) j + 1);

				// 城市
				if (null != continuousBuy.getCityName()) {
					row.createCell(0).setCellValue(objToString(continuousBuy.getCityName()));
				} else {
					row.createCell(0).setCellValue(" ");
				}

				// 门店名称

				if (null != continuousBuy.getDiySiteName()) {
					row.createCell(1).setCellValue(objToString(continuousBuy.getDiySiteName()));
				} else {
					row.createCell(1).setCellValue(" ");
				}

				// 销顾编号

				if (null != continuousBuy.getSellerUsername()) {
					row.createCell(2).setCellValue(objToString(continuousBuy.getSellerUsername()));
				} else {
					row.createCell(2).setCellValue(" ");
				}

				// 销顾名称
				if (null != continuousBuy.getSellerRealName()) {
					row.createCell(3).setCellValue(objToString(continuousBuy.getSellerRealName()));
				} else {
					row.createCell(3).setCellValue(" ");
				}
				//会员编号
				if (null != continuousBuy.getUserId()) {
					row.createCell(4).setCellValue(objToString(continuousBuy.getUserId()));
				} else {
					row.createCell(4).setCellValue(" ");
				}
				// 会员名称
				if (null != continuousBuy.getRealName()) {
					row.createCell(5).setCellValue(objToString(continuousBuy.getRealName()));
				} else {
					row.createCell(5).setCellValue(" ");
				}
				// 会员电话
				if (null != continuousBuy.getUsername()) {
					row.createCell(6).setCellValue(objToString(replacePhoneNumberWithStar(continuousBuy.getUsername())));
				} else {
					row.createCell(6).setCellValue(" ");
				}

				// 会员电话
				if (null != continuousBuy.getUsername()) {
					row.createCell(7).setCellValue(objToString(replacePhoneNumberWithStar(continuousBuy.getUsername())));
				} else {
					row.createCell(7).setCellValue(" ");
				}

				String beginStrSplit = beginStr.substring(0, 4) + "-" + beginStr.substring(4, 6);
				String endStrSplit = endStr.substring(0, 4) + "-" + endStr.substring(4, 6);
				Date d1 = new SimpleDateFormat("yyyy-MM").parse(beginStrSplit);// 定义起始日期

				Date d2 = new SimpleDateFormat("yyyy-MM").parse(endStrSplit);// 定义结束日期

				Calendar dd = Calendar.getInstance();// 定义日期实例

				dd.setTime(d1);// 设置日期起始时间
				int tempFlag = 8;

				while (!dd.getTime().after(d2)) {// 判断是否到结束日期
					SimpleDateFormat sdf = new SimpleDateFormat("yyyyMM");
					String monthStr = sdf.format(dd.getTime());
					Integer tempSale = tdSalesForContinuousBuyService.retriveSale(continuousBuy.getDiySiteName(),
							continuousBuy.getUsername(), continuousBuy.getSellerUsername(), monthStr);
					if(null !=tempSale ){
						row.createCell(tempFlag).setCellValue(objToString(tempSale.intValue()));
					}else{
						row.createCell(tempFlag).setCellValue(objToString(0));
					}
					
					tempFlag++;
					dd.add(Calendar.MONTH, 1);// 进行当前日期月份加1
				}

			indicator++;
			System.out.println(indicator);
			}
		}

		return wb;
	}
	
	/*private HSSFWorkbook LyzHrDeliveryFeeBook(Date begin, Date end, String diyCode, String cityName, String username,
			List<String> roleDiyIds) {
		// 创建工作簿
		HSSFWorkbook wb = new HSSFWorkbook();

		List<TdOrderDeliveryFeeDetail> detailList = tdOrderDeliveryFeeDetailService.queryDownList(begin, end, cityName,
				diyCode, roleDiyIds);
		int maxRowNum = 60000;
		int maxSize = 0;
		if (detailList != null) {
			maxSize = detailList.size();
		}
		int sheets = maxSize / maxRowNum + 1;

		// 写入excel文件数据信息
		for (int i = 0; i < sheets; i++) {

			// 第二步，在webbook中添加一个sheet,对应Excel文件中的sheet
			HSSFSheet sheet = wb.createSheet("第" + (i + 1) + "页");
			// 第三步，在sheet中添加表头第0行,注意老版本poi对Excel的行数列数有限制short
			// 列宽
			int[] widths = { 20, 30, 20, 20, 15, 15, 20, 18, 18, 18, 25, 25, 18, 18, 18, 18, 18, 18, 18, 18, 18 };
			sheetColumnWidth(sheet, widths);

			// 第四步，创建单元格，并设置值表头 设置表头居中
			HSSFCellStyle style = wb.createCellStyle();
			style.setAlignment(HSSFCellStyle.ALIGN_CENTER); // 创建一个居中格式
			style.setWrapText(true);

			// 设置标题
			HSSFRow row = sheet.createRow((int) 0);

			String[] cellValues = { "门店名称", "主单号", "下单日期", "封车日期", "订单状态", "导购", "客户名称", "客户电话", "大桶漆配送费", "硝基漆10L配送费",
					"小桶漆/木器漆配送费", "4kg以下漆类配送费", "墙面辅料费", "客户应承担运费", "打折减免客户运费", "购辅料减免客户运费", "客户实际运费", "商户应承担运费",
					"打折减免商户运费", "购辅料减免商户运费", "商户实际运费" };
			cellDates(cellValues, style, row);

			for (int j = 0; j < maxRowNum; j++) {
				if (j + i * maxRowNum >= maxSize) {
					break;
				}
				TdOrderDeliveryFeeDetail detail = detailList.get(j + i * maxRowNum);
				row = sheet.createRow((int) j + 1);

				// 门店名称
				if (null != detail.getDiySiteName()) {
					row.createCell(0).setCellValue(objToString(detail.getDiySiteName()));
				} else {
					row.createCell(0).setCellValue(" ");
				}

				// 主单号

				if (null != detail.getMainOrderNumber()) {
					row.createCell(1).setCellValue(objToString(detail.getMainOrderNumber()));
				} else {
					row.createCell(1).setCellValue(" ");
				}

				// 下单日期
				if (null != detail.getOrderTime()) {
					row.createCell(2).setCellValue(objToString(detail.getOrderTime()));
				} else {
					row.createCell(2).setCellValue(objToString(" "));
				}

				// 封车日期
				if (null != detail.getSendTime()) {
					row.createCell(3).setCellValue(objToString(detail.getSendTime()));
				} else {
					row.createCell(3).setCellValue(objToString(" "));
				}

				// 订单状态
				if (null != detail.getStatusId()) {
					switch (detail.getStatusId()) {
					case 4:
						row.createCell(4).setCellValue("待签收");
						break;
					case 5:
						row.createCell(4).setCellValue("待评价");
						break;
					case 6:
						row.createCell(4).setCellValue("已完成");
						break;
					case 7:
						row.createCell(4).setCellValue("已取消");
						break;
					case 8:
						row.createCell(4).setCellValue("已删除");
						break;
					case 9:
						row.createCell(4).setCellValue("退货中");
						break;
					case 10:
						row.createCell(4).setCellValue("退货确认");
						break;
					case 11:
						row.createCell(4).setCellValue("退货取消");
						break;
					case 12:
						row.createCell(4).setCellValue("退货完成");
						break;
					default:
						break;
					}
				} else {
					row.createCell(4).setCellValue(objToString(" "));
				}

				// 导购
				if (null != detail.getSellerRealName()) {
					row.createCell(5).setCellValue(objToString(detail.getSellerRealName()));
				} else {
					row.createCell(5).setCellValue(objToString(" "));
				}

				// 客户名称
				if (null != detail.getUserRealName()) {
					row.createCell(6).setCellValue(objToString(detail.getUserRealName()));
				} else {
					row.createCell(6).setCellValue(objToString(" "));
				}

				// 客户电话
				if (null != detail.getUsername()) {
					row.createCell(7).setCellValue(objToString(replacePhoneNumberWithStar(detail.getUsername())));
				} else {
					row.createCell(7).setCellValue(objToString(" "));
				}

				// 大桶漆配送费
				if (null != detail.getBucketsOfPaintFee()) {
					row.createCell(8).setCellValue(objToString(detail.getBucketsOfPaintFee()));
				} else {
					row.createCell(8).setCellValue(objToString("0.00 "));
				}

				// 硝基漆10L配送费
				if (null != detail.getNitrolacquerFee()) {
					row.createCell(9).setCellValue(objToString(detail.getNitrolacquerFee()));
				} else {
					row.createCell(9).setCellValue(objToString("0.00 "));
				}

				// 小桶漆/木器漆配送费
				if (null != detail.getCarpentryPaintFee()) {
					row.createCell(10).setCellValue(objToString(detail.getCarpentryPaintFee()));
				} else {
					row.createCell(10).setCellValue(objToString("0.00"));
				}

				// 4kg以下漆类配送费
				if (null != detail.getBelowFourKiloFee()) {
					row.createCell(11).setCellValue(objToString(detail.getBelowFourKiloFee()));
				} else {
					row.createCell(11).setCellValue(objToString("0.00"));
				}

				// 墙面辅料金额
				if (null != detail.getWallAccessories()) {
					row.createCell(12).setCellValue(objToString(detail.getWallAccessories()));
				} else {
					row.createCell(12).setCellValue(objToString("0.00"));
				}

				// 客户应承担运费
				if (null != detail.getConsumerDeliveryFee()) {
					row.createCell(13).setCellValue(objToString(detail.getConsumerDeliveryFee()));
				} else {

					row.createCell(13).setCellValue(objToString("0.00"));
				}

				// 打折减免运费
				if (null != detail.getConsumerDeliveryFeeDiscount()) {
					row.createCell(14).setCellValue(objToString(detail.getConsumerDeliveryFeeDiscount()));
				} else {
					row.createCell(14).setCellValue("0.00");
				}

				// 购辅料减免运费
				if (null != detail.getConsumerDeliveryFeeReduce()) {
					row.createCell(15).setCellValue(objToString(detail.getConsumerDeliveryFeeReduce()));
				} else {
					row.createCell(15).setCellValue(objToString("0.00"));
				}

				// 客户实际运费
				if (null != detail.getConsumerDeliveryFeeFinal()) {
					row.createCell(16).setCellValue(objToString(detail.getConsumerDeliveryFeeFinal()));
				} else {
					row.createCell(16).setCellValue(objToString("0.00"));
				}

				// 公司应承担运费
				if (null != detail.getCompanyDeliveryFee()) {
					row.createCell(17).setCellValue(objToString(detail.getCompanyDeliveryFee()));
				} else {
					row.createCell(17).setCellValue(objToString("0.00"));
				}

				// 打折减免公司运费
				if (null != detail.getCompanyDeliveryFeeDiscount()) {
					row.createCell(18).setCellValue(objToString(detail.getCompanyDeliveryFeeDiscount()));
				} else {
					row.createCell(18).setCellValue(objToString("0.00"));
				}
				// 购辅料减免运费
				if (null != detail.getCompanyDeliveryFeeReduce()) {
					row.createCell(19).setCellValue(objToString(detail.getCompanyDeliveryFeeReduce()));
				} else {
					row.createCell(19).setCellValue(objToString("0.00"));
				}
				// 公司实际运费
				if (null != detail.getCompanyDeliveryFeeFinal()) {
					row.createCell(20).setCellValue(objToString(detail.getCompanyDeliveryFeeFinal()));
				} else {
					row.createCell(20).setCellValue(objToString("0.00"));
				}

			}
		}
		return wb;
	}*/
	
	/**
	 * 乐易装华润基础运费报表（备用）
	 * @param begin
	 * @param end
	 * @param diyCode
	 * @param cityName
	 * @param username
	 * @param roleDiyIds
	 * @return 
	 * @throws Exception 
	 */
	private HSSFWorkbook LyzHrDeliveryFeeBookBackUp(Date begin, Date end, String diyCode, String cityName,
			String username, List<String> roleDiyIds) throws Exception {
		// 创建工作簿
		HSSFWorkbook wb = new HSSFWorkbook();
		List<TdOrder> orders = tdOrderService.findOrdersOfDeliveryHome(begin, end, diyCode, cityName, roleDiyIds);// 按条件查找全部送货上门的订单
		int maxRowNum = 60000;
		int maxSize = 0;
		if (orders != null) {
			maxSize = orders.size();
		}
		int sheets = maxSize / maxRowNum + 1;
		// 写入excel文件数据信息
		for (int i = 0; i < sheets; i++) {

			// 第二步，在webbook中添加一个sheet,对应Excel文件中的sheet
			HSSFSheet sheet = wb.createSheet("第" + (i + 1) + "页");
			// 第三步，在sheet中添加表头第0行,注意老版本poi对Excel的行数列数有限制short
			// 列宽
			int[] widths = { 20, 30, 20, 20, 15, 15, 20, 18, 18, 18, 25, 25, 18, 18, 18, 18, 18, 18, 18, 18, 18 };
			sheetColumnWidth(sheet, widths);

			// 第四步，创建单元格，并设置值表头 设置表头居中
			HSSFCellStyle style = wb.createCellStyle();
			style.setAlignment(HSSFCellStyle.ALIGN_CENTER); // 创建一个居中格式
			style.setWrapText(true);

			// 设置标题
			HSSFRow row = sheet.createRow((int) 0);

			String[] cellValues = { "门店名称", "主单号", "下单日期", "封车日期", "订单状态", "导购", "客户名称", "客户电话", "大桶漆配送费", "硝基漆10L配送费",
					"小桶漆/木器漆配送费", "4kg以下漆类配送费", "墙面辅料费", "客户应承担运费", "打折减免客户运费", "购辅料减免客户运费", "客户实际运费", "商户应承担运费",
					"打折减免商户运费", "购辅料减免商户运费", "商户实际运费" };
			cellDates(cellValues, style, row);

			for (int j = 0; j < maxRowNum; j++) {
				if (j + i * maxRowNum >= maxSize) {
					break;
				}
				TdOrder detail = orders.get(j + i * maxRowNum);
				row = sheet.createRow((int) j + 1);

				// 门店名称
				if (null != detail.getDiySiteName()) {
					row.createCell(0).setCellValue(objToString(detail.getDiySiteName()));
				} else {
					row.createCell(0).setCellValue(" ");
				}

				// 主单号

				if (null != detail.getMainOrderNumber()) {
					row.createCell(1).setCellValue(objToString(detail.getMainOrderNumber()));
				} else {
					row.createCell(1).setCellValue(" ");
				}

				// 下单日期
				if (null != detail.getOrderTime()) {
					row.createCell(2).setCellValue(objToString(detail.getOrderTime()));
				} else {
					row.createCell(2).setCellValue(objToString(" "));
				}

				// 封车日期
				if (null != detail.getSendTime()) {
					row.createCell(3).setCellValue(objToString(detail.getSendTime()));
				} else {
					row.createCell(3).setCellValue(objToString(" "));
				}

				// 订单状态
				if (null != detail.getStatusId()) {
					switch (detail.getStatusId().intValue()) {
					case 4:
						row.createCell(4).setCellValue("待签收");
						break;
					case 5:
						row.createCell(4).setCellValue("待评价");
						break;
					case 6:
						row.createCell(4).setCellValue("已完成");
						break;
					case 7:
						row.createCell(4).setCellValue("已取消");
						break;
					case 8:
						row.createCell(4).setCellValue("已删除");
						break;
					case 9:
						row.createCell(4).setCellValue("退货中");
						break;
					case 10:
						row.createCell(4).setCellValue("退货确认");
						break;
					case 11:
						row.createCell(4).setCellValue("退货取消");
						break;
					case 12:
						row.createCell(4).setCellValue("退货完成");
						break;
					default:
						break;
					}
				} else {
					row.createCell(4).setCellValue(objToString(" "));
				}

				// 导购
				if (null != detail.getSellerRealName()) {
					row.createCell(5).setCellValue(objToString(detail.getSellerRealName()));
				} else {
					row.createCell(5).setCellValue(objToString(" "));
				}

				// 客户名称
				if (null != detail.getRealUserRealName()) {
					row.createCell(6).setCellValue(objToString(detail.getRealUserRealName()));
				} else {
					row.createCell(6).setCellValue(objToString(" "));
				}

				// 客户电话
				if (null != detail.getUsername()) {
					row.createCell(7).setCellValue(objToString(replacePhoneNumberWithStar(detail.getUsername())));
				} else {
					row.createCell(7).setCellValue(objToString(" "));
				}

				List<TdOrderGoods> orderGoodsList = tdOrderGoodsService
						.findByMainOrderNumber(detail.getMainOrderNumber());
				TdOrder orderYF = tdOrderService.findFixedFlagByMainOrderNumber(detail.getMainOrderNumber());

				double bucketsOfPaintFee = 0;// 大桶漆配送费
				double nitrolacquerFee = 0;// 硝基漆10L配送费
				double carpentryPaintFee = 0;// 小桶漆/木器漆配送费
				double belowFourKiloFee = 0;// 4kg以下漆类配送费
				double wallAccessories = 0;// 订单辅料总金额
				double consumerDeliveryFee = 0;// 客户承担的运费
				double companyDeliveryFee = 0;// 公司承担的运费

				long consumerAffordQuantity = 0;// 客户承担运费的漆桶数

				long companyAffordQuantity = 0;// 公司承担运费的漆桶数
				TdOrderDeliveryFeeDetail deliveryFeeDetail = tdOrderDeliveryFeeDetailService
						.findByMainOrderNumber(detail.getMainOrderNumber());
				if (null == deliveryFeeDetail){
					deliveryFeeDetail = new TdOrderDeliveryFeeDetail();
				}
				Double deliveryFee = 0d;// 运费总额
				TdUser userTemp = tdUserService.findByUsername(detail.getUsername());
				if(null == userTemp){
					System.out.println("该单未找到用户："+detail.getMainOrderNumber());
					userTemp = new TdUser();
					
						switch (detail.getCity()) {
						case "成都市":
							userTemp.setCityId(2121L);
							break;
						case "郑州市":
							userTemp.setCityId(2033L);
							break;
						default:
							break;
						}
					}
				

				// Map<Long, TdOrderGoods> orderGoodsMap =
				// this.countOrderGoodsNumber(order);
				for (TdOrderGoods orderGoods : orderGoodsList) {
					TdDeliveryFeeHead tdDeliveryFeeHead = tdDeliveryFeeHeadService
							.findByGoodsId(orderGoods.getGoodsId());
					Double fee = settlementService.countOrderGoodsDeliveryFee(userTemp, orderGoods);
					// 运费大于0 说明该产品有运费配置，则tdDeliveryFeeHead不为null
					if (null != fee && fee > 0) {
						switch (tdDeliveryFeeHead.getGoodsTypeId()) {
						case 1:
							bucketsOfPaintFee += fee;
							break;
						case 2:
							nitrolacquerFee += fee;
							break;
						case 3:
							carpentryPaintFee += fee;
							break;
						case 4:
							belowFourKiloFee += fee;
							break;
						default:
							break;
						}
					}

					if (null != tdDeliveryFeeHead && tdDeliveryFeeHead.getAssumedObjectId() == 1) {
						consumerDeliveryFee += fee;
						consumerAffordQuantity += orderGoods.getQuantity();

					} else if (null != tdDeliveryFeeHead && tdDeliveryFeeHead.getAssumedObjectId() == 2) {
						companyDeliveryFee += fee;
						companyAffordQuantity += orderGoods.getQuantity();
					}
					if (orderGoods.getIsWallAccessory() == true) {
						wallAccessories += orderGoods.getPrice() * orderGoods.getQuantity();
					}

				}

				// 设置各种类漆的运费金额
				deliveryFeeDetail.setBucketsOfPaintFee(bucketsOfPaintFee);
				deliveryFeeDetail.setNitrolacquerFee(nitrolacquerFee);
				deliveryFeeDetail.setCarpentryPaintFee(carpentryPaintFee);
				deliveryFeeDetail.setBelowFourKiloFee(belowFourKiloFee);

				// 大桶漆配送费
				if (null != deliveryFeeDetail.getBucketsOfPaintFee()) {
					row.createCell(8).setCellValue(objToString(deliveryFeeDetail.getBucketsOfPaintFee()));
				} else {
					row.createCell(8).setCellValue(objToString("0.00 "));
				}

				// 硝基漆10L配送费
				if (null != deliveryFeeDetail.getNitrolacquerFee()) {
					row.createCell(9).setCellValue(objToString(deliveryFeeDetail.getNitrolacquerFee()));
				} else {
					row.createCell(9).setCellValue(objToString("0.00 "));
				}

				// 小桶漆/木器漆配送费
				if (null != deliveryFeeDetail.getCarpentryPaintFee()) {
					row.createCell(10).setCellValue(objToString(deliveryFeeDetail.getCarpentryPaintFee()));
				} else {
					row.createCell(10).setCellValue(objToString("0.00"));
				}

				// 4kg以下漆类配送费
				if (null != deliveryFeeDetail.getBelowFourKiloFee()) {
					row.createCell(11).setCellValue(objToString(deliveryFeeDetail.getBelowFourKiloFee()));
				} else {
					row.createCell(11).setCellValue(objToString("0.00"));
				}

				// 设置本单墙面辅料总金额
				deliveryFeeDetail.setWallAccessories(wallAccessories);

				// 墙面辅料金额
				if (null != deliveryFeeDetail.getWallAccessories()) {
					row.createCell(12).setCellValue(objToString(deliveryFeeDetail.getWallAccessories()));
				} else {
					row.createCell(12).setCellValue(objToString("0.00"));
				}

				deliveryFeeDetail.setConsumerDeliveryFee(consumerDeliveryFee);// 设置优惠前客户承担运费金额
				// 客户应承担运费
				if (null != deliveryFeeDetail.getConsumerDeliveryFee()) {
					row.createCell(13).setCellValue(objToString(deliveryFeeDetail.getConsumerDeliveryFee()));
				} else {

					row.createCell(13).setCellValue(objToString("0.00"));
				}

				Double companyDeliveryFeeTemp = companyDeliveryFee;

				deliveryFeeDetail.setCompanyDeliveryFee(companyDeliveryFee);// 设置优惠前公司承担运费金额

				deliveryFee = consumerDeliveryFee + companyDeliveryFee;

				TdSetting setting = tdSettingService.findTopBy();

				// 判断总运费金额是否大于等于20，如果小于20，则差额由华润公司承担
				Double settingMinFee = null == setting.getMinDeliveryFee() ? 0d : setting.getMinDeliveryFee();
				if (deliveryFee <= settingMinFee) {
					if (consumerDeliveryFee > 0.00 && companyDeliveryFee == 0.00) { // 客户承担运费
						deliveryFeeDetail.setConsumerDeliveryFeeAdjust(settingMinFee - deliveryFee);
						deliveryFeeDetail.setCompanyDeliveryFeeAdjust(0.00);
						consumerDeliveryFee = settingMinFee;
					} else if(consumerDeliveryFee >= 0.00 && companyDeliveryFee > 0.00) {
						deliveryFeeDetail.setConsumerDeliveryFeeAdjust(0.00);
						deliveryFeeDetail.setCompanyDeliveryFeeAdjust(settingMinFee - deliveryFee);
						companyDeliveryFee = settingMinFee-consumerDeliveryFee;
					}else if(consumerDeliveryFee == 0.00 && companyDeliveryFee == 0.00){
						deliveryFeeDetail.setConsumerDeliveryFeeAdjust(0.00);
						deliveryFeeDetail.setCompanyDeliveryFeeAdjust(0.00);
					}

				} else {
					deliveryFeeDetail.setConsumerDeliveryFeeAdjust(0.00);
					deliveryFeeDetail.setCompanyDeliveryFeeAdjust(0.00);
				}
				// 运费折扣优惠，如果用户承担运费的漆类桶数和华润承担运费的类桶数任意一个大于20桶，则双方运费都打7.5折；如果大于100桶，折扣为6折
				if (20<=(consumerAffordQuantity + companyAffordQuantity) && (consumerAffordQuantity + companyAffordQuantity)< 99){
					consumerDeliveryFee = consumerDeliveryFee * 0.75;
					companyDeliveryFee = companyDeliveryFee * 0.75;
					deliveryFee = consumerDeliveryFee + companyDeliveryFee;
				} else if (consumerAffordQuantity >= 100 || companyAffordQuantity >= 100) {
					consumerDeliveryFee = consumerDeliveryFee * 0.6;
					companyDeliveryFee = companyDeliveryFee * 0.6;
					deliveryFee = consumerDeliveryFee + companyDeliveryFee;
				}
				deliveryFeeDetail.setConsumerDeliveryFeeDiscount(deliveryFeeDetail.getConsumerDeliveryFee()-deliveryFeeDetail.getConsumerDeliveryFeeAdjust() - consumerDeliveryFee);// 设置用户运费打折金额
				deliveryFeeDetail.setCompanyDeliveryFeeDiscount(deliveryFeeDetail.getCompanyDeliveryFee()+deliveryFeeDetail.getCompanyDeliveryFeeAdjust() - companyDeliveryFee);// 设置华润公司运费打折金额
				// 墙面辅料金额以500为阶梯减免运费。500减20,1000减40，以此类推。其中减免的运费优先由用户享受，如果用户承担的运费小于优惠金额，则剩余的优惠金额才能由华润享受
				if (wallAccessories >= 500) {
					double reduceDeliveryFee = ((int)wallAccessories / 500) * 20;// 购辅料减免运费总额
					if (reduceDeliveryFee <= deliveryFee) {
						if (reduceDeliveryFee <= consumerDeliveryFee) {// 如果辅料减免的运费小于当前用户承担的运费，则全部用来减免用户用费
							deliveryFeeDetail.setConsumerDeliveryFeeReduce(reduceDeliveryFee);
							deliveryFeeDetail.setCompanyDeliveryFeeReduce(0.00);

							consumerDeliveryFee -= reduceDeliveryFee;
						} else{// 如果辅料减免运费大于用户承担的运费，则用户用费全免，剩余部分用来减免华润运费
							deliveryFeeDetail.setConsumerDeliveryFeeReduce(consumerDeliveryFee);
							deliveryFeeDetail.setCompanyDeliveryFeeReduce(reduceDeliveryFee - consumerDeliveryFee);

							companyDeliveryFee -= (reduceDeliveryFee - consumerDeliveryFee);
							consumerDeliveryFee = 0.0;

						}
					} else {//如果运费减免大于当前运费总和，则运费全为0
						deliveryFeeDetail.setConsumerDeliveryFeeReduce(consumerDeliveryFee);
						deliveryFeeDetail.setCompanyDeliveryFeeReduce(companyDeliveryFee);
						consumerDeliveryFee = 0.0;
						companyDeliveryFee = 0.0;

					}
				} else {
					deliveryFeeDetail.setConsumerDeliveryFeeReduce(0.00);
					deliveryFeeDetail.setCompanyDeliveryFeeReduce(0.00);
				}
				deliveryFeeDetail.setConsumerDeliveryFeeFinal(consumerDeliveryFee);
				deliveryFeeDetail.setCompanyDeliveryFeeFinal(companyDeliveryFee);
				deliveryFeeDetail.setCustomerDeliveryFeeBeforeModified(consumerDeliveryFee);


				if (null != orderYF && null != orderYF.getIsFixedDeliveryFee() && orderYF.getIsFixedDeliveryFee()) {
					deliveryFeeDetail.setConsumerDeliveryFeeFinal(orderYF.getDeliverFee());
					deliveryFeeDetail.setCompanyDeliveryFeeFinal(orderYF.getCompanyDeliveryFee());
				}
				// 打折减免运费
				if (null != deliveryFeeDetail.getConsumerDeliveryFeeDiscount()) {
					row.createCell(14).setCellValue(objToString(deliveryFeeDetail.getConsumerDeliveryFeeDiscount()));
				} else {
					row.createCell(14).setCellValue("0.00");
				}

				// 购辅料减免运费
				if (null != deliveryFeeDetail.getConsumerDeliveryFeeReduce()) {
					row.createCell(15).setCellValue(objToString(deliveryFeeDetail.getConsumerDeliveryFeeReduce()));
				} else {
					row.createCell(15).setCellValue(objToString("0.00"));
				}

				// 客户实际运费
				if (null != deliveryFeeDetail.getConsumerDeliveryFeeFinal()) {
					row.createCell(16).setCellValue(objToString(deliveryFeeDetail.getConsumerDeliveryFeeFinal()));
				} else {
					row.createCell(16).setCellValue(objToString("0.00"));
				}

				// 公司应承担运费
				if (null != detail.getCompanyDeliveryFee()) {
					row.createCell(17).setCellValue(objToString(companyDeliveryFeeTemp));
				} else {
					row.createCell(17).setCellValue(objToString("0.00"));
				}

				// 打折减免公司运费
				if (null != deliveryFeeDetail.getCompanyDeliveryFeeDiscount()) {
					row.createCell(18).setCellValue(objToString(deliveryFeeDetail.getCompanyDeliveryFeeDiscount()));
				} else {
					row.createCell(18).setCellValue(objToString("0.00"));
				}
				// 购辅料减免运费
				if (null != deliveryFeeDetail.getCompanyDeliveryFeeReduce()) {
					row.createCell(19).setCellValue(objToString(deliveryFeeDetail.getCompanyDeliveryFeeReduce()));
				} else {
					row.createCell(19).setCellValue(objToString("0.00"));
				}
				// 公司实际运费
				if (null != deliveryFeeDetail.getCompanyDeliveryFeeFinal()) {
					row.createCell(20).setCellValue(objToString(deliveryFeeDetail.getCompanyDeliveryFeeFinal()));
				} else {
					row.createCell(20).setCellValue(objToString("0.00"));
				}

			}
		}

		return wb;
	}

	/**
	 * 预存款变更商品明细表
	 * @param begin
	 * @param end
	 * @param diyCode
	 * @param cityName
	 * @param username
	 * @param roleDiyIds
	 * @return
	 */
	private HSSFWorkbook salesDetailForFranchiser(Date begin,Date end,String diyCode,String cityName,String username,List<String> roleDiyIds) {


        List<TdSalesDetailForFranchiser> salesDetailList= tdSalesDetailForFranchiserService.queryDownList(begin, end, cityName, diyCode, roleDiyIds);
		//创建excel工作簿
		HSSFWorkbook wb = new HSSFWorkbook();
		//excel单表最大行数是65535
        int maxRowNum = 60000;
        int maxSize=0;
        if(salesDetailList!=null){
        	maxSize=salesDetailList.size();
        }
        int sheets = maxSize/maxRowNum+1;
        
		//写入excel文件数据信息
		for(int i=0;i<sheets;i++){
			
			//在webbook中添加一个sheet,对应Excel文件中的sheet
	        HSSFSheet sheet = wb.createSheet("第"+(i+1)+"页");
	        //设置列宽
	        int[] widths={13,30,30,25,13,13,13,13,18,18,13,
	        		9,9,9,9,15,15,15,15,13,13,18,13,
	        		13,13,13,20,30,30};
	        sheetColumnWidth(sheet,widths);
	        // 创建单元格，并设置值表头 设置表头居中
	        HSSFCellStyle style = wb.createCellStyle();
	        style.setAlignment(HSSFCellStyle.ALIGN_CENTER); // 创建一个居中格式
	        style.setWrapText(true);
	       	//设置标题
	        HSSFRow row = sheet.createRow(0);
	        String[] cellValues={"门店名称","主单号","分单号","预存款变更时间","导购","客户编号","客户名称","客户电话","产品编号","产品名称",
	        		"数量","结算单价","结算总价","经销单价","经销总价","使用产品券数量","预存款变更金额","品牌","商品父分类","商品子分类","配送状态","配送方式","中转仓","配送人员",
	        		"配送人员电话","收货人姓名","收货人电话","收货人地址","订单备注"};
			cellDates(cellValues, style, row);
			for(int j=0;j<maxRowNum;j++)
	        {
				if(j+i*maxRowNum>=maxSize){
					break;
				}
				TdSalesDetailForFranchiser salesDetail= salesDetailList.get(j+i*maxRowNum);

	        	row = sheet.createRow( j + 1);
	        	//门店名称
	        	row.createCell(0).setCellValue(objToString(salesDetail.getDiySiteName()));

	        	//主单号
	        	row.createCell(1).setCellValue(objToString(salesDetail.getMainOrderNumber()));
	        	
	        	//分单号
	        	row.createCell(2).setCellValue(objToString(salesDetail.getOrderNumber()));
	        	
	        	
	        	//预存款变更时间
	        	if(null != salesDetail.getOrderTime()){
	        		row.createCell(3).setCellValue(objToString(salesDetail.getOrderTime()));
	        	}else{
	        		row.createCell(3).setCellValue("");
	        	}
	        	
	        	//导购
				row.createCell(4).setCellValue(objToString(salesDetail.getSellerRealName()));
				//客户编号
				row.createCell(5).setCellValue(objToString(salesDetail.getUserId()));
				//客户名称
				row.createCell(6).setCellValue(objToString(salesDetail.getRealName()));
				//客户电话
				row.createCell(7).setCellValue(objToString(replacePhoneNumberWithStar(salesDetail.getUsername())));
				//产品编号
				row.createCell(8).setCellValue(objToString(salesDetail.getSku()));
				//产品名称
				row.createCell(9).setCellValue(objToString(salesDetail.getGoodsTitle()));
				//产品数量
				row.createCell(10).setCellValue(objToString(salesDetail.getQuantity()));
				//产品价格
				row.createCell(11).setCellValue(objToString(salesDetail.getPrice()));
				//产品总价
				row.createCell(12).setCellValue(objToString((salesDetail.getPrice() * salesDetail.getQuantity() *100)/100));
                if(salesDetail.getIsDirect()){
                    //经销单价
                    row.createCell(13).setCellValue("NULL");
                    //经销总价
                    row.createCell(14).setCellValue("NULL");
                }else{
                    if(null != salesDetail.getJxPrice()){
                        //经销单价
                        row.createCell(13).setCellValue(objToString(salesDetail.getJxPrice()));
                        //经销总价
                        if(null != salesDetail.getQuantity()){
                            row.createCell(14).setCellValue(objToString(salesDetail.getJxPrice() * salesDetail.getQuantity()));
                        }else{
                            row.createCell(14).setCellValue(objToString(0L));
                        }
                    }else{
                        row.createCell(13).setCellValue(objToString(0L));
                        row.createCell(14).setCellValue(objToString(0L));
                    }
                }
				//使用产品券数量
                row.createCell(15).setCellValue(objToString(salesDetail.getProductCouponQuantity()));
                //预存款变更金额
                if(salesDetail.getIsDirect()){
                    row.createCell(16).setCellValue(objToString((salesDetail.getQuantity()-salesDetail.getProductCouponQuantity())*salesDetail.getPrice()));
                }else{
                    row.createCell(16).setCellValue(objToString((salesDetail.getQuantity()-salesDetail.getProductCouponQuantity())*salesDetail.getJxPrice()));
                }
	          	//品牌
				row.createCell(17).setCellValue(objToString(salesDetail.getBrandTitle()));
	    		//商品父分类
				if(null != salesDetail.getGoodsParentTypeTitle()){
					row.createCell(18).setCellValue(objToString(salesDetail.getGoodsParentTypeTitle()));
				}else{
					row.createCell(18).setCellValue("");
				}
				//商品子分类
				if(null != salesDetail.getGoodsTypeTitle()){
					row.createCell(19).setCellValue(objToString(salesDetail.getGoodsTypeTitle()));
				}else{
					row.createCell(19).setCellValue("");
				}
	        	//配送状态
	        	if(null != salesDetail.getMainOrderNumber()){
	        		if(salesDetail.getMainOrderNumber().contains("T") ){
	        			if(salesDetail.getRemark().equals("用户取消订单，退货")){
	        				row.createCell(20).setCellValue("订单取消");
	        			}else{
	        				row.createCell(20).setCellValue("已收货");
	        			}
	        		}else{
	        			if(salesDetail.getStatusId()>3 && salesDetail.getStatusId() != 7 && salesDetail.getStatusId() !=8 ){
	        				row.createCell(20).setCellValue("已出货");
	        			}else{
	        				row.createCell(20).setCellValue("未出货");
	        			}
	        		}
	        	}
				//配送方式
	        	row.createCell(21).setCellValue(objToString(salesDetail.getDeliverTypeTitle()));
				//中转仓
	        	if(null != salesDetail.getWhName()){
	        		row.createCell(22).setCellValue(objToString(salesDetail.getWhName()));
	        	}else{
	        		row.createCell(22).setCellValue("");
	        	}
	        	//配送人员
	            if(null != salesDetail.getDeliverRealName()){
	            	row.createCell(23).setCellValue(objToString(salesDetail.getDeliverRealName()));
	            }else{
	            	row.createCell(23).setCellValue("");
	            }
	        	//配送人员电话
	            if(null != salesDetail.getDeliverUsername()){
	            	row.createCell(24).setCellValue(objToString(salesDetail.getDeliverUsername()));
	            }else{
	            	row.createCell(24).setCellValue("");
	            }
	        	//收货人姓名
	        	if(null != salesDetail.getShippingName()){
	        		row.createCell(25).setCellValue(objToString(salesDetail.getShippingName()));
	        	}else{
	        		row.createCell(25).setCellValue("");
	        	}
	        	//收货人电话
	        	if(null != salesDetail.getShippingPhone()){
	        		row.createCell(26).setCellValue(objToString(salesDetail.getShippingPhone()));
	        	}else{
	        		row.createCell(26).setCellValue("");
	        	}
	        	//收货人地址
	        	if(null != salesDetail.getShippingAddress()){
	        		row.createCell(27).setCellValue(objToString(salesDetail.getShippingAddress()));
	        	}else{
	        		row.createCell(27).setCellValue("");
	        	}
	        	//订单备注
	        	if(null != salesDetail.getRemark()){
	        		row.createCell(28).setCellValue(objToString(salesDetail.getRemark()));
	        	}else{
	        		row.createCell(28).setCellValue("");
	        	}
			}
		}
        return wb;
	}

	
	private String objToString(Object obj) {
		if (obj == null) {
			return "";
		}
		return obj.toString();
	}

	public static <T> T[] concat(T[] first, T[] second) {
		T[] result = Arrays.copyOf(first, first.length + second.length);
		System.arraycopy(second, 0, result, first.length, second.length);
		return result;
	}

	/**
	 * 日期转字符串
	 */
	public static String dateToString(Date date) {

		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		return sdf.format(date).substring(0, 6);

	}
	
	
	/**
	 * 获取月份数组
	 * @param begin
	 * @param end
	 * @return
	 * @throws ParseException
	 */
	public static List<String> getMonth(String begin,String end) throws ParseException{
		
		 List<String> listStr = new ArrayList<String>();
		 String beginStr = begin.substring(0,4)+"-"+begin.substring(4,6);
		 String endStr = end.substring(0,4)+"-"+end.substring(4,6);
		  Date d1 = new SimpleDateFormat("yyyy-MM").parse(beginStr);//定义起始日期

		  Date d2 = new SimpleDateFormat("yyyy-MM").parse(endStr);//定义结束日期

		  Calendar dd = Calendar.getInstance();//定义日期实例

		  dd.setTime(d1);//设置日期起始时间

		  while(!dd.getTime().after(d2)){//判断是否到结束日期

		  SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");

		  String str = sdf.format(dd.getTime());

		  System.out.println(str);//输出日期结果
		  listStr.add(str);

		  dd.add(Calendar.MONTH, 1);//进行当前日期月份加1

		  }
	        
		System.out.println(listStr);
		return listStr;
		
	}
	
	public String replacePhoneNumberWithStar(String phoneNumber){
		if(null != phoneNumber && !"".equals(phoneNumber)){
			return phoneNumber.substring(0,3)+"****"+phoneNumber.substring(7);
		}
		return "";
	}
	
	/**
	 * 涂料基础运费报表
	 * @param begin
	 * @param end
	 * @param diyCode
	 * @param cityName
	 * @param username
	 * @param roleDiyIds
	 * @return
	 */
	
	private HSSFWorkbook LyzHrDeliveryFeeBook(Date begin, Date end, String diyCode, String cityName, String username,
			List<String> roleDiyIds) {
		// 创建工作簿
		HSSFWorkbook wb = new HSSFWorkbook();

		List<TdOrderDeliveryFeeDetailStatement> detailList = tdOrderDeliveryFeeDetailStatementService.queryDownOrderList(begin, end, cityName,
				diyCode, roleDiyIds);
		int maxRowNum = 60000;
		int maxSize = 0;
		if (detailList != null) {
			maxSize = detailList.size();
		}
		int sheets = maxSize / maxRowNum + 1;

		// 写入excel文件数据信息
		for (int i = 0; i < sheets; i++) {

			// 第二步，在webbook中添加一个sheet,对应Excel文件中的sheet
			HSSFSheet sheet = wb.createSheet("第" + (i + 1) + "页");
			// 第三步，在sheet中添加表头第0行,注意老版本poi对Excel的行数列数有限制short
			// 列宽
			int[] widths = { 20, 30, 20, 20, 15, 15, 20, 18, 18, 18, 25, 25, 18, 18, 18, 18, 18, 18, 18, 18, 18 };
			sheetColumnWidth(sheet, widths);

			// 第四步，创建单元格，并设置值表头 设置表头居中
			HSSFCellStyle style = wb.createCellStyle();
			style.setAlignment(HSSFCellStyle.ALIGN_CENTER); // 创建一个居中格式
			style.setWrapText(true);

			// 设置标题
			HSSFRow row = sheet.createRow((int) 0);

			String[] cellValues = { "门店名称", "主单号", "下单日期", "封车日期", "订单状态", "导购", "客户名称", "客户电话", "大桶漆配送费", "硝基漆10L配送费",
					"小桶漆/木器漆配送费", "4kg以下漆类配送费", "订单总金额", "客户实际运费", "商户应承担运费","打折减免商户运费", "商户实际运费" };
			cellDates(cellValues, style, row);

			for (int j = 0; j < maxRowNum; j++) {
				if (j + i * maxRowNum >= maxSize) {
					break;
				}
				
				TdOrderDeliveryFeeDetailStatement detail = (TdOrderDeliveryFeeDetailStatement)detailList.get(j + i * maxRowNum);
				row = sheet.createRow((int) j + 1);

				// 门店名称0
				if (null != detail.getDiySiteName()) {
					row.createCell(0).setCellValue(objToString(detail.getDiySiteName()));
				} else {
					row.createCell(0).setCellValue(" ");
				}

				// 主单号1

				if (null != detail.getMainOrderNumber()) {
					row.createCell(1).setCellValue(objToString(detail.getMainOrderNumber()));
				} else {
					row.createCell(1).setCellValue(" ");
				}

				// 下单日期2
				if (null != detail.getOrderTime()) {
					row.createCell(2).setCellValue(objToString(detail.getOrderTime()));
				} else {
					row.createCell(2).setCellValue(objToString(" "));
				}

				// 封车日期3
				if (null != detail.getSendTime()) {
					row.createCell(3).setCellValue(objToString(detail.getSendTime()));
				} else {
					row.createCell(3).setCellValue(objToString(" "));
				}

				// 订单状态4
				if (null != detail.getStatusId()) {
					switch (detail.getStatusId()) {
					case 4:
						row.createCell(4).setCellValue("待签收");
						break;
					case 5:
						row.createCell(4).setCellValue("待评价");
						break;
					case 6:
						row.createCell(4).setCellValue("已完成");
						break;
					case 7:
						row.createCell(4).setCellValue("已取消");
						break;
					case 8:
						row.createCell(4).setCellValue("已删除");
						break;
					case 9:
						row.createCell(4).setCellValue("退货中");
						break;
					case 10:
						row.createCell(4).setCellValue("退货确认");
						break;
					case 11:
						row.createCell(4).setCellValue("退货取消");
						break;
					case 12:
						row.createCell(4).setCellValue("退货完成");
						break;
					default:
						break;
					}
				} else {
					row.createCell(4).setCellValue(objToString(" "));
				}

				// 导购5
				if (null != detail.getSellerRealName()) {
					row.createCell(5).setCellValue(objToString(detail.getSellerRealName()));
				} else {
					row.createCell(5).setCellValue(objToString(" "));
				}

				// 客户名称6
				if (null != detail.getUserRealName()) {
					row.createCell(6).setCellValue(objToString(detail.getUserRealName()));
				} else {
					row.createCell(6).setCellValue(objToString(" "));
				}

				// 客户电话7
				if (null != detail.getUsername()) {
					row.createCell(7).setCellValue(objToString(replacePhoneNumberWithStar(detail.getUsername())));
				} else {
					row.createCell(7).setCellValue(objToString(" "));
				}

				// 大桶漆配送费8
				if (null != detail.getBucketsOfPaintFee()) {
					row.createCell(8).setCellValue(objToString(detail.getBucketsOfPaintFee()));
				} else {
					row.createCell(8).setCellValue(objToString("0.00 "));
				}

				// 硝基漆10L配送费9
				if (null != detail.getNitrolacquerFee()) {
					row.createCell(9).setCellValue(objToString(detail.getNitrolacquerFee()));
				} else {
					row.createCell(9).setCellValue(objToString("0.00 "));
				}

				// 小桶漆/木器漆配送费10
				if (null != detail.getCarpentryPaintFee()) {
					row.createCell(10).setCellValue(objToString(detail.getCarpentryPaintFee()));
				} else {
					row.createCell(10).setCellValue(objToString("0.00"));
				}

				// 4kg以下漆类配送费11
				if (null != detail.getBelowFourKiloFee()) {
					row.createCell(11).setCellValue(objToString(detail.getBelowFourKiloFee()));
				} else {
					row.createCell(11).setCellValue(objToString("0.00"));
				}

				//订单总金额12
				if(null != detail.getOrderTotalMoney()){
					row.createCell(12).setCellValue(objToString(detail.getOrderTotalMoney()));
				}else{
					row.createCell(12).setCellValue(objToString("0.00"));
				}

				// 客户实际运费13
				if (null != detail.getConsumerDeliveryFeeFinal()) {
					row.createCell(13).setCellValue(objToString(detail.getConsumerDeliveryFeeFinal()));
				} else {
					row.createCell(13).setCellValue(objToString("0.00"));
				}

				// 公司应承担运费14
				if (null != detail.getCompanyDeliveryFee()) {
					row.createCell(14).setCellValue(objToString(detail.getCompanyDeliveryFee()));
				} else {
					row.createCell(14).setCellValue(objToString("0.00"));
				}

				// 打折减免公司运费15
				if (null != detail.getCompanyDeliveryFeeDiscount()) {
					row.createCell(15).setCellValue(objToString(detail.getCompanyDeliveryFeeDiscount()));
				} else {
					row.createCell(15).setCellValue(objToString("0.00"));
				}
				
				// 公司实际运费16
				if (null != detail.getCompanyDeliveryFeeFinal()) {
					row.createCell(16).setCellValue(objToString(detail.getCompanyDeliveryFeeFinal()));
				} else {
					row.createCell(16).setCellValue(objToString("0.00"));
				}

			}
		}
		return wb;
	}
	
	/**
	 * 商品出退货报表
	 *
	 * 查询条件增加管理员管辖门店
	 * @param begin 开始时间
	 * @param end 结束时间
	 * @param diyCode 门店编号
	 * @param cityName 城市编号
 	 * @param username 当前用户
	 * @return
	 */
	private HSSFWorkbook  franchiseesGoodsInOutWorkBook(Date begin,Date end,String diyCode,String cityName,String username,List<String> roleDiyIds){
		// 第一步，创建一个webbook，对应一个Excel文件 
        HSSFWorkbook wb = new HSSFWorkbook();  

        // 第五步，设置值  
        List<TdGoodsInOutFranchisees> goodsInOutList=tdGoodsInOutFranchiseesService.queryFranchiseesDownList(begin, end, cityName, diyCode, roleDiyIds);
        //excel单表最大行数是65535
        int maxRowNum = 60000;
        int maxSize=0;
        if(goodsInOutList!=null){
        	maxSize=goodsInOutList.size();
        }
        int sheets = maxSize/maxRowNum+1;
        
		//写入excel文件数据信息
		for(int i=0;i<sheets;i++){
			
			// 第二步，在webbook中添加一个sheet,对应Excel文件中的sheet  
	        HSSFSheet sheet = wb.createSheet("第"+(i+1)+"页");  
	        // 第三步，在sheet中添加表头第0行,注意老版本poi对Excel的行数列数有限制short  
	        //列宽
	        int[] widths={13,25,25,18,18,13,18,18,13,13,
	        		18,9,9,9,9,9,9,9,13,13,13,18,
	        		13,13,13,13,20,20,25,30};
	        sheetColumnWidth(sheet,widths);
	        
	        // 第四步，创建单元格，并设置值表头 设置表头居中  
	        HSSFCellStyle style = wb.createCellStyle();  
	        style.setAlignment(HSSFCellStyle.ALIGN_CENTER); // 创建一个居中格式
	        style.setWrapText(true);


	       	//设置标题
	        HSSFRow row = sheet.createRow((int) 0); 
	        
	        String[] cellValues={"门店名称","主单号","分单号","下单日期","出&退货日期","订单状态","导购","客户编号","客户名称","客户电话","产品编号",
	        		"产品名称","数量","结算单价","结算总价","经销单价","经销总价","使用产品券数量","预存款变更金额","品牌","商品父分类","商品子分类","配送方式","中转仓",
	        		"配送人员","配送人员电话","收货人姓名","收货人电话","收货人地址","订单备注"};
			cellDates(cellValues, style, row);
			
			for(int j=0;j<maxRowNum;j++)
	        {
				if(j+i*maxRowNum>=maxSize){
					break;
				}
				TdGoodsInOutFranchisees goodsInOut= goodsInOutList.get(j+i*maxRowNum);
	        	row = sheet.createRow((int) j + 1);
	        	//门店名称
	        	row.createCell(0).setCellValue(objToString(goodsInOut.getDiySiteName()));

	        	//代付款订单没有主单号  分单号显示到主单号位置
	        	if(goodsInOut.getStatusId() != null && goodsInOut.getStatusId().equals(2L)){
	        		row.createCell(1).setCellValue(objToString(goodsInOut.getOrderNumber()));
	        	}else{
	        		row.createCell(1).setCellValue(objToString(goodsInOut.getMainOrderNumber()));
	        		row.createCell(2).setCellValue(objToString(goodsInOut.getOrderNumber()));
	        	} 
	        	//下单时间
	        	row.createCell(3).setCellValue(objToString(goodsInOut.getOrderTime()));
	        	//确认时间
	        	row.createCell(4).setCellValue(objToString(goodsInOut.getSalesTime()));
	        	//订单状态
                if(!goodsInOut.getMainOrderNumber().contains("T")){
                    switch (goodsInOut.getStatusId().intValue()) {
                        case 4:
                            row.createCell(5).setCellValue("待签收");
                            break;
                        case 5:
                            row.createCell(5).setCellValue("待评价");
                            break;
                        case 6:
                            row.createCell(5).setCellValue("已完成");
                            break;
                        case 7:
                            row.createCell(5).setCellValue("已取消");
                            break;
                        case 8:
                            row.createCell(5).setCellValue("已删除");
                            break;
                        case 9:
                            row.createCell(5).setCellValue("退货中");
                            break;
                        case 10:
                            row.createCell(5).setCellValue("退货确认");
                            break;
                        case 11:
                            row.createCell(5).setCellValue("退货取消");
                            break;
                        case 12:
                            row.createCell(5).setCellValue("退货完成");
                            break;
                        default:
                            break;
                    }
                }else{
                    switch (goodsInOut.getStatusId().intValue()){
                       // 1:待通知物流 2:待取货 3: 待确认收货 4 待退款（物流确认） 5 已完成 6 退货取消
                        case 1:
                            row.createCell(5).setCellValue("待通知物流");
                            break;
                        case 2:
                            row.createCell(5).setCellValue("待取货");
                            break;
                        case 3:
                            row.createCell(5).setCellValue("待确认收货");
                            break;
                        case 4:
                            row.createCell(5).setCellValue("待退款");
                            break;
                        case 5:
                            row.createCell(5).setCellValue("已完成");
                            break;
                        case 6:
                            row.createCell(5).setCellValue("退货取消");
                            break;
                        default:
                            break;
                    }
                }
	        	//导购
				row.createCell(6).setCellValue(objToString(goodsInOut.getSellerRealName()));
				//会员编号ID
				row.createCell(7).setCellValue(objToString(goodsInOut.getUserId()));
				//客户名称
				row.createCell(8).setCellValue(objToString(goodsInOut.getRealName()));
				//客户电话
				row.createCell(9).setCellValue(objToString(replacePhoneNumberWithStar(goodsInOut.getUsername())));
				//产品编号
				row.createCell(10).setCellValue(objToString(goodsInOut.getSku()));
				//产品名称
				row.createCell(11).setCellValue(objToString(goodsInOut.getGoodsTitle()));
				//产品数量
				row.createCell(12).setCellValue(objToString(goodsInOut.getQuantity()));
				//产品价格
				row.createCell(13).setCellValue(objToString(goodsInOut.getPrice()));
				//产品总价
				row.createCell(14).setCellValue(objToString((goodsInOut.getPrice() * goodsInOut.getQuantity() *100)/100));
                if(goodsInOut.getIsDirect()){
                    //经销单价
                    row.createCell(15).setCellValue("NULL");
                    //经销总价
                    row.createCell(16).setCellValue("NULL");
                }else{
                    if(null != goodsInOut.getJxPrice()){
                        //经销单价
                        row.createCell(15).setCellValue(objToString(goodsInOut.getJxPrice()));
                        //经销总价
                        if(null != goodsInOut.getQuantity()){
                            row.createCell(16).setCellValue(objToString(goodsInOut.getJxPrice() * goodsInOut.getQuantity()));
                        }else{
                            row.createCell(16).setCellValue(objToString(0L));
                        }
                    }else{
                        row.createCell(15).setCellValue(objToString(0L));
                        row.createCell(16).setCellValue(objToString(0L));
                    }
                }
	        	//使用产品券数量
                row.createCell(17).setCellValue(objToString(goodsInOut.getProductCouponQuantity()));
                //预存款变更金额
                if(goodsInOut.getIsDirect()){
                    row.createCell(18).setCellValue(objToString((goodsInOut.getQuantity()-goodsInOut.getProductCouponQuantity())*goodsInOut.getPrice()));
                }else{
                    row.createCell(18).setCellValue(objToString((goodsInOut.getQuantity()-goodsInOut.getProductCouponQuantity())*goodsInOut.getJxPrice()));
                }
	          	//品牌
				row.createCell(19).setCellValue(objToString(goodsInOut.getBrandTitle()));
	    		//商品父分类
				row.createCell(20).setCellValue(objToString(goodsInOut.getGoodsParentTypeTitle()));
				//商品子分类
	        	row.createCell(21).setCellValue(objToString(goodsInOut.getGoodsTypeTitle()));
				//配送方式
	        	row.createCell(22).setCellValue(objToString(goodsInOut.getDeliverTypeTitle()));
				//中转仓
	            row.createCell(23).setCellValue(objToString(goodsInOut.getWhName()));
	    		//配送人员
	        	row.createCell(24).setCellValue(objToString(goodsInOut.getDeliverRealName()));
	        	//配送人员电话
	        	row.createCell(25).setCellValue(objToString(goodsInOut.getDeliverUsername()));
	        	//收货人姓名
                row.createCell(26).setCellValue(objToString(goodsInOut.getShippingName()));
                //收货人电话
                row.createCell(27).setCellValue(objToString(goodsInOut.getShippingPhone()));
                //收货人地址
                row.createCell(28).setCellValue(objToString(goodsInOut.getShippingAddress()));
	        	//订单备注
	        	row.createCell(29).setCellValue(objToString(goodsInOut.getRemark()));
			}
		}
        return wb;
	}
}
