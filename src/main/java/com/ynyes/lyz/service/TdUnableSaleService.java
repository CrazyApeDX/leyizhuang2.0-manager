package com.ynyes.lyz.service;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ynyes.lyz.entity.goods.TdUnableSale;
import com.ynyes.lyz.repository.goods.TdUnableSaleRepo;
import com.ynyes.lyz.util.Criteria;
import com.ynyes.lyz.util.Restrictions;

@Service
@Transactional
public class TdUnableSaleService {

	@Autowired
	private TdUnableSaleRepo repository;

	public TdUnableSale save(TdUnableSale e) {
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

	public TdUnableSale findOne(Long id) {
		if (null == id) {
			return null;
		}
		return repository.findOne(id);
	}

	public List<TdUnableSale> findAll() {
		return (List<TdUnableSale>) repository.findAll();
	}

	public Page<TdUnableSale> findAll(int page, int size) {
		PageRequest pageRequest = new PageRequest(page, size);
		return repository.findAll(pageRequest);
	}

	public Page<TdUnableSale> findByDiySiteId(Long diySiteId, int page, int size) {
		if (null == diySiteId) {
			return null;
		}
		PageRequest pageRequest = new PageRequest(page, size);
		return repository.findByDiySiteId(diySiteId, pageRequest);
	}

	public Page<TdUnableSale> findAllByWhere(String diyCode, String keywords, int page, int size) {
		PageRequest pageRequest = new PageRequest(page, size);
		Criteria<TdUnableSale> c = new Criteria<>();
		if (StringUtils.isNotBlank(diyCode)) {
			c.add(Restrictions.eq("diyCode", diyCode.trim(), true));
		}
		if (StringUtils.isNotBlank(keywords)) {
			c.add(Restrictions.like("goodsSku", keywords.trim(), true));
		}
		return repository.findAll(c, pageRequest);
	}
	
	public List<TdUnableSale> findByDiySiteIdAndGoodsId(Long diySiteId, Long goodsId) {
		if (null == diySiteId || null == goodsId) {
			return null;
		}
		return repository.findByDiySiteIdAndGoodsId(diySiteId, goodsId);
	}
}
