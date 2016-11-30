package com.ynyes.lyz.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ynyes.lyz.entity.TdManagerDiySitePermission;
import com.ynyes.lyz.repository.TdManagerDiySitePermissionRepo;

/**
 * TdManagerDiySitePermission 服务类
 * 
 * @author Sharon
 *
 */

@Service
@Transactional
public class TdDiySitePermissionService {
    
    @Autowired
    TdManagerDiySitePermissionRepo repository;
    
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
    public void delete(TdManagerDiySitePermission e)
    {
        if (null != e)
        {
            repository.delete(e);
        }
    }
    
    public void delete(List<TdManagerDiySitePermission> entities)
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
    public TdManagerDiySitePermission findOne(Long id)
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
    public List<TdManagerDiySitePermission> findAll(Iterable<Long> ids)
    {
        return (List<TdManagerDiySitePermission>) repository.findAll(ids);
    }
    
    public List<TdManagerDiySitePermission> findAll()
    {
        return (List<TdManagerDiySitePermission>) repository.findAll();
    }
    
    public Page<TdManagerDiySitePermission> findAll(int page, int size)
    {
        PageRequest pageRequest = new PageRequest(page, size, new Sort(Direction.DESC, "createTime"));
        
        return repository.findAll(pageRequest);
    }
    
    public List<TdManagerDiySitePermission> findAllOrderBySortIdAsc()
    {
        return (List<TdManagerDiySitePermission>) repository.findAll(new Sort(Direction.ASC, "sortId"));
    }
    
    /**
     * 保存
     * 
     * @param e
     * @return
     */
    public TdManagerDiySitePermission save(TdManagerDiySitePermission e)
    {
        return repository.save(e);
    }
    
    public List<TdManagerDiySitePermission> save(List<TdManagerDiySitePermission> entities)
    {
        
        return (List<TdManagerDiySitePermission>) repository.save(entities);
    }
}
