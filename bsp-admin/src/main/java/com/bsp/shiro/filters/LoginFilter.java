package com.bsp.shiro.filters;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import org.apache.shiro.web.filter.AccessControlFilter;

import com.bsp.entity.Administrator;
import com.bsp.enums.BussCode;
import com.bsp.shiro.ShiroUtils;

public class LoginFilter  extends AccessControlFilter {
	
	@Override
	protected boolean isAccessAllowed(ServletRequest request,
			ServletResponse response, Object mappedValue) throws Exception {
		Administrator token = (Administrator)ShiroUtils.getToken();
		if(null != token || isLoginRequest(request, response)){// && isEnabled()
            return Boolean.TRUE;
        } 
		System.err.println(ShiroFilterUtils.isAjax(request));
		if (ShiroFilterUtils.isAjax(request)) {// ajax请求
			Map<String,String> resultMap = new HashMap<String, String>();
			resultMap.put("code", BussCode.NOT_LOGIN.getCode().toString());
			resultMap.put("msg", "用户未登录");//当前用户没有登录！
			ShiroFilterUtils.out(response, resultMap);
		}
		return Boolean.FALSE ;
            
	}

	@Override
	protected boolean onAccessDenied(ServletRequest request, ServletResponse response)
			throws Exception {
		//保存Request和Response 到登录后的链接
		saveRequestAndRedirectToLogin(request, response);
		return Boolean.FALSE ;
	}
	

}