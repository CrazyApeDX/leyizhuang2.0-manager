package com.ynyes.lyz.interfaces.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.springframework.format.annotation.DateTimeFormat;

/**
 * wms整转零
 * @author yanle
 *
 */

@Entity
public class TdTbwWholeSepDirection extends TdInfBaseEntity
{
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	//仓库编号
	@Column(length = 10)
	private String cWhNo;

	//委托业主
	@Column(length = 10)
	private String cOwnerNo;

	//指示编号
	@Column(length = 30)
	private String cDirectNo;
	
	//批示ID
	@Column
	private Long cDirectId;
	
	//任务类型
	@Column(length=10)
	private String cTaskType;
	
	//作业类型
	@Column(length=10)
	private String cOpType;
	
	//源储位
	@Column(length=30)
	private String cSLocationNo;
	
	//源储位id
	@Column
	private Long cSLocationId;
	
	//源容器内部板号
	@Column(length=30)
	private String cSContainerSerno;
	
	//源容器板号
	@Column(length=30)
	private String cSContainerNo;
	
	//目的储位
	@Column(length=30)
	private String cDLocationNo;
	
	//目的储位ID
	@Column
	private Long cDLocationId;
	
	//目的容器内部板号
	@Column(length=30)
	private String cDContainerSerno;
	
	//目的容器
	@Column(length=30)
	private String cDContainerNo;
	
	//商品编号
	@Column(length=30)
	private String cGcode;
	
	@Column(length=30)
	private String cDGcode;
	
	//库存属性
	@Column
	private Long cStockattrId;
	
	//包装数量
	@Column
	private Long cPackQty;
	
	//源单号
	@Column(length=30)
	private String cSourceNo; 
	
	//数量
	@Column(length=16,scale=2)
	private Double cQty;
	
	
	@Column(length=16,scale=2)
	private Double cInQty;
	
	//波次号
	@Column(length=30)
	private String cWaveNo;
	
	//状态（初始、已分配）
	@Column(length=10)
	private String cStatus;
	
	//任务编号
	@Column(length=30)
	private String cTaskNo;
	
	//任务id
	@Column
	private Long cTaskId;
	
	//预留1
	@Column(length=50)
	private String cReserved1;
	
	//预留2
	@Column(length=50)
	private String cReserved2;
	
	//预留3
	@Column(length=50)
	private String cReserved3;
	
	//预留4
	@Column(length=50)
	private String cReserved4;
	
	//预留5
	@Column(length=50)
	private String cReserved5;
	
	//备注
	@Column(length = 50)
	private String cNote;

	// 开始操作时间
	@Column
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date cBeginDt;

	// 结束操作时间
	@Column
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date cEndDt;
	
	// 建立人员
	@Column(length = 10)
	private String cMkUserno;
	
	// 建立时间
	@Column
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date cMkDt;
	

	//修改人员
	@Column(length = 10)
	private String cModifiedUserno;

	//修改时间
	@Column
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date cModifiedDt;
	
	// 分公司Id
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

	public String getcDirectNo() {
		return cDirectNo;
	}

	public void setcDirectNo(String cDirectNo) {
		this.cDirectNo = cDirectNo;
	}

	public Long getcDirectId() {
		return cDirectId;
	}

	public void setcDirectId(Long cDirectId) {
		this.cDirectId = cDirectId;
	}

	public String getcTaskType() {
		return cTaskType;
	}

	public void setcTaskType(String cTaskType) {
		this.cTaskType = cTaskType;
	}

	public String getcOpType() {
		return cOpType;
	}

	public void setcOpType(String cOpType) {
		this.cOpType = cOpType;
	}

	public String getcSLocationNo() {
		return cSLocationNo;
	}

	public void setcSLocationNo(String cSLocationNo) {
		this.cSLocationNo = cSLocationNo;
	}

	public Long getcSLocationId() {
		return cSLocationId;
	}

	public void setcSLocationId(Long cSLocationId) {
		this.cSLocationId = cSLocationId;
	}

	public String getcSContainerSerno() {
		return cSContainerSerno;
	}

	public void setcSContainerSerno(String cSContainerSerno) {
		this.cSContainerSerno = cSContainerSerno;
	}

	public String getcSContainerNo() {
		return cSContainerNo;
	}

	public void setcSContainerNo(String cSContainerNo) {
		this.cSContainerNo = cSContainerNo;
	}

	public String getcDLocationNo() {
		return cDLocationNo;
	}

	public void setcDLocationNo(String cDLocationNo) {
		this.cDLocationNo = cDLocationNo;
	}

	public Long getcDLocationId() {
		return cDLocationId;
	}

	public void setcDLocationId(Long cDLocationId) {
		this.cDLocationId = cDLocationId;
	}

	public String getcDContainerSerno() {
		return cDContainerSerno;
	}

	public void setcDContainerSerno(String cDContainerSerno) {
		this.cDContainerSerno = cDContainerSerno;
	}

	public String getcDContainerNo() {
		return cDContainerNo;
	}

	public void setcDContainerNo(String cDContainerNo) {
		this.cDContainerNo = cDContainerNo;
	}

	public String getcGcode() {
		return cGcode;
	}

	public void setcGcode(String cGcode) {
		this.cGcode = cGcode;
	}

	public String getcDGcode() {
		return cDGcode;
	}

	public void setcDGcode(String cDGcode) {
		this.cDGcode = cDGcode;
	}

	public Long getcStockattrId() {
		return cStockattrId;
	}

	public void setcStockattrId(Long cStockattrId) {
		this.cStockattrId = cStockattrId;
	}

	public Long getcPackQty() {
		return cPackQty;
	}

	public void setcPackQty(Long cPackQty) {
		this.cPackQty = cPackQty;
	}

	public String getcSourceNo() {
		return cSourceNo;
	}

	public void setcSourceNo(String cSourceNo) {
		this.cSourceNo = cSourceNo;
	}

	public Double getcQty() {
		return cQty;
	}

	public void setcQty(Double cQty) {
		this.cQty = cQty;
	}

	public Double getcInQty() {
		return cInQty;
	}

	public void setcInQty(Double cInQty) {
		this.cInQty = cInQty;
	}

	public String getcWaveNo() {
		return cWaveNo;
	}

	public void setcWaveNo(String cWaveNo) {
		this.cWaveNo = cWaveNo;
	}

	public String getcStatus() {
		return cStatus;
	}

	public void setcStatus(String cStatus) {
		this.cStatus = cStatus;
	}

	public String getcTaskNo() {
		return cTaskNo;
	}

	public void setcTaskNo(String cTaskNo) {
		this.cTaskNo = cTaskNo;
	}

	public Long getcTaskId() {
		return cTaskId;
	}

	public void setcTaskId(Long cTaskId) {
		this.cTaskId = cTaskId;
	}

	public String getcReserved1() {
		return cReserved1;
	}

	public void setcReserved1(String cReserved1) {
		this.cReserved1 = cReserved1;
	}

	public String getcReserved2() {
		return cReserved2;
	}

	public void setcReserved2(String cReserved2) {
		this.cReserved2 = cReserved2;
	}

	public String getcReserved3() {
		return cReserved3;
	}

	public void setcReserved3(String cReserved3) {
		this.cReserved3 = cReserved3;
	}

	public String getcReserved4() {
		return cReserved4;
	}

	public void setcReserved4(String cReserved4) {
		this.cReserved4 = cReserved4;
	}

	public String getcReserved5() {
		return cReserved5;
	}

	public void setcReserved5(String cReserved5) {
		this.cReserved5 = cReserved5;
	}

	public String getcNote() {
		return cNote;
	}

	public void setcNote(String cNote) {
		this.cNote = cNote;
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

	public Long getcCompanyId() {
		return cCompanyId;
	}

	public void setcCompanyId(Long cCompanyId) {
		this.cCompanyId = cCompanyId;
	}

	@Override
	public String toString() {
		return "TdTbwWholeSepDirection [id=" + id + ", cWhNo=" + cWhNo + ", cOwnerNo=" + cOwnerNo + ", cDirectNo="
				+ cDirectNo + ", cDirectId=" + cDirectId + ", cTaskType=" + cTaskType + ", cOpType=" + cOpType
				+ ", cSLocationNo=" + cSLocationNo + ", cSLocationId=" + cSLocationId + ", cSContainerSerno="
				+ cSContainerSerno + ", cSContainerNo=" + cSContainerNo + ", cDLocationNo=" + cDLocationNo
				+ ", cDLocationId=" + cDLocationId + ", cDContainerSerno=" + cDContainerSerno + ", cDContainerNo="
				+ cDContainerNo + ", cGcode=" + cGcode + ", cDGcode=" + cDGcode + ", cStockattrId=" + cStockattrId
				+ ", cPackQty=" + cPackQty + ", cSourceNo=" + cSourceNo + ", cQty=" + cQty + ", cInQty=" + cInQty
				+ ", cWaveNo=" + cWaveNo + ", cStatus=" + cStatus + ", cTaskNo=" + cTaskNo + ", cTaskId=" + cTaskId
				+ ", cReserved1=" + cReserved1 + ", cReserved2=" + cReserved2 + ", cReserved3=" + cReserved3
				+ ", cReserved4=" + cReserved4 + ", cReserved5=" + cReserved5 + ", cNote=" + cNote + ", cBeginDt="
				+ cBeginDt + ", cEndDt=" + cEndDt + ", cMkUserno=" + cMkUserno + ", cMkDt=" + cMkDt
				+ ", cModifiedUserno=" + cModifiedUserno + ", cModifiedDt=" + cModifiedDt + ", cCompanyId=" + cCompanyId
				+ "]";
	}

	
	
	

	
}
