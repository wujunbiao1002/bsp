package com.bsp.entity;

import org.apache.ibatis.type.Alias;

@Alias("Administrator")
public class Administrator extends BaseEntity {
	private static final long serialVersionUID = 1L;

	// 管理员唯一标识
    private String aUuid;

    // 管理员登录账号
    private String aId;

    // 密码
    private String aPassword;

    // 运营方名称
    private String aName;

    // 联系电话
    private String aPhone;

    // 运营方地址
    private String aAddress;

    // 运营者描述
    private String aComments;

    // 是否可用，登录时需要判断，0没有禁用，1被禁用
    private Byte isDelete;
    
    /**
     * 判断是否为有效用户，是：true，否：false
     */
    public boolean isAvailible() {
    	return this.isDelete == 0 ? true : false;
    }
    
    /**
     * 管理员唯一标识
     */
    public String getaUuid() {
        return aUuid;
    }

    /**
     * @param aUuid 管理员唯一标识
     */
    public void setaUuid(String aUuid) {
        this.aUuid = aUuid == null ? null : aUuid.trim();
    }

    /**
     * 管理员登录账号
     */
    public String getaId() {
        return aId;
    }

    /**
     * @param aId 管理员登录账号
     */
    public void setaId(String aId) {
        this.aId = aId == null ? null : aId.trim();
    }

    /**
     * 密码
     */
    public String getaPassword() {
        return aPassword;
    }

    /**
     * @param aPassword 密码
     */
    public void setaPassword(String aPassword) {
        this.aPassword = aPassword == null ? null : aPassword.trim();
    }

    /**
     * 运营方名称
     */
    public String getaName() {
        return aName;
    }

    /**
     * @param aName 运营方名称
     */
    public void setaName(String aName) {
        this.aName = aName == null ? null : aName.trim();
    }

    /**
     * 联系电话
     */
    public String getaPhone() {
        return aPhone;
    }

    /**
     * @param aPhone 联系电话
     */
    public void setaPhone(String aPhone) {
        this.aPhone = aPhone == null ? null : aPhone.trim();
    }

    /**
     * 运营方地址
     */
    public String getaAddress() {
        return aAddress;
    }

    /**
     * @param aAddress 运营方地址
     */
    public void setaAddress(String aAddress) {
        this.aAddress = aAddress == null ? null : aAddress.trim();
    }

    /**
     * 运营者描述
     */
    public String getaComments() {
        return aComments;
    }

    /**
     * @param aComments 运营者描述
     */
    public void setaComments(String aComments) {
        this.aComments = aComments == null ? null : aComments.trim();
    }

    /**
     * 是否可用，登录时需要判断，0没有禁用，1被禁用
     */
    public Byte getIsDelete() {
        return isDelete;
    }

    /**
     * @param isDelete 是否可用，登录时需要判断，0没有禁用，1被禁用
     */
    public void setIsDelete(Byte isDelete) {
        this.isDelete = isDelete;
    }
}