package com.cmic.attendance.service;

import com.cmic.attendance.dao.AttendanceUserDao;
import com.cmic.attendance.model.AttendanceUser;
import com.cmic.attendance.model.Audit;
import com.cmic.attendance.model.GroupRule;
import com.cmic.attendance.utils.MD5Util;
import com.cmic.attendance.vo.AttendanceUserVo;
import com.cmic.saas.base.model.BaseAdminEntity;
import com.cmic.saas.base.service.CrudService;
import com.cmic.saas.base.web.RestException;
import com.cmic.saas.utils.StringUtils;
import com.cmic.saas.utils.WebUtils;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


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
        if (!checkUser.getAttendancePassword().equals(md5Password)){
            result.put("msg","密码错误");
            result.put("status","2");
            return result;
        }
        result.put("msg","登录成功");
        result.put("status","0");
        result.put("phone",checkUser.getAttendancePhone());
        result.put("userType",checkUser.getUserType());
        //result.put("attendance_group",checkUser.getAttendanceGroup());
        attendanceUserVo.setAttendanceGroup(checkUser.getAttendanceGroup());
        attendanceUserVo.setId(checkUser.getId());
        attendanceUserVo.setUserType(checkUser.getUserType());
        //服务器session
        request.getSession().setAttribute("attendanceUserVo",attendanceUserVo);
        //系统架构session
        BaseAdminEntity adminEntity = new BaseAdminEntity();
        adminEntity.setId(checkUser.getAttendancePhone());
        adminEntity.setName(checkUser.getAttendanceUsername());
        request.getSession().setAttribute("_CURRENT_ADMIN_INFO",adminEntity);
        return result;
    }

    //检查当前用户是否存在
    public AttendanceUser checkUserByName(String userName){
        return this.dao.checkUserByName(userName);
    }

    //查询用户列表信息
    public Map<String, Object> findAttendanceUserList(int pageNum,int pageSize,String attendanceUsername) {
        //创建封装数据
        Map<String,Object> paramMap = new HashMap<>();
        if(pageNum==0){
            pageNum=1;
        }
        paramMap.put("pageNum",pageNum);
        paramMap.put("pageSize",pageSize);
        if(attendanceUsername!=null) {
            paramMap.put("attendanceUsername", "%" + attendanceUsername + "%");
        }
        //设置查询参数和排序条件
        PageHelper.startPage(pageNum,pageSize);

        //分页查询并获取分页信息
        List<Map> attendanceUserList = dao.findAttendanceUserList(paramMap);
        PageInfo<AttendanceUser> pageInfo =  new PageInfo(attendanceUserList);
        //创建对象对相应数据进行封装
        Map<String,Object> responseMap = new HashMap<String,Object>();
        List< Map<String,Object>> ruleList = new ArrayList<>();

        //获取总页数和总记录数
        responseMap.put("totalPages",pageInfo.getPages());
        responseMap.put("totalCount",pageInfo.getTotal());
        responseMap.put("ruleList",pageInfo.getList());
        return responseMap;
    }

}