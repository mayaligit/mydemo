package com.cmic.attendance.web;

import com.cmic.attendance.model.GroupAudit;
import com.cmic.attendance.service.GroupAuditService;
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
@RequestMapping("/audit")
public class GroupAuditController extends BaseRestController<GroupAuditService> {

    @ApiOperation(value = "查询", notes = "查询列表", httpMethod = "GET")
    @ApiImplicitParams({
        @ApiImplicitParam(name = "id", value="UUID主键", paramType = "query"),
        @ApiImplicitParam(name = "auditPhone", value="日报审核人手机号", paramType = "query"),
        @ApiImplicitParam(name = "auditName", value="日报审核人名字", paramType = "query"),
        @ApiImplicitParam(name = "enterpriseId", value="企业id", paramType = "query"),
        @ApiImplicitParam(name = "enterpriseName", value="企业名称（保留字段）", paramType = "query"),
        @ApiImplicitParam(name = "dailyRuleId", value="关联对应的日报表", paramType = "query"),
        @ApiImplicitParam(name = "createBy.id", value="创建用户手机", paramType = "query"),
        @ApiImplicitParam(name = "createDate", value="创建时间", paramType = "query"),
        @ApiImplicitParam(name = "updateBy.id", value="更新用户手机", paramType = "query"),
        @ApiImplicitParam(name = "updateDate", value="更新时间", paramType = "query"),
        @ApiImplicitParam(name = "attendanceGroup", value="所属考勤组名", paramType = "query"),
        @ApiImplicitParam(name = "orderBy", value="排序", defaultValue = "createDate desc", paramType = "query"),
        @ApiImplicitParam(name = "pageSize", value="分页大小", defaultValue = "10", paramType = "query"),
        @ApiImplicitParam(name = "pageNum", value="页码", defaultValue = "1", paramType = "query")
    })
    @RequestMapping(value="/", method = RequestMethod.GET)
    public PageInfo<GroupAudit> get(@ApiIgnore GroupAudit groupAudit, @ApiIgnore PageInfo page){
        page = service.findPage(page, groupAudit);
        return page;
    }

    @ApiOperation(value = "新增", notes = "新增", httpMethod = "POST")
    @RequestMapping(value="/", method = RequestMethod.POST)
    public GroupAudit post(@Validated @RequestBody GroupAudit groupAudit){
        service.insert(groupAudit);
        return groupAudit;
    }

    @ApiOperation(value = "获取", notes = "获取", httpMethod = "GET")
    @RequestMapping(value="/{id}", method = RequestMethod.GET)
    public GroupAudit get(@ApiParam(value = "ID") @PathVariable String id){
        GroupAudit groupAudit = service.get(id);
        return groupAudit;
    }

    @ApiOperation(value = "新增/更新", notes = "新增/更新", httpMethod = "PUT")
    @RequestMapping(value="/{id}", method = RequestMethod.PUT)
    public GroupAudit put(@PathVariable String id, @Validated @RequestBody GroupAudit groupAudit){
        groupAudit.setId(id);
        service.save(groupAudit);
        return get(id);
    }

    @ApiOperation(value = "动态更新", notes = "动态更新", httpMethod = "PATCH")
    @RequestMapping(value="/{id}", method = RequestMethod.PATCH)
    public GroupAudit patch(@ApiParam(value = "ID") @PathVariable String id, @RequestBody GroupAudit groupAudit){
        groupAudit.setId(id);
        service.dynamicUpdate(groupAudit);
        return get(id);
    }

    @ApiOperation(value = "删除", notes = "删除", httpMethod = "DELETE")
    @RequestMapping(value="/{id}", method = RequestMethod.DELETE)
    public void delete(@ApiParam(value = "ID") @PathVariable String id){
        service.delete(id);
    }

}