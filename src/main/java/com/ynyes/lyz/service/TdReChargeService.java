package com.ynyes.lyz.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ynyes.lyz.entity.TdRecharge;
import com.ynyes.lyz.repository.TdRechargeRepo;

/**
 * 充值单服务类
 * 
 * @author 作者：DengXiao
 * @version 版本：2016年5月30日上午10:07:09
 */
@Service
@Transactional
public class TdReChargeService {

	@Autowired
	private TdRechargeRepo repository;

	public TdRecharge save(TdRecharge e) {
		if (null == e) {
			return null;
		}
		return repository.save(e);
	}

	public void delete(Long id) {
		if (null != id) {
			repository.delete(id);
		}
	}

	public TdRecharge findOne(Long id) {
		if (null == id) {
			return null;
		}
		return repository.findOne(id);
	}

	public List<TdRecharge> findAll() {
		return (List<TdRecharge>) repository.findAll();
	}

	/**
	 * 根据充值单号查找指定的充值单
	 * 
	 * @author 作者：DengXiao
	 * @version 版本：20162016年5月30日上午10:06:33
	 */
	public TdRecharge findByNumber(String number) {
		if (null == number) {
			return null;
		}
		return repository.findByNumber(number);
	}
}
