package com.common.oss.utils;

import java.io.IOException;
import java.io.InputStream;

import com.common.oss.FileClient;
import com.common.oss.exception.FileClientException;
import com.common.util.RandomUtil;
import com.ynyes.lyz.util.SiteMagConstant;

public class FileClientutils {
	
	private static FileClientutils instance = null;

	private FileClientutils() {

	}
	
	public static FileClientutils getInstance() {
		if (instance == null) {
			synchronized (FileClientutils.class) {
				if (instance == null)
					instance = new FileClientutils();
			}
		}

		return instance;
	}
	
	/**
	 * 上传文件
	 *
	 * @param stream
	 * @param length
	 * @param fileName 文件名字
	 * @param source 文件来源   
	 * @return
	 */
	public String uploadFile(InputStream stream, long length, String fileName, String folder) throws IOException, FileClientException {
		FileClient client = FileClient.getInstance();
		StringBuilder path = new StringBuilder("apk");
		path.append("/");
		path.append(folder);
		path.append("/");
		client.saveFile(stream, length, path.toString(), fileName);
		
		return path.append(fileName).toString();
	}
	
	/**
	 * 根据相对路径获取绝对路径.
	 *
	 * @param path
	 * @return
	 */
	public String getAbsProjectImagePath(String path) {
		String[] hosts = SiteMagConstant.cdnHosts;
		int i = RandomUtil.nextGaussian(hosts.length);
		return String.format("%s/%s", "http://" + hosts[i], path);
	}

}
