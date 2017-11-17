package com.cmic.attendance.service;

import com.cmic.attendance.dao.DailyDao;
import com.cmic.attendance.model.Attendance;
import com.cmic.attendance.model.Daily;
import com.cmic.attendance.vo.DailyVo;
import com.cmic.saas.base.model.BaseAdminEntity;
import com.cmic.saas.base.service.CrudService;
import com.cmic.saas.base.web.RestException;
import com.cmic.saas.utils.WebUtils;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.commons.lang3.StringUtils;
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
        System.out.println("===========================考勤id======================= = " + dailyVo.getAttendanceId());
        if(this.dao.getDailyByAttendanceId(dailyVo.getAttendanceId())!=null){
            return "0";
        }

        BaseAdminEntity user= (BaseAdminEntity)request.getSession().getAttribute("_CURRENT_ADMIN_INFO");
        dailyVo.setSuggestionStatus("1");//意见状态设置为未阅
        if(StringUtils.isBlank(dailyVo.getFinishedWork()) && StringUtils.isBlank(dailyVo.getDailyDesc())){
            dailyVo.setDailyDesc("有未完成工作");
        }
        dailyVo.setExaminer("陈华龙");
        dailyVo.preInsert();

        Attendance attendance = attendanceService.get(dailyVo.getAttendanceId());
//        考勤不存在则插入
        if (attendance==null){
            attendance = new Attendance();
            if (null == user ) {
                return "1";
            }
            attendance.setAttendanceStatus("1");
            attendance.setDailyStatus("1");
            Calendar cal = Calendar.getInstance();
            Integer month = cal.get(Calendar.MONTH )+1;
            attendance.setAttendanceMonth(cal.get(Calendar.YEAR )+"-"+month.toString());
            attendance.setAttendanceUser(user.getName());
            attendanceService.save(attendance);
        }else{
            attendance.setUpdateTime(new Date());
            attendance.setDailyStatus("1");
            attendance.setAttendanceUser(user.getName());
            attendanceService.update(attendance);
        }
        dailyVo.setAttendanceId(attendance.getId());
        dailyVo.setExamineTime(dailyVo.getCreateTime());
        dailyVo.setUsername(user.getName());
        this.save(dailyVo);
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

    public  Map<String, Object> findDailyList(PageInfo<Daily> page ,Daily daily){
        if(page.getPageNum() == 0) {
            page.setPageNum(1);
        }

        if(page.getPageSize() == 0) {
            page.setPageSize(10);
        }

        if(StringUtils.isEmpty(page.getOrderBy())) {
            page.setOrderBy("a.create_time desc");
        }

        PageHelper.startPage(page.getPageNum(), page.getPageSize() > 0?page.getPageSize():10, page.getOrderBy());

        PageInfo<Map> result=  new PageInfo(dao.findDailyList(daily));

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