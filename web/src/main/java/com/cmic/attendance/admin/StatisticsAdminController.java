package com.cmic.attendance.admin;

import com.cmic.attendance.model.Statistics;
import com.cmic.attendance.service.StatisticsService;
import com.cmic.saas.base.web.BaseRestController;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.*;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

/**
 * @author 何家来
 * @create 2017-11-02 17:32
*/
@Api(description = "统计表管理")
@RestController
@RequestMapping("/attendance")
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

    /*@ApiOperation(value = "获取", notes = "获取统计表", httpMethod = "GET")
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
    }*/
/*
    @ApiOperation(value = "动态更新", notes = "动态更新统计表", httpMethod = "PATCH")
    @RequestMapping(value="/{id}", method = RequestMethod.PATCH)
    public Statistics patch(@ApiParam(value = "统计表ID") @PathVariable String id, @RequestBody Statistics statistics){
        statistics.setId(id);
        service.dynamicUpdate(statistics);
        return get(id);
    }*/

    @ApiOperation(value = "删除", notes = "删除统计表", httpMethod = "DELETE")
    @RequestMapping(value="/{id}", method = RequestMethod.DELETE)
    public void delete(@ApiParam(value = "统计表ID") @PathVariable String id){
        service.delete(id);
    }






}