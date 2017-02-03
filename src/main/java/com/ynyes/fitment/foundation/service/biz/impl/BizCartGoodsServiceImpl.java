package com.ynyes.fitment.foundation.service.biz.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ynyes.fitment.core.entity.client.result.ClientResult;
import com.ynyes.fitment.core.entity.client.result.ClientResult.ActionCode;
import com.ynyes.fitment.core.entity.persistent.table.TableEntity.OriginType;
import com.ynyes.fitment.foundation.entity.FitCartGoods;
import com.ynyes.fitment.foundation.entity.FitEmployee;
import com.ynyes.fitment.foundation.service.FitCartGoodsService;
import com.ynyes.fitment.foundation.service.biz.BizCartGoodsService;
import com.ynyes.fitment.foundation.service.biz.BizInventoryService;
import com.ynyes.fitment.foundation.service.biz.BizPriceService;
import com.ynyes.lyz.entity.TdGoods;
import com.ynyes.lyz.service.TdGoodsService;

@Service
@Transactional
public class BizCartGoodsServiceImpl implements BizCartGoodsService {

	@Autowired
	private TdGoodsService tdGoodsService;

	@Autowired
	private FitCartGoodsService fitCartGoodsService;

	@Autowired
	private BizPriceService bizPriceService;

	@Autowired
	private BizInventoryService bizInventoryService;

	@Override
	public void addToCart(String params, FitEmployee employee) throws Exception {
		String[] param = params.split("\\-");
		for (int i = 0; i < param.length; i++) {
			String item = param[i];
			String[] goodsId_quantity = item.split("\\+");
			Long goodsId = Long.valueOf(goodsId_quantity[0]);
			Long quantity = Long.valueOf(goodsId_quantity[1]);
			TdGoods goods = this.tdGoodsService.findOne(goodsId);
			FitCartGoods cartGoods = this.fitCartGoodsService.findByEmployeeIdAndGoodsId(employee.getId(), goodsId);
			if (null == cartGoods) {
				cartGoods = new FitCartGoods().setEmployeeId(employee.getId()).setGoodsId(goodsId)
						.setGoodsTitle(goods.getTitle()).setGoodsSku(goods.getCode())
						.setImageUri(goods.getCoverImageUri())
						.setPrice(bizPriceService.getPriceByCompanyIdAndGoodsId(employee.getCompanyId(), goodsId))
						.setQuantity(quantity);
				cartGoods.setCreateOrigin(OriginType.BUSINESS);
			} else {
				Long inventory = this.bizInventoryService.getCityInventoryByGoodsId(employee.getCompanyId(), goodsId);

				cartGoods = cartGoods.setEmployeeId(employee.getId()).setGoodsTitle(goods.getTitle())
						.setGoodsId(goodsId).setGoodsSku(goods.getCode()).setImageUri(goods.getCoverImageUri())
						.setPrice(bizPriceService.getPriceByCompanyIdAndGoodsId(employee.getCompanyId(), goodsId))
						.setQuantity(cartGoods.getQuantity() + quantity > inventory ? inventory
								: cartGoods.getQuantity() + quantity);
			}
			cartGoods.getTotalPrice();
			this.fitCartGoodsService.save(cartGoods);
		}
	}

	@Override
	public void clearCartWithId(Long id) throws Exception {
		this.fitCartGoodsService.deleteById(id);
	}

	@Override
	public Long getSelectedCounts(Long employeeId) throws Exception {
		return this.fitCartGoodsService.countByEmployeeId(employeeId);
	}

	@Override
	public List<FitCartGoods> loadEmployeeCart(Long employeeId) throws Exception {
		return this.fitCartGoodsService.findByEmployeeId(employeeId);
	}

	@Override
	public void changeCartGoodsQuantity(String operation, FitCartGoods cart, Long quantity, Long inventory)
			throws Exception {
		Long selected = cart.getQuantity();
		switch (operation) {
		case "add":
			if (selected >= inventory) {
				cart.setQuantity(inventory);
			} else {
				cart.setQuantity(cart.getQuantity() + 1L);
			}
			break;
		case "delete":
			if (selected.equals(1L)) {
				// do nothing!
			} else {
				cart.setQuantity(cart.getQuantity() - 1L);
			}
			break;
		case "key":
			if (quantity > inventory) {
				cart.setQuantity(inventory);
				this.fitCartGoodsService.save(cart);
			} else if (quantity < 1) {
				cart.setQuantity(1L);
				this.fitCartGoodsService.save(cart);
			}
			break;
		}
		this.fitCartGoodsService.save(cart);
	}

	@Override
	public FitCartGoods getCartGoodsByGoodsId(Long employeeId, Long goodsId) throws Exception {
		return this.fitCartGoodsService.findByEmployeeIdAndGoodsId(employeeId, goodsId);
	}

	@Override
	public ClientResult deleteCartGoods(Long employeeId, Long goodsId) throws Exception {
		this.fitCartGoodsService.deleteByGoodsIdAndEmployeeId(goodsId, employeeId);
		Double totalPrice = this.fitCartGoodsService.getTotalCartGoodsPriceByEmployeeId(employeeId);
		return new ClientResult(ActionCode.SUCCESS, null == totalPrice ? 0d : totalPrice);
	}

}
