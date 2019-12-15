package com.bsp.dao;

import java.util.List;

import com.bsp.dto.OrderQueryObject;
import com.bsp.entity.LendingRecord;

public interface LendingRecordMapper extends GenericMapper<LendingRecord, Integer> {
	/**
	 * 计算分页查询中的记录总数(借入)
	 * @param queryObject 查询对象
	 */
	int getTotalCount(OrderQueryObject queryObject);
	
	/**
	 * 分页高级查询(借入)
	 * @param queryObject 查询对象
	 */
	List<LendingRecord> selectByQueryObject(OrderQueryObject queryObject);
	
	/**
	 * 计算分页查询中的记录总数(借出)
	 * @param queryObject 查询对象
	 */
	int getTotalCountOfLend(OrderQueryObject queryObject);
	
	/**
	 * 分页高级查询(借出)
	 * @param queryObject 查询对象
	 */
	List<LendingRecord> selectByQueryObjectOfLend(OrderQueryObject queryObject);
	
	
}