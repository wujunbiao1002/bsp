package com.bsp.dto;

public class CheckLoanableBookQueryObject extends QueryObject {
	
	private String uuid; // 用户uuid
	
	public CheckLoanableBookQueryObject() {
		super();
	}

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}
	
	
	
}
