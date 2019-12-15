package com.bsp.controller;


import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authz.annotation.RequiresUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.bsp.dto.QueryObject;
import com.bsp.entity.UserInfor;
import com.bsp.exceptions.SystemErrorException;
import com.bsp.service.IUserService;
import com.bsp.utils.CommonUtil;
import com.bsp.utils.Page;
import com.bsp.utils.Result;

@RestController
@Scope(value="prototype")
@RequestMapping("/user")
public class UserController extends BaseController {

	@Autowired
	private IUserService userService;
	
	public void setUserService(IUserService userService) {
		this.userService = userService;
	}
	
	@RequestMapping("lockOrUnlock")
	@RequiresUser
	public Result lockOrUnlock(String uuid) {
		try {
			userService.lockOrUnlock(uuid);
		} catch (SystemErrorException e) {
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
		Page page = userService.findByQueryObject(queryObject);
		return page;
	}
	
	/**
	 * 获取一条用户记录
	 * @param uuid 用户主键
	 */
	@RequestMapping("detial")
	@RequiresUser
	public Result getUserDetial(@RequestParam("uuid") String uuid) {
		UserInfor userInfor = null;
		try {
			userInfor = userService.findUserInforByUuid(uuid);
		} catch (SystemErrorException e) {
			e.printStackTrace();
			return Result.error(e.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
			return Result.error("由于未知原因，查询用户数据失败");
		}
		Result result = Result.success();
		result.put("obj", userInfor);
		return result;
	}
}