package com.cmic.attendance.service;

import com.cmic.attendance.dao.GroupPersonnelDao;
import com.cmic.attendance.model.GroupPersonnel;
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
public class GroupPersonnelService extends CrudService<GroupPersonnelDao, GroupPersonnel> {

    public GroupPersonnel get(String id) {
        return super.get(id);
    }

    public List<GroupPersonnel> findList(GroupPersonnel groupPersonnel) {
        return super.findList(groupPersonnel);
    }

    public PageInfo<GroupPersonnel> findPage(PageInfo<GroupPersonnel> page, GroupPersonnel groupPersonnel) {
        return super.findPage(page, groupPersonnel);
    }

    @Transactional(readOnly = false)
    public void save(GroupPersonnel groupPersonnel) {
        super.save(groupPersonnel);
    }

    @Transactional(readOnly = false)
    public void dynamicUpdate(GroupPersonnel groupPersonnel) {
        super.dynamicUpdate(groupPersonnel);
    }

    @Transactional(readOnly = false)
    public void delete(String id) {
        //判断是否存在
        GroupPersonnel groupPersonnel = get(id);
        if(groupPersonnel==null|| StringUtils.isEmpty(groupPersonnel.getId())){
            throw new RestException("删除失败，不存在");
        }
        super.delete(id);
        logger.info("删除：" + groupPersonnel.toJSONString());
    }

}