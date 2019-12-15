package com.bsp.dao;

import com.bsp.dto.OrderQueryObject;
import com.bsp.entity.LendingRecord;

import java.util.List;

public interface LendingRecordMapper extends GenericMapper<LendingRecord, Integer> {

	/**
	 * 查找所有的记录
	 * @return 所有的对象
	 */
	List<LendingRecord> selectBylrStrutsLendingRecord();
	
	/**
	 * 计算分页查询中的记录总数
	 * @param queryObject 查询对象
	 */
	int getTotalCount(OrderQueryObject queryObject);
	
	/**
	 * 分页高级查询
	 * @param queryObject 查询对象
	 */
	List<LendingRecord> selectByQueryObject(OrderQueryObject queryObject);

	/**
	 * @Author: 邬俊标
	 * @Description: 通过主键查找记录
	 * @Date: 20:42 2018/10/18
	 * @Param: lrId
	 * @Return:
	 **/
	LendingRecord selectByPrimaryKey(Integer lrId);

	List<LendingRecord> selectAllByLrStruts();

	Integer getNumByLrStatus(int i);
}