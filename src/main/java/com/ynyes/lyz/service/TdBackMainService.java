package com.ynyes.lyz.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ynyes.lyz.entity.TdBackMain;
import com.ynyes.lyz.repository.TdBackMainRepo;


/**
 * TdBackMain 服务类
 * 
 * @author Sharon
 *
 */

@Service
@Transactional
public class TdBackMainService {
    
    @Autowired
    TdBackMainRepo repository;
    
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
    public void delete(TdBackMain e)
    {
        if (null != e)
        {
            repository.delete(e);
        }
    }
    
    public void delete(List<TdBackMain> entities)
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
    public TdBackMain findOne(Long id)
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
    public List<TdBackMain> findAll(Iterable<Long> ids)
    {
        return (List<TdBackMain>) repository.findAll(ids);
    }
    
    public List<TdBackMain> findAll()
    {
        return (List<TdBackMain>) repository.findAll();
    }
    
    public Page<TdBackMain> findAllOrderBySortIdAsc(int page, int size)
    {
        PageRequest pageRequest = new PageRequest(page, size);
        
        return repository.findAll(pageRequest);
    }
   
   
//    private TdBackMain findByWhNo(String whNo)
//    {
//    	if (whNo == null) 
//    	{
//			return null;
//		}
//    	return repository.findByWhNo(whNo);
//    }
    
    public TdBackMain findByRecNo(String c_rec_no)
    {
    	if (c_rec_no == null)
    	{
			return null;
		}
    	return repository.findByRecNo(c_rec_no);
    }

    
    /**
     * 保存
     * 
     * @param e
     * @return
     */
    public TdBackMain save(TdBackMain e)
    {
        return repository.save(e);
    }
    
    public List<TdBackMain> save(List<TdBackMain> entities)
    {
        
        return (List<TdBackMain>) repository.save(entities);
    }
}
