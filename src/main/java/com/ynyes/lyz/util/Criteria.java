package com.ynyes.lyz.util;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.jpa.domain.Specification;

/**
 * 定义一个查询条件容器
 * @author zp
 *
 * @param <T>
 */
public class Criteria<T> implements Specification<T> {

		private List<Criterion> criterions = new ArrayList<Criterion>();
		private String orderByAsc="";
		private String orderByDesc="";

		public Predicate toPredicate(Root<T> root, CriteriaQuery<?> query,
				CriteriaBuilder builder) {
			if(!"".equals(orderByAsc)){
				query.orderBy(builder.asc(root.get(orderByAsc)));
			}
			if(!"".equals(orderByDesc)){
				query.orderBy(builder.desc(root.get(orderByDesc)));
			}
			
			if (!criterions.isEmpty()) {
				List<Predicate> predicates = new ArrayList<Predicate>();
				for(Criterion c : criterions){
					predicates.add(c.toPredicate(root, query,builder));
				}
				// 将所有条件用 and 联合起来
				if (predicates.size() > 0) {
					return builder.and(predicates.toArray(new Predicate[predicates.size()]));
				}
			}
			return builder.conjunction();
		}
		/**
		 * 增加简单条件表达式
		 * @Methods Name add
		 * @Create In 2012-2-8 By lee
		 * @param expression0 void
		 */
		public void add(Criterion criterion){
			if(criterion!=null){
				criterions.add(criterion);
			}
		}
		
		public void setOrderByAsc(String orderByAsc) {
			this.orderByAsc = orderByAsc;
		}
		public void setOrderByDesc(String orderByDesc) {
			this.orderByDesc = orderByDesc;
		}
		
		

}
