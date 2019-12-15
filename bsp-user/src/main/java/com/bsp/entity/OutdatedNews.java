package com.bsp.entity;

import java.util.Date;

import org.apache.ibatis.type.Alias;

@Alias("OutdatedNews")
public class OutdatedNews extends BaseEntity {
	private static final long serialVersionUID = 1L;

	// 通知消息记录ID，来源news表主键	
    private Integer nId;

    // 通知消息标题
    private String nSubject;

    // 产生消息的时间
    private Date newsTime;

    // 通知消息所属的用户 
    private User user;

    // 通知消息内容
    private String nContent;

    /**
     * 通知消息记录ID，来源news表主键	
     */
    public Integer getnId() {
        return nId;
    }

    /**
     * @param nId 通知消息记录ID，来源news表主键	
     */
    public void setnId(Integer nId) {
        this.nId = nId;
    }

    /**
     * 通知消息标题
     */
    public String getnSubject() {
        return nSubject;
    }

    /**
     * @param nSubject 通知消息标题
     */
    public void setnSubject(String nSubject) {
        this.nSubject = nSubject == null ? null : nSubject.trim();
    }

    /**
     * 产生消息的时间
     */
    public Date getNewsTime() {
        return newsTime;
    }

    /**
     * @param newsTime 产生消息的时间
     */
    public void setNewsTime(Date newsTime) {
        this.newsTime = newsTime;
    }

    /**
     * 通知消息所属的用户 
     */
    public User getUser() {
        return user;
    }

    /**
     * @param user 通知消息所属的用户 
     */
    public void setUser(User user) {
        this.user = user;
    }

    /**
     * 通知消息内容
     */
    public String getnContent() {
        return nContent;
    }

    /**
     * @param nContent 通知消息内容
     */
    public void setnContent(String nContent) {
        this.nContent = nContent == null ? null : nContent.trim();
    }
}