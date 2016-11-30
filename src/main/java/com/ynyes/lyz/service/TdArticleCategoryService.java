package com.ynyes.lyz.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ynyes.lyz.entity.TdArticleCategory;
import com.ynyes.lyz.repository.TdArticleCategoryRepo;

/**
 * TdArticleCategory 服务类
 * 
 * @author Sharon
 *
 */

@Service
@Transactional
public class TdArticleCategoryService {
	@Autowired
	TdArticleCategoryRepo repository;

	/**
	 * 查找菜单项
	 * 
	 * @param parentId
	 *            父菜单ID，为0时代报顶层菜单
	 * @return
	 */
	public List<TdArticleCategory> findByParentId(Long parentId) {
		if (null == parentId) {
			return null;
		}

		return repository.findByParentIdOrderBySortIdAsc(parentId);
	}

	public List<TdArticleCategory> findByMenuIdAndParentId(Long menuId, Long parentId) {
		return repository.findByMenuIdAndParentIdOrderBySortIdAsc(menuId, parentId);
	}

	/**
	 * 查找菜单项，只支持三级菜单
	 * 
	 * @param menuId
	 *            菜单ID
	 * @return
	 */
	public List<TdArticleCategory> findByMenuId(Long menuId) {
		if (null == menuId) {
			return null;
		}

		List<TdArticleCategory> resList = new ArrayList<TdArticleCategory>();
		List<TdArticleCategory> tacList = repository.findByMenuIdAndParentIdOrderBySortIdAsc(menuId, 0L);

		for (TdArticleCategory tac : tacList) {
			resList.add(tac);

			List<TdArticleCategory> level2List = repository.findByParentIdOrderBySortIdAsc(tac.getId());

			if (null != level2List && level2List.size() > 0) {
				for (TdArticleCategory l2Tac : level2List) {
					resList.add(l2Tac);

					List<TdArticleCategory> level3List = repository.findByParentIdOrderBySortIdAsc(l2Tac.getId());

					if (null != level3List && level3List.size() > 0) {
						resList.addAll(level3List);
					}
				}
			}
		}

		return resList;
	}

	/**
	 * 查找菜单项，只支持三级菜单
	 * 
	 * @param cid
	 *            菜单ID
	 * @return
	 */
	public List<TdArticleCategory> findByChannelId(Long cid) {
		if (null == cid) {
			return null;
		}

		List<TdArticleCategory> resList = new ArrayList<TdArticleCategory>();
		List<TdArticleCategory> tacList = repository.findByChannelIdAndParentIdOrderBySortIdAsc(cid, 0L);

		for (TdArticleCategory tac : tacList) {
			resList.add(tac);

			List<TdArticleCategory> level2List = repository.findByParentIdOrderBySortIdAsc(tac.getId());

			if (null != level2List && level2List.size() > 0) {
				for (TdArticleCategory l2Tac : level2List) {
					resList.add(l2Tac);

					List<TdArticleCategory> level3List = repository.findByParentIdOrderBySortIdAsc(l2Tac.getId());

					if (null != level3List && level3List.size() > 0) {
						resList.addAll(level3List);
					}
				}
			}
		}

		return resList;
	}

	/**
	 * 删除
	 * 
	 * @param id
	 *            菜单项ID
	 */
	public void delete(Long id) {
		if (null != id) {
			repository.delete(id);
		}
	}

	/**
	 * 删除
	 * 
	 * @param e
	 *            菜单项
	 */
	public void delete(TdArticleCategory e) {
		if (null != e) {
			repository.delete(e);
		}
	}

	/**
	 * 查找
	 * 
	 * @param id
	 *            ID
	 * @return
	 */
	public TdArticleCategory findOne(Long id) {
		if (null == id) {
			return null;
		}

		return repository.findOne(id);
	}

	/**
	 * 保存类型
	 * 
	 * @param e
	 * @return
	 */
	public TdArticleCategory save(TdArticleCategory e) {
		if (null == e) {
			return null;
		}

		// 设置layerCount

		if (null == e.getParentId() || e.getParentId().equals(0L)) {
			e.setLayerCount(1L);
		} else {
//			TdArticleCategory parent = repository.findOne(e.getParentId());
//			e.setLayerCount(parent.getLayerCount() + 1L);
			
			//zhangji  设置最大3层
			TdArticleCategory parent = repository.findOne(e.getParentId());
			Long layerCount = parent.getLayerCount() + 1L;
			if(layerCount > 3L)
			{
				layerCount = 3L;
			}
			e.setLayerCount(layerCount);
		}

		return repository.save(e);
	}

	/**
	 * 根据标题查找消息分类
	 * 
	 * @author dengxiao
	 */
	public TdArticleCategory findByTitle(String title) {
		if (null == title) {
			return null;
		}
		return repository.findByTitle(title);
	}

}
