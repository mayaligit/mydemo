package com.cmic.attendance.web;

import com.cmic.attendance.model.GroupDailyRule;
import com.cmic.attendance.service.GroupDailyRuleService;
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
@RequestMapping("/rule")
public class GroupDailyRuleController extends BaseRestController<GroupDailyRuleService> {

    @ApiOperation(value = "查询", notes = "查询列表", httpMethod = "GET")
    @ApiImplicitParams({
        @ApiImplicitParam(name = "id", value="UUID主键", paramType = "query"),
        @ApiImplicitParam(name = "ruleDailyType", value="0/日报 1/周报", paramType = "query"),
        @ApiImplicitParam(name = "ruleDailyWeek", value="(注：1-7表示提示日期)", paramType = "query"),
        @ApiImplicitParam(name = "ruleTemplateId", value="关联日报模板模块 ", paramType = "query"),
        @ApiImplicitParam(name = "attendanceGroupId", value="关联对应考勤组", paramType = "query"),
        @ApiImplicitParam(name = "ruleDailyStatus", value="0/立即启用 1/停用", paramType = "query"),
        @ApiImplicitParam(name = "ruleDailyReserve", value="用户预订的生效时间（待定是年月日）", paramType = "query"),
        @ApiImplicitParam(name = "ruleDeadline", value="生效时间从-至 至的话默认2099年", paramType = "query"),
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
    public PageInfo<GroupDailyRule> get(@ApiIgnore GroupDailyRule groupDailyRule, @ApiIgnore PageInfo page){
        page = service.findPage(page, groupDailyRule);
        return page;
    }

    @ApiOperation(value = "新增", notes = "新增", httpMethod = "POST")
    @RequestMapping(value="/", method = RequestMethod.POST)
    public GroupDailyRule post(@Validated @RequestBody GroupDailyRule groupDailyRule){
        service.insert(groupDailyRule);
        return groupDailyRule;
    }

    @ApiOperation(value = "获取", notes = "获取", httpMethod = "GET")
    @RequestMapping(value="/{id}", method = RequestMethod.GET)
    public GroupDailyRule get(@ApiParam(value = "ID") @PathVariable String id){
        GroupDailyRule groupDailyRule = service.get(id);
        return groupDailyRule;
    }

    @ApiOperation(value = "新增/更新", notes = "新增/更新", httpMethod = "PUT")
    @RequestMapping(value="/{id}", method = RequestMethod.PUT)
    public GroupDailyRule put(@PathVariable String id, @Validated @RequestBody GroupDailyRule groupDailyRule){
        groupDailyRule.setId(id);
        service.save(groupDailyRule);
        return get(id);
    }

    @ApiOperation(value = "动态更新", notes = "动态更新", httpMethod = "PATCH")
    @RequestMapping(value="/{id}", method = RequestMethod.PATCH)
    public GroupDailyRule patch(@ApiParam(value = "ID") @PathVariable String id, @RequestBody GroupDailyRule groupDailyRule){
        groupDailyRule.setId(id);
        service.dynamicUpdate(groupDailyRule);
        return get(id);
    }

    @ApiOperation(value = "删除", notes = "删除", httpMethod = "DELETE")
    @RequestMapping(value="/{id}", method = RequestMethod.DELETE)
    public void delete(@ApiParam(value = "ID") @PathVariable String id){
        service.delete(id);
    }

}