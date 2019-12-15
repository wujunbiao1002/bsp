package com.bsp.dto;

/**
 * 封装分页请求中的参数，如果查询时字段不够用，请继承并扩展此类
 * 
 * @author hayate
 *
 */
public class ClassificationQueryObject extends QueryObject {
	
	private Integer status; // 审核状态，0-未审核 1-审核未通过
	
	public ClassificationQueryObject() {
		super();
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Integer getStatus() {
		return status;
	}
	
}
