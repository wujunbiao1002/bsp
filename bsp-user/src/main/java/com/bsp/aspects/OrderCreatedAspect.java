package com.bsp.aspects;

import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.bsp.dao.LoanableBookMapper;
import com.bsp.dao.MappingMapper;
import com.bsp.entity.LendingRecord;
import com.bsp.entity.LoanableBook;
import com.bsp.entity.Mapping;
import com.bsp.service.IEmailService;
import com.bsp.service.IMessageService;

/**
 * 切面：创建订单时发送消息通知
 */
@Aspect
@Component
public class OrderCreatedAspect {
	
	@Autowired
	private LoanableBookMapper loanableBookMapper;
	@Autowired
	private IMessageService messageService;
	@Autowired
	private IEmailService emailService;
	@Autowired
	private MappingMapper mappingMapper;
	
	public void setMappingMapper(MappingMapper mappingMapper) {
		this.mappingMapper = mappingMapper;
	}

	public void setMessageService(IMessageService messageService) {
		this.messageService = messageService;
	}

	public void setEmailService(IEmailService emailService) {
		this.emailService = emailService;
	}

	public void setLoanableBookMapper(LoanableBookMapper loanableBookMapper) {
		this.loanableBookMapper = loanableBookMapper;
	}

	@Pointcut("execution(** com.bsp.service.IOrderService.addOrder(com.bsp.entity.LendingRecord))&&args(lendingRecord)")
	public void pointCut(LendingRecord lendingRecord) {};
	
	/**
	 * 发送成功通知
	 */
	@AfterReturning("pointCut(lendingRecord)")
	public void sendSuccessMessage(LendingRecord lendingRecord) {
		LoanableBook loanableBook = loanableBookMapper.selectByPrimaryKey(lendingRecord.getLoanableBook().getLbId());
		lendingRecord.setLoanableBook(loanableBook);
		Mapping overtime_agree_apply = mappingMapper.selectByPrimaryKey("overtime_agree_apply"); // 申请处理期限
		Mapping msg_create_order_success = mappingMapper.selectByPrimaryKey("msg_create_order_success"); // 借入方消息模板
		Mapping msg_reveive_borrow_apply = mappingMapper.selectByPrimaryKey("msg_reveive_borrow_apply"); // 借出方消息模板
		Mapping url_user = mappingMapper.selectByPrimaryKey("url_user"); // 网站地址
		// 借入方消息
		String subjectBorrow = "成功提交借阅申请";
		String contentBorrow = msg_create_order_success.getmValue().replaceAll("%lb_id", loanableBook.getLbId().toString())
				.replaceAll("%bookname", loanableBook.getLbName())
				.replaceAll("%overtime_agree_apply", overtime_agree_apply.getmValue())
				.replaceAll("%url_user", url_user.getmValue());
		messageService.sendMessage(lendingRecord.getUser().getMail(), subjectBorrow, contentBorrow);// 借入方消息
		emailService.sendEmail(lendingRecord.getUser().getMail(), subjectBorrow, contentBorrow); // 借入方邮件
		
		// 借出方消息
		String subjectLend = "收到借阅申请，请尽快处理";
		String contentLend = msg_reveive_borrow_apply.getmValue().replaceAll("%lb_id", loanableBook.getLbId().toString())
				.replaceAll("%bookname", loanableBook.getLbName())
				.replaceAll("%overtime_agree_apply", overtime_agree_apply.getmValue())
				.replaceAll("%url_user", url_user.getmValue());
		messageService.sendMessage(loanableBook.getUser().getMail(), subjectBorrow, subjectLend);// 借出方消息
		emailService.sendEmail(loanableBook.getUser().getMail(), subjectBorrow, contentLend); // 借入方邮件
	}
	
}
