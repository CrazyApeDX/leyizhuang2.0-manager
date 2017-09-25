package com.ynyes.lyz.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ynyes.lyz.entity.TdTbwRecm;
import com.ynyes.lyz.repository.TdTbwRecmRepo;


/**
 * TdAd 服务类
 * 
 * @author Sharon
 *
 */

@Service
@Transactional
public class TdTbwRecmService {
    
    @Autowired
     TdTbwRecmRepo repository;
    
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
    public void delete(TdTbwRecm e)
    {
        if (null != e)
        {
            repository.delete(e);
        }
    }
    
    public void delete(List<TdTbwRecm> entities)
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
    public TdTbwRecm findOne(Long id)
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
    public List<TdTbwRecm> findAll(Iterable<Long> ids)
    {
        return (List<TdTbwRecm>) repository.findAll(ids);
    }
    
    public List<TdTbwRecm> findAll()
    {
        return (List<TdTbwRecm>) repository.findAll();
    }
    
    public TdTbwRecm findByCRecNo(String cRecNo)
    {
    	if (cRecNo == null)
		{
			return null;
		}
    	return repository.findByCRecNo(cRecNo);
    }
    
    /**
     * 保存
     * 
     * @param e
     * @return
     */
    public TdTbwRecm save(TdTbwRecm e)
    {
        return repository.save(e);
    }
    
    public List<TdTbwRecm> save(List<TdTbwRecm> entities)
    {
        
        return (List<TdTbwRecm>) repository.save(entities);
    }
}
