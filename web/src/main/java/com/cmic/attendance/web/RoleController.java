package com.cmic.attendance.web;

import com.cmic.attendance.model.Role;
import com.cmic.attendance.service.RoleService;
import com.cmic.saas.base.web.BaseRestController;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.*;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import java.util.List;
import java.util.Map;

/**
* 角色表Controller
*/
@Api(description = "角色表管理")
@RestController
@RequestMapping("/role")
public class RoleController extends BaseRestController<RoleService> {

    @ApiOperation(value = "查询", notes = "查询角色表列表", httpMethod = "GET")
    @ApiImplicitParams({
        @ApiImplicitParam(name = "orderBy", value="排序", defaultValue = "createDate desc", paramType = "query"),
        @ApiImplicitParam(name = "pageSize", value="分页大小", defaultValue = "10", paramType = "query"),
        @ApiImplicitParam(name = "pageNum", value="页码", defaultValue = "1", paramType = "query")
    })
    @RequestMapping(value="/", method = RequestMethod.GET)
    public PageInfo<Role> get(@ApiIgnore Role role, @ApiIgnore PageInfo page){
        page = service.findPage(page, role);
        return page;
    }

    @ApiOperation(value = "新增", notes = "新增角色表", httpMethod = "POST")
    @RequestMapping(value="/", method = RequestMethod.POST)
    public Role post(@Validated @RequestBody Role role){
        service.insert(role);
        return role;
    }

    @ApiOperation(value = "获取", notes = "获取角色表", httpMethod = "GET")
    @RequestMapping(value="/{id}", method = RequestMethod.GET)
    public Role get(@ApiParam(value = "角色表ID") @PathVariable String id){
        Role role = service.get(id);
        return role;
    }

    @ApiOperation(value = "新增/更新", notes = "新增/更新角色表", httpMethod = "PUT")
    @RequestMapping(value="/{id}", method = RequestMethod.PUT)
    public Role put(@PathVariable String id, @Validated @RequestBody Role role){
        role.setId(id);
        service.save(role);
        return get(id);
    }

    @ApiOperation(value = "动态更新", notes = "动态更新角色表", httpMethod = "PATCH")
    @RequestMapping(value="/{id}", method = RequestMethod.PATCH)
    public Role patch(@ApiParam(value = "角色表ID") @PathVariable String id, @RequestBody Role role){
        role.setId(id);
        service.dynamicUpdate(role);
        return get(id);
    }

    @ApiOperation(value = "删除", notes = "删除角色表", httpMethod = "DELETE")
    @RequestMapping(value="/{id}", method = RequestMethod.DELETE)
    public void delete(@ApiParam(value = "角色表ID") @PathVariable String id){
        service.delete(id);
    }

    //返回所有供应商id和名字
    @ApiOperation(value = "查询", notes = "查询供应商", httpMethod = "GET")
    @RequestMapping(value="getSupplierList", method = RequestMethod.GET)
    public List<Map> getSupplierList() {
        return service.getSupplierList();
    }
}