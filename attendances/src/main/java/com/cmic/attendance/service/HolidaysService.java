package com.cmic.attendance.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.cmic.attendance.dao.HolidaysDao;
import com.cmic.attendance.model.Holidays;
import com.cmic.attendance.utils.DateUtils;
import com.cmic.saas.base.service.CrudService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 节假日service
 */
@Service
@Transactional(readOnly = true)
public class HolidaysService extends CrudService<HolidaysDao,Holidays> {

    public Holidays get(String id) {
        return super.get(id);
    }

    public List<Holidays> findList(Holidays holidays) {
        return super.findList(holidays);
    }

    public List<String> findMonthDayByYear(String year){
        return dao.findMonthDayByYear(year);
    }

    @Transactional(readOnly = false)
    public void save(Holidays holidays) {
        super.save(holidays);
    }

    @Transactional(readOnly = false)
    public void saveHolidys(){
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
            List<String> valueList = this.findMonthDayByYear(data);
            if (valueList != null && valueList.size() == 0) {
                for (Object key : map.keySet()) {
                    holidays = new Holidays();
                    holidays.setYear(data);
                    String monthDay = key.toString();
                    String dayStatus = map.get(monthDay).toString();
                    holidays.setMonthDay(monthDay);
                    holidays.setDayStatus(dayStatus);
                    this.save(holidays);
                }
            }
        }
    }
}
