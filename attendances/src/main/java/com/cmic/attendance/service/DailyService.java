package com.cmic.attendance.service;

import com.cmic.attendance.dao.DailyDao;
import com.cmic.attendance.model.Attendance;
import com.cmic.attendance.model.Daily;
import com.cmic.attendance.pojo.DailyPojo;
import com.cmic.attendance.utils.DateUtils;
import com.cmic.attendance.vo.AttendanceUserVo;
import com.cmic.attendance.vo.DailyVo;
import com.cmic.saas.base.model.BaseAdminEntity;
import com.cmic.saas.base.service.CrudService;
import com.cmic.saas.base.web.RestException;
import com.cmic.saas.utils.WebUtils;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.util.*;


/**
* 日报表Service
*/
@Service
@Transactional(readOnly = true)
public class DailyService extends CrudService<DailyDao, Daily> {

    public Daily get(String id) {
        return super.get(id);
    }

    public List<Daily> findList(Daily daily) {
        return super.findList(daily);
    }

    public PageInfo<Daily> findPage(PageInfo<Daily> page, Daily daily) {
        return super.findPage(page, daily);
    }

    @Transactional(readOnly = false)
    public void save(Daily daily) {
        super.save(daily);
    }

    @Transactional(readOnly = false)
    public void dynamicUpdate(Daily daily) {
        super.dynamicUpdate(daily);
    }

    @Transactional(readOnly = false)
    public void delete(String id) {
        //判断是否存在
        Daily daily = get(id);
        if(daily==null|| StringUtils.isEmpty(daily.getId())){
            throw new RestException("删除失败，日报表不存在");
        }
        super.delete(id);
        logger.info("删除日报表：" + daily.toJSONString());
    }

    @Autowired
    private AttendanceService attendanceService;

    @Transactional(readOnly = false)
    public String insertDailyAndAttendance(DailyVo dailyVo){

        HttpServletRequest request = WebUtils.getRequest();
        BaseAdminEntity user= (BaseAdminEntity)request.getSession().getAttribute("_CURRENT_ADMIN_INFO");
        System.out.println("===========================考勤id"+ dailyVo.getAttendanceId()+"========================");
        if (null == user ) {
            return "1";//登录超时
        }
        System.out.println("=============手机号码phone:"+user.getId()+"==============前端传过来的日期:updateDate:"+user.getId()+"======================== ");
        String date = dailyVo.getDate();//前端传过来的日期,格式：2017-11-11
        DailyPojo dailyPojo = new DailyPojo();
        dailyPojo.setDate(date);
        dailyPojo.setPhone(user.getId());
        Daily daily = this.dao.getDailyByPhoneAndUser(dailyPojo);//根据手机号码和日期查询当天日报是否存在
        if(daily!= null){
            return "0";//日报已存在
        }

        String dateToHourMinuteS = DateUtils.getDateToHourMinuteS(new Date());//获取当前时间的时分秒：格式：HH:mm:ss
        Date date1 = DateUtils.getStringsToDates(date+ " "+dateToHourMinuteS);//拼接时间，格式为：yyyy-MM-dd HH:mm:ss

        dailyVo.setSuggestionStatus(1);//意见状态设置为未阅
        dailyVo.setExaminer("陈华龙");
        dailyVo.preInsert();
        dailyVo.setSubmitTime(date1);
        dailyVo.setCreateDate(date1);
        dailyVo.setUpdateDate(date1);

        Attendance attendance = attendanceService.checkAttendance(user.getId(), date);
//        考勤不存在则插入
        if (attendance==null){
            attendance = new Attendance();
            attendance.setCreateDate(date1);
            attendance.setUpdateDate(date1);
            attendance.setAttendanceStatus("1");
            attendance.setDailyStatus(1);
            Calendar cal = Calendar.getInstance();
            Integer month = cal.get(Calendar.MONTH )+1;
            attendance.setAttendanceMonth(cal.get(Calendar.YEAR )+"-"+month.toString());
            attendance.setAttendanceUser(user.getName());
//          attendance.setAttendanceUser("陈华龙");//测试数据
            attendanceService.save(attendance);//插入考勤
        }else{
            attendance.setUpdateDate(date1);
            attendance.setUpdateDate(new Date());
            attendance.setDailyStatus(1);
            attendance.setAttendanceUser(user.getName());
//          attendance.setAttendanceUser("陈华龙");//测试数据
            attendanceService.update(attendance);//更新考勤
        }
        dailyVo.setAttendanceId(attendance.getId());
        dailyVo.setExamineTime(dailyVo.getCreateDate());
        dailyVo.setUsername(user.getName());
        dailyVo.setAttendanceGroup("odc");//测试数据，暂时写死
//      dailyVo.setUsername("陈华龙");//测试数据
        this.save(dailyVo);//插入日报
        return null;
    }


    /**
     * 获取单条日报详情
     * @param dailyId 日报id
     * @return
     */
    public Daily findDailyById(String dailyId){
        return  dao.getDailyById(dailyId);
    }

    /**
     * 日报审批
     * @param dailyId 日报id
     * @return
     */
    @Transactional(readOnly = false)
    public String auditDailyById(String dailyId){
        Map<String,Object> paramMap = new HashMap<String,Object>();
        paramMap.put("dailyId",dailyId);
        //还没审批确定怎么获取审批人电话
        paramMap.put("updateBy","10086");
        paramMap.put("updateTime",new Date());
        paramMap.put("examineTime",new Date());
        paramMap.put("suggestionStatus","0");
        dao.updateDailyAuditById(paramMap);
        return "日报审批完成";
    }

    public  Map<String, Object> findDailyList(DailyVo dailyVo){

        HttpServletRequest request = WebUtils.getRequest();
        AttendanceUserVo attendanceUserVo = (AttendanceUserVo)request.getSession().getAttribute("attendanceUserVo");
        String attendanceGroup = attendanceUserVo.getAttendanceGroup();

        PageInfo page = dailyVo.getPageInfo();
        if(page.getPageNum() <= 0) {
            page.setPageNum(1);
        }
        if(page.getPageSize() <= 0) {
            page.setPageSize(10);
        }
        if(StringUtils.isEmpty(page.getOrderBy())) {
            page.setOrderBy("a.create_time desc");
        }
        DailyPojo dailyPojo = new DailyPojo();
        BeanUtils.copyProperties(dailyVo,dailyPojo);
        dailyPojo.setAttendanceGroup(attendanceGroup);

        PageHelper.startPage(page.getPageNum(), page.getPageSize() > 0?page.getPageSize():10, page.getOrderBy());
        PageInfo<Map> result=  new PageInfo(dao.findDailyList(dailyPojo));

        //创建封装数据
        Map<String, Object> dataMap = new HashMap<>();
        //考勤数据
        dataMap.put("dailyList",result.getList());
        //总页数
        dataMap.put("pageCount",result.getPages());
        //总记录数
        dataMap.put("pageTotal",result.getTotal());
        return  dataMap;
    }
}