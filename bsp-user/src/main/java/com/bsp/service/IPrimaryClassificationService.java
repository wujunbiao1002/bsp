package com.bsp.service;

import java.util.List;

import com.bsp.dto.QueryObject;
import com.bsp.entity.PrimaryClassification;
import com.bsp.utils.Page;

public interface IPrimaryClassificationService {
	
	/**
	 * 获取一页数据
	 * @param queryObject
	 */
	Page findByQueryObject(QueryObject queryObject);
	
	/**
	 * 增加一条记录
	 * @param primaryClassification 实体
	 */
	void add(PrimaryClassification primaryClassification);
	
	/**
	 * 删除一条记录
	 * @param id 记录id
	 */
	void delete(Integer id);
	
	/**
	 * 更新
	 * @param pc
	 */
	void update(PrimaryClassification pc);
	
	/**
	 * 恢复使用
	 * @param id
	 */
	void reuse(Integer id);
	
	/**
	 * 查询所有，不包括删除状态的记录
	 */
	List<PrimaryClassification> findAll();
}
