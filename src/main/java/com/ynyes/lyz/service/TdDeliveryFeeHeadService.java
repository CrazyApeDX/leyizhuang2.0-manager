package com.ynyes.lyz.service;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ynyes.lyz.entity.delivery.TdDeliveryFeeHead;
import com.ynyes.lyz.entity.delivery.TdDeliveryFeeLine;
import com.ynyes.lyz.repository.delivery.TdDeliveryFeeHeadRepository;
import com.ynyes.lyz.repository.delivery.TdDeliveryFeeLineRepository;

/**
 * <p>
 * 标题：TdDeliveryFeeHeadService.java
 * </p>
 * <p>
 * 描述：
 * </p>
 * 
 * @author 作者：CrazyDX
 * @version 版本：2016年11月17日上午10:05:08
 */
@Service
@Transactional
public class TdDeliveryFeeHeadService {

	@Autowired
	private TdDeliveryFeeHeadRepository repository;

	@Autowired
	private TdDeliveryFeeLineRepository lineRepo;

	public TdDeliveryFeeHead save(TdDeliveryFeeHead head) {
		if (null == head) {
			return null;
		}
		return repository.save(head);
	}

	public void delete(Long id) {
		if (null != id) {
			List<TdDeliveryFeeLine> lines = lineRepo.findByHeadId(id);
			lineRepo.delete(lines);
			repository.delete(id);
		}
	}

	public TdDeliveryFeeHead findOne(Long id) {
		if (null == id) {
			return null;
		}
		return repository.findOne(id);
	}

	public List<TdDeliveryFeeHead> findAll() {
		return (List<TdDeliveryFeeHead>) repository.findAll();
	}

	public TdDeliveryFeeHead findBySobIdAndGoodsId(Long sobId, Long goodsId) {
		if (null == sobId || null == goodsId) {
			return null;
		}
		return repository.findBySobIdAndGoodsId(sobId, goodsId);
	}

	public Integer countBySobIdAndGoodsId(Long sobId, Long goodsId) {
		if (null == sobId || null == goodsId) {
			return null;
		}
		return repository.countBySobIdAndGoodsId(sobId, goodsId);
	}

	public TdDeliveryFeeHead findBySobIdAndGoodsSku(Long sobId, String goodsSku) {
		if (null == sobId || null == goodsSku) {
			return null;
		}
		return repository.findBySobIdAndGoodsSku(sobId, goodsSku);
	}

	public List<TdDeliveryFeeHead> findBySobId(Long sobId) {
		if (null == sobId) {
			return null;
		}
		return repository.findBySobId(sobId);
	}

	public Page<TdDeliveryFeeHead> findBySobId(Long sobId, int page, int size) {
		if (null == sobId) {
			return null;
		}
		PageRequest pageRequest = new PageRequest(page, size);
		return repository.findBySobId(sobId, pageRequest);
	}

	public Page<TdDeliveryFeeHead> findBySobIdAndGoodsSkuContaining(Long sobId, String keywords,int page, int size) {
		if (StringUtils.isEmpty(keywords)) {
			return null;
		}
		PageRequest pageRequest = new PageRequest(page, size);
		return repository.findBySobIdAndGoodsSkuContaining(sobId, keywords, pageRequest);
	}
}
