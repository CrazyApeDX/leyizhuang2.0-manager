package com.ynyes.fitment.web.controller.front;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.ynyes.fitment.core.entity.client.result.ClientResult;
import com.ynyes.fitment.core.entity.client.result.ClientResult.ActionCode;
import com.ynyes.fitment.core.exception.ApplicationException;
import com.ynyes.fitment.foundation.entity.client.ClientCompanyCategory;
import com.ynyes.fitment.foundation.service.FitCompanyCategoryService;

@RestController
@RequestMapping(value = "/fit/category", produces = "application/json;charset=utf-8")
public class FitCategoryController {

	private final Logger LOGGER = LoggerFactory.getLogger(FitCategoryController.class);
	
	@Autowired
	private FitCompanyCategoryService fitCompanyCategoryService;
	
	@RequestMapping(value = "/company/{companyId}", method = RequestMethod.GET)
	public ClientResult fitCategoryParentIdGet(@PathVariable Long companyId) {
		try {
			if (LOGGER.isInfoEnabled()) {
				LOGGER.info("开始查找商品分类，参数companyId = {}", companyId);
			}
			
			List<ClientCompanyCategory> categoryList = fitCompanyCategoryService.getSaleableCategoryTreeByCompanyId(companyId);
			
			if (LOGGER.isInfoEnabled()) {
				LOGGER.info("商品分类查询完毕，获取共计{}项一级分类", categoryList.size());
			}
			return new ClientResult(ActionCode.SUCCESS, categoryList);
		} catch (ApplicationException e) {
			e.printStackTrace();
			if (LOGGER.isWarnEnabled()) {
				LOGGER.warn(e.getMessage());
			}
			return new ClientResult(ActionCode.FAILURE, e.getNotice());
				
		} catch (Exception e) {
			e.printStackTrace();
			if (LOGGER.isErrorEnabled()) {
				LOGGER.error(e.getMessage());
			}
			return new ClientResult(ActionCode.FAILURE, "出现意外的错误，请联系管理员");
		}
	}
}
