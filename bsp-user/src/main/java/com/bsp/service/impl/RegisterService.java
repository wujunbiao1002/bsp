package com.bsp.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bsp.dao.UserInforMapper;
import com.bsp.dao.UserMapper;
import com.bsp.entity.User;
import com.bsp.exceptions.SendEmailException;
import com.bsp.exceptions.SystemErrorException;
import com.bsp.service.IRegisterService;
import com.bsp.utils.mail.MailSendUtils;

@Service
public class RegisterService implements IRegisterService {

	@Autowired
	private UserMapper userMapper;

	@Autowired
	UserInforMapper userInforMapper;

	public void setUserInforMapper(UserInforMapper userInforMapper) {
		this.userInforMapper = userInforMapper;
	}

	public void setUserMapper(UserMapper userMapper) {
		this.userMapper = userMapper;
	}

	@Override
	public boolean isAvailableEmail(String mail) {
		List<User> users = null;
		try {
			users = userMapper.selectByMail(mail);
		} catch (Exception e) {
			e.printStackTrace();
			throw new SystemErrorException("请求出错，系统异常");
		}
		if (users.size() == 0) {
			return true;
		} else {
			return false;
		}
	}

	@Override
	public String sendEmailCode(String dest) {
		// 生成邮箱验证码
		String mailVcode = String.valueOf((int) ((Math.random() * 9 + 1) * 100000));
		// 生成邮件主题内容
		String subject = "xxx图书共享平台验证码"; // 邮件主题
		StringBuilder content = new StringBuilder("尊敬的用户,您好：<br/><br/>您正在进行注册操作，本次请求的邮件验证码是：<b>").append(mailVcode)
				.append("</b>（为了保证您账号的安全性，请您在30分钟内完成验证）。 本验证码30分钟内有效，请及时输入。<br/><br/>")
				.append("如非本人操作，请忽略该邮件。<br/>祝在本平台收获愉快！<br/><br/>").append("( ゜- ゜)つロ乾杯~<br/>（这是一封自动发送的邮件，请不要直接回复）"); // 邮件主体内容
		MailSendUtils mailSendUtils = new MailSendUtils();
		try {
			mailSendUtils.sendMail(dest, subject, content.toString());// 发送邮件
		} catch (Exception e) {
			e.printStackTrace();
			throw new SendEmailException("发送验证码失败，请确保邮箱有效并重试");
		}
		return mailVcode;
	}

}
