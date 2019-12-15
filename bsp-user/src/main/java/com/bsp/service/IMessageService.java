package com.bsp.service;


import com.bsp.dto.QueryObject;
import com.bsp.entity.News;
import com.bsp.utils.Page;

public interface IMessageService {
	
	/**
	 * 根据获取一条消息
	 * @param id 消息id
	 */
	News getUnReadMsg(Integer id);
	
	/**
	 * 根据获取一条已读信息
	 * @param id 消息id
	 */
	News getReadMsg(Integer id);
	
	/**
	 * 根据用户ID获取新（未读）消息数
	 * @param uuid 用户ID
	 */
	Integer getNewMsgAmount(String uuid);
	
	/**
	 * 获取未读消息列表（分页）
	 * @param queryObject 查询对象
	 */
	Page getNewMsgList(QueryObject queryObject);
	
	/**
	 * 获取已读消息列表（分页）
	 * @param queryObject
	 */
	Page getHistoryMsgList(QueryObject queryObject);
	
	/**
	 * 阅读一条未读消息，把未读消息移动到已读消息表中
	 * @param uuid 用户
	 * @param nId 消息Id
	 */
	void readMsg(String uuid, Integer nId);
	
	/**
	 * 根据用户id和消息id删除一条未读消息
	 * @param uuid 用户id
	 * @param nId 消息id
	 */
	void deleteUnreadMsg(String uuid, Integer nId);
	
	/**
	 * 根据用户id和消息id删除一条已读消息
	 * @param uuid 用户id
	 * @param nId 消息id
	 */
	void deleteReadMsg(String uuid, Integer nId);
	
	/**
	 * 发送消息
	 * @param mail 用户邮箱（登录名）
	 * @param subject 主题
	 * @param content 正文
	 */
	void sendMessage(String mail, String subject, String content);
}
