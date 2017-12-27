package com.cmic.attendance.service;

import com.cmic.attendance.dao.GroupAddressDao;
import com.cmic.attendance.dao.GroupPersonnelDao;
import com.cmic.attendance.dao.GroupRuleDao;
import com.cmic.attendance.exception.GroupRuleExeption;
import com.cmic.attendance.model.*;
import com.cmic.attendance.vo.GroupRuleVo;
import com.cmic.saas.base.service.CrudService;
import com.cmic.saas.base.web.RestException;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
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
    private GroupPersonnelDao personnelDao;

    @Autowired
    private GroupAddressDao groupAddressDao;

    @Autowired
    private GroupRuleDao groupRuleDao;

    @Autowired
    private RedisTemplate redisTemplate;

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
    public GroupRule findGroupNameAndGroupStatus(String groupName,Integer groupStatus){
        System.out.println("groupNamesh组名== "+groupName+"<<<<<>>>>>>>groupStatus== "+groupStatus);
        HashMap<String,Object> paramMap=new HashMap<>();
        paramMap.put("groupName",groupName);
        paramMap.put("groupStatus",groupStatus);
        return dao.getByGroupNameAndGroupStatus(paramMap);
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
            if (dailyRuleId!=null) {
                groupAudit.setDailyRuleId(dailyRuleId);
                groupAuditService.save(groupAudit);
            }

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

    public Map<String,Object> findAllGroupRuleList(int pageNum,int pageSize,String groupName){

        Map<String,Object> paramMap = new HashMap<>();
        if(pageNum==0){
            pageNum=1;
        }
        paramMap.put("pageNum",pageNum);
        paramMap.put("pageSize",pageSize);
        if(groupName!=null) {
            paramMap.put("groupName", "%" + groupName + "%");
        }
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
        List<GroupPersonnel> groupPersonnelList = personnelDao.findListByGroupRuleId(groupRuleId);
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
                if(resses[i].indexOf("-")>0) {
                    String[] places = resses[i].split("-");
                    if(i==resses.length-1){
                        place = place + places[3];
                        break;
                    }
                    place = place + places[3]+",";
                }
            }
            groupRuleVo.getGroupRule().setGroupAddress(place);
            //更新规则主表数据
            groupRuleDao.dynamicUpdate(groupRuleVo.getGroupRule());
        }catch (Exception e){
            throw  new GroupRuleExeption("规则表更新失败");
        }

        //更新考勤地址信息
        try {
            if (resses != null) {

                for (int i = 0; i < resses.length; i++) {
                    String adds[] = resses[i].split("-");
                    if(adds.length>4){
                        //修改多地址
                        Map<String, Object> paraMap = new HashMap<String, Object>();
                        //还没确定怎么获取审批人的电话号码, 暂时硬编码
                        //AttendanceUserVo attendanceUserVo = (AttendanceUserVo) WebUtils.getSession().getAttribute("attendanceUserVo");
                        //paraMap.put("updateBy",attendanceUserVo.getAttendanceUsername());

                        paraMap.put("groupAttendanceLongitude", Float.parseFloat(adds[0]));
                        paraMap.put("groupAttendanceDimension", Float.parseFloat(adds[1]));
                        paraMap.put("groupAttendanceScope", Integer.parseInt(adds[2]));
                        paraMap.put("groupAddress", adds[3]);
                        paraMap.put("attendanceGroupId", adds[4]);
                        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
                        String updateDate = simpleDateFormat.format(new Date());

                        paraMap.put("updateDate",updateDate);
                        paraMap.put("addressid", adds[5]);
                        //检查当前用户是否已经打卡
                        paraMap.put("updateBy", groupRuleVo.getGroupRule().getUpdateBy());
                        groupAddressDao.updateGroupAddressById(paraMap);
                    }else if(adds.length==4){
                        //添加多地址
                        GroupAddress groupAddress = new GroupAddress();
                        groupAddress.setGroupAttendanceLongitude(Float.parseFloat(adds[0]));
                        groupAddress.setGroupAttendanceDimension(Float.parseFloat(adds[1]));
                        groupAddress.setGroupAttendanceScope(Integer.parseInt(adds[2]));
                        groupAddress.setGroupAddress(adds[3]);
                        groupAddress.setAttendanceGroupId(attendanceGroupId);
                        groupAddressService.save(groupAddress);
                    }else if(adds.length==1){
                        groupAddressService.delete(adds[0]);
                    }
                }
            }
        }catch (Exception e){
            throw new GroupRuleExeption("更新多地址失败");
        }
    }

    public List<GroupRule> findAllGroupNameList(){
        return dao.findAllGroupNameList();
    }

    public  int startWork(String attendanceGroup) {
        return dao.startWork(attendanceGroup);
    }
    public  int endWork(String attendanceGroup) {
        return dao.endWork(attendanceGroup);
    }

    public boolean checkGroupNameIsExist(String groupRuleName){
        GroupRule groupRule = dao.getGroupRuleByName(groupRuleName);
        return groupRule==null?false:true;
    }
}

