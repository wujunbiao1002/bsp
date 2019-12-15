package com.bsp.service;

import org.springframework.stereotype.Service;

import com.bsp.dto.QueryObject;
import com.bsp.entity.Administrator;
import com.bsp.utils.Page;

@Service
public interface IAdminService {
	
	/**
	 * 根据用户名查找记录
	 * @param aid 用户名
	 */
	Administrator findByAID(String username);
	
	/**
	 * 根据用户名查找记录
	 * @param aid 用户名
	 */
	Administrator findByKey(String uuid);
	
	/**
	 * 修改密码
	 * @param uuid 用户uuid
	 * @param currentPassword 原密码，null则不校验原密码
	 * @param newPassword 新密码
	 * @param confirmPassword 确认密码
	 */
	void changePassword(String uuid, String currentPassword, String newPassword, String confirmPassword);
	
	/**
	 * 查找一页数据
	 * @param queryObject
	 * @return
	 */
	Page findByQueryObject(QueryObject queryObject);
	
	/**
	 * 冻结|解冻管理员
	 * @param uuid
	 */
	void lockOrUnlock(String uuid);
	
	/**
	 * 添加一个管理员
	 */
	void add(Administrator obj);
	
	/**
	 * 修改信息
	 * @param obj 实体
	 * @param withPassword 是否修改密码
	 * @param oprator 操作人
	 */
	void update(Administrator obj, boolean withPassword, Administrator operator);
	
	/**
	 * 检查用户是否已经存在
	 */
	boolean isExist(String aId);
}
