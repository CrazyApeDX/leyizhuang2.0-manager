package com.ynyes.lyz.repository;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.ynyes.lyz.entity.TdEmployee;

public interface TdEmployeeRepo extends PagingAndSortingRepository<TdEmployee, Long>, JpaSpecificationExecutor<TdEmployee>{

	TdEmployee findByCityNameAndPhoneAndIsEnableTrue(String cityName,String phone);
}
