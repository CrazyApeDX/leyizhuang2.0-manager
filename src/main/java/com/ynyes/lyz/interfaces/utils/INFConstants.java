package com.ynyes.lyz.interfaces.utils;

public class INFConstants
{
	/**
	 * 销售订单头表
	 */
	public static final String	INF_ORDER_STR				= "TD_ORDER";

	/**
	 * 销售订单行表
	 */
	public static final String	INF_ORDER_GOODS_STR		= "TD_ORDER_GOODS";

	/**
	 * 销售订单券使用行表
	 */
	public static final String	INF_ORDER_COUPON_STR		= "TD_ORDER_COUPONS";

	/**
	 * 到店自提单收货时间表
	 */
	public static final String	INF_ORDER_RECEIVE_STR		= "TD_ORDER_RECEIVE";

	/**
	 * 销退订单头表
	 */
	public static final String	INF_RT_ORDER_STR			= "TD_RETURN_ORDER";

	/**
	 * 销退订单行表
	 */
	public static final String	INF_RT_ORDER_GOODS_STR	= "TD_RT_ORDER_GOODS";

	/**
	 * 销退单的券退回表
	 */
	public static final String	INF_RT_ORDER_COUPONS_STR	= "TD_RT_COUPONS";

	/**
	 * 到店退货单退货时间表
	 */
	public static final String	INF_ORDER_RETMD_STR		= "TD_ORDER_RETMD";

	/**
	 * 收款表
	 */
	public static final String	INF_CASH_RECEIPTS_STR		= "TD_CASH_RECEIPTS";
	
	public static final String INF_CASH_REFUND_STR		= "TD_CASH_REFUND";


	/**
	 * 取消订单退货
	 */
	public static final Integer INF_RETURN_ORDER_CANCEL_INT	= 0;


	/**
	 * 其他退货
	 */
	public static final Integer INF_RETURN_ORDER_SUB_INT	= 1;
	
	public static final Integer INF_RECEIPT_TYPE_ALIPAY_INT		= 0;
	public static final Integer INF_RECEIPT_TYPE_WECHAT_INT		= 1;
	public static final Integer INF_RECEIPT_TYPE_UNION_INT		= 2;
	public static final Integer INF_RECEIPT_TYPE_DIYSITE_INT		= 3;
	public static final Integer INF_RECEIPT_TYPE_DELIVER_INT		= 4;
	
	
	
	
	/*         --  字典key  --         */
	
	public static final String kCouponList		= "coupon_list_key";
	public static final String kBalance		= "balance_key";
	public static final String kPrcieDif		= "price_difference";
	
}
