package com.ynyes.lyz.repository;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.ynyes.lyz.entity.TdActiveRedPacket;
import com.ynyes.lyz.entity.TdActivity;

public interface TdActiveRedPacketRepo 
 				extends PagingAndSortingRepository<TdActiveRedPacket, Long>, JpaSpecificationExecutor<TdActiveRedPacket>  {

}
