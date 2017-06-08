package com.ynyes.lyz.entity;

public enum AllocationTypeEnum {

	NEW(1, "新建"),

	SENT(2, "已出库"),

	ENTERED(3, "已入库"),

	CANCELLED(4, "已作废");

	private int value;

	private String name;

	private AllocationTypeEnum(int value, String name) {
		this.value = value;
		this.name = name;
	}

	public int getValue() {
		return value;
	}

	public String getName() {
		return name;
	}

	public static String getName(int value) {
		for (AllocationTypeEnum type : AllocationTypeEnum.values()) {
			if (type.getValue() == value) {
				return type.getName();
			}
		}
		return "未知";
	}

}
