package com.ynyes.lyz.interfaces.repository;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.ynyes.lyz.interfaces.entity.TdTbwBackRecM;

public interface TdTbwBackRecMRepo extends PagingAndSortingRepository<TdTbwBackRecM, Long> ,JpaSpecificationExecutor<TdTbwBackRecM>
{
	TdTbwBackRecM findByCRecNo(String cRecNo);
}
