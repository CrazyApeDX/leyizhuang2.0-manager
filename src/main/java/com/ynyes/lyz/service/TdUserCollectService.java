package com.ynyes.lyz.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ynyes.lyz.entity.TdUserCollect;
import com.ynyes.lyz.repository.TdUserCollectRepo;

/**
 * TdUserCollect 服务类
 * 
 * @author Sharon
 *
 */

@Service
@Transactional
public class TdUserCollectService {

	@Autowired
	private TdUserCollectRepo repository;

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
	public void delete(TdUserCollect e) {
		if (null != e) {
			repository.delete(e);
		}
	}

	public void delete(List<TdUserCollect> entities) {
		if (null != entities) {
			repository.delete(entities);
		}
	}

	/**
	 * 查找
	 * 
	 * @param id
	 *            ID
	 * @return
	 */
	public TdUserCollect findOne(Long id) {
		if (null == id) {
			return null;
		}

		return repository.findOne(id);
	}

	/**
	 * 查找
	 * 
	 * @param ids
	 * @return
	 */
	public TdUserCollect findByUsernameAndGoodsId(String username, Long goodsId) {
		if (null == username || null == goodsId) {
			return null;
		}

		return repository.findByUsernameAndGoodsId(username, goodsId);
	}

	public List<TdUserCollect> findAll(Iterable<Long> ids) {
		return (List<TdUserCollect>) repository.findAll(ids);
	}

	public List<TdUserCollect> findByUsername(String username) {
		return repository.findByUsername(username);
	}

	public Long countByGoodsId(Long goodsId) {
		return repository.countByGoodsId(goodsId);
	}

	public Page<TdUserCollect> findAllOrderBySortIdAsc(int page, int size) {
		PageRequest pageRequest = new PageRequest(page, size, new Sort(Direction.ASC, "sortId"));

		return repository.findAll(pageRequest);
	}

	public Page<TdUserCollect> findByUsername(String username, int page, int size) {
		PageRequest pageRequest = new PageRequest(page, size);

		return repository.findByUsernameOrderByIdDesc(username, pageRequest);
	}

	public Page<TdUserCollect> findByUsernameAndSearch(String username, String keywords, int page, int size) {
		PageRequest pageRequest = new PageRequest(page, size);

		return repository.findByUsernameAndGoodsTitleContainingOrderByIdDesc(username, keywords, pageRequest);
	}

	public List<TdUserCollect> findByUsernameAndSearch(String username, String keywords) {
		return repository.findByUsernameAndGoodsTitleContainingOrderByIdDesc(username, keywords);
	}

	/**
	 * 保存
	 * 
	 * @param e
	 * @return
	 */
	public TdUserCollect save(TdUserCollect e) {

		return repository.save(e);
	}

	public List<TdUserCollect> save(List<TdUserCollect> entities) {

		return (List<TdUserCollect>) repository.save(entities);
	}
	
	/**
	 * 查找指定用户收藏的商品按照收藏时间反序排序
	 * 
	 * @author dengxiao
	 */
	public List<TdUserCollect> findByUsernameOrderByCollectTimeDesc(String username){
		if(null == username){
			return null;
		}
		return repository.findByUsernameOrderByCollectTimeDesc(username);
	}
	
	/**
	 * 查找指定用户收藏的商品按照收藏时间正序排序
	 * 
	 * @author dengxiao
	 */
	public List<TdUserCollect> findByUsernameOrderByCollectTimeAsc(String username){
		if(null == username){
			return null;
		}
		return repository.findByUsernameOrderByCollectTimeAsc(username);
	}

	/**
	 * 查找指定用户收藏的商品按照商品的销量反序排序
	 * 
	 * @author dengxiao
	 */
	public List<TdUserCollect> findByUsernameOrderBySoldNumberDesc(String username, Long priceListId) {
		if (null == username || null == priceListId) {
			return null;
		}
		return repository.findByUsernameOrderBySoldNumberDesc(username, priceListId);
	};

	/**
	 * 查找指定用户收藏的商品按照商品的销量正序排序
	 * 
	 * @author dengxiao
	 */
	public List<TdUserCollect> findByUsernameOrderBySoldNumberAsc(String username, Long priceListId) {
		if (null == username || null == priceListId) {
			return null;
		}
		return repository.findByUsernameOrderBySoldNumberAsc(username, priceListId);
	}
	
	/**
	 * 查找指定用户收藏的商品按照商品的价格反序排序
	 * 
	 * @author dengxiao
	 */
	public List<TdUserCollect> findByUsernameOrderBySalePriceDesc(String username,Long priceListId){
		if (null == username || null == priceListId) {
			return null;
		}
		return repository.findByUsernameOrderBySalePriceDesc(username, priceListId);
	}
	
	/**
	 * 查找指定用户收藏的商品按照商品的价格正序排序
	 * 
	 * @author dengxiao
	 */
	public List<TdUserCollect> findByUsernameOrderBySalePriceAsc(String username,Long priceListId){
		if (null == username || null == priceListId) {
			return null;
		}
		return repository.findByUsernameOrderBySalePriceAsc(username, priceListId);
	}
}

