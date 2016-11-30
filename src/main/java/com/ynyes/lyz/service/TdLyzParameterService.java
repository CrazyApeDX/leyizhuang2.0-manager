package com.ynyes.lyz.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ynyes.lyz.entity.TdLyzParameter;
import com.ynyes.lyz.repository.TdLyzParameterRepo;


/**
 * TdAd 服务类
 * 
 * @author Sharon
 *
 */

@Service
@Transactional
public class TdLyzParameterService {
    
    @Autowired
    TdLyzParameterRepo repository;
    
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
    public void delete(TdLyzParameter e)
    {
        if (null != e)
        {
            repository.delete(e);
        }
    }
    
    public void delete(List<TdLyzParameter> entities)
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
    public TdLyzParameter findOne(Long id)
    {
        if (null == id)
        {
            return null;
        }
        
        return repository.findOne(id);
    }
    
    public TdLyzParameter findByCategoryId(Long categoryId)
    {
    	if (categoryId == null)
    	{
			return null;
		}
    	return repository.findByCategoryId(categoryId);
    }
    
    /**
     * 查找
     * 
     * @param ids
     * @return
     */
    public List<TdLyzParameter> findAll(Iterable<Long> ids)
    {
        return (List<TdLyzParameter>) repository.findAll(ids);
    }
    
    public List<TdLyzParameter> findAll()
    {
        return (List<TdLyzParameter>) repository.findAll();
    }
    
    
    
    /**
     * 保存
     * 
     * @param e
     * @return
     */
    public TdLyzParameter save(TdLyzParameter e)
    {
        return repository.save(e);
    }
    
    public List<TdLyzParameter> save(List<TdLyzParameter> entities)
    {
        
        return (List<TdLyzParameter>) repository.save(entities);
    }
}
