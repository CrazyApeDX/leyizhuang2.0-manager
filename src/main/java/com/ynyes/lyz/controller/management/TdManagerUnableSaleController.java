package com.ynyes.lyz.controller.management;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.ynyes.lyz.entity.TdDiySite;
import com.ynyes.lyz.entity.TdGoods;
import com.ynyes.lyz.entity.goods.TdUnableSale;
import com.ynyes.lyz.service.TdDiySiteService;
import com.ynyes.lyz.service.TdGoodsService;
import com.ynyes.lyz.service.TdManagerLogService;
import com.ynyes.lyz.service.TdUnableSaleService;
import com.ynyes.lyz.util.SiteMagConstant;

/**
 * <p>
 * 标题：TdManagerUnableSaleController.java
 * </p>
 * <p>
 * 描述：城市限购CMS控制器
 * </p>
 * 
 * @author 作者：CrazyDX
 * @version 版本：2016年10月21日上午9:36:59
 */
@Controller
@RequestMapping(value = "/Verwalter/unable/sale")
public class TdManagerUnableSaleController {

	@Autowired
	private TdDiySiteService tdDiySiteService;

	@Autowired
	private TdUnableSaleService tdUnableSaleService;

	@Autowired
	private TdGoodsService tdGoodsService;
	
	@Autowired
	private TdManagerLogService tdManagerLogService;

	@RequestMapping(value = "/list")
	public String unableSaleList(String __EVENTTARGET, String __EVENTARGUMENT, String __VIEWSTATE, Long[] listId,
			Integer[] listChkId, Double[] listSortId, ModelMap map, HttpServletRequest req, String diyCode,
			String keywords, Integer page, Integer size) {
		String username = (String) req.getSession().getAttribute("manager");
		if (null == username) {
			return "redirect:/Verwalter/login";
		}
		
		if (null == page) {
			page = 0;
		}
		if (null == size) {
			size = SiteMagConstant.pageSize;
		}
		
		if (null != __EVENTTARGET) {
			switch (__EVENTTARGET) {
			case "lbtnViewTxt":
			case "lbtnViewImg":
				__VIEWSTATE = __EVENTTARGET;
				break;

			case "btnDelete":
				btnDelete(listId, listChkId);
				tdManagerLogService.addLog("delete", "用户删除门店限购", req);
				break;

			case "btnPage":
				if (null != __EVENTARGUMENT) {
					page = Integer.parseInt(__EVENTARGUMENT);
				}
				break;
			}
		}
		
		List<TdDiySite> diySiteList = tdDiySiteService.findAll();
		
		Page<TdUnableSale> unableSalePage = tdUnableSaleService.findAllByWhere(diyCode, keywords, page, size);
		map.addAttribute("unableSalePage", unableSalePage);
		map.addAttribute("diySiteList", diySiteList);
		map.addAttribute("page", page);
		map.addAttribute("size", size);
		map.addAttribute("diyCode", diyCode);
		map.addAttribute("keywords", keywords);
		return "/site_mag/unable_sale_list";
	}

	@RequestMapping(value = "/edit")
	public String unableSaleEdit(HttpServletRequest req, ModelMap map, Long id) {
		String username = (String) req.getSession().getAttribute("manager");
		if (null == username) {
			return "redirect:/Verwalter/login";
		}
		
		if (null != id) {
			TdUnableSale item = tdUnableSaleService.findOne(id);
			map.addAttribute("item", item);
			if (null != item) {
				Long goodsId = item.getGoodsId();
				TdGoods goods = tdGoodsService.findOne(goodsId);
				List<TdGoods> goodsList = new ArrayList<>();
				if (null != goods) {
					goodsList.add(goods);
					map.addAttribute("goodsList", goodsList);
				}
			}
		}
		
		List<TdDiySite> diySiteList = tdDiySiteService.findAll();
		map.addAttribute("diySiteList", diySiteList);
		return "/site_mag/unable_sale_edit";
	}
	
	@RequestMapping(value = "/search", method = RequestMethod.POST)
	public String unableSaleSearch(String keywords, ModelMap map) {
		List<TdGoods> goodsList = tdGoodsService.findByCodeContaining(keywords);
		map.addAttribute("goodsList", goodsList);
		return "/site_mag/unable_sale_goods";
	}
	
	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public String unableSaleSave(HttpServletRequest req, ModelMap map, String diySiteCode, Long goodsId ) {
		String username = (String) req.getSession().getAttribute("manager");
		if (null == username) {
			return "redirect:/Verwalter/login";
		}
		
		TdUnableSale sale = new TdUnableSale();
		TdGoods goods = tdGoodsService.findOne(goodsId);
		TdDiySite diySite = tdDiySiteService.findByStoreCode(diySiteCode);
		if (null != goods) {
			sale.setGoodsId(goods.getId());
			sale.setGoodsSku(goods.getCode());
		}
		if (null != diySite) {
			sale.setDiySiteCode(diySite.getStoreCode());
			sale.setDiySiteId(diySite.getId());
		}
		
		tdUnableSaleService.save(sale);
		return "redirect:/Verwalter/unable/sale/list";
	}
	
	private void btnDelete(Long[] ids, Integer[] chkIds) {
		if (null == ids || null == chkIds || ids.length < 1 || chkIds.length < 1) {
			return;
		}

		for (int chkId : chkIds) {
			if (chkId >= 0 && ids.length > chkId) {
				Long id = ids[chkId];

				tdUnableSaleService.delete(id);
			}
		}
	}
}
