package com.ynyes.fitment.web.controller.management;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ynyes.fitment.core.constant.Global;
import com.ynyes.fitment.foundation.entity.FitCompanyGoods;
import com.ynyes.fitment.foundation.service.FitCompanyGoodsService;
import com.ynyes.lyz.entity.TdGoods;
import com.ynyes.lyz.service.TdGoodsService;

@Controller
@RequestMapping(value = "/Verwalter/fitment/goods")
public class FitManagementGoodsController {

	@Autowired
	private TdGoodsService tdGoodsService;

	@Autowired
	private FitCompanyGoodsService fitCompanyGoodsService;

	@RequestMapping(value = "/list/{companyId}", produces = "text/html;chatset=utf-8")
	public String goodsList(ModelMap map, Integer page, Integer size, @PathVariable("companyId") Long companyId,
			String __EVENTTARGET, String __EVENTARGUMENT, String __VIEWSTATE) {

		if (null != __EVENTTARGET) {
			switch (__EVENTTARGET) {
			case "btnPage":
				if (null != __EVENTARGUMENT) {
					page = Integer.parseInt(__EVENTARGUMENT);
				}
				break;
			}
		}

		Page<FitCompanyGoods> goodsPage = null;
		page = null == page ? Global.DEFAULT_PAGE : page;
		size = null == size ? Global.DEFAULT_SIZE : size;
		try {
			goodsPage = this.fitCompanyGoodsService.findByCompanyIdOrderByGoodsSortIdAsc(companyId, page, size);
		} catch (Exception e) {
			e.printStackTrace();
		}
		map.addAttribute("goodsPage", goodsPage);
		map.addAttribute("companyId", companyId);
		map.addAttribute("page", page);
		map.addAttribute("size", size);
		return "/fitment/management/company_goods_list";
	}

	@RequestMapping(value = "/add", method = RequestMethod.POST, produces = "application/json;charset=utf-8")
	@ResponseBody
	public Map<String, Object> goodsAdd(Long companyId, String sku) {
		Map<String, Object> res = new HashMap<>();
		res.put("status", -1);
		TdGoods goods = tdGoodsService.findByCode(sku);
		if (null == goods) {
			res.put("message", "该SKU的商品不存在");
			return res;
		} else {
			try {
				Boolean validate = this.fitCompanyGoodsService.validateRepeatByCompanyIdAndGoodsId(companyId,
						goods.getId());
				if (validate) {
					res.put("message", "该SKU的商品已经存在，不能重复添加");
				} else {
					this.fitCompanyGoodsService.managerAddCompanyGoods(goods, companyId);
					res.put("status", 0);
				}
			} catch (Exception e) {
				res.put("message", "出现了意外的错误，请稍后重试或联系管理员");
				e.printStackTrace();
			}
			return res;
		}
	}

	@RequestMapping(value = "/init/{companyId}", method = RequestMethod.GET)
	public String companyGoodsInit(@PathVariable("companyId") Long companyId) {
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
			this.fitCompanyGoodsService.initCompanyGoodsByPriceLine(companyId);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "redirect:/Verwalter/fitment/goods/list/" + companyId;
	}

	@RequestMapping(value = "/delete/{id}/{companyId}", method = RequestMethod.GET)
	public String companyDelete(@PathVariable("id") Long id, @PathVariable("companyId") Long companyId) {
		try {
			this.fitCompanyGoodsService.delete(id);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "redirect:/Verwalter/fitment/goods/list/" + companyId;
	}
}
