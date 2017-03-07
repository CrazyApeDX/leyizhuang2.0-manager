package com.ynyes.lyz.controller.management;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.ynyes.lyz.entity.TdCity;
import com.ynyes.lyz.entity.TdPriceList;
import com.ynyes.lyz.service.TdCityService;
import com.ynyes.lyz.service.TdDiySiteService;
import com.ynyes.lyz.service.TdManagerLogService;
import com.ynyes.lyz.service.TdPriceListItemService;
import com.ynyes.lyz.service.TdPriceListService;
import com.ynyes.lyz.util.SiteMagConstant;

// 价目表管理

@Controller
@RequestMapping(value="/Verwalter/pricelist")
public class TdManagerPriceListController {
	@Autowired
	TdPriceListService tdPriceListService;
	
	@Autowired
	TdPriceListItemService tdPriceListItemService;
	
	@Autowired
	TdDiySiteService tdDiySiteService;
	
	@Autowired
	TdManagerLogService tdManagerLogService;
	
	@Autowired
	TdCityService tdCityService;   //读取城市列表需要  zhangji
	
	@RequestMapping(value = "/pricelistitem/list")
    public String settingDistrictList(Integer page,
						            Integer size,
						            String __EVENTTARGET,
						            String __EVENTARGUMENT,
						            String __VIEWSTATE,
						            Long[] listId,
						            Integer[] listChkId,
						            ModelMap map,
						            HttpServletRequest req,
						            String keywords)
    {
    	String username = (String) req.getSession().getAttribute("manager");
        if (null == username)
        {
            return "redirect:/Verwalter/login";
        }
        if (null != __EVENTTARGET)
        {
            if (__EVENTTARGET.equalsIgnoreCase("btnDelete"))
            {
                tdManagerLogService.addLog("delete", "删除行政区划", req);
            }
            else if (__EVENTTARGET.equalsIgnoreCase("btnPage"))
            {
                if (null != __EVENTARGUMENT)
                {
                    page = Integer.parseInt(__EVENTARGUMENT);
                }
            }
            else if (__EVENTTARGET.equalsIgnoreCase("btnSearch"))
            {//点击查询按钮当前页修改为第一页
            	page=0;
            }
        }
        
        if (null == page || page < 0)
        {
            page = 0;
        }
        
        if (null == size || size <= 0)
        {
            size = SiteMagConstant.pageSize;;
        }
        
        map.addAttribute("keywords", keywords);
        map.addAttribute("page", page);
        map.addAttribute("size", size);
        map.addAttribute("__EVENTTARGET", __EVENTTARGET);
        map.addAttribute("__EVENTARGUMENT", __EVENTARGUMENT);
        map.addAttribute("__VIEWSTATE", __VIEWSTATE);
       
        if(StringUtils.isNotBlank(keywords)){
        	map.addAttribute("price_item_page", tdPriceListItemService.findByItemDescContainingOrItemNumContaining(keywords, page, size));
        }else{
        	 map.addAttribute("price_item_page", tdPriceListItemService.findAll(page, size));
        }
        
    	return "/site_mag/pricelist_item_list";
    }
	
	/**
	 * pirceList 表头
	 * @param map
	 * @param req
	 * @return
	 */
	@RequestMapping(value="/list")
	public String pricelist(ModelMap map, HttpServletRequest req,String keywords)
	{
		 String username = (String) req.getSession().getAttribute("manager");
	     if (null == username) {
	         return "redirect:/Verwalter/login";
	     }
         int  page = 0;
         int  size = SiteMagConstant.pageSize;

	     map.addAttribute("page", page);
	     map.addAttribute("size", size);

	     if (StringUtils.isNotBlank(keywords))
	     {
	    	 map.addAttribute("pricelist_page", tdPriceListService.searchAll(keywords, page, size));
		 }
	     else
	     {
	    	 map.addAttribute("pricelist_page", tdPriceListService.findAll(page, size));
	     }
         return "/site_mag/pricelist_list";

	}
	
	@RequestMapping(value="/item/list", method = RequestMethod.GET)
	public String pricelistItem( 
			          ModelMap map,
			          HttpServletRequest req){
		 String username = (String) req.getSession().getAttribute("manager");
	     if (null == username) {
	         return "redirect:/Verwalter/login";
	     }

         int  page = 0;

         int  size = SiteMagConstant.pageSize;

	     map.addAttribute("page", page);
	     map.addAttribute("size", size);

         map.addAttribute("pricelistItem_page", 
        		 tdPriceListItemService.findAll(page, size));
         
         return "/site_mag/pricelistItem_list";

	}
	
//	@RequestMapping(value="/list", method = RequestMethod.POST)
//	public String pricelist( String __EVENTTARGET,Integer page,   Integer size, 
//					  String type,
//			          String __EVENTARGUMENT,
//			          String __VIEWSTATE,
//			          String keywords,
//			          Long[] listId,
//			          Integer[] listChkId,
//			          Double[] listSortId,
//			          ModelMap map,
//			          HttpServletRequest req){
//		 String username = (String) req.getSession().getAttribute("manager");
//	     if (null == username) {
//	         return "redirect:/Verwalter/login";
//	     }
//	     
//	     if (null == type || type.equals(""))
//	     {
//	    	 type = "pricelist";
//	     }
//	        
//	     if (null == page || page < 0)
//	     {
//	         page = 0;
//	     }
//	        
//	     if (null == size || size <= 0)
//	     {
//	         size = SiteMagConstant.pageSize;;
//	     }
//	        
//	     if (null != keywords)
//	     {
//	         keywords = keywords.trim();
//	     }
//	     
//	     if (null != __EVENTTARGET)
//	        {
//	            if (__EVENTTARGET.equalsIgnoreCase("btnDelete"))
//	            {
//	                btnDelete(type, listId, listChkId);
//	                	               
//	                tdManagerLogService.addLog("delete", "删除价目表", req);
//	               
//	            }
//	            else if (__EVENTTARGET.equalsIgnoreCase("btnSave"))
//	            {
//	                btnSave(type, listId, listSortId);
//
//	                tdManagerLogService.addLog("edit", "修改价目表", req);
//	               
//	            }
//	            else if (__EVENTTARGET.equalsIgnoreCase("btnPage"))
//	            {
//	                if (null != __EVENTARGUMENT)
//	                {
//	                    page = Integer.parseInt(__EVENTARGUMENT);
//	                } 
//	            }
//	        } 
//	     
//	     map.addAttribute("page", page);
//	     map.addAttribute("size", size);
//	     map.addAttribute("keywords", keywords);
//	     map.addAttribute("__EVENTTARGET", __EVENTTARGET);
//	     map.addAttribute("__EVENTARGUMENT", __EVENTARGUMENT);
//	     map.addAttribute("__VIEWSTATE", __VIEWSTATE);
//	     
//	     if (type.equalsIgnoreCase("pricelist"))
//	     {
//	    	 if (null == keywords)
//	         {
//	             map.addAttribute("pricelist_page", 
//	            		 tdPriceListService.findAll(page, size));
//	         }
//	         else
//	         {
//	             map.addAttribute("pricelist_page", 
//	            		 tdPriceListService.searchAll(keywords, page, size));
//	         }
//	         
//	         return "/site_mag/pricelist_list";
//	     }
//	     else if(type.equalsIgnoreCase("pricelistItem"))
//	     {
//	    	 if (null == keywords)
//	         {
//	             map.addAttribute("pricelistItem_page", 
//	            		 tdPriceListItemService.findAll(page, size));
//	         }
//	         else
//	         {
//	             map.addAttribute("pricelistItem_page", 
//	            		 tdPriceListItemService.searchAll(keywords, page, size));
//	         }
//	         
//	         return "/site_mag/pricelistItem_list";
//	     }
//	     return "/site_mag/center";
//	}
	
	/**
	 * 编辑价目表
	 * @author Zhangji
	 * @param id
	 * @param map
	 * @param req
	 * @return
	 */
	@RequestMapping(value = "/edit")
    public String priceListEdit(Long id, ModelMap map,
            HttpServletRequest req) {
        String username = (String) req.getSession().getAttribute("manager");
        if (null == username) {
            return "redirect:/Verwalter/login";
        }
        
        if (null != id)
        {
        	map.addAttribute("priceList",tdPriceListService.findOne(id));
        }
    	
    	map.addAttribute("city_list", tdCityService.findAll());

        return "/site_mag/pricelist_edit";
    }
	

	
	//zhangji 保存价目表
	@RequestMapping(value = "/save")
    public String priceListSave(TdPriceList tdPriceList,  ModelMap map,
            HttpServletRequest req) {
        String username = (String) req.getSession().getAttribute("manager");
        if (null == username) {
            return "redirect:/Verwalter/login";
        }

        if (null != tdPriceList.getCityId())
        {
        	 TdCity tdCity = tdCityService.findOne(tdPriceList.getCityId());
        	 tdPriceList.setcityName(tdCity.getProvince()+"-"+tdCity.getCityName());
        	 
             tdPriceListService.save(tdPriceList);
        	 
             Long id = tdPriceList.getId();
             String number = String.format("%04d", id);
             tdPriceList.setPriceListNumber(number);
             tdPriceListService.save(tdPriceList);
             
             if (null != tdPriceList.getId())
             {
                 tdCity.setPriceListId(tdPriceList.getId());
                 tdCityService.save(tdCity);
             }
        }

        return "redirect:/Verwalter/pricelist/list";
    }
	
	
	@RequestMapping(value = "/itemEdit")
    public String categoryEditDialog(Long id, String __EVENTTARGET,
            String __EVENTARGUMENT, String __VIEWSTATE, ModelMap map,
            HttpServletRequest req) {
        String username = (String) req.getSession().getAttribute("manager");
        if (null == username) {
            return "redirect:/Verwalter/login";
        }

        map.addAttribute("__EVENTTARGET", __EVENTTARGET);
        map.addAttribute("__EVENTARGUMENT", __EVENTARGUMENT);
        map.addAttribute("__VIEWSTATE", __VIEWSTATE);
        
        map.addAttribute("pricelist_list", tdPriceListService.findAll());
        
        if (null != id) {
            map.addAttribute("pricelist",tdPriceListService.findOne(id));
        }

        return "/site_mag/pricelistItem_edit";
    }
	
	//保存价目表管理内容 zhangji
	@RequestMapping(value = "/itemSave")
    public String priceListItemSave(TdPriceList tdPriceList,  ModelMap map,
            HttpServletRequest req) {
        String username = (String) req.getSession().getAttribute("manager");
        if (null == username) {
            return "redirect:/Verwalter/login";
        }
        
        tdPriceListService.save(tdPriceList);

        return "redirect:/Verwalter/pricelist/list";
    }
	
//	 private void btnSave(String type, Long[] ids, Double[] sortIds)
//	    {
//	        if (null == type || type.isEmpty())
//	        {
//	            return;
//	        }
//	        
//	        if (null == ids || null == sortIds
//	                || ids.length < 1 || sortIds.length < 1)
//	        {
//	            return;
//	        }
//	        
//	        for (int i = 0; i < ids.length; i++)
//	        {
//	            Long id = ids[i];
//	            
//	            //zhangji 修改
//	            if (type.equalsIgnoreCase("pricelist"))
//	            {
//	                TdPriceList e = tdPriceListService.findOne(id);
//	                
//	                if (null != e)
//	                {
//	                    if (sortIds.length > i)
//	                    {
//	                        e.setSortId(sortIds[i]);
//	                        tdPriceListService.save(e);
//	                    }
//	                }
//	            }
//	            else if (type.equalsIgnoreCase("pricelistItem"))
//	            {
//	                TdPriceListItem e = tdPriceListItemService.findOne(id);
//	                
//	                if (null != e)
//	                {
//	                    if (sortIds.length > i)
//	                    {
//	                        e.setSortId(sortIds[i]);
//	                        tdPriceListItemService.save(e);
//	                    }
//	                }
//	            }
//	           
//	        }
//	    }
//	    
//	    private void btnDelete(String type, Long[] ids, Integer[] chkIds)
//	    {
//	        if (null == type || type.isEmpty())
//	        {
//	            return;
//	        }
//	        
//	        if (null == ids || null == chkIds
//	                || ids.length < 1 || chkIds.length < 1)
//	        {
//	            return;
//	        }
//	        
//	        for (int chkId : chkIds)
//	        {
//	            if (chkId >=0 && ids.length > chkId)
//	            {
//	                Long id = ids[chkId];
//	                
//	                // zhangji 修改
//	                if (type.equalsIgnoreCase("pricelist"))
//	                {
//	                	//同步城市表
//	                	TdCity tdCity = tdCityService
//	                			.findOne(tdPriceListService
//	                					.findOne(id).getCityId());
//	                	tdCity.setPriceListId(null);
//	                	tdCityService.save(tdCity);
//	                	
//	                	//删除价目表商品 zhangji
//	                	List<TdPriceListItem> itemList = tdPriceListItemService.findByPriceListIdOrderBySortIdAsc(id);
//	                	tdPriceListItemService.delete(itemList);
//	                	
//	                	tdPriceListService.delete(id);
//	                }
//	                else if (type.equalsIgnoreCase("pricelistItem"))
//	                {
//	                	tdPriceListItemService.delete(id);
//	                }
//	            }
//	        }
//	    }
}
