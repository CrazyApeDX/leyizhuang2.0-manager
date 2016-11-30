package com.ynyes.lyz.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ynyes.lyz.entity.TdSetting;
import com.ynyes.lyz.entity.user.TdUser;
import com.ynyes.lyz.repository.TdSettingRepo;

/**
 * TdSetting 服务类
 * 
 * @author Sharon
 *
 */

@Service
@Transactional
public class TdSettingService {
    
    @Autowired
    TdSettingRepo repository;
    
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
    public void delete(TdSetting e)
    {
        if (null != e)
        {
            repository.delete(e);
        }
    }
    
    public void delete(List<TdSetting> entities)
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
    public TdSetting findOne(Long id)
    {
        if (null == id)
        {
            return null;
        }
        
        return repository.findOne(id);
    }
    
    public TdSetting findTopBy()
    {
        return repository.findTopBy();
    }
    
    /**
     * 查找
     * 
     * @param ids
     * @return
     */
    public List<TdSetting> findAll(Iterable<Long> ids)
    {
        return (List<TdSetting>) repository.findAll(ids);
    }
    
    public List<TdSetting> findAll()
    {
        return (List<TdSetting>) repository.findAll();
    }
    
    public Page<TdSetting> findAllOrderBySortIdAsc(int page, int size)
    {
        PageRequest pageRequest = new PageRequest(page, size, new Sort(Direction.ASC, "sortId"));
        
        return repository.findAll(pageRequest);
    }
    
    public List<TdSetting> findAllOrderBySortIdAsc()
    {
        return (List<TdSetting>) repository.findAll(new Sort(Direction.ASC, "sortId"));
    }
    
    /**
     * 保存
     * 
     * @param e
     * @return
     */
    public TdSetting save(TdSetting e)
    {
        return repository.save(e);
    }
    
    public List<TdSetting> save(List<TdSetting> entities)
    {
        
        return (List<TdSetting>) repository.save(entities);
    }
    /**
     * 判断是否超过最大限制数量
     * @param res 放回的结果集
     * @param user 会员
     * @param operation 0添加 1修改
     * @return true:超过 false:没超过
     */
    public Boolean checkMaxShipping(Map<String, Object> res,TdUser user,Long operation){
    	//判断是否超过限制数量
    	Long maxShipping= this.findTopBy().getMaxShipping();
    	int shippingCount= user.getShippingAddressList().size();
    	if(operation==0L){
    		shippingCount+=1;
    	}
    	//为空或者0 默认不限制
    	if(maxShipping!=null && maxShipping>0){
    		if(shippingCount>maxShipping){
    			res.put("message", "亲，只能添加"+maxShipping+"条收货地址");
    			return true;
    		}
    	}
    	return false;
    }
}
