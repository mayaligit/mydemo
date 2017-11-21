package com.cmic.attendance.service;

import com.cmic.attendance.model.Attendance;
import com.github.pagehelper.PageInfo;
import com.cmic.saas.base.service.CrudService;
import com.cmic.saas.base.web.RestException;
import com.cmic.saas.utils.StringUtils;
import com.cmic.attendance.dao.ClazzesDao;
import com.cmic.attendance.model.Clazzes;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;


/**
* 班次表Service
*/
@Service
@Transactional(readOnly = true)
public class ClazzesService extends CrudService<ClazzesDao, Clazzes> {

    public Clazzes get(String id) {
        return super.get(id);
    }

    public List<Clazzes> findList(Clazzes clazzes) {
        return super.findList(clazzes);
    }

    public PageInfo<Clazzes> findPage(PageInfo<Clazzes> page, Clazzes clazzes) {
        return super.findPage(page, clazzes);
    }

    @Transactional(readOnly = false)
    public void save(Clazzes clazzes) {
        super.save(clazzes);
    }

    @Transactional(readOnly = false)
    public void dynamicUpdate(Clazzes clazzes) {
        super.dynamicUpdate(clazzes);
    }

    @Transactional(readOnly = false)
    public void delete(String id) {
        //判断是否存在
        Clazzes clazzes = get(id);
        if(clazzes==null|| StringUtils.isEmpty(clazzes.getId())){
            throw new RestException("删除失败，班次表不存在");
        }
        super.delete(id);
        logger.info("删除班次表：" + clazzes.toJSONString());
    }
    //根据id获取班次表
    public Clazzes getClazzesById(String clazzesId) {
        return dao.getClazzesById(clazzesId);
    }
    public  List<Attendance> checkAttendanceLatterByDay(Map<String, Object> map) {

        dao.checkAttendanceLatterByDay(map);
        return null;
    }

    public Clazzes getTotalById(String id) {
        return dao.getTotalById(id);
    }

    public  int startWork(String attendanceGroup) {
        return dao.startWork(attendanceGroup);
    }
    public  int endWork(String attendanceGroup) {
        return dao.endWork(attendanceGroup);
    }

    public Clazzes getByGroupName(String attendanceGroup) {
        Clazzes clazzes = dao.getByGroupName(attendanceGroup);
        return clazzes;
    }
}