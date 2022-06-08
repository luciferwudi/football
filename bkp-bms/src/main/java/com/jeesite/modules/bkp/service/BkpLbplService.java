/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.bkp.service;

import java.io.IOException;
import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeesite.common.entity.Page;
import com.jeesite.common.service.CrudService;
import com.jeesite.modules.bkp.entity.BkpLbpl;
import com.jeesite.modules.bkp.dao.BkpLbplDao;
import org.w3c.dom.ls.LSException;

/**
 * 立博的主场各赔率Service
 * @author Lucifer
 * @version 2021-02-20
 */
@Service
@Transactional(readOnly=true)
public class BkpLbplService extends CrudService<BkpLbplDao, BkpLbpl> {

	@Autowired
	private BkpLbplDao bkpLbplDao;
	
	/**
	 * 获取单条数据
	 * @param bkpLbpl
	 * @return
	 */
	@Override
	public BkpLbpl get(BkpLbpl bkpLbpl) {
		return super.get(bkpLbpl);
	}
	
	/**
	 * 查询分页数据
	 * @param bkpLbpl 查询条件
	 * @param bkpLbpl.page 分页对象
	 * @return
	 */
	@Override
	public Page<BkpLbpl> findPage(BkpLbpl bkpLbpl) {
		return super.findPage(bkpLbpl);
	}
	
	/**
	 * 保存数据（插入或更新）
	 * @param bkpLbpl
	 */
	@Override
	@Transactional(readOnly=false)
	public void save(BkpLbpl bkpLbpl) {
		super.save(bkpLbpl);
	}
	
	/**
	 * 更新状态
	 * @param bkpLbpl
	 */
	@Override
	@Transactional(readOnly=false)
	public void updateStatus(BkpLbpl bkpLbpl) {
		super.updateStatus(bkpLbpl);
	}
	
	/**
	 * 删除数据
	 * @param bkpLbpl
	 */
	@Override
	@Transactional(readOnly=false)
	public void delete(BkpLbpl bkpLbpl) {
		super.delete(bkpLbpl);
	}

	@Transactional(readOnly=false)
    public void autoUpdate(String time) throws IOException {
		String url = "http://score.nowscore.com/1x2/Companyhistory.htm?id=82&company=%E7%AB%8B%E5%8D%9A(%E8%8B%B1%E5%9B%BD)&matchdate=";
//		//查询所有天数
//		List<String> timeList=bkpLbplDao.selectGroupByTime();   直接查询所有天数  会导致ip被封杀
//		for (String s:timeList){
//			url+=s;
//			lb(url);
//		}
		lb(url+time);
	}


    public void lb(String url) throws IOException {
		Connection conn = Jsoup.connect(url); // 建立与url中页面的连接
		Document doc = conn.get(); // 解析页面
		Elements ss = doc.select("tbody");//表
		Elements trList = ss.get(0).select("tr[id^=tr_]"); //获取其中不让球的赔率
		for (int i = 0; i < trList.size(); i++) {
			Element select = trList.get(i);
			Elements tds = select.select("td");
			BkpLbpl bkpLbpl=new BkpLbpl();
			bkpLbpl.setLeague(tds.get(0).text());//联赛
			bkpLbpl.setTime(tds.get(1).text());//时间
			bkpLbpl.setHometeam(tds.get(2).text());//主队
			bkpLbpl.setVistteam(tds.get(10).text());//客队
			bkpLbpl.setH(tds.get(3).text());//主胜赔率
			bkpLbpl.setD(tds.get(4).text()); //平局赔率
			bkpLbpl.setA(tds.get(5).text());//主负赔率
			bkpLbpl.setScore(tds.get(11).text());//比分   如 1-2  score
			String[] sz = tds.get(11).text().split("-");
			int zd = Integer.valueOf(sz[0]);//主队比分
			int kd = Integer.valueOf(sz[1]);//客队比分
			String result = "";
			if (zd == kd) {
				result = "平";
			}
			if (zd > kd) {
				result = "胜";
			}
			if (zd < kd) {
				result = "负";
			}
			bkpLbpl.setResult(result);
			save(bkpLbpl);
		}
	}

	public Object analysisInterval(Double a1, Double a2, String n) {
		DecimalFormat df=new DecimalFormat("0.00");
		Map map=new HashMap();
		if ("主".equals(n)){
			int s=bkpLbplDao.selectz(a1,"胜",a2);
			int p=bkpLbplDao.selectz(a1,"平",a2);
			int f=bkpLbplDao.selectz(a1,"负",a2);
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
			int s=bkpLbplDao.selectk(a1,"胜",a2);
			int p=bkpLbplDao.selectk(a1,"平",a2);
			int f=bkpLbplDao.selectk(a1,"负",a2);
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