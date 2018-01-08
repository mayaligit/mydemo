package com.cmic.attendance.dao;

import com.cmic.attendance.model.EmployeeScore;
import com.cmic.saas.base.dao.CrudDao;
import org.apache.ibatis.annotations.Mapper;

import java.util.HashMap;
import java.util.List;

/**
* 雇员分数Dao
*/
@Mapper
public interface EmployeeScoreDao extends CrudDao<EmployeeScore> {
    List<HashMap> getEmployeeByGroup(EmployeeScore employeeScore);
}