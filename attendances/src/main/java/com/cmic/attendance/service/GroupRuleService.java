package com.cmic.attendance.service;

import com.cmic.attendance.dao.*;
import com.cmic.attendance.exception.GroupRuleExeption;
import com.cmic.attendance.model.*;
import com.cmic.attendance.vo.AttendanceUserVo;
import com.cmic.attendance.vo.AttendanceVo;
import com.cmic.attendance.vo.GroupRuleVo;
import com.cmic.saas.base.service.CrudService;
import com.cmic.saas.base.web.RestException;
import com.cmic.saas.utils.WebUtils;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.*;

/**
* Service
*/
@Service
@Transactional(readOnly = true)
public class GroupRuleService extends CrudService<GroupRuleDao, GroupRule> {

    @Autowired
    private GroupDailyRuleService groupDailyRuleService;

    @Autowired
    private GroupAuditService groupAuditService;

    @Autowired
    private GroupAddressService groupAddressService;

    @Autowired
    private  GroupPersonnelService groupPersonnelService;

    @Autowired
    private GroupAddressDao groupAddressDao;

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

    /**
     * 根据组名以及启用状态获取考勤组信息
     *
     */
    public GroupRule findGroupNameAndGroupStatus(String groupName,int groupStatus){
        System.out.println("groupName=="+groupName+"groupStatus=="+groupStatus);
        return dao.getByGroupNameAndGroupStatus(groupName,groupStatus);
    }
	/*
     插入考勤组规则数据
     */
    @Transactional(readOnly = false)
    public void insertGroupRule(GroupRuleVo groupRuleVo) throws GroupRuleExeption {

        //返回考勤主表ID
        String attendanceGroupId = null;

        //多地址获取
        String addresses = groupRuleVo.getGroupRule().getGroupAddress();
        String[] resses = addresses.split(",");

        try {
            String place = "";
            for(int i=0;i<resses.length;i++){
               String[] places = resses[i].split("-");
               if(i==resses.length-1){
                   place = place + places[3];
               }else {
                   place = place + places[3] + ",";
               }
            }
            groupRuleVo.getGroupRule().setGroupAddress(place);
            //插入规则主表数据
            this.save(groupRuleVo.getGroupRule());
            attendanceGroupId = groupRuleVo.getGroupRule().getId();
        }catch (Exception e){
            throw  new GroupRuleExeption("规则表插入失败");
        }

        //插入考勤人员
        try {
            //分割考勤人员输入，获取各值
            String persons = groupRuleVo.getGroupPersonnel().getPersonnelName();
            String person[] = persons.split(",");

            for(int i=0;i<person.length;i++){
                String[] p = person[i].split("-");
                String personnelName = p[0];
                String personnelPhone = p[1];
                String groupEnterId = p[2];
                GroupPersonnel groupPersonnel = new GroupPersonnel();
                groupPersonnel.setPersonnelName(personnelName);
                groupPersonnel.setPersonnelPhone(personnelPhone);
                groupPersonnel.setEnterpriseId(groupEnterId);
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

            if(resses!=null){
                for (int i = 0; i < resses.length; i++) {
                    String adds[] = resses[i].split("-");
                    GroupAddress groupAddress = new GroupAddress();
                    groupAddress.setAttendanceGroupId(attendanceGroupId);
                    groupAddress.setGroupAttendanceLongitude(Float.parseFloat(adds[0]));
                    groupAddress.setGroupAttendanceDimension(Float.parseFloat(adds[1]));
                    groupAddress.setGroupAttendanceScope(Integer.parseInt(adds[2]));
                    groupAddress.setGroupAddress(adds[3]);
                    groupAddressService.save(groupAddress);
                }
            }

        }catch (Exception e){
            throw  new GroupRuleExeption("考勤表地址信息插入失败");
        }

    }

    public Map<String,Object> findAllGroupRuleList(int pageNum,int pageSize){

        Map<String,Object> paramMap = new HashMap<>();
        if(pageNum==0){
            pageNum=1;
        }
        paramMap.put("pageNum",pageNum);
        paramMap.put("pageSize",pageSize);
        //设置查询参数和排序条件
        PageHelper.startPage(pageNum,pageSize);
        PageHelper.orderBy("updateDate");
        //分页查询并获取分页信息
        List<Map> groupRuleList = dao.findGroupRuleList(paramMap);
        for(int i=0;i<groupRuleList.size();i++){
            Map<String,Object> m = groupRuleList.get(i);
            Object createDate = m.get("createDate");
            Object updateDate = m.get("updateDate");
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm");
            String dcreate = simpleDateFormat.format(createDate);
            String udate = simpleDateFormat.format(updateDate);
            m.put("createDate",dcreate);
            m.put("updateDate",udate);
        }

        PageInfo<GroupRule> pageInfo =  new PageInfo(groupRuleList);

        //创建对象对相应数据进行封装
        Map<String,Object> responseMap = new HashMap<String,Object>();
        List< Map<String,Object>> ruleList = new ArrayList<>();

        //获取总页数和总记录数
        responseMap.put("totalPages",pageInfo.getPages());
        responseMap.put("totalCount",pageInfo.getTotal());
        responseMap.put("ruleList",pageInfo.getList());
        return responseMap;

    }

    /**
     * 根据id获取数据
     */
    public Map<String,Object> findGroupRuleVoById(String groupRuleId){

        Map<String,Object> map = new HashMap<>();

        //根据id获取考勤主表
        GroupRule groupRule = dao.get(groupRuleId);
        if(groupRule.getGroupAttendanceStart()!=null&&groupRule.getGroupAttendanceEnd()!=null){
            groupRule.setGroupAttendanceStart(groupRule.getGroupAttendanceStart().substring(0,5));
            groupRule.setGroupAttendanceEnd(groupRule.getGroupAttendanceEnd().substring(0,5));
        }
        map.put("groupRule",groupRule);

        //根据考勤组id获取考勤人员
        List<GroupPersonnel> groupPersonnelList = groupPersonnelService.findListByGroupRuleId(groupRuleId);
        map.put("groupPersonnelList",groupPersonnelList);

        //根据考勤组id获取多地址
        List<GroupAddress> addressList = groupAddressService.findListByGroupRuleId(groupRuleId);
        map.put("groupAddressList",addressList);
        return map;
    }

    /**
     * 更新数据
     * */
    @Transactional
    public void updateGroupRule(GroupRuleVo groupRuleVo) throws GroupRuleExeption{
        //返回考勤主表ID
        String attendanceGroupId = groupRuleVo.getGroupRule().getId();

        //多地址获取
        String addresses = groupRuleVo.getGroupRule().getGroupAddress();
        String[] resses = addresses.split(",");

        try {
            String place = "";
            for(int i=0;i<resses.length;i++){
                String[] places = resses[i].split("-");
                place = place + places[3]+",";
                if(i==resses.length-1){
                    place = place + places[3];
                }
            }
            groupRuleVo.getGroupRule().setGroupAddress(place);
            //更新规则主表数据
            dao.dynamicUpdate(groupRuleVo.getGroupRule());
        }catch (Exception e){
            throw  new GroupRuleExeption("规则表更新失败");
        }

        //更新考勤人员
        try {
            //分割考勤人员输入，获取各值
            String persons = groupRuleVo.getGroupPersonnel().getPersonnelName();
            String person[] = persons.split(",");
            List<GroupPersonnel> list = groupPersonnelService.findListByGroupRuleId(attendanceGroupId);

            for(int i=0;i<person.length;i++){
                String[] p = person[i].split("-");
                String personnelName = p[0];
                String personnelPhone = p[1];
                String groupEnterId = p[2];
                groupRuleVo.getGroupPersonnel().setPersonnelName(p[0]);
                groupRuleVo.getGroupPersonnel().setPersonnelPhone(personnelPhone);
                groupRuleVo.getGroupPersonnel().setEnterpriseId(groupEnterId);
                groupRuleVo.getGroupPersonnel().setId(list.get(i).getId());

                GroupPersonnel groupPersonnel = groupRuleVo.getGroupPersonnel();
                groupPersonnel.setAttendanceGroupId(attendanceGroupId);
                groupPersonnelService.dynamicUpdate(groupPersonnel);
            }
        }catch (Exception e){
            throw  new GroupRuleExeption("考勤人员表更新失败");
        }

        //插入考勤地址信息

                List<GroupAddress> gaddressList = groupAddressDao.findListByGroupRuleId(attendanceGroupId);
                if (resses != null) {
                    for (int i = 0; i < resses.length; i++) {
                        String adds[] = resses[i].split("-");
                        GroupAddress groupAddress = gaddressList.get(i);

                        Map<String,Object> paraMap = new HashMap<String,Object>();
                        //还没确定怎么获取审批人的电话号码, 暂时硬编码
                        //AttendanceUserVo attendanceUserVo = (AttendanceUserVo) WebUtils.getSession().getAttribute("attendanceUserVo");
                        //paraMap.put("updateBy",attendanceUserVo.getAttendanceUsername());

                        paraMap.put("addressid",groupAddress.getId());
                        paraMap.put("groupAttendanceLongitude",Float.parseFloat(adds[0]));
                        paraMap.put("groupAttendanceDimension",Float.parseFloat(adds[1]));
                        paraMap.put("groupAttendanceScope",Integer.parseInt(adds[2]));
                        paraMap.put("groupAddress",adds[3]);
                        paraMap.put("attendanceGroupId",attendanceGroupId);
                        /*if(resses.length-gaddressList.size()>0){
                            int alength = resses.length-gaddressList.size();
                            if(alength==1){
                                GroupAddress address = new GroupAddress();
                                address.setGroupAttendanceLongitude(Float.parseFloat(adds[0]));
                                address.setGroupAttendanceDimension(Float.parseFloat(adds[1]));
                                address.setGroupAddress(adds[3]);
                                address.setAttendanceGroupId(attendanceGroupId);
                                address.setUpdateDate(new Date());

                            }
                        }*/
                        paraMap.put("updateDate",new Date());
                        groupAddressDao.updateGroupAddressById(paraMap);
                    }
            }

    }

    public Map<String,Object> findGroupRuleByName(String groupName){
        Map<String,Object> paramMap = new HashMap<>();

        //获取考勤主表
        GroupRule groupRule = dao.findGroupRuleByName(groupName);
        if(groupRule.getGroupAttendanceStart()!=null&&groupRule.getGroupAttendanceEnd()!=null){
            groupRule.setGroupAttendanceStart(groupRule.getGroupAttendanceStart().substring(0,5));
            groupRule.setGroupAttendanceEnd(groupRule.getGroupAttendanceEnd().substring(0,5));
        }
        paramMap.put("groupRule",groupRule);

        //获取考勤人员
        List<GroupPersonnel> groupPersonnelList = groupPersonnelService.findListByGroupRuleId(groupRule.getId());
        paramMap.put("groupPersonnelList",groupPersonnelList);

        //获取考勤多地址
        List<GroupAddress> addressList = groupAddressService.findListByGroupRuleId(groupRule.getId());
        paramMap.put("groupAddressList",addressList);

        return paramMap;
    }
}

