package com.bsp.dao;

import com.bsp.dto.ClassificationQueryObject;
import com.bsp.entity.SecondaryClassification;

import java.util.List;

public interface SecondaryClassificationMapper extends GenericMapper<SecondaryClassification, Integer> {
	/**
	 * 计算分页查询中的记录总数
	 * @param queryObject 查询对象
	 */
	int getTotalCount(ClassificationQueryObject queryObject);
	
	/**
	 * 分页高级查询
	 * @param queryObject 查询对象
	 */
	List<SecondaryClassification> selectByQueryObject(ClassificationQueryObject queryObject);
	
	/**
	 * 根据一级分类Id查找
	 * @param pcId
	 */
	List<SecondaryClassification> selectByPcId(Integer pcId);

    List<SecondaryClassification> selectCountByPcId(Integer pcId);
}