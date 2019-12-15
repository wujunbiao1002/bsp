package com.bsp.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authz.AuthorizationException;
import org.apache.shiro.authz.UnauthenticatedException;
import org.apache.shiro.authz.UnauthorizedException;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.bsp.enums.BussCode;
import com.bsp.shiro.filters.ShiroFilterUtils;
import com.bsp.utils.JsonUtils;

/**
 * 用于抽取共同的属性、、字段和方法
 * 
 * @author Hayate
 *
 */
public abstract class BaseController {

	protected static final String LOGIN_URL = "/p/login";// 登录页面
	protected final static String UNAUTHORIZED_URL = "/p/unauthorized";// 没有权限

	protected static final String EMAIL_REGEX = "^[a-zA-Z0-9_-]+@[a-zA-Z0-9_-]+(\\.[a-zA-Z0-9_-]+)+$"; // 邮箱正则表达式
	protected static final String PHONE_REGEX = "^((17[0-9])|(14[0-9])|(13[0-9])|(15[^4,\\D])|(18[0,5-9]))\\d{8}$"; // 手机号正则表达式

	/**
	 * 登录认证异常
	 */
	@ExceptionHandler({ UnauthenticatedException.class, AuthenticationException.class })
	public String authenticationException(HttpServletRequest request, HttpServletResponse response) {
		System.err.println("BaseController.authenticationException()");
		if (ShiroFilterUtils.isAjax(request)) {
			// 输出JSON
			Map<String, Object> map = new HashMap<>();
			map.put("code", BussCode.NOT_LOGIN.getCode());
			map.put("msg", "账户未登录");
			writeJson(map, response);
			return null;
		} else {
			return "redirect:" + LOGIN_URL;
		}
	}

	/**
	 * 权限异常
	 */
	@ExceptionHandler({ UnauthorizedException.class, AuthorizationException.class })
	public String authorizationException(HttpServletRequest request, HttpServletResponse response) {
		System.err.println("BaseController.authorizationException()");
		if (ShiroFilterUtils.isAjax(request)) {
			// 输出JSON
			Map<String, Object> map = new HashMap<>();
			map.put("code", BussCode.NOT_AUTHORIZATION);
			map.put("msg", "无权限");
			writeJson(map, response);
			return null;
		} else {
			return "redirect:" + UNAUTHORIZED_URL;
		}
	}

	/**
	 * 输出JSON
	 *
	 * @param response
	 * @author SHANHY
	 * @create 2017年4月4日
	 */
	private void writeJson(Map<String, Object> map, HttpServletResponse response) {
		PrintWriter out = null;
		try {
			response.setCharacterEncoding("UTF-8");
			response.setContentType("application/json; charset=utf-8");
			out = response.getWriter();
			out.write(JsonUtils.toJsonStr(map));
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (out != null) {
				out.close();
			}
		}
	}
}
