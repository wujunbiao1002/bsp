package com.bsp.entity;

import java.util.Date;

import org.apache.ibatis.type.Alias;

@Alias("LoanableBook")
public class LoanableBook extends BaseEntity {
	private static final long serialVersionUID = 1L;

	// 共享的图书标识，来源CheckLoanableBook表主键
    private Integer lbId;

    // 共享图书名称
    private String lbName;

    // 共享图书作者
    private String lbAuthor;

    // 共享图书出版社
    private String lbPublishing;

    // 共享图书的ISBN
    private String isbn;

    // 共享图书可共享时长
    private Integer lbDuratuin;

    // 可共享图书数量
    private Integer lbNumber;
    
    // 剩余数量
    private Integer left;

	// 共享图书照片路径
    private String imagePath;

    // 备注
    private String lbComment;

    // 共享者的联系电话
    private String phone;

    // 开启图书共享的时间
    private Date openLoanTime;

    // 共享累计借出总数,初始为0
    private Integer totalLending;

    // 图书状态：0用户停止共享，1开始共享，2管理员下架，默认为1
    private Byte lbStatus;

    // 删除图书：0没有删除，1表示删除，默认为0
    private Byte isDelete;

    // 共享图书所属的二级分类
    private SecondaryClassification secondaryClassification;

    // 共享图书所属的用户
    private User user;
    

    public Integer getLeft() {
		return left;
	}

	public void setLeft(Integer left) {
		this.left = left;
	}
    
    /**
     * 共享的图书标识，来源CheckLoanableBook表主键
     */
    public Integer getLbId() {
        return lbId;
    }

    /**
     * @param lbId 共享的图书标识，来源CheckLoanableBook表主键
     */
    public void setLbId(Integer lbId) {
        this.lbId = lbId;
    }

    /**
     * 共享图书名称
     */
    public String getLbName() {
        return lbName;
    }

    /**
     * @param lbName 共享图书名称
     */
    public void setLbName(String lbName) {
        this.lbName = lbName == null ? null : lbName.trim();
    }

    /**
     * 共享图书作者
     */
    public String getLbAuthor() {
        return lbAuthor;
    }

    /**
     * @param lbAuthor 共享图书作者
     */
    public void setLbAuthor(String lbAuthor) {
        this.lbAuthor = lbAuthor == null ? null : lbAuthor.trim();
    }

    /**
     * 共享图书出版社
     */
    public String getLbPublishing() {
        return lbPublishing;
    }

    /**
     * @param lbPublishing 共享图书出版社
     */
    public void setLbPublishing(String lbPublishing) {
        this.lbPublishing = lbPublishing == null ? null : lbPublishing.trim();
    }

    /**
     * 共享图书的ISBN
     */
    public String getIsbn() {
        return isbn;
    }

    /**
     * @param isbn 共享图书的ISBN
     */
    public void setIsbn(String isbn) {
        this.isbn = isbn == null ? null : isbn.trim();
    }

    /**
     * 共享图书可共享时长
     */
    public Integer getLbDuratuin() {
        return lbDuratuin;
    }

    /**
     * @param lbDuratuin 共享图书可共享时长
     */
    public void setLbDuratuin(Integer lbDuratuin) {
        this.lbDuratuin = lbDuratuin;
    }

    /**
     * 可共享图书数量
     */
    public Integer getLbNumber() {
        return lbNumber;
    }

    /**
     * @param lbNumber 可共享图书数量
     */
    public void setLbNumber(Integer lbNumber) {
        this.lbNumber = lbNumber;
    }

    /**
     * 共享图书照片路径
     */
    public String getImagePath() {
        return imagePath;
    }

    /**
     * @param imagePath 共享图书照片路径
     */
    public void setImagePath(String imagePath) {
        this.imagePath = imagePath == null ? null : imagePath.trim();
    }

    /**
     * 备注
     */
    public String getLbComment() {
        return lbComment;
    }

    /**
     * @param lbComment 备注
     */
    public void setLbComment(String lbComment) {
        this.lbComment = lbComment == null ? null : lbComment.trim();
    }

    /**
     * 共享者的联系电话
     */
    public String getPhone() {
        return phone;
    }

    /**
     * @param phone 共享者的联系电话
     */
    public void setPhone(String phone) {
        this.phone = phone == null ? null : phone.trim();
    }

    /**
     * 开启图书共享的时间
     */
    public Date getOpenLoanTime() {
        return openLoanTime;
    }

    /**
     * @param openLoanTime 开启图书共享的时间
     */
    public void setOpenLoanTime(Date openLoanTime) {
        this.openLoanTime = openLoanTime;
    }

    /**
     * 共享累计借出总数,初始为0
     */
    public Integer getTotalLending() {
        return totalLending;
    }

    /**
     * @param totalLending 共享累计借出总数,初始为0
     */
    public void setTotalLending(Integer totalLending) {
        this.totalLending = totalLending;
    }

    /**
     * 图书状态：0用户停止共享，1开始共享，2管理员下架，默认为1
     */
    public Byte getLbStatus() {
        return lbStatus;
    }

    /**
     * @param lbStatus 图书状态：0用户停止共享，1开始共享，2管理员下架，默认为1
     */
    public void setLbStatus(Byte lbStatus) {
        this.lbStatus = lbStatus;
    }

    /**
     * 删除图书：0没有删除，1表示删除，默认为0
     */
    public Byte getIsDelete() {
        return isDelete;
    }

    /**
     * @param isDelete 删除图书：0没有删除，1表示删除，默认为0
     */
    public void setIsDelete(Byte isDelete) {
        this.isDelete = isDelete;
    }

    /**
     * 共享图书所属的二级分类
     */
    public SecondaryClassification getSecondaryClassification() {
        return secondaryClassification;
    }

    /**
     * @param secondaryClassification 共享图书所属的二级分类
     */
    public void setSecondaryClassification(SecondaryClassification secondaryClassification) {
        this.secondaryClassification = secondaryClassification;
    }

    /**
     * 共享图书所属的用户
     */
    public User getUser() {
        return user;
    }

    /**
     * @param user 共享图书所属的用户
     */
    public void setUser(User user) {
        this.user = user;
    }
}