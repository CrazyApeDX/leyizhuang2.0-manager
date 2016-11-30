package com.ynyes.lyz.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ynyes.lyz.entity.TdRequisition;
import com.ynyes.lyz.repository.TdRequisitionRepo;

@Service
@Transactional
public class TdRequisitionService {
	 @Autowired
	 TdRequisitionRepo repository;
	 
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
	    public void delete(TdRequisition e)
	    {
	        if (null != e)
	        {
	            repository.delete(e);
	        }
	    }
	    
	    public void delete(List<TdRequisition> entities)
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
	    public TdRequisition findOne(Long id)
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
	    public List<TdRequisition> findAll(Iterable<Long> ids)
	    {
	        return (List<TdRequisition>) repository.findAll(ids);
	    }
	    
	    public List<TdRequisition> findAll()
	    {
	        return (List<TdRequisition>) repository.findAll();
	    }
	    
	    public TdRequisition findByOrderNumber(String orderNumber)
	    {
	    	if (orderNumber == null)
	    	{
				return null;
			}
	    	return repository.findByOrderNumber(orderNumber);
	    }
//	    public TdRequisition findBySubOrderNumber(String subOrderNumber)
//	    {
//	    	if (subOrderNumber == null)
//	    	{
//				return null;
//			}
//	    	return repository.findBySubOrderNumber(subOrderNumber);
//	    }
	 
	    
	    public Page<TdRequisition> findAll(int page, int size){
	    	PageRequest pageRequest = new PageRequest(page, size, new Sort(Direction.DESC, "id"));
	    	
	    	return repository.findAll(pageRequest);
	    }
	    
//	    public Page<TdRequisition> searchAll(String keywords, int page, int size){
//	    	PageRequest pageRequest = new PageRequest(page, size, new Sort(Direction.DESC, "id"));
//	    	
//	    	return repository.findByDiySiteTitleContainingOrRemarkInfoContainingOrManagerRemarkInfoContainingOrRequisitionNumberContaining(keywords, keywords, keywords, keywords, pageRequest);
//	    }
	    
	    // 通过typeid查询不同类别的要货单
	    public Page<TdRequisition> findByTypeId(Long typeId, int page, int size){
	    	PageRequest pageRequest = new PageRequest(page, size, new Sort(Direction.DESC, "id"));
	    	
	    	return repository.findByTypeId(typeId, pageRequest);
	    }
	    
//	    public Page<TdRequisition> searchByTypeId(String keywords, Long typeId, int page, int size){
//	    	PageRequest pageRequest = new PageRequest(page, size, new Sort(Direction.DESC, "id"));
//	    	
//	    	return repository.findByDiySiteTitleContainingAndTypeIdOrRemarkInfoContainingAndTypeIdOrManagerRemarkInfoContainingAndTypeIdOrRequisitionNumberContainingAndTypeId(keywords, typeId, keywords, typeId, keywords, typeId, keywords, typeId, pageRequest);
//	    }
	    
	    /**
	     * 保存
	     * 
	     * @param e
	     * @return
	     */
	    public TdRequisition save(TdRequisition e)
	    {	       	        
	        return repository.save(e);
	    }
	    
	    public List<TdRequisition> save(List<TdRequisition> entities)
	    {
	        
	        return (List<TdRequisition>) repository.save(entities);
	    }
}
