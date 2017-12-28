package com.cmic.attendance.service;

import com.github.pagehelper.PageInfo;
import com.cmic.saas.base.service.CrudService;
import com.cmic.saas.base.web.RestException;
import com.cmic.saas.utils.StringUtils;
import com.cmic.attendance.dao.PermissionDao;
import com.cmic.attendance.model.Permission;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


/**
* 角色权限中间表Service
*/
@Service
@Transactional(readOnly = true)
public class PermissionService extends CrudService<PermissionDao, Permission> {

    public Permission get(String id) {
        return super.get(id);
    }

    public List<Permission> findList(Permission permission) {
        return super.findList(permission);
    }

    public PageInfo<Permission> findPage(PageInfo<Permission> page, Permission permission) {
        return super.findPage(page, permission);
    }

    @Transactional(readOnly = false)
    public void save(Permission permission) {
        super.save(permission);
    }

    @Transactional(readOnly = false)
    public void dynamicUpdate(Permission permission) {
        super.dynamicUpdate(permission);
    }

    @Transactional(readOnly = false)
    public void delete(String id) {
        //判断是否存在
        Permission permission = get(id);
        if(permission==null|| StringUtils.isEmpty(permission.getId())){
            throw new RestException("删除失败，角色权限中间表不存在");
        }
        super.delete(id);
        logger.info("删除角色权限中间表：" + permission.toJSONString());
    }

}