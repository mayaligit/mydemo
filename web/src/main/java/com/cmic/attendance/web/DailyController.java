package com.cmic.attendance.web;

import com.cmic.attendance.model.Daily;
import com.cmic.attendance.service.DailyService;
import com.cmic.attendance.vo.DailyVo;
import com.cmic.saas.base.web.BaseRestController;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.*;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

/**
* 日报表Controller
*/
@Api(description = "日报表管理")
@RestController
@RequestMapping("/attendance/daily")
public class DailyController extends BaseRestController<DailyService> {

    private static Logger log = Logger.getLogger(CentifyUserController.class);

    @ApiOperation(value = "查询", notes = "查询日报表列表", httpMethod = "GET")
    @ApiImplicitParams({
        @ApiImplicitParam(name = "orderBy", value="排序", defaultValue = "createDate desc", paramType = "query"),
        @ApiImplicitParam(name = "pageSize", value="分页大小", defaultValue = "10", paramType = "query"),
        @ApiImplicitParam(name = "pageNum", value="页码", defaultValue = "1", paramType = "query")
    })
    @RequestMapping(value="/", method = RequestMethod.GET)
    public PageInfo<Daily> get(@ApiIgnore Daily daily, @ApiIgnore PageInfo page){
        page = service.findPage(page, daily);
        return page;
    }

    @ApiOperation(value = "新增", notes = "新增日报表", httpMethod = "POST")
    @RequestMapping(value="/save", method = RequestMethod.POST)
    public Map post(HttpServletResponse response, @RequestBody DailyVo dailyVo, HttpServletRequest request){

//        response.setHeader("Access-Control-Allow-Origin", "*");
        HashMap<String, Object> map = new HashMap<>();
        map.put("status",0);

        log.debug("==========测试前端是否传考勤ID上来==="+dailyVo.getAttendanceId()+"=====");

        if(StringUtils.isBlank(dailyVo.getDailyTitle())){
            map.put("status",1);//标题为空
            return map;
        }

       /* if(StringUtils.isBlank(dailyVo.getYesterdayUnfinished()) ||
                StringUtils.isBlank(dailyVo.getYesterdayFinished()) ||
                StringUtils.isBlank(dailyVo.getYesterdayPlan()) ||
                StringUtils.isBlank(dailyVo.getTodayPlan())){
            map.put("status",2);//日报内容为空
            return map;
        }*/
        if(StringUtils.isBlank(dailyVo.getUnfinishedWork()) || StringUtils.isBlank(dailyVo.getFinishedWork())){
            map.put("status",2);//日报内容为空
            return map;
        }

        String msg = service.insertDailyAndAttendance(dailyVo);
        if(StringUtils.isNotBlank(msg)){
            if(msg.equals("0")){
                map.put("status",3);//日报已存在
            }
            if(msg.equals("1")){
                map.put("status",4);//登录超时
            }

        }
        return map;
    }

    @ApiOperation(value = "获取", notes = "获取日报表", httpMethod = "GET")
    @RequestMapping(value="/{attendanceId}", method = RequestMethod.GET)
    public Daily get(HttpServletResponse response,@PathVariable String attendanceId){

        response.setHeader("Access-Control-Allow-Origin", "*");
        if(StringUtils.isBlank(attendanceId)){
            return null;
        }
        Daily daily = service.get(attendanceId);
        return daily;
    }



    @ApiOperation(value = "删除", notes = "删除日报表", httpMethod = "DELETE")
    @RequestMapping(value="/{id}", method = RequestMethod.DELETE)
    public void delete(@ApiParam(value = "日报表ID") @PathVariable String id){
        service.delete(id);
    }


}