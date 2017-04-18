/**
 * 
 */
package com.common.oss.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import com.common.oss.ImageClient;
import com.common.oss.exception.ImageClientException;
import com.common.util.DateUtil;
import com.common.util.RandomUtil;

public class ImageClientUtils {

	// TODO CND域名有多个，待添加
	private static final String[] IMG_HOSTS = { "img1.leyizhuang.com.cn"};

	private static final String APPIMAGEFOLDER = "app/images/";

	private static ImageClientUtils instance = null;

	private ImageClientUtils() {

	}

	public static ImageClientUtils getInstance() {
		if (instance == null) {
			synchronized (ImageClientUtils.class) {
				if (instance == null)
					instance = new ImageClientUtils();
			}
		}

		return instance;
	}

	/**
	 * 根据相对路径获取绝对路径.
	 *
	 * @param path
	 * @return
	 */
	public String getAbsProjectImagePath(String path) {
		int i = RandomUtil.nextGaussian(IMG_HOSTS.length);
		return String.format("%s/%s", "http://" + IMG_HOSTS[i], path);
	}

	/**
	 * 上传图片.
	 *
	 * @param stream
	 * @param length
	 * @return
	 */
	public String uploadImage(InputStream stream, long length) throws IOException, ImageClientException {
		ImageClient client = ImageClient.getInstance();

		String filePath = String.format("%s/%s/", APPIMAGEFOLDER, DateUtil.getCurrentDateStr("yyyyMMdd"));
		String fileName = String.format("%s_%s.jpg", DateUtil.getCurrentTimeStr("HHmmssSSS"), RandomUtil.randomNumCode(4));

		client.saveImage(stream, length, filePath, fileName);

		return filePath + fileName;
	}

	/**
	 * 上传图片.
	 *
	 * @param stream
	 * @param length
	 * @param fileName 文件名字
	 * @return
	 */
	public String uploadImage(InputStream stream, long length, String fileName) throws IOException, ImageClientException {
		ImageClient client = ImageClient.getInstance();

		client.saveImage(stream, length, APPIMAGEFOLDER, fileName);

		return APPIMAGEFOLDER + fileName;
	}

	/**
	 * 更新图片.
	 *
	 * @param stream
	 * @param length
	 * @return
	 */
	public void updateImage(InputStream stream, long length, String path) throws IOException, ImageClientException {
		ImageClient client = ImageClient.getInstance();
		client.updateImage(stream, length, path);
	}

	/**
	 * 删除图片.
	 *
	 * @param stream
	 * @param length
	 * @return
	 */
	public void deleteImage(String path) throws IOException, ImageClientException {
		ImageClient client = ImageClient.getInstance();
		client.deleteImage(path);
	}

	public static void main(String[] args) {
		File pic = new File("C:/Users/Administrator/Desktop/tmp/test22.png");
		try {
			// System.out.println(ImageClientUtils.getInstance().uploadImage(new
			// FileInputStream(pic), pic.length()));
			// String path = "app/20170417/151138208_5259.jpg";
			// System.out.println(ImageClientUtils.getInstance().getAbsProjectImagePath(path));
			// ImageClientUtils.getInstance().deleteImage(path);
			ImageClientUtils.getInstance().updateImage(new FileInputStream(pic), pic.length(), "app/20170417/152053875_6165.jpg");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
