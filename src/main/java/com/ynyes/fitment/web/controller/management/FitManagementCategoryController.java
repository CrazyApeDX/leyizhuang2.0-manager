package com.ynyes.fitment.web.controller.management;

import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.ynyes.fitment.foundation.entity.FitCompany;
import com.ynyes.fitment.foundation.entity.FitCompanyCategory;
import com.ynyes.fitment.foundation.service.FitCompanyCategoryService;
import com.ynyes.fitment.foundation.service.FitCompanyService;
import com.ynyes.lyz.entity.TdProductCategory;
import com.ynyes.lyz.service.TdProductCategoryService;

@Controller
@RequestMapping(value = "/Verwalter/fitment/category")
public class FitManagementCategoryController {

	@Autowired
	private FitCompanyService fitCompanyService;
	
	@Autowired
	private TdProductCategoryService tdProductCategoryService;
	
	@Autowired
	private FitCompanyCategoryService fitCompanyCategoryService;
	
	@RequestMapping(value = "/edit/{companyId}", method = RequestMethod.GET, produces = "text/html;charset=utf-8")
	public String categoryEdit(@PathVariable Long companyId, ModelMap map) {
		
		// 查询所有的一级分类
		List<TdProductCategory> level_one = tdProductCategoryService.findByParentIdIsNullOrderBySortIdAsc();
		map.addAttribute("level_one", level_one);
		// 根据一级分类查找二级分类
		if (null != level_one) {
			for (int i = 0; i < level_one.size(); i++) {
				TdProductCategory category = level_one.get(i);
				// 根据指定的一级分类查找其下所属的二级分类
				List<TdProductCategory> level_two = tdProductCategoryService
						.findByParentIdOrderBySortIdAsc(category.getId());
				map.addAttribute("level_two" + i, level_two);
			}
		}
		
		FitCompany company = null;
		List<FitCompanyCategory> companyCategoryList = null;
		try {
			company = this.fitCompanyService.findOne(companyId);
			companyCategoryList = this.fitCompanyCategoryService.findByCompanyIdOrderByCategorySortIdAsc(companyId);
			if (CollectionUtils.isNotEmpty(companyCategoryList)) {
				for (FitCompanyCategory companyCategory : companyCategoryList) {
					if (null != companyCategory) {
						map.addAttribute("limit" + companyCategory.getCategoryId(), true);
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		map.addAttribute("company", company);
		return "/fitment/management/company_category_edit";
	}
	
	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public String categorySave(Long companyId, String limit) {
		try {
			if (null != limit && !"".equals(limit)) {
				// 拆分limit
				String[] cats = limit.split(",");
				if (null != cats && cats.length > 0) {
					// 删除数据库中该装饰公司的分类限购信息
					this.fitCompanyCategoryService.deleteByCompanyId(companyId);
					for (String sId : cats) {
						// 获取分类id
						if (null != sId && !"".equals(sId)) {
							// 获取到分类的id
							Long id = Long.parseLong(sId);
							// 获取指定的分类
							TdProductCategory category = tdProductCategoryService.findOne(id);
							FitCompanyCategory companyCategory = new FitCompanyCategory();
							companyCategory.setCompanyId(companyId);
							companyCategory.setCategoryTitle(category.getTitle());
							companyCategory.setCategoryId(category.getId());
							companyCategory.setCategoryParentId(null == category.getParentId() ? 0l : category.getParentId());
							companyCategory.setCategorySortId(category.getSortId());
							this.fitCompanyCategoryService.managerAdd(companyCategory);
						}
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "redirect:/Verwalter/fitment/company/list";
	}
}
