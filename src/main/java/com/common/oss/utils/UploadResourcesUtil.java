package com.common.oss.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.aliyun.oss.OSSClient;
import com.aliyun.oss.model.ObjectMetadata;
import com.common.oss.ImageClient;

public class UploadResourcesUtil {
	private OSSClient ossClient = null;

	private static final String STATIC_PATH = "static/";

	private static final Logger LOGGER = LoggerFactory.getLogger(UploadResourcesUtil.class);

	private static final String IMG_CACHE = "2592000";

	private static final String STATIC_CACHE = "604800";

	private static UploadResourcesUtil instance = null;

	private UploadResourcesUtil() {
		ossClient = new OSSClient(ImageClient.END_POINT, ImageClient.ACCESS_KEY_ID, ImageClient.ACCESS_KEY_SECRET);
	}

	public static UploadResourcesUtil getInstance() {
		if (instance == null) {
			synchronized (ImageClientUtils.class) {
				if (instance == null)
					instance = new UploadResourcesUtil();
			}
		}
		return instance;
	}

	private String getContentType(String fileName) {
		if (fileName.endsWith(".jpg")) {
			return "image/jpg";
		}

		if (fileName.endsWith(".png")) {
			return "image/png";
		}
		if (fileName.endsWith(".jpeg")) {
			return "image/jpeg";
		}
		if (fileName.endsWith(".gif")) {
			return "image/gif";
		}
		if (fileName.endsWith(".bmp")) {
			return "image/bmp";
		}
		if (fileName.endsWith(".css")) {
			return "text/css";
		}
		if (fileName.endsWith(".js")) {
			return "application/x-javascript";
		}

		return "application/octet-stream";
	}

	private boolean isImage(String fileName) {
		if (fileName.endsWith(".jpg")) {
			return true;
		}
		if (fileName.endsWith(".png")) {
			return true;
		}
		if (fileName.endsWith(".jpeg")) {
			return true;
		}
		if (fileName.endsWith(".gif")) {
			return true;
		}
		if (fileName.endsWith(".bmp")) {
			return true;
		}
		return false;
	}

	private void saveFile(InputStream stream, long length, String fileName, String path) {
		path = STATIC_PATH + path + fileName;
		ObjectMetadata meta = new ObjectMetadata();
		meta.setContentLength(length);
		meta.setContentType(getContentType(fileName));
		if (isImage(fileName)) {
			meta.setCacheControl("max-age=" + IMG_CACHE);
		} else {
			meta.setCacheControl("max-age=" + STATIC_CACHE);
		}

		ossClient.putObject(ImageClient.BUCKET_NAME, path, stream, meta);
	}

	private void saveDir(File dir, String path) throws Exception {
		path = path + dir.getName() + "/";
		File[] files = dir.listFiles();
		for (File file : files) {
			if (file.isFile() && !file.isHidden()) {
				InputStream stream = new FileInputStream(file);
				LOGGER.info("uploading file:---------" + path + file.getName());
				saveFile(stream, file.length(), file.getName(), path);
				stream.close();
			} else if (file.isDirectory()) {
				saveDir(file, path);
			}
		}
	}

	@SuppressWarnings("restriction")
	public static void main(String[] args) {
		java.security.Security.addProvider(new com.sun.crypto.provider.SunJCE());
		if (args.length != 2) {
			System.out.println("need two param : source dir and oss save dir ...");
			System.out.println("eg: /data/code/xq-xihome/xq-xihome-web/src/main/webapp/images www/");
		}
		try {
			UploadResourcesUtil util = UploadResourcesUtil.getInstance();
			// File dir = new
			// File("/Users/zhaoyiwei/xq-projects/xq-xihome/xq-xihome-web/src/main/webapp/css");
			File dir = new File(args[0]);
			if (!dir.isDirectory()) {
				return;
			}
			// util.saveDir(dir,"www/");
			util.saveDir(dir, args[1]);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
