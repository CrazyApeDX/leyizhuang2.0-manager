package com.ynyes.lyz.controller.management;

import java.util.ArrayList;
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
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ynyes.lyz.entity.TdBalanceLog;
import com.ynyes.lyz.entity.TdCity;
import com.ynyes.lyz.entity.TdDiySite;
import com.ynyes.lyz.entity.user.TdUser;
import com.ynyes.lyz.service.TdBalanceLogService;
import com.ynyes.lyz.service.TdDiySiteRoleService;
import com.ynyes.lyz.service.TdUserService;
import com.ynyes.lyz.util.SiteMagConstant;

/**
 * <p>标题：TdManagerBanalceLogController.java</p>
 * <p>描述：后台管理系统余额变更明细相关的控制器</p>
 * @author 作者：DengXiao
 * @version 版本：下午2:07:56
 */
@Controller
@RequestMapping(value = "/Verwalter/balance")
public class TdManagerBanalceLogController extends TdManagerBaseController {

	@Autowired
	private TdBalanceLogService tdBalanceLogService;
	@Autowired
	private TdDiySiteRoleService tdDiySiteRoleService;
	@Autowired   
	private TdUserService tdUserService;

	@RequestMapping(value = "/list")
	public String balanceList(Integer page, Integer size, String keywords,Long type, String __EVENTTARGET, String __EVENTARGUMENT,
			String __VIEWSTATE, HttpServletRequest req, ModelMap map,Long cityCode,Long diyCode,String startTime, String endTime) {

		String username = (String) req.getSession().getAttribute("manager");
		if (null == username) {
			return "redirect:/Verwalter/login";
		}
		//获取管理员管辖城市
    	List<TdCity> cityList= new ArrayList<TdCity>();
    	//获取管理员管辖门店
    	List<TdDiySite> diyList=new ArrayList<TdDiySite>(); 
    	
    	//管理员获取管辖的城市和门店
    	tdDiySiteRoleService.userRoleCityAndDiy(cityList, diyList, username);
    	
    	if (null != __EVENTTARGET) {
			if (__EVENTTARGET.equalsIgnoreCase("btnPage")) {
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
		}

		if (null != keywords) {
			keywords = keywords.trim();
		}
		
		//修改城市刷新门店列表
		if(cityCode!=null){
			//需要删除的diy
			List<TdDiySite> diyRemoveList=new ArrayList<TdDiySite>(); 
			for (TdDiySite tdDiySite : diyList) {
				if(!cityCode.equals(tdDiySite.getRegionId())){
					diyRemoveList.add(tdDiySite);
					if(tdDiySite.getId()==diyCode){
						diyCode=null;
					}
				}
			}
			diyList.removeAll(diyRemoveList);
		}
		//获取管辖门店id列表
		List<Long> roleDiyIds=new ArrayList<Long>();
		if(diyList!=null && diyList.size()>0){
			for (TdDiySite diy : diyList) {
				roleDiyIds.add(diy.getId());
			}
		}
		
//		Page<TdBalanceLog> balance_page = tdBalanceLogService.findAll(page, size);
		Page<TdBalanceLog> balance_page = tdBalanceLogService.searchList(keywords, roleDiyIds, type, page, size,cityCode,diyCode,startTime,endTime);
		map.addAttribute("balance_page", balance_page);
		
		map.addAttribute("page", page);
		map.addAttribute("size", size);
		map.addAttribute("keywords", keywords);
		map.addAttribute("type", type);
		map.addAttribute("cityList", cityList);
		map.addAttribute("diySiteList", diyList);
		map.addAttribute("cityCode", cityCode);
		map.addAttribute("diyCode", diyCode);
		map.addAttribute("startTime", startTime);
		map.addAttribute("endTime", endTime);
		
		return "/site_mag/balance_log_list";
	}
	
	/*
	 * 预存款记录报表
	 */
	@RequestMapping(value = "/downdata")
	@ResponseBody
	public String dowmData(HttpServletRequest req, ModelMap map, String begindata, String enddata,
			HttpServletResponse response, Long cityCode, Long diyCode,String keywords,Long type) {

		String username = (String) req.getSession().getAttribute("manager");
		if (null == username) {
			return "redirect:/Verwalter/login";
		}
		
		//获取管理员管辖城市
    	List<TdCity> cityList= new ArrayList<TdCity>();
    	//获取管理员管辖门店
    	List<TdDiySite> diyList=new ArrayList<TdDiySite>(); 
    	
    	//管理员获取管辖的城市和门店
    	tdDiySiteRoleService.userRoleCityAndDiy(cityList, diyList, username);
    	
    	//获取管辖门店id列表
    	List<Long> roleDiyIds=new ArrayList<Long>();
    	if(diyList!=null && diyList.size()>0){
    		for (TdDiySite diy : diyList) {
    			roleDiyIds.add(diy.getId());
    		}
    	}
    	//报表数据
    	List<TdBalanceLog> balanceList = tdBalanceLogService.searchList(keywords, roleDiyIds, type,cityCode,diyCode,begindata,enddata);
		
		// 第一步，创建一个webbook，对应一个Excel文件
		HSSFWorkbook wb = new HSSFWorkbook();
		// 第二步，在webbook中添加一个sheet,对应Excel文件中的sheet
		HSSFSheet sheet = wb.createSheet("领用记录报表");
		// 第三步，在sheet中添加表头第0行,注意老版本poi对Excel的行数列数有限制short
		// 列宽
		int[] widths = { 13, 18, 13, 13, 13, 15, 13, 11, 19, 11, 20, 25, 40, 13, 13, 15, 15 };
		sheetColumnWidth(sheet, widths);

		// 第四步，创建单元格，并设置值表头 设置表头居中
		HSSFCellStyle style = wb.createCellStyle();
		style.setAlignment(HSSFCellStyle.ALIGN_CENTER); // 创建一个居中格式
		style.setWrapText(true);

		// 优惠券名称、金额、领卷时间、领用用户、是否使用、使用的时间、使用订单号
		HSSFRow row = sheet.createRow((int) 0);

		String[] cellValues = { "归属城市","归属门店","用户名", "用户姓名", "类型","金额类型", "金额变化", "变更后余额", "变更时间","到账时间","商户订单号", "是否成功", 
				"操作描述", "涉及单号", "变更后可提现余额", "变更后不可提现余额", "变更后余额总额"};
		cellDates(cellValues, style, row);

		// 第五步，设置值

//		// 获取所有的会员
		List<TdUser> userList = tdUserService.findByUserTypeOrderByIdDesc(0L);
		// 存放会员信息的map
		Map<String, Object> userMap = new HashMap<String, Object>();
		if (userList != null && userList.size() > 0) {
			for (TdUser tdUser : userList) {
				userMap.put(tdUser.getUsername() + "name", tdUser.getRealName());
				userMap.put(tdUser.getUsername() + "diyName", tdUser.getDiyName());
				userMap.put(tdUser.getUsername() + "cityName", tdUser.getCityName());
			}
		}

		Integer i = 0;
		for (TdBalanceLog log : balanceList) {
			row = sheet.createRow((int) i + 1);
			String strfu="";
			if (null != userMap.get(log.getUsername() + "cityName")) {// 归属城市
				row.createCell(0).setCellValue(userMap.get(log.getUsername() + "cityName").toString());
			}
			if (null != userMap.get(log.getUsername() + "diyName")) {// 归属门店
				row.createCell(1).setCellValue(userMap.get(log.getUsername() + "diyName").toString());
			}
			if (null != log.getUsername()) {// 用户名
				row.createCell(2).setCellValue(log.getUsername());
			}
			if (null != userMap.get(log.getUsername() + "name")) {// 用户姓名
				row.createCell(3).setCellValue(userMap.get(log.getUsername() + "name").toString());
			}
			if (null != log.getType()) {// 类型
				if(log.getType()==1L || log.getType()==3L){
					strfu="-";
				}
				row.createCell(4).setCellValue(log.getTypeName());
			}
			if (null != log.getBalanceType()) {// 预存款类型
				row.createCell(5).setCellValue(log.getBalanceTypeName()+"预存款");
			}
			if (null != log.getMoney()) {// 变更金额
				row.createCell(6).setCellValue(strfu+log.getMoney());
			}
			if (null != log.getBalance()) {// 变更后余额
				row.createCell(7).setCellValue(log.getBalance());
			}
			if (null != log.getCreateTime()) {// 变更时间
				row.createCell(8).setCellValue(log.getCreateTime().toString());
			}
			if(null != log.getTransferTime()){//到账时间
				row.createCell(9).setCellValue(log.getTransferTime().toString());
			}else{
				row.createCell(9).setCellValue("NULL");
			}
			if(null != log.getUserOrderNumber()){//商户订单号
				row.createCell(10).setCellValue(log.getUserOrderNumber().toString());
			}else{
				row.createCell(10).setCellValue("NULL");
			}
			if (null != log.getIsSuccess()) {// 是否成功
				row.createCell(11).setCellValue(log.getIsSuccess()?"是":"否");
			}
			if (null != log.getReason()) {// 操作描述
				row.createCell(12).setCellValue(log.getReason());
			}
			if (null != log.getOrderNumber()) {// 涉及单号
				row.createCell(13).setCellValue(log.getOrderNumber());
			} 
			if (null != log.getCashLeft()) {// 可提现余额剩余
				row.createCell(14).setCellValue(log.getCashLeft());
			}
			if (null != log.getUnCashLeft()) {// 不可提现余额剩余
				row.createCell(15).setCellValue(log.getUnCashLeft());
			}
			if (null != log.getAllLeft()) {// 总额剩余
				row.createCell(16).setCellValue(log.getAllLeft());
			}
			i++;
		}

		String exportAllUrl = SiteMagConstant.backupPath;
		download(wb, exportAllUrl, response, "预存款变更明细");
		return "";
	}
}
