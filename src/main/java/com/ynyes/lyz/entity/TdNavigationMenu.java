package com.ynyes.lyz.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;


/**
 * 导航菜单栏
 * 
 * @author Sharon
 *
 */

@Entity
public class TdNavigationMenu {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    
    // 类型
    @Column
    private String type;
    
    // 名称
    @Column
    private String name;
    
    // 标题
    @Column
    private String title;
    
    // 频道ID，频道用于区分在哪张表里取数据
    @Column
    private Long channelId;
    
    // 图标ID
    @Column
    private String iconId;
    
    // 链接地址
    @Column
    private String linkUrl;
    
    // 排序号
    @Column
    private Double sortId;
    
    // 父级菜单
    @Column
    private Long parentId;

    // 是否开启
    @Column
    private Boolean isEnable;
    
    // 支持的操作类型
    @Column 
    private String actionType;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Long getChannelId() {
        return channelId;
    }

    public void setChannelId(Long channelId) {
        this.channelId = channelId;
    }

    public String getIconId() {
        return iconId;
    }

    public void setIconId(String iconId) {
        this.iconId = iconId;
    }

    public String getLinkUrl() {
        return linkUrl;
    }

    public void setLinkUrl(String linkUrl) {
        this.linkUrl = linkUrl;
    }

    public Double getSortId() {
        return sortId;
    }

    public void setSortId(Double sortId) {
        this.sortId = sortId;
    }

    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    public Boolean getIsEnable() {
        return isEnable;
    }

    public void setIsEnable(Boolean isEnable) {
        this.isEnable = isEnable;
    }

    public String getActionType() {
        return actionType;
    }

    public void setActionType(String actionType) {
        this.actionType = actionType;
    }
}
