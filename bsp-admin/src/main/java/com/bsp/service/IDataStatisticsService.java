package com.bsp.service;

import com.bsp.vo.KeyValueNumberVO;
import com.bsp.vo.EchartsSex;

import java.util.List;
import java.util.Map;

/**
 * @program: bsp
 * @Author：邬俊标
 * @Description：
 * @Date：18:01 2018/9/9
 * @Version: 1.0
 */
public interface IDataStatisticsService {

    List<EchartsSex> getSexSum();

    List<KeyValueNumberVO> getcheckLoanableStatistics();

    List<KeyValueNumberVO> getSharingBookStatistics();

    Map<String, Integer> getSharingDonatedNumStatistics();

    List<KeyValueNumberVO> getstatusLendingNumStatistics();

    List<KeyValueNumberVO> getPerMonthSharNumStatistics(String year);

    List<KeyValueNumberVO> getclassifyBookNumStatistics();
}
