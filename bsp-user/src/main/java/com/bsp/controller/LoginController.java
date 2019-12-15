package com.bsp.controller;

import javax.servlet.http.HttpServletRequest;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.annotation.RequiresUser;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.util.WebUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.bsp.entity.User;
import com.bsp.entity.UserInfor;
import com.bsp.enums.BussCode;
import com.bsp.exceptions.SystemErrorException;
import com.bsp.service.IUserService;
import com.bsp.shiro.ShiroUtils;
import com.bsp.utils.Cryptography;
import com.bsp.utils.Result;
import com.bsp.vo.UserVO;


@Controller
@Scope(value="prototype")
public class LoginController extends BaseController {
	private static final Logger logger = LoggerFactory.getLogger(LoginController.class);
	
	@Autowired
	private IUserService userService;
	
	public void setUserService(IUserService userService) {
		this.userService = userService;
	}

	/**
	 * 获取登录用户
	 */
	@ResponseBody
	@RequestMapping("/token")
	@RequiresUser
	public Result getToken() {
		User user = null;
		UserVO vo;
		UserInfor userInfor;
		user = ShiroUtils.getToken();
		try {
			userInfor = userService.getUserInforByUser(user);
			vo = new UserVO(user, userInfor);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(ShiroUtils.getSession().getId() + "获取登录信息失败:" + e.getMessage());
			return Result.error("系统错误，获取登录信息失败");
		}
		Result result = Result.success();
		result.put("token", vo);
		return result;
	}
	
	/**
	 * 登录
	 */
	@RequestMapping(value = "login", method = RequestMethod.POST)
	@ResponseBody
	public Result login(HttpServletRequest request, @RequestParam("username") String username,
			@RequestParam("password") String password, @RequestParam("vcode") String vcode) {
		if (!vcode.equalsIgnoreCase((String)request.getSession().getAttribute("session_vcode"))) {
			return Result.error(BussCode.NOT_LOGIN, "验证码错误");
		}
		Subject subject = ShiroUtils.getSubject();
		try {
			UsernamePasswordToken token = new UsernamePasswordToken(username,
					Cryptography.MD5Hash(password, username));
			subject.login(token);
		} catch (UnknownAccountException e) {
			return Result.error(e.getMessage());
		} catch (IncorrectCredentialsException e) {
			return Result.error(BussCode.NOT_LOGIN, "用户名或密码不正确");
		} catch (LockedAccountException e) {
			return Result.error(e.getMessage());
		} catch (AuthenticationException e) {
			return Result.error("账户验证失败");
		} catch (SystemErrorException e) {
			return Result.error(e.getMessage());
		} catch (Exception e) {
			return Result.error(BussCode.ERR_UNKNOWN, "系统错误");
		}
		Result result = Result.success();
		if (WebUtils.getSavedRequest(request)!=null) {// 登录前访问的url
			result.put("url", WebUtils.getSavedRequest(request).getRequestUrl());
		}
		logger.info(((User)ShiroUtils.getToken()).getUuid() + "登录系统");
		return result;
	}
	
	/**
	 * 退出
	 */
	@RequestMapping(value="logout",method =RequestMethod.GET)
	@ResponseBody
	@RequiresUser
	public Result logout(){
		try {
			ShiroUtils.logout();
		} catch (Exception e) {
			logger.error("登出失败:" + e.getMessage());
			return Result.error(BussCode.ERR_UNKNOWN, "系统异常，登出失败");
		}
		return Result.success();
	}
	
}
