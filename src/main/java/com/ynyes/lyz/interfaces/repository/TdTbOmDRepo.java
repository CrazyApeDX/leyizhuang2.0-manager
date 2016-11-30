package com.ynyes.lyz.interfaces.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.ynyes.lyz.interfaces.entity.TdTbOmD;

public interface TdTbOmDRepo extends PagingAndSortingRepository<TdTbOmD, Long>,JpaSpecificationExecutor<TdTbOmD>
{
	List<TdTbOmD> findByCOmNo(String COmNo);
}
