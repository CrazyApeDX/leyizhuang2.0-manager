package com.ynyes.lyz.interfaces.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.springframework.format.annotation.DateTimeFormat;

@Entity
public class TdTbwWasted  extends TdInfBaseEntity
{
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	//单号
	@Column(length = 10)
	private String  cWhNo;

	//委托业主
	@Column(length = 10)
	private String cOwnerNo;

	//损溢单号
	@Column(length = 30)
	private String cWasteNo;

	//损溢ID
	@Column
	private Long cWasteId;

	//损溢单类型(一般报损、一般报溢)
	@Column(length = 10)
	private String cWasteType;

	//商品
	@Column(length = 30)
	private String cGcode;

	//委托业主商品
	@Column(length = 30)
	private String cOwnerGcode;

	//储位编号
	@Column(length = 30)
	private String cLocationNo;

	//商品属性ID
	@Column
	private Long cStockattrId;

	//包装数量
	@Column
	private Long cPackQty;

	//数量
	@Column
	private Double  cQty;

	//批号
	@Column(length = 10)
	private String cLotNo;

	//规格
	@Column(length = 30)
	private String cSpec;

	//产的日期
	@Column
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date cProduceDt;

	//到期日
	@Column
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date cExpireDt;

	//定位量
	@Column
	private Double  cWaveQty;

	//品质(良品、不良品、赠品)
	@Column(length = 10)
	private String cItemType;

	//实际报损数量
	@Column
	private Double  cAckQty;

	//商品单价
	@Column
	private Double  cPrice;

	//税率
	@Column
	private Double  cTaxRate;

	//作业状态(初始，定位，发单，完成)
	@Column(length = 10)
	private String cOpStatus;

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
	
	//分公司Id
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

	public String getcWasteNo() {
		return cWasteNo;
	}

	public void setcWasteNo(String cWasteNo) {
		this.cWasteNo = cWasteNo;
	}

	public Long getcWasteId() {
		return cWasteId;
	}

	public void setcWasteId(Long cWasteId) {
		this.cWasteId = cWasteId;
	}

	public String getcWasteType() {
		return cWasteType;
	}

	public void setcWasteType(String cWasteType) {
		this.cWasteType = cWasteType;
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

	public String getcLocationNo() {
		return cLocationNo;
	}

	public void setcLocationNo(String cLocationNo) {
		this.cLocationNo = cLocationNo;
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

	public Double getcQty() {
		return cQty;
	}

	public void setcQty(Double cQty) {
		this.cQty = cQty;
	}

	public String getcLotNo() {
		return cLotNo;
	}

	public void setcLotNo(String cLotNo) {
		this.cLotNo = cLotNo;
	}

	public String getcSpec() {
		return cSpec;
	}

	public void setcSpec(String cSpec) {
		this.cSpec = cSpec;
	}

	public Date getcProduceDt() {
		return cProduceDt;
	}

	public void setcProduceDt(Date cProduceDt) {
		this.cProduceDt = cProduceDt;
	}

	public Date getcExpireDt() {
		return cExpireDt;
	}

	public void setcExpireDt(Date cExpireDt) {
		this.cExpireDt = cExpireDt;
	}

	public Double getcWaveQty() {
		return cWaveQty;
	}

	public void setcWaveQty(Double cWaveQty) {
		this.cWaveQty = cWaveQty;
	}

	public String getcItemType() {
		return cItemType;
	}

	public void setcItemType(String cItemType) {
		this.cItemType = cItemType;
	}

	public Double getcAckQty() {
		return cAckQty;
	}

	public void setcAckQty(Double cAckQty) {
		this.cAckQty = cAckQty;
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

	public Long getcCompanyId() {
		return cCompanyId;
	}

	public void setcCompanyId(Long cCompanyId) {
		this.cCompanyId = cCompanyId;
	}

	@Override
	public String toString() {
		return "TdTbwWasted [id=" + id + ", cWhNo=" + cWhNo + ", cOwnerNo=" + cOwnerNo + ", cWasteNo=" + cWasteNo
				+ ", cWasteId=" + cWasteId + ", cWasteType=" + cWasteType + ", cGcode=" + cGcode + ", cOwnerGcode="
				+ cOwnerGcode + ", cLocationNo=" + cLocationNo + ", cStockattrId=" + cStockattrId + ", cPackQty="
				+ cPackQty + ", cQty=" + cQty + ", cLotNo=" + cLotNo + ", cSpec=" + cSpec + ", cProduceDt=" + cProduceDt
				+ ", cExpireDt=" + cExpireDt + ", cWaveQty=" + cWaveQty + ", cItemType=" + cItemType + ", cAckQty="
				+ cAckQty + ", cPrice=" + cPrice + ", cTaxRate=" + cTaxRate + ", cOpStatus=" + cOpStatus
				+ ", cReserved1=" + cReserved1 + ", cReserved2=" + cReserved2 + ", cReserved3=" + cReserved3
				+ ", cReserved4=" + cReserved4 + ", cReserved5=" + cReserved5 + ", cNote=" + cNote + ", cMkUserno="
				+ cMkUserno + ", cMkDt=" + cMkDt + ", cModifiedUserno=" + cModifiedUserno + ", cModifiedDt="
				+ cModifiedDt + ", cUploadStatus=" + cUploadStatus + ", cCompanyId=" + cCompanyId + "]";
	}

}
