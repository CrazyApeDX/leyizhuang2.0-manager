package com.ynyes.lyz.util;

public class ApplicationConstant {

	public String imagePath;
	public String alipayReturnUrl;
	public String alipayReturnUrlAsnyc;
	public String wechatReturnUrlAsnyc;
	private String[] cdnHosts;
	private String ossFolder;
	private String ossBucket;
	
	public String getImagePath() {
		return imagePath;
	}
	public void setImagePath(String imagePath) {
		this.imagePath = imagePath;
		SiteMagConstant.imagePath = imagePath;
	}
	public String getAlipayReturnUrl() {
		return alipayReturnUrl;
	}
	public void setAlipayReturnUrl(String alipayReturnUrl) {
		this.alipayReturnUrl = alipayReturnUrl;
		SiteMagConstant.alipayReturnUrl = alipayReturnUrl;
	}
	public String getAlipayReturnUrlAsnyc() {
		return alipayReturnUrlAsnyc;
	}
	public void setAlipayReturnUrlAsnyc(String alipayReturnUrlAsnyc) {
		this.alipayReturnUrlAsnyc = alipayReturnUrlAsnyc;
		SiteMagConstant.alipayReturnUrlAsnyc = alipayReturnUrlAsnyc;
	}
	public String getWechatReturnUrlAsnyc() {
		return wechatReturnUrlAsnyc;
	}
	public void setWechatReturnUrlAsnyc(String wechatReturnUrlAsnyc) {
		this.wechatReturnUrlAsnyc = wechatReturnUrlAsnyc;
		SiteMagConstant.wechatReturnUrlAsnyc = wechatReturnUrlAsnyc;
	}
	public String[] getCdnHosts() {
		return cdnHosts;
	}
	public void setCdnHosts(String[] cdnHosts) {
		this.cdnHosts = cdnHosts;
		SiteMagConstant.cdnHosts = cdnHosts;
	}
	public String getOssFolder() {
		return ossFolder;
	}
	public void setOssFolder(String ossFolder) {
		this.ossFolder = ossFolder;
		SiteMagConstant.ossFolder = ossFolder;
	}
	public String getOssBucket() {
		return ossBucket;
	}
	public void setOssBucket(String ossBucket) {
		this.ossBucket = ossBucket;
		SiteMagConstant.ossBucket = ossBucket;
	}
}
