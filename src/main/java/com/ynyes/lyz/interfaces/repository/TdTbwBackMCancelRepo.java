package com.ynyes.lyz.interfaces.repository;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.ynyes.lyz.interfaces.entity.TdTbwBackMCancel;

public interface TdTbwBackMCancelRepo extends PagingAndSortingRepository<TdTbwBackMCancel, Long> ,JpaSpecificationExecutor<TdTbwBackMCancel>
{
}
