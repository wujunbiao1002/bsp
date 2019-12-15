package com.bsp.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bsp.dao.LoanableBookMapper;
import com.bsp.dao.PrimaryClassificationMapper;
import com.bsp.dao.SecondaryClassificationMapper;
import com.bsp.dto.LoanableBookQueryObject;
import com.bsp.entity.LoanableBook;
import com.bsp.entity.PrimaryClassification;
import com.bsp.entity.SecondaryClassification;
import com.bsp.entity.User;
import com.bsp.exceptions.DataUpdateException;
import com.bsp.exceptions.SystemErrorException;
import com.bsp.service.ILoanableBookService;
import com.bsp.utils.Page;

@Service
@Transactional
public class LoanableBookService implements ILoanableBookService{
	@Autowired
	private LoanableBookMapper loanableBookMapper;
	@Autowired private SecondaryClassificationMapper secondaryClassificationMapper;
	@Autowired private PrimaryClassificationMapper primaryClassificationMapper;
	
	public void setLoanableBookMapper(LoanableBookMapper loanableBookMapper) {
		this.loanableBookMapper = loanableBookMapper;
	}

	@Override
	public LoanableBook getLoanableBookInforByid(Integer id) {
		return loanableBookMapper.selectByPrimaryKey(id);
	}

	@Override
	public Page getAllListBook(LoanableBookQueryObject queryObject) {
		LoanableBookQueryObject bookQueryObject = new LoanableBookQueryObject();
		bookQueryObject.setLimit(queryObject.getLimit());
		bookQueryObject.setOrder(queryObject.getOrder());
		bookQueryObject.setPageNumber(queryObject.getPageNumber());
		bookQueryObject.setSort(queryObject.getSort());
		List<LoanableBook> list = null;
		Integer total = null;
		try {
			list = loanableBookMapper.selectByQueryObject(bookQueryObject);
			total = loanableBookMapper.getTotalCount(bookQueryObject);
		} catch (Exception e) {
			e.printStackTrace();
			throw new SystemErrorException("系统错误，获取图书列表失败");
		}
		return new Page(list,total,queryObject.getLimit(),queryObject.getPageNumber());
	}

	@Override
	public Page getAllListBookByUUID(LoanableBookQueryObject queryObject) {
		LoanableBookQueryObject bookQueryObject = new LoanableBookQueryObject();
		bookQueryObject.setLimit(queryObject.getLimit());
		bookQueryObject.setOrder(queryObject.getOrder());
		bookQueryObject.setPageNumber(queryObject.getPageNumber());
		bookQueryObject.setSort(queryObject.getSort());
		bookQueryObject.setUuid(queryObject.getUuid());
		List<LoanableBook> list = null;
		Integer total = null;
		try {
			list = loanableBookMapper.selectByQueryObject(bookQueryObject);
			total = loanableBookMapper.getTotalCount(bookQueryObject);
		} catch (Exception e) {
			e.printStackTrace();
			throw new SystemErrorException("系统错误，获取图书列表失败");
		}
		return new Page(list,total,queryObject.getLimit(),queryObject.getPageNumber());
	}

	@Override
	public List<PrimaryClassification> getAllPrimary() {
		List<PrimaryClassification> list = null;
		try {
			list = primaryClassificationMapper.selectAll();
		} catch (Exception e) {
			e.printStackTrace();
			throw new SystemErrorException("系统错误，获取图书分类列表失败");
		}
		return list;
	}

	@Override
	public List<SecondaryClassification> getAllSecondary() {
		List<SecondaryClassification> list = null;
		try {
			list = secondaryClassificationMapper.selectAll();
		} catch (Exception e) {
			e.printStackTrace();
			throw new SystemErrorException("系统错误，获取图书分类列表失败");
		}
		return list;
	}


	@Override
	public Page getPrimaryListBook(LoanableBookQueryObject queryObject, Integer pcId) {
		LoanableBookQueryObject bookQueryObject = new LoanableBookQueryObject();
		bookQueryObject.setLimit(queryObject.getLimit());
		bookQueryObject.setOrder(queryObject.getOrder());
		bookQueryObject.setPageNumber(queryObject.getPageNumber());
		bookQueryObject.setSort(queryObject.getSort());
		bookQueryObject.setPrimaryClassification(primaryClassificationMapper.selectByPrimaryKey(pcId));
		List<LoanableBook> list = null;
		Integer total = null;
		try {
			list = loanableBookMapper.selectByQueryObject(bookQueryObject);
			total = loanableBookMapper.getTotalCount(bookQueryObject);
		} catch (Exception e) {
			e.printStackTrace();
			throw new SystemErrorException("系统错误，获取一级分类图书列表失败");
		}
		Page page = new Page(list,total,queryObject.getLimit(),queryObject.getPageNumber());
		return page;
	}

	@Override
	public Page getSecondaryListBook(LoanableBookQueryObject queryObject, Integer scId) {
		LoanableBookQueryObject bookQueryObject = new LoanableBookQueryObject();
		bookQueryObject.setLimit(queryObject.getLimit());
		bookQueryObject.setOrder(queryObject.getOrder());
		bookQueryObject.setPageNumber(queryObject.getPageNumber());
		bookQueryObject.setSort(queryObject.getSort());
		bookQueryObject.setSecondaryClassification(secondaryClassificationMapper.selectByPrimaryKey(scId));
		List<LoanableBook> list = null;
		Integer total = null;
		try {
			list = loanableBookMapper.selectByQueryObject(bookQueryObject);
			total = loanableBookMapper.getTotalCount(bookQueryObject);
		} catch (Exception e) {
			e.printStackTrace();
			throw new SystemErrorException("系统错误，获取二级分类图书列表失败");
		}
		Page page = new Page(list,total,queryObject.getLimit(),queryObject.getPageNumber());
		return page;
	}

	@Override
	public Page getSearchListBook(LoanableBookQueryObject queryObject, String bookName) {
		LoanableBookQueryObject bookQueryObject = new LoanableBookQueryObject();
		bookQueryObject.setLimit(queryObject.getLimit());
		bookQueryObject.setOrder(queryObject.getOrder());
		bookQueryObject.setPageNumber(queryObject.getPageNumber());
		bookQueryObject.setSort(queryObject.getSort());
		bookQueryObject.setSearch(bookName);
		List<LoanableBook> list = null;
		Integer total = null;
		try {
			list = loanableBookMapper.selectByQueryObject(bookQueryObject);
			total = loanableBookMapper.getTotalCount(bookQueryObject);
		} catch (Exception e) {
			e.printStackTrace();
			throw new SystemErrorException("系统错误，获取搜索列表失败");
		}
		Page page = new Page(list,total,queryObject.getLimit(),queryObject.getPageNumber());
		return page;
	}

	@Override
	public void close(User user, Integer lbId) {
		LoanableBook lb = null;
		try {
			lb = loanableBookMapper.selectByPrimaryKey(lbId);
		} catch (Exception e) {
			e.printStackTrace();
			throw new SystemErrorException("服务器异常，查找记录失败");
		}
		if (lb==null) {
			throw new DataUpdateException("记录不存在");
		}
		if (!lb.getUser().getUuid().equals(user.getUuid())) {
			throw new DataUpdateException("非本用户不允许操作");
		}
		if (lb.getIsDelete() == (byte)1) {//纪录已删除
			throw new DataUpdateException("图书已删除");
		}
		if (lb.getLbStatus() == (byte)0) {//已经是关闭状态
			throw new DataUpdateException("共享已关闭，无需操作");
		} else if (lb.getLbStatus() == (byte)2) {//已被管理员下架
			throw new DataUpdateException("不可操作，该图书已被管理员下架");
		}
		lb.setLbStatus((byte)0); // 关闭共享
		try {
			loanableBookMapper.updateByPrimaryKeySelective(lb);
		} catch (Exception e) {
			e.printStackTrace();
			throw new SystemErrorException("服务器异常，关闭共享失败");
		}
	}

	@Override
	public void open(User user, Integer lbId) {
		LoanableBook lb = null;
		try {
			lb = loanableBookMapper.selectByPrimaryKey(lbId);
		} catch (Exception e) {
			e.printStackTrace();
			throw new SystemErrorException("服务器异常，查找记录失败");
		}
		if (lb==null) {
			throw new DataUpdateException("记录不存在");
		}
		if (!lb.getUser().getUuid().equals(user.getUuid())) {
			throw new DataUpdateException("非本用户不允许操作");
		}
		if (lb.getIsDelete()  == (byte)1) {//纪录已删除
			throw new DataUpdateException("图书已删除");
		}
		if (lb.getLbStatus() == (byte)1) {//共享已开启
			throw new DataUpdateException("共享已开启，无需操作");
		} else if (lb.getLbStatus() == (byte)2) {//已被管理员下架
			throw new DataUpdateException("不可操作，该图书已被管理员下架");
		}
		lb.setLbStatus((byte)1); // 开启共享
		try {
			loanableBookMapper.updateByPrimaryKeySelective(lb);
		} catch (Exception e) {
			e.printStackTrace();
			throw new SystemErrorException("服务器异常，开启共享失败");
		}
	}

	@Override
	public void delete(User user, Integer lbId) {
		LoanableBook lb = null;
		try {
			lb = loanableBookMapper.selectByPrimaryKey(lbId);
		} catch (Exception e) {
			e.printStackTrace();
			throw new SystemErrorException("服务器异常，查找记录失败");
		}
		if (lb==null) {
			throw new DataUpdateException("记录不存在");
		}
		if (!lb.getUser().getUuid().equals(user.getUuid())) {
			throw new DataUpdateException("非本用户不允许操作");
		}
		if (lb.getIsDelete()  == (byte)1) {//纪录已删除
			throw new DataUpdateException("图书已删除");
		}
		lb.setIsDelete((byte)1); // 删除状态
		try {
			loanableBookMapper.updateByPrimaryKeySelective(lb);
		} catch (Exception e) {
			e.printStackTrace();
			throw new SystemErrorException("服务器异常，删除失败");
		}
	}

}
