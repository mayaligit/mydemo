package com.cmic.attendance.service;

import com.github.pagehelper.PageInfo;
import com.cmic.saas.base.service.CrudService;
import com.cmic.saas.base.web.RestException;
import com.cmic.saas.utils.StringUtils;
import com.cmic.attendance.dao.DailyRecordDao;
import com.cmic.attendance.model.DailyRecord;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


/**
* 日志表Service
*/
@Service
@Transactional(readOnly = true)
public class DailyRecordService extends CrudService<DailyRecordDao, DailyRecord> {

    public DailyRecord get(String id) {
        return super.get(id);
    }

    public List<DailyRecord> findList(DailyRecord dailyRecord) {
        return super.findList(dailyRecord);
    }

    public PageInfo<DailyRecord> findPage(PageInfo<DailyRecord> page, DailyRecord dailyRecord) {
        return super.findPage(page, dailyRecord);
    }

    @Transactional(readOnly = false)
    public void save(DailyRecord dailyRecord) {
        super.save(dailyRecord);
    }

    @Transactional(readOnly = false)
    public void dynamicUpdate(DailyRecord dailyRecord) {
        super.dynamicUpdate(dailyRecord);
    }

    @Transactional(readOnly = false)
    public void delete(String id) {
        //判断是否存在
        DailyRecord dailyRecord = get(id);
        if(dailyRecord==null|| StringUtils.isEmpty(dailyRecord.getId())){
            throw new RestException("删除失败，日志表不存在");
        }
        super.delete(id);
        logger.info("删除日志表：" + dailyRecord.toJSONString());
    }

}