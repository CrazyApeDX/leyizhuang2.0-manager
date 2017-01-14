package com.ynyes.fitment.foundation.service.biz;

import java.util.List;

import com.ynyes.fitment.foundation.entity.client.ClientCategory;
import com.ynyes.fitment.foundation.entity.client.ClientGoods;

public interface BizGoodsService {

	List<ClientCategory> getCategoryTree(Long companyId) throws Exception;
	
	List<ClientGoods> getGoodsByCategoryId(Long categoryId,Long companyId) throws Exception;
}
