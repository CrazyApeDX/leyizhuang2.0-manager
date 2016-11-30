package com.ynyes.lyz.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.springframework.format.annotation.DateTimeFormat;

/**
 * 仓库
 * @author 华仔
 *
 */
@Entity
public class TdWareHouse {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	//仓库编号
	@Column
	private String whNumber;
	
	//仓库名称
	@Column
	private String whName;
	
	// 创建时间
	@Column
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date creatTime;
	
	@Column
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date updateTime;
	
	// 排序号
	@Column
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Double sortId;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getWhNumber() {
		return whNumber;
	}

	public void setWhNumber(String whNumber) {
		this.whNumber = whNumber;
	}

	public String getWhName() {
		return whName;
	}

	public void setWhName(String whName) {
		this.whName = whName;
	}

	public Date getCreatTime() {
		return creatTime;
	}

	public void setCreatTime(Date creatTime) {
		this.creatTime = creatTime;
	}

	public Double getSortId() {
		return sortId;
	}

	public void setSortId(Double sortId) {
		this.sortId = sortId;
	}
	
	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	
}
