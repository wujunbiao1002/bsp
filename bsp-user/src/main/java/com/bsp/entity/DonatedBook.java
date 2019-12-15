package com.bsp.entity;

import java.util.Date;

public class DonatedBook extends BaseEntity {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	// 捐赠的图书标识，自增
    private Integer dobId;

    // 捐赠的图书名称
    private String dobName;

    // 捐赠的图书ISBN号
    private String isbn;

    // 捐赠的图书数量
    private Integer number;


    // 捐赠图书所属的二级分类
    private SecondaryClassification secondaryClassification;

    // 捐赠人
    private User user;

    // 捐赠来源
    private String source;

    // 捐赠人
    private String donor;

    // 捐赠时间
    private Date time;

    // 联系电话
    private String phone;

    /**
     * 捐赠的图书标识，自增
     */
    public Integer getDobId() {
        return dobId;
    }

    /**
     * @param dobId 捐赠的图书标识，自增
     */
    public void setDobId(Integer dobId) {
        this.dobId = dobId;
    }

    /**
     * 捐赠的图书名称
     */
    public String getDobName() {
        return dobName;
    }

    /**
     * @param dobName 捐赠的图书名称
     */
    public void setDobName(String dobName) {
        this.dobName = dobName == null ? null : dobName.trim();
    }

    /**
     * 捐赠的图书ISBN号
     */
    public String getIsbn() {
        return isbn;
    }

    /**
     * @param isbn 捐赠的图书ISBN号
     */
    public void setIsbn(String isbn) {
        this.isbn = isbn == null ? null : isbn.trim();
    }

    /**
     * 捐赠的图书数量
     */
    public Integer getNumber() {
        return number;
    }

    /**
     * @param number 捐赠的图书数量
     */
    public void setNumber(Integer number) {
        this.number = number;
    }

    /**
     * 捐赠图书所属的二级分类
     */
    public SecondaryClassification getSecondaryClassification() {
        return secondaryClassification;
    }

    /**
     * @param secondaryClassification 捐赠图书所属的二级分类
     */
    public void setSecondaryClassification(SecondaryClassification secondaryClassification) {
        this.secondaryClassification = secondaryClassification;
    }

    /**
     * 捐赠人账户
     */
    public User getUser() {
        return user;
    }

    /**
     * @param user 捐赠人账户
     */
    public void setUser(User user) {
        this.user = user;
    }

    /**
     * 捐赠来源
     */
    public String getSource() {
        return source;
    }

    /**
     * @param source 捐赠来源
     */
    public void setSource(String source) {
        this.source = source == null ? null : source.trim();
    }

    /**
     * 捐赠人
     */
    public String getDonor() {
        return donor;
    }

    /**
     * @param donor 捐赠人
     */
    public void setDonor(String donor) {
        this.donor = donor == null ? null : donor.trim();
    }

    /**
     * 捐赠时间
     */
    public Date getTime() {
        return time;
    }

    /**
     * @param time 捐赠时间
     */
    public void setTime(Date time) {
        this.time = time;
    }

    /**
     * 联系电话
     */
    public String getPhone() {
        return phone;
    }

    /**
     * @param phone 联系电话
     */
    public void setPhone(String phone) {
        this.phone = phone == null ? null : phone.trim();
    }
}