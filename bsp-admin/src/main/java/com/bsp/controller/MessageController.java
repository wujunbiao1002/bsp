package com.bsp.controller;

import org.apache.shiro.authz.annotation.RequiresUser;
import org.springframework.context.annotation.Scope;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.bsp.utils.Result;

/**
 * 消息
 * @author hayate
 *
 */
@RestController
@Scope(value="prototype")
@RequestMapping("msg")
public class MessageController {
	
	/**
	 * 发送消息
	 * @param subject 通知消息标题
	 * @param uuid 通知消息所属的用户id
	 * @param content 消息内容
	 */
	@RequestMapping("send")
	@RequiresUser
	public Result send(@RequestParam("subject") String subject,@RequestParam("uuid") String uuid, @RequestParam("content") String content) {
		return Result.success();
	}
	
}
