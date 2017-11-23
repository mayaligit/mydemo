package com.cmic.attendance.service;

import com.cmic.attendance.dao.GroupRuleDao;
import com.cmic.attendance.model.GroupRule;
import com.cmic.saas.base.service.CrudService;
import com.cmic.saas.base.web.RestException;
import com.cmic.saas.utils.StringUtils;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
* Service
*/
@Service
@Transactional(readOnly = true)
public class GroupRuleService extends CrudService<GroupRuleDao, GroupRule> {

    public GroupRule get(String id) {
        return super.get(id);
    }

    public List<GroupRule> findList(GroupRule groupRule) {
        return super.findList(groupRule);
    }

    public PageInfo<GroupRule> findPage(PageInfo<GroupRule> page, GroupRule groupRule) {
        return super.findPage(page, groupRule);
    }

    @Transactional(readOnly = false)
    public void save(GroupRule groupRule) {
        super.save(groupRule);
    }

    @Transactional(readOnly = false)
    public void dynamicUpdate(GroupRule groupRule) {
        super.dynamicUpdate(groupRule);
    }

    @Transactional(readOnly = false)
    public void delete(String id) {
        //判断是否存在
        GroupRule groupRule = get(id);
        if(groupRule==null|| StringUtils.isEmpty(groupRule.getId())){
            throw new RestException("删除失败，不存在");
        }
        super.delete(id);
        logger.info("删除：" + groupRule.toJSONString());
    }
	
	/*
     插入考勤组规则数据
     */
    @Transactional(readOnly = false)
    public void insertGroupRule(GroupRuleVo groupRuleVo) throws GroupRuleExeption {


        //返回考勤主表ID
        String attendanceGroupId = null;
        //分割考勤人员输入，获取各值
        String persons = groupRuleVo.getGroupPersonnel().getPersonnelName();
        String person[] = persons.split(",");
        //多地址获取
        String addresses = groupRuleVo.getGroupRule().getGroupAddress();
        String[] resses = addresses.split(",");

        try {
            //添加企业id
            String groupEnterId = "";
            if(person!=null&&person.length>0) {
                for (int i = 0; i < person.length; i++) {
                    String[] p = person[i].split("-");
                    groupEnterId = p[2] + " ";
                }
            }
            groupRuleVo.getGroupRule().setGroupEnterpriseId(groupEnterId);
            groupRuleVo.getGroupRule().setGroupAddress(null);
            //插入规则主表数据
            this.save(groupRuleVo.getGroupRule());
            attendanceGroupId = groupRuleVo.getGroupRule().getId();
        }catch (Exception e){
            throw  new GroupRuleExeption("规则表插入失败");
        }

        //插入考勤人员
        try {
            for(int i=0;i<person.length;i++){
                String[] p = person[i].split("-");
                String personnelName = p[0];
                String personnelPhone = p[1];
                String groupEnterId = p[2];
                groupRuleVo.getGroupPersonnel().setPersonnelName(p[0]);
                groupRuleVo.getGroupPersonnel().setPersonnelPhone(personnelPhone);
                groupRuleVo.getGroupPersonnel().setEnterpriseId(groupEnterId);

                GroupPersonnel groupPersonnel = groupRuleVo.getGroupPersonnel();
                groupPersonnel.setAttendanceGroupId(attendanceGroupId);
                groupPersonnelService.save(groupPersonnel);
            }
        }catch (Exception e){
            throw  new GroupRuleExeption("考勤表插入失败");
        }

        //插入日报规则表
        String dailyRuleId=null;
        try {
            GroupDailyRule groupDailyRule = groupRuleVo.getGroupDailyRule();
            if(groupDailyRule==null){
                groupDailyRule = new GroupDailyRule();
            }
            groupDailyRule.setAttendanceGroupId(attendanceGroupId);
            groupDailyRuleService.save(groupDailyRule);
            dailyRuleId = groupDailyRule.getId();
        }catch (Exception e){
            throw  new GroupRuleExeption("日报表操作失败");
        }

        //插入审核人员
        try {
            GroupAudit groupAudit = groupRuleVo.getGroupAudit();
            if(groupAudit==null){
                groupAudit = new GroupAudit();
            }
            groupAudit.setDailyRuleId(dailyRuleId);
            groupAuditService.save(groupAudit);

        }catch (Exception e){
            throw  new GroupRuleExeption("插入审人员核操作失败");
        }

        //插入考勤地址信息
        try {
            GroupAddress groupAddress = groupRuleVo.getGroupAddress();
            if(groupAddress==null){
                groupAddress = new GroupAddress();
            }

            groupAddress.setAttendanceGroupId(attendanceGroupId);
            if(resses!=null){
                for (int i = 0; i < resses.length; i++) {
                    String adds[] = resses[i].split("-");
                    groupAddress.setGroupAttendanceLongitude(adds[0]);
                    groupAddress.setGroupAttendanceDimension(adds[1]);
                    groupAddress.setGroupAttendanceScope(adds[2]);
                    groupAddressService.save(groupAddress);
                }
            }

        }catch (Exception e){
            throw  new GroupRuleExeption("考勤表地址信息插入失败");
        }

    }

}