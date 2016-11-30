package com.ynyes.lyz.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.springframework.format.annotation.DateTimeFormat;


/**
 * 文章
 * 
 * @author Sharon
 *
 */

@Entity
public class TdArticle {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    
    // 标题
    @Column
    private String title;

    // 所属菜单ID
    @Column
    private Long menuId;
    
    // 类型ID
    @Column
    private Long categoryId;
    
    // 频道ID
    @Column
    private Long channelId;
    
    // 显示状态
    @Column
    private Long statusId;
    
    // 推荐类型
    @Column
    private Long recommendId;
    
    // 排序号
    @Column
    private Double sortId;
    
    // 浏览次数
    @Column
    private Long viewCount;
    
    // 调用别名
    @Column
    private String callIndex;
    
    // 链接地址
    @Column
    private String linkUrl;
    
    // 图片地址
    @Column
    private String imgUrl;
    
    // 内容摘要
    @Column
    private String brief;
    
    // 详细内容
    @Column
    private String content;
    
    // 信息来源
    @Column
    private String source;
    
    // 作者
    @Column
    private String author;
    
    // SEO标题
    @Column
    private String seoTitle;
    
    // SEO关键字
    @Column
    private String seoKeywords;
    
    // SEO描述
    @Column
    private String seoDescription;
    
    // 发布时间
    @Column
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createTime;
    
    // 更新时间
    @Column
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @Temporal(TemporalType.TIMESTAMP)
    private Date updateTime;
    
    // 推荐类型
    @Column
    private Long paramInputType;
    
    // 参数是否多选
    @Column
    private Boolean paramIsMultiple;
    
    // 参数是否可检索
    @Column
    private Boolean paramIsSearchable;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCallIndex() {
        return callIndex;
    }

    public void setCallIndex(String callIndex) {
        this.callIndex = callIndex;
    }

    public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
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

    public Long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }

    public Long getMenuId() {
        return menuId;
    }

    public void setMenuId(Long menuId) {
        this.menuId = menuId;
    }

    public Long getStatusId() {
        return statusId;
    }

    public void setStatusId(Long statusId) {
        this.statusId = statusId;
    }

    public Long getRecommendId() {
        return recommendId;
    }

    public void setRecommendId(Long recommendId) {
        this.recommendId = recommendId;
    }

    public Double getSortId() {
        return sortId;
    }

    public void setSortId(Double sortId) {
        this.sortId = sortId;
    }

    public Long getViewCount() {
        return viewCount;
    }

    public void setViewCount(Long viewCount) {
        this.viewCount = viewCount;
    }

    public String getLinkUrl() {
        return linkUrl;
    }

    public void setLinkUrl(String linkUrl) {
        this.linkUrl = linkUrl;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public String getBrief() {
        return brief;
    }

    public void setBrief(String brief) {
        this.brief = brief;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getSeoTitle() {
        return seoTitle;
    }

    public void setSeoTitle(String seoTitle) {
        this.seoTitle = seoTitle;
    }

    public String getSeoKeywords() {
        return seoKeywords;
    }

    public void setSeoKeywords(String seoKeywords) {
        this.seoKeywords = seoKeywords;
    }

    public String getSeoDescription() {
        return seoDescription;
    }

    public void setSeoDescription(String seoDescription) {
        this.seoDescription = seoDescription;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public Long getParamInputType() {
        return paramInputType;
    }

    public void setParamInputType(Long paramInputType) {
        this.paramInputType = paramInputType;
    }

    public Boolean getParamIsMultiple() {
        return paramIsMultiple;
    }

    public void setParamIsMultiple(Boolean paramIsMultiple) {
        this.paramIsMultiple = paramIsMultiple;
    }

    public Boolean getParamIsSearchable() {
        return paramIsSearchable;
    }

    public void setParamIsSearchable(Boolean paramIsSearchable) {
        this.paramIsSearchable = paramIsSearchable;
    }
    
    
}
