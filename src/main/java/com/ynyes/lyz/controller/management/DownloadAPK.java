package com.ynyes.lyz.controller.management;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;


@Controller
@RequestMapping("/Verwalter/downloadAPk")
public class DownloadAPK {
	
	@RequestMapping(value = "/goDownload")
	public String goodsList(HttpServletRequest req) {
		
		return "/site_mag/download_APk";
	}
	
}
