package com.cmic.attendance.schedule;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.cmic.attendance.model.Holidays;
import com.cmic.attendance.service.HolidaysService;
import com.cmic.attendance.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by issuser on 2017/12/18.
 */
@Component
public class HolidaysSchedule {

    @Autowired
    private HolidaysService holidaysService;

    @Scheduled(cron="0 0 23 1 1 ?")//每年一月一日23:00:00执行
    public void cronJob(){
        String date = DateUtils.getDateToYearMonthDay(new Date());
        String httpUrl = "http://tool.bitefu.net/jiari/";
        String data = date.replace("-","").substring(0,4);
        String fdate = "d=" + data;
        String jsonResult = DateUtils.request(httpUrl, fdate);
        JSONObject jsonObject = JSON.parseObject(jsonResult);
        Map map = jsonObject;
        if( map.get(data).toString().substring(0,1).equals("{")) {
            map = (Map) map.get(data);
            Holidays holidays = null;
            List<String> valueList = holidaysService.findMonthDayByYear(data);
            if (valueList != null && valueList.size() == 0) {
                for (Object key : map.keySet()) {
                    holidays = new Holidays();
                    holidays.setYear(data);
                    String monthDay = key.toString();
                    String dayStatus = map.get(monthDay).toString();
                    holidays.setMonthDay(monthDay);
                    holidays.setDayStatus(dayStatus);
                    holidaysService.save(holidays);
                }
            }
        }
    }
}
