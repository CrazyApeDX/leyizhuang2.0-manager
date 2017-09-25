package com.ynyes.lyz.service;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ynyes.lyz.entity.TdManagerRole;
import com.ynyes.lyz.repository.TdManagerRoleRepo;

/**
 * TdManagerRole 服务类
 * 
 * @author Sharon
 *
 */

@Service
@Transactional
public class TdManagerRoleService {
    
    @Autowired
    TdManagerRoleRepo repository;
    
    @Autowired
    TdManagerPermissionService tdManagerPermissionService;
    
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
    public void delete(TdManagerRole e)
    {
        if (null != e)
        {
            repository.delete(e);
        }
    }
    
    public void delete(List<TdManagerRole> entities)
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
    public TdManagerRole findOne(Long id)
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
    public List<TdManagerRole> findAll(Iterable<Long> ids)
    {
        return (List<TdManagerRole>) repository.findAll(ids);
    }
    
    public List<TdManagerRole> findAll()
    {
        return (List<TdManagerRole>) repository.findAll();
    }
    
    public List<TdManagerRole> findByRoleTitle(String roleTitle)
    {
    	if (StringUtils.isBlank(roleTitle))
    	{
			return null;
		}
    	return repository.findByTitle(roleTitle);
	}
    
    public Page<TdManagerRole> findAll(int page, int size)
    {
        PageRequest pageRequest = new PageRequest(page, size, new Sort(Direction.DESC, "id"));
        
        return repository.findAll(pageRequest);
    }
    
    public List<TdManagerRole> findAllOrderBySortIdAsc()
    {
        return (List<TdManagerRole>) repository.findAll(new Sort(Direction.ASC, "sortId"));
    }
    
    /**
     * 保存
     * 
     * @param e
     * @return
     */
    public TdManagerRole save(TdManagerRole e)
    {
        // 修改时
        if (null != e.getPermissionList())
        {
            e.setTotalPermission(e.getPermissionList().size());
        }
        else
        {
            e.setTotalPermission(0);
        }       
        
        tdManagerPermissionService.save(e.getPermissionList());

        return repository.save(e);
    }
    
    public List<TdManagerRole> save(List<TdManagerRole> entities)
    {
        return (List<TdManagerRole>) repository.save(entities);
    }
    /**
	 * 查询超级管理员货非超级管理员
	 * @param isys 是否是超级管理员
	 * @return结果列表
	 * @author zp
	 */
	public List<TdManagerRole> findByIsSys(Boolean isys){
		return repository.findByIsSys(isys);
	}
}
