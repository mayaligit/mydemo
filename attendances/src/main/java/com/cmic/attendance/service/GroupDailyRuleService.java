package com.cmic.attendance.service;

import com.cmic.attendance.dao.GroupDailyRuleDao;
import com.cmic.attendance.model.GroupDailyRule;
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
public class GroupDailyRuleService extends CrudService<GroupDailyRuleDao, GroupDailyRule> {

    public GroupDailyRule get(String id) {
        return super.get(id);
    }

    public List<GroupDailyRule> findList(GroupDailyRule groupDailyRule) {
        return super.findList(groupDailyRule);
    }

    public PageInfo<GroupDailyRule> findPage(PageInfo<GroupDailyRule> page, GroupDailyRule groupDailyRule) {
        return super.findPage(page, groupDailyRule);
    }

    @Transactional(readOnly = false)
    public void save(GroupDailyRule groupDailyRule) {
        super.save(groupDailyRule);
    }

    @Transactional(readOnly = false)
    public void dynamicUpdate(GroupDailyRule groupDailyRule) {
        super.dynamicUpdate(groupDailyRule);
    }

    @Transactional(readOnly = false)
    public void delete(String id) {
        //判断是否存在
        GroupDailyRule groupDailyRule = get(id);
        if(groupDailyRule==null|| StringUtils.isEmpty(groupDailyRule.getId())){
            throw new RestException("删除失败，不存在");
        }
        super.delete(id);
        logger.info("删除：" + groupDailyRule.toJSONString());
    }

    public GroupDailyRule getDailyByGroupRuleId(String groupRuleId){
        return dao.getDailyByGroupRuleId(groupRuleId);
    }

}