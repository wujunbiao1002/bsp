package com.bsp.service.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bsp.dao.LendingHistoryMapper;
import com.bsp.dao.LendingRecordMapper;
import com.bsp.dao.MappingMapper;
import com.bsp.dao.NewsMapper;
import com.bsp.entity.Administrator;
import com.bsp.entity.LendingHistory;
import com.bsp.entity.LendingRecord;
import com.bsp.entity.MailEntity;
import com.bsp.entity.News;
import com.bsp.exceptions.SendEmailException;
import com.bsp.exceptions.SystemErrorException;
import com.bsp.service.ILendingTimerTaskService;
import com.bsp.utils.mail.MailSendUtils;

@Service
public class LendingTimerTaskService implements ILendingTimerTaskService {
	@Autowired
	private LendingRecordMapper lendingRecordMapper;
	@Autowired
	private MappingMapper mappingMapper;
	@Autowired
	private NewsMapper newsMapper;
	@Autowired
	private LendingHistoryMapper lendingHistoryMapper;
	private int daySeconds = 3600 * 24; // 一天的秒数
	private int intervalUpdateSeconds = 10800; // 每天间隔3小时更新一次

	public void setLendingRecordMapper(LendingRecordMapper lendingRecordMapper) {
		this.lendingRecordMapper = lendingRecordMapper;
	}

	public void setMappingMapper(MappingMapper mappingMapper) {
		this.mappingMapper = mappingMapper;
	}

	public void setNewsMapper(NewsMapper newsMapper) {
		this.newsMapper = newsMapper;
	}

	public void setLendingHistoryMapper(LendingHistoryMapper lendingHistoryMapper) {
		this.lendingHistoryMapper = lendingHistoryMapper;
	}

	/**
	 * 处理借出记录中的各种记录是否超时以及消息提醒
	 */
	@Override
	public void checkLendingRecordAllOvertime() {
		// 现在的服务器的时间
		Date nowDate = new Date();
		// 借出人超时未同意天数
		int overtimeAgreeApply = Integer
				.valueOf((mappingMapper.selectByPrimaryKey("overtime_agree_apply").getmValue()));
		// 借出人超时未将图书送到运营点的天数
		int overtimeBringToTransferStation = Integer
				.valueOf((mappingMapper.selectByPrimaryKey("overtime_bring_to_transfer_station").getmValue()));
		// 借阅者超时未将图书取走的天数
		int overtimeTakeFromTransferStation = Integer
				.valueOf((mappingMapper.selectByPrimaryKey("overtime_take_from_transfer_station").getmValue()));
		// 借出人超时未将图书取回的天数
		int overtimeTakeBackFromTransferStation = Integer
				.valueOf((mappingMapper.selectByPrimaryKey("overtime_take_back_from_transfer_station").getmValue()));
		// 数据库所有的状态为0、4、6、7、8、10借出记录
		List<LendingRecord> lendingRecords = lendingRecordMapper.selectBylrStrutsLendingRecord();
		List<MailEntity> mailBeans = new ArrayList<MailEntity>(); // 批量存放邮件
		List<News> newss = new ArrayList<News>(); // 批量存放通知消息
		// 遍历查询
		for (LendingRecord lendingRecord : lendingRecords) {
			if (lendingRecord.getLrStruts() == 0) {
				isOvertimeAgreeApply(lendingRecord, mailBeans, newss, nowDate, overtimeAgreeApply);
			} else if (lendingRecord.getLrStruts() == 4) {
				isOvertimeBringToTransferStation(lendingRecord, mailBeans, newss, nowDate,
						overtimeBringToTransferStation);
			} else if (lendingRecord.getLrStruts() == 6) {
				isOvertimeTakeFromTransferStation(lendingRecord, mailBeans, newss, nowDate,
						overtimeTakeFromTransferStation);
			} else if (lendingRecord.getLrStruts() == 7) {
				isOvertimeTakeBackFromTransferStation(lendingRecord, mailBeans, newss, nowDate,
						overtimeTakeBackFromTransferStation, overtimeTakeFromTransferStation);
			} else if (lendingRecord.getLrStruts() == 8) {
				isOvertimeReturnToTransferStation(lendingRecord, mailBeans, newss, nowDate);
			} else if (lendingRecord.getLrStruts() == 10) {
				isOvertimeTakeBackFromTransferStation2(lendingRecord, mailBeans, newss, nowDate,
						overtimeTakeBackFromTransferStation);
			}
		}
		// 批量发送邮件
		for (MailEntity mailBean : mailBeans) {
			MailSendUtils mailSendUtils = new MailSendUtils();
			try {
				mailSendUtils.sendMail(mailBean.getToMail(), mailBean.getSubject(), mailBean.getContent());// 发送邮件
			} catch (Exception e) {
				e.printStackTrace();
				throw new SendEmailException("发送借阅申请失效提醒失败，系统异常");
			}
		}
		// 批量发送通知消息
		for (News news : newss) {
			insertNews(news);
		}
	}

	/**
	 * 状态为10，借阅者已经归还图书，等待借出人取书，判断借出人是否逾期取书。逾期10-11
	 * @param lendingRecord
	 * @param mailBeans
	 * @param newss
	 * @param nowDate
	 * @param overtimeTakeBackFromTransferStation
	 */
	private void isOvertimeTakeBackFromTransferStation2(LendingRecord lendingRecord, List<MailEntity> mailBeans,
			List<News> newss, Date nowDate, int overtimeTakeBackFromTransferStation) {
		// 借阅者真实的还书时间
		Date actualReturnTime = lendingRecord.getActualReturnTime();

		// 相差的时间秒数
		long seconds = (nowDate.getTime() - actualReturnTime.getTime()) / 1000;

		if (seconds >= (overtimeTakeBackFromTransferStation - 2) * daySeconds
				&& seconds < ((overtimeTakeBackFromTransferStation - 2) * daySeconds
						+ intervalUpdateSeconds)) {
			/*
			 * 还有两天时间就超时，但借阅人没有把图书取走。 发送通知信息到借出人账号和发送邮件提醒。
			 */
			String destMail = lendingRecord.getLoanableBook().getUser().getMail();

			// 生成邮件主题内容以及添加邮件到mailBeans
			String subject = "书籍取回提醒"; // 邮件主题
			String content = "您同意借出的《" + lendingRecord.getLoanableBook().getLbName()
					+ "》图书，你已经过了" + (overtimeTakeBackFromTransferStation - 2) + "天没有前往"
					+ mappingMapper.selectByPrimaryKey("transfer_station").getmValue() + "取回图书，请你尽快凭订单号将图书取回。谢谢!!!"; // 邮件主体内容
			MailEntity mailBean = new MailEntity(destMail, subject, content);
			mailBeans.add(mailBean);

			// 发送账号通知信息
			News news = new News(subject, new Date(), lendingRecord.getLoanableBook().getUser(), content);
			newss.add(news);
		} else if (seconds >= (overtimeTakeBackFromTransferStation - 1) * daySeconds
				&& seconds < ((overtimeTakeBackFromTransferStation - 1) * daySeconds
						+ intervalUpdateSeconds)) {
			/*
			 * 还有一天时间就超时，但借阅人没有把图书取走。 发送通知信息到借出人账号和发送邮件提醒。
			 */
			String destMail = lendingRecord.getLoanableBook().getUser().getMail();

			// 生成邮件主题内容以及添加邮件到mailBeans
			String subject = "书籍取回提醒"; // 邮件主题
			String content = "您同意借出的《" + lendingRecord.getLoanableBook().getLbName()
					+ "》图书，你已经过了" + (overtimeTakeBackFromTransferStation - 1) + "天没有前往"
					+ mappingMapper.selectByPrimaryKey("transfer_station").getmValue() + "取回图书，请你尽快凭订单号将图书取回。谢谢!!!"; // 邮件主体内容
			MailEntity mailBean = new MailEntity(destMail, subject, content);
			mailBeans.add(mailBean);

			// 发送账号通知信息
			News news = new News(subject, new Date(), lendingRecord.getLoanableBook().getUser(), content);
			newss.add(news);
		}
		//else if (seconds >= overtimeTakeBackFromTransferStation * daySeconds) {
//			/*
//			 * 借出人超时没有把图书取走。 并修改状态为11表示借出人超时未取走图书,显示到管理员端人工处理
//			 */
//			// 把该条记录lending_record表中，并修改状态为11表示借出人取回超时
//			lendingRecord.setLrStruts((byte) 11);
//			lendingRecordMapper.updateByPrimaryKey(lendingRecord);
//		}

	}

	/**
	 * 状态为8，借阅者已经从运营点取走图书，等待还书，判断借阅者是否逾期还书。逾期8-9
	 * 
	 * @param lendingRecord
	 * @param mailBeans
	 * @param newss
	 * @param nowDate
	 */
	private void isOvertimeReturnToTransferStation(LendingRecord lendingRecord, List<MailEntity> mailBeans,
			List<News> newss, Date nowDate) {
		// 预计归还时间
		Date expectedReturnTime = lendingRecord.getExpectedReturnTime();
		// 相差的时间秒数
		long seconds = (expectedReturnTime.getTime() - nowDate.getTime()) / 1000;
		if (seconds >= daySeconds * 2 - intervalUpdateSeconds && seconds < 2 * daySeconds) {
			/*
			 * 还有两天时间就逾期归还图书，但借阅者没有把图书还回运营点。 发送通知信息到借阅者账号和发送邮件提醒。
			 */
			String destMail = lendingRecord.getUser().getMail();

			// 生成邮件主题内容以及添加邮件到mailBeans
			String subject = "还书提醒"; // 邮件主题
			String content = "您借阅的《" + lendingRecord.getLoanableBook().getLbName()
					+ "》图书，距离还书逾期还有两天，请你尽快凭订单号将图书还回到取书地点。谢谢!!!"; // 邮件主体内容
			MailEntity mailBean = new MailEntity(destMail, subject, content);
			mailBeans.add(mailBean);

			// 发送账号通知信息
			News news = new News(subject, new Date(), lendingRecord.getUser(), content);
			newss.add(news);
		} else if (seconds >= daySeconds - intervalUpdateSeconds && seconds < daySeconds) {
			/*
			 * 还有一天时间就逾期归还图书，但借阅者没有把图书还回运营点。 发送通知信息到借阅人账号和发送邮件提醒。
			 */
			String destMail = lendingRecord.getUser().getMail();
			// 生成邮件主题内容以及添加邮件到mailBeans
			String subject = "还书提醒"; // 邮件主题
			String content = "您借阅的《" + lendingRecord.getLoanableBook().getLbName()
					+ "》图书，距离还书逾期还有一天，请你尽快凭订单号将图书还回到取书地点。谢谢!!!"; // 邮件主体内容
			MailEntity mailBean = new MailEntity(destMail, subject, content);
			mailBeans.add(mailBean);

			// 发送账号通知信息
			News news = new News(subject, new Date(), lendingRecord.getUser(), content);
			newss.add(news);
		} else if (seconds <=  intervalUpdateSeconds) {
			/*
			 * 借阅者超时没有归还图书。 并修改状态为9表示借阅者逾期还书,显示到管理员端人工处理
			 */
			String destMail = lendingRecord.getUser().getMail();
			// 生成邮件主题内容以及添加邮件到mailBeans
			String subject = "还书提醒"; // 邮件主题
			String content = "您借阅的《" + lendingRecord.getLoanableBook().getLbName()
					+ "》图书，已经超过预期的还书时间，请你尽快凭订单号将图书还回到取书地点。谢谢!!!"; // 邮件主体内容
			MailEntity mailBean = new MailEntity(destMail, subject, content);
			mailBeans.add(mailBean);

			// 发送账号通知信息
			News news = new News(subject, new Date(), lendingRecord.getUser(), content);
			newss.add(news);
			// 把该条记录lending_record表中，并修改状态为9表示借阅者逾期未还书
			lendingRecord.setLrStruts((byte) 9);
			lendingRecordMapper.updateByPrimaryKey(lendingRecord);
		}

	}

	/**
	 * 状态为7，借阅者逾期未取书，等待借出人取回书籍，判断借出人是否逾期未从运营点取回书籍。
	 * 
	 * @param lendingRecord
	 * @param mailBeans
	 * @param newss
	 * @param nowDate
	 * @param overtimeTakeBackFromTransferStation
	 * @param overtimeTakeFromTransferStation
	 */
	private void isOvertimeTakeBackFromTransferStation(LendingRecord lendingRecord, List<MailEntity> mailBeans,
			List<News> newss, Date nowDate, int overtimeTakeBackFromTransferStation,
			int overtimeTakeFromTransferStation) {
		// 借出人把图书送达运营点的时间
		Date sendToTime = lendingRecord.getSendToTime();

		// 相差的时间秒数
		long seconds = (nowDate.getTime() - sendToTime.getTime()) / 1000;

		if (seconds >= (overtimeTakeFromTransferStation + overtimeTakeBackFromTransferStation - 2) * daySeconds
				&& seconds < ((overtimeTakeFromTransferStation + overtimeTakeBackFromTransferStation - 2) * daySeconds
						+ intervalUpdateSeconds)) {
			/*
			 * 还有两天时间就超时，但借阅人没有把图书取走。 发送通知信息到借出人账号和发送邮件提醒。
			 */
			String destMail = lendingRecord.getLoanableBook().getUser().getMail();

			// 生成邮件主题内容以及添加邮件到mailBeans
			String subject = "书籍取回提醒"; // 邮件主题
			String content = "您同意借出的《" + lendingRecord.getLoanableBook().getLbName()
					+ "》图书，由于对方超时没有将图书取走，系统已经自动取消此次借阅，你已经过了" + (overtimeTakeBackFromTransferStation - 2) + "天没有前往"
					+ mappingMapper.selectByPrimaryKey("transfer_station").getmValue() + "取回图书，请你尽快凭订单号将图书取回。谢谢!!!"; // 邮件主体内容
			MailEntity mailBean = new MailEntity(destMail, subject, content);
			mailBeans.add(mailBean);

			// 发送账号通知信息
			News news = new News(subject, new Date(), lendingRecord.getLoanableBook().getUser(), content);
			newss.add(news);
		} else if (seconds >= (overtimeTakeFromTransferStation + overtimeTakeBackFromTransferStation - 1) * daySeconds
				&& seconds < ((overtimeTakeFromTransferStation + overtimeTakeBackFromTransferStation - 1) * daySeconds
						+ intervalUpdateSeconds)) {
			/*
			 * 还有一天时间就超时，但借阅人没有把图书取走。 发送通知信息到借出人账号和发送邮件提醒。
			 */
			String destMail = lendingRecord.getLoanableBook().getUser().getMail();

			// 生成邮件主题内容以及添加邮件到mailBeans
			String subject = "书籍取回提醒"; // 邮件主题
			String content = "您同意借出的《" + lendingRecord.getLoanableBook().getLbName()
					+ "》图书，由于对方超时没有将图书取走，系统已经自动取消此次借阅，你已经过了" + (overtimeTakeBackFromTransferStation - 1) + "天没有前往"
					+ mappingMapper.selectByPrimaryKey("transfer_station").getmValue() + "取回图书，请你尽快凭订单号将图书取回。谢谢!!!"; // 邮件主体内容
			MailEntity mailBean = new MailEntity(destMail, subject, content);
			mailBeans.add(mailBean);

			// 发送账号通知信息
			News news = new News(subject, new Date(), lendingRecord.getLoanableBook().getUser(), content);
			newss.add(news);
		}
//		else if (seconds >= (overtimeTakeFromTransferStation + overtimeTakeBackFromTransferStation) * daySeconds) {
//			/*
//			 * 借出人超时没有把图书取走。 并修改状态为11表示借出人超时未取走图书,显示到管理员端人工处理
//			 */
//			// 把该条记录lending_record表中，并修改状态为11表示借出人取回超时
//			lendingRecord.setLrStruts((byte) 11);
//			lendingRecordMapper.updateByPrimaryKey(lendingRecord);
//		}
	}

	/**
	 * 状态为6,表示借出人已经把书送到运营点，判断借阅人是否逾期未把图书取走。逾期6-7
	 * 
	 * @param lendingRecord
	 * @param mailBeans
	 * @param newss
	 * @param nowDate
	 * @param overtimeTakeFromTransferStation
	 */
	private void isOvertimeTakeFromTransferStation(LendingRecord lendingRecord, List<MailEntity> mailBeans,
			List<News> newss, Date nowDate, int overtimeTakeFromTransferStation) {
		// 借阅者把图书送达运营点的时间
		Date sendToTime = lendingRecord.getSendToTime();

		// 相差的时间秒数
		long seconds = (nowDate.getTime() - sendToTime.getTime()) / 1000;
		// 最后的期限
		String deadline = getDeadline(sendToTime, overtimeTakeFromTransferStation);

		if (seconds >= (overtimeTakeFromTransferStation - 2) * daySeconds
				&& seconds < ((overtimeTakeFromTransferStation - 2) * daySeconds + intervalUpdateSeconds)) {
			/*
			 * 还有两天时间就超时，但借阅者没有把图书取走。 发送通知信息到借阅者账号和发送邮件提醒。
			 */
			String destMail = lendingRecord.getUser().getMail();

			// 生成邮件主题内容以及添加邮件到mailBeans
			String subject = "取书提醒"; // 邮件主题
			String content = "您申请借阅的《" + lendingRecord.getLoanableBook().getLbName() + "》图书，已送到运营点："
					+ mappingMapper.selectByPrimaryKey("transfer_station").getmValue() + ",距离最迟取走图书还有两天(最后期限为："
					+ deadline + ")，请你尽快凭订单号到指定地点将图书取走。谢谢!!!"; // 邮件主体内容
			MailEntity mailBean = new MailEntity(destMail, subject, content);
			mailBeans.add(mailBean);

			// 发送账号通知信息
			News news = new News(subject, new Date(), lendingRecord.getUser(), content);
			newss.add(news);
		} else if (seconds >= (overtimeTakeFromTransferStation - 1) * daySeconds
				&& seconds < ((overtimeTakeFromTransferStation - 1) * daySeconds + intervalUpdateSeconds)) {
			/*
			 * 还有一天时间就超时，但借阅者没有把图书取走。 发送通知信息到借阅者账号和发送邮件提醒。
			 */
			String destMail = lendingRecord.getUser().getMail();

			// 生成邮件主题内容以及添加邮件到mailBeans
			String subject = "取书提醒"; // 邮件主题
			String content = "您申请借阅的《" + lendingRecord.getLoanableBook().getLbName() + "》图书，已送到运营点："
					+ mappingMapper.selectByPrimaryKey("transfer_station").getmValue() + ",距离最迟取走图书还有一天(最后期限为："
					+ deadline + ")，请你尽快凭订单号到指定地点将图书取走。谢谢!!!"; // 邮件主体内容
			MailEntity mailBean = new MailEntity(destMail, subject, content);
			mailBeans.add(mailBean);

			// 发送账号通知信息
			News news = new News(subject, new Date(), lendingRecord.getUser(), content);
			newss.add(news);
		} else if (seconds >= overtimeTakeFromTransferStation * daySeconds) {
			/*
			 * 借阅者超时没有从运营点把图书取走。 发送通知信息到借出人账号和发送邮件提醒。 发送通知信息到借阅者账号和发送邮件提醒。
			 * 并修改状态为7表示借阅人超时未取走图书
			 */
			String loanMail = lendingRecord.getLoanableBook().getUser().getMail();
			String borrowMail = lendingRecord.getUser().getMail();

			// 生成邮件主题内容以及添加邮件到mailBeans
			String subject = "取书逾期提醒"; // 邮件主题
			String loanContent = "您同意借出的《" + lendingRecord.getLoanableBook().getLbName() + "》图书，由于借阅者逾期未到运营点取走图书(最后期限为："
					+ deadline + ")，系统已自动帮您取消了此次借阅。请您尽快凭订单号前往"
					+ mappingMapper.selectByPrimaryKey("transfer_station").getmValue() + "取回图书。谢谢!!!"; // 给借出人邮件主体内容
			String borrowContent = "您申请借阅的《" + lendingRecord.getLoanableBook().getLbName()
					+ "》图书，由于你未在取书逾期之前到运营点取走图书(最后期限为：" + deadline + ")，系统已自动帮您取消了借阅。你可以去寻找别的图书进行借阅。谢谢!!!"; // 给借阅人邮件主体内容
			MailEntity mailBean1 = new MailEntity(loanMail, subject, loanContent);
			MailEntity mailBean2 = new MailEntity(borrowMail, subject, borrowContent);
			mailBeans.add(mailBean1);
			mailBeans.add(mailBean2);

			// 发送账号通知信息
			News news1 = new News(subject, new Date(), lendingRecord.getLoanableBook().getUser(), loanContent);
			News news2 = new News(subject, new Date(), lendingRecord.getUser(), borrowContent);
			newss.add(news1);
			newss.add(news2);

			// 取消 借阅者超时未取
			lendingRecord.setLrStruts((byte) 7);
			lendingRecordMapper.updateByPrimaryKey(lendingRecord);
		}
	}

	/**
	 * 状态为4,表示借出人同意借出，判断借出人是否逾期未把图书送到运营点。逾期4-5
	 * 
	 * @param lendingRecord
	 * @param mailBeans
	 * @param newss
	 * @param nowDate
	 * @param overtimeBringToTransferStation
	 */
	private void isOvertimeBringToTransferStation(LendingRecord lendingRecord, List<MailEntity> mailBeans,
			List<News> newss, Date nowDate, int overtimeBringToTransferStation) {
		// 借出人同意借阅的时间
		Date agreeTime = lendingRecord.getAgreeTime();

		// 相差的时间秒数
		long seconds = (nowDate.getTime() - agreeTime.getTime()) / 1000;
		// 最后的期限
		String deadline = getDeadline(agreeTime, overtimeBringToTransferStation);

		if (seconds >= (overtimeBringToTransferStation - 2) * daySeconds
				&& seconds < ((overtimeBringToTransferStation - 2) * daySeconds + intervalUpdateSeconds)) {
			/*
			 * 还有两天时间就超时，但借出人没有将书籍送到运营点。 发送通知信息到借出人账号和发送邮件提醒。
			 */
			String destMail = lendingRecord.getLoanableBook().getUser().getMail();
			// 生成邮件主题内容以及添加邮件到mailBeans
			String subject = "送书提醒"; // 邮件主题
			String content = "您同意借出的《" + lendingRecord.getLoanableBook().getLbName() + "》图书，距离最迟送达运营点("
					+ mappingMapper.selectByPrimaryKey("transfer_station").getmValue() + ")的时间还有两天(最后期限为：" + deadline
					+ ")，请你尽快将图书送到运营点。谢谢!!!"; // 邮件主体内容
			MailEntity mailBean = new MailEntity(destMail, subject, content);
			mailBeans.add(mailBean);

			// 发送账号通知信息
			News news = new News(subject, new Date(), lendingRecord.getLoanableBook().getUser(), content);
			newss.add(news);
		} else if (seconds >= (overtimeBringToTransferStation - 1) * daySeconds
				&& seconds < ((overtimeBringToTransferStation - 1) * daySeconds + intervalUpdateSeconds)) {
			/*
			 * 还有一天时间就超时，但借出人没有将书籍送到运营点。 发送通知信息到借出人账号和发送邮件提醒。
			 */
			String destMail = lendingRecord.getLoanableBook().getUser().getMail();
			// 生成邮件主题内容以及添加邮件到mailBeans
			String subject = "送书提醒"; // 邮件主题
			String content = "您同意借出的《" + lendingRecord.getLoanableBook().getLbName() + "》图书，距离最迟送达运营点("
					+ mappingMapper.selectByPrimaryKey("transfer_station").getmValue() + ")的时间还有一天(最后期限为：" + deadline
					+ ")，请你尽快将图书送到运营点。谢谢!!!"; // 邮件主体内容
			MailEntity mailBean = new MailEntity(destMail, subject, content);
			mailBeans.add(mailBean);

			// 发送账号通知信息
			News news = new News(subject, new Date(), lendingRecord.getLoanableBook().getUser(), content);
			newss.add(news);
		} else if (seconds >= overtimeBringToTransferStation * daySeconds) {
			/*
			 * 送书期限已经超时，但借出人没有将书籍送到运营点。 发送通知信息到借出人账号和发送邮件提醒。
			 * 发送通知信息到借阅人账号和发送邮件提醒。
			 * 把该条记录移到lending_history表中，并修改状态为5表示借出人超时未送达，删除lending_record表中的记录
			 */
			String loanMail = lendingRecord.getLoanableBook().getUser().getMail();
			String borrowMail = lendingRecord.getUser().getMail();

			// 生成邮件主题内容以及添加邮件到mailBeans
			String subject = "送书逾期提醒"; // 邮件主题
			String loanContent = "您同意借出的《" + lendingRecord.getLoanableBook().getLbName() + "》图书，由于你逾期未将图书送到运营点(最后期限为："
					+ deadline + ")，系统已自动帮您取消了借阅同意。谢谢!!!"; // 给借出人邮件主体内容
			String borrowContent = "您申请借阅的《" + lendingRecord.getLoanableBook().getLbName()
					+ "》图书，由于对方超时未将图书送到运营点(最后期限为：" + deadline + ")，系统已自动帮您取消了借阅。你可以去寻找别的图书进行借阅。谢谢!!!"; // 给借阅人邮件主体内容
			MailEntity mailBean1 = new MailEntity(loanMail, subject, loanContent);
			MailEntity mailBean2 = new MailEntity(borrowMail, subject, borrowContent);
			mailBeans.add(mailBean1);
			mailBeans.add(mailBean2);

			// 发送账号通知信息
			News news1 = new News(subject, new Date(), lendingRecord.getLoanableBook().getUser(), loanContent);
			News news2 = new News(subject, new Date(), lendingRecord.getUser(), borrowContent);
			newss.add(news1);
			newss.add(news2);

			// 把该条记录移到lending_history表中，并修改状态为3表示申请超时
//			LendingHistory lendingHistory = createLendingHistory(lendingRecord, (byte) 5);

			lendingRecord.setLrStruts((byte) 5);

			lendingRecordMapper.updateByPrimaryKey(lendingRecord);
			//insertLendingHistory(lendingHistory); // insert到历史表中

			// 删除lending_record表中的该记录
			//deleteLendingRecordByPrimaryKey(lendingRecord.getLrId());
		}
	}

	/**
	 * 状态为0,表示借书人提交了借书申请，判断借出人是否超时未同意借书人的借书请求。逾期0-3
	 * 
	 * @param lendingRecord
	 * @param mailBeans
	 * @param newss
	 * @param nowDate
	 * @param overtimeAgreeApply
	 */
	private void isOvertimeAgreeApply(LendingRecord lendingRecord, List<MailEntity> mailBeans, List<News> newss,
			Date nowDate, int overtimeAgreeApply) {
		// 提交借书的时间
		Date createTime = lendingRecord.getCreateTime();

		// 相差的时间秒数
		long seconds = (nowDate.getTime() - createTime.getTime()) / 1000;
		System.out.println(seconds);
		// 最后的期限
		String deadline = getDeadline(createTime, overtimeAgreeApply);
		if (seconds >= (overtimeAgreeApply - 2) * daySeconds
				&& seconds < ((overtimeAgreeApply - 2) * daySeconds + intervalUpdateSeconds)) {
			/*
			 * 还有两天时间就超时，但借出人没有同意借阅者的图书借阅申请。 发送通知信息到借出人账号和发送邮件提醒。
			 */
			String destMail = lendingRecord.getLoanableBook().getUser().getMail();
			// 生成邮件主题内容以及添加邮件到mailBeans
			String subject = "借阅申请提醒"; // 邮件主题
			String content = "您的《" + lendingRecord.getLoanableBook().getLbName() + "》图书正在被申请借阅，距离申请失效还有两天时间(最后期限为："
					+ deadline + ")，请你尽快同意或拒接申请。谢谢!!!"; // 邮件主体内容
			MailEntity mailBean = new MailEntity(destMail, subject, content);
			mailBeans.add(mailBean);

			// 发送账号通知信息
			News news = new News(subject, new Date(), lendingRecord.getLoanableBook().getUser(), content);
			newss.add(news);
		} else if (seconds >= (overtimeAgreeApply - 1) * daySeconds
				&& seconds < ((overtimeAgreeApply - 1) * daySeconds + intervalUpdateSeconds)) {
			/*
			 * 还有一天时间就超时，但借出人没有同意借阅者的图书借阅申请。 发送通知信息到借出人账号和发送邮件提醒。
			 */
			String destMail = lendingRecord.getLoanableBook().getUser().getMail();
			// 生成邮件主题内容以及添加邮件到mailBeans
			String subject = "借阅申请提醒"; // 邮件主题
			String content = "您的《" + lendingRecord.getLoanableBook().getLbName() + "》图书正在被申请借阅，距离申请失效还有一天时间(最后期限为："
					+ deadline + ")，请你尽快同意或拒接申请。谢谢!!!"; // 邮件主体内容
			MailEntity mailBean = new MailEntity(destMail, subject, content);
			mailBeans.add(mailBean);

			// 发送账号通知信息
			News news = new News(subject, new Date(), lendingRecord.getLoanableBook().getUser(), content);
			newss.add(news);
		} else if (seconds >= overtimeAgreeApply * daySeconds) {
			/*
			 * 借阅申请已经超时，但借出人没有同意借阅者的图书借阅申请。 发送通知信息到借出人账号和发送邮件提醒。
			 * 发送通知信息到借阅人账号和发送邮件提醒。
			 * 把该条记录移到lending_history表中，并修改状态为3表示申请超时，删除lending_record表中的记录
			 */
			String loanMail = lendingRecord.getLoanableBook().getUser().getMail();
			String borrowMail = lendingRecord.getUser().getMail();

			// 生成邮件主题内容以及添加邮件到mailBeans
			String subject = "借阅申请失效提醒"; // 邮件主题
			String loanContent = "您的《" + lendingRecord.getLoanableBook().getLbName() + "》图书被申请借阅，由于你超时未操作申请(最后期限为："
					+ deadline + ")，系统已自动帮您取消了借阅申请。谢谢!!!"; // 给借出人邮件主体内容
			String borrowContent = "您申请借阅的《" + lendingRecord.getLoanableBook().getLbName() + "》图书，由于对方超时未操作申请(最后期限为："
					+ deadline + ")，系统已自动取消了借阅申请。你可以去寻找别的图书进行借阅。谢谢!!!"; // 给借阅人邮件主体内容
			MailEntity mailBean1 = new MailEntity(loanMail, subject, loanContent);
			MailEntity mailBean2 = new MailEntity(borrowMail, subject, borrowContent);
			mailBeans.add(mailBean1);
			mailBeans.add(mailBean2);

			// 发送账号通知信息
			News news1 = new News(subject, new Date(), lendingRecord.getLoanableBook().getUser(), loanContent);
			News news2 = new News(subject, new Date(), lendingRecord.getUser(), borrowContent);
			newss.add(news1);
			newss.add(news2);

			// 把该条记录移到lending_history表中，并修改状态为3表示申请超时
			//LendingHistory lendingHistory = createLendingHistory(lendingRecord, (byte) 3);

			lendingRecord.setLrStruts((byte) 3);

			lendingRecordMapper.updateByPrimaryKey(lendingRecord);
			//insertLendingHistory(lendingHistory); // insert到历史表中

			// 删除lending_record表中的该记录
			//deleteLendingRecordByPrimaryKey(lendingRecord.getLrId());
		}
	}

	/**
	 * 计算最后期限并转化为String
	 * 
	 * @param Date
	 * @param overtime
	 * @return String
	 */
	private String getDeadline(Date agreeTime, int overtime) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(agreeTime);
		calendar.add(Calendar.DAY_OF_MONTH, overtime);
		Date deadline = calendar.getTime();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

		return sdf.format(deadline);
	}

	/**
	 * 根据lendingRecord对象信息创建lendingHistory对象
	 * 
	 * @param lendingRecord
	 * @param status
	 * @return lendingRecord对象
	 */
	private LendingHistory createLendingHistory(LendingRecord lendingRecord, byte status) {
		Administrator administrator = new Administrator();
		LendingHistory lendingHistory = new LendingHistory();

		lendingHistory.setLhId(lendingRecord.getLrId());
		lendingHistory.setCreateTime(lendingRecord.getCreateTime());
		lendingHistory.setAgreeTime(lendingRecord.getAgreeTime());
		lendingHistory.setSendToTime(lendingRecord.getSendToTime());
		lendingHistory.setTakeAwayTime(lendingRecord.getTakeAwayTime());
		lendingHistory.setExpectedReturnTime(lendingRecord.getExpectedReturnTime());
		lendingHistory.setActualReturnTime(lendingRecord.getActualReturnTime());
		lendingHistory.setTakeBackTime(lendingRecord.getTakeBackTime());
		lendingHistory.setLhStruts(status);
		lendingHistory.setLoanPhone(lendingRecord.getLoanPhone());
		lendingHistory.setLoanableBook(lendingRecord.getLoanableBook());
		lendingHistory.setUser(lendingRecord.getUser());
		if (lendingRecord.getReceiveAdmin() != null) {
			lendingHistory.setReceiveAdmin(lendingRecord.getReceiveAdmin());
		} else {
			lendingHistory.setReceiveAdmin(administrator);
		}
		if (lendingRecord.getBackAdmin() != null) {
			lendingHistory.setBackAdmin(lendingRecord.getBackAdmin());
		} else {
			lendingHistory.setBackAdmin(administrator);
		}
		return lendingHistory;
	}

	/**
	 * 根据主键删除lending_record表中的记录
	 * 
	 * @param lrId
	 */
	@Transactional
	private void deleteLendingRecordByPrimaryKey(Integer lrId) {
		try {
			lendingRecordMapper.deleteByPrimaryKey(lrId);
		} catch (Exception e) {
			e.printStackTrace();
			throw new SystemErrorException("操作失败，系统异常");
		}
	}

	/**
	 * 向lending_history表中插入记录
	 * 
	 * @param lendingHistory
	 */
	@Transactional
	private void insertLendingHistory(LendingHistory lendingHistory) {
		try {
			lendingHistoryMapper.insertSelective(lendingHistory);
		} catch (Exception e) {
			e.printStackTrace();
			throw new SystemErrorException("操作失败，系统异常");
		}
	}

	/**
	 * 向news表中插入通知消息
	 * 
	 * @param news
	 */
	@Transactional
	private void insertNews(News news) {
		try {
			newsMapper.insert(news);
		} catch (Exception e) {
			e.printStackTrace();
			throw new SystemErrorException("操作失败，系统异常");
		}
	}
}
