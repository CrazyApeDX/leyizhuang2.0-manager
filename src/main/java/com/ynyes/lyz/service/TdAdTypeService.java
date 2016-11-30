package com.ynyes.lyz.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ynyes.lyz.entity.TdAdType;
import com.ynyes.lyz.repository.TdAdTypeRepo;


/**
 * TdAdType 服务类
 * 
 * @author Sharon
 *
 */

@Service
@Transactional
public class TdAdTypeService {
    
    @Autowired
    TdAdTypeRepo repository;
    
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
    public void delete(TdAdType e)
    {
        if (null != e)
        {
            repository.delete(e);
        }
    }
    
    public void delete(List<TdAdType> entities)
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
    public TdAdType findOne(Long id)
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
    public List<TdAdType> findAll(Iterable<Long> ids)
    {
        return (List<TdAdType>) repository.findAll(ids);
    }
    
    public List<TdAdType> findAll()
    {
        return (List<TdAdType>) repository.findAll();
    }
    
    public Page<TdAdType> findAllOrderBySortIdAsc(int page, int size)
    {
        PageRequest pageRequest = new PageRequest(page, size, new Sort(Direction.ASC, "sortId"));
        
        return repository.findAll(pageRequest);
    }
    
    public List<TdAdType> findAllOrderBySortIdAsc()
    {
        return (List<TdAdType>) repository.findAll(new Sort(Direction.ASC, "sortId"));
    }
    
    public TdAdType findByTitle(String title)
    {
        return repository.findByTitle(title);
    }
    
    /**
     * 保存
     * 
     * @param e
     * @return
     */
    public TdAdType save(TdAdType e)
    {
        if (null == e.getCreateTime())
        {
            e.setCreateTime(new Date());
        }
        
        return repository.save(e);
    }
    
    public List<TdAdType> save(List<TdAdType> entities)
    {
        
        return (List<TdAdType>) repository.save(entities);
    }
}
