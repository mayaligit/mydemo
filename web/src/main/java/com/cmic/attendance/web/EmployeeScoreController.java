package com.cmic.attendance.web;

import com.cmic.saas.base.web.RestException;
import com.cmic.saas.utils.StringUtils;
import com.github.pagehelper.PageInfo;
import com.cmic.saas.base.web.BaseRestController;
import com.cmic.attendance.model.EmployeeScore;
import com.cmic.attendance.service.EmployeeScoreService;
import io.swagger.annotations.*;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

/**
 * Controller
 */
@Api(description = "管理")
@RestController
@RequestMapping("/score")
public class EmployeeScoreController extends BaseRestController<EmployeeScoreService> {

    @RequestMapping(value="/getEmployeeByGroup")
    public List<HashMap> getEmployeeByGroup(@RequestBody EmployeeScore employeeScore){
        try{
            employeeScore.setBeginNum((employeeScore.getPageNum()-1)*employeeScore.getPageSize());
            SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyy-MM");
            String month=simpleDateFormat.format(new Date());
            employeeScore.setMonth(month);
            List<HashMap> list=service.getEmployeeByGroup(employeeScore);
            return list;
        }catch (Exception e){
            e.printStackTrace();
            throw new RestException("分组查询雇员异常");
        }
    }



    @ApiOperation(value = "查询", notes = "查询列表", httpMethod = "GET")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value="主键使用uuid", paramType = "query"),
            @ApiImplicitParam(name = "phone", value="手机号", paramType = "query"),
            @ApiImplicitParam(name = "orderBy", value="排序", defaultValue = "createDate desc", paramType = "query"),
            @ApiImplicitParam(name = "pageSize", value="分页大小", defaultValue = "10", paramType = "query"),
            @ApiImplicitParam(name = "pageNum", value="页码", defaultValue = "1", paramType = "query")
    })
    @RequestMapping(value="/", method = RequestMethod.GET)
    public PageInfo<EmployeeScore> get(@ApiIgnore EmployeeScore employeeScore, @ApiIgnore PageInfo page){
        page = service.findPage(page, employeeScore);
        return page;
    }

    @ApiOperation(value = "新增", notes = "新增", httpMethod = "POST")
    @RequestMapping(value="/", method = RequestMethod.POST)
    public EmployeeScore post(@Validated @RequestBody EmployeeScore employeeScore){
        service.insert(employeeScore);
        return employeeScore;
    }

    @ApiOperation(value = "获取", notes = "获取", httpMethod = "GET")
    @RequestMapping(value="/{id}", method = RequestMethod.GET)
    public EmployeeScore get(@ApiParam(value = "ID") @PathVariable String id){
        EmployeeScore employeeScore = service.get(id);
        return employeeScore;
    }

    @RequestMapping(value="/getEmployeeScoreByPhoneAndMonth", method = RequestMethod.POST)
    public EmployeeScore getEmployeeScoreByPhoneAndMonth(@RequestBody EmployeeScore employeeScore){
        try{
            return service.getEmployeeScoreByPhoneAndMonth(employeeScore);
        }catch (Exception e){
            e.printStackTrace();
            throw new RestException("查询打分详情异常！");
        }
    }

    @RequestMapping(value="/insertOrUpdateEmployeeScore", method = RequestMethod.POST)
    public String insertOrUpdateEmployeeScore(@RequestBody EmployeeScore employeeScore){
        try{
            String id=employeeScore.getId();
            if((id!=null)&&(!"".equals(id.trim()))){
                //修改打分
                service.dynamicUpdate(employeeScore);
            }else {
                //插入打分
                String uuid = UUID.randomUUID().toString().replaceAll("-", "");
                employeeScore.setScoreId(uuid);
                service.insert(employeeScore);
            }
            return "打分成功！";
        }catch (Exception e){
            e.printStackTrace();
            throw new RestException("查询打分详情异常！");
        }
    }

    @ApiOperation(value = "新增/更新", notes = "新增/更新", httpMethod = "POST")
    @RequestMapping(value="/save/{id}", method = RequestMethod.POST)
    public EmployeeScore put(@PathVariable String id, @Validated @RequestBody EmployeeScore employeeScore){
        employeeScore.setId(id);
        service.save(employeeScore);
        return get(id);
    }

    @ApiOperation(value = "动态更新", notes = "动态更新", httpMethod = "PATCH")
    @RequestMapping(value="/{id}", method = RequestMethod.PATCH)
    public EmployeeScore patch(@ApiParam(value = "ID") @PathVariable String id, @RequestBody EmployeeScore employeeScore){
        employeeScore.setId(id);
        service.dynamicUpdate(employeeScore);
        return get(id);
    }

    @ApiOperation(value = "删除", notes = "删除", httpMethod = "DELETE")
    @RequestMapping(value="/{id}", method = RequestMethod.DELETE)
    public void delete(@ApiParam(value = "ID") @PathVariable String id){
        service.delete(id);
    }

}