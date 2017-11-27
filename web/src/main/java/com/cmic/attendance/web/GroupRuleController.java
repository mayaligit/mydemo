package com.cmic.attendance.web;

import com.cmic.attendance.exception.GroupRuleExeption;
import com.cmic.attendance.model.GroupPersonnel;
import com.cmic.attendance.model.GroupRule;
import com.cmic.attendance.service.GroupRuleService;
import com.cmic.attendance.vo.GroupRuleVo;
import com.cmic.saas.base.model.BaseAdminEntity;
import com.cmic.saas.base.web.BaseRestController;
import com.cmic.saas.utils.WebUtils;
import com.github.pagehelper.PageInfo;
import com.netflix.discovery.converters.Auto;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.List;

/**
* Controller
*/
@Api(description = "管理")
@RestController
@RequestMapping("/rule")
public class GroupRuleController extends BaseRestController<GroupRuleService> {

    @ApiOperation(value = "查询", notes = "查询列表", httpMethod = "GET")
    @ApiImplicitParams({
        @ApiImplicitParam(name = "id", value="UUID主键", paramType = "query"),
        @ApiImplicitParam(name = "groupName", value="考勤组名", paramType = "query"),
        @ApiImplicitParam(name = "groupEnterpriseId", value="企业id (保留字段)", paramType = "query"),
        @ApiImplicitParam(name = "groupEnterpriseName", value="企业名称(保留字段)", paramType = "query"),
        @ApiImplicitParam(name = "groupStatus", value="0/启用 1/停用 默认1", paramType = "query"),
        @ApiImplicitParam(name = "groupDeadline", value="生效时间从-至 至的话默认2099年", paramType = "query"),
        @ApiImplicitParam(name = "groupDailyId", value="考勤组启用模板ID", paramType = "query"),
        @ApiImplicitParam(name = "groupAttendanceWay", value="考勤方式 0/自由 1/时长", paramType = "query"),
        @ApiImplicitParam(name = "groupAttendanceStart", value="组考勤上班时间", paramType = "query"),
        @ApiImplicitParam(name = "groupAttendanceEnd", value="组考勤下班时间", paramType = "query"),
        @ApiImplicitParam(name = "groupAttendanceDuration", value="考勤的时长（注：单位小时，如8.5小时)", paramType = "query"),
        @ApiImplicitParam(name = "groupAttendanceWeek", value="组考勤的周", paramType = "query"),
        @ApiImplicitParam(name = "groupAttendanceLongitude", value="组考勤的经度", paramType = "query"),
        @ApiImplicitParam(name = "groupAttendanceDimension", value="组考勤的维度", paramType = "query"),
        @ApiImplicitParam(name = "groupAddress", value="考勤地址", paramType = "query"),
        @ApiImplicitParam(name = "groupAttendanceScope", value="组考勤的范围（注：单位米 如1000米）", paramType = "query"),
        @ApiImplicitParam(name = "createBy.id", value="创建用户手机", paramType = "query"),
        @ApiImplicitParam(name = "createDate", value="创建时间", paramType = "query"),
        @ApiImplicitParam(name = "updateBy.id", value="更新用户手机", paramType = "query"),
        @ApiImplicitParam(name = "updateDate", value="更新时间", paramType = "query"),
        @ApiImplicitParam(name = "orderBy", value="排序", defaultValue = "createDate desc", paramType = "query"),
        @ApiImplicitParam(name = "pageSize", value="分页大小", defaultValue = "10", paramType = "query"),
        @ApiImplicitParam(name = "pageNum", value="页码", defaultValue = "1", paramType = "query")
    })
    @RequestMapping(value="/findGroupRuleList", method = RequestMethod.POST)
    @ResponseBody
    public Map<String,Object> findGroupList(@RequestBody PageInfo page){

        //HttpServletResponse response = WebUtils.getRequestAttributes().getResponse();
        //response.setHeader("Access-Control-Allow-Origin", "*");
        int pageNum = page.getPageNum();
        int pageSize = page.getPageSize();
        Map<String,Object> map = service.findAllGroupRuleList(pageNum,pageSize);
        return map;

    }

    @ApiOperation(value = "新增", notes = "新增", httpMethod = "POST")
    @RequestMapping(value="/", method = RequestMethod.POST)
    public GroupRule post(@Validated @RequestBody GroupRule groupRule){
        service.insert(groupRule);
        return groupRule;
    }

    @ApiOperation(value = "获取", notes = "获取", httpMethod = "GET")
    @RequestMapping(value="/getGroupRuleById/{id}", method = RequestMethod.GET)
    @ResponseBody
    public Map<String,Object> getGroupRule(@PathVariable String id){
        Map<String,Object> map = service.findGroupRuleVoById(id);
        return map;
    }

    @ApiOperation(value = "新增/更新", notes = "新增/更新", httpMethod = "PUT")
    @RequestMapping(value="/updateGroupRule/{id}", method = RequestMethod.PUT)
    public String put(@RequestParam("id")String groupRuleId,@Validated @RequestBody GroupRuleVo groupRuleVo){
        groupRuleVo.getGroupRule().setId(groupRuleId);
        //service.save(groupRule);
        try {
            service.updateGroupRule(groupRuleVo);
            return "true";
        }catch (GroupRuleExeption e){

        }
        return "ture";
    }

    @ApiOperation(value = "动态更新", notes = "动态更新", httpMethod = "PATCH")
    @RequestMapping(value="/{id}", method = RequestMethod.PATCH)
    public GroupRule patch(@ApiParam(value = "ID") @PathVariable String id, @RequestBody GroupRule groupRule){
        groupRule.setId(id);
        service.dynamicUpdate(groupRule);
        //return get(id);
        return service.get(id);
    }

    @ApiOperation(value = "删除", notes = "删除", httpMethod = "DELETE")
    @RequestMapping(value="/delGroupRuleById/{id}", method = RequestMethod.DELETE)
    public void delete(@ApiParam(value = "ID") @PathVariable String id) {
        service.delByGroupRuleId(id);
    }
	
	 /**
     * 插入考勤规则
     * //@param groupRuleVo
     */
    @ApiOperation(value = "插入规则", notes = "删除考勤主表", httpMethod = "POST")
    @RequestMapping(value="/insertGroupRule")
    @ResponseBody
    public Map<String,String> insertGroupRule(@Validated @RequestBody GroupRuleVo groupRuleVo){
        //测试数据
        HttpServletRequest request = WebUtils.getRequest();
        HttpServletResponse response = WebUtils.getRequestAttributes().getResponse();
        response.setHeader("Access-Control-Allow-Origin", "*");
        BaseAdminEntity adminEntity = new BaseAdminEntity();
        adminEntity.setId("15240653787");
        adminEntity.setName("梁渝");
        //测试数据结束
        request.getSession().setAttribute("_CURRENT_ADMIN_INFO"    ,adminEntity);
        HashMap<String,String> resultHash =new HashMap<String,String>();
        try {
            service.insertGroupRule(groupRuleVo);
            resultHash.put("code","0");
            resultHash.put("msg","操作成功");
            return resultHash;
        } catch (GroupRuleExeption g) {
            resultHash.put("code","1");
            resultHash.put("msg",g.getMessage());
            return resultHash;
        }
    }

}