package com.cmic.attendance.service;

import com.github.pagehelper.PageInfo;
import com.cmic.saas.base.service.CrudService;
import com.cmic.saas.base.web.RestException;
import com.cmic.saas.utils.StringUtils;
import com.cmic.attendance.dao.EmployeeScoreDao;
import com.cmic.attendance.model.EmployeeScore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;


/**
 * Service
 */
@Service
@Transactional(readOnly = true)
public class EmployeeScoreService extends CrudService<EmployeeScoreDao, EmployeeScore> {

    @Autowired
    EmployeeScoreDao employeeScoreDao;

    public EmployeeScore get(String id) {
        return super.get(id);
    }

    public List<EmployeeScore> findList(EmployeeScore employeeScore) {
        return super.findList(employeeScore);
    }

    public PageInfo<EmployeeScore> findPage(PageInfo<EmployeeScore> page, EmployeeScore employeeScore) {
        return super.findPage(page, employeeScore);
    }

    @Transactional(readOnly = false)
    public void save(EmployeeScore employeeScore) {
        super.save(employeeScore);
    }

    @Transactional(readOnly = false)
    public void dynamicUpdate(EmployeeScore employeeScore) {
        super.dynamicUpdate(employeeScore);
    }

    @Transactional(readOnly = false)
    public void delete(String id) {
        //判断是否存在
        EmployeeScore employeeScore = get(id);
        if(employeeScore==null|| StringUtils.isEmpty(employeeScore.getId())){
            throw new RestException("删除失败，不存在");
        }
        super.delete(id);
        logger.info("删除：" + employeeScore.toJSONString());
    }

    public List<HashMap> getEmployeeByGroup(EmployeeScore employeeScore){
        return employeeScoreDao.getEmployeeByGroup(employeeScore);
    }

}