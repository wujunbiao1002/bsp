package com.bsp.service;

public interface IMessageService {
	
	/**
	 * 发送消息
	 * @param mail 用户邮箱（登录名）
	 * @param subject 主题
	 * @param content 正文
	 */
	void sendMessage(String mail, String subject, String content);
	
}
