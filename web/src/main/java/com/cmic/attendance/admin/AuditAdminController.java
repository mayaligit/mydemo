package com.cmic.attendance.admin;

import com.cmic.attendance.model.Audit;
import com.cmic.attendance.service.AuditService;
import com.cmic.saas.base.web.BaseRestController;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * 审批表Controller
 */
@Api(description = "审批表后台管理")
@RestController
@RequestMapping("/attendance/adminAudit")
public class AuditAdminController extends BaseRestController<AuditService> {
    /**
     * 根据审批记录 id 获取单条审批的明细
     * @param auditId  审批表记录id
     * @return 页面展示需要的内容, audit对象
     */
    @RequestMapping(value = "/auditDetail/{auditId}",method = RequestMethod.GET)
    public Audit getAuditById(@PathVariable String auditId){
        if(StringUtils.isBlank(auditId)){
            return  null;
        }
        return  service.getAuditById(auditId);
    }

    /**
     * 处理审批表记录审核的方法
     * @param audit  封装页面传递的参数 ,必须有 id 和 auditSuggestion
     * @return  提示信息
     */
    @RequestMapping(value = "/audit",method = RequestMethod.POST)
    public String updateAudit(@RequestBody Audit audit , HttpServletRequest request){
        if(StringUtils.isBlank(audit.getId()) || StringUtils.isBlank(audit.getAuditSuggestion().toString())){
            return "审批意见不能为空";
        }
        service.updateAudit(audit , request);
        return "审批成功";
    }

    /**
     * 获取审批列表
     * @param parmMap 封装参数 pageSize , pageNum , username 用户名
     * @return
     */
    @ApiOperation(value = "查找",notes = "查找审批表")
    @RequestMapping(value = "/auditList",method = RequestMethod.POST)
    public Map<String,Object> findAuditList(@RequestBody Map<String,Object> parmMap){
        Audit audit = new Audit();
        PageInfo<Audit> page = new PageInfo();

        if (parmMap != null && parmMap.size() > 0) {
            Integer pageNum = (Integer)parmMap.get("pageNum");
            Integer pageSize = (Integer)parmMap.get("pageSize");
            String UserName = (String) parmMap.get("username");
            String auditStatus = (String) parmMap.get("auditStatus");
            if(pageNum!=0){
                page.setPageNum(Integer.valueOf(pageNum));
            }
            if(pageSize!=0){
                page.setPageSize(pageSize);
            }
            if(StringUtils.isNotBlank(UserName)){
                audit.setUserName(UserName);
            }
            if(StringUtils.isNotBlank(auditStatus)){
                audit.setAuditStatus(Integer.valueOf(auditStatus));
            }
        }

        return service.findAuditList(page, audit);
    }

}