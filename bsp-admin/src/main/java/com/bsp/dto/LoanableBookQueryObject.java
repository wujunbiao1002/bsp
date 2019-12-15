package com.bsp.dto;

import com.bsp.entity.PrimaryClassification;
import com.bsp.entity.SecondaryClassification;

public class LoanableBookQueryObject extends QueryObject{
	
	private Integer status;
	private PrimaryClassification primaryClassification; // 一级分类,设置二级分类后无效
	private SecondaryClassification secondaryClassification; // 二级分类
	
	public LoanableBookQueryObject() {
		super();
	}
	
	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	/**
	 * 根据一级分类查询
	 * @param limit 页大小
	 * @param pageNumber 页码
	 * @param order 排序，desc|asc
	 * @param sort 排序字段
	 * @param search 搜索关键字
	 * @param primaryClassification
	 */
	public LoanableBookQueryObject(Integer limit, Integer pageNumber, String order, String sort, String search,
			PrimaryClassification primaryClassification) {
		super(limit, pageNumber, order, sort, search);
		this.primaryClassification = primaryClassification;
	}
	
	
	/**
	 * 根据一级分类查询
	 * @param limit 页大小
	 * @param pageNumber 页码
	 * @param order 排序，desc|asc
	 * @param sort 排序字段
	 * @param search 搜索关键字
	 * @param secondaryClassification 二级分类
	 */
	public LoanableBookQueryObject(Integer limit, Integer pageNumber, String order, String sort, String search,
			SecondaryClassification secondaryClassification) {
		super(limit, pageNumber, order, sort, search);
		this.secondaryClassification = secondaryClassification;
	}

	
	/**
	 * 
	 * @param limit 页大小
	 * @param pageNumber 页码
	 * @param order 排序，desc|asc
	 * @param sort 排序字段
	 * @param search 搜索关键字
	 */
	public LoanableBookQueryObject(Integer limit, Integer pageNumber, String order, String sort, String search) {
		super(limit, pageNumber, order, sort, search);
	}

	public PrimaryClassification getPrimaryClassification() {
		return primaryClassification;
	}

	public void setPrimaryClassification(PrimaryClassification primaryClassification) {
		this.primaryClassification = primaryClassification;
	}

	public SecondaryClassification getSecondaryClassification() {
		return secondaryClassification;
	}

	public void setSecondaryClassification(SecondaryClassification secondaryClassification) {
		this.secondaryClassification = secondaryClassification;
	}
	
}
