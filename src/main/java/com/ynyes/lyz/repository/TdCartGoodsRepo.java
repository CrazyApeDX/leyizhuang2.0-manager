package com.ynyes.lyz.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.ynyes.lyz.entity.TdCartGoods;

/**
 * TdCartGoods 实体数据库操作接口
 * 
 * @author Sharon
 *
 */

public interface TdCartGoodsRepo
		extends PagingAndSortingRepository<TdCartGoods, Long>, JpaSpecificationExecutor<TdCartGoods> {
	TdCartGoods findTopByGoodsIdAndPriceAndUsername(Long goodsId, Double price, String username);

	List<TdCartGoods> findByGoodsIdAndPriceAndUsername(Long goodsId, Double price, String username);

	List<TdCartGoods> findByUsername(String username);

	List<TdCartGoods> findByUsernameAndIsSelectedTrue(String username);

	/**
	 * 根据用户名和商品id查找购物车项
	 * 
	 * @author dengxiao
	 */
	TdCartGoods findByUsernameAndGoodsId(String username, Long goodsId);

	/**
	 * 根据用户id查找购物车项
	 * 
	 * @author dengxiao
	 */
	List<TdCartGoods> findByUserId(Long userId);

	/**
	 * 根据用户id查找已选的调色包商品
	 * 
	 * @author dengxiao
	 */
	List<TdCartGoods> findByUserIdAndIsColorTrue(Long userId);

	/**
	 * 根据用户id查找已选商品的数量
	 * 
	 * @author dengxiao
	 */
	Long countByUserId(Long userId);
}
