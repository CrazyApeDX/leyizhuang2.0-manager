package com.ynyes.lyz.entity;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;


/**
 * 管理员角色
 * 
 * @author Sharon
 *
 */

@Entity
public class TdManagerRole {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    
    // 名称
    @Column
    private String title;
    
    // 是否系统管理员
    @Column
    private Boolean isSys;
    
    // 角色权限
    @OneToMany
    @JoinColumn(name="roleId")
    private List<TdManagerPermission> permissionList;
    
    // 权限总数
    @Column
    private Integer totalPermission;

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

    public List<TdManagerPermission> getPermissionList() {
        return permissionList;
    }

    public void setPermissionList(List<TdManagerPermission> permissionList) {
        this.permissionList = permissionList;
    }

    public Integer getTotalPermission() {
        return totalPermission;
    }

    public void setTotalPermission(Integer totalPermission) {
        this.totalPermission = totalPermission;
    }
}
