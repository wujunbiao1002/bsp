package com.bsp.entity;

import org.apache.ibatis.type.Alias;

@Alias("UserInfor")
public class UserInfor extends BaseEntity {
	private static final long serialVersionUID = 1L;

	// 用户唯一标识来源于user表主键
    private String uuid;

    // 用户的昵称
    private String uNickname;

    // 用户性别
    private String uSex;

    // 用户联系手机号码
    private String uPhone;

    // 用户联系地址
    private String uAddress;

    // 用户QQ号
    private String uQq;

    // 用户微信号
    private String uWechat;

    // 头像路径
    private String uHeadpath;

    // 用户个性签名 
    private String uSignature;

    /**
     * 用户唯一标识来源于user表主键
     */
    public String getUuid() {
        return uuid;
    }

    /**
     * @param uuid 用户唯一标识来源于user表主键
     */
    public void setUuid(String uuid) {
        this.uuid = uuid == null ? null : uuid.trim();
    }

    /**
     * 用户的昵称
     */
    public String getuNickname() {
        return uNickname;
    }

    /**
     * @param uNickname 用户的昵称
     */
    public void setuNickname(String uNickname) {
        this.uNickname = uNickname == null ? null : uNickname.trim();
    }

    /**
     * 用户性别
     */
    public String getuSex() {
        return uSex;
    }

    /**
     * @param uSex 用户性别
     */
    public void setuSex(String uSex) {
        this.uSex = uSex == null ? null : uSex.trim();
    }

    /**
     * 用户联系手机号码
     */
    public String getuPhone() {
        return uPhone;
    }

    /**
     * @param uPhone 用户联系手机号码
     */
    public void setuPhone(String uPhone) {
        this.uPhone = uPhone == null ? null : uPhone.trim();
    }

    /**
     * 用户联系地址
     */
    public String getuAddress() {
        return uAddress;
    }

    /**
     * @param uAddress 用户联系地址
     */
    public void setuAddress(String uAddress) {
        this.uAddress = uAddress == null ? null : uAddress.trim();
    }

    /**
     * 用户QQ号
     */
    public String getuQq() {
        return uQq;
    }

    /**
     * @param uQq 用户QQ号
     */
    public void setuQq(String uQq) {
        this.uQq = uQq == null ? null : uQq.trim();
    }

    /**
     * 用户微信号
     */
    public String getuWechat() {
        return uWechat;
    }

    /**
     * @param uWechat 用户微信号
     */
    public void setuWechat(String uWechat) {
        this.uWechat = uWechat == null ? null : uWechat.trim();
    }

    /**
     * 头像路径
     */
    public String getuHeadpath() {
        return uHeadpath;
    }

    /**
     * @param uHeadpath 头像路径
     */
    public void setuHeadpath(String uHeadpath) {
        this.uHeadpath = uHeadpath == null ? null : uHeadpath.trim();
    }

    /**
     * 用户个性签名 
     */
    public String getuSignature() {
        return uSignature;
    }

    /**
     * @param uSignature 用户个性签名 
     */
    public void setuSignature(String uSignature) {
        this.uSignature = uSignature == null ? null : uSignature.trim();
    }
}