package com.bsp.service;

import com.bsp.dto.LoanableBookQueryObject;
import com.bsp.entity.LoanableBook;
import com.bsp.entity.PrimaryClassification;
import com.bsp.entity.SecondaryClassification;
import com.bsp.entity.User;
import com.bsp.utils.Page;

import java.util.List;

public interface ILoanableBookService {

	/**
	 * 根据id返回图书信息
	 * @param id 图书id
	 */
	LoanableBook getLoanableBookInforByid(Integer id);
	
	/**
	 * 返回所有图书分页查询的结果
	 * @param queryObject 封装分页请求中的参数
	 */
	Page getAllListBook(LoanableBookQueryObject queryObject);

	/**
	 * 返回指定用户的所有图书分页查询的结果
	 * @param queryObject 封装分页请求中的参数
	 */
	Page getAllListBookByUUID(LoanableBookQueryObject queryObject);
	/**
	 * 返回一级分类的所有结果
	 */
	List<PrimaryClassification> getAllPrimary();
	
	/**
	 * 返回二级分类的所有结果
	 */
	List<SecondaryClassification> getAllSecondary();

	/**
	 * 返回一级分类图书分页查询的结果
	 * @param queryObject 封装分页请求中的参数
	 * @param pcId 一级分类id
	 */
	Page getPrimaryListBook(LoanableBookQueryObject queryObject, Integer pcId);

	/**
	 * 返回二级分类图书分页查询的结果
	 * @param queryObject 封装分页请求中的参数
	 * @param scId 二级分类id
	 */
	Page getSecondaryListBook(LoanableBookQueryObject queryObject, Integer scId);

	/**
	 * 返回二级分类图书分页查询的结果
	 * @param queryObject 封装分页请求中的参数
	 * @param bookName 搜索的书名
	 * @return
	 */
	Page getSearchListBook(LoanableBookQueryObject queryObject, String bookName);
	
	/**
	 * 关闭共享
	 * @param user 用户用于判断是否为本用户操作
	 * @param lbId 
	 */
	void close(User user, Integer lbId);
	
	/**
	 * 开启共享
	 * @param user 用户用于判断是否为本用户操作
	 * @param lbId 
	 */
	void open(User user, Integer lbId);
	
	/**
	 * 删除共享
	 * @param user 用户用于判断是否为本用户操作
	 * @param lbId 
	 */
	void delete(User user, Integer lbId);
	
}
