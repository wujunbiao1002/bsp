package com.bsp.controller;

import org.apache.shiro.authz.annotation.RequiresUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.bsp.dto.LoanableBookQueryObject;
import com.bsp.exceptions.DataUpdateException;
import com.bsp.exceptions.SystemErrorException;
import com.bsp.service.ILoanableBookService;
import com.bsp.shiro.ShiroUtils;
import com.bsp.utils.Page;
import com.bsp.utils.Result;

/**
 * 用户已共享图书管理，查表loanable_book，图书所有者为当前用户
 * @author hayate
 *
 */
@RestController
@Scope(value="prototype")
@RequestMapping("shared_book")
public class SharedBookController extends BaseController {
	
	
	@Autowired
	private ILoanableBookService loanableBookService;
	
	public void setLoanableBookService(ILoanableBookService loanableBookService) {
		this.loanableBookService = loanableBookService;
	}
	
	@RequestMapping("page")
	@RequiresUser
	public Result page(LoanableBookQueryObject queryObject) {
		queryObject.setUuid(ShiroUtils.getToken().getUuid());
		Page page = null;
		try {
			page = loanableBookService.getAllListBookByUUID(queryObject);
		} catch (SystemErrorException e) {
			e.printStackTrace();
			return Result.error(e.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
			return Result.error("由于未知错误，查询数据失败");
		}
		Result result = Result.success();
		result.put("page", page);
		return result;
	}
	
	/**
	 * 更新分享的图书信息，只能修改部分字段，实现时请完成参数列表
	 * @param lbId 正在共享的书的ID
	 */
	@RequestMapping("update")
	public Result update(@RequestParam("lbId") Integer lbId) {
		return Result.success();
	}
	
	/**
	 * 关闭共享
	 * @param lbId 正在共享的书的ID
	 */
	@RequestMapping("close")
	public Result close(@RequestParam("lbId") Integer lbId) {
		try {
			loanableBookService.close(ShiroUtils.getToken(), lbId);
		} catch (DataUpdateException e) {
			e.printStackTrace();
			return Result.error(e.getMessage());
		} catch (SystemErrorException e) {
			e.printStackTrace();
			return Result.error(e.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
			return Result.error("由于未知错误，操作失败");
		}
		return Result.success();
	}
	
	/**
	 * 开启共享
	 * @param lbId 正在共享的书的ID
	 */
	@RequestMapping("open")
	public Result open(@RequestParam("lbId") Integer lbId) {
		try {
			loanableBookService.open(ShiroUtils.getToken(), lbId);
		} catch (DataUpdateException e) {
			e.printStackTrace();
			return Result.error(e.getMessage());
		} catch (SystemErrorException e) {
			e.printStackTrace();
			return Result.error(e.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
			return Result.error("由于未知错误，操作失败");
		}
		return Result.success();
	}
	
	/**
	 * 删除共享
	 * @param lbId 正在共享的书的ID
	 */
	@RequestMapping("delete")
	public Result delete(@RequestParam("lbId") Integer lbId) {
		try {
			loanableBookService.delete(ShiroUtils.getToken(), lbId);
		} catch (DataUpdateException e) {
			e.printStackTrace();
			return Result.error(e.getMessage());
		} catch (SystemErrorException e) {
			e.printStackTrace();
			return Result.error(e.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
			return Result.error("由于未知错误，操作失败");
		}
		return Result.success();
	}
	
}
