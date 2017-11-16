package com.cmic.attendance.web;

import com.cmic.attendance.model.AttendanceUser;
import com.cmic.saas.utils.WebUtils;
import com.github.pagehelper.PageInfo;
import com.cmic.saas.base.web.BaseRestController;
import com.cmic.attendance.service.AttendanceUserService;
import io.swagger.annotations.*;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import java.util.HashMap;
import java.util.Map;

/**
* 考勤后台管理表Controller
*/
@Api(description = "考勤后台管理表管理")
@RestController
@RequestMapping("/attendance/user")
public class AttendanceUserController extends BaseRestController<AttendanceUserService> {

    @ApiOperation(value = "查询", notes = "查询考勤后台管理表列表", httpMethod = "GET")
    @ApiImplicitParams({
        @ApiImplicitParam(name = "orderBy", value="排序", defaultValue = "createDate desc", paramType = "query"),
        @ApiImplicitParam(name = "pageSize", value="分页大小", defaultValue = "10", paramType = "query"),
        @ApiImplicitParam(name = "pageNum", value="页码", defaultValue = "1", paramType = "query")
    })
    @RequestMapping(value="/", method = RequestMethod.GET)
    public PageInfo<AttendanceUser> get(@ApiIgnore AttendanceUser attendanceUser, @ApiIgnore PageInfo page){
        page = service.findPage(page, attendanceUser);
        return page;
    }

    @ApiOperation(value = "新增", notes = "新增考勤后台管理表", httpMethod = "POST")
    @RequestMapping(value="/", method = RequestMethod.POST)
    public AttendanceUser post(@Validated @RequestBody AttendanceUser attendanceUser){
        service.insert(attendanceUser);
        return attendanceUser;
    }

    @ApiOperation(value = "获取", notes = "获取考勤后台管理表", httpMethod = "GET")
    @RequestMapping(value="/{id}", method = RequestMethod.GET)
    public AttendanceUser get(@ApiParam(value = "考勤后台管理表ID") @PathVariable String id){
        AttendanceUser attendanceUser = service.get(id);
        return attendanceUser;
    }

    @ApiOperation(value = "新增/更新", notes = "新增/更新考勤后台管理表", httpMethod = "PUT")
    @RequestMapping(value="/{id}", method = RequestMethod.PUT)
    public AttendanceUser put(@PathVariable String id, @Validated @RequestBody AttendanceUser attendanceUser){
        attendanceUser.setId(id);
        service.save(attendanceUser);
        return get(id);
    }

    @ApiOperation(value = "动态更新", notes = "动态更新考勤后台管理表", httpMethod = "PATCH")
    @RequestMapping(value="/{id}", method = RequestMethod.PATCH)
    public AttendanceUser patch(@ApiParam(value = "考勤后台管理表ID") @PathVariable String id, @RequestBody AttendanceUser attendanceUser){
        attendanceUser.setId(id);
        service.dynamicUpdate(attendanceUser);
        return get(id);
    }

    @ApiOperation(value = "删除", notes = "删除考勤后台管理表", httpMethod = "DELETE")
    @RequestMapping(value="/{id}", method = RequestMethod.DELETE)
    public void delete(@ApiParam(value = "考勤后台管理表ID") @PathVariable String id){
        service.delete(id);
    }


    @ApiOperation(value = "用户登录", notes = "用户登录", httpMethod = "POST")
    @RequestMapping(value="/login", method = RequestMethod.POST)
    public Map login(@RequestBody AttendanceUser attendanceUser){
        HashMap<String,String> result=new HashMap<String,String>();

        Map<String, String> login = service.login(attendanceUser);
        if ("0".equals(login.get("status"))){
            WebUtils.getSession().setAttribute("attendanceUser",attendanceUser);
        }
        return result;
    }
}