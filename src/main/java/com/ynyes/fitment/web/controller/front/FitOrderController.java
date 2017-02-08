package com.ynyes.fitment.web.controller.front;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ynyes.fitment.core.constant.Global;
import com.ynyes.fitment.core.entity.client.result.ClientResult;
import com.ynyes.fitment.core.entity.client.result.ClientResult.ActionCode;
import com.ynyes.fitment.foundation.entity.FitCartGoods;
import com.ynyes.fitment.foundation.entity.FitEmployee;
import com.ynyes.fitment.foundation.entity.FitOrder;
import com.ynyes.fitment.foundation.service.biz.BizCartGoodsService;
import com.ynyes.fitment.foundation.service.biz.BizOrderService;
import com.ynyes.lyz.entity.TdCity;
import com.ynyes.lyz.entity.TdDistrict;
import com.ynyes.lyz.entity.TdSubdistrict;
import com.ynyes.lyz.service.TdCityService;
import com.ynyes.lyz.service.TdDistrictService;
import com.ynyes.lyz.service.TdSubdistrictService;

@Controller
@RequestMapping(value = "/fit/order")
public class FitOrderController extends FitBasicController {

	@Autowired
	private TdCityService tdCityService;

	@Autowired
	private TdDistrictService tdDistrictService;

	@Autowired
	private TdSubdistrictService tdSubdistrictService;

	@Autowired
	private BizOrderService bizOrderService;

	@Autowired
	private BizCartGoodsService bizCartGoodsService;

	@RequestMapping(value = "/init", method = RequestMethod.POST, produces = "application/json;charset=utf-8")
	@ResponseBody
	public ClientResult fitOrderPost(HttpServletRequest request, String receiver, String receiverMobile,
			String baseAddress, String detailAddress) {
		FitEmployee employee = this.getLoginEmployee(request);
		try {
			List<FitCartGoods> cartGoodsList = this.bizCartGoodsService.loadEmployeeCart(employee.getId());
			if (CollectionUtils.isEmpty(cartGoodsList)) {
				return new ClientResult(ActionCode.FAILURE, "当前购物车中无商品，请返回并点击\"审核\"查看订单是否已经生成");
			}
			FitOrder order = this.bizOrderService.initOrder(cartGoodsList, receiver, receiverMobile, baseAddress,
					detailAddress, employee);
			if (employee.getIsMain()) {
				return new ClientResult(ActionCode.SUCCESS, order.getId());
			} else {
				return new ClientResult(ActionCode.SUCCESS, null);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return new ClientResult(ActionCode.FAILURE, "出现意外的错误，请联系管理员");
		}
	}

	@RequestMapping(value = "/load", method = RequestMethod.POST)
	public String auditLoad(HttpServletRequest request, ModelMap map, Integer page) {
		try {
			FitEmployee employee = this.getLoginEmployee(request);
			Page<FitOrder> orderPage = null;
			if (employee.getIsMain()) {
				orderPage = this.bizOrderService.loadOrderByCompanyId(employee.getCompanyId(), page + 1,
						Global.DEFAULT_SIZE);
			} else {
				orderPage = this.bizOrderService.loadOrderByEmployeeId(employee.getId(), page + 1, Global.DEFAULT_SIZE);
			}
			map.addAttribute("orderPage", orderPage);
			map.addAttribute("employee", this.getLoginEmployee(request));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "/fitment/order_audit_data";
	}

	@RequestMapping(value = "/address/district", method = RequestMethod.POST)
	public String addressDistrict(HttpServletRequest request, ModelMap map) {
		FitEmployee employee = this.getLoginEmployee(request);
		TdCity city = this.tdCityService.findByCityName(employee.getCityTitle());
		List<TdDistrict> region_list = tdDistrictService.findByCityIdOrderBySortIdAsc(city.getId());
		map.addAttribute("region_list", region_list);
		map.addAttribute("status", 1);
		return "/fitment/address_detail";
	}

	@RequestMapping(value = "/address/subdistrict", method = RequestMethod.POST)
	public String addressSubdistrict(Long id, ModelMap map) {
		List<TdSubdistrict> region_list = tdSubdistrictService.findByDistrictIdOrderBySortIdAsc(id);
		map.addAttribute("region_list", region_list);
		map.addAttribute("status", 2);
		return "/fitment/address_detail";
	}

	@RequestMapping(value = "/audit")
	@ResponseBody
	public ClientResult orderAudit(HttpServletRequest request, String action, Long id) {
		try {
			FitEmployee employee = this.getLoginEmployee(request);
			this.bizOrderService.auditOrder(id, employee, action);
			return new ClientResult(ActionCode.SUCCESS, null);
		} catch (Exception e) {
			e.printStackTrace();
			return new ClientResult(ActionCode.FAILURE, "出现意外的错误，请联系管理员");
		}
	}

	@RequestMapping(value = "/remark")
	@ResponseBody
	public ClientResult orderRemark(Long id, String remark) {
		try {
			FitOrder order = bizOrderService.findOne(id);
			this.bizOrderService.saveRemark(order, remark);
			return new ClientResult(ActionCode.SUCCESS, order.getRemark());
		} catch (Exception e) {
			e.printStackTrace();
			return new ClientResult(ActionCode.FAILURE, "出现意外的错误，请联系管理员");
		}
	}

	@RequestMapping(value = "/delivery")
	@ResponseBody
	public ClientResult orderDelivery(String deliveryDate, Long deliveryTime, Long floor, Long id) {
		try {
			FitOrder order = this.bizOrderService.findOne(id);
			this.bizOrderService.saveDelivery(order, deliveryDate, deliveryTime, floor);
			return new ClientResult(ActionCode.SUCCESS, null);
		} catch (Exception e) {
			e.printStackTrace();
			return new ClientResult(ActionCode.FAILURE, "出现意外的错误，请联系管理员");
		}
	}
}
