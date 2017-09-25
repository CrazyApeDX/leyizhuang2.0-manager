package com.ynyes.lyz.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.springframework.format.annotation.DateTimeFormat;

/**
 * <p>标题：TdCashReturnNote.java</p>
 * <p>描述：第三方退款单据，只有在需要退还支付宝、微信、银联金额的时候才能够退还</p>
 * <p>公司：http://www.ynsite.com</p>
 * @author 作者：DengXiao
 * @version 版本：上午11:52:39
 */
@Entity
public class TdCashReturnNote {

	// 主键
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	// 生成时间
	@Column
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date createTime;
	
	// 打款金额
	@Column(scale = 2)
	private Double money;
	
	// 打款方式id（该字段引用支付方式id，但是电商平台不支持现金和POS还有其他三种方式，所以定义固定值-1代表现金；固定值-2代表POS；固定值-3代表其他）
	@Column
	private Long typeId;
	
	// 打款方式名称
	@Column
	private String typeTitle;
	
	// 完成时间（及打款时间）
	@Column
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date finishTime;
	
	// 涉及分订单号
	@Column
	private String orderNumber;
	
	//涉及主订单号
	@Column
	private String mainOrderNumber;
	
	// 涉及退货单号
	@Column
	private String returnNoteNumber;
	
	// 涉及用户id
	@Column
	private Long userId;
	
	// 设计用户名
	@Column
	private String username;
	
	// 是否打款
	@Column
	private Boolean isOperated;
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Double getMoney() {
		return money;
	}

	public void setMoney(Double money) {
		this.money = money;
	}

	public Long getTypeId() {
		return typeId;
	}

	public void setTypeId(Long typeId) {
		this.typeId = typeId;
	}

	public String getTypeTitle() {
		return typeTitle;
	}

	public void setTypeTitle(String typeTitle) {
		this.typeTitle = typeTitle;
	}

	public Date getFinishTime() {
		return finishTime;
	}

	public void setFinishTime(Date finishTime) {
		this.finishTime = finishTime;
	}

	public String getOrderNumber() {
		return orderNumber;
	}

	public void setOrderNumber(String orderNumber) {
		this.orderNumber = orderNumber;
	}

	public String getReturnNoteNumber() {
		return returnNoteNumber;
	}

	public void setReturnNoteNumber(String returnNoteNumber) {
		this.returnNoteNumber = returnNoteNumber;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public Boolean getIsOperated() {
		return isOperated;
	}

	public void setIsOperated(Boolean isOperated) {
		this.isOperated = isOperated;
	}

	public String getMainOrderNumber() {
		return mainOrderNumber;
	}

	public void setMainOrderNumber(String mainOrderNumber) {
		this.mainOrderNumber = mainOrderNumber;
	}

	@Override
	public String toString() {
		return "TdCashReturnNote [id=" + id + ", createTime=" + createTime + ", money=" + money + ", typeId=" + typeId
				+ ", typeTitle=" + typeTitle + ", finishTime=" + finishTime + ", orderNumber=" + orderNumber
				+ ", mainOrderNumber=" + mainOrderNumber + ", returnNoteNumber=" + returnNoteNumber + ", userId="
				+ userId + ", username=" + username + ", isOperated=" + isOperated + "]";
	}
}
