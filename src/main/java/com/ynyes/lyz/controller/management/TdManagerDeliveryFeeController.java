package com.ynyes.lyz.controller.management;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ynyes.lyz.entity.TdCity;
import com.ynyes.lyz.entity.TdGoods;
import com.ynyes.lyz.entity.delivery.TdDeliveryFeeHead;
import com.ynyes.lyz.entity.delivery.TdDeliveryFeeLine;
import com.ynyes.lyz.service.TdCityService;
import com.ynyes.lyz.service.TdDeliveryFeeHeadService;
import com.ynyes.lyz.service.TdDeliveryFeeLineService;
import com.ynyes.lyz.service.TdGoodsService;
import com.ynyes.lyz.util.SiteMagConstant;

@Controller
@RequestMapping("/Verwalter/delivery/fee")
public class TdManagerDeliveryFeeController {

	@Autowired
	private TdCityService tdCityService;

	@Autowired
	private TdGoodsService tdGoodsService;

	@Autowired
	private TdDeliveryFeeHeadService tdDeliveryFeeHeadService;

	@Autowired
	private TdDeliveryFeeLineService tdDeliveryFeeLineService;

	@RequestMapping(value = "/head/{sobId}", produces = "text/html;charset=utf-8")
	public String deliveryFeeHeadGet(HttpServletRequest request, ModelMap map, @PathVariable("sobId") Long sobId,
			String keywords, Integer page, Integer size, String __EVENTTARGET, String __EVENTARGUMENT,
			String __VIEWSTATE, Long[] listId, Integer[] listChkId) {
		String username = (String) request.getSession().getAttribute("manager");
		if (null == username) {
			return "redirect:/Verwalter/login";
		}

		if (null != __EVENTTARGET) {
			if (__EVENTTARGET.equalsIgnoreCase("btnPage")) {
				if (null != __EVENTARGUMENT) {
					page = Integer.parseInt(__EVENTARGUMENT);
				}
			} else if (__EVENTTARGET.equalsIgnoreCase("btnDelete")) {
				btnDelete(listId, listChkId);
			}
		}

		if (null == page || page < 0) {
			page = 0;
		}

		if (null == size || size <= 0) {
			size = SiteMagConstant.pageSize;
		}

		TdCity city = tdCityService.findBySobIdCity(sobId);
		if (null != city) {
			map.addAttribute("cityName", city.getCityName());
		}

		Page<TdDeliveryFeeHead> deliveryFeeHeadPage = null;
		if (StringUtils.isEmpty(keywords)) {
			deliveryFeeHeadPage = tdDeliveryFeeHeadService.findBySobId(sobId, page, size);
		} else {
			deliveryFeeHeadPage = tdDeliveryFeeHeadService.findBySobIdAndGoodsSkuContaining(sobId, keywords, page,
					size);
		}

		map.addAttribute("deliveryFeeHeadPage", deliveryFeeHeadPage);
		map.addAttribute("sobId", sobId);
		map.addAttribute("page", page);
		map.addAttribute("size", size);
		map.addAttribute("keywords", keywords);

		return "/site_mag/delivery_fee_head_list";
	}

	@RequestMapping(value = "/head/add/{sobId}", method = RequestMethod.GET, produces = "text/html;charset=utf-8")
	public String deliveryFeeHeadAddGet(HttpServletRequest request, ModelMap map, @PathVariable("sobId") Long sobId) {
		String username = (String) request.getSession().getAttribute("manager");
		if (null == username) {
			return "redirect:/Verwalter/login";
		}

		TdCity city = tdCityService.findBySobIdCity(sobId);
		if (null != city) {
			map.addAttribute("cityName", city.getCityName());
		}

		map.addAttribute("sobId", sobId);
		return "/site_mag/delivery_fee_head_add";
	}

	@RequestMapping(value = "/head/init/{sobId}", method = RequestMethod.GET)
	public String deliveryFeeHeadInitGet(@PathVariable Long sobId) {
		List<TdGoods> goodsList = tdGoodsService.findAll();
		if (null != goodsList && goodsList.size() > 0) {
			for (TdGoods goods : goodsList) {
				TdDeliveryFeeHead existsHead = tdDeliveryFeeHeadService.findBySobIdAndGoodsId(sobId, goods.getId());
				if (null == existsHead) {
					TdDeliveryFeeHead head = new TdDeliveryFeeHead();
					head.setSobId(sobId);
					head.setGoodsId(goods.getId());
					head.setGoodsTitle(goods.getTitle());
					head.setGoodsSku(goods.getCode());
					tdDeliveryFeeHeadService.save(head);
				}
			}
		}
		return "redirect:/Verwalter/delivery/fee/head/" + sobId;
	}

	@RequestMapping(value = "/head/goods", method = RequestMethod.POST, produces = "text/html;charset=utf-8")
	public String deliveryFeeHeadGoods(ModelMap map, String keywords) {
		List<TdGoods> goodsList = tdGoodsService.findByCodeContaining(keywords);
		map.addAttribute("goodsList", goodsList);
		return "/site_mag/delivery_fee_head_goods";
	}

	@RequestMapping(value = "/head/save", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deliveryFeeHeadSave(Long sobId, Long goodsId) {
		Map<String, Object> res = new HashMap<>();

		Integer count = tdDeliveryFeeHeadService.countBySobIdAndGoodsId(sobId, goodsId);
		if (!count.equals(0)) {
			res.put("status", -1);
			res.put("message", "该城市已经存在当前SKU的运费配置，操作失败");
		} else {
			TdGoods goods = tdGoodsService.findOne(goodsId);

			TdDeliveryFeeHead head = new TdDeliveryFeeHead();
			head.setSobId(sobId);
			head.setGoodsId(goods.getId());
			head.setGoodsTitle(goods.getTitle());
			head.setGoodsSku(goods.getCode());
			tdDeliveryFeeHeadService.save(head);

			res.put("status", 0);
			res.put("message", "添加成功");
		}
		return res;
	}

	@RequestMapping(value = "/line/{sobId}/{headId}", produces = "text/html;charset=utf-8")
	public String deliveryFeeLineSoIdHeadId(HttpServletRequest request, ModelMap map, @PathVariable("sobId") Long sobId,
			@PathVariable("headId") Long headId, Integer page, Integer size, String __EVENTTARGET,
			String __EVENTARGUMENT, String __VIEWSTATE, Long[] listId, Integer[] listChkId) {
		String username = (String) request.getSession().getAttribute("manager");
		if (null == username) {
			return "redirect:/Verwalter/login";
		}

		if (null != __EVENTTARGET) {
			if (__EVENTTARGET.equalsIgnoreCase("btnPage")) {
				if (null != __EVENTARGUMENT) {
					page = Integer.parseInt(__EVENTARGUMENT);
				}
			} else if (__EVENTTARGET.equalsIgnoreCase("btnDelete")) {
				btnDelete(listId, listChkId);
			}
		}

		if (null == page || page < 0) {
			page = 0;
		}

		if (null == size || size <= 0) {
			size = SiteMagConstant.pageSize;
		}

		TdCity city = tdCityService.findBySobIdCity(sobId);
		if (null != city) {
			map.addAttribute("cityName", city.getCityName());
		}

		Page<TdDeliveryFeeLine> deliveryFeeLinePage = tdDeliveryFeeLineService.findByHeadId(headId, page, size);

		map.addAttribute("deliveryFeeLinePage", deliveryFeeLinePage);
		map.addAttribute("sobId", sobId);
		map.addAttribute("headId", headId);
		map.addAttribute("page", page);
		map.addAttribute("size", size);

		return "/site_mag/delivery_fee_line_list";
	}

	@RequestMapping(value = "/line/edit/{headId}/{lineId}", method = RequestMethod.GET, produces = "text/html;charset=utf-8")
	public String deliveryFeeLineEditHeadIdLineIdGet(HttpServletRequest request, ModelMap map,
			@PathVariable Long headId, @PathVariable Long lineId) {
		String username = (String) request.getSession().getAttribute("manager");
		if (null == username) {
			return "redirect:/Verwalter/login";
		}

		TdDeliveryFeeLine deliveryFeeLine = tdDeliveryFeeLineService.findOne(lineId);
		map.addAttribute("headId", headId);
		map.addAttribute("lineId", lineId);
		map.addAttribute("deliveryFeeLine", deliveryFeeLine);

		return "/site_mag/delivery_fee_line_edit";
	}

	@RequestMapping(value = "/line/edit/{headId}/{lineId}", method = RequestMethod.POST, produces = "application/json;charset=utf-8")
	@ResponseBody
	public Map<String, Object> deliveryFeeLineEditHeadIdLineId(Long headId, Long lineId, Long minNumber, Long maxNumber,
			Double unit, Double fixedPrice) {
		Map<String, Object> res = new HashMap<>();
		TdDeliveryFeeLine line = null;
		if (null == lineId) {
			line = new TdDeliveryFeeLine();
		} else {
			line = tdDeliveryFeeLineService.findOne(lineId);
			line = null == line ? new TdDeliveryFeeLine() : line;
		}

		List<TdDeliveryFeeLine> lineList = tdDeliveryFeeLineService.findByHeadId(headId);
		Boolean repeat = false;
		if (null != lineList && lineList.size() > 0) {
			for (TdDeliveryFeeLine item : lineList) {
				if (item.getId().equals(lineId)) {
					break;
				}
				if (minNumber <= item.getMaxNumber()) {
					repeat = true;
					break;
				} else if (maxNumber <= item.getMinNumber()) {
					repeat = true;
					break;
				} else {
					// do nothing
				}
			}
		}
		if (repeat) {
			res.put("status", -1);
			res.put("message", "数量区间有重复，操作失败");
		} else {
			line.setHeadId(headId);
			line.setMinNumber(minNumber);
			line.setMaxNumber(maxNumber);
			line.setUnit(unit);
			line.setFixedPrice(fixedPrice);
			tdDeliveryFeeLineService.save(line);
			res.put("status", 0);
			res.put("message", "操作成功");
		}

		return res;
	}

	private void btnDelete(Long[] ids, Integer[] chkIds) {
		if (null == ids || null == chkIds || ids.length < 1 || chkIds.length < 1) {
			return;
		}

		for (int chkId : chkIds) {
			if (chkId >= 0 && ids.length > chkId) {
				Long id = ids[chkId];
				tdDeliveryFeeHeadService.delete(id);
			}
		}
	}
}
