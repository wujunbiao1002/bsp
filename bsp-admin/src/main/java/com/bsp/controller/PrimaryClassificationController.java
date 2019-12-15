package com.bsp.controller;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authz.annotation.RequiresUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bsp.dto.ClassificationQueryObject;
import com.bsp.entity.PrimaryClassification;
import com.bsp.exceptions.DataUpdateException;
import com.bsp.exceptions.SystemErrorException;
import com.bsp.service.IPrimaryClassificationService;
import com.bsp.utils.CommonUtil;
import com.bsp.utils.Page;
import com.bsp.utils.Result;

@RestController
@Scope(value="prototype")
@RequestMapping("pc")
public class PrimaryClassificationController extends BaseController {

	@Autowired
	private IPrimaryClassificationService primaryClassificationService;
	
	public void setPrimaryClassificationService(IPrimaryClassificationService primaryClassificationService) {
		this.primaryClassificationService = primaryClassificationService;
	}

	@RequestMapping("page")
	@RequiresUser
	public Page page(ClassificationQueryObject queryObject) {
		if (!StringUtils.isBlank(queryObject.getSort())) {
			queryObject.setSort(CommonUtil.HumpToUnderline(queryObject.getSort()));
		}
		return primaryClassificationService.findByQueryObject(queryObject);
	}
	
	@RequestMapping("add")
	@RequiresUser
	public Result add(PrimaryClassification obj) {
		try {
			primaryClassificationService.add(obj);
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
	
	@RequestMapping("delete")
	@RequiresUser
	public Result delete(Integer id) {
		try {
			primaryClassificationService.delete(id);
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
	
	@RequestMapping("update")
	@RequiresUser
	public Result update(PrimaryClassification obj) {
		try {
			primaryClassificationService.update(obj);
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
	 * 恢复使用
	 * @param id
	 * @return
	 */
	@RequestMapping("reuse")
	@RequiresUser
	public Result reuse(Integer id) {
		try {
			primaryClassificationService.reuse(id);
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
	 * 查询所有一级分类，不包括删除状态的记录
	 */
	@RequestMapping("all")
	@RequiresUser
	public Result all() {
		List<PrimaryClassification> list = null;
		try {
			list = primaryClassificationService.findAll();
		} catch (SystemErrorException e) {
			e.printStackTrace();
			return Result.error(e.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
			return Result.error("由于未知错误，查询一级分类数据失败");
		}
		Result result = Result.success();
		result.put("list", list);
		return result;
	}
	
}
