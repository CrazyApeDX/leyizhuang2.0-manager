package com.ynyes.lyz.controller;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ynyes.lyz.interfaces.utils.InterfaceConfigure;
import com.ynyes.lyz.util.ApplicationConstant;

@Controller
@RequestMapping(value = "/application/env")
public class EnvironmentController {

	private static final Logger LOG = LoggerFactory.getLogger(EnvironmentController.class);
	
	@Autowired
	private ApplicationConstant applicationConstant;
	
	@RequestMapping
	@ResponseBody
	public Map<String, String> env() {
		LOG.info("imagePath : {}", applicationConstant.imagePath);
		LOG.info("alipayReturnUrl : {}", applicationConstant.alipayReturnUrl);
		LOG.info("alipayReturnUrlAsync : {}", applicationConstant.alipayReturnUrlAsnyc);
		LOG.info("wechatReturnUrlAsnyc : {}", applicationConstant.wechatReturnUrlAsnyc);
		LOG.info("cdnHosts : {}", Arrays.toString(applicationConstant.getCdnHosts()));
		LOG.info("ossFolder : {}", applicationConstant.getOssFolder());
		LOG.info("ossBucket : {}", applicationConstant.getOssBucket());
		LOG.info("wmsUrl : {}", InterfaceConfigure.WMS_WS_URL);
		LOG.info("ebsUrl : {}", InterfaceConfigure.EBS_WS_URL);
		Map<String, String> result = new HashMap<>();
		result.put("status", "OK");
		return result;
	}
}
