package com.ynyes.lyz.interfaces.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ynyes.lyz.interfaces.entity.TdReturnTimeInf;
import com.ynyes.lyz.interfaces.repository.TdReturnTimeInfRepo;


/**
 * TdOrderInf 服务类
 * 
 * @author Sharon
 *
 */

@Service
@Transactional
public class TdReturnTimeInfService {
    
    @Autowired
    private TdReturnTimeInfRepo repository;
    
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
    public void delete(TdReturnTimeInf e)
    {
        if (null != e)
        {
            repository.delete(e);
        }
    }
    
    public void delete(List<TdReturnTimeInf> entities)
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
    public TdReturnTimeInf findOne(Long id)
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
    public List<TdReturnTimeInf> findAll(Iterable<Long> ids)
    {
        return (List<TdReturnTimeInf>) repository.findAll(ids);
    }
    
    public List<TdReturnTimeInf> findAll()
    {
        return (List<TdReturnTimeInf>) repository.findAll();
    }
//    public TdReturnOrderInf findByOrderNumber(String orderNumber)
//    {
//    	return repository.findByOrderNumber(orderNumber);
//    }
    
    public List<TdReturnTimeInf> findByReturnNumber(String returnNumber)
    {
    	if (returnNumber == null)
    	{
			return null;
		}
    	return repository.findByReturnNumber(returnNumber);
    }
    
    /**
     * 保存
     * 
     * @param e
     * @return
     */
    public TdReturnTimeInf save(TdReturnTimeInf e)
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
    
    public List<TdReturnTimeInf> save(List<TdReturnTimeInf> entities)
    {
        return (List<TdReturnTimeInf>) repository.save(entities);
    }

	public List<TdReturnTimeInf> findBySendFlagOrSendFlagIsNull(Integer sendFlag) {
		
		return repository.findBySendFlagOrSendFlagIsNull(sendFlag);
	}
    
}
