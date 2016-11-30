package com.ynyes.lyz.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;


/**
 * 产品
 *
 * 记录了产品的相关信息
 * 
 * @author Sharon
 *
 */

@Entity
public class TdProduct {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    
    // 产品名称
    @Column
    private String title;
    
    // 货号
    @Column
    private String productNumber;
    
    // 产品分类ID
    @Column
    private Long productCategoryId;
    
    // 产品分类层级
    @Column
    private String productCategoryTree;
    
    // 筛选项数量, 最多支持3项
    @Column
    private Integer totalSelects;
    
    // 筛选项1名称
    @Column
    private String selectOneName;
    
    // 筛选项2名称
    @Column
    private String selectTwoName;
    
    // 筛选项3名称
    @Column
    private String selectThreeName;

//    // 筛选项4名称
//    @Column
//    private String selectFourName;
    
    // 排序号
    @Column
    private Double sortId;
    
    // 调用别名
    @Column
    private String callIndex;
    
    // SEO标题
    @Column
    private String seoTitle;
    
    // SEO关键字
    @Column
    private String seoKeywords;
    
    // SEO描述
    @Column
    private String seoDescription;

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

    public String getProductNumber() {
        return productNumber;
    }

    public void setProductNumber(String productNumber) {
        this.productNumber = productNumber;
    }

	public Long getProductCategoryId() {
        return productCategoryId;
    }

    public void setProductCategoryId(Long productCategoryId) {
        this.productCategoryId = productCategoryId;
    }

    public String getProductCategoryTree() {
        return productCategoryTree;
    }

    public void setProductCategoryTree(String productCategoryTree) {
        this.productCategoryTree = productCategoryTree;
    }

    public Double getSortId() {
        return sortId;
    }

    public void setSortId(Double sortId) {
        this.sortId = sortId;
    }

    public String getCallIndex() {
        return callIndex;
    }

    public void setCallIndex(String callIndex) {
        this.callIndex = callIndex;
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

    public Integer getTotalSelects() {
        return totalSelects;
    }

    public void setTotalSelects(Integer totalSelects) {
        this.totalSelects = totalSelects;
    }

    public String getSelectOneName() {
        return selectOneName;
    }

    public void setSelectOneName(String selectOneName) {
        this.selectOneName = selectOneName;
    }

    public String getSelectTwoName() {
        return selectTwoName;
    }

    public void setSelectTwoName(String selectTwoName) {
        this.selectTwoName = selectTwoName;
    }

    public String getSelectThreeName() {
        return selectThreeName;
    }

    public void setSelectThreeName(String selectThreeName) {
        this.selectThreeName = selectThreeName;
    }
}
