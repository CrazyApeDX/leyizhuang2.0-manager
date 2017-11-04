package com.ynyes.lyz.repository;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import com.ynyes.lyz.entity.user.TdUser;

public interface TdUserRepo extends PagingAndSortingRepository<TdUser, Long>, JpaSpecificationExecutor<TdUser> {

	TdUser findByUsernameAndPasswordAndIsEnableTrue(String username, String password);

	TdUser findByUsernameAndIsEnableTrue(String username);

	TdUser findByUsername(String username);

	TdUser findByUsernameAndCityNameAndIsEnableTrue(String username, String cityName);

	TdUser findByUsernameAndIdNot(String username, Long id); // zhangji 2016-1-8
																// // 10:26:41

	Page<TdUser> findByUserTypeOrderByIdDesc(Long userlevelId, Pageable page);

	Page<TdUser> findByUsernameContainingOrRealNameContainingOrderByIdDesc(String keywords1, String keywords2,
			Pageable page);

	TdUser findByOpUser(String opUser);

	/**
	 * 根据指定的门店查找销售顾问和店长
	 * 
	 * @author DengXiao
	 */
	List<TdUser> findByCityIdAndCustomerIdAndUserTypeAndIsEnableTrueOrCityIdAndCustomerIdAndUserTypeAndIsEnableTrue(Long cityId1, Long customerId1,
			Long userType1, Long cityId2, Long customerId2, Long userType2);

	/**
	 * 根据关键字查找销售顾问和店长
	 * 
	 * @author DengXiao
	 */
	List<TdUser> findByCityIdAndRealNameContainingAndUserTypeAndIsEnableTrueOrCityIdAndRealNameContainingAndUserTypeAndIsEnableTrue(Long cityId1,
			 String keywords1, Long userType1, Long cityId2, String keywords2, Long userType2);

	/**
	 * 根据关键字查找销售顾问和店长
	 * 
	 * @author DengXiao
	 */
	List<TdUser> findByCityIdAndCustomerIdAndRealNameContainingAndUserTypeAndIsEnableTrueOrCityIdAndCustomerIdAndRealNameContainingAndUserTypeAndIsEnableTrue(Long cityId1,
			 Long customerId1,String keywords1, Long userType1, Long cityId2, Long customerId2, String keywords2, Long userType2);

	
	/**
	 * 根据指定的城市查找所有的销顾和店长
	 * 
	 * @author DengXiao
	 */
	List<TdUser> findByCityIdAndUserTypeOrCityIdAndUserTypeOrderBySortIdAsc(Long cityId1, Long userType1, Long cityId2,
			Long userType2);

	/**
	 * 查询指定门店下的所有用户
	 * 
	 * @author DengXiao
	 */
	List<TdUser> findByCityIdAndCustomerIdAndUserTypeAndIsEnableTrueOrderBySortIdAsc(Long cityId, Long customerId, Long userType);

	/**
	 * 根据关键词查询指定门店下的所有用户
	 * 
	 * @author DengXiao
	 */
	List<TdUser> findByCityIdAndCustomerIdAndUserTypeAndRealNameContainingAndIsEnableTrueOrCityIdAndCustomerIdAndUserTypeAndUsernameContainingAndIsEnableTrueOrderBySortIdAsc(
			Long cityId1, Long customerId1, Long userType1, String keywords1, Long cityId2, Long customerId2,
			Long userType2, String keywords2);

	/**
	 * 根据真实姓名查询用户
	 * 
	 * @param realName
	 * @return
	 */
	List<TdUser> findByRealName(String realName);

	/**
	 * 根据主单号查询快递员
	 * 
	 * @param mainOrderNumber
	 *            主单号
	 * @return
	 */
	@Query("select u from TdUser u,TdDeliveryInfo d where u.opUser=d.driver and d.orderNumber=?1")
	TdUser searchDriverByMainOrderNumber(String mainOrderNumber);

	/**
	 * @注释：根据城市查找所有按id降序排序
	 */
	public Page<TdUser> findByCityNameOrderByIdDesc(String cityName, Pageable page);

	/**
	 * @注释：搜索城市下面的用户
	 */
	public Page<TdUser> findByCityNameAndUsernameContainingOrCityNameAndRealNameContainingOrderByIdDesc(
			String cityName0, String username, String cityName, String realName, Pageable page);

	/**
	 * 根据用户类型查询用户
	 * 
	 * @param userType
	 *            用户类型
	 * @return
	 */
	public List<TdUser> findByUserTypeOrderByIdDesc(Long userType);

	/**
	 * 根据关键词查找指定门店的销顾和店长
	 * 
	 * @author 作者：DengXiao
	 * @version 创建时间：2016年4月20日上午11:24:54
	 */
	public List<TdUser> findByCustomerIdAndCityIdAndUserTypeAndUsernameContainingAndIsEnableTrueOrCustomerIdAndCityIdAndUserTypeAndRealNameContainingAndIsEnableTrueOrCustomerIdAndCityIdAndUserTypeAndUsernameContainingAndIsEnableTrueOrCustomerIdAndCityIdAndUserTypeAndRealNameContainingOrderBySortIdAsc(
			Long customerId1, Long cityId1, Long userType1, String keywords1, Long customerId2, Long cityId2,
			Long userType2, String keywords2, Long customerId3, Long cityId3, Long userType3, String keywords3,
			Long customerId4, Long cityId4, Long userType4, String keywords4);
	/**
	 * 根据导购id查询改导购下面的客户
	 * @return
	 */
	public List<TdUser> findBySellerIdAndUserType(Long sellerId,Long userType);
	
	/**
	 * 查询门店下面的所有会员
	 * @param diyId 门店id
	 * @return
	 */
	List<TdUser> findByUpperDiySiteId(Long diyId);
	
	/**
	 * 查询城市下面的所有会员
	 * @param cityId 城市编号
	 * @return
	 */
	List<TdUser> findByCityId(Long cityId);
	
//	@Query("update TdUser set opUser = (?1).opUser where id = (?1).id")
//	TdUser saveWithOutBalance(TdUser user);
	
	
	/**
	 * @title 根据门店ID查询用户列表
	 * @describe 
	 * @author Generation Road
	 * @date 2017年5月23日
	 * @param upperDiySiteId
	 * @return
	 */
	List<TdUser> findByUpperDiySiteIdAndIsEnableTrue(Long upperDiySiteId);

	Page<TdUser> findByUsernameContainingOrRealNameContaining(String keywords, String keywords2,
			Pageable page);

	
	Page<TdUser> findByUsernameContainingOrRealNameContainingAndUserTypeIn(String keywords, String keywords2,
			Pageable page, List<Long> userTypeList);

	Page<TdUser> findByUsernameContainingOrRealNameContainingAndDiyCodeAndUserTypeIn(String keywords, String keywords2,
			Pageable page, String diyCode, List<Long> userTypeList);

	Page<TdUser> findByUsernameContainingOrRealNameContainingAndCityNameAndUserTypeIn(String keywords, String keywords2,
			Pageable page, String cityName, List<Long> userTypeList);

	Page<TdUser> findByUsernameContainingOrRealNameContainingAndUserType(String keywords, String keywords2,
			Pageable page, long userType);
	
	@Modifying
	@Query("update TdUser set cash_balance = :cashBalance, un_cash_balance = :unCashBalance where id = :id and version = :version")
	public int update(@Param("cashBalance")Double cashBalance, @Param("unCashBalance")Double unCashBalance, @Param("id")Long id, @Param("version")Timestamp version);
	
	/**
	 * 查询所有的会员
	 * 
	 * @return
	 */
	@Query(value = "SELECT * from td_user where user_type = 0 and seller_id is NOT NULL and city_name = ?1 and register_time <= ?2 and seller_id != 0" , nativeQuery = true)
	List<TdUser> queryAllUser(String cityName,Date date);
	
	@Query(value = "SELECT * from td_user WHERE user_type in (1,2,3,4) and diy_code = ?1 and is_enable is true", nativeQuery = true)
	List<TdUser> findByUserTypeAndCityId(String diyCode);
}
