package com.ynyes.fitment.web.controller.management;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.ynyes.fitment.foundation.service.FitCompanyGoodsService;

@Controller
@RequestMapping(value = "/Verwalter/fitment/inventory")
public class FitManagementInventoryController extends FitManagementBasicController {

	@Autowired
	private FitCompanyGoodsService fitCompanyGoodsService;

	@RequestMapping(value = "/init/{companyId}", method = RequestMethod.POST)
	public String initInventory(@PathVariable Long companyId) {
		try {
			// List<TdGoods> goodsList = this.tdGoodsService.findAll();
			// for (TdGoods goods : goodsList) {
			// Boolean validate =
			// this.fitCompanyGoodsService.validateRepeatByCompanyIdAndGoodsId(companyId,
			// goods.getId());
			// if (!validate) {
			// this.fitCompanyGoodsService.managerAddCompanyGoods(goods,
			// companyId);
			// }
			// }
			this.fitCompanyGoodsService.initInventoryByCompanyId(companyId);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "redirect:/Verwalter/fitment/goods/list/" + companyId;
	}
}
