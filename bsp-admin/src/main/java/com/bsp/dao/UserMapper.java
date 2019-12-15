package com.bsp.dao;

import java.util.List;

import com.bsp.entity.User;

public interface UserMapper extends GenericMapper<User, String> {
	
	/**
	 * 根据邮箱获取用户
	 * @param mail 邮箱
	 * @return 用户列表
	 */
	List<User> selectByMail(String mail);
}