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

import com.common.oss.utils.ImageClientUtils;

import net.sf.json.JSONObject;


@Controller
@RequestMapping("/Verwalter/uploadAPk")
public class UploadAPk {
	
	
	@RequestMapping(value = "/goUpload")
	public String goodsList(HttpServletRequest req) {
		
		return "/site_mag/upload_APk";
	}
	
	
	@RequestMapping(value = "/upload", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> upload(String action,
            @RequestParam MultipartFile Filedata, HttpServletRequest req, HttpServletResponse response) {
        Map<String, Object> res = new HashMap<String, Object>();
        res.put("status", 0);
        String username = (String) req.getSession().getAttribute("manager");
        
        if (null == username) {
            res.put("msg", "请重新登录！");
            return res;
        }

        if (null == Filedata || Filedata.isEmpty()) {
            res.put("msg", "图片不存在");
            return res;
        }
        try {

            InputStream stream = Filedata.getInputStream();

            ImageClientUtils client = ImageClientUtils.getInstance();
            String fileName = client.uploadImage(stream, stream.available());
            String path = client.getAbsProjectImagePath(fileName);
            stream.close();
            res.put("status", 1);
            res.put("msg", "上传文件成功！");
            res.put("path",  path);
            res.put("size", Filedata.getSize());

        } catch (Exception e) {
            res.put("status", 0);
            res.put("msg", "上传文件失败！");
        }
        response.reset();
        response.setCharacterEncoding("UTF-8");
        response.setContentType("text/html");
        PrintWriter writer = null;
        try {
            writer = response.getWriter();
            JSONObject jsonObject = JSONObject.fromObject(res);
            writer.println(jsonObject);  //想办法把map转成json
            writer.flush();
        } catch (IOException e) {
        } finally {
            if (writer != null) {
                try {
                    writer.close();
                } catch (Exception e) {
                }
            }
        }

        return null;

//        return res;

    }
	

}
