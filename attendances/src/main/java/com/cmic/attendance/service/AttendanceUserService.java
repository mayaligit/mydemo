package com.cmic.attendance.service;

import com.github.pagehelper.PageInfo;
import com.cmic.saas.base.service.CrudService;
import com.cmic.saas.base.web.RestException;
import com.cmic.saas.utils.StringUtils;
import com.cmic.attendance.dao.AttendanceUserDao;
import com.cmic.attendance.model.AttendanceUser;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


/**
* 后台y用户表Service
*/
@Service
@Transactional(readOnly = true)
public class AttendanceUserService extends CrudService<AttendanceUserDao, AttendanceUser> {

    public AttendanceUser get(String id) {
        return super.get(id);
    }

    public List<AttendanceUser> findList(AttendanceUser attendanceUser) {
        return super.findList(attendanceUser);
    }

    public PageInfo<AttendanceUser> findPage(PageInfo<AttendanceUser> page, AttendanceUser attendanceUser) {
        return super.findPage(page, attendanceUser);
    }

    @Transactional(readOnly = false)
    public void save(AttendanceUser attendanceUser) {
        super.save(attendanceUser);
    }

    @Transactional(readOnly = false)
    public void dynamicUpdate(AttendanceUser attendanceUser) {
        super.dynamicUpdate(attendanceUser);
    }

    @Transactional(readOnly = false)
    public void delete(String id) {
        //判断是否存在
        AttendanceUser attendanceUser = get(id);
        if(attendanceUser==null|| StringUtils.isEmpty(attendanceUser.getId())){
            throw new RestException("删除失败，后台y用户表不存在");
        }
        super.delete(id);
        logger.info("删除后台y用户表：" + attendanceUser.toJSONString());
    }

}