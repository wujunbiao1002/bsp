package com.bsp.service.impl;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bsp.dao.UserInforMapper;
import com.bsp.dao.UserMapper;
import com.bsp.entity.User;
import com.bsp.entity.UserInfor;
import com.bsp.exceptions.DataUpdateException;
import com.bsp.exceptions.SystemErrorException;
import com.bsp.service.IUserService;
import com.bsp.utils.CommonUtil;
import com.bsp.utils.Cryptography;
import com.bsp.vo.UserVO;

@Service
@Transactional
public class UserService implements IUserService {
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
	@Transactional
	public void addUser(User user, UserInfor userInfor) {
		// md5加密
		String uuid = CommonUtil.createUUID();
		// 设置uuid
		user.setUuid(uuid);
		userInfor.setUuid(uuid);
		// 盐值加密，盐值为用户名，即邮箱
		user.setPassword(Cryptography.MD5Hash(user.getPassword(), user.getMail()));
		try {
			userMapper.insertSelective(user);
			userInforMapper.insertSelective(userInfor);
		} catch (Exception e) {
			e.printStackTrace();
			throw new SystemErrorException("操作失败，系统异常");
		}

	}

	@Override
	public User getUserByMail(String email) {
		List<User> users = null;
		try {
			users = userMapper.selectByMail(email);
		} catch (Exception e) {
			e.printStackTrace();
			throw new SystemErrorException("请求出错，系统异常");
		}
		return users.size() > 0 ? users.get(0) : null;
	}

	@Override
	public UserInfor getUserInforByUser(User user) {
		UserInfor userInfor;
		try {
			userInfor = userInforMapper.selectByPrimaryKey(user.getUuid());
		} catch (Exception e) {
			e.printStackTrace();
			throw new SystemErrorException("系统异常，获取用户信息失败");
		}
		return userInfor;
	}

	@Override
	public void changePassword(User user, String currentPassword, String newPassword, String confirmPassword) {
		if (StringUtils.isAnyBlank(newPassword, confirmPassword) || newPassword.length() < 8
				|| newPassword.length() > 20) {
			throw new DataUpdateException("修改密码失败，密码长度为8-20位");
		}
		if (!newPassword.equals(confirmPassword)) {
			throw new DataUpdateException("修改密码失败，两次输入密码不一致");
		}
		User newUser = null;
		try {
			newUser = userMapper.selectByPrimaryKey(user.getUuid());
		} catch (Exception e) {
			e.printStackTrace();
			throw new SystemErrorException("修改密码失败，系统异常");
		}
		// 原密码是否正确
		if (!Cryptography.checkMd5Hash(newUser.getPassword(), currentPassword, newUser.getMail())) {
			throw new DataUpdateException("修改密码失败，原密码错误");
		}
		try {
			// 修改密码，新密码盐值加密
			newUser.setPassword(Cryptography.MD5Hash(newPassword, newUser.getMail()));
			userMapper.updateByPrimaryKeySelective(newUser);
		} catch (Exception e) {
			e.printStackTrace();
			throw new SystemErrorException("修改密码失败，系统异常");
		}
	}

	@Override
	public void updateUserInfo(UserVO userVO) {
		
		UserInfor userInfor = userVO.getUserInfor();
		try {
			userInforMapper.updateByPrimaryKeySelective(userInfor);
		} catch (Exception e) {
			e.printStackTrace();
			throw new SystemErrorException("更新失败，系统异常");
		}
		
	}

}
