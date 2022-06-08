/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.bkp.service;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.jeesite.modules.teamdata.entity.Teamdata;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeesite.common.entity.Page;
import com.jeesite.common.service.CrudService;
import com.jeesite.modules.bkp.entity.BkpFixedprofit;
import com.jeesite.modules.bkp.dao.BkpFixedprofitDao;

/**
 * 固定盈利模式Service
 * @author Lucifer
 * @version 2020-08-31
 */
@Service
@Transactional(readOnly=true)
public class BkpFixedprofitService extends CrudService<BkpFixedprofitDao, BkpFixedprofit> {

	@Autowired
	private BkpFixedprofitDao bkpFixedprofitDao;
	/**
	 * 获取单条数据
	 * @param bkpFixedprofit
	 * @return
	 */
	@Override
	public BkpFixedprofit get(BkpFixedprofit bkpFixedprofit) {
		return super.get(bkpFixedprofit);
	}
	
	/**
	 * 查询分页数据
	 * @param bkpFixedprofit 查询条件
	 * @param bkpFixedprofit.page 分页对象
	 * @return
	 */
	@Override
	public Page<BkpFixedprofit> findPage(BkpFixedprofit bkpFixedprofit) {
		return super.findPage(bkpFixedprofit);
	}
	
	/**
	 * 保存数据（插入或更新）
	 * @param bkpFixedprofit
	 */
	@Override
	@Transactional(readOnly=false)
	public void save(BkpFixedprofit bkpFixedprofit) {
		super.save(bkpFixedprofit);
	}
	
	/**
	 * 更新状态
	 * @param bkpFixedprofit
	 */
	@Override
	@Transactional(readOnly=false)
	public void updateStatus(BkpFixedprofit bkpFixedprofit) {
		super.updateStatus(bkpFixedprofit);
	}
	
	/**
	 * 删除数据
	 * @param bkpFixedprofit
	 */
	@Override
	@Transactional(readOnly=false)
	public void delete(BkpFixedprofit bkpFixedprofit) {
		super.delete(bkpFixedprofit);
	}

    public BkpFixedprofit userQuery(String userCode) {
		return bkpFixedprofitDao.userQuery(userCode);
    }

    public List<Teamdata> queryGame(List<String> id) {
		List<Teamdata> list=new ArrayList<>();
		for (String s:id){
			Teamdata teamdata=bkpFixedprofitDao.queryGame(s);
			list.add(teamdata);
		}
		return list;

    }

    public BkpFixedprofit findByid(String id) {
		return bkpFixedprofitDao.findByid(id);
    }

    public int selectBets(String id) {
		return bkpFixedprofitDao.selectBets(id);
    }

    public BkpFixedprofit getBypcId(String id) {
		return bkpFixedprofitDao.getBypcId(id);
    }

    public Object analysisInterval(double a1, double a2, String n) {
		DecimalFormat df=new DecimalFormat("0.00");
		Map map=new HashMap();
		if (a1>=a2){
			return "请规范选择区间";
		}
		if ("主".equals(n)){
			int s=bkpFixedprofitDao.selectz(a1,"胜",a2,"had");
			int p=bkpFixedprofitDao.selectz(a1,"平",a2,"had");
			int f=bkpFixedprofitDao.selectz(a1,"负",a2,"had");
			int sum=s+p+f;
			String sl=df.format((float)s/sum*100)+"%";
			String spl=df.format((float)(s+p)/sum*100)+"%";
			System.out.println("主场赔率"+a1+"-"+a2+"  "+"胜率为"+sl+"  "+"胜平率为"+spl);
			if (s==0){
				sl="0%";
				spl="0%";
			}
			map.put("h",s);
			map.put("d",p);
			map.put("a",f);
			map.put("sum",sum);
			map.put("result","主场此区域内"+"  "+"胜率为"+sl+"  "+"胜平率为"+spl);
			return map;
		}
		if ("客".equals(n)){
			int s=bkpFixedprofitDao.selectk(a1,"胜",a2,"had");
			int p=bkpFixedprofitDao.selectk(a1,"平",a2,"had");
			int f=bkpFixedprofitDao.selectk(a1,"负",a2,"had");
			int sum=s+p+f;
			String sl=df.format((float)f/sum*100)+"%";
			String spl=df.format((float)(f+p)/sum*100)+"%";
			System.out.println("客场赔率"+a1+"-"+a2+"  "+"负率为"+sl+"  "+"负平率为"+spl);
			map.put("h",s);
			map.put("d",p);
			map.put("a",f);
			map.put("sum",sum);
			if (f==0){
				sl="0%";
				spl="0%";
			}
			map.put("result","客场此区域内"+"  "+"负率为"+sl+"  "+"负平率为"+spl);
			return map;
		}
		return "请规范选择区间";
	}

    }

