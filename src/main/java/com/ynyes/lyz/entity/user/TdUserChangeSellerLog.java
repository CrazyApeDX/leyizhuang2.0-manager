package com.ynyes.lyz.entity.user;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Table(name = "td_user_change_seller_log")
public class TdUserChangeSellerLog {
	
		// 自增主键
		@Id
		@GeneratedValue(strategy = GenerationType.AUTO)
		private Long id;
		
		// 创建时间
		@Column
		@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
		private Date createTime;
		
		// 用户电话
		@Column(length = 11)
		private String username;
		
		// 用户姓名
		@Column(length = 225, nullable = false)
		private String userRealName;
		
		// 旧门店id
		@Column(length = 20, nullable = false)
		private Long oldUpperDiySiteId;
		
		// 旧门店名
		@Column(length = 15, nullable = false)
		private String oldDiyName;
		
		// 旧门店编码
		@Column(length = 10, nullable = false)
		private String oldDiyCode;
		
		// 旧销顾id
		@Column(length = 20)
		private Long oldSellerId;
		
		// 旧销顾姓名
		@Column(length = 10)
		private String oldSellerName;
		
		// 用户旧门店id
		@Column(length = 20, nullable = false)
		private Long newUpperDiySiteId;
		
		// 旧门店名
		@Column(length = 15, nullable = false)
		private String newDiyName;
		
		// 旧门店编码
		@Column(length = 10, nullable = false)
		private String newDiyCode;
		
		// 旧销顾id
		@Column(length = 20)
		private Long newSellerId;
		
		// 旧销顾姓名
		@Column(length = 10)
		private String newSellerName;

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

		public String getUsername() {
			return username;
		}

		public void setUsername(String username) {
			this.username = username;
		}

		public String getUserRealName() {
			return userRealName;
		}

		public void setUserRealName(String userRealName) {
			this.userRealName = userRealName;
		}

		public Long getOldUpperDiySiteId() {
			return oldUpperDiySiteId;
		}

		public void setOldUpperDiySiteId(Long oldUpperDiySiteId) {
			this.oldUpperDiySiteId = oldUpperDiySiteId;
		}

		public String getOldDiyName() {
			return oldDiyName;
		}

		public void setOldDiyName(String oldDiyName) {
			this.oldDiyName = oldDiyName;
		}

		public String getOldDiyCode() {
			return oldDiyCode;
		}

		public void setOldDiyCode(String oldDiyCode) {
			this.oldDiyCode = oldDiyCode;
		}

		public Long getOldSellerId() {
			return oldSellerId;
		}

		public void setOldSellerId(Long oldSellerId) {
			this.oldSellerId = oldSellerId;
		}

		public String getOldSellerName() {
			return oldSellerName;
		}

		public void setOldSellerName(String oldSellerName) {
			this.oldSellerName = oldSellerName;
		}

		public Long getNewUpperDiySiteId() {
			return newUpperDiySiteId;
		}

		public void setNewUpperDiySiteId(Long newUpperDiySiteId) {
			this.newUpperDiySiteId = newUpperDiySiteId;
		}

		public String getNewDiyName() {
			return newDiyName;
		}

		public void setNewDiyName(String newDiyName) {
			this.newDiyName = newDiyName;
		}

		public String getNewDiyCode() {
			return newDiyCode;
		}

		public void setNewDiyCode(String newDiyCode) {
			this.newDiyCode = newDiyCode;
		}

		public Long getNewSellerId() {
			return newSellerId;
		}

		public void setNewSellerId(Long newSellerId) {
			this.newSellerId = newSellerId;
		}

		public String getNewSellerName() {
			return newSellerName;
		}

		public void setNewSellerName(String newSellerName) {
			this.newSellerName = newSellerName;
		}
		
		
}
