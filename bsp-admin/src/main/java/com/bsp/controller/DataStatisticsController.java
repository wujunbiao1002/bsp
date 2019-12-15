package com.bsp.controller;

import com.bsp.service.impl.DataStatisticsService;
import com.bsp.vo.EchartsSex;
import com.bsp.vo.KeyValueNumberVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @program: bsp
 * @Author：邬俊标
 * @Description：
 * @Date：18:00 2018/9/9
 * @Version: 1.0
 */
@RestController
@Scope(value="prototype")
@RequestMapping("/echarts")
public class DataStatisticsController {
    @Autowired
    private DataStatisticsService dataStatisticsService = null;
    @RequestMapping("/sex.do")
    public Map<String, List<EchartsSex>> getSexEchartsStatistics(){
        HashMap<String, List<EchartsSex>> map = new HashMap<>();
        map.put("list", dataStatisticsService.getSexSum());
        return map;
    }

    @ResponseBody
    @RequestMapping("/checkEcharts.do")
    public List<KeyValueNumberVO> getcheckLoanableStatistics(){
        List<KeyValueNumberVO> list = dataStatisticsService.getcheckLoanableStatistics();
        return list;
    }

    @ResponseBody
    @RequestMapping("/sharingEcharts.do")
    public List<KeyValueNumberVO> getSharingBooksStatistics(){
        List<KeyValueNumberVO> list = dataStatisticsService.getSharingBookStatistics();
        return list;
    }

    /**
     * @Author: 邬俊标
     * @Description: 成功借阅的书籍数量和捐赠的书籍数量
     * @Date: 5:50 2018/10/19
     * @Param:
     * @Return:
     **/

    @ResponseBody
    @RequestMapping("/sharingDonatedNum.do")
    public Map<String, Integer> getSharingDonatedNum(){
        Map<String, Integer> map = dataStatisticsService.getSharingDonatedNumStatistics();
        return map;
    }

    /**
     * @Author: 邬俊标
     * @Description: 获取借阅记录中不同转态的数量
     * @Date: 5:49 2018/10/19
     * @Param:
     * @Return:
     **/
    @ResponseBody
    @RequestMapping("/statusLendingNum.do")
    public List<KeyValueNumberVO> getStatusLendingNum(){
        List<KeyValueNumberVO> list = dataStatisticsService.getstatusLendingNumStatistics();
        return list;
    }

    /**
     * @Author: 邬俊标
     * @Description: 每月共享的图书数量
     * @Date: 5:49 2018/10/19
     * @Param:
     * @Return:
     **/
    @ResponseBody
    @RequestMapping("/perMonthSharNum.do")
    public List<KeyValueNumberVO> getPerMonthSharNum(@RequestParam("year") String year){
        List<KeyValueNumberVO> list = dataStatisticsService.getPerMonthSharNumStatistics(year);
        return list;
    }

    /**
     * @Author: 邬俊标
     * @Description: 获取种类图书
     * @Date: 5:49 2018/10/19
     * @Param:
     * @Return:
     **/
    @ResponseBody
    @RequestMapping("/classifyBookNum.do")
    public List<KeyValueNumberVO> geclassifyBookNum(){
        List<KeyValueNumberVO> list = dataStatisticsService.getclassifyBookNumStatistics();
        return list;
    }


}
