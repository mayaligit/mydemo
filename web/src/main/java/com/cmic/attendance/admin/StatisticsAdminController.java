package com.cmic.attendance.admin;

import com.cmic.attendance.model.Statistics;
import com.cmic.attendance.service.StatisticsService;
import com.cmic.attendance.utils.DateUtils;
import com.cmic.saas.base.web.BaseRestController;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.*;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.servlet.http.HttpServletResponse;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @author 何家来
 * @create 2017-11-02 17:32
*/
@Api(description = "统计表管理")
@RestController
@RequestMapping("/adminStatistics")
public class StatisticsAdminController extends BaseRestController<StatisticsService> {

    @ApiOperation(value = "查询", notes = "查询统计表列表", httpMethod = "GET")
    @ApiImplicitParams({
        @ApiImplicitParam(name = "orderBy", value="排序", defaultValue = "createDate desc", paramType = "query"),
        @ApiImplicitParam(name = "pageSize", value="分页大小", defaultValue = "10", paramType = "query"),
        @ApiImplicitParam(name = "pageNum", value="页码", defaultValue = "1", paramType = "query")
    })
    @RequestMapping(value="/", method = RequestMethod.GET)
    public PageInfo<Statistics> get(@ApiIgnore Statistics statistics, @ApiIgnore PageInfo page){
        page = service.findPage(page, statistics);
        return page;
    }

    @ApiOperation(value = "新增", notes = "新增统计表", httpMethod = "POST")
    @RequestMapping(value="/", method = RequestMethod.POST)
    public Statistics post(@Validated @RequestBody Statistics statistics){
        service.insert(statistics);
        return statistics;
    }

    @ApiOperation(value = "获取", notes = "获取统计表", httpMethod = "GET")
    @RequestMapping(value="/{id}", method = RequestMethod.GET)
    public Statistics get(@ApiParam(value = "统计表ID") @PathVariable String id){
        Statistics statistics = service.get(id);
        return statistics;
    }

    @ApiOperation(value = "新增/更新", notes = "新增/更新统计表", httpMethod = "PUT")
    @RequestMapping(value="/{id}", method = RequestMethod.PUT)
    public Statistics put(@PathVariable String id, @Validated @RequestBody Statistics statistics){
        statistics.setId(id);
        service.save(statistics);
        return get(id);
    }

    @ApiOperation(value = "动态更新", notes = "动态更新统计表", httpMethod = "PATCH")
    @RequestMapping(value="/{id}", method = RequestMethod.PATCH)
    public Statistics patch(@ApiParam(value = "统计表ID") @PathVariable String id, @RequestBody Statistics statistics){
        statistics.setId(id);
        service.dynamicUpdate(statistics);
        return get(id);
    }

    @ApiOperation(value = "删除", notes = "删除统计表", httpMethod = "DELETE")
    @RequestMapping(value="/{id}", method = RequestMethod.DELETE)
    public void delete(@ApiParam(value = "统计表ID") @PathVariable String id){
        service.delete(id);
    }


    /**
     * @author 何家来
     * @return
     * 考勤统计,按日统计勤奋榜
     */
    @ApiOperation(value = "考勤统计", notes = "考勤统计", httpMethod = "POST")
    @RequestMapping(value="/checkAttendanceHardworkingByDay",method = RequestMethod.POST)
    @ResponseBody
    public Map<String,Object> checkAttendanceHardworkingByDay(HttpServletResponse response, String date, @RequestBody PageInfo page) {

//        response.setHeader("Access-Control-Allow-Origin", "*");
        Map<String, Object> map = service.checkAttendanceHardworkingByDay(date, page);
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
    public Map<String,Object> checkAttendanceHardworkingByMonth(HttpServletResponse response, String date,@RequestBody PageInfo page) {

//        response.setHeader("Access-Control-Allow-Origin", "*");
        Map<String, Object> map = service.checkAttendanceHardworkingByMonth(date, page);
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
    public Map<String,Object> checkAttendanceLatterByMonth(HttpServletResponse response, String date, @RequestBody PageInfo page) {

//        response.setHeader("Access-Control-Allow-Origin", "*");
        Map<String, Object> map = service.checkAttendanceLatterByMonth(date, page);
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

//        response.setHeader("Access-Control-Allow-Origin", "*");
        Date date = new Date();
        String nowDate = DateUtils.getDateToString(date);
        Calendar c=Calendar.getInstance();
        c.setTime(date);
        int weekday=c.get(Calendar.DAY_OF_WEEK)-1;
        String[] weekDays = {"星期日", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六"};

        Map map = new HashMap<String,Object>();
        map.put("nowDate",nowDate);
        map.put("weekday",weekDays[weekday]);
        return map;
    }

}