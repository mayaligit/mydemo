package com.cmic.attendance.web;

import com.cmic.attendance.model.Attendance;
import com.cmic.attendance.service.AttendanceService;
import com.cmic.attendance.utils.DateUtils;
import com.cmic.attendance.vo.AttendanceEndVo;
import com.cmic.attendance.vo.AttendanceVo;
import com.cmic.saas.base.model.BaseAdminEntity;
import com.cmic.saas.base.web.BaseRestController;
import com.cmic.saas.base.web.RestException;
import com.cmic.saas.utils.WebUtils;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.Map;

/**
 * 考勤表Controller
 */
@Api(description = "考勤表管理")
@RestController
@RequestMapping("/attendance")
public class AttendanceController extends BaseRestController<AttendanceService> {

    private static Logger log = Logger.getLogger(AttendanceController.class);

    @Value("${clazzesService.id}")
    private String clazzesId;

    @ApiOperation(value = "查询", notes = "查询考勤表列表", httpMethod = "GET")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "orderBy", value="排序", defaultValue = "createDate desc", paramType = "query"),
            @ApiImplicitParam(name = "pageSize", value="分页大小", defaultValue = "10", paramType = "query"),
            @ApiImplicitParam(name = "pageNum", value="页码", defaultValue = "1", paramType = "query")
    })
    @RequestMapping(value="/aa", method = RequestMethod.GET)
    public PageInfo<Attendance> get(@ApiIgnore Attendance attendance, @ApiIgnore PageInfo page){
        page = service.findPage(page, attendance);
        return page;
    }

    @ApiOperation(value = "新增", notes = "新增考勤表", httpMethod = "GET")
    @RequestMapping(value="/insertAttence", method = RequestMethod.GET)
    public Attendance post(@Validated @RequestBody Attendance attendance){
        service.insert(attendance);
        return attendance;
    }

    /*@ApiOperation(value = "获取", notes = "获取考勤表", httpMethod = "GET")
    @RequestMapping(value="/{id}", method = RequestMethod.GET)
    public Attendance get(@ApiParam(value = "考勤表ID") @PathVariable String id){
        Attendance attendance = service.get(id);
        return attendance;
    }*/

    /*@ApiOperation(value = "新增/更新", notes = "新增/更新考勤表", httpMethod = "PUT")
    @RequestMapping(value="/{id}", method = RequestMethod.PUT)
    public Attendance put(@PathVariable String id, @Validated @RequestBody Attendance attendance){
        attendance.setId(id);
        service.save(attendance);
        return get(id);
    }*/

   /* @ApiOperation(value = "动态更新", notes = "动态更新考勤表", httpMethod = "PATCH")
    @RequestMapping(value="/{id}", method = RequestMethod.PATCH)
    public Attendance patch(@ApiParam(value = "考勤表ID") @PathVariable String id, @RequestBody Attendance attendance){
        attendance.setId(id);
        service.dynamicUpdate(attendance);
        return get(id);
    }*/

   /* @ApiOperation(value = "删除", notes = "删除考勤表", httpMethod = "DELETE")
    @RequestMapping(value="/{id}", method = RequestMethod.DELETE)
    public void delete(@ApiParam(value = "考勤表ID") @PathVariable String id){
        service.delete(id);
    }*/
//TODO 上班卡初始化业务需要的数据
    /**
     * 上班打卡页面初始化需要的是数据
     */
    @ApiOperation(value = "获取服务器数据", notes = "获取服务器时间", httpMethod = "POST")
    @RequestMapping(value="/getStartServerMesg",method =RequestMethod.POST)
    @ResponseBody
    public AttendanceVo getStartServerMesg() {
        HttpServletResponse response = WebUtils.getRequestAttributes().getResponse();
       /* response.setHeader("Access-Control-Allow-Origin", "*");*/
        HttpServletRequest request = WebUtils.getRequest();
        /*//测试数据
        BaseAdminEntity user2=new BaseAdminEntity();
        user2.setId("18503096229");
        user2.setName("18503096229");
        request.getSession().setAttribute("_CURRENT_ADMIN_INFO",user2);*/
        //测试数据结束*/
        BaseAdminEntity user= (BaseAdminEntity)request.getSession().getAttribute("_CURRENT_ADMIN_INFO");
        Date serverTime=new Date();
        Long serverTimes=serverTime.getTime();
        String serverDate=DateUtils.getDateToYearMonthDay(serverTime);
        //检查当前用户是否已经打卡
        Attendance DBattendance=service.checkAttendance(user.getId(),serverDate);
        AttendanceVo attendanceVo = new AttendanceVo();
        if (null !=DBattendance && null !=DBattendance.getStartTime() ){
            log.debug("用户已经打过卡了");
            //是否已经打卡;
            /**考勤UUID 打卡的时间 地址 日报状态0/未完成,1/已完成  是否异常 打卡状态 是否打卡*/
            Date dat= DBattendance.getStartTime();
            String dates=DateUtils.getDateToStrings(dat);
            String datass=dates.split(" ")[1];
            attendanceVo.setAttendanceId(DBattendance.getId());
            attendanceVo.setAttendanceHour(datass);
            attendanceVo.setLocation(DBattendance.getStartLocation());
            attendanceVo.setDailyStatus(DBattendance.getDailyStatus());
            attendanceVo.setAttendanceStatus(DBattendance.getAttendanceStatus());
            attendanceVo.setStartTimeStatus(DBattendance.getStartTimeStatus());
            attendanceVo.setIsAttendanceStart("0");
            //判断是否打过下班卡了
            if ( DBattendance.getEndTime() !=null){
                attendanceVo.setIsAttendanceEnd("0");
            }else {
                attendanceVo.setIsAttendanceEnd("1");
            }
        }

        if (null !=DBattendance && DBattendance.getEndTime()!=null){
            //打过下班卡
            /**考勤UUID 打卡的时间 地址 日报状态0/未完成,1/已完成  是否异常 打卡状态 是否打卡*/
            Date dat= DBattendance.getEndTime();
            String dates=DateUtils.getDateToStrings(dat);
            String datass=dates.split(" ")[1];
            attendanceVo.setAttendanceId(DBattendance.getId());
            attendanceVo.setOfftime(datass);
            attendanceVo.setEndLocation(DBattendance.getEndLocation());
            attendanceVo.setDailyStatus(DBattendance.getDailyStatus());
            attendanceVo.setAttendanceStatus(DBattendance.getAttendanceStatus());
            attendanceVo.setEndTimeStatus(DBattendance.getEndTimeStatus());
            attendanceVo.setIsAttendanceEnd("0");
            //判断是否打过上班卡了
            if ( DBattendance.getStartTime() !=null){
                attendanceVo.setIsAttendanceStart("0");
            }else {
                attendanceVo.setIsAttendanceStart("1");
            }
        }else if (null ==DBattendance){
            attendanceVo.setIsAttendanceStart("1");
            attendanceVo.setIsAttendanceEnd("1");
        }
        attendanceVo.setUsername(user.getName());
        attendanceVo.setPhone(user.getId());
        attendanceVo.setDate(serverDate);
        attendanceVo.setServerTime(serverTimes.toString());
        return attendanceVo;
    }
    /**
     * 上班卡业务业务
     */
    @ApiOperation(value = "上班打卡", notes = "上班打卡接口", httpMethod = "POST")
    @RequestMapping(value = "/punchCardStart",method = RequestMethod.POST)
    public AttendanceVo punchCardStart(@RequestBody AttendanceVo attendanceVo){
        HttpServletResponse response =WebUtils.getRequestAttributes().getResponse();
      /*  response.setHeader("Access-Control-Allow-Origin", "*");*/
        attendanceVo.setClazzesId(this.clazzesId);
        Attendance attendanceBo = service.punchCard(attendanceVo);

        AttendanceVo resultAttendanceVo =new AttendanceVo();
        resultAttendanceVo.setAttendanceId(attendanceBo.getId());
        resultAttendanceVo.setUsername(attendanceBo.getAttendanceUser());
        resultAttendanceVo.setPhone(attendanceVo.getPhone());
        resultAttendanceVo.setAttendanceStatus(attendanceBo.getAttendanceStatus());
        resultAttendanceVo.setAttendanceDesc(attendanceBo.getAttendanceDesc());
        resultAttendanceVo.setDailyStatus(attendanceBo.getDailyStatus());
        resultAttendanceVo.setLocation(attendanceBo.getStartLocation());
        resultAttendanceVo.setStartTimeStatus(attendanceBo.getStartTimeStatus());
        resultAttendanceVo.setAttendanceMonth(attendanceBo.getAttendanceMonth());
        resultAttendanceVo.setLocationStatus(attendanceBo.getAttendanceStatus());
        log.debug("》》》》返回上班时间"+DateUtils.getDateToHourMinuteS(attendanceBo.getStartTime()));
        resultAttendanceVo.setAttendanceHour(DateUtils.getDateToHourMinuteS(attendanceBo.getStartTime()));
        resultAttendanceVo.setIsAttendanceStart("0");
        return resultAttendanceVo;
    }


    //TODO 下班卡业务
    /**
     * 下班卡 AttendanceEndVo
     */
    @ApiOperation(value = "下班打卡", notes = "下班打卡", httpMethod = "POST")
    @RequestMapping(value = "/punchCardEnd",method = RequestMethod.POST)
    public AttendanceEndVo punchCardEnd(@RequestBody AttendanceEndVo attendanceEndVo){
        HttpServletResponse response =WebUtils.getRequestAttributes().getResponse();
        /*response.setHeader("Access-Control-Allow-Origin", "*");*/
        log.debug("attendanceEndVo"+attendanceEndVo.toString());
        attendanceEndVo.setClazzesId(this.clazzesId);
        AttendanceEndVo resultAttendance= service.punchCardEnd(attendanceEndVo);
        return resultAttendance;
    }

    /**
     *
     * @param paramMap 封装 attaendanceMonth考勤月份,attaendancePageSize分页显示条数,attaendancePageNum第几页 三个参数
     * @return 考勤统计数据
     */
    @ApiOperation(value = "月度考勤统计", notes = "月度考勤统计接口", httpMethod = "GET")
    @RequestMapping(value = "/statisticsMonthAttendance")
    public Map<String,Object> statisticsMonthAttendance(@RequestBody Map<String,Object> paramMap, HttpServletRequest request){
        if(paramMap == null || paramMap.get("pageSize")==null||paramMap.get("pageNum")==null) {
            return null ;
        }

        //以下注释 在生产及联合测试的环境中使用
        Object obj = WebUtils.getSession().getAttribute("_CURRENT_ADMIN_INFO");
        if(obj == null || !(obj instanceof BaseAdminEntity)){
            /*throw new RestException("用户登陆异常");*/
            paramMap.put("status","0");
            paramMap.put("meg","用户登录时间过期");
            return paramMap;

        }
        BaseAdminEntity user = (BaseAdminEntity) obj;
        String userPhone = user.getId();

        //开发测试使用
       /* String userPhone = "13802885145";*/

        return service.getMonthAttendanceData(paramMap,userPhone);

    }
}