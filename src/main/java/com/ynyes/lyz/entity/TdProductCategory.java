package com.ynyes.lyz.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 * 商品类型
 * 
 * @author Sharon
 *
 */

@Entity
public class TdProductCategory {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	// 类型名称
	@Column(unique=true)
	private String title;
	
	// 父类型
	@Column
	private Long parentId;
	
	// 父类型列表
    @Column
    private String parentTree;
    
    // 排序号
    @Column
    private Double sortId;
    
    // 是否推荐类型
    @Column
    private Boolean isRecommend;
    
    // 关联的参数类型ID
    @Column
    private Long paramCategoryId;
    
    // 频道ID
    @Column
    private Long channelId;
    
    // 调用别名
    @Column
    private String callIndex;
    
    // 所属菜单ID
    @Column
    private Long menuId;
    
    // 链接地址
    @Column
    private String linkUrl;
    
    // 图片地址
    @Column
    private String imgUrl;
    
    // 层级
    @Column
    private Long layerCount;
    
    // SEO标题
    @Column
    private String seoTitle;
    
    // SEO关键字
    @Column
    private String seoKeywords;
    
    // SEO描述
    @Column
    private String seoDescription;
    
    //主类编号(1.水 ;2.电 3.木;4.瓦;5.油)
    @Column
    private String mainNumber;
    
    // ebs接口 新增字段
    
    // EBS物料类别id
    @Column
    private Long invCategoryId;
    
	public Long getInvCategoryId() {
		return invCategoryId;
	}

	public void setInvCategoryId(Long invCategoryId) {
		this.invCategoryId = invCategoryId;
	}

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

    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    public String getParentTree() {
        return parentTree;
    }

    public void setParentTree(String parentTree) {
        this.parentTree = parentTree;
    }

    public Double getSortId() {
        return sortId;
    }

    public void setSortId(Double sortId) {
        this.sortId = sortId;
    }

    public Boolean getIsRecommend() {
        return isRecommend;
    }

    public void setIsRecommend(Boolean isRecommend) {
        this.isRecommend = isRecommend;
    }

    public Long getParamCategoryId() {
        return paramCategoryId;
    }

    public void setParamCategoryId(Long paramCategoryId) {
        this.paramCategoryId = paramCategoryId;
    }

    public Long getChannelId() {
        return channelId;
    }

    public void setChannelId(Long channelId) {
        this.channelId = channelId;
    }

    public String getCallIndex() {
        return callIndex;
    }

    public void setCallIndex(String callIndex) {
        this.callIndex = callIndex;
    }

    public Long getMenuId() {
        return menuId;
    }

    public void setMenuId(Long menuId) {
        this.menuId = menuId;
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

    public Long getLayerCount() {
        return layerCount;
    }

    public void setLayerCount(Long layerCount) {
        this.layerCount = layerCount;
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

	public String getMainNumber() {
		return mainNumber;
	}

	public void setMainNumber(String mainNumber) {
		this.mainNumber = mainNumber;
	}
}
