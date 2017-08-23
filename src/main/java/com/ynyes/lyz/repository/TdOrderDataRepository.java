package com.ynyes.lyz.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ynyes.lyz.entity.TdOrderData;

@Repository
public interface TdOrderDataRepository extends JpaRepository<TdOrderData, Long> {

	TdOrderData findByMainOrderNumber(String mainOrderNumber);
}
