package com.bsp.dao;

import java.util.List;

import com.bsp.entity.SecondaryClassification;

public interface SecondaryClassificationMapper extends GenericMapper<SecondaryClassification, Integer> {
	/**
	 * 根据一级分类Id查找
	 * @param pcId
	 */
	List<SecondaryClassification> selectByPcId(Integer pcId);
}