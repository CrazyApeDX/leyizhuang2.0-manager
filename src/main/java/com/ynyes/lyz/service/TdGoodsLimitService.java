package com.ynyes.lyz.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ynyes.lyz.entity.TdGoodsLimit;
import com.ynyes.lyz.repository.TdGoodsLimitRepo;

@Service
@Transactional
public class TdGoodsLimitService {

	@Autowired
	private TdGoodsLimitRepo repository;
	
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
    public void delete(TdGoodsLimit e)
    {
        if (null != e)
        {
            repository.delete(e);
        }
    }
    
    public void delete(List<TdGoodsLimit> entities)
    {
        if (null != entities)
        {
            repository.delete(entities);
        }
    }
    /**
     * 保存
     * 
     * @param e
     * @return
     */
    public TdGoodsLimit save(TdGoodsLimit e)
    {
        
        return repository.save(e);
    }
    
    public TdGoodsLimit findByLimitId(Long limitId)
    {
    	if (limitId == null)
    	{
			return null;
		}
    	return repository.findByLimitId(limitId);
    }
    
    public List<TdGoodsLimit> save(List<TdGoodsLimit> entities)
    {
        return (List<TdGoodsLimit>) repository.save(entities);
    }
    /**
     * 查找
     * 
     * @param id ID
     * @return
     */
    public TdGoodsLimit findOne(Long id)
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
    public List<TdGoodsLimit> findAll(Iterable<Long> ids)
    {
        return (List<TdGoodsLimit>) repository.findAll(ids);
    }
}
