package com.ynyes.lyz.interfaces.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ynyes.lyz.interfaces.entity.TdTbOmD;
import com.ynyes.lyz.interfaces.repository.TdTbOmDRepo;

@Service
@Transactional
public class TdTbOmDService
{
	@Autowired
    private TdTbOmDRepo repository;
    
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
    public void delete(TdTbOmD e)
    {
        if (null != e)
        {
            repository.delete(e);
        }
    }
    
    public void delete(List<TdTbOmD> entities)
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
    public TdTbOmD findOne(Long id)
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
    public List<TdTbOmD> findAll(Iterable<Long> ids)
    {
        return (List<TdTbOmD>) repository.findAll(ids);
    }
    
    public List<TdTbOmD> findByCOmNo(String COmNo)
    {
    	if (COmNo == null)
    	{
			return null;
		}
    	return repository.findByCOmNo(COmNo);
    }
    
    /**
     * 保存
     * 
     * @param e
     * @return
     */
    public TdTbOmD save(TdTbOmD e)
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
    
    public List<TdTbOmD> save(List<TdTbOmD> entities)
    {
        return (List<TdTbOmD>) repository.save(entities);
    }
}
