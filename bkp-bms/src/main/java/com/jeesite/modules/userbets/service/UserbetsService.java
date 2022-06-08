/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.userbets.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeesite.common.entity.Page;
import com.jeesite.common.service.CrudService;
import com.jeesite.modules.userbets.entity.Userbets;
import com.jeesite.modules.userbets.dao.UserbetsDao;

/**
 * userbetsService
 * @author Lucifer
 * @version 2020-08-14
 */
@Service
@Transactional(readOnly=true)
public class UserbetsService extends CrudService<UserbetsDao, Userbets> {

	@Autowired
	private UserbetsDao userbetsDao;
	
	/**
	 * 获取单条数据
	 * @param userbets
	 * @return
	 */
	@Override
	public Userbets get(Userbets userbets) {
		return super.get(userbets);
	}
	
	/**
	 * 查询分页数据
	 * @param userbets 查询条件
	 * @param userbets.page 分页对象
	 * @return
	 */
	@Override
	public Page<Userbets> findPage(Userbets userbets) {
		return super.findPage(userbets);
	}
	
	/**
	 * 保存数据（插入或更新）
	 * @param userbets
	 */
	@Override
	@Transactional(readOnly=false)
	public void save(Userbets userbets) {
		super.save(userbets);
	}
	
	/**
	 * 更新状态
	 * @param userbets
	 */
	@Override
	@Transactional(readOnly=false)
	public void updateStatus(Userbets userbets) {
		super.updateStatus(userbets);
	}
	
	/**
	 * 删除数据
	 * @param userbets
	 */
	@Override
	@Transactional(readOnly=false)
	public void delete(Userbets userbets) {
		super.delete(userbets);
	}


	@Transactional(readOnly=false)
	public List<Userbets> selectByResult() {
		return userbetsDao.selectByResult();
	}
}