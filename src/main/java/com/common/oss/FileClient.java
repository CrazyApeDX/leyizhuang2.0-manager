package com.common.oss;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

import com.aliyun.oss.OSSClient;
import com.aliyun.oss.model.ObjectMetadata;
import com.aliyun.oss.model.PutObjectResult;
import com.common.oss.exception.FileClientException;
import com.common.oss.exception.ImageClientException;
import com.common.oss.exception.ImageClientExceptionCode;
import com.ynyes.lyz.util.SiteMagConstant;

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
	
	/**
	 * 保存文件
	 * 
	 * @param stream 文件流.
	 * @param length 文件流长度.
	 * @param filePath 文件路径.
	 * @param fileName 文件名.
	 * @throws IOException
	 * @throws FileClientException
	 */
	public void saveImage(InputStream stream, long length, String filePath, String fileName)
			throws FileClientException, IOException {
		String key = getFullPath(filePath, fileName);


		saveImageHelper(stream, length, key);
	}
	
	private String getFullPath(String filePath, String fileName) throws FileClientException, IOException {
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
	
	public boolean isDirExist(String dir) throws FileClientException {
		if (dir.charAt(dir.length() - 1) != '/') {
			FileClientException ex = new FileClientException();

			ex.setCode(ImageClientExceptionCode.InvalidDir);
			ex.setMessage("Invalid dir, must be end /");

			throw ex;
		}

		return ossClient.doesObjectExist(SiteMagConstant.ossBucket, dir);
	}
	
	
	/**
	 * 新建一个文件夹.
	 * 
	 * @param dir 文件夹路径,必须以字符'/'结尾.
	 * @throws IOException
	 * @throws ImageClientException
	 */
	public void createDir(String dir) throws IOException, FileClientException {
		if (dir.charAt(dir.length() - 1) != '/') {
			FileClientException ex = new FileClientException();

			ex.setCode(ImageClientExceptionCode.InvalidDir);
			ex.setMessage("Invalid dir, must be end /");

			throw ex;
		}

		if (isDirExist(dir)) {
			FileClientException ex = new FileClientException();

			ex.setCode(ImageClientExceptionCode.DirAlreadyExits);
			ex.setMessage("Directory already exists");

			throw ex;
		}

		ObjectMetadata meta = new ObjectMetadata();

		byte[] buffer = new byte[0];
		ByteArrayInputStream in = new ByteArrayInputStream(buffer);

		meta.setContentLength(0);

		try {
			ossClient.putObject(SiteMagConstant.ossBucket, dir, in, meta);
		} finally {
			in.close();
		}
	}

	
	private PutObjectResult saveImageHelper(InputStream stream, long length, String key)
			throws IOException, FileClientException {
		ObjectMetadata meta = new ObjectMetadata();

		meta.setContentLength(length);
		meta.setContentType("image/jpeg");

		PutObjectResult result = ossClient.putObject(SiteMagConstant.ossBucket, key, stream, meta);
		return result;
	}

}
