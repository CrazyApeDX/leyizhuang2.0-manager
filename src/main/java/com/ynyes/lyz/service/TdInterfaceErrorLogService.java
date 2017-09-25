package com.ynyes.lyz.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ynyes.lyz.entity.TdInterfaceErrorLog;
import com.ynyes.lyz.repository.TdInterfaceErrorLogRepo;


/**
 * TdAd 服务类
 * 
 * @author Sharon
 *
 */

@Service
@Transactional
public class TdInterfaceErrorLogService {
    
    @Autowired
    TdInterfaceErrorLogRepo repository;
    
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
    public void delete(TdInterfaceErrorLog e)
    {
        if (null != e)
        {
            repository.delete(e);
        }
    }
    
    public void delete(List<TdInterfaceErrorLog> entities)
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
    public TdInterfaceErrorLog findOne(Long id)
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
    public List<TdInterfaceErrorLog> findAll(Iterable<Long> ids)
    {
        return (List<TdInterfaceErrorLog>) repository.findAll(ids);
    }
    
    public List<TdInterfaceErrorLog> findAll()
    {
        return (List<TdInterfaceErrorLog>) repository.findAll();
    }
    
    
    
    /**
     * 保存
     * 
     * @param e
     * @return
     */
    public TdInterfaceErrorLog save(TdInterfaceErrorLog e)
    {
        if (null == e.getTime())
        {
            e.setTime(new Date());
        }
        return repository.save(e);
    }
    
    public List<TdInterfaceErrorLog> save(List<TdInterfaceErrorLog> entities)
    {
        
        return (List<TdInterfaceErrorLog>) repository.save(entities);
    }
}
