package com.ynyes.lyz.service;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ynyes.lyz.entity.qrcode.QrcodeRegisterLog;
import com.ynyes.lyz.repository.qrcode.QrcodeRegisterLogRepo;

@Service
@Transactional
public class QrcodeRegisterLogService {

	@Autowired
	private QrcodeRegisterLogRepo repository;
	
	public QrcodeRegisterLog save(QrcodeRegisterLog e) {
		if (null == e) {
			return null;
		}
		return repository.save(e);
	}
	
	public void delete(Long id) {
		if (null != id) {
			repository.delete(id);
		}
	}
	
	public QrcodeRegisterLog findOne(Long id) {
		if (null == id) {
			return null;
		}
		return repository.findOne(id);
	}
}
