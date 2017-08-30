package com.ynyes.lyz.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.springframework.format.annotation.DateTimeFormat;

/**
 * 红包活动实体类
 * @author panjie
 *
 */

@Entity
public class TdActiveRedPacket {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	//名称
	@Column
	private String name;
	
	// 城市id
	@Column
	private Long cityId;

	// 城市名称
	@Column
	private String cityName;
	
	// 活动开始时间
	@Column
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@Temporal(TemporalType.TIMESTAMP)
	private Date beginDate;

	// 活动结束时间
	@Column
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@Temporal(TemporalType.TIMESTAMP)
	private Date finishDate;
	
	// 红包使用开始时间
	@Column
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@Temporal(TemporalType.TIMESTAMP)
	private Date useBeginDate;

	// 红包使用结束时间
	@Column
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@Temporal(TemporalType.TIMESTAMP)
	private Date useFinishDate;
	
	// 红包金额
	@Column
	private Double price;

	// 使用红包需要满足标准金额
	@Column
	private Double totalPrice;
	
	// 红包图片
	@Column
	private String redPacketImg;

	
	// 排序号
	@Column
	private Double sortId;
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getCityId() {
		return cityId;
	}

	public void setCityId(Long cityId) {
		this.cityId = cityId;
	}

	public String getCityName() {
		return cityName;
	}

	public void setCityName(String cityName) {
		this.cityName = cityName;
	}

	public Date getBeginDate() {
		return beginDate;
	}

	public void setBeginDate(Date beginDate) {
		this.beginDate = beginDate;
	}

	public Date getFinishDate() {
		return finishDate;
	}

	public void setFinishDate(Date finishDate) {
		this.finishDate = finishDate;
	}

	public Date getUseBeginDate() {
		return useBeginDate;
	}

	public void setUseBeginDate(Date useBeginDate) {
		this.useBeginDate = useBeginDate;
	}

	public Date getUseFinishDate() {
		return useFinishDate;
	}

	public void setUseFinishDate(Date useFinishDate) {
		this.useFinishDate = useFinishDate;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	public String getRedPacketImg() {
		return redPacketImg;
	}

	public void setRedPacketImg(String redPacketImg) {
		this.redPacketImg = redPacketImg;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Double getSortId() {
		return sortId;
	}

	public void setSortId(Double sortId) {
		this.sortId = sortId;
	}

	public Double getTotalPrice() {
		return totalPrice;
	}

	public void setTotalPrice(Double totalPrice) {
		this.totalPrice = totalPrice;
	}
	
}
