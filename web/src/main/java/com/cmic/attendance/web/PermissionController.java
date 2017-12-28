package com.cmic.attendance.web;

import com.github.pagehelper.PageInfo;
import com.cmic.saas.base.web.BaseRestController;
import com.cmic.attendance.model.Permission;
import com.cmic.attendance.service.PermissionService;
import io.swagger.annotations.*;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

/**
* 角色权限中间表Controller
*/
@Api(description = "角色权限中间表管理")
@RestController
@RequestMapping("/permission")
public class PermissionController extends BaseRestController<PermissionService> {

    @ApiOperation(value = "查询", notes = "查询角色权限中间表列表", httpMethod = "GET")
    @ApiImplicitParams({
        @ApiImplicitParam(name = "orderBy", value="排序", defaultValue = "createDate desc", paramType = "query"),
        @ApiImplicitParam(name = "pageSize", value="分页大小", defaultValue = "10", paramType = "query"),
        @ApiImplicitParam(name = "pageNum", value="页码", defaultValue = "1", paramType = "query")
    })
    @RequestMapping(value="/", method = RequestMethod.GET)
    public PageInfo<Permission> get(@ApiIgnore Permission permission, @ApiIgnore PageInfo page){
        page = service.findPage(page, permission);
        return page;
    }

    @ApiOperation(value = "新增", notes = "新增角色权限中间表", httpMethod = "POST")
    @RequestMapping(value="/", method = RequestMethod.POST)
    public Permission post(@Validated @RequestBody Permission permission){
        service.insert(permission);
        return permission;
    }

    @ApiOperation(value = "获取", notes = "获取角色权限中间表", httpMethod = "GET")
    @RequestMapping(value="/{id}", method = RequestMethod.GET)
    public Permission get(@ApiParam(value = "角色权限中间表ID") @PathVariable String id){
        Permission permission = service.get(id);
        return permission;
    }

    @ApiOperation(value = "新增/更新", notes = "新增/更新角色权限中间表", httpMethod = "PUT")
    @RequestMapping(value="/{id}", method = RequestMethod.PUT)
    public Permission put(@PathVariable String id, @Validated @RequestBody Permission permission){
        permission.setId(id);
        service.save(permission);
        return get(id);
    }

    @ApiOperation(value = "动态更新", notes = "动态更新角色权限中间表", httpMethod = "PATCH")
    @RequestMapping(value="/{id}", method = RequestMethod.PATCH)
    public Permission patch(@ApiParam(value = "角色权限中间表ID") @PathVariable String id, @RequestBody Permission permission){
        permission.setId(id);
        service.dynamicUpdate(permission);
        return get(id);
    }

    @ApiOperation(value = "删除", notes = "删除角色权限中间表", httpMethod = "DELETE")
    @RequestMapping(value="/{id}", method = RequestMethod.DELETE)
    public void delete(@ApiParam(value = "角色权限中间表ID") @PathVariable String id){
        service.delete(id);
    }

}