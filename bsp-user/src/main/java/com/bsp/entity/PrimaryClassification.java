package com.bsp.entity;

import org.apache.ibatis.type.Alias;

@Alias("PrimaryClassification")
public class PrimaryClassification extends BaseEntity {
	private static final long serialVersionUID = 1L;

	// 图书一级分类唯一标识，数字自增长
    private Integer pcId;

    // 一级分类名称
    private String pcName;

    // 是否删除分类 0表示没有删除，1表示删除，默认为0
    private Byte isDelete;

    /**
     * 图书一级分类唯一标识，数字自增长
     */
    public Integer getPcId() {
        return pcId;
    }

    /**
     * @param pcId 图书一级分类唯一标识，数字自增长
     */
    public void setPcId(Integer pcId) {
        this.pcId = pcId;
    }

    
    public String getPcName() {
        return pcName;
    }

    /**
     * @param isDelete 一级分类名称
     */
    public void setPcName(String pcName) {
        this.pcName = pcName == null ? null : pcName.trim();
    }

    /**
     * 一级分类名称
     */
    public Byte getIsDelete() {
        return isDelete;
    }

    /**
     * @param pcName 是否删除分类 0表示没有删除，1表示删除，默认为0
     */
    public void setIsDelete(Byte isDelete) {
        this.isDelete = isDelete;
    }
}