package com.cmic.attendance.schedule;

import com.cmic.attendance.service.HolidaysService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * Created by issuser on 2017/12/18.
 */
@Component
public class HolidaysSchedule {

    @Autowired
    private HolidaysService holidaysService;

    @Scheduled(cron="0 0 23 1 1 ?")//每年一月一日23:00:00执行
    public void cronJob(){
        holidaysService.saveHolidys();
    }
}
