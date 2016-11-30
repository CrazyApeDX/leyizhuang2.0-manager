package com.ynyes.lyz.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ynyes.lyz.entity.TdDeliveryInfo;
import com.ynyes.lyz.repository.TdDeliveryInfoRepo;


/**
 * TdAd 服务类
 * 
 * @author Sharon
 *
 */

@Service
@Transactional
public class TdDeliveryInfoService {
    
    @Autowired
    TdDeliveryInfoRepo repository;
    
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
    public void delete(TdDeliveryInfo e)
    {
        if (null != e)
        {
            repository.delete(e);
        }
    }
    
    public void delete(List<TdDeliveryInfo> entities)
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
    public TdDeliveryInfo findOne(Long id)
    {
        if (null == id)
        {
            return null;
        }
        
        return repository.findOne(id);
    }
    
    public TdDeliveryInfo findByTaskNo(String taskNo)
    {
		if (taskNo == null)
		{
			return null;
		}
		return repository.findByTaskNo(taskNo);
	}
    public List<TdDeliveryInfo> findDistinctTaskNoByTaskNo(String taskNo)
    {
    	if (taskNo == null)
    	{
			return null;
		}
    	return repository.findDistinctTaskNoByTaskNo(taskNo);
    }
    
    public List<TdDeliveryInfo> findByOpUser(String opUser)
    {
		if (opUser == null)
		{
			return null;
		}
		return repository.findByOpUser(opUser);
	}
    
    public List<TdDeliveryInfo> findDistinctTaskNoByDriver(String driver)
    {
		if (driver == null)
		{
			return null;
		}
		
		return repository.findDistinctTaskNoByDriver(driver);
	}
    
    /**
     * 查找
     * 
     * @param ids
     * @return
     */
    public List<TdDeliveryInfo> findAll(Iterable<Long> ids)
    {
        return (List<TdDeliveryInfo>) repository.findAll(ids);
    }
    
    public List<TdDeliveryInfo> findAll()
    {
        return (List<TdDeliveryInfo>) repository.findAll();
    }
    
    public Page<TdDeliveryInfo> findAllOrderBySortIdAsc(int page, int size)
    {
        PageRequest pageRequest = new PageRequest(page, size, new Sort(Direction.ASC, "sortId"));
        
        return repository.findAll(pageRequest);
    }
    
    public List<TdDeliveryInfo> findAllOrderBySortIdAsc()
    {
        return (List<TdDeliveryInfo>) repository.findAll(new Sort(Direction.ASC, "sortId"));
    }
    
    public List<TdDeliveryInfo> findByOrderNumberOrderByBeginDtDesc(String orderNumber)
    {
    	if (orderNumber == null)
    	{
			return null;
		}
    	return repository.findByOrderNumberOrderByBeginDtDesc(orderNumber);
    }
    
    /**
     * 保存
     * 
     * @param e
     * @return
     */
    public TdDeliveryInfo save(TdDeliveryInfo e)
    {
        return repository.save(e);
    }
    
    public List<TdDeliveryInfo> save(List<TdDeliveryInfo> entities)
    {
        
        return (List<TdDeliveryInfo>) repository.save(entities);
    }
    
}
