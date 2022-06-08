/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.bkp.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.jeesite.modules.teamdata.entity.Teamdata;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.ParameterResolutionDelegate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeesite.common.entity.Page;
import com.jeesite.common.service.CrudService;
import com.jeesite.modules.bkp.entity.BkpRecommendeddescription;
import com.jeesite.modules.bkp.dao.BkpRecommendeddescriptionDao;

/**
 * 推荐说明表Service
 * @author Lucifer
 * @version 2020-08-28
 */
@Service
@Transactional(readOnly=true)
public class BkpRecommendeddescriptionService extends CrudService<BkpRecommendeddescriptionDao, BkpRecommendeddescription> {

	@Autowired
	private BkpRecommendeddescriptionDao bkpRecommendeddescriptionDao;
	/**
	 * 获取单条数据
	 * @param bkpRecommendeddescription
	 * @return
	 */
	@Override
	public BkpRecommendeddescription get(BkpRecommendeddescription bkpRecommendeddescription) {
		return super.get(bkpRecommendeddescription);
	}
	
	/**
	 * 查询分页数据
	 * @param bkpRecommendeddescription 查询条件
	 * @param bkpRecommendeddescription.page 分页对象
	 * @return
	 */
	@Override
	public Page<BkpRecommendeddescription> findPage(BkpRecommendeddescription bkpRecommendeddescription) {
		return super.findPage(bkpRecommendeddescription);
	}
	
	/**
	 * 保存数据（插入或更新）
	 * @param bkpRecommendeddescription
	 */
	@Override
	@Transactional(readOnly=false)
	public void save(BkpRecommendeddescription bkpRecommendeddescription) {
		super.save(bkpRecommendeddescription);
	}
	
	/**
	 * 更新状态
	 * @param bkpRecommendeddescription
	 */
	@Override
	@Transactional(readOnly=false)
	public void updateStatus(BkpRecommendeddescription bkpRecommendeddescription) {
		super.updateStatus(bkpRecommendeddescription);
	}
	
	/**
	 * 删除数据
	 * @param bkpRecommendeddescription
	 */
	@Override
	@Transactional(readOnly=false)
	public void delete(BkpRecommendeddescription bkpRecommendeddescription) {
		super.delete(bkpRecommendeddescription);
	}

    public Object teamQuery(String hometeam, String awayteam) {
		Map map=new HashMap();
		map.put("hometeam",bkpRecommendeddescriptionDao.teamQuery(hometeam));
		map.put("awayteam",bkpRecommendeddescriptionDao.teamQuery(awayteam));
		return map;
    }

    public BkpRecommendeddescription findByTeam(String s) {
		return bkpRecommendeddescriptionDao.teamQuery(s);
    }

	public List<Teamdata> selectTeam(String hometeam) {
		return bkpRecommendeddescriptionDao.selectTeam(hometeam);
	}

	public List<Teamdata> selectATeam(String awayteam) {
		return bkpRecommendeddescriptionDao.selectATeam(awayteam);
	}
}