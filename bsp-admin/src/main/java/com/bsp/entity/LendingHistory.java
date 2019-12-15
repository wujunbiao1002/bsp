package com.bsp.entity;

import java.util.Date;

import org.apache.ibatis.type.Alias;

@Alias("LendingHistory")
public class LendingHistory extends BaseEntity {
	private static final long serialVersionUID = 1L;

	// 借出记录历史标识，来源于LendingRecord表的主键
    private Integer lhId;

    // 借阅人申请时间，创建订单
    private Date createTime;

    // 借出人同意申请时间
    private Date agreeTime;

    // 借出人送达运营商服务点时间
    private Date sendToTime;

    // 借阅人取走图书时间
    private Date takeAwayTime;

    // 借阅人预期还书时间
    private Date expectedReturnTime;

    // 借阅人实际还书时间
    private Date actualReturnTime;

    // 借出人取回图书时间
    private Date takeBackTime;

    // 1借阅人取消，2借出人拒绝申请，3申请超时借出人未同意，5借出人逾期未送达运营方，12借出人取回归还的图书，13借出人捐赠图书
    private Byte lhStruts;

    // 借阅人电话号码
    private String loanPhone;
    
    // 数量
    private Integer amount;

    // 借阅的图书
    private LoanableBook loanableBook;

    // 借阅人
    private User user;

    // 收到借出人送达图书的管理员
    private Administrator receiveAdmin;

    // 收到借阅人还图书的管理员
    private Administrator backAdmin;

    /**
     * 借出记录历史标识，来源于LendingRecord表的主键
     */
    public Integer getLhId() {
        return lhId;
    }

    /**
     * @param lhId 借出记录历史标识，来源于LendingRecord表的主键
     */
    public void setLhId(Integer lhId) {
        this.lhId = lhId;
    }

    /**
     * 借阅人申请时间，创建订单
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * @param createTime 借阅人申请时间，创建订单
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    /**
     * 借出人同意申请时间
     */
    public Date getAgreeTime() {
        return agreeTime;
    }

    /**
     * @param agreeTime 借出人同意申请时间
     */
    public void setAgreeTime(Date agreeTime) {
        this.agreeTime = agreeTime;
    }

    /**
     * 借出人送达运营商服务点时间
     */
    public Date getSendToTime() {
        return sendToTime;
    }

    /**
     * @param sendToTime 借出人送达运营商服务点时间
     */
    public void setSendToTime(Date sendToTime) {
        this.sendToTime = sendToTime;
    }
    
    public Integer getAmount() {
		return amount;
	}

	public void setAmount(Integer amount) {
		this.amount = amount;
	}

	/**
     * 借阅人取走图书时间
     */
    public Date getTakeAwayTime() {
        return takeAwayTime;
    }

    /**
     * @param takeAwayTime 借阅人取走图书时间
     */
    public void setTakeAwayTime(Date takeAwayTime) {
        this.takeAwayTime = takeAwayTime;
    }

    /**
     * 借阅人预期还书时间
     */
    public Date getExpectedReturnTime() {
        return expectedReturnTime;
    }

    /**
     * @param expectedReturnTime 借阅人预期还书时间
     */
    public void setExpectedReturnTime(Date expectedReturnTime) {
        this.expectedReturnTime = expectedReturnTime;
    }

    /**
     * 借阅人实际还书时间
     */
    public Date getActualReturnTime() {
        return actualReturnTime;
    }

    /**
     * @param actualReturnTime 借阅人实际还书时间
     */
    public void setActualReturnTime(Date actualReturnTime) {
        this.actualReturnTime = actualReturnTime;
    }

    /**
     * 借出人取回图书时间
     */
    public Date getTakeBackTime() {
        return takeBackTime;
    }

    /**
     * @param takeBackTime 借出人取回图书时间
     */
    public void setTakeBackTime(Date takeBackTime) {
        this.takeBackTime = takeBackTime;
    }

    /**
     * 1借阅人取消，2借出人拒绝申请，3申请超时借出人未同意，5借出人逾期未送达运营方，12借出人取回归还的图书，13借出人捐赠图书
     */
    public Byte getLhStruts() {
        return lhStruts;
    }

    /**
     * @param lhStruts 1借阅人取消，2借出人拒绝申请，3申请超时借出人未同意，5借出人逾期未送达运营方，12借出人取回归还的图书，13借出人捐赠图书
     */
    public void setLhStruts(Byte lhStruts) {
        this.lhStruts = lhStruts;
    }

    /**
     * 借阅人电话号码
     */
    public String getLoanPhone() {
        return loanPhone;
    }

    /**
     * @param loanPhone 借阅人电话号码
     */
    public void setLoanPhone(String loanPhone) {
        this.loanPhone = loanPhone == null ? null : loanPhone.trim();
    }

    /**
     * 借阅的图书
     */
    public LoanableBook getLoanableBook() {
        return loanableBook;
    }

    /**
     * @param loanableBook 借阅的图书
     */
    public void setLoanableBook(LoanableBook loanableBook) {
        this.loanableBook = loanableBook;
    }

    /**
     * 借阅人
     */
    public User getUser() {
        return user;
    }

    /**
     * @param user 借阅人
     */
    public void setUser(User user) {
        this.user = user;
    }

    /**
     * 收到借出人送达图书的管理员
     */
    public Administrator getReceiveAdmin() {
        return receiveAdmin;
    }

    /**
     * @param receiveAdmin 收到借出人送达图书的管理员
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