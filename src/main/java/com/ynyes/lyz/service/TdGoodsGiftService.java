package com.ynyes.lyz.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ynyes.lyz.entity.TdGoodsGift;
import com.ynyes.lyz.repository.TdGoodsGiftRepo;



/**
 * TdGoodsGift 服务类
 * 
 * @author Sharon
 *
 */

@Service
@Transactional
public class TdGoodsGiftService {
    @Autowired
    TdGoodsGiftRepo repository;
    
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
    public void delete(TdGoodsGift e)
    {
        if (null != e)
        {
            repository.delete(e);
        }
    }
    
    public void delete(List<TdGoodsGift> entities)
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
    public TdGoodsGift findOne(Long id)
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
    public List<TdGoodsGift> findAll(Iterable<Long> ids)
    {
        return (List<TdGoodsGift>) repository.findAll(ids);
    }
    
    /**
     * 保存
     * 
     * @param e
     * @return
     */
    public TdGoodsGift save(TdGoodsGift e)
    {
        
        return repository.save(e);
    }
    
    public List<TdGoodsGift> save(List<TdGoodsGift> entities)
    {
        
        return (List<TdGoodsGift>) repository.save(entities);
    }
}
