package com.ynyes.lyz.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ynyes.lyz.entity.TdStorage;
import com.ynyes.lyz.repository.TdStorageRepo;


/**
 * TdStorage 服务类
 * 
 * @author Sharon
 *
 */

@Service
@Transactional
public class TdStorageService {
    
    @Autowired
    TdStorageRepo repository;
    
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
    public void delete(TdStorage e)
    {
        if (null != e)
        {
            repository.delete(e);
        }
    }
    
    public void delete(List<TdStorage> entities)
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
    public TdStorage findOne(Long id)
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
    public List<TdStorage> findAll(Iterable<Long> ids)
    {
        return (List<TdStorage>) repository.findAll(ids);
    }
    
    public List<TdStorage> findAll()
    {
        return (List<TdStorage>) repository.findAll();
    }
    
    public Page<TdStorage> findAllOrderBySortIdAsc(int page, int size)
    {
        PageRequest pageRequest = new PageRequest(page, size, new Sort(Direction.ASC, "sortId"));
        
        return repository.findAll(pageRequest);
    }
    
    public List<TdStorage> findAllOrderBySortIdAsc()
    {
        return (List<TdStorage>) repository.findAll(new Sort(Direction.ASC, "sortId"));
    }
    
    public TdStorage findByTitle(String title)
    {
        return repository.findByTitle(title);
    }
    
    /**
     * 保存
     * 
     * @param e
     * @return
     */
    public TdStorage save(TdStorage e)
    {
        return repository.save(e);
    }
    
    public List<TdStorage> save(List<TdStorage> entities)
    {
        
        return (List<TdStorage>) repository.save(entities);
    }
}
