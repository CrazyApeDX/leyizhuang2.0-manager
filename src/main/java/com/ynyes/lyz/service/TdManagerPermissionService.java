package com.ynyes.lyz.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ynyes.lyz.entity.TdManagerPermission;
import com.ynyes.lyz.repository.TdManagerPermissionRepo;

/**
 * TdManagerPermission 服务类
 * 
 * @author Sharon
 *
 */

@Service
@Transactional
public class TdManagerPermissionService {
    
    @Autowired
    TdManagerPermissionRepo repository;
    
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
    public void delete(TdManagerPermission e)
    {
        if (null != e)
        {
            repository.delete(e);
        }
    }
    
    public void delete(List<TdManagerPermission> entities)
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
    public TdManagerPermission findOne(Long id)
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
    public List<TdManagerPermission> findAll(Iterable<Long> ids)
    {
        return (List<TdManagerPermission>) repository.findAll(ids);
    }
    
    public List<TdManagerPermission> findAll()
    {
        return (List<TdManagerPermission>) repository.findAll();
    }
    
    public Page<TdManagerPermission> findAll(int page, int size)
    {
        PageRequest pageRequest = new PageRequest(page, size, new Sort(Direction.DESC, "createTime"));
        
        return repository.findAll(pageRequest);
    }
    
    public List<TdManagerPermission> findAllOrderBySortIdAsc()
    {
        return (List<TdManagerPermission>) repository.findAll(new Sort(Direction.ASC, "sortId"));
    }
    
    /**
     * 保存
     * 
     * @param e
     * @return
     */
    public TdManagerPermission save(TdManagerPermission e)
    {
        return repository.save(e);
    }
    
    public List<TdManagerPermission> save(List<TdManagerPermission> entities)
    {
        
        return (List<TdManagerPermission>) repository.save(entities);
    }
}
