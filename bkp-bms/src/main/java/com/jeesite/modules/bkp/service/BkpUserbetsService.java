/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.bkp.service;

import java.util.List;
import java.util.Map;

import com.jeesite.modules.bkp.entity.BkpFixedprofit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeesite.common.entity.Page;
import com.jeesite.common.service.CrudService;
import com.jeesite.modules.bkp.entity.BkpUserbets;
import com.jeesite.modules.bkp.dao.BkpUserbetsDao;

/**
 * 用户下注表Service
 * @author Lucifer
 * @version 2020-09-02
 */
@Service
@Transactional(readOnly=true)
public class BkpUserbetsService extends CrudService<BkpUserbetsDao, BkpUserbets> {
	@Autowired
	private BkpUserbetsDao bkpUserbetsDao;
	
	/**
	 * 获取单条数据
	 * @param bkpUserbets
	 * @return
	 */
	@Override
	public BkpUserbets get(BkpUserbets bkpUserbets) {
		return super.get(bkpUserbets);
	}
	
	/**
	 * 查询分页数据
	 * @param bkpUserbets 查询条件
	 * @param bkpUserbets.page 分页对象
	 * @return
	 */
	@Override
	public Page<BkpUserbets> findPage(BkpUserbets bkpUserbets) {
		return super.findPage(bkpUserbets);
	}
	
	/**
	 * 保存数据（插入或更新）
	 * @param bkpUserbets
	 */
	@Override
	@Transactional(readOnly=false)
	public void save(BkpUserbets bkpUserbets) {
		super.save(bkpUserbets);
	}
	
	/**
	 * 更新状态
	 * @param bkpUserbets
	 */
	@Override
	@Transactional(readOnly=false)
	public void updateStatus(BkpUserbets bkpUserbets) {
		super.updateStatus(bkpUserbets);
	}
	
	/**
	 * 删除数据
	 * @param bkpUserbets
	 */
	@Override
	@Transactional(readOnly=false)
	public void delete(BkpUserbets bkpUserbets) {
		super.delete(bkpUserbets);
	}

    public List<BkpUserbets> selectAllNullMatchResults(String UserCode) {
		return bkpUserbetsDao.selectAllNullMatchResults(UserCode);
    }

    public List<Map> selectCombination(String userCode) {
		return bkpUserbetsDao.selectCombination(userCode);
    }

	public List<BkpUserbets> selectByCombination(Long s) {
		return bkpUserbetsDao.selectByCombination(s);
	}

    public List<BkpUserbets> getById(Long pcjlid) {
		return bkpUserbetsDao.getById(pcjlid);
    }

    public List<Integer> getBygdylId(String id) {
		return bkpUserbetsDao.getBygdylId(id);
    }

    public BkpFixedprofit getByGdmsid(Long gdmsid) {
		return bkpUserbetsDao.getByGdmsid(gdmsid);
    }

	public List<Map> selectCombinationAndPcjlid(String userCode, Long pcjlid) {
		return bkpUserbetsDao.selectCombinationAndPcjlid(userCode,pcjlid);
	}

	public String selectByGdylmsId(Long gdmsid) {
		return bkpUserbetsDao.selectByGdylmsId(gdmsid);
	}

	public Integer selectByPcjlId(Long pcjlid) {
		return bkpUserbetsDao.selectByPcjlId(pcjlid);
	}

    public List<Long> selectById(Long pcjlid) {
		return bkpUserbetsDao.selectById(pcjlid);
    }

	public Long findById(Long id) {
		return bkpUserbetsDao.findById(id);
	}
}