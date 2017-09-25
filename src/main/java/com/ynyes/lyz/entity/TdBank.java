package com.ynyes.lyz.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 * 可提现银行实体类
 * 
 * @author dengxiao
 */

@Entity
public class TdBank {
	
	public TdBank() {
		super();
	}
	

	public TdBank(String title, Double sortId) {
		super();
		this.title = title;
		this.sortId = sortId;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	// 名称
	@Column
	private String title;

	// 排序号
	@Column
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

	@Override
	public String toString() {
		return "TdBank [id=" + id + ", title=" + title + ", sortId=" + sortId + "]";
	}
}
