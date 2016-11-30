package com.ynyes.lyz.controller.management;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.ynyes.lyz.entity.TdArticle;
import com.ynyes.lyz.entity.TdArticleCategory;
import com.ynyes.lyz.service.TdArticleCategoryService;
import com.ynyes.lyz.service.TdArticleService;
import com.ynyes.lyz.service.TdManagerLogService;
import com.ynyes.lyz.util.SiteMagConstant;



@Controller
@RequestMapping(value="/Verwalter")
public class TdManagerArticleController {
	 @Autowired
	    TdArticleCategoryService tdArticleCategoryService;
	    
	    @Autowired
	    TdArticleService tdArticleService;
	    	    
	    @Autowired
	    TdManagerLogService tdManagerLogService;
	    
	    @RequestMapping(value="/category/list")
	    public String categoryList(Long cid, 
	                                Long mid, 
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
	        
	        if (null != __EVENTTARGET)
	        {
	            switch (__EVENTTARGET)
	            {
	            case "btnSave":
	                if (cid.equals(1L)) // 文章
	                {
	                    articleCategoryBtnSave(listId, listSortId);
	                    tdManagerLogService.addLog("edit", "用户修改文章分类", req);
	                }	                
	                break;
	                
	            case "btnDelete":
	                if (cid.equals(1L)) // 文章
	                {
	                    articleCategoryBtnDelete(listId, listChkId);
	                    tdManagerLogService.addLog("delete", "用户删除文章分类", req);
	                }
	                break;
	            }
	        }
	        
	        if (cid.equals(1L)) // 文章
	        {
	            map.addAttribute("category_list", tdArticleCategoryService.findByMenuId(mid));
	        }
	        
	        // 参数注回
	        map.addAttribute("cid", cid);
	        map.addAttribute("mid", mid);
	        map.addAttribute("__EVENTTARGET", __EVENTTARGET);
	        map.addAttribute("__EVENTARGUMENT", __EVENTARGUMENT);
	        map.addAttribute("__VIEWSTATE", __VIEWSTATE);
	        
	        return "/site_mag/category_list";
	    }
	    
	    @RequestMapping(value="/category/list", method=RequestMethod.POST)
	    public String categoryPostList(Long cid, 
	                            Long mid, 
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
	        
	        if (null != __EVENTTARGET)
	        {
	            switch (__EVENTTARGET)
	            {
	            case "btnSave":
	                if (cid.equals(1L)) // 文章
	                {
	                    articleCategoryBtnSave(listId, listSortId);
	                    tdManagerLogService.addLog("edit", "用户修改文章分类", req);
	                }
	                break;
	                
	            case "btnDelete":
	                if (cid.equals(1L)) // 文章
	                {
	                    articleCategoryBtnDelete(listId, listChkId);
	                    tdManagerLogService.addLog("delete", "用户删除文章分类", req);
	                }
	                break;
	            }
	        }
	        
	        if (cid.equals(1L)) // 文章
	        {
	            map.addAttribute("category_list", tdArticleCategoryService.findByMenuId(mid));
	        }
	        
	        // 参数注回
	        map.addAttribute("cid", cid);
	        map.addAttribute("mid", mid);
	        map.addAttribute("__EVENTTARGET", __EVENTTARGET);
	        map.addAttribute("__EVENTARGUMENT", __EVENTARGUMENT);
	        map.addAttribute("__VIEWSTATE", __VIEWSTATE);
	        
	        return "/site_mag/category_list";
	    }
	    
	    @RequestMapping(value="/content/list")
	    public String contentList(Long cid, 
	                                Long mid, 
	                                Integer page, 
	                                Integer size,
	                                Long categoryId,
	                                String property,
	                                String __EVENTTARGET,
	                                String __EVENTARGUMENT,
	                                String __VIEWSTATE,
	                                String keywords,
	                                Long[] listId,
	                                Integer[] listChkId,
	                                Long[] listSortId,
	                                ModelMap map,
	                                HttpServletRequest req){
	        String username = (String) req.getSession().getAttribute("manager");
	        if (null == username) {
	            return "redirect:/Verwalter/login";
	        }
	        
	        if (null == page || page < 0)
	        {
	            page = 0;
	        }
	        
	        if (null == size || size <= 0)
	        {
	            size = SiteMagConstant.pageSize;;
	        }
	        
	        if (null != __EVENTTARGET)
	        {
	            switch (__EVENTTARGET)
	            {
	            case "lbtnViewTxt":
	            case "lbtnViewImg":
	                __VIEWSTATE = __EVENTTARGET;
	                break;
	            
	            case "btnSave":
	                btnSave(cid, listId, listSortId, username);
	                tdManagerLogService.addLog("edit", "用户修改文章", req);
	                break;
	                
	            case "btnDelete":
	                btnDelete(cid, listId, listChkId);
	                tdManagerLogService.addLog("delete", "用户删除文章", req);
	                
	                break;
	            }
	            if (__EVENTTARGET.equalsIgnoreCase("btnPage"))
	            {
	                if (null != __EVENTARGUMENT)
	                {
	                    page = Integer.parseInt(__EVENTARGUMENT);
	                } 
	            }
	        }
	        
	        if (null != __EVENTTARGET && __EVENTTARGET.equalsIgnoreCase("lbtnViewTxt")
	                || null != __EVENTTARGET && __EVENTTARGET.equalsIgnoreCase("lbtnViewImg"))
	        {
	            __VIEWSTATE = __EVENTTARGET;
	        }
	        
	        if (cid.equals(1L) || cid.equals(3L)) // 文章或商品参数
	        {
	            map.addAttribute("category_list", tdArticleCategoryService.findByMenuId(mid));
	            
	            if (null == categoryId)
	            {
	                map.addAttribute("content_page", tdArticleService.findByMenuId(mid, page, size));
	            }
	            else
	            {
	                map.addAttribute("content_page", tdArticleService.findByMenuIdAndCategoryId(mid, categoryId, page, size));
	            }
	        }

	        // 参数注回
	        map.addAttribute("cid", cid);
	        map.addAttribute("mid", mid);
	        map.addAttribute("page", page);
	        map.addAttribute("size", size);
	        map.addAttribute("__EVENTTARGET", __EVENTTARGET);
	        map.addAttribute("__EVENTARGUMENT", __EVENTARGUMENT);
	        map.addAttribute("__VIEWSTATE", __VIEWSTATE);
	        map.addAttribute("categoryId", categoryId);
	        map.addAttribute("property", property);
	        
	        // 文字列表模式
	        if (null != __VIEWSTATE && __VIEWSTATE.equals("lbtnViewTxt"))
	        {
	            if (null != cid && cid.equals(2L)) // 商品
	            {
	                return "/site_mag/goods_txt_list";
	            }
	            else
	            {
	                return "/site_mag/content_txt_list";
	            }
	        }
	        
	        // 图片列表模式
	        if (null != cid && cid.equals(2L)) // 商品
	        {
	            return "/site_mag/goods_pic_list";
	        }
	        else
	        {
	            return "/site_mag/content_pic_list";
	        }
	    }
	    
	    @RequestMapping(value="/content/list", method=RequestMethod.POST)
	    public String contentListP(Long cid, 
	                              Long mid, 
	                              Integer page, 
	                              Integer size,
	                              Long categoryId,
	                              String property,
	                              String __EVENTTARGET,
	                              String __EVENTARGUMENT,
	                              String __VIEWSTATE,
	                              String keywords,
	                              Long[] listId,
	                              Integer[] listChkId,
	                              Long[] listSortId,
	                              ModelMap map,
	                              HttpServletRequest req){
	        String username = (String) req.getSession().getAttribute("manager");
	        if (null == username) {
	            return "redirect:/Verwalter/login";
	        }
	        
	        if (null == page || page < 0)
	        {
	            page = 0;
	        }
	        
	        if (null == size || size <= 0)
	        {
	            size = SiteMagConstant.pageSize;;
	        }
	        
	        if (null != __EVENTTARGET)
	        {
	            switch (__EVENTTARGET)
	            {
	            case "lbtnViewTxt":
	            case "lbtnViewImg":
	                __VIEWSTATE = __EVENTTARGET;
	                break;
	            
	            case "btnSave":
	                btnSave(cid, listId, listSortId, username);
	                tdManagerLogService.addLog("edit", "用户修改文章", req);
	                break;
	                
	            case "btnDelete":
	                btnDelete(cid, listId, listChkId);
	                tdManagerLogService.addLog("delete", "用户删除文章", req);
	                break;
	                
	            case "btnPage":
	                if (null != __EVENTARGUMENT)
	                {
	                    page = Integer.parseInt(__EVENTARGUMENT);
	                } 
	                break;
	            }
	        }
	        
	        if (null != __EVENTTARGET && __EVENTTARGET.equalsIgnoreCase("lbtnViewTxt")
	                || null != __EVENTTARGET && __EVENTTARGET.equalsIgnoreCase("lbtnViewImg"))
	        {
	            __VIEWSTATE = __EVENTTARGET;
	        }
	        
	        if (cid.equals(1L) || cid.equals(3L)) // 文章或商品参数
	        {
	            map.addAttribute("category_list", tdArticleCategoryService.findByMenuId(mid));
	            
	            if (null == categoryId)
	            {
	            	if (null == keywords || keywords.equals(""))
	            	{
	            		map.addAttribute("content_page", tdArticleService.findByMenuId(mid, page, size));
	            	}
	            	else{
	            		map.addAttribute("content_page", tdArticleService.findByMenuIdAndSearch(mid , keywords , page , size));
	            	}
	            }
	            else
	            {
	            	if(null == keywords || keywords.equals(""))
	            	{
	            		map.addAttribute("content_page", tdArticleService.findByMenuIdAndCategoryId(mid, categoryId, page, size));
	            	}
	            	else{
	            		map.addAttribute("content_page", tdArticleService.findByMenuIdAndCategoryIdAndSearch(mid , categoryId , keywords , page ,size));
	            	}
	            }
	        }

	        // 参数注回
	        map.addAttribute("cid", cid);
	        map.addAttribute("mid", mid);
	        map.addAttribute("page", page);
	        map.addAttribute("size", size);
	        map.addAttribute("keywords",keywords);
	        map.addAttribute("__EVENTTARGET", __EVENTTARGET);
	        map.addAttribute("__EVENTARGUMENT", __EVENTARGUMENT);
	        map.addAttribute("__VIEWSTATE", __VIEWSTATE);
	        map.addAttribute("categoryId", categoryId);
	        map.addAttribute("property", property);
	        
	        // 文字列表模式
	        if (null != __VIEWSTATE && __VIEWSTATE.equals("lbtnViewTxt"))
	        {
	            if (null != cid && cid.equals(2L)) // 商品
	            {
	                return "/site_mag/goods_txt_list";
	            }
	            else
	            {
	                return "/site_mag/content_txt_list";
	            }
	        }
	        
	        // 图片列表模式
	        if (null != cid && cid.equals(2L)) // 商品
	        {
	            return "/site_mag/goods_pic_list";
	        }
	        else
	        {
	            return "/site_mag/content_pic_list";
	        }
	    }
	    
	    @RequestMapping(value="/category/edit")
	    public String categoryEditDialog(Long cid, Long mid, Long id, Long sub, 
	                                    String __EVENTTARGET,
	                                    String __EVENTARGUMENT,
	                                    String __VIEWSTATE,
	                                    ModelMap map,
	                                    HttpServletRequest req){
	        String username = (String) req.getSession().getAttribute("manager");
	        if (null == username) {
	            return "redirect:/Verwalter/login";
	        }

	        map.addAttribute("cid", cid);
	        map.addAttribute("mid", mid);
	        map.addAttribute("__EVENTTARGET", __EVENTTARGET);
	        map.addAttribute("__EVENTARGUMENT", __EVENTARGUMENT);
	        map.addAttribute("__VIEWSTATE", __VIEWSTATE);
	        
//	        if (null != cid && cid.equals(2L)) // 商品分类
//	        {
//	            map.addAttribute("category_list", tdProductCategoryService.findAll());
//	            
//	            // 参数类型表
//	            map.addAttribute("param_category_list", tdParameterCategoryService.findAll());
//	            
//	            if (null != sub) // 添加子类
//	            {
//	                if (null != id)
//	                {
//	                    map.addAttribute("fatherCat", tdProductCategoryService.findOne(id));
//	                }
//	            }
//	            else
//	            {
//	                if (null != id)
//	                {
//	                    map.addAttribute("cat", tdProductCategoryService.findOne(id));
//	                }
//	            }
//	            
//	            return "/site_mag/product_category_edit";
//	        }
//	        else
	        {
	            if (null != mid)
	            {
	                map.addAttribute("category_list", tdArticleCategoryService.findByMenuId(mid));
	            }
	            
	            if (null != id)
	            {
	                if (null != sub) // 添加子类
	                {
	                    map.addAttribute("fatherCat", tdArticleCategoryService.findOne(id));
	                }
	                else
	                {
	                    map.addAttribute("cat", tdArticleCategoryService.findOne(id));
	                }
	            }
	            
	            return "/site_mag/article_category_edit";
	        }
	    }
	    
	    @RequestMapping(value="/category/save", method = RequestMethod.POST)
	    public String save(TdArticleCategory cat, 
	                        String __EVENTTARGET,
	                        String __EVENTARGUMENT,
	                        String __VIEWSTATE,
	                        ModelMap map,
	                        HttpServletRequest req){
	        String username = (String) req.getSession().getAttribute("manager");
	        if (null == username)
	        {
	            return "redirect:/Verwalter/login";
	        }
	        
	        if (null == cat.getId())
	        {
	            tdManagerLogService.addLog("add", "用户修改文章分类", req);
	        }
	        else
	        {
	            tdManagerLogService.addLog("edit", "用户修改文章分类", req);
	        }
	        
	        
	        tdArticleCategoryService.save(cat);
	        
	        //zhangji 子类相关修改
	        Long id = cat.getId();
	        if (null != id)
	        {
//	        	Long i = tdArticleCategoryService.findOne(id).getLayerCount() - (tdArticleCategoryService.findOne(cat.getParentId()).getLayerCount()+1L); 
	        	List<TdArticleCategory> acList =  tdArticleCategoryService.findByParentId(cat.getId());
	        	for (TdArticleCategory item : acList)
	        	{
//	        		item.setParentId(item.getLayerCount()+i);
//	        		item.setLayerCount(item.getLayerCount()+i);
	        		tdArticleCategoryService.save(item);
	        	}
	        }
	        
	        return "redirect:/Verwalter/category/list?cid=" + cat.getChannelId() 
	                + "&mid=" + cat.getMenuId()
	                + "&__EVENTTARGET=" + __EVENTTARGET
	                + "&__EVENTARGUMENT=" + __EVENTARGUMENT
	                + "&__VIEWSTATE=" + __VIEWSTATE;
	    }
	    
	    @RequestMapping(value="/article/edit")
	    public String articleEditDialog(Long cid, Long mid, Long pid, Long id, 
	            String __EVENTTARGET,
	            String __EVENTARGUMENT,
	            String __VIEWSTATE,
	            ModelMap map,
	            HttpServletRequest req){
	        String username = (String) req.getSession().getAttribute("manager");
	        if (null == username) {
	            return "redirect:/Verwalter/login";
	        }
	        
	        if (null != id)
	        {
	            map.addAttribute("article", tdArticleService.findOne(id));
	        }
	        
	        map.addAttribute("cid", cid);
	        map.addAttribute("mid", mid);
	        map.addAttribute("__EVENTTARGET", __EVENTTARGET);
	        map.addAttribute("__EVENTARGUMENT", __EVENTARGUMENT);
	        map.addAttribute("__VIEWSTATE", __VIEWSTATE);
	        
	        if (null != mid)
	        {
	            map.addAttribute("category_list", tdArticleCategoryService.findByMenuId(mid));
	        }
	        
//	        if (null != cid)
//	        {
//	            if (cid.equals(5L)) // 产品
//	            {
//	                map.addAttribute("category_list", tdProductCategoryService.findAll());
//	                return "/site_mag/product_edit";
//	            }
//	            else if (cid.equals(2L)) // 商品
//	            {
//	                map.addAttribute("category_list", tdProductCategoryService.findAll());
//	                
//	                if (null != id)
//	                {
//	                    TdGoods tdGoods = tdGoodsService.findOne(id);
//	                    
//	                    if (null != tdGoods)
//	                    {
//	                        // 参数列表
//	                        TdProductCategory tpc = tdProductCategoryService.findOne(tdGoods.getCategoryId());
//	                        
//	                        if (null != tpc && null != tpc.getParamCategoryId())
//	                        {
//	                            map.addAttribute("param_list", tdParameterService.findByCategoryTreeContaining(tpc.getParamCategoryId()));
//	                        }
//	                        
//	                        // 查找产品列表
//	                        map.addAttribute("product_list", tdProductService.findByProductCategoryTreeContaining(tdGoods.getCategoryId()));
//	                    
//	                        // 查找品牌
//	                        map.addAttribute("brand_list", tdBrandService.findByProductCategoryTreeContaining(tdGoods.getCategoryId()));
//	                        
//	                        map.addAttribute("warehouse_list", tdWarehouseService.findAll());
//	                        
//	                        if (null != tdGoods.getProductId())
//	                        {
//	                            map.addAttribute("product", tdProductService.findOne(tdGoods.getProductId()));
//	                        }
//	                        
//	                        map.addAttribute("goods", tdGoods);
//	                    }
//	                }
//	                
//	                return "/site_mag/goods_edit";
//	            }
//	        }
	        
	        return "/site_mag/article_content_edit";
	    }
	    
	    @RequestMapping(value="/article/save", method = RequestMethod.POST)
	    public String save(TdArticle article, 
	            String __EVENTTARGET,
	            String __EVENTARGUMENT,
	            String __VIEWSTATE,
	            ModelMap map,
	            HttpServletRequest req){
	        String username = (String) req.getSession().getAttribute("manager");
	        if (null == username) {
	            return "redirect:/Verwalter/login";
	        }
	        
	        String logType = null;
	        if (null == article.getId())
	        {
	            logType = "add";
	        }
	        else
	        {
	            logType = "edit";
	        }
	        tdArticleService.save(article);
	        
	        tdManagerLogService.addLog(logType, "用户修改文章", req);
	        
	        return "redirect:/Verwalter/content/list?cid=" + article.getChannelId() 
	                + "&mid=" + article.getMenuId()
	                + "&__EVENTTARGET=" + __EVENTTARGET
	                + "&__EVENTARGUMENT=" + __EVENTARGUMENT
	                + "&__VIEWSTATE=" + __VIEWSTATE;
	    }
	    
	    /**
	     * 保存文章排序ID
	     * 
	     * @param ids
	     * @param chkIds
	     * @param sortIds
	     */
//	    private void articleBtnSave(Long[] ids, Integer[] chkIds, Long[] sortIds)
//	    {
//	        if (null == ids || null == chkIds || null == sortIds
//	                || ids.length < 1 || chkIds.length < 1 || sortIds.length < 1)
//	        {
//	            return;
//	        }
//	        
//	        for (int chkId : chkIds)
//	        {
//	            if (chkId >=0 && ids.length > chkId && sortIds.length > chkId)
//	            {
//	                Long id = ids[chkId];
//	                
//	                TdArticle article = tdArticleService.findOne(id);
//	                
//	                article.setSortId(new Double(sortIds[chkId]));
//	                
//	                tdArticleService.save(article);
//	            }
//	        }
//	    }
	    
	    /**
	     * 保存类型排序ID
	     * 
	     * @param ids
	     * @param chkIds
	     * @param sortIds
	     */
	    private void articleCategoryBtnSave(Long[] ids, Double[] sortIds)
	    {
	        if (null == ids || null == sortIds
	                || ids.length < 1 || sortIds.length < 1)
	        {
	            return;
	        }
	        
	        for (int i = 0; i < ids.length; i++)
	        {
	            Long id = ids[i];
	            TdArticleCategory category = tdArticleCategoryService.findOne(id);
	                
	            if (sortIds.length > i)
	            {
	                category.setSortId(new Double(sortIds[i]));
	                tdArticleCategoryService.save(category);
	            }
	        }
	    }
	    
	    
	    /**
	     * 删除类型
	     * 
	     * @param ids
	     * @param chkIds
	     * @param sortIds
	     */
	    private void articleCategoryBtnDelete(Long[] ids, Integer[] chkIds)
	    {
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
	                
	                tdArticleCategoryService.delete(id);
	                
	                //删除该类别相关文章  zhangji
	                List<TdArticle> articleList = tdArticleService.findByCategoryId(id);
	                for (TdArticle item : articleList)
	                {
	                	tdArticleService.delete(item.getId());
	                }
	                
	            }
	        }
	    }
	    	    
	    /**
	     * 保存文章排序ID
	     * 
	     * @param ids
	     * @param chkIds
	     * @param sortIds
	     */
	    private void btnSave(Long cid, Long[] ids, Long[] sortIds, String username)
	    {
	        if (null == ids || null == sortIds
	                || ids.length < 1 || sortIds.length < 1)
	        {
	            return;
	        }
	        
	        for (int i = 0; i < ids.length; i++)
	        {
	            Long id = ids[i];
	            if (null != cid && cid.equals(2L))
	            {
//	                TdGoods goods = tdGoodsService.findOne(id);
//	                
//	                if (sortIds.length > i)
//	                {
//	                    goods.setSortId(sortIds[i]);
//	                    tdGoodsService.save(goods, username);
//	                }
	            }
	            else
	            {
	                TdArticle article = tdArticleService.findOne(id);
	                    
	                if (sortIds.length > i)
	                {
	                    article.setSortId(new Double(sortIds[i]));
	                    tdArticleService.save(article);
	                }
	            }
	        }
	    }
	    
	    /**
	     * 删除文章
	     * 
	     * @param ids
	     * @param chkIds
	     * @param sortIds
	     */
	    private void btnDelete(Long cid, Long[] ids, Integer[] chkIds)
	    {
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
	                
	                if (null != cid && cid.equals(2L))
	                {
//	                    tdGoodsService.delete(id);
	                }
	                else
	                {
	                    tdArticleService.delete(id);
	                }
	            }
	        }
	    }
}
