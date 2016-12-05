package com.ynyes.lyz.controller.management;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import com.ibm.icu.util.Calendar;

public class TdManagerBaseController {

	/**
	 * 字符串转换时间默认格式yyyy-MM-dd HH:mm:ss
	 * 
	 * @param time
	 *            需要转换的时间
	 * @param dateFormat
	 *            时间格式
	 * @return
	 */
	public Date stringToDate(String time, String dateFormat) {
		if (null == dateFormat || "".equals(dateFormat)) {
			dateFormat = "yyyy-MM-dd HH:mm:ss";
		}
		SimpleDateFormat sdf = new SimpleDateFormat(dateFormat);
		Date date = null;
		if (StringUtils.isNotBlank(time)) {
			try {
				date = sdf.parse(time);
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}
		return date;
	}
	
	/**
	 * 时间转换字符串默认格式yyyy-MM-dd HH:mm:ss
	 * 
	 * @param time
	 *            需要转换的时间
	 * @param dateFormat
	 *            时间格式
	 * @return
	 */
	public String dateToString(Date time, String dateFormat) {
		if (null == dateFormat || "".equals(dateFormat)) {
			dateFormat = "yyyy-MM-dd HH:mm:ss";
		}
		SimpleDateFormat sdf = new SimpleDateFormat(dateFormat);
		String dateStr = null;
		if (null != time) {
			try {
				dateStr = sdf.format(time);
			} catch (Exception e) {
				System.out.println(e);
			}
		}
		return dateStr;
	}
	
	/**
	 * 设置列宽
	 * @param sheet
	 * @param widths
	 */
	public void sheetColumnWidth(HSSFSheet sheet,int[] widths){
		for (int i = 0; i < widths.length; i++) {
			sheet.setColumnWidth(i , widths[i]*256);
		}
		
	}

	/**
	 * 添加导出列名
	 * 
	 * @param CellValues
	 *            列名数组
	 * @param style
	 *            样式
	 * @param style
	 *            当前行
	 * @return
	 */
	public void cellDates(String[] cellValues, HSSFCellStyle style,
			HSSFRow row) {
		HSSFCell cell = null;
		if (null != cellValues && cellValues.length > 0) {
			for (int i = 0; i < cellValues.length; i++) {
				cell = row.createCell(i);
				cell.setCellValue(cellValues[i]);
				cell.setCellStyle(style);
			}
		}
	}

	/**
	 * @author lc
	 * @注释：下载
	 */
	public Boolean download(HSSFWorkbook wb, String exportUrl,HttpServletResponse resp,String fileName) { 
		String filename="table";
		try {
			filename = new String(fileName.getBytes("GBK"), "ISO-8859-1");
		} catch (UnsupportedEncodingException e1) {
			System.out.println("下载文件名格式转换错误！");
		}
		try {
			OutputStream os;
			try {
				os = resp.getOutputStream();
				try {
					resp.reset();
					resp.setHeader("Content-Disposition",
							"attachment; filename=" + filename +".xls");
					resp.setContentType("application/octet-stream; charset=utf-8");
					wb.write(os);
					os.flush();
				} finally {
					if (os != null) {
						os.close();
					}
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return true;
	}
	
	
//	/**
//     * 异常处理
//     * @param r
//     * @param e
//     * @return
//     */
//    @ExceptionHandler
//    public String exception(HttpServletRequest r, Exception e) {
//        String view = "exception";
//        r.setAttribute("exception", e);
//        String xRequestedWith = r.getHeader("X-Requested-With");
//        if (!StringUtils.isEmpty(xRequestedWith)) {
//            view = "exceptionAjax";
//        }
//        return view;
//    }
	
	/**
	 * 获取本月第一天 报表默认开始时间
	 */
	public Date getStartTime(){  
        Calendar todayStart = Calendar.getInstance();//获取当前日期
        
        todayStart.set(Calendar.MONTH, todayStart.get(Calendar.MONTH));
        todayStart.set(Calendar.DAY_OF_MONTH,1);//设置为1号,当前日期既为本月第一天 
        todayStart.set(Calendar.HOUR_OF_DAY, 1);  //设置时分秒
        todayStart.set(Calendar.MINUTE, 0);  
        todayStart.set(Calendar.SECOND, 0);  
        todayStart.set(Calendar.MILLISECOND, 0);  
        return todayStart.getTime();  
    }  
      
	/**
	 * 获取当前时间  报表默认结算时间
	 */
    public Date getEndTime(){  
        Calendar todayEnd = Calendar.getInstance();  
//        todayEnd.set(Calendar.HOUR, 23);  
//        todayEnd.set(Calendar.MINUTE, 59);  
//        todayEnd.set(Calendar.SECOND, 59);  
//        todayEnd.set(Calendar.MILLISECOND, 999);  
        return todayEnd.getTime();  
    }

    /**
     * 根据订单编号 返回订单状态
     * @param status 订单编号
     * @return
     */
	public String orderStatus(Long status)
	{
		// 订单状态 1:待审核 2:待付款 3:待出库 4:待签收 5: 待评价 6: 已完成 7: 已取消 8:用户删除 9:退货中 10：退货确认
		// 11：退货取消 12 : 退货完成
		if(status==null){
			return "未知";
		}
		Integer integerStatus = status.intValue();
		switch (integerStatus) {
			case 1:
				return "待审核";
			case 2:
				return "待付款";
			case 3:
				return "待出库";
			case 4:
				return "待收货";
			case 5:
				return "待评价";
			case 6:
				return "已完成";
			case 7:
				return "已取消";
			case 8:
				return "用户删除";
			case 9:
				return "退货中";
			case 10:
				return "退货确认";
			case 11:
				return "退货取消";
			case 12:
				return "退货完成";
				
			default:
				return "未知";
		}
	}
	
	/**
	 * @author lc
	 * @注释：下载
	 */
	public Boolean downloadForSale(HSSFWorkbook wb, String exportUrl,HttpServletResponse response,String fileName) { 
		File newFile = new File("/mnt/root/assets","sale"+String.valueOf(System.currentTimeMillis())+".xls");
		//File newFile = new File("e:/report","sale"+String.valueOf(System.currentTimeMillis())+".xls");
		// 新文件写入数据，并下载*****************************************************
				if (wb != null) {
					try {
						// 写数据
						FileOutputStream fos = new FileOutputStream(newFile);
						wb.write(fos);
						fos.flush();
						fos.close();

						// 下载
						InputStream fis = new BufferedInputStream(new FileInputStream(newFile));
						//HttpServletResponse response = this.getResponse();
						byte[] buffer = new byte[fis.available()];
						fis.read(buffer);
						fis.close();
						response.reset();
						response.setContentType("text/html;charset=UTF-8");
						OutputStream toClient = new BufferedOutputStream(response.getOutputStream());
						response.setContentType("application/x-msdownload");
						String newName = URLEncoder.encode(fileName + ".xls", "UTF-8");
						response.addHeader("Content-Disposition", "attachment;filename=\"" + newName + "\"");
						response.addHeader("Content-Length", "" + newFile.length());
						toClient.write(buffer);
						toClient.flush();
					} catch (Exception e) {
						e.printStackTrace();
					} 
				}
				// 删除创建的新文件
				this.deleteFile(newFile);
		return true;
	}
	
	/**
	 * 下载成功后删除
	 * 
	 * @param files
	 */
	private void deleteFile(File... files) {
		for (File file : files) {
			if (file.exists()) {
				file.delete();
			}
		}
	}
}
