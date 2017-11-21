package com.cmic.attendance.service;

import com.github.pagehelper.PageInfo;
import com.cmic.saas.base.service.CrudService;
import com.cmic.saas.base.web.RestException;
import com.cmic.saas.utils.StringUtils;
import com.cmic.attendance.dao.GroupAddressDao;
import com.cmic.attendance.model.GroupAddress;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


/**
* 考勤地址表Service
*/
@Service
@Transactional(readOnly = true)
public class GroupAddressService extends CrudService<GroupAddressDao, GroupAddress> {

    public GroupAddress get(String id) {
        return super.get(id);
    }

    public List<GroupAddress> findList(GroupAddress groupAddress) {
        return super.findList(groupAddress);
    }

    public PageInfo<GroupAddress> findPage(PageInfo<GroupAddress> page, GroupAddress groupAddress) {
        return super.findPage(page, groupAddress);
    }

    @Transactional(readOnly = false)
    public void save(GroupAddress groupAddress) {
        super.save(groupAddress);
    }

    @Transactional(readOnly = false)
    public void dynamicUpdate(GroupAddress groupAddress) {
        super.dynamicUpdate(groupAddress);
    }

    @Transactional(readOnly = false)
    public void delete(String id) {
        //判断是否存在
        GroupAddress groupAddress = get(id);
        if(groupAddress==null|| StringUtils.isEmpty(groupAddress.getId())){
            throw new RestException("删除失败，考勤地址表不存在");
        }
        super.delete(id);
        logger.info("删除考勤地址表：" + groupAddress.toJSONString());
    }

    /**
     * 获取多地址打卡经纬度
     */
    public List<GroupAddress> findAll() {
        List<GroupAddress> allGroupAddress = super.findAllList();
        return allGroupAddress;
    }

}