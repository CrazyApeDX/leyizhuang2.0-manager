package com.ynyes.lyz.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ynyes.lyz.entity.TdGeoInfo;
import com.ynyes.lyz.repository.TdGeoInfoRepo;


/**
 * TdGeoInfo 服务类
 * 
 * @author Sharon
 *
 */

@Service
@Transactional
public class TdGeoInfoService {
    
    @Autowired
    TdGeoInfoRepo repository;
    
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
    public void delete(TdGeoInfo e)
    {
        if (null != e)
        {
            repository.delete(e);
        }
    }
    
    public void delete(List<TdGeoInfo> entities)
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
    public TdGeoInfo findOne(Long id)
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
    public List<TdGeoInfo> findAll(Iterable<Long> ids)
    {
        return (List<TdGeoInfo>) repository.findAll(ids);
    }
    
    public List<TdGeoInfo> findAll()
    {
        return (List<TdGeoInfo>) repository.findAll();
    }
    
    public Page<TdGeoInfo> findAllOrderBySortIdAsc(int page, int size)
    {
        PageRequest pageRequest = new PageRequest(page, size, new Sort(Direction.ASC, "sortId"));
        
        return repository.findAll(pageRequest);
    }
    
    public List<TdGeoInfo> findAllOrderBySortIdAsc()
    {
        return (List<TdGeoInfo>) repository.findAll(new Sort(Direction.ASC, "sortId"));
    }
    
    public List<TdGeoInfo> save(List<TdGeoInfo> entities)
    {
        
        return (List<TdGeoInfo>) repository.save(entities);
    }
    
    public TdGeoInfo save(TdGeoInfo e)
    {
        
        return repository.save(e);
    }
    
    public List<TdGeoInfo> findByOpUserOrderByTimeDesc(String opUser){
    	if(null == opUser){
    		return null;
    	}
    	return repository.findByOpUserOrderByTimeDesc(opUser);
    }
}
