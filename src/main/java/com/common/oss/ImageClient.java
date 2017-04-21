package com.common.oss;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import com.aliyun.oss.OSSClient;
import com.aliyun.oss.model.Bucket;
import com.aliyun.oss.model.GetObjectRequest;
import com.aliyun.oss.model.ListObjectsRequest;
import com.aliyun.oss.model.OSSObject;
import com.aliyun.oss.model.ObjectListing;
import com.aliyun.oss.model.ObjectMetadata;
import com.aliyun.oss.model.PutObjectResult;
import com.common.oss.exception.ImageClientException;
import com.common.oss.exception.ImageClientExceptionCode;

public class ImageClient {
	public static final String BUCKET_NAME = "leyizhuang-dev";

	public static final String ACCESS_KEY_ID = "LTAIwF4A6ScfhcYW";

	public static final String ACCESS_KEY_SECRET = "FdKQEvgLQWsyO0C3mg6WcJc3mzjAAi";

	public static final String END_POINT = "http://oss-cn-shenzhen.aliyuncs.com";

	private static ImageClient instance = null;

	private OSSClient ossClient = null;

	public String getDefaultBucket() {
		return BUCKET_NAME;
	}

	private ImageClient() {
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

	public static ImageClient getInstance() {
		if (instance == null) {
			synchronized (ImageClient.class) {
				if (instance == null)
					instance = new ImageClient();
			}
		}

		return instance;
	}

	/**
	 * 判断一个文件夹是否存在.
	 * 
	 * @param dir
	 * @return
	 * @throws ImageClientException
	 */
	public boolean isDirExist(String dir) throws ImageClientException {
		if (dir.charAt(dir.length() - 1) != '/') {
			ImageClientException ex = new ImageClientException();

			ex.setCode(ImageClientExceptionCode.InvalidDir);
			ex.setMessage("Invalid dir, must be end /");

			throw ex;
		}

		return ossClient.doesObjectExist(BUCKET_NAME, dir);
	}

	/**
	 * 新建一个文件夹.
	 * 
	 * @param dir 文件夹路径,必须以字符'/'结尾.
	 * @throws IOException
	 * @throws ImageClientException
	 */
	public void createDir(String dir) throws IOException, ImageClientException {
		if (dir.charAt(dir.length() - 1) != '/') {
			ImageClientException ex = new ImageClientException();

			ex.setCode(ImageClientExceptionCode.InvalidDir);
			ex.setMessage("Invalid dir, must be end /");

			throw ex;
		}

		if (isDirExist(dir)) {
			ImageClientException ex = new ImageClientException();

			ex.setCode(ImageClientExceptionCode.DirAlreadyExits);
			ex.setMessage("Directory already exists");

			throw ex;
		}

		ObjectMetadata meta = new ObjectMetadata();

		byte[] buffer = new byte[0];
		ByteArrayInputStream in = new ByteArrayInputStream(buffer);

		meta.setContentLength(0);

		try {
			ossClient.putObject(BUCKET_NAME, dir, in, meta);
		} finally {
			in.close();
		}
	}

	/**
	 * 删除一个目录.
	 * 
	 * @param dir 必须以字符'/'结尾.
	 * @throws ImageClientException
	 */
	public void deleteDir(String dir) throws ImageClientException {
		if (dir.charAt(dir.length() - 1) != '/') {
			ImageClientException ex = new ImageClientException();

			ex.setCode(ImageClientExceptionCode.InvalidDir);
			ex.setMessage("Invalid dir, must be end /");

			throw ex;
		}

		ossClient.deleteObject(BUCKET_NAME, dir);
	}

	/**
	 * 列出所有目录.
	 * 
	 * @return
	 */
	public List<String> listDirs() {
		ListObjectsRequest listObjectsRequest = new ListObjectsRequest(BUCKET_NAME);

		listObjectsRequest.setDelimiter("/");

		ObjectListing listing = ossClient.listObjects(listObjectsRequest);
		List<String> dirs = new ArrayList<String>();

		for (String commonPrefix : listing.getCommonPrefixes()) {
			dirs.add(commonPrefix);
		}

		return dirs;
	}

	/**
	 * 列出指定目录下的文件夹.
	 * 
	 * @param path 必须以字符'/'结尾.
	 * @return
	 * @throws ImageClientException
	 */
	public List<String> listDirs(String path) throws ImageClientException {
		if (path.charAt(path.length() - 1) != '/') {
			ImageClientException ex = new ImageClientException();

			ex.setCode(ImageClientExceptionCode.InvalidDir);
			ex.setMessage("Invalid path, must be end /");

			throw ex;
		}

		ListObjectsRequest listObjectsRequest = new ListObjectsRequest(BUCKET_NAME);

		listObjectsRequest.setDelimiter("/");
		listObjectsRequest.setPrefix(path);

		ObjectListing listing = ossClient.listObjects(listObjectsRequest);
		List<String> dirs = new ArrayList<String>();

		for (String commonPrefix : listing.getCommonPrefixes()) {
			dirs.add(commonPrefix);
		}

		return dirs;
	}

	private String getFullPath(String filePath, String fileName) throws ImageClientException, IOException {
		if (filePath == null)
			filePath = "";

		// 判断目录是否存在.
		if (filePath.length() != 0 && filePath.charAt(filePath.length() - 1) != '/') {
			filePath += '/';

			if (!isDirExist(filePath))
				createDir(filePath);
		}

		return filePath + fileName;
	}

	private PutObjectResult saveImageHelper(InputStream stream, long length, String key)
			throws IOException, ImageClientException {
		ObjectMetadata meta = new ObjectMetadata();

		meta.setContentLength(length);
		meta.setContentType("image/jpeg");

		PutObjectResult result = ossClient.putObject(BUCKET_NAME, key, stream, meta);
		return result;
	}

	/**
	 * 保存图片.
	 * 
	 * @param stream 文件流.
	 * @param length 文件流长度.
	 * @param filePath 文件路径.
	 * @param fileName 文件名.
	 * @throws IOException
	 * @throws ImageClientException
	 */
	public void saveImage(InputStream stream, long length, String filePath, String fileName)
			throws ImageClientException, IOException {
		String key = getFullPath(filePath, fileName);

		// 判断图片是否存在.
		/*
		 * if (isImageExist(key)) { ImageClientException ex = new
		 * ImageClientException();
		 * 
		 * ex.setCode(ImageClientExceptionCode.ObjectAlreadyExists);
		 * ex.setMessage("Image Already Exists");
		 * 
		 * throw ex; }
		 */

		saveImageHelper(stream, length, key);
	}

	/**
	 * 更新图片.
	 * 
	 * @param stream 文件流.
	 * @param length 文件流长度.
	 * @param filePath 文件路径.
	 * @param fileName 文件名.
	 * @throws IOException
	 * @throws ImageClientException
	 */
	public void updateImage(InputStream stream, long length, String path)
			throws ImageClientException, IOException {

		saveImageHelper(stream, length, path);
	}

	/**
	 * 获取图像流,用完后你应该主动关闭流.
	 * 
	 * @param fileName
	 * @return
	 */
	public InputStream getImage(String fileName) {
		OSSObject object = ossClient.getObject(BUCKET_NAME, fileName);

		return object.getObjectContent();
	}

	/**
	 * 将图片保存到文件.
	 * 
	 * @param fileName
	 * @param filePath
	 * @return
	 */
	public File getImage(String fileName, String filePath) {
		GetObjectRequest request = new GetObjectRequest(BUCKET_NAME, fileName);
		File file = new File(filePath);

		ossClient.getObject(request, file);

		return file;
	}

	/**
	 * 删除图片.
	 * 
	 * @param name 图片名称(可能包含path信息).
	 */
	public void deleteImage(String name) {
		ossClient.deleteObject(BUCKET_NAME, name);
	}

	/**
	 * 判断图片是否存在.
	 * 
	 * @param name
	 * @return
	 */
	public boolean isImageExist(String name) {
		return ossClient.doesObjectExist(BUCKET_NAME, name);
	}

	/**
	 * 判断bucket是否存在.
	 * 
	 * @param name
	 * @return
	 */
	public boolean isBucketExist(String name) {
		return ossClient.doesBucketExist(name);
	}

	/**
	 * 列出用记的所有bucket.
	 * 
	 * @return
	 */
	public List<String> listBuckets() {
		List<String> buckets = new ArrayList<String>();
		List<Bucket> lst = ossClient.listBuckets();

		for (Bucket bucket : lst) {
			buckets.add(bucket.getName());
		}

		return buckets;
	}
}
