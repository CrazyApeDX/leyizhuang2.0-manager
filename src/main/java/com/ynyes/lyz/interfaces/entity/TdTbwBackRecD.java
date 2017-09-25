package com.ynyes.lyz.interfaces.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.springframework.format.annotation.DateTimeFormat;

/**
 * wms城市仓库退货明细
 * @author Administrator
 *
 */

@Entity
public class TdTbwBackRecD extends TdInfBaseEntity
{
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	//委托业主
	@Column(length = 10)
	private String cOwnerNo;

	//任务编号
	@Column(length = 30)
	private String cRecNo;

	//任务id
	@Column
	private Long cRecId;

	//商品编号
	@Column(length = 30)
	private String cGcode;

	//包装数量
	@Column
	private String cPackQty;

	//价格
	@Column
	private Double cPrice;

	//验收赠品数量
	@Column
	private String cGiftQty;

	//验收不良品数量
	@Column
	private String cBadQty;

	//验收数量
	@Column
	private String cRecQty;

	//作业人员
	@Column(length = 10)
	private String cRecUser;

	//月台
	@Column(length = 30)
	private String cPlatNo;

	//操作工具(表单,pda,电子标签)
	@Column(length = 10)
	private String cOpTools;

	//状态（初始、作业中、完成、结案)
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
	@Column(length = 10)
	private String cMkUserno;

	//建立时间
	@Column
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date cMkDt;

	//修改人员
	@Column
	private String cModifiedUserno;

	//修改时间
	@Column
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date cModifiedDt;

	public Long getId()
	{
		return id;
	}

	public void setId(Long id)
	{
		this.id = id;
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

	public Long getcRecId()
	{
		return cRecId;
	}

	public void setcRecId(Long cRecId)
	{
		this.cRecId = cRecId;
	}

	public String getcGcode()
	{
		return cGcode;
	}

	public void setcGcode(String cGcode)
	{
		this.cGcode = cGcode;
	}

	public String getcPackQty()
	{
		return cPackQty;
	}

	public void setcPackQty(String cPackQty)
	{
		this.cPackQty = cPackQty;
	}

	public Double getcPrice()
	{
		return cPrice;
	}

	public void setcPrice(Double cPrice)
	{
		this.cPrice = cPrice;
	}

	public String getcGiftQty()
	{
		return cGiftQty;
	}

	public void setcGiftQty(String cGiftQty)
	{
		this.cGiftQty = cGiftQty;
	}

	public String getcBadQty()
	{
		return cBadQty;
	}

	public void setcBadQty(String cBadQty)
	{
		this.cBadQty = cBadQty;
	}

	public String getcRecQty()
	{
		return cRecQty;
	}

	public void setcRecQty(String cRecQty)
	{
		this.cRecQty = cRecQty;
	}

	public String getcRecUser()
	{
		return cRecUser;
	}

	public void setcRecUser(String cRecUser)
	{
		this.cRecUser = cRecUser;
	}

	public String getcPlatNo()
	{
		return cPlatNo;
	}

	public void setcPlatNo(String cPlatNo)
	{
		this.cPlatNo = cPlatNo;
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

	public String getcReserved1()
	{
		return cReserved1;
	}

	public void setcReserved1(String cReserved1)
	{
		this.cReserved1 = cReserved1;
	}

	public String getcReserved2()
	{
		return cReserved2;
	}

	public void setcReserved2(String cReserved2)
	{
		this.cReserved2 = cReserved2;
	}

	public String getcReserved3()
	{
		return cReserved3;
	}

	public void setcReserved3(String cReserved3)
	{
		this.cReserved3 = cReserved3;
	}

	public String getcReserved4()
	{
		return cReserved4;
	}

	public void setcReserved4(String cReserved4)
	{
		this.cReserved4 = cReserved4;
	}

	public String getcReserved5()
	{
		return cReserved5;
	}

	public void setcReserved5(String cReserved5)
	{
		this.cReserved5 = cReserved5;
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

	@Override
	public String toString()
	{
		return "TdTbwBackRecD [cOwnerNo=" + cOwnerNo + ", cRecNo=" + cRecNo + ", cRecId=" + cRecId + ", cGcode="
		        + cGcode + ", cPackQty=" + cPackQty + ", cPrice=" + cPrice + ", cGiftQty=" + cGiftQty + ", cBadQty="
		        + cBadQty + ", cRecQty=" + cRecQty + ", cRecUser=" + cRecUser + ", cPlatNo=" + cPlatNo + ", cOpTools="
		        + cOpTools + ", cOpStatus=" + cOpStatus + ", cReserved1=" + cReserved1 + ", cReserved2=" + cReserved2
		        + ", cReserved3=" + cReserved3 + ", cReserved4=" + cReserved4 + ", cReserved5=" + cReserved5
		        + ", cNote=" + cNote + ", cMkUserno=" + cMkUserno + ", cMkDt=" + cMkDt + ", cModifiedUserno="
		        + cModifiedUserno + ", cModifiedDt=" + cModifiedDt + "]";
	}

}
