package com.bsp.service;

import com.bsp.dto.OrderQueryObject;
import com.bsp.utils.Page;

public interface ILendService {
	
	/**
	 * 同意借出
	 * @param lrId 记录ID
	 */
	void agree(Integer lrId);
	
	/**
	 * 拒绝借出
	 * @param lrId
	 */
	void deny(Integer lrId);
	
	/**
	 * 分页查询
	 * @param queryObject
	 */
	Page getPage(OrderQueryObject queryObject);
	
}
