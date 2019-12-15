package com.bsp.dao;

import com.bsp.entity.Administrator;

public interface AdministratorMapper extends GenericMapper<Administrator, String> {
	
	/**
	 * 根据用户名查找记录
	 * @param aid 用户名
	 */
	Administrator selectByAID(String aid);
	
}