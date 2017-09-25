package com.ynyes.lyz.interfaces.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ynyes.lyz.interfaces.entity.TdReturnGoodsInf;
import com.ynyes.lyz.interfaces.repository.TdReturnGoodsInfRepo;


/**
 * TdOrderInf 服务类
 * 
 * @author Sharon
 *
 */

@Service
@Transactional
public class TdReturnGoodsInfService {
    
    @Autowired
    private TdReturnGoodsInfRepo repository;
    
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
    public void delete(TdReturnGoodsInf e)
    {
        if (null != e)
        {
            repository.delete(e);
        }
    }
    
    public void delete(List<TdReturnGoodsInf> entities)
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
    public TdReturnGoodsInf findOne(Long id)
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
    public List<TdReturnGoodsInf> findAll(Iterable<Long> ids)
    {
        return (List<TdReturnGoodsInf>) repository.findAll(ids);
    }
    
    public List<TdReturnGoodsInf> findAll()
    {
        return (List<TdReturnGoodsInf>) repository.findAll();
    }
    
    public List<TdReturnGoodsInf> findByRtHeaderId(Long rtHeaderId)
    {
    	return repository.findByRtHeaderId(rtHeaderId);
    }
    
    /**
     * 保存
     * 
     * @param e
     * @return
     */
    public TdReturnGoodsInf save(TdReturnGoodsInf e)
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
    
    public List<TdReturnGoodsInf> save(List<TdReturnGoodsInf> entities)
    {
        return (List<TdReturnGoodsInf>) repository.save(entities);
    }
    
}
