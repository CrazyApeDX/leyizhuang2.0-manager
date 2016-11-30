package com.ynyes.lyz.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;


/**
 * 用户等级
 * 
 * 用户等级信息
 * 
 * @author Sharon
 *
 */

@Entity
public class TdUserLevel {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
	
    // 级别
    @Column(unique=true)
    private Long levelId;
    
	// 等级名称
	@Column
	private String title;
	
	// 需要累计消费额
    @Column(scale=2)
    private Double requiredConsumption;
    
    // 优惠比例
    @Column(scale=2)
    private Double discountRatio;
    
    //特权 @zhangji
    @Column
    private String privilege;
    
    //特权id @zhangji
    @Column(unique=true) 
    private Long privilegeId;
    
    // 是否默认
    @Column
    private Boolean isEnable;
    
    // 排序号
    @Column
    private Double sortId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getLevelId() {
        return levelId;
    }

    public void setLevelId(Long levelId) {
        this.levelId = levelId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Double getRequiredConsumption() {
        return requiredConsumption;
    }

    public void setRequiredConsumption(Double requiredConsumption) {
        this.requiredConsumption = requiredConsumption;
    }

    public Double getDiscountRatio() {
        return discountRatio;
    }

    public void setDiscountRatio(Double discountRatio) {
        this.discountRatio = discountRatio;
    }

    public Boolean getIsEnable() {
        return isEnable;
    }

    public void setIsEnable(Boolean isEnable) {
        this.isEnable = isEnable;
    }

    public Double getSortId() {
        return sortId;
    }

    public void setSortId(Double sortId) {
        this.sortId = sortId;
    }

	public String getPrivilege() {
		return privilege;
	}

	public void setPrivilege(String privilege) {
		this.privilege = privilege;
	}

	public Long getPrivilegeId() {
		return privilegeId;
	}

	public void setPrivilegeId(Long privilegeId) {
		this.privilegeId = privilegeId;
	}
	
    
}
