package com.bsp.dao;

import java.util.List;

import com.bsp.dto.ClassificationQueryObject;
import com.bsp.entity.PrimaryClassification;

public interface PrimaryClassificationMapper extends GenericMapper<PrimaryClassification, Integer> {
	
	/**
	 * 计算分页查询中的记录总数
	 * @param queryObject 查询对象
	 */
	int getTotalCount(ClassificationQueryObject queryObject);
	
	/**
	 * 分页高级查询
	 * @param queryObject 查询对象
	 */
	List<PrimaryClassification> selectByQueryObject(ClassificationQueryObject queryObject);
}