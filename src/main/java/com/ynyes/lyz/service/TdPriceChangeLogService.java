package com.ynyes.lyz.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ynyes.lyz.entity.TdPriceChangeLog;
import com.ynyes.lyz.repository.TdPriceChangeLogRepo;


/**
 * TdPriceChangeLog 服务类
 * 
 * @author Sharon
 *
 */

@Service
@Transactional
public class TdPriceChangeLogService {
    
    @Autowired
    TdPriceChangeLogRepo repository;
    
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
    public void delete(TdPriceChangeLog e)
    {
        if (null != e)
        {
            repository.delete(e);
        }
    }
    
    public void delete(List<TdPriceChangeLog> entities)
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
    public TdPriceChangeLog findOne(Long id)
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
    public List<TdPriceChangeLog> findAll(Iterable<Long> ids)
    {
        return (List<TdPriceChangeLog>) repository.findAll(ids);
    }
    
    public List<TdPriceChangeLog> findAll()
    {
        return (List<TdPriceChangeLog>) repository.findAll();
    }
    
    public Page<TdPriceChangeLog> findAllOrderBySortIdAsc(int page, int size)
    {
        PageRequest pageRequest = new PageRequest(page, size, new Sort(Direction.ASC, "sortId"));
        
        return repository.findAll(pageRequest);
    }
    
    public Page<TdPriceChangeLog> findByGoodsIdOrderByIdDesc(Long goodsId, int page, int size)
    {
        if (null == goodsId)
        {
            return null;
        }
        
        PageRequest pageRequest = new PageRequest(page, size, new Sort(Direction.DESC, "id"));
        
        return repository.findByGoodsIdOrderByIdDesc(goodsId, pageRequest);
    }
    
    public TdPriceChangeLog findTopByGoodsIdOrderByIdDesc(Long goodsId)
    {
        if (null == goodsId)
        {
            return null;
        }
        
        List<TdPriceChangeLog> priceLogList = repository.findByGoodsIdOrderByIdDesc(goodsId);
        
        if (null != priceLogList && priceLogList.size() > 0)
        {
            return priceLogList.get(0);
        }
        
        return null;
    }
    
    public List<TdPriceChangeLog> findAllOrderBySortIdAsc()
    {
        return (List<TdPriceChangeLog>) repository.findAll(new Sort(Direction.ASC, "sortId"));
    }
    
    /**
     * 保存
     * 
     * @param e
     * @return
     */
    public TdPriceChangeLog save(TdPriceChangeLog e)
    {
        if (null == e.getCreateTime())
        {
            e.setCreateTime(new Date());
        }
        
        return repository.save(e);
    }
    
    public List<TdPriceChangeLog> save(List<TdPriceChangeLog> entities)
    {
        
        return (List<TdPriceChangeLog>) repository.save(entities);
    }
}
