package com.cmic.attendance.service;

import com.cmic.attendance.dao.GroupAuditDao;
import com.cmic.attendance.model.GroupAudit;
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
public class GroupAuditService extends CrudService<GroupAuditDao, GroupAudit> {

    public GroupAudit get(String id) {
        return super.get(id);
    }

    public List<GroupAudit> findList(GroupAudit groupAudit) {
        return super.findList(groupAudit);
    }

    public PageInfo<GroupAudit> findPage(PageInfo<GroupAudit> page, GroupAudit groupAudit) {
        return super.findPage(page, groupAudit);
    }

    @Transactional(readOnly = false)
    public void save(GroupAudit groupAudit) {
        super.save(groupAudit);
    }

    @Transactional(readOnly = false)
    public void dynamicUpdate(GroupAudit groupAudit) {
        super.dynamicUpdate(groupAudit);
    }

    @Transactional(readOnly = false)
    public void delete(String id) {
        //判断是否存在
        GroupAudit groupAudit = get(id);
        if(groupAudit==null|| StringUtils.isEmpty(groupAudit.getId())){
            throw new RestException("删除失败，不存在");
        }
        super.delete(id);
        logger.info("删除：" + groupAudit.toJSONString());
    }

}