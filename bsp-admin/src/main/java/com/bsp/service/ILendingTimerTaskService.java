package com.bsp.service;

public interface ILendingTimerTaskService {
	/**
	 * 检查借出记录LendingRecord中的订单，各种状态是否超时。
	 */
	void checkLendingRecordAllOvertime ();
}
