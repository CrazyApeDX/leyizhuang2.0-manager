package com.ynyes.lyz.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;


/**
 * 门店控制角色
 * 
 * @author Shawn
 *
 */

@Entity
public class TdManagerDiySiteRole {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    
    // 角色名称
    @Column
    private String title;
    
    // 是否系统管理员
    @Column
    private Boolean isSys;
    
    // 拥有控制权限的门店
    @Column
    private String diySiteTree;
    
    // 管理门店总数
    @Column
    private Integer totalDiySitePermission;
    
    //拥有控制权限的城市 zp
    @Column
    private String cityTree;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Boolean getIsSys() {
		return isSys;
	}

	public void setIsSys(Boolean isSys) {
		this.isSys = isSys;
	}

	public Integer getTotalDiySitePermission() {
		return totalDiySitePermission;
	}

	public void setTotalDiySitePermission(Integer totalDiySitePermission) {
		this.totalDiySitePermission = totalDiySitePermission;
	}

	public String getDiySiteTree() {
		return diySiteTree;
	}

	public void setDiySiteTree(String diySiteTree) {
		this.diySiteTree = diySiteTree;
	}

	public String getCityTree() {
		return cityTree;
	}

	public void setCityTree(String cityTree) {
		this.cityTree = cityTree;
	}
}
