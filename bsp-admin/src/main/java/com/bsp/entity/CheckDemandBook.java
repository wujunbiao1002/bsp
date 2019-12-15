package com.bsp.entity;

import org.apache.ibatis.type.Alias;

@Alias("CheckDemandBook")
public class CheckDemandBook extends BaseEntity {
	private static final long serialVersionUID = 1L;

	// 需求图书标识，数字自增长
    private Integer cdbId;

    // 需求图书名称
    private String cdbName;

    // 需求图书作者
    private String cdbAuthor;

    // 需求图书出版社
    private String cdbPublishing;

    // 需求图书的ISBN
    private String isbn;

    // 需求图书需求时长
    private Integer cdbDuration;

    // 需要图书数量
    private Integer cdbNumber;

    // 需求图书照片路径
    private String imagePath;

    // 备注
    private String cdbComment;

    // 需求者联系电话
    private String phone;

    // 图书审核状态:0提交申请未审核转态，1申请失败返回原因
    private Byte cdbStatus;

    // 审核人员填写，审核失败的原因
    private String failureCause;

    // 需求图书所属的二级分类
    private SecondaryClassification secondaryClassification;

    // 需求图书申请人
    private User user;

    /**
     * 需求图书标识，数字自增长
     */
    public Integer getCdbId() {
        return cdbId;
    }

    /**
     * @param cdbId 需求图书标识，数字自增长
     */
    public void setCdbId(Integer cdbId) {
        this.cdbId = cdbId;
    }

    /**
     * 需求图书名称
     */
    public String getCdbName() {
        return cdbName;
    }

    /**
     * @param cdbName 需求图书名称
     */
    public void setCdbName(String cdbName) {
        this.cdbName = cdbName == null ? null : cdbName.trim();
    }

    /**
     * 需求图书作者
     */
    public String getCdbAuthor() {
        return cdbAuthor;
    }

    /**
     * @param cdbAuthor 需求图书作者
     */
    public void setCdbAuthor(String cdbAuthor) {
        this.cdbAuthor = cdbAuthor == null ? null : cdbAuthor.trim();
    }

    /**
     * 需求图书出版社
     */
    public String getCdbPublishing() {
        return cdbPublishing;
    }

    /**
     * @param cdbPublishing 需求图书出版社
     */
    public void setCdbPublishing(String cdbPublishing) {
        this.cdbPublishing = cdbPublishing == null ? null : cdbPublishing.trim();
    }

    /**
     * 需求图书的ISBN
     */
    public String getIsbn() {
        return isbn;
    }

    /**
     * @param isbn 需求图书的ISBN
     */
    public void setIsbn(String isbn) {
        this.isbn = isbn == null ? null : isbn.trim();
    }

    /**
     * 需求图书需求时长
     */
    public Integer getCdbDuration() {
        return cdbDuration;
    }

    /**
     * @param cdbDuration 需求图书需求时长
     */
    public void setCdbDuration(Integer cdbDuration) {
        this.cdbDuration = cdbDuration;
    }

    /**
     * 需要图书数量
     */
    public Integer getCdbNumber() {
        return cdbNumber;
    }

    /**
     * @param cdbNumber 需要图书数量
     */
    public void setCdbNumber(Integer cdbNumber) {
        this.cdbNumber = cdbNumber;
    }

    /**
     * 需求图书照片路径
     */
    public String getImagePath() {
        return imagePath;
    }

    /**
     * @param imagePath 需求图书照片路径
     */
    public void setImagePath(String imagePath) {
        this.imagePath = imagePath == null ? null : imagePath.trim();
    }

    /**
     * 备注
     */
    public String getCdbComment() {
        return cdbComment;
    }

    /**
     * @param cdbComment 备注
     */
    public void setCdbComment(String cdbComment) {
        this.cdbComment = cdbComment == null ? null : cdbComment.trim();
    }

    /**
     * 需求者联系电话
     */
    public String getPhone() {
        return phone;
    }

    /**
     * @param phone 需求者联系电话
     */
    public void setPhone(String phone) {
        this.phone = phone == null ? null : phone.trim();
    }

    /**
     * 图书审核状态:0提交申请未审核转态，1申请失败返回原因
     */
    public Byte getCdbStatus() {
        return cdbStatus;
    }

    /**
     * @param cdbStatus 图书审核状态:0提交申请未审核转态，1申请失败返回原因
     */
    public void setCdbStatus(Byte cdbStatus) {
        this.cdbStatus = cdbStatus;
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
     * 需求图书所属的二级分类
     */
    public SecondaryClassification getSecondaryClassification() {
        return secondaryClassification;
    }

    /**
     * @param secondaryClassification 需求图书所属的二级分类
     */
    public void setSecondaryClassification(SecondaryClassification secondaryClassification) {
        this.secondaryClassification = secondaryClassification;
    }

    /**
     * 需求图书申请人
     */
    public User getUser() {
        return user;
    }

    /**
     * @param user 需求图书申请人
     */
    public void setUser(User user) {
        this.user = user;
    }
}