package com.ynyes.lyz.interfaces.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ynyes.lyz.interfaces.entity.TdTbwBackRecM;
import com.ynyes.lyz.interfaces.repository.TdTbwBackRecMRepo;

@Service
@Transactional
public class TdTbwBackRecMService
{
	@Autowired
    private TdTbwBackRecMRepo repository;
    
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
    public void delete(TdTbwBackRecM e)
    {
        if (null != e)
        {
            repository.delete(e);
        }
    }
    
    public void delete(List<TdTbwBackRecM> entities)
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
    public TdTbwBackRecM findOne(Long id)
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
    public List<TdTbwBackRecM> findAll(Iterable<Long> ids)
    {
        return (List<TdTbwBackRecM>) repository.findAll(ids);
    }
    
    public List<TdTbwBackRecM> findAll()
    {
        return (List<TdTbwBackRecM>) repository.findAll();
    }
    
    public TdTbwBackRecM findByCRecNo(String cRecNo)
    {
    	if (cRecNo == null)
		{
			return null;
		}
    	return repository.findByCRecNo(cRecNo);
    }
    
    /**
     * 保存
     * 
     * @param e
     * @return
     */
    public TdTbwBackRecM save(TdTbwBackRecM e)
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
    
    public List<TdTbwBackRecM> save(List<TdTbwBackRecM> entities)
    {
        return (List<TdTbwBackRecM>) repository.save(entities);
    }
}
