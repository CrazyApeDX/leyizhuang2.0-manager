package com.ynyes.lyz.service;

import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ynyes.lyz.entity.delivery.TdOrderDeliveryFeeDetail;
import com.ynyes.lyz.repository.TdOrderDeliveryFeeDetailRepo;
import com.ynyes.lyz.util.Utils;

/**
 * @author yanle
 * 订单运费服务层
 *
 */
@Service
@Transactional
public class TdOrderDeliveryFeeDetailService {

	@Autowired
	private TdOrderDeliveryFeeDetailRepo repository;

	public TdOrderDeliveryFeeDetail save(TdOrderDeliveryFeeDetail detail) {
		if (null == detail) {
			return null;
		}
		return repository.save(detail);
	}

	// public TdUser saveWithOutBalance(TdUser user)
	// {
	// return repository.saveWithOutBalance(user);
	// }

	public void delete(Long id) {
		if (null != id) {
			repository.delete(id);
		}
	}

	public TdOrderDeliveryFeeDetail findOne(Long id) {
		if (null == id) {
			return null;
		}
		return repository.findOne(id);
	}

	

	public List<TdOrderDeliveryFeeDetail> findAll() {
		return (List<TdOrderDeliveryFeeDetail>) repository.findAll();
	}

	public TdOrderDeliveryFeeDetail findByMainOrderNumber(String mainOrderNumber) {
		if(null == mainOrderNumber){
			return null;
		}
		return repository.findByMainOrderNumber(mainOrderNumber);
	}

	public List<TdOrderDeliveryFeeDetail> queryDownList(Date begin, Date end, String cityName, String diySiteCode,
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
		return repository.queryDownList(begin, end, cityName, diySiteCode, roleDiyIds);
	}

	


}
