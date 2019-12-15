package com.bsp.service.impl;


import org.springframework.stereotype.Service;

import com.bsp.exceptions.SendEmailException;
import com.bsp.service.IEmailService;
import com.bsp.utils.mail.MailSendUtils;

@Service("emailService")
public class EmailService implements IEmailService {

	@Override
	public void sendEmail(String email, String subject, String content) {
		MailSendUtils mailSendUtils = new MailSendUtils();
		try {
			mailSendUtils.sendMail(email, subject, content.toString());// 发送邮件
		} catch (Exception e) {
			e.printStackTrace();
			throw new SendEmailException("发送验证码失败，请确保邮箱有效并重试");
		}
	}
}
