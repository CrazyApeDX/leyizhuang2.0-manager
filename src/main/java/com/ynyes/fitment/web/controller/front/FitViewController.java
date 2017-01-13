package com.ynyes.fitment.web.controller.front;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping(value = "/fit", method = RequestMethod.GET, produces = "text/html;charset=utf-8")
public class FitViewController extends FitBasicController {

	@RequestMapping
	public String fitIndex(HttpServletRequest request) {
		String identity = this.validateEmployeeLoginStatus(request);
		if (null == identity) {
			return "redirect:/fit/login";
		} else {
			return "/fitment/index";
		}
	}
	
	@RequestMapping(value = "/login")
	public String view() {
		return "/fitment/login";
	}
	
	
}
