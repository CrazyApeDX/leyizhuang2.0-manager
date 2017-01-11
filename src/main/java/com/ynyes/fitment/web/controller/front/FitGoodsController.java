package com.ynyes.fitment.web.controller.front;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/fit/goods", produces = "application/json;charset=utf-8")
public class FitGoodsController {

//	private final Logger LOGGER = LoggerFactory.getLogger(FitCategoryController.class);
//
//	@Autowired
//	private FitCompanyGoodsService fitCompanyGoodsService;
//
//	@RequestMapping(value = "/company/{companyId}/category/{categoryId}", method = RequestMethod.GET)
//	public ClientResult fitGoodsCategoryGet(@PathVariable Long companyId, @PathVariable Long categoryId) {
//		try {
//			if (LOGGER.isInfoEnabled()) {
//				LOGGER.info("开始查找商品，参数：companyId = {}，categoryId = {}", companyId, categoryId);
//			}
//
//			List<ClientCompanyGoods> goodsList = fitCompanyGoodsService.getGoodsByCategoryIdAndComapnyId(categoryId,
//					companyId);
//
//			if (LOGGER.isInfoEnabled()) {
//				LOGGER.info("查询商品结束，共计查询{}条商品记录", goodsList.size());
//			}
//
//			return new ClientResult(ActionCode.SUCCESS, goodsList);
//
//		} catch (ApplicationException e) {
//			e.printStackTrace();
//			if (LOGGER.isWarnEnabled()) {
//				LOGGER.warn(e.getMessage());
//			}
//			return new ClientResult(ActionCode.FAILURE, e.getNotice());
//		} catch (Exception e) {
//			e.printStackTrace();
//			if (LOGGER.isErrorEnabled()) {
//				LOGGER.error(e.getMessage());
//			}
//			return new ClientResult(ActionCode.FAILURE, "出现意外的错误，请联系管理员");
//		}
//	}
}
