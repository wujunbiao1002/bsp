package com.bsp.config;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.spring.LifecycleBeanPostProcessor;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.multipart.MultipartResolver;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.DefaultServletHandlerConfigurer;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter;

@Configuration
@EnableWebMvc  // <!-- 启用注解驱动的SpringMVC --> 等价于 <mvc:annotation-driven />
@ComponentScan(basePackages= {"com.bsp.controller"}) // 扫描Controller
public class WebConfig extends WebMvcConfigurerAdapter {
	
	/**
	 * 配置视图解析器
	 */
	@Bean
	public ViewResolver viewResolver() {
		InternalResourceViewResolver resolver = new InternalResourceViewResolver();
		resolver.setPrefix("/templates");
		resolver.setSuffix(".html");
		resolver.setExposeContextBeansAsAttributes(true); // 设置是否把所有在上下文中定义的bean作为request属性可公开访问。
		return resolver;
	}
	
	/**
	 * 注册拦截器
	 */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
	}

	/**
	 * 配置对静态资源的处理
	 */
	@Override
	public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer) {
		configurer.enable();
	}
	
	/**
	 * 使用CommonsMultipartResolver处理multipart请求
	 * 配置CommonsMultipartResolver的具体细节
	 * 临时文件保存位置
	 * 限制文件大小不超过2M
	 * 无法设置请求整体的最大容量
	 * 如果文件大小达到最大容量将会写入到临时文件路径中，默认值为0，也就是说所有上传文件都会写入磁盘中
	 * @throws IOException
	 */
	@Bean
	public MultipartResolver multipartResolver() throws IOException {
		CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver();
		multipartResolver.setMaxUploadSize(2097152);
		multipartResolver.setMaxInMemorySize(0);
		multipartResolver.setDefaultEncoding("UTF-8");
		return multipartResolver;
	}
	
	/**
	 * 配置JSON转换
	 */
	@Bean
	public FastJsonHttpMessageConverter fastJsonHttpMessageConverter() {
		FastJsonHttpMessageConverter converter = new FastJsonHttpMessageConverter();
		List<MediaType> supportedMediaTypes = new ArrayList<>(); 
	       supportedMediaTypes.add(MediaType.APPLICATION_JSON);
	       supportedMediaTypes.add(MediaType.APPLICATION_JSON_UTF8);
	       supportedMediaTypes.add(MediaType.APPLICATION_ATOM_XML);
	       supportedMediaTypes.add(MediaType.APPLICATION_FORM_URLENCODED);
	       supportedMediaTypes.add(MediaType.APPLICATION_OCTET_STREAM);
	       supportedMediaTypes.add(MediaType.APPLICATION_PDF);
	       supportedMediaTypes.add(MediaType.APPLICATION_RSS_XML);
	       supportedMediaTypes.add(MediaType.APPLICATION_XHTML_XML);
	       supportedMediaTypes.add(MediaType.APPLICATION_XML);
	       supportedMediaTypes.add(MediaType.IMAGE_GIF);
	       supportedMediaTypes.add(MediaType.IMAGE_JPEG);
	       supportedMediaTypes.add(MediaType.IMAGE_PNG);
	       supportedMediaTypes.add(MediaType.TEXT_EVENT_STREAM);
	       supportedMediaTypes.add(MediaType.TEXT_HTML);
	       supportedMediaTypes.add(MediaType.TEXT_MARKDOWN);
	       supportedMediaTypes.add(MediaType.TEXT_PLAIN);
	       supportedMediaTypes.add(MediaType.TEXT_XML); 
	       converter.setSupportedMediaTypes(supportedMediaTypes);
		return converter;
	}
	@Override
	public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
		converters.add(fastJsonHttpMessageConverter());
		super.configureMessageConverters(converters);
	}
	// ####################### Shiro About Start ########################
	@Bean("lifecycleBeanPostProcessor")
	public LifecycleBeanPostProcessor lifecycleBeanPostProcessor() {
		return new LifecycleBeanPostProcessor();
	}

	@Bean
	public DefaultAdvisorAutoProxyCreator defaultAdvisorAutoProxyCreator() {
		DefaultAdvisorAutoProxyCreator proxyCreator = new DefaultAdvisorAutoProxyCreator();
		proxyCreator.setProxyTargetClass(true);
		return proxyCreator;
	}

	@Bean
	public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(SecurityManager securityManager) {
		AuthorizationAttributeSourceAdvisor advisor = new AuthorizationAttributeSourceAdvisor();
		advisor.setSecurityManager(securityManager);
		return advisor;
	}
	// ######################## Shiro About End ########################
}
