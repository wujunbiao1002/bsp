package com.bsp.service;

public interface IRegisterService {
	
	/**
	 * 验证邮箱是否可用，即是否已被使用，
	 * @param mail
	 */
	public boolean isAvailableEmail(String mail);
	
	/**
	 * 发送邮箱验证码到目标邮箱
	 * @param dest 目标邮箱
	 * @return 验证码
	 */
	String sendEmailCode(String dest);
	
}
