package com.bsp.service.impl;

import com.bsp.dao.*;
import com.bsp.entity.*;
import com.bsp.service.IDataStatisticsService;
import com.bsp.vo.KeyValueNumberVO;
import com.bsp.vo.EchartsSex;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @program: bsp
 * @Author：邬俊标
 * @Description：
 * @Date：18:01 2018/9/9
 * @Version: 1.0
 */
@Service
public class DataStatisticsService implements IDataStatisticsService {

    @Autowired
    private UserInforMapper userInforMapper = null;
    @Autowired
    private CheckLoanableBookMapper checkLoanableBookMapper = null;
    @Autowired
    private LoanableBookMapper loanableBookMapper = null;
    @Autowired
    private DonatedBookMapper donatedBookMapper = null;
    @Autowired
    private LendingRecordMapper lendingRecordMapper = null;
    @Autowired
    private PrimaryClassificationMapper primaryClassificationMapper;
    @Autowired
    private SecondaryClassificationMapper secondaryClassificationMapper;

    @Override
    public List<EchartsSex> getSexSum() {
        int manSum = userInforMapper.getSexSum("男");
        int womanSum = userInforMapper.getSexSum("女");
        List<EchartsSex> list = new ArrayList<>();

        EchartsSex echartsSex = new EchartsSex();
        echartsSex.setSex("男");
        echartsSex.setNumber(manSum);
        list.add(echartsSex);

        EchartsSex echartsSex1 = new EchartsSex();
        echartsSex1.setSex("女");
        echartsSex1.setNumber(womanSum);
        list.add(echartsSex1);
        return list;
    }

    @Override
    public List<KeyValueNumberVO> getcheckLoanableStatistics() {
        List<KeyValueNumberVO> list = new ArrayList<>();
//        [{name:'待审核',value:10},{name:'审核未通过',value:13},{name:'通过审核',value:10}]
        String name[] = {"待审核", "审核未通过", "审核通过"};
        for (int i = 0; i < 3; i++) {
            KeyValueNumberVO keyValueNumberVO = new KeyValueNumberVO();
            keyValueNumberVO.setName(name[i]);
            keyValueNumberVO.setValue(checkLoanableBookMapper.getNumByClbStatus(i));
            list.add(keyValueNumberVO);
        }
        return list;
    }

    @Override
    public List<KeyValueNumberVO> getSharingBookStatistics() {
        List<KeyValueNumberVO> list = new ArrayList<>();
//    ['停止共享', '正在共享', '被下架']
        String name[] = {"停止共享", "正在共享", "被下架"};
        for (int i = 0; i < 3; i++) {
            KeyValueNumberVO keyValueNumberVO = new KeyValueNumberVO();
            keyValueNumberVO.setName(name[i]);
            keyValueNumberVO.setValue(loanableBookMapper.getNumByLbStatus(i));
            list.add(keyValueNumberVO);
        }
        return list;
    }

    @Override
    public Map<String, Integer> getSharingDonatedNumStatistics() {
        HashMap<String, Integer> map = new HashMap<>();
        Integer sharingNum = 0;
        Integer donatedNum = 0;
        List<DonatedBook> donatedBooks = donatedBookMapper.selectAll();
        for (DonatedBook donatedBook : donatedBooks) {
            donatedNum += donatedBook.getNumber();
        }
        List<LendingRecord> lendingRecords = lendingRecordMapper.selectAllByLrStruts();
        for (LendingRecord lendingRecord : lendingRecords) {
            sharingNum += lendingRecord.getAmount();
        }
        map.put("sharing", sharingNum);
        map.put("donated", donatedNum);
        return map;
    }

    @Override
    public List<KeyValueNumberVO> getstatusLendingNumStatistics() {
        List<KeyValueNumberVO> list = new ArrayList<>();
//        data: ["借阅申请","借阅者取消申请","借出人拒绝申请","申请超时","借出人同意借出","借出人逾期未送书"
//                ,"借出人送到运营点","借阅者逾期未取书","借阅者取走书","借阅者逾期未还书","借阅者已还书","借出人捐赠","借出人取回书"]
        String name[] = {"借阅申请", "借阅者取消申请", "借出人拒绝申请", "申请超时", "借出人同意借出", "借出人逾期未送书"
                , "借出人送到运营点", "借阅者逾期未取书", "借阅者取走书", "借阅者逾期未还书", "借阅者已还书", "借出人捐赠", "借出人取回书"};
        for (int i = 0; i < 13; i++) {
            KeyValueNumberVO keyValueNumberVO = new KeyValueNumberVO();
            keyValueNumberVO.setName(name[i]);
            keyValueNumberVO.setValue(lendingRecordMapper.getNumByLrStatus(i));
            list.add(keyValueNumberVO);
        }
        return list;
    }

    @Override
    public List<KeyValueNumberVO> getPerMonthSharNumStatistics(String year) {
        ArrayList<KeyValueNumberVO> listReslt = new ArrayList<>();
        String date = null;
        for (int month = 1; month <= 12; month++) {
            int num = 0;
            KeyValueNumberVO keyValueNumberVO = new KeyValueNumberVO();
            if (month < 10) {
                date = year + "-0" + month;
            } else {
                date = year + "-" + month;
            }
            List<LoanableBook> lists = loanableBookMapper.getTotalBookByDate(date);
            for (LoanableBook loanableBook : lists) {
                num += loanableBook.getLeft();
            }
            keyValueNumberVO.setName(month+"月");
            keyValueNumberVO.setValue(num);
            listReslt.add(keyValueNumberVO);
        }
        return listReslt;
    }

    @Override
    public List<KeyValueNumberVO> getclassifyBookNumStatistics() {
        List<KeyValueNumberVO> list = new ArrayList<>();
        List<PrimaryClassification> primaryClassifications = primaryClassificationMapper.selectAll();
        for (PrimaryClassification primaryClassification : primaryClassifications){
            KeyValueNumberVO keyValueNumberVO = new KeyValueNumberVO();
            keyValueNumberVO.setName(primaryClassification.getPcName());

            List<SecondaryClassification> secondaryClassifications =
                    secondaryClassificationMapper.selectCountByPcId(primaryClassification.getPcId());
            int num = 0;
            for (SecondaryClassification secondaryClassification : secondaryClassifications){
                List<LoanableBook> bookLists = loanableBookMapper.getTotalCountByScId(secondaryClassification.getScId());
                for (LoanableBook loanableBook : bookLists){
                    num += loanableBook.getLeft();
                }
            }
            keyValueNumberVO.setValue(num);
            list.add(keyValueNumberVO);
        }
        return list;
    }

}
