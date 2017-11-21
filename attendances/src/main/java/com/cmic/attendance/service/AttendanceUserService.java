package com.cmic.attendance.service;

import com.cmic.attendance.dao.AttendanceUserDao;
import com.cmic.attendance.model.AttendanceUser;
import com.cmic.attendance.utils.MD5Util;
import com.cmic.attendance.vo.AttendanceUserVo;
import com.cmic.saas.base.service.CrudService;
import com.cmic.saas.base.web.RestException;
import com.cmic.saas.utils.StringUtils;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
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

    @Transactional(readOnly = false)
    public HashMap<String, String> login(AttendanceUserVo attendanceUserVo,HttpServletRequest request){
        AttendanceUser checkUser = checkUserByName(attendanceUserVo.getAttendanceUsername());
        HashMap<String,String> result=new HashMap<String,String>();

        if (null==checkUser){
            //当前用户不存在
            result.put("msg","当前用户不存在");
            result.put("status","1");
            return result;
        }

        String attendancePassword = attendanceUserVo.getAttendancePassword();
        String md5Password = MD5Util.md5(attendancePassword);
        System.out.println("md5Password"+md5Password);
        if (!checkUser.getAttendancePassword().equals(md5Password)){
            result.put("msg","密码错误");
            result.put("status","2");
            return result;
        }

        result.put("msg","登录成功");
        result.put("status","0");
        //服务器session
        request.getSession().setAttribute("attendanceUserVo",attendanceUserVo);
        return result;
    }

    //检查当前用户是否存在
    public AttendanceUser checkUserByName(String userName){
        return this.dao.checkUserByName(userName);
    }

}