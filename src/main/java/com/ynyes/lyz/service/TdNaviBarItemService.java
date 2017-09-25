package com.ynyes.lyz.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ynyes.lyz.entity.TdNaviBarItem;
import com.ynyes.lyz.repository.TdNaviBarItemRepo;


/**
 * TdNaviBarItem 服务类
 * 
 * @author Sharon
 *
 */

@Service
@Transactional
public class TdNaviBarItemService {
    
    @Autowired
    TdNaviBarItemRepo repository;
    
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
     * @param e 菜单项
     */
    public void delete(TdNaviBarItem e)
    {
        if (null != e)
        {
            repository.delete(e);
        }
    }
    
    public void delete(List<TdNaviBarItem> entities)
    {
        if (null != entities)
        {
            repository.delete(entities);
        }
    }
    
    /**
     * 查找
     * 
     * @param id ID
     * @return
     */
    public TdNaviBarItem findOne(Long id)
    {
        if (null == id)
        {
            return null;
        }
        
        return repository.findOne(id);
    }
    
    /**
     * 查找
     * 
     * @param ids
     * @return
     */
    public List<TdNaviBarItem> findAll(Iterable<Long> ids)
    {
        return (List<TdNaviBarItem>) repository.findAll(ids);
    }
    
    public List<TdNaviBarItem> findAll()
    {
        return (List<TdNaviBarItem>) repository.findAll();
    }
    
    public Page<TdNaviBarItem> findAllOrderBySortIdAsc(int page, int size)
    {
        PageRequest pageRequest = new PageRequest(page, size, new Sort(Direction.ASC, "sortId"));
        
        return repository.findAll(pageRequest);
    }
    
    public List<TdNaviBarItem> findAllOrderBySortIdAsc()
    {
        return (List<TdNaviBarItem>) repository.findAll(new Sort(Direction.ASC, "sortId"));
    }
    
    public List<TdNaviBarItem> findByIsEnableTrueOrderBySortIdAsc()
    {
        return repository.findByIsEnableTrueOrderBySortIdAsc();
    }
    
    /**
     * 保存
     * 
     * @param e
     * @return
     */
    public TdNaviBarItem save(TdNaviBarItem e)
    {
        return repository.save(e);
    }
    
    public List<TdNaviBarItem> save(List<TdNaviBarItem> entities)
    {
        
        return (List<TdNaviBarItem>) repository.save(entities);
    }
}
