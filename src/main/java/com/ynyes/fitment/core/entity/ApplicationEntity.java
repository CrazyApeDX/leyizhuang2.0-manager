package com.ynyes.fitment.core.entity;

import java.io.Serializable;
import java.lang.reflect.Field;

import org.apache.commons.lang.ArrayUtils;

/**
 * 公共实体信息类
 * 将toString方法final化，以JSON数据的形式输入对象信息
 * 同时实现序列化接口，以便于缓存
 * @author dengxiao
 */

public abstract class ApplicationEntity implements Serializable {

	@Override
	public String toString() {
		StringBuffer buffer = new StringBuffer();
		Class<? extends ApplicationEntity> clazz = this.getClass();
		buffer.append(clazz.getName()).append(":").append("{");
		buffer.append(this.descSelf(clazz));
		buffer.append("}");
		return buffer.toString();
	}

	private String descSelf(Class<?> clazz) {
		StringBuffer buffer = new StringBuffer();
		Class<?> superClazz = clazz.getSuperclass();
		if (!superClazz.getName().equals(ApplicationEntity.class.getName())) {
			buffer.append(this.descSelf(superClazz));
		}
		buffer.append(this.getFieldAndValue(clazz));
		return buffer.toString();
	}

	private String getFieldAndValue(Class<?> clazz) {
		StringBuffer buffer = new StringBuffer();
		Field[] fields = clazz.getDeclaredFields();
		if (!ArrayUtils.isEmpty(fields)) {
			for (Field field : fields) {
				field.setAccessible(true);
				String name = null;
				Object value = null;
				name = field.getName();
				try {
					value = field.get(this);
				} catch (Exception e) {
					e.printStackTrace();
				}
				buffer.append(name).append(" : ").append(null == value ? "NULL" : value.toString()).append(";");
			}
		}
		return buffer.toString();
	}
}
