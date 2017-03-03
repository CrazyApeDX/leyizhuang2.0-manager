package com.ynyes.lyz.interfaces.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ynyes.lyz.interfaces.entity.TdOrderReceiveInf;
import com.ynyes.lyz.interfaces.repository.TdOrderReceiveInfRepo;


/**
 * TdOrderInf 服务类
 * 
 * @author Sharon
 *
 */

@Service
@Transactional
public class TdOrderReceiveInfService {
    
    @Autowired
    private TdOrderReceiveInfRepo repository;
    
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
    public void delete(TdOrderReceiveInf e)
    {
        if (null != e)
        {
            repository.delete(e);
        }
    }
    
    public void delete(List<TdOrderReceiveInf> entities)
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
    public TdOrderReceiveInf findOne(Long id)
    {
        if (null == id)
        {
            return null;
        }
        
        return repository.findOne(id);
    }
    
    public List<TdOrderReceiveInf> findByOrderNumber(String orderNumber)
    {
    	if (orderNumber == null)
    	{
			return null;
		}
    	return repository.findByOrderNumber(orderNumber);
    }
    
    public List<TdOrderReceiveInf> findBySendFlageOrSendFlagIsNull(Integer sendFlag)
    {
    	
    	return repository.findBySendFlagOrSendFlagIsNull(sendFlag);
    }
    
    /**
     * 查找
     * 
     * @param ids
     * @return
     */
    public List<TdOrderReceiveInf> findAll(Iterable<Long> ids)
    {
        return (List<TdOrderReceiveInf>) repository.findAll(ids);
    }
    
    public List<TdOrderReceiveInf> findAll()
    {
        return (List<TdOrderReceiveInf>) repository.findAll();
    }
    
    
    /**
     * 保存
     * 
     * @param e
     * @return
     */
    public TdOrderReceiveInf save(TdOrderReceiveInf e)
    {
    	if (null == e.getInitDate())
        {
            e.setInitDate(new Date());
        }
    	e.setModifyDate(new Date());
        return repository.save(e);
    }
    
    public List<TdOrderReceiveInf> save(List<TdOrderReceiveInf> entities)
    {
        return (List<TdOrderReceiveInf>) repository.save(entities);
    }
    
}
