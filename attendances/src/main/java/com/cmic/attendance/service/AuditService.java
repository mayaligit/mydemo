package com.cmic.attendance.service;

import com.cmic.attendance.dao.AttendanceDao;
import com.cmic.attendance.dao.AuditDao;
import com.cmic.attendance.model.Attendance;
import com.cmic.attendance.model.Audit;
import com.cmic.attendance.utils.DateUtils;
import com.cmic.attendance.vo.AttendanceUserVo;
import com.cmic.saas.base.model.BaseAdminEntity;
import com.cmic.saas.base.service.CrudService;
import com.cmic.saas.base.web.RestException;
import com.cmic.saas.utils.StringUtils;
import com.cmic.saas.utils.WebUtils;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
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
    @Autowired
    private AttendanceDao attendanceDao;

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
    public void updateAudit(Audit audit , HttpServletRequest request){
        Map<String,Object> paraMap = new HashMap<String,Object>();
        //还没确定怎么获取审批人的电话号码, 暂时硬编码
       /* request.getSession().setAttribute("attendanceUserVo",attendanceUserVo);*/
        AttendanceUserVo attendanceUserVo = (AttendanceUserVo) WebUtils.getSession().getAttribute("attendanceUserVo");
        paraMap.put("updateBy",attendanceUserVo.getAttendanceUsername());

        paraMap.put("updateTime",new Date());
        paraMap.put("auditTime", DateUtils.getDateToStrings(new Date()));
        paraMap.put("auditStatus","0"); //设置审批意见状态为 已处理
        paraMap.put("auditId",audit.getId());
        paraMap.put("auditSuggestion",audit.getAuditSuggestion());
//        paraMap.put("suggestionRemarks",audit.getSuggestionRemarks());
        //更新 审批状态为 已处理
        dao.updateAudit(paraMap);

        //获取当天数据库的打卡数据
        String phone = audit.getCreateBy().getId();
        Date submitTime = audit.getSubmitTime();
        String createTime = DateUtils.getDateToYearMonthDay(submitTime);
        Attendance DBattendance = attendanceDao.getAttendanceByCreatebyAndCreateTime(phone, createTime);

        //将手机号码放到session中
        BaseAdminEntity adminEntity = new BaseAdminEntity();
        adminEntity.setId(phone);
        request.getSession().setAttribute("_CURRENT_ADMIN_INFO", adminEntity);

        Attendance attendance = new Attendance();
        String businessType = audit.getBusinessType();
        if ( null == DBattendance) { //没有打卡数据
            attendance.preInsert();
            attendance.setAttendanceUser(audit.getUsername());
            attendance.setCreateDate(new Date());
            attendance.setUpdateDate(new Date());
            attendance.setAttendanceMonth(createTime);
            attendance.setAttendanceGroup("ODC");

            if (businessType.trim().equals("0")) {  //处理补上班卡
                Date startTime = DateUtils.getStringsToDates(createTime +" "+"9:00:00");
                attendance.setStartTime(startTime);
                attendance.setStartTimeStatus("0");
                attendance.setStartLocation("南方基地");
                attendance.setAttendanceStatus("0");
                attendance.setDailyStatus("0");

            } else if (businessType.trim().equals("1")) {  //处理补下班卡
                Date endTime = DateUtils.getStringsToDates(createTime +" "+"18:00:00");
                attendance.setEndTime(endTime);
                attendance.setEndLocation("南方基地");
                attendance.setEndTimeStatus("0");
                attendance.setDailyStatus("0");
                attendance.setAttendanceStatus("1");

            }
            attendanceDao.insert(attendance);

        }else { //有打卡数据
            attendance.setId(DBattendance.getId());
            attendance.preUpdate();
            attendance.setUpdateDate(new Date());

            if (businessType.trim().equals("0")) {  //处理补上班卡
                Date startTime = DateUtils.getStringsToDates(createTime +" "+"9:00:00");
                attendance.setStartTime(startTime);
                attendance.setStartTimeStatus("0");
                attendance.setStartLocation("南方基地");

            } else if (businessType.trim().equals("1")) {  //处理补下班卡
                Date endTime = DateUtils.getStringsToDates(createTime +" "+"18:00:00");
                attendance.setEndTime(endTime);
                attendance.setEndLocation("南方基地");
                attendance.setEndTimeStatus("0");
            }
            attendanceDao.dynamicUpdate(attendance);
        }
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