package com.cmic.attendance.employee;

import com.cmic.attendance.model.Employee;
import com.cmic.attendance.service.EmployeeService;
import com.cmic.saas.base.model.BaseAdminEntity;
import com.cmic.saas.base.web.BaseRestController;
import com.cmic.saas.utils.WebUtils;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
* 入职人员信息表Controller
*/
//@Api(description = "入职人员信息表管理")
@RestController
@RequestMapping("/employee")
public class EmployeeController extends BaseRestController<EmployeeService> {

    @ApiOperation(value = "查询", notes = "查询入职人员信息表列表", httpMethod = "GET")
    @ApiImplicitParams({
        @ApiImplicitParam(name = "orderBy", value="排序", defaultValue = "createDate desc", paramType = "query"),
        @ApiImplicitParam(name = "pageSize", value="分页大小", defaultValue = "10", paramType = "query"),
        @ApiImplicitParam(name = "pageNum", value="页码", defaultValue = "1", paramType = "query")
    })
    @RequestMapping(value="/", method = RequestMethod.GET)
    public PageInfo<Employee> get(@ApiIgnore Employee employee, @ApiIgnore PageInfo page){
        page = service.findPage(page, employee);
        return page;
    }

    @ApiOperation(value = "新增", notes = "新增入职人员信息表", httpMethod = "POST")
    @RequestMapping(value="/", method = RequestMethod.POST)
    public Employee post(@Validated @RequestBody Employee employee){
        service.insert(employee);
        return employee;
    }

    @ApiOperation(value = "获取", notes = "获取入职人员信息表", httpMethod = "GET")
    @RequestMapping(value="/{id}", method = RequestMethod.GET)
    public Employee get(@ApiParam(value = "入职人员信息表ID") @PathVariable String id){
        Employee employee = service.get(id);
        return employee;
    }

    @ApiOperation(value = "新增/更新", notes = "新增/更新入职人员信息表", httpMethod = "PUT")
    @RequestMapping(value="/{id}", method = RequestMethod.PUT)
    public Employee put(@PathVariable String id, @Validated @RequestBody Employee employee){
        employee.setId(id);
        service.save(employee);
        return get(id);
    }

    @ApiOperation(value = "动态更新", notes = "动态更新入职人员信息表", httpMethod = "PATCH")
    @RequestMapping(value="/{id}", method = RequestMethod.PATCH)
    public Employee patch(@ApiParam(value = "入职人员信息表ID") @PathVariable String id, @RequestBody Employee employee){
        employee.setId(id);
        service.dynamicUpdate(employee);
        return get(id);
    }

    //@ApiOperation(value = "删除", notes = "删除入职人员信息表", httpMethod = "DELETE")
    @RequestMapping(value="/{id}", method = RequestMethod.DELETE)
    public void delete(@ApiParam(value = "入职人员信息表ID") @PathVariable String id){
        service.delete(id);
    }

//    @ApiOperation(value = "新增", notes = "新增入职人员信息表", httpMethod = "POST")
    @RequestMapping(value="/save"/*, method = RequestMethod.POST*/)
    public Map save(@RequestBody Employee employee){
        Map map = new HashMap<>();
        Employee e = service.getEmployee(employee);
        map.put("flag",0);
        if(null != e){
            map.put("flag",1);
            return map;
        }
        BaseAdminEntity adminEntity = new BaseAdminEntity();
        adminEntity.setId(employee.getTelephone());
        adminEntity.setName(employee.getEmployeeName());

        HttpServletRequest request = WebUtils.getRequest();
        request.getSession().setAttribute("_CURRENT_ADMIN_INFO"    ,adminEntity);
        service.insert(employee);
        return map;
    }

}