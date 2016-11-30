package com.ynyes.lyz.repository;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.ynyes.lyz.entity.TdRecharge;

/**
 * 充值单仓库类
 * 
 * @author 作者：DengXiao
 * @version 版本：2016年5月30日上午10:04:29
 */
public interface TdRechargeRepo
		extends PagingAndSortingRepository<TdRecharge, Long>, JpaSpecificationExecutor<TdRecharge> {

	/**
	 * 根据充值单号查找指定的充值单
	 * 
	 * @author 作者：DengXiao
	 * @version 版本：20162016年5月30日上午10:06:33
	 */
	TdRecharge findByNumber(String number);
}
