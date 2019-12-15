package com.bsp.utils;

import java.util.HashMap;
import java.util.Map;

import com.bsp.enums.BussCode;


/**
 * 封装返回数据
 * @author hayate
 *
 */
public class Result extends HashMap<String, Object> {
	private static final long serialVersionUID = 1L;
	
	private static final String CODE = "code";
	private static final String MSG = "msg";
	
	public Result() {
		//code==0,表示正常
		put(CODE, 0);
	}
	
	/**
	 * 异常
	 */
	public static Result error() {
		return error(BussCode.ERR_UNKNOWN, "系统异常，请联系管理员");
	}
	
	/**
	 * 异常
	 * @param msg 文本消息
	 * @return
	 */
	public static Result error(String msg) {
		return error(BussCode.ERR_UNKNOWN, msg);
	}

	/**
	 * 指定异常code值，和异常描述
	 * @param code 业务异常，枚举
	 * @param msg 描述
	 */
	public static Result error(BussCode code, String msg) {
		Result r = new Result();
		r.put(CODE, code.getCode());
		r.put(MSG, msg);
		return r;
	}
	
	/**
	 * 正常
	 * @param msg 文本消息
	 */
	public static Result success(String msg) {
		Result r = new Result();
		r.put(MSG, msg);
		return r;
	}
	
	/**
	 * 正常
	 * @param map 需要返回的数据
	 */
	public static Result success(Map<String, Object> map) {
		Result r = new Result();
		r.putAll(map);
		return r;
	}
	
	/**
	 * 正常
	 */
	public static Result success() {
		return new Result();
	}

	/**
	 * 添加需要返回的数据
	 * @param key 键
	 * @param value 值
	 */
	@Override
	public Result put(String key, Object value) {
		super.put(key, value);
		return this;
	}
}
