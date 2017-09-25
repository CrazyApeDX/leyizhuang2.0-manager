package com.ynyes.fitment.web.controller.front;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ynyes.fitment.core.entity.client.result.ClientResult;
import com.ynyes.fitment.core.entity.client.result.ClientResult.ActionCode;
import com.ynyes.fitment.foundation.entity.FitCartGoods;
import com.ynyes.fitment.foundation.entity.FitEmployee;
import com.ynyes.fitment.foundation.service.biz.BizCartGoodsService;
import com.ynyes.fitment.foundation.service.biz.BizInventoryService;

@Controller
@RequestMapping(value = "/fit/cart")
public class FitCartController extends FitBasicController {

	private final Logger LOGGER = LoggerFactory.getLogger(FitCartController.class);

	@Autowired
	private BizCartGoodsService bizCartGoodsService;

	@Autowired
	private BizInventoryService bizInventoryService;

	@RequestMapping(method = RequestMethod.POST, produces = "application/json;charset=utf-8")
	@ResponseBody
	public ClientResult cartPost(HttpServletRequest request, String params) {
		try {
			if (LOGGER.isInfoEnabled()) {
				LOGGER.info("进入控制器，准备添加商品到购物车，参数：params = {}", params);
			}
			FitEmployee employee = this.getLoginEmployee(request);
			this.bizCartGoodsService.addToCart(params, employee);
			if (LOGGER.isInfoEnabled()) {
				LOGGER.info("开始查询当前已选数量，参数：employeeId = {}", null == employee ? null : employee.getId());
			}
			Long selected = this.bizCartGoodsService.getSelectedCounts(employee.getId());
			return new ClientResult(ActionCode.SUCCESS, selected);
		} catch (Exception e) {
			e.printStackTrace();
			if (LOGGER.isWarnEnabled()) {
				LOGGER.warn(e.getMessage());
			}
			return new ClientResult(ActionCode.FAILURE, "出现意外的错误，请稍后重试或联系管理员");
		}
	}

	@RequestMapping(value = "/{operation}/{id}", method = RequestMethod.POST)
	public String changeQuantity(@PathVariable("operation") String operation, @PathVariable("id") Long id,
			Long quantity, HttpServletRequest request, ModelMap map) {
		try {
			if (LOGGER.isInfoEnabled()) {
				LOGGER.info("开始操作购物车，参数：operation = {}, id = {}, quantity = {}", operation, id, quantity);
			}
			FitEmployee employee = this.getLoginEmployee(request);
			Long inventory = this.bizInventoryService.getCityInventoryByGoodsId(employee.getCompanyId(), id);
			FitCartGoods cart = this.bizCartGoodsService.getCartGoodsByGoodsId(employee.getId(), id);
			this.bizCartGoodsService.changeCartGoodsQuantity(operation, cart, quantity, inventory);
			if (LOGGER.isInfoEnabled()) {
				LOGGER.info("操作完成，开始计算当前购物车商品总金额");
			}
			List<FitCartGoods> cartList = this.bizCartGoodsService.loadEmployeeCart(employee.getId());
			map.addAttribute("cartList", cartList);
			if (CollectionUtils.isNotEmpty(cartList)) {
				Double totalPrice = 0d;
				for (int i = 0; i < cartList.size(); i++) {
					FitCartGoods cartGoods = cartList.get(i);
					totalPrice += null == cartGoods.getTotalPrice() ? 0d : cartGoods.getTotalPrice();
					map.addAttribute("goods" + i, this.bizInventoryService
							.getCityInventoryByGoodsId(employee.getCompanyId(), cartGoods.getGoodsId()));
				}
				if (LOGGER.isInfoEnabled()) {
					LOGGER.info("当前购物车商品总金额计算完毕，totalPrice = {}", totalPrice);
				}
				map.addAttribute("totalPrice", totalPrice);
			}
		} catch (Exception e) {
			if (LOGGER.isWarnEnabled()) {
				LOGGER.warn(e.getMessage());
			}
			e.printStackTrace();
		}
		return "/fitment/selected_goods_and_color";
	}
	
	@RequestMapping(value = "/delete/{id}", method=RequestMethod.POST, produces = "application/json;charset=utf-8")
	@ResponseBody
	public ClientResult cartDeletePost(@PathVariable("id") Long id, HttpServletRequest request) {
		try {
			FitEmployee employee = this.getLoginEmployee(request);
			return this.bizCartGoodsService.deleteCartGoods(employee.getId(), id);
		} catch (Exception e) {
			if (LOGGER.isWarnEnabled()) {
				LOGGER.warn(e.getMessage());
			}
			e.printStackTrace();
			return new ClientResult(ActionCode.FAILURE, "出现意外的错误，请稍后重试或联系管理员");
		}
	}
}
