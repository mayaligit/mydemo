package com.cmic.attendance.admin;

import com.cmic.attendance.model.Attendance;
import com.cmic.attendance.model.HolidayAndAttendance;
import com.cmic.attendance.model.WorkStatistics;
import com.cmic.attendance.service.AttendanceService;
import com.cmic.attendance.utils.DateUtils;
import com.cmic.attendance.vo.AttendanceUserVo;
import com.cmic.attendance.vo.EmployeeVo;
import com.cmic.attendance.vo.QueryAttendanceVo;
import com.cmic.saas.base.web.BaseRestController;
import com.cmic.saas.base.web.RestException;
import com.cmic.saas.utils.WebUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @author 何荣
 * @create 2017-10-30 15:56
 **/
@Api(description = "考勤表后台管理")
@RestController
@RequestMapping("/attendance/adminAttendance")
public class AttendanceAdminController extends BaseRestController<AttendanceService> {

    protected Logger logger = LoggerFactory.getLogger(this.getClass());
    /**
     * 查询考勤列表分页
     * @param paramMap
     *  pageSize 显示条数
     *  pageNum 当前页
     *  attendanceUser 名字
     *  attendanceStatus  状态
     *  attendanceMonth 考勤月份
     * @return
     */
    @PostMapping(value="/attendanceList", consumes="application/json")
    public Map<String,Object> selectAttendances(@RequestBody Map<String,String> paramMap,
                                                HttpSession session){
        //获取session中的用户信息
        //AttendanceUserVo attendanceUserVo = (AttendanceUserVo)session.getAttribute("attendanceUserVo");
        Object sessionObject = session.getAttribute("attendanceUserVo");
        AttendanceUserVo attendanceUserVo = null;
        int flag=1;
        String attendance_group=null;
        if(!(sessionObject==null)){
            flag=0;
            attendanceUserVo = (AttendanceUserVo)sessionObject;
            attendance_group = attendanceUserVo.getAttendanceGroup();
        }

        Attendance attendance = new Attendance();
        //获取考勤月份
        if(StringUtils.isNotBlank(paramMap.get("attendanceMonth")) ){
           attendance.setAttendanceMonth(paramMap.get("attendanceMonth"));
        }

         //设置考勤用户
        if(StringUtils.isNotBlank(paramMap.get("attendanceUser"))){
            attendance.setAttendanceUser(paramMap.get("attendanceUser").trim());
        }
        //设置考勤状态
        if(StringUtils.isNotBlank(paramMap.get("attendanceStatus"))){
            attendance.setAttendanceStatus(paramMap.get("attendanceStatus"));
        }

        //设置根据日报状态查询
        if(StringUtils.isNotBlank(paramMap.get("dailyStatus"))){
            attendance.setDailyStatus(Integer.parseInt(paramMap.get("dailyStatus")));
        }

        //设置早退状态
        if(StringUtils.isNotBlank(paramMap.get("endTimeStatus"))){
            attendance.setEndTimeStatus(paramMap.get("endTimeStatus"));
        }

        //设置迟到状态
        if(StringUtils.isNotBlank(paramMap.get("startTimeStatus"))){
            attendance.setStartTimeStatus(paramMap.get("startTimeStatus"));
        }

        //分配不同权限,查看不同东西内容
        List<Integer> roleList =(List<Integer> ) WebUtils.getRequest().getSession().getAttribute("roleList");
        if(roleList.contains(1)){
            attendance.setAttendanceGroup(null);
        }else {
            attendance.setAttendanceGroup(attendance_group);
        }

        Integer pageNum = StringUtils.isBlank(paramMap.get("pageNum")) ? 1 : Integer.parseInt(paramMap.get("pageNum"));
        Integer pageSize = StringUtils.isBlank(paramMap.get("pageSize")) ? 10 : Integer.parseInt(paramMap.get("pageSize"));

        Map<String,Object> dateMap = service.selectAttendances(pageNum,pageSize,attendance);
        dateMap.put("flag",flag);
        return dateMap;
    }

    /**
     * 进行条件导出excel
     * @param attendance 动态封装一下四个参数
     *  attendanceUser 名字
     *  attendanceStatus  状态
     *  attendanceMonth 考勤月份
     *  dailyStatus  日报状态
     */
    @PostMapping("/exportExcel")
    public void exportExcel( Attendance attendance,HttpServletResponse response){
        //返回的是excel的临时
        String data = service.exportExcel( attendance);

        //判断
        if (data != null){
            //先建立一个文件读取流去读取这个临时excel文件
            PrintWriter out = null;
            try {

                //这个一定要设定，告诉浏览器这次请求是一个下载的数据流
                response.setContentType("APPLICATION/OCTET-STREAM");
                response.setHeader("Content-type", "text/html;charset=UTF-8");
                response.setCharacterEncoding("UTF-8");
                response.setHeader("Content-Disposition", "attachment; filename=\"" + attendance.getAttendanceMonth()+".xls" + "\"");
                //获取输出流
                out = response.getWriter();
                out.write(data);
                out.flush();
                out.close();
            } catch (Exception e) {
                e .printStackTrace();
                 throw new RestException("导出失败!");
            }

        }

    }

    @RequestMapping("/attendanceExportExcel")
    public void attendanceExportExcel( Attendance attendance,HttpServletResponse response,HttpSession session){
        Object sessionObject = session.getAttribute("attendanceUserVo");
        int flag=1;
        String attendance_group=null;
        if(!(sessionObject==null)){
            flag=0;
            AttendanceUserVo attendanceUserVo = (AttendanceUserVo)sessionObject;
            attendance_group = attendanceUserVo.getAttendanceGroup();
        }
        //设置后台管理者的所属组
        if(StringUtils.isNotBlank(attendance_group)){
            attendance.setAttendanceGroup(attendance_group);
        }
        //返回的是excel的临时
        String data = service.exportExcel( attendance);

        //判断
        if (data != null){
            //先建立一个文件读取流去读取这个临时excel文件
            PrintWriter out = null;
            try {

                //这个一定要设定，告诉浏览器这次请求是一个下载的数据流
                //response.setContentType("APPLICATION/OCTET-STREAM");
                response.setContentType("application/vnd.ms-excel;charset=utf-8");
                response.setHeader("Content-type", "text/html;charset=UTF-8");
                response.setCharacterEncoding("UTF-8");
                response.setHeader("Content-Disposition", "attachment; filename=\"" + attendance.getAttendanceMonth()+".xls" + "\"");
                //获取输出流
                out = response.getWriter();
                out.write(data);
                out.flush();
                out.close();
            } catch (Exception e) {
                e .printStackTrace();
                throw new RestException("导出失败!");
            }

        }

    }

    @PostMapping("/selectAttendancesToExcel")
    public void selectAttendancesToExcel( Attendance attendance,HttpServletRequest request,HttpServletResponse response){
        try{
            service.selectAttendancesToExcel(attendance,request,response);
        }catch (Exception e){
            e.printStackTrace();
            throw new RestException("考勤导出异常！");
        }
    }


    /**
     * @author 何家来
     * @return
     * 考勤统计,按日统计早到榜
     */
    @ApiOperation(value = "考勤统计", notes = "考勤统计", httpMethod = "POST")
    @RequestMapping(value="/checkAttendanceByDay",method = RequestMethod.POST)
    public Map<String,Object> checkAttendanceByDay(@RequestBody QueryAttendanceVo queryAttendanceVo) {

        Map<String, Object> map = service.checkAttendanceByDay(queryAttendanceVo);
        return map;
    }

    /**
     * @author 何家来
     * @return
     * 考勤统计,按日统计迟到榜
     */
    @ApiOperation(value = "考勤统计", notes = "考勤统计", httpMethod = "POST")
    @RequestMapping(value="/checkAttendanceLatterByDay",method = RequestMethod.POST)
    @ResponseBody
    public Map<String,Object> checkAttendanceLatterByDay(@RequestBody QueryAttendanceVo queryAttendanceVo) {

        Map<String, Object> map = service.checkAttendanceLatterByDay(queryAttendanceVo);
        return map;
    }

    /**
     * @author 何家来
     * @return
     * 考勤统计,按日统计出勤率
     */
    @ApiOperation(value = "考勤统计", notes = "考勤统计", httpMethod = "POST")
    @RequestMapping(value="/checkAttendanceData",method = RequestMethod.POST)
    @ResponseBody
    public Map<String,Object> checkAttendanceData(@RequestBody QueryAttendanceVo queryAttendanceVo) {

        Map<String,Object> map = service.checkAttendanceData(queryAttendanceVo);
        return map;
    }

    /**
     * @author 何家来
     * @return
     * 考勤统计,按日统计勤奋榜
     */
    @RequestMapping(value="/checkAttendanceHardworkingByDay",method = RequestMethod.POST)
    public Map<String,Object> checkAttendanceHardworkingByDay(@RequestBody QueryAttendanceVo queryAttendanceVo) {

        System.out.print("=======按日统计勤奋榜=========="+queryAttendanceVo.getDate()+"=========="+queryAttendanceVo.getPageInfo()+"==========");
        Map<String, Object> map = service.checkAttendanceHardworkingByDay(queryAttendanceVo);
        return map;
    }

    /**
     * @author 何家来
     * @return
     * 考勤统计,按月统计勤奋榜
     */
    @ApiOperation(value = "考勤统计", notes = "考勤统计", httpMethod = "POST")
    @RequestMapping(value="/checkAttendanceHardworkingByMonth",method = RequestMethod.POST)
    @ResponseBody
    public Map<String,Object> checkAttendanceHardworkingByMonth(@RequestBody QueryAttendanceVo queryAttendanceVo) {

        System.out.print("=======按月统计勤奋榜=========="+queryAttendanceVo.getDate()+"=========="+queryAttendanceVo.getPageInfo()+"==========");
        Map<String, Object> map = service.checkAttendanceHardworkingByMonth(queryAttendanceVo);
        return map;
    }

    /**
     * @author 何家来
     * @return
     * 考勤统计,按月统计迟到榜
     */
    @ApiOperation(value = "考勤统计", notes = "考勤统计", httpMethod = "POST")
    @RequestMapping(value="/checkAttendanceLatterByMonth",method = RequestMethod.POST)
    @ResponseBody
    public Map<String,Object> checkAttendanceLatterByMonth(@RequestBody QueryAttendanceVo queryAttendanceVo) {

        Map<String, Object> map = service.checkAttendanceLatterByMonth(queryAttendanceVo);
        return map;
    }

    /**
     * @author 何家来
     * @return
     * 获取当前系统时间
     */
    @ApiOperation(value = "获取当前系统时间", notes = "获取当前系统时间", httpMethod = "GET")
    @RequestMapping(value="/getNowDate",method = RequestMethod.GET)
    @ResponseBody
    public Map getNowDate() {

        HttpServletRequest request = WebUtils.getRequest();
        AttendanceUserVo attendanceUserVo = (AttendanceUserVo)request.getSession().getAttribute("attendanceUserVo");
        logger.debug(">>>>>>>>>>>>>>>>>>>>>>>登录成功session"+attendanceUserVo.getAttendanceUsername()+"<<<<<<<<<<<<<<<<<<<<<");
        Map map = new HashMap<>();
        map.put("flag",0);
        if(null == attendanceUserVo){
            map.put("flag",1);
            return map;
        }

        Date date = new Date();
        String nowDate = DateUtils.getDateToYearMonthDay(date);
        Calendar c=Calendar.getInstance();
        c.setTime(date);
        int weekday=c.get(Calendar.DAY_OF_WEEK)-1;
        String[] weekDays = {"星期日", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六"};

        map.put("nowDate",nowDate);
        map.put("weekday",weekDays[weekday]);
        return map;
    }


    /**
     * @author hjl
     * 进行条件导出,今日打卡和未打卡数据excel
     * @param
     */
    @RequestMapping("/exportAttendanceExcel")
    public void exportAttendanceExcel(EmployeeVo employeeVo, HttpServletResponse response){
        //返回的是excel的临时
        String data = service.exportAttendanceExcel(employeeVo);

        String fileName = null;
        StringBuffer sb = new StringBuffer();
        String[] split = employeeVo.getDate().split("-");
        for(String a:split){
            sb.append(a);
        }
        if("1".equals(employeeVo.getAttFlag())){
            fileName = sb.toString()+"打卡员工信息";
        }else{
            fileName = sb.toString()+"未打卡员工信息";
        }
        //判断
        if (data != null){
            //先建立一个文件读取流去读取这个临时excel文件
            PrintWriter out = null;
            try {
                //这个一定要设定，告诉浏览器这次请求是一个下载的数据流
                response.setContentType("APPLICATION/OCTET-STREAM");
                response.setHeader("Content-type", "text/html;charset=UTF-8");

                response.setHeader("Content-Disposition", "attachment; filename=\"" + new String(fileName.getBytes(),"iso-8859-1")+".xls" + "\"");
                //获取输出流
                out = response.getWriter();
                out.write(data);
                out.flush();
                out.close();
            } catch (Exception e) {
                e .printStackTrace();
                throw new RestException("导出失败!");
            }

        }

    }

    /**
     * @author 陈志豪
     * @return
     * 考勤统计,统计某个月每个人的请假总时长和总的出勤天数
     */
    @RequestMapping(value="/statisticsHolidayAndAttendance",method = RequestMethod.POST)
    @ResponseBody
    public Map<String,Object> statisticsHolidayAndAttendance(@RequestBody WorkStatistics workStatistics) {
        //SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMM");
        try{
            StringBuilder sb = new StringBuilder();
            String month=sb.append(workStatistics.getMonth()).insert(4,"-").toString();
            List<HolidayAndAttendance> list=service.statisticsHolidayAndAttendance(month);
            HashMap<String,Object> map =new HashMap();
            map.put("list",list);
            return map;
        }catch (Exception e){
            e.printStackTrace();
            throw new RestException("统计异常");
        }
    }

}
