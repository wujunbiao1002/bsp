package com.bsp.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.bsp.dto.LoanableBookQueryObject;
import com.bsp.entity.LoanableBook;
import com.bsp.entity.PrimaryClassification;
import com.bsp.entity.SecondaryClassification;
import com.bsp.exceptions.SystemErrorException;
import com.bsp.service.ILoanableBookService;
import com.bsp.utils.Page;
import com.bsp.utils.Result;

/**
 * 可借图书数据调取
 * @author hayate
 *
 */
@RestController
@Scope(value="prototype")
@RequestMapping("/loanble_book")
public class LoanableBookController extends BaseController {
	
	@Autowired
	private ILoanableBookService loanableBookService;
	
	public void setLoanableBookService(ILoanableBookService loanableBookService) {
		this.loanableBookService = loanableBookService;
	}
	
	/**
	 * 分页查询所有图书
	 * @param queryObject
	 */
	@RequestMapping("query")
	public Result query(LoanableBookQueryObject queryObject) {
		Page page = null;
		try {
			page = loanableBookService.getAllListBook(queryObject);
		}catch (SystemErrorException e) {
			e.printStackTrace();
			return Result.error(e.getMessage());
		}catch (Exception e) {
			e.printStackTrace();
			return Result.error("由于未知错误，请求失败");
		}
		Result result = new Result();
		result.put("booklist", page);
		return result;
	}
	
	/**
	 * 分页查询一级分类图书
	 * @param queryObject
	 */
	@RequestMapping("queryprimary")
	public Result queryprimary(LoanableBookQueryObject queryObject,@RequestParam("pcId")Integer pcId) {
		//System.out.println("一级分类的id："+pcId);
		Page page = null;
		try {
			page = loanableBookService.getPrimaryListBook(queryObject,pcId);
		}catch (SystemErrorException e) {
			e.printStackTrace();
			return Result.error(e.getMessage());
		}catch (Exception e) {
			e.printStackTrace();
			return Result.error("由于未知错误，请求失败");
		}
		Result result = new Result();
		result.put("booklist", page);
		result.put("primary", pcId);
		return result;
	}
	
	/**
	 * 分页查询二级分类图书
	 * @param queryObject
	 */
	@RequestMapping("querySecondary")
	public Result querySecondary(LoanableBookQueryObject queryObject,@RequestParam("scId")Integer scId) {
		Page page = null;
		try {
			page = loanableBookService.getSecondaryListBook(queryObject,scId);
		}catch (SystemErrorException e) {
			e.printStackTrace();
			return Result.error(e.getMessage());
		}catch (Exception e) {
			e.printStackTrace();
			return Result.error("由于未知错误，请求失败");
		}
		Result result = new Result();
		result.put("booklist", page);
		result.put("secondary", scId);
		return result;
	}
	
	/**
	 * 分页查询搜索的图书
	 * @param queryObject
	 */
	@RequestMapping("querySearch")
	public Result querySearch(LoanableBookQueryObject queryObject,@RequestParam("bookName")String bookName) {
		Page page = null;
		try {
			page = loanableBookService.getSearchListBook(queryObject,bookName);
		}catch (SystemErrorException e) {
			e.printStackTrace();
			return Result.error(e.getMessage());
		}catch (Exception e) {
			e.printStackTrace();
			return Result.error("由于未知错误，请求失败");
		}
		Result result = new Result();
		result.put("booklist", page);
		result.put("search", bookName);
		return result;
	}
	
	/**
	 * 获取一本书
	 * @param idInteger
	 * @return
	 */
	@RequestMapping("/detail")
	public Result detail(@RequestParam("lbId")Integer idInteger) {
		LoanableBook book = null;
		try {
			book = loanableBookService.getLoanableBookInforByid(idInteger);
		}catch (SystemErrorException e) {
			e.printStackTrace();
			return Result.error(e.getMessage());
		}catch (Exception e) {
			e.printStackTrace();
			return Result.error("由于未知错误，请求失败");
		}
		Result result = new Result();
		result.put("detail", book);
		return result;
	}
	
	/**
	 * 获取所有一级分类和二级分类
	 * @return
	 */
	@RequestMapping("/classify")
	public Result classify() {
		List<PrimaryClassification> primarylist = null;
		List<SecondaryClassification> secondarylist = null;
		try {
			primarylist = loanableBookService.getAllPrimary();
			secondarylist = loanableBookService.getAllSecondary();
		}catch (SystemErrorException e) {
			e.printStackTrace();
			return Result.error(e.getMessage());
		}catch (Exception e) {
			e.printStackTrace();
			return Result.error("由于未知错误，请求失败");
		}
		Result result = new Result();
		result.put("primarylist", primarylist);
		result.put("secondarylist", secondarylist);
		return result;
	}
	
}
