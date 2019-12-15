package com.bsp.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bsp.dao.CheckLoanableBookMapper;
import com.bsp.dto.CheckLoanableBookQueryObject;
import com.bsp.entity.CheckLoanableBook;
import com.bsp.exceptions.DataUpdateException;
import com.bsp.exceptions.SystemErrorException;
import com.bsp.service.IShareApplyService;
import com.bsp.utils.Page;

@Service
public class ShareApplyService implements IShareApplyService {
	
	@Autowired
	private CheckLoanableBookMapper checkLoanableBookMapper;
	
	@Override
	public void addShare(CheckLoanableBook checkLoanableBook) {
		checkLoanableBook.setClbStatus((byte)0);// 转台0，未审核
		try {
			checkLoanableBookMapper.insertSelective(checkLoanableBook);
		} catch (Exception e) {
			e.printStackTrace();
			throw new SystemErrorException("服务器异常，提交申请失败");
		}
	}
	
	@Override
	public void updateShare(CheckLoanableBook checkLoanableBook) {
		try {
			checkLoanableBookMapper.updateByPrimaryKeySelective(checkLoanableBook);
			if (checkLoanableBook.getClbStatus() == (byte)2) {
				throw new DataUpdateException("更新失败，申请已通过审核");
			}
		} catch (DataUpdateException e) {
			e.printStackTrace();
			throw new DataUpdateException(e.getMessage());
		}catch (Exception e) {
			e.printStackTrace();
			throw new SystemErrorException("服务器异常，更新失败");
		}
	}
	
	@Override
	public void cancelApply(Integer clbId) {
		CheckLoanableBook record = checkLoanableBookMapper.selectByPrimaryKey(clbId);
		if (record == null) {
			throw new DataUpdateException("删除失败，记录不存在");
		}
		if (record.getClbStatus() == (byte)2) {
			throw new DataUpdateException("删除失败，该申请已通过审核");
		}
		try {
			checkLoanableBookMapper.deleteByPrimaryKey(clbId);
		} catch (Exception e) {
			e.printStackTrace();
			throw new SystemErrorException("服务器异常，删除记录失败");
		}
	}
	
	@Override
	public Page pageOfApply(CheckLoanableBookQueryObject queryObject) {
		Integer totalCount = 0;
		List<CheckLoanableBook> list = null;
		try {
			totalCount = checkLoanableBookMapper.getTotalCount(queryObject);
			list = checkLoanableBookMapper.selectByQueryObject(queryObject);
		} catch (Exception e) {
			e.printStackTrace();
			throw new SystemErrorException("服务器异常，提交申请失败");
		}
		return new Page(list, totalCount, queryObject.getLimit(), queryObject.getPageNumber());
	}
	
	@Override
	public CheckLoanableBook findCheckLoanableBookById(Integer clbId) {
		
		CheckLoanableBook record = null;
		try {
			record = checkLoanableBookMapper.selectByPrimaryKey(clbId);
			if (record == null) {
				throw new NullPointerException("记录不存在");
			}
		} catch (NullPointerException e) {
			e.printStackTrace();
			throw new NullPointerException(e.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
			throw new SystemErrorException("服务器异常，查找记录失败");
		}
		return record;
	}

	public CheckLoanableBookMapper getCheckLoanableBookMapper() {
		return checkLoanableBookMapper;
	}

	public void setCheckLoanableBookMapper(CheckLoanableBookMapper checkLoanableBookMapper) {
		this.checkLoanableBookMapper = checkLoanableBookMapper;
	}

}
