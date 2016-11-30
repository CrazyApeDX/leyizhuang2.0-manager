package com.ynyes.lyz;

import javax.sql.DataSource;

import org.springframework.boot.bind.RelaxedPropertyResolver;
import org.springframework.boot.context.embedded.FilterRegistrationBean;
import org.springframework.boot.context.embedded.ServletRegistrationBean;
import org.springframework.context.EnvironmentAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.support.http.StatViewServlet;
import com.alibaba.druid.support.http.WebStatFilter;

/**
 * <p>
 * 标题：DuridConfiguration.java
 * </p>
 * <p>
 * 描述：Durid连接池配置类
 * </p>
 * 
 * @author 作者：CrazyDX
 * @version 版本：2016年10月12日下午3:44:07
 */
@Configuration
@EnableTransactionManagement
public class DuridConfiguration implements EnvironmentAware {

	private RelaxedPropertyResolver propertyResolver;

	@Override
	public void setEnvironment(Environment env) {
		this.propertyResolver = new RelaxedPropertyResolver(env, "spring.datasource.");
	}

	@Bean(destroyMethod = "close", initMethod = "init")
	public DataSource writeDataSource() {
		DruidDataSource datasource = new DruidDataSource();
		datasource.setUrl(propertyResolver.getProperty("url"));
		datasource.setDriverClassName(propertyResolver.getProperty("driver--name"));
		datasource.setUsername(propertyResolver.getProperty("username"));
		datasource.setPassword(propertyResolver.getProperty("password"));
		datasource.setInitialSize(Integer.valueOf(propertyResolver.getProperty("initialSize")));
		datasource.setMinIdle(Integer.valueOf(propertyResolver.getProperty("minIdle")));
		datasource.setMaxWait(Long.valueOf(propertyResolver.getProperty("maxWait")));
		datasource.setMaxActive(Integer.valueOf(propertyResolver.getProperty("maxActive")));
		return datasource;
	}

	// Druid Servlet配置
	@Bean
	public ServletRegistrationBean DruidStatViewServle2() {
		ServletRegistrationBean servletRegistrationBean = new ServletRegistrationBean(new StatViewServlet(),
				"/druid/*");

		// 登录查看信息的账号密码.
		servletRegistrationBean.addInitParameter("loginUsername", "magpie.it");
		servletRegistrationBean.addInitParameter("loginPassword", "it.com");
		// 是否能够重置数据.
		servletRegistrationBean.addInitParameter("resetEnable", "false");
		return servletRegistrationBean;
	}

	@Bean
	public FilterRegistrationBean druidStatFilter2() {
		FilterRegistrationBean filterRegistrationBean = new FilterRegistrationBean(new WebStatFilter());
		// 添加过滤规则.
		filterRegistrationBean.addUrlPatterns("/*");
		// 添加不需要忽略的格式信息.
		filterRegistrationBean.addInitParameter("exclusions", "*.js,*.gif,*.jpg,*.png,*.css,*.ico,/druid/*");
		return filterRegistrationBean;
	}
}
