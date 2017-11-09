package com.cmic.attendance.web;

import com.github.pagehelper.PageInfo;
import com.cmic.saas.base.web.BaseRestController;
import com.cmic.attendance.model.Clazzes;
import com.cmic.attendance.service.ClazzesService;
import io.swagger.annotations.*;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

/**
* 班次表Controller
*/
@Api(description = "班次表管理")
@RestController
@RequestMapping("/clazzes")
public class ClazzesController extends BaseRestController<ClazzesService> {

    @ApiOperation(value = "查询", notes = "查询班次表列表", httpMethod = "GET")
    @ApiImplicitParams({
        @ApiImplicitParam(name = "orderBy", value="排序", defaultValue = "createDate desc", paramType = "query"),
        @ApiImplicitParam(name = "pageSize", value="分页大小", defaultValue = "10", paramType = "query"),
        @ApiImplicitParam(name = "pageNum", value="页码", defaultValue = "1", paramType = "query")
    })
    @RequestMapping(value="/", method = RequestMethod.GET)
    public PageInfo<Clazzes> get(@ApiIgnore Clazzes clazzes, @ApiIgnore PageInfo page){
        page = service.findPage(page, clazzes);
        return page;
    }

    @ApiOperation(value = "新增", notes = "新增班次表", httpMethod = "POST")
    @RequestMapping(value="/", method = RequestMethod.POST)
    public Clazzes post(@Validated @RequestBody Clazzes clazzes){
        service.insert(clazzes);
        return clazzes;
    }

    @ApiOperation(value = "获取", notes = "获取班次表", httpMethod = "GET")
    @RequestMapping(value="/{id}", method = RequestMethod.GET)
    public Clazzes get(@ApiParam(value = "班次表ID") @PathVariable String id){
        Clazzes clazzes = service.get(id);
        return clazzes;
    }

    @ApiOperation(value = "新增/更新", notes = "新增/更新班次表", httpMethod = "PUT")
    @RequestMapping(value="/{id}", method = RequestMethod.PUT)
    public Clazzes put(@PathVariable String id, @Validated @RequestBody Clazzes clazzes){
        clazzes.setId(id);
        service.save(clazzes);
        return get(id);
    }

    @ApiOperation(value = "动态更新", notes = "动态更新班次表", httpMethod = "PATCH")
    @RequestMapping(value="/{id}", method = RequestMethod.PATCH)
    public Clazzes patch(@ApiParam(value = "班次表ID") @PathVariable String id, @RequestBody Clazzes clazzes){
        clazzes.setId(id);
        service.dynamicUpdate(clazzes);
        return get(id);
    }

    @ApiOperation(value = "删除", notes = "删除班次表", httpMethod = "DELETE")
    @RequestMapping(value="/{id}", method = RequestMethod.DELETE)
    public void delete(@ApiParam(value = "班次表ID") @PathVariable String id){
        service.delete(id);
    }

}