package com.cmic.attendance.dao;

import com.cmic.attendance.model.Employee;
import com.cmic.attendance.pojo.AttendancePojo;
import com.cmic.saas.base.dao.CrudDao;
import org.apache.ibatis.annotations.Mapper;

/**
* 入职人员信息表Dao
*/
@Mapper
public interface EmployeeDao extends CrudDao<Employee> {

    public Employee findEmployeeByTelephone(String telephone);

    int getTotal(AttendancePojo attendancePojo);


    Employee getEmployee(Employee employee);
}