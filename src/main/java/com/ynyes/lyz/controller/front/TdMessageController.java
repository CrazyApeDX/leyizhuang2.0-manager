package com.ynyes.lyz.controller.front;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ynyes.lyz.entity.TdMessage;
import com.ynyes.lyz.entity.TdMessageType;
import com.ynyes.lyz.entity.TdUserSuggestion;
import com.ynyes.lyz.entity.user.TdUser;
import com.ynyes.lyz.service.TdMessageService;
import com.ynyes.lyz.service.TdMessageTypeService;
import com.ynyes.lyz.service.TdUserService;
import com.ynyes.lyz.service.TdUserSuggestionService;

@Controller
@RequestMapping(value = "/message")
public class TdMessageController {

	@Autowired
	private TdMessageService tdMessageService;

	@Autowired
	private TdMessageTypeService tdMessageTypeService;

	@Autowired
	private TdUserService tdUserService;

	@Autowired
	private TdUserSuggestionService tdUserSuggestionService;

	/**
	 * 跳转到消息分类的方法
	 * 
	 * @author dengxiao
	 */
	@RequestMapping
	public String message(HttpServletRequest req, ModelMap map) {
		String username = (String) req.getSession().getAttribute("username");
		// 通过用户名查找未冻结的用户
		TdUser user = tdUserService.findByUsernameAndIsEnableTrue(username);
		if (null == user) {
			return "redirect:/login";
		}

		// 获取所有的消息类别
		List<TdMessageType> message_type_list = tdMessageTypeService.findByIsEnableTrueOrderBySortIdAsc();
		// 遍历所有的消息类型，获取每种消息类型下的未读信息
		if (null != message_type_list && message_type_list.size() > 0) {
			for (int i = 0; i < message_type_list.size(); i++) {
				List<TdMessage> message_in_type_list = tdMessageService
						.findByTypeIdAndUserIdAndIsReadFalseOrderByCreateTimeDesc(message_type_list.get(i).getId(),
								user.getId());
				if (null != message_in_type_list && message_in_type_list.size() > 0) {
					// 添加未读消息的数量
					map.addAttribute("unread" + i, message_in_type_list.size());
					// 获取第一条未读消息的类容
					String content = message_in_type_list.get(0).getContent();
					map.addAttribute("content" + i, content.substring(0, 9));
				}
			}
		}
		map.addAttribute("all_type", message_type_list);

		return "/client/message_type";
	}

	@RequestMapping(value = "/list/{id}")
	public String list(HttpServletRequest req, ModelMap map, @PathVariable Long id) {
		String username = (String) req.getSession().getAttribute("username");
		TdUser user = tdUserService.findByUsernameAndIsEnableTrue(username);
		if (null == user) {
			return "redirect:/login";
		}
		// 根据用户id和消息类型查找消息
		if (0L == id.longValue()) {
			List<TdUserSuggestion> suggestions = new ArrayList<>();
			suggestions = tdUserSuggestionService
					.findByUserIdAndParentIdAndIsDeleteFalseOrderByCreateTimeDesc(user.getId(), 0L);
			map.addAttribute("suggestions", suggestions);
		} else {
			List<TdMessage> all_message_list = tdMessageService.findByTypeIdAndUserIdOrderByCreateTimeDesc(id,
					user.getId());
			map.addAttribute("all_message_list", all_message_list);
		}
		return "/client/message_list";
	}
}
