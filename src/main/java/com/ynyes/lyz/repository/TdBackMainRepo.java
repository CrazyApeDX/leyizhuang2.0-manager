package com.ynyes.lyz.repository;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.ynyes.lyz.entity.TdBackMain;

/**
 * TdBackMain  实体数据库操作接口
 * 
 * @author Sharon
 *
 */

public interface TdBackMainRepo extends PagingAndSortingRepository<TdBackMain, Long>, JpaSpecificationExecutor<TdBackMain> {
	
		TdBackMain findByWhNo(String whNo);
		
		TdBackMain findByRecNo(String c_rec_no);
}
