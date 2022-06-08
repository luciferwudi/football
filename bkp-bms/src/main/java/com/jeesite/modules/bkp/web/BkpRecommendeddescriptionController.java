/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.bkp.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.jeesite.common.shiro.realms.B;
import com.jeesite.common.shiro.realms.V;
import com.jeesite.modules.bkp.entity.BkpCompetitionresult;
import com.jeesite.modules.teamdata.entity.Teamdata;
import com.jeesite.modules.utils.RecommendJsoup;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import com.jeesite.common.config.Global;
import com.jeesite.common.entity.Page;
import com.jeesite.common.web.BaseController;
import com.jeesite.modules.bkp.entity.BkpRecommendeddescription;
import com.jeesite.modules.bkp.service.BkpRecommendeddescriptionService;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 推荐说明表Controller
 *
 * @author Lucifer
 * @version 2020-08-28
 */
@Controller
@RequestMapping(value = "${adminPath}/bkp/bkpRecommendeddescription")
public class BkpRecommendeddescriptionController extends BaseController {

    @Autowired
    private BkpRecommendeddescriptionService bkpRecommendeddescriptionService;
    @Autowired
    private RecommendJsoup recommendJsoup;

    /**
     * 获取数据
     */
    @ModelAttribute
    public BkpRecommendeddescription get(String id, boolean isNewRecord) {
        return bkpRecommendeddescriptionService.get(id, isNewRecord);
    }

    /**
     * 查询列表
     */
    @RequiresPermissions("bkp:bkpRecommendeddescription:view")
    @RequestMapping(value = {"list", ""})
    public String list(BkpRecommendeddescription bkpRecommendeddescription, Model model) {
        model.addAttribute("bkpRecommendeddescription", bkpRecommendeddescription);
        return "modules/bkp/bkpRecommendeddescriptionList";
    }

    /**
     * 查询列表数据
     */
    @RequiresPermissions("bkp:bkpRecommendeddescription:view")
    @RequestMapping(value = "listData")
    @ResponseBody
    public Page<BkpRecommendeddescription> listData(BkpRecommendeddescription bkpRecommendeddescription, HttpServletRequest request, HttpServletResponse response) {
        bkpRecommendeddescription.setPage(new Page<>(request, response));
        Page<BkpRecommendeddescription> page = bkpRecommendeddescriptionService.findPage(bkpRecommendeddescription);
        return page;
    }

    /**
     * 查看编辑表单
     */
    @RequiresPermissions("bkp:bkpRecommendeddescription:view")
    @RequestMapping(value = "form")
    public String form(BkpRecommendeddescription bkpRecommendeddescription, Model model) {
        model.addAttribute("bkpRecommendeddescription", bkpRecommendeddescription);
        return "modules/bkp/bkpRecommendeddescriptionForm";
    }

    @RequestMapping(value = "lsym")
    public String lsym(){
        return "modules/test/leis";
    }

    /**
     *
     * @return
     */
    @RequestMapping(value = "leisugd")
    @ResponseBody
    public Object leisugd(@RequestParam String href) throws Exception {
        String url=href;
        String[] aa=url.split("/");
        String aaa=aa[aa.length-1];
        Connection conn = Jsoup.connect(url); // 建立与url中页面的连接
        // 解析页面
        Document doc = conn.get();
        doc.select("div[class=header]").remove();
        doc.select("div[class=hot-page m-b-20]").remove();
        doc.select("div[class=footer-bottom]").remove();
        doc.select("div[class=tool-tips-fixed]").remove();
        doc.select("div[id=disk-analysis]").remove();
        Elements colstable= doc.select("div[class=clearfix-row]");
        Elements tds = colstable.get(0).select("td");
        tds.get(tds.size()-1).remove();
//        doc.select("script").remove();
        doc.select("div[class=team-icon]").remove();
        doc.select("div[class=tool-tips-fixed hide]").remove();
        doc.select("i[class=icon]").remove();
        doc.select("div[class=setting-panel]").remove();
        doc.select("a[href!=#]").removeAttr("href");
        doc.select("div[class=clearfix-row f-s-14 color-777]").remove();
        String fileName = "f://leisu//" + aaa + ".txt";
        FileOutputStream fos = new FileOutputStream(fileName, false);
        fos.write(doc.html().getBytes());
        fos.close();

        String context=readHtml(fileName);
        context=context.replaceAll("static","www");
        Map map = new HashMap();
        map.put("ce", context);
        return map;
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


    @PostMapping(value = "teamQuery")
    @ResponseBody
    public Object teamQuery(@RequestParam String hometeam, @RequestParam String awayteam) {
        return bkpRecommendeddescriptionService.teamQuery(hometeam, awayteam);
    }

    /**
     * 往雷速查询推荐比赛的信息,修改推荐说明
     */
    @PostMapping(value = "queryls")
    @ResponseBody
    public void queryls(BkpCompetitionresult bkpCompetitionresult) throws Exception {
		List<String> list=new ArrayList<>();
        List<Teamdata> list1 =bkpRecommendeddescriptionService.selectTeam(bkpCompetitionresult.getHometeam());
        list.add(list1.get(0).getHcn());//换成不缩写的
        List<Teamdata> list2 =bkpRecommendeddescriptionService.selectATeam(bkpCompetitionresult.getAwayteam());
		list.add(list2.get(0).getAcn());//不缩写的
        List<String> list12=new ArrayList<>();
        list12.add(bkpCompetitionresult.getHometeam());
        list12.add(bkpCompetitionresult.getAwayteam());
		 for (int i=0;i<list.size();i++){
			//查询是否已经有此数据
			BkpRecommendeddescription bkpRecommendeddescription2 = bkpRecommendeddescriptionService.findByTeam(list12.get(i));
			if (null==bkpRecommendeddescription2){
				//新增
				Map map = (Map) recommendJsoup.jsoup(list.get(i));
				BkpRecommendeddescription bkpRecommendeddescription3=new BkpRecommendeddescription();
				bkpRecommendeddescription3.setConceded((String) map.get("conceded"));
				bkpRecommendeddescription3.setLeague((String) map.get("league"));
                String[] yqdsz = (String[]) map.get("yqdsz");
                String[]  ms =  (String[]) map.get("ms");
                List<String> ys=new ArrayList<>();
                List<String> ls=new ArrayList<>();
                if (yqdsz!=null){
                    for (int j=0;j<yqdsz.length;j++){
                        if ("优".equals(yqdsz[j])){
                            ys.add(ms[j]);
                        }else if ("劣".equals(yqdsz[j])){
                            ls.add(ms[j]);
                        }
                    }
                    if (ys.size()==1){
                        bkpRecommendeddescription3.setSuperiority1(ys.get(0));
                    }
                    if (ys.size()==2){
                        bkpRecommendeddescription3.setSuperiority1(ys.get(0));
                        bkpRecommendeddescription3.setSuperiority2(ys.get(1));
                    }
                    if (ys.size()>=3){
                        bkpRecommendeddescription3.setSuperiority1(ys.get(0));
                        bkpRecommendeddescription3.setSuperiority2(ys.get(1));
                        bkpRecommendeddescription3.setSuperiority3(ys.get(2));
                    }

                    if (ls.size()==1){
                        bkpRecommendeddescription3.setInferior1(ls.get(0));
                    }
                    if (ls.size()==2){
                        bkpRecommendeddescription3.setInferior1(ls.get(0));
                        bkpRecommendeddescription3.setInferior2(ls.get(1));
                    }
                    if (ls.size()>=3){
                        bkpRecommendeddescription3.setInferior1(ls.get(0));
                        bkpRecommendeddescription3.setInferior2(ls.get(1));
                        bkpRecommendeddescription3.setInferior3(ls.get(2));
                    }
                }

                bkpRecommendeddescription3.setProbabilityofwinning((String) map.get("probabilityofwinning"));
				bkpRecommendeddescription3.setHistoricalwinrate((String) map.get("historicalwinrate"));
				bkpRecommendeddescription3.setTeam(list12.get(i));
                bkpRecommendeddescription3.setHref((String) map.get("href"));
				bkpRecommendeddescription3.setNumberofgoals((String) map.get("numberofgoals"));
				bkpRecommendeddescriptionService.save(bkpRecommendeddescription3);
			}else{
				//修改
				Map map = (Map) recommendJsoup.jsoup(list.get(i));
				bkpRecommendeddescription2.setConceded((String) map.get("conceded"));
				bkpRecommendeddescription2.setLeague((String) map.get("league"));
                String[] yqdsz = (String[]) map.get("yqdsz");
                String[]  ms =  (String[]) map.get("ms");
                List<String> ys=new ArrayList<>();
                List<String> ls=new ArrayList<>();
                if (yqdsz!=null){
                    for (int j=0;j<yqdsz.length;j++){
                        if ("优".equals(yqdsz[j])){
                            ys.add(ms[j]);
                        }else if ("劣".equals(yqdsz[j])){
                            ls.add(ms[j]);
                        }
                    }
                    if (ys.size()==1){
                        bkpRecommendeddescription2.setSuperiority1(ys.get(0));
                        bkpRecommendeddescription2.setSuperiority2("");
                        bkpRecommendeddescription2.setSuperiority3("");
                    }
                    if (ys.size()==2){
                        bkpRecommendeddescription2.setSuperiority1(ys.get(0));
                        bkpRecommendeddescription2.setSuperiority2(ys.get(1));
                        bkpRecommendeddescription2.setSuperiority3("");
                    }
                    if (ys.size()>=3){
                        bkpRecommendeddescription2.setSuperiority1(ys.get(0));
                        bkpRecommendeddescription2.setSuperiority2(ys.get(1));
                        bkpRecommendeddescription2.setSuperiority3(ys.get(2));
                    }

                    if (ls.size()==1){
                        bkpRecommendeddescription2.setInferior1(ls.get(0));
                        bkpRecommendeddescription2.setInferior2("");
                        bkpRecommendeddescription2.setInferior3("");
                    }
                    if (ls.size()==2){
                        bkpRecommendeddescription2.setInferior1(ls.get(0));
                        bkpRecommendeddescription2.setInferior2(ls.get(1));
                        bkpRecommendeddescription2.setInferior3("");
                    }
                    if (ls.size()>=3){
                        bkpRecommendeddescription2.setInferior1(ls.get(0));
                        bkpRecommendeddescription2.setInferior2(ls.get(1));
                        bkpRecommendeddescription2.setInferior3(ls.get(2));
                    }
                }

				bkpRecommendeddescription2.setProbabilityofwinning((String) map.get("probabilityofwinning"));
				bkpRecommendeddescription2.setHistoricalwinrate((String) map.get("historicalwinrate"));
				bkpRecommendeddescription2.setTeam(list12.get(i));
                bkpRecommendeddescription2.setHref((String) map.get("href"));
				bkpRecommendeddescription2.setNumberofgoals((String) map.get("numberofgoals"));
				bkpRecommendeddescriptionService.update(bkpRecommendeddescription2);
			}

		}
		 for (int i=0;i<list12.size();i++){
             //查询是否已经有此数据
             BkpRecommendeddescription bkpRecommendeddescription2 = bkpRecommendeddescriptionService.findByTeam(list12.get(i));
             if (null==bkpRecommendeddescription2){
                 //新增
                 Map map = (Map) recommendJsoup.jsoup(list12.get(i));
                 BkpRecommendeddescription bkpRecommendeddescription3=new BkpRecommendeddescription();
                 bkpRecommendeddescription3.setConceded((String) map.get("conceded"));
                 String[] yqdsz = (String[]) map.get("yqdsz");
                 String[]  ms =  (String[]) map.get("ms");
                 List<String> ys=new ArrayList<>();
                 List<String> ls=new ArrayList<>();
                 if (yqdsz!=null){
                     for (int j=0;j<yqdsz.length;j++){
                         if ("优".equals(yqdsz[j])){
                             ys.add(ms[j]);
                         }else if ("劣".equals(yqdsz[j])){
                             ls.add(ms[j]);
                         }
                     }
                     if (ys.size()==1){
                         bkpRecommendeddescription2.setSuperiority1(ys.get(0));
                     }
                     if (ys.size()==2){
                         bkpRecommendeddescription2.setSuperiority1(ys.get(0));
                         bkpRecommendeddescription2.setSuperiority2(ys.get(1));
                     }
                     if (ys.size()>=3){
                         bkpRecommendeddescription2.setSuperiority1(ys.get(0));
                         bkpRecommendeddescription2.setSuperiority2(ys.get(1));
                         bkpRecommendeddescription2.setSuperiority3(ys.get(2));
                     }

                     if (ls.size()==1){
                         bkpRecommendeddescription2.setInferior1(ls.get(0));
                     }
                     if (ls.size()==2){
                         bkpRecommendeddescription2.setInferior1(ls.get(0));
                         bkpRecommendeddescription2.setInferior2(ls.get(1));
                     }
                     if (ls.size()>=3){
                         bkpRecommendeddescription2.setInferior1(ls.get(0));
                         bkpRecommendeddescription2.setInferior2(ls.get(1));
                         bkpRecommendeddescription2.setInferior3(ls.get(2));
                     }
                 }
                 bkpRecommendeddescription3.setLeague((String) map.get("league"));
                 bkpRecommendeddescription3.setProbabilityofwinning((String) map.get("probabilityofwinning"));
                 bkpRecommendeddescription3.setHistoricalwinrate((String) map.get("historicalwinrate"));
                 bkpRecommendeddescription3.setTeam(list12.get(i));
                 bkpRecommendeddescription3.setHref((String) map.get("href"));
                 bkpRecommendeddescription3.setNumberofgoals((String) map.get("numberofgoals"));
                 bkpRecommendeddescriptionService.save(bkpRecommendeddescription3);
             }else{
                 //修改
                 Map map = (Map) recommendJsoup.jsoup(list12.get(i));
                 bkpRecommendeddescription2.setConceded((String) map.get("conceded"));
                 bkpRecommendeddescription2.setLeague((String) map.get("league"));
                 String[] yqdsz = (String[]) map.get("yqdsz");
                 String[]  ms =  (String[]) map.get("ms");
                 List<String> ys=new ArrayList<>();
                 List<String> ls=new ArrayList<>();
                 if (yqdsz!=null){
                     for (int j=0;j<yqdsz.length;j++){
                         if ("优".equals(yqdsz[j])){
                             ys.add(ms[j]);
                         }else if ("劣".equals(yqdsz[j])){
                             ls.add(ms[j]);
                         }
                     }
                     if (ys.size()==1){
                         bkpRecommendeddescription2.setSuperiority1(ys.get(0));
                     }
                     if (ys.size()==2){
                         bkpRecommendeddescription2.setSuperiority1(ys.get(0));
                         bkpRecommendeddescription2.setSuperiority2(ys.get(1));
                     }
                     if (ys.size()>=3){
                         bkpRecommendeddescription2.setSuperiority1(ys.get(0));
                         bkpRecommendeddescription2.setSuperiority2(ys.get(1));
                         bkpRecommendeddescription2.setSuperiority3(ys.get(2));
                     }

                     if (ls.size()==1){
                         bkpRecommendeddescription2.setInferior1(ls.get(0));
                     }
                     if (ls.size()==2){
                         bkpRecommendeddescription2.setInferior1(ls.get(0));
                         bkpRecommendeddescription2.setInferior2(ls.get(1));
                     }
                     if (ls.size()>=3){
                         bkpRecommendeddescription2.setInferior1(ls.get(0));
                         bkpRecommendeddescription2.setInferior2(ls.get(1));
                         bkpRecommendeddescription2.setInferior3(ls.get(2));
                     }
                 }
                 bkpRecommendeddescription2.setProbabilityofwinning((String) map.get("probabilityofwinning"));
                 bkpRecommendeddescription2.setHistoricalwinrate((String) map.get("historicalwinrate"));
                 bkpRecommendeddescription2.setTeam(list12.get(i));
                 bkpRecommendeddescription2.setHref((String) map.get("href"));
                 bkpRecommendeddescription2.setNumberofgoals((String) map.get("numberofgoals"));
                 bkpRecommendeddescriptionService.update(bkpRecommendeddescription2);
             }
         }

	}

    /**
     * 保存数据
     */
    @RequiresPermissions("bkp:bkpRecommendeddescription:edit")
    @PostMapping(value = "save")
    @ResponseBody
    public String save(@Validated BkpRecommendeddescription bkpRecommendeddescription) {
        bkpRecommendeddescriptionService.save(bkpRecommendeddescription);
        return renderResult(Global.TRUE, text("保存推荐说明表成功！"));
    }

    /**
     * 删除数据
     */
    @RequiresPermissions("bkp:bkpRecommendeddescription:edit")
    @RequestMapping(value = "delete")
    @ResponseBody
    public String delete(BkpRecommendeddescription bkpRecommendeddescription) {
        bkpRecommendeddescriptionService.delete(bkpRecommendeddescription);
        return renderResult(Global.TRUE, text("删除推荐说明表成功！"));
    }

}