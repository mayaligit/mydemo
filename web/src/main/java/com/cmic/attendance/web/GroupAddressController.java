package com.cmic.attendance.web;

import com.github.pagehelper.PageInfo;
import com.cmic.saas.base.web.BaseRestController;
import com.cmic.attendance.model.GroupAddress;
import com.cmic.attendance.service.GroupAddressService;
import io.swagger.annotations.*;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

/**
* 考勤地址表Controller
*/
@Api(description = "考勤地址表管理")
@RestController
@RequestMapping("/attendance/address")
public class GroupAddressController extends BaseRestController<GroupAddressService> {

    @ApiOperation(value = "查询", notes = "查询考勤地址表列表", httpMethod = "GET")
    @ApiImplicitParams({
        @ApiImplicitParam(name = "groupAttendanceLongitude", value="考勤组的经度", paramType = "query"),
        @ApiImplicitParam(name = "groupAttendanceDimension", value="考勤组的维度", paramType = "query"),
        @ApiImplicitParam(name = "groupAddress", value="考勤地址", paramType = "query"),
        @ApiImplicitParam(name = "attendanceGroupId", value="关联对应的考勤组", paramType = "query"),
        @ApiImplicitParam(name = "remarks", value="预留字段", paramType = "query"),
        @ApiImplicitParam(name = "createBy.id", value="创建用户手机", paramType = "query"),
        @ApiImplicitParam(name = "createDate", value="创建时间", paramType = "query"),
        @ApiImplicitParam(name = "updateBy.id", value="更新用户手机", paramType = "query"),
        @ApiImplicitParam(name = "updateDate", value="更新时间", paramType = "query"),
        @ApiImplicitParam(name = "orderBy", value="排序", defaultValue = "createDate desc", paramType = "query"),
        @ApiImplicitParam(name = "pageSize", value="分页大小", defaultValue = "10", paramType = "query"),
        @ApiImplicitParam(name = "pageNum", value="页码", defaultValue = "1", paramType = "query")
    })
    @RequestMapping(value="/", method = RequestMethod.GET)
    public PageInfo<GroupAddress> get(@ApiIgnore GroupAddress groupAddress, @ApiIgnore PageInfo page){
        page = service.findPage(page, groupAddress);
        return page;
    }

    @ApiOperation(value = "新增", notes = "新增考勤地址表", httpMethod = "POST")
    @RequestMapping(value="/", method = RequestMethod.POST)
    public GroupAddress post(@Validated @RequestBody GroupAddress groupAddress){
        service.insert(groupAddress);
        return groupAddress;
    }

    @ApiOperation(value = "获取", notes = "获取考勤地址表", httpMethod = "GET")
    @RequestMapping(value="/{id}", method = RequestMethod.GET)
    public GroupAddress get(@ApiParam(value = "考勤地址表ID") @PathVariable String id){
        GroupAddress groupAddress = service.get(id);
        return groupAddress;
    }

    @ApiOperation(value = "新增/更新", notes = "新增/更新考勤地址表", httpMethod = "PUT")
    @RequestMapping(value="/{id}", method = RequestMethod.PUT)
    public GroupAddress put(@PathVariable String id, @Validated @RequestBody GroupAddress groupAddress){
        groupAddress.setId(id);
        service.save(groupAddress);
        return get(id);
    }

    @ApiOperation(value = "动态更新", notes = "动态更新考勤地址表", httpMethod = "PATCH")
    @RequestMapping(value="/{id}", method = RequestMethod.PATCH)
    public GroupAddress patch(@ApiParam(value = "考勤地址表ID") @PathVariable String id, @RequestBody GroupAddress groupAddress){
        groupAddress.setId(id);
        service.dynamicUpdate(groupAddress);
        return get(id);
    }

    @ApiOperation(value = "删除", notes = "删除考勤地址表", httpMethod = "DELETE")
    @RequestMapping(value="/del/{id}", method = RequestMethod.DELETE)
    public void delete(@ApiParam(value = "考勤地址表ID") @PathVariable String id){
        service.delete(id);
    }

}