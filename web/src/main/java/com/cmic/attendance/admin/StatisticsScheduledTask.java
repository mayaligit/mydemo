package com.cmic.attendance.admin;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * @return 考勤统计, 定时任务
 * @author 何家来
 */
@Component
public class StatisticsScheduledTask {

    int i = 0;
    @Scheduled(cron = "*/1 * * * * ?")
    public void ScheduledTask3() {
        System.out.println(" 我是一个每隔1秒钟就就会执行的任务: " + i);
        i++;
    }

}