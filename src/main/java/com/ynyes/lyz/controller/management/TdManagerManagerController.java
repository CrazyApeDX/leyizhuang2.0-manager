package com.ynyes.lyz.controller.management;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ynyes.lyz.entity.TdManager;
import com.ynyes.lyz.entity.TdManagerDiySiteRole;
import com.ynyes.lyz.entity.TdManagerPermission;
import com.ynyes.lyz.entity.TdManagerPermissionList;
import com.ynyes.lyz.entity.TdManagerRole;
import com.ynyes.lyz.entity.TdNavigationMenu;
import com.ynyes.lyz.service.TdCityService;
import com.ynyes.lyz.service.TdDiySiteRoleService;
import com.ynyes.lyz.service.TdDiySiteService;
import com.ynyes.lyz.service.TdManagerLogService;
import com.ynyes.lyz.service.TdManagerRoleService;
import com.ynyes.lyz.service.TdManagerService;
import com.ynyes.lyz.service.TdNavigationMenuService;
import com.ynyes.lyz.util.SiteMagConstant;

/**
 * 后台管理员控制器
 * 
 * @author Sharon
 */

@Controller
@RequestMapping(value = "/Verwalter/manager")
public class TdManagerManagerController {

	@Autowired
	TdManagerService tdManagerService;

	@Autowired
	TdManagerLogService tdManagerLogService;

	@Autowired
	TdManagerRoleService tdManagerRoleService;

	@Autowired
	TdNavigationMenuService tdNavigationMenuService;

	@Autowired
	TdDiySiteRoleService tdDiySiteRoleService;

	@Autowired
	TdDiySiteService tdDiySiteService;

	@Autowired
	TdCityService tdCityService;

	@RequestMapping(value = "/list")
	public String managerList(Integer page, Integer size, String __EVENTTARGET, String __EVENTARGUMENT,
			String __VIEWSTATE, Long[] listId, Integer[] listChkId, Double[] listSortId, ModelMap map,
			HttpServletRequest req, String keywords) {
		String username = (String) req.getSession().getAttribute("manager");
		if (null == username) {
			return "redirect:/Verwalter/login";
		}
		if (null != __EVENTTARGET) {
			if (__EVENTTARGET.equalsIgnoreCase("btnDelete")) {
				if (username.equalsIgnoreCase("tdadmin")) {
					btnDelete(listId, listChkId);
					tdManagerLogService.addLog("delete", "删除管理员", req);
				}
			} else if (__EVENTTARGET.equalsIgnoreCase("btnSave")) {
				btnSave(listId, listSortId);
				tdManagerLogService.addLog("edit", "修改管理员", req);
			} else if (__EVENTTARGET.equalsIgnoreCase("btnPage")) {
				if (null != __EVENTARGUMENT) {
					page = Integer.parseInt(__EVENTARGUMENT);
				}
			} else if (__EVENTTARGET.equalsIgnoreCase("btnSearch")) {
				page = 0;
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
		map.addAttribute("__EVENTTARGET", __EVENTTARGET);
		map.addAttribute("__EVENTARGUMENT", __EVENTARGUMENT);
		map.addAttribute("__VIEWSTATE", __VIEWSTATE);
		if (null != keywords) {
			map.addAttribute("manager_page", tdManagerService.searchAndOrderByIdDesc(keywords, page, size));
		} else {
			map.addAttribute("manager_page", tdManagerService.findAllOrderBySortIdAsc(page, size));
		}
		map.addAttribute("keywords", keywords);

		return "/site_mag/manager_list";
	}

	@RequestMapping(value = "/role/list")
	public String roleList(Integer page, Integer size, String __EVENTTARGET, String __EVENTARGUMENT, String __VIEWSTATE,
			Long[] listId, Integer[] listChkId, ModelMap map, HttpServletRequest req) {
		String username = (String) req.getSession().getAttribute("manager");

		if (null == username) {
			return "redirect:/Verwalter/login";
		}

		if (null != __EVENTTARGET) {
			if (__EVENTTARGET.equalsIgnoreCase("btnDelete")) {
				btnDeleteRole(listId, listChkId);
				tdManagerLogService.addLog("delete", "删除管理员角色", req);
			} else if (__EVENTTARGET.equalsIgnoreCase("btnPage")) {
				if (null != __EVENTARGUMENT) {
					page = Integer.parseInt(__EVENTARGUMENT);
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
		map.addAttribute("__EVENTTARGET", __EVENTTARGET);
		map.addAttribute("__EVENTARGUMENT", __EVENTARGUMENT);
		map.addAttribute("__VIEWSTATE", __VIEWSTATE);

		map.addAttribute("role_page", tdManagerRoleService.findAll(page, size));

		return "/site_mag/manager_role_list";
	}

	@RequestMapping(value = "/role/edit")
	public String roleEdit(Long id, String __VIEWSTATE, ModelMap map, HttpServletRequest req) {
		String username = (String) req.getSession().getAttribute("manager");
		if (null == username) {
			return "redirect:/Verwalter/login";
		}

		map.addAttribute("__VIEWSTATE", __VIEWSTATE);

		if (null != id) {
			map.addAttribute("tdRole", tdManagerRoleService.findOne(id));
		}

		// 根菜单列表
		List<TdNavigationMenu> rootMenuList = tdNavigationMenuService.findByParentIdAndSort(0L);

		if (null != rootMenuList && rootMenuList.size() > 0) {
			for (int i = 0; i < rootMenuList.size(); i++) {
				TdNavigationMenu rootMenu = rootMenuList.get(i);

				// 取一级菜单列表
				List<TdNavigationMenu> level0MenuList = tdNavigationMenuService.findByParentIdAndSort(rootMenu.getId());

				if (null != level0MenuList && level0MenuList.size() > 0) {
					map.addAttribute("level_" + i + "_menu_list", level0MenuList);

					for (int j = 0; j < level0MenuList.size(); j++) {
						TdNavigationMenu level0Menu = level0MenuList.get(j);

						// 取二级菜单列表
						List<TdNavigationMenu> level1MenuList = tdNavigationMenuService
								.findByParentIdAndSort(level0Menu.getId());

						if (null != level1MenuList && level1MenuList.size() > 0) {
							map.addAttribute("level_" + i + j + "_menu_list", level1MenuList);
						}

					}
				}

			}
		}

		map.addAttribute("root_menu_list", rootMenuList);

		return "/site_mag/manager_role_edit";
	}

	@RequestMapping(value = "/role/save")
	public String roleSave(TdManagerRole tdManagerRole, TdManagerPermissionList tdManagerPermissionList,
			String __VIEWSTATE, ModelMap map, HttpServletRequest req) {
		String username = (String) req.getSession().getAttribute("manager");
		if (null == username) {
			return "redirect:/Verwalter/login";
		}

		map.addAttribute("__VIEWSTATE", __VIEWSTATE);

		/**
		 * @author lc
		 * @注释：权限修改
		 */
		// for(int i = 0; i <
		// tdManagerPermissionList.getPermissionlist().size(); i++){
		// System.out.println(tdManagerPermissionList.getPermissionlist().get(i).getIsView()+"
		// "+i);
		// }
		//
		if (null != tdManagerRole.getPermissionList()) {
			for (int i = 0; i < tdManagerRole.getPermissionList().size(); i++) {
				if (null == tdManagerRole.getPermissionList().get(i).getIsView()) {
					tdManagerRole.getPermissionList().get(i).setIsView(false);
				}
			}
		}
		if (null == tdManagerRole.getPermissionList() && null != tdManagerPermissionList
				&& null != tdManagerPermissionList.getPermissionlist()) {
			tdManagerRole.setPermissionList(tdManagerPermissionList.getPermissionlist());
		} else {

			if (null != tdManagerPermissionList && null != tdManagerPermissionList.getPermissionlist()) {

				if (tdManagerPermissionList.getPermissionlist().size() < tdManagerRole.getPermissionList().size()) {
					for (int i = 0; i < tdManagerPermissionList.getPermissionlist().size(); i++) {
						if (null != tdManagerPermissionList.getPermissionlist().get(i).getIsView()
								&& tdManagerPermissionList.getPermissionlist().get(i).getIsView()) {
							tdManagerRole.getPermissionList().get(i).setIsView(true);
						} else {
							tdManagerRole.getPermissionList().get(i).setIsView(false);
						}
					}
					for (int i = tdManagerPermissionList.getPermissionlist().size(); i < tdManagerRole
							.getPermissionList().size(); i++) {
						tdManagerRole.getPermissionList().get(i).setIsView(false);
					}
				} else {
					for (int i = 0; i < tdManagerPermissionList.getPermissionlist().size(); i++) {
						if (i >= tdManagerRole.getPermissionList().size()) {
							TdManagerPermission tdManagerPermission = new TdManagerPermission();
							if (null != tdManagerPermissionList.getPermissionlist().get(i).getIsView()
									&& tdManagerPermissionList.getPermissionlist().get(i).getIsView()) {
								tdManagerPermission.setIsView(true);
							} else {
								tdManagerPermission.setIsView(false);
							}

							tdManagerRole.getPermissionList().add(tdManagerPermission);
						} else {
							if (null != tdManagerPermissionList.getPermissionlist().get(i).getIsView()
									&& tdManagerPermissionList.getPermissionlist().get(i).getIsView()) {
								tdManagerRole.getPermissionList().get(i).setIsView(true);
							} else {
								tdManagerRole.getPermissionList().get(i).setIsView(false);
							}
						}

					}
				}
			} else {
				if (null != tdManagerRole.getPermissionList()) {
					for (int i = 0; i < tdManagerRole.getPermissionList().size(); i++) {
						tdManagerRole.getPermissionList().get(i).setIsView(false);
					}
				}

			}

		}
		if (null == tdManagerRole.getPermissionList() && null == tdManagerPermissionList.getPermissionlist()) {
			List<TdManagerPermission> tdManagerPermissions = new ArrayList<>();
			tdManagerRole.setPermissionList(tdManagerPermissions);
			int totalsize = tdNavigationMenuService.findAllIsEnableTrue().size();
			for (int i = 0; i < totalsize; i++) {
				TdManagerPermission tdManagerPermission = new TdManagerPermission();
				tdManagerPermission.setIsView(false);
				tdManagerRole.getPermissionList().add(tdManagerPermission);
			}
		}
		// 将为空的权限设为false
		int totalsize = tdNavigationMenuService.findAllIsEnableTrue().size();
		for (int i = 0; i < totalsize; i++) {
			if (i < tdManagerRole.getPermissionList().size()) {
				if (null == tdManagerRole.getPermissionList().get(i).getIsView()) {
					tdManagerRole.getPermissionList().get(i).setIsView(false);
				}
			} else {
				TdManagerPermission tdManagerPermission = new TdManagerPermission();
				tdManagerPermission.setIsView(false);
				tdManagerRole.getPermissionList().add(tdManagerPermission);
			}
		}

		tdManagerRoleService.save(tdManagerRole);

		if (null == tdManagerRole.getId()) {
			tdManagerLogService.addLog("add", "修改管理角色权限", req);
		} else {
			tdManagerLogService.addLog("edit", "修改管理角色权限", req);
		}

		return "redirect:/Verwalter/manager/role/list";
	}

	@RequestMapping(value = "/log")
	public String logList(Integer page, Integer size, String __EVENTTARGET, String __EVENTARGUMENT, String __VIEWSTATE,
			String action, Long[] listId, Integer[] listChkId, ModelMap map, HttpServletRequest req) {
		String username = (String) req.getSession().getAttribute("manager");
		if (null == username) {
			return "redirect:/Verwalter/login";
		}
		if (null != __EVENTTARGET) {
			if (__EVENTTARGET.equalsIgnoreCase("btnDelete")) {
				btnDeleteLog(listId, listChkId);
				tdManagerLogService.addLog("delete", "删除管理日志", req);
			} else if (__EVENTTARGET.equalsIgnoreCase("btnPage")) {
				if (null != __EVENTARGUMENT) {
					page = Integer.parseInt(__EVENTARGUMENT);
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
		map.addAttribute("action", action);
		map.addAttribute("__EVENTTARGET", __EVENTTARGET);
		map.addAttribute("__EVENTARGUMENT", __EVENTARGUMENT);
		map.addAttribute("__VIEWSTATE", __VIEWSTATE);

		if (null == action || action.isEmpty()) {
			map.addAttribute("log_page", tdManagerLogService.findAll(page, size));
		} else {
			map.addAttribute("log_page", tdManagerLogService.findByActionType(action, page, size));
		}

		return "/site_mag/log_list";
	}

	@RequestMapping(value = "/edit")
	public String managerEdit(Long id, String __VIEWSTATE, ModelMap map, HttpServletRequest req) {
		String username = (String) req.getSession().getAttribute("manager");
		if (null == username) {
			return "redirect:/Verwalter/login";
		}

		map.addAttribute("__VIEWSTATE", __VIEWSTATE);

		/**
		 * @author lc
		 * @注释：添加角色类型
		 */
		map.addAttribute("role_list", tdManagerRoleService.findAll());

		if (null != id) {
			map.addAttribute("tdManager", tdManagerService.findOne(id));
		}
		return "/site_mag/manager_edit";
	}

	@RequestMapping(value = "/save")
	public String orderEdit(TdManager tdManager, String __VIEWSTATE, ModelMap map, HttpServletRequest req) {
		String username = (String) req.getSession().getAttribute("manager");
		if (null == username) {
			return "redirect:/Verwalter/login";
		}

		map.addAttribute("__VIEWSTATE", __VIEWSTATE);

		if (null == tdManager.getId()) {
			tdManagerLogService.addLog("add", "修改管理员", req);
		} else {
			tdManagerLogService.addLog("edit", "修改管理员", req);
		}

		tdManagerService.save(tdManager);

		return "redirect:/Verwalter/manager/list";
	}

	@RequestMapping(value = "/diysiterole/list")
	public String diysiteroleList(Integer page, Integer size, String __EVENTTARGET, String __EVENTARGUMENT,
			String __VIEWSTATE, Long[] listId, Integer[] listChkId, ModelMap map, HttpServletRequest req) {
		String username = (String) req.getSession().getAttribute("manager");

		if (null == username) {
			return "redirect:/Verwalter/login";
		}

		// 管理员角色
		// TdManager tdManager =
		// tdManagerService.findByUsernameAndIsEnableTrue(username);
		// TdManagerRole tdManagerRole = null;
		//
		// if (null != tdManager && null != tdManager.getRoleId())
		// {
		// tdManagerRole = tdManagerRoleService.findOne(tdManager.getRoleId());
		// }
		//
		// if (null != tdManagerRole) {
		// map.addAttribute("tdManagerRole", tdManagerRole);
		// }

		if (null != __EVENTTARGET) {
			if (__EVENTTARGET.equalsIgnoreCase("btnDelete")) {
				btnDeleteDiySiteRole(listId, listChkId);
				tdManagerLogService.addLog("delete", "删除门店权限角色", req);
			} else if (__EVENTTARGET.equalsIgnoreCase("btnPage")) {
				if (null != __EVENTARGUMENT) {
					page = Integer.parseInt(__EVENTARGUMENT);
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
		map.addAttribute("__EVENTTARGET", __EVENTTARGET);
		map.addAttribute("__EVENTARGUMENT", __EVENTARGUMENT);
		map.addAttribute("__VIEWSTATE", __VIEWSTATE);

		map.addAttribute("role_page", tdDiySiteRoleService.findAll(page, size));

		return "/site_mag/manager_diysiterole_list";
	}

	/**
	 * 增加城市列表 增加返回值:所有非超级管理员 zp
	 */
	@RequestMapping(value = "/diysiterole/edit")
	public String diysiteRoleEdit(Long id, String __VIEWSTATE, ModelMap map, HttpServletRequest req) {
		String username = (String) req.getSession().getAttribute("manager");
		if (null == username) {
			return "redirect:/Verwalter/login";
		}

		map.addAttribute("__VIEWSTATE", __VIEWSTATE);

		if (null != id) {
			map.addAttribute("tdDiySiteRole", tdDiySiteRoleService.findOne(id));

		}

		// 门店列表
		Sort sort = new Sort(Direction.DESC, "cityId");
		map.addAttribute("diysite_list", tdDiySiteService.findAll(sort));
		// 所有非超级管理员
		map.addAttribute("manager_role", tdManagerRoleService.findByIsSys(false));

		return "/site_mag/manager_diysiterole_edit";
	}

	// 增加城市权限 zp
	@RequestMapping(value = "/diysiterole/save")
	public String diysiteRoleSave(TdManagerDiySiteRole tdManagerDiySiteRole, Integer[] listChkId, String __VIEWSTATE,
			ModelMap map, HttpServletRequest req, Integer[] cityChkIds) {
		String username = (String) req.getSession().getAttribute("manager");
		if (null == username) {
			return "redirect:/Verwalter/login";
		}

		map.addAttribute("__VIEWSTATE", __VIEWSTATE);

		if (null == listChkId) {
			tdManagerDiySiteRole.setDiySiteTree(null);
		} else {
			String idstr = "";
			for (Integer chkId : listChkId) {
				idstr = idstr + "[" + chkId + "]";
			}

			tdManagerDiySiteRole.setDiySiteTree(idstr);
		}
		// 增加管理城市
		if (null == cityChkIds) {
			tdManagerDiySiteRole.setCityTree(null);
		} else {
			String idstr = "";
			for (Integer chkId : cityChkIds) {
				idstr = idstr + "[" + chkId + "]";
			}

			tdManagerDiySiteRole.setCityTree(idstr);
		}

		tdDiySiteRoleService.save(tdManagerDiySiteRole);

		return "redirect:/Verwalter/manager/diysiterole/list";
	}

	@ModelAttribute
	public void getModel(@RequestParam(value = "id", required = false) Long id,
			@RequestParam(value = "roleId", required = false) Long roleId,
			@RequestParam(value = "diySiteRoleId", required = false) Long diySiteRoleId, Model model) {
		if (null != id) {
			model.addAttribute("tdManager", tdManagerService.findOne(id));
		}

		if (null != roleId) {
			model.addAttribute("tdManagerRole", tdManagerRoleService.findOne(roleId));
		}

		if (null != diySiteRoleId) {
			model.addAttribute("tdManagerDiySiteRole", tdDiySiteRoleService.findOne(diySiteRoleId));
		}
	}

	private void btnSave(Long[] ids, Double[] sortIds) {
		if (null == ids || null == sortIds || ids.length < 1 || sortIds.length < 1) {
			return;
		}

		for (int i = 0; i < ids.length; i++) {
			Long id = ids[i];

			TdManager e = tdManagerService.findOne(id);

			if (null != e) {
				if (sortIds.length > i) {
					e.setSortId(new Double(sortIds[i]));
					tdManagerService.save(e);
				}
			}
		}
	}

	private void btnDelete(Long[] ids, Integer[] chkIds) {
		if (null == ids || null == chkIds || ids.length < 1 || chkIds.length < 1) {
			return;
		}

		for (int chkId : chkIds) {
			if (chkId >= 0 && ids.length > chkId) {
				Long id = ids[chkId];

				tdManagerService.delete(id);
			}
		}
	}

	private void btnDeleteLog(Long[] ids, Integer[] chkIds) {
		if (null == ids || null == chkIds || ids.length < 1 || chkIds.length < 1) {
			return;
		}

		for (int chkId : chkIds) {
			if (chkId >= 0 && ids.length > chkId) {
				Long id = ids[chkId];

				tdManagerLogService.delete(id);
			}
		}
	}

	private void btnDeleteRole(Long[] ids, Integer[] chkIds) {
		if (null == ids || null == chkIds || ids.length < 1 || chkIds.length < 1) {
			return;
		}

		for (int chkId : chkIds) {
			if (chkId >= 0 && ids.length > chkId) {
				Long id = ids[chkId];

				tdManagerRoleService.delete(id);
			}
		}
	}

	private void btnDeleteDiySiteRole(Long[] ids, Integer[] chkIds) {
		if (null == ids || null == chkIds || ids.length < 1 || chkIds.length < 1) {
			return;
		}

		for (int chkId : chkIds) {
			if (chkId >= 0 && ids.length > chkId) {
				Long id = ids[chkId];

				tdDiySiteRoleService.delete(id);
			}
		}
	}

	@RequestMapping(value = "/check", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, String> checkUsername(String param, Long id) {
		Map<String, String> res = new HashMap<String, String>();
		res.put("status", "n");

		if (null == param || "".equals(param.trim())) {
			res.put("info", "用户名不能为空");
			return res;
		}

		TdManager manager = tdManagerService.findByUsername(param);
		if (null == id) {
			if (null != manager) {
				res.put("info", "输入用户名重复");
				return res;
			} 
		}
		else if (null !=manager&&id!=manager.getId()){
			res.put("info", "输入用户名重复");
			return res;
	}
		res.put("status", "y");
		return res;

	}
}
