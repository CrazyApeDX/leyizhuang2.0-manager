package com.ynyes.lyz.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * <p>标题：AppManyToOne.java</p>
 * <p>描述：标记App管理的多对一关系字段</p>
 * @author 作者：CrazyDX
 * @version 版本：2016年10月12日上午11:17:51
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.SOURCE)
public @interface AppManyToOne {

	// 目标类
	Class<?> target();
	
	// 关联字段
	String field();
}
