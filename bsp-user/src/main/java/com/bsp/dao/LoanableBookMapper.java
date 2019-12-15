package com.bsp.dao;

import java.util.List;

import com.bsp.dto.LoanableBookQueryObject;
import com.bsp.entity.LoanableBook;

public interface LoanableBookMapper extends GenericMapper<LoanableBook, Integer> {
	
	/**
	 * 计算分页查询中的记录总数
	 * @param queryObject 查询对象
	 */
	int getTotalCount(LoanableBookQueryObject queryObject);
	
	/**
	 * 分页高级查询
	 * @param queryObject 查询对象
	 */
	List<LoanableBook> selectByQueryObject(LoanableBookQueryObject queryObject);
	
}