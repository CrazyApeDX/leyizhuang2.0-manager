package com.ynyes.lyz.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import com.ynyes.lyz.entity.TdReturnReason;
import com.ynyes.lyz.repository.TdReturnReasonRepo;

/**
 * <p>标题：TdReturnReasonService.java</p>
 * <p>描述：退货原因服务类</p>
 * <p>公司：http://www.ynsite.com</p>
 * @author 作者：DengXiao
 * @version 版本：上午10:33:04
 */
@Service
@Transactional
public class TdReturnReasonService {

	@Autowired
	private TdReturnReasonRepo repository;
	
	public TdReturnReason save(TdReturnReason e) {
		if (null == e) {
			return null;
		}
		return repository.save(e);
	}
	
	public void delete(Long id) {
		if (null != id) {
			repository.delete(id);
		}
	}
	
	public TdReturnReason findOne(Long id) {
		if (null == id) {
			return null;
		}
		return repository.findOne(id);
	}
	
	public List<TdReturnReason> findAll() {
		return (List<TdReturnReason>) repository.findAll(new Sort(Direction.ASC, "sortId"));
	}
	
	public Page<TdReturnReason> findAll(int page, int size){
		PageRequest pageRequest = new PageRequest(page, size, new Sort(Direction.ASC, "sortId"));
		return repository.findAll(pageRequest);
	}
}
