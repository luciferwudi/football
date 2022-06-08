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
import com.jeesite.modules.bkp.entity.BkpCompetitionresult;
import com.jeesite.modules.bkp.dao.BkpCompetitionresultDao;

/**
 * 比赛结果推荐表Service
 * @author Lucifer
 * @version 2020-08-27
 */
@Service
@Transactional(readOnly=true)
public class BkpCompetitionresultService extends CrudService<BkpCompetitionresultDao, BkpCompetitionresult> {

	@Autowired
	private BkpCompetitionresultDao bkpCompetitionresultDao;
	/**
	 * 获取单条数据
	 * @param bkpCompetitionresult
	 * @return
	 */
	@Override
	public BkpCompetitionresult get(BkpCompetitionresult bkpCompetitionresult) {
		return super.get(bkpCompetitionresult);
	}
	
	/**
	 * 查询分页数据
	 * @param bkpCompetitionresult 查询条件
	 * @param bkpCompetitionresult.page 分页对象
	 * @return
	 */
	@Override
	public Page<BkpCompetitionresult> findPage(BkpCompetitionresult bkpCompetitionresult) {
		return super.findPage(bkpCompetitionresult);
	}
	
	/**
	 * 保存数据（插入或更新）
	 * @param bkpCompetitionresult
	 */
	@Override
	@Transactional(readOnly=false)
	public void save(BkpCompetitionresult bkpCompetitionresult) {
		super.save(bkpCompetitionresult);
	}
	
	/**
	 * 更新状态
	 * @param bkpCompetitionresult
	 */
	@Override
	@Transactional(readOnly=false)
	public void updateStatus(BkpCompetitionresult bkpCompetitionresult) {
		super.updateStatus(bkpCompetitionresult);
	}
	
	/**
	 * 删除数据
	 * @param bkpCompetitionresult
	 */
	@Override
	@Transactional(readOnly=false)
	public void delete(BkpCompetitionresult bkpCompetitionresult) {
		super.delete(bkpCompetitionresult);
	}

	@Transactional(readOnly=false)
    public void deleteByCombination() {
		bkpCompetitionresultDao.deleteByCombination();
    }

    public Object selectCombination(String howToPlay) {
		return bkpCompetitionresultDao.selectCombination(howToPlay);

    }

	public Object queryRecommendation(String wf) {
		return bkpCompetitionresultDao.queryRecommendation(wf);
	}
}