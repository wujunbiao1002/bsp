package com.bsp.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bsp.dao.NewsMapper;
import com.bsp.dao.UserMapper;
import com.bsp.entity.News;
import com.bsp.entity.User;
import com.bsp.exceptions.SystemErrorException;
import com.bsp.exceptions.UserDefinedException;
import com.bsp.service.IMessageService;

@Service("messageService")
public class MessageService implements IMessageService {
	
	@Autowired
	private NewsMapper newsMapper;
	@Autowired
	private UserMapper userMapper;

	public void setUserMapper(UserMapper userMapper) {
		this.userMapper = userMapper;
	}

	public void setNewsMapper(NewsMapper newsMapper) {
		this.newsMapper = newsMapper;
	}


	@Override
	public void sendMessage(String mail, String subject, String content) {
		News news = new News();
		news.setnContent(content);
		news.setNewsTime(new Date());
		news.setnSubject(subject);
		List<User> list = userMapper.selectByMail(mail);
		if (list.size()==0) {
			throw new UserDefinedException("用户不存在");
		}
		news.setUser(list.get(0));
		try {
			newsMapper.insertSelective(news);
		} catch (Exception e) {
			e.printStackTrace();
			throw new SystemErrorException("系统异常，发送信息失败");
		}
	}

}
