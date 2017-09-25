package com.ynyes.lyz.service;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ynyes.lyz.entity.TdUserTurnRecord;
import com.ynyes.lyz.repository.TdUserTurnRecordRepo;

/**
 * 
 * @author Max
 *
 */

@Service
@Transactional
public class TdUserTurnRecordService {
	
	@Autowired
	private TdUserTurnRecordRepo repository;
	
	
	public TdUserTurnRecord save(TdUserTurnRecord e)
	{
		if(null == e)
		{
			return null ;
		}
		return repository.save(e);
	}
	
	public List<TdUserTurnRecord> save(List<TdUserTurnRecord> entities)
	{
		return (List<TdUserTurnRecord>) repository.save(entities);
	}
}
