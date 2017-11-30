package com.cmic.attendance.admin;

import com.cmic.attendance.model.Daily;
import com.cmic.attendance.service.DailyService;
import com.cmic.attendance.vo.DailyVo;
import com.cmic.saas.base.web.BaseRestController;
import io.swagger.annotations.Api;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.*;

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
     * @author 何家来
     * 分页查询日报列表
     * @param  dailyVo 封装 pageInfo , username 两个参数，其中pageInfo:pageSize,pageNum
     * @return
     */
    @PostMapping("/dailyList")
    public Map<String, Object> get(@RequestBody DailyVo dailyVo) {

        return service.findDailyList(dailyVo);
    }


}