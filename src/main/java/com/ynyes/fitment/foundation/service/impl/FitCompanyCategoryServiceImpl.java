package com.ynyes.fitment.foundation.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ynyes.fitment.foundation.entity.FitCompanyCategory;
import com.ynyes.fitment.foundation.entity.client.ClientCompanyCategory;
import com.ynyes.fitment.foundation.repo.FitCompanyCategoryRepo;
import com.ynyes.fitment.foundation.service.FitCompanyCategoryService;

@Service
@Transactional
public class FitCompanyCategoryServiceImpl implements FitCompanyCategoryService {

	@Autowired
	private FitCompanyCategoryRepo fitCompanyCategoryRepo;

	@Override
	public List<ClientCompanyCategory> getSaleableCategoryTreeByCompanyId(Long companyId) throws Exception {
		List<ClientCompanyCategory> clientCompanyCategoryList = new ArrayList<>();
		// 第一步，获取所有的一级分类
		List<FitCompanyCategory> levelOneList = fitCompanyCategoryRepo
				.findByCompanyIdAndCategoryParentIdOrderByIdAsc(companyId, 0l);
		if (!CollectionUtils.isEmpty(levelOneList)) {
			for (FitCompanyCategory levelOneCategory : levelOneList) {
				ClientCompanyCategory client = new ClientCompanyCategory().init(levelOneCategory);
				// 第二步：根据一级分类获取所有的二级分类
				List<FitCompanyCategory> levelTwoList = fitCompanyCategoryRepo
						.findByCompanyIdAndCategoryParentIdOrderByIdAsc(companyId, levelOneCategory.getCategoryId());
				List<ClientCompanyCategory> children = new ArrayList<>();
				if (!CollectionUtils.isEmpty(levelTwoList)) {
					for (FitCompanyCategory levelTwoCategory : levelTwoList) {
						ClientCompanyCategory clientChild = new ClientCompanyCategory().init(levelTwoCategory);
						children.add(clientChild);
					}
					client.setChildren(children);
				}
				clientCompanyCategoryList.add(client);
			}
		}
		return clientCompanyCategoryList;
	}

}
