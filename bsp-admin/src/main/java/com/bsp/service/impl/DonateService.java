package com.bsp.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bsp.dao.DonatedBookMapper;
import com.bsp.dao.LendingRecordMapper;
import com.bsp.dto.QueryObject;
import com.bsp.entity.DonatedBook;
import com.bsp.entity.LendingRecord;
import com.bsp.exceptions.SystemErrorException;
import com.bsp.service.IDonateService;
import com.bsp.utils.Page;

@Service("donateService")
public class DonateService implements IDonateService {
	
	@Autowired
	private DonatedBookMapper donatedBookMapper;
	
	@Autowired
	private LendingRecordMapper lendingRecordMapper;

	public void setLendingRecordMapper(LendingRecordMapper lendingRecordMapper) {
		this.lendingRecordMapper = lendingRecordMapper;
	}

	public void setDonatedBookMapper(DonatedBookMapper donatedBookMapper) {
		this.donatedBookMapper = donatedBookMapper;
	}

	@Override
	public void donateFormLendingRecord(Integer lrId) {
		try {
			LendingRecord lr = lendingRecordMapper.selectByPrimaryKey(lrId);
			DonatedBook db = new DonatedBook();
			db.setDobName(lr.getLoanableBook().getLbName());
			db.setIsbn(lr.getLoanableBook().getIsbn());
			db.setSecondaryClassification(lr.getLoanableBook().getSecondaryClassification());
			db.setNumber(lr.getAmount());
			db.setPhone(lr.getLoanableBook().getPhone());
			db.setSource("共享记录" + lr.getLrId());
			db.setTime(new Date());
			db.setUser(lr.getLoanableBook().getUser());
			db.setDonor(lr.getUser().getMail());
			lr.setLrStruts(new Byte("11"));// 设置订单为捐赠状态
			lendingRecordMapper.updateByPrimaryKeySelective(lr);
			donatedBookMapper.insertSelective(db);
		} catch (Exception e) {
			e.printStackTrace();
			throw new SystemErrorException("捐赠失败，系统错误");
		}
	}

	@Override
	public void donate(DonatedBook donatedBook) {
		try {
			donatedBookMapper.insert(donatedBook);
		} catch (Exception e) {
			e.printStackTrace();
			throw new SystemErrorException("系统异常，捐赠失败");
		}
	}

	@Override
	public Page findByQueryObject(QueryObject queryObject) {
		Integer totalCount = null;
		List<DonatedBook> list = null;
		try {
			totalCount = donatedBookMapper.getTotalCount(queryObject);
			list = donatedBookMapper.selectByQueryObject(queryObject);
		} catch (Exception e) {
			e.printStackTrace();
			throw new SystemErrorException("系统异常，查询数据失败");
		}
		return new Page(list, totalCount, queryObject.getLimit(), queryObject.getPageNumber());
	}

	@Override
	public DonatedBook findByPrimaryKey(Integer id) {
		DonatedBook db = null;
		try {
			db = donatedBookMapper.selectByPrimaryKey(id);
		} catch (Exception e) {
			e.printStackTrace();
			throw new SystemErrorException("系统异常，查询数据失败");
		}
		return db;
	}

	@Override
	public void add(DonatedBook donatedBook) {
		try {
			donatedBook.setTime(new Date());
			donatedBookMapper.insertSelective(donatedBook);
		} catch (Exception e) {
			e.printStackTrace();
			throw new SystemErrorException("系统异常，添加失败");
		}
	}

	@Override
	public void update(DonatedBook donatedBook) {
		try {
			donatedBookMapper.updateByPrimaryKeySelective(donatedBook);
		} catch (Exception e) {
			e.printStackTrace();
			throw new SystemErrorException("系统异常，更新失败");
		}
	}
	
}
