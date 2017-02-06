package com.ynyes.fitment.web.controller.front;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.ynyes.fitment.core.constant.Global;
import com.ynyes.fitment.foundation.entity.FitCartGoods;
import com.ynyes.fitment.foundation.entity.FitEmployee;
import com.ynyes.fitment.foundation.entity.FitOrder;
import com.ynyes.fitment.foundation.entity.client.ClientCategory;
import com.ynyes.fitment.foundation.service.biz.BizCartGoodsService;
import com.ynyes.fitment.foundation.service.biz.BizGoodsService;
import com.ynyes.fitment.foundation.service.biz.BizInventoryService;
import com.ynyes.fitment.foundation.service.biz.BizOrderService;

@Controller
@RequestMapping(value = "/fit", method = RequestMethod.GET, produces = "text/html;charset=utf-8")
public class FitViewController extends FitBasicController {

	@Autowired
	private BizGoodsService bizGoodsService;

	@Autowired
	private BizCartGoodsService bizCartGoodsService;

	@Autowired
	private BizInventoryService bizInventoryService;

	@Autowired
	private BizOrderService bizOrderService;

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
			return "/fitment/500";
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
			return "/fitment/500";
		}
	}

	@RequestMapping(value = "/address")
	public String address(HttpServletRequest request, ModelMap map) {
		try {
			return "/fitment/address_base";
		} catch (Exception e) {
			e.printStackTrace();
			return "/fitment/500";
		}
	}

	@RequestMapping(value = "/audit")
	public String audit(HttpServletRequest request, ModelMap map) {
		try {
			FitEmployee employee = this.getLoginEmployee(request);
			Page<FitOrder> orderPage = null;
			if (employee.getIsMain()) {
				orderPage = this.bizOrderService.loadOrderByCompanyId(employee.getCompanyId(), Global.DEFAULT_PAGE,
						Global.DEFAULT_SIZE);
			} else {
				orderPage = this.bizOrderService.loadOrderByEmployeeId(employee.getId(), Global.DEFAULT_PAGE,
						Global.DEFAULT_SIZE);
			}
			map.addAttribute("orderPage", orderPage);
			return "/fitment/order_audit";
		} catch (Exception e) {
			e.printStackTrace();
			return "/fitment/500";
		}
	}

	@RequestMapping(value = "/pay/{id}")
	public String fitPay(HttpServletRequest request, ModelMap map, @PathVariable("id") Long id) {
		try {
			FitOrder order = this.bizOrderService.findOne(id);
			map.addAttribute("order", order);
			return "/fitment/pay/order_pay";
		} catch (Exception e) {
			e.printStackTrace();
			return "/fitment/500";
		}
	}

	@ModelAttribute
	public void modelAttrubute(HttpServletRequest request, ModelMap map) {
		FitEmployee employee = this.getLoginEmployee(request);
		map.addAttribute("employee", employee);
	}
}
