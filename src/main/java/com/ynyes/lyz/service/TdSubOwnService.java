package com.ynyes.lyz.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ynyes.lyz.entity.TdSubOwn;
import com.ynyes.lyz.repository.TdSubOwnRepo;

/**
 * TdGoodsINOutRepo 服务类
 * 
 * @author
 *
 */

@Service
@Transactional
public class TdSubOwnService {
	
	@Autowired
	TdSubOwnRepo repository;
	
	public List<TdSubOwn> queryDownListDetail(String orderNumber) {
		
		return repository.queryDownListDetail(orderNumber);

}
	
	
	
	
}
