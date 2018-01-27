package com.ynyes.lyz.controller.management;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.beust.jcommander.internal.Lists;
import com.beust.jcommander.internal.Maps;
import com.common.util.DateUtil;
import com.common.util.RandomUtil;
import com.ynyes.lyz.entity.AllocationTypeEnum;
import com.ynyes.lyz.entity.TdAllocation;
import com.ynyes.lyz.entity.TdAllocationQuery;
import com.ynyes.lyz.entity.TdCity;
import com.ynyes.lyz.entity.TdDiySite;
import com.ynyes.lyz.entity.TdManager;
import com.ynyes.lyz.service.TdAllocationService;
import com.ynyes.lyz.service.TdDiySiteRoleService;
import com.ynyes.lyz.service.TdDiySiteService;
import com.ynyes.lyz.service.TdManagerLogService;
import com.ynyes.lyz.service.TdManagerService;
import com.ynyes.lyz.util.SiteMagConstant;

@Controller
@RequestMapping("/Verwalter/allocation")
public class TdManagerAllocationController {

	private static final Logger LOGGER = LoggerFactory.getLogger(TdManagerAllocationController.class);

	@Autowired
	private TdAllocationService tdAllocationService;

	@Autowired
	private TdManagerLogService tdManagerLogService;

	@Autowired
	private TdDiySiteService tdDiySiteService;

	@Autowired
	private TdManagerService tdManagerService;

	@Autowired
	private TdDiySiteRoleService tdDiySiteRoleService;

	@RequestMapping("/list")
	public String list(Integer page, Integer size, Integer status, Long cityId, Integer allocationType, Long diySiteId,
			String startTime, String endTime, String __EVENTTARGET, String __EVENTARGUMENT, String __VIEWSTATE, String number,
			Long[] listId, Integer[] listChkId, Long[] listSortId, ModelMap map, HttpServletRequest req) {
		String username = (String) req.getSession().getAttribute("manager");
		if (null == username) {
			return "redirect:/Verwalter/login";
		}

		if (null == page || page < 0) {
			page = 0;
		}

		if (null == size || size <= 0) {
			size = SiteMagConstant.pageSize;
		}

		TdAllocationQuery conditon = new TdAllocationQuery();
		if (StringUtils.isNotBlank(number)) {
			conditon.setNumber(number.trim());
		}
		if (null != status) {
			conditon.setStatus(status);
		}
		if (null != diySiteId) {
			conditon.setDiySiteId(diySiteId);
			if (null != cityId) {
				map.addAttribute("diySites", tdDiySiteRoleService.getUserRoleDirectDiy(username, cityId));
			}
		} else {
			List<Long> diySiteIds = Lists.newArrayList();
			List<TdDiySite> diySites;
			if (null != cityId) {
				diySites = tdDiySiteRoleService.getUserRoleDirectDiy(username, cityId);
				map.addAttribute("diySites", diySites);
			} else {
				diySites = tdDiySiteRoleService.getUserRoleDirectDiy(username);
			}
			for (TdDiySite diySite : diySites) {
				diySiteIds.add(diySite.getId());
			}
			if (diySiteIds.size() == 0) {
				diySiteIds.add(-1L);
				conditon.setDiySiteIds(diySiteIds);
			} else if (diySiteIds.size() == 1) {
				conditon.setDiySiteId(diySiteIds.get(0));
			} else {
				conditon.setDiySiteIds(diySiteIds);
			}
		}
		if (null != allocationType) {
			conditon.setAllocationType(allocationType);
		}
		if (StringUtils.isNotBlank(startTime)) {
			try {
				conditon.setStartTime(DateUtil.parseDateTime(startTime));
			} catch (Exception e) {
				LOGGER.error("转换时间错误：" + e.getMessage());
			}
		}
		if (StringUtils.isNotBlank(endTime)) {
			try {
				conditon.setEndTime(DateUtil.parseDateTime(endTime));
			} catch (Exception e) {
				LOGGER.error("转换时间错误：" + e.getMessage());
			}
		}

		if (null != __EVENTTARGET) {
			switch (__EVENTTARGET) {
			case "lbtnViewTxt":
			case "lbtnViewImg":
				__VIEWSTATE = __EVENTTARGET;
				break;

			case "btnPage":
				if (null != __EVENTARGUMENT) {
					page = Integer.parseInt(__EVENTARGUMENT);
				}
				break;
			}
		}

		if (null != __EVENTTARGET && __EVENTTARGET.equalsIgnoreCase("lbtnViewTxt")
				|| null != __EVENTTARGET && __EVENTTARGET.equalsIgnoreCase("lbtnViewImg")) {
			__VIEWSTATE = __EVENTTARGET;
		}

		// 参数注回
		map.addAttribute("page", page);
		map.addAttribute("size", size);
		map.addAttribute("number", number);
		map.addAttribute("__EVENTTARGET", __EVENTTARGET);
		map.addAttribute("__EVENTARGUMENT", __EVENTARGUMENT);
		map.addAttribute("__VIEWSTATE", __VIEWSTATE);
		map.addAttribute("status", status);
		map.addAttribute("cityId", cityId);
		map.addAttribute("diySiteId", diySiteId);
		map.addAttribute("allocationType", allocationType);
		map.addAttribute("oldStartTime", startTime);
		map.addAttribute("oldEndTime", endTime);

		List<TdCity> diyCities = tdDiySiteRoleService.getUserRoleCity(username);
		if (diyCities.size() > 0) {
			map.addAttribute("cities", diyCities);
		} else {
			// 获取管理员信息
			TdManager tdManager = tdManagerService.findByUsernameAndIsEnableTrue(username);
			if (tdManager == null || tdManager.getDiyCode() == null) {
				return "redirect:/Verwalter/login";
			}
			TdDiySite diy = tdDiySiteService.findByStoreCode(tdManager.getDiyCode());
			map.addAttribute("diySiteId", diy.getId());
		}

		map.addAttribute("allocation_page", tdAllocationService.findAllocation(page, size, conditon));
		return "/site_mag/allocation_list";
	}

	@RequestMapping("/add")
	public String add(Long fns, ModelMap map, HttpServletRequest req) {
		String username = (String) req.getSession().getAttribute("manager");
		if (null == username) {
			return "redirect:/Verwalter/login";
		}
		// 获取管理员信息
		TdManager tdManager = tdManagerService.findByUsernameAndIsEnableTrue(username);
		if (tdManager == null || StringUtils.isBlank(tdManager.getDiyCode())) {
			return "redirect:/Verwalter/login";
		}
		TdDiySite diy = tdDiySiteService.findByStoreCode(tdManager.getDiyCode());
		if (null == diy) {
			return "redirect:/Verwalter/login";
		}
		Long cityId = diy.getCityId();

		// 获取所属城市下的直营门店
		List<TdDiySite> diySites = tdDiySiteService.getTdDiySites(cityId, diy.getId());
		map.addAttribute("cityId", cityId);
		map.addAttribute("cityName", diy.getCity());
		map.addAttribute("diySites", diySites);
		if (null != fns) {
			map.addAttribute("fns", fns);
		}
		return "/site_mag/allocation_add";
	}

	@RequestMapping(value = "/edit")
	public String edit(Long fns, Long pid, Long id, String __EVENTTARGET, String __EVENTARGUMENT, String __VIEWSTATE,
			ModelMap map, HttpServletRequest req) {
		String username = (String) req.getSession().getAttribute("manager");
		if (null == username) {
			return "redirect:/Verwalter/login";
		}
		// 获取管理员信息
		TdManager tdManager = tdManagerService.findByUsernameAndIsEnableTrue(username);
		if (tdManager == null) {
			return "redirect:/Verwalter/login";
		}

		map.addAttribute("__EVENTTARGET", __EVENTTARGET);
		map.addAttribute("__EVENTARGUMENT", __EVENTARGUMENT);
		map.addAttribute("__VIEWSTATE", __VIEWSTATE);

		if (null != id) {
			TdAllocation tdAllocation = tdAllocationService.findOne(id);
			map.addAttribute("allocation", tdAllocation);
			if (StringUtils.isNotBlank(tdManager.getDiyCode())) {
				TdDiySite diy = tdDiySiteService.findByStoreCode(tdManager.getDiyCode());
				if (null != diy) {
					if (diy.getId().equals(tdAllocation.getAllocationFrom())) {
						map.addAttribute("allocationFromFlag", true);
					}
					if (diy.getId().equals(tdAllocation.getAllocationTo())) {
						map.addAttribute("allocationToFlag", true);
					}
				}
			}

		}

		if (null != fns) {
			map.addAttribute("fns", fns);
		}
		return "/site_mag/allocation_edit";
	}

	@RequestMapping(value = "/save", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> save(String tdAllocationJson, HttpServletRequest req) {
		Map<String, Object> result = Maps.newHashMap();
		String username = (String) req.getSession().getAttribute("manager");
		if (null == username) {
			result.put("code", 1);
			result.put("msg", "登录已过期，请重新登陆！");
			return result;
		}
		// 获取管理员信息
		TdManager tdManager = tdManagerService.findByUsernameAndIsEnableTrue(username);
		if (tdManager == null || tdManager.getDiyCode() == null) {
			result.put("code", 1);
			result.put("msg", "登录已过期，请重新登陆！");
			return result;
		}
		try {
			TdAllocation tdAllocation = JSON.parseObject(tdAllocationJson, TdAllocation.class);
			TdDiySite diy = tdDiySiteService.findByStoreCode(tdManager.getDiyCode());
			tdAllocation.setNumber(this.getAllocationNumber());
			Long allocationFromId = tdAllocation.getAllocationFrom();
			if (null != allocationFromId) {
				TdDiySite diyFrom = tdDiySiteService.findOne(allocationFromId);
				if (null != diyFrom) {
					tdAllocation.setAllocationFromName(diyFrom.getTitle());
				}
			}
			tdAllocation.setAllocationTo(diy.getId());
			tdAllocation.setAllocationToName(diy.getTitle());
			tdAllocationService.insert(tdAllocation, username);
			result.put("code", 0);
			result.put("msg", "操作成功！");
		} catch (Exception e) {
			LOGGER.error("新建调拨单错误, err=" + e.getMessage(), e);
			result.put("code", -1);
			result.put("msg", "系统正忙，请稍后重试！");
		}

		tdManagerLogService.addLog("add", "用户新建调拨单", req);

		return result;
	}

	@RequestMapping(value = "/cancel", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> cancle(Long id, HttpServletRequest req) {
		Map<String, Object> result = Maps.newHashMap();
		String username = (String) req.getSession().getAttribute("manager");
		if (null == username) {
			result.put("code", 1);
			result.put("msg", "登录已过期，请重新登陆！");
			return result;
		}
		try {
			TdAllocation allocation = tdAllocationService.get(id);
			if (AllocationTypeEnum.NEW.getValue() == allocation.getStatus()) {
				tdAllocationService.cancel(allocation, username);
				result.put("code", 0);
				result.put("msg", "操作成功！");
			} else {
				result.put("code", 2);
				result.put("msg", "调拨单已经出库，不允许作废！");
			}
		} catch (Exception e) {
			LOGGER.error("调拨单取消错误, err=" + e.getMessage(), e);
			result.put("code", -1);
			result.put("msg", "系统正忙，请稍后重试！");
		}

		return result;
	}

	@RequestMapping(value = "/send", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> send(Long id, String realNums, HttpServletRequest req) {
		Map<String, Object> result = Maps.newHashMap();
		String username = (String) req.getSession().getAttribute("manager");
		if (null == username) {
			result.put("code", 1);
			result.put("msg", "登录已过期，请重新登陆！");
			return result;
		}
		try {
			TdAllocation allocation = tdAllocationService.get(id);
			if (AllocationTypeEnum.NEW.getValue() == allocation.getStatus()) {
				tdAllocationService.send(allocation, realNums, username);
				result.put("code", 0);
				result.put("msg", "操作成功！");
			} else {
				result.put("code", 2);
				result.put("msg", "调拨单已作废，不允许出库！");
			}
		} catch (Exception e) {
			if (e.getMessage().contains("库存不足")) {
				LOGGER.info(e.getMessage());
				result.put("code", 3);
				result.put("msg", e.getMessage());
			} else {
				LOGGER.error("调拨单出库错误,id=" + id + ", err=" + e.getMessage(), e);
				result.put("code", -1);
				result.put("msg", "系统正忙，请稍后重试！");
			}
		}

		return result;
	}

	@RequestMapping(value = "/receive", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> receive(Long id, HttpServletRequest req) {
		Map<String, Object> result = Maps.newHashMap();
		String username = (String) req.getSession().getAttribute("manager");
		if (null == username) {
			result.put("code", 1);
			result.put("msg", "登录已过期，请重新登陆！");
			return result;
		}
		try {
			TdAllocation allocation = tdAllocationService.get(id);
			if (AllocationTypeEnum.SENT.getValue() == allocation.getStatus()) {
				tdAllocationService.receive(allocation, username);
				result.put("code", 0);
				result.put("msg", "操作成功！");
			} else {
				result.put("code", 2);
				result.put("msg", "调拨单未出库，不允许入库！");
			}
		} catch (Exception e) {
			LOGGER.error("调拨单入库错误,id=" + id + ", err=" + e.getMessage(), e);
			result.put("code", -1);
			result.put("msg", "系统正忙，请稍后重试！");
		}

		return result;
	}

	@RequestMapping(value = "/resendAll", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> resendAll(HttpServletRequest req) {
		Map<String, Object> result = Maps.newHashMap();
		String username = (String) req.getSession().getAttribute("manager");
		if (null == username) {
			result.put("code", 1);
			result.put("msg", "登录已过期，请重新登陆！");
			return result;
		}
		try {
			tdAllocationService.resendAllAllocation();
			result.put("code", 0);
			result.put("msg", "操作成功！");
		} catch (Exception e) {
			LOGGER.error("调拨单重新发送错误, err=" + e.getMessage(), e);
			result.put("code", -1);
			result.put("msg", "系统正忙，请稍后重试！");
		}

		return result;
	}

	private String getAllocationNumber() {
		StringBuilder number = new StringBuilder();
		number.append("DB_");
		number.append(DateUtil.getCurrentTimeStr("yyyyMMddHHmmssSSS"));
		number.append(RandomUtil.randomNumCode(3));
		return number.toString();
	}
}
