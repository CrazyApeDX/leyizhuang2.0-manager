package com.ynyes.lyz.controller.qrcode;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ynyes.lyz.entity.TdDiySite;
import com.ynyes.lyz.entity.qrcode.QrcodeRegisterLog;
import com.ynyes.lyz.entity.user.TdUser;
import com.ynyes.lyz.service.QrcodeRegisterLogService;
import com.ynyes.lyz.service.TdDiySiteService;
import com.ynyes.lyz.service.TdUserService;
import com.ynyes.lyz.util.MD5;

@Controller
@RequestMapping(value = "/qrcode")
public class TdQrcodeController {

	@Autowired
	private TdUserService tdUserService;

	@Autowired
	private TdDiySiteService tdDiySiteService;

	@Autowired
	private QrcodeRegisterLogService qrcodeRegisterLogService;

	@RequestMapping(value = "/{phone}", method = RequestMethod.GET, produces = "text/html;charset=utf-8")
	public String qrcode(@PathVariable String phone, ModelMap map) {
		map.addAttribute("phone", phone);
		TdUser seller = tdUserService.findByUsername(phone);
		if (null != seller) {
			map.addAttribute("seller", seller.getUsername());
		} else {
			map.addAttribute("message", "读取推荐人信息失败");
			return "/qrcode/failure";
		}

		Long upperDiySiteId = seller.getUpperDiySiteId();
		TdDiySite diySite = tdDiySiteService.findOne(upperDiySiteId);
		if (null != diySite) {
			map.addAttribute("diySiteName", diySite.getTitle());
		} else {
			map.addAttribute("message", "读取门店信息失败");
			return "/qrcode/failure";
		}
		return "/qrcode/regist";
	}

	@RequestMapping(value = "/regist", method = RequestMethod.POST, produces = "application/json;charset=utf-8")
	@ResponseBody
	public Map<String, Object> qrRegist(String name, String phone, String sellerName, String diySiteName) {
		Map<String, Object> res = new HashMap<>();
		res.put("status", -1);

		TdUser user = tdUserService.findByUsername(phone);
		if (null != user) {
			res.put("message", "该手机号码已经注册");
			return res;
		} else {
			TdUser seller = tdUserService.findByUsername(sellerName);
			user = new TdUser();
			user.setBalance(0d);
			user.setCityName(seller.getCityName());
			user.setDiyName(seller.getDiyName());
			user.setIsEnable(true);
			user.setRegisterTime(new Date());
			user.setLastLoginTime(user.getRegisterTime());
			user.setPassword(MD5.md5("123456", 32));
			user.setRealName(name);
			user.setReferPhone(sellerName);
			user.setSex("保密");
			user.setUpperDiySiteId(seller.getUpperDiySiteId());
			user.setUserType(0L);
			user.setUsername(phone);
			user.setCashBalance(0d);
			user.setUnCashBalance(0d);
			user.setCityId(seller.getCityId());
			user.setSortId(99.9);
			user.setHeadImageUri("/client/images/per_titleimg01.png");
			user.setCustomerId(seller.getCustomerId());
			user.setIdentityType(0);
			user.setIsCashOnDelivery(true);
			user.setDiyCode(seller.getDiyCode());
			user.setSellerId(seller.getId());
			user.setSellerName(seller.getRealName());
			tdUserService.save(user);
			
			QrcodeRegisterLog log = new QrcodeRegisterLog();
			log.setSellerUsername(seller.getUsername());
			log.setUsername(user.getUsername());
			log.setDiySiteTitle(seller.getDiyName());
			log.setDiySiteCode(seller.getDiyCode());
			log.setRegistTime(new Date());
			qrcodeRegisterLogService.save(log);
			res.put("status", 0);
			return res;
		}
	}
	
	@RequestMapping(value = "/success",method = RequestMethod.GET, produces = "text/html;charset=utf-8")
	public String success() {
		return "/qrcode/success";
	}
}
