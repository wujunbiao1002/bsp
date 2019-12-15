package com.bsp.service;

import com.bsp.dto.OrderQueryObject;
import com.bsp.entity.LendingRecord;
import com.bsp.utils.Page;

public interface ILendingRecordService {
	
	/**
	 * 根据查询对象查找订单（结束记录）
	 * @param queryObject
	 * @return
	 */
	Page findByQueryObject(OrderQueryObject queryObject);
	
	/**
	 * 根据订单发送订单消息
	 * @param lrId 记录Id
	 * @param sendTo 接收方,0-借出方，1-借入方
	 * @param subject 主题，限20个字符
	 * @param content 正文，限1000个字符
	 */
	void sendMsg(Integer lrId, Integer sendTo, String subject, String content);
	
	/**
	 * 根据主键获取一张订单
	 * @param lrId 订单Id
	 */
	LendingRecord findByPrimaryKey(Integer lrId);
}
