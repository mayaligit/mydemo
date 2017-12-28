package com.cmic.attendance.service;

import com.github.pagehelper.PageInfo;
import com.cmic.saas.base.service.CrudService;
import com.cmic.saas.base.web.RestException;
import com.cmic.saas.utils.StringUtils;
import com.cmic.attendance.dao.RoleDao;
import com.cmic.attendance.model.Role;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


/**
* 角色表Service
*/
@Service
@Transactional(readOnly = true)
public class RoleService extends CrudService<RoleDao, Role> {

    public Role get(String id) {
        return super.get(id);
    }

    public List<Role> findList(Role role) {
        return super.findList(role);
    }

    public PageInfo<Role> findPage(PageInfo<Role> page, Role role) {
        return super.findPage(page, role);
    }

    @Transactional(readOnly = false)
    public void save(Role role) {
        super.save(role);
    }

    @Transactional(readOnly = false)
    public void dynamicUpdate(Role role) {
        super.dynamicUpdate(role);
    }

    @Transactional(readOnly = false)
    public void delete(String id) {
        //判断是否存在
        Role role = get(id);
        if(role==null|| StringUtils.isEmpty(role.getId())){
            throw new RestException("删除失败，角色表不存在");
        }
        super.delete(id);
        logger.info("删除角色表：" + role.toJSONString());
    }

}