package com.common.oss.utils;

import java.io.IOException;
import java.io.InputStream;

import com.common.oss.FileClient;
import com.common.oss.ImageClient;
import com.common.oss.exception.FileClientException;
import com.common.oss.exception.ImageClientException;
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
	public String uploadFile(InputStream stream, long length, String fileName, String source) throws IOException, FileClientException {
		FileClient client = FileClient.getInstance();
		StringBuilder path = new StringBuilder(SiteMagConstant.ossFolder);
		path.append(source);
		path.append("/");
//		client.saveImage(stream, length, path.toString(), fileName);
		
		return path.append(fileName).toString();
	}

}
