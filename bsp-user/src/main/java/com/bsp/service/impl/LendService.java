package com.bsp.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bsp.dao.LendingRecordMapper;
import com.bsp.dao.LoanableBookMapper;
import com.bsp.dto.OrderQueryObject;
import com.bsp.entity.LendingRecord;
import com.bsp.entity.LoanableBook;
import com.bsp.exceptions.DataUpdateException;
import com.bsp.exceptions.SystemErrorException;
import com.bsp.service.ILendService;
import com.bsp.utils.Page;

@Service
public class LendService implements ILendService {
	
	@Autowired
	private LendingRecordMapper lendingRecordMapper;
	@Autowired
	private LoanableBookMapper loanableBookMapper;
	
	public void setLendingRecordMapper(LendingRecordMapper lendingRecordMapper) {
		this.lendingRecordMapper = lendingRecordMapper;
	}

	public void setLoanableBookMapper(LoanableBookMapper loanableBookMapper) {
		this.loanableBookMapper = loanableBookMapper;
	}


	@Override
	public void agree(Integer lrId) {
		LendingRecord record = null;
		try {
			record = lendingRecordMapper.selectByPrimaryKey(lrId);
		} catch (Exception e) {
			e.printStackTrace();
			throw new SystemErrorException("系统异常，获取订单信息失败");
		}
		Byte status = record.getLrStruts();
		// 只有状态为0才能操作
		if (status != 0) {
			throw new DataUpdateException("操作失败，订单状态已发生改变，请刷新页面重新操作");
		}
		record.setLrStruts((byte)4);
		record.setAgreeTime(new Date());
		try {
			lendingRecordMapper.updateByPrimaryKey(record);
		} catch (Exception e) {
			e.printStackTrace();
			throw new SystemErrorException("系统异常，操作失败");
		}
	}
	
	@Override
	public void deny(Integer lrId) {
		LendingRecord record = null;
		try {
			record = lendingRecordMapper.selectByPrimaryKey(lrId);
		} catch (Exception e) {
			e.printStackTrace();
			throw new SystemErrorException("系统错误，获取订单信息失败");
		}
		Byte status = record.getLrStruts();
		// 只有状态为0才能操作
		if (status != 0) {
			throw new DataUpdateException("操作失败，订单状态已发生改变，请刷新页面重新操作");
		}
		record.setLrStruts((byte)2);
		LoanableBook book = record.getLoanableBook();
		book.setLeft(book.getLeft() + record.getAmount());// 恢复可借数量
		try {
			lendingRecordMapper.updateByPrimaryKey(record);
			loanableBookMapper.updateByPrimaryKey(book);
		} catch (Exception e) {
			e.printStackTrace();
			throw new SystemErrorException("系统异常，操作失败");
		}
	}
	
	@Override
	public Page getPage(OrderQueryObject queryObject) {
		Integer totalCount = null;
		List<LendingRecord> list = null;
		try {
			totalCount = lendingRecordMapper.getTotalCountOfLend(queryObject);
			list = lendingRecordMapper.selectByQueryObjectOfLend(queryObject);
		} catch (Exception e) {
			e.printStackTrace();
			throw new SystemErrorException("系统异常，查询数据失败");
		}
		return new Page(list, totalCount, queryObject.getLimit(), queryObject.getPageNumber());
	}
	
}
