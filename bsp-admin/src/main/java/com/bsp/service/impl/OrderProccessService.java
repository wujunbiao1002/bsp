package com.bsp.service.impl;

import com.bsp.dao.*;
import com.bsp.entity.Administrator;
import com.bsp.entity.LendingRecord;
import com.bsp.entity.MailEntity;
import com.bsp.entity.News;
import com.bsp.exceptions.SystemErrorException;
import com.bsp.service.IOrderProccessService;
import com.bsp.utils.mail.MailSendUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

@Service
public class OrderProccessService implements IOrderProccessService {

    @Autowired
    private LendingRecordMapper LendingRecordMapper;
    @Autowired
    private LendingHistoryMapper LendingHistoryMapper;
    @Autowired
    private AdministratorMapper AdministratorMapper;
    @Autowired
    private MappingMapper mappingMapper;
    @Autowired
    private NewsMapper newsMapper;

    public void setLendingRecordMapper(LendingRecordMapper lendingRecordMapper) {
        LendingRecordMapper = lendingRecordMapper;
    }

    public void setLendingHistoryMapper(LendingHistoryMapper lendingHistoryMapper) {
        LendingHistoryMapper = lendingHistoryMapper;
    }

    public void setAdministratorMapper(AdministratorMapper administratorMapper) {
        AdministratorMapper = administratorMapper;
    }

    /**
     * @Author: 邬俊标
     * @Description:
     * @Date: 20:26 2018/10/18
     * @Param: lrId operator
     * @Return:
     **/
    @Override
    public void nextStep(Integer lrId, Administrator operator) {
        LendingRecord lendingRecord = LendingRecordMapper.selectByPrimaryKey(lrId);

        if (lendingRecord.getLrStruts() == 4) {
            /*
            借出方将图书送达运营点
             */
            lendingRecord.setLrStruts((byte) 6);
            lendingRecord.setSendToTime(new Date());
            lendingRecord.setReceiveAdmin(operator);
            LendingRecordMapper.updateByPrimaryKey(lendingRecord);

            String destMail = lendingRecord.getUser().getMail();
            // 生成邮件主题内容以及添加邮件到mailBeans
            String subject = "取书提醒"; // 邮件主题
            String content = "您申请借阅的《" + lendingRecord.getLoanableBook().getLbName() + "》图书，已送到运营点："
                    + mappingMapper.selectByPrimaryKey("transfer_station").getmValue() + ",请你尽快凭订单号到指定地点将图书取走。谢谢!!!"; // 邮件主体内容
            MailEntity mailBean = new MailEntity(destMail, subject, content);
            MailSendUtils mailSendUtils = new MailSendUtils();
            mailSendUtils.sendMail(mailBean.getToMail(), mailBean.getSubject(), mailBean.getContent());// 发送邮件

            // 发送账号通知信息
            News news = new News(subject, new Date(), lendingRecord.getUser(), content);
            insertNews(news);

        } else if (lendingRecord.getLrStruts() == 6) {
            /*
            图书被借阅者取走
             */
            Date now = new Date();

            Calendar ca = Calendar.getInstance();
            ca.add(Calendar.DATE, lendingRecord.getLoanableBook().getLbDuratuin());// num为增加的天数，可以改变的
            Date expectedReturnTime = ca.getTime();
//            String enddate = format.format(now);
//            System.out.println("增加天数以后的日期：" + enddate);

            lendingRecord.setLrStruts((byte) 8);
            lendingRecord.setTakeAwayTime(now);
            lendingRecord.setExpectedReturnTime(expectedReturnTime);
            LendingRecordMapper.updateByPrimaryKey(lendingRecord);

        } else if(lendingRecord.getLrStruts() == 7){
             /*
            借出方取回书籍
             */
            lendingRecord.setLrStruts((byte) 12);
            lendingRecord.setTakeBackTime(new Date());
            LendingRecordMapper.updateByPrimaryKey(lendingRecord);

        } else if (lendingRecord.getLrStruts() == 8 || lendingRecord.getLrStruts() == 9) {
            /*
            图书借阅者归还
             */
            lendingRecord.setLrStruts((byte) 10);
            lendingRecord.setActualReturnTime(new Date());
            lendingRecord.setBackAdmin(operator);
            LendingRecordMapper.updateByPrimaryKey(lendingRecord);

            String destMail = lendingRecord.getLoanableBook().getUser().getMail();
            // 生成邮件主题内容以及添加邮件到mailBeans
            String subject = "书籍取回提醒"; // 邮件主题
            String content = "您借出的《" + lendingRecord.getLoanableBook().getLbName()
                    + "》图书，已经归还给到"
                    + mappingMapper.selectByPrimaryKey("transfer_station").getmValue() + "，请你尽快凭订单号取回书籍。谢谢!!!"; // 邮件主体内容
            MailEntity mailBean = new MailEntity(destMail, subject, content);
            MailSendUtils mailSendUtils = new MailSendUtils();
            mailSendUtils.sendMail(mailBean.getToMail(), mailBean.getSubject(), mailBean.getContent());// 发送邮件

            // 发送账号通知信息
            News news = new News(subject, new Date(), lendingRecord.getLoanableBook().getUser(), content);
            insertNews(news);
        }else if (lendingRecord.getLrStruts() == 10) {
             /*
            借出方取回书籍
             */
            lendingRecord.setLrStruts((byte) 12);
            lendingRecord.setTakeBackTime(new Date());
            LendingRecordMapper.updateByPrimaryKey(lendingRecord);
        }
    }

    /**
     * 向news表中插入通知消息
     *
     * @param news
     */
    @Transactional
    public void insertNews(News news) {
        try {
            newsMapper.insert(news);
        } catch (Exception e) {
            e.printStackTrace();
            throw new SystemErrorException("操作失败，系统异常");
        }
    }
}
