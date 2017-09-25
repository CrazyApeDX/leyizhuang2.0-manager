package com.ynyes.lyz.interfaces.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ynyes.lyz.interfaces.entity.TdTbwBackRecD;
import com.ynyes.lyz.interfaces.repository.TdTbwBackRecDRepo;

@Service
@Transactional
public class TdTbwBackRecDService
{
	@Autowired
    private TdTbwBackRecDRepo repository;
    
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
    public void delete(TdTbwBackRecD e)
    {
        if (null != e)
        {
            repository.delete(e);
        }
    }
    
    public void delete(List<TdTbwBackRecD> entities)
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
    public TdTbwBackRecD findOne(Long id)
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
    public List<TdTbwBackRecD> findAll(Iterable<Long> ids)
    {
        return (List<TdTbwBackRecD>) repository.findAll(ids);
    }
    
    public List<TdTbwBackRecD> findAll()
    {
        return (List<TdTbwBackRecD>) repository.findAll();
    }
    public List<TdTbwBackRecD> findByCRecNo(String recNo)
    {
    	if (recNo == null)
		{
			return null;
		}
    	return repository.findByCRecNo(recNo);
    }
    
    /**
     * 保存
     * 
     * @param e
     * @return
     */
    public TdTbwBackRecD save(TdTbwBackRecD e)
    {
        if (null == e.getInitDate())
        {
            e.setInitDate(new Date());
        }
//        if (null == e.getSendFlag())
//        {
//			e.setSendFlag("N");
//		}
        e.setModifyDate(new Date());

        return repository.save(e);
    }
    
    public List<TdTbwBackRecD> save(List<TdTbwBackRecD> entities)
    {
        return (List<TdTbwBackRecD>) repository.save(entities);
    }
}
