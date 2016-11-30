package com.ynyes.lyz.util;

import java.awt.image.BufferedImage;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.imageio.ImageIO;

import org.springframework.web.multipart.MultipartFile;

/**
 * 上传图片函数
 * @author Sharon
 *
 */
public class ImageUtil {
    
    static public final String ImageRoot = SiteMagConstant.imagePath;

    /**
     * 上传图片，成功后返回uriggs
     * @param image 图片
     * @return
     *      map.code "0":成功  "1":"失败"
     *      map.message 成功时为图片uri，失败时为失败消息
     */
    static 
    public Map<String, String> upload(MultipartFile image)
    {
        Map<String, String> map = new HashMap<String, String>();
        
        if (image.getContentType().startsWith("image"))
        {
            try {
                byte[] bytes = image.getBytes();
                
                Date dt = new Date(System.currentTimeMillis());
                SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmssSSS");
                String   fileName   =   sdf.format(dt);
                
                String uri = ImageRoot + "/" + fileName;
                
                File file = new File(uri);
                
                BufferedOutputStream stream =
                        new BufferedOutputStream(new FileOutputStream(file));
                stream.write(bytes);
                stream.close();
                
                BufferedImage bufferedImage = ImageIO.read(file);
                
                bufferedImage.getWidth();

                map.put("code", "0");
                map.put("message", uri.substring(ImageRoot.length()));
                map.put("height", "" + bufferedImage.getHeight());
                map.put("width", "" + bufferedImage.getWidth());
            } catch (Exception e) {
                
                map.put("code", "1");
                map.put("message", e.getMessage());
            }
        }
        else
        {
            map.put("code", "1");
            map.put("message", "未知的图片格式");
        }
        
        return map;
    }
}
