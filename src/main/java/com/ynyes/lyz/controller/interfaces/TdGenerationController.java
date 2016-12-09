package com.ynyes.lyz.controller.interfaces;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ynyes.lyz.entity.TdOrder;
import com.ynyes.lyz.service.TdOrderService;
import com.ynyes.lyz.service.basic.generation.IGenerationService;

@Controller
@RequestMapping(value = "/generation")
public class TdGenerationController {
	
	@Autowired
	private TdOrderService tdOrderService;
	
	@Autowired
	private IGenerationService generationService;
	
	@RequestMapping(value = "/{number}", produces = "application/json;charset=utf8")
	@ResponseBody
	public String generation(@PathVariable String number) {
		TdOrder order = tdOrderService.findByOrderNumber(number);
		String result = generationService.generateOrderData(order);
		return result;
	} 
}
