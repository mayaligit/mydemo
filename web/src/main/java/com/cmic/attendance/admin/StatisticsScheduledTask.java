package com.cmic.attendance.admin;

/**
 * @return 考勤统计, 定时任务
 * @author 何家来
 */
//@Component
public class StatisticsScheduledTask {

/*
    @Autowired
    private GroupRuleDao groupRuleDao;
    @Autowired
    private StatisticsDao statisticsDao;*/

    int i = 0;
//  @Scheduled(cron = "*/1 * * * * ?")
    public void ScheduledTask3() {
        System.out.println(" 我是一个每隔1秒钟就就会执行的任务: " + i);
        i++;
        //查出所有组信息

        //通过组关联考勤人员表和考勤表查出表当天的所有员工的信息

        //遍历结果集，作处理，插入考勤统计表
    }

}