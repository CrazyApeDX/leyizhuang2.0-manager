package com.ynyes.lyz.interfaces.repository;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.ynyes.lyz.interfaces.entity.TdTbOmM;

public interface TdTbOmMRepo extends PagingAndSortingRepository<TdTbOmM, Long> ,JpaSpecificationExecutor<TdTbOmM>
{
	TdTbOmM findByCOmNo(String cOmNo);
}
