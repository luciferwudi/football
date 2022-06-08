/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.odds.service;

import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import com.googlecode.aviator.AviatorEvaluator;
import com.jeesite.common.cache.CacheUtils;
import com.jeesite.common.lang.DateUtils;
import com.jeesite.common.utils.excel.ExcelExport;
import com.jeesite.modules.bkp.entity.BkpRecommendeddescription;
import com.jeesite.modules.bkp.entity.BkpUserbets;
import com.jeesite.modules.bkp.service.BkpUserbetsService;
import com.jeesite.modules.odds.entity.DoGetData;
import com.jeesite.modules.odds.entity.Multiallup;
import com.jeesite.modules.odds.entity.StatusVo;
import com.jeesite.modules.odds.web.TestController002;
import com.jeesite.modules.sys.entity.EmpUser;
import com.jeesite.modules.teamdata.dao.TeamdataDao;
import com.jeesite.modules.teamdata.entity.Teamdata;
import com.jeesite.modules.utils.DoGetData002;
import com.jeesite.modules.utils.MemberVo;
import com.jeesite.modules.utils.RecommendJsoup;
import com.jeesite.modules.utils.Recommend;
import com.jeesite.modules.vo.Opp;
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
import com.jeesite.modules.odds.entity.Odds;
import com.jeesite.modules.odds.dao.OddsDao;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

/**
 * oddsService
 * @author Lucifer
 * @version 2020-07-30
 */
@Service
public class OddsService extends CrudService<OddsDao, Odds> {

	@Autowired
	private OddsDao oddsDao;
	@Autowired
	private TeamdataDao teamdataDao;
	@Autowired
	private TestController002 testController002;
	@Autowired
	private BkpUserbetsService bkpUserbetsService;
	@Autowired
	private RecommendJsoup recommendJsoup;
	@Autowired
	private Recommend recommend;


	/**
	 * 获取单条数据
	 * @param odds
	 * @return
	 */
	@Override
	public Odds get(Odds odds) {
		return super.get(odds);
	}
	
	/**
	 * 查询分页数据
	 * @param odds 查询条件
	 * @param odds.page 分页对象
	 * @return
	 */
	@Override
	public Page<Odds> findPage(Odds odds) {
		return super.findPage(odds);
	}
	
	/**
	 * 保存数据（插入或更新）
	 * @param odds
	 */
	@Override
	@Transactional(readOnly=false)
	public void save(Odds odds) {
		super.save(odds);
	}
	
	/**
	 * 更新状态
	 * @param odds
	 */
	@Override
	@Transactional(readOnly=false)
	public void updateStatus(Odds odds) {
		super.updateStatus(odds);
	}
	
	/**
	 * 删除数据
	 * @param odds
	 */
	@Override
	@Transactional(readOnly=false)
	public void delete(Odds odds) {
		super.delete(odds);
	}



    public Object test() throws Exception {
		Long aa=System.currentTimeMillis();
		HashMap map=new LinkedHashMap();
		List<Teamdata> list=oddsDao.selectAllTeamdata();
		List<Map> maplist= (List<Map>) recommend.jsoup();
        ///查询赔率值  根据Teamdata里面的值id值查询出具体的值
		DateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
		DateFormat sdf1=new SimpleDateFormat("HH:mm:ss");
		Date date=new Date();
		String Date01=sdf.format(date);
		String Time=sdf1.format(date);
		for (Teamdata s:list){
			int a=getMin(Date01,s.getDate(),"yyyy-MM-dd");
			int b=getMin(Time,s.getTime(),"HH:mm:ss");
			if (a<0) {
				String type = "";
				for (int i = 0; i < 2; i++) {
					if (i == 0) type = "had";
					s.setHad(oddsDao.selectOdds(type, s.getTeamdataid()));     //不让球
					if (i == 1) type = "hhad";
					s.setHhad(oddsDao.selectOdds(type, s.getTeamdataid()));//让球
				}
				s.setJg(recommendedResults(s,maplist));
				map.put("_" + s.getId(), s);

			}else if (a==0){
				if (b<=0){
					String type = "";
					for (int i = 0; i < 2; i++) {
						if (i == 0) type = "had";
						s.setHad(oddsDao.selectOdds(type, s.getTeamdataid()));     //不让球
						if (i == 1) type = "hhad";
						s.setHhad(oddsDao.selectOdds(type, s.getTeamdataid()));//让球
					}
					s.setJg(recommendedResults(s,maplist));
					map.put("_" + s.getId(), s);
				}
			}
		}
		StatusVo statusVo=new StatusVo();
		statusVo.setMaxcount("8");
		statusVo.setLast_updated("2020-07-28 15:41:49");
		Map allupmap=new HashMap();
		Map map1=new HashMap();
		map1=getHhadMultiallup(map1);
		allupmap.put("HHAD",map1);
		Map map2=new HashMap();
		map2=getHhadMultiallup(map2);
		allupmap.put("HAD",map2);
		statusVo.setAllup(allupmap);
		Map map3=new HashMap();
		map3.put("status",statusVo);
		map3.put("data",map);
		Long bb=System.currentTimeMillis();
		System.out.println(bb-aa+"损耗时间");
		return map3;
    }
	public static Map getHhadMultiallup(Map map){
		Multiallup m_2x1=new Multiallup();
		m_2x1.setValue("1");
		m_2x1.setMultiallup("1");
		map.put("2x1",m_2x1);

		Multiallup m_2x3=new Multiallup();
		m_2x3.setValue("0");
		m_2x3.setMultiallup("0");
		map.put("2x3",m_2x3);

		Multiallup m_3x1=new Multiallup();
		m_3x1.setValue("1");
		m_3x1.setMultiallup("1");
		map.put("3x1",m_3x1);

		Multiallup m_3x3=new Multiallup();
		m_3x3.setValue("1");
		m_3x3.setMultiallup("0");
		map.put("3x3",m_3x3);

		Multiallup m_3x4=new Multiallup();
		m_3x4.setValue("1");
		m_3x4.setMultiallup("0");
		map.put("3x4",m_3x4);

		Multiallup m_3x6=new Multiallup();
		m_3x6.setValue("0");
		m_3x6.setMultiallup("0");
		map.put("3x6",m_3x6);


		Multiallup m_3x7=new Multiallup();
		m_3x7.setValue("0");
		m_3x7.setMultiallup("0");
		map.put("3x7",m_3x7);

		Multiallup m_4x1=new Multiallup();
		m_4x1.setValue("1");
		m_4x1.setMultiallup("1");
		map.put("4x1",m_4x1);


		Multiallup m_4x4=new Multiallup();
		m_4x4.setValue("1");
		m_4x4.setMultiallup("0");
		map.put("4x4",m_4x4);

		Multiallup m_4x5=new Multiallup();
		m_4x5.setValue("1");
		m_4x5.setMultiallup("0");
		map.put("4x5",m_4x5);

		Multiallup m_4x6=new Multiallup();
		m_4x6.setValue("1");
		m_4x6.setMultiallup("0");
		map.put("4x6",m_4x6);

		Multiallup m_4x10=new Multiallup();
		m_4x10.setValue("0");
		m_4x10.setMultiallup("0");
		map.put("4x10",m_4x10);

		Multiallup m_4x11=new Multiallup();
		m_4x11.setValue("1");
		m_4x11.setMultiallup("0");
		map.put("4x11",m_4x11);

		Multiallup m_4x14=new Multiallup();
		m_4x14.setValue("0");
		m_4x14.setMultiallup("0");
		map.put("4x14",m_4x14);

		Multiallup m_4x15=new Multiallup();
		m_4x15.setValue("0");
		m_4x15.setMultiallup("0");
		map.put("4x15",m_4x15);

		Multiallup m_5x1=new Multiallup();
		m_5x1.setValue("1");
		m_5x1.setMultiallup("1");
		map.put("5x1",m_5x1);

		Multiallup m_5x5=new Multiallup();
		m_5x5.setValue("1");
		m_5x5.setMultiallup("0");
		map.put("5x5",m_5x5);

		Multiallup m_5x6=new Multiallup();
		m_5x6.setValue("1");
		m_5x6.setMultiallup("0");
		map.put("5x6",m_5x6);

		Multiallup m_5x10=new Multiallup();
		m_5x10.setValue("1");
		m_5x10.setMultiallup("0");
		map.put("5x10",m_5x10);

		Multiallup m_5x15=new Multiallup();
		m_5x15.setValue("0");
		m_5x15.setMultiallup("0");
		map.put("5x15",m_5x15);

		Multiallup m_5x16=new Multiallup();
		m_5x16.setValue("1");
		m_5x16.setMultiallup("0");
		map.put("5x16",m_5x16);

		Multiallup m_5x20=new Multiallup();
		m_5x20.setValue("1");
		m_5x20.setMultiallup("0");
		map.put("5x20",m_5x20);

		Multiallup m_5x25=new Multiallup();
		m_5x25.setValue("0");
		m_5x25.setMultiallup("0");
		map.put("5x25",m_5x25);

		Multiallup m_5x26=new Multiallup();
		m_5x26.setValue("1");
		m_5x26.setMultiallup("0");
		map.put("5x26",m_5x26);

		Multiallup m_5x30=new Multiallup();
		m_5x30.setValue("0");
		m_5x30.setMultiallup("0");
		map.put("5x30",m_5x30);

		Multiallup m_5x31=new Multiallup();
		m_5x31.setValue("0");
		m_5x31.setMultiallup("0");
		map.put("5x31",m_5x31);

		Multiallup m_6x1=new Multiallup();
		m_6x1.setValue("1");
		m_6x1.setMultiallup("1");
		map.put("6x1",m_6x1);

		Multiallup m_6x6=new Multiallup();
		m_6x6.setValue("1");
		m_6x6.setMultiallup("0");
		map.put("6x6",m_6x6);

		Multiallup m_6x7=new Multiallup();
		m_6x7.setValue("1");
		m_6x7.setMultiallup("0");
		map.put("6x7",m_6x7);

		Multiallup m_6x15=new Multiallup();
		m_6x15.setValue("1");
		m_6x15.setMultiallup("0");
		map.put("6x15",m_6x15);

		Multiallup m_6x20=new Multiallup();
		m_6x20.setValue("1");
		m_6x20.setMultiallup("0");
		map.put("6x20",m_6x20);

		Multiallup m_6x21=new Multiallup();
		m_6x21.setValue("0");
		m_6x21.setMultiallup("0");
		map.put("6x21",m_6x21);

		Multiallup m_6x22=new Multiallup();
		m_6x22.setValue("1");
		m_6x22.setMultiallup("0");
		map.put("6x22",m_6x22);

		Multiallup m_6x35=new Multiallup();
		m_6x35.setValue("1");
		m_6x35.setMultiallup("0");
		map.put("6x35",m_6x35);

		Multiallup m_6x41=new Multiallup();
		m_6x41.setValue("0");
		m_6x41.setMultiallup("0");
		map.put("6x41",m_6x41);

		Multiallup m_6x42=new Multiallup();
		m_6x42.setValue("1");
		m_6x42.setMultiallup("0");
		map.put("6x42",m_6x42);

		Multiallup m_6x50=new Multiallup();
		m_6x50.setValue("1");
		m_6x50.setMultiallup("0");
		map.put("6x50",m_6x50);

		Multiallup m_6x56=new Multiallup();
		m_6x56.setValue("0");
		m_6x56.setMultiallup("0");
		map.put("6x56",m_6x56);

		Multiallup m_6x57=new Multiallup();
		m_6x57.setValue("1");
		m_6x57.setMultiallup("0");
		map.put("6x57",m_6x57);

		Multiallup m_6x62=new Multiallup();
		m_6x62.setValue("0");
		m_6x62.setMultiallup("0");
		map.put("6x62",m_6x62);

		Multiallup m_6x63=new Multiallup();
		m_6x63.setValue("0");
		m_6x63.setMultiallup("0");
		map.put("6x63",m_6x63);

		Multiallup m_7x1=new Multiallup();
		m_7x1.setValue("1");
		m_7x1.setMultiallup("1");
		map.put("7x1",m_7x1);

		Multiallup m_7x7=new Multiallup();
		m_7x7.setValue("1");
		m_7x7.setMultiallup("0");
		map.put("7x7",m_7x7);

		Multiallup m_7x8=new Multiallup();
		m_7x8.setValue("1");
		m_7x8.setMultiallup("0");
		map.put("7x8",m_7x8);

		Multiallup m_7x21=new Multiallup();
		m_7x21.setValue("1");
		m_7x21.setMultiallup("0");
		map.put("7x21",m_7x21);

		Multiallup m_7x25=new Multiallup();
		m_7x25.setValue("1");
		m_7x25.setMultiallup("0");
		map.put("7x35",m_7x25);

		Multiallup m_7x120=new Multiallup();
		m_7x120.setValue("1");
		m_7x120.setMultiallup("0");
		map.put("7x120",m_7x120);

		Multiallup m_7x127=new Multiallup();
		m_7x127.setValue("0");
		m_7x127.setMultiallup("0");
		map.put("7x127",m_7x127);

		Multiallup m_8x1=new Multiallup();
		m_8x1.setValue("1");
		m_8x1.setMultiallup("1");
		map.put("8x1",m_8x1);

		Multiallup m_8x8=new Multiallup();
		m_8x8.setValue("1");
		m_8x8.setMultiallup("0");
		map.put("8x8",m_8x8);

		Multiallup m_8x9=new Multiallup();
		m_8x9.setValue("1");
		m_8x9.setMultiallup("0");
		map.put("8x9",m_8x9);

		Multiallup m_8x28=new Multiallup();
		m_8x28.setValue("1");
		m_8x28.setMultiallup("0");
		map.put("8x28",m_8x28);

		Multiallup m_8x56=new Multiallup();
		m_8x56.setValue("1");
		m_8x56.setMultiallup("0");
		map.put("8x56",m_8x56);

		Multiallup m_8x70=new Multiallup();
		m_8x70.setValue("1");
		m_8x70.setMultiallup("0");
		map.put("8x70",m_8x70);

		Multiallup m_8x247=new Multiallup();
		m_8x247.setValue("1");
		m_8x247.setMultiallup("0");
		map.put("8x247",m_8x247);

		Multiallup m_8x255=new Multiallup();
		m_8x255.setValue("1");
		m_8x255.setMultiallup("0");
		map.put("8x255",m_8x255);

		Multiallup single=new Multiallup();
		single.setValue("1");
		single.setMultiallup("1");
		map.put("Single",single);
		return map;
	}

	public static Integer getMin(String date,String date1,String format) throws ParseException {
		DateFormat dateFormat = new SimpleDateFormat(format);
		Date dateTime1 = dateFormat.parse(date);
		Date dateTime2 = dateFormat.parse(date1);
		int i = dateTime1.compareTo(dateTime2); //返回-1 则是比当前时间小 返回0相等  返回>1则是比当前时间大
		return i;
	}

	@Transactional(readOnly=false)
	public Object insertData(Map map) {
		List<Teamdata> list = (List<Teamdata>) map.get("data");
		//查询数据库里面的id
		try {
			if (list.size()>0) {
				for (Teamdata s : list) {
					//根据数据库的对比  有就update  没有就insert
					if (teamdataDao.findByid(s.getId()) == 1) {
						teamdataDao.update(s);
						Odds odds = s.getHad();
						if (null != odds) {
							odds.setType("had");
							odds.setTeamdataid(s.getId());
							oddsDao.updateByTeamdataIdAndType(odds);
						}
						Odds odds1 = s.getHhad();
						if (null != odds1) {
							odds1.setType("hhad");
							odds1.setTeamdataid(s.getId());
							oddsDao.updateByTeamdataIdAndType(odds1);
						}
					} else if (teamdataDao.findByid(s.getId()) == 0) {
						teamdataDao.insert(s);
						Odds odds = s.getHad();
						if (null != odds) {
							odds.setType("had");
							odds.setTeamdataid(s.getId());
							oddsDao.insert(odds);
						}
						Odds odds1 = s.getHhad();
						if (null != odds1) {
							odds1.setType("hhad");
							odds1.setTeamdataid(s.getId());
							oddsDao.insert(odds1);
						}

					}

				}
			}
			return "success";
		} catch (Exception e) {
			e.printStackTrace();
			return "error";
		}
	}

    public Object minimumOdds() throws Exception {
		SimpleDateFormat myFmt2=new SimpleDateFormat("yyyy-MM-dd HH");
		String ms =myFmt2.format(new Date());
		Object minimumOdds = CacheUtils.get("minimumOdds", ms);
		List<Map> maplist= (List<Map>) recommend.jsoup();
		if (minimumOdds==null){ //暂时先不加缓存
			Long aaa=System.currentTimeMillis();
			System.out.println("没数据");
			Map map= (Map) testController002.test();
			LinkedHashMap teamList= new LinkedHashMap();
			LinkedHashMap listMap= (LinkedHashMap) map.get("data");
			//算出每场最小值 填入数组
			Iterator iterator=listMap.entrySet().iterator();
			List<Object> list=new ArrayList<>();
			int aa=0;
			while (iterator.hasNext()){
				Map.Entry entry = (Map.Entry) iterator.next();
				Teamdata teamdata=(Teamdata)entry.getValue();
				Map map1=new HashMap();
                if (null==teamdata.getHad()){
                	continue;
				}
				double a=Double.valueOf(teamdata.getHad().getH());
				double b=Double.valueOf(teamdata.getHad().getD());
				double c=Double.valueOf(teamdata.getHad().getA());
				double min=(a<b?a:b)<c?(a<b?a:b):c;
				map1.put("bDate",teamdata.getBdate());//时间
				map1.put("num",teamdata.getNum());
				map1.put("hcnAbbr",teamdata.getHcnAbbr());
				map1.put("acnAbbr",teamdata.getAcnAbbr());
				map1.put("lcnAbbr",teamdata.getLcnAbbr());
				//推荐结果
                map1.put("tjjg",recommendedResults(teamdata,maplist));
				map1.put("H",a);//胜
				map1.put("D",b);//平
                map1.put("A",c);//负
				if (a==min) map1.put("result","胜");
				if (b==min) map1.put("result","平");
				if (c==min) map1.put("result","负");
				map1.put("time",teamdata.getTime());
				map1.put("min",min);
				aa++;
				list.add(map1);
			}
			CacheUtils.put("minimumOdds",ms,list);
			Long bbb=System.currentTimeMillis();
			System.out.println(bbb-aaa+"损耗时间02");
			return list;
		}else {
			Long aaa=System.currentTimeMillis();
			System.out.println("有数据");
			List<Object> list= (List<Object>) minimumOdds;
			Long bbb=System.currentTimeMillis();
			System.out.println(bbb-aaa+"损耗时间");
			return list;
		}

    }
    /*
    推荐结果
     */
    public String recommendedResults(Teamdata teamdata,List<Map> mapList) throws Exception {
    	String vistsl="";
		String homesl="";
		;//缩写后
		;//未缩写客队名字
    	//查询雷速该比赛的胜率
		for (Map s:mapList){
			String acn=teamdata.getAcn();//客队未缩写
			String acnabbr=teamdata.getAcnAbbr();//客队缩写
			String hcn=teamdata.getHcn();//主队未缩写
			String hcnabbr=teamdata.getHcnAbbr();//主队缩写
			if (acn.equals("仁川联")){
				acn+="队";
			}
			if (hcn.equals("仁川联")){
				hcn+="队";
			}
			if (s.containsKey(acn)){
				vistsl= (String) s.get(acn);
			}else if (s.containsKey(acnabbr)){
				vistsl= (String) s.get(acnabbr);
			}
			if (s.containsKey(hcn)){
				homesl= (String) s.get(hcn);
			}else if (s.containsKey(hcnabbr)){
				homesl= (String) s.get(hcnabbr);
			}

		}
        if (homesl.isEmpty()||vistsl.isEmpty()){
        	return "";
		}
		float homeTeam=Float.valueOf(homesl.replaceAll("%",""));
		float vistTeam=Float.valueOf(vistsl.replaceAll("%",""));
        if (homeTeam>vistTeam){
        	return "胜";
		}else if (homeTeam==vistTeam){
        	return "平";
		}else {
        	return "负";
		}
	}


    /*
   推荐结果analysisCoefficient
    */
    public Object analysisCoefficient(Teamdata teamdata,List<Map> mapList) throws Exception {
        String vistsl="";
        String homesl="";
        String homelssl="";
        String vistlssl="";
        String homejq="";
        String vistjq="";
        String homesq="";
        String vistsq="";
        ;//缩写后
        ;//未缩写客队名字
        //查询雷速该比赛的胜率
        for (Map s:mapList){
            teamdata.getAcn();//客队未缩写
            teamdata.getAcnAbbr();//客队缩写
            teamdata.getHcn();//主队未缩写
            teamdata.getHcnAbbr();//主队缩写
            if (s.containsKey(teamdata.getAcn())){
                vistsl= (String) s.get("Teamwinning");
                vistlssl= (String) s.get("historicalwinrate");
                vistjq= (String) s.get("numberofgoals");
                vistsq= (String) s.get("conceded");
            }else if (s.containsKey(teamdata.getAcnAbbr())){
                vistsl= (String) s.get("Teamwinning");
                vistlssl= (String) s.get("historicalwinrate");
                vistjq= (String) s.get("numberofgoals");
                vistsq= (String) s.get("conceded");
            }
            if (s.containsKey(teamdata.getHcn())){
                homesl= (String) s.get("Teamwinning");
                homelssl= (String) s.get("historicalwinrate");
                homejq= (String) s.get("numberofgoals");
                homesq= (String) s.get("conceded");
            }else if (s.containsKey(teamdata.getHcnAbbr())){
                homesl= (String) s.get("Teamwinning");
                homelssl= (String) s.get("historicalwinrate");
                homejq= (String) s.get("numberofgoals");
                homesq= (String) s.get("conceded");
            }

        }
        if (homesl.isEmpty()||vistsl.isEmpty()){
            return "";
        }
        //胜率
        float homeTeam=Float.valueOf(homesl.replaceAll("%",""));
        float vistTeam=Float.valueOf(vistsl.replaceAll("%",""));
        //历史胜率
        float homeTeamls=Float.valueOf(homelssl.replaceAll("%",""));
        float vistTeamls=Float.valueOf(vistlssl.replaceAll("%",""));
        int homeTeamjq=Integer.valueOf(homejq);
        int vistTeamjq=Integer.valueOf(vistjq);
        int homeTeamsq=Integer.valueOf(homesq);
        int vistTeamsq=Integer.valueOf(vistsq);
		Map<String, Object> paramMap=new HashMap<>();
		paramMap.put("a", homeTeam);
		paramMap.put("a1",vistTeam); //0.064
		paramMap.put("b",homeTeamls);
		paramMap.put("b1",vistTeamls);//0.134
		paramMap.put("c",homeTeamjq);
		paramMap.put("c1",vistTeamjq);//3.5
		paramMap.put("d",homeTeamsq);
		paramMap.put("d1",vistTeamsq);//1.5
		String string = AviatorEvaluator.execute("(a-a1)*0.8+(b-b1)*0.2+(c-c1)*0.5+(d-d1)*0.5", paramMap).toString();
        return string;
    }

	public Object getGameData(String time,String num) throws IOException {
		String str = "https://zx.500.com/jczq/kaijiang.php?playid=2&d=";
		String url = str + time;
		Connection conn = Jsoup.connect(url);
		Document doc = conn.get(); // 解析页面
		List<Map<String,Object>> list=new ArrayList<>();
		Map map1=new HashMap();
		Elements rows = doc.select("table[class=ld_table]").get(0).select("tr");
		if (rows.size() == 1) {
			return map1;
		}else {
			for (int i=1;i<rows.size();i++){
				Map map=new HashMap();
				Element row = rows.get(i);
				map.put("num",row.select("td").get(0).text());//次序
				map.put("lcnAbbr",row.select("td").get(1).text());//联赛
				map.put("competingTime",row.select("td").get(2).text());//时间
				map.put("hcnAbbr",row.select("td").get(3).text());//主队
				map.put("acnAbbr",row.select("td").get(5).text());//客队
				map.put("result",row.select("td").get(7).text());//赛果
				map.put("jj",row.select("td").get(9).text());//奖金比率
				if (map.get("num").equals(num)){
					return map;
				}
			}
			return map1;
		}
	}

	public Object personalCenterHistogram(String userCode) {
		Map map=new HashMap();
		//统计该用户下投注的信息
		List<String> list=oddsDao.selectByUserCode(userCode);////天
		List<Long> sumList=new ArrayList<>();
		for (String s:list){ //时间
			float sum=0;
			List<Long> comm=oddsDao.selectBycom(s,userCode); //组合
			for (Long f:comm){ //每个组合的下注的钱
				List<BkpUserbets> list1=oddsDao.selectByCombination(f);
				sum+=list1.get(0).getBetamount();
			}
			//这个时间的钱
			sumList.add((long) sum);
		}
		map.put("time",list);
		map.put("money",sumList);
		return map;
	}

    public Object userWins(String userCode) {
		Map map=new HashMap();
		List<Map> list=bkpUserbetsService.selectCombination(userCode);
		if (list.size()==0){
			return map;
		}
		int zz=0;
		int wzz=0;
		int dkj=0;
		for (Map s:list){
			List<BkpUserbets> list1 = bkpUserbetsService.selectByCombination(Long.valueOf((String) s.get("combination")));
			if (list1.size() == 0) {
				continue;
			}
			//查询下面结果出来的  数据进行对比
			boolean flag=true;
			String matchResults = "";
			for (BkpUserbets f:list1){
				if (f.getMatchresults().equals("待开奖")) {
					matchResults += "待开奖";
				} else {
					int i = f.getPick().indexOf(f.getMatchresults());
					if (i < 0) {
						flag = false;
					}
					matchResults += f.getMatchresults();
				}
				matchResults += ";";
			}
			if (flag){
				if (matchResults.indexOf("待开奖")>=0){ //说明都是待开奖
					dkj++;
				}else { //中注
					zz++;
				}
			}else {
				if (matchResults.indexOf("待开奖")>=0){  //还没出完整的结果
					dkj++;
				}else {
					wzz++;
				}
			}
		}
		map.put("zz",zz);
		map.put("wzz",wzz);
		map.put("dkj",dkj);
		return map;
    }

	public Teamdata selectTeam(Map param) {
		return oddsDao.selectTeam(param);
	}

	public Object explanation(Map param) {
		Map map=new HashMap();
		BkpRecommendeddescription hometeam=oddsDao.selectExplanation(param.get("hometeam"));
		BkpRecommendeddescription vistteam=oddsDao.selectExplanation(param.get("vistteam"));
		map.put("hometeam",hometeam);
		map.put("vistteam",vistteam);
		return map;
	}


	public Object queryTid(String team) {
    	Map map=new HashMap();
    	SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyy-MM-dd");
    	String time=simpleDateFormat.format(new Date());
    	List<String> tid=oddsDao.queryTid(team);
    	if (tid.size()==0){
			tid=oddsDao.queryTid1(team);
			if (tid.size()!=0){
				map.put("tid",tid.get(0));
			}
		}else{
    		map.put("tid",tid.get(0));
		}
    	map.put("time",time);
    	return map;
	}

    public void resultSet(HttpServletResponse res) throws Exception {
        List<Teamdata> list1=new ArrayList<Teamdata>();  //比赛list
        Map<String, Multiallup> hhad_multiallupMap=new HashMap<String, Multiallup>();  //不让球过关方式
        Map<String, Multiallup> had_multiallupMap=new HashMap<String, Multiallup>();   //让球过关方式
        DoGetData doGET =new DoGetData();
        Map map=doGET.getDatas(list1,hhad_multiallupMap,had_multiallupMap);
		DoGetData002 doGetData002=new DoGetData002();
        List<Opp> oppList= (List<Opp>) doGetData002.doGetData002();//拿到支持率
        List<Teamdata> list = (List<Teamdata>) map.get("data");
        List<Map> maplist= (List<Map>) recommend.jsoup1();
		List<MemberVo> memberVoList=new ArrayList<>();
        for (Teamdata s:list){
           ///查看里面是否有该队数据
            String o = (String) analysisCoefficient(s, maplist);
            if (!o.equals("")){
				MemberVo memberVo=new MemberVo();
				@Valid MemberVo memberVo1 = memberVo.getMemberVo();
				memberVo1.setLs(s.getLcnAbbr());
				memberVo1.setZd(s.getHcnAbbr());
				memberVo1.setKd(s.getAcnAbbr());
				memberVo1.setNum(s.getNum());
				memberVo1.setSj(s.getBdate());
				memberVo1.setXs(o);
				memberVo1.setFzcl("0");
				memberVo1.setPzcl("0");
				memberVo1.setSzcl("0");
				memberVo1.setTotal("0");
                memberVo1.setSingle("");
				for (Opp t:oppList){
					if (null!=t.getHad()) {
						if (s.getId().toString().equals(t.getHad().getMid())) {
							memberVo1.setSzcl(t.getHad().getWin());
							if (s.getHad().getSingle().toString().equals("1")){
                                memberVo1.setSingle("单");
                            }
							memberVo1.setPzcl(t.getHad().getDraw());
							memberVo1.setFzcl(t.getHad().getLose());
							memberVo1.setTotal(t.getHad().getTotal());
							break;
						}
					}
				}
				memberVoList.add(memberVo1);
            }
        }
		String fileName = "系数数据" + DateUtils.getDate("yyyyMMddHHmmss") + ".xlsx";
		try(ExcelExport ee = new ExcelExport("系数数据", MemberVo.class)){
			ee.setDataList(memberVoList).write(res, fileName);
		}
    }

	public List<Teamdata> selectTeamdata() {
    	return oddsDao.selectTeamdata();
	}

	public Odds get1(Odds odds) {
    	return oddsDao.get1(odds.getTeamdataid(),odds.getType());
	}

	public Object test02(String time) throws Exception {
		Long aa=System.currentTimeMillis();
		HashMap map=new LinkedHashMap();
		List<Teamdata> list=oddsDao.selectAllTeamdata();
		List<Map> maplist= (List<Map>) recommend.jsoup();
		///查询赔率值  根据Teamdata里面的值id值查询出具体的值
		DateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
		DateFormat sdf1=new SimpleDateFormat("HH:mm:ss");
		Date date=new Date();
		String Date01=sdf.format(date);
		String Time=sdf1.format(date);
		for (Teamdata s:list){
			int a=getMin(time,s.getDate(),"yyyy-MM-dd");
//			int b=getMin(Time,s.getTime(),"HH:mm:ss");
//			if (a<0) {
//				String type = "";
//				for (int i = 0; i < 2; i++) {
//					if (i == 0) type = "had";
//					s.setHad(oddsDao.selectOdds(type, s.getTeamdataid()));     //不让球
//					if (i == 1) type = "hhad";
//					s.setHhad(oddsDao.selectOdds(type, s.getTeamdataid()));//让球
//				}
//				s.setJg(recommendedResults(s,maplist));
//				map.put("_" + s.getId(), s);
//
//			}else
			if (a==0){
//				if (b<=0){
					String type = "";
					for (int i = 0; i < 2; i++) {
						if (i == 0) type = "had";
						s.setHad(oddsDao.selectOdds(type, s.getTeamdataid()));     //不让球
						if (i == 1) type = "hhad";
						s.setHhad(oddsDao.selectOdds(type, s.getTeamdataid()));//让球
					}
					s.setJg(recommendedResults(s,maplist));
					map.put("_" + s.getId(), s);
//				}
			}
		}
		StatusVo statusVo=new StatusVo();
		statusVo.setMaxcount("8");
		statusVo.setLast_updated("2020-07-28 15:41:49");
		Map allupmap=new HashMap();
		Map map1=new HashMap();
		map1=getHhadMultiallup(map1);
		allupmap.put("HHAD",map1);
		Map map2=new HashMap();
		map2=getHhadMultiallup(map2);
		allupmap.put("HAD",map2);
		statusVo.setAllup(allupmap);
		Map map3=new HashMap();
		map3.put("status",statusVo);
		map3.put("data",map);
		Long bb=System.currentTimeMillis();
		System.out.println(bb-aa+"损耗时间");
		return map3;
	}
}