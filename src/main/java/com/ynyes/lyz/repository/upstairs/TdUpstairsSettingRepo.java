package com.ynyes.lyz.repository.upstairs;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.ynyes.lyz.entity.upstairs.TdUpstairsSetting;

@Repository
public interface TdUpstairsSettingRepo
		extends PagingAndSortingRepository<TdUpstairsSetting, Long>, JpaSpecificationExecutor<TdUpstairsSetting> {

	TdUpstairsSetting findTopBy();
}
