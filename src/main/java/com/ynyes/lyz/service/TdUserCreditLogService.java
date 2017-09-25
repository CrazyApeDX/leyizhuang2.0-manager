package com.ynyes.lyz.service;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ynyes.lyz.entity.TdOrder;
import com.ynyes.lyz.entity.user.CreditChangeType;
import com.ynyes.lyz.entity.user.TdUser;
import com.ynyes.lyz.entity.user.TdUserCreditLog;
import com.ynyes.lyz.repository.TdUserCreditLogRepo;

@Service
@Transactional
public class TdUserCreditLogService {

	@Autowired
	private TdUserCreditLogRepo repository;

	public TdUserCreditLog save(TdUserCreditLog entity) {
		if (null == entity) {
			return null;
		}
		return repository.save(entity);
	}

	public void delete(Long id) {
		if (null != id) {
			repository.delete(id);
		}
	}

	public TdUserCreditLog findOne(Long id) {
		if (null == id) {
			return null;
		}
		return repository.findOne(id);
	}

	public TdUserCreditLog findAll() {
		return (TdUserCreditLog) repository.findAll();
	}

	public Page<TdUserCreditLog> findBySellerIdOrderByChangeTimeDesc(Long sellerId, Integer page, Integer size) {
		if (null == sellerId) {
			return null;
		}
		PageRequest pageRequest = new PageRequest(page, size);
		return repository.findBySellerIdOrderByChangeTimeDesc(sellerId, pageRequest);
	}

	public TdUserCreditLog createLogByCondition(CreditChangeType type, TdUser seller, TdOrder order) {
		TdUserCreditLog log = new TdUserCreditLog();
		log.setAfterChange(seller.getCredit());
		log.setChangeTime(new Date());
		log.setReferenceNumber(order.getOrderNumber());
		log.setSellerId(seller.getId());
		log.setType(type);
		switch (type) {
		case CONSUME:
			log.setBrforeChange(seller.getCredit() + order.getTotalPrice());
			log.setChangeValue(-1 * order.getTotalPrice());
			break;
		case CANCEL:
			log.setBrforeChange(seller.getCredit() - order.getTotalPrice());
			log.setChangeValue(order.getTotalPrice());
			break;
		case REJECT:
			log.setBrforeChange(seller.getCredit() - order.getTotalPrice());
			log.setChangeValue(order.getTotalPrice());
			break;
		case REPAY:
			log.setBrforeChange(seller.getCredit() - order.getTotalPrice());
			log.setChangeValue(order.getTotalPrice());
			break;
		case INIT:
			// do nothing
			break;
		}
		log = this.save(log);
		return log;
	}

	public TdUserCreditLog createLogByCondition(CreditChangeType type, TdUser seller, String orderNumber,
			Double amount) {
		TdUserCreditLog log = new TdUserCreditLog();
		log.setAfterChange(seller.getCredit());
		log.setChangeTime(new Date());
		log.setReferenceNumber(orderNumber);
		log.setSellerId(seller.getId());
		log.setType(type);
		switch (type) {
		case CONSUME:
			log.setBrforeChange(seller.getCredit() + amount);
			log.setChangeValue(-1 * amount);
			break;
		case CANCEL:
			log.setBrforeChange(seller.getCredit() - amount);
			log.setChangeValue(amount);
			break;
		case REJECT:
			log.setBrforeChange(seller.getCredit() - amount);
			log.setChangeValue(amount);
			break;
		case REPAY:
			log.setBrforeChange(seller.getCredit() - amount);
			log.setChangeValue(amount);
			break;
		case INIT:
			// do nothing
			break;
		}
		log = this.save(log);
		return log;
	}
}
