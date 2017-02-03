package com.ynyes.fitment.web.controller.front;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.ynyes.fitment.foundation.entity.FitCartGoods;
import com.ynyes.fitment.foundation.entity.FitEmployee;
import com.ynyes.fitment.foundation.entity.client.ClientCategory;
import com.ynyes.fitment.foundation.service.biz.BizCartGoodsService;
import com.ynyes.fitment.foundation.service.biz.BizGoodsService;
import com.ynyes.fitment.foundation.service.biz.BizInventoryService;

@Controller
@RequestMapping(value = "/fit", method = RequestMethod.GET, produces = "text/html;charset=utf-8")
public class FitViewController extends FitBasicController {

	@Autowired
	private BizGoodsService bizGoodsService;

	@Autowired
	private BizCartGoodsService bizCartGoodsService;

	@Autowired
	private BizInventoryService bizInventoryService;

	@RequestMapping
	public String fitIndex(HttpServletRequest request) {
		FitEmployee employee = this.getLoginEmployee(request);
		if (null == employee) {
			return "redirect:/fit/login";
		} else {
			return "redirect:/fit/home";
		}
	}

	@RequestMapping(value = "/login")
	public String view() {
		return "/fitment/login";
	}

	@RequestMapping(value = "/home")
	public String home(HttpServletRequest request, ModelMap map) {
		try {
			FitEmployee employee = this.getLoginEmployee(request);
			List<ClientCategory> categoryTree = bizGoodsService.getCategoryTree(employee.getCompanyId());
			map.addAttribute("categoryTree", categoryTree);
			Long selected = bizCartGoodsService.getSelectedCounts(employee.getId());
			map.addAttribute("selected", selected);
			return "/fitment/home";
		} catch (Exception e) {
			e.printStackTrace();
			return "fitment/500";
		}
	}

	@RequestMapping(value = "/cart")
	public String cart(HttpServletRequest request, ModelMap map) {
		try {
			FitEmployee employee = this.getLoginEmployee(request);
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
				map.addAttribute("totalPrice", totalPrice);
			}
			return "/fitment/cart_goods";
		} catch (Exception e) {
			e.printStackTrace();
			return "fitment/500";
		}
	}

	@RequestMapping(value = "/address")
	public String address(HttpServletRequest request, ModelMap map) {
		try {
			return "/fitment/address_base";
		} catch (Exception e) {
			e.printStackTrace();
			return "fitment/500";
		}
	}

	@ModelAttribute
	public void modelAttrubute(HttpServletRequest request, ModelMap map) {
		FitEmployee employee = this.getLoginEmployee(request);
		map.addAttribute("employee", employee);
	}
}
