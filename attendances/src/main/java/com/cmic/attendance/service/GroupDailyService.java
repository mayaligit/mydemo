package com.cmic.attendance.service;

import com.cmic.attendance.dao.GroupDailyDao;
import com.cmic.attendance.model.GroupDaily;
import com.cmic.saas.base.service.CrudService;
import com.cmic.saas.base.web.RestException;
import com.cmic.saas.utils.StringUtils;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


/**
* Service
*/
@Service
@Transactional(readOnly = true)
public class GroupDailyService extends CrudService<GroupDailyDao, GroupDaily> {

    public GroupDaily get(String id) {
        return super.get(id);
    }

    public List<GroupDaily> findList(GroupDaily groupDaily) {
        return super.findList(groupDaily);
    }

    public PageInfo<GroupDaily> findPage(PageInfo<GroupDaily> page, GroupDaily groupDaily) {
        return super.findPage(page, groupDaily);
    }

    @Transactional(readOnly = false)
    public void save(GroupDaily groupDaily) {
        super.save(groupDaily);
    }

    @Transactional(readOnly = false)
    public void dynamicUpdate(GroupDaily groupDaily) {
        super.dynamicUpdate(groupDaily);
    }

    @Transactional(readOnly = false)
    public void delete(String id) {
        //判断是否存在
        GroupDaily groupDaily = get(id);
        if(groupDaily==null|| StringUtils.isEmpty(groupDaily.getId())){
            throw new RestException("删除失败，不存在");
        }
        super.delete(id);
        logger.info("删除：" + groupDaily.toJSONString());
    }

}