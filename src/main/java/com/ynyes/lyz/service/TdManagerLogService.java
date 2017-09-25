package com.ynyes.lyz.service;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ynyes.lyz.entity.TdManagerLog;
import com.ynyes.lyz.repository.TdManagerLogRepo;


/**
 * TdManagerLog 服务类
 * 
 * @author Sharon
 *
 */

@Service
@Transactional
public class TdManagerLogService {
    
    @Autowired
    TdManagerLogRepo repository;
    
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
    public void delete(TdManagerLog e)
    {
        if (null != e)
        {
            repository.delete(e);
        }
    }
    
    public void delete(List<TdManagerLog> entities)
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
    public TdManagerLog findOne(Long id)
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
    public List<TdManagerLog> findAll(Iterable<Long> ids)
    {
        return (List<TdManagerLog>) repository.findAll(ids);
    }
    
    public List<TdManagerLog> findAll()
    {
        return (List<TdManagerLog>) repository.findAll();
    }
    
    public Page<TdManagerLog> findAll(int page, int size)
    {
        PageRequest pageRequest = new PageRequest(page, size, new Sort(Direction.DESC, "createTime"));
        
        return repository.findAll(pageRequest);
    }
    
    public Page<TdManagerLog> findByActionType(String action, int page, int size)
    {
        PageRequest pageRequest = new PageRequest(page, size);
        
        return repository.findByActionTypeIgnoreCaseOrderByCreateTimeDesc(action, pageRequest);
    }
    
    public List<TdManagerLog> findAllOrderBySortIdAsc()
    {
        return (List<TdManagerLog>) repository.findAll(new Sort(Direction.ASC, "sortId"));
    }
    
    /**
     * 保存
     * 
     * @param e
     * @return
     */
    public TdManagerLog save(TdManagerLog e)
    {
        return repository.save(e);
    }
    
    public List<TdManagerLog> save(List<TdManagerLog> entities)
    {
        
        return (List<TdManagerLog>) repository.save(entities);
    }
    
    public void addLog(String type, String detail, HttpServletRequest req)
    {
        TdManagerLog log = new TdManagerLog();
        
        String ip = req.getHeader("x-forwarded-for");
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = req.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknow".equalsIgnoreCase(ip)) {
            ip = req.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = req.getRemoteAddr();
        }
        
        String username = (String) req.getSession().getAttribute("manager");
        
        log.setCreateTime(new Date());
        log.setActionType(type);
        log.setMark(detail);
        log.setUsername(username);
        log.setIp(ip);
        
        repository.save(log);
    }
}
