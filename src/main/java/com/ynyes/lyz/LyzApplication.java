package com.ynyes.lyz;

import javax.servlet.MultipartConfigElement;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.context.embedded.MultipartConfigFactory;
import org.springframework.boot.context.embedded.ServletRegistrationBean;
import org.springframework.boot.context.web.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.filter.CharacterEncodingFilter;

@Configuration
@ComponentScan
@EnableAutoConfiguration
@EnableScheduling
@ImportResource({ "/cxf-servlet.xml" })
public class LyzApplication extends SpringBootServletInitializer implements
		CommandLineRunner {

	@Bean
	public CharacterEncodingFilter encodingFilter() {
		CharacterEncodingFilter filter = new CharacterEncodingFilter();
		filter.setEncoding("UTF-8");
		filter.setForceEncoding(true);
		return filter;
	}
	
	@Bean
	MultipartConfigElement multipartConfigElement() {
		MultipartConfigFactory factory = new MultipartConfigFactory();
		factory.setMaxFileSize("10MB");
		factory.setMaxRequestSize("10MB");
		return factory.createMultipartConfig();
	}

	@Bean
	public ServletRegistrationBean cxfServlet() {
		org.apache.cxf.transport.servlet.CXFServlet cxfServlet = new org.apache.cxf.transport.servlet.CXFServlet();
		ServletRegistrationBean servletDef = new ServletRegistrationBean(
				cxfServlet, "/services/*");
		servletDef.setLoadOnStartup(1);
		return servletDef;
	}

	public static void main(String[] args) {
//		Runtime runtime = Runtime.getRuntime();
//		long freeMemory = runtime.freeMemory();
//		long totalMemory = runtime.totalMemory();
//		long maxMemory = runtime.maxMemory();
//		freeMemory = freeMemory/1024/1024;
//		totalMemory = totalMemory/1024/1024;
//		maxMemory = maxMemory/1024/1024;
//		System.out.println("-------------------------->---------------->当前 Java 虚拟机中的总内存量：" + totalMemory + " 兆");
//		System.out.println("-------------------------->---------------->当前 Java 虚拟机中的最大内存量：" + maxMemory + " 兆");
//		System.out.println("-------------------------->---------------->当前 Java 虚拟机中的空闲内存量：" + freeMemory + " 兆");
		SpringApplication.run(LyzApplication.class, args);
	}

	@Override
	public void run(String... arg0) throws Exception {
		// Endpoint.publish("/AddService", new BookVO());
	}
	
}