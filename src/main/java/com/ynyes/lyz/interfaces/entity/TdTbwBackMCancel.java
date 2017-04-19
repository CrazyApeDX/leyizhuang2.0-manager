package com.ynyes.lyz.interfaces.entity;

import java.io.UnsupportedEncodingException;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.apache.geronimo.mail.util.Base64;
import org.springframework.format.annotation.DateTimeFormat;

/**
 * 客户取消退货接口表 (WMS)
 * 
 * @author YanLe
 *
 */

@Entity
@Table(name="td_tbw_back_m_cancel")
public class TdTbwBackMCancel extends TdInfBaseEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	// 仓库编号
	@Column(length = 10)
	private String cWhNo;

	// 委托业主
	@Column(length = 10)
	private String cOwnerNo;

	// 返配单号
	@Column(length = 30)
	private String cBackNo;

	// 返配单类型（一般返配）
	@Column(length = 10)
	private String cBackType;

	// 返配单分类(存储型、越库型)
	@Column(length = 10)
	private String cBackClass;

	// 门店退单(退单号)
	@Column(length = 30, nullable = false)
	private String cPoNo;

	// 退单类型
	@Column(length = 10)
	private String cPoType;

	// 有效时间
	@Column(nullable = false)
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date cDueDt;

	// 客户编码（门店编码）
	@Column(length = 30)
	private String cCustomerNo;

	// 退货时间
	@Column(nullable = false)
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date cRequestDt;

	// 操作状态(初始、作业中、完成、结案)
	@Column(length = 10)
	private String cOpStatus;

	// 操作状态(初始、作业中、完成、结案)
	@Column(length = 10)
	private String cCreateFlag;

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

	public String getcBackNo() {
		return cBackNo;
	}

	public void setcBackNo(String cBackNo) {
		this.cBackNo = cBackNo;
	}

	public String getcBackType() {
		return cBackType;
	}

	public void setcBackType(String cBackType) {
		this.cBackType = cBackType;
	}

	public String getcBackClass() {
		return cBackClass;
	}

	public void setcBackClass(String cBackClass) {
		this.cBackClass = cBackClass;
	}

	public String getcPoNo() {
		return cPoNo;
	}

	public void setcPoNo(String cPoNo) {
		this.cPoNo = cPoNo;
	}

	public String getcPoType() {
		return cPoType;
	}

	public void setcPoType(String cPoType) {
		this.cPoType = cPoType;
	}

	public Date getcDueDt() {
		return cDueDt;
	}

	public void setcDueDt(Date cDueDt) {
		this.cDueDt = cDueDt;
	}

	public String getcCustomerNo() {
		return cCustomerNo;
	}

	public void setcCustomerNo(String cCustomerNo) {
		this.cCustomerNo = cCustomerNo;
	}

	public Date getcRequestDt() {
		return cRequestDt;
	}

	public void setcRequestDt(Date cRequestDt) {
		this.cRequestDt = cRequestDt;
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

	@Override
	public String toString() {
		return "TdTbwBackRecMCancel [id=" + id + ", cWhNo=" + cWhNo + ", cOwnerNo=" + cOwnerNo + ", cBackNo=" + cBackNo
				+ ", cBackType=" + cBackType + ", cBackClass=" + cBackClass + ", cPoNo=" + cPoNo + ", cPoType="
				+ cPoType + ", cDueDt=" + cDueDt + ", cCustomerNo=" + cCustomerNo + ", cRequestDt=" + cRequestDt
				+ ", cOpStatus=" + cOpStatus + ", cCreateFlag=" + cCreateFlag + "]";
	}
	
	
	public String toXml(){
		String encodeXML = null;
		StringBuffer buffer = new StringBuffer();
		buffer.append("<ERP>")
			  .append("<TABLE>")
			  .append("<c_wh_no>" + cWhNo +"</c_wh_no>")
			  .append("<c_owner_no>" + cOwnerNo + "<c_owner_no>")
			  .append("<c_back_no>" + cBackNo + "</c_back_no>")
			  .append("<c_back_type>" + cBackType + "</c_back_type>")
			  .append("<c_back_class>" + cBackClass + "</c_back_class>")
			  .append("<c_po_no>" + cPoNo + "</c_po_no>")
			  .append("<c_po_type>" + cPoType + "</c_po_type>")
			  .append("<c_due_dt>" + cDueDt + "</c_due_dt>")
			  .append("<c_customer_no>" + cCustomerNo +"</c_customer_no>")
			  .append("<c_request_dt>" + cRequestDt +"</c_request_dt>")
			  .append("<c_op_status>" + cOpStatus + "</c_op_status>")
			  .append("<c_create_flag>" + cCreateFlag + "</c_create_flag>");
		String xmlStr = buffer.toString().replace("null", "");
		byte[] bs = xmlStr.getBytes();
		byte[] encodeByte = Base64.encode(bs);
		try {
			encodeXML = new String(encodeByte, "UTF-8");
		} catch (UnsupportedEncodingException e1) {
			System.err.println("MDJ_WMS:XML 编码出错!");
			return "Failed";
		}
		return encodeXML;
	}
	
	

}
