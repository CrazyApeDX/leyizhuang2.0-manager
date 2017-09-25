package com.ynyes.lyz.interfaces.service;


import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ynyes.lyz.interfaces.entity.TdEbsInfLog;
import com.ynyes.lyz.interfaces.repository.TdEbsInfLogRepo;

@Service
@Transactional
public class TdEbsInfLogService
{
	@Autowired
	private TdEbsInfLogRepo repository;
	
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
    public void delete(TdEbsInfLog e)
    {
        if (null != e)
        {
            repository.delete(e);
        }
    }
    
    public void delete(List<TdEbsInfLog> entities)
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
    public TdEbsInfLog findOne(Long id)
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
    public List<TdEbsInfLog> findAll(Iterable<Long> ids)
    {
        return (List<TdEbsInfLog>) repository.findAll(ids);
    }
    
    public List<TdEbsInfLog> findAll()
    {
        return (List<TdEbsInfLog>) repository.findAll();
    }
    
    /**
     * 保存
     * 
     * @param e
     * @return
     */
    public TdEbsInfLog save(TdEbsInfLog e)
    {
        if (null == e.getInitDate())
        {
            e.setInitDate(new Date());
        }
//        if (null == e.getSendFlag())
//        {
//			e.setSendFlag("N");
//		}
        e.setModifyDate(new Date());

        return repository.save(e);
    }
    
    public List<TdEbsInfLog> save(List<TdEbsInfLog> entities)
    {
        return (List<TdEbsInfLog>) repository.save(entities);
    }
}
