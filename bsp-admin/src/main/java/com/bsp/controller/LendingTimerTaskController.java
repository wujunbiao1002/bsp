package com.bsp.controller;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.bsp.service.impl.LendingTimerTaskService;

/**
 * 
 * 类名称：TimerTask 类描述：定时器任务 创建人：wjb 创建时间：2018年5月14日22:28:33
 * 
 * @version V1.0
 *
 */
@Component
public class LendingTimerTaskController extends BaseController {

	@Autowired
	private LendingTimerTaskService timerTaskService;	

	public void setTimerTaskService(LendingTimerTaskService timerTaskService) {
		this.timerTaskService = timerTaskService;
	}

	/**
	 * 检查借出记录LendingRecord中的订单，各种状态是否超时。
	 */
	@Scheduled(cron = "0 0 0,3,6,9,12,15,18,21 * * ?") // 指定时间没三小时一次执行
	public void checkLendingRecordStatus() {
		Date day = new Date();
		DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		System.out.println(sdf.format(day) + "*********进行借出记录表中，超时记录处理！！！");
		timerTaskService.checkLendingRecordAllOvertime();
	}

}
