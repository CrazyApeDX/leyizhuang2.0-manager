package com.ynyes.lyz.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ynyes.lyz.entity.TdActivityGiftList;
import com.ynyes.lyz.repository.TdActivityGiftListRepo;

@Service
@Transactional
public class TdActivityGiftListService {

	@Autowired
	private TdActivityGiftListRepo repository;
	
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
    public void delete(TdActivityGiftList e)
    {
        if (null != e)
        {
            repository.delete(e);
        }
    }
    
    public void delete(List<TdActivityGiftList> entities)
    {
        if (null != entities)
        {
            repository.delete(entities);
        }
    }
    /**
     * 保存
     * 
     * @param e
     * @return
     */
    public TdActivityGiftList save(TdActivityGiftList e)
    {
        
        return repository.save(e);
    }
    
    public List<TdActivityGiftList> save(List<TdActivityGiftList> entities)
    {
        return (List<TdActivityGiftList>) repository.save(entities);
    }
}
