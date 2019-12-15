package com.bsp.controller;

import org.apache.shiro.authz.annotation.RequiresUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.bsp.dto.QueryObject;
import com.bsp.entity.User;
import com.bsp.exceptions.SystemErrorException;
import com.bsp.service.IMessageService;
import com.bsp.shiro.ShiroUtils;
import com.bsp.utils.Page;
import com.bsp.utils.Result;

/**
 * 消息相关
 * @author hayate
 *
 */
@RestController
@Scope(value="prototype")
@RequestMapping("msg")
public class MessageController extends BaseController {
	
	@Autowired
	private IMessageService messageService;
	
	public void setMessageService(IMessageService messageService) {
		this.messageService = messageService;
	}

	/**
	 * 获取未读消息数
	 * @return
	 */
	@RequestMapping("amount")
	@RequiresUser
	public Result getAmount() {
		User user = ShiroUtils.getToken();
		Integer amount = messageService.getNewMsgAmount(user.getUuid());
		Result rs = Result.success();
		rs.put("msgNum", amount);
		return rs;
	}
	
	/**
	 * 获取未读消息列表
	 */
	@RequestMapping("unread_list")
	@RequiresUser
	public Result getNewMsgList(QueryObject queryObject) {
		User user = ShiroUtils.getToken();
		queryObject.setSearch(user.getUuid());// 设置用户uuid
		Result rs = new Result();
		Page page = null;
		try {
			page = messageService.getNewMsgList(queryObject);
			rs.put("page", page);
		} catch (Exception e) {
			e.printStackTrace();
			return Result.error(e.getMessage());
		}
		return rs;
	}
	
	/**
	 * 获取已读消息列表
	 */
	@RequestMapping("read_list")
	@RequiresUser
	public Result getHistoryMsgList(QueryObject queryObject) {
		User user = ShiroUtils.getToken();
		queryObject.setSearch(user.getUuid());// 设置用户uuid
		Result rs = new Result();
		Page page = null;
		try {
			page = messageService.getHistoryMsgList(queryObject);
			rs.put("page", page);
		} catch (Exception e) {
			e.printStackTrace();
			return Result.error(e.getMessage());
		}
		return rs;
	}
	
	/**
	 * 获取所有消息列表
	 */
	@RequestMapping("all")
	@RequiresUser
	public Result getAllyMsgList(QueryObject queryObject) {
		return Result.success();
	}
	
	/**
	 * 打开一条已读消息
	 * @param nId 消息id
	 */
	@RequestMapping("read")
	@RequiresUser
	public Result readMsg(@RequestParam("nId") Integer nId) {
		User user = ShiroUtils.getToken();
		try {
			messageService.readMsg(user.getUuid(), nId);
		} catch (SystemErrorException e) {
			e.printStackTrace();
			return Result.error(e.getMessage());
		}
		return Result.success();
	}
	
	/**
	 * 删除一条未读消息，暂时不开放
	 * @param nId 消息id
	 */
	@RequestMapping("deleteUnread")
	@RequiresUser
	public Result deleteUnread(@RequestParam("nId") Integer nId) {
		
		return Result.success();
	}
	
	/**
	 * 删除一条已读消息
	 * @param nId 消息id
	 */
	@RequestMapping("deleteRead")
	@RequiresUser
	public Result deleteRead(@RequestParam("nId") Integer nId) {
		try {
			messageService.deleteReadMsg(ShiroUtils.getToken().getUuid(), nId);
		} catch (Exception e) {
			e.printStackTrace();
			return Result.error(e.getMessage());
		}
		return Result.success();
	}
	
}
