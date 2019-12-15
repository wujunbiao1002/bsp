package com.bsp.controller;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authz.annotation.RequiresUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bsp.dto.ClassificationQueryObject;
import com.bsp.entity.SecondaryClassification;
import com.bsp.exceptions.DataUpdateException;
import com.bsp.exceptions.SystemErrorException;
import com.bsp.service.ISecondaryClassificationService;
import com.bsp.utils.CommonUtil;
import com.bsp.utils.Page;
import com.bsp.utils.Result;

@RestController
@Scope(value="prototype")
@RequestMapping("sc")
public class SencondaryClassificationController extends BaseController {

	@Autowired
	private ISecondaryClassificationService secondaryClassificationService;
	
	public void setSecondaryClassificationService(ISecondaryClassificationService secondaryClassificationService) {
		this.secondaryClassificationService = secondaryClassificationService;
	}

	@RequestMapping("page")
	@RequiresUser
	public Page page(ClassificationQueryObject queryObject) {
		if (!StringUtils.isBlank(queryObject.getSort())) {
			queryObject.setSort(CommonUtil.HumpToUnderline(queryObject.getSort()));
		}
		return secondaryClassificationService.findByQueryObject(queryObject);
	}
	
	@RequestMapping("add")
	@RequiresUser
	public Result add(@RequestBody SecondaryClassification obj) {
		System.out.println(obj.getScName());
		try {
			secondaryClassificationService.add(obj);
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
			secondaryClassificationService.delete(id);
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
	public Result update(@RequestBody SecondaryClassification obj) {
		try {
			secondaryClassificationService.update(obj);
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
			secondaryClassificationService.reuse(id);
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
	
	@RequestMapping("findByPcId")
	@RequiresUser
	public Result findByPcId(Integer pcId) {
		List<SecondaryClassification> list = null;
		try {
			list = secondaryClassificationService.findByPrimaryClassificationId(pcId);
		} catch (SystemErrorException e) {
			e.printStackTrace();
			return Result.error(e.getMessage());
		}
		Result result = Result.success();
		result.put("list", list);
		return result;
	}
}
