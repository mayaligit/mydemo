package com.cmic.attendance.service;

import com.cmic.attendance.dao.RoleDao;
import com.cmic.attendance.model.AttendanceUser;
import com.cmic.attendance.model.Role;
import com.cmic.attendance.model.RoleUser;
import com.cmic.attendance.pojo.AttendanceUserPojo;
import com.cmic.saas.base.service.CrudService;
import com.cmic.saas.base.web.RestException;
import com.cmic.saas.utils.StringUtils;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.HashMap;
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
    @Autowired
    private AttendanceUserService attendanceUserService;
    @Autowired
    private RoleUserService roleUserService;


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

    @Transactional(readOnly = false)
    public void saveRole(AttendanceUser attendanceUser, AttendanceUserPojo attendanceUserPojo) {
        Integer role = attendanceUserPojo.getRole();

        //考勤用户表中插入数据
        logger.debug("=====插入用户的信息start====="+attendanceUser.toString());
        attendanceUserService.save(attendanceUser);
        logger.debug("=====插入用户的信息end====="+attendanceUser.toString());

        //====维护角色中间表===
        RoleUser roleUser = new RoleUser();
        roleUser.setRoleId(attendanceUserPojo.getRoleId());
        roleUser.setUserId(attendanceUser.getId());
        roleUserService.save(roleUser);

        //判断是否是 考勤的角色
        Map<String ,Object> paraMap = new HashMap<String,Object>();
        if (role == 2){ //招聘需求方
            //维护 面试官表
            paraMap.put("id",attendanceUser.getId());
            paraMap.put("interviewerName",attendanceUserPojo.getUsername());
            paraMap.put("department",attendanceUserPojo.getDepartment());
            paraMap.put("interviewerPhone",attendanceUserPojo.getAttendancePhone());
            paraMap.put("interviewerEmail",attendanceUserPojo.getEmail());
            paraMap.put("createDate",new Date());
            paraMap.put("createBy",attendanceUser.getCreateBy().getId());
            paraMap.put("updateDate",paraMap.get("createDate"));
            paraMap.put("updateBy",paraMap.get("createBy"));
            dao.insertInterviewerUser(paraMap);

            //维护用户角色中间表
            RoleUser interviewerRoleUser = new RoleUser();
            interviewerRoleUser.setUserId(attendanceUser.getId());
            interviewerRoleUser.setRoleId(attendanceUserPojo.getRoleId());
            roleUserService.save(interviewerRoleUser);

        }else if (role == 3){ //招聘供应商
            //维护供应商负责人用户角色中间表
            paraMap.put("id",attendanceUser.getId());
            paraMap.put("principalName",attendanceUserPojo.getUsername());
            paraMap.put("projectName",attendanceUserPojo.getDepartment());
            paraMap.put("supplierId",attendanceUserPojo.getSupplierId());
            paraMap.put("principalPhone",attendanceUserPojo.getAttendancePhone());
            paraMap.put("principalEmail",attendanceUserPojo.getEmail());
            paraMap.put("createDate",new Date());
            paraMap.put("createBy",attendanceUser.getCreateBy().getId());
            paraMap.put("updateDate",paraMap.get("createDate"));
            paraMap.put("updateBy",paraMap.get("createBy"));
            dao.insertPrincipalUser(paraMap);

            //维护用户角色中间表
            RoleUser interviewerRoleUser = new RoleUser();
            interviewerRoleUser.setUserId(attendanceUser.getId());
            interviewerRoleUser.setRoleId(attendanceUserPojo.getRoleId());
            roleUserService.save(interviewerRoleUser);

        }

    }
}