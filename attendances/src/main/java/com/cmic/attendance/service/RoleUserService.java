package com.cmic.attendance.service;

import com.github.pagehelper.PageInfo;
import com.cmic.saas.base.service.CrudService;
import com.cmic.saas.base.web.RestException;
import com.cmic.saas.utils.StringUtils;
import com.cmic.attendance.dao.RoleUserDao;
import com.cmic.attendance.model.RoleUser;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


/**
* 用户角色中间表Service
*/
@Service
@Transactional(readOnly = true)
public class RoleUserService extends CrudService<RoleUserDao, RoleUser> {

    public RoleUser get(String id) {
        return super.get(id);
    }

    public List<RoleUser> findList(RoleUser roleUser) {
        return super.findList(roleUser);
    }

    public PageInfo<RoleUser> findPage(PageInfo<RoleUser> page, RoleUser roleUser) {
        return super.findPage(page, roleUser);
    }

    @Transactional(readOnly = false)
    public void save(RoleUser roleUser) {
        super.save(roleUser);
    }

    @Transactional(readOnly = false)
    public void dynamicUpdate(RoleUser roleUser) {
        super.dynamicUpdate(roleUser);
    }

    @Transactional(readOnly = false)
    public void delete(String id) {
        //判断是否存在
        RoleUser roleUser = get(id);
        if(roleUser==null|| StringUtils.isEmpty(roleUser.getId())){
            throw new RestException("删除失败，用户角色中间表不存在");
        }
        super.delete(id);
        logger.info("删除用户角色中间表：" + roleUser.toJSONString());
    }

}