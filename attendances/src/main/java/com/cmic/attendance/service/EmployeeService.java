package com.cmic.attendance.service;

import com.cmic.attendance.dao.EmployeeDao;
import com.cmic.attendance.model.Employee;
import com.cmic.attendance.pojo.AttendancePojo;
import com.cmic.saas.base.service.CrudService;
import com.cmic.saas.base.web.RestException;
import com.cmic.saas.utils.StringUtils;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


/**
* 入职人员信息表Service
*/
@Service
@Transactional(readOnly = true)
public class EmployeeService extends CrudService<EmployeeDao, Employee> {

    public Employee get(String id) {
        return super.get(id);
    }

    public List<Employee> findList(Employee employee) {
        return super.findList(employee);
    }

    public Employee findEmployeeByTelephone(String telephone){
        return dao.findEmployeeByTelephone(telephone);
    }

    public PageInfo<Employee> findPage(PageInfo<Employee> page, Employee employee) {
        return super.findPage(page, employee);
    }

    @Transactional(readOnly = false)
    public void save(Employee employee) {
        super.save(employee);
    }

    @Transactional(readOnly = false)
    public void dynamicUpdate(Employee employee) {
        super.dynamicUpdate(employee);
    }

    @Transactional(readOnly = false)
    public void delete(String id) {
        //判断是否存在
        Employee employee = get(id);
        if(employee==null|| StringUtils.isEmpty(employee.getId())){
            throw new RestException("删除失败，入职人员信息表不存在");
        }
        super.delete(id);
        logger.info("删除入职人员信息表：" + employee.toJSONString());
    }

    public int getTotal(AttendancePojo attendancePojo) {

        return dao.getTotal(attendancePojo);
    }

    public Employee getEmployee(Employee employee){

        return dao.getEmployee(employee);
    }



}