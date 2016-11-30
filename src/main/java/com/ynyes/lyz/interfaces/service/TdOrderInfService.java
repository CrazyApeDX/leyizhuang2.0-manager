package com.ynyes.lyz.interfaces.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ynyes.lyz.interfaces.entity.TdOrderInf;
import com.ynyes.lyz.interfaces.repository.TdOrderInfRepo;


/**
 * TdOrderInf 服务类
 * 
 * @author Sharon
 *
 */

@Service
@Transactional
public class TdOrderInfService {
    
    @Autowired
    private TdOrderInfRepo repository;
    
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
    public void delete(TdOrderInf e)
    {
        if (null != e)
        {
            repository.delete(e);
        }
    }
    
    public void delete(List<TdOrderInf> entities)
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
    public TdOrderInf findOne(Long id)
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
    public List<TdOrderInf> findAll(Iterable<Long> ids)
    {
        return (List<TdOrderInf>) repository.findAll(ids);
    }
    
    public List<TdOrderInf> findAll()
    {
        return (List<TdOrderInf>) repository.findAll();
    }
    public TdOrderInf findByOrderNumber(String orderNumber)
    {
    	return repository.findByOrderNumber(orderNumber);
    }
    
    /**
     * 保存
     * 
     * @param e
     * @return
     */
    public TdOrderInf save(TdOrderInf e)
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
    
    public List<TdOrderInf> save(List<TdOrderInf> entities)
    {
        return (List<TdOrderInf>) repository.save(entities);
    }
    
    public List<TdOrderInf> findBySendFlag(Integer sendFlag) {
    	if (null == sendFlag) {
    		return null;
    	}
    	return repository.findBySendFlag(sendFlag);
    }
    
    public List<TdOrderInf> findBySendFlagAndSobId(Integer sendFlag, Long sobId){
    	if (null == sendFlag || null == sobId) {
    		return null;
    	}
    	return repository.findBySendFlagAndSobId(sendFlag, sobId);
    }
    
}
