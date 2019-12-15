package com.bsp.service.impl;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.bsp.dao.MappingMapper;
import com.bsp.exceptions.SystemErrorException;
import com.bsp.service.IFileUploadService;
import com.bsp.utils.CommonUtil;

@Service
public class FileUploadService implements IFileUploadService {
	
	@Autowired
	private MappingMapper mapingMapper;
	
	private String sys = System.getProperty("os.name");
	private String dir = ""; // 存储路径

	@Override
	public String uploadCover(MultipartFile multipartFile) {
		String[] allowTypes = {".jpg", ".jpeg",".gif",".bmp", ".png"};
		String originalFilename = multipartFile.getOriginalFilename();
		String suffix = originalFilename.substring(originalFilename.lastIndexOf(".")); // 获取文件后缀名
		Long size = multipartFile.getSize();
		if (!CommonUtil.targetInStringArray(true, suffix, allowTypes)) { // 检查是否为允许上传的文件格式
			throw new SystemErrorException("上传失败，不支持该类型文件");
		}
		if (1024*1024 < size) { //文件大于1M
			throw new SystemErrorException("上传失败，文件大小超过1M");
		}
		String newFileName = CommonUtil.createUUID().toString() + suffix;
		String relativePath = this.getRelativePathByFileName(newFileName); //生成二级目录
		try {
			if (StringUtils.containsIgnoreCase(sys, "linux")) { // Linux系统
				dir = mapingMapper.selectByPrimaryKey("path_image_linux").getmValue();
			} else if (StringUtils.containsIgnoreCase(sys, "windows")) { // Windows系统
				dir = mapingMapper.selectByPrimaryKey("path_image_win").getmValue();
			} else {
				throw new SystemErrorException("未知服务器类型，操作失败;");
			}
			String absPath = dir+relativePath; // 绝对路径
			InputStream inputStream = multipartFile.getInputStream();
			File abs = new File(absPath); 
			if (!abs.exists()) {
				abs.mkdirs();// 创建目录
			}
			FileOutputStream outputStream = new FileOutputStream(
					new File(absPath, newFileName));
			IOUtils.copy(inputStream, outputStream);
		} catch (IOException e) {
			e.printStackTrace();
			throw new SystemErrorException("服务器异常，上传失败");
		} catch (SystemErrorException e) {
			throw new SystemErrorException(e.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
			throw new SystemErrorException("服务器异常，上传失败");
		}
		return new File(relativePath+"/"+newFileName).toString();
	}
	
	/**
	 * 根据文件名生成二级目录 ，如文件名 hg4s45gh1xc4sd.jpg，返回“/h/g”
	 */
	private String getRelativePathByFileName(String fileName) {
		char[] arr = fileName.toCharArray();
		char firstDir = arr[0];
		char secondfDir = arr[1];
		String relativePath = "/";
		relativePath+=firstDir;
		relativePath+="/";
		relativePath+=secondfDir;
		return relativePath;
	}
	
	public void setMapingMapper(MappingMapper mapingMapper) {
		this.mapingMapper = mapingMapper;
	}
	
}
