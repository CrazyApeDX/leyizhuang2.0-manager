package com.ynyes.lyz.repository;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.ynyes.lyz.entity.TdActivityGiftList;

public interface TdActivityGiftListRepo
		extends PagingAndSortingRepository<TdActivityGiftList, Long>, JpaSpecificationExecutor<TdActivityGiftList> 
{

}

