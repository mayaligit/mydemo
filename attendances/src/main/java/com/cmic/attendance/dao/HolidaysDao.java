package com.cmic.attendance.dao;

import com.cmic.attendance.model.Holidays;
import com.cmic.saas.base.dao.CrudDao;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 节假日dao
 */
@Mapper
public interface HolidaysDao extends CrudDao<Holidays> {

    public List<String> findMonthDayByYear(String year);
}
