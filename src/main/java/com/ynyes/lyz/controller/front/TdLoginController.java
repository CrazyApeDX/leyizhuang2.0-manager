package com.ynyes.lyz.controller.front;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ynyes.lyz.entity.user.TdUser;
import com.ynyes.lyz.service.TdUserService;
import com.ynyes.lyz.util.MD5;

@Controller
@RequestMapping(value = "/login")
public class TdLoginController {

	@Autowired
	private TdUserService tdUserService;

	@RequestMapping
	public String index() {
		return "/client/login";
	};
	
	
	public Boolean IsUserLogin(HttpServletRequest req, String username)
	{
		if (username == null)
		{
			return false;
		}
		
		TdUser user = tdUserService.findByUsername(username);
		
		if (user == null)
		{
			return false;
		}
		
		if (user.getIsLogin() != null && user.getIsLogin())
		{
			if (req == null)
			{
				return false;
			}
			String sessionId = req.getSession().getId();
			if (user.getLoginSession() != null && user.getLoginSession().equalsIgnoreCase(sessionId))
			{
				return true;
			}
			else
			{
				Date date = user.getLastVisitTime();
				Date now = new Date();
				Long tempmin =  now.getTime() - date.getTime();
				if (tempmin >= 1000 * 60 * 1)
				{
					return true;
				}
				else
				{
					return false;
				}
			}
		}
		else
		{
			return true;
		}
	}

	@RequestMapping(value = "/check")
	@ResponseBody
	public Map<String, Object> loginCheck(HttpServletRequest req, String username, String password) {
		Map<String, Object> res = new HashMap<>();
		res.put("status", -1);
		TdUser user = tdUserService.findByUsernameAndPasswordAndIsEnableTrue(username, MD5.md5(password, 32));
			if (null != user) 
			{
				if (IsUserLogin(req, username))
				{
					res.put("status", 0);
					user.setLastLoginTime(new Date());
					// 设置session失效时间为一天
					req.getSession().setMaxInactiveInterval((60 * 60 * 24));
					req.getSession().setAttribute("username", username);
					user.setLoginSession(req.getSession().getId());
					user.setIsLogin(true);
					user.setLastVisitTime(new Date());
					tdUserService.save(user);

					//tdCommonService.clear(req); 清空购物车的方法 
				}
				else
				{
					res.put("message", "该账号已在其他地方登陆");
				}
			}
			else
			{
				TdUser user_by_username_is_enable = tdUserService.findByUsernameAndIsEnableTrue(username);
				if (null == user_by_username_is_enable) 
				{
					TdUser user_by_username = tdUserService.findByUsername(username);
					if (null == user_by_username) 
					{
						res.put("message", "该手机号码未注册");
					}
					else
					{
						res.put("message", "该账号已被冻结");
					}
				} 
				else
				{
					user_by_username_is_enable.setIsLogin(false);
					tdUserService.save(user_by_username_is_enable);
					res.put("message", "您输入的密码有误");
				}
			}
		return res;
	}

	/**
	 * 退出登陆的方法
	 * 
	 * @author dengxiao
	 */
	@RequestMapping(value = "/out")
	public String loginout(HttpServletRequest req, ModelMap map) {
		// 清空session中的用户信息
		
		String username = (String)req.getSession().getAttribute("username");
		
		if (username != null)
		{
			TdUser tdUser = tdUserService.findByUsername(username);
			tdUser.setIsLogin(false);
			tdUserService.save(tdUser);
		}
		
		req.getSession().invalidate();
//		req.getSession().setAttribute("username", null);
		return "redirect:/login";
	}
}
