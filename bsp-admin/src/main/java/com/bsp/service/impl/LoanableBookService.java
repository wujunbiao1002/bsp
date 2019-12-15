package com.bsp.service.impl;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bsp.dao.LoanableBookMapper;
import com.bsp.dto.LoanableBookQueryObject;
import com.bsp.entity.LoanableBook;
import com.bsp.exceptions.DataUpdateException;
import com.bsp.exceptions.SystemErrorException;
import com.bsp.service.ILoanableBookService;
import com.bsp.utils.Page;

@Service("loanableBookService")
public class LoanableBookService implements ILoanableBookService {
	
	@Autowired
	private LoanableBookMapper loanableBookMapper;
	
	public void setLoanableBookMapper(LoanableBookMapper loanableBookMapper) {
		this.loanableBookMapper = loanableBookMapper;
	}

	@Override
	public Page findByQueryObject(LoanableBookQueryObject queryObject) {
		Integer totalCount = null;
		List<LoanableBook> list = null;
		try {
			totalCount = loanableBookMapper.getTotalCount(queryObject);
			list = loanableBookMapper.selectByQueryObject(queryObject);
		} catch (Exception e) {
			e.printStackTrace();
			throw new SystemErrorException("系统异常，查询数据失败");
		}
		return new Page(list, totalCount, queryObject.getLimit(), queryObject.getPageNumber());
	}

	@Override
	public void Shelve(Integer lbId) {
		LoanableBook lb = null;
		try {
			lb = loanableBookMapper.selectByPrimaryKey(lbId);
		} catch (Exception e) {
			e.printStackTrace();
			throw new SystemErrorException("查找记录失败");
		}
		if (lb==null) {
			throw new DataUpdateException("记录不存在");
		}
		if (lb.getLbStatus()==new Byte("0")||lb.getLbStatus()==new Byte("1")) {//已开启
			throw new DataUpdateException("图书已经是上架状态");
		} else if (lb.getIsDelete()==new Byte("1")) {//纪录已删除
			throw new DataUpdateException("图书已删除");
		}
		lb.setLbStatus(new Byte("0"));
		try {
			loanableBookMapper.updateByPrimaryKeySelective(lb);
		} catch (Exception e) {
			e.printStackTrace();
			throw new SystemErrorException("更新记录失败");
		}
	}

	@Override
	public void Unshelve(Integer lbId) {
		LoanableBook lb = null;
		try {
			lb = loanableBookMapper.selectByPrimaryKey(lbId);
		} catch (Exception e) {
			e.printStackTrace();
			throw new SystemErrorException("查找记录失败");
		}
		if (lb==null) {
			throw new DataUpdateException("记录不存在");
		}
		if (lb.getLbStatus()==new Byte("2")) {//已下架
			throw new DataUpdateException("图书已经是下架状态");
		} else if (lb.getIsDelete()==new Byte("1")) {//纪录已删除
			throw new DataUpdateException("图书已删除");
		}
		lb.setLbStatus(new Byte("2"));
		try {
			loanableBookMapper.updateByPrimaryKeySelective(lb);
		} catch (Exception e) {
			e.printStackTrace();
			throw new SystemErrorException("更新记录失败");
		}
	}

}
