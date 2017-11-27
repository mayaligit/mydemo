package com.cmic.attendance.admin;

import com.cmic.attendance.model.Attendance;
import com.cmic.attendance.service.AttendanceService;
import com.cmic.attendance.vo.AttendanceUserVo;
import com.cmic.attendance.vo.QueryAttendanceVo;
import com.cmic.saas.base.web.BaseRestController;
import com.cmic.saas.base.web.RestException;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.PrintWriter;
import java.util.Map;

/**
 * @author 何荣
 * @create 2017-10-30 15:56
 **/
@Api(description = "考勤表后台管理")
@RestController
@RequestMapping("/adminAttendance")
public class AttendanceAdminController extends BaseRestController<AttendanceService> {
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
        AttendanceUserVo attendanceUserVo = (AttendanceUserVo)session.getAttribute("attendanceUserVo");
        String attendance_group = attendanceUserVo.getAttendanceGroup();
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

        //设置后台管理者的所属组  todo 用false 把分组条件关了，要用的时候打开
        if(false&&StringUtils.isNotBlank(attendance_group)){
            attendance.setAttendanceGroup(attendance_group);
        }

        Integer pageNum = paramMap.get("pageNum") == null ? 1 : Integer.parseInt(paramMap.get("pageNum"));
        Integer pageSize = paramMap.get("pageSize") == null ? 10 : Integer.parseInt(paramMap.get("pageSize"));

        Map<String,Object> dateMap = service.selectAttendances(pageNum,pageSize,attendance);
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

            } catch (Exception e) {
                e .printStackTrace();
                 throw new RestException("导出失败!");
            }finally {
                out.close();
            }

        }

    }




    /**
     * @author 何家来
     * @return
     * 考勤统计,按日统计早到榜
     */
    @ApiOperation(value = "考勤统计", notes = "考勤统计", httpMethod = "POST")
    @RequestMapping(value="/checkAttendanceByDay",method = RequestMethod.POST)
//    @ResponseBody
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
    @ApiOperation(value = "考勤统计", notes = "考勤统计", httpMethod = "GET")
    @RequestMapping(value="/checkAttendanceData",method = RequestMethod.GET)
    @ResponseBody
    public Map<String,Object> checkAttendanceData(@RequestBody QueryAttendanceVo queryAttendanceVo) {

        Map<String,Object> map = service.checkAttendanceData(queryAttendanceVo);
        return map;
    }

}
