package com.bsp.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bsp.dao.NewsMapper;
import com.bsp.dao.OutdatedNewsMapper;
import com.bsp.dao.UserMapper;
import com.bsp.dto.QueryObject;
import com.bsp.entity.News;
import com.bsp.entity.OutdatedNews;
import com.bsp.entity.User;
import com.bsp.exceptions.SystemErrorException;
import com.bsp.exceptions.UserDefinedException;
import com.bsp.service.IMessageService;
import com.bsp.utils.Page;

@Service
public class MessageService implements IMessageService {
	
	@Autowired
	private NewsMapper newsMapper;
	@Autowired
	private OutdatedNewsMapper outdatedNewsMapper;
	@Autowired
	private UserMapper userMapper;

	public void setUserMapper(UserMapper userMapper) {
		this.userMapper = userMapper;
	}
	
	public void setOutdatedNewsMapper(OutdatedNewsMapper outdatedNewsMapper) {
		this.outdatedNewsMapper = outdatedNewsMapper;
	}

	public void setNewsMapper(NewsMapper newsMapper) {
		this.newsMapper = newsMapper;
	}

	@Override
	public News getUnReadMsg(Integer id) {
		return null;
	}

	@Override
	public News getReadMsg(Integer id) {
		return null;
	}

	@Override
	public Integer getNewMsgAmount(String uuid) {
		Integer amount = null;
		try {
			amount = newsMapper.getNewMsgAmount(uuid);
		} catch (Exception e) {
			e.printStackTrace();
			throw new SystemErrorException("系统异常");
		}
		return amount;
	}

	@Override
	public Page getNewMsgList(QueryObject queryObject) {
		List<News> list = null;
		Integer totalCount;
		try {
			list = newsMapper.selectByQueryObject(queryObject);
			totalCount = newsMapper.getTotalCount(queryObject);
		} catch (Exception e) {
			e.printStackTrace();
			throw new SystemErrorException("系统异常");
		}
		return new Page(list, totalCount, queryObject.getLimit(), queryObject.getPageNumber());
	}

	@Override
	public Page getHistoryMsgList(QueryObject queryObject) {
		List<OutdatedNews> list = null;
		Integer totalCount;
		try {
			list = outdatedNewsMapper.selectByQueryObject(queryObject);
			totalCount = outdatedNewsMapper.getTotalCount(queryObject);
		} catch (Exception e) {
			e.printStackTrace();
			throw new SystemErrorException("系统异常");
		}
		return new Page(list, totalCount, queryObject.getLimit(), queryObject.getPageNumber());
	}
	
	@Override
	public void readMsg(String uuid, Integer nId) {
		Map<String, Object> params = new HashMap<>();
		params.put("uuid", uuid);
		params.put("nId", nId);
		try {
			News news = newsMapper.selectByPrimaryKey(nId);
			OutdatedNews outdatedNews = new OutdatedNews();
			BeanUtils.copyProperties(outdatedNews, news);
			newsMapper.deleteByUserIdAndNid(params);
			outdatedNewsMapper.insertSelective(outdatedNews);
		} catch (Exception e) {
			e.printStackTrace();
			throw new SystemErrorException("查看消息失败，系统异常");
		}
	}

	@Override
	public void deleteUnreadMsg(String uuid, Integer nId) {
		Map<String, Object> params = new HashMap<>();
		params.put("uuid", uuid);
		params.put("nId", nId);
		try {
			newsMapper.deleteByUserIdAndNid(params);
		} catch (Exception e) {
			e.printStackTrace();
			throw new SystemErrorException("删除消息失败");
		}
	}

	@Override
	public void deleteReadMsg(String uuid, Integer nId) {
		Map<String, Object> params = new HashMap<>();
		params.put("uuid", uuid);
		params.put("nId", nId);
		try {
			outdatedNewsMapper.deleteByUserIdAndNid(params);
		} catch (Exception e) {
			e.printStackTrace();
			throw new SystemErrorException("删除消息失败");
		}
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
