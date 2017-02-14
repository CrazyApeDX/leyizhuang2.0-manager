package com.ynyes.lyz.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ynyes.lyz.entity.TdTbwRecm;
import com.ynyes.lyz.interfaces.entity.TdTbwWholeSepDirection;
import com.ynyes.lyz.repository.TdTbwRecmRepo;
import com.ynyes.lyz.repository.TdTbwWholeSepDirectionRepo;


/**
 * 整转零接口 服务类
 * 
 * @author yanle
 *
 */

@Service
@Transactional
public class TdTbwWholeSepDirectionService {
    
    @Autowired
     TdTbwWholeSepDirectionRepo repository;
    
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
    public void delete(TdTbwWholeSepDirection e)
    {
        if (null != e)
        {
            repository.delete(e);
        }
    }
    
    public void delete(List<TdTbwWholeSepDirection> entities)
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
    public TdTbwWholeSepDirection findOne(Long id)
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
    public List<TdTbwWholeSepDirection> findAll(Iterable<Long> ids)
    {
        return (List<TdTbwWholeSepDirection>) repository.findAll(ids);
    }
    
    public List<TdTbwWholeSepDirection> findAll()
    {
        return (List<TdTbwWholeSepDirection>) repository.findAll();
    }
    
    public TdTbwWholeSepDirection findByCOwnerNoAndCDirectNoAndCDirectIdAndCTaskType(String COwnerNo,String CDirectNo,Long CDirectId,String CTaskType)
    {
    	if (null == COwnerNo || null==CDirectNo || null == CDirectId || null == CTaskType)
		{
			return null;
		}
    	return repository.findByCOwnerNoAndCDirectNoAndCDirectIdAndCTaskType(COwnerNo,CDirectNo,CDirectId,CTaskType);
    }
    
    /**
     * 保存
     * 
     * @param e
     * @return
     */
    public TdTbwWholeSepDirection save(TdTbwWholeSepDirection e)
    {
        return repository.save(e);
    }
    
    public List<TdTbwWholeSepDirection> save(List<TdTbwWholeSepDirection> entities)
    {
        
        return (List<TdTbwWholeSepDirection>) repository.save(entities);
    }
}
