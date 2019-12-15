package com.bsp.service;

import com.bsp.dto.LoanableBookQueryObject;
import com.bsp.utils.Page;

public interface ILoanableBookService {
	
	Page findByQueryObject(LoanableBookQueryObject queryObject);
	
	/**
	 * 上架
	 * @param lbId 图书Id
	 */
	void Shelve(Integer lbId);
	
	/**
	 * 下架
	 * @param lbId 图书Id
	 */
	void Unshelve(Integer lbId);
	
}
