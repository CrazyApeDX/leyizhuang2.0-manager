package com.ynyes.lyz.controller.management;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URLEncoder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ynyes.lyz.service.TdSalesForActiveUserService;

@Controller
@RequestMapping(value="/test")
public class testActiveUser {
	
	
	
	@Autowired
	private  TdSalesForActiveUserService activeSaleService;
	
	
	@RequestMapping(value="/active")
	public void testAcitveUser() {
		System.out.println("开始插入数据");
		activeSaleService.insertDataToSales();
		System.out.println("数据插入成功");
	}
	
	@RequestMapping(value="/download")
	public void exportExcel(HttpServletResponse response,HttpServletRequest request) {
		
		//File newFile = createNewFile();
		File newFile = new File("d:/","sale"+String.valueOf(System.currentTimeMillis())+".xls");
		

		// 新文件写入数据，并下载*****************************************************
		InputStream is = null;
		HSSFWorkbook workbook = new HSSFWorkbook();
		HSSFSheet sheet = workbook.createSheet("第1页");

		if (sheet != null) {
			try {
				// 写数据
				FileOutputStream fos = new FileOutputStream(newFile);
				HSSFRow row = sheet.createRow(0);
				HSSFCell cell = row.createCell(0);
				System.out.println(cell.getStringCellValue());
				cell.setCellValue("奶奶个嘴儿");
				workbook.write(fos);
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
				String newName = URLEncoder.encode("销量报表" + System.currentTimeMillis() + ".xls", "UTF-8");
				response.addHeader("Content-Disposition", "attachment;filename=\"" + newName + "\"");
				response.addHeader("Content-Length", "" + newFile.length());
				toClient.write(buffer);
				toClient.flush();
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				try {
					if (null != is) {
						is.close();
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		// 删除创建的新文件
		this.deleteFile(newFile);
	}

	/**
	 * 复制文件
	 * 
	 * @param s
	 *            源文件
	 * @param t
	 *            复制到的新文件
	 */

	public void fileChannelCopy(File s, File t) {
		try {
			InputStream in = null;
			OutputStream out = null;
			try {
				in = new BufferedInputStream(new FileInputStream(s), 1024);
				out = new BufferedOutputStream(new FileOutputStream(t), 1024);
				byte[] buffer = new byte[1024];
				int len;
				while ((len = in.read(buffer)) != -1) {
					out.write(buffer, 0, len);
				}
			} finally {
				if (null != in) {
					in.close();
				}
				if (null != out) {
					out.close();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 读取excel模板，并复制到新文件中供写入和下载
	 * 
	 * @return
	 */
	

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
