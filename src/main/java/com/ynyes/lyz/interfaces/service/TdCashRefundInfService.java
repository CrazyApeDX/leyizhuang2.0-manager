package com.ynyes.lyz.interfaces.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ynyes.lyz.interfaces.entity.TdCashRefundInf;
import com.ynyes.lyz.interfaces.repository.TdCashRefundInfRepo;


/**
 * TdOrderInf 服务类
 * 
 * @author Sharon
 *
 */

@Service
@Transactional
public class TdCashRefundInfService {
    
    @Autowired
    private TdCashRefundInfRepo repository;
    
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
    public void delete(TdCashRefundInf e)
    {
        if (null != e)
        {
            repository.delete(e);
        }
    }
    
    public void delete(List<TdCashRefundInf> entities)
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
    public TdCashRefundInf findOne(Long id)
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
    public List<TdCashRefundInf> findAll(Iterable<Long> ids)
    {
        return (List<TdCashRefundInf>) repository.findAll(ids);
    }
    
    public List<TdCashRefundInf> findAll()
    {
        return (List<TdCashRefundInf>) repository.findAll();
    }
    public List<TdCashRefundInf> findByOrderHeaderId(Long orderHeaderId)
    {
    	if (orderHeaderId == null)
    	{
			return null;
		}
    	return repository.findByOrderHeaderId(orderHeaderId);
    }
    public List<TdCashRefundInf> findByReturnNumber(String returnNumber)
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
    public TdCashRefundInf save(TdCashRefundInf e)
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
    
    public List<TdCashRefundInf> save(List<TdCashRefundInf> entities)
    {
        return (List<TdCashRefundInf>) repository.save(entities);
    }
    
    // 查询传递失败的提现记录
 	public List<TdCashRefundInf> findByRefundTypeAndSendFlag(String type, Integer flag) {
 		if (null == type || null == flag) {
 			return null;
 		}
 		return repository.findByRefundTypeAndSendFlag(type, flag);
 	}

	public List<TdCashRefundInf> findBySendFlagOrSendFlagIsNull(Integer sendFlag) {
		
		return repository.findBySendFlagOrSendFlagIsNull(sendFlag);
	}
    
}
