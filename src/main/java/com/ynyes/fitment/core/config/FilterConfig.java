package com.ynyes.fitment.core.config;

import org.springframework.boot.context.embedded.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.ynyes.fitment.web.filter.FitmentFrontFilter;

@Configuration
public class FilterConfig {

	@Bean
	public FilterRegistrationBean fitFrontFilter() {
		FilterRegistrationBean bean = new FilterRegistrationBean(new FitmentFrontFilter());
		bean.addUrlPatterns("/fit/home", "/fit/cart", "/fit/address");
		return bean;
	}
}
