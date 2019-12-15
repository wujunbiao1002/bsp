package com.bsp.controller;

import com.bsp.dto.CheckLoanableBookQueryObject;
import com.bsp.entity.CheckLoanableBook;
import com.bsp.enums.BussCode;
import com.bsp.exceptions.SystemErrorException;
import com.bsp.service.IFileUploadService;
import com.bsp.service.IShareApplyService;
import com.bsp.shiro.ShiroUtils;
import com.bsp.utils.Page;
import com.bsp.utils.Result;
import org.apache.shiro.authz.annotation.RequiresUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;

@RestController
@Scope(value="prototype")
@RequestMapping("share")
public class ShareApplyController extends BaseController {
	
	@Autowired
	private IShareApplyService shareApplyService;
	
	@Autowired
	private IFileUploadService fileUploadService;

	@RequestMapping("/cover")
	public Result coverUpload(@RequestParam("cover") MultipartFile multipartFile, HttpServletRequest request) {
		String fileNameSessionKey = "session_cover_" + ShiroUtils.getToken().getUuid();
		try {
			String fileName = fileUploadService.uploadCover(multipartFile);
			request.getSession().setAttribute(fileNameSessionKey, fileName); // 包含二级目录的文件名到session
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
	 * 共享图书，生成待管理员审核的订单，图片上传还需定义接口
	 * @param param 封装图书信息
	 */
	@RequestMapping("apply")
	@RequiresUser
	public Result apply(@RequestBody CheckLoanableBook checkLoanableBook, HttpServletRequest request) {
		String fileNameSessionKey = "session_cover_" + ShiroUtils.getToken().getUuid();
		String session_cover = (String)request.getSession().getAttribute(fileNameSessionKey);
		if (session_cover == null) {
			return Result.error(BussCode.MODIFY_ERR, "请上传封面");
		}
		checkLoanableBook.setUser(ShiroUtils.getToken()); // 设置用户
		checkLoanableBook.setImagePath(session_cover); // 封面路径
		try {
			shareApplyService.addShare(checkLoanableBook);
		} catch (SystemErrorException e) {
			e.printStackTrace();
			return Result.error(e.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
			return Result.error("由于未知错误，操作失败");
		} finally {
			request.getSession().removeAttribute(session_cover);// 删除session中的封面路径
		}
		return Result.success();
	}
	
	/**
	 * 查找申请中的订单
	 * @param clbId 待审核的书的id
	 */
	@RequestMapping("findByClbId")
	@RequiresUser
	public Result findByClbId(Integer clbId) {
		CheckLoanableBook record = null;
		try {
			record = shareApplyService.findCheckLoanableBookById(clbId);
		} catch (NullPointerException e) {
			e.printStackTrace();
			return Result.error(e.getMessage());
		} catch (SystemErrorException e) {
			e.printStackTrace();
			return Result.error(e.getMessage());
		}catch (Exception e) {
			e.printStackTrace();
			return Result.error("由于未知错误，查找记录失败");
		}
		Result result = Result.success();
		result.put("record", record);
		return result;
	}
	
	/**
	 * 更新数据
	 * @param clbId 待审核的书的id
	 */
	@RequestMapping("update")
	@RequiresUser
	public Result update(@RequestBody CheckLoanableBook checkLoanableBook, HttpServletRequest request) {
		String fileNameSessionKey = "session_cover_" + ShiroUtils.getToken().getUuid();
		String session_cover = (String)request.getSession().getAttribute(fileNameSessionKey);
		if (session_cover != null) { // 如果有重新上传封面
			checkLoanableBook.setImagePath(session_cover);
		}
		checkLoanableBook.setUser(ShiroUtils.getToken()); // 设置用户
		try {
			shareApplyService.updateShare(checkLoanableBook);
		} catch (SystemErrorException e) {
			e.printStackTrace();
			return Result.error(e.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
			return Result.error("由于未知错误，操作失败");
		} finally {
			request.getSession().removeAttribute(session_cover);// 删除session中的封面路径
		}
		return Result.success();
	}
	
	/**
	 * 取消分享申请
	 * @param clbId 待审核图书的id
	 */
	@RequestMapping("cancel")
	@RequiresUser
	public Result cancel(Integer clbId) {
		try {
			shareApplyService.cancelApply(clbId);
		} catch (SystemErrorException e) {
			e.printStackTrace();
			return Result.error(e.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
			return Result.error("由于未知错误，操作失败");
		}
		return Result.success();
	}
	
	@RequestMapping("page")
	@RequiresUser
	public Result page(CheckLoanableBookQueryObject queryObject) {
		queryObject.setUuid(ShiroUtils.getToken().getUuid());
		Page page;
		try {
			page = shareApplyService.pageOfApply(queryObject);
		} catch (SystemErrorException e) {
			e.printStackTrace();
			return Result.error(e.getMessage());
		} 
		catch (Exception e) {
			e.printStackTrace();
			return Result.error("由于未知错误，查询数据失败");
		}
		Result result = Result.success();
		result.put("page", page);
		return result;
	}

	public void setShareApplyService(IShareApplyService shareApplyService) {
		this.shareApplyService = shareApplyService;
	}
	
	public void setFileUploadService(IFileUploadService fileUploadService) {
		this.fileUploadService = fileUploadService;
	}
	
}
