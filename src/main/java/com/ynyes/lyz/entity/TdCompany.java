package com.ynyes.lyz.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 * 各地区分公司实体类
 * 
 * @author dengxiao
 */

@Entity
public class TdCompany {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	// 分公司id：由EBS提供
	@Column
	private Long sobIdCompany;
	
	// 分公司名称
	@Column(unique = true)
	private String name;

	// 备注信息
	@Column
	private String remark;

	// 排序号
	@Column
	private Double sortId;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getSobIdCompany() {
		return sobIdCompany;
	}

	public void setSobIdCompany(Long sobIdCompany) {
		this.sobIdCompany = sobIdCompany;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public Double getSortId() {
		return sortId;
	}

	public void setSortId(Double sortId) {
		this.sortId = sortId;
	}

	@Override
	public String toString() {
		return "TdCompany [id=" + id + ", name=" + name + ", remark=" + remark + ", sortId=" + sortId + "]";
	}

}
