package com.ynyes.lyz.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ynyes.lyz.entity.TdServiceItem;
import com.ynyes.lyz.repository.TdServiceItemRepo;


/**
 * TdMallService 服务类
 * 
 * @author Sharon
 *
 */

@Service
@Transactional
public class TdServiceItemService {
    @Autowired
    TdServiceItemRepo repository;
    
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
    public void delete(TdServiceItem e)
    {
        if (null != e)
        {
            repository.delete(e);
        }
    }
    
    public void delete(List<TdServiceItem> entities)
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
    public TdServiceItem findOne(Long id)
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
    public List<TdServiceItem> findAll(Iterable<Long> ids)
    {
        return (List<TdServiceItem>) repository.findAll(ids);
    }
    
    public List<TdServiceItem> findAllOrderBySortIdAsc()
    {
        Sort sort = new Sort(Direction.ASC, "sortId");
        
        return (List<TdServiceItem>) repository.findAll(sort);
    }
    
    public List<TdServiceItem> findByIsEnableTrue()
    {
        return repository.findByIsEnableTrue();
    }
    
    public Page<TdServiceItem> findAllOrderBySortIdAsc(int page, int size)
    {
        PageRequest pageRequest = new PageRequest(page, size, new Sort(Direction.ASC, "sortId"));
        
        return repository.findAll(pageRequest);
    }
    
    public Page<TdServiceItem> searchAllOrderBySortIdAsc(String keywords, int page, int size)
    {
        PageRequest pageRequest = new PageRequest(page, size);
        
        return repository.findByTitleContainingOrderBySortIdAsc(keywords, pageRequest);
    }
    
    public List<TdServiceItem> findByIsEnableTrueOrderBySortIdAsc()
    {
        return repository.findByIsEnableTrueOrderBySortIdAsc();
    }
    
    /**
	 * @author lc
	 * @注释：
	 */
    public List<TdServiceItem> findByIsEnableTrueAndIsGoodsServiceTrueOrderBySortIdAsc()
    {
        return repository.findByIsEnableTrueAndIsGoodsServiceTrueOrderBySortIdAsc();
    }
    public List<TdServiceItem> findByIsEnableTrueAndIsGoodsServiceFalseOrderBySortIdAsc()
    {
        return repository.findByIsEnableTrueAndIsGoodsServiceFalseOrderBySortIdAsc();
    }
    
    /**
     * 保存
     * 
     * @param e
     * @return
     */
    public TdServiceItem save(TdServiceItem e)
    {
        
        return repository.save(e);
    }
    
    public List<TdServiceItem> save(List<TdServiceItem> entities)
    {
        
        return (List<TdServiceItem>) repository.save(entities);
    }
}
