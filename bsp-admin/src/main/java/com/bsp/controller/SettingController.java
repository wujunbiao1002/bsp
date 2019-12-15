package com.bsp.controller;

import com.bsp.dao.MappingMapper;
import com.bsp.entity.Mapping;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;

/**
 * @program: bsp
 * @Author：邬俊标
 * @Description：
 * @Date：12:22 2018/10/19
 * @Version: 1.0
 */
@Controller
@Scope(value="prototype")
@RequestMapping("/setting")
public class SettingController {
    @Autowired
    private MappingMapper mappingMapper;

    @ResponseBody
    @RequestMapping("/getOverTime.do")
    public Map<String,String> getOverTime(){
        Map<String, String> map = new HashMap<>();
        map.put("overtime_agree_apply", mappingMapper.selectByPrimaryKey("overtime_agree_apply").getmValue());
        map.put("overtime_bring_to_transfer_station", mappingMapper.selectByPrimaryKey("overtime_bring_to_transfer_station").getmValue());
        map.put("overtime_take_from_transfer_station", mappingMapper.selectByPrimaryKey("overtime_take_from_transfer_station").getmValue());
        map.put("overtime_take_back_from_transfer_station", mappingMapper.selectByPrimaryKey("overtime_take_back_from_transfer_station").getmValue());
        return map;
    }

    @ResponseBody
    @RequestMapping("/updateOverTime.do")
    public void updateOverTime(String overtime_agree_apply, String overtime_bring_to_transfer_station,
                               String overtime_take_from_transfer_station,String overtime_take_back_from_transfer_station){

        Mapping mapping = new Mapping();
        mapping.setMapkey("overtime_agree_apply");
        mapping.setmValue(overtime_agree_apply);
        mappingMapper.updateByPrimaryKeySelective(mapping);

        mapping.setMapkey("overtime_bring_to_transfer_station");
        mapping.setmValue(overtime_bring_to_transfer_station);
        mappingMapper.updateByPrimaryKeySelective(mapping);

        mapping.setMapkey("overtime_take_from_transfer_station");
        mapping.setmValue(overtime_take_from_transfer_station);
        mappingMapper.updateByPrimaryKeySelective(mapping);

        mapping.setMapkey("overtime_take_back_from_transfer_station");
        mapping.setmValue(overtime_take_back_from_transfer_station);
        mappingMapper.updateByPrimaryKeySelective(mapping);
    }
}
