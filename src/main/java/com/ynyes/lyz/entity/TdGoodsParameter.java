package com.ynyes.lyz.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 * 商品类型<->属性关联表
 * 
 * @author Sharon
 *
 */

@Entity
public class TdGoodsParameter {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    
    // 参数ID
    @Column
	private Long paramId;
	
	// 参数名称
    @Column
	private String paramName;
    
    // 参数类型
    @Column
    private String paramCategory;
    
    // 参数值
    @Column
    private String value;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getParamId() {
        return paramId;
    }

    public void setParamId(Long paramId) {
        this.paramId = paramId;
    }

    public String getParamName() {
        return paramName;
    }

    public void setParamName(String paramName) {
        this.paramName = paramName;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getParamCategory() {
        return paramCategory;
    }

    public void setParamCategory(String paramCategory) {
        this.paramCategory = paramCategory;
    }
}
