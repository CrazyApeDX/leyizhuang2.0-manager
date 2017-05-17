package com.ynyes.lyz.controller.front;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(value = "/")
public class RedirectController {

	@RequestMapping
	public String redirect() {
		return "redirect:/Verwalter";
	}
}
