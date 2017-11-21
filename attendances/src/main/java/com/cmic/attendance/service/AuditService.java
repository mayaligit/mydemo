package com.cmic.attendance.service;

import com.cmic.attendance.dao.AuditDao;
import com.cmic.attendance.model.Audit;
import com.cmic.attendance.utils.DateUtils;
import com.cmic.attendance.vo.AttendanceUserVo;
import com.cmic.saas.base.service.CrudService;
import com.cmic.saas.base.web.RestException;
import com.cmic.saas.utils.StringUtils;
import com.cmic.saas.utils.WebUtils;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * 审批表Service
 */
@Service
@Transactional(readOnly = true)
public class AuditService extends CrudService<AuditDao, Audit> {

    public Audit get(String id) {
        return super.get(id);
    }

    public List<Audit> findList(Audit audit) {
        return super.findList(audit);
    }

    public PageInfo<Audit> findPage(PageInfo<Audit> page, Audit audit) {
        return super.findPage(page, audit);
    }

    @Transactional(readOnly = false)
    public void save(Audit audit) {
        super.save(audit);
    }

    @Transactional(readOnly = false)
    public void dynamicUpdate(Audit audit) {
        super.dynamicUpdate(audit);
    }

    @Transactional(readOnly = false)
    public void delete(String id) {
        //判断是否存在
        Audit audit = get(id);
        if(audit==null|| StringUtils.isEmpty(audit.getId())){
            throw new RestException("删除失败，审批表不存在");
        }
        super.delete(id);
        logger.info("删除审批表：" + audit.toJSONString());
    }

    @Transactional(readOnly = false)
    public Audit getAuditById(String auditId){
        Audit audit =  dao.getAuditById(auditId);
        return audit;
    }

    @Transactional(readOnly = false)
    public void updateAudit(Audit audit){
        Map<String,Object> paraMap = new HashMap<String,Object>();
        //还没确定怎么获取审批人的电话号码, 暂时硬编码
       /* request.getSession().setAttribute("attendanceUserVo",attendanceUserVo);*/
        AttendanceUserVo attendanceUserVo = (AttendanceUserVo)WebUtils.getSession().getAttribute("attendanceUserVo");
        paraMap.put("updateBy",attendanceUserVo.getAttendanceUsername());
        paraMap.put("updateTime",new Date());
        paraMap.put("auditTime", DateUtils.getDateToStrings(new Date()));
        paraMap.put("auditStatus","0"); //设置审批意见状态为 已处理
        paraMap.put("auditId",audit.getId());
        paraMap.put("auditSuggestion",audit.getAuditSuggestion());
        paraMap.put("suggestionRemarks",audit.getSuggestionRemarks());
        //更新 审批状态为 已处理
        dao.updateAudit(paraMap);
    }

    public Map<String, Object> findAuditList(PageInfo<Audit> page , Audit audit ){
        if(page.getPageNum() == 0) {
            page.setPageNum(1);
        }

        if(page.getPageSize() == 0) {
            page.setPageSize(10);
        }

        if(org.apache.commons.lang3.StringUtils.isEmpty(page.getOrderBy())) {
            page.setOrderBy("a.create_time desc");
        }

        PageHelper.startPage(page.getPageNum(), page.getPageSize() > 0?page.getPageSize():10, page.getOrderBy());

        PageInfo<Map> result=  new PageInfo(dao.findAuditList(audit));

        //创建封装数据
        Map<String, Object> dataMap = new HashMap<>();
        //考勤数据
        dataMap.put("auditList",result.getList());
        //总页数
        dataMap.put("pageCount",result.getPages());
        //总记录数
        dataMap.put("pageTotal",result.getTotal());
        return  dataMap;
    }

}