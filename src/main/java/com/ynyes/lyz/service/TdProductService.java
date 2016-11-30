package com.ynyes.lyz.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ynyes.lyz.entity.TdProduct;
import com.ynyes.lyz.entity.TdProductCategory;
import com.ynyes.lyz.repository.TdProductRepo;


/**
 * TdProduct 服务类
 * 
 * @author Sharon
 *
 */

@Service
@Transactional
public class TdProductService {
    
    @Autowired
    TdProductRepo repository;
    
    @Autowired
    TdProductCategoryService tdProductCategoryService;
    
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
    public void delete(TdProduct e)
    {
        if (null != e)
        {
            repository.delete(e);
        }
    }
    
    public void delete(List<TdProduct> entities)
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
    public TdProduct findOne(Long id)
    {
        if (null == id)
        {
            return null;
        }
        
        return repository.findOne(id);
    }
    
    public TdProduct findByProductCategoryTreeContainingAndTitle(Long productCategoryId, String title)
    {
        if (null == title || null == productCategoryId)
        {
            return null;
        }
        
        return repository.findByProductCategoryTreeContainingAndTitle("["+productCategoryId+"]", title);
    }
    
    public List<TdProduct> findByProductCategoryTreeContaining(Long productCategoryId)
    {
        if (null == productCategoryId)
        {
            return null;
        }
        
        return repository.findByProductCategoryTreeContaining("["+productCategoryId+"]");
    }
    
    public TdProduct findByTitle(String title)
    {
        if (null == title)
        {
            return null;
        }
        
        return repository.findByTitle(title);
    }
    
    public TdProduct findByProductCategoryTreeContainingAndTitleAndIdNot(Long productCategoryId, String title, Long id)
    {
        if (null == title || null == productCategoryId || null == id)
        {
            return null;
        }
        
        return repository.findByProductCategoryTreeContainingAndTitleAndIdNot("["+productCategoryId+"]", title, id);
    }
    
    public TdProduct findByTitleAndIdNot(String title, Long id)
    {
        if (null == title || null == id)
        {
            return null;
        }
        
        return repository.findByTitleAndIdNot(title, id);
    }
    
    /**
     * 查找
     * 
     * @param ids
     * @return
     */
    public List<TdProduct> findAll(Iterable<Long> ids)
    {
        return (List<TdProduct>) repository.findAll(ids);
    }
    
    public List<TdProduct> findAll()
    {
        return (List<TdProduct>) repository.findAll();
    }
    
    public Page<TdProduct> findAllOrderBySortIdAsc(int page, int size)
    {
        PageRequest pageRequest = new PageRequest(page, size, new Sort(Direction.ASC, "sortId"));
        
        return repository.findAll(pageRequest);
    }
    
    public Page<TdProduct> searchAndOrderBySortIdAsc(String keywords, int page, int size)
    {
        PageRequest pageRequest = new PageRequest(page, size);
        
        return repository.findByTitleContainingOrderBySortIdAsc(keywords, pageRequest);
    }
    
    /**
     * 保存
     * 
     * @param e
     * @return
     */
    public TdProduct save(TdProduct e)
    {
        
        if (null != e.getProductCategoryId())
        {
            TdProductCategory cat = tdProductCategoryService.findOne(e.getProductCategoryId());
            
            if (null != cat)
            {
                e.setProductCategoryTree(cat.getParentTree());
            }
        }
        
        return repository.save(e);
    }
    
    public List<TdProduct> save(List<TdProduct> entities)
    {
        
        return (List<TdProduct>) repository.save(entities);
    }
}
