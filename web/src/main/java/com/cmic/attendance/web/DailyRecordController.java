package com.cmic.attendance.web;

import com.github.pagehelper.PageInfo;
import com.cmic.saas.base.web.BaseRestController;
import com.cmic.attendance.model.DailyRecord;
import com.cmic.attendance.service.DailyRecordService;
import io.swagger.annotations.*;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

/**
* 日志表Controller
*/
@Api(description = "日志表管理")
@RestController
@RequestMapping("/record")
public class DailyRecordController extends BaseRestController<DailyRecordService> {

    @ApiOperation(value = "查询", notes = "查询日志表列表", httpMethod = "GET")
    @ApiImplicitParams({
        @ApiImplicitParam(name = "orderBy", value="排序", defaultValue = "createDate desc", paramType = "query"),
        @ApiImplicitParam(name = "pageSize", value="分页大小", defaultValue = "10", paramType = "query"),
        @ApiImplicitParam(name = "pageNum", value="页码", defaultValue = "1", paramType = "query")
    })
    @RequestMapping(value="/", method = RequestMethod.GET)
    public PageInfo<DailyRecord> get(@ApiIgnore DailyRecord dailyRecord, @ApiIgnore PageInfo page){
        page = service.findPage(page, dailyRecord);
        return page;
    }

    @ApiOperation(value = "新增", notes = "新增日志表", httpMethod = "POST")
    @RequestMapping(value="/", method = RequestMethod.POST)
    public DailyRecord post(@Validated @RequestBody DailyRecord dailyRecord){
        service.insert(dailyRecord);
        return dailyRecord;
    }

    @ApiOperation(value = "获取", notes = "获取日志表", httpMethod = "GET")
    @RequestMapping(value="/{id}", method = RequestMethod.GET)
    public DailyRecord get(@ApiParam(value = "日志表ID") @PathVariable String id){
        DailyRecord dailyRecord = service.get(id);
        return dailyRecord;
    }

    @ApiOperation(value = "新增/更新", notes = "新增/更新日志表", httpMethod = "PUT")
    @RequestMapping(value="/{id}", method = RequestMethod.PUT)
    public DailyRecord put(@PathVariable String id, @Validated @RequestBody DailyRecord dailyRecord){
        dailyRecord.setId(id);
        service.save(dailyRecord);
        return get(id);
    }

    @ApiOperation(value = "动态更新", notes = "动态更新日志表", httpMethod = "PATCH")
    @RequestMapping(value="/{id}", method = RequestMethod.PATCH)
    public DailyRecord patch(@ApiParam(value = "日志表ID") @PathVariable String id, @RequestBody DailyRecord dailyRecord){
        dailyRecord.setId(id);
        service.dynamicUpdate(dailyRecord);
        return get(id);
    }

    @ApiOperation(value = "删除", notes = "删除日志表", httpMethod = "DELETE")
    @RequestMapping(value="/{id}", method = RequestMethod.DELETE)
    public void delete(@ApiParam(value = "日志表ID") @PathVariable String id){
        service.delete(id);
    }

}