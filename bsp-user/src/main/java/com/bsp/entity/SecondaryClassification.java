package com.bsp.entity;

import org.apache.ibatis.type.Alias;

@Alias("SecondaryClassification")
public class SecondaryClassification extends BaseEntity {
	private static final long serialVersionUID = 1L;

	// 图书二级分类唯一标识，数字增长值
    private Integer scId;

    // 二级分类名称
    private String scName;

    // 所属一级分类
    private PrimaryClassification primaryClassification;

    // 是否删除分类 0表示没有删除，1表示删除，默认为0
    private Byte isDelete;

    /**
     * 图书二级分类唯一标识，数字增长值
     */
    public Integer getScId() {
        return scId;
    }

    /**
     * @param scId 图书二级分类唯一标识，数字增长值
     */
    public void setScId(Integer scId) {
        this.scId = scId;
    }

    /**
     * 二级分类名称
     */
    public String getScName() {
        return scName;
    }

    /**
     * @param scName 二级分类名称
     */
    public void setScName(String scName) {
        this.scName = scName == null ? null : scName.trim();
    }

    /**
     * 所属一级分类
     */
    public PrimaryClassification getPrimaryClassification() {
        return primaryClassification;
    }

    /**
     * @param primaryClassification 所属一级分类
     */
    public void setPrimaryClassification(PrimaryClassification primaryClassification) {
        this.primaryClassification = primaryClassification;
    }

    /**
     * 是否删除分类 0表示没有删除，1表示删除，默认为0
     */
    public Byte getIsDelete() {
        return isDelete;
    }

    /**
     * @param isDelete 是否删除分类 0表示没有删除，1表示删除，默认为0
     */
    public void setIsDelete(Byte isDelete) {
        this.isDelete = isDelete;
    }
}