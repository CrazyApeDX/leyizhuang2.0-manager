package com.ynyes.lyz.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.ynyes.lyz.entity.TdUserRecentVisit;

/**
 * TdUserRecentVisit 实体数据库操作接口
 * 
 * @author Sharon
 *
 */

public interface TdUserRecentVisitRepo
		extends PagingAndSortingRepository<TdUserRecentVisit, Long>, JpaSpecificationExecutor<TdUserRecentVisit> {
	Page<TdUserRecentVisit> findByUsernameOrderByVisitTimeDesc(String username, Pageable page);

	Page<TdUserRecentVisit> findByUsernameAndGoodsTitleContainingOrderByVisitTimeDesc(String username, String keywords,
			Pageable page);

	List<TdUserRecentVisit> findByUsername(String username);

	TdUserRecentVisit findByUsernameAndGoodsId(String username, Long goodsId);

	// 根据用户id查找所有的浏览记录，按浏览时间倒序排序
	List<TdUserRecentVisit> findByUserIdOrderByVisitTimeDesc(Long userId);

	// 查找指定用户最早的一个浏览记录
	TdUserRecentVisit findTopByUserIdOrderByVisitTimeAsc(Long userId);

	// 根据用户id查找所有的浏览记录，按照浏览时间正序排序
	List<TdUserRecentVisit> findByUserIdOrderByVisitTimeAsc(Long userId);

	// 根据指定商品id和用户id查找浏览记录
	TdUserRecentVisit findByGoodsIdAndUserId(Long goodsId, Long userId);

	/**
	 * 根据用户名查找所有的浏览记录，按时间倒序排序
	 * 
	 * @author dengxiao
	 */
	List<TdUserRecentVisit> findByUsernameOrderByVisitTimeDesc(String username);

	/**
	 * 根据用户名查找所有的浏览记录，按时间正序排序
	 * 
	 * @author dengxiao
	 */
	List<TdUserRecentVisit> findByUsernameOrderByVisitTimeAsc(String username);
	
	/**
	 * 根据用户名查找所有的浏览记录，按照销量反序排序
	 * 
	 * @author dengxiao
	 */
	@Query("select r from TdUserRecentVisit r,TdGoods g where r.username = ?1 and r.goodsId = g.id order by g.soldNumber desc")
	List<TdUserRecentVisit> findByUsernameOrderBySoldNumberDesc(String username);

	/**
	 * 根据用户名查找所有的浏览记录，按照销量正序排序
	 * 
	 * @author dengxiao
	 */
	@Query("select r from TdUserRecentVisit r,TdGoods g where r.username = ?1 and r.goodsId = g.id order by g.soldNumber asc")
	List<TdUserRecentVisit> findByUsernameOrderBySoldNumberAsc(String username);
	
	/**
	 * 根据用户名查找所有的浏览记录，按照价格反序排序
	 * 
	 * @author dengxiao
	 */
	@Query("select r from TdUserRecentVisit r,TdPriceListItem p where r.username = ?1 and p.priceListId = ?2 and r.goodsId = p.goodsId order by p.salePrice desc")
	List<TdUserRecentVisit> findByUsernameOrderBySalePriceDesc(String username, Long priceListId);

	/**
	 * 根据用户名查找所有的浏览记录，按照价格正序排序
	 * 
	 * @author dengxiao
	 */
	@Query("select r from TdUserRecentVisit r,TdPriceListItem p where r.username = ?1 and p.priceListId = ?2 and r.goodsId = p.goodsId order by p.salePrice asc")
	List<TdUserRecentVisit> findByUsernameOrderBySalePriceAsc(String username, Long priceListId);
}
