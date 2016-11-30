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
 *  退货入库单 主档
 * 
 * @author Sharon
 *
 */

@Entity
public class TdBackMain {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	// 仓库编号
	@Column
	private String whNo;

	// 委托业主
	@Column
	private String ownerNo;

	// 返配验收单号
	@Column
	private String recNo;

	// 打印次数
	@Column
	private Integer printTimes;

	// 返配单号
	@Column
	private String backNo;

	// 返配单类型（一般返配）
	@Column
	private String backType;

	// 返配单分类(存储型、越库型)
	@Column
	private String backClass;

	// 客户信息
	@Column
	private String customerNo;

	// 月台
	@Column
	private String platNo;

	// 验收人员
	@Column
	private String recUser;

	// 操作工具(表单,pda,电子标签)
	@Column
	private String opTools;

	// 操作状态(初始、作业中、完成、结案)
	@Column
	private String opStatus;

	// 备注
	@Column
	private String note;

	// 建立人员
	@Column
	private String mkUserno;

	// 修改人员
	@Column
	private String modifiedUserno;

	// 门店退单
	@Column
	private String poNo;

	// 开始操作时间
	@Column
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@Temporal(TemporalType.TIMESTAMP)
	private Date beginDt;

	// 结束操作时间
	@Column
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@Temporal(TemporalType.TIMESTAMP)
	private Date endDt;
	
	//配送人员
	@Column
	private String driver;
	
	// 建立时间
	@Column
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@Temporal(TemporalType.TIMESTAMP)
	private Date mkDt;
	
	// 修改时间
	@Column
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@Temporal(TemporalType.TIMESTAMP)
	private Date modifiedDt;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getWhNo() {
		return whNo;
	}

	public void setWhNo(String whNo) {
		this.whNo = whNo;
	}

	public String getOwnerNo() {
		return ownerNo;
	}

	public void setOwnerNo(String ownerNo) {
		this.ownerNo = ownerNo;
	}

	public String getRecNo() {
		return recNo;
	}

	public void setRecNo(String recNo) {
		this.recNo = recNo;
	}

	public Integer getPrintTimes() {
		return printTimes;
	}

	public void setPrintTimes(Integer printTimes) {
		this.printTimes = printTimes;
	}

	public String getBackNo() {
		return backNo;
	}

	public void setBackNo(String backNo) {
		this.backNo = backNo;
	}

	public String getBackType() {
		return backType;
	}

	public void setBackType(String backType) {
		this.backType = backType;
	}

	public String getBackClass() {
		return backClass;
	}

	public void setBackClass(String backClass) {
		this.backClass = backClass;
	}

	public String getCustomerNo() {
		return customerNo;
	}

	public void setCustomerNo(String customerNo) {
		this.customerNo = customerNo;
	}

	public String getPlatNo() {
		return platNo;
	}

	public void setPlatNo(String platNo) {
		this.platNo = platNo;
	}

	public String getRecUser() {
		return recUser;
	}

	public void setRecUser(String recUser) {
		this.recUser = recUser;
	}

	public String getOpTools() {
		return opTools;
	}

	public void setOpTools(String opTools) {
		this.opTools = opTools;
	}

	public String getOpStatus() {
		return opStatus;
	}

	public void setOpStatus(String opStatus) {
		this.opStatus = opStatus;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public String getMkUserno() {
		return mkUserno;
	}

	public void setMkUserno(String mkUserno) {
		this.mkUserno = mkUserno;
	}

	public String getModifiedUserno() {
		return modifiedUserno;
	}

	public void setModifiedUserno(String modifiedUserno) {
		this.modifiedUserno = modifiedUserno;
	}

	public String getPoNo() {
		return poNo;
	}

	public void setPoNo(String poNo) {
		this.poNo = poNo;
	}
	
	public String getDriver() {
		return driver;
	}

	public void setDriver(String driver) {
		this.driver = driver;
	}

	public Date getBeginDt() {
		return beginDt;
	}

	public void setBeginDt(Date beginDt) {
		this.beginDt = beginDt;
	}

	public Date getEndDt() {
		return endDt;
	}

	public void setEndDt(Date endDt) {
		this.endDt = endDt;
	}

	public Date getMkDt() {
		return mkDt;
	}

	public void setMkDt(Date mkDt) {
		this.mkDt = mkDt;
	}

	public Date getModifiedDt() {
		return modifiedDt;
	}

	public void setModifiedDt(Date modifiedDt) {
		this.modifiedDt = modifiedDt;
	}
	
}
