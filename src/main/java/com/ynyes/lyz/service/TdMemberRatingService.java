package com.ynyes.lyz.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ynyes.lyz.entity.TdMemberRating;
import com.ynyes.lyz.repository.TdMemberRatingRepo;

@Service
@Transactional
public class TdMemberRatingService {
	
	@Autowired
	TdMemberRatingRepo repository;
	
	public TdMemberRating findById(Long id) {
		if (id == null) {
			return null;
		}
		return repository.findById(id);
	}
	

}
