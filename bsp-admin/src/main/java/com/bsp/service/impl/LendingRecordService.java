package com.bsp.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bsp.dao.LendingRecordMapper;
import com.bsp.dto.OrderQueryObject;
import com.bsp.entity.LendingRecord;
import com.bsp.exceptions.SystemErrorException;
import com.bsp.service.ILendingRecordService;
import com.bsp.utils.Page;

@Service("lendingRecordService")
public class LendingRecordService implements ILendingRecordService {
	
	@Autowired
	private LendingRecordMapper lendingRecordMapper;
	
	public void setLendingRecordMapper(LendingRecordMapper lendingRecordMapper) {
		this.lendingRecordMapper = lendingRecordMapper;
	}

	@Override
	public Page findByQueryObject(OrderQueryObject queryObject) {
		Integer totalCount = null;
		List<LendingRecord> list = null;
		try {
			totalCount = lendingRecordMapper.getTotalCount(queryObject);
			list = lendingRecordMapper.selectByQueryObject(queryObject);
		} catch (Exception e) {
			e.printStackTrace();
			throw new SystemErrorException("系统异常");
		}
		return new Page(list, totalCount, queryObject.getLimit(), queryObject.getPageNumber());
	}

	@Override
	public void sendMsg(Integer lrId, Integer sendTo, String subject, String content) {
		
	}

	@Override
	public LendingRecord findByPrimaryKey(Integer lrId) {
		LendingRecord lendingRecord = null;
		try {
			lendingRecord = lendingRecordMapper.selectByPrimaryKey(lrId);
		} catch (Exception e) {
			e.printStackTrace();
			throw new SystemErrorException("系统异常，获取订单信息失败");
		}
		return lendingRecord;
	}

}
