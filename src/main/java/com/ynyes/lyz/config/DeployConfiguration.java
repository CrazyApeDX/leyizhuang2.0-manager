package com.ynyes.lyz.config;

import java.util.Arrays;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.ynyes.lyz.util.ApplicationConstant;

@Configuration
@AutoConfigureBefore(WebServiceConfiguration.class)
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
	
	@Value("${deploy.oss.cdnHosts}")
	private String[] cdnHosts;

	@Value("${deploy.oss.folder}")
	private String ossFolder;

	@Value("${deploy.oss.bucket}")
	private String ossBucket;
	
	@Bean
	public ApplicationConstant applicationConstant() {
		LOG.info("imagePath : {}", imagePath);
		LOG.info("alipayReturnUrl : {}", alipayReturnUrl);
		LOG.info("alipayReturnUrlAsync : {}", alipayReturnUrlAsync);
		LOG.info("wechatReturnUrlAsnyc : {}", wechatReturnUrlAsnyc);
		LOG.info("cdnHosts : {}", Arrays.toString(cdnHosts));
		LOG.info("ossFolder : {}", ossFolder);
		LOG.info("ossBucket : {}", ossBucket);

		ApplicationConstant constant = new ApplicationConstant();
		constant.setImagePath(imagePath);
		constant.setAlipayReturnUrl(alipayReturnUrl);
		constant.setAlipayReturnUrlAsnyc(alipayReturnUrlAsync);
		constant.setWechatReturnUrlAsnyc(wechatReturnUrlAsnyc);
		constant.setCdnHosts(cdnHosts);
		constant.setOssFolder(ossFolder);
		constant.setOssBucket(ossBucket);
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

	public String[] getCdnHosts() {
		return cdnHosts;
	}

	public void setCdnHosts(String[] cdnHosts) {
		this.cdnHosts = cdnHosts;
	}

	public String getOssFolder() {
		return ossFolder;
	}

	public void setOssFolder(String ossFolder) {
		this.ossFolder = ossFolder;
	}

	public String getOssBucket() {
		return ossBucket;
	}

	public void setOssBucket(String ossBucket) {
		this.ossBucket = ossBucket;
	}
}