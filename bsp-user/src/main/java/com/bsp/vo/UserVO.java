package com.bsp.vo;

import java.lang.reflect.InvocationTargetException;

import org.apache.commons.beanutils.BeanUtils;

import com.bsp.entity.User;
import com.bsp.entity.UserInfor;

public class UserVO {
	// 用户唯一标识符号
	private String uuid;

	// 用户邮箱，作为用户登录账号
	private String mail;

	// false没有禁用，true被禁用，默认为0
	private boolean isDelete;

	// 用户信息
	private UserInfor userInfor = new UserInfor();

	/**
	 * @param user
	 *            用户
	 * @param userInfor
	 *            用户信息
	 * @throws InvocationTargetException
	 * @throws IllegalAccessException
	 */
	public UserVO(User user, UserInfor userInfor) throws IllegalAccessException, InvocationTargetException {
		if (user != null) {
			this.uuid = user.getUuid();
			this.mail = user.getMail();
			switch (user.getIsDelete()) {
			case 0:
				isDelete = false;
				break;
			default:
				isDelete = true;
				break;
			}
		}
		if (userInfor != null) {
			BeanUtils.copyProperties(this.userInfor, userInfor);
		}
	}

	public UserInfor getUserInfor() {
		return userInfor;
	}

	public void setUserInfor(UserInfor userInfor) {
		this.userInfor = userInfor;
	}

	/**
	 * 用户唯一标识符号
	 */
	public String getUuid() {
		return uuid;
	}

	/**
	 * @param uuid
	 *            用户唯一标识符号
	 */
	public void setUuid(String uuid) {
		this.uuid = uuid == null ? null : uuid.trim();
	}

	/**
	 * 用户邮箱，作为用户登录账号
	 */
	public String getMail() {
		return mail;
	}

	/**
	 * @param mail
	 *            用户邮箱，作为用户登录账号
	 */
	public void setMail(String mail) {
		this.mail = mail == null ? null : mail.trim();
	}

	/**
	 * false没有禁用，true被禁用，默认为0
	 */
	public boolean getIsDelete() {
		return isDelete;
	}

	/**
	 * @param isDelete
	 *            false没有禁用，true被禁用，默认为0
	 */
	public void setIsDelete(boolean isDelete) {
		this.isDelete = isDelete;
	}
}
