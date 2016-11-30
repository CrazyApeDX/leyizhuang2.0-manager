package com.ynyes.lyz.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;


/**
 * 管理员权限
 * 
 * @author Sharon
 *
 */

@Entity
public class TdManagerPermission {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    
    // 导航栏ID
    @Column
    private Long navId;
    
    // 显示权限
    @Column
    private Boolean isView;
    
    // 添加权限
    @Column
    private Boolean isAdd;
    
    // 修改权限
    @Column
    private Boolean isModify;
    
    // 删除权限
    @Column
    private Boolean isDelete;
    
    // 审核权限
    @Column
    private Boolean isVerify;
    
    // 回复权限
    @Column
    private Boolean isReply;
    
    // 取消权限
    @Column
    private Boolean isCancel;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getNavId() {
        return navId;
    }

    public void setNavId(Long navId) {
        this.navId = navId;
    }

    public Boolean getIsView() {
        return isView;
    }

    public void setIsView(Boolean isView) {
        this.isView = isView;
    }

    public Boolean getIsAdd() {
        return isAdd;
    }

    public void setIsAdd(Boolean isAdd) {
        this.isAdd = isAdd;
    }

    public Boolean getIsModify() {
        return isModify;
    }

    public void setIsModify(Boolean isModify) {
        this.isModify = isModify;
    }

    public Boolean getIsDelete() {
        return isDelete;
    }

    public void setIsDelete(Boolean isDelete) {
        this.isDelete = isDelete;
    }

    public Boolean getIsVerify() {
        return isVerify;
    }

    public void setIsVerify(Boolean isVerify) {
        this.isVerify = isVerify;
    }

    public Boolean getIsReply() {
        return isReply;
    }

    public void setIsReply(Boolean isReply) {
        this.isReply = isReply;
    }

    public Boolean getIsCancel() {
        return isCancel;
    }

    public void setIsCancel(Boolean isCancel) {
        this.isCancel = isCancel;
    }
}
