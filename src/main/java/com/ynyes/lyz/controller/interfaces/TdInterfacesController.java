package com.ynyes.lyz.controller.interfaces;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ynyes.lyz.interfaces.entity.TdOrderGoodsInf;
import com.ynyes.lyz.interfaces.entity.TdOrderInf;
import com.ynyes.lyz.interfaces.service.TdInterfaceService;
import com.ynyes.lyz.interfaces.service.TdOrderGoodsInfService;
import com.ynyes.lyz.interfaces.service.TdOrderInfService;
import com.ynyes.lyz.interfaces.utils.INFConstants;
import com.ynyes.lyz.interfaces.utils.EnumUtils.INFTYPE;

/**
 * <p>接口重传相关控制器</p>
 * @version 版本：上午9:22:33
 */
@Controller
@RequestMapping(value = "/inter")
public class TdInterfacesController {
	
	@Autowired
	private TdOrderInfService tdOrderInfService;
	
	@Autowired
	private TdOrderGoodsInfService tdOrderGoodsInfService;
	
	@Autowired
	private TdInterfaceService tdInterfaceService;

	private final String TOKEN = "e150d359c9588fd82369f99712aac757";
	
	// 订单接口数据管理别名
	private final String _ORDER_INFO = "_ORDER_INFO";
	
	// 订单明细数据管理别名
	private final String _ORDER_DETAIL = "_ORDER_DETAIL";
	
	// 访问页面的控制器
	@RequestMapping(value = "/{token}")
	public String scanView(HttpServletRequest req, ModelMap map, @PathVariable String token) {
		if (null != token && TOKEN.equals(token)) {
			req.getSession().setAttribute("ACCESS", true);
			return "/inter/index";
		} else {
			return "redirect:/Verwalter";
		}
	}
	
	// 访问内容页
	@RequestMapping(value = "/content", method = RequestMethod.POST)
	public String scanContent(HttpServletRequest req, ModelMap map, String alias) {
		Boolean isAccess = (Boolean) req.getSession().getAttribute("ACCESS");

		if (null != isAccess && isAccess) {
			 if (null != alias) {
				 if (alias.equals(_ORDER_INFO)) {
					 // 获取未发送成功的订单信息
					 List<TdOrderInf> orderInfList = tdOrderInfService.findBySendFlag(1);
					 map.addAttribute("orderInfList", orderInfList);
					 map.addAttribute("title", "订单接口数据管理");
				 } else if (alias.equals(_ORDER_DETAIL)) {
					 List<TdOrderGoodsInf> orderGoodsInfList = tdOrderGoodsInfService.findBySendFlag(1);
					 map.addAttribute("orderGoodsInfList", orderGoodsInfList);
					 map.addAttribute("title", "订单明细数据管理");
				 }
			 }
		} else {
			return "/inner/error";
		}
		return "/inter/table";
	}
	
	// 重发数据的方法
	@RequestMapping(value = "/send", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> send(HttpServletRequest req, String alias, Long[] values) {
		Map<String, Object> res = new HashMap<>();
		res.put("status", -1);
		StringBuffer sb = new StringBuffer();
		Boolean isAccess = (Boolean) req.getSession().getAttribute("ACCESS");

		if (null != isAccess && isAccess) {
			if (null != alias) {
				if (alias.equals(_ORDER_INFO)) {
					for (Long id : values) {
						TdOrderInf orderInf = tdOrderInfService.findOne(id);
						if (null == orderInf) {
							sb.append("id:" + id + ";该id的订单信息不存在\n");
						} else if (null != orderInf && orderInf.getSendFlag().intValue() == 0) {
							sb.append("id:" + id + ";该id的订单信息已经发送成功\n");
						} else {
							String orderInfXML = tdInterfaceService.XmlWithObject(orderInf, INFTYPE.ORDERINF);
							Object[] orderInfObject = { INFConstants.INF_ORDER_STR, "1", orderInfXML };
							String resultStr = tdInterfaceService.ebsWsInvoke(orderInfObject);
							sb.append("id:" + id + ";结果:" + resultStr +"\n");
						}
					}
				}
			}
		} else {
			sb.append("您没有权限重新发送数据");
			res.put("message", sb.toString());
			return res;
		}
		
		res.put("message", sb.toString());
		res.put("status", 0);
		return res;
	}
}
