package com.ynyes.lyz.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.ynyes.lyz.util.ApplicationConstant;

@Configuration
public class DeployConfiguration {
	
	private final Logger LOG = LoggerFactory.getLogger(DeployConfiguration.class);
	
	@Value("${deploy.image-path}")
	private String imagePath;
	
	@Value("${deploy.alipay.url}")
	private String alipayReturnUrl;
	
	@Value("${deploy.alipay.async.url}")
	private String alipayReturnUrlAsync;
	
	@Value("${deploy.wechat.async.url}")
	private String wechatReturnUrlAsnyc;
	
	@Bean
	public ApplicationConstant applicationConstant() {
		LOG.info("imagePath : {}", imagePath);
		LOG.info("alipayReturnUrl : {}", alipayReturnUrl);
		LOG.info("alipayReturnUrlAsync : {}", alipayReturnUrlAsync);
		LOG.info("wechatReturnUrlAsnyc : {}", wechatReturnUrlAsnyc);
		ApplicationConstant constant = new ApplicationConstant();
		constant.setImagePath(imagePath);
		constant.setAlipayReturnUrl(alipayReturnUrl);
		constant.setAlipayReturnUrlAsnyc(alipayReturnUrlAsync);
		constant.setWechatReturnUrlAsnyc(wechatReturnUrlAsnyc);
		return constant;
	}

	public String getImagePath() {
		return imagePath;
	}

	public void setImagePath(String imagePath) {
		this.imagePath = imagePath;
	}

	public String getAlipayReturnUrl() {
		return alipayReturnUrl;
	}

	public void setAlipayReturnUrl(String alipayReturnUrl) {
		this.alipayReturnUrl = alipayReturnUrl;
	}

	public String getAlipayReturnUrlAsync() {
		return alipayReturnUrlAsync;
	}

	public void setAlipayReturnUrlAsync(String alipayReturnUrlAsync) {
		this.alipayReturnUrlAsync = alipayReturnUrlAsync;
	}

	public String getWechatReturnUrlAsnyc() {
		return wechatReturnUrlAsnyc;
	}

	public void setWechatReturnUrlAsnyc(String wechatReturnUrlAsnyc) {
		this.wechatReturnUrlAsnyc = wechatReturnUrlAsnyc;
	}
}