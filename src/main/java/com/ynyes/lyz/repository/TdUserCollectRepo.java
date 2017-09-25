package com.ynyes.lyz.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.ynyes.lyz.entity.TdUserCollect;

/**
 * TdUserCollect 实体数据库操作接口
 * 
 * @author Sharon
 *
 */

public interface TdUserCollectRepo
		extends PagingAndSortingRepository<TdUserCollect, Long>, JpaSpecificationExecutor<TdUserCollect> {
	Page<TdUserCollect> findByUsernameOrderByIdDesc(String username, Pageable page);

	Page<TdUserCollect> findByUsernameAndGoodsTitleContainingOrderByIdDesc(String username, String keywords,
			Pageable page);

	// 不分页的findByUsernameAndGoodsTitleContainingOrderByIdDesc——by dengxiao
	List<TdUserCollect> findByUsernameAndGoodsTitleContainingOrderByIdDesc(String username, String keywords);

	/**
	 * 查找指定用户收藏的商品
	 */
	@Query("select uc from TdUserCollect uc,TdGoods g where "
			+ "uc.goodsId=g.id and uc.username =?1 and g.isOnSale=1 ")
	List<TdUserCollect> findByUsername(String username);

	TdUserCollect findByUsernameAndGoodsId(String username, Long goodsId);

	Long countByGoodsId(Long goodsId);

	/**
	 * 查找指定用户收藏的商品按照收藏时间反序排序
	 * 
	 * @author dengxiao
	 */
	@Query("select uc from TdUserCollect uc,TdGoods g where "
			+ "uc.goodsId=g.id and uc.username =?1 and g.isOnSale=1 order by uc.collectTime desc")
	List<TdUserCollect> findByUsernameOrderByCollectTimeDesc(String username);

	/**
	 * 查找指定用户收藏的商品按照收藏时间正序排序
	 * 
	 * @author dengxiao
	 */
	List<TdUserCollect> findByUsernameOrderByCollectTimeAsc(String username);

	/**
	 * 查找指定用户收藏的商品按照商品的销量反序排序
	 * 
	 * @author dengxiao
	 */
	@Query("select c.goodsTitle,c.goodsCoverImageUri,p.salePrice from TdUserCollect c,TdGoods g,TdPriceListItem p where c.username = ?1 and c.goodsId = g.id and p.goodsId = g.id and p.priceListId = ?2 order by g.soldNumber desc")
	List<TdUserCollect> findByUsernameOrderBySoldNumberDesc(String username, Long priceListId);

	/**
	 * 查找指定用户收藏的商品按照商品的销量正序排序
	 * 
	 * @author dengxiao
	 */
	@Query("select c.goodsTitle,c.goodsCoverImageUri,p.salePrice from TdUserCollect c,TdGoods g,TdPriceListItem p where c.username = ?1 and c.goodsId = g.id and p.goodsId = g.id and p.priceListId = ?2 order by g.soldNumber asc")
	List<TdUserCollect> findByUsernameOrderBySoldNumberAsc(String username, Long priceListId);

	/**
	 * 查找指定用户收藏的商品按照商品的价格反序排序
	 * 
	 * @author dengxiao
	 */
	@Query("select c.goodsTitle,c.goodsCoverImageUri,p.salePrice from TdUserCollect c,TdGoods g,TdPriceListItem p where c.username = ?1 and c.goodsId = g.id and p.goodsId = g.id and p.priceListId = ?2 order by p.salePrice desc")
	List<TdUserCollect> findByUsernameOrderBySalePriceDesc(String username,Long priceListId);
	
	/**
	 * 查找指定用户收藏的商品按照商品的价格正序排序
	 * 
	 * @author dengxiao
	 */
	@Query("select c.goodsTitle,c.goodsCoverImageUri,p.salePrice from TdUserCollect c,TdGoods g,TdPriceListItem p where c.username = ?1 and c.goodsId = g.id and p.goodsId = g.id and p.priceListId = ?2 order by p.salePrice asc")
	List<TdUserCollect> findByUsernameOrderBySalePriceAsc(String username,Long priceListId);

}
