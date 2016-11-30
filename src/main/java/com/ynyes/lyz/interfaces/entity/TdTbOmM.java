package com.ynyes.lyz.interfaces.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.springframework.format.annotation.DateTimeFormat;

/**
 * 调拨入库主档
 * @author 
 *
 */

@Entity
public class TdTbOmM extends TdInfBaseEntity
{
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	//仓库
	@Column(length = 10)
	private String cWhNo;
	
	//委托业主
	@Column(length = 10)
	private String cOwnerNo;
	
	//调拨单号
	@Column(length = 30)
	private String cOmNo;
	
	//调拨单类型(一般调拨)
	@Column(length = 10)
	private String cOmType;
	
	//目的仓
	@Column(length = 30)
	private String cDWhNo;
	
	//原单号类型(一般调拨)
	@Column(length = 10)
	private String cPoType;
	
	//原单号
	@Column(length = 30)
	private String cPoNo;
	
	//地址
	@Column(length = 200)
	private String cAddress;
	
	//邮编
	@Column(length = 10)
	private String cPostCode;
	
	//总金额
	@Column
	private Double cTotalMoney;
	
	//总税额
	@Column
	private Double cTotalTax;
	
	//退货日期
	@Column
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date cUmoutDt;
	
	//作业状态(初始，定位，发单，完成)
	@Column(length = 10)
	private String cOpStatus;
	
	//创建识标（WMS,ERP）
	@Column(length = 10)
	private String cCreateFlag;
	
	//备注
	@Column(length = 50)
	private String cNote;
	
	//建立人
	@Column(length = 10)
	private String cMkUserno;
	
	//建立时间
	@Column
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date cMkDt;
	
	//修改人
	@Column(length = 20)
	private String cModifiedUserno;
	
	//修改时间
	@Column
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date cModifiedDt;
	
	//上传状态
	@Column(length = 10)
	private String cUploadStatus;
	
	//上传文档
	@Column(length = 50)
	private String cUploadFilename;
	
	//城市id
	@Column
	private Long cCompanyId;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getcWhNo() {
		return cWhNo;
	}

	public void setcWhNo(String cWhNo) {
		this.cWhNo = cWhNo;
	}

	public String getcOwnerNo() {
		return cOwnerNo;
	}

	public void setcOwnerNo(String cOwnerNo) {
		this.cOwnerNo = cOwnerNo;
	}

	public String getcOmNo() {
		return cOmNo;
	}

	public void setcOmNo(String cOmNo) {
		this.cOmNo = cOmNo;
	}

	public String getcOmType() {
		return cOmType;
	}

	public void setcOmType(String cOmType) {
		this.cOmType = cOmType;
	}

	public String getcDWhNo() {
		return cDWhNo;
	}

	public void setcDWhNo(String cDWhNo) {
		this.cDWhNo = cDWhNo;
	}

	public String getcPoType() {
		return cPoType;
	}

	public void setcPoType(String cPoType) {
		this.cPoType = cPoType;
	}

	public String getcPoNo() {
		return cPoNo;
	}

	public void setcPoNo(String cPoNo) {
		this.cPoNo = cPoNo;
	}

	public String getcAddress() {
		return cAddress;
	}

	public void setcAddress(String cAddress) {
		this.cAddress = cAddress;
	}

	public String getcPostCode() {
		return cPostCode;
	}

	public void setcPostCode(String cPostCode) {
		this.cPostCode = cPostCode;
	}

	public Double getcTotalMoney() {
		return cTotalMoney;
	}

	public void setcTotalMoney(Double cTotalMoney) {
		this.cTotalMoney = cTotalMoney;
	}

	public Double getcTotalTax() {
		return cTotalTax;
	}

	public void setcTotalTax(Double cTotalTax) {
		this.cTotalTax = cTotalTax;
	}

	public Date getcUmoutDt() {
		return cUmoutDt;
	}

	public void setcUmoutDt(Date cUmoutDt) {
		this.cUmoutDt = cUmoutDt;
	}

	public String getcOpStatus() {
		return cOpStatus;
	}

	public void setcOpStatus(String cOpStatus) {
		this.cOpStatus = cOpStatus;
	}

	public String getcCreateFlag() {
		return cCreateFlag;
	}

	public void setcCreateFlag(String cCreateFlag) {
		this.cCreateFlag = cCreateFlag;
	}

	public String getcNote() {
		return cNote;
	}

	public void setcNote(String cNote) {
		this.cNote = cNote;
	}

	public String getcMkUserno() {
		return cMkUserno;
	}

	public void setcMkUserno(String cMkUserno) {
		this.cMkUserno = cMkUserno;
	}

	public Date getcMkDt() {
		return cMkDt;
	}

	public void setcMkDt(Date cMkDt) {
		this.cMkDt = cMkDt;
	}

	public String getcModifiedUserno() {
		return cModifiedUserno;
	}

	public void setcModifiedUserno(String cModifiedUserno) {
		this.cModifiedUserno = cModifiedUserno;
	}

	public Date getcModifiedDt() {
		return cModifiedDt;
	}

	public void setcModifiedDt(Date cModifiedDt) {
		this.cModifiedDt = cModifiedDt;
	}

	public String getcUploadStatus() {
		return cUploadStatus;
	}

	public void setcUploadStatus(String cUploadStatus) {
		this.cUploadStatus = cUploadStatus;
	}

	public String getcUploadFilename() {
		return cUploadFilename;
	}

	public void setcUploadFilename(String cUploadFilename) {
		this.cUploadFilename = cUploadFilename;
	}

	public Long getcCompanyId() {
		return cCompanyId;
	}

	public void setcCompanyId(Long cCompanyId) {
		this.cCompanyId = cCompanyId;
	}

	@Override
	public String toString() {
		return "TdTbOmM [id=" + id + ", cWhNo=" + cWhNo + ", cOwnerNo=" + cOwnerNo + ", cOmNo=" + cOmNo + ", cOmType="
				+ cOmType + ", cDWhNo=" + cDWhNo + ", cPoType=" + cPoType + ", cPoNo=" + cPoNo + ", cAddress="
				+ cAddress + ", cPostCode=" + cPostCode + ", cTotalMoney=" + cTotalMoney + ", cTotalTax=" + cTotalTax
				+ ", cUmoutDt=" + cUmoutDt + ", cOpStatus=" + cOpStatus + ", cCreateFlag=" + cCreateFlag + ", cNote="
				+ cNote + ", cMkUserno=" + cMkUserno + ", cMkDt=" + cMkDt + ", cModifiedUserno=" + cModifiedUserno
				+ ", cModifiedDt=" + cModifiedDt + ", cUploadStatus=" + cUploadStatus + ", cUploadFilename="
				+ cUploadFilename + ", cCompanyId=" + cCompanyId + "]";
	}
	
}
