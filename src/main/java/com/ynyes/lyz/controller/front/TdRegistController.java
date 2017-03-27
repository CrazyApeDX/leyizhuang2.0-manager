package com.ynyes.lyz.controller.front;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ynyes.lyz.entity.TdCity;
import com.ynyes.lyz.entity.TdDiySite;
import com.ynyes.lyz.entity.TdSmsAccount;
import com.ynyes.lyz.entity.user.TdUser;
import com.ynyes.lyz.service.TdCityService;
import com.ynyes.lyz.service.TdDiySiteService;
import com.ynyes.lyz.service.TdSmsAccountService;
import com.ynyes.lyz.service.TdUserService;
import com.ynyes.lyz.util.MD5;

@Controller
@RequestMapping(value = "/regist")
public class TdRegistController {

	@Autowired
	private TdCityService tdRegionService;

	@Autowired
	private TdUserService tdUserService;

	@Autowired
	private TdSmsAccountService tdSmsAccountService;

	@Autowired
	private TdCityService tdCityService;

	@Autowired
	private TdDiySiteService tdDiySiteService;

	@RequestMapping
	public String regist(HttpServletRequest req, ModelMap map) {
		List<TdCity> regions = tdRegionService.findAll();
		map.addAttribute("regions", regions);
		return "/client/regist";
	}

	@RequestMapping(value = "/refer/check")
	@ResponseBody
	public Map<String, Object> referCheck(HttpServletRequest req, String referPhone, String cityInfo) {
		Map<String, Object> res = new HashMap<>();
		res.put("status", -1);
		TdUser user = tdUserService.findByUsernameAndCityNameAndIsEnableTrue(referPhone, cityInfo);
		if (null == user) {
			// 根据城市的名称获取指定城市的信息
			TdCity city = tdCityService.findByCityName(cityInfo);
			if (null == city) {
				city = new TdCity();
				city.setCityName(cityInfo);
				city = tdCityService.save(city);
			}

			// 获取门店名称
			TdDiySite diySite = tdDiySiteService.findByRegionIdAndStatusAndIsEnableTrue(city.getSobIdCity(), 2L).get(0);
			res.put("message", diySite.getTitle());
			return res;
		}
		res.put("message", user.getDiyName());
		res.put("status", 0);
		return res;
	}

	@RequestMapping(value = "/save")
	@ResponseBody
	public Map<String, Object> save(HttpServletRequest req, String cityInfo, String name, String phone, String code,
			String password, String repassword, String referPhone, String diySiteName, Integer identityType) {
		Map<String, Object> res = new HashMap<>();
		res.put("status", -1);
		String smsCode = (String) req.getSession().getAttribute("SMSCODE");
		String smsMobile = (String) req.getSession().getAttribute("SMSMOBILE");
		TdUser user = tdUserService.findByUsername(phone);
		if (null == cityInfo || "".equals(cityInfo) || "地区".equals(cityInfo)) {
			res.put("message", "您还未选择您的地区");
			return res;
		}
		if (null != user) {
			res.put("message", "该手机号码已注册");
			return res;
		}
		if (null == smsCode || smsMobile == null) {
			res.put("message", "验证码错误");
			return res;
		}
		if (!smsCode.equals(code) || !smsMobile.equalsIgnoreCase(phone)) {
			res.put("message", "验证码错误");
			return res;
		}
		if (StringUtils.isNotBlank(password)) {
			if (!repassword.equals(password)) {
				res.put("message", "两次输入的密码不一致");
				return res;
			}
		} else {
			password = "123456";
		}

		// 根据城市的名称获取指定城市的信息
		TdCity city = tdCityService.findByCityName(cityInfo);
		if (null == city) {
			// city = new TdCity();
			// city.setCityName(cityInfo);
			// city = tdCityService.save(city);
			res.put("message", "该城市（" + cityInfo + "）未开通服务");
			return res;
		}

		// 获取门店名称
		TdDiySite diySite = tdDiySiteService.findByRegionIdAndStatusAndIsEnableTrue(city.getSobIdCity(), 2L).get(0);
		TdUser new_user = new TdUser();
		new_user.setUsername(phone);
		new_user.setPassword(MD5.md5(password, 32));
		new_user.setReferPhone(referPhone);
		new_user.setBalance(0.00);
		// new_user.setNickname(phone);
		new_user.setRegisterTime(new Date());
		// new_user.setAllPayed(0.00);
		new_user.setUserType(0L);
		new_user.setCityName(cityInfo);
		new_user.setCityId(city.getSobIdCity());
		// new_user.setFirstOrder(true);
		new_user.setRealName(name);
		// new_user.setIsOld(false);
		new_user.setLastLoginTime(new Date());
		new_user.setDiyName(cityInfo + "默认门店");
		new_user.setUpperDiySiteId(diySite.getId());
		new_user.setSellerId(0L);
		new_user.setSellerName("无");
		// 设置默认头像
		new_user.setHeadImageUri("/client/images/per_titleimg01.png");
		new_user.setCashBalance(0.0);
		new_user.setUnCashBalance(0.0);
		new_user.setIsEnable(true);
		new_user.setCustomerId(diySite.getCustomerId());
		if (null == identityType) {
			identityType = 0;
		}
		new_user.setIdentityType(identityType);

		TdUser refer_user = tdUserService.findByUsernameAndCityNameAndIsEnableTrue(referPhone, cityInfo);
		if (null != refer_user) {
			new_user.setUpperDiySiteId(refer_user.getUpperDiySiteId());
			new_user.setDiyName(refer_user.getDiyName());
			new_user.setCityId(refer_user.getCityId());

			new_user.setSellerId(refer_user.getId());
			new_user.setSellerName(refer_user.getRealName());
			new_user.setCustomerId(refer_user.getCustomerId());
			TdDiySite refer_diySite = tdDiySiteService.findByRegionIdAndCustomerId(refer_user.getCityId(),
					refer_user.getCustomerId());
			if (refer_diySite != null) {
				new_user.setCustomerId(refer_diySite.getCustomerId());
			}
			
			if (cityInfo.equalsIgnoreCase("成都市")) {
				new_user.setIsCashOnDelivery(true);
			} else if (cityInfo.equalsIgnoreCase("郑州市")) {
				new_user.setIsCashOnDelivery(false);
			}
		}

		tdUserService.save(new_user);
		req.getSession().setMaxInactiveInterval((60 * 60 * 24));
		req.getSession().setAttribute("username", phone);

		res.put("status", 0);
		return res;
	}

	@RequestMapping(value = "/send/code")
	@ResponseBody
	public Map<String, Object> sendCode(HttpServletRequest req, String phone, String cityInfo) {
		Map<String, Object> res = new HashMap<>();
		res.put("status", -1);
		Random random = new Random();
		String smscode = random.nextInt(9000) + 1000 + "";
		HttpSession session = req.getSession();
		session.setAttribute("SMSCODE", smscode);
		session.setAttribute("SMSMOBILE", phone);
		String info = "您的验证码为" + smscode + "，请在页面中输入以完成验证。";
		System.err.println("MDJ:SMSCODE:" + smscode + "AND Moblie:" + phone);
		String content = null;
		try {
			content = URLEncoder.encode(info, "GB2312");
			System.err.println(content);
		} catch (Exception e) {
			e.printStackTrace();
			res.put("message", "验证码生成失败");
			return res;
		}

		TdCity region = tdRegionService.findByCityName(cityInfo);
		if (null == region) {
			res.put("message", "该地区未开通服务");
			return res;
		}
		TdSmsAccount account = tdSmsAccountService.findOne(region.getSmsAccountId());
		String url = "http://115.28.146.248:6070/interface/Send.aspx?enCode=" + account.getEncode() + "&enPass="
				+ account.getEnpass() + "&userName=" + account.getUserName() + "&mob=" + phone + "&msg=" + content;
		StringBuffer return_code = null;
		try {
			URL u = new URL(url);
			URLConnection connection = u.openConnection();
			HttpURLConnection httpConn = (HttpURLConnection) connection;
			httpConn.setRequestProperty("Content-type", "text/html");
			httpConn.setRequestProperty("Accept-Charset", "utf-8");
			httpConn.setRequestProperty("contentType", "utf-8");
			InputStream inputStream = null;
			InputStreamReader inputStreamReader = null;
			BufferedReader reader = null;
			StringBuffer resultBuffer = new StringBuffer();
			String tempLine = null;

			if (httpConn.getResponseCode() >= 300) {
				res.put("message", "HTTP Request is not success, Response code is " + httpConn.getResponseCode());
				return res;
			}

			try {
				inputStream = httpConn.getInputStream();
				inputStreamReader = new InputStreamReader(inputStream);
				reader = new BufferedReader(inputStreamReader);

				while ((tempLine = reader.readLine()) != null) {
					resultBuffer.append(tempLine);
				}
				return_code = resultBuffer;

			} finally {
				if (reader != null) {
					reader.close();
				}
				if (inputStreamReader != null) {
					inputStreamReader.close();
				}
				if (inputStream != null) {
					inputStream.close();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			res.put("message", "验证码生成失败");
			return res;
		}
		res.put("status", 0);
		res.put("code", return_code);
		return res;
	}

}
