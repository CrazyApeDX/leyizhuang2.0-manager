package com.ynyes.lyz.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ynyes.lyz.entity.TdManager;
import com.ynyes.lyz.entity.user.TdUser;
import com.ynyes.lyz.repository.TdManagerRepo;


/**
 * TdManager 服务类
 * 
 * @author Sharon
 *
 */

@Service
@Transactional
public class TdManagerService {
    
    @Autowired
    TdManagerRepo repository;
    
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
    public void delete(TdManager e)
    {
        if (null != e)
        {
            repository.delete(e);
        }
    }
    
    public void delete(List<TdManager> entities)
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
    public TdManager findOne(Long id)
    {
        if (null == id)
        {
            return null;
        }
        
        return repository.findOne(id);
    }
    
    public TdManager findByUsernameAndIsEnableTrue(String username)
    {
        if (null == username)
        {
            return null;
        }
        
        return repository.findByUsernameAndIsEnableTrue(username);
    }
    
    public List<TdManager> findByRoleId(Long roleId)
    {
    	if (roleId == null)
    	{
			return null;
		}
    	
    	return repository.findByRoleId(roleId);
    }
    
    /**
     * 查找
     * 
     * @param ids
     * @return
     */
    public List<TdManager> findAll(Iterable<Long> ids)
    {
        return (List<TdManager>) repository.findAll(ids);
    }
    
    public List<TdManager> findAll()
    {
        return (List<TdManager>) repository.findAll();
    }
    
    public Page<TdManager> findAllOrderBySortIdAsc(int page, int size)
    {
        PageRequest pageRequest = new PageRequest(page, size, new Sort(Direction.ASC, "sortId"));
        
        return repository.findAll(pageRequest);
    }
    
    public Page<TdManager> searchAndOrderByIdDesc(String keywords, Integer page, Integer size) {
		PageRequest pageRequest = new PageRequest(page, size);

		return repository.findByUsernameContainingOrRealNameContainingOrderByIdDesc(keywords, keywords, pageRequest);
	}
    
    public List<TdManager> findAllOrderBySortIdAsc()
    {
        return (List<TdManager>) repository.findAll(new Sort(Direction.ASC, "sortId"));
    }
    
    /**
     * 保存
     * 
     * @param e
     * @return
     */
    public TdManager save(TdManager e)
    {
        if (null == e.getCreateTime())
        {
            e.setCreateTime(new Date());
        }
        
        return repository.save(e);
    }
    
    public List<TdManager> save(List<TdManager> entities)
    {
        
        return (List<TdManager>) repository.save(entities);
    }
}
