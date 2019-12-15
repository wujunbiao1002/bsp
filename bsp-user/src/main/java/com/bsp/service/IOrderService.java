package com.bsp.service;


import com.bsp.dto.OrderQueryObject;
import com.bsp.entity.LendingRecord;
import com.bsp.utils.Page;

public interface IOrderService {
	
	/**
	 * 获取Mapping表里的管理员联系电话
	 * @return
	 */
	String getContact_phones();
	
	/**
	 * 获取Mapping表里的取书地点
	 * @return
	 */
	String getTransfer_station();
	
	/**
	 * 添加订单
	 */
	void addOrder(LendingRecord lendingRecord);
	
	/**
	 * 获取分页的订单数据
	 * @param queryObject
	 */
	Page getAllListOrder(OrderQueryObject queryObject);
	
	void updata(Integer lrId);
}
