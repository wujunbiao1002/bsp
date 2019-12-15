package com.bsp.entity;

import java.util.Date;

import org.apache.ibatis.type.Alias;

@Alias("RespondRecord")
public class RespondRecord extends BaseEntity {
	private static final long serialVersionUID = 1L;

	// 响应记录标识，数字自增长
    private Integer rrId;

    // 需求者响应时间
    private Date respondTime;

    // 响应者送达运营商服务点时间
    private Date sendToTime;

    // 需求者取走图书时间
    private Date takeAwayTime;

    // 需求者预期还书时间
    private Date expectedReturnTime;

    // 需求者实际还书时间
    private Date actualReturnTime;

    // 响应者取回图书时间
    private Date takeBackTime;

    // 0需求被响应，4响应者送达图书到运营方，5需求者逾期未取书，6需求者取走图书，7需求者逾期未还，8需求者还书，9响应者逾期未取回
    private Byte rrStruts;

    // 响应者电话号码
    private String respondPhone;

    // 需求的图书
    private DemandBook demandBook;

    // 响应者
    private User user;

    // 收到借出人送到图书的管理员
    private Administrator receiveAdmin;

    // 收到借阅人还图书的管理员
    private Administrator backAdmin;

    /**
     * 响应记录标识，数字自增长
     */
    public Integer getRrId() {
        return rrId;
    }

    /**
     * @param rrId 响应记录标识，数字自增长
     */
    public void setRrId(Integer rrId) {
        this.rrId = rrId;
    }

    /**
     * 需求者响应时间
     */
    public Date getRespondTime() {
        return respondTime;
    }

    /**
     * @param respondTime 需求者响应时间
     */
    public void setRespondTime(Date respondTime) {
        this.respondTime = respondTime;
    }

    /**
     * 响应者送达运营商服务点时间
     */
    public Date getSendToTime() {
        return sendToTime;
    }

    /**
     * @param sendToTime 响应者送达运营商服务点时间
     */
    public void setSendToTime(Date sendToTime) {
        this.sendToTime = sendToTime;
    }

    /**
     * 需求者取走图书时间
     */
    public Date getTakeAwayTime() {
        return takeAwayTime;
    }

    /**
     * @param takeAwayTime 需求者取走图书时间
     */
    public void setTakeAwayTime(Date takeAwayTime) {
        this.takeAwayTime = takeAwayTime;
    }

    /**
     * 需求者预期还书时间
     */
    public Date getExpectedReturnTime() {
        return expectedReturnTime;
    }

    /**
     * @param expectedReturnTime 需求者预期还书时间
     */
    public void setExpectedReturnTime(Date expectedReturnTime) {
        this.expectedReturnTime = expectedReturnTime;
    }

    /**
     * 需求者实际还书时间
     */
    public Date getActualReturnTime() {
        return actualReturnTime;
    }

    /**
     * @param actualReturnTime 需求者实际还书时间
     */
    public void setActualReturnTime(Date actualReturnTime) {
        this.actualReturnTime = actualReturnTime;
    }

    /**
     * 响应者取回图书时间
     */
    public Date getTakeBackTime() {
        return takeBackTime;
    }

    /**
     * @param takeBackTime 响应者取回图书时间
     */
    public void setTakeBackTime(Date takeBackTime) {
        this.takeBackTime = takeBackTime;
    }

    /**
     * 0需求被响应，4响应者送达图书到运营方，5需求者逾期未取书，6需求者取走图书，7需求者逾期未还，8需求者还书，9响应者逾期未取回
     */
    public Byte getRrStruts() {
        return rrStruts;
    }

    /**
     * @param rrStruts 0需求被响应，4响应者送达图书到运营方，5需求者逾期未取书，6需求者取走图书，7需求者逾期未还，8需求者还书，9响应者逾期未取回
     */
    public void setRrStruts(Byte rrStruts) {
        this.rrStruts = rrStruts;
    }

    /**
     * 响应者电话号码
     */
    public String getRespondPhone() {
        return respondPhone;
    }

    /**
     * @param respondPhone 响应者电话号码
     */
    public void setRespondPhone(String respondPhone) {
        this.respondPhone = respondPhone == null ? null : respondPhone.trim();
    }

    /**
     * 需求的图书
     */
    public DemandBook getDemandBook() {
        return demandBook;
    }

    /**
     * @param demandBook 需求的图书
     */
    public void setDemandBook(DemandBook demandBook) {
        this.demandBook = demandBook;
    }

    /**
     * 响应者
     */
    public User getUser() {
        return user;
    }

    /**
     * @param user 响应者
     */
    public void setUser(User user) {
        this.user = user;
    }

    /**
     * 收到借出人送到图书的管理员
     */
    public Administrator getReceiveAdmin() {
        return receiveAdmin;
    }

    /**
     * @param receiveAdmin 收到借出人送到图书的管理员
     */
    public void setReceiveAdmin(Administrator receiveAdmin) {
        this.receiveAdmin = receiveAdmin;
    }

    /**
     * 收到借阅人还图书的管理员
     */
    public Administrator getBackAdmin() {
        return backAdmin;
    }

    /**
     * @param backAdmin 收到借阅人还图书的管理员
     */
    public void setBackAdmin(Administrator backAdmin) {
        this.backAdmin = backAdmin;
    }
}