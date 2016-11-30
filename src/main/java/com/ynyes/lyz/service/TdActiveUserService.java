package com.ynyes.lyz.service;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ynyes.lyz.entity.TdActiveUser;
import com.ynyes.lyz.repository.TdActiveUserRepo;
import com.ynyes.lyz.util.Utils;

/**
 * 活跃会员服务类
 * 
 * @author
 *
 */

@Service
@Transactional
public class TdActiveUserService {

	@Autowired
	TdActiveUserRepo repository;
	

	public List<TdActiveUser> queryDownList(Date begin, Date end, String cityName, String diySiteCode,
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

}
