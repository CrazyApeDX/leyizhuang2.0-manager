package com.common.util;

import java.io.File;
import java.io.IOException;

import com.tinify.Source;
import com.tinify.Tinify;

public class ImageCompressUtil {
	static {
		Tinify.setKey("3nOkFfbUo0OzCnhwtYhOyQjh0GzLBVyW");
	}

	public static void compressFileToFile() {
		int start = (int) System.currentTimeMillis(); // 开始时间
		try {
			Source source = Tinify.fromFile("G:/tmp/tile 拷贝.png");
			source.toFile("G:/tmp/optimized.png");
			int end = (int) System.currentTimeMillis(); // 结束时间
			int re = end - start; // 但图片生成处理时间
			System.out.println("图片压缩处理使用了: " + re + "毫秒");
			System.out.println("图片压缩后大小为: " + new File("G:/tmp/optimized.png").length());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void compressFileToBuffer() {
		int start = (int) System.currentTimeMillis(); // 开始时间
		try {
			Source source = Tinify.fromFile("G:/tmp/tile 拷贝.png");
			byte[] compressFile = source.toBuffer();
			int end = (int) System.currentTimeMillis(); // 结束时间
			int re = end - start; // 但图片生成处理时间
			System.out.println("图片压缩处理使用了: " + re + "毫秒");
			System.out.println("图片压缩后大小为: " + compressFile.length);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static byte[] compressFileFromUrl(String url) throws IOException {
		Source source = Tinify.fromUrl(url);
		return source.toBuffer();
	}

	public static byte[] compressFile(String sourceFile) throws IOException {
		Source source = Tinify.fromFile(sourceFile);
		return source.toBuffer();
	}
	
	public static byte[] compressFile(byte[] sourceFile) throws IOException {
		Source source = Tinify.fromBuffer(sourceFile);
		return source.toBuffer();
	}

	public static void main(String[] args) {
		compressFileToFile();
		compressFileToBuffer();
		// file.length() == buffer.length
	}
}