package com.bsp.service;

import com.bsp.entity.User;
import com.bsp.entity.UserInfor;
import com.bsp.exceptions.UserDefinedException;
import com.bsp.vo.UserVO;

public interface IUserService {

	void addUser(User user, UserInfor userInfor);
	
	/**
	 * 根据用户名（邮箱）获取用户
	 * @param user
	 * @throws UserDefinedException
	 */
	User getUserByMail(String email);
	
	/**
	 * 根据用户获取用户信息
	 * @param user 用户
	 */
	UserInfor getUserInforByUser(User user);
	
	/**
	 * 修改密码
	 * @param user 用户
	 * @param currentPassword 原密码
	 * @param newPassword 新密码
	 * @param confirmPassword 确认密码
	 */
	void changePassword(User user, String currentPassword, String newPassword, String confirmPassword);
	
	/**
	 * 修改用户信息
	 * @param userVO
	 */
	void updateUserInfo(UserVO userVO);
}
