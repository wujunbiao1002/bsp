package com.bsp.service;

import org.springframework.web.multipart.MultipartFile;

public interface IFileUploadService {
	
	/**
	 * 上传图书封面，生成uuid文件名，取uuid前两位字符决定存储路径
	 * @param multipartFile
	 */
	String uploadCover(MultipartFile multipartFile);
	
}
