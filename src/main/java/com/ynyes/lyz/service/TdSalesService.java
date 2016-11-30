package com.ynyes.lyz.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ynyes.lyz.entity.TdSales;
import com.ynyes.lyz.repository.TdSalesRepo;
import com.ynyes.lyz.util.Utils;

/**
 * 欠款报表 服务类
 * 
 * @author
 *
 */

@Service
@Transactional
public class TdSalesService {

	@Autowired
	TdSalesRepo repository;

	public List<TdSales> queryDownList(Date begin, Date end, String cityName, String diySiteCode,
			List<String> roleDiyIds) {

		// 判断空值
		if (begin == null) {
			begin = Utils.getSysStartDate();
		}
		if (end == null) {
			end = new Date();
		}
		if (StringUtils.isBlank(cityName)) {
			cityName = "%";
		}
		if (StringUtils.isBlank(diySiteCode)) {
			diySiteCode = "%";
		}
		if (roleDiyIds == null || roleDiyIds.size() == 0) {
			roleDiyIds.add("0");
		}
		return repository.queryDownList(begin, end, cityName, diySiteCode, roleDiyIds);

	}

	

	public List<TdSales> querySalesList() {
		String beginDateString = "2016-09-01 00:00:00";
		Date beginDate = stringToDate(beginDateString, null);
		Date endDate = new Date();
		List<TdSales> list = repository.getSalesList(beginDate, endDate);
		return list;

	}

	/**
	 * 字符串转日期
	 * 
	 * @param time
	 * @param dateFormat
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

}
