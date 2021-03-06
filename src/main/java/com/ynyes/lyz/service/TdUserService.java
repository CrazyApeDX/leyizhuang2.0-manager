package com.ynyes.lyz.service;

import com.ynyes.lyz.entity.TdDiySite;
import com.ynyes.lyz.entity.TdOrder;
import com.ynyes.lyz.entity.TdOrderData;
import com.ynyes.lyz.entity.TdOwnMoneyRecord;
import com.ynyes.lyz.entity.user.CreditChangeType;
import com.ynyes.lyz.entity.user.TdUser;
import com.ynyes.lyz.entity.user.TdUserChangeSellerLog;
import com.ynyes.lyz.excp.AppConcurrentExcp;
import com.ynyes.lyz.interfaces.service.TdInterfaceService;
import com.ynyes.lyz.interfaces.utils.INFConstants;
import com.ynyes.lyz.repository.TdUserRepo;
import com.ynyes.lyz.util.CountUtil;
import com.ynyes.lyz.util.Criteria;
import com.ynyes.lyz.util.Restrictions;
import com.ynyes.lyz.util.Utils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Service
@Transactional
public class TdUserService {

    @Autowired
    private TdUserRepo repository;

    @Autowired
    private TdUserCreditLogService tdUserCreditService;

    @Autowired
    private TdDiySiteService tdDiySiteService;

    @Autowired
    private TdUserChangeSellerLogService tdUserChangeSellerLogService;
    
    @Autowired
	private TdInterfaceService tdInterfaceService;
    
    @Autowired
	private TdOrderDataService tdOrderDataService;
    
    @Autowired
	private TdOwnMoneyRecordService tdOwnMoneyRecordService;
    
    @Autowired
	TdOrderService tdOrderService;

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
     * @param username
     * @param id
     * @return
     * @author Zhangji
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
     * @param mainOrderNumber 主单号
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
     * @param userType 用户类型
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
     * @param diyId 门店id
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
     * @param cityId 城市编号
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

    @Transactional(rollbackFor = Exception.class)
    public void repayCredit(CreditChangeType type, TdUser seller, Double amount, String orderNumber) {
        if (amount > 0) {
            seller.setCredit(seller.getCredit() + amount);
            this.save(seller);
            tdUserCreditService.createLogByCondition(type, seller, orderNumber, amount);
        }
    }


    /**
     * @param upperDiySiteId
     * @return
     * @title 根据门店ID查询用户列表
     * @describe
     * @author Generation Road
     * @date 2017年5月23日
     */
    public List<TdUser> findByUpperDiySiteIdAndIsEnableTrue(Long upperDiySiteId) {
        if (upperDiySiteId == null) {
            return null;
        }
        return repository.findByUpperDiySiteIdAndIsEnableTrue(upperDiySiteId);
    }

    public Page<TdUser> findByUsernameContainingOrRealNameContaining(String keywords, Integer page, Integer size) {
        if (null == keywords) {
            return null;
        }
        PageRequest pageRequest = new PageRequest(page, size);
        return repository.findByUsernameContainingOrRealNameContaining(keywords, keywords,
                pageRequest);
    }


    public Page<TdUser> findByUsernameContainingOrRealNameContainingAndUserTypeIn(String keywords, Integer page,
                                                                                  Integer size, List<Long> userTypeList) {
        if (null == keywords) {
            return null;
        }
        PageRequest pageRequest = new PageRequest(page, size);
        return repository.findByUsernameContainingOrRealNameContainingAndUserTypeIn(keywords, keywords,
                pageRequest, userTypeList);
    }

    public Page<TdUser> findByUsernameContainingOrRealNameContainingAndDiyCodeAndUserTypeIn(String keywords,
                                                                                            Integer page, Integer size, String diyCode, List<Long> userTypeList) {
        if (null == keywords) {
            return null;
        }
        PageRequest pageRequest = new PageRequest(page, size);
        return repository.findByUsernameContainingOrRealNameContainingAndDiyCodeAndUserTypeIn(keywords, keywords,
                pageRequest, diyCode, userTypeList);
    }

    public Page<TdUser> findByUsernameContainingOrRealNameContainingAndCityAndUserTypeIn(String keywords, Integer page,
                                                                                         Integer size, String cityName, List<Long> userTypeList) {
        if (null == keywords) {
            return null;
        }
        PageRequest pageRequest = new PageRequest(page, size);
        return repository.findByUsernameContainingOrRealNameContainingAndCityNameAndUserTypeIn(keywords, keywords,
                pageRequest, cityName, userTypeList);
    }

    public Page<TdUser> findByUsernameContainingOrRealNameContainingAndUserType(String keywords, Integer page,
                                                                                Integer size, Long userType, List<Long> diySiteIds) {

        PageRequest pageRequest = new PageRequest(page, size);
        Criteria<TdUser> c = new Criteria<TdUser>();
        // 用户名
        if (StringUtils.isNotBlank(keywords)) {
            c.add(Restrictions.or(Restrictions.like("realName", keywords, true),
                    Restrictions.like("username", keywords, true)));
        }
        if (null != diySiteIds && diySiteIds.size() > 0) {
            c.add(Restrictions.in("upperDiySiteId", diySiteIds, true));
        }
        if (null != userType) {
            c.add(Restrictions.eq("userType", userType, true));
        }

        return repository.findAll(c, pageRequest);

//		if (null == keywords) {
//			return null;
//		}
//		PageRequest pageRequest = new PageRequest(page, size);
//		return repository.findByUsernameContainingOrRealNameContainingAndUserType(keywords, keywords,
//				pageRequest,userType);
    }

    public TdUser modifyBalance(Double variableAmount, TdUser user) {

        if (null == user) {
            return user;
        }
        if (null == variableAmount || 0.0 == variableAmount || variableAmount > user.getBalance()) {
            return user;
        }

        Double unCashBalance = user.getUnCashBalance();
        Double cashBalance = user.getCashBalance();
        // 先扣除不可提现预存款，在扣除可提现预存款
        if (variableAmount <= unCashBalance) {
            user.setUnCashBalance(unCashBalance - variableAmount);
        } else {
            variableAmount = variableAmount - unCashBalance;
            user.setUnCashBalance(0.0);
            user.setCashBalance(cashBalance - variableAmount);
        }

        int row = repository.update(user.getCashBalance(), user.getUnCashBalance(), user.getId(),
                user.getVersion());
        // 并发控制，判断version是否改变
        if (1 != row) {
            throw new AppConcurrentExcp("账号余额信息过期！");
        }
        return user;
    }

    /**
     * 查询所有的会员
     *
     * @param cityName
     * @return
     */
    public List<TdUser> queryAllUser( Date date) {
        return repository.queryAllUser( date);
    }

    ;

    /**
     * 清空导购 将门店设为默认门店
     */
    public TdUser clearSeller(TdUser user) {
        if (user == null) {
            return new TdUser();
        }
        
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = new Date();
        try {
        	date = sdf.parse("2017-11-26 00:00:00");
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        if(user.getCityName().equals("郑州市") && user.getRegisterTime().before(date)){
        	// 郑州存在虚拟门店帐号 2017-11-26 号以前注册的都是虚拟帐号
        	return user;
        }

        // 创建日志对象，记录修改的导购信息
        TdUserChangeSellerLog log = new TdUserChangeSellerLog();
        log.setUsername(user.getUsername());
        log.setUserRealName(user.getRealName());
        log.setOldDiyCode(user.getDiyCode());
        log.setOldDiyName(user.getDiyName());
        log.setOldSellerId(user.getSellerId());
        log.setOldSellerName(user.getSellerName());
        log.setOldUpperDiySiteId(user.getUpperDiySiteId());
        log.setCreateTime(new Date());

        // 获取门店名称
        TdDiySite defaultDiySite = tdDiySiteService.findDefaultDiyByCityInfo(user.getCityName());
        // 门店设置为默认门店
        user.setDiyName(defaultDiySite.getTitle());
        user.setDiyCode(defaultDiySite.getStoreCode());
        user.setUpperDiySiteId(defaultDiySite.getId());
        user.setCustomerId(defaultDiySite.getCustomerId());
        // 清空导购
        user.setSellerId(0L);
        user.setSellerName("无");
        user.setChangeSellerTime(new Date());
        user.setLoginFlag(1L);

        log.setNewDiyCode(defaultDiySite.getStoreCode());
        log.setNewDiyName(defaultDiySite.getTitle());
        log.setNewSellerId(0L);
        log.setNewSellerName("无");
        log.setNewUpperDiySiteId(defaultDiySite.getId());

        tdUserChangeSellerLogService.save(log);

        return user;
    }

    /**
     * @param cityId
     * @return
     * @title 查询城市下的所有导购
     * @describe
     * @author Generation Road
     * @date 2017年11月3日
     */
    public List<TdUser> findByUserTypeAndCityId(String diyCode) {
        if (diyCode == null) {
            return null;
        }
        return repository.findByUserTypeAndCityId(diyCode);
    }

    public List<Object> queryFXMemberDownList(Date begin, Date end, String cityName, String diyCode, List<String> roleDiyIds) {
        // 判断空值
        if (begin == null) {
            begin = Utils.getSysStartDate();
        }
        if (end == null) {
            end = new Date();
        }
        if (StringUtils.isBlank(cityName)) {
            cityName = "%";
        }
        if (StringUtils.isBlank(diyCode)) {
            diyCode = "%";
        }
        if (roleDiyIds == null || roleDiyIds.size() == 0) {
            roleDiyIds.add("0");
        }
        return repository.queryFXMemberDownList(begin, end, cityName, diyCode, roleDiyIds);
    }
    
    public void backMoney(TdOrder tdOrder, String serialNumber, Double money, Double pos, Double other, String realPayTime) throws Exception{
    	TdOwnMoneyRecord ownMoneyRecord = this.generateOwnMoneyRecord(serialNumber, tdOrder, money, pos, other, realPayTime);
    	
    	TdUser seller = this.findOne(tdOrder.getSellerId());
		this.repayCredit(CreditChangeType.REPAY, seller, (money + pos + other),
				tdOrder.getMainOrderNumber());
		
		//记录收款并发ebs
		this.tdInterfaceService.initCashReciptByTdOwnMoneyRecord(ownMoneyRecord, INFConstants.INF_RECEIPT_TYPE_DIYSITE_INT);
    }
    
    
    @Transactional(rollbackFor = Exception.class)
    public TdOwnMoneyRecord generateOwnMoneyRecord(String serialNumber,TdOrder order, Double money, Double pos, Double other, String realPayTime){
	    TdOwnMoneyRecord rec = new TdOwnMoneyRecord();
		if (null != serialNumber) {
			rec.setSerialNumber(serialNumber);
		}
		rec.setCreateTime(new Date());
		rec.setOrderNumber(order.getMainOrderNumber());
		rec.setDiyCode(order.getDiySiteCode());
		rec.setOwned(0.0);
		rec.setPayed(money + pos + other);
		rec.setBackMoney(money);
		rec.setBackPos(pos);
		rec.setBackOther(other);
		rec.setUsername(order.getUsername());
		rec.setIsOwn(false);
		rec.setIsEnable(false);
		rec.setIsPayed(false);
		rec.setSortId(99L);
		rec.setPayTime(new Date());
	
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date date = null;
		try {
			date = sdf.parse(realPayTime);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		rec.setRealPayTime(date);
		tdOwnMoneyRecordService.save(rec);
	
		// 门店收款保存到订单
		order.setCashPay(money);
		order.setPosPay(pos);
		order.setBackOtherPay(other);
		tdOrderService.save(order);
		
		// 查找TdOrderData，如果存在，则设置TdOrderData的值
		TdOrderData orderData = tdOrderDataService.findByMainOrderNumber(order.getMainOrderNumber());
		if (null != orderData) {
			orderData.setSellerCash(CountUtil.add(orderData.getSellerCash(), money));
			orderData.setSellerPos(CountUtil.add(orderData.getSellerPos(), pos));
			orderData.setSellerOther(CountUtil.add(orderData.getSellerOther(), other));
			orderData.setDue(orderData.countDue());
			tdOrderDataService.save(orderData);
		}
		return rec;
    }
}
