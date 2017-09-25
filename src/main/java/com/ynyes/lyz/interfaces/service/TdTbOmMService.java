package com.ynyes.lyz.interfaces.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ynyes.lyz.interfaces.entity.TdTbOmM;
import com.ynyes.lyz.interfaces.repository.TdTbOmMRepo;

@Service
@Transactional
public class TdTbOmMService
{
	@Autowired
    private TdTbOmMRepo repository;
    
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
    public void delete(TdTbOmM e)
    {
        if (null != e)
        {
            repository.delete(e);
        }
    }
    
    public void delete(List<TdTbOmM> entities)
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
    public TdTbOmM findOne(Long id)
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
    public List<TdTbOmM> findAll(Iterable<Long> ids)
    {
        return (List<TdTbOmM>) repository.findAll(ids);
    }
    
    public List<TdTbOmM> findAll()
    {
        return (List<TdTbOmM>) repository.findAll();
    }
    public TdTbOmM findByCOmNo(String cOmNo)
    {
    	if (cOmNo == null)
    	{
			return null;
		}
    	return repository.findByCOmNo(cOmNo);
    }
    
    /**
     * 保存
     * 
     * @param e
     * @return
     */
    public TdTbOmM save(TdTbOmM e)
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
    
    public List<TdTbOmM> save(List<TdTbOmM> entities)
    {
        return (List<TdTbOmM>) repository.save(entities);
    }
}
