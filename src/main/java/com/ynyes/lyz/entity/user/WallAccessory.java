package com.ynyes.lyz.entity.user;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author lenovo
 * 墙面辅料实体类
 *
 */
@Entity
@Table(name = "td_wall_accessory")
public class WallAccessory {
	
	// 自增主键
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	//商品编码
	@Column(length=50,nullable=false)
	private String goodsCode;
	
	//商品名称
	@Column(length=100,nullable=false)
	private String goodsTitle;
	
	//零售价
	@Column(length=10,scale=2,nullable=false)
	private Double retailPrice;
	
	// 零售价
	@Column(length = 10, scale = 2, nullable = false)
	private Double vipPrice;

}
