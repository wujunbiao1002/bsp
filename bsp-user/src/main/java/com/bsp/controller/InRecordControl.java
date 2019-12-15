package com.bsp.controller;

import org.apache.shiro.authz.annotation.RequiresUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.bsp.dto.OrderQueryObject;
import com.bsp.entity.User;
import com.bsp.exceptions.SystemErrorException;
import com.bsp.service.IOrderService;
import com.bsp.shiro.ShiroUtils;
import com.bsp.utils.Page;
import com.bsp.utils.Result;

@RestController
@Scope(value="prototype")
@RequestMapping("/in_record")
public class InRecordControl extends BaseController{
	
	@Autowired
	private IOrderService orderService;
	public void setOrderService(IOrderService orderService) {
		this.orderService = orderService;
	}
	
	/**
	 * 分页查询借阅记录
	 */
	@RequestMapping("query")
	@RequiresUser
	public Result query(OrderQueryObject queryObject) {
		User user = null;
		user = ShiroUtils.getToken();
		Page page = null;
		try {
			queryObject.setUuid(user.getUuid());
			page = orderService.getAllListOrder(queryObject);
		} catch (SystemErrorException e) {
			e.printStackTrace();
			return Result.error(e.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
			return Result.error("由于未知错误，请求失败");
		}
		Result result = new Result();
		result.put("page", page);
		return result;
	}
	 /**
	  * 取消订单
	  * @return
	  */
	@RequestMapping("cancel")
	@RequiresUser
	public Result cancel(@RequestParam("lrId")Integer lrId) {
		try {
			orderService.updata(lrId);
		} catch (SystemErrorException e) {
			e.printStackTrace();
			return Result.error(e.getMessage());
		} catch (Exception e) {			
			e.printStackTrace();
			return Result.error("由于未知错误，请求失败");
		}
		Result result = new Result();
		result.put("msg", "取消订单成功！");
		return result;
	}

}
