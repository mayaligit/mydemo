package com.cmic.attendance.web;

import com.cmic.attendance.model.WorkStatistics;
import com.cmic.attendance.service.WorkStatisticsService;
import com.github.pagehelper.PageInfo;
import com.cmic.saas.base.web.BaseRestController;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

/**
 * Controller
 */
@Api(description = "管理")
@RestController
@RequestMapping("/work")
public class WorkStatisticsController extends BaseRestController<WorkStatisticsService> {

    @Autowired
    WorkStatisticsService workStatisticsService;

    @ApiOperation(value = "查询", notes = "查询列表", httpMethod = "GET")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "phone", value = "手机号", paramType = "query"),
            @ApiImplicitParam(name = "userName", value = "用户名", paramType = "query"),
            @ApiImplicitParam(name = "id", value = "", paramType = "query"),
            @ApiImplicitParam(name = "month", value = "月份（字符串）。例如：2017-11", paramType = "query"),
            @ApiImplicitParam(name = "orderBy", value = "排序", defaultValue = "createDate desc", paramType = "query"),
            @ApiImplicitParam(name = "pageSize", value = "分页大小", defaultValue = "10", paramType = "query"),
            @ApiImplicitParam(name = "pageNum", value = "页码", defaultValue = "1", paramType = "query")
    })
    @RequestMapping(value = "/findPage", method = RequestMethod.GET)
    public PageInfo<WorkStatistics> get(@ApiIgnore WorkStatistics workStatistics, @ApiIgnore PageInfo page) {
        page = service.findPage(page, workStatistics);
        return page;
    }

    @ApiOperation(value = "工作统计", notes = "工作统计", httpMethod = "GET")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "phone", value = "手机号", paramType = "query"),
            @ApiImplicitParam(name = "userName", value = "用户名", paramType = "query"),
            @ApiImplicitParam(name = "id", value = "", paramType = "query"),
            @ApiImplicitParam(name = "month", value = "月份（字符串）。例如：2017-11", paramType = "query"),
            @ApiImplicitParam(name = "orderBy", value = "排序", defaultValue = "createDate desc", paramType = "query"),
            @ApiImplicitParam(name = "pageSize", value = "分页大小", defaultValue = "10", paramType = "query"),
            @ApiImplicitParam(name = "pageNum", value = "页码", defaultValue = "1", paramType = "query")
    })
    @RequestMapping(value = "/workStatistics", method = RequestMethod.GET)
    public WorkStatistics workStatistics(@ApiIgnore  WorkStatistics workStatistics) {
        System.out.println("abc");
        WorkStatistics workStatistics2 =null;
        try{
            workStatistics2  = workStatisticsService.workStatistics(workStatistics);
            return workStatistics2;
        }catch (Exception e){
            e.printStackTrace();
        }
       return workStatistics2;
    }

    @ApiOperation(value = "新增", notes = "新增", httpMethod = "POST")
    @RequestMapping(value = "/insert", method = RequestMethod.POST)
    public WorkStatistics post(@Validated @RequestBody WorkStatistics workStatistics) {
        service.insert(workStatistics);
        return workStatistics;
    }

    @ApiOperation(value = "获取", notes = "获取", httpMethod = "GET")
    @RequestMapping(value = "/get/{id}", method = RequestMethod.GET)
    public WorkStatistics get(@ApiParam(value = "ID") @PathVariable String id) {
        WorkStatistics workStatistics = service.get(id);
        return workStatistics;
    }

    @ApiOperation(value = "新增/更新", notes = "新增/更新", httpMethod = "POST")
    @RequestMapping(value = "/save/{id}", method = RequestMethod.POST)
    public WorkStatistics put(@PathVariable String id, @Validated @RequestBody WorkStatistics workStatistics) {
        workStatistics.setId(id);
        service.save(workStatistics);
        return get(id);
    }

    @ApiOperation(value = "动态更新", notes = "动态更新", httpMethod = "POST")
    @RequestMapping(value = "/daySave/{id}", method = RequestMethod.POST)
    public WorkStatistics patch(@ApiParam(value = "ID") @PathVariable String id, @RequestBody WorkStatistics workStatistics) {
        workStatistics.setId(id);
        service.dynamicUpdate(workStatistics);
        return get(id);
    }

    @ApiOperation(value = "删除", notes = "删除", httpMethod = "POST")
    @RequestMapping(value = "/del/{id}", method = RequestMethod.POST)
    public void delete(@ApiParam(value = "ID") @PathVariable String id) {
        service.delete(id);
    }

}