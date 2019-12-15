package com.bsp.service;

import java.util.List;

import com.bsp.dto.QueryObject;
import com.bsp.entity.SecondaryClassification;
import com.bsp.utils.Page;

public interface ISecondaryClassificationService {
	
	/**
	 * 获取一页数据
	 * @param queryObject
	 */
	Page findByQueryObject(QueryObject queryObject);
	
	/**
	 * 增加一条记录
	 * @param secondaryClassification 实体
	 */
	void add(SecondaryClassification secondaryClassification);
	
	/**
	 * 删除一条记录
	 * @param id 记录id
	 */
	void delete(Integer id);
	
	/**
	 * 更新
	 * @param pc
	 */
	void update(SecondaryClassification pc);
	
	/**
	 * 恢复使用
	 * @param id
	 */
	void reuse(Integer id);
	
	/**
	 * 根据一级分类Id查找
	 * @param pcId
	 */
	List<SecondaryClassification> findByPrimaryClassificationId(Integer pcId);
}
