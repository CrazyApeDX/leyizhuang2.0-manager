package com.ynyes.lyz.util;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;



/**
 * 简单条件表达式
 * @author zp
 *
 */
public class SimpleExpression implements Criterion {

	
		 private String fieldName;		//属性名
		 private Object value;			//对应值
		 private Operator operator;		//计算符

		protected SimpleExpression(String fieldName, Object value, Operator operator) {
			this.fieldName = fieldName;
			this.value = value;
			this.operator = operator;
		}

		public String getFieldName() {
			return fieldName;
		}
		public Object getValue() {
			return value;
		}
		public Operator getOperator() {
			return operator;
		}
		@SuppressWarnings({ "rawtypes", "unchecked" })
		public Predicate toPredicate(Root<?> root, CriteriaQuery<?> query,
				CriteriaBuilder builder) {
			Path expression = null;
			if(fieldName.contains(".")){
				String[] names =  fieldName.split(".");
				expression = root.get(names[0]);
				for (int i = 1; i < names.length; i++) {
					expression = expression.get(names[i]);
				}
			}else{
				expression = root.get(fieldName);
			}
			
			switch (operator) {
			case EQ:
				return builder.equal(expression, value);
			case NE:
				return builder.notEqual(expression, value);
			case LIKE:
				return builder.like((Expression<String>) expression, "%" + value + "%");
			case LT:
				return builder.lessThan(expression, (Comparable) value);
			case GT:
				return builder.greaterThan(expression, (Comparable) value);
			case LTE:
				return builder.lessThanOrEqualTo(expression, (Comparable) value);
			case GTE:
				return builder.greaterThanOrEqualTo(expression, (Comparable) value);
			case NULL:
				return builder.isNull(expression);
			case NOTNULL:
				return builder.isNotNull(expression);
			case NLIKE:
				return builder.notLike((Expression<String>) expression, "%" + value + "%");
			default:
				return null;
			}
		}

}
