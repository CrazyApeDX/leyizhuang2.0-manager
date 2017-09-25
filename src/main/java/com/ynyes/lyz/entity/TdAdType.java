package com.ynyes.lyz.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.springframework.format.annotation.DateTimeFormat;


/**
 * 广告位
 * 
 * @author Sharon
 *
 */

@Entity
public class TdAdType {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
	
    // 名称
    @Column
    private String title;
    
    // 类型
    @Column
    private Long type;
    
    // 显示数量
    @Column
    private Long totalShows;
    
    // 创建时间
    @Column
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date createTime;
    
    // 排序号
    @Column
    private Double sortId;

    // 是否在新窗口显示
    @Column
    private Boolean isNewWindow;
    
    // 价格
    @Column(scale=2)
    private Double price;
    
    // 宽度
    @Column
    private Long width;
    
    // 高度
    @Column
    private Long heigth;
    
    // 备注
    @Column
    private String mark;

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

    public Long getType() {
        return type;
    }

    public void setType(Long type) {
        this.type = type;
    }

    public Long getTotalShows() {
        return totalShows;
    }

    public void setTotalShows(Long totalShows) {
        this.totalShows = totalShows;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Double getSortId() {
        return sortId;
    }

    public void setSortId(Double sortId) {
        this.sortId = sortId;
    }

    public Boolean getIsNewWindow() {
        return isNewWindow;
    }

    public void setIsNewWindow(Boolean isNewWindow) {
        this.isNewWindow = isNewWindow;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Long getWidth() {
        return width;
    }

    public void setWidth(Long width) {
        this.width = width;
    }

    public Long getHeigth() {
        return heigth;
    }

    public void setHeigth(Long heigth) {
        this.heigth = heigth;
    }

    public String getMark() {
        return mark;
    }

    public void setMark(String mark) {
        this.mark = mark;
    }
}
