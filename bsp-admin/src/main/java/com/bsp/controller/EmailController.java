package com.bsp.controller;

import org.apache.shiro.authz.annotation.RequiresUser;
import org.springframework.context.annotation.Scope;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.bsp.utils.Result;
import com.bsp.utils.mail.MailSendUtils;

@RestController
@Scope(value="prototype")
@RequestMapping("email")
public class EmailController {
	
	/**
	 * 发送邮件
	 * @param dest 目标邮箱
	 * @param subject 主题
	 * @param content 内容
	 */
	@RequestMapping("send")
	@RequiresUser
	public Result send(@RequestParam("dest") String dest, @RequestParam("subject") String subject,@RequestParam("content")String content) {
		try {
			new MailSendUtils().sendMail(dest, subject, content);// 发送邮件
		} catch (Exception e) {
			e.printStackTrace();
			return Result.error(e.getMessage());
		}
		return Result.success();
	}
	
}
