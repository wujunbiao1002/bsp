package com.bsp.dto;

/**
 * 封装LendingRecord和lendingHistory的查询参数
 * @author hayate
 *
 */
public class OrderQueryObject extends QueryObject {
	
	private Integer status;// 订单状态，0-进行中 1-异常 2-已结束 3-全部

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}
	
}
