package com.ynyes.lyz.util;

import com.ynyes.lyz.interfaces.utils.InterfaceConfigure;

public class ApplicationConstant {

	public String imagePath;
	public String alipayReturnUrl;
	public String alipayReturnUrlAsnyc;
	public String wechatReturnUrlAsnyc;
	private String[] cdnHosts;
	private String ossFolder;
	private String ossBucket;
	private String wmsUrl;
	private String ebsUrl;
	
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
	public String getWmsUrl() {
		return wmsUrl;
	}
	public void setWmsUrl(String wmsUrl) {
		this.wmsUrl = wmsUrl;
		InterfaceConfigure.WMS_WS_URL = wmsUrl;
	}
	public String getEbsUrl() {
		return ebsUrl;
	}
	public void setEbsUrl(String ebsUrl) {
		this.ebsUrl = ebsUrl;
		InterfaceConfigure.EBS_WS_URL = ebsUrl;
	}
}
