package com.bsp.entity;

import java.util.Date;

import org.apache.ibatis.type.Alias;

@Alias("DemandBook")
public class DemandBook extends BaseEntity {
	private static final long serialVersionUID = 1L;

	// 需求图书标识，来源于check_demand_book表主键
    private Integer dbId;

    // 需求图书名称
    private String dbName;

    // 需求图书作者
    private String dbAuthor;

    //  需求图书的出版社
    private String dbPublishing;

    // 需求图书的ISBN
    private String isbn;

    // 需求图书时长
    private Integer dbDuratuin;

    // 需求图书的数量
    private Integer dbNumber;

    // 需求图书照片路径
    private String imagePath;

    // 备注
    private String dbComment;

    //  需求者的联系电话
    private String phone;

    //  开启图书需求的时间
    private Date openDemandTime;

    // 开启需求状态：0停止需求，1开始需求
    private Byte dbStatus;

    // 删除图书：0没有删除，1表示删除，默认为0
    private Byte isDelete;

    //  需求图书所属的二级分类
    private SecondaryClassification secondaryClassification;

    // 需求图书所属的用户
    private User user;

    /**
     * 需求图书标识，来源于check_demand_book表主键
     */
    public Integer getDbId() {
        return dbId;
    }

    /**
     * @param dbId 需求图书标识，来源于check_demand_book表主键
     */
    public void setDbId(Integer dbId) {
        this.dbId = dbId;
    }

    /**
     * 需求图书名称
     */
    public String getDbName() {
        return dbName;
    }

    /**
     * @param dbName 需求图书名称
     */
    public void setDbName(String dbName) {
        this.dbName = dbName == null ? null : dbName.trim();
    }

    /**
     * 需求图书作者
     */
    public String getDbAuthor() {
        return dbAuthor;
    }

    /**
     * @param dbAuthor 需求图书作者
     */
    public void setDbAuthor(String dbAuthor) {
        this.dbAuthor = dbAuthor == null ? null : dbAuthor.trim();
    }

    /**
     *  需求图书的出版社
     */
    public String getDbPublishing() {
        return dbPublishing;
    }

    /**
     * @param dbPublishing  需求图书的出版社
     */
    public void setDbPublishing(String dbPublishing) {
        this.dbPublishing = dbPublishing == null ? null : dbPublishing.trim();
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
     * 需求图书时长
     */
    public Integer getDbDuratuin() {
        return dbDuratuin;
    }

    /**
     * @param dbDuratuin 需求图书时长
     */
    public void setDbDuratuin(Integer dbDuratuin) {
        this.dbDuratuin = dbDuratuin;
    }

    /**
     * 需求图书的数量
     */
    public Integer getDbNumber() {
        return dbNumber;
    }

    /**
     * @param dbNumber 需求图书的数量
     */
    public void setDbNumber(Integer dbNumber) {
        this.dbNumber = dbNumber;
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
    public String getDbComment() {
        return dbComment;
    }

    /**
     * @param dbComment 备注
     */
    public void setDbComment(String dbComment) {
        this.dbComment = dbComment == null ? null : dbComment.trim();
    }

    /**
     *  需求者的联系电话
     */
    public String getPhone() {
        return phone;
    }

    /**
     * @param phone  需求者的联系电话
     */
    public void setPhone(String phone) {
        this.phone = phone == null ? null : phone.trim();
    }

    /**
     *  开启图书需求的时间
     */
    public Date getOpenDemandTime() {
        return openDemandTime;
    }

    /**
     * @param openDemandTime  开启图书需求的时间
     */
    public void setOpenDemandTime(Date openDemandTime) {
        this.openDemandTime = openDemandTime;
    }

    /**
     * 开启需求状态：0停止需求，1开始需求
     */
    public Byte getDbStatus() {
        return dbStatus;
    }

    /**
     * @param dbStatus 开启需求状态：0停止需求，1开始需求
     */
    public void setDbStatus(Byte dbStatus) {
        this.dbStatus = dbStatus;
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
     *  需求图书所属的二级分类
     */
    public SecondaryClassification getSecondaryClassification() {
        return secondaryClassification;
    }

    /**
     * @param secondaryClassification  需求图书所属的二级分类
     */
    public void setSecondaryClassification(SecondaryClassification secondaryClassification) {
        this.secondaryClassification = secondaryClassification;
    }

    /**
     * 需求图书所属的用户
     */
    public User getUser() {
        return user;
    }

    /**
     * @param user 需求图书所属的用户
     */
    public void setUser(User user) {
        this.user = user;
    }
}