package com.ynyes.lyz.interfaces.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ynyes.lyz.interfaces.entity.TdCashReciptInf;
import com.ynyes.lyz.interfaces.repository.TdCashReciptInfRepo;


/**
 * TdOrderInf 服务类
 * 
 * @author Sharon
 *
 */

@Service
@Transactional
public class TdCashReciptInfService {
    
    @Autowired
    private TdCashReciptInfRepo repository;
    
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
    public void delete(TdCashReciptInf e)
    {
        if (null != e)
        {
            repository.delete(e);
        }
    }
    
    public void delete(List<TdCashReciptInf> entities)
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
    public TdCashReciptInf findOne(Long id)
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
    public List<TdCashReciptInf> findAll(Iterable<Long> ids)
    {
        return (List<TdCashReciptInf>) repository.findAll(ids);
    }
    
    public List<TdCashReciptInf> findAll()
    {
        return (List<TdCashReciptInf>) repository.findAll();
    }
    
    public List<TdCashReciptInf> findByOrderHeaderId(Long headerId)
    {
    	return repository.findByOrderHeaderId(headerId);
    }
    public List<TdCashReciptInf> findByOrderNumber(String orderNumber)
    {
    	if (orderNumber == null)
    	{
			return null;
		}
    	return repository.findByOrderNumber(orderNumber);
    }
    
    /**
     * 保存
     * 
     * @param e
     * @return
     */
    public TdCashReciptInf save(TdCashReciptInf e)
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
    
    public List<TdCashReciptInf> save(List<TdCashReciptInf> entities)
    {
        return (List<TdCashReciptInf>) repository.save(entities);
    }
    
    // 查询未成功的充值记录
    public List<TdCashReciptInf> findByReceiptTypeAndSendFlag(String reciptType, Integer flag) {
    	if (null == reciptType || null == flag) {
    		return null;
    	}
    	return repository.findByReceiptTypeAndSendFlag(reciptType, flag);
    }
    
}
