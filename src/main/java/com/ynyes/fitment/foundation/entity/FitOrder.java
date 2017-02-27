package com.ynyes.fitment.foundation.entity;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.format.annotation.DateTimeFormat;

import com.ynyes.fitment.core.constant.AuditStatus;
import com.ynyes.fitment.core.entity.persistent.table.TableEntity;

@Entity
@Table(name = "FIT_ORDER")
public class FitOrder extends TableEntity {

	private static final SimpleDateFormat FORMATTER = new SimpleDateFormat("yyyyMMddHHmmss");

	// 公司id
	@Column(length = 20, nullable = false, updatable = false)
	private Long companyId;

	// 公司名称
	@Column(length = 20, nullable = false, updatable = false)
	private String companyTitle;

	// 下单人id
	@Column(length = 20, nullable = false, updatable = false)
	private Long employeeId;

	// 下单人姓名
	@Column(length = 5, nullable = false, updatable = false)
	private String employeeName;

	// 下单人电话号码
	@Column(length = 11, nullable = false, updatable = false)
	private String employeeMobile;

	// 审核人id
	@Column(length = 20, nullable = false)
	private Long auditorId = 0L;

	// 审核人姓名
	@Column(length = 5)
	private String auditorName = "暂无";

	// 审核人电话号码
	@Column(length = 11, updatable = false)
	private String auditorMobile;

	// 单据状态：待审核、审核未通过、审核已通过
	@Column(length = 15, nullable = false)
	@Enumerated(EnumType.STRING)
	private AuditStatus status = AuditStatus.WAIT_AUDIT;

	// 审核时间
	@Column
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date auditTime;

	// 订单号
	@Column(length = 30, nullable = false, unique = true)
	private String orderNumber;

	// 订单商品
	@OneToMany
	@JoinColumn(name = "FIT_ORDER_ID")
	private List<FitOrderGoods> orderGoodsList;

	// 下单时间
	@Column(nullable = false, updatable = false)
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date orderTime = new Date();

	// 收货人姓名
	@Column(length = 50, nullable = false, updatable = false)
	private String receiveName;

	// 收货人联系电话
	@Column(length = 11, nullable = false, updatable = false)
	private String receivePhone;

	// 收货地址
	@Column(length = 150, nullable = false, updatable = false)
	private String receiveAddress;

	// 上楼方式
	@Column(length = 10, nullable = false)
	private String upstairsType = "不上楼";

	// 订单商品总价值
	@Column(nullable = false, scale = 2)
	private Double totalGoodsPrice = 0d;

	// 优惠后订单价格
	@Column(nullable = false, scale = 2)
	private Double totalPrice = 0d;

	// 预存款支付金额
	@Column(nullable = false, scale = 2)
	private Double balancePayed = 0d;

	// 第三方支付的金额
	@Column(nullable = false, scale = 2)
	private Double otherPayed = 0d;

	// 运费
	@Column(nullable = false, scale = 2)
	private Double deliveryFee = 0d;

	// 订单实际价值
	@Column(nullable = false, scale = 2)
	private Double allTotalGoodsPrice = 0d;

	// 订单共计应付金额
	@Column(nullable = false, scale = 2)
	private Double allTotalPrice = 0d;

	// 剩余未支付金额
	@Column(nullable = false, scale = 2)
	private Double leftPrice = 0d;

	// 楼层
	@Column(length = 2, nullable = false)
	private Long floor = 1L;

	// 上楼费
	@Column(length = 10, scale = 2, nullable = false)
	private Double upstairsFee = 0d;

	// 已收上楼费（预存款）
	@Column(length = 10, scale = 2, nullable = false)
	private Double upstairsBalancePayed = 0d;

	// 已收上楼费（第三方）
	@Column(length = 10, scale = 2, nullable = false)
	private Double upstairsOtherPayed = 0d;

	// 剩余上楼费
	@Column(length = 10, scale = 2, nullable = false)
	private Double upstairsLeftFee = 0d;

	// 是否被删除
	@Column(nullable = false)
	private Boolean isDelete = false;

	// 是否完成
	@Column(nullable = false)
	private Boolean isFinish = false;

	// 配送日期
	@Column(length = 10, nullable = false)
	private String deliveryDate;

	// 配送时间段
	@Column(length = 2, nullable = false)
	private Long deliveryTime;

	// 最早配送日期
	@Column(length = 10, nullable = false)
	private String earlyDate;

	// 最早配送时间段
	@Column(length = 2, nullable = false)
	private Long earlyTime;

	// 备注
	@Column
	private String remark;

	public Long getCompanyId() {
		return companyId;
	}

	public void setCompanyId(Long companyId) {
		this.companyId = companyId;
	}

	public Long getEmployeeId() {
		return employeeId;
	}

	public void setEmployeeId(Long employeeId) {
		this.employeeId = employeeId;
	}

	public String getEmployeeName() {
		return employeeName;
	}

	public void setEmployeeName(String employeeName) {
		this.employeeName = employeeName;
	}

	public Long getAuditorId() {
		return auditorId;
	}

	public void setAuditorId(Long auditorId) {
		this.auditorId = auditorId;
	}

	public String getAuditorName() {
		return auditorName;
	}

	public void setAuditorName(String auditorName) {
		this.auditorName = auditorName;
	}

	public AuditStatus getStatus() {
		return status;
	}

	public void setStatus(AuditStatus status) {
		this.status = status;
	}

	public Date getAuditTime() {
		return auditTime;
	}

	public void setAuditTime(Date auditTime) {
		this.auditTime = auditTime;
	}

	public List<FitOrderGoods> getOrderGoodsList() {
		return orderGoodsList;
	}

	public void setOrderGoodsList(List<FitOrderGoods> orderGoodsList) {
		this.orderGoodsList = orderGoodsList;
	}

	public String getReceiveName() {
		return receiveName;
	}

	public void setReceiveName(String receiveName) {
		this.receiveName = receiveName;
	}

	public String getReceivePhone() {
		return receivePhone;
	}

	public void setReceivePhone(String receivePhone) {
		this.receivePhone = receivePhone;
	}

	public String getReceiveAddress() {
		return receiveAddress;
	}

	public void setReceiveAddress(String receiveAddress) {
		this.receiveAddress = receiveAddress;
	}

	public String getUpstairsType() {
		return upstairsType;
	}

	public void setUpstairsType(String upstairsType) {
		this.upstairsType = upstairsType;
	}

	public Long getFloor() {
		return floor;
	}

	public void setFloor(Long floor) {
		this.floor = floor;
	}

	public Double getTotalGoodsPrice() {
		totalGoodsPrice = 0d;
		totalPrice = 0d;
		if (CollectionUtils.isNotEmpty(orderGoodsList)) {
			for (FitOrderGoods orderGoods : orderGoodsList) {
				totalGoodsPrice += null == orderGoods.getTotalPrice() ? 0d : orderGoods.getTotalPrice();
				totalPrice += null == orderGoods.getRealTotalPrice() ? 0d : orderGoods.getRealTotalPrice();
			}
		}
		return totalGoodsPrice;
	}

	public void setTotalGoodsPrice(Double totalGoodsPrice) {
		this.totalGoodsPrice = totalGoodsPrice;
	}

	public Double getTotalPrice() {
		totalGoodsPrice = 0d;
		totalPrice = 0d;
		if (CollectionUtils.isNotEmpty(orderGoodsList)) {
			for (FitOrderGoods orderGoods : orderGoodsList) {
				totalGoodsPrice += null == orderGoods.getTotalPrice() ? 0d : orderGoods.getTotalPrice();
				totalPrice += null == orderGoods.getRealTotalPrice() ? 0d : orderGoods.getRealTotalPrice();
			}
		}
		return totalPrice;
	}

	public void setTotalPrice(Double totalPrice) {
		this.totalPrice = totalPrice;
	}

	public Double getBalancePayed() {
		return balancePayed;
	}

	public void setBalancePayed(Double balancePayed) {
		this.balancePayed = balancePayed;
	}

	public Double getOtherPayed() {
		return otherPayed;
	}

	public void setOtherPayed(Double otherPayed) {
		this.otherPayed = otherPayed;
	}

	public Double getDeliveryFee() {
		return deliveryFee;
	}

	public void setDeliveryFee(Double deliveryFee) {
		this.deliveryFee = deliveryFee;
	}

	public Double getAllTotalGoodsPrice() {
		// 订单实际应支付金额 = 优惠后的商品金额 + 上楼费 + 运费
		this.allTotalGoodsPrice = this.totalGoodsPrice + this.upstairsFee + this.deliveryFee;
		return allTotalGoodsPrice;
	}

	public void setAllTotalGoodsPrice(Double allTotalGoodsPrice) {
		this.allTotalGoodsPrice = allTotalGoodsPrice;
	}

	public Double getAllTotalPrice() {
		// 订单实际应支付金额 = 优惠后的商品金额 + 上楼费 + 运费
		this.allTotalPrice = this.totalPrice + this.upstairsFee + this.deliveryFee;
		return allTotalPrice;
	}

	public void setAllTotalPrice(Double allTotalPrice) {
		this.allTotalPrice = allTotalPrice;
	}

	public Double getLeftPrice() {
		// 订单应付 = 订单实际应支付总额 - 预存款支付额度 - 第三方支付额度 - 预存款支付上楼费额度 - 第三方支付上楼费额度
		this.leftPrice = this.allTotalPrice - this.balancePayed - this.otherPayed - this.upstairsBalancePayed
				- this.upstairsOtherPayed;
		return leftPrice;
	}

	public void setLeftPrice(Double leftPrice) {
		this.leftPrice = leftPrice;
	}

	public Double getUpstairsFee() {
		return upstairsFee;
	}

	public void setUpstairsFee(Double upstairsFee) {
		this.upstairsFee = upstairsFee;
	}

	public Double getUpstairsBalancePayed() {
		return upstairsBalancePayed;
	}

	public void setUpstairsBalancePayed(Double upstairsBalancePayed) {
		this.upstairsBalancePayed = upstairsBalancePayed;
	}

	public Double getUpstairsOtherPayed() {
		return upstairsOtherPayed;
	}

	public void setUpstairsOtherPayed(Double upstairsOtherPayed) {
		this.upstairsOtherPayed = upstairsOtherPayed;
	}

	public Double getUpstairsLeftFee() {
		// 上楼费剩余金额 = 上楼费金额 - 预存款支付上楼费金额 - 第三方支付上楼费金额
		this.upstairsLeftFee = this.upstairsFee - this.upstairsBalancePayed - this.upstairsOtherPayed;
		return upstairsLeftFee;
	}

	public void setUpstairsLeftFee(Double upstairsLeftFee) {
		this.upstairsLeftFee = upstairsLeftFee;
	}

	public String getOrderNumber() {
		return orderNumber;
	}

	public FitOrder setOrderNumber(String orderNumber) {
		this.orderNumber = orderNumber;
		return this;
	}

	public Boolean getIsDelete() {
		return isDelete;
	}

	public void setIsDelete(Boolean isDelete) {
		this.isDelete = isDelete;
	}

	public Boolean getIsFinish() {
		return isFinish;
	}

	public void setIsFinish(Boolean isFinish) {
		this.isFinish = isFinish;
	}

	public Date getOrderTime() {
		return orderTime;
	}

	public void setOrderTime(Date orderTime) {
		this.orderTime = orderTime;
	}

	public String getDeliveryDate() {
		return deliveryDate;
	}

	public void setDeliveryDate(String deliveryDate) {
		this.deliveryDate = deliveryDate;
	}

	public Long getDeliveryTime() {
		return deliveryTime;
	}

	public void setDeliveryTime(Long deliveryTime) {
		this.deliveryTime = deliveryTime;
	}

	public String getEarlyDate() {
		return earlyDate;
	}

	public void setEarlyDate(String earlyDate) {
		this.earlyDate = earlyDate;
	}

	public Long getEarlyTime() {
		return earlyTime;
	}

	public void setEarlyTime(Long earlyTime) {
		this.earlyTime = earlyTime;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getEmployeeMobile() {
		return employeeMobile;
	}

	public void setEmployeeMobile(String employeeMobile) {
		this.employeeMobile = employeeMobile;
	}

	public String getAuditorMobile() {
		return auditorMobile;
	}

	public void setAuditorMobile(String auditorMobile) {
		this.auditorMobile = auditorMobile;
	}

	public String getCompanyTitle() {
		return companyTitle;
	}

	public void setCompanyTitle(String companyTitle) {
		this.companyTitle = companyTitle;
	}

	public FitOrder initOrderNumber() {
		StringBuffer buffer = new StringBuffer("FIT").append(FORMATTER.format(new Date()))
				.append((int) (Math.random() * 900) + 100);
		return this.setOrderNumber(buffer.toString());
	}

	public FitOrder initPrice() {
		this.getTotalGoodsPrice();
		this.getTotalPrice();
		this.getAllTotalPrice();
		this.getUpstairsLeftFee();
		return this;
	}
}
