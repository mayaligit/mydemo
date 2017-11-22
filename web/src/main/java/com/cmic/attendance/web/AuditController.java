package com.cmic.attendance.web;

import com.cmic.attendance.model.Audit;
import com.cmic.attendance.service.AuditService;
import com.cmic.saas.base.model.BaseAdminEntity;
import com.cmic.saas.base.web.BaseRestController;
import com.cmic.saas.utils.WebUtils;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.*;
import org.apache.commons.lang3.StringUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * 审批表Controller
 */
@Api(description = "审批表管理")
@RestController
@RequestMapping("/attendance/audit")
public class AuditController extends BaseRestController<AuditService> {

    @ApiOperation(value = "查询", notes = "查询审批表列表", httpMethod = "GET")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "orderBy", value="排序", defaultValue = "createDate desc", paramType = "query"),
            @ApiImplicitParam(name = "pageSize", value="分页大小", defaultValue = "10", paramType = "query"),
            @ApiImplicitParam(name = "pageNum", value="页码", defaultValue = "1", paramType = "query")
    })
    @RequestMapping(value="/", method = RequestMethod.GET)
    public PageInfo<Audit> get(@ApiIgnore Audit audit, @ApiIgnore PageInfo page){
        page = service.findPage(page, audit);
        return page;
    }

    @ApiOperation(value = "新增", notes = "新增审批表", httpMethod = "POST")
    @RequestMapping(value="/", method = RequestMethod.POST)
    public Audit post(@Validated @RequestBody Audit audit){
        service.insert(audit);
        return audit;
    }

    @ApiOperation(value = "获取", notes = "获取审批表", httpMethod = "GET")
    @RequestMapping(value="/{id}", method = RequestMethod.GET)
    public Audit get(@ApiParam(value = "审批表ID") @PathVariable String id){
        Audit audit = service.get(id);
        return audit;
    }

    @ApiOperation(value = "新增/更新", notes = "新增/更新审批表", httpMethod = "PUT")
    @RequestMapping(value="/{id}", method = RequestMethod.PUT)
    public Audit put(@PathVariable String id, @Validated @RequestBody Audit audit){
        audit.setId(id);
        service.save(audit);
        return get(id);
    }

    @ApiOperation(value = "动态更新", notes = "动态更新审批表", httpMethod = "PATCH")
    @RequestMapping(value="/{id}", method = RequestMethod.PATCH)
    public Audit patch(@ApiParam(value = "审批表ID") @PathVariable String id, @RequestBody Audit audit){
        audit.setId(id);
        service.dynamicUpdate(audit);
        return get(id);
    }

    @ApiOperation(value = "删除", notes = "删除审批表", httpMethod = "DELETE")
    @RequestMapping(value="/{id}", method = RequestMethod.DELETE)
    public void delete(@ApiParam(value = "审批表ID") @PathVariable String id){
        service.delete(id);
    }

    @RequestMapping(value="/save", method = RequestMethod.POST)
    public Map<String , String> saveAutditMsg(HttpServletResponse response, @RequestBody Audit audit) {

        /*response.setHeader("Access-Control-Allow-Origin", "*");*/
        Map<String, String> map = new HashMap<>();
        //判断是否携带了必须携带的两个参数
        if (StringUtils.isBlank(audit.getAuditContent()) || StringUtils.isBlank(audit.getAuditUserName())||StringUtils.isBlank(audit.getAuditUserId())){
            map.put("msg","请求参数不能为空");
            return map ;
        }
        audit.setSubmitTime(new Date());
        audit.setAuditStatus("1");  //设置审批状态为未处理

        audit.setCreateDate(audit.getSubmitTime());
        audit.setUpdateDate(audit.getSubmitTime());

        //设置用户名
        Object obj = WebUtils.getRequest().getSession().getAttribute("_CURRENT_ADMIN_INFO");
        if(null == obj || !(obj instanceof BaseAdminEntity)){
            map.put("msg","登陆超时,请重新登陆");
            return map ;
        }
        BaseAdminEntity user = (BaseAdminEntity)obj;
        audit.setUsername(user.getName());

        service.save(audit);

        map.put("msg", "申请提交成功");
        return map;
    }

}