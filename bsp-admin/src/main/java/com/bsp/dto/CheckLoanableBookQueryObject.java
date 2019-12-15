package com.bsp.dto;

/**
 * 封装分页请求中的参数，如果查询时字段不够用，请继承并扩展此类
 * 
 * @author hayate
 *
 */
public class CheckLoanableBookQueryObject extends QueryObject {
	
	private Integer clbStatus; // 审核状态，0-未审核 1-审核未通过
	
	public CheckLoanableBookQueryObject() {
		super();
	}

	public Integer getClbStatus() {
		return clbStatus;
	}

	public void setClbStatus(Integer clbStatus) {
		this.clbStatus = clbStatus;
	}
	
}
