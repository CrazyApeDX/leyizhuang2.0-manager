package com.ynyes.lyz.interfaces.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ynyes.lyz.interfaces.entity.TdTbwWasted;
import com.ynyes.lyz.interfaces.repository.TdTbwWastedRepo;

@Service
@Transactional
public class TdTbwWastedService
{
	@Autowired
    private TdTbwWastedRepo repository;
    
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
    public void delete(TdTbwWasted e)
    {
        if (null != e)
        {
            repository.delete(e);
        }
    }
    
    public void delete(List<TdTbwWasted> entities)
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
    public TdTbwWasted findOne(Long id)
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
    public List<TdTbwWasted> findAll(Iterable<Long> ids)
    {
        return (List<TdTbwWasted>) repository.findAll(ids);
    }
    
    public List<TdTbwWasted> findAll()
    {
        return (List<TdTbwWasted>) repository.findAll();
    }
    
    public TdTbwWasted findByCWasteNo(String cWasteNo)
    {
    	if (cWasteNo == null )
    	{
			return null;
		}
    	return repository.findByCWasteNo(cWasteNo);
    }
    
    public TdTbwWasted findByCWasteNoAndCWasteId(String cWasteNo,Long cWasteId)
    {
    	if (cWasteId == null || cWasteNo == null)
    	{
			return null;
		}
    	return repository.findByCWasteNoAndCWasteId(cWasteNo, cWasteId);
    }
    
    /**
     * 保存
     * 
     * @param e
     * @return
     */
    public TdTbwWasted save(TdTbwWasted e)
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
    
    public List<TdTbwWasted> save(List<TdTbwWasted> entities)
    {
        return (List<TdTbwWasted>) repository.save(entities);
    }
}
