package com.bsp.dao;

import java.util.List;

import com.bsp.dto.CheckLoanableBookQueryObject;
import com.bsp.entity.CheckLoanableBook;

public interface CheckLoanableBookMapper extends GenericMapper<CheckLoanableBook, Integer> {
	
	/**
	 * 计算分页查询中的记录总数
	 * @param queryObject 查询对象
	 */
	int getTotalCount(CheckLoanableBookQueryObject queryObject);
	
	/**
	 * 分页高级查询
	 * @param queryObject 查询对象
	 */
	List<CheckLoanableBook> selectByQueryObject(CheckLoanableBookQueryObject queryObject);
	
}