package com.bsp.shiro.listeners;

import java.util.Date;

import org.apache.log4j.Logger;
import org.apache.shiro.session.Session;
import org.apache.shiro.session.SessionListener;


public class MySessionListener implements SessionListener{

	private static final Logger logger=Logger.getLogger(MySessionListener.class);

	@Override
	public void onStart(Session session) {
		// TODO Auto-generated method stub
		logger.info("会话创建：" + session.getId()+"》》时间："+session.getLastAccessTime());  
	}

	@Override
	public void onStop(Session session) {
		// TODO Auto-generated method stub
		logger.info("会话过期：" + session.getId()+"》》时间："+new Date());  
	}

	@Override
	public void onExpiration(Session session) {
		// TODO Auto-generated method stub
		logger.info("会话停止：" + session.getId()+"》》时间："+session.getLastAccessTime());  
	}

}
