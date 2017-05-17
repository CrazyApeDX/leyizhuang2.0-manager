package com.ynyes.lyz.config;

import org.apache.cxf.transport.servlet.CXFServlet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.context.embedded.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.ynyes.lyz.interfaces.utils.InterfaceConfigure;

@Configuration
@AutoConfigureAfter(DeployConfiguration.class)
public class WebServiceConfiguration {

	@Value("${deploy.wms.url}")
	private String wmsUrl;
	
	@Value("${deploy.ebs.url}")
	private String ebsUrl;
	
	private final static Logger LOG = LoggerFactory.getLogger(WebServiceConfiguration.class);
	
	@Bean
	public ServletRegistrationBean cxfServlet() {
		LOG.info("wmsUrl : {}", wmsUrl);
		LOG.info("ebsUrl : {}", ebsUrl);
		InterfaceConfigure.WMS_WS_URL = wmsUrl;
		InterfaceConfigure.EBS_WS_URL = ebsUrl;
		CXFServlet cxfServlet = new CXFServlet();
		ServletRegistrationBean servletDef = new ServletRegistrationBean(
				cxfServlet, "/services/*");
		servletDef.setLoadOnStartup(1);
		return servletDef;
	}

	public String getWmsUrl() {
		return wmsUrl;
	}

	public void setWmsUrl(String wmsUrl) {
		this.wmsUrl = wmsUrl;
	}

	public String getEbsUrl() {
		return ebsUrl;
	}

	public void setEbsUrl(String ebsUrl) {
		this.ebsUrl = ebsUrl;
	}
}
