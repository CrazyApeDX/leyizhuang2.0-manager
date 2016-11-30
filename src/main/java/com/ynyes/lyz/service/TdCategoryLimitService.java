package com.ynyes.lyz.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ynyes.lyz.entity.TdCategoryLimit;
import com.ynyes.lyz.repository.TdCategoryLimitRepo;

@Service
@Transactional
public class TdCategoryLimitService {

	@Autowired
	private TdCategoryLimitRepo repository;

	public TdCategoryLimit save(TdCategoryLimit e) {
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

	public TdCategoryLimit findOne(Long id) {
		if (null == id) {
			return null;
		}
		return repository.findOne(id);
	}

	public List<TdCategoryLimit> findAll() {
		return (List<TdCategoryLimit>) repository.findAll();
	}

	public List<TdCategoryLimit> findBySobId(Long sobId) {
		if (null == sobId) {
			return null;
		}
		return repository.findBySobId(sobId);
	}

	public void deleteCityLimits(Long sobId) {
		if (null != sobId) {
			List<TdCategoryLimit> limits = this.findBySobId(sobId);
			if (null != limits && limits.size() > 0) {
				for (TdCategoryLimit limit : limits) {
					if (null != limit && null != limit.getId()) {
						this.delete(limit.getId());
					}
				}
			}
		}
	}

	/**
	 * 查找指定城市id下的一级分类
	 * 
	 * @author DengXiao
	 */
	public List<TdCategoryLimit> findBySobIdAndParentIdIsNullOrderBySortIdAsc(Long sobId) {
		if (null == sobId) {
			return null;
		}
		return repository.findBySobIdAndParentIdIsNullOrderBySortIdAsc(sobId);
	}

	/**
	 * 根据城市sobid和分类父id查找指定的分类
	 * 
	 * @author DengXiao
	 */
	public List<TdCategoryLimit> findBySobIdAndParentIdOrderBySortIdAsc(Long sobId, Long parentId){
		if(null == sobId || null == parentId){
			return null;
		}
		return repository.findBySobIdAndParentIdOrderBySortIdAsc(sobId, parentId);
	}
	
	/**
	 * 根据分类名称模糊查询
	 * @author zp
	 */
	public List<TdCategoryLimit> findByTitleContaining(String title){
		return repository.findByTitleContaining(title);
	}
}
