package com.bsp.service;

import com.bsp.dto.CheckLoanableBookQueryObject;
import com.bsp.entity.CheckLoanableBook;
import com.bsp.utils.Page;

public interface IShareApplyService {
	
	/**
	 * 查找一页数据
	 * @param queryObject
	 */
	Page pageOfApply(CheckLoanableBookQueryObject queryObject);
	
	/**
	 * 添加共享申请
	 * @param checkLoanableBook 实体
	 */
	void addShare(CheckLoanableBook checkLoanableBook);
	
	/**
	 * 更新共享申请
	 * @param checkLoanableBook 实体
	 */
	void updateShare(CheckLoanableBook checkLoanableBook);
	
	/**
	 * 删除申请
	 * @param clbId　id
	 */
	void cancelApply(Integer clbId);
	
	/**
	 * 查找申请记录
	 * @param clbId id
	 */
	CheckLoanableBook findCheckLoanableBookById(Integer clbId);
	
}
