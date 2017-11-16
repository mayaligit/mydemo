package com.cmic.attendance.web;

import com.cmic.attendance.filter.LogFilter;
import com.cmic.attendance.model.AttendanceUser;
import com.cmic.attendance.vo.AttendanceUserVo;
import com.cmic.saas.utils.StringUtils;
import com.cmic.saas.utils.WebUtils;
import com.github.pagehelper.PageInfo;
import com.cmic.saas.base.web.BaseRestController;
import com.cmic.attendance.service.AttendanceUserService;
import io.swagger.annotations.*;
import org.apache.log4j.Logger;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

/**
* 考勤后台管理表Controller
*/
@Api(description = "考勤后台管理表管理")
@RestController
@RequestMapping("/attendance/user")
public class AttendanceUserController extends BaseRestController<AttendanceUserService> {

    private static Logger log = Logger.getLogger(AttendanceUserController.class);

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
    @ResponseBody
    public HashMap<String,String> login(@RequestBody AttendanceUserVo attendanceUserVo){
        HashMap<String,String> map = new HashMap<>();
//        验证码是否为空
        if(StringUtils.isBlank(attendanceUserVo.getCheckCode())) {
            map.put("status", "4");
            return map;
        }
        String checkCode = (String)WebUtils.getSession().getAttribute("checkCode");

        if(StringUtils.isNotBlank(checkCode)){
//            验证码是否不正在正确
            if(!attendanceUserVo.getCheckCode().equals(checkCode)){

                map.put("status","3");
                return map;
            }
        }
        HashMap<String, String> login = service.login(attendanceUserVo);
        if ("0".equals(login.get("status"))){
            WebUtils.getSession().setAttribute("attendanceUser",attendanceUserVo);
            log.debug("登录成功的session"+attendanceUserVo.toString());
        }
        return login;
    }
}