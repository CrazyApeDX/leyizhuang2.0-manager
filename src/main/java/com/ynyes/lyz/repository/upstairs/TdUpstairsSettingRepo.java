package com.ynyes.lyz.repository.upstairs;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.ynyes.lyz.entity.upstairs.TdUpstairsSetting;

public interface TdUpstairsSettingRepo
		extends PagingAndSortingRepository<TdUpstairsSetting, Long>, JpaSpecificationExecutor<TdUpstairsSetting> {

	TdUpstairsSetting findTopOne();
}
