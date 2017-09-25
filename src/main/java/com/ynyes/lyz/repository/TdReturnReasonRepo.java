package com.ynyes.lyz.repository;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.ynyes.lyz.entity.TdReturnReason;

/**
 * <p>标题：TdReturnReasonRepo.java</p>
 * <p>描述：退货原因仓库类</p>
 * <p>公司：http://www.ynsite.com</p>
 * @author 作者：DengXiao
 * @version 版本：上午10:31:15
 */
public interface TdReturnReasonRepo
		extends PagingAndSortingRepository<TdReturnReason, Long>, JpaSpecificationExecutor<TdReturnReason> {

}
