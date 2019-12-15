package com.bsp.enums;

/**
 * 业务异常
 * @author hayate
 *
 */
public enum BussCode {
	/**
	 * 未登录
	 */
	NOT_LOGIN(401),
	 /**
     * 未授权
     */
    NOT_AUTHORIZATION(403),
    /**
     * 未知异常，请联系管理员
     */
    ERR_UNKNOWN(500),
    /**
     * 修改信息失败
     */
    MODIFY_ERR(1003)
    ;
	
	/**
	 * 异常代码
	 */
    private Integer code;

    BussCode(Integer code) {
        this.code = code;
    }
    /**
     * 获取异常代码
     * @return
     */
    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

}
