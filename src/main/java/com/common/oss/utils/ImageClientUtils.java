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
import com.ynyes.lyz.util.SiteMagConstant;

public class ImageClientUtils {

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
		String[] hosts = SiteMagConstant.cdnHosts;
		int i = RandomUtil.nextGaussian(hosts.length);
		return String.format("%s/%s", "http://" + hosts[i], path);
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

		String filePath = String.format("%s/%s/", SiteMagConstant.ossFolder, DateUtil.getCurrentDateStr("yyyyMMdd"));
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

		client.saveImage(stream, length, SiteMagConstant.ossFolder, fileName);

		return SiteMagConstant.ossFolder + fileName;
	}
	
	/**
	 * 上传其它来源图片.
	 *
	 * @param stream
	 * @param length
	 * @param fileName 文件名字
	 * @param source 图片来源   article/goods/message/pay/ui/ad
	 * @return
	 */
	public String uploadImage(InputStream stream, long length, String fileName, String source) throws IOException, ImageClientException {
		ImageClient client = ImageClient.getInstance();
		StringBuilder path = new StringBuilder(SiteMagConstant.ossFolder);
		path.append(source);
		path.append("/");
		client.saveImage(stream, length, path.toString(), fileName);
		
		return path.append(fileName).toString();
	}
	
	/**
	 * 上传商品图片.
	 *
	 * @param stream
	 * @param length
	 * @param fileName 文件名字
	 * @return
	 */
	public String uploadGoodsImage(InputStream stream, long length, String fileName, Long goodsId) throws IOException, ImageClientException {
		ImageClient client = ImageClient.getInstance();
		StringBuilder path = new StringBuilder(SiteMagConstant.ossFolder);
		path.append("goods/");
		path.append(goodsId);
		path.append("/");
		client.saveImage(stream, length, path.toString(), fileName);
		
		return path.append(fileName).toString();
	}

	/**
	 * 上传广告图片.
	 *
	 * @param stream
	 * @param length
	 * @param fileName 文件名字
	 * @return
	 */
	public String uploadAdImage(InputStream stream, long length, String fileName, Long adId) throws IOException, ImageClientException {
		ImageClient client = ImageClient.getInstance();
		StringBuilder path = new StringBuilder(SiteMagConstant.ossFolder);
		path.append("ad/");
		path.append(adId);
		path.append("/");
		client.saveImage(stream, length, path.toString(), fileName);
		
		return path.append(fileName).toString();
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
			 System.out.println(ImageClientUtils.getInstance().uploadImage(new
			 FileInputStream(pic), pic.length()));
			// String path = "app/20170417/151138208_5259.jpg";
			// System.out.println(ImageClientUtils.getInstance().getAbsProjectImagePath(path));
			// ImageClientUtils.getInstance().deleteImage(path);
//			ImageClientUtils.getInstance().updateImage(new FileInputStream(pic), pic.length(), "app/20170417/152053875_6165.jpg");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
