package com.bsp.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bsp.dao.LendingRecordMapper;
import com.bsp.dao.LoanableBookMapper;
import com.bsp.dao.MappingMapper;
import com.bsp.dto.OrderQueryObject;
import com.bsp.entity.LendingRecord;
import com.bsp.entity.LoanableBook;
import com.bsp.exceptions.DataUpdateException;
import com.bsp.exceptions.SystemErrorException;
import com.bsp.service.IOrderService;
import com.bsp.utils.Page;

@Service
@Transactional
public class OrderService implements IOrderService{

	@Autowired
	private MappingMapper mappingMapper;
	public void setMappingMapper(MappingMapper mappingMapper) {
		this.mappingMapper = mappingMapper;
	}
	
	@Autowired
	private LendingRecordMapper lendingRecordMapper;
	public void setLendingRecordMapper(LendingRecordMapper lendingRecordMapper) {
		this.lendingRecordMapper = lendingRecordMapper;
	}
	
	@Autowired
	private LoanableBookMapper loanableBookMapper;
	public void setLoanableBookMapper(LoanableBookMapper loanableBookMapper) {
		this.loanableBookMapper = loanableBookMapper;
	}
	
	@Override
	public String getContact_phones() {
		String contact_phones = null;
		try {
			contact_phones = mappingMapper.selectByPrimaryKey("contact_phones").getmValue();
		} catch (Exception e) {			
			e.printStackTrace();
			throw new SystemErrorException("系统错误，获取管理员联系电话失败");
		}
		return contact_phones;
	}
	@Override
	public String getTransfer_station() {
		String transfer_station = null;
		try {
			transfer_station = mappingMapper.selectByPrimaryKey("transfer_station").getmValue();
		} catch (Exception e) {			
			e.printStackTrace();
			throw new SystemErrorException("系统错误，获取取书地点失败");
		}
		return transfer_station;
	}
	@Override
	public void addOrder(LendingRecord lendingRecord) {
		LoanableBook book = null;
		try {
			book = loanableBookMapper.selectByPrimaryKey(lendingRecord.getLoanableBook().getLbId());
		} catch (Exception e2) {
			e2.printStackTrace();
			throw new SystemErrorException("系统异常，获取图书数据失败");
		}
		if (book.getUser().getUuid().equals(lendingRecord.getUser().getUuid())) {
			throw new DataUpdateException("不允许借阅自己共享的图书");			
		}
		if(book.getLeft()==0) {
			throw new DataUpdateException("图书剩余可借数量为0");
		}
		book.setLeft(book.getLeft()-lendingRecord.getAmount());
		try {
			loanableBookMapper.updateByPrimaryKeySelective(book);
		} catch (Exception e1) {			
			e1.printStackTrace();
			throw new SystemErrorException("系统异常，更新图书数据失败");
		}
		lendingRecord.setCreateTime(new Date());
		lendingRecord.setLrStruts(new Byte("0"));
		try {
			lendingRecordMapper.insertSelective(lendingRecord);
		} catch (Exception e) {
			e.printStackTrace();
			throw new SystemErrorException("系统异常，插入数据失败");
		}
	}

	@Override
	public Page getAllListOrder(OrderQueryObject queryObject) {
		List<LendingRecord> list = null;
		Integer total = null;
		try {
			list = lendingRecordMapper.selectByQueryObject(queryObject);
			total = lendingRecordMapper.getTotalCount(queryObject);
		} catch (Exception e) {
			e.printStackTrace();
			throw new SystemErrorException("系统错误，获取借阅记录失败");
		}
		return new Page(list,total,queryObject.getLimit(),queryObject.getPageNumber());
	}

	@Override
	public void updata(Integer lrId) {
		LendingRecord record = null;
		try {
			record = lendingRecordMapper.selectByPrimaryKey(lrId);
		} catch (Exception e) {			
			e.printStackTrace();
			throw new SystemErrorException("系统错误，获取订单记录失败");
		}
		LoanableBook book = null;
		try {
			book = loanableBookMapper.selectByPrimaryKey(record.getLoanableBook().getLbId());
		} catch (Exception e1) {			
			e1.printStackTrace();
			throw new SystemErrorException("系统异常，获取图书数据失败");
		}
		book.setLeft(book.getLeft()+record.getAmount());
		try {
			loanableBookMapper.updateByPrimaryKeySelective(book);
		} catch (Exception e1) {			
			e1.printStackTrace();
			throw new SystemErrorException("系统异常，更新图书数据失败");
		}
		record.setLrStruts(new Byte("1"));
		try {
			lendingRecordMapper.updateByPrimaryKeySelective(record);
		} catch (Exception e) {			
			e.printStackTrace();
			throw new SystemErrorException("系统错误，更新订单记录失败");
		}
	}
	
	

}
