package com.bsp.dao;

import java.util.Map;

import com.bsp.entity.News;
import com.bsp.entity.OutdatedNews;

public interface OutdatedNewsMapper extends GenericMapper<OutdatedNews, Integer> {
	
	/**
	 * 包含消息内容的更新
	 * @param  记录
	 * @return 受影响行数
	 */
	int updateByPrimaryKeyWithBLOBs(News record);
	
	/**
	 * 根据用户id和消息id删除一条已读消息
	 * @param param 参数必须包含uuid和nId
	 */
	Integer deleteByUserIdAndNid(Map<String, Object> params);
}