package com.common.zookeeper;

import java.io.IOException;
import java.io.InputStream;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.core.io.AbstractResource;

public class ZookeeperResource extends AbstractResource implements ApplicationContextAware, DisposableBean {
	private String zkPath;

	public String getZkPath() {
		return zkPath;
	}

	public void setZkPath(String zkPath) {
		this.zkPath = zkPath;
	}

	@Override
	public String getDescription() {
		final String desc = "zookeeper resouce at:" + zkPath;
		return desc;
	}

	@Override
	public InputStream getInputStream() throws IOException {
		return ZkTool.getInstance().getInputStream(zkPath);
	}

	@Override
	public void destroy() throws Exception {
	}

	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
	}

}
