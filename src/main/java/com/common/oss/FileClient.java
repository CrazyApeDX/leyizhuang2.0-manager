package com.common.oss;

import com.aliyun.oss.OSSClient;

public class FileClient {

	private static final String ACCESS_KEY_ID = "LTAIwF4A6ScfhcYW";

	private static final String ACCESS_KEY_SECRET = "FdKQEvgLQWsyO0C3mg6WcJc3mzjAAi";

	private static final String END_POINT = "http://oss-cn-shenzhen.aliyuncs.com";

	private OSSClient ossClient;

	private static FileClient instance = null;

	private FileClient() {
		super();
		ossClient = new OSSClient(END_POINT, ACCESS_KEY_ID, ACCESS_KEY_SECRET);
	}
	
	public static FileClient getInstance() {
		if (null == instance) {
			synchronized (FileClient.class) {
				instance = null == instance ? new FileClient() : instance;
			}
		}
		return instance;
	}

	
}
