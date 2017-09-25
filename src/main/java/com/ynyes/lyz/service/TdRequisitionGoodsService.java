package com.ynyes.lyz.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ynyes.lyz.entity.TdRequisitionGoods;
import com.ynyes.lyz.repository.TdRequisitionGoodsRepo;


/**
 * TdAd 服务类
 * 
 * @author Sharon
 *
 */

@Service
@Transactional
public class TdRequisitionGoodsService {
    
    @Autowired
    TdRequisitionGoodsRepo repository;
    
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
    public void delete(TdRequisitionGoods e)
    {
        if (null != e)
        {
            repository.delete(e);
        }
    }
    
    public void delete(List<TdRequisitionGoods> entities)
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
    public TdRequisitionGoods findOne(Long id)
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
    public List<TdRequisitionGoods> findAll(Iterable<Long> ids)
    {
        return (List<TdRequisitionGoods>) repository.findAll(ids);
    }
    
    public List<TdRequisitionGoods> findAll()
    {
        return (List<TdRequisitionGoods>) repository.findAll();
    }
    
    public Page<TdRequisitionGoods> findAllOrderBySortIdAsc(int page, int size)
    {
        PageRequest pageRequest = new PageRequest(page, size, new Sort(Direction.ASC, "sortId"));
        
        return repository.findAll(pageRequest);
    }
    
    /**
     * 保存
     * 
     * @param e
     * @return
     */
    public TdRequisitionGoods save(TdRequisitionGoods e)
    {        
        return repository.save(e);
    }
    
    public List<TdRequisitionGoods> save(List<TdRequisitionGoods> entities)
    {
        return (List<TdRequisitionGoods>) repository.save(entities);
    }
}
