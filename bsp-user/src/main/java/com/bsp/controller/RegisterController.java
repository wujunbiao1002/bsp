package com.bsp.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.bsp.entity.User;
import com.bsp.entity.UserInfor;
import com.bsp.exceptions.SendEmailException;
import com.bsp.exceptions.SystemErrorException;
import com.bsp.service.IRegisterService;
import com.bsp.service.IUserService;
import com.bsp.utils.Result;

@RestController
@Scope(value="prototype")
@RequestMapping("sign_up")
public class RegisterController extends BaseController {
	@Autowired
	private IUserService userService;
	
	@Autowired
	private IRegisterService registerService;
	
	public void setRegisterService(IRegisterService registerService) {
		this.registerService = registerService;
	}

	public void setUserService(IUserService userService) {
		this.userService = userService;
	}
	
	/**
	 * 进行邮箱校验
	 */
	@RequestMapping("checkEmail")
	@ResponseBody
	public Result checkMail(HttpServletRequest request, @RequestParam("email") String email) {
		Result result = new Result();
		// 校验邮箱
		if (!email.matches(EMAIL_REGEX)) {
			result.put("available", false);
			result.put("msg", "邮箱格式错误");
			return result;
		}
		try {
			if (!registerService.isAvailableEmail(email)) {
				result.put("available", false);
				result.put("msg", "该邮箱已被注册，可尝试找回密码。");
				return result;
			}
		} catch (SystemErrorException e) {
			e.printStackTrace();
			return Result.error("请求失败，系统异常");
		}
		result.put("available", true);
		return result;
	}

	/**
	 * 发送邮箱验证码，并进行校验邮箱
	 */
	@RequestMapping("sendEmailCode")
	@ResponseBody
	public Result sendMailCode(HttpServletRequest request, @RequestParam("email") String email, @RequestParam("vcode") String vcode) {
		if (!registerService.isAvailableEmail(email)) {
			return Result.error("该邮箱已被注册，可尝试找回密码。");
		}
		String session_vcode = (String) request.getSession().getAttribute("session_vcode");
		// 校验验证码
		if (!vcode.toLowerCase().equalsIgnoreCase(session_vcode)) {
			return Result.error("验证码错误");
		}
		// 校验邮箱
		if (!email.matches(EMAIL_REGEX)) {
			return Result.error("邮箱格式错误");
		}
		// 生成邮箱验证码
		String mailVcode;
		try {
			mailVcode = registerService.sendEmailCode(email);
		} catch (SendEmailException e) {
			e.printStackTrace();
			return Result.error(e.getMessage());
		}
		// 保存邮箱验证码到session中，用于验证邮箱验证码
		request.getSession().setAttribute("session_mailVcode", mailVcode);
		request.getSession().setAttribute("temp_email", email);
		return Result.success();
	}

	/**
	 * 校验邮箱验证码
	 */
	@RequestMapping("checkMailCode")
	@ResponseBody
	public Result checkMailVcode(HttpServletRequest request, @RequestParam("email") String email, @RequestParam("mailVcode") String mailVcode) {
		if (!registerService.isAvailableEmail(email)) {
			return Result.error("该邮箱已被注册，可尝试找回密码。");
		}
		if (!email.equalsIgnoreCase(request.getSession().getAttribute("temp_email").toString())) {
			return Result.error("邮箱账号与发送验证码邮箱不匹配");
		}
		if (!mailVcode.equalsIgnoreCase(request.getSession().getAttribute("session_mailVcode").toString())) {
			return Result.error("邮箱验证码错误，请重新输入");
		}
		// 保存邮箱验证码到session中，用于注册
		request.getSession().setAttribute("email", email);
		return Result.success();
	}

	/**
	 * 用户填写个人信息并注册
	 */
	@RequestMapping("register")
	@ResponseBody
	public Result register(HttpServletRequest request,
			@RequestParam("password1") String password1, 
			@RequestParam("password2") String password2, 
			UserInfor userInfor) {
		if (!registerService.isAvailableEmail((String) request.getSession().getAttribute("email"))) {
			return Result.error("该邮箱已被注册，可尝试找回密码。");
		}
		// 表单验证
		if (userInfor.getuNickname().length() > 20) { // 昵称校验
			return Result.error("昵称长度过长不能超过20位");
		} else if (!password1.equals(password2)) { // 密码校验
			return Result.error("两次输入密码不一致");
		} else if (password1.length() < 6 || password1.length() > 20) {
			return Result.error("密码长度为6-20位");
		} else if (!userInfor.getuPhone().matches(PHONE_REGEX)) { // 手机号码校验
			return Result.error("请正确填写手机号码");
		}
		User user = new User();
		user.setMail((String) request.getSession().getAttribute("email"));
		user.setPassword(password1);
		this.userService.addUser(user, userInfor);
		return Result.success();
	}
}
