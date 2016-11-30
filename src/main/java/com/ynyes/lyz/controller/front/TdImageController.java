package com.ynyes.lyz.controller.front;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;

import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ynyes.lyz.util.ImageUtil;

/**
 * 图片控制器
 *
 * @author Sharon
 *
 */
@Controller
public class TdImageController {
	/*
	 * 返回图片
	 */
	@RequestMapping(value = "/images/{name:.+}", method = RequestMethod.GET)
	@ResponseBody
	public void getPic(@PathVariable String name, HttpServletResponse resp) throws IOException {
		if (null == name) {
			return;
		}

		String path = ImageUtil.ImageRoot + "/";

		FileInputStream fis = new FileInputStream(path + name);

		int size = fis.available(); // 得到文件大小

		byte data[] = new byte[size];

		fis.read(data); // 读数据

		fis.close();

		resp.setContentType("image/gif");

		OutputStream os = resp.getOutputStream();
		os.write(data);
		os.flush();
		os.close();
	}

}
