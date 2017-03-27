package com.ynyes.lyz.service;

import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import com.ynyes.lyz.entity.TdOrder;
import com.ynyes.lyz.entity.user.CreditChangeType;
import com.ynyes.lyz.entity.user.TdUser;
import com.ynyes.lyz.repository.TdUserRepo;
import com.ynyes.lyz.util.Criteria;
import com.ynyes.lyz.util.Restrictions;

@Service
@Transactional
public class TdUserService {

	@Autowired
	private TdUserRepo repository;

	@Autowired
	private TdUserCreditLogService tdUserCreditService;

	public TdUser save(TdUser user) {
		if (null == user) {
			return null;
		}
		return repository.save(user);
	}

	// public TdUser saveWithOutBalance(TdUser user)
	// {
	// return repository.saveWithOutBalance(user);
	// }

	public void delete(Long id) {
		if (null != id) {
			repository.delete(id);
		}
	}

	public TdUser findOne(Long id) {
		if (null == id) {
			return null;
		}
		return repository.findOne(id);
	}

	/**
	 * 按username查找，自身除外
	 * 
	 * @author Zhangji
	 * @param username
	 * @param id
	 * @return
	 */
	public TdUser findByUsernameAndIdNot(String username, Long id) {
		if (null == username || null == id) {
			return null;
		}

		return repository.findByUsernameAndIdNot(username, id);
	}

	public List<TdUser> findAll() {
		return (List<TdUser>) repository.findAll();
	}

	/**
	 * 根据账号密码查找用户
	 * 
	 * @author dengxiao
	 */
	public TdUser findByUsernameAndPasswordAndIsEnableTrue(String username, String password) {
		if (null == username || null == password) {
			return null;
		}
		return repository.findByUsernameAndPasswordAndIsEnableTrue(username, password);
	}

	/**
	 * 根据用户名查找用户
	 * 
	 * @author dengxiao
	 */
	public TdUser findByUsername(String username) {
		if (null == username) {
			return null;
		}
		return repository.findByUsername(username);
	}

	/**
	 * 根据用户名查找启用的用户
	 * 
	 * @author dengxiao
	 */
	public TdUser findByUsernameAndIsEnableTrue(String username) {
		if (null == username) {
			return null;
		}
		TdUser user = repository.findByUsernameAndIsEnableTrue(username);
		if (user != null) {
			user.setLastVisitTime(new Date());
			repository.save(user);
		}
		return repository.findByUsernameAndIsEnableTrue(username);
	}

	/**
	 * 根据用户名和城市名查找用户
	 * 
	 * @author dengxiao
	 */
	public TdUser findByUsernameAndCityNameAndIsEnableTrue(String username, String cityName) {
		if (null == username || null == cityName) {
			return null;
		}
		return repository.findByUsernameAndCityNameAndIsEnableTrue(username, cityName);
	}

	/**
	 * @author lc
	 * @注释：查找所有按id降序排序
	 */
	public Page<TdUser> findAllOrderByIdDesc(int page, int size) {
		PageRequest pageRequest = new PageRequest(page, size, new Sort(Direction.DESC, "id"));

		return repository.findAll(pageRequest);
	}

	/**
	 * @author lc @注释：
	 */
	public Page<TdUser> findByUserTypeOrderByIdDesc(Long usertype, int page, int size) {
		PageRequest pageRequest = new PageRequest(page, size, new Sort(Direction.DESC, "id"));

		return repository.findByUserTypeOrderByIdDesc(usertype, pageRequest);
	}

	/**
	 * @author lc
	 * @注释：搜索用户
	 */
	public Page<TdUser> searchAndOrderByIdDesc(String keywords, int page, int size) {
		PageRequest pageRequest = new PageRequest(page, size);

		return repository.findByUsernameContainingOrRealNameContainingOrderByIdDesc(keywords, keywords, pageRequest);
	}

	public TdUser findByOpUser(String opUser) {
		if (null == opUser) {
			return null;
		}
		return repository.findByOpUser(opUser);
	}

	/**
	 * 根据指定的门店查找销售顾问和店长
	 * 
	 * @author DengXiao
	 */
	public List<TdUser> findByCityIdAndCustomerIdAndUserTypeOrCityIdAndCustomerIdAndUserType(Long cityId,
			Long customerId) {
		if (null == cityId || null == customerId) {
			return null;
		}
		return repository
				.findByCityIdAndCustomerIdAndUserTypeAndIsEnableTrueOrCityIdAndCustomerIdAndUserTypeAndIsEnableTrue(
						cityId, customerId, 1L, cityId, customerId, 2L);
	}

	/**
	 * 根据关键字查找销售顾问和店长
	 * 
	 * @author DengXiao
	 */
	public List<TdUser> findByCityIdAndRealNameContainingAndUserTypeOrCityIdAndRealNameContainingAndUserType(
			Long cityId, String keywords, Long customerId) {
		if (null == cityId || null == keywords) {
			return null;
		}
		if (customerId == null) {
			return repository
					.findByCityIdAndRealNameContainingAndUserTypeAndIsEnableTrueOrCityIdAndRealNameContainingAndUserTypeAndIsEnableTrue(
							cityId, keywords, 1L, cityId, keywords, 2L);
		} else {
			return repository
					.findByCityIdAndCustomerIdAndRealNameContainingAndUserTypeAndIsEnableTrueOrCityIdAndCustomerIdAndRealNameContainingAndUserTypeAndIsEnableTrue(
							cityId, customerId, keywords, 1L, cityId, customerId, keywords, 2L);
		}

	}

	/**
	 * 根据指定的城市查找所有的销顾和店长
	 * 
	 * @author DengXiao
	 */
	public List<TdUser> findByCityIdAndUserTypeOrCityIdAndUserTypeOrderBySortIdAsc(Long cityId) {
		if (null == cityId) {
			return null;
		}
		return repository.findByCityIdAndUserTypeOrCityIdAndUserTypeOrderBySortIdAsc(cityId, 1L, cityId, 2L);
	}

	/**
	 * 查询指定门店下的所有用户
	 * 
	 * @author DengXiao
	 */
	public List<TdUser> findByCityIdAndCustomerIdAndUserTypeOrderBySortIdAsc(Long cityId, Long customerId) {
		if (null == cityId || null == customerId) {
			return null;
		}
		return repository.findByCityIdAndCustomerIdAndUserTypeAndIsEnableTrueOrderBySortIdAsc(cityId, customerId, 0L);
	}

	/**
	 * 根据关键词查询指定门店下的所有用户
	 * 
	 * @author DengXiao
	 */
	public List<TdUser> findByCityIdAndCustomerIdAndUserTypeAndRealNameContainingOrderBySortIdAsc(Long cityId,
			Long customerId, String keywords) {
		if (null == cityId || null == customerId || null == keywords) {
			return null;
		}
		return repository
				.findByCityIdAndCustomerIdAndUserTypeAndRealNameContainingAndIsEnableTrueOrCityIdAndCustomerIdAndUserTypeAndUsernameContainingAndIsEnableTrueOrderBySortIdAsc(
						cityId, customerId, 0L, keywords, cityId, customerId, 0L, keywords);
	}

	/**
	 * 根据真实姓名查询用户
	 * 
	 * @param realName
	 * @return
	 */
	public List<TdUser> findByRealName(String realName) {
		if (null == realName) {
			return null;
		}
		return repository.findByRealName(realName);
	}

	/**
	 * 根据主单号查询快递员
	 * 
	 * @param mainOrderNumber
	 *            主单号
	 * @return
	 */
	public TdUser searchDriverByMainOrderNumber(String mainOrderNumber) {
		if (null == mainOrderNumber) {
			return null;
		}
		return repository.searchDriverByMainOrderNumber(mainOrderNumber);
	}

	/**
	 * @注释：根据城市查找所有按id降序排序
	 */
	public Page<TdUser> findByCityNameOrderByIdDesc(String cityName, int page, int size) {
		PageRequest pageRequest = new PageRequest(page, size, new Sort(Direction.DESC, "id"));

		return repository.findByCityNameOrderByIdDesc(cityName, pageRequest);
	}

	/**
	 * @注释：搜索城市下面的用户
	 */
	public Page<TdUser> searchcityNameAndOrderByIdDesc(String keywords, String cityName, int page, int size) {
		PageRequest pageRequest = new PageRequest(page, size);
		if (StringUtils.isBlank(cityName) || StringUtils.isBlank(keywords)) {
			return null;
		}
		return repository.findByCityNameAndUsernameContainingOrCityNameAndRealNameContainingOrderByIdDesc(cityName,
				keywords, cityName, keywords, pageRequest);
	}

	/**
	 * 根据用户类型查询用户
	 * 
	 * @param userType
	 *            用户类型
	 * @return
	 */
	public List<TdUser> findByUserTypeOrderByIdDesc(Long userType) {
		return repository.findByUserTypeOrderByIdDesc(userType);
	}

	/**
	 * 根据关键词查找指定门店的销顾和店长
	 * 
	 * @author 作者：DengXiao
	 * @version 创建时间：2016年4月20日上午11:24:54
	 */
	public List<TdUser> findByCustomerIdAndCityIdAndUserTypeAndUsernameContainingOrCustomerIdAndCityIdAndUserTypeAndRealNameContainingOrCustomerIdAndCityIdAndUserTypeAndUsernameContainingOrCustomerIdAndCityIdAndUserTypeAndRealNameContainingOrderBySortIdAsc(
			Long customerId, Long cityId, String keywords) {
		if (null == customerId || null == cityId || null == keywords) {
			return null;
		}
		return repository
				.findByCustomerIdAndCityIdAndUserTypeAndUsernameContainingAndIsEnableTrueOrCustomerIdAndCityIdAndUserTypeAndRealNameContainingAndIsEnableTrueOrCustomerIdAndCityIdAndUserTypeAndUsernameContainingAndIsEnableTrueOrCustomerIdAndCityIdAndUserTypeAndRealNameContainingOrderBySortIdAsc(
						customerId, cityId, 1L, keywords, customerId, cityId, 1L, keywords, customerId, cityId, 2L,
						keywords, customerId, cityId, 2L, keywords);
	}

	/**
	 * 根据导购id查询改导购下面的客户
	 * 
	 * @param sellerId
	 * @return
	 */
	public List<TdUser> findBySellerIdAndUserType(Long sellerId, Long userType) {
		if (sellerId == null || userType == null) {
			return null;
		}
		return repository.findBySellerIdAndUserType(sellerId, userType);
	}

	/**
	 * 根据用户类型获取类型名称
	 * 
	 * @param userType
	 * @return
	 */
	public String getUserTypeName(Long userType) {
		if (userType != null) {
			if (userType == 0L) {
				return "会员";
			} else if (userType == 1L) {
				return "销售顾问";
			} else if (userType == 2L) {
				return "店长";
			} else if (userType == 3L) {
				return "店主";
			} else if (userType == 4L) {
				return "区域经理";
			} else if (userType == 5L) {
				return "配送员";
			} else {
				return "" + userType;
			}
		}
		return "" + userType;

	}

	/**
	 * 用户列表查询
	 * 
	 * @return
	 * @author zp
	 */
	public Page<TdUser> searchList(String keywords, List<Long> roleDiyIds, Long userType, Long city, Long diyCode,
			int size, int page) {
		PageRequest pageRequest = new PageRequest(page, size);
		Criteria<TdUser> c = new Criteria<TdUser>();
		// 用户名
		if (StringUtils.isNotBlank(keywords)) {
			c.add(Restrictions.or(Restrictions.like("realName", keywords, true),
					Restrictions.like("username", keywords, true)));
		}
		if (roleDiyIds != null && roleDiyIds.size() > 0) {
			c.add(Restrictions.in("upperDiySiteId", roleDiyIds, true));
		}
		if (userType != null) {
			c.add(Restrictions.eq("userType", userType, true));
		}
		if (city != null) {
			c.add(Restrictions.eq("cityId", city, true));
		}
		if (diyCode != null) {
			c.add(Restrictions.eq("upperDiySiteId", diyCode, true));
		}

		c.setOrderByDesc("registerTime");
		return repository.findAll(c, pageRequest);
	}

	/**
	 * 查询门店下面的所有会员
	 * 
	 * @param diyId
	 *            门店id
	 * @return
	 */
	public List<TdUser> findByUpperDiySiteId(Long diyId) {
		if (diyId == null) {
			return null;
		}
		return repository.findByUpperDiySiteId(diyId);
	}

	/**
	 * 查询城市下面的所有会员
	 * 
	 * @param cityId
	 *            城市编号
	 * @return
	 */
	public List<TdUser> findByCityId(Long cityId) {
		if (cityId == null) {
			return null;
		}
		return repository.findByCityId(cityId);
	}

	public Boolean validateCredit(TdOrder order) {
		TdUser seller = this.findOne(order.getSellerId());
		return this.validateCredit(seller, order);
	}

	public Boolean validateCredit(TdUser seller, TdOrder order) {
		return seller.getCredit() >= order.getTotalPrice();
	}

	public void useCredit(CreditChangeType type, TdOrder order) {
		TdUser seller = this.findOne(order.getSellerId());
		this.useCredit(type, seller, order);
	}

	public void useCredit(CreditChangeType type, TdUser seller, TdOrder order) {
		if (order.getTotalPrice() > 0) {
			seller.setCredit(seller.getCredit() - order.getTotalPrice());
			this.save(seller);
			tdUserCreditService.createLogByCondition(type, seller, order);
		}
	}

	public void repayCredit(CreditChangeType type, TdUser seller, TdOrder order) {
		if (order.getTotalPrice() > 0) {
			seller.setCredit(seller.getCredit() + order.getTotalPrice());
			this.save(seller);
			tdUserCreditService.createLogByCondition(type, seller, order);
		}
	}

	public void repayCredit(CreditChangeType type, TdUser seller, Double amount, String orderNumber) {
		if (amount > 0) {
			seller.setCredit(seller.getCredit() + amount);
			this.save(seller);
			tdUserCreditService.createLogByCondition(type, seller, orderNumber, amount);
		}
	}
}
