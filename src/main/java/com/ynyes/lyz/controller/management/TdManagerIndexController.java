package com.ynyes.lyz.controller.management;

import java.util.List;
import java.util.Properties;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ynyes.lyz.entity.TdManager;
import com.ynyes.lyz.entity.TdManagerRole;
import com.ynyes.lyz.entity.TdNavigationMenu;
import com.ynyes.lyz.entity.TdSetting;
import com.ynyes.lyz.service.TdManagerRoleService;
import com.ynyes.lyz.service.TdManagerService;
import com.ynyes.lyz.service.TdNavigationMenuService;
import com.ynyes.lyz.service.TdSettingService;


/**
 * 后台首页控制器
 * 
 * @author Sharon
 */
@Controller
public class TdManagerIndexController {

    @Autowired
    TdNavigationMenuService tdNavigationMenuService;

    @Autowired
    TdManagerService tdManagerService;

    @Autowired
    TdSettingService tdSettingService;
       
    @Autowired
    TdManagerRoleService tdManagerRoleService;
    
    //增加返回值 manager_role:管理员角色 zp
    @RequestMapping(value = "/Verwalter")
    public String index(ModelMap map, HttpServletRequest req) {
        String username = (String) req.getSession().getAttribute("manager");
        if (null == username) {
            return "redirect:/Verwalter/login";
        }

        /**
		 * @author lc
		 * @注释：管理员角色判断
		 */
        TdManager tdManager = tdManagerService.findByUsernameAndIsEnableTrue(username);
        TdManagerRole tdManagerRole = null;
        
        if (null != tdManager && null != tdManager.getRoleId())
        {
            tdManagerRole = tdManagerRoleService.findOne(tdManager.getRoleId());
        }
        
        if (null != tdManagerRole && !tdManagerRole.getIsSys())
        {
        	// rootmenuList :数据库里面的list
        	// rootmenuList :根据角色提取的list
        	List<TdNavigationMenu> rootMenuList = tdNavigationMenuService.findByParentIdAndSort(0L);
        	TdNavigationMenu rootmenuList[] = null;
        	if (null !=rootMenuList )
        	{
        		//将list中的数据存入数组中
                rootmenuList = new TdNavigationMenu[rootMenuList.size()];
            	for(int i = 0; i < rootMenuList.size(); i++)
            	{
            		rootmenuList[i] = rootMenuList.get(i);
            	}
			}
        	
//        	int tempNumber = 0;
//        	int total_index = 0;
//			for(int i = 0; i < rootmenuList.length && total_index < tdManagerRole.getTotalPermission(); i++)
        	for(int i = 0; i < rootmenuList.length; i++)
			{
				TdNavigationMenu rootMenu = null;
//					if (total_index >= tdManagerRole.getPermissionList().size()) 
//					{
//						//rootMenuList.remove(i);
//						rootmenuList[i] = null;
//					}
//					else
//					{
//						if (null!=(tdManagerRole.getPermissionList().get(total_index).getIsView()) && (tdManagerRole.getPermissionList().get(total_index).getIsView())==false)
//						{
//							rootmenuList[i] = null;
//						}
						
				for (int j = 0; j < tdManagerRole.getPermissionList().size(); j++) {
					if(rootmenuList[i].getId() == tdManagerRole.getPermissionList().get(j).getMenuId()){
						if (null != tdManagerRole.getPermissionList().get(j).getIsView() && tdManagerRole.getPermissionList().get(j).getIsView()) {
							rootMenu = rootMenuList.get(i);
						}
						
					}
				}
//					}
//					total_index = total_index + 1;
					
//					if (i < rootmenuList.length) 
//					{
//					    rootMenu = rootMenuList.get(i);
//					}
					
	                // 取一级菜单列表
                List<TdNavigationMenu> level0MenuList = null;
                if (null != rootMenu) 
                {
                	level0MenuList = tdNavigationMenuService.findByParentIdAndSort(rootMenu.getId());
				}
                else
                {
					
				}
	                
                TdNavigationMenu level0menuList[] = null;
                if (null != level0MenuList)
                {
                	//将list中的数据存入数组中
	                level0menuList = new TdNavigationMenu[level0MenuList.size()];
	            	for(int a = 0; a < level0MenuList.size(); a++)
	            	{
	            		level0menuList[a] = level0MenuList.get(a);
	            	}
				}
	                
                int tempIndex = 0;
                if (null != level0menuList && level0menuList.length > 0)
                {
//		                for(int j = 0; j < level0menuList.length && total_index < tdManagerRole.getTotalPermission(); j++)
                	for(int j = 0; j < level0menuList.length; j++)
	                {
//		                	if (total_index >= tdManagerRole.getPermissionList().size())
//		                	{
//		                		level0menuList[j] = null;
//							}
//		                	else
//		                	{
//								if(null!=tdManagerRole.getPermissionList().get(total_index))
//								{
////			                		if (null!=(tdManagerRole.getPermissionList().get(total_index).getIsView()) && (tdManagerRole.getPermissionList().get(total_index).getIsView())==false) 
////			                		{
////			                			level0menuList[j] = null;
////				    				}
//			                	}
//							}
		                	
//			                	total_index = total_index + 1;
			                	
			            TdNavigationMenu level0Menu = null;
//			                	if (j < level0menuList.length) {
//			                		level0Menu = level0MenuList.get(j);
//								}	
                		
                		for (int k = 0; k < tdManagerRole.getPermissionList().size(); k++) {
                			if (null != tdManagerRole.getPermissionList().get(k).getMenuId()) {
	        					if(null != level0menuList[j] && level0menuList[j].getId().longValue() == tdManagerRole.getPermissionList().get(k).getMenuId().longValue()){
	        						if (null != tdManagerRole.getPermissionList().get(k).getIsView() && tdManagerRole.getPermissionList().get(k).getIsView()) {
	        							level0Menu = level0MenuList.get(j);
	        							break;
	        						}
	        						level0menuList[j] = null;
	        					}
                			}
        				}
			                 
	                    // 取二级菜单列表
	                	
	                    List<TdNavigationMenu> level1MenuList = null;
	                    if(null != level0Menu)
	                    {
	                    	level1MenuList = tdNavigationMenuService.findByParentIdAndSort(level0Menu.getId());
	                    }
			                    
	                    TdNavigationMenu level1menuList[] = null;
	                    if (null != level1MenuList) 
	                    {
	                    	//将list中的数据存入数组中
			            	level1menuList = new TdNavigationMenu[level1MenuList.size()];
			            	for(int b = 0; b < level1MenuList.size(); b++)
			            	{
			            		level1menuList[b] = level1MenuList.get(b);
			            	}
						}
			                    
	                    if (null != level1menuList && level1menuList.length > 0) 
	                    {
//				                    for(int c = 0; c < level1menuList.length && total_index < tdManagerRole.getTotalPermission(); c++)
	                    	for(int c = 0; c < level1menuList.length; c++)
		                    {
//				                    	if (total_index >= tdManagerRole.getPermissionList().size()) 
//				                    	{
//				                    		level1menuList[c] = null;
//										}
//				                    	else
//				                    	{
//										if(null!=tdManagerRole.getPermissionList().get(total_index))
////										{
//					                    		if (null!=(tdManagerRole.getPermissionList().get(total_index).getIsView()) && (tdManagerRole.getPermissionList().get(total_index).getIsView())==false) 
//					                    		{
//					                    			level1menuList[c] = null;
//						        				}
//						                    	
//					                    	}
//										}
	                    		boolean temp = false;
	                    		for (int k = 0; k < tdManagerRole.getPermissionList().size(); k++) {
	                    			if (null != tdManagerRole.getPermissionList().get(k).getMenuId()) {
		                    			if(null != level1menuList[c] && level1menuList[c].getId().longValue() == tdManagerRole.getPermissionList().get(k).getMenuId().longValue()){
		            						temp = true;
		            						if (null == tdManagerRole.getPermissionList().get(k).getIsView() || tdManagerRole.getPermissionList().get(k).getIsView() == false) {
		            							level1menuList[c] = null;
		            							break;
		            						}
		            					}
	                    			}
	            				}
	                    		if (!temp) {
	                    			level1menuList[c] = null;
								}
//				                    	total_index = total_index + 1;
		                    }
		                    
		                    change(level1MenuList, level1menuList);
		                    if (null != level1MenuList && level1MenuList.size() > 0) 
		                    {
			                    map.addAttribute("level_" + i + tempIndex + "_menu_list", level1MenuList);
			                    tempIndex +=1;
		                    }
	                    }
	
	                }
	                change(level0MenuList, level0menuList);
	                if (null != level0MenuList && level0MenuList.size() > 0)
	                {
		                map.addAttribute("level_" + i + "_menu_list", level0MenuList);
		             }
                }		
				
			}
			//change(rootMenuList, rootmenuList);
			if (null != rootMenuList && rootMenuList.size() > 0)
			{
				map.addAttribute("root_menu_list", rootMenuList);
		    }
		}
        else
        {
        	List<TdNavigationMenu> rootMenuList = tdNavigationMenuService.findByParentIdAndSort(0L);

            if (null != rootMenuList && rootMenuList.size() > 0) 
            {
                for (int i = 0; i < rootMenuList.size(); i++) 
                {
                    TdNavigationMenu rootMenu = rootMenuList.get(i);

                    // 取一级菜单列表
                    List<TdNavigationMenu> level0MenuList = tdNavigationMenuService
                            .findByParentIdAndSort(rootMenu.getId());

                    if (null != level0MenuList && level0MenuList.size() > 0) 
                    {
                        map.addAttribute("level_" + i + "_menu_list", level0MenuList);

                        for (int j = 0; j < level0MenuList.size(); j++) 
                        {
                            TdNavigationMenu level0Menu = level0MenuList.get(j);

                            // 取二级菜单列表
                            List<TdNavigationMenu> level1MenuList = tdNavigationMenuService.findByParentIdAndSort(level0Menu.getId());

                            if (null != level1MenuList && level1MenuList.size() > 0)
                            {
                                map.addAttribute("level_" + i + j + "_menu_list", level1MenuList);
                            }
                        }
                    }
                }
            }

            map.addAttribute("root_menu_list", rootMenuList);
        }
        map.addAttribute("manager_role", tdManagerRole);
        map.addAttribute("manager_title", tdManager.getRealName());
        return "/site_mag/frame";
    }

    /**
	 * @author lc
	 * @注释：
	 */
    public List<TdNavigationMenu> change(List<TdNavigationMenu> list, TdNavigationMenu shu[]){
    	list.clear();
    	for(int i = 0; i < shu.length; i++){
    		if (null != shu[i]) {
				list.add(shu[i]);
			}
    	}
    	
    	return list;
    } 
    
    @RequestMapping(value = "/Verwalter/center")
    public String center(ModelMap map, HttpServletRequest req) {
        String username = (String) req.getSession().getAttribute("manager");
        if (null == username) {
            return "redirect:/Verwalter/login";
        }
        Properties props = System.getProperties();

        map.addAttribute(
                "os_name",
                props.getProperty("os.name") + " "
                        + props.getProperty("os.version"));
        map.addAttribute("java_home", props.getProperty("java.home"));
        map.addAttribute("java_version", props.getProperty("java.version"));
        map.addAttribute("remote_ip", req.getRemoteAddr());
        map.addAttribute("server_name", req.getServerName());
        map.addAttribute("server_ip", req.getLocalAddr());
        map.addAttribute("server_port", req.getServerPort());

        TdSetting setting = tdSettingService.findTopBy();

        if (null != setting) 
        {
            map.addAttribute("site_name", setting.getTitle());
            map.addAttribute("company_name", setting.getCompany());
        }

        if (!username.equalsIgnoreCase("admin")) {
            TdManager manager = tdManagerService
                    .findByUsernameAndIsEnableTrue(username);
            map.addAttribute("last_ip", manager.getLastLoginIp());
            map.addAttribute("last_login_time", manager.getLastLoginTime());
        }

        String ip = req.getHeader("x-forwarded-for");
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = req.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknow".equalsIgnoreCase(ip)) {
            ip = req.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = req.getRemoteAddr();
        }

        map.addAttribute("client_ip", ip);

        return "/site_mag/center";
    }

  
}
