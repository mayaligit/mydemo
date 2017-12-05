package com.cmic.attendance.dao;

import com.cmic.saas.base.dao.CrudDao;
import com.cmic.attendance.model.Employee;
import org.apache.ibatis.annotations.Mapper;

/**
* 入职人员信息表Dao
*/
@Mapper
public interface EmployeeDao extends CrudDao<Employee> {

}