package com.ynyes.lyz.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.springframework.format.annotation.DateTimeFormat;

@Entity
public class TdMemberRating {

	// 自增主键
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	// 城市编码
	@Column(length = 20, nullable = false)
	private Long cityCode;
	
	// 等级编码
	@Column(length = 20, nullable = false)
	private String ratingCode;
	
	// 等级名称
	@Column(length = 20, nullable = false)
	private String ratingName;	
	
	//所属等级（最低为1级）
	@Column(length = 2, nullable = false)
	private Integer ratingNum;
		
	//华润价目表id
	@Column(length = 20, nullable = false)
	private Long HrListHeaderId;

	//乐意装价目表id
	@Column(length = 20, nullable = false)
	private Long LyzListHeaderId;
	
	//莹润价目表id
	@Column(length = 20, nullable = false)
	private Long YrListHeaderId;
	
	@Column
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date startTime;
	
	@Column
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date endTime;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getCityCode() {
		return cityCode;
	}

	public void setCityCode(Long cityCode) {
		this.cityCode = cityCode;
	}

	public String getRatingCode() {
		return ratingCode;
	}

	public void setRatingCode(String ratingCode) {
		this.ratingCode = ratingCode;
	}

	public String getRatingName() {
		return ratingName;
	}

	public void setRatingName(String ratingName) {
		this.ratingName = ratingName;
	}

	public Integer getRatingNum() {
		return ratingNum;
	}

	public void setRatingNum(Integer ratingNum) {
		this.ratingNum = ratingNum;
	}

	public Long getHrListHeaderId() {
		return HrListHeaderId;
	}

	public void setHrListHeaderId(Long hrListHeaderId) {
		HrListHeaderId = hrListHeaderId;
	}

	public Long getLyzListHeaderId() {
		return LyzListHeaderId;
	}

	public void setLyzListHeaderId(Long lyzListHeaderId) {
		LyzListHeaderId = lyzListHeaderId;
	}

	public Long getYrListHeaderId() {
		return YrListHeaderId;
	}

	public void setYrListHeaderId(Long yrListHeaderId) {
		YrListHeaderId = yrListHeaderId;
	}

	public Date getStartTime() {
		return startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}
}
