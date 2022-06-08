/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.bkp.service;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import com.jeesite.modules.teamdata.entity.Teamdata;
import com.jeesite.modules.utils.injuries;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeesite.common.entity.Page;
import com.jeesite.common.service.CrudService;
import com.jeesite.modules.bkp.entity.BkpInjuries;
import com.jeesite.modules.bkp.dao.BkpInjuriesDao;

/**
 * 球队球员伤痛表Service
 * @author Lucifer
 * @version 2020-09-25
 */
@Service
@Transactional(readOnly=true)
public class BkpInjuriesService extends CrudService<BkpInjuriesDao, BkpInjuries> {

	@Autowired
	private com.jeesite.modules.utils.injuries injuries;
	@Autowired
	private BkpInjuriesDao bkpInjuriesDao;

	
	/**
	 * 获取单条数据
	 * @param bkpInjuries
	 * @return
	 */
	@Override
	public BkpInjuries get(BkpInjuries bkpInjuries) {
		return super.get(bkpInjuries);
	}
	
	/**
	 * 查询分页数据
	 * @param bkpInjuries 查询条件
	 * @param bkpInjuries.page 分页对象
	 * @return
	 */
	@Override
	public Page<BkpInjuries> findPage(BkpInjuries bkpInjuries) {
		return super.findPage(bkpInjuries);
	}
	
	/**
	 * 保存数据（插入或更新）
	 * @param bkpInjuries
	 */
	@Override
	@Transactional(readOnly=false)
	public void save(BkpInjuries bkpInjuries) {
		super.save(bkpInjuries);
	}
	
	/**
	 * 更新状态
	 * @param bkpInjuries
	 */
	@Override
	@Transactional(readOnly=false)
	public void updateStatus(BkpInjuries bkpInjuries) {
		super.updateStatus(bkpInjuries);
	}
	
	/**
	 * 删除数据
	 * @param bkpInjuries
	 */
	@Override
	@Transactional(readOnly=false)
	public void delete(BkpInjuries bkpInjuries) {
		super.delete(bkpInjuries);
	}

	@Transactional(readOnly=false)
    public void insertData() throws Exception {
		BkpInjuries bkpInjuries1=new BkpInjuries();
		List<BkpInjuries> list1=findList(bkpInjuries1);
		for (BkpInjuries ff:list1){
			delete(ff);
		}
		List<String> league = injuries.league();
		for (String s:league){
			List<Map> list = injuries.jsoup01(s);
			for (Map f:list){
				//查询是否已经有了该球队的数据  如果有先删掉
				BkpInjuries bkpInjuries=new BkpInjuries();
				bkpInjuries.setCnname((String) f.get("team"));
				bkpInjuries.setCause((String) f.get("cause"));
				bkpInjuries.setInfluence((String) f.get("affect"));
				bkpInjuries.setName((String) f.get("name"));
				bkpInjuries.setSocial((String) f.get("value"));
				bkpInjuries.setTime((String) f.get("time"));
				bkpInjuries.setPosition((String) f.get("position"));
				save(bkpInjuries);
			}
		}


	}

    public Object playerInjury(String tid) {
		//查询出这个id代表的球队的未缩写
		String teamName="";
		List<Teamdata> list=bkpInjuriesDao.selectTeamName(tid);
		if (list.size()==0){
			list=bkpInjuriesDao.selectTeamName1(tid);
			if (list.size()!=0){
				teamName=list.get(0).getAcn();
			}
		}else {
			teamName=list.get(0).getHcn();
		}
		if (teamName.equals("")){
			return "1";
		}else {
			BkpInjuries bkpInjuries=new BkpInjuries();
			bkpInjuries.setCnname(teamName);
			List<BkpInjuries> list1=findList(bkpInjuries);
			if (list1.size()==0){
				return "1";
			}else {
				return list1;
			}

		}
    }
}