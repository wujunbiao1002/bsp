package com.bsp.shiro.realms;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.bsp.entity.Administrator;
import com.bsp.exceptions.SystemErrorException;
import com.bsp.service.IAdminService;

@Component
public class AdminRealm  extends AuthorizingRealm  {
	
	@Autowired
	private IAdminService adminService;
	
	public void setAdminService(IAdminService adminService) {
		this.adminService = adminService;
	}

	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
		Administrator admin = (Administrator) principals.getPrimaryPrincipal();
		// 存放权限信息
		SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
		info.addRole("admin");
		// 是否为超级管理员
		if (admin.getaLevel()==1||admin.getaLevel()==0) {
			info.addRole("super_admin");
		}
		return info;
	}

	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
		String username = (String) token.getPrincipal();
		Administrator admin  = null;
		try {
			admin = adminService.findByAID(username);
		} catch (SystemErrorException e) {
			e.printStackTrace();
			throw new SystemErrorException("系统异常，登录失败");
		}
		// 不存在
		if (admin == null) {
			throw new UnknownAccountException("用户不存在或不可用");
		}
		// 是否可用
		if (!admin.isAvailible()) {
			throw new LockedAccountException("用户不存在或不可用");
		}
		// 参数分别是对象，凭证(会拿去和登陆逻辑参数比较)
		return new SimpleAuthenticationInfo(admin, admin.getaPassword(), getName());
	}
	
}
