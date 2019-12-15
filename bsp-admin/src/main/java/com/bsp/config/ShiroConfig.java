package com.bsp.config;

import org.apache.shiro.cache.ehcache.EhCacheManager;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.session.mgt.SessionManager;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.bsp.shiro.filters.LoginFilter;
import com.bsp.shiro.realms.AdminRealm;

import java.util.LinkedHashMap;
import java.util.Map;

import javax.servlet.Filter;

@Configuration
public class ShiroConfig {
	
	/**
	 * 创建缓存实例
	 */
	@Bean("shiroEhCacheManager")
	public EhCacheManager shiroEhCacheManager() {
		EhCacheManager cm = new EhCacheManager();
		cm.setCacheManagerConfigFile("classpath:ehcache-shiro.xml");
		return cm;
	}

	@Bean("sessionManager")
	public SessionManager sessionManager() {
		DefaultWebSessionManager sessionManager = new DefaultWebSessionManager();
		// 设置session过期时间为1小时(单位：毫秒)，默认为30分钟
		sessionManager.setGlobalSessionTimeout(60 * 60 * 1000);
		sessionManager.setSessionValidationSchedulerEnabled(true);
		// 去除地址栏JSESSIONID参数
		sessionManager.setSessionIdUrlRewritingEnabled(false);
		return sessionManager;
	}
	
	@Bean
	public AdminRealm adminRealm() {
		return new AdminRealm();
	}
	
	@Bean("securityManager")
	public SecurityManager securityManager(AdminRealm adminRealm, SessionManager sessionManager) {
		DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
		securityManager.setRealm(adminRealm);
		securityManager.setSessionManager(sessionManager);
		securityManager.setCacheManager(shiroEhCacheManager());
		return securityManager;
	}

	@Bean("shiroFilter")
	public ShiroFilterFactoryBean shiroFilter(SecurityManager securityManager) {
		ShiroFilterFactoryBean shiroFilter = new ShiroFilterFactoryBean();
		shiroFilter.setSecurityManager(securityManager);
		
		// 配置过滤器
		Map<String, Filter> filters = new LinkedHashMap<>();
		filters.put("login", loginFilter()); // 登录过滤器
		shiroFilter.setFilters(filters);
		shiroFilter.setLoginUrl("/login");
		//shiroFilter.setUnauthorizedUrl("/no_access");
		// 放权请求
		Map<String, String> filterMap = new LinkedHashMap<>();
		filterMap.put("/statics/**", "anon");
		filterMap.put("/verifyCode", "anon");
		filterMap.put("/login", "anon");
		filterMap.put("/logout", "anon");
		filterMap.put("/favicon.ico", "anon");
		filterMap.put("/**", "login");// 拦截所有请求
		shiroFilter.setFilterChainDefinitionMap(filterMap);
		return shiroFilter;
	}
	
	/**
	 * 登录过滤器
	 */
	@Bean
	public LoginFilter loginFilter() {
		return new LoginFilter();
	}
	
}
