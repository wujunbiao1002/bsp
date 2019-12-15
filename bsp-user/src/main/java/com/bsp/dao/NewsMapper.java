package com.bsp.dao;

import java.util.Map;

import com.bsp.entity.News;

public interface NewsMapper extends GenericMapper<News, Integer> {
	
	/**
	 * 包含消息内容的更新
	 * @param  记录
	 * @return 受影响行数
	 */
	int updateByPrimaryKeyWithBLOBs(News record);
	
	/**
	 * 未读消息数
	 * @param uuid 用户id
	 */
	Integer getNewMsgAmount(String uuid);
	
	/**
	 * 根据用户id和消息id删除一条未读消息
	 * @param param 参数必须包含uuid和nId
	 */
	Integer deleteByUserIdAndNid(Map<String, Object> params);
	
}