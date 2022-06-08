/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.bkp.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeesite.common.entity.Page;
import com.jeesite.common.service.CrudService;
import com.jeesite.modules.bkp.entity.BkpLeis;
import com.jeesite.modules.bkp.dao.BkpLeisDao;

/**
 * bkp_leisService
 * @author Lucifer
 * @version 2020-10-13
 */
@Service
@Transactional(readOnly=true)
public class BkpLeisService extends CrudService<BkpLeisDao, BkpLeis> {

	@Autowired
	private BkpLeisDao bkpLeisDao;
	/**
	 * 获取单条数据
	 * @param bkpLeis
	 * @return
	 */
	@Override
	public BkpLeis get(BkpLeis bkpLeis) {
		return super.get(bkpLeis);
	}
	
	/**
	 * 查询分页数据
	 * @param bkpLeis 查询条件
	 * @param bkpLeis.page 分页对象
	 * @return
	 */
	@Override
	public Page<BkpLeis> findPage(BkpLeis bkpLeis) {
		return super.findPage(bkpLeis);
	}
	
	/**
	 * 保存数据（插入或更新）
	 * @param bkpLeis
	 */
	@Override
	@Transactional(readOnly=false)
	public void save(BkpLeis bkpLeis) {
		super.save(bkpLeis);
	}
	
	/**
	 * 更新状态
	 * @param bkpLeis
	 */
	@Override
	@Transactional(readOnly=false)
	public void updateStatus(BkpLeis bkpLeis) {
		super.updateStatus(bkpLeis);
	}
	
	/**
	 * 删除数据
	 * @param bkpLeis
	 */
	@Override
	@Transactional(readOnly=false)
	public void delete(BkpLeis bkpLeis) {
		super.delete(bkpLeis);
	}

    public BkpLeis getentiy(BkpLeis bkpLeis) {
		return bkpLeisDao.getentiy(bkpLeis.getLeague(),bkpLeis.getTime(),bkpLeis.getHometeam());
    }

    public List<BkpLeis> selectAllResult(String time) {
		return bkpLeisDao.selectAllResult(time);
    }

	public List<String> selectByGroup() {
		return bkpLeisDao.selectByGroup();
	}
}