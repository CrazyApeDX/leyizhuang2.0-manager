package com.ynyes.lyz.interfaces.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ynyes.lyz.interfaces.entity.TdReturnCouponInf;
import com.ynyes.lyz.interfaces.repository.TdReturnCouponInfRepo;


/**
 * TdOrderInf 服务类
 * 
 * @author Sharon
 *
 */

@Service
@Transactional
public class TdReturnCouponInfService {
    
    @Autowired
    private TdReturnCouponInfRepo repository;
    
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
    public void delete(TdReturnCouponInf e)
    {
        if (null != e)
        {
            repository.delete(e);
        }
    }
    
    public void delete(List<TdReturnCouponInf> entities)
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
    public TdReturnCouponInf findOne(Long id)
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
    public List<TdReturnCouponInf> findAll(Iterable<Long> ids)
    {
        return (List<TdReturnCouponInf>) repository.findAll(ids);
    }
    
    public List<TdReturnCouponInf> findAll()
    {
        return (List<TdReturnCouponInf>) repository.findAll();
    }
    public List<TdReturnCouponInf> findByRtHeaderId(Long rtHeaderId)
    {
    	return repository.findByRtHeaderId(rtHeaderId);
    }
    
    /**
     * 保存
     * 
     * @param e
     * @return
     */
    public TdReturnCouponInf save(TdReturnCouponInf e)
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
    
    public List<TdReturnCouponInf> save(List<TdReturnCouponInf> entities)
    {
        return (List<TdReturnCouponInf>) repository.save(entities);
    }
    
}
