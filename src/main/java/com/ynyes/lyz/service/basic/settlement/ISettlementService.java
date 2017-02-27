package com.ynyes.lyz.service.basic.settlement;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.ynyes.lyz.entity.TdOrder;
import com.ynyes.lyz.entity.TdOrderGoods;
import com.ynyes.lyz.entity.user.TdUser;

/**
 * <p>标题：ISettlementService.java</p>
 * <p>描述：结算相关服务接口</p>
 * @author 作者：CrazyDX
 * @version 版本：2016年10月12日下午1:30:20
 */
public interface ISettlementService {

	/**
	 * <p>描述：验证订单非空的方法</p>
	 * @param order 订单
	 * @author 作者：CrazyDX
	 * @version 版本：2016年10月18日下午4:14:13
	 */
	void orderBasicValidate(TdOrder order) throws Exception;
	
	/**
	 * <p>描述：订单属性校验方法</p>
	 * @param 待校验的订单
	 * @author 作者：CrazyDX
	 * @version 版本：2016年10月12日下午3:13:37
	 */
	void orderAttributeValidate(TdOrder order, TdUser realUser, TdUser seller) throws Exception;
	
	/**
	 * <p>描述：主单数据处理，实质是是设置主单中可提现余额和不可提现余额的使用额度</p>
	 * @param realUser 订单归属真实用户
	 * @param order 待处理订单
	 * @author 作者：CrazyDX
	 * @version 版本：2016年10月12日下午3:18:17
	 */
	void mainOrderDataAction(TdOrder order, TdUser realUser) throws Exception;
	
	/**
	 * <p>描述：根据订单信息获取返回给客户端的结果集信息</p>
	 * @param order 订单
	 * @author 作者：CrazyDX
	 * @version 版本：2016年10月18日下午4:19:31
	 */
	Map<String, Object> getClientResult(TdOrder order) throws Exception;
	
	/**
	 * <p>描述：拆单方法</p>
	 * @param mainOrder 主单
	 * @author 作者：CrazyDX
	 * @version 版本：2016年10月17日上午10:13:39
	 */
	void disminlate(HttpServletRequest req, TdOrder mainOrder, Boolean isFitmentOrder) throws Exception;
	
	Map<String,Double> countOrderDeliveryFee(TdUser user, TdOrder order) throws Exception;
	
	Map<Long, TdOrderGoods> countOrderGoodsNumber(TdOrder order) throws Exception;
	
	Double countOrderGoodsDeliveryFee(TdUser user, TdOrderGoods orderGoods) throws Exception;
	
}
