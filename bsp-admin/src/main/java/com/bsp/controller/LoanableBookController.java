package com.bsp.controller;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authz.annotation.RequiresUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bsp.dto.LoanableBookQueryObject;
import com.bsp.exceptions.DataUpdateException;
import com.bsp.exceptions.SystemErrorException;
import com.bsp.service.ILoanableBookService;
import com.bsp.utils.CommonUtil;
import com.bsp.utils.Page;
import com.bsp.utils.Result;

/**
 * 可借图书数据调取
 * @author hayate
 *
 */
@RestController
@Scope(value="prototype")
@RequestMapping("/loanableBook")
public class LoanableBookController extends BaseController {
	
	@Autowired
	private ILoanableBookService loanableBookService;
	
	public void setLoanableBookService(ILoanableBookService loanableBookService) {
		this.loanableBookService = loanableBookService;
	}
	
	/**
	 * 调取一页数据
	 * @param queryObject 查询对象
	 */
	@RequestMapping("page")
	@RequiresUser
	public Page page(LoanableBookQueryObject queryObject) {
		if (!StringUtils.isBlank(queryObject.getSort())) {
			queryObject.setSort(CommonUtil.HumpToUnderline(queryObject.getSort()));
		}
		return loanableBookService.findByQueryObject(queryObject);
	}
	
	/**
	 * 上架
	 * @param id 订单id
	 */
	@RequestMapping("shelve")
	@RequiresUser
	public Result shelve(Integer id) {
		try {
			loanableBookService.Shelve(id);
		} catch (DataUpdateException e) {
			e.printStackTrace();
			return Result.error(e.getMessage());
		} catch (SystemErrorException e) {
			e.printStackTrace();
			return Result.error(e.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
			return Result.error("由于未知错误，请求失败");
		}
		return Result.success();
	}
	
	/**
	 * 下架
	 * @param id 订单id
	 */
	@RequestMapping("unshelve")
	@RequiresUser
	public Result Unshelve(Integer id) {
		try {
			loanableBookService.Unshelve(id);
		} catch (DataUpdateException e) {
			e.printStackTrace();
			return Result.error(e.getMessage());
		} catch (SystemErrorException e) {
			e.printStackTrace();
			return Result.error(e.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
			return Result.error("由于未知错误，请求失败");
		}
		return Result.success();
	}
}
