package com.ynyes.fitment.foundation.service.impl;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ynyes.fitment.core.service.PageableService;
import com.ynyes.fitment.foundation.entity.FitCompany;
import com.ynyes.fitment.foundation.entity.FitCreditChangeLog;
import com.ynyes.fitment.foundation.repo.FitCompanyRepo;
import com.ynyes.fitment.foundation.service.FitCompanyService;
import com.ynyes.lyz.util.Criteria;
import com.ynyes.lyz.util.Restrictions;

@Service
@Transactional
public class FitCompanyServiceImpl extends PageableService implements FitCompanyService {

	@Autowired
	private FitCompanyRepo fitCompanyRepo;

	@Override
	public FitCompany save(FitCompany fitCompany) throws Exception {
		if (null == fitCompany) {
			return null;
		}
		return this.fitCompanyRepo.save(fitCompany);
	}

	@Override
	public void delete(Long id) throws Exception {
		if (null != id) {
			this.fitCompanyRepo.delete(id);
		}
	}

	@Override
	public FitCompany findOne(Long companyId) {
		return this.fitCompanyRepo.findOne(companyId);
	}

	@Override
	public List<FitCompany> findAll() throws Exception {
		return this.fitCompanyRepo.findAll();
	}

	@Override
	public Page<FitCompany> findAll(Integer page, Integer size) throws Exception {
		return this.fitCompanyRepo.findAll(this.initPage(page, size));
	}

	@Override
	public Boolean validateRepeatCompanyByName(String name, Long id) throws Exception {
		Long count = this.fitCompanyRepo.countByNameAndIdNot(name, id);
		return (count > 0);
	}

	@Override
	public Boolean validateRepeatCompanyByCode(String code, Long id) throws Exception {
		Long count = this.fitCompanyRepo.countByCodeAndIdNot(code, id);
		return (count > 0);
	}

	@Override
	public FitCompany findByCode(String code) {
		if (null == code) {
			return null;
		} else {
			return this.fitCompanyRepo.findByCode(code);
		}
	}

	@Override
	public List<FitCompany> findFitCompanyBySobId(List<Long> sobIdList) {
		if(null != sobIdList && sobIdList.size()>0 ){
			return this.fitCompanyRepo.findFitCompanyBySobId(sobIdList);
		}
		return null;
	}
	
	@Override
	public List<FitCompany> findBySobId(Long sobId) {
		if(null != sobId){
			return this.fitCompanyRepo.findBySobId(sobId);
		}
		return null;
	}
	/**
	 * ��ѯ������ҳ  == 2017-07-13 == panjie == ���� �����ơ��������롯�����Ƿ񶳽ᡯ�� ��ѯ����
	 */
	@Override
	public Page<FitCompany> findAllAddConditionDeliveryType(Integer page, Integer size, String keyWords,
			String frozen) throws Exception {
		PageRequest pageRequest = new PageRequest(page, size);
		Criteria<FitCompany> c = new Criteria<FitCompany>();
		
		if (null != keyWords && !"".equals(keyWords)) {
			//ȥ��ǰ��ո�
			keyWords = keyWords.trim();
			c.add(Restrictions.or(Restrictions.like("name", keyWords, true),Restrictions.like("code", keyWords, true)));
		}
		
		if (null != frozen && !"".equals(frozen)) {
			boolean b = true;
			if(frozen.equals("0")){
				b = false;
			}
			c.add(Restrictions.eq("frozen", b, true));
		}
		c.setOrderByDesc("createTime");
		return this.fitCompanyRepo.findAll(c,pageRequest);
	}

	@Override
	public Page<FitCompany> findCompany(Integer page, Integer size, String keywords) throws Exception {
		PageRequest pageRequest = new PageRequest(page, size);
		Criteria<FitCompany> c = new Criteria<FitCompany>();
		if (null != keywords && !keywords.equalsIgnoreCase("")) {
			c.add(Restrictions.or(Restrictions.like("companyCode",keywords, true),Restrictions.like("companyTitle", keywords, true)));
		}
		c.setOrderByDesc("changeTime");
		return fitCompanyRepo.findAll(c,pageRequest);
	}
}
