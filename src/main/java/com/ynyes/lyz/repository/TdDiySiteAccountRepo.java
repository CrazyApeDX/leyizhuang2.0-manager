package com.ynyes.lyz.repository;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import com.ynyes.lyz.entity.TdDiySiteAccount;

public interface TdDiySiteAccountRepo extends PagingAndSortingRepository<TdDiySiteAccount, Long>, JpaSpecificationExecutor<TdDiySiteAccount>{

	TdDiySiteAccount findByDiySiteId(Long diySiteId);
	
	@Modifying
	@Query("update TdDiySiteAccount set user_id = :userId, username = :username where id = :id")
	void update(@Param("userId")Long userId, @Param("username")String username, @Param("id")Long id);
}
