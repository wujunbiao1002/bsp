package com.bsp.service;

public interface IEmailService {
	
	/**
	 * 发送邮件
	 * @param email 用户邮箱（登录名）
	 * @param subject 主题
	 * @param content 正文
	 */
	void sendEmail(String email, String subject, String content);
}
