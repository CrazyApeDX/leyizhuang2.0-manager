package com.ynyes.fitment.core.entity.persistent.table;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

import org.springframework.format.annotation.DateTimeFormat;

import com.ynyes.fitment.core.entity.persistent.PersistentEntity;

/**
 * 表持久化模型类，提供公共属性
 * @author dengxiao
 */
@MappedSuperclass
public abstract class TableEntity extends PersistentEntity{

	/**
	 * 数据来源枚举类
	 * 具有：
	 * 	① DEVELOP，开发人员通过SQL添加；
	 *  ② BUSINESS，根据业务流转而产生的业务
	 *  ③ ADD，后台管理员添加
	 *  ④ RECEIVE，通过接口接收而来的数据
	 * @author dengxiao
	 */
	public enum OriginType {
		DEVELOP, BUSINESS, ADD, RECEIVE, QRCODE
	}
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@Enumerated(EnumType.STRING)
	private OriginType createOrigin = OriginType.BUSINESS;
	
	@Column(nullable = false, updatable = false)
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date createTime = new Date();

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public OriginType getCreateOrigin() {
		return createOrigin;
	}

	public void setCreateOrigin(OriginType createOrigin) {
		this.createOrigin = createOrigin;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
}
