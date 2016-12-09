package com.ynyes.lyz.controller.qrcode;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ynyes.lyz.service.TdUserService;

@Controller
@RequestMapping(value = "/qrcode")
public class TdQrcodeController {

	@Autowired
	private TdUserService tdUserService;
	
	@RequestMapping(value = "/{phone}", produces = "text/html;charset=utf-8")
	public String qrcode(@PathVariable String phone) {
		
		return "/qrcode/regist";
	}
}
