package com.key.dwsurvey.systemuser.service.impl;

import com.key.dwsurvey.systemuser.dao.SysEmailDao;
import com.key.dwsurvey.systemuser.entity.SysEmail;
import com.key.dwsurvey.systemuser.service.SysEmailManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.key.common.service.BaseServiceImpl;

@Service
public class SysEmailManagerImpl extends BaseServiceImpl<SysEmail, String> implements SysEmailManager {
	
	@Autowired
	private SysEmailDao sysEmailDao;
	
	@Override
	public void setBaseDao() {
		this.baseDao=sysEmailDao;
	}
	
}
