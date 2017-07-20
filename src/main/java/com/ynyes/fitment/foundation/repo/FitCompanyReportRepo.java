package com.ynyes.fitment.foundation.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.ynyes.fitment.foundation.entity.FitCompanyReport;

@Repository
public interface FitCompanyReportRepo extends PagingAndSortingRepository<FitCompanyReport, Long>,
JpaSpecificationExecutor<FitCompanyReport>{

	@Query(value="select a.* from"
		+ " (SELECT IFNULL(after_change, 0) after_change,"
		+ " change_time,company_title,IFNULL(money, 0) money,"
		+ " reference_number,remark,type,"
		+ " IFNULL(after_change_promotion, 0) after_change_promotion,"
		+ " city,company_code,operator_user_name,IFNULL(total_balance, 0) total_balance,"
		+ " written_remarks,arrival_time"
		+ " from fit_credit_change_log"
		+ " UNION"
		+ " SELECT IFNULL(after_change, 0) after_change,"
		+ " change_time,company_title,"
		+ " IFNULL(money, 0) money,reference_number, remark, type,"
		+ " IFNULL(after_change_promotion, 0) after_change_promotion,"
		+ " city,company_code,operator_user_name,"
		+ " IFNULL(total_balance, 0) total_balance,"
		+ " written_remarks,arrival_time"
		+ " from fit_promotion_money_log) a"
		+ " where a.type like %?6%"
		+ " and a.city like %?3%"
		+ " and a.company_id = ?4"
		+ " and a.change_time >= ?1"
		+ " and a.change_time <= ?2"
		+ " and (a.company_title like %?5%"
		+ " or a.company_code like %?5%"
		+ " or a.reference_number like %?5%)"
		+ " ORDER BY change_time desc",nativeQuery=true)
	List<FitCompanyReport> queryDownList(String begindata, String enddata, String city, Long companyId,
			String keywords, String type);
}
