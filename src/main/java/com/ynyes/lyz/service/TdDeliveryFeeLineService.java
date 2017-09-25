package com.ynyes.lyz.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ynyes.lyz.entity.delivery.TdDeliveryFeeLine;
import com.ynyes.lyz.repository.delivery.TdDeliveryFeeLineRepository;

/**
 * <p>
 * 标题：TdDeliveryFeeLineService.java
 * </p>
 * <p>
 * 描述：
 * </p>
 * 
 * @author 作者：CrazyDX
 * @version 版本：2016年11月17日上午10:12:31
 */
@Service
@Transactional
public class TdDeliveryFeeLineService {

	@Autowired
	private TdDeliveryFeeLineRepository repository;

	public TdDeliveryFeeLine save(TdDeliveryFeeLine line) {
		if (null == line) {
			return null;
		}
		return repository.save(line);
	}

	public void delete(Long id) {
		if (null != id) {
			repository.delete(id);
		}
	}

	public TdDeliveryFeeLine findOne(Long id) {
		if (null == id) {
			return null;
		}
		return repository.findOne(id);
	}

	public List<TdDeliveryFeeLine> findAll() {
		return (List<TdDeliveryFeeLine>) repository.findAll();
	}

	public List<TdDeliveryFeeLine> findByHeadId(Long headId) {
		if (null == headId) {
			return null;
		}
		return repository.findByHeadId(headId);
	}

	public Page<TdDeliveryFeeLine> findByHeadId(Long headId, int page, int size) {
		if (null == headId) {
			return null;
		}
		PageRequest pageRequest = new PageRequest(page, size);
		return repository.findByHeadId(headId, pageRequest);
	}

	public TdDeliveryFeeLine findBySobIdAndGoodsIdAndNumber(Long sobId, Long goodsId, Long number) {
		if (null == sobId || null == goodsId || null == number) {
			return null;
		}
		return repository.findBySobIdAndGoodsIdAndNumber(sobId, goodsId, number);
	}
}
