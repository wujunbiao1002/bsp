package com.bsp.controller;

import org.springframework.context.annotation.Scope;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.bsp.dto.QueryObject;
import com.bsp.utils.Result;

/**
 * 借书记录，借入方为当前用户，查表lending_record、lending_history
 * @author hayate
 *
 */
@RestController
@Scope(value="prototype")
@RequestMapping("borrow")
public class BorrowController extends BaseController {
	
	/**
	 * 申请借书
	 * @param lbId 可借出的书的id
	 * @param mount 借书数量
	 */
	@RequestMapping("apply")
	public Result apply(@RequestParam("lbId") Integer lbId, @RequestParam("mount")  Integer mount) {
		return Result.success();
	}
	
	/**
	 * 更新申请信息，只能修改部分字段，实现时请完成参数列表
	 * @param lbId 长在进行中的订单的id，订单状态
	 */
	@RequestMapping("update")
	public Result update(@RequestParam("lbId") Integer lbId) {
		return Result.success();
	}
	
	/**
	 * 取消尚未被同意的借书申请
	 * @param lrId 长在进行中的订单的id，订单状态为借出方未同意借出
	 */
	@RequestMapping("cancel")
	public Result apply(@RequestParam("lrId") Integer lrId) {
		return Result.success();
	}
	
	/**
	 * 获取一条正在进行中的订单记录，查表lending_record
	 * @param lrId
	 */
	@RequestMapping("borrowing/detial")
	public Result getBorrowingDetial(@RequestParam("lrId") Integer lrId) {
		return Result.success();
	}
	
	/**
	 * 获取进行中的订单列表（分页），查表lending_history
	 * @param queryObject 查询对象
	 */
	@RequestMapping("borrowing/list")
	public Result getBorrowingList(QueryObject queryObject) {
		return Result.success();
	}
	
	/**
	 * 获取历史订单记录
	 * @param lhId 借出历史订单id
	 */
	@RequestMapping("history/detial")
	public Result getHistoryDetial(@RequestParam("lhId") Integer lhId) {
		return Result.success();
	}
	
	/**
	 * 获取历史列表
	 * @param queryObject 查询对象
	 * @return
	 */
	@RequestMapping("history/list")
	public Result getHistoryList(QueryObject queryObject) {
		return Result.success();
	}
}
