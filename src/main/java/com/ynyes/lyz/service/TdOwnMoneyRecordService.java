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

import com.ynyes.lyz.entity.TdOwnMoneyRecord;
import com.ynyes.lyz.repository.TdOwnMoneyRecordRepo;
import com.ynyes.lyz.util.Criteria;
import com.ynyes.lyz.util.Restrictions;


/**
 * TdOwnMoneyRecord 服务类
 * 
 * @author Sharon
 *
 */

@Service
@Transactional
public class TdOwnMoneyRecordService {
    
    @Autowired
    TdOwnMoneyRecordRepo repository;
    
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
    public void delete(TdOwnMoneyRecord e)
    {
        if (null != e)
        {
            repository.delete(e);
        }
    }
    
    public void delete(List<TdOwnMoneyRecord> entities)
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
    public TdOwnMoneyRecord findOne(Long id)
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
    public List<TdOwnMoneyRecord> findAll(Iterable<Long> ids)
    {
        return (List<TdOwnMoneyRecord>) repository.findAll(ids);
    }
    
    public List<TdOwnMoneyRecord> findByOrderNumberIgnoreCase(String orderNumber)
    {
    	if (null == orderNumber)
    	{
    		return null;
    	}
    	
        return repository.findByOrderNumberIgnoreCase(orderNumber);
    }
    
    public List<TdOwnMoneyRecord> findAll()
    {
        return (List<TdOwnMoneyRecord>) repository.findAll();
    }
    
    public Page<TdOwnMoneyRecord> findAllOrderBySortIdAsc(int page, int size)
    {
        PageRequest pageRequest = new PageRequest(page, size, new Sort(Direction.DESC, "createTime"));
        
        return repository.findByIsOwnIsNull(pageRequest);
    }
    
    public Page<TdOwnMoneyRecord> findByDiyCodeAndIsEnableOrderByIdDesc(String diyCode,Boolean isEnable ,int page, int size)
    {
    	PageRequest pageRequest = new PageRequest(page, size, new Sort(Direction.DESC, "createTime"));
    	if (diyCode == null || diyCode.equalsIgnoreCase(""))
    	{
			return repository.findByIsEnableAndIsOwnIsNullOrderByIdDesc(isEnable, pageRequest);
		}
    	return repository.findByDiyCodeAndIsEnableAndIsOwnIsNullOrderByIdDesc(diyCode, isEnable, pageRequest);
    }
    public Page<TdOwnMoneyRecord> findByDiyCodeAndIsPayedOrderByIdDesc(String diyCode,Boolean isPayed ,int page, int size)
	{
		PageRequest pageRequest = new PageRequest(page, size, new Sort(Direction.DESC, "createTime"));
		
		if (diyCode == null || diyCode.equalsIgnoreCase(""))
    	{
			return repository.findByIsPayedAndIsOwnIsNullOrderByIdDesc(isPayed, pageRequest);
		}
		return repository.findByDiyCodeAndIsPayedAndIsOwnIsNullOrderByIdDesc(diyCode, isPayed, pageRequest);
	}
    
    public Page<TdOwnMoneyRecord> findByIsEnableOrderByIdDesc(Boolean isEnale ,int page, int size)
    {
    	PageRequest pageRequest = new PageRequest(page, size, new Sort(Direction.DESC, "createTime"));
    	return repository.findByIsEnableAndIsOwnIsNullOrderByIdDesc(isEnale, pageRequest);
    }
    
    public Page<TdOwnMoneyRecord> findByIsPayedOrderByIdDesc(Boolean isPayed,int page, int size)
    {
    	PageRequest pageRequest = new PageRequest(page, size, new Sort(Direction.DESC, "createTime"));
    	return repository.findByIsPayedAndIsOwnIsNullOrderByIdDesc(isPayed, pageRequest);
    }
    
    
    public Page<TdOwnMoneyRecord> findByDiyCodeOrderByIdDesc(String diyCode ,int page, int size)
    {
        PageRequest pageRequest = new PageRequest(page, size, new Sort(Direction.DESC, "createTime"));
        
        return repository.findByDiyCodeAndIsOwnIsNullOrderByIdDesc(diyCode, pageRequest);
    }
    
    
    
    public List<TdOwnMoneyRecord> findAllOrderBySortIdAsc()
    {
        return (List<TdOwnMoneyRecord>) repository.findAll(new Sort(Direction.ASC, "sortId"));
    }
    
    /**
     * 保存
     * 
     * @param e
     * @return
     */
    public TdOwnMoneyRecord save(TdOwnMoneyRecord e)
    {
        if (null == e.getCreateTime())
        {
            e.setCreateTime(new Date());
        }
        
        return repository.save(e);
    }
    
    public List<TdOwnMoneyRecord> save(List<TdOwnMoneyRecord> entities)
    {
        
        return (List<TdOwnMoneyRecord>) repository.save(entities);
    }
    
    /**
     * 欠款审核 查询
     * @param diyCode 门店编号
     * @param orderNumber 订单号
     * @param isPayed 是否还清
     * @param ispassed 是否通过
     * @param size 每页数量
     * @param page 当前页
     * @return 结果集
     * @author zp
     */
	public Page<TdOwnMoneyRecord> searchOwnList(String diyCode,String orderNumber,Long isEnable,Long isPayed,Long ispassed,List<String> roleDiyCodes,List<String> cityDiyCodes,int size,int page){
		PageRequest pageRequest = new PageRequest(page, size);
		Criteria<TdOwnMoneyRecord> c = new Criteria<TdOwnMoneyRecord>();
		
		if(null!=diyCode){
			c.add(Restrictions.eq("diyCode", diyCode, true));
		}
		if(null!=orderNumber){
			c.add(Restrictions.like("orderNumber", orderNumber, true));
		}
		if(null!=isEnable){
			c.add(Restrictions.eq("isEnable", isEnable, true));
		}
		if(null!=isPayed){
			c.add(Restrictions.eq("isPayed", isPayed, true));
		}
		if(null!=ispassed){
			c.add(Restrictions.eq("ispassed", ispassed, true));
		}
		if(null!=roleDiyCodes && roleDiyCodes.size()>0){
			c.add(Restrictions.in("diyCode", roleDiyCodes, true));
		}
		if(null!=cityDiyCodes && cityDiyCodes.size()>0){
			c.add(Restrictions.in("diyCode", cityDiyCodes, true));
		}
		c.add(Restrictions.eq("isOwn",true, true));
		
		c.setOrderByDesc("createTime");
		return repository.findAll(c,pageRequest);
	}
	
	public List<TdOwnMoneyRecord> findByOrderNumberAndIsOwnAndIsPayed(String mainOrderNumber, Boolean isOwn, Boolean isPayed) {
		if (null == mainOrderNumber || null == isOwn || null == isPayed) {
			return null;
		}
		return repository.findByOrderNumberAndIsOwnAndIsPayed(mainOrderNumber, isOwn, isPayed);
	}

	public List<String> findMissedOrderNumbers(Date beginDate) {
		return repository.findMissedOrderNumbers(beginDate);
	}

	public List<TdOwnMoneyRecord> findByOrderNumberIn(List<String> missedOrderNumber) {
		// TODO Auto-generated method stub
		return repository.findByOrderNumberIn(missedOrderNumber);
	}
}
