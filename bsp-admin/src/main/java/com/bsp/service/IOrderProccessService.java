package com.bsp.service;

import com.bsp.entity.Administrator;

public interface IOrderProccessService {
	
	/**
	 * 订单正常流转，根据当前订单状态决定下一个状态
	 * @param lrId 订单Id
	 * @param operator 操作人
	 */
	public void nextStep(Integer lrId, Administrator operator);
	
}
