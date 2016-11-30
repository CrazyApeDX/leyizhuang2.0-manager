package com.ynyes.lyz.controller.front;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ynyes.lyz.entity.TdArticle;
import com.ynyes.lyz.entity.user.TdUser;
import com.ynyes.lyz.service.TdArticleService;
import com.ynyes.lyz.service.TdUserService;

@Controller
@RequestMapping(value = "/article")
public class TdArticleController {

	@Autowired
	private TdArticleService tdArticleService;

	@Autowired
	private TdUserService tdUserService;

	/**
	 * 跳转到文章详情的方法
	 * 
	 * @author dengxiao
	 */
	@RequestMapping(value = "/{id}")
	public String articleDetail(HttpServletRequest req, ModelMap map, @PathVariable Long id) {
		String username = (String) req.getSession().getAttribute("username");
		TdUser user = tdUserService.findByUsernameAndIsEnableTrue(username);
		if (null == user) {
			return "redirect:/login";
		}
		TdArticle article = tdArticleService.findOne(id);
		map.addAttribute("article", article);
		return "/client/article";
	}
}
