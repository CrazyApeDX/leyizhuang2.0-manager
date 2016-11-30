package com.ynyes.lyz.interfaces.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ynyes.lyz.interfaces.entity.TdOrderCouponInf;
import com.ynyes.lyz.interfaces.repository.TdOrderCouponInfRepo;


/**
 * TdOrderInf 服务类
 * 
 * @author Sharon
 *
 */

@Service
@Transactional
public class TdOrderCouponInfService {
    
    @Autowired
    private TdOrderCouponInfRepo repository;
    
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
    public void delete(TdOrderCouponInf e)
    {
        if (null != e)
        {
            repository.delete(e);
        }
    }
    
    public void delete(List<TdOrderCouponInf> entities)
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
    public TdOrderCouponInf findOne(Long id)
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
    public List<TdOrderCouponInf> findAll(Iterable<Long> ids)
    {
        return (List<TdOrderCouponInf>) repository.findAll(ids);
    }
    
    public List<TdOrderCouponInf> findAll()
    {
        return (List<TdOrderCouponInf>) repository.findAll();
    }
    
    public List<TdOrderCouponInf> findByorderHeaderId(Long headerId)
    {
    	return repository.findByOrderHeaderId(headerId);
    }
    
    /**
     * 保存
     * 
     * @param e
     * @return
     */
    public TdOrderCouponInf save(TdOrderCouponInf e)
    {
    	if (null == e.getInitDate())
        {
            e.setInitDate(new Date());
        }
    	e.setModifyDate(new Date());
        return repository.save(e);
    }
    
    public List<TdOrderCouponInf> save(List<TdOrderCouponInf> entities)
    {
        return (List<TdOrderCouponInf>) repository.save(entities);
    }
    
}
