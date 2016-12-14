package com.ynyes.lyz.service;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ynyes.lyz.entity.upstairs.TdUpstairsSetting;
import com.ynyes.lyz.repository.upstairs.TdUpstairsSettingRepo;

@Service
@Transactional
public class TdUpstairsSettingService {

	@Autowired
	private TdUpstairsSettingRepo repository;
	
	public TdUpstairsSetting save(TdUpstairsSetting e) {
		if (null == e) {
			return null;
		}
		return repository.save(e);
	}
	
	public TdUpstairsSetting findTopOne() {
		return repository.findTopOne();
	}
}
