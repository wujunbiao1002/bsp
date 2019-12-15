package com.bsp.service;

import com.bsp.dto.QueryObject;
import com.bsp.entity.User;
import com.bsp.entity.UserInfor;
import com.bsp.utils.Page;

public interface IUserService {
	
	Page findByQueryObject(QueryObject queryObject);
	
	void addUser(User user, UserInfor userInfor);
	
	/**
	 * 根据用户名（邮箱）获取用户
	 * @param user
	 * @throws UserDefinedException
	 */
	User getUserByMail(User user);
	
	/**
	 * 根据用户获取用户信息
	 * @param uuid 用户id
	 */
	UserInfor findUserInforByUuid(String uuid);
	
	/**
	 * 冻结或（软）删除用户
	 */
	void lockOrUnlock(String uuid);

}
