package com.ynyes.lyz.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ynyes.lyz.entity.TdNavigationMenu;
import com.ynyes.lyz.repository.TdNavigationMenuRepo;

/**
 * TDNavigationMenu 服务类
 * 
 * @author Sharon
 *
 */

@Service
@Transactional
public class TdNavigationMenuService {
    @Autowired
    TdNavigationMenuRepo repository;
    
    /**
     * 查找菜单项
     * 
     * @param parentId 父菜单ID，为0时代报顶层菜单
     * @return
     */
    public List<TdNavigationMenu> findByParentIdAndSort(Long parentId)
    {
        if (null == parentId)
        {
            return null;
        }
        
        return repository.findByParentIdAndIsEnableTrueOrderBySortIdAsc(parentId);
    }
    
    public List<TdNavigationMenu> findAllIsEnableTrue(){
    	return  repository.findByIsEnableTrueOrderBySortIdAsc();
    }
    
    /**
     * 删除
     * 
     * @param id 菜单项ID
     */
    public void delete(Long id)
    {
        if (null != id)
        {
            repository.delete(id);
        }
    }
    
    /**
     * 删除
     * 
     * @param menu 菜单项
     */
    public void delete(TdNavigationMenu menu)
    {
        if (null != menu)
        {
            repository.delete(menu);
        }
    }
    
    /**
     * 查找
     * 
     * @param id 菜单项ID
     * @return
     */
    public TdNavigationMenu findOne(Long id)
    {
        if (null == id)
        {
            return null;
        }
        
        return repository.findOne(id);
    }
}
