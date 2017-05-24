package com.ynyes.lyz.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ynyes.lyz.entity.TdDiySiteAccount;
import com.ynyes.lyz.repository.TdDiySiteAccountRepo;

@Service
@Transactional
public class TdDiySiteAccountService {
	
	@Autowired
	private TdDiySiteAccountRepo tdDiySiteAccountRepo;
	
	public TdDiySiteAccount findByDiySiteId(Long diySiteId){
		return tdDiySiteAccountRepo.findByDiySiteId(diySiteId);
	}
	
	public TdDiySiteAccount save(TdDiySiteAccount tdDiySiteAccount){
		TdDiySiteAccount diySiteAccount = tdDiySiteAccountRepo.findByDiySiteId(tdDiySiteAccount.getDiySiteId());
		if (null == diySiteAccount) {
			tdDiySiteAccountRepo.save(tdDiySiteAccount);
		}else {
//			tdDiySiteAccountRepo.delete(diySiteAccount.getId());
//			tdDiySiteAccountRepo.save(tdDiySiteAccount);
			
			tdDiySiteAccountRepo.update(tdDiySiteAccount.getUserId(),tdDiySiteAccount.getUsername(),diySiteAccount.getId());
		}
		
		return tdDiySiteAccount;
	}

}
