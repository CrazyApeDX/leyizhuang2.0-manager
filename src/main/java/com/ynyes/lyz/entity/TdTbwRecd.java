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
 * wms入库的明细档
 * 
 * @author Sharon
 *
 */

@Entity
public class TdTbwRecd {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	// 委托业主	
	@Column(length = 10)
	private String ownerNo;

	//  任务编号
	@Column(length = 30)
	private String recNo;

	// 任务id
	@Column
	private Long recId;

	// 商品编号
	@Column(length = 30)
	private String gcode;

	// 包装数量
	@Column
	private Long packQty;

	// 价格
	@Column
	private String price;
	
	// 品质(良品、不良品、赠品)
	@Column(length = 10)
	private String itemType;
	
	//验收赠品数量
	@Column
	private String giftQty;

	// 验收赠品不良品数量
	@Column
	private String badQty;

	// 验收数量(红冲数量)
	@Column
	private String recQty;

	// 作业人员
	@Column(length = 10)
	private String recUserno;

	//月台
	@Column(length = 30)
	private String platNo;

	// 操作工具(表单,pda,电子标签)
	@Column(length = 10)
	private String opTools;

	// 状态（初始、作业中、完成、结案）
	@Column(length = 10)
	private String opStatus;

	// 预留
	@Column(length = 50)
	private String reserved1;
	
	// 预留
	@Column(length = 50)
	private String reserved2;

	// 预留
	@Column(length = 50)
	private String reserved3;

	// 预留
	@Column(length = 50)
	private String reserved4;

	// 预留
	@Column(length = 50)
	private String reserved5;
		
	// 备注
	@Column(length = 50)
	private String note;

	// 建立人员
	@Column(length = 10)
	private String mkUserno;

	// 建立时间
	@Column
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@Temporal(TemporalType.TIMESTAMP)
	private Date mkDt;

	// 修改人员
	@Column
	private String modifiedUserno;

	// 修改时间
	@Column
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@Temporal(TemporalType.TIMESTAMP)
	private Date modifiedDt;
	
	// 分播数量
	@Column
	private String dpsQty;
	
	// 修改时间
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

	public Long getRecId() {
		return recId;
	}

	public void setRecId(Long recId) {
		this.recId = recId;
	}

	public String getGcode() {
		return gcode;
	}

	public void setGcode(String gcode) {
		this.gcode = gcode;
	}

	public Long getPackQty() {
		return packQty;
	}

	public void setPackQty(Long packQty) {
		this.packQty = packQty;
	}

	public String getPrice() {
		return price;
	}

	public void setPrice(String price) {
		this.price = price;
	}

	public String getItemType() {
		return itemType;
	}

	public void setItemType(String itemType) {
		this.itemType = itemType;
	}

	public String getGiftQty() {
		return giftQty;
	}

	public void setGiftQty(String giftQty) {
		this.giftQty = giftQty;
	}

	public String getBadQty() {
		return badQty;
	}

	public void setBadQty(String badQty) {
		this.badQty = badQty;
	}

	public String getRecQty() {
		return recQty;
	}

	public void setRecQty(String recQty) {
		this.recQty = recQty;
	}

	public String getRecUserno() {
		return recUserno;
	}

	public void setRecUserno(String recUserno) {
		this.recUserno = recUserno;
	}

	public String getPlatNo() {
		return platNo;
	}

	public void setPlatNo(String platNo) {
		this.platNo = platNo;
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

	public String getReserved1() {
		return reserved1;
	}

	public void setReserved1(String reserved1) {
		this.reserved1 = reserved1;
	}

	public String getReserved2() {
		return reserved2;
	}

	public void setReserved2(String reserved2) {
		this.reserved2 = reserved2;
	}

	public String getReserved3() {
		return reserved3;
	}

	public void setReserved3(String reserved3) {
		this.reserved3 = reserved3;
	}

	public String getReserved4() {
		return reserved4;
	}

	public void setReserved4(String reserved4) {
		this.reserved4 = reserved4;
	}

	public String getReserved5() {
		return reserved5;
	}

	public void setReserved5(String reserved5) {
		this.reserved5 = reserved5;
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

	public Date getMkDt() {
		return mkDt;
	}

	public void setMkDt(Date mkDt) {
		this.mkDt = mkDt;
	}

	public String getModifiedUserno() {
		return modifiedUserno;
	}

	public void setModifiedUserno(String modifiedUserno) {
		this.modifiedUserno = modifiedUserno;
	}

	public Date getModifiedDt() {
		return modifiedDt;
	}

	public void setModifiedDt(Date modifiedDt) {
		this.modifiedDt = modifiedDt;
	}

	public String getDpsQty() {
		return dpsQty;
	}

	public void setDpsQty(String dpsQty) {
		this.dpsQty = dpsQty;
	}

	public Date getInitTime() {
		return initTime;
	}

	public void setInitTime(Date initTime) {
		this.initTime = initTime;
	}
	
}
