package com.ynyes.lyz.service;

import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ynyes.lyz.entity.TdGoods;
import com.ynyes.lyz.entity.TdOrder;
import com.ynyes.lyz.entity.TdOrderGoods;
import com.ynyes.lyz.entity.TdProductCategory;
import com.ynyes.lyz.repository.TdOrderGoodsRepo;


/**
 * TdOrderGoods 服务类
 * 
 * @author Sharon
 *
 */

@Service
@Transactional
public class TdOrderGoodsService {
    
    @Autowired
    TdOrderGoodsRepo repository;
    @Autowired
    TdCartGoodsService tdCartGoodsService;
    @Autowired
    TdGoodsService tdGoodsService;
    @Autowired
    TdProductCategoryService tdProductCategoryService;
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
    public void delete(TdOrderGoods e)
    {
        if (null != e)
        {
            repository.delete(e);
        }
    }
    
    public void delete(List<TdOrderGoods> entities)
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
    public TdOrderGoods findOne(Long id)
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
    public List<TdOrderGoods> findAll(Iterable<Long> ids)
    {
        return (List<TdOrderGoods>) repository.findAll(ids);
    }
    
    public List<TdOrderGoods> findAll()
    {
        return (List<TdOrderGoods>) repository.findAll();
    }
    
    public Page<TdOrderGoods> findAllOrderBySortIdAsc(int page, int size)
    {
        PageRequest pageRequest = new PageRequest(page, size, new Sort(Direction.ASC, "sortId"));
        
        return repository.findAll(pageRequest);
    }
    
    /**
     * 保存
     * 
     * @param e
     * @return
     */
    public TdOrderGoods save(TdOrderGoods e)
    {
        
        return repository.save(e);
    }
    
    public List<TdOrderGoods> save(List<TdOrderGoods> entities)
    {
        
        return (List<TdOrderGoods>) repository.save(entities);
    }
    
    /**
     * 修改订单商品归属活动
     * @param order 订单
     * @param cost 活动所需要的商品及其数量的列表
     * @param activityId 活动id
     * @param min 参加活动的次数
     * @param type 类型1活动 2小辅料活动 3满金额赠送
     * @author zp
     */
    public void updateOrderGoodsActivity(TdOrder order,Map<Long, Long> cost,Long activityId,Long min,Long type){
    	// 获取用户的已选
    	List<TdOrderGoods> orderGoodsList= order.getOrderGoodsList();
    	//循环所有商品
    	if(orderGoodsList!=null && orderGoodsList.size()>0){
    		for (TdOrderGoods orderGood : orderGoodsList) {
    			//判断是否是要修改的商品
    			if(type.equals(1L)){ //普通活动
    				//判断商品id是否相等
    				if(cost.containsKey(orderGood.getGoodsId())){
    					//记录活动id
    					String activityIds=orderGood.getActivityId();
    					if(StringUtils.isNotBlank(activityIds)){
    						if(!activityIds.contains(activityId+"_")){
    							orderGood.setActivityId(activityIds+","+activityId.toString()+"_"+cost.get(orderGood.getGoodsId())*min);
    						}
    					}else{
    						orderGood.setActivityId(""+activityId.toString()+"_"+cost.get(orderGood.getGoodsId())*min);
    					}

    				}
    			}else if(type.equals(2L)){  //小辅料活动
    				//判断商品类型是否相等
    				TdGoods goods = tdGoodsService.findOne(orderGood.getGoodsId());
    				// 获取已选商品的分类id
    				Long cateId = goods.getCategoryId();
    				// 获取指定的分类
    				TdProductCategory category = tdProductCategoryService.findOne(cateId);
    				// 获取指定分类的父类
    				if (null != category) {
    					Long parentId = category.getParentId();
    					//判断商品类型是否存在
    					if(cost.containsKey(parentId)){
    						//记录活动id
    						String activityIds=orderGood.getActivityId();
    						if(StringUtils.isNotBlank(activityIds)){
    							if(!activityIds.contains(activityId+"_")){
    								orderGood.setActivityId(activityIds+",A"+activityId.toString()+"_"+orderGood.getQuantity());
    							}
    						}else{
    							orderGood.setActivityId("A"+activityId.toString()+"_"+orderGood.getQuantity());
    						}

    					}
    				}
    			}else if(type.equals(3L)){ //满金额赠送
    				//判断商品id是否相等
    				if(cost.containsKey(orderGood.getGoodsId())){
    					//记录活动id
    					String activityIds=orderGood.getActivityId();
    					if(StringUtils.isNotBlank(activityIds)){
    						if(!activityIds.contains(activityId+"_")){
    							orderGood.setActivityId(activityIds+",M"+activityId.toString()+"_"+orderGood.getQuantity());
    						}
    					}else{
    						orderGood.setActivityId("M"+activityId.toString()+"_"+orderGood.getQuantity());
    					}
    				}
    			}
    			
			}
    	}
    	//保存
    	order.setOrderGoodsList(orderGoodsList);
    }
}
