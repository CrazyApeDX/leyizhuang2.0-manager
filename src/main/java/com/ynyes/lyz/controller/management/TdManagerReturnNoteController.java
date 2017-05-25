package com.ynyes.lyz.controller.management;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ynyes.lyz.entity.TdCashReturnNote;
import com.ynyes.lyz.entity.TdCity;
import com.ynyes.lyz.entity.TdDiySite;
import com.ynyes.lyz.entity.TdDiySiteAccount;
import com.ynyes.lyz.entity.TdManager;
import com.ynyes.lyz.entity.TdManagerDiySiteRole;
import com.ynyes.lyz.entity.TdManagerRole;
import com.ynyes.lyz.entity.TdOrder;
import com.ynyes.lyz.entity.TdReturnNote;
import com.ynyes.lyz.entity.user.TdUser;
import com.ynyes.lyz.interfaces.entity.TdCashRefundInf;
import com.ynyes.lyz.interfaces.entity.TdReturnTimeInf;
import com.ynyes.lyz.interfaces.service.TdInterfaceService;
import com.ynyes.lyz.interfaces.utils.EnumUtils.INFTYPE;
import com.ynyes.lyz.service.TdCashReturnNoteService;
import com.ynyes.lyz.service.TdCityService;
import com.ynyes.lyz.service.TdCommonService;
import com.ynyes.lyz.service.TdDiySiteAccountService;
import com.ynyes.lyz.service.TdDiySiteInventoryService;
import com.ynyes.lyz.service.TdDiySiteRoleService;
import com.ynyes.lyz.service.TdDiySiteService;
import com.ynyes.lyz.service.TdManagerLogService;
import com.ynyes.lyz.service.TdManagerRoleService;
import com.ynyes.lyz.service.TdManagerService;
import com.ynyes.lyz.service.TdOrderService;
import com.ynyes.lyz.service.TdPriceCountService;
import com.ynyes.lyz.service.TdReturnNoteService;
import com.ynyes.lyz.service.TdUserService;
import com.ynyes.lyz.util.SiteMagConstant;

@Controller
@RequestMapping(value = "/Verwalter/returnNote")
public class TdManagerReturnNoteController extends TdManagerBaseController {

	@Autowired
	TdReturnNoteService tdReturnNoteService;

	@Autowired
	TdManagerLogService tdManagerLogService;

	@Autowired
	private TdOrderService tdOrderService;

	@Autowired
	private TdUserService tdUserService;

	@Autowired
	private TdCommonService tdCommonService;

	@Autowired
	private TdManagerService tdManagerService;

	@Autowired
	private TdManagerRoleService tdManagerRoleService;

	@Autowired
	private TdCityService tdCityService;

	@Autowired
	private TdDiySiteService tdDiySiteService;

	@Autowired
	private TdPriceCountService tdPriceCountService;

	@Autowired
	private TdInterfaceService tdInterfaceService;

	@Autowired
	private TdDiySiteRoleService tdDiySiteRoleService;

	@Autowired
	private TdDiySiteInventoryService tdDiySiteInventoryService;

	@Autowired
	private TdCashReturnNoteService tdCashReturnNoteService;

	@Autowired
	private TdDiySiteAccountService tdDiySiteAccountService;

	// 列表
	@RequestMapping(value = "/{type}/list")
	public String list(@PathVariable String type, Integer page, Integer size, String keywords, String __EVENTTARGET,
			String __EVENTARGUMENT, String __VIEWSTATE, Long[] listId, Integer[] listChkId, Double[] listSortId,
			ModelMap map, HttpServletRequest req, Long diyCode, String city, Long statusId) {
		String username = (String) req.getSession().getAttribute("manager");
		if (null == username) {
			return "redirect:/Verwalter/login";
		}

		TdManager tdManager = tdManagerService.findByUsernameAndIsEnableTrue(username);
		TdManagerRole tdManagerRole = null;
		if (tdManager != null && tdManager.getRoleId() != null) {
			tdManagerRole = tdManagerRoleService.findOne(tdManager.getRoleId());
		}
		if (tdManagerRole == null) {
			return "redirect:/Verwalter/login";
		}

		if (null != __EVENTTARGET) {
			if (__EVENTTARGET.equalsIgnoreCase("btnDelete")) {
				btnDelete(type, listId, listChkId);

				if (type.equalsIgnoreCase("returnNote")) {
					tdManagerLogService.addLog("delete", "删除退货单", req);
				}

			}
			if (__EVENTTARGET.equalsIgnoreCase("btnPage")) {
				if (__EVENTARGUMENT != null) {
					try {
						page = Integer.parseInt(__EVENTARGUMENT);
					} catch (Exception e) {
						e.printStackTrace();
						page = 0;
					}
				}
			}

		}

		if (null == page || page < 0) {
			page = 0;
		}

		if (null == size || size <= 0) {
			size = SiteMagConstant.pageSize;
			;
		}

		map.addAttribute("page", page);
		map.addAttribute("size", size);
		map.addAttribute("keywords", keywords);
		map.addAttribute("__EVENTTARGET", __EVENTTARGET);
		map.addAttribute("__EVENTARGUMENT", __EVENTARGUMENT);
		map.addAttribute("__VIEWSTATE", __VIEWSTATE);

		if (null != type) {
			if (type.equalsIgnoreCase("returnNote")) //
			{
				// if (tdManagerRole.getTitle().equalsIgnoreCase("门店"))
				// {
				// String diyCode = tdManager.getDiyCode();
				// TdDiySite tdDiySite =
				// tdDisSiteService.findByStoreCode(diyCode);
				// map.addAttribute("returnNote_page",tdReturnNoteService.findBySiteIdAndKeywords(tdDiySite.getId(),
				// keywords, page, size));
				// }
				// else
				// {
				// if (StringUtils.isNotBlank(keywords))
				// {
				// map.addAttribute("returnNote_page",
				// tdReturnNoteService.searchAll(keywords, page, size));
				// }
				// else
				// {
				// map.addAttribute("returnNote_page",
				// tdReturnNoteService.findAll(page, size));
				// }
				// }
				// 查询用户管辖门店权限
				TdManagerDiySiteRole diySiteRole = tdDiySiteRoleService.findByTitle(tdManagerRole.getTitle());
				// 获取管理员管辖城市
				List<TdCity> cityList = new ArrayList<TdCity>();
				// 获取管理员管辖门店
				List<TdDiySite> diyList = new ArrayList<TdDiySite>();

				// 管理员获取管辖的城市和门店
				tdDiySiteRoleService.userRoleCityAndDiy(cityList, diyList, diySiteRole, tdManagerRole, tdManager);

				// 权限门店
				List<Long> roleDiyCodes = new ArrayList<Long>();
				if (diyList != null && diyList.size() > 0) {
					for (TdDiySite diy : diyList) {
						roleDiyCodes.add(diy.getId());
					}
				}

				// 修改城市刷新门店列表
				if (StringUtils.isNotBlank(city)) {
					// 需要删除的diy
					List<TdDiySite> diyRemoveList = new ArrayList<TdDiySite>();
					for (TdDiySite tdDiySite : diyList) {
						if (!city.equals(tdDiySite.getCity())) {
							diyRemoveList.add(tdDiySite);
							if (tdDiySite.getId().equals(diyCode)) {
								diyCode = null;
							}
						}
					}
					diyList.removeAll(diyRemoveList);
				}

				// 搜索条件城市 数据库里面没有城市 转换为门code查询
				List<Long> cityDiyCodes = new ArrayList<Long>();
				TdCity tdCity = tdCityService.findByCityName(city);
				if (tdCity != null) {
					List<TdDiySite> diySiteList = tdDiySiteService.findByCityId(tdCity.getId());
					if (diySiteList != null && diySiteList.size() > 0) {
						for (TdDiySite tdDiySite : diySiteList) {
							if (roleDiyCodes.contains(tdDiySite.getId())) {
								cityDiyCodes.add(tdDiySite.getId());
							}
						}
					} else {
						// 城市下面没有门店 默认为null 查询不到任何门店
						cityDiyCodes.add(0L);
					}
				}

				Page<TdReturnNote> returnNotePage = tdReturnNoteService.searchReturnList(keywords, diyCode,
						roleDiyCodes, cityDiyCodes, size, page, statusId);
				map.addAttribute("returnNote_page", returnNotePage);
				// 循环获取用户名称
				List<TdReturnNote> returnNoteList = returnNotePage.getContent();
				Map<String, Object> nameMap = new HashMap<String, Object>();
				if (returnNoteList != null && returnNoteList.size() > 0) {
					for (TdReturnNote returnNote : returnNoteList) {
						String returnUsername = returnNote.getUsername();
						if (nameMap.containsKey(returnUsername)) {
							continue;
						} else {
							TdUser tdUser = tdUserService.findByUsername(returnUsername);
							if (null != tdUser && StringUtils.isNotBlank(returnUsername)) {
								nameMap.put(returnUsername, tdUser.getRealName());
							}
						}
					}
				}

				map.addAttribute("name_map", nameMap);
				map.addAttribute("diySiteList", diyList);
				map.addAttribute("cityList", cityList);
				map.addAttribute("cityName", city);
				map.addAttribute("diyCode", diyCode);
				map.addAttribute("statusId", statusId);

				// 以前的查询 zp
				// String siteName =
				// tdReturnNoteService.findSiteTitleByUserName(username);
				// siteName = StringUtils.isNotBlank(siteName) ? siteName :
				// keywords;
				// String keyword = StringUtils.isNotBlank(keywords) ? keywords
				// : "";
				// if (StringUtils.isNotBlank(siteName))
				// {
				// map.addAttribute("returnNote_page", tdReturnNoteService
				// .findByDiySiteTitleAndOrderNumberOrReturnNumberOrUsername(siteName,
				// keyword, page, size));
				// }
				// else if (StringUtils.isNotBlank(keyword))
				// {
				// map.addAttribute("returnNote_page",
				// tdReturnNoteService.searchAll(keyword, page, size));
				// }
				// else
				// {
				// map.addAttribute("returnNote_page",
				// tdReturnNoteService.findAll(page, size));
				// }
				// //城市和门店信息
				// if (tdManagerRole.getIsSys()){
				// map.addAttribute("diySiteList",tdDiySiteService.findAll());
				// map.addAttribute("cityList", tdCityService.findAll());
				// }
				return "/site_mag/returnNote_list";
			}

		}
		return "/site_mag/returnNote_list";
	}

	@RequestMapping(value = "/{type}/edit")
	public String edit(@PathVariable String type, Long id, String __VIEWSTATE, ModelMap map, HttpServletRequest req) {
		String username = (String) req.getSession().getAttribute("manager");
		if (null == username) {
			return "redirect:/Verwalter/login";
		}

		map.addAttribute("__VIEWSTATE", __VIEWSTATE);

		if (null != type) {
			if (type.equalsIgnoreCase("returnNote")) // 支付方式
			{
				if (null != id) {
					TdReturnNote returnNote = tdReturnNoteService.findOne(id);
					map.addAttribute("returnNote", returnNote);
					map.addAttribute("user", tdUserService.findByUsername(returnNote.getUsername()));
					// map.addAttribute("order",
					// tdOrderService.findByOrderNumber(returnNote.getOrderNumber()));
				}

				return "/site_mag/returnNote_edit";
			}
		}

		return "/site_mag/returnNote_edit";
	}

	/**
	 * 修改退货单状态
	 * 
	 * @author Max
	 * 
	 */
	@RequestMapping(value = "/param/edit", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> returnNoteParam(String returnNumber, String type, String data, HttpServletRequest req) {
		Map<String, Object> res = new HashMap<>();

		res.put("code", 1);

		String username = (String) req.getSession().getAttribute("manager");

		if (null == username) {
			res.put("message", "请重新登录");
			return res;
		}

		if (null != returnNumber && !returnNumber.isEmpty() && null != type && !type.isEmpty()) {
			TdReturnNote returnNote = tdReturnNoteService.findByReturnNumber(returnNumber);

			if (returnNote == null) {
				res.put("message", "参数错误");
				return res;
			}
			// 通知物流
			if ("informDiy".equalsIgnoreCase(type)) {
				// 配送单——到店退
				if (returnNote.getTurnType() == 2 && returnNote.getStatusId() != null
						&& returnNote.getStatusId() == 1) {
					// 生成收货通知
					tdCommonService.sendBackToWMS(returnNote);
					// tdInterfaceService.sendReturnOrderByAsyn(returnNote);
					if (returnNote.getStatusId() == 1) {
						returnNote.setStatusId(2L);
					}
				}
			}
			// 确认收货
			else if ("btnConfirm".equalsIgnoreCase(type)) {
				if (returnNote.getManagerRemarkInfo() != null) {
					returnNote.setManagerRemarkInfo(returnNote.getManagerRemarkInfo() + "|后台确认收货(" + username + ")");
				} else {
					returnNote.setManagerRemarkInfo("后台确认收货(" + username + ")");
				}
				if (returnNote.getStatusId() != null && returnNote.getStatusId() == 2L) {
					returnNote.setStatusId(4L);
					returnNote.setReceiveTime(new Date());
				}
				// 修改库存
				tdDiySiteInventoryService.changeGoodsInventory(returnNote, req);
				TdReturnTimeInf returnTimeInf = tdInterfaceService.initReturnTimeByReturnNote(returnNote);
				tdInterfaceService.ebsWithObject(returnTimeInf, INFTYPE.RETURNTIMEINF);

			}
			// 确认验货
			else if ("examineReturn".equalsIgnoreCase(type)) {
				if (returnNote.getStatusId().equals(3L)) {
					returnNote.setStatusId(4L);
					returnNote.setCheckTime(new Date());
				}
			}
			// 确认退款
			else if ("refund".equals(type)) {
				// 取消状态判断
				// if (returnNote.getStatusId().equals(3L))
				// {
				// 查找订单
				TdOrder order = tdOrderService.findByOrderNumber(returnNote.getOrderNumber());
				if (order != null && order.getStatusId() != null && order.getStatusId() == 9L) {

					// 2017-05-24修改，如果是经销商的订单，确认退款之前需要收回对应的经销差价
					// 判断是否是经销商门店的单据
					Long diySiteId = order.getDiySiteId();
					TdDiySite diySite = tdDiySiteService.findOne(diySiteId);
					if (null != diySite && null != diySite.getCustTypeName()
							&& "经销商".equalsIgnoreCase(diySite.getCustTypeName())) {

						TdDiySiteAccount diySiteAccount = tdDiySiteAccountService.findByDiySiteId(diySite.getId());
						if (null == diySiteAccount) {
							res.put("message", "严重错误：未查询到经销商货款账号，请联系管理员");
							return res;
						}

						TdUser user = tdUserService.findOne(diySiteAccount.getUserId());
						Boolean result = this.checkBalance(user, returnNote);
						if (null == result) {
							res.put("message", "严重错误：未查询到经销商货款账号，请联系管理员");
							return res;
						} else if (!result) {
							res.put("message", "经销商货款账号金额不足（需要返回" + returnNote.getJxReturn() + "元经销差价）");
							return res;
						} else {
							// 返还经销差价
							List<TdCashRefundInf> cashRefundList = tdReturnNoteService
									.doActionWithReturnCash(returnNote, user, diySite);
							for (TdCashRefundInf cashRefundInf : cashRefundList) {
								tdInterfaceService.ebsWithObject(cashRefundInf, INFTYPE.CASHREFUNDINF);
							}
						}
					}

					List<TdCashReturnNote> cashReturnNotes = tdPriceCountService.actAccordingWMS(returnNote,
							order.getId());
					order.setStatusId(12L);
					returnNote.setReturnTime(new Date());
					tdOrderService.save(order);
					if (cashReturnNotes != null && cashReturnNotes.size() > 0) {
						for (TdCashReturnNote tdCashReturnNote : cashReturnNotes) {
							TdCashRefundInf cashRefundInf = tdInterfaceService.initCashRefundInf(tdCashReturnNote);
							tdInterfaceService.ebsWithObject(cashRefundInf, INFTYPE.CASHREFUNDINF);
						}
						for (int index = 0; index < cashReturnNotes.size(); index++) {
							TdCashReturnNote cashReturnNote = cashReturnNotes.get(index);
							if (cashReturnNote != null && cashReturnNote.getMoney() == null
									|| (cashReturnNote.getMoney() != null && cashReturnNote.getMoney() == 0d)) {
								tdCashReturnNoteService.delete(cashReturnNote);
							}
						}
					}
					returnNote.setReturnTime(new Date());
					returnNote.setStatusId(5L);// 退货单设置已完成
				}
				// }
			}

			tdReturnNoteService.save(returnNote);
			res.put("code", 0);
			res.put("message", "修改成功");
			return res;

		}

		res.put("message", "参数错误");
		return res;
	}

	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public String save(TdReturnNote tdReturnNote, ModelMap map, HttpServletRequest req) {
		String username = (String) req.getSession().getAttribute("manager");
		if (null == username) {
			return "redirect:/Verwalter/login";
		}

		if (null == tdReturnNote.getId()) {
			tdManagerLogService.addLog("add", "新增退货单", req);
		} else {
			tdManagerLogService.addLog("edit", "修改退货单", req);
		}
		tdReturnNoteService.save(tdReturnNote);

		return "redirect:/Verwalter/returnNote/returnNote/list";
	}

	@ModelAttribute
	public void getModel(@RequestParam(value = "returnNoteId", required = false) Long returnNoteId, Model model) {
		if (null != returnNoteId) {
			model.addAttribute("tdReturnNote", tdReturnNoteService.findOne(returnNoteId));
		}

	}

	private void btnDelete(String type, Long[] ids, Integer[] chkIds) {
		if (null == type || type.isEmpty()) {
			return;
		}

		if (null == ids || null == chkIds || ids.length < 1 || chkIds.length < 1) {
			return;
		}

		for (int chkId : chkIds) {
			if (chkId >= 0 && ids.length > chkId) {
				Long id = ids[chkId];

				if (type.equalsIgnoreCase("returnNote")) {
					tdReturnNoteService.delete(id);
				}

			}
		}
	}

	private Boolean checkBalance(TdUser user, TdReturnNote returnNote) {
		if (null == user) {
			return null;
		}
		if (user.getBalance() >= returnNote.getJxReturn()) {
			return Boolean.TRUE;
		}
		return Boolean.FALSE;
	}

}
