package com.ynyes.lyz.controller.management;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ynyes.lyz.entity.TdGoods;
import com.ynyes.lyz.entity.TdProductCategory;
import com.ynyes.lyz.service.TdGoodsService;
import com.ynyes.lyz.service.TdManagerLogService;
import com.ynyes.lyz.service.TdProductCategoryService;

/**
 * 后台产品控制器
 * 
 * @author Sharon
 */

@Controller
@RequestMapping(value = "/Verwalter/product/category")
public class TdManagerProductCategoryController {

    @Autowired
    TdProductCategoryService tdProductCategoryService;

    @Autowired
    TdManagerLogService tdManagerLogService;
    
    @Autowired
    TdGoodsService tdGoodsService;    //add by zhangji
    
//    @Autowired
//    TdParameterCategoryService tdParameterCategoryService;

    @RequestMapping(value = "/list")
    public String categoryList(String __EVENTTARGET, String __EVENTARGUMENT,
            String __VIEWSTATE, Long[] listId, Integer[] listChkId,
            Double[] listSortId, ModelMap map, HttpServletRequest req) {
        String username = (String) req.getSession().getAttribute("manager");
        if (null == username) {
            return "redirect:/Verwalter/login";
        }

        if (null != __EVENTTARGET) {
            switch (__EVENTTARGET) {
            case "btnSave":
                productCategoryBtnSave(listId, listSortId);

                break;

            case "btnDelete":
                productCategoryBtnDelete(listId, listChkId);

                break;
            }
        }

        map.addAttribute("category_list", tdProductCategoryService.findAll());

        // 参数注回
        map.addAttribute("__EVENTTARGET", __EVENTTARGET);
        map.addAttribute("__EVENTARGUMENT", __EVENTARGUMENT);
        map.addAttribute("__VIEWSTATE", __VIEWSTATE);

        return "/site_mag/product_category_list";
    }

    @RequestMapping(value = "/edit")
    public String categoryEditDialog(Long id, Long sub, String __EVENTTARGET,
            String __EVENTARGUMENT, String __VIEWSTATE, ModelMap map,
            HttpServletRequest req) {
        String username = (String) req.getSession().getAttribute("manager");
        if (null == username) {
            return "redirect:/Verwalter/login";
        }

        map.addAttribute("__EVENTTARGET", __EVENTTARGET);
        map.addAttribute("__EVENTARGUMENT", __EVENTARGUMENT);
        map.addAttribute("__VIEWSTATE", __VIEWSTATE);

        map.addAttribute("category_list", tdProductCategoryService.findAll());

        // 参数类型表
//        map.addAttribute("param_category_list",
//                tdParameterCategoryService.findAll());

        if (null != sub) // 添加子类
        {
            if (null != id) {
                map.addAttribute("fatherCat",
                        tdProductCategoryService.findOne(id));
            }
        } else {
            if (null != id) {
                map.addAttribute("cat", tdProductCategoryService.findOne(id));
            }
        }

        return "/site_mag/product_category_edit";

    }

    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public String save(TdProductCategory cat, String __EVENTTARGET,
            String __EVENTARGUMENT, String __VIEWSTATE, ModelMap map,
            HttpServletRequest req) {
        String username = (String) req.getSession().getAttribute("manager");
        if (null == username) {
            return "redirect:/Verwalter/login";
        }

        if (null == cat.getId()) {
            tdManagerLogService.addLog("add", "用户修改产品分类", req);
        } else {
            tdManagerLogService.addLog("edit", "用户修改产品分类", req);
            
            //更新商品类别的信息
            List<TdGoods> goodsList = tdGoodsService.findByCategoryIdOrderBySortIdAsc(cat.getId());
            for(TdGoods item : goodsList)
            {
            	item.setCategoryTitle(cat.getTitle());
            	tdGoodsService.save(item, username);  //应该是记录用的用户名吧？ zhangji
            }
            
        }
        
        tdProductCategoryService.save(cat);
        
        //下一级类别刷新一下层数 zhangji
        List<TdProductCategory> pcList = tdProductCategoryService.findByParentIdOrderBySortIdAsc(cat.getId());
        for(TdProductCategory item : pcList)
        {
        	tdProductCategoryService.save(item);
        }

        return "redirect:/Verwalter/product/category/list";
    }

    @RequestMapping(value = "/check", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, String> validateForm(String param, Long id) {
        Map<String, String> res = new HashMap<String, String>();

        res.put("status", "n");

        if (null == param || param.isEmpty()) {
            res.put("info", "该字段不能为空");
            return res;
        }

        if (null == id) // 新增分类，查找所有
        {
            if (null != tdProductCategoryService.findByTitle(param)) {
                res.put("info", "已存在同名分类");
                return res;
            }
        } else // 修改，查找除当前ID的所有
        {
            if (null != tdProductCategoryService.findByTitleAndIdNot(param, id)) {
                res.put("info", "已存在同名分类");
                return res;
            }
        }

        res.put("status", "y");

        return res;
    }

    private void productCategoryBtnSave(Long[] ids, Double[] sortIds) {
        if (null == ids || null == sortIds || ids.length < 1
                || sortIds.length < 1) {
            return;
        }

        for (int i = 0; i < ids.length; i++) {
            Long id = ids[i];
            TdProductCategory category = tdProductCategoryService.findOne(id);

            if (sortIds.length > i) {
                category.setSortId(new Double(sortIds[i]));
                tdProductCategoryService.save(category);
            }
        }
    }

    private void productCategoryBtnDelete(Long[] ids, Integer[] chkIds) {
        if (null == ids || null == chkIds || ids.length < 1
                || chkIds.length < 1) {
            return;
        }

        for (int chkId : chkIds) {
            if (chkId >= 0 && ids.length > chkId) {
                Long id = ids[chkId];

                tdProductCategoryService.delete(id);
            }
        }
    }
}
