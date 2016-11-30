package com.ynyes.lyz.interfaces.repository;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.ynyes.lyz.interfaces.entity.TdTbwWasted;

public interface TdTbwWastedRepo extends PagingAndSortingRepository<TdTbwWasted, Long> ,JpaSpecificationExecutor<TdTbwWasted>
{
	TdTbwWasted findByCWasteNo(String cWasteNo);
	
	TdTbwWasted findByCWasteNoAndCWasteId(String cWasteNo,Long cWasteId);
}
