package com.bsp.entity;
/**
 * 邮件实体类
 * @time 2018年5月16日15:55:32
 * @author wjb
 *
 */
public class MailEntity {
	private String toMail;	// 邮件接收地址
	private String subject;	// 邮件主题
	private String content;	// 邮件内容
	
	public MailEntity (){
	}
	public MailEntity(String toMail, String subject, String content) {
		super();
		this.toMail = toMail;
		this.subject = subject;
		this.content = content;
	}
	public String getToMail() {
		return toMail;
	}
	public void setToMail(String toMail) {
		this.toMail = toMail;
	}
	public String getSubject() {
		return subject;
	}
	public void setSubject(String subject) {
		this.subject = subject;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
}	
