package com.ynyes.lyz.entity.qrcode;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.springframework.format.annotation.DateTimeFormat;

@Entity
public class QrcodeRegisterLog {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@Column(length = 11, nullable = false)
	private String sellerUsername;
	
	@Column(length = 10, nullable = false)
	private String diySiteTitle;
	
	@Column(length = 10, nullable = false)
	private String diySiteCode;
	
	@Column(length = 11, nullable = false, unique = true)
	private String username;
	
	@Column(nullable = false)
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date registTime;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getSellerUsername() {
		return sellerUsername;
	}

	public void setSellerUsername(String sellerUsername) {
		this.sellerUsername = sellerUsername;
	}

	public String getDiySiteTitle() {
		return diySiteTitle;
	}

	public void setDiySiteTitle(String diySiteTitle) {
		this.diySiteTitle = diySiteTitle;
	}

	public String getDiySiteCode() {
		return diySiteCode;
	}

	public void setDiySiteCode(String diySiteCode) {
		this.diySiteCode = diySiteCode;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public Date getRegistTime() {
		return registTime;
	}

	public void setRegistTime(Date registTime) {
		this.registTime = registTime;
	}

	@Override
	public String toString() {
		return "QrcodeRegisterLog [id=" + id + ", sellerUsername=" + sellerUsername + ", diySiteTitle=" + diySiteTitle
				+ ", diySiteCode=" + diySiteCode + ", username=" + username + ", registTime=" + registTime + "]";
	}
}
