package com.ynyes.lyz.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ynyes.lyz.entity.TdGoods;
import com.ynyes.lyz.entity.TdUserRecentVisit;
import com.ynyes.lyz.repository.TdUserRecentVisitRepo;

/**
 * TdUserRecentVisit 服务类
 * 
 * @author Sharon
 *
 */

@Service
@Transactional
public class TdUserRecentVisitService {

	@Autowired
	private TdUserRecentVisitRepo repository;

	@Autowired
	private TdGoodsService tdGoodsService;

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
	public void delete(TdUserRecentVisit e) {
		if (null != e) {
			repository.delete(e);
		}
	}

	public void delete(List<TdUserRecentVisit> entities) {
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
	public TdUserRecentVisit findOne(Long id) {
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
	public List<TdUserRecentVisit> findAll(Iterable<Long> ids) {
		return (List<TdUserRecentVisit>) repository.findAll(ids);
	}

	public List<TdUserRecentVisit> findByUsername(String username) {
		return repository.findByUsername(username);
	}

	public Page<TdUserRecentVisit> findAllOrderBySortIdAsc(int page, int size) {
		PageRequest pageRequest = new PageRequest(page, size, new Sort(Direction.ASC, "sortId"));

		return repository.findAll(pageRequest);
	}

	public Page<TdUserRecentVisit> findByUsernameOrderByVisitTimeDesc(String username, int page, int size) {
		PageRequest pageRequest = new PageRequest(page, size);

		return repository.findByUsernameOrderByVisitTimeDesc(username, pageRequest);
	}

	public Page<TdUserRecentVisit> findByUsernameAndSearchOrderByVisitTimeDesc(String username, String keywords,
			int page, int size) {
		PageRequest pageRequest = new PageRequest(page, size);

		return repository.findByUsernameAndGoodsTitleContainingOrderByVisitTimeDesc(username, keywords, pageRequest);
	}

	/**
	 * 保存
	 * 
	 * @param e
	 * @return
	 */
	public TdUserRecentVisit save(TdUserRecentVisit e) {

		return repository.save(e);
	}

	public TdUserRecentVisit addNew(String username, Long goodsId) {
		if (null == username || null == goodsId) {
			return null;
		}

		TdUserRecentVisit recent = repository.findByUsernameAndGoodsId(username, goodsId);

		if (null == recent) {
			TdGoods goods = tdGoodsService.findOne(goodsId);

			if (null == goods) {
				return null;
			}

			recent = new TdUserRecentVisit();
			recent.setUsername(username);
			recent.setGoodsId(goodsId);
			recent.setGoodsCoverImageUri(goods.getCoverImageUri());
			recent.setGoodsSalePrice(goods.getSalePrice());
			recent.setGoodsTitle(goods.getTitle());
			recent.setVisitTime(new Date());
		} else {
			recent.setVisitTime(new Date());
		}

		return repository.save(recent);
	}

	public List<TdUserRecentVisit> save(List<TdUserRecentVisit> entities) {

		return (List<TdUserRecentVisit>) repository.save(entities);
	}

	// 根据用户id查找所有的浏览记录，按浏览时间倒序排序
	public List<TdUserRecentVisit> findByUserIdOrderByVisitTimeDesc(Long userId) {
		if (null == userId) {
			return null;
		}
		return repository.findByUserIdOrderByVisitTimeDesc(userId);
	}

	// 根据用户id查找所有的浏览记录，按照浏览时间正序排序
	public List<TdUserRecentVisit> findByUserIdOrderByVisitTimeAsc(Long userId) {
		if (null == userId) {
			return null;
		}
		return repository.findByUserIdOrderByVisitTimeAsc(userId);
	}

	// 查找指定用户最早的一个浏览记录
	public TdUserRecentVisit findTopByUserIdOrderByVisitTimeAsc(Long userId) {
		if (null == userId) {
			return null;
		}
		return repository.findTopByUserIdOrderByVisitTimeAsc(userId);
	}

	// 删除多个浏览记录方法
	public void deleteAll(List<TdUserRecentVisit> list) {
		if (null != list) {
			repository.delete(list);
		}
	}

	// 存储多个浏览记录的方法
	public List<TdUserRecentVisit> saveAll(List<TdUserRecentVisit> list) {
		if (null == list) {
			return null;
		}
		return (List<TdUserRecentVisit>) repository.save(list);
	}

	/**
	 * 根据指定商品的id和用户id查找浏览记录
	 * 
	 * @author dengxiao
	 */
	public TdUserRecentVisit findByGoodsIdAndUserId(Long goodsId, Long userId) {
		if (null == goodsId || null == userId) {
			return null;
		}
		return repository.findByGoodsIdAndUserId(goodsId, userId);
	}

	/**
	 * 根据用户名查找所有的浏览记录，按时间倒序排序
	 * 
	 * @author dengxiao
	 */
	public List<TdUserRecentVisit> findByUsernameOrderByVisitTimeDesc(String username) {
		if (null == username) {
			return null;
		}
		return repository.findByUsernameOrderByVisitTimeDesc(username);
	}

	/**
	 * 根据用户名查找所有的浏览记录，按时间正序排序
	 * 
	 * @author dengxiao
	 */
	public List<TdUserRecentVisit> findByUsernameOrderByVisitTimeAsc(String username) {
		if (null == username) {
			return null;
		}
		return repository.findByUsernameOrderByVisitTimeAsc(username);
	};

	/**
	 * 根据用户名查找所有的浏览记录，按照价格反序排序
	 * 
	 * @author dengxiao
	 */
	public List<TdUserRecentVisit> findByUsernameOrderBySalePriceDesc(String username, Long priceListId) {
		if (null == username) {
			return null;
		}
		return repository.findByUsernameOrderBySalePriceDesc(username, priceListId);
	}

	/**
	 * 根据用户名查找所有的浏览记录，按照价格正序排序
	 * 
	 * @author dengxiao
	 */
	public List<TdUserRecentVisit> findByUsernameOrderBySalePriceAsc(String username, Long priceListId) {
		if (null == username) {
			return null;
		}
		return repository.findByUsernameOrderBySalePriceAsc(username, priceListId);
	}

	/**
	 * 根据用户名查找所有的浏览记录，按照销量反序排序
	 * 
	 * @author dengxiao
	 */
	public List<TdUserRecentVisit> findByUsernameOrderBySoldNumberDesc(String username) {
		if (null == username) {
			return null;
		}
		return repository.findByUsernameOrderBySoldNumberDesc(username);
	}

	/**
	 * 根据用户名查找所有的浏览记录，按照销量正序排序
	 * 
	 * @author dengxiao
	 */
	public List<TdUserRecentVisit> findByUsernameOrderBySoldNumberAsc(String username) {
		if (null == username) {
			return null;
		}
		return repository.findByUsernameOrderBySoldNumberAsc(username);
	}
}
