package com.ynyes.lyz.interfaces.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.springframework.format.annotation.DateTimeFormat;

/**
 * wms城市仓库退货主档
 * @author Administrator
 *
 */

@Entity
public class TdTbwBackRecM extends TdInfBaseEntity
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

	//返配验收单号
	@Column(length = 30)
	private String cRecNo;

	//打印次数
	@Column
	private Long cPrintTimes;

	//返配单号
	@Column(length = 30)
	private String cBackNo;

	//返配单类型（一般返配
	@Column(length = 10)
	private String cBackType;

	//返配单分类(存储型、越库型)
	@Column(length = 10)
	private String cBackClass;

	//客户信息
	@Column(length = 30)
	private String cCustomerNo;

	//月台
	@Column(length = 30)
	private String cPlatNo;

	//验收人员
	@Column(length = 10)
	private String cRecUser;

	//操作工具(表单,pda,电子标签)
	@Column(length = 10)
	private String cOpTools;

	//操作状态(初始、作业中、完成、结案)
	@Column(length = 10)
	private String cOpStatus;

	//开始操作时间
	@Column
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date cBeginDt;

	//结束操作时间
	@Column
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date cEndDt;

	//备注
	@Column(length = 50)
	private String cNote;

	//建立人员
	@Column(length = 10)
	private String cMkUserno;

	//建立时间
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

	//门店退单
	@Column(length = 30)
	private String cPoNo;
	
	//分公司Id
	@Column
	private Long cCompanyId;
	

	public Long getId()
	{
		return id;
	}

	public void setId(Long id)
	{
		this.id = id;
	}

	public String getcWhNo()
	{
		return cWhNo;
	}

	public void setcWhNo(String cWhNo)
	{
		this.cWhNo = cWhNo;
	}

	public String getcOwnerNo()
	{
		return cOwnerNo;
	}

	public void setcOwnerNo(String cOwnerNo)
	{
		this.cOwnerNo = cOwnerNo;
	}

	public String getcRecNo()
	{
		return cRecNo;
	}

	public void setcRecNo(String cRecNo)
	{
		this.cRecNo = cRecNo;
	}

	public Long getcPrintTimes()
	{
		return cPrintTimes;
	}

	public void setcPrintTimes(Long cPrintTimes)
	{
		this.cPrintTimes = cPrintTimes;
	}

	public String getcBackNo()
	{
		return cBackNo;
	}

	public void setcBackNo(String cBackNo)
	{
		this.cBackNo = cBackNo;
	}

	public String getcBackType()
	{
		return cBackType;
	}

	public void setcBackType(String cBackType)
	{
		this.cBackType = cBackType;
	}

	public String getcBackClass()
	{
		return cBackClass;
	}

	public void setcBackClass(String cBackClass)
	{
		this.cBackClass = cBackClass;
	}

	public String getcCustomerNo()
	{
		return cCustomerNo;
	}

	public void setcCustomerNo(String cCustomerNo)
	{
		this.cCustomerNo = cCustomerNo;
	}

	public String getcPlatNo()
	{
		return cPlatNo;
	}

	public void setcPlatNo(String cPlatNo)
	{
		this.cPlatNo = cPlatNo;
	}

	public String getcRecUser()
	{
		return cRecUser;
	}

	public void setcRecUser(String cRecUser)
	{
		this.cRecUser = cRecUser;
	}

	public String getcOpTools()
	{
		return cOpTools;
	}

	public void setcOpTools(String cOpTools)
	{
		this.cOpTools = cOpTools;
	}

	public String getcOpStatus()
	{
		return cOpStatus;
	}

	public void setcOpStatus(String cOpStatus)
	{
		this.cOpStatus = cOpStatus;
	}

	public Date getcBeginDt()
	{
		return cBeginDt;
	}

	public void setcBeginDt(Date cBeginDt)
	{
		this.cBeginDt = cBeginDt;
	}

	public Date getcEndDt()
	{
		return cEndDt;
	}

	public void setcEndDt(Date cEndDt)
	{
		this.cEndDt = cEndDt;
	}

	public String getcNote()
	{
		return cNote;
	}

	public void setcNote(String cNote)
	{
		this.cNote = cNote;
	}

	public String getcMkUserno()
	{
		return cMkUserno;
	}

	public void setcMkUserno(String cMkUserno)
	{
		this.cMkUserno = cMkUserno;
	}

	public Date getcMkDt()
	{
		return cMkDt;
	}

	public void setcMkDt(Date cMkDt)
	{
		this.cMkDt = cMkDt;
	}

	public String getcModifiedUserno()
	{
		return cModifiedUserno;
	}

	public void setcModifiedUserno(String cModifiedUserno)
	{
		this.cModifiedUserno = cModifiedUserno;
	}

	public Date getcModifiedDt()
	{
		return cModifiedDt;
	}

	public void setcModifiedDt(Date cModifiedDt)
	{
		this.cModifiedDt = cModifiedDt;
	}

	public String getcPoNo()
	{
		return cPoNo;
	}

	public void setcPoNo(String cPoNo)
	{
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

	@Override
	public String toString()
	{
		return "TdTbwBackRecM [id=" + id + ", cWhNo=" + cWhNo + ", cOwnerNo=" + cOwnerNo + ", cRecNo=" + cRecNo
		        + ", cPrintTimes=" + cPrintTimes + ", cBackNo=" + cBackNo + ", cBackType=" + cBackType + ", cBackClass="
		        + cBackClass + ", cCustomerNo=" + cCustomerNo + ", cPlatNo=" + cPlatNo + ", cRecUser=" + cRecUser
		        + ", cOpTools=" + cOpTools + ", cOpStatus=" + cOpStatus + ", cBeginDt=" + cBeginDt + ", cEndDt="
		        + cEndDt + ", cNote=" + cNote + ", cMkUserno=" + cMkUserno + ", cMkDt=" + cMkDt + ", cModifiedUserno="
		        + cModifiedUserno + ", cModifiedDt=" + cModifiedDt + ", cPoNo=" + cPoNo + "]";
	}

}
