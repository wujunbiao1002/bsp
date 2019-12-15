package com.bsp.controller;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.bsp.dto.OrderQueryObject;
import com.bsp.exceptions.DataUpdateException;
import com.bsp.exceptions.SystemErrorException;
import com.bsp.service.ILendService;
import com.bsp.shiro.ShiroUtils;
import com.bsp.utils.CommonUtil;
import com.bsp.utils.Page;
import com.bsp.utils.Result;

/**
 * 借出订单的管理，包括申请,借出方为当前用户，查表lending_record
 * @author hayate
 *
 */
@RestController
@Scope(value="prototype")
@RequestMapping("lend")
public class LendOrderController extends BaseController {
	
	@Autowired
	private ILendService lendService;
	
	public void setLendService(ILendService lendService) {
		this.lendService = lendService;
	}

	/**
	 * 同意借出
	 * @param lrId 借书记录id
	 */
	@RequestMapping("agree")
	public Result agree(@RequestParam("lrId") Integer lrId) {
		try {
			lendService.agree(lrId);
		} catch (SystemErrorException e) {
			e.printStackTrace();
			return Result.error(e.getMessage());
		} catch (DataUpdateException e) {
			e.printStackTrace();
			return Result.error(e.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
			return Result.error("由于未知错误，请求失败");
		}
		return Result.success();
	}
	
	/**
	 * 拒绝借出
	 * @param lrId 借书记录id
	 */
	@RequestMapping("deny")
	public Result deny(@RequestParam("lrId") Integer lrId) {
		try {
			lendService.deny(lrId);
		} catch (SystemErrorException e) {
			e.printStackTrace();
			return Result.error(e.getMessage());
		} catch (DataUpdateException e) {
			e.printStackTrace();
			return Result.error(e.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
			return Result.error("由于未知错误，请求失败");
		}
		return Result.success();
	}
	
	/**
	 * 获取一页数据
	 * @param OrderQueryObject 查询对象
	 */
	@RequestMapping("page")
	public Result page(OrderQueryObject queryObject) {
		if (!StringUtils.isBlank(queryObject.getSort())) {//排序字段名需要改成数据库使用的snake风格字符串
			queryObject.setSort(CommonUtil.HumpToUnderline(queryObject.getSort()));
		}
		queryObject.setUuid(ShiroUtils.getToken().getUuid());
		Page page = null;
		try {
			page = lendService.getPage(queryObject);
		} catch (SystemErrorException e) {
			e.printStackTrace();
			return Result.error(e.getMessage());
		} 
		catch (Exception e) {
			e.printStackTrace();
			return Result.error("由于未知错误，获取数据失败");
		}
		Result result = Result.success();
		result.put("page", page);
		return result;
	}

}
