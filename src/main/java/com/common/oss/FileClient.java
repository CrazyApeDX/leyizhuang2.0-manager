package com.common.oss;

import com.aliyun.oss.OSSClient;

public class FileClient {
	
	public static final String ACCESS_KEY_ID = "LTAIwF4A6ScfhcYW";

	public static final String ACCESS_KEY_SECRET = "FdKQEvgLQWsyO0C3mg6WcJc3mzjAAi";

	public static final String END_POINT = "http://oss-cn-shenzhen.aliyuncs.com";

	private static FileClient instance = null;

	private OSSClient ossClient = null;

	private FileClient() {
		ossClient = new OSSClient(END_POINT, ACCESS_KEY_ID, ACCESS_KEY_SECRET);
	}

	protected void finalize() {
		ossClient.shutdown();

		try {
			super.finalize();
		} catch (Throwable throwable) {
			throwable.printStackTrace();
		}
	}
	
	public static FileClient getInstance() {
		if (instance == null) {
			synchronized (FileClient.class) {
				if (instance == null)
					instance = new FileClient();
			}
		}

		return instance;
	}


}
