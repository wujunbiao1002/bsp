package com.bsp.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bsp.dao.CheckLoanableBookMapper;
import com.bsp.dao.LoanableBookMapper;
import com.bsp.dao.MappingMapper;
import com.bsp.dao.NewsMapper;
import com.bsp.dto.CheckLoanableBookQueryObject;
import com.bsp.entity.CheckLoanableBook;
import com.bsp.entity.LoanableBook;
import com.bsp.entity.Mapping;
import com.bsp.entity.News;
import com.bsp.exceptions.SystemErrorException;
import com.bsp.service.ICheckLoanableBookService;
import com.bsp.utils.Page;

@Service("checkLoanableBookService")
public class CheckLoanableBookService implements ICheckLoanableBookService {
	
	@Autowired
	private CheckLoanableBookMapper checkLoanableBookMapper;
	
	@Autowired
	private LoanableBookMapper loanableBookMapper;
	
	@Autowired
	private NewsMapper newsMapper;
	
	@Autowired
	private MappingMapper mappingMapper; //系统设置信息
	
	public void setMappingMapper(MappingMapper mappingMapper) {
		this.mappingMapper = mappingMapper;
	}

	public void setNewsMapper(NewsMapper newsMapper) {
		this.newsMapper = newsMapper;
	}

	public void setLoanableBookMapper(LoanableBookMapper loanableBookMapper) {
		this.loanableBookMapper = loanableBookMapper;
	}

	public void setCheckLoanableBookMapper(CheckLoanableBookMapper checkLoanableBookMapper) {
		this.checkLoanableBookMapper = checkLoanableBookMapper;
	}

	@Override
	public Page findByQueryObject(CheckLoanableBookQueryObject queryObject) {
		Integer totalCount = null;
		List<CheckLoanableBook> list = null;
		try {
			totalCount = checkLoanableBookMapper.getTotalCount(queryObject);
			list = checkLoanableBookMapper.selectByQueryObject(queryObject);
		} catch (Exception e) {
			e.printStackTrace();
			throw new SystemErrorException("系统异常");
		}
		return new Page(list, totalCount, queryObject.getLimit(), queryObject.getPageNumber());
	}

	@Override
	public void approve(Integer clbId) {
		try {
			CheckLoanableBook checkLoanableBook = checkLoanableBookMapper.selectByPrimaryKey(clbId);
			LoanableBook loanableBook = new LoanableBook();
			this.assembleLoanableBook(checkLoanableBook, loanableBook);
			checkLoanableBook.setClbStatus(new Byte("2"));// 审核通过状态改为2
			checkLoanableBookMapper.updateByPrimaryKeySelective(checkLoanableBook);//拼装LoanableBook
			loanableBookMapper.insertSelective(loanableBook);
			sendMsg(true, checkLoanableBook.getUser().getUuid(), checkLoanableBook);//结果反馈
		} catch (Exception e) {
			e.printStackTrace();
			throw new SystemErrorException("系统异常，操作失败");
		}
	}

	@Override
	public void deny(Integer clbId, String failureMsg) {
		try {
			CheckLoanableBook checkLoanableBook = checkLoanableBookMapper.selectByPrimaryKey(clbId);
			checkLoanableBook.setClbStatus(new Byte("1"));// 审核失败状态改为1
			checkLoanableBook.setFailureCause(failureMsg);// 失败原因
			checkLoanableBookMapper.updateByPrimaryKeySelective(checkLoanableBook);//拼装LoanableBook
			sendMsg(false, checkLoanableBook.getUser().getUuid(), checkLoanableBook);//结果反馈
		} catch (Exception e) {
			e.printStackTrace();
			throw new SystemErrorException("系统异常，操作失败");
		}
	}
	
	/**
	 * 拼装LoanableBook
	 * @param checkLoanableBook 审核通过的书
	 * @param loanableBook
	 */
	private void assembleLoanableBook(CheckLoanableBook checkLoanableBook, LoanableBook loanableBook) {
		loanableBook.setLbName(checkLoanableBook.getClbName());
		loanableBook.setLbAuthor(checkLoanableBook.getClbAuthor());
		loanableBook.setLbPublishing(checkLoanableBook.getClbPublishing());
		loanableBook.setIsbn(checkLoanableBook.getIsbn());
		loanableBook.setLbDuratuin(checkLoanableBook.getClbDuration());
		loanableBook.setLbNumber(checkLoanableBook.getClbNumber());
		loanableBook.setImagePath(checkLoanableBook.getImagePath());
		loanableBook.setLbComment(checkLoanableBook.getClbComment());
		loanableBook.setLeft(checkLoanableBook.getClbNumber());
		loanableBook.setPhone(checkLoanableBook.getPhone());
		loanableBook.setOpenLoanTime(new Date());
		loanableBook.setTotalLending(0);
		loanableBook.setLbStatus(new Byte("1"));// 开启共享
		loanableBook.setIsDelete(new Byte("0"));//未删除
		loanableBook.setSecondaryClassification(checkLoanableBook.getSecondaryClassification());
		loanableBook.setUser(checkLoanableBook.getUser());
	}
	
	/**
	 * 发送审核结果消息
	 * @param isApproved 是否通过审核
	 * @param uuid 用户id
	 * @param clb 审核记录
	 */
	private void sendMsg(boolean isApproved, String uuid, CheckLoanableBook clb) {
		News msg = new News();
		String template;// 消息模板
		String content;// 消息内容
		if (isApproved) { // 审核通过
			msg.setnSubject("审核通过");
			Mapping msgShareSuccess = mappingMapper.selectByPrimaryKey("msg_share_success");// 获取消息模板配置
			template = msgShareSuccess.getmValue();//获取消息模板
			content = template.replace("%bookname", clb.getClbName()); // 设置占位符（书名）
			msg.setnContent(content);
		} else { // 审核不通过
			msg.setnSubject("审核失败");
			Mapping msgShareFailure = mappingMapper.selectByPrimaryKey("msg_share_failure");// 获取消息模板配置
			template = msgShareFailure.getmValue();//获取消息模板
			content = template.replace("%bookname", clb.getClbName()); // 设置占位符（书名）
			content = content.replace("%failureMsg", clb.getFailureCause());// 设置占位符（失败原因）
			msg.setnContent(content);
		}
		msg.setNewsTime(new Date());
		msg.setUser(clb.getUser());
		newsMapper.insertSelective(msg); // 添加审核反馈消息
	}

}
