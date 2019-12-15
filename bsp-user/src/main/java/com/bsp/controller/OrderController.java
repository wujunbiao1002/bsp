package com.bsp.controller;


import org.apache.shiro.authz.annotation.RequiresUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.bsp.entity.LendingRecord;
import com.bsp.entity.LoanableBook;
import com.bsp.entity.User;
import com.bsp.entity.UserInfor;
import com.bsp.exceptions.DataUpdateException;
import com.bsp.exceptions.SystemErrorException;
import com.bsp.service.ILoanableBookService;
import com.bsp.service.IOrderService;
import com.bsp.service.IUserService;
import com.bsp.shiro.ShiroUtils;
import com.bsp.utils.Result;

/**
 * 订单流转
 * @author xcl
 *
 */
@RestController
@Scope(value="prototype")
@RequestMapping("/order")
public class OrderController extends BaseController {
	
	@Autowired
	private IUserService userService;
	public void setUserService(IUserService userService) {
		this.userService = userService;
	}
	
	@Autowired
	private ILoanableBookService loanableBookService;
	public void setLoanableBookService(ILoanableBookService loanableBookService) {
		this.loanableBookService = loanableBookService;
	}
	
	@Autowired
	private IOrderService orderService;
	public void setOrderService(IOrderService orderService) {
		this.orderService = orderService;
	}
	
	/**
	 * 获取一本书、登录用户的信息
	 * @param idInteger
	 * @return
	 */
	@RequestMapping("/detail")
	@RequiresUser
	public Result detail(@RequestParam("lbId")Integer idInteger) {
		User user = null;
		UserInfor userInfor;
		user = ShiroUtils.getToken();
		LoanableBook book = null;
		try {
			userInfor = userService.getUserInforByUser(user);
			book = loanableBookService.getLoanableBookInforByid(idInteger);
		}catch (SystemErrorException e) {
			e.printStackTrace();
			return Result.error(e.getMessage());
		}catch (Exception e) {
			e.printStackTrace();
			return Result.error("由于未知错误，请求失败");
		}
		Result result = new Result();
		result.put("detail", book);
		result.put("user", userInfor);
		return result;
	}
	
	@RequestMapping("/submit")
	@RequiresUser
	public Result submit(@RequestBody LendingRecord lendingRecord) {
/*		System.out.println(lendingRecord.getAmount());
		System.out.println(lendingRecord.getLoanPhone());
		System.out.println(lendingRecord.getLoanableBook().getLbId());*/
		User user = null;
		user = ShiroUtils.getToken();
		lendingRecord.setUser(user);
		try {
			orderService.addOrder(lendingRecord);
		} catch (SystemErrorException e) {
			e.printStackTrace();
			return Result.error(e.getMessage());
		} catch (DataUpdateException e) {
			e.printStackTrace();
			return Result.error(e.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
			return Result.error("由于未知错误，请求失败");
		}
		Result result = new Result();
		return result;
	}

}
