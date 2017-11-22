package com.cmic.attendance.web;

import com.cmic.attendance.model.GroupPersonnel;
import com.cmic.attendance.service.GroupPersonnelService;
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
@RequestMapping("/personnel")
public class GroupPersonnelController extends BaseRestController<GroupPersonnelService> {

    @ApiOperation(value = "查询", notes = "查询列表", httpMethod = "GET")
    @ApiImplicitParams({
        @ApiImplicitParam(name = "id", value="UUID主键", paramType = "query"),
        @ApiImplicitParam(name = "personnelPhone", value="考勤人员手机号", paramType = "query"),
        @ApiImplicitParam(name = "personnelName", value="考勤人员用户名", paramType = "query"),
        @ApiImplicitParam(name = "enterpriseId", value="企业id", paramType = "query"),
        @ApiImplicitParam(name = "enterpriseName", value="企业名称（保留字段)", paramType = "query"),
        @ApiImplicitParam(name = "attendanceGroupId", value="关联对应的考勤组", paramType = "query"),
        @ApiImplicitParam(name = "createBy.id", value="创建用户手机", paramType = "query"),
        @ApiImplicitParam(name = "createDate", value="创建时间", paramType = "query"),
        @ApiImplicitParam(name = "updateBy.id", value="更新用户手机", paramType = "query"),
        @ApiImplicitParam(name = "updateDate", value="更新时间", paramType = "query"),
        @ApiImplicitParam(name = "orderBy", value="排序", defaultValue = "createDate desc", paramType = "query"),
        @ApiImplicitParam(name = "pageSize", value="分页大小", defaultValue = "10", paramType = "query"),
        @ApiImplicitParam(name = "pageNum", value="页码", defaultValue = "1", paramType = "query")
    })
    @RequestMapping(value="/", method = RequestMethod.GET)
    public PageInfo<GroupPersonnel> get(@ApiIgnore GroupPersonnel groupPersonnel, @ApiIgnore PageInfo page){
        page = service.findPage(page, groupPersonnel);
        return page;
    }

    @ApiOperation(value = "新增", notes = "新增", httpMethod = "POST")
    @RequestMapping(value="/", method = RequestMethod.POST)
    public GroupPersonnel post(@Validated @RequestBody GroupPersonnel groupPersonnel){
        service.insert(groupPersonnel);
        return groupPersonnel;
    }

    @ApiOperation(value = "获取", notes = "获取", httpMethod = "GET")
    @RequestMapping(value="/{id}", method = RequestMethod.GET)
    public GroupPersonnel get(@ApiParam(value = "ID") @PathVariable String id){
        GroupPersonnel groupPersonnel = service.get(id);
        return groupPersonnel;
    }

    @ApiOperation(value = "新增/更新", notes = "新增/更新", httpMethod = "PUT")
    @RequestMapping(value="/{id}", method = RequestMethod.PUT)
    public GroupPersonnel put(@PathVariable String id, @Validated @RequestBody GroupPersonnel groupPersonnel){
        groupPersonnel.setId(id);
        service.save(groupPersonnel);
        return get(id);
    }

    @ApiOperation(value = "动态更新", notes = "动态更新", httpMethod = "PATCH")
    @RequestMapping(value="/{id}", method = RequestMethod.PATCH)
    public GroupPersonnel patch(@ApiParam(value = "ID") @PathVariable String id, @RequestBody GroupPersonnel groupPersonnel){
        groupPersonnel.setId(id);
        service.dynamicUpdate(groupPersonnel);
        return get(id);
    }

    @ApiOperation(value = "删除", notes = "删除", httpMethod = "DELETE")
    @RequestMapping(value="/{id}", method = RequestMethod.DELETE)
    public void delete(@ApiParam(value = "ID") @PathVariable String id){
        service.delete(id);
    }

}