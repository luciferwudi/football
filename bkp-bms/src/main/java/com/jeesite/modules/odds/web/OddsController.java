/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.odds.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import com.jeesite.common.lang.DateUtils;
import com.jeesite.common.utils.excel.ExcelExport;
import com.jeesite.modules.bkp.entity.BkpFixedprofit;
import com.jeesite.modules.bkp.service.BkpFixedprofitService;
import com.jeesite.modules.bkp.web.BkpFixedprofitController;
import com.jeesite.modules.sys.entity.User;
import com.jeesite.modules.sys.utils.UserUtils;
import com.jeesite.modules.teamdata.entity.Teamdata;
import com.jeesite.modules.teamdata.service.TeamdataService;
import com.jeesite.modules.utils.TimeUpdate;
import com.jeesite.modules.vo.RqVo;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import com.jeesite.common.config.Global;
import com.jeesite.common.entity.Page;
import com.jeesite.common.web.BaseController;
import com.jeesite.modules.odds.entity.Odds;
import com.jeesite.modules.odds.service.OddsService;

import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * oddsController
 *
 * @author Lucifer
 * @version 2020-07-31
 */
@Controller
@RequestMapping(value = "${adminPath}/odds/odds")
public class OddsController extends BaseController {

    @Autowired
    private OddsService oddsService;
    @Autowired
    private BkpFixedprofitController bkpFixedprofitController;
    @Autowired
    private BkpFixedprofitService bkpFixedprofitService;
    @Autowired
    private TimeUpdate timeUpdate;
    @Autowired
    private TeamdataService teamdataService;

    /**
     * 获取数据
     */
    @ModelAttribute
    public Odds get(String id, boolean isNewRecord) {
        return oddsService.get(id, isNewRecord);
    }

    /**
     * 查询列表
     */
    @RequiresPermissions("odds:odds:view")
    @RequestMapping(value = {"list", ""})
    public String list(Odds odds, Model model) {
        model.addAttribute("odds", odds);
        return "modules/odds/oddsList";
    }

    //	@RequiresPermissions("odds:odds:view")
    @RequestMapping(value = "web")
    public String web() throws IOException {
        User user = UserUtils.getUser();
        if (user.getUserCode().equals("poor_ztt7")) {
            return "modules/test/ld";
        } else {
            return "modules/test/web";
        }

    }

    @RequestMapping(value = "web01")
    public String web01() {
        return "modules/test/web01";
    }

    /*
    查询球队tid
     */
    @RequestMapping(value = "queryTid")
    @ResponseBody
    public Object queryTid(@RequestParam String team) {
        return oddsService.queryTid(team);
    }


    @RequestMapping(value = "queryTeamById")
    @ResponseBody
    public Object queryTeamById(@RequestParam String id) {
        return teamdataService.get(id);
    }

    /*
    用户投注数据的柱状图
     */
    @RequestMapping(value = "personalCenterHistogram")
    @ResponseBody
    public Object personalCenterHistogram() {
        User user = UserUtils.getUser();
        return oddsService.personalCenterHistogram(user.getUserCode());
    }

    /*
    用户中奖的饼状图
     */
    @RequestMapping(value = "userWins")
    @ResponseBody
    public Object userWins() {
        User user = UserUtils.getUser();
        return oddsService.userWins(user.getUserCode());
    }
    @RequestMapping("makeUp")
    public String makeUp(){
        return "modules/user/bl";
    }

    @RequestMapping(value = "personalCenter")
    public String personalCenter() {
        return "modules/user/personalCenter";
    }

    @RequestMapping(value = "recommendedDescription")
    public String recommendedDescription() {
        return "modules/user/recommendedDescription";
    }

    @RequestMapping(value = "recommendedDescription1")
    public String recommendedDescription1() {
        return "modules/user/recommendedDescription1";
    }

    @RequestMapping(value = "schedule")
    public String playerInjury() {

        return "modules/test/schedule";
    }

    /*
    爬取球队的id
     */
    @RequestMapping(value = "queryTid1")
    @ResponseBody
    public Object queryTid1(@RequestParam String team) throws IOException {
        Map map=new HashMap();
        String url = "https://trade.500.com/jczq/";
        Connection conn = Jsoup.connect(url);
        Document doc = conn.get(); // 解析页面
        Elements ss = doc.select("a[class=team-l]");//主队
        Elements vist = doc.select("a[class=team-r]");//客队
        String href = "";
        String teamname="";
        for (int i = 0; i < ss.size(); i++) {
            Element s = ss.get(i);
            Element s1 = vist.get(i);
            String homeTeam = s.text();
            String visTeam = s1.text();
            if (homeTeam.equals(team)) {
                href = s.attr("href");
                String[] str = href.split("team");
                href = str[str.length - 1].replaceAll("/", "");
                teamname=homeTeam;
            }
            if (visTeam.equals(team)) {
                href = s1.attr("href");
                String[] str = href.split("team");
                href = str[str.length - 1].replaceAll("/", "");
                teamname=visTeam;
            }
        }
        map.put("href",href);
        map.put("teamname",teamname);
        return map;
    }

    @RequestMapping(value = "teamfixture")
    @ResponseBody
    public Object teamfixture(@RequestParam int id) throws Exception{
        //先去爬取 https://liansai.500.com/team/1578/teamlineup/
        String url = "https://liansai.500.com/team/"+id+"/teamfixture/";
        String fileName = "f://html//" + id +"teamfixture"+ ".txt";
        saveHtml1(url,fileName);
        //然后读取
        //读取html内容
        String content = readHtml(fileName);
        System.out.println("====" + content);
        Map map = new HashMap();
        map.put("ce", content);
        return map;
    }
    public void saveHtml1(String url,String fileName) throws IOException {
        Connection conn = Jsoup.connect(url); // 建立与url中页面的连接
        Document doc = conn.get(); // 解析页面
        doc.select("div[class=header header2]").remove();
        doc.select("div[class=footer]").remove();
        doc.select("div[class=topbar]").remove();
        doc.select("div[class=div_m]").remove();
        doc.select("div[class=nav]").remove();
        doc.select("div[class=lcrumbs]").remove();
        doc.select("div[class=footer-keywords-wrap]").remove();
        doc.select("div[class=lcol_hd lcol_hd_nobd]").remove();
        doc.select("a[href!=#]").removeAttr("href");
        Elements elements=doc.select("a[href!=#]");
        String team=doc.select("h2[class=lsnav_qdnav_name]").text();
        doc.select("base").remove();
        Elements ss = doc.select("ul[class=lsnav_qdnav_list clearfix]");
        Elements li = ss.select("li");
        li.get(0).select("a").attr("href","schedule");
        li.get(0).select("a").attr("onclick","f1()");
        li.get(1).select("a").attr("href","schedule");
        li.get(1).select("a").attr("onclick","teamfixture()");
        li.get(2).remove();
        ss.append("<li><a href='web01' style='color:red' target='_blank'>"+team+"球员资料"+"</a></li>");
        Elements parents = doc.select("select[class=his_select_pl]");
        for (int i=0;i<parents.size();i++){
            Elements s=parents.get(i).select("option");
            for (int j=1;j<s.size();j++){
                s.get(j).remove();
            }
        }
        Elements select = doc.select("table[class=ltable jTrHover]");
        for (int i=0;i<select.size();i++){
            Elements s=select.get(i).select("th");
            s.get(s.size()-1).remove();
        }
        doc.select("div[class=ltab_hd]").remove();
        Elements select1 = doc.select("tbody[class=jTrInterval his_table]").select("tr");
        for (int i=0;i<select1.size();i++){
            Elements td=select1.get(i).select("td");
            td.get(td.size()-1).remove();
        }
        Elements select2 = doc.select("table[class=lweilss_list ltable jTrHover]");
        for (int i=0;i<select2.size();i++){
            Elements th = select2.get(i).select("th");
            th.get(th.size()-1).remove();
        }
        Elements elements1=doc.select("tbody[id=f_table]");
        for (int i=0;i<elements1.size();i++){
            Elements tr=elements1.get(i).select("tr");
            for (int j=0;j<tr.size();j++){
                Elements td=tr.get(j).select("td");
                td.get(td.size()-1).remove();
            }
        }

        FileOutputStream fos = new FileOutputStream(fileName, false);
        fos.write(doc.html().getBytes());
        fos.close();
    }

    @RequestMapping(value = "ce")
    @ResponseBody
    public Object c(@RequestParam int aa) throws Exception {
        //先去爬取
        String url = "https://liansai.500.com/team/" + aa + "/";
        String fileName = "f://html//" + aa + ".txt";
        saveHtml(url, fileName);
        //然后读取
        //读取html内容
        String content = readHtml(fileName);
        System.out.println("====" + content);
        Map map = new HashMap();
        map.put("ce", content);
        return map;
    }

    public void saveHtml(String url, String filename) throws Exception {
        Connection conn = Jsoup.connect(url); // 建立与url中页面的连接
        Document doc = conn.get(); // 解析页面
        doc.select("div[class=lcol_tit_r]").remove();
        doc.select("div[class=lbox_tit_r]").remove();
        doc.select("a[class=lbox_tit_r]").remove();
        Elements select = doc.select("table[class=lstable2 lweilss_list_s ltc jTrHover]");
        Elements th = select.select("th");
        th.get(th.size() - 1).remove();
		Elements select1 = doc.select("table[class=lcur_race_list ltable jTrHover]");
		Elements th1=select1.select("th");
		th1.get(th1.size()-1).remove();
        //onclick=qyxxsb("+team+")"+"
        Elements lbox=doc.select("div[class=lbox]");
        lbox.get(lbox.size()-1).remove();
        doc.select("td[class=last]").remove();
        doc.select("div[class=header header2]").remove();
        doc.select("div[class=footer]").remove();
        doc.select("div[class=topbar]").remove();
        doc.select("div[class=div_m]").remove();
        doc.select("div[class=nav]").remove();
        doc.select("div[class=lcrumbs]").remove();
        doc.select("div[class=footer-keywords-wrap]").remove();
        doc.select("a[href!=#]").removeAttr("href");
        Elements elements = doc.select("a[href!=#]");
        String team=doc.select("h2[class=lsnav_qdnav_name]").text();
        doc.select("base").remove();
        Elements ss = doc.select("ul[class=lsnav_qdnav_list clearfix]");
        Elements li = ss.select("li");
        li.get(0).select("a").attr("href","schedule");
        li.get(0).select("a").attr("onclick","f1()");
        li.get(1).select("a").attr("href","schedule");
        li.get(1).select("a").attr("onclick","teamfixture()");
        li.get(2).remove();
        ss.append("<li><a href='web01' style='color:red' target='_blank'>"+team+"球员资料"+"</a></li>");
        FileOutputStream fos = new FileOutputStream(filename, false);
        fos.write(doc.html().getBytes());
        fos.close();
    }

    public String readHtml(String fileName) throws Exception {
        File file = new File(fileName);
        BufferedReader reader = null;
        StringBuffer sbf = new StringBuffer();
        try {
            reader = new BufferedReader(new FileReader(file));
            String tempStr;
            while ((tempStr = reader.readLine()) != null) {
                sbf.append(tempStr);
            }
            reader.close();
            return sbf.toString();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
        }
        return sbf.toString();
    }


    @RequestMapping(value = "selectTeam")
    @ResponseBody
    public Teamdata selectTeam(@RequestParam Map param) {
        return oddsService.selectTeam(param);
    }

    @RequestMapping(value = "explanation")
    @ResponseBody
    public Object explanation(@RequestParam Map param) {
        return oddsService.explanation(param);
    }


    @RequestMapping(value = "resultSet")
    public void resultSet(HttpServletResponse response) throws Exception {
        oddsService.resultSet(response);
    }

    /**
     * 查询列表数据
     */
    @RequiresPermissions("odds:odds:view")
    @RequestMapping(value = "listData")
    @ResponseBody
    public Page<Odds> listData(Odds odds, HttpServletRequest request, HttpServletResponse response) {
        odds.setPage(new Page<>(request, response));
        Page<Odds> page = oddsService.findPage(odds);
        return page;
    }

    /**
     * 查看编辑表单
     */
    @RequiresPermissions("odds:odds:view")
    @RequestMapping(value = "form")
    public String form(Odds odds, Model model) {
        model.addAttribute("odds", odds);
        return "modules/odds/oddsForm";
    }

    @GetMapping(value = "userQuery")
    @ResponseBody
    public Object userQuery() throws IOException, ParseException {
//		User user=UserUtils.getUser();

        return bkpFixedprofitController.userQuery();
    }

    public void modifyStatus(String UserCode) {
        BkpFixedprofit bkpFixedprofit = new BkpFixedprofit();
        bkpFixedprofit.setUsercode(UserCode);
        List<BkpFixedprofit> list = bkpFixedprofitService.findList(bkpFixedprofit);
        for (BkpFixedprofit s : list) {
            if (Float.valueOf(s.getProfitamount()) >= (Float.valueOf(s.getMonthamount()) + Float.valueOf(s.getTotalloss()))) {
                s.setDeletelogo(0L);
                SimpleDateFormat sim = new SimpleDateFormat("yyyy-MM-dd");
                s.setEndtime(sim.format(new Date()));
                bkpFixedprofitService.update(s);
            }
        }
    }

    /**
     * 保存数据
     */
    @RequiresPermissions("odds:odds:edit")
    @PostMapping(value = "save")
    @ResponseBody
    public String save(@Validated Odds odds) {
        oddsService.save(odds);
        return renderResult(Global.TRUE, text("保存odds成功！"));
    }

    /**
     * 删除数据
     */
    @RequiresPermissions("odds:odds:edit")
    @RequestMapping(value = "delete")
    @ResponseBody
    public String delete(Odds odds) {
        oddsService.delete(odds);
        return renderResult(Global.TRUE, text("删除odds成功！"));
    }

    /*
    导出让球 excel表
     */
    @RequestMapping("exportHandicap")
    public void exportHandicap(HttpServletResponse res){
        //查询近7天
        List<Teamdata> list=oddsService.selectTeamdata();
        List<RqVo> rqVoList=new ArrayList<>();
        for (Teamdata s:list){
            Odds odds=new Odds();
            odds.setTeamdataid(s.getId());
            odds.setType("hhad");
            Odds odds1=oddsService.get1(odds);
            if (null!=odds1){
                String fixedodds = odds1.getFixedodds();
                RqVo rqVo=new RqVo();
                @Valid RqVo rqVo1 = rqVo.getrqVo();
                rqVo1.setKd(s.getAcnAbbr());
                rqVo1.setZd(s.getHcnAbbr());
                rqVo1.setLs(s.getLcnAbbr());
                rqVo1.setSj(s.getBdate());
                rqVo1.setNum(s.getNum());
                rqVo1.setResult(s.getResult());
                rqVo1.setRq(fixedodds);
                rqVoList.add(rqVo1);
            }
        }
        String fileName = "让球数据" + DateUtils.getDate("yyyyMMddHH") + ".xlsx";
        try(ExcelExport ee = new ExcelExport("让球数据", RqVo.class)){
            ee.setDataList(rqVoList).write(res, fileName);
        }
    }


}