package com.ynyes.lyz.interfaces.utils;

public class EnumUtils {

	public enum INFTYPE
	{
		/**
		 * 销售订单头表
		 */
		ORDERINF,
		
		/**
		 * 销售订单行表
		 */
		ORDERGOODSINF,
		
		/**
		 * 销售订单券使用行表
		 */
		ORDERCOUPONINF,
		
		/**
		 * 到店自提单收货时间表
		 */
		ORDERRECEIVEINF,
		
		/**
		 * 销退订单头表
		 */
		RETURNORDERINF,
		
		/**
		 * 销退订单行表
		 */
		RETURNGOODSINF,
		
		/**
		 * 销退单的券退回表
		 */
		RETURNCOUPONINF,
		
		/**
		 * 到店退货单退货时间表
		 */
		RETURNTIMEINF,
		
		/**
		 * 收款表
		 */
		CASHRECEIPTINF,
		
		/**
		 * 退款表
		 */
		CASHREFUNDINF,
		
		/**
		 * WMS
		 */
		WMSWEBSERVICE,
		
		/**
		 * EBS
		 */
		EBSWEBSERVICE
	}
}
