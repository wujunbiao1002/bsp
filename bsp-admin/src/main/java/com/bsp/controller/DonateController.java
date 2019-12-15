package com.bsp.controller;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authz.annotation.RequiresUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bsp.dto.QueryObject;
import com.bsp.entity.DonatedBook;
import com.bsp.exceptions.SystemErrorException;
import com.bsp.service.IDonateService;
import com.bsp.utils.CommonUtil;
import com.bsp.utils.Page;
import com.bsp.utils.Result;

@RestController
@Scope(value="prototype")
@RequestMapping("donate")
public class DonateController {
	
	@Autowired
	private IDonateService donateService;
	
	public void setDonateService(IDonateService donateService) {
		this.donateService = donateService;
	}

	/**
	 * 获取订单列表捐赠
	 * @param queryObject 分页参数
	 */
	@RequestMapping("page")
	@RequiresUser
	public Page page(QueryObject queryObject) {
		if (!StringUtils.isBlank(queryObject.getSort())) {//排序字段名需要改成数据库使用的snake风格字符串
			queryObject.setSort(CommonUtil.HumpToUnderline(queryObject.getSort()));
		}
		Page page = donateService.findByQueryObject(queryObject);
		return page;
	}
	
	/**
	 * 捐赠
	 */
	@RequestMapping("donate")
	@RequiresUser
	public Result donate(@RequestBody DonatedBook donatedBook) {
		return Result.success();
	}
	
	/**
	 * 查找一条记录
	 * @param id 主键
	 * @return
	 */
	@RequestMapping("findByKey")
	@RequiresUser
	public Result findByKey(Integer id) {
		DonatedBook db = null;
		try {
			db = donateService.findByPrimaryKey(id);
		} catch (SystemErrorException e) {
			e.printStackTrace();
			return Result.error(e.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
			return Result.error("由于未知错误，查询数据失败");
		}
		Result result = Result.success();
		result.put("obj", db);
		return result;
	}
	
	/**
	 * 添加一条记录
	 */
	@RequestMapping("add")
	@RequiresUser
	public Result add(@RequestBody DonatedBook donatedBook) {
		try {
			donateService.add(donatedBook);
		} catch (SystemErrorException e) {
			e.printStackTrace();
			return Result.error(e.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
			return Result.error("由于未知错误，添加失败");
		}
		return Result.success();
	}
	
	/**
	 * 更新一条记录
	 */
	@RequestMapping("update")
	@RequiresUser
	public Result update(@RequestBody DonatedBook donatedBook) {
		try {
			donateService.update(donatedBook);
		} catch (SystemErrorException e) {
			e.printStackTrace();
			return Result.error(e.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
			return Result.error("由于未知错误，更新失败");
		}
		return Result.success();
	}
	
}
