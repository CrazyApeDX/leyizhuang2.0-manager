package com.ynyes.lyz.repository;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.ynyes.lyz.entity.TdMemberRating;

@Repository
public interface TdMemberRatingRepo extends PagingAndSortingRepository<TdMemberRating, Long>, JpaSpecificationExecutor<TdMemberRating>{

	TdMemberRating findById(Long id);
}
