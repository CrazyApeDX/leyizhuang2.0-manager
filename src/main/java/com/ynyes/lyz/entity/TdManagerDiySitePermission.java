package com.ynyes.lyz.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;


/**
 * 管理员门店权限表
 * 
 * @author Shawn
 *
 */

@Entity
public class TdManagerDiySitePermission {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    
    // 门店ID
    @Column
    private Long diySiteId;
    
    // 是否具有控制权限
    @Column
    private Boolean hasAuthority;
    
    // 角色ID
    @Column
    private Long diySiteRoleId;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getDiySiteId() {
		return diySiteId;
	}

	public void setDiySiteId(Long diySiteId) {
		this.diySiteId = diySiteId;
	}

	public Boolean getHasAuthority() {
		return hasAuthority;
	}

	public void setHasAuthority(Boolean hasAuthority) {
		this.hasAuthority = hasAuthority;
	}

	public Long getDiySiteRoleId() {
		return diySiteRoleId;
	}

	public void setDiySiteRoleId(Long diySiteRoleId) {
		this.diySiteRoleId = diySiteRoleId;
	}
}
