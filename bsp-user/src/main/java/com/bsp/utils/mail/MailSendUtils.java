package com.bsp.utils.mail;

import java.io.IOException;
import java.util.Properties;

import javax.mail.MessagingException;
import javax.mail.Session;

import com.bsp.exceptions.SendEmailException;

import cn.itcast.mail.Mail;
import cn.itcast.mail.MailUtils;

public class MailSendUtils {
	private String host; // 邮件服务器名
	private String password; // 邮箱登录密码或者授权码
	private String from; // 发件人邮箱账号
	private String username; // 发件人昵称
	private Session session; // 邮箱连接session
	/*
	 * 加载配置邮箱配置文件，生成邮箱连接session
	 */

	private void loadProperties() {
		Properties properties = new Properties();
		try {
			// 加载配置邮箱账号配置文件, 重点
			properties.load(this.getClass().getClassLoader()
					.getResourceAsStream("email_account.properties"));
		} catch (IOException e) {
			System.out.println("邮箱账号配置加载失败");
			e.printStackTrace();
		}
		// 获取配置文件的内容
		host = properties.getProperty("host"); // 邮件服务器名
		password = properties.getProperty("password"); // 邮箱登录密码或者授权码
		from = properties.getProperty("from"); // 发件人邮箱账号
		username = properties.getProperty("username"); // 发件人昵称
		// 获取邮箱连接session
		session = MailUtils.createSession(host, username, password);
	}

	public void sendMail(String to, String subject, String content) {
		// 加载配置文件内容,生成session
		loadProperties();
		// 创建邮件体
		Mail mail = new Mail(from, to, subject, content);
		// 发送邮件
		try {
			MailUtils.send(session, mail);
		} catch (MessagingException e) {
			e.printStackTrace();
			throw new SendEmailException("邮件发送失败");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
