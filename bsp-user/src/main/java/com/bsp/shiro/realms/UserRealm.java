package com.bsp.shiro.realms;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.bsp.entity.User;
import com.bsp.exceptions.SystemErrorException;
import com.bsp.service.IUserService;

@Component
public class UserRealm  extends AuthorizingRealm  {
	
	@Autowired
	private IUserService userService;
	
	public void setUserService(IUserService userService) {
		this.userService = userService;
	}

	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
		System.err.println("doGetAuthenticationInfo");
		String username = (String) token.getPrincipal();
		User user  = null;
		try {
			user = userService.getUserByMail(username);
		} catch (SystemErrorException e) {
			e.printStackTrace();
			throw new SystemErrorException("系统异常，登录失败");
		}
		// 不存在
		if (user == null) {
			throw new UnknownAccountException("用户不存在或不可用");
		}
		// 是否可用
		if (!user.isAvailible()) {
			throw new LockedAccountException("用户不存在或不可用");
		}
		// 参数分别是对象，凭证(会拿去和登陆逻辑参数比较)
		return new SimpleAuthenticationInfo(user, user.getPassword(), getName());
	}

	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
		return null;
	}
	
}
