package com.cmic.attendance.web;

import com.cmic.attendance.model.GroupDaily;
import com.cmic.attendance.service.GroupDailyService;
import com.cmic.saas.base.web.BaseRestController;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.*;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;


/**
* Controller
*/
@Api(description = "管理")
@RestController
@RequestMapping("/daily")
public class GroupDailyController extends BaseRestController<GroupDailyService> {

    @ApiOperation(value = "查询", notes = "查询列表", httpMethod = "GET")
    @ApiImplicitParams({
        @ApiImplicitParam(name = "id", value="UUID主键", paramType = "query"),
        @ApiImplicitParam(name = "dailyName", value="日报模板名字", paramType = "query"),
        @ApiImplicitParam(name = "dailyAddress", value="日报模板地址(html地址)", paramType = "query"),
        @ApiImplicitParam(name = "dailyContent", value="日报内容(注：格式为json格式)", paramType = "query"),
        @ApiImplicitParam(name = "dailyStatus", value="日报状态(注0/启用 1/停用)", paramType = "query"),
        @ApiImplicitParam(name = "createBy.id", value="创建用户手机", paramType = "query"),
        @ApiImplicitParam(name = "orderBy", value="排序", defaultValue = "createDate desc", paramType = "query"),
        @ApiImplicitParam(name = "pageSize", value="分页大小", defaultValue = "10", paramType = "query"),
        @ApiImplicitParam(name = "pageNum", value="页码", defaultValue = "1", paramType = "query")
    })
    @RequestMapping(value="/", method = RequestMethod.GET)
    public PageInfo<GroupDaily> get(@ApiIgnore GroupDaily groupDaily, @ApiIgnore PageInfo page){
        page = service.findPage(page, groupDaily);
        return page;
    }

    @ApiOperation(value = "新增", notes = "新增", httpMethod = "POST")
    @RequestMapping(value="/", method = RequestMethod.POST)
    public GroupDaily post(@Validated @RequestBody GroupDaily groupDaily){
        service.insert(groupDaily);
        return groupDaily;
    }

    @ApiOperation(value = "获取", notes = "获取", httpMethod = "GET")
    @RequestMapping(value="/{id}", method = RequestMethod.GET)
    public GroupDaily get(@ApiParam(value = "ID") @PathVariable String id){
        GroupDaily groupDaily = service.get(id);
        return groupDaily;
    }

    @ApiOperation(value = "新增/更新", notes = "新增/更新", httpMethod = "PUT")
    @RequestMapping(value="/{id}", method = RequestMethod.PUT)
    public GroupDaily put(@PathVariable String id, @Validated @RequestBody GroupDaily groupDaily){
        groupDaily.setId(id);
        service.save(groupDaily);
        return get(id);
    }

    @ApiOperation(value = "动态更新", notes = "动态更新", httpMethod = "PATCH")
    @RequestMapping(value="/{id}", method = RequestMethod.PATCH)
    public GroupDaily patch(@ApiParam(value = "ID") @PathVariable String id, @RequestBody GroupDaily groupDaily){
        groupDaily.setId(id);
        service.dynamicUpdate(groupDaily);
        return get(id);
    }

    @ApiOperation(value = "删除", notes = "删除", httpMethod = "DELETE")
    @RequestMapping(value="/{id}", method = RequestMethod.DELETE)
    public void delete(@ApiParam(value = "ID") @PathVariable String id){
        service.delete(id);
    }

}