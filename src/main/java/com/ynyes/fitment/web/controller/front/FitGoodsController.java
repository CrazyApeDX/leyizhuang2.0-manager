package com.ynyes.fitment.web.controller.front;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.ynyes.fitment.foundation.entity.FitEmployee;
import com.ynyes.fitment.foundation.entity.client.ClientGoods;
import com.ynyes.fitment.foundation.service.biz.BizGoodsService;

@Controller
@RequestMapping(value = "/fit/goods")
public class FitGoodsController extends FitBasicController {

	private final Logger LOGGER = LoggerFactory.getLogger(FitGoodsController.class);

	@Autowired
	private BizGoodsService bizGoodsService;

	@RequestMapping(value = "/category/{categoryId}", method = RequestMethod.POST, produces = "text/html;charset=utf-8")
	public String getGoodsByCategoryId(HttpServletRequest request, ModelMap map, @PathVariable Long categoryId) {
		try {
			if (LOGGER.isInfoEnabled()) {
				LOGGER.info("进入控制器，根据商品分类ID，获取可售商品，参数：categoryId = {}", categoryId);
			}
			FitEmployee employee = this.getLoginEmployee(request);
			if (LOGGER.isInfoEnabled()) {
				LOGGER.info("获取当前登录用户的信息，得到装饰公司id = {}", null == employee ? null : employee.getCompanyId());
			}
			List<ClientGoods> someGoods = this.bizGoodsService.getGoodsByCategoryId(categoryId, employee.getCompanyId());
			if (LOGGER.isInfoEnabled()) {
				LOGGER.info("查询完成，查询共计{}件商品", someGoods.size());
			}
			map.addAttribute("some_goods", someGoods);
			return "/fitment/goods_list";
		} catch (Exception e) {
			e.printStackTrace();
			if (LOGGER.isWarnEnabled()) {
				LOGGER.warn(e.getMessage());
			}
			return "fitment/500";
		}
	}
}
