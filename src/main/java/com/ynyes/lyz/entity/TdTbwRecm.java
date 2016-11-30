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
 * wms采购入库的主档
 * 
 * @author Sharon
 *
 */

@Entity
public class TdTbwRecm {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	// 仓库编号
	@Column(length = 10)
	private String cWhNo;

	// 委托业主
	@Column(length = 10)
	private String cOwnerNo;

	// 验收单号(当类型是红冲时为:红冲单号)
	@Column(length = 30)
	private String cRecNo;

	// 打印次数
	@Column
	private Integer cPrintTimes ;

	// 验收汇总
	@Column(length = 30)
	private String cGatherRecNo;

	// 进货汇总
	@Column(length = 30)
	private String cGatherNo;

	// 进货单分类(存储型、越库型)
	@Column(length = 10)
	private String cInClass;

	// 进货单号(当类型是红冲时为:验收单号)
	@Column(length = 30)
	private String cInNo;

	// 进货单类型(一般订单，紧急订单,红冲多，红冲少)
	@Column(length = 10)
	private String cInType;

	// 供应商编码
	@Column(length = 30)
	private String cProviderNo;

	// 月台
	@Column(length = 30)
	private String cPlatNo;

	// 验收人员
	@Column(length = 10)
	private String cRecUserno;

	// 操作工具(表单,pda,电子标签)
	@Column(length = 10)
	private String cOpTools;

	// 操作状态(初始、作业中、完成、结案)
	@Column(length = 10)
	private String cOpStatus;

	// 开始操作时间
	@Column
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@Temporal(TemporalType.TIMESTAMP)
	private Date cBeginDt;

	// 结束操作时间
	@Column
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@Temporal(TemporalType.TIMESTAMP)
	private Date cEndDt;

	// 备注
	@Column(length = 50)
	private String cNote;

	// 建立人员
	@Column(length = 10)
	private String cMkUserno;

	// 建立时间
	@Column
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@Temporal(TemporalType.TIMESTAMP)
	private Date cMkDt;

	// 修改人员
	@Column(length = 10)
	private String cModifiedUserno;

	// 修改时间
	@Column
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@Temporal(TemporalType.TIMESTAMP)
	private Date cModifiedDt;

	// 采购单号
	@Column(length = 30)
	private String cPoNo;

	//分公司Id
	@Column
	private Long cCompanyId;
	
	// 记录产生时间（收到wms信息生成此记录的时间）
	@Column
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@Temporal(TemporalType.TIMESTAMP)
	private Date initTime;

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

	public String getcRecNo() {
		return cRecNo;
	}

	public void setcRecNo(String cRecNo) {
		this.cRecNo = cRecNo;
	}

	public Integer getcPrintTimes() {
		return cPrintTimes;
	}

	public void setcPrintTimes(Integer cPrintTimes) {
		this.cPrintTimes = cPrintTimes;
	}

	public String getcGatherRecNo() {
		return cGatherRecNo;
	}

	public void setcGatherRecNo(String cGatherRecNo) {
		this.cGatherRecNo = cGatherRecNo;
	}

	public String getcGatherNo() {
		return cGatherNo;
	}

	public void setcGatherNo(String cGatherNo) {
		this.cGatherNo = cGatherNo;
	}

	public String getcInClass() {
		return cInClass;
	}

	public void setcInClass(String cInClass) {
		this.cInClass = cInClass;
	}

	public String getcInNo() {
		return cInNo;
	}

	public void setcInNo(String cInNo) {
		this.cInNo = cInNo;
	}

	public String getcInType() {
		return cInType;
	}

	public void setcInType(String cInType) {
		this.cInType = cInType;
	}

	public String getcProviderNo() {
		return cProviderNo;
	}

	public void setcProviderNo(String cProviderNo) {
		this.cProviderNo = cProviderNo;
	}

	public String getcPlatNo() {
		return cPlatNo;
	}

	public void setcPlatNo(String cPlatNo) {
		this.cPlatNo = cPlatNo;
	}

	public String getcRecUserno() {
		return cRecUserno;
	}

	public void setcRecUserno(String cRecUserno) {
		this.cRecUserno = cRecUserno;
	}

	public String getcOpTools() {
		return cOpTools;
	}

	public void setcOpTools(String cOpTools) {
		this.cOpTools = cOpTools;
	}

	public String getcOpStatus() {
		return cOpStatus;
	}

	public void setcOpStatus(String cOpStatus) {
		this.cOpStatus = cOpStatus;
	}

	public Date getcBeginDt() {
		return cBeginDt;
	}

	public void setcBeginDt(Date cBeginDt) {
		this.cBeginDt = cBeginDt;
	}

	public Date getcEndDt() {
		return cEndDt;
	}

	public void setcEndDt(Date cEndDt) {
		this.cEndDt = cEndDt;
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

	public String getcPoNo() {
		return cPoNo;
	}

	public void setcPoNo(String cPoNo) {
		this.cPoNo = cPoNo;
	}

	public Long getcCompanyId()
	{
		return cCompanyId;
	}

	public void setcCompanyId(Long cCompanyId)
	{
		this.cCompanyId = cCompanyId;
	}

	public Date getInitTime() {
		return initTime;
	}

	public void setInitTime(Date initTime) {
		this.initTime = initTime;
	}
	
}
