package com.jeesite.modules.odds.web;


import com.jeesite.modules.odds.entity.DoGetData;
import com.jeesite.modules.odds.entity.Multiallup;
import com.jeesite.modules.odds.service.OddsService;
import com.jeesite.modules.teamdata.entity.Teamdata;
import com.jeesite.modules.userbets.entity.Userbets;
import com.jeesite.modules.userbets.service.UserbetsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.text.ParseException;
import java.util.*;

@Controller
@RequestMapping("/test01")
public class TestController002 {

    @Autowired private OddsService oddsService;
    @Autowired
    private UserbetsService userbetsService;


    @GetMapping("/test01")
    @ResponseBody
    public Object test() throws Exception {
        return oddsService.test();
    }
    @RequestMapping("/test02")
    @ResponseBody
    public Object test02(@RequestParam String time) throws Exception {
        return oddsService.test02(time);
    }
    @GetMapping("/minimumOdds")
    @ResponseBody
    public Object minimumOdds() throws Exception {
        return oddsService.minimumOdds();
    }
    @GetMapping("/ceshi")
    @ResponseBody
    public Object ceshi() throws Exception {
        List<Teamdata> list=new ArrayList<Teamdata>();  //比赛list
        Map<String, Multiallup> hhad_multiallupMap=new HashMap<String, Multiallup>();  //让球过关方式
        Map<String, Multiallup> had_multiallupMap=new HashMap<String, Multiallup>();   //不让球过关方式
        DoGetData doGET =new DoGetData();
        Map map=doGET.getDatas(list,hhad_multiallupMap,had_multiallupMap);
        return oddsService.insertData(map);
    }
    //获取比赛数据
    @PostMapping("/getGameData")
    @ResponseBody
    public Object getGameData(@RequestParam String time,@RequestParam String num) throws IOException {
        return oddsService.getGameData(time,num);
    }

    @GetMapping("/insertMatchResult")
    public void insertMatchResult() throws IOException {
        List<Userbets> list=userbetsService.selectByResult();
        if (list.size()>0){
            for (Userbets  s: list){
                 List<String> list1= Arrays.asList(s.getSelectresult().split(";"));
                 String rs="";
                 Boolean flag=true;
                 for (String j:list1){
                     String result1=j.substring(0,15);//2020-08-16周日027
                     String time=result1.substring(0,10);//2020-08-16
                     String num=result1.substring(10,15);//周日027
                     Map<String,Object> result= (Map<String, Object>) getGameData(time,num);
                     if (null==result.get("result")){
                         result.put("result","未开赛");
                     }
                     //对比投注结果
                     int index = j.indexOf(result.get("result") + ":");
                     if (index<0) flag=false;
                     rs+=result.get("result")+"/";
                 }
                System.out.println(rs);
                 int a=rs.indexOf("未开赛");
                 if (a<0){
                     if (flag){
                         s.setResult("中注");
                     }else {
                         s.setResult("未中注");
                     }
                 }
                s.setMatchResults(rs);
                userbetsService.update(s);
            }
        }
    }
}
