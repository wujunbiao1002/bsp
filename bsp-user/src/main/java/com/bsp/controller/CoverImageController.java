package com.bsp.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.bsp.dao.MappingMapper;
import com.bsp.exceptions.SystemErrorException;

@Controller
public class CoverImageController {

	@Autowired
	private MappingMapper mappingMapper;

	private String sys = System.getProperty("os.name");
	private String dir = "/";
	
	/**
	 * 读取服务器中的封面图片
	 * @param imagePath
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	@RequestMapping("/cover")
	public void loadImage(String imagePath, HttpServletRequest request, HttpServletResponse response)
			throws IOException {
		if (StringUtils.containsIgnoreCase(sys, "linux")) { // Linux系统
			dir = mappingMapper.selectByPrimaryKey("path_image_linux").getmValue();
		} else if (StringUtils.containsIgnoreCase(sys, "windows")) { // Windows系统
			dir = mappingMapper.selectByPrimaryKey("path_image_win").getmValue();
		} else {
			throw new SystemErrorException("未知服务器类型，操作失败;");
		}
		String filePath = dir + imagePath;
		FileInputStream fis = null;
		
		File file = new File(filePath);
		if (!file.exists()) { // 如果文件不存在,读取备选封面
			String alt = request.getSession().getServletContext().getRealPath("/statics/img/cover_alt.png");
			file = new File(alt);
		}
		fis = new FileInputStream(file);
		response.setContentType("image/jpg"); // 设置返回的文件类型
		response.setHeader("Access-Control-Allow-Origin", "*");// 设置该图片允许跨域访问
		IOUtils.copy(fis, response.getOutputStream());
	}

	public void setMappingMapper(MappingMapper mappingMapper) {
		this.mappingMapper = mappingMapper;
	}

}
