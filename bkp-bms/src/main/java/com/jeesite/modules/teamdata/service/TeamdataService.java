/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.teamdata.service;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import com.jeesite.modules.odds.web.TestController002;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeesite.common.entity.Page;
import com.jeesite.common.service.CrudService;
import com.jeesite.modules.teamdata.entity.Teamdata;
import com.jeesite.modules.teamdata.dao.TeamdataDao;

/**
 * teamdataService
 * @author Lucifer
 * @version 2020-07-31
 */
@Service
@Transactional(readOnly=true)
public class TeamdataService extends CrudService<TeamdataDao, Teamdata> {
	@Autowired
	private TeamdataDao teamdataDao;
	@Autowired
	private TestController002 testController002;
	
	/**
	 * 获取单条数据
	 * @param teamdata
	 * @return
	 */
	@Override
	public Teamdata get(Teamdata teamdata) {
		return super.get(teamdata);
	}
	
	/**
	 * 查询分页数据
	 * @param teamdata 查询条件
	 * @param teamdata.page 分页对象
	 * @return
	 */
	@Override
	public Page<Teamdata> findPage(Teamdata teamdata) {
		return super.findPage(teamdata);
	}
	
	/**
	 * 保存数据（插入或更新）
	 * @param teamdata
	 */
	@Override
	@Transactional(readOnly=false)
	public void save(Teamdata teamdata) {
		super.save(teamdata);
	}
	
	/**
	 * 更新状态
	 * @param teamdata
	 */
	@Override
	@Transactional(readOnly=false)
	public void updateStatus(Teamdata teamdata) {
		super.updateStatus(teamdata);
	}
	
	/**
	 * 删除数据
	 * @param teamdata
	 */
	@Override
	@Transactional(readOnly=false)
	public void delete(Teamdata teamdata) {
		super.delete(teamdata);
	}

	@Transactional(readOnly=false)
    public void task() throws IOException {
		List<Teamdata> list=teamdataDao.selectByResult();
        if (list.size()>0){
        	for (Teamdata s:list){
				Map<String,Object> result= (Map<String, Object>) testController002.getGameData(s.getBdate(),s.getNum());
				if (null==result.get("result")){
					result.put("result","无效场次");
				}
				s.setResult((String) result.get("result"));
				teamdataDao.update(s);
			}
		}
    }

    public Object queryData(String time) {

		return teamdataDao.queryData(time);
    }
}