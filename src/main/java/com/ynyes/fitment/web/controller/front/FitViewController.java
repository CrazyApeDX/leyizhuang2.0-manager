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
import com.ynyes.fitment.core.constant.LoginSign;
import com.ynyes.fitment.foundation.entity.FitCartGoods;
import com.ynyes.fitment.foundation.entity.FitCompany;
import com.ynyes.fitment.foundation.entity.FitCreditChangeLog;
import com.ynyes.fitment.foundation.entity.FitEmployee;
import com.ynyes.fitment.foundation.entity.FitOrder;
import com.ynyes.fitment.foundation.entity.FitOrderCancel;
import com.ynyes.fitment.foundation.entity.FitOrderRefund;
import com.ynyes.fitment.foundation.entity.client.ClientCategory;
import com.ynyes.fitment.foundation.service.FitCompanyService;
import com.ynyes.fitment.foundation.service.FitOrderCancelService;
import com.ynyes.fitment.foundation.service.FitOrderRefundService;
import com.ynyes.fitment.foundation.service.biz.BizCartGoodsService;
import com.ynyes.fitment.foundation.service.biz.BizCreditChangeLogService;
import com.ynyes.fitment.foundation.service.biz.BizGoodsService;
import com.ynyes.fitment.foundation.service.biz.BizInventoryService;
import com.ynyes.fitment.foundation.service.biz.BizOrderService;
import com.ynyes.lyz.entity.TdOrder;
import com.ynyes.lyz.service.TdOrderService;

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

	@Autowired
	private BizCreditChangeLogService bizCreditChangeLogService;

	@Autowired
	private FitCompanyService fitCompanyService;

	@Autowired
	private TdOrderService tdOrderService;

	@Autowired
	private FitOrderCancelService fitOrderCancelService;

	@Autowired
	private FitOrderRefundService fitOrderRefundService;

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

	@RequestMapping(value = "/audit/{type}")
	public String audit(HttpServletRequest request, ModelMap map, @PathVariable String type) {
		try {
			FitEmployee employee = this.getLoginEmployee(request);
			if (null == type) {
				type = "order";
			}
			switch (type) {
			case "order":
				Page<FitOrder> orderPage = null;
				if (employee.getIsMain()) {
					orderPage = this.bizOrderService.loadOrderByCompanyId(employee.getCompanyId(), Global.DEFAULT_PAGE,
							Global.DEFAULT_SIZE);
				} else {
					orderPage = this.bizOrderService.loadOrderByEmployeeId(employee.getId(), Global.DEFAULT_PAGE,
							Global.DEFAULT_SIZE);
				}
				map.addAttribute("orderPage", orderPage);
				break;
			case "cancel":
				Page<FitOrderCancel> orderCancelPage = null;
				if (employee.getIsMain()) {
					orderCancelPage = this.fitOrderCancelService.findByCompanyIdOrderByCancelTimeDesc(
							employee.getCompanyId(), Global.DEFAULT_PAGE, Global.DEFAULT_SIZE);
				} else {
					orderCancelPage = this.fitOrderCancelService.findByEmployeeIdOrderByCancelTimeDesc(employee.getId(),
							Global.DEFAULT_PAGE, Global.DEFAULT_SIZE);
				}
				map.addAttribute("orderCancelPage", orderCancelPage);
				break;
			case "refund":
				Page<FitOrderRefund> orderRefundPage = null;
				if (employee.getIsMain()) {
					orderRefundPage = this.fitOrderRefundService.findByCompanyIdOrderByRefundTimeDesc(
							employee.getCompanyId(), Global.DEFAULT_PAGE, Global.DEFAULT_SIZE);
				} else {
					orderRefundPage = this.fitOrderRefundService.findByEmployeeIdOrderByRefundTimeDesc(employee.getId(),
							Global.DEFAULT_PAGE, Global.DEFAULT_SIZE);
				}
				map.addAttribute("orderRefundPage", orderRefundPage);
				break;
			}
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
			FitCompany company = this.fitCompanyService.findOne(order.getCompanyId());
			map.addAttribute("credit", company.getCredit());
			map.addAttribute("order", order);
			return "/fitment/pay/order_pay";
		} catch (Exception e) {
			e.printStackTrace();
			return "/fitment/500";
		}
	}

	@RequestMapping(value = "/pay/delivery/{id}")
	public String fitPayDelivery(HttpServletRequest request, ModelMap map, @PathVariable("id") Long id) {
		try {
			FitOrder order = this.bizOrderService.findOne(id);
			map.addAttribute("order", order);
			return "/fitment/pay/order_pay_delivery";
		} catch (Exception e) {
			e.printStackTrace();
			return "/fitment/500";
		}
	}

	@RequestMapping(value = "/pay/goods/{id}")
	public String fitPayGoods(HttpServletRequest request, ModelMap map, @PathVariable("id") Long id) {
		try {
			FitOrder order = this.bizOrderService.findOne(id);
			map.addAttribute("order", order);
			return "/fitment/pay/order_pay_goods";
		} catch (Exception e) {
			e.printStackTrace();
			return "/fitment/500";
		}
	}

	@RequestMapping(value = "/employee")
	public String fitEmployee(HttpServletRequest request, ModelMap map) {
		try {
			return "/fitment/employee_center";
		} catch (Exception e) {
			e.printStackTrace();
			return "/fitment/500";
		}
	}

	@RequestMapping(value = "/employee/info")
	public String fitEmployeeInfo(HttpServletRequest request, ModelMap map) {
		try {
			return "/fitment/employee_info";
		} catch (Exception e) {
			e.printStackTrace();
			return "/fitment/500";
		}
	}

	@RequestMapping(value = "/employee/password")
	public String fitEmployeePassword(HttpServletRequest request, ModelMap map) {
		try {
			return "/fitment/employee_password";
		} catch (Exception e) {
			e.printStackTrace();
			return "/fitment/500";
		}
	}

	@RequestMapping(value = "/credit")
	public String fitCredit(HttpServletRequest request, ModelMap map) {
		try {
			FitEmployee employee = this.getLoginEmployee(request);
			FitCompany company = fitCompanyService.findOne(employee.getCompanyId());
			map.addAttribute("credit", company.getCredit());
			Page<FitCreditChangeLog> logPage = this.bizCreditChangeLogService.employeeGetLog(employee,
					Global.DEFAULT_PAGE, Global.DEFAULT_SIZE);
			map.addAttribute("logPage", logPage);
			return "/fitment/credit";
		} catch (Exception e) {
			e.printStackTrace();
			return "/fitment/500";
		}
	}

	@RequestMapping(value = "/out")
	public String fitOut(HttpServletRequest request) {
		request.getSession().removeAttribute(LoginSign.EMPLOYEE_SIGN.toString());
		return "redirect:/fit";
	}

	@RequestMapping(value = "/history/{status}")
	public String fitHistory(HttpServletRequest request, ModelMap map, @PathVariable("status") Long status)
			throws Exception {
		FitEmployee employee = this.getLoginEmployee(request);
		Page<TdOrder> orderPage = null;
		if (employee.getIsMain()) {
			orderPage = tdOrderService.findBySellerUsernameOrderByOrderTimeDesc("FIT" + employee.getMobile(), status,
					Global.DEFAULT_PAGE, Global.DEFAULT_SIZE);
		} else {
			orderPage = tdOrderService.findByRealUserUsernameOrderByOrderTimeDesc("FIT" + employee.getMobile(), status,
					Global.DEFAULT_PAGE, Global.DEFAULT_SIZE);
		}
		map.addAttribute("status", status);
		map.addAttribute("orderPage", orderPage);
		return "/fitment/user_order_list";
	}

	@RequestMapping(value = "/refund/{id}")
	public String fitRefund(@PathVariable Long id, HttpServletRequest request, ModelMap map) {
		TdOrder order = tdOrderService.findOne(id);
		map.addAttribute("order", order);
		return "/fitment/user_order_refund";
	}

	@RequestMapping(value = "/refund/address/{id}/{data}")
	public String fitRefundAddress(@PathVariable("id") Long id, @PathVariable("data") String data, ModelMap map) {
		map.addAttribute("id", id);
		map.addAttribute("data", data);
		return "/fitment/user_order_refund_address_base";
	}

	@ModelAttribute
	public void modelAttrubute(HttpServletRequest request, ModelMap map) {
		FitEmployee employee = this.getLoginEmployee(request);
		map.addAttribute("employee", employee);
	}
}
