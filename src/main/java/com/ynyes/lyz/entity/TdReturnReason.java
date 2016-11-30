package com.ynyes.lyz.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 * <p>标题：TdReturnReason.java</p>
 * <p>描述：退货原因实体类</p>
 * <p>公司：http://www.ynsite.com</p>
 * @author 作者：DengXiao
 * @version 版本：上午10:26:34
 */
@Entity
public class TdReturnReason {

	public TdReturnReason() {
		super();
	}

	public TdReturnReason(Long id, String title, String remark, Double sortId) {
		super();
		this.id = id;
		this.title = title;
		this.remark = remark;
		this.sortId = sortId;
	}

	// 自增主键
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	// 标题
	@Column
	private String title;
	
	// 备注
	@Column
	private String remark;

	// 排序号
	@Column(scale = 2)
	private Double sortId;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Double getSortId() {
		return sortId;
	}

	public void setSortId(Double sortId) {
		this.sortId = sortId;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	@Override
	public String toString() {
		return "TdReturnReason [id=" + id + ", title=" + title + ", remark=" + remark + ", sortId=" + sortId + "]";
	}
}
