package com.cmic.attendance.service;

import com.github.pagehelper.PageInfo;
import com.cmic.saas.base.service.CrudService;
import com.cmic.saas.base.web.RestException;
import com.cmic.saas.utils.StringUtils;
import com.cmic.attendance.dao.RolePermissionDao;
import com.cmic.attendance.model.RolePermission;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


/**
* 角色权限中间表Service
*/
@Service
@Transactional(readOnly = true)
public class RolePermissionService extends CrudService<RolePermissionDao, RolePermission> {

    public RolePermission get(String id) {
        return super.get(id);
    }

    public List<RolePermission> findList(RolePermission rolePermission) {
        return super.findList(rolePermission);
    }

    public PageInfo<RolePermission> findPage(PageInfo<RolePermission> page, RolePermission rolePermission) {
        return super.findPage(page, rolePermission);
    }

    @Transactional(readOnly = false)
    public void save(RolePermission rolePermission) {
        super.save(rolePermission);
    }

    @Transactional(readOnly = false)
    public void dynamicUpdate(RolePermission rolePermission) {
        super.dynamicUpdate(rolePermission);
    }

    @Transactional(readOnly = false)
    public void delete(String id) {
        //判断是否存在
        RolePermission rolePermission = get(id);
        if(rolePermission==null|| StringUtils.isEmpty(rolePermission.getId())){
            throw new RestException("删除失败，角色权限中间表不存在");
        }
        super.delete(id);
        logger.info("删除角色权限中间表：" + rolePermission.toJSONString());
    }

}