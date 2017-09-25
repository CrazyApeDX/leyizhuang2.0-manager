package com.ynyes.lyz.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 *  退货入库单 详细
 * 
 * @author Sharon
 *
 */

@Entity
public class TdBackDetail {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	// 委托业主	
	@Column
	private String ownerNo;

	//  任务编号
	@Column
	private String recNo;

	// 任务id
	@Column
	private String recId;

	// 商品编号
	@Column
	private String gcode;

	// 包装数量
	@Column
	private String packQty;

	// 价格
	@Column
	private String price;
	
	//验收赠品数量
	@Column
	private String giftQty;

	// 验收赠品不良品数量
	@Column
	private String badQty;

	// 验收数量
	@Column
	private String recQty;

	// 作业人员
	@Column
	private String recUser;

	//月台
	@Column
	private String platNo;

	// 操作工具(表单,pda,电子标签)
	@Column
	private String opTools;

	// 状态（初始、作业中、完成、结案）
	@Column
	private String opStatus;

	// 预留
	@Column
	private String reserved1;
	
	// 预留
	@Column
	private String reserved2;

	// 预留
	@Column
	private String reserved3;

	// 预留
	@Column
	private String reserved4;

	// 预留
	@Column
	private String reserved5;
		
	// 备注
	@Column
	private String note;

	// 建立人员
	@Column
	private String mkUserno;

	// 修改人员
	@Column
	private String modifiedUserno;

	// 建立时间
	@Column
	private String mkDt;

	// 修改时间
	@Column
	private String modifiedDt;

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

	public String getRecId() {
		return recId;
	}

	public void setRecId(String recId) {
		this.recId = recId;
	}

	public String getGcode() {
		return gcode;
	}

	public void setGcode(String gcode) {
		this.gcode = gcode;
	}

	public String getPackQty() {
		return packQty;
	}

	public void setPackQty(String packQty) {
		this.packQty = packQty;
	}

	public String getPrice() {
		return price;
	}

	public void setPrice(String price) {
		this.price = price;
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

	public String getRecUser() {
		return recUser;
	}

	public void setRecUser(String recUser) {
		this.recUser = recUser;
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

	public String getModifiedUserno() {
		return modifiedUserno;
	}

	public void setModifiedUserno(String modifiedUserno) {
		this.modifiedUserno = modifiedUserno;
	}

	public String getMkDt() {
		return mkDt;
	}

	public void setMkDt(String mkDt) {
		this.mkDt = mkDt;
	}

	public String getModifiedDt() {
		return modifiedDt;
	}

	public void setModifiedDt(String modifiedDt) {
		this.modifiedDt = modifiedDt;
	}
	
}
