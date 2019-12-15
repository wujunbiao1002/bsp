package com.bsp.service;

import com.bsp.dto.QueryObject;
import com.bsp.entity.DonatedBook;
import com.bsp.utils.Page;

public interface IDonateService {
	
	/**
	 * 来自订单捐赠
	 * @param lrId 订单记录Id
	 */
	void donateFormLendingRecord(Integer lrId);
	
	/**
	 * 捐赠
	 * @param donatedBook 捐赠实体
	 */
	void donate(DonatedBook donatedBook);
	
	/**
	 * 查找一页数据
	 */
	Page findByQueryObject(QueryObject queryObject);
	
	/**
	 * 查找一条记录
	 * @param id
	 */
	DonatedBook findByPrimaryKey(Integer id);
	
	/**
	 * 添加一条记录
	 */
	void add(DonatedBook donatedBook);
	
	/**
	 * 更新一条记录
	 */
	void update(DonatedBook donatedBook);
	
}
