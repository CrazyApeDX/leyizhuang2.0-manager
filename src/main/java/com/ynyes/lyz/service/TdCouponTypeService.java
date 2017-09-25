package com.ynyes.lyz.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ynyes.lyz.entity.TdCouponType;
import com.ynyes.lyz.repository.TdCouponTypeRepo;

/**
 * TdCouponType 服务类
 * 
 * @author Sharon
 *
 */

@Service
@Transactional
public class TdCouponTypeService {
    @Autowired
    TdCouponTypeRepo repository;
    
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
    public void delete(TdCouponType e)
    {
        if (null != e)
        {
            repository.delete(e);
        }
    }
    
    public void delete(List<TdCouponType> entities)
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
    public TdCouponType findOne(Long id)
    {
        if (null == id)
        {
            return null;
        }
        
        return repository.findOne(id);
    }
    
    /**
     * 根据类型查找，唯一优惠券
     * @author Zhangji
     * @param categoryId
     * @return
     */
    public TdCouponType findByCategoryIdAndPicUriNotNull(Long categoryId)
    {
    	return repository.findByCategoryIdAndPicUriNotNull(categoryId);
    }
    
    /**
     * 查找
     * 
     * @param ids
     * @return
     */
    public List<TdCouponType> findAll(Iterable<Long> ids)
    {
        return (List<TdCouponType>) repository.findAll(ids);
    }
    
    public List<TdCouponType> findAllOrderBySortIdAsc()
    {
        Sort sort = new Sort(Direction.ASC, "sortId");
        
        return (List<TdCouponType>) repository.findAll(sort);
    }
    
    public Page<TdCouponType> findAllOrderBySortIdAsc(int page, int size)
    {
        PageRequest pageRequest = new PageRequest(page, size, new Sort(Direction.ASC, "sortId"));
        
        return repository.findAll(pageRequest);
    }
    
    public Page<TdCouponType> searchAllOrderBySortIdAsc(String keywords, int page, int size)
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
    public TdCouponType save(TdCouponType e)
    {
        
        return repository.save(e);
    }
    
    public List<TdCouponType> save(List<TdCouponType> entities)
    {
        
        return (List<TdCouponType>) repository.save(entities);
    }
    
    /**
     * 按照优惠券类型Id查找优惠券
     * @param categoryId
     * @return
     */
    public List<TdCouponType> findByCategoryId(Long categoryId)
    {
    	return repository.findByCategoryIdIs(categoryId);
    }
}
