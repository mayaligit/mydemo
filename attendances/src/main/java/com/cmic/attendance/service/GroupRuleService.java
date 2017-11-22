package com.cmic.attendance.service;

import com.cmic.attendance.dao.GroupRuleDao;
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
public class GroupRuleService extends CrudService<GroupRuleDao, GroupRule> {

    public GroupRule get(String id) {
        return super.get(id);
    }

    public List<GroupRule> findList(GroupRule groupRule) {
        return super.findList(groupRule);
    }

    public PageInfo<GroupRule> findPage(PageInfo<GroupRule> page, GroupRule groupRule) {
        return super.findPage(page, groupRule);
    }

    @Transactional(readOnly = false)
    public void save(GroupRule groupRule) {
        super.save(groupRule);
    }

    @Transactional(readOnly = false)
    public void dynamicUpdate(GroupRule groupRule) {
        super.dynamicUpdate(groupRule);
    }

    @Transactional(readOnly = false)
    public void delete(String id) {
        //判断是否存在
        GroupRule groupRule = get(id);
        if(groupRule==null|| StringUtils.isEmpty(groupRule.getId())){
            throw new RestException("删除失败，不存在");
        }
        super.delete(id);
        logger.info("删除：" + groupRule.toJSONString());
    }

}