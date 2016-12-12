package com.ynyes.lyz.repository.qrcode;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.ynyes.lyz.entity.qrcode.QrcodeRegisterLog;

@Repository
public interface QrcodeRegisterLogRepo
		extends PagingAndSortingRepository<QrcodeRegisterLog, Long>, JpaSpecificationExecutor<QrcodeRegisterLog> {

}
