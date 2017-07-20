package com.ynyes.fitment.web.controller.management;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ynyes.fitment.foundation.entity.FitCreditChangeLog;
import com.ynyes.fitment.foundation.service.FitCreditChangeLogService;
import com.ynyes.lyz.controller.management.TdManagerBaseController;
import com.ynyes.lyz.util.SiteMagConstant;

@Controller
@RequestMapping(value = "/Verwalter/companyChange")
public class FitManagermentCompanyChangeController extends TdManagerBaseController{

	@Autowired
	private FitCreditChangeLogService fitCreditChangeLogService;
	
	@RequestMapping(value = "/downdata")
	@ResponseBody
	public String dowmData(HttpServletRequest req, ModelMap map, String startTime, String endTime,
			HttpServletResponse response, String city, String companyCode,String keywords,String type) {

		String username = (String) req.getSession().getAttribute("manager");
		if (null == username) {
			return "redirect:/Verwalter/login";
		}
		
    	//报表数据
		List<FitCreditChangeLog> list = this.fitCreditChangeLogService.queryDownList(startTime, endTime, city, companyCode,
				keywords, type);
		
		// 第一步，创建一个webbook，对应一个Excel文件
		HSSFWorkbook wb = new HSSFWorkbook();
		// 第二步，在webbook中添加一个sheet,对应Excel文件中的sheet
		HSSFSheet sheet = wb.createSheet("APP装饰公司账户变更明细报表");
		// 第三步，在sheet中添加表头第0行,注意老版本poi对Excel的行数列数有限制short
		// 列宽
		int[] widths = { 13, 18, 13, 13, 13, 15, 13, 11, 19, 11, 20, 25, 40, 13 };
		sheetColumnWidth(sheet, widths);

		// 第四步，创建单元格，并设置值表头 设置表头居中
		HSSFCellStyle style = wb.createCellStyle();
		style.setAlignment(HSSFCellStyle.ALIGN_CENTER); // 创建一个居中格式
		style.setWrapText(true);

		HSSFRow row = sheet.createRow((int) 0);

		String[] cellValues = { "城市","装饰公司名称","装饰公司代码", "变更类型", "变更时间",
				"到账日期", "对应单号", "变更金额", "信用额度余额","现金返利余额","总余额", 
				"操作描述", "变更人", "备注"};
		cellDates(cellValues, style, row);

		// 第五步，设置值


		Integer i = 0;
		for (FitCreditChangeLog log : list) {
			row = sheet.createRow((int) i + 1);
			String strfu="";
			if (null != log.getCity()) {// 城市
				row.createCell(0).setCellValue(log.getCity());
			}
			if (null != log.getCompanyTitle()) {// 装饰公司名称
				row.createCell(1).setCellValue(log.getCompanyTitle());
			}
			if (null != log.getCompanyCode()) {// 装饰公司代码
				row.createCell(2).setCellValue(log.getCompanyCode());
			}
			if (null != log.getType()) {// 变更类型
				row.createCell(3).setCellValue(log.getType());
			}
			if (null != log.getChangeTime()) {// 变更时间
				row.createCell(4).setCellValue(log.getChangeTime().toString());
			}
			if (null != log.getArrivalTime()) {// 到账日期
				row.createCell(5).setCellValue(log.getArrivalTime().toString());
			}
			if (null != log.getReferenceNumber()) {// 对应单号
				row.createCell(6).setCellValue(strfu+log.getReferenceNumber());
			}
			if (null != log.getMoney()) {// 变更金额
				row.createCell(7).setCellValue(log.getMoney());
			}
			if (null != log.getAfterChange()) {// 信用额度余额
				row.createCell(8).setCellValue(log.getAfterChange().toString());
			}
			if(null != log.getAfterChangePromotion()){//现金返利余额
				row.createCell(9).setCellValue(log.getAfterChangePromotion().toString());
			}
			if(null != log.getTotalBalance()){//总余额
				row.createCell(10).setCellValue(log.getTotalBalance().toString());
			}
			if (null != log.getRemark()) {// 操作描述
				row.createCell(11).setCellValue(log.getRemark());
			}
			if (null != log.getOperatorUserName()) {// 变更人
				row.createCell(12).setCellValue(log.getOperatorUserName());
			}
			if (null != log.getWrittenRemarks()) {// 备注
				row.createCell(13).setCellValue(log.getWrittenRemarks());
			} 
			i++;
		}

		String exportAllUrl = SiteMagConstant.backupPath;
		download(wb, exportAllUrl, response, "预存款变更明细");
		return "";
	}
}
