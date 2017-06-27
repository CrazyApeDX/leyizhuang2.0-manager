package com.ynyes.lyz.controller.management;

import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.common.oss.utils.ImageClientUtils;
import com.common.util.ImageCompressUtil;
import com.ynyes.lyz.service.TdArticleCategoryService;
import com.ynyes.lyz.service.TdArticleService;
import com.ynyes.lyz.util.SiteMagConstant;

import net.sf.json.JSONObject;






/**
 * 后台首页控制器
 * 
 * @author Sharon
 */

@Controller
@RequestMapping(value = "/Verwalter")
public class TdUploadController {

    String ImageRoot = SiteMagConstant.imagePath;

    @Autowired
    TdArticleCategoryService tdArticleCategoryService;

    @Autowired
    TdArticleService tdArticleService;
    
    private static final SimpleDateFormat SDF = new SimpleDateFormat("yyyyMMddHHmmssSSS");;

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

        String name = Filedata.getOriginalFilename();
//        String contentType = Filedata.getContentType();

        String ext = name.substring(name.lastIndexOf("."));

        try {
            byte[] bytes = Filedata.getBytes();

            Date dt = new Date(System.currentTimeMillis());
            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmssSSS");
            String fileName = sdf.format(dt) + ext;

            String uri = ImageRoot + "/" + fileName;

            File file = new File(uri);

            BufferedOutputStream stream = new BufferedOutputStream(
                    new FileOutputStream(file));
            stream.write(bytes);
            stream.close();

            res.put("status", 1);
            res.put("msg", "上传文件成功！");
            res.put("path", "/images/" + fileName);
            res.put("thumb", "/images/" + fileName);
            res.put("name", name);
            res.put("size", Filedata.getSize());
            res.put("ext", ext.substring(1));

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
            System.err.println(e);
        } finally {
            if (writer != null) {
                try {
                    writer.close();
                } catch (Exception e) {
                    System.err.println(e);
                }
            }
        }

        return null;

//        return res;

    }

    @RequestMapping(value = "/uploadGoodPic", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> uploadGoodPic(String action, Long goodId, 
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
        String name = Filedata.getOriginalFilename();
        String ext = name.substring(name.lastIndexOf("."));
        try {
            Date dt = new Date(System.currentTimeMillis());
            String fileName = SDF.format(dt) + ext;
            
            String path = "";
            long fileSize = Filedata.getSize();
			if (fileSize > 1024 * 1024) {
				byte[] compressFile = ImageCompressUtil.compressFile(Filedata.getBytes());
				fileSize = compressFile.length;
				path = ImageClientUtils.getInstance().uploadGoodsImage(new ByteArrayInputStream(compressFile), fileSize, fileName, goodId);
			} else {
				path = ImageClientUtils.getInstance().uploadGoodsImage(Filedata.getInputStream(), fileSize, fileName, goodId);
            }
           
			String url = ImageClientUtils.getInstance().getAbsProjectImagePath(path);

            res.put("status", 1);
            res.put("msg", "上传文件成功！");
            res.put("path", url);
            res.put("thumb", url);
            res.put("name", name);
            res.put("size", fileSize);
            res.put("ext", ext.substring(1));

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
            System.err.println(e);
        } finally {
            if (writer != null) {
                try {
                    writer.close();
                } catch (Exception e) {
                    System.err.println(e);
                }
            }
        }

        return null;
    }

    @RequestMapping(value = "/uploadPic", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> uploadPic(String action, String source, 
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
        String name = Filedata.getOriginalFilename();
        String ext = name.substring(name.lastIndexOf("."));
        try {
            Date dt = new Date(System.currentTimeMillis());
            String fileName = SDF.format(dt) + ext;
            
            String path = "";
            long fileSize = Filedata.getSize();
			if (fileSize > 1024 * 1024) {
				byte[] compressFile = ImageCompressUtil.compressFile(Filedata.getBytes());
				fileSize = compressFile.length;
				path = ImageClientUtils.getInstance().uploadImage(new ByteArrayInputStream(compressFile), fileSize, fileName, source);
			} else {
				path = ImageClientUtils.getInstance().uploadImage(Filedata.getInputStream(), fileSize, fileName, source);
            }
           
			String url = ImageClientUtils.getInstance().getAbsProjectImagePath(path);

            res.put("status", 1);
            res.put("msg", "上传文件成功！");
            res.put("path", url);
            res.put("thumb", url);
            res.put("name", name);
            res.put("size", fileSize);
            res.put("ext", ext.substring(1));

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
            System.err.println(e);
        } finally {
            if (writer != null) {
                try {
                    writer.close();
                } catch (Exception e) {
                    System.err.println(e);
                }
            }
        }

        return null;
    }
    @RequestMapping(value = "/upload/client", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> uploadclient(String action,
            @RequestParam MultipartFile Filedata, HttpServletRequest req) {
        Map<String, Object> res = new HashMap<String, Object>();
        res.put("status", 0);
        String username = (String) req.getSession().getAttribute("username");
        
        if (null == username) {
            res.put("msg", "请重新登录！");
            return res;
        }

        if (null == Filedata || Filedata.isEmpty()) {
            res.put("msg", "图片不存在");
            return res;
        }

        String name = Filedata.getOriginalFilename();
//        String contentType = Filedata.getContentType();

        String ext = name.substring(name.lastIndexOf("."));

        try {
            byte[] bytes = Filedata.getBytes();

            Date dt = new Date(System.currentTimeMillis());
            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmssSSS");
            String fileName = sdf.format(dt) + ext;

            String uri = ImageRoot + "/" + fileName;

            File file = new File(uri);

            BufferedOutputStream stream = new BufferedOutputStream(
                    new FileOutputStream(file));
            stream.write(bytes);
            stream.close();

            res.put("status", 1);
            res.put("msg", "上传文件成功！");
            res.put("path", "/images/" + fileName);
            res.put("thumb", "/images/" + fileName);
            res.put("name", name);
            res.put("size", Filedata.getSize());
            res.put("ext", ext.substring(1));

        } catch (Exception e) {
            res.put("status", 0);
            res.put("msg", "上传文件失败！");
        }

        return res;

    }

    @RequestMapping(value = "/editor/upload", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> editorUpload(String action,
            @RequestParam MultipartFile imgFile, HttpServletRequest req , HttpServletResponse response) {
        Map<String, Object> res = new HashMap<String, Object>();

        res.put("error", 1);

        String username = (String) req.getSession().getAttribute("manager");
        if (null == username) {
            res.put("msg", "请重新登录！");
            return res;
        }

        if (null == imgFile || imgFile.isEmpty() || null == imgFile.getName()) {
            res.put("msg", "图片不存在");
            return res;
        }

        String name = imgFile.getOriginalFilename();
        String ext = name.substring(name.lastIndexOf("."));

        try {
            byte[] bytes = imgFile.getBytes();

            Date dt = new Date(System.currentTimeMillis());
            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmssSSS");
            String fileName = sdf.format(dt) + ext;

            String uri = ImageRoot + "/" + fileName;

            File file = new File(uri);

            BufferedOutputStream stream = new BufferedOutputStream(
                    new FileOutputStream(file));
            stream.write(bytes);
            stream.close();

            res.put("error", 0);
            res.put("msg", "上传文件成功！");
            res.put("url", "/images/" + fileName);

        } catch (Exception e) {
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
            System.err.println(e);
        } finally {
            if (writer != null) {
                try {
                    writer.close();
                } catch (Exception e) {
                    System.err.println(e);
                }
            }
        }

        return null;
        
//        return res;

    }

    @RequestMapping(value = "/editor/uploadGoodPic", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> editorUploadGoodPic(String action, Long goodId,
            @RequestParam MultipartFile imgFile, HttpServletRequest req , HttpServletResponse response) {
        Map<String, Object> res = new HashMap<String, Object>();
        res.put("error", 1);
        String username = (String) req.getSession().getAttribute("manager");
        if (null == username) {
            res.put("msg", "请重新登录！");
            return res;
        }

        if (null == imgFile || imgFile.isEmpty() || null == imgFile.getName()) {
            res.put("msg", "图片不存在");
            return res;
        }

        String name = imgFile.getOriginalFilename();
        String ext = name.substring(name.lastIndexOf("."));

        try {
            Date dt = new Date(System.currentTimeMillis());
            String fileName = SDF.format(dt) + ext;
            
            String path = "";
            long fileSize = imgFile.getSize();
			if (fileSize > 1024 * 1024) {
				byte[] compressFile = ImageCompressUtil.compressFile(imgFile.getBytes());
				fileSize = compressFile.length;
				path = ImageClientUtils.getInstance().uploadGoodsImage(new ByteArrayInputStream(compressFile), fileSize, fileName, goodId);
			} else {
				path = ImageClientUtils.getInstance().uploadGoodsImage(imgFile.getInputStream(), fileSize, fileName, goodId);
            }
           
			String url = ImageClientUtils.getInstance().getAbsProjectImagePath(path);

            res.put("error", 0);
            res.put("msg", "上传文件成功！");
            res.put("url", url);

        } catch (Exception e) {
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
            System.err.println(e);
        } finally {
            if (writer != null) {
                try {
                    writer.close();
                } catch (Exception e) {
                    System.err.println(e);
                }
            }
        }
        return null;
    }

    @RequestMapping(value = "/editor/uploadPic", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> editorUploadPic(String action, String source,
            @RequestParam MultipartFile imgFile, HttpServletRequest req , HttpServletResponse response) {
        Map<String, Object> res = new HashMap<String, Object>();
        res.put("error", 1);
        String username = (String) req.getSession().getAttribute("manager");
        if (null == username) {
            res.put("msg", "请重新登录！");
            return res;
        }

        if (null == imgFile || imgFile.isEmpty() || null == imgFile.getName()) {
            res.put("msg", "图片不存在");
            return res;
        }

        String name = imgFile.getOriginalFilename();
        String ext = name.substring(name.lastIndexOf("."));

        try {
            Date dt = new Date(System.currentTimeMillis());
            String fileName = SDF.format(dt) + ext;
            
            String path = "";
            long fileSize = imgFile.getSize();
			if (fileSize > 1024 * 1024) {
				byte[] compressFile = ImageCompressUtil.compressFile(imgFile.getBytes());
				fileSize = compressFile.length;
				path = ImageClientUtils.getInstance().uploadImage(new ByteArrayInputStream(compressFile), fileSize, fileName, source);
			} else {
				path = ImageClientUtils.getInstance().uploadImage(imgFile.getInputStream(), fileSize, fileName, source);
            }
			
			String url = ImageClientUtils.getInstance().getAbsProjectImagePath(path);

            res.put("error", 0);
            res.put("msg", "上传文件成功！");
            res.put("url", url);

        } catch (Exception e) {
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
            System.err.println(e);
        } finally {
            if (writer != null) {
                try {
                    writer.close();
                } catch (Exception e) {
                    System.err.println(e);
                }
            }
        }
        return null;
    }
}
