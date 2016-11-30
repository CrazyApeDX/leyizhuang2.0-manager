package com.ynyes.lyz.controller.management;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ynyes.lyz.entity.TdFile;
import com.ynyes.lyz.service.TdManagerLogService;
import com.ynyes.lyz.util.SiteMagConstant;

/**
 * 数据库管理
 * 
 * @author Sharon
 */

@Controller
@RequestMapping(value = "/Verwalter/database")
public class TdManagerDatabaseController {
    
    String filepath = SiteMagConstant.backupPath;
    
    @Autowired
    TdManagerLogService tdManagerLogService;
    
    @RequestMapping(value = "/list")
    public String setting(String __EVENTTARGET, String __EVENTARGUMENT,
            String __VIEWSTATE, String[] listChkTitle, ModelMap map,
            HttpServletRequest req){
        String username = (String) req.getSession().getAttribute("manager");
        if (null == username) {
            return "redirect:/Verwalter/login";
        }
        
        File file = new File(filepath);

        List<TdFile> temList = new ArrayList<TdFile>();

        if (file.isDirectory()) {
            String[] filelist = file.list();

            for (String f : filelist) {
                File rfile = new File(filepath + f);

                if (rfile.isFile()) {
                    TdFile tem = new TdFile();
                    tem.setTitle(rfile.getName());
                    tem.setModifyTime(new Date(rfile.lastModified()));
                    temList.add(tem);
                }
            }
        }

        map.addAttribute("backup_list", temList);

        return "/site_mag/backup_list";
    }
    
    @RequestMapping(value="/backup", method=RequestMethod.POST)
    public String save(String __EVENTTARGET, 
            String __EVENTARGUMENT,
            String __VIEWSTATE,
            ModelMap map,
            HttpServletRequest req){
        String username = (String) req.getSession().getAttribute("manager");
        if (null == username) {
            return "redirect:/Verwalter/login";
        }
        
        if (null != __EVENTTARGET)
        {
            if (__EVENTTARGET.equalsIgnoreCase("btnSave"))
            {
                try {
                    
                    Runtime rt = Runtime.getRuntime();
         
                    // 调用 mysql 的 cmd:
                    Process child = rt
                            .exec("/usr/bin/mysqldump -uroot -proot --set-charset=utf8 lyz");// 设置导出编码为utf8。这里必须是utf8
         
                    // 把进程执行中的控制台输出信息写入.sql文件，即生成了备份文件。注：如果不对控制台信息进行读出，则会导致进程堵塞无法运行
                    InputStream in = child.getInputStream();// 控制台的输出信息作为输入流
         
                    InputStreamReader xx = new InputStreamReader(in, "utf8");// 设置输出流编码为utf8。这里必须是utf8，否则从流中读入的是乱码
         
                    String inStr;
                    StringBuffer sb = new StringBuffer("");
                    String outStr;
                    // 组合控制台输出信息字符串
                    BufferedReader br = new BufferedReader(xx);
                    while ((inStr = br.readLine()) != null) {
                        sb.append(inStr + "\r\n");
                    }
                    outStr = sb.toString();
         
                    Date current = new Date();
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmssSSS");
                    
                    String filename = sdf.format(current);
                    
                    // 要用来做导入用的sql目标文件：
                    FileOutputStream fout = new FileOutputStream(filepath + filename + ".sql");
                    
                    OutputStreamWriter writer = new OutputStreamWriter(fout, "utf8");
                    
                    writer.write(outStr);
                    // 注：这里如果用缓冲方式写入文件的话，会导致中文乱码，用flush()方法则可以避免
                    writer.flush();
         
                    // 别忘记关闭输入输出流
                    in.close();
                    xx.close();
                    br.close();
                    writer.close();
                    fout.close();
         
                    // 日志
                    tdManagerLogService.addLog("add", "用户备份数据库", req);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            else if (__EVENTTARGET.equalsIgnoreCase("btnDelete"))
            {
                
            }
        }
        

        return "redirect:/Verwalter/database/list";
    }
    
    @RequestMapping(value="/download", method = RequestMethod.GET)
    @ResponseBody
    public void download(String name,
                HttpServletResponse resp,
                HttpServletRequest req) throws IOException {
        String username = (String) req.getSession().getAttribute("manager");
        if (null == username) {
            return;
        }
        
        if (null == name)
        {
            return;
        }
        
        OutputStream os = resp.getOutputStream();  
        
        File file = new File(filepath + name);
        
        if (file.exists())
        {
            try {
                resp.reset();
                resp.setHeader("Content-Disposition", "attachment; filename="
                        + name);
                resp.setContentType("application/octet-stream; charset=utf-8");
                os.write(FileUtils.readFileToByteArray(file));
                os.flush();
            } finally {
                if (os != null) {
                    os.close();
                }
            }
        }
    }
    
    @RequestMapping(value="/del", method = RequestMethod.GET)
    public String delete(String name,
                HttpServletResponse resp,
                HttpServletRequest req) throws IOException {
        String username = (String) req.getSession().getAttribute("manager");
        if (null == username) {
            return "redirect:/Verwalter/login";
        }
        
        if (null == name)
        {
            return "redirect:/Verwalter/database/list";
        }
        
        File file = new File(filepath + name);
        
        if (file.exists() && file.isFile())
        {
            file.delete();
            
            // 日志
            tdManagerLogService.addLog("delete", "用户删除备份数据库", req);
        }
        
        return "redirect:/Verwalter/database/list";
    }
}
