package com.ynyes.lyz.interfaces.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.springframework.format.annotation.DateTimeFormat;

/**
 * 调拨入库明细
 * @author 
 *
 */
@Entity
public class TdTbOmD extends TdInfBaseEntity
{
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	//委托业主
	@Column(length = 10)
	private String cOwnerNo;

	//调拨单号
	@Column(length = 30)
	private String cOmNo;

	//调拨ID
	@Column
	private Long cOmId;

	//调拨单类型(一般调拨)
	@Column(length = 10)
	private String cOmType;

	//商品
	@Column(length = 30)
	private String cGcode;

	//委托业主商品
	@Column(length = 30)
	private String cOwnerGcode;

	//包装数量
	@Column
	private Long cPackQty;

	//数量
	@Column
	private Double cQty;

	//定位量
	@Column
	private Double cWaveQty;

	//实际出货数量
	@Column
	private Double cAckQty;

	//实际收货数量
	@Column
	private Double cCheckQty;

	//商品单价
	@Column
	private Double cPrice;

	//税率
	@Column
	private Double cTaxRate;

	//作业状态(初始，定位，发单，完成)
	@Column(length = 10)
	private String cOpStatus;

	//品质（良品、不良品、赠品）
	@Column(length = 10)
	private String cItemType;

	//预留1
	@Column(length = 50)
	private String cReserved1;

	//预留2
	@Column(length = 50)
	private String cReserved2;

	//预留3
	@Column(length = 50)
	private String cReserved3;

	//预留4
	@Column(length = 50)
	private String cReserved4;

	//预留5
	@Column(length = 50)
	private String cReserved5;

	//备注
	@Column(length = 50)
	private String cNote;

	//出货人员
	@Column(length = 20)
	private String cOutUserno;

	//出货时间
	@Column
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date cOutDt;

	//收货人员
	@Column(length = 20)
	private String cCheckUserno;

	//收货时间
	@Column
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date cCheckDt;

	//生产日期
	@Column
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date cProduceDt;

	//建立人员
	@Column(length = 20)
	private String cMkUserno;

	//建立时间
	@Column
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date cMkDt;

	//修改人员
	@Column(length = 20)
	private String cModifiedUserno;

	//修改时间
	@Column
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date cModifiedDt;

	//上传状态
	@Column(length = 10)
	private String cUploadStatus;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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

	public Long getcOmId() {
		return cOmId;
	}

	public void setcOmId(Long cOmId) {
		this.cOmId = cOmId;
	}

	public String getcOmType() {
		return cOmType;
	}

	public void setcOmType(String cOmType) {
		this.cOmType = cOmType;
	}

	public String getcGcode() {
		return cGcode;
	}

	public void setcGcode(String cGcode) {
		this.cGcode = cGcode;
	}

	public String getcOwnerGcode() {
		return cOwnerGcode;
	}

	public void setcOwnerGcode(String cOwnerGcode) {
		this.cOwnerGcode = cOwnerGcode;
	}

	public Long getcPackQty() {
		return cPackQty;
	}

	public void setcPackQty(Long cPackQty) {
		this.cPackQty = cPackQty;
	}

	public Double getcQty() {
		return cQty;
	}

	public void setcQty(Double cQty) {
		this.cQty = cQty;
	}

	public Double getcWaveQty() {
		return cWaveQty;
	}

	public void setcWaveQty(Double cWaveQty) {
		this.cWaveQty = cWaveQty;
	}

	public Double getcAckQty() {
		return cAckQty;
	}

	public void setcAckQty(Double cAckQty) {
		this.cAckQty = cAckQty;
	}

	public Double getcCheckQty() {
		return cCheckQty;
	}

	public void setcCheckQty(Double cCheckQty) {
		this.cCheckQty = cCheckQty;
	}

	public Double getcPrice() {
		return cPrice;
	}

	public void setcPrice(Double cPrice) {
		this.cPrice = cPrice;
	}

	public Double getcTaxRate() {
		return cTaxRate;
	}

	public void setcTaxRate(Double cTaxRate) {
		this.cTaxRate = cTaxRate;
	}

	public String getcOpStatus() {
		return cOpStatus;
	}

	public void setcOpStatus(String cOpStatus) {
		this.cOpStatus = cOpStatus;
	}

	public String getcItemType() {
		return cItemType;
	}

	public void setcItemType(String cItemType) {
		this.cItemType = cItemType;
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

	public String getcOutUserno() {
		return cOutUserno;
	}

	public void setcOutUserno(String cOutUserno) {
		this.cOutUserno = cOutUserno;
	}

	public Date getcOutDt() {
		return cOutDt;
	}

	public void setcOutDt(Date cOutDt) {
		this.cOutDt = cOutDt;
	}

	public String getcCheckUserno() {
		return cCheckUserno;
	}

	public void setcCheckUserno(String cCheckUserno) {
		this.cCheckUserno = cCheckUserno;
	}

	public Date getcCheckDt() {
		return cCheckDt;
	}

	public void setcCheckDt(Date cCheckDt) {
		this.cCheckDt = cCheckDt;
	}

	public Date getcProduceDt() {
		return cProduceDt;
	}

	public void setcProduceDt(Date cProduceDt) {
		this.cProduceDt = cProduceDt;
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

	@Override
	public String toString() {
		return "TdTbOmD [id=" + id + ", cOwnerNo=" + cOwnerNo + ", cOmNo=" + cOmNo + ", cOmId=" + cOmId + ", cOmType="
				+ cOmType + ", cGcode=" + cGcode + ", cOwnerGcode=" + cOwnerGcode + ", cPackQty=" + cPackQty + ", cQty="
				+ cQty + ", cWaveQty=" + cWaveQty + ", cAckQty=" + cAckQty + ", cCheckQty=" + cCheckQty + ", cPrice="
				+ cPrice + ", cTaxRate=" + cTaxRate + ", cOpStatus=" + cOpStatus + ", cItemType=" + cItemType
				+ ", cReserved1=" + cReserved1 + ", cReserved2=" + cReserved2 + ", cReserved3=" + cReserved3
				+ ", cReserved4=" + cReserved4 + ", cReserved5=" + cReserved5 + ", cNote=" + cNote + ", cOutUserno="
				+ cOutUserno + ", cOutDt=" + cOutDt + ", cCheckUserno=" + cCheckUserno + ", cCheckDt=" + cCheckDt
				+ ", cProduceDt=" + cProduceDt + ", cMkUserno=" + cMkUserno + ", cMkDt=" + cMkDt + ", cModifiedUserno="
				+ cModifiedUserno + ", cModifiedDt=" + cModifiedDt + ", cUploadStatus=" + cUploadStatus + "]";
	}
	
}
