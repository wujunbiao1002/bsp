package com.bsp.dao;

import com.bsp.entity.CheckLoanableBook;

import java.util.List;

public interface CheckLoanableBookMapper extends GenericMapper<CheckLoanableBook, Integer> {
	
	/**
	 * 计算分页查询中的记录总数
	 * @param queryObject 查询对象
	 */
	int getTotalCount(CheckLoanableBook queryObject);
	
	/**
	 * 分页高级查询
	 * @param queryObject 查询对象
	 */
	List<CheckLoanableBook> selectByQueryObject(CheckLoanableBook queryObject);

	/**
	 * @Author: 邬俊标
	 * @Description: 统计审核中的个个转态数量
	 * @Date: 3:13 2018/10/19
	 * @Param:
	 * @Return:
	 *
	 * @param i*/
    Integer getNumByClbStatus(int i);
}