package com.ynyes.lyz.controller.management;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.ynyes.lyz.entity.TdRequisition;
import com.ynyes.lyz.service.TdManagerLogService;
import com.ynyes.lyz.service.TdRequisitionService;
import com.ynyes.lyz.util.SiteMagConstant;

@Controller
@RequestMapping(value="/Verwalter/requisition")
public class TdManagerRequisitionController {
	
	@Autowired
	TdRequisitionService tdRequisitionService;
	
    @Autowired
    TdManagerLogService tdManagerLogService;
	
	// 列表
	@RequestMapping(value="/{type}/list")
	public String list(@PathVariable String type,
						Integer page,
			            Integer size,
			            String keywords,
			            String __EVENTTARGET,
			            String __EVENTARGUMENT,
			            String __VIEWSTATE,
			            Long[] listId,
			            Integer[] listChkId,
			            Double[] listSortId,
			            ModelMap map,
			            HttpServletRequest req){
		String username = (String) req.getSession().getAttribute("manager");
	    if (null == username) {
	        return "redirect:/Verwalter/login";
	    }
	     
	    if (null != __EVENTTARGET) {
	    	 if (__EVENTTARGET.equalsIgnoreCase("btnDelete"))
	            {
	                btnDelete(type, listId, listChkId);
	                
	                if (type.equalsIgnoreCase("requisition"))
	                {
	                    tdManagerLogService.addLog("delete", "删除要货单", req);
	                }else if (type.equalsIgnoreCase("requisitionChargeback"))
	                {
	                	tdManagerLogService.addLog("delete", "删除要货单退单", req);
					}else if (type.equalsIgnoreCase("requisitionOrder")) 
					{
						tdManagerLogService.addLog("delete", "删除要货单订单", req);
					}
	               
	            }
	            else if (__EVENTTARGET.equalsIgnoreCase("btnSave"))
	            {
	                btnSave(type, listId, listSortId);
	                
	                if (type.equalsIgnoreCase("requisition"))
	                {
	                    tdManagerLogService.addLog("edit", "修改要货单", req);
	                }else if (type.equalsIgnoreCase("requisitionChargeback"))
	                {
	                	tdManagerLogService.addLog("edit", "修改要货单退单", req);
					}else if (type.equalsIgnoreCase("requisitionOrder")) 
					{
						tdManagerLogService.addLog("edit", "修改要货单订单", req);
					}
	               
	            }
	            else if (__EVENTTARGET.equalsIgnoreCase("btnPage"))
	            {
	                if (null != __EVENTARGUMENT)
	                {
	                    page = Integer.parseInt(__EVENTARGUMENT);
	                } 
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
        
        map.addAttribute("page", page);
        map.addAttribute("size", size);
        map.addAttribute("keywords", keywords);
        map.addAttribute("__EVENTTARGET", __EVENTTARGET);
        map.addAttribute("__EVENTARGUMENT", __EVENTARGUMENT);
        map.addAttribute("__VIEWSTATE", __VIEWSTATE);
        
        if (null != type)
        {
        	if (type.equalsIgnoreCase("requisition")) // 
            {
                if (null == keywords)
                {
                    map.addAttribute("requisition_page", 
                            tdRequisitionService.findByTypeId(1L, page, size));
                }
                else
                {
//                    map.addAttribute("requisition_page", 
//                    		tdRequisitionService.searchByTypeId(keywords, 1L, page, size));
                }
                
                return "/site_mag/requisition_list";
            }
        	if (type.equalsIgnoreCase("requisitionChargeback")) // 
            {
                if (null == keywords)
                {
                    map.addAttribute("requisition_page", 
                            tdRequisitionService.findByTypeId(2L, page, size));
                }
//                else
//                {
//                    map.addAttribute("requisition_page", 
//                    		tdRequisitionService.searchByTypeId(keywords, 2L, page, size));
//                }
                
                return "/site_mag/requisition_list";
            }
        	if (type.equalsIgnoreCase("requisitionOrder")) // 
            {
                if (null == keywords)
                {
                    map.addAttribute("requisition_page", 
                            tdRequisitionService.findByTypeId(3L, page, size));
                }
//                else
//                {
//                    map.addAttribute("requisition_page", 
//                    		tdRequisitionService.searchByTypeId(keywords, 3L, page, size));
//                }
                
                return "/site_mag/requisition_list";
            }
        }
        return "/site_mag/requisition_list";
	}
	
	 @RequestMapping(value="/{type}/edit")
	 public String edit(@PathVariable String type, 
	                        Long id,
	                        String __VIEWSTATE,
	                        ModelMap map,
	                        HttpServletRequest req){
	        String username = (String) req.getSession().getAttribute("manager");
	        if (null == username)
	        {
	            return "redirect:/Verwalter/login";
	        }
	        
	        map.addAttribute("__VIEWSTATE", __VIEWSTATE);
	        
	        if (null != type)
	        {
	            if (type.equalsIgnoreCase("requisition")) // 支付方式
	            {
	                if (null != id)
	                {
	                    map.addAttribute("requisition", tdRequisitionService.findOne(id));
	                }
	                
	                return "/site_mag/requisition_edit";
	            }else if (type.equalsIgnoreCase("requisitionChargeback")) 
	            {
	            	 if (null != id)
		             {
		                 map.addAttribute("requisition", tdRequisitionService.findOne(id));
		             }
				}else if (type.equalsIgnoreCase("requisitionOrder")) 
	            {
					 if (null != id)
		             {
		                 map.addAttribute("requisition", tdRequisitionService.findOne(id));
		             }
				}	          
	        }
	        
	        return "/site_mag/requisition_edit";
	}
	
	 @RequestMapping(value="/save", method = RequestMethod.POST)
	 public String save(TdRequisition tdRequisition,
	                        ModelMap map,
	                        HttpServletRequest req){
	        String username = (String) req.getSession().getAttribute("manager");
	        if (null == username)
	        {
	            return "redirect:/Verwalter/login";
	        }
	        
	        if (null == tdRequisition.getId())
	        {
	            tdManagerLogService.addLog("add", "新增要货单", req);
	        }
	        else
	        {
	            tdManagerLogService.addLog("edit", "修改要货单", req);
	        }
	        tdRequisitionService.save(tdRequisition);
	        
	        return "redirect:/Verwalter/requisition/requisition/list";
	    } 
	
	 @ModelAttribute
	    public void getModel(@RequestParam(value = "requisitionId", required = false) Long requisitionId,
	                        Model model) {
	        if (null != requisitionId) {
	            model.addAttribute("tdRequisition", tdRequisitionService.findOne(requisitionId));
	        }
	        	      
	    }
	 
	private void btnDelete(String type, Long[] ids, Integer[] chkIds)
    {
        if (null == type || type.isEmpty())
        {
            return;
        }
        
        if (null == ids || null == chkIds
                || ids.length < 1 || chkIds.length < 1)
        {
            return;
        }
        
        for (int chkId : chkIds)
        {
            if (chkId >=0 && ids.length > chkId)
            {
                Long id = ids[chkId];
                
                if (type.equalsIgnoreCase("requisition"))
                {
                    tdRequisitionService.delete(id);
                }
               
            }
        }
    }
	
	 private void btnSave(String type, Long[] ids, Double[] sortIds)
	    {
	        if (null == type || type.isEmpty())
	        {
	            return;
	        }
	        
	        if (null == ids || null == sortIds
	                || ids.length < 1 || sortIds.length < 1)
	        {
	            return;
	        }
	        
	        for (int i = 0; i < ids.length; i++)
	        {
	            Long id = ids[i];
	            
	            if (type.equalsIgnoreCase("requisition"))
	            {
	                TdRequisition e = tdRequisitionService.findOne(id);
	                
	                if (null != e)
	                {
	                    if (sortIds.length > i)
	                    {
	                       // e.setSortId(sortIds[i]);
	                        tdRequisitionService.save(e);
	                    }
	                }
	            }
	          
	        }
	    }
}
