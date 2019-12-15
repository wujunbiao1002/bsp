package com.bsp.controller;


import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.apache.shiro.authz.annotation.RequiresUser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.bsp.dto.QueryObject;
import com.bsp.entity.Administrator;
import com.bsp.exceptions.DataUpdateException;
import com.bsp.exceptions.SystemErrorException;
import com.bsp.service.IAdminService;
import com.bsp.shiro.ShiroUtils;
import com.bsp.utils.CommonUtil;
import com.bsp.utils.Page;
import com.bsp.utils.Result;

@RestController
@Scope(value="prototype")
@RequestMapping("admin")
public class AdminController extends BaseController {
	private static final Logger logger = LoggerFactory.getLogger(AdminController.class);
	
	@Autowired
	private IAdminService adminService;
	
	public void setAdminService(IAdminService adminService) {
		this.adminService = adminService;
	}
	
	/**
	 * 禁用|删除一个管理员，只有超级管理员能调用，不能禁用当前用户
	 * @param uuid 管理员id
	 */
	@RequestMapping("lockOrUnlock")
	@RequiresRoles("super_admin")
	@RequiresUser
	public Result lockOrUnlock(String uuid) {
		// 不能操作当前用户，不能操作永久用户（状态为0）
		if (ShiroUtils.getToken().getaUuid().equals(uuid)) {
			return Result.error("不允许操作当前登录用户");
		}
		try {
			adminService.lockOrUnlock(uuid);
		} catch (SystemErrorException e) {
			e.printStackTrace();
			return Result.error(e.getMessage());
		} catch (DataUpdateException e) {
			e.printStackTrace();
			return Result.error(e.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
			return Result.error("由于未知原因，操作失败");
		}
		return Result.success();
	}
	
	/**
	 * 获取用户列表
	 * @param pageParams
	 * @return
	 */
	@RequestMapping("page")
	@RequiresUser
	public Page page(QueryObject queryObject) {
		if (!StringUtils.isBlank(queryObject.getSort())) {//排序字段名需要改成数据库使用的snake风格字符串
			queryObject.setSort(CommonUtil.HumpToUnderline(queryObject.getSort()));
		}
		Page page = adminService.findByQueryObject(queryObject);
		return page;
	}
	
	/**
	 * 更新资料，只能修改当前用户资料的部分字段，实现时请完成参数列表
	 */
	@RequestMapping("update")
	@RequiresUser
	public Result update(@RequestBody Administrator administrator) {
		try {
			adminService.update(administrator, administrator.getaPassword()!=null, ShiroUtils.getToken());
		} catch (DataUpdateException e) {
			e.printStackTrace();
			logger.error(e.getMessage());
			return Result.error(e.getMessage());
		} catch (SystemErrorException e) {
			e.printStackTrace();
			logger.error(e.getMessage());
			return Result.error(e.getMessage());
		}
		return Result.success();
	}
	
	/**
	 * 修改密码，限超级管理员使用
	 * @param newPassword 新密码
	 * @param confirmPassword 确认
	 */
	@RequestMapping("pwdChange")
	@RequiresRoles("super_admin")
	public Result changePasswordWithUuid(String uuid, String newPassword, String confirmPassword) {
		try {
			adminService.changePassword(uuid, null, newPassword, confirmPassword);
		} catch (DataUpdateException e) {
			e.printStackTrace();
			logger.error(e.getMessage());
			return Result.error(e.getMessage());
		} catch (SystemErrorException e) {
			e.printStackTrace();
			logger.error(e.getMessage());
			return Result.error(e.getMessage());
		}
		return Result.success();
	}
	
	/**
	 * 修改当前登录用户密码
	 */
	@RequestMapping(value="password", method=RequestMethod.POST)
	@RequiresUser
	public Result changePassword(String currentPassword, String newPassword, String confirmPassword) {
		Administrator admin = ShiroUtils.getToken();
		if (null == currentPassword) {
			return Result.error("原密码不允许为空");
		}
		try {
			adminService.changePassword(admin.getaUuid(), currentPassword, newPassword, confirmPassword);
		} catch (DataUpdateException e) {
			e.printStackTrace();
			logger.error(e.getMessage());
			return Result.error(e.getMessage());
		} catch (SystemErrorException e) {
			e.printStackTrace();
			logger.error(e.getMessage());
			return Result.error(e.getMessage());
		}
		logger.info(admin.getaId()+"修改密码成功");
		return Result.success();
	}
	
	/**
	 * 用户是否已经存在
	 */
	@RequestMapping("exist")
	public Result exist(String id) {
		Result result = Result.success();
		result.put("exist", adminService.isExist(id));
		return result;
	}
	
	/**
	 * 用于添加用户于检查用户是否合法（不存在）
	 */
	@RequestMapping("valid")
	public Result valid(String id) {
		Result result = Result.success();
		result.put("valid", !adminService.isExist(id));
		return result;
	}
	
	/**
	 * 添加管理员，只有超级管理员能调用，实现时请完成参数列表
	 */
	@RequestMapping("add")
	@RequiresRoles("super_admin")
	public Result add(@RequestBody Administrator obj) {
		try {
			adminService.add(obj);
		} catch (SystemErrorException e) {
			e.printStackTrace();
			return Result.error(e.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
			return Result.error("由于未知错误，添加管理员失败");
		}
		return Result.success();
	}
	
	@RequestMapping("findByKey")
	@RequiresUser
	public Result findByKey(String uuid) {
		Administrator admin = null;
		try {
			admin = adminService.findByKey(uuid);
			admin.setaPassword(null);
		} catch (SystemErrorException e) {
			e.printStackTrace();
			return Result.error(e.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
			return Result.error("由于未知错误，查询数据失败");
		}
		Result result = Result.success();
		result.put("obj", admin);
		return result;
	}
	
}
