package com.ynyes.fitment.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping(value = "/fit", produces = "text/html;charset=utf-8")
public class FitViewController {

	@RequestMapping(method = RequestMethod.GET)
	public String view() {
		return "/fitment/index";
	}
}
