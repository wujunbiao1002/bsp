package com.bsp.controller;

import javax.servlet.http.HttpServletRequest;

import org.apache.shiro.authz.annotation.RequiresUser;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.bsp.entity.Administrator;
import com.bsp.shiro.ShiroUtils;

/**
 * 控制页面跳转
 * 
 * @author hayate
 *
 */
@Controller
@Scope(value="prototype")
public class PageController {
	
	/**
	 * 控制页面跳转
	 *
	 * @param request
	 * @param htmlName 页面
	 * @return
	 */
	@RequestMapping("/p/{htmlName}")
	@RequiresUser
	public String showPage(HttpServletRequest request, @PathVariable("htmlName") String htmlName) {
		return "/admin/" + htmlName;
	}
	
	/**
	 * 账号登陆页面
	 */
	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public String signIn() {
		Administrator administrator = ShiroUtils.getToken();
		if (administrator!=null) {
			return "redirect:/";
		}
		return "/admin/login";
	}
	
	/**
	 * 无权限页面
	 */
	@RequestMapping(value = "/unauthorized", method = RequestMethod.GET)
	public String unauthorized() {
		Administrator administrator = ShiroUtils.getToken();
		if (administrator!=null) {
			return "redirect:/";
		}
		return "/admin/unauthorized";
	}
	
	/**
	 * 首页
	 */
	@RequestMapping({"/index","/"})
	@RequiresUser
	public String index() {
		return "/admin/index";
	}

}
