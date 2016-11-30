package com.ynyes.lyz.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ynyes.lyz.entity.TdGoodsCombination;
import com.ynyes.lyz.repository.TdGoodsCombinationRepo;



/**
 * TdGoodsCombination 服务类
 * 
 * @author Sharon
 *
 */

@Service
@Transactional
public class TdGoodsCombinationService {
    @Autowired
    TdGoodsCombinationRepo repository;
    
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
    public void delete(TdGoodsCombination e)
    {
        if (null != e)
        {
            repository.delete(e);
        }
    }
    
    public void delete(List<TdGoodsCombination> entities)
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
    public TdGoodsCombination findOne(Long id)
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
    public List<TdGoodsCombination> findAll(Iterable<Long> ids)
    {
        return (List<TdGoodsCombination>) repository.findAll(ids);
    }
    
    public List<TdGoodsCombination> findByGoodsId(Long goodsId)
    {
        return repository.findByGoodsId(goodsId);
    }
    
    /**
     * 保存
     * 
     * @param e
     * @return
     */
    public TdGoodsCombination save(TdGoodsCombination e)
    {
        
        return repository.save(e);
    }
    
    public List<TdGoodsCombination> save(List<TdGoodsCombination> entities)
    {
        
        return (List<TdGoodsCombination>) repository.save(entities);
    }
}
