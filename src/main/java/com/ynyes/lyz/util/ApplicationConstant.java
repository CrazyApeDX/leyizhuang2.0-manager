package com.ynyes.lyz.util;

public class ApplicationConstant {

	public String imagePath;
	public String alipayReturnUrl;
	public String alipayReturnUrlAsnyc;
	public String wechatReturnUrlAsnyc;
	
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
}
