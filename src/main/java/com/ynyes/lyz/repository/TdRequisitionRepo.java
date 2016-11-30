package com.ynyes.lyz.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.ynyes.lyz.entity.TdRequisition;

public interface TdRequisitionRepo extends PagingAndSortingRepository<TdRequisition, Long>, JpaSpecificationExecutor<TdRequisition>{
//	Page<TdRequisition> findByDiySiteTitleContainingOrRemarkInfoContainingOrManagerRemarkInfoContainingOrRequisitionNumberContaining(String keyword,String keyword1,String keyword2,String keyword3,Pageable page );
//	
//	Page<TdRequisition> findByDiySiteTitleContainingAndTypeIdOrRemarkInfoContainingAndTypeIdOrManagerRemarkInfoContainingAndTypeIdOrRequisitionNumberContainingAndTypeId(String keyword, Long typeId, String keyword1, Long typeId1, String keyword2, Long typeId2, String keyword3, Long typeId3, Pageable page );
	Page<TdRequisition> findByTypeId(Long typeId, Pageable page);
	TdRequisition findByOrderNumber(String orderNumber);
//	TdRequisition findBySubOrderNumber(String subOrderNumber);
}
