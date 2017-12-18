package com.cmic.attendance.service;

import com.cmic.attendance.dao.HolidaysDao;
import com.cmic.attendance.model.Holidays;
import com.cmic.saas.base.service.CrudService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 节假日service
 */
@Service
@Transactional(readOnly = true)
public class HolidaysService extends CrudService<HolidaysDao,Holidays> {

    public Holidays get(String id) {
        return super.get(id);
    }

    public List<Holidays> findList(Holidays holidays) {
        return super.findList(holidays);
    }

    public List<String> findMonthDayByYear(String year){
        return dao.findMonthDayByYear(year);
    }

    @Transactional(readOnly = false)
    public void save(Holidays holidays) {
        super.save(holidays);
    }
}
