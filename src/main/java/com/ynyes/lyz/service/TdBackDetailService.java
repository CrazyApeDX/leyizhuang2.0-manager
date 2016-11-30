package com.ynyes.lyz.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ynyes.lyz.entity.TdBackDetail;
import com.ynyes.lyz.repository.TdBackDetailRepo;


/**
 * TdBackMain 服务类
 * 
 * @author Sharon
 *
 */

@Service
@Transactional
public class TdBackDetailService {
    
    @Autowired
    TdBackDetailRepo repository;
    
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
    public void delete(TdBackDetail e)
    {
        if (null != e)
        {
            repository.delete(e);
        }
    }
    
    public void delete(List<TdBackDetail> entities)
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
    public TdBackDetail findOne(Long id)
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
    public List<TdBackDetail> findAll(Iterable<Long> ids)
    {
        return (List<TdBackDetail>) repository.findAll(ids);
    }
    
    public List<TdBackDetail> findAll()
    {
        return (List<TdBackDetail>) repository.findAll();
    }
    
    public Page<TdBackDetail> findAllOrderBySortIdAsc(int page, int size)
    {
        PageRequest pageRequest = new PageRequest(page, size);
        
        return repository.findAll(pageRequest);
    }
   

    
    /**
     * 保存
     * 
     * @param e
     * @return
     */
    public TdBackDetail save(TdBackDetail e)
    {
        return repository.save(e);
    }
    
    public List<TdBackDetail> save(List<TdBackDetail> entities)
    {
        return (List<TdBackDetail>) repository.save(entities);
    }
}
