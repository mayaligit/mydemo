package com.cmic.attendance.service;

import com.cmic.attendance.dao.RoleDao;
import com.cmic.attendance.model.Role;
import com.cmic.saas.base.service.CrudService;
import com.cmic.saas.base.web.RestException;
import com.cmic.saas.utils.StringUtils;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

/**
* 角色表Service
*/
@Service
@Transactional(readOnly = true)
public class RoleService extends CrudService<RoleDao, Role> {
    @Autowired
    private RoleDao roleDao;

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
    //返回供应商的id和名称
    public List<Map> getSupplierList(){
        return roleDao.supplierList();
    }

    public List<Map> getRoleList() {
        return roleDao.getRoleList();
    }
}