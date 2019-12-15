package com.bsp.entity;

import org.apache.ibatis.type.Alias;

@Alias("Mapping")
public class Mapping extends BaseEntity {
	private static final long serialVersionUID = 1L;

	// 主键，键
    private String mapkey;

    //  值
    private String mValue;

    /**
     * 主键，键
     */
    public String getMapkey() {
        return mapkey;
    }

    /**
     * @param mapkey 主键，键
     */
    public void setMapkey(String mapkey) {
        this.mapkey = mapkey == null ? null : mapkey.trim();
    }

    /**
     *  值
     */
    public String getmValue() {
        return mValue;
    }

    /**
     * @param mValue  值
     */
    public void setmValue(String mValue) {
        this.mValue = mValue == null ? null : mValue.trim();
    }
}