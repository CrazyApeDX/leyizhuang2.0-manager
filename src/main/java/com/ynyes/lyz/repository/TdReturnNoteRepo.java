package com.ynyes.lyz.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.ynyes.lyz.entity.TdReturnNote;

public interface TdReturnNoteRepo extends PagingAndSortingRepository<TdReturnNote, Long>, JpaSpecificationExecutor<TdReturnNote>{
	Page<TdReturnNote> findByDiySiteTitleOrOrderNumberOrReturnNumberOrUsername(String keywords, String keywords1, String keywords2, String keywords3, Pageable page);
	
	List <TdReturnNote> findByUsername(String username);
	
	List <TdReturnNote> findByOrderNumberContaining(String orderNumber);
	
	TdReturnNote findByReturnNumber(String returnNumber);
    //以前的退货查询方法
	List<TdReturnNote> findByStatusIdAndOrderTimeBetweenOrderByIdDesc(Long statusId, Date start, Date end);
	//以前的退货查询方法
	List<TdReturnNote> findByStatusIdAndOrderTimeAfterOrderByIdDesc(Long statusId, Date time);
	//以前的退货查询方法
	Integer countByStatusIdAndOrderTimeBetweenOrderByIdDesc(Long statusId, Date start, Date end);
	//以前的退货查询方法
	Integer countByStatusIdAndOrderTimeAfterOrderByIdDesc(Long statusId, Date time);
	
	Page<TdReturnNote> findByDiySiteId(Long siteId,Pageable page);
	
	Page<TdReturnNote> findByDiySiteIdAndReturnNumberContainingOrDiySiteIdAndOrderNumberContainingOrDiySiteIdAndUsernameContaining(Long siteId0,String returnNumebr,Long siteId1,String orderNumebr,Long siteId2,String username,Pageable page);
	
	@Query(value = "select ds.title from td_manager m join td_manager_role mr on m.role_id = "
			+ " mr.id left JOIN td_diy_site ds on m.diy_code = ds.store_code "
			+ " where mr.is_sys = 0 and m.username = ?1 ", nativeQuery = true)
	String findSiteTitleByUserName(String username);
	
	//退货查询 增加配送员编号
	List<TdReturnNote> findByDriverAndStatusIdAndOrderTimeBetweenOrderByIdDesc(String driver,Long statusId, Date start, Date end);
	//退货查询 增加配送员编号
	List<TdReturnNote> findByDriverAndStatusIdAndOrderTimeAfterOrderByIdDesc(String driver,Long statusId, Date time);
	//退货查询 增加配送员编号
	Integer countByDriverAndStatusIdAndOrderTimeBetweenOrderByIdDesc(String driver,Long statusId, Date start, Date end);
	//退货查询 增加配送员编号
	Integer countByDriverAndStatusIdAndOrderTimeAfterOrderByIdDesc(String driver,Long statusId, Date time);
	
	/**
	 * 退货单 搜索功能
	 * @param driver 配送员编号
	 * @param keyword1 关键字
	 * @return 结果集
	 * @author zp
	 */
	List<TdReturnNote> findByDriverAndOrderNumberContainingOrDriverAndReturnNumberContainingOrDriverAndDiySiteTelContainingOrDriverAndDiySiteTitleContainingOrDriverAndDiySiteAddressContainingOrderByIdDesc(String driver1,String keyword1,String driver2,String keyword2,String driver3,String keyword3,String driver4,String keyword4,String driver5,String keyword5);
	/**
	 * 根据用户名分页查询
	 * @param username 用户名
	 * @param page 分页
	 * @return 结果列表
	 * @author zp
	 */
	Page<TdReturnNote> findByUsernameAndRemarkInfoNot(String username,String remark,Pageable page);
	
	/**
	 * 根据退货单号或者订单号查询退货单
	 * @author zp
	 */
	List<TdReturnNote> findByReturnNumberContainingAndUsernameAndRemarkInfoNotOrOrderNumberContainingAndUsernameAndRemarkInfoNotOrderByOrderTimeDesc(String keywords1,String username1,String remark1,String keywords2,String username2,String remark2);
	
	@Query(value=" SELECT "
			+" 	rn.* "
			+" FROM "
			+" 	td_return_note rn "
			+" WHERE "
			+" 	( "
			+" 		rn.is_coupon IS FALSE "
			+" 		OR rn.is_coupon IS NULL "
			+" 	) "
			+" AND rn.order_time >= ?1 "
			+" AND rn.return_number NOT IN ( "
			+" 	SELECT "
			+" 		return_number "
			+" 	FROM "
			+" 		td_return_order_inf "
			+" 	WHERE "
			+" 		init_date >= ?1 "
			+" ) AND rn.remark_info ='用户取消订单，退货'; ",nativeQuery=true)
	List<TdReturnNote> findMissedReturnOrder(Date beginDate);

	@Query(value=" SELECT "
			+" 	rn.* "
			+" FROM "
			+" 	td_return_note rn "
			+" WHERE "
			+" 	( "
			+" 		rn.is_coupon IS FALSE "
			+" 		OR rn.is_coupon IS NULL "
			+" 	) "
			+" AND rn.order_time >= ?1 "
			+" AND rn.return_number NOT IN ( "
			+" 	SELECT "
			+" 		return_number "
			+" 	FROM "
			+" 		td_return_order_inf "
			+" 	WHERE "
			+" 		init_date >= ?1 "
			+" ) AND rn.remark_info ='拒签退货'; ",nativeQuery=true)
	List<TdReturnNote> findRefusedReturnOrder(Date beginDate);
	
	@Query(value=" SELECT "
			+" 	rn.* "
			+" FROM "
			+" 	td_return_note rn "
			+" WHERE "
			+" 	( "
			+" 		rn.is_coupon IS FALSE "
			+" 		OR rn.is_coupon IS NULL "
			+" 	) "
			+" AND rn.order_time >= ?1 "
			+" AND rn.return_number NOT IN ( "
			+" 	SELECT "
			+" 		return_number "
			+" 	FROM "
			+" 		td_return_order_inf "
			+" 	WHERE "
			+" 		init_date >= ?1 "
			+" ) AND rn.remark_info NOT in('拒签退货','用户取消订单，退货'); ",nativeQuery=true)
	List<TdReturnNote> findNormalReturnOrder(Date beginDate);
	
}
