package com.bsp.controller;

import com.bsp.dto.OrderQueryObject;
import com.bsp.entity.Administrator;
import com.bsp.entity.LendingRecord;
import com.bsp.exceptions.SendEmailException;
import com.bsp.exceptions.SystemErrorException;
import com.bsp.exceptions.UserDefinedException;
import com.bsp.service.*;
import com.bsp.shiro.ShiroUtils;
import com.bsp.utils.CommonUtil;
import com.bsp.utils.Page;
import com.bsp.utils.Result;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authz.annotation.RequiresUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * 订单流转
 * @author hayate
 *
 */
@RestController
@Scope(value="prototype")
@RequestMapping("proccess")
public class OrderProccessController extends BaseController {
	
	@Autowired
	private ILendingRecordService lendingRecordService;
	
	@Autowired
	private IDonateService donateService;
	
	@Autowired
	private IMessageService messageService;
	@Autowired
	private IEmailService emailService;

	@Autowired
	private IOrderProccessService iOrderProccessService;

	public void setMessageService(IMessageService messageService) {
		this.messageService = messageService;
	}

	public void setEmailService(IEmailService emailService) {
		this.emailService = emailService;
	}

	public void setDonateService(IDonateService donateService) {
		this.donateService = donateService;
	}

	public void setLendingRecordService(ILendingRecordService lendingRecordService) {
		this.lendingRecordService = lendingRecordService;
	}

	/**
	 * 进行下一步，即正常订单状态转换
	 * @param lrId 订单记录ID，查表lending_record
	 */
	@RequestMapping("next_step")
	@RequiresUser
	public Result nextStep(@RequestParam("lrId") Integer lrId) {
		// 获取订单并进行下一步的状态转换
		System.out.println(lrId);
		Administrator admin = ShiroUtils.getToken();
		try {
			iOrderProccessService.nextStep(lrId, admin);
		} catch (SystemErrorException e) {
			e.printStackTrace();
			return Result.error(e.getMessage());
		}
		return Result.success();
	}
	
	/**
	 * 获取数据页
	 */
	@RequestMapping("page")
	@RequiresUser
	public Page list(OrderQueryObject queryObject) {
		if (!StringUtils.isBlank(queryObject.getSort())) {
			queryObject.setSort(CommonUtil.HumpToUnderline(queryObject.getSort()));
		}
		Page page = lendingRecordService.findByQueryObject(queryObject);
		return page;
	}
	
	/**
	 * 来自订单的捐赠，图书在中转站的状态有效
	 * @param lrId 订单Id
	 */
	@RequestMapping("donate")
	@RequiresUser
	public Result donate(Integer lrId) {
		try {
			donateService.donateFormLendingRecord(lrId);
		} catch (SystemErrorException e) {
			e.printStackTrace();
			return Result.error(e.getMessage());
		}
		return Result.success();
	}
	
	/**
	 * 发送消息
	 * @param sendTo 接收方，发送消息对象，0-借入方 1-借出方
	 * @param subject 主题
	 * @param content 正文
	 */
	@RequestMapping("sendMsg")
	@RequiresUser
	public Result sendMsg(Integer lrId, Integer sendTo, String subject, String content) {
		LendingRecord lr = null;
		String email = null;
		try {
			lr = lendingRecordService.findByPrimaryKey(lrId);
		} catch (Exception e) {
			e.printStackTrace();
			return Result.error(e.getMessage());
		}
		if (lr==null) {
			return Result.error("订单不存在");
		}
		if (sendTo==0) {
			email = lr.getUser().getMail();
		} else {
			email=lr.getLoanableBook().getUser().getMail();
		}
		try {
			emailService.sendEmail(email, subject, content);
			messageService.sendMessage(email, subject, content);
		} catch (SendEmailException e) {
			e.printStackTrace();
			return Result.error(e.getMessage());
		} catch (UserDefinedException e) {
			e.printStackTrace();
			return Result.error(e.getMessage());
		} catch (SystemErrorException e) {
			e.printStackTrace();
			return Result.error(e.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
			return Result.error("发生未知错误");
		}
		return Result.success();
	}
}
