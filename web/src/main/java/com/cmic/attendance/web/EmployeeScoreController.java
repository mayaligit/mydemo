package com.cmic.attendance.web;

import com.cmic.attendance.model.EmployeeScore;
import com.cmic.attendance.service.EmployeeScoreService;
import com.github.pagehelper.PageInfo;
import com.cmic.saas.base.web.BaseRestController;
import io.swagger.annotations.*;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

/**
* 雇员分数Controller
*/
@Api(description = "雇员分数管理")
@RestController
@RequestMapping("/score")
public class EmployeeScoreController extends BaseRestController<EmployeeScoreService> {

    @ApiOperation(value = "查询", notes = "查询雇员分数列表", httpMethod = "GET")
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

    @ApiOperation(value = "新增", notes = "新增雇员分数", httpMethod = "POST")
    @RequestMapping(value="/", method = RequestMethod.POST)
    public EmployeeScore post(@Validated @RequestBody EmployeeScore employeeScore){
        service.insert(employeeScore);
        return employeeScore;
    }

    @ApiOperation(value = "获取", notes = "获取雇员分数", httpMethod = "GET")
    @RequestMapping(value="/{id}", method = RequestMethod.GET)
    public EmployeeScore get(@ApiParam(value = "雇员分数ID") @PathVariable String id){
        EmployeeScore employeeScore = service.get(id);
        return employeeScore;
    }

    @ApiOperation(value = "新增/更新", notes = "新增/更新雇员分数", httpMethod = "PUT")
    @RequestMapping(value="/{id}", method = RequestMethod.PUT)
    public EmployeeScore put(@PathVariable String id, @Validated @RequestBody EmployeeScore employeeScore){
        employeeScore.setId(id);
        service.save(employeeScore);
        return get(id);
    }

    @ApiOperation(value = "动态更新", notes = "动态更新雇员分数", httpMethod = "PATCH")
    @RequestMapping(value="/{id}", method = RequestMethod.PATCH)
    public EmployeeScore patch(@ApiParam(value = "雇员分数ID") @PathVariable String id, @RequestBody EmployeeScore employeeScore){
        employeeScore.setId(id);
        service.dynamicUpdate(employeeScore);
        return get(id);
    }

    @ApiOperation(value = "删除", notes = "删除雇员分数", httpMethod = "DELETE")
    @RequestMapping(value="/{id}", method = RequestMethod.DELETE)
    public void delete(@ApiParam(value = "雇员分数ID") @PathVariable String id){
        service.delete(id);
    }

}