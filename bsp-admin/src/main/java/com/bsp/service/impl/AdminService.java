package com.bsp.service.impl;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bsp.dao.AdministratorMapper;
import com.bsp.dto.QueryObject;
import com.bsp.entity.Administrator;
import com.bsp.exceptions.DataUpdateException;
import com.bsp.exceptions.SystemErrorException;
import com.bsp.service.IAdminService;
import com.bsp.utils.CommonUtil;
import com.bsp.utils.Cryptography;
import com.bsp.utils.Page;

@Service("adminService")
public class AdminService implements IAdminService {
	
	@Autowired
	private AdministratorMapper administratorMapper;
	
	public void setAdministratorMapper(AdministratorMapper administratorMapper) {
		this.administratorMapper = administratorMapper;
	}

	@Override
	public Administrator findByAID(String username) {
		Administrator admin = null;
		try {
			admin = administratorMapper.selectByAID(username);
		} catch (Exception e) {
			throw new SystemErrorException("系统异常，查询失败");
		}
		return admin;
	}

	@Override
	public void changePassword(String uuid, String currentPassword, String newPassword,
			String confirmPassword) {
		if (!!StringUtils.equals(newPassword, confirmPassword)) {
			throw new DataUpdateException("更改失败，密两次输入密码不一致");
		}
		Administrator newAdmin = null;
		try {
			newAdmin = administratorMapper.selectByPrimaryKey(uuid);
		} catch (Exception e) {
			throw new SystemErrorException("密码更改失败，系统异常，请联系管理员");
		}
		// 原密码为不空，检测是否正确
		if (null!=currentPassword) {			
			boolean correct = Cryptography.checkMd5Hash(newAdmin.getaPassword(), currentPassword, newAdmin.getaId());
			if (!correct) {
				throw new DataUpdateException("密码更改失败，原密码错误");
			}
		}
		newPassword = Cryptography.MD5Hash(newPassword, newAdmin.getaId());
		// 只更新密码
		newAdmin.setaPassword(newPassword);
		try {
			administratorMapper.updateByPrimaryKeySelective(newAdmin);
		} catch (Exception e) {
			throw new SystemErrorException("系统异常，密码更改失败，请联系管理员");
		}
	}

	@Override
	public Page findByQueryObject(QueryObject queryObject) {
		Integer totalCount = null;
		List<Administrator> list = null;
		try {
			totalCount = administratorMapper.getTotalCount(queryObject);
			list = administratorMapper.selectByQueryObject(queryObject);
		} catch (Exception e) {
			e.printStackTrace();
			throw new SystemErrorException("系统异常，查询数据失败");
		}
		return new Page(list, totalCount, queryObject.getLimit(), queryObject.getPageNumber());
	}

	@Override
	public void lockOrUnlock(String uuid) {
		Administrator admin = null;
		try {
			admin = administratorMapper.selectByPrimaryKey(uuid);
		} catch (Exception e) {
			e.printStackTrace();
			throw new SystemErrorException("操作失败，系统异常");
		}
		if (admin.getaLevel()==0) {
			throw new DataUpdateException("不允许操作永久用户");
		}
		try {
			admin.lockOrUnlock();
			administratorMapper.updateByPrimaryKeySelective(admin);
		} catch (Exception e) {
			e.printStackTrace();
			throw new SystemErrorException("操作失败，系统异常");
		}
	}

	@Override
	public void add(Administrator obj) {
		try {
			if(obj.getaLevel()==0) {//防止非法注册永久管理员
				obj.setaLevel(2);//设置为普通管理员
			}
			obj.setaUuid(CommonUtil.createUUID());
			String md5 = Cryptography.MD5Hash(obj.getaPassword(), obj.getaId());
			obj.setaPassword(md5);
			obj.setIsDelete((byte)0);
			administratorMapper.insertSelective(obj);
		} catch (Exception e) {
			e.printStackTrace();
			throw new SystemErrorException("系统异常，添加失败");
		}
	}

	@Override
	public boolean isExist(String aId) {
		Administrator admin = administratorMapper.selectByAID(aId);
		if (null != admin) {
			return true;
		}
		return false;
	}

	@Override
	public void update(Administrator target, boolean withPassword, Administrator operator) {
		Administrator newObj = null;
		try {
			newObj = administratorMapper.selectByPrimaryKey(target.getaUuid());
		} catch (Exception e) {
			e.printStackTrace();
			throw new SystemErrorException("更新失败，查询记录时出现异常");
		}
		if (null == newObj) {
			throw new DataUpdateException("更新失败，找不到记录");
		}
		if (!this.isUpdateArrowed(operator, target)) {
			throw new DataUpdateException("更新失败，无权限");
		}
		newObj.setaAddress(target.getaAddress());
		newObj.setaComments(target.getaComments());
		newObj.setaLevel(target.getaLevel());
		newObj.setaName(target.getaName());
		newObj.setaPhone(target.getaPhone());
		if (withPassword) { //修改密码
			String md5 = Cryptography.MD5Hash(target.getaPassword(), target.getaId());
			newObj.setaPassword(md5);
		}
		try {
			administratorMapper.updateByPrimaryKeySelective(newObj);
		} catch (Exception e) {
			e.printStackTrace();
			throw new SystemErrorException("更新失败，系统异常");
		}
	}

	@Override
	public Administrator findByKey(String uuid) {
		Administrator admin = null;
		try {
			admin = administratorMapper.selectByPrimaryKey(uuid);
		} catch (Exception e) {
			throw new SystemErrorException("系统异常，查询失败");
		}
		return admin;
	}
	
	/**
	 * 判断是否有权限操作
	 * @param operator 执行操作的用户对象
	 * @param target 被操作的用户对象
	 */
	private boolean isUpdateArrowed(Administrator operator, Administrator target) {
		// 操作用户不是所修改用户，且等级不高于被修改用户对象，禁止操作（权限值越小等级越高）
		if (target.getaLevel() < operator.getaLevel() && !target.getaUuid().equals(operator.getaUuid()) ) { 
			return false;
		}
		// 不能提升本用户权限（权限值越小等级越高）
		if (target.getaUuid().equals(operator.getaUuid()) && target.getaLevel() < operator.getaLevel()) { 
			return false;
		}
		return true;
	}

}
