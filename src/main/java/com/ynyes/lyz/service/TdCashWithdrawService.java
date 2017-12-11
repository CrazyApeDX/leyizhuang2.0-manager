package com.ynyes.lyz.service;


import javax.transaction.Transactional;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.ynyes.lyz.entity.TdCashWithdraw;
import com.ynyes.lyz.repository.TdCashWithdrawRepo;
import com.ynyes.lyz.util.Criteria;
import com.ynyes.lyz.util.Restrictions;

@Service
@Transactional
public class TdCashWithdrawService {
	
	@Autowired
	private TdCashWithdrawRepo tdCashWithdrawRepo;
	
	public TdCashWithdraw save(TdCashWithdraw entity) {
		if (null == entity) {
			return null;
		}
		return tdCashWithdrawRepo.save(entity);
	}
	
	public TdCashWithdraw findOne(Long id) {
        if (null == id) {
            return null;
        }
        return tdCashWithdrawRepo.findOne(id);
    }
	
	public Page<TdCashWithdraw> searchList(String keywords, int size, int page) {
		PageRequest pageRequest = new PageRequest(page, size);
		Criteria<TdCashWithdraw> c = new Criteria<TdCashWithdraw>();
		// 用户名
		if (StringUtils.isNotBlank(keywords)) {
			c.add(Restrictions.or(Restrictions.like("realName", keywords, true),
					Restrictions.like("username", keywords, true),
					Restrictions.like("withdrawNumber", keywords, true),
					Restrictions.like("payeeName", keywords, true)));
		}
		
		c.setOrderByDesc("createTime");
		return tdCashWithdrawRepo.findAll(c, pageRequest);
	}
	
}
