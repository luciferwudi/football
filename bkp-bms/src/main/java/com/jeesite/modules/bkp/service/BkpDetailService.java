/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.bkp.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeesite.common.entity.Page;
import com.jeesite.common.service.CrudService;
import com.jeesite.modules.bkp.entity.BkpDetail;
import com.jeesite.modules.bkp.dao.BkpDetailDao;

/**
 * 比赛下注详情表Service
 * @author Lucifer
 * @version 2020-09-17
 */
@Service
@Transactional(readOnly=true)
public class BkpDetailService extends CrudService<BkpDetailDao, BkpDetail> {
	
	/**
	 * 获取单条数据
	 * @param bkpDetail
	 * @return
	 */
	@Override
	public BkpDetail get(BkpDetail bkpDetail) {
		return super.get(bkpDetail);
	}
	
	/**
	 * 查询分页数据
	 * @param bkpDetail 查询条件
	 * @param bkpDetail.page 分页对象
	 * @return
	 */
	@Override
	public Page<BkpDetail> findPage(BkpDetail bkpDetail) {
		return super.findPage(bkpDetail);
	}
	
	/**
	 * 保存数据（插入或更新）
	 * @param bkpDetail
	 */
	@Override
	@Transactional(readOnly=false)
	public void save(BkpDetail bkpDetail) {
		super.save(bkpDetail);
	}
	
	/**
	 * 更新状态
	 * @param bkpDetail
	 */
	@Override
	@Transactional(readOnly=false)
	public void updateStatus(BkpDetail bkpDetail) {
		super.updateStatus(bkpDetail);
	}
	
	/**
	 * 删除数据
	 * @param bkpDetail
	 */
	@Override
	@Transactional(readOnly=false)
	public void delete(BkpDetail bkpDetail) {
		super.delete(bkpDetail);
	}
	
}