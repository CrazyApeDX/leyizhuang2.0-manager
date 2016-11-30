package com.ynyes.lyz.util;

import java.util.Collection;

import org.hibernate.criterion.MatchMode;

import com.ynyes.lyz.util.Criterion.Operator;

/**
 * 条件构造器
 * 用于创建条件表达式
 * @Class Name Restrictions
 * @Author zp
 */
public class Restrictions {



		/**
		 * 等于
		 * @param fieldName
		 * @param value
		 * @param ignoreNull
		 * @return
		 */
		public static SimpleExpression eq(String fieldName, Object value, boolean ignoreNull) {
			if(StringUtils.isEmpty(value))return null;
			return new SimpleExpression (fieldName, value, Operator.EQ);
		}
		
		/**
		 * 不等于
		 * @param fieldName
		 * @param value
		 * @param ignoreNull
		 * @return
		 */
		public static SimpleExpression ne(String fieldName, Object value, boolean ignoreNull) {
			if(StringUtils.isEmpty(value))return null;
			return new SimpleExpression (fieldName, value, Operator.NE);
		}

		/**
		 * 模糊匹配
		 * @param fieldName
		 * @param value
		 * @param ignoreNull
		 * @return
		 */
		public static SimpleExpression like(String fieldName, String value, boolean ignoreNull) {
			if(StringUtils.isEmpty(value))return null;
			return new SimpleExpression (fieldName, value, Operator.LIKE);
		}

		/**
		 * 
		 * @param fieldName
		 * @param value
		 * @param matchMode
		 * @param ignoreNull
		 * @return
		 */
		public static SimpleExpression like(String fieldName, String value,
				MatchMode matchMode, boolean ignoreNull) {
			if(StringUtils.isEmpty(value))return null;
			return null;
		}

		/**
		 * 大于
		 * @param fieldName
		 * @param value
		 * @param ignoreNull
		 * @return
		 */
		public static SimpleExpression gt(String fieldName, Object value, boolean ignoreNull) {
			if(StringUtils.isEmpty(value))return null;
			return new SimpleExpression (fieldName, value, Operator.GT);
		}

		/**
		 * 小于
		 * @param fieldName
		 * @param value
		 * @param ignoreNull
		 * @return
		 */
		public static SimpleExpression lt(String fieldName, Object value, boolean ignoreNull) {
			if(StringUtils.isEmpty(value))return null;
			return new SimpleExpression (fieldName, value, Operator.LT);
		}

		/**
		 * 大于等于
		 * @param fieldName
		 * @param value
		 * @param ignoreNull
		 * @return
		 */
		public static SimpleExpression gte(String fieldName, Object value, boolean ignoreNull) {
			if(StringUtils.isEmpty(value))return null;
			return new SimpleExpression (fieldName, value, Operator.GTE);
		}

		/**
		 * 小于等于
		 * @param fieldName
		 * @param value
		 * @param ignoreNull
		 * @return
		 */
		public static SimpleExpression lte(String fieldName, Object value, boolean ignoreNull) {
			if(StringUtils.isEmpty(value))return null;
			return new SimpleExpression (fieldName, value, Operator.LTE);
		}
		
		/**
		 * 并且
		 * @param criterions
		 * @return
		 */
		public static LogicalExpression and(Criterion... criterions){
			return new LogicalExpression(criterions, Operator.AND);
		}
		/**
		 * 或者
		 * @param criterions
		 * @return
		 */
		public static LogicalExpression or(Criterion... criterions){
			return new LogicalExpression(criterions, Operator.OR);
		}
		/**
		 * 包含于
		 * @param fieldName
		 * @param value
		 * @return
		 */
		@SuppressWarnings("rawtypes")
		public static LogicalExpression in(String fieldName, Collection value, boolean ignoreNull) {
			if(ignoreNull&&(value==null||value.isEmpty())){
				return null;
			}
			SimpleExpression[] ses = new SimpleExpression[value.size()];
			int i=0;
			for(Object obj : value){
				ses[i]=new SimpleExpression(fieldName,obj,Operator.EQ);
				i++;
			}
			return new LogicalExpression(ses,Operator.OR);
		}
		/**
		 * 为空
		 * @param fieldName 字段名
		 * @return
		 */
		public static SimpleExpression isNull(String fieldName) {
			return new SimpleExpression (fieldName, null, Operator.NULL);
		}
		/**
		 * 不为空
		 * @param fieldName 字段名
		 * @return
		 */
		public static SimpleExpression isNotNull(String fieldName) {
			return new SimpleExpression (fieldName, null, Operator.NOTNULL);
		}
		
		/**
		 * 模糊匹配不等
		 * @param fieldName
		 * @param value
		 * @param ignoreNull
		 * @return
		 */
		public static SimpleExpression notLike(String fieldName, String value, boolean ignoreNull) {
			if(StringUtils.isEmpty(value))return null;
			return new SimpleExpression (fieldName, value, Operator.NLIKE);
		}
}
