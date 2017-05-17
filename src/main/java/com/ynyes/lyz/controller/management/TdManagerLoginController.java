package com.ynyes.lyz.controller.management;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ynyes.fitment.core.constant.LoginSign;
import com.ynyes.lyz.entity.TdManager;
import com.ynyes.lyz.service.TdManagerLogService;
import com.ynyes.lyz.service.TdManagerService;


/**
 * 品牌管理控制器
 * 
 * @author Sharon
 * 
 */
@Controller
@RequestMapping(value="/Verwalter")
public class TdManagerLoginController {
    
    @Autowired
    TdManagerLogService tdManagerLogService;
    
    @Autowired
    TdManagerService tdManagerService;

	private static final Logger LOGGER = LoggerFactory.getLogger(TdManagerLoginController.class);
    
    @RequestMapping(value="/login")
    public String login(String username, String password, ModelMap map, HttpServletRequest request){
		LOGGER.info("login, username=" + username);
        
        if (null == username || null == password || username.isEmpty() || password.isEmpty())
        {
            map.addAttribute("error", "用户名和密码不能为空");
            return "/site_mag/login";
        }
        //去掉默认admin登录
//        if (username.equalsIgnoreCase("admin") && password.equals("admin888"))
//        {
//            request.getSession().setAttribute("manager", username);
//            tdManagerLogService.addLog("login", "用户登录", request);
//            return "redirect:/Verwalter";
//        }
//        else
//        {
            TdManager manager = tdManagerService.findByUsernameAndIsEnableTrue(username);
            
            if (null != manager)
            {
                if (password.equals(manager.getPassword()))
                {
                    manager.setLastLoginIp(manager.getIp());
                    manager.setLastLoginTime(manager.getLoginTime());
                    
                    String ip = request.getHeader("x-forwarded-for");
                    if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
                        ip = request.getHeader("Proxy-Client-IP");
                    }
                    if (ip == null || ip.length() == 0 || "unknow".equalsIgnoreCase(ip)) {
                        ip = request.getHeader("WL-Proxy-Client-IP");
                    }
                    if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
                        ip = request.getRemoteAddr();
                    }
                    
                    manager.setIp(ip);
                    manager.setLoginTime(new Date());
                    
                    tdManagerService.save(manager);
                    
                    request.getSession().setAttribute("manager", username);
                    request.getSession().setAttribute(LoginSign.MANAGER_SIGN.toString(), username);
                    tdManagerLogService.addLog("login", "用户登录", request);
                    return "redirect:/Verwalter";
                }
            }
            
            map.addAttribute("error", "用户不存在或密码错误");
            return "/site_mag/login";
//        }   
    }
    
    @RequestMapping(value="/logout")
    public String logout(HttpServletRequest request){

        tdManagerLogService.addLog("logout", "用户退出登录", request);
        
        request.getSession().invalidate();
        
        return "redirect:/Verwalter/login";
    }
}
