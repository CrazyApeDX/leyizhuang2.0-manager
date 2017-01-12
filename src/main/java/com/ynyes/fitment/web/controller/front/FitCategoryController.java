package com.ynyes.fitment.web.controller.front;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ynyes.fitment.foundation.service.FitCompanyCategoryService;

@RestController
@RequestMapping(value = "/fit/category", produces = "application/json;charset=utf-8")
public class FitCategoryController {

	private final Logger LOGGER = LoggerFactory.getLogger(FitCategoryController.class);
	
	@Autowired
	private FitCompanyCategoryService fitCompanyCategoryService;
	
}
