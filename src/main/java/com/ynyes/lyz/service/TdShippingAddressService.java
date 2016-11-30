package com.ynyes.lyz.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ynyes.lyz.entity.TdShippingAddress;
import com.ynyes.lyz.repository.TdShippingAddressRepo;

/**
 * TdShippingAddress 服务类
 * 
 * @author Sharon
 *
 */

@Service
@Transactional
public class TdShippingAddressService {
    
    @Autowired
    TdShippingAddressRepo repository;
    

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
    public void delete(TdShippingAddress e)
    {
        if (null != e)
        {
            repository.delete(e);
        }
    }
    
    public void delete(List<TdShippingAddress> entities)
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
    public TdShippingAddress findOne(Long id)
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
    public List<TdShippingAddress> findAll(Iterable<Long> ids)
    {
        return (List<TdShippingAddress>) repository.findAll(ids);
    }
    
    public List<TdShippingAddress> findAll()
    {
        return (List<TdShippingAddress>) repository.findAll();
    }
    
    /**
     * 保存
     * 
     * @param e
     * @return
     */
    public TdShippingAddress save(TdShippingAddress e)
    {
        
        return repository.save(e);
    }
    
    public List<TdShippingAddress> save(List<TdShippingAddress> entities)
    {
        
        return (List<TdShippingAddress>) repository.save(entities);
    }
}
