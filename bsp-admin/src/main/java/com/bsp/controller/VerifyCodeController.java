package com.bsp.controller;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import cn.itcast.vcode.utils.VerifyCode;


@Controller
@Scope(value="prototype")
public class VerifyCodeController {
	
	@RequestMapping("/verifyCode")
	public void getVerifyCode(HttpServletRequest request, HttpServletResponse response) {
		/*
		 * 1. 创建验证码类
		 */
		VerifyCode vc = new VerifyCode();
		/*
		 * 2. 得到验证码图片
		 */
		BufferedImage image = vc.getImage();
		/*
		 * 3. 把图片上的文本保存到session中
		 */
		request.getSession().setAttribute("session_vcode", vc.getText().toLowerCase());
		/*
		 * 4. 把图片响应给客户端
		 */
		try {
			VerifyCode.output(image, response.getOutputStream());
		} catch (IOException e) {
			System.out.println("验证码发送失败");
			e.printStackTrace();
		}
	}
	
}
