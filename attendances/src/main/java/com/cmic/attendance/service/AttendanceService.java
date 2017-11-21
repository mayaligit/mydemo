package com.cmic.attendance.service;

import com.cmic.attendance.bo.InsetEndStaticBo;
import com.cmic.attendance.dao.AttendanceDao;
import com.cmic.attendance.model.Attendance;
import com.cmic.attendance.model.Clazzes;
import com.cmic.attendance.model.Statistics;
import com.cmic.attendance.utils.DateUtils;
import com.cmic.attendance.vo.AttendanceEndVo;
import com.cmic.attendance.vo.AttendanceVo;
import com.cmic.saas.base.service.CrudService;
import com.cmic.saas.base.web.RestException;
import com.cmic.saas.utils.StringUtils;
import com.github.pagehelper.ISelect;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import freemarker.template.Configuration;
import freemarker.template.Template;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.*;
import java.util.*;

/**
 * 考勤表Service
 */
@Service
@Transactional(readOnly = true)
public class AttendanceService extends CrudService<AttendanceDao, Attendance> {

    private static Logger log = Logger.getLogger(AttendanceService.class);

    @Autowired
    private ClazzesService clazzesService;
    @Autowired
    private StatisticsService statisticsService;

    public Attendance get(String id) {
        return super.get(id);
    }

    public List<Attendance> findList(Attendance attendance) {
        return super.findList(attendance);
    }

    public PageInfo<Attendance> findPage(PageInfo<Attendance> page, Attendance attendance) {
        return super.findPage(page, attendance);
    }

    @Transactional(readOnly = false)
    public void save(Attendance attendance) {
        super.save(attendance);
    }

    @Transactional(readOnly = false)
    public void dynamicUpdate(Attendance attendance) {
        super.dynamicUpdate(attendance);
    }

    @Transactional(readOnly = false)
    public void delete(String id) {
        //判断是否存在
        Attendance attendance = get(id);
        if (attendance == null || StringUtils.isEmpty(attendance.getId())) {
            throw new RestException("删除失败，考勤表不存在");
        }
        super.delete(id);
        logger.info("删除考勤表：" + attendance.toJSONString());
    }
//#TODO 判断当前用户是否存在
    /**
     * @param phone
     * @param createTime
     * @return Attendance
     */
    public Attendance checkAttendance(String phone,String createTime) {
        log.debug("检验唯一性考勤数据手机号："+phone +" 考勤日期："+createTime);
        Attendance attendance = dao.getAttendanceByCreatebyAndCreateTime(phone,createTime);
        if ( null ==attendance ) {
            return null;
        } else {
            return attendance;
        }
    }
//#TODO 早上打卡业务
    /**
     * @param attendanceVo 封装前台数据的bean
     * @return AttendanceVo 封装统一返回信息的bean
     * //获取规则表
    Clazzes clazzes = clazzesService.getClazzesById(attendanceVo.getClazzesId());
     */
    @Transactional(readOnly = false)
    public Attendance punchCard(AttendanceVo attendanceVo) {

        /*读取规则gui*/
       /* GroupRule groupRule = groupRuleService.getByGroupNameAndGroupStatus(attendanceVo.getAttendanceGroup(), 0);*/

        //获取规则表固定
        Clazzes clazzes = clazzesService.getClazzesById(attendanceVo.getClazzesId());
        if (clazzes==null){
            //不在考勤日期内直接返回预留业务
            return null;
        }else {
            //开始读取考勤组考勤的方式
            Integer groupAttendanceWay = 1;
            //一、固定时长
            if ("1".equals("1")) {
                //判断当前地点是否异常
                Attendance saveAttendance = new Attendance();
                String distance2 = attendanceVo.getDistance();
                if (distance2 ==null || "".equals(distance2)){
                    distance2="0:0";
                }
                String[] split = distance2.split("\\.");
                String distances=split[0];
                Integer groupAttendanceScope = Integer.parseInt(clazzes.getNomalAddress());
                if (Integer.parseInt(distances) > groupAttendanceScope) {
                    saveAttendance.setAttendanceStatus("1");
                    saveAttendance.setAttendanceDesc("地点异常");

                } else {
                    //地址重合则会是null或小于都走这里的逻辑
                    saveAttendance.setAttendanceStatus("0");
                }

                //服务器时间
                Date startDate = new Date();
                String compareTime = DateUtils.getDateToHourMinuteS(startDate);
                String[] compareTimeArry = compareTime.split(":");
                Integer compareHour = Integer.parseInt(compareTimeArry[0]);
                Integer compareMinute = Integer.parseInt(compareTimeArry[1]);

                //设计考勤时间
                /*String groupAttendanceStart = groupRule.getGroupAttendanceStart();*/
                String groupAttendanceStart = clazzes.getNomalStartTime();
                String[] AttendanceStartArry = groupAttendanceStart.split(":");
                Integer groupAttendanceHour = Integer.parseInt(AttendanceStartArry[0]);
                Integer groupAttendanceMinute = Integer.parseInt(AttendanceStartArry[1]);

                //根据考勤时间不同插入状态码
                if (compareHour < groupAttendanceHour) {
                    //正常考勤
                    saveAttendance.setStartTimeStatus("0");
                } else if (compareHour == groupAttendanceHour) {
                    //正常考勤
                    if (compareMinute < groupAttendanceMinute) {
                        saveAttendance.setStartTimeStatus("0");
                    } else {
                        saveAttendance.setStartTimeStatus("1");
                    }
                } else if (compareHour > groupAttendanceHour) {
                    saveAttendance.setStartTimeStatus("1");
                }
                //插入数据
                saveAttendance.setAttendanceUser(attendanceVo.getUsername());
                Date startTime = DateUtils.getStringsToDates(DateUtils.getDateToStrings(startDate));
                saveAttendance.setStartTime(startTime);
                //年月日
                String dateToYearMonthDay = DateUtils.getDateToYearMonthDay(startDate);
                String[] dateToYearMonthDayArry = dateToYearMonthDay.split("-");
                saveAttendance.setAttendanceMonth(dateToYearMonthDayArry[0] + "-" +
                        dateToYearMonthDayArry[1]);
                saveAttendance.setStartLocation(attendanceVo.getLocation());
                saveAttendance.setDailyStatus("0");
                /*saveAttendance.setAttendanceGroup(attendanceVo.getAttendanceGroup());*/
                this.save(saveAttendance);
                log.debug("保存后返回的ID"+saveAttendance.getId());
                try {
                    //向统计表插入数据 String CreateBy,String createTime,String userName
                    insetStartStatic(attendanceVo.getPhone(), dateToYearMonthDay, attendanceVo.getUsername());
                } catch (Exception e) {
                    log.debug("插入统计表失败" + e.getMessage());
                }
                //返回数据页面
                return saveAttendance;
            } else {
                //2、预留自由打卡业务
                return null;
            }
        }
    }

    //TODO 下班打卡业务
    /*
      @param  attendanceEndVo 封装下班业务的bean
      @return AttendanceEndVo 返回Controller的bean
     */
    @Transactional(readOnly = false)
    public AttendanceEndVo punchCardEnd(AttendanceEndVo attendanceEndVo ){

        Date serverTime=new Date();
        String offtime = DateUtils.getDateToStrings(serverTime);
        String[] split2 = offtime.split(" ");
        String[] offtimeArry = split2[1].split(":");
        int hourTime = Integer.parseInt(offtimeArry[0]);
        int minuteTime = Integer.parseInt(offtimeArry[1]);
        attendanceEndVo.setOfftime(split2[1]);
        //从班次表中获取获取考勤时间 考勤地址 比较服务器时间
        Clazzes clazzes = clazzesService.getClazzesById(attendanceEndVo.getClazzesId());
        String dateString = clazzes.getNomalEndTime();
        String[] saArry = dateString.split(":");
        int offHourTime = Integer.parseInt(saArry[0]);
        int offMinuteTime = Integer.parseInt(saArry[1]);
        //获取标准上班时间
        String nomalStartTime = clazzes.getNomalStartTime();
        int tempTime=14;
        /*//判断考勤地点是否异常
        if (clazzes.getNomalAddress().equals(attendanceEndVo.getLocation())){
            attendanceEndVo.setAttendanceStatus("0");
        }else {
            attendanceEndVo.setAttendanceStatus("1");
            attendanceEndVo.setAttendanceDesc("地点异常");
        }*/
        //比较地点 范围内有效数据
        String StnomalDistance = clazzes.getNomalAddress();
        String distance = attendanceEndVo.getDistance();
        if (null ==distance){
            attendanceEndVo.setAttendanceStatus("0");
        }else {
            String[] split = distance.split("\\.");
            String distances=split[0];
            if (Integer.parseInt(distances)>=Integer.parseInt(StnomalDistance)){
                //地点异常
                attendanceEndVo.setAttendanceStatus("1");
                attendanceEndVo.setAttendanceDesc("地点异常");
            }else {
                attendanceEndVo.setAttendanceStatus("0");
            }
        }
        //早退
        if ( hourTime < offHourTime && tempTime<= hourTime) {
            attendanceEndVo.setEndTimeStatus("1");
            //打卡插入数据
            AttendanceEndVo AttendanceEndVo=EndTmieMesg(attendanceEndVo,"");
           /* 统计表插入数据 待优化部分 与打卡功能业务无关*/
            try{
                InsetEndStaticBo insetEndStaticBo = new InsetEndStaticBo();
                insetEndStaticBo.setCreateBy(attendanceEndVo.getPhone());
                //当日的数据 2017-11-2
                insetEndStaticBo.setCreateTime(attendanceEndVo.getAttendanceMonth());
                insetEndStaticBo.setUserName(attendanceEndVo.getUsername());
                insetEndStaticBo.setOfftime(attendanceEndVo.getOfftime());
                //获取当天的上班时间
                Attendance DBattendance = checkAttendance(attendanceEndVo.getPhone(), attendanceEndVo.getAttendanceMonth());
                String startTime=null;
                if (null !=DBattendance && DBattendance.getStartTime()!=null){
                    Date DBstartTime=DBattendance.getStartTime();
                    String dateToStrings = DateUtils.getDateToStrings(DBstartTime);
                    String[] split1 = dateToStrings.split(" ");
                    startTime=split1[1];
                }
                insetEndStaticBo.setStartTime(startTime);
                //设置标准上班时间
                insetEndStaticBo.setStandardTime(nomalStartTime);
                //插入数据到统计表
                insetEndStatic(insetEndStaticBo);
            }catch (Exception e){
                log.debug("插入数据到统计表失败"+e.getMessage());
            }
            return AttendanceEndVo;
            /*正常*/
        } else if (hourTime >= offHourTime) {
            attendanceEndVo.setEndTimeStatus("0");
           /* 插入数据是否使用分布式事务
            DistributedLockUtils lock=new DistributedLockUtils(redisTemplate,"123",300,300);
            try {
                if(lock.lock()){
                    //需要执行的代码
                }
            }catch (InterruptedException e) {
                e.printStackTrace();
            }finally {
                lock.unlock();;
            }
            */
            //插入数据 并返回处理结果
            AttendanceEndVo AttendanceEndVo=EndTmieMesg(attendanceEndVo,"");
            //插入计算统计时长的问题方法
            try{
                InsetEndStaticBo insetStatic = new InsetEndStaticBo();
                insetStatic.setCreateBy(attendanceEndVo.getPhone());
                //当日的数据 2017-11-2
                insetStatic.setCreateTime(attendanceEndVo.getAttendanceMonth());
                insetStatic.setUserName(attendanceEndVo.getUsername());
                insetStatic.setOfftime(attendanceEndVo.getOfftime());
                //获取当天的上班时间
                Attendance DBattendance = checkAttendance(attendanceEndVo.getPhone(), attendanceEndVo.getAttendanceMonth());
                String startTime=null;
                if (null !=DBattendance){
                    Date DBstartTime=DBattendance.getStartTime();
                    if (DBstartTime!=null){
                        String dateToStrings = DateUtils.getDateToStrings(DBstartTime);
                        String[] split1 = dateToStrings.split(" ");
                        startTime=split1[1];
                    }
                }
                insetStatic.setStartTime(startTime);
                insetEndMStatic(insetStatic);
            }catch (Exception e){
                log.debug("插入数据到统计表失败"+e.getMessage());
            }
            return AttendanceEndVo;
        }else if (hourTime<14){
            //早于14点打下班卡
            attendanceEndVo.setEndTimeStatus("1");
            //打卡插入数据
            AttendanceEndVo AttendanceEndVo=EndTmieMesg(attendanceEndVo,"0");
            //早退插入统计表数据
             /* 统计表插入数据 待优化部分 与打卡功能业务无关*/
            try{
                InsetEndStaticBo insetStatic = new InsetEndStaticBo();
                insetStatic.setCreateBy(attendanceEndVo.getPhone());
                //当日的数据 2017-11-2
                insetStatic.setCreateTime(attendanceEndVo.getAttendanceMonth());
                insetStatic.setUserName(attendanceEndVo.getUsername());
                insetStatic.setOfftime(attendanceEndVo.getOfftime());
                //获取当天的上班时间
                Attendance DBattendance = checkAttendance(attendanceEndVo.getPhone(), attendanceEndVo.getAttendanceMonth());
                String startTime=null;
                if (null !=DBattendance){
                    Date DBstartTime=DBattendance.getStartTime();
                    if (DBstartTime!=null){
                        String dateToStrings = DateUtils.getDateToStrings(DBstartTime);
                        String[] split1 = dateToStrings.split(" ");
                        startTime=split1[1];
                    }
                }
                insetStatic.setStartTime(startTime);
                insetEndStatic(insetStatic);
            }catch (Exception e){
                log.debug("插入数据到统计表失败"+e.getMessage());
            }
            return AttendanceEndVo;
        }
        //打卡异常
        return null;
    }
    //TODO 下班卡插入数据并且返回数据
    /* 返回数据封装入口方法
     * @param attendanceEndBo 业务层的bean
     * @return AttendanceEndVo 返回给业务层的bean
     * String status  "0" 正常时间    "1" 打早于12点打下班卡
     */
    @Transactional(readOnly = false)
    public AttendanceEndVo EndTmieMesg(AttendanceEndVo attendanceEndBo,String status){
        //检查是否存在表数据 需要参数 1.手机号 2.年月日
        Attendance Dbattendance= checkAttendance(attendanceEndBo.getPhone(),attendanceEndBo.getAttendanceMonth());
        String attendanceId ="";
        String dailyStatus="0";
        if (null ==Dbattendance ){
            //插入数据插入数据
            Attendance saveAttendance = new Attendance();
            saveAttendance.setAttendanceUser(attendanceEndBo.getUsername());
            String endTime =attendanceEndBo.getAttendanceMonth()+" "+attendanceEndBo.getOfftime();
            Date dates = DateUtils.getStringsToDates(endTime);
            saveAttendance.setEndTime(dates);
            saveAttendance.setAttendanceStatus(attendanceEndBo.getAttendanceStatus());
            saveAttendance.setEndTimeStatus(attendanceEndBo.getEndTimeStatus());
            saveAttendance.setAttendanceDesc(attendanceEndBo.getAttendanceDesc());
            //对日期类进行切割
            String[] array=attendanceEndBo.getAttendanceMonth().split("-");
            String year=array[0];
            String month=array[1];
            String attendanceMonth=year+"-"+month;
            saveAttendance.setAttendanceMonth(attendanceMonth);
            saveAttendance.setEndLocation(attendanceEndBo.getLocation());
            //日报默认是未完成
            saveAttendance.setDailyStatus("0");
            if (dates == null) {
                saveAttendance.setAttendanceDesc("打卡异常");
                //打卡异常 抛异常回滚数据
            }
            this.save(saveAttendance);
            //返回数据 id
            attendanceId=saveAttendance.getId();
        }else if (null !=Dbattendance){
            //考勤数据存在
            Dbattendance.getId();
            Dbattendance.setAttendanceUser(attendanceEndBo.getUsername());
            String endTime =attendanceEndBo.getAttendanceMonth()+" "+attendanceEndBo.getOfftime();
            Date dates = DateUtils.getStringsToDates(endTime);
            Dbattendance.setEndTime(dates);
            Dbattendance.setAttendanceStatus(attendanceEndBo.getAttendanceStatus());
            Dbattendance.setEndTimeStatus(attendanceEndBo.getEndTimeStatus());
            //对日期类进行切割
            String[] array=attendanceEndBo.getAttendanceMonth().split("-");
            String year=array[0];
            String month=array[1];
            String attendanceMonth=year+"-"+month;
            Dbattendance.setAttendanceMonth(attendanceMonth);
            Dbattendance.setEndLocation(attendanceEndBo.getLocation());
            //设置更新时间
            Date date = new Date();
            String dateToStrings = DateUtils.getDateToStrings(date);
            Date stringsToDates = DateUtils.getStringsToDates(dateToStrings);
            Dbattendance.setUpdateTime(stringsToDates);
            if (dates == null) {
                Dbattendance.setAttendanceDesc("打卡异常");
                //异常状态码1 抛异常 回滚数据
            }
            this.save(Dbattendance);
            attendanceId=Dbattendance.getId();
        }else if (null !=Dbattendance && "1".equals(status)){
            //早于12点打卡
            Dbattendance.getId();
            String endTime =attendanceEndBo.getAttendanceMonth()+" "+attendanceEndBo.getOfftime();
            Date dates = DateUtils.getStringsToDates(endTime);
            Dbattendance.setEndTime(dates);
            Dbattendance.setAttendanceStatus(attendanceEndBo.getAttendanceStatus());
            Dbattendance.setEndTimeStatus(attendanceEndBo.getEndTimeStatus());
            //对日期类进行切割
            String[] array=attendanceEndBo.getAttendanceMonth().split("-");
            String year=array[0];
            String month=array[1];
            String attendanceMonth=year+"-"+month;
            Dbattendance.setAttendanceMonth(attendanceMonth);
            Dbattendance.setEndLocation(attendanceEndBo.getLocation());
            //设置更新时间
            Date date = new Date();
            String dateToStrings = DateUtils.getDateToStrings(date);
            Date stringsToDates = DateUtils.getStringsToDates(dateToStrings);
            Dbattendance.setUpdateTime(stringsToDates);
            if (dates == null) {
                Dbattendance.setAttendanceDesc("打卡异常");
                //异常状态码1 抛异常 回滚数据
            }
            this.save(Dbattendance);
            attendanceId=Dbattendance.getId();
        }

        AttendanceEndVo resultAttendance=new AttendanceEndVo();
        resultAttendance.setAttendanceId(attendanceId);
         /*异常状态状态 判断地点的异常 0/正常 1/异常 2/外勤*/
         if (null ==Dbattendance){
             resultAttendance.setAttendanceStatus(attendanceEndBo.getAttendanceStatus());
         }else {
             resultAttendance.setAttendanceStatus(Dbattendance.getAttendanceStatus());
         }
       /* resultAttendance.setIsAttendanceEnd(attendanceEndBo.getIsAttendanceEnd()); 是否打卡不需要*/
        resultAttendance.setOfftime(attendanceEndBo.getOfftime());
        resultAttendance.setLocation(attendanceEndBo.getLocation());
        resultAttendance.setAttendanceDesc(attendanceEndBo.getAttendanceDesc());
        resultAttendance.setEndTimeStatus(attendanceEndBo.getEndTimeStatus());
        resultAttendance.setDailyStatus(dailyStatus);
        return resultAttendance;
    }

    @Transactional(readOnly = false)
    public Map<String, Object> checkAttendanceByDay(String date,PageInfo page) {

        if(page.getPageNum() <= 0) {
            page.setPageNum(1);
        }
        if(page.getPageSize() <= 0) {
            page.setPageSize(10);
        }
        int pageNum = page.getPageNum();
        int pageSize = page.getPageSize();
        PageHelper.startPage(pageNum, pageSize,"startTime");

        List<Map> pageInfo = (List<Map>)dao.checkAttendanceByDay(date);
        Page pi = (Page)pageInfo;
        long total = pi.getTotal();
        Map<String, Object> map = new HashMap<>();
        map.put("pageInfo",pageInfo);
        map.put("total",total);
        map.put("pageCount",pi.getPages());
        return map;
    }


   /* @Autowired
    private ClazzesDao clazzesDao;*/

    public Map<String, Object> checkAttendanceLatterByDay(String date, PageInfo page) {

        if(page.getPageNum() <= 0) {
            page.setPageNum(1);
        }
        if(page.getPageSize() <= 0) {
            page.setPageSize(10);
        }
        //获取班次信息
        Clazzes clazzes = clazzesService.get("c2310c7ec996409b8e91f12daa428a98");
        String nomalStartTime = clazzes.getNomalStartTime();

        Map<String, Object> param = new HashMap<>();
        param.put("date",date);
        param.put("nomalStartTime",nomalStartTime);
        PageHelper.startPage(page.getPageNum(), page.getPageSize(),"startTime DESC");

        List<Map> pageInfo = (List<Map>)this.dao.checkAttendanceLatterByDay(param);
        Page pi = (Page)pageInfo;
        long total = pi.getTotal();
        Map<String, Object> map = new HashMap<>();
        map.put("pageInfo",pageInfo);
        map.put("total",total);
        map.put("pageCount",pi.getPages());
        return map;
    }


    public Map<String,Object> checkAttendanceData(String date) {

        Map<String, Object> map = new HashMap<>();
//        获取班次信息
        Clazzes clazzes = clazzesService.get("c2310c7ec996409b8e91f12daa428a98");
        if(clazzes==null){
            return null;
        }

        int startWork = clazzesService.startWork();
        int endWork = clazzesService.endWork();
        if(startWork>0){
            map.put("startWorkFlag","1");
        }else{
            map.put("startWorkFlag","0");
        }
        if(endWork>0){
            map.put("endWorkFlag","1");
        }else{
            map.put("endWorkFlag","0");
        }

      /*  Date nowTime = new Date();
        DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String format = sdf.format(nowTime);
        int i = format.indexOf(" ");
        format = format.substring(i+1);*/
        String total = clazzes.getTotal();
        map.put("total",total);
//        获取当天打卡人数
        int workCount = this.dao.getWorkCount(date);
        map.put("workCount",workCount);
        map.put("noWorkCount",Integer.parseInt(total)-workCount);

//        获取外勤人数
        int outworkCount = this.dao.getOutworkCount(date);
        map.put("outworkCount",outworkCount);

//       当天迟到人数
        Map<String, Object> param = new HashMap<>();
        String nomalStartTime = clazzes.getNomalStartTime();
        param.put("date",date);
//        param.put("nomalStartTime",nomalStartTime);
        int latterCount = this.dao.getLatterCount(param);
        map.put("latterCount",latterCount);

        return map;
    }

    //记录上班时间数据
    public void  insetStartStatic(String CreateBy,String createTime,String userName){
        Statistics DBstatistics =
                statisticsService.checkAttendanceByCreateByAndCreateTime(CreateBy, createTime);
        if (null==DBstatistics){
            Statistics saveStatistics = new Statistics();
            saveStatistics.setLateTime(1);
            saveStatistics.setUsername(userName);
            statisticsService.save(saveStatistics);
        }else {
            if (0==DBstatistics.getLateTime()){
                DBstatistics.setLateTime(1);
            }else {
                int lateTime = DBstatistics.getLateTime();
                lateTime=lateTime+1;
                statisticsService.save(DBstatistics);
            }
        }
    }

    //统计下班数据
    public void  insetEndStatic(InsetEndStaticBo insetEndStaticBo) {
        Statistics DBstatistics =
                statisticsService.checkAttendanceByCreateByAndCreateTime(insetEndStaticBo.getCreateBy()
                        , insetEndStaticBo.getCreateTime());
        if (null ==DBstatistics){
            //产生统计数据
            Statistics saveStatistics = new Statistics();
            saveStatistics.setEarlyTime(1);
            //如果没打上班卡，使用默认时间来统计时长 14点开始上班 早于 12点打下班卡
            Date date=new Date();
            String dates = DateUtils.getDateToStrings(date);
            String Hour = dates.split(" ")[1].split(":")[0];
            int Hours=Integer.parseInt(Hour);
            String startTime=null;
            if (null==insetEndStaticBo.getStartTime() && Hours<=14){
                startTime=insetEndStaticBo.getStartTime();
            }else if (null==insetEndStaticBo.getStartTime() && Hours>=14){
                startTime = "14:00:00";
            }else {
                startTime=insetEndStaticBo.getStartTime();
            }
            String[] startTimeArry = startTime.split(":");
            int starHour=Integer.parseInt(startTimeArry[0]);
            int starMinute=Integer.parseInt(startTimeArry[1]);
            int starTime=starHour*60+starMinute;
            String offtime = insetEndStaticBo.getOfftime();
            String[]offtimeArry = offtime.split(":");
            int offHour=Integer.parseInt(offtimeArry[0]);
            int offMinute=Integer.parseInt(offtimeArry[1]);
            int offTime=offHour*60+offMinute;
            int saveTime=offTime-starTime;
            saveStatistics.setOfficeTime(saveTime);
            saveStatistics.setUsername(insetEndStaticBo.getUserName());
            statisticsService.save(saveStatistics);
        }else {
            //更新数据
            int earlyTime=0;
            if (DBstatistics.getEarlyTime()!=0){
                int DbearlyTime = DBstatistics.getEarlyTime();
                earlyTime=DbearlyTime+1;
            }else {
                earlyTime=1;
            }
            DBstatistics.setEarlyTime(earlyTime);

            //如果没打上班卡，使用默认时间来统计时长 14点开始上班 早于
            Date date=new Date();
            String dates = DateUtils.getDateToStrings(date);
            String Hour = dates.split(" ")[1].split(":")[0];
            int Hours=Integer.parseInt(Hour);
            String startTime2=null;
            if (null==insetEndStaticBo.getStartTime() && Hours>=14){
                startTime2 = "14:00:00";
            }else if (null==insetEndStaticBo.getStartTime() && Hours<14){
                startTime2=insetEndStaticBo.getStartTime();
            }else {
                startTime2=insetEndStaticBo.getStartTime();
            }
            String[] startTimeArry = startTime2.split(":");
            int starHour=Integer.parseInt(startTimeArry[0]);
            int starMinute=Integer.parseInt(startTimeArry[1]);
            int starTime=starHour*60+starMinute;
            //如果是负数则为0
            String offtime = insetEndStaticBo.getOfftime();
            String[] offtimeArry = offtime.split(":");
            int offHour=Integer.parseInt(offtimeArry[0]);
            int offMinute=Integer.parseInt(offtimeArry[1]);
            /*int offSecond=Integer.parseInt(offtimeArry[2]);
            int offCreated=offHour*3600*1000+offMinute*60*1000+offSecond*1000;*/
            int offTime=offHour*60+ offMinute;
            int saveTime=offTime-starTime;
            DBstatistics.setOfficeTime(saveTime);
            DBstatistics.setUsername(insetEndStaticBo.getUserName());
            statisticsService.save(DBstatistics);
        }
    }

 /**
     * 查询考勤列表数据分页
     * @param pageNum 当前页
     * @param pageSize 显示条数
     * @param attendance  人名 状态  开始时间 结束时间
     * @return map集合
     */
    public Map<String, Object> selectAttendances(Integer pageNum , Integer pageSize, Attendance attendance){
        PageInfo<Map> pageInfo = PageHelper.startPage(pageNum, pageSize,"a.create_time DESC").doSelectPageInfo(new ISelect() {
            @Override
            public void doSelect() {
                dao.selectAttendances(attendance);
            }
        });
        //创建封装数据
        Map<String, Object> dateMap = new HashMap<>();
        //考勤数据
        dateMap.put("attendances",pageInfo.getList());
        //当前页
        dateMap.put("pageNum",pageNum);
        //显示条数
        dateMap.put("pageSize",pageSize);
        //总页数
        dateMap.put("pageCount",pageInfo.getPages());
        //总记录数
        dateMap.put("pageTotal",pageInfo.getTotal());
        return  dateMap;
    }


    /**
     * 查询考勤数据导出
     * @param attendance   人名 状态  开始时间 结束时间
     *          filePath   保存的路径
     * @return 状态码 0
     */
    public String exportExcel( Attendance attendance){
        //获取数据
        List<Attendance> attendanceList = dao.selectExportAttendanceData(attendance);
        Configuration configuration = null;

        StringWriter stringWriter = null;
        BufferedWriter bufferedWriter = null ;
        try {

            /** 创建Configuration配置信息对象,需要指定版本号 */
            configuration = new Configuration(Configuration.VERSION_2_3_25);
            /** 通过Configuration设置模版文件的基础路径 */
            configuration.setClassForTemplateLoading(this.getClass(), "/templates");
            /** 通过Configuration获取指定模版文件对应的模版对象 */
            Template template = configuration.getTemplate("excel.ftl");
            /** 定义模版中需要的数据模型 */
            Map<String, Object> dataModel = new HashMap<>();
            /** 设置数据 */
            dataModel.put("attendanceList", attendanceList);
            dataModel.put("currMonth",attendance.getAttendanceMonth());

            stringWriter = new StringWriter();
            bufferedWriter = new BufferedWriter(stringWriter);
            template.process(dataModel,bufferedWriter);

            return stringWriter.toString() ;
        }catch (Exception e){
            e.printStackTrace();
            throw new RestException("导出失败!");
        }finally {
            try {
                bufferedWriter.flush();
                bufferedWriter.close();
            } catch (IOException e) {
                e.printStackTrace();
            }finally {
                try {
                    stringWriter.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

    }

    /**
     * 通过考勤月份查询当月的考勤数据
     * @param paramMap 封装 attaendanceMonth考勤月份,pageSize分页显示条数,pageNum第几页 三个参数
     * @param phone 用户电话号码
     * @return 考勤数据集合
     */
    public Map<String,Object> getMonthAttendanceData(Map<String,Object> paramMap ,String phone){
        //设置查询参数和排序条件
        int pageSize = (int)paramMap.get("pageSize");
        int pageNum = (int)paramMap.get("pageNum");
        PageHelper.startPage(pageNum,pageSize,"a.create_time ASC");
        paramMap.put("createBy",phone);

        String attaendanceMonth = (String)paramMap.get("attaendanceMonth");
        paramMap.put("attaendanceMonth",attaendanceMonth.replace("/","-"));
        //分页查询并获取分页信息
        List<Map> attendanceList = dao.findAttendanceList(paramMap);
        PageInfo<Attendance> pageInfo =  new PageInfo(attendanceList);

        //创建对象对相应数据进行封装
        Map<String,Object> responseMap = new HashMap<String,Object>();
        List< Map<String,Object>> attendList = new ArrayList<>();

        //获取总页数和总记录数
        responseMap.put("totalPages",pageInfo.getPages());
        responseMap.put("totalCount",pageInfo.getTotal());
        responseMap.put("attendanceList",pageInfo.getList());
        return responseMap;

    }


    //统计正常下班数据 只更新数据不做迟到计算
    public void  insetEndMStatic(InsetEndStaticBo insetEndStaticBo) {
        Statistics DBstatistics =
                statisticsService.checkAttendanceByCreateByAndCreateTime(insetEndStaticBo.getCreateBy()
                        , insetEndStaticBo.getCreateTime());
        if (null ==DBstatistics){
            //产生统计数据
            Statistics saveStatistics = new Statistics();
            //如果没打上班卡，使用默认时间来统计时长 14点开始上班 早于 12点打下班卡
            Date date=new Date();
            String dates = DateUtils.getDateToStrings(date);
            String Hour = dates.split(" ")[1].split(":")[0];
            int Hours=Integer.parseInt(Hour);
            String startTime=null;

            if (null==insetEndStaticBo.getStartTime() && Hours>=14){
                startTime = "14:00:00";
            }else if (null==insetEndStaticBo.getStartTime() && Hours<=14){
                startTime=insetEndStaticBo.getStartTime();
            }else {
                startTime=insetEndStaticBo.getStartTime();
            }
            String[] startTimeArry = startTime.split(":");
            int starHour=Integer.parseInt(startTimeArry[0]);
            int starMinute=Integer.parseInt(startTimeArry[1]);
            int starTime=starHour*60+starMinute;
            String offtime = insetEndStaticBo.getOfftime();
            String[]offtimeArry = offtime.split(":");
            int offHour=Integer.parseInt(offtimeArry[0]);
            int offMinute=Integer.parseInt(offtimeArry[1]);
            int offTime=offHour*60+offMinute;
            int saveTime=offTime-starTime;
            saveStatistics.setOfficeTime(saveTime);
            saveStatistics.setUsername(insetEndStaticBo.getUserName());
            statisticsService.save(saveStatistics);
        }else {
            //如果没打上班卡，使用默认时间来统计时长 14点开始上班 早于
            Date date=new Date();
            String dates = DateUtils.getDateToStrings(date);
            String Hour = dates.split(" ")[1].split(":")[0];
            int Hours=Integer.parseInt(Hour);
            String startTime3=null;
            if (null==insetEndStaticBo.getStartTime() && Hours>=14){
                startTime3 = "14:00:00";
            }else if (null==insetEndStaticBo.getStartTime() && Hours<14){
                startTime3=insetEndStaticBo.getStartTime();
            }else {
                startTime3=insetEndStaticBo.getStartTime();
            }
            String[] startTimeArry = startTime3.split(":");
            int starHour=Integer.parseInt(startTimeArry[0]);
            int starMinute=Integer.parseInt(startTimeArry[1]);
            int starTime=starHour*60+starMinute;
            //如果是负数则为0
            String offtime = insetEndStaticBo.getOfftime();
            String[] offtimeArry = offtime.split(":");
            int offHour=Integer.parseInt(offtimeArry[0]);
            int offMinute=Integer.parseInt(offtimeArry[1]);
            /*int offSecond=Integer.parseInt(offtimeArry[2]);
            int offCreated=offHour*3600*1000+offMinute*60*1000+offSecond*1000;*/
            int offTime=offHour*60+ offMinute;
            int saveTime=offTime-starTime;
            DBstatistics.setOfficeTime(saveTime);
            DBstatistics.setUsername(insetEndStaticBo.getUserName());
            statisticsService.save(DBstatistics);
        }
    }
}