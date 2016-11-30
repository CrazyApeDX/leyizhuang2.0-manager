package com.ynyes.lyz.service;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ynyes.lyz.entity.GarmentFranchisorReport;
import com.ynyes.lyz.repository.TdGarmentFranchisorReportRepo;
import com.ynyes.lyz.util.Utils;

/**
 * 加盟商对账报表 服务层
 * 
 * @author
 *
 */

@Service
@Transactional
public class TdGarmentFranchisorReportService {

	@Autowired
	TdGarmentFranchisorReportRepo repository;
	

	public List<GarmentFranchisorReport> queryDownList(Date begin, Date end, String cityName, String diySiteCode,
			List<String> roleDiyIds) {

			// 判断空值
			if (null == begin || "".equals(begin)) {
				begin = Utils.getSysStartDate();
			}
			if (null == end || "".equals(end)) {
				end = new Date();
			}
			if (StringUtils.isBlank(cityName)) {
				cityName = "%";
			}
			if (StringUtils.isBlank(diySiteCode)) {
				diySiteCode = "%";
			}
			if (null == roleDiyIds  || roleDiyIds.size() == 0) {
				roleDiyIds.add("0");
			}
			return repository.queryDownList(begin, end, cityName, diySiteCode, roleDiyIds);

		
	}

}
