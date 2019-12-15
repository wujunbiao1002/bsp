package com.bsp.entity;

import org.apache.ibatis.type.Alias;

@Alias("User")
public class User extends BaseEntity {
	private static final long serialVersionUID = 1L;

	// 用户唯一标识符号
    private String uuid;

    // 用户邮箱，作为用户登录账号
    private String mail;

    // 用户登录账号密码
    private String password;

    // 0没有禁用，1被禁用，默认为0
    private Byte isDelete;
    
    public boolean isAvailible() {
		return this.isDelete == 0 ? true : false;
	}
    
    public void lockOrDelete() {
    	this.isDelete = 1;
    }
    
    /**
     * 用户唯一标识符号
     */
    public String getUuid() {
        return uuid;
    }

    /**
     * @param uuid 用户唯一标识符号
     */
    public void setUuid(String uuid) {
        this.uuid = uuid == null ? null : uuid.trim();
    }

    /**
     * 用户邮箱，作为用户登录账号
     */
    public String getMail() {
        return mail;
    }

    /**
     * @param mail 用户邮箱，作为用户登录账号
     */
    public void setMail(String mail) {
        this.mail = mail == null ? null : mail.trim();
    }

    /**
     * 用户登录账号密码
     */
    public String getPassword() {
        return password;
    }

    /**
     * @param password 用户登录账号密码
     */
    public void setPassword(String password) {
        this.password = password == null ? null : password.trim();
    }

    /**
     * 0没有禁用，1被禁用，默认为0
     */
    public Byte getIsDelete() {
        return isDelete;
    }

    /**
     * @param isDelete 0没有禁用，1被禁用，默认为0
     */
    public void setIsDelete(Byte isDelete) {
        this.isDelete = isDelete;
    }
}