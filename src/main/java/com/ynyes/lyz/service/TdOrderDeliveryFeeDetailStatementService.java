package com.ynyes.lyz.service;

import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ynyes.lyz.entity.delivery.TdOrderDeliveryFeeDetailStatement;
import com.ynyes.lyz.repository.TdOrderDeliveryFeeDetailStatementRepo;
import com.ynyes.lyz.util.Utils;

/**
 * @author yanle
 * 运费报表服务层
 *
 */
@Service
@Transactional
public class TdOrderDeliveryFeeDetailStatementService {

	@Autowired
	private TdOrderDeliveryFeeDetailStatementRepo repository;

	public List<TdOrderDeliveryFeeDetailStatement> queryDownOrderList(Date begin, Date end, String cityName, String diySiteCode,
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
		if (null == roleDiyIds || roleDiyIds.size() == 0) {
			roleDiyIds.add("0");
		}
		return repository.queryDownOrderList(begin, end, cityName, diySiteCode, roleDiyIds);
	}



}
