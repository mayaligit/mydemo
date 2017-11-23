package com.cmic.attendance.admin;

import com.cmic.attendance.model.Daily;
import com.cmic.attendance.service.DailyService;
import com.cmic.attendance.vo.AttendanceUserVo;
import com.cmic.saas.base.web.BaseRestController;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.Api;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.Map;

/**
* 日报表Controller
*/
@Api(description = "日报表后台管理")
@RestController
@RequestMapping("/adminDaily")
public class DailyAdminController extends BaseRestController<DailyService> {

    //获取单条日报信息
    @RequestMapping(value="/dailyDetail/{dailyId}", method = RequestMethod.GET)
     public Daily findDailyById(@PathVariable String dailyId){
        if (StringUtils.isBlank(dailyId)){
            return  null ;
        }
         return service.findDailyById(dailyId);
     }

     //日报审批
    @RequestMapping(value="/dailyAudit/{dailyId}", method = RequestMethod.GET)
     public String auditDailyById(@PathVariable String dailyId){
        if (StringUtils.isBlank(dailyId)){
            return  null ;
        }
         return service.auditDailyById(dailyId);
     }

    /**
     * f分页查询日报列表
     * @param parmMap 封装 pageSize ,pageNum , username 三个参数
     * @return
     */
    @PostMapping("/dailyList")
    public Map<String, Object> get(@RequestBody(required = false) Map<String, Object> parmMap,
                                   HttpSession session) {
        //获取session中的用户信息
        AttendanceUserVo attendanceUserVo = (AttendanceUserVo)session.getAttribute("attendanceUserVo");
        String attendance_group = attendanceUserVo.getAttendanceGroup();
        Daily daily = new Daily();
        PageInfo<Daily> page = new PageInfo();

        if (parmMap != null && parmMap.size() > 0) {
            page.setPageNum(parmMap.get("pageNum")== null ? 1:(int)parmMap.get("pageNum"));
            page.setPageSize(parmMap.get("pageSize")==null?10:(int)parmMap.get("pageSize"));
            daily.setUsername((String)parmMap.get("username"));
            daily.setSuggestionStatus((Integer) parmMap.get("suggestionStatus"));
            daily.setAttendanceGroup(attendance_group);
        }

        return service.findDailyList(page, daily);
    }


}