package com.bsp.entity;

import org.apache.ibatis.type.Alias;

@Alias("CheckLoanableBook")
public class CheckLoanableBook extends BaseEntity {
	private static final long serialVersionUID = 1L;

	// 共享的图书标识，数字自增长
    private Integer clbId;

    // 共享图书作者
    private String clbAuthor;

    // 共享图书名称
    private String clbName;

    // 共享图书出版社
    private String clbPublishing;

    // 共享图书的ISBN
    private String isbn;

    // 图书可共享时长
    private Integer clbDuration;

    // 可共享图书数量
    private Integer clbNumber;

    // 共享图书照片路径
    private String imagePath;

    // 备注
    private String clbComment;

    // 共享者的联系电话
    private String phone;

    // 图书审核状态:0提交申请未审核转态，1申请失败返回原因
    private Byte clbStatus;

    // 审核人员填写，审核失败的原因
    private String failureCause;

    // 共享图书所属的二级分类
    private SecondaryClassification secondaryClassification;

    // 共享图书申请人
    private User user;

    /**
     * 共享的图书标识，数字自增长
     */
    public Integer getClbId() {
        return clbId;
    }

    /**
     * @param clbId 共享的图书标识，数字自增长
     */
    public void setClbId(Integer clbId) {
        this.clbId = clbId;
    }

    /**
     * 共享图书作者
     */
    public String getClbAuthor() {
        return clbAuthor;
    }

    /**
     * @param clbAuthor 共享图书作者
     */
    public void setClbAuthor(String clbAuthor) {
        this.clbAuthor = clbAuthor == null ? null : clbAuthor.trim();
    }

    /**
     * 共享图书名称
     */
    public String getClbName() {
        return clbName;
    }

    /**
     * @param clbName 共享图书名称
     */
    public void setClbName(String clbName) {
        this.clbName = clbName == null ? null : clbName.trim();
    }

    /**
     * 共享图书出版社
     */
    public String getClbPublishing() {
        return clbPublishing;
    }

    /**
     * @param clbPublishing 共享图书出版社
     */
    public void setClbPublishing(String clbPublishing) {
        this.clbPublishing = clbPublishing == null ? null : clbPublishing.trim();
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
     * 图书可共享时长
     */
    public Integer getClbDuration() {
        return clbDuration;
    }

    /**
     * @param clbDuration 图书可共享时长
     */
    public void setClbDuration(Integer clbDuration) {
        this.clbDuration = clbDuration;
    }

    /**
     * 可共享图书数量
     */
    public Integer getClbNumber() {
        return clbNumber;
    }

    /**
     * @param clbNumber 可共享图书数量
     */
    public void setClbNumber(Integer clbNumber) {
        this.clbNumber = clbNumber;
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
    public String getClbComment() {
        return clbComment;
    }

    /**
     * @param clbComment 备注
     */
    public void setClbComment(String clbComment) {
        this.clbComment = clbComment == null ? null : clbComment.trim();
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
     * 图书审核状态:0提交申请未审核转态，1申请失败返回原因
     */
    public Byte getClbStatus() {
        return clbStatus;
    }

    /**
     * @param clbStatus 图书审核状态:0提交申请未审核转态，1申请失败返回原因
     */
    public void setClbStatus(Byte clbStatus) {
        this.clbStatus = clbStatus;
    }

    /**
     * 审核人员填写，审核失败的原因
     */
    public String getFailureCause() {
        return failureCause;
    }

    /**
     * @param failureCause 审核人员填写，审核失败的原因
     */
    public void setFailureCause(String failureCause) {
        this.failureCause = failureCause == null ? null : failureCause.trim();
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
     * 共享图书申请人
     */
    public User getUser() {
        return user;
    }

    /**
     * @param user 共享图书申请人
     */
    public void setUser(User user) {
        this.user = user;
    }
}