package com.jeesite.modules.utils;

import com.jeesite.common.lang.DateUtils;
import com.jeesite.common.shiro.realms.B;
import com.jeesite.modules.bkp.entity.BkpLeis;
import com.jeesite.modules.bkp.service.BkpLeisService;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

@Repository
public class RecommendJsoup {
    @Autowired
    private BkpLeisService bkpLeisService;
    public  Object jsoup(String teamName) throws Exception {
        Map map=new HashMap<>();
        // 建立与url中页面的连接
        String url="https://guide.leisu.com/";
        Connection conn = Jsoup.connect(url).ignoreContentType(true);;
        // 解析页面  userBet
        Document doc = conn.get();

        //标题
        Elements rows_lis = doc.select("li[class^=matches-item]");
        if(rows_lis.size()>0) {
            for(int li=0;li<rows_lis.size();li++) {
                Element li_row = rows_lis.get(li);
                //标题
                Element rows_titles = li_row.select("div[class=data-row]").get(0);
                //比赛时间
                String time = rows_titles.select("div[class=time]").text();
                //联赛
                String name = rows_titles.select("span[class=name]").text();
                //球队
                String link = rows_titles.select("a[class=link]").text();

                String[] teamsa = link.split(" ");
                //主队
                String homeTeam = teamsa[0];
                //客队
                String visitingTeam="";
                if (teamsa.length<2){
                   visitingTeam="";
                }else {
                    visitingTeam= teamsa[1];
                }


                  if (teamName.equals("仁川联")){
                      teamName+="队";
                  }
//                System.out.println("比赛时间： " + time);
//                System.out.println("联赛： " + name);
//                System.out.println("比赛队： " + link);
                if (teamName.equals(homeTeam) || teamName.equals(visitingTeam)) { //减少消耗
                    //获胜参考数据
                    Elements rows_num = li_row.select("div[class=bar]");
                    String[] homeNums = null;
                    String[] visitingNums = null;
                    if (rows_num.size() > 0) {
                        //主队获胜数组
                        String homeNum = rows_num.select("span[class=num1]").text();
                        homeNums = homeNum.split(" ");
                        //System.out.println("获胜概率"+num1);
                        //客队获胜数组
                        String visitingNum = rows_num.select("span[class=num2]").text();
                        visitingNums = visitingNum.split(" ");
                    }

                    //主客队优势
                    Elements analysis_item = li_row.select("div[class=analysis-item]");
                    if (analysis_item.size()==0){
                        return "";
                    }
                    Element home_analysis = analysis_item.get(0);
                    //描述对象
                    Elements devs = null;
                    if (home_analysis != null) {
                        //Elements advs = li_row.select("span[class=tip]");
                        devs = li_row.select("ul li span[class=text]");
                    }


                    Elements rows = li_row.select("div[class=children analysis]");

                    Element row = rows.get(0);


                    //优缺点
                    String adv = row.select("span[class=tip]").text();

                    String[] advs = RecommendJsoup.getAdv(adv);

                    //主队优缺点
                    String homeTeamAds = advs[0];
                    String[] homeTeamAds_arr = homeTeamAds.split(" ");

                    //客队优缺点
                    String visitingTeamAds = advs[1];
                    String[] visitingTeamAds_arr = visitingTeamAds.split(" ");

                    //主队优缺点描述
                    String[] homeTeamAdsDes = new String[homeTeamAds_arr.length];
                    for (int h = 0; h < homeTeamAdsDes.length; h++) {
                        homeTeamAdsDes[h] = devs.get(h).text();
                    }

                    //客队优缺点描述
                    String[] visitingTeamAdsDes = new String[visitingTeamAds_arr.length];
                    for (int v = 0; v < visitingTeamAdsDes.length; v++) {
                        visitingTeamAdsDes[v] = devs.get(homeTeamAdsDes.length + v).text();
                    }
                    Elements hreflist=li_row.select("a[class=more]");
                    String href=hreflist.get(0).attr("href");
                    if (teamName.equals(homeTeam)) {
                        map.put("team", homeTeam);
                        map.put("league", name);
                        map.put("yqdsz", homeTeamAds_arr);//优缺点
                        map.put("ms", homeTeamAdsDes);
                        map.put("probabilityofwinning", homeNums[0]);//获胜概率
                        map.put("historicalwinrate", homeNums[1]);//历史交战
                        map.put("numberofgoals", homeNums[2]);//最近6场进球
                        map.put("conceded", homeNums[3]);//最近6场比赛失球
                        map.put("href",href);
                        return map;
                    } else if (teamName.equals(visitingTeam)) {
                        map.put("team", visitingTeam);
                        map.put("league", name);
                        map.put("yqdsz", visitingTeamAds_arr);//优缺点
                        map.put("ms", visitingTeamAdsDes);
                        map.put("probabilityofwinning", visitingNums[0]);//获胜概率
                        map.put("historicalwinrate", visitingNums[1]);//历史交战
                        map.put("numberofgoals", visitingNums[2]);//最近6场进球
                        map.put("conceded", visitingNums[3]);//最近6场比赛失球
                        map.put("href",href);
                        return map;
                    }

                    System.out.println("主队：" + homeTeam);
                    System.out.println("主队优缺点：" + Arrays.toString(homeTeamAds_arr));
                    System.out.println("主队优缺点描述：" + Arrays.toString(homeTeamAdsDes));
                    System.out.println("主队＿获胜概率: " + homeNums[0]);
                    System.out.println("主队＿历史交锋胜率: " + homeNums[1]);
                    System.out.println("主队＿最近6场比赛进球数: " + homeNums[2]);
                    System.out.println("主队＿最近6场比赛失球数:　" + homeNums[3]);

                    System.out.println("客队：" + visitingTeam);
                    System.out.println("客队优缺点：" + Arrays.toString(visitingTeamAds_arr));
                    System.out.println("客队优缺点描述：" + Arrays.toString(visitingTeamAdsDes));
                    System.out.println("客队＿获胜概率: " + visitingNums[0]);
                    System.out.println("客队＿历史交锋胜率: " + visitingNums[1]);
                    System.out.println("客队＿最近6场比赛进球数: " + visitingNums[2]);
                    System.out.println("客队＿最近6场比赛失球数:　" + visitingNums[3]);
                    System.out.println("++++++++++++++++++++++++++++++++++++++++++++++++++=");
                }
            }
        }
        return map;

    }



    public void insertpbkleis() throws Exception{
        String url="https://guide.leisu.com/";
        // 建立与url中页面的连接
        Connection conn = Jsoup.connect(url);
        // 解析页面  userBet
        Document doc = conn.get();

        //标题
        Elements rows_lis = doc.select("li[class^=matches-item]");
        if(rows_lis.size()>0) {
            for(int li=0;li<rows_lis.size();li++) {
                Element li_row = rows_lis.get(li);
                //标题
                Element rows_titles = li_row.select("div[class=data-row]").get(0);
                //比赛时间
                String time = rows_titles.select("div[class=time]").text();
                //联赛
                String name = rows_titles.select("span[class=name]").text();
                //球队
                String link = rows_titles.select("a[class=link]").text();

                //href
                Elements hreflist=li_row.select("a[class=more]");
                String href=hreflist.get(0).attr("href");

                Connection conn1 = Jsoup.connect(href);
                // 解析页面
                Document doc1 = conn1.get();
                Elements table=doc1.select("table[class=information]");
                Elements tds=table.get(0).select("td");
                String kdyl1="";
                String kdyl2="";
                String kdyl3="";

                String kdbl1="";
                String kdbl2="";
                String kdbl3="";

                String zdyl1="";
                String zdyl2="";
                String zdyl3="";

                String zdbl1="";
                String zdbl2="";
                String zdbl3="";

                String zlqb1="";
                String zlqb2="";
                String zlqb3="";
                for (int i=0;i<tds.size();i++){
                    if (i==1){
                        Element element = tds.get(i);
                        Elements select = element.select("div[class=children middle]");
                        Elements lilist=select.get(0).select("li");
                        for (int j=0;j<lilist.size();j++){
                            if (j==0){
                                zlqb1=lilist.get(j).text();
                            }
                            if (j==1){
                                zlqb2=lilist.get(j).text();
                            }
                            if (j==2){
                                zlqb3=lilist.get(j).text();
                            }
                            if (j>2){
                                break;
                            }
                        }
                    }else if (i==0){
                        Element element = tds.get(i); //0为主队的情报
                        Elements select = element.select("div[class=children good]");
                        Elements lilist=select.get(0).select("li");
                        for (int j=0;j<lilist.size();j++){
                            if (j==0){
                                zdyl1=lilist.get(j).text();
                            }
                            if (j==1){
                                zdyl2=lilist.get(j).text();
                            }
                            if (j==2){
                                zdyl3=lilist.get(j).text();
                            }
                            if (j>2){
                                break;
                            }
                        }
                        Elements bad = element.select("div[class=children harmful]");
                        Elements badlist=bad.get(0).select("li");
                        for (int j=0;j<badlist.size();j++){
                            if (j==0){
                                zdbl1=badlist.get(j).text();
                            }
                            if (j==1){
                                zdbl2=badlist.get(j).text();
                            }
                            if (j==2){
                                zdbl3=badlist.get(j).text();
                            }
                            if (j>2){
                                break;
                            }
                        }
                    }else if (i==2){
                        Element element = tds.get(i); //2为客队的情报
                        Elements select = element.select("div[class=children good]");
                        Elements lilist=select.get(0).select("li");
                        for (int j=0;j<lilist.size();j++){
                            if (j==0){
                                kdyl1=lilist.get(j).text();
                            }
                            if (j==1){
                                kdyl2=lilist.get(j).text();
                            }
                            if (j==2){
                                kdyl3=lilist.get(j).text();
                            }
                            if (j>2){
                                break;
                            }
                        }
                        Elements bad = element.select("div[class=children harmful]");
                        Elements badlist=bad.get(0).select("li");
                        for (int j=0;j<badlist.size();j++){
                            if (j==0){
                                kdbl1=badlist.get(j).text();
                            }
                            if (j==1){
                                kdbl2=badlist.get(j).text();
                            }
                            if (j==2){
                                kdbl3=badlist.get(j).text();
                            }
                            if (j>2){
                                break;
                            }
                        }
                    }

                }

                String[] teamsa = link.split(" ");
                //主队
                String homeTeam = teamsa[0];
                //客队
                String visitingTeam = teamsa[1];

                System.out.println("比赛时间：" + time.split(" ")[1]);
                System.out.println("联赛： " + name);
                System.out.println("比赛队： " + link);

                //获胜参考数据
                Elements rows_num=li_row.select("div[class=bar]");
                String[] homeNums=null;
                String[] visitingNums=null;
                if(rows_num.size()>0){
                    //主队获胜数组
                    String homeNum = rows_num.select("span[class=num1]").text();
                    homeNums=homeNum.split(" ");
                    //System.out.println("获胜概率"+num1);
                    //客队获胜数组
                    String  visitingNum = rows_num.select("span[class=num2]").text();
                    visitingNums=visitingNum.split(" ");
                }
                //先查询是否已经有了
                BkpLeis bkpLeis=new BkpLeis();
                bkpLeis.setHometeam(homeTeam);
                bkpLeis.setTime(time.split(" ")[1]);
                bkpLeis.setLeague(name);
                BkpLeis bkpLeis1 = bkpLeisService.getentiy(bkpLeis);
                if (null==bkpLeis1){
                    BkpLeis bkpLeis2=new BkpLeis();
                    bkpLeis2.setLeague(name);
                    String yyyy = DateUtils.getDate("yyyy");
                    bkpLeis2.setTime(yyyy+"-"+time.split(" ")[1]);
                    bkpLeis2.setHometeam(homeTeam);
                    bkpLeis2.setHomeconceded(homeNums[3]);
                    bkpLeis2.setHomeprobabilityofwinning(homeNums[0]);
                    bkpLeis2.setHomehistoricalwinrate(homeNums[1]);
                    bkpLeis2.setHomenumberofgoals(homeNums[2]);
                    bkpLeis2.setZdbl1(zdbl1);
                    bkpLeis2.setZdbl2(zdbl2);
                    bkpLeis2.setZdbl3(zdbl3);
                    bkpLeis2.setZdyl1(zdyl1);
                    bkpLeis2.setZdyl2(zdyl2);
                    bkpLeis2.setZdyl3(zdyl3);

                    bkpLeis2.setZlqb1(zlqb1);
                    bkpLeis2.setZlqb2(zlqb2);
                    bkpLeis2.setZlqb3(zlqb3);

                    bkpLeis2.setVistteam(visitingTeam);
                    bkpLeis2.setVistprobabilityofwinning(visitingNums[0]);
                    bkpLeis2.setVisthistoricalwinrate(visitingNums[1]);
                    bkpLeis2.setVistnumberofgoals(visitingNums[2]);
                    bkpLeis2.setVistconceded(visitingNums[3]);
                    bkpLeis2.setKdbl1(kdbl1);
                    bkpLeis2.setKdbl2(kdbl2);
                    bkpLeis2.setKdbl3(kdbl3);
                    bkpLeis2.setKdyl1(kdyl1);
                    bkpLeis2.setKdyl2(kdyl2);
                    bkpLeis2.setKdyl3(kdyl3);
                    bkpLeisService.save(bkpLeis2);
                }else { //修改
                    bkpLeis1.setHomeconceded(homeNums[3]);
                    bkpLeis1.setHomeprobabilityofwinning(homeNums[0]);
                    bkpLeis1.setHomehistoricalwinrate(homeNums[1]);
                    bkpLeis1.setHomenumberofgoals(homeNums[2]);

                    bkpLeis1.setVistprobabilityofwinning(visitingNums[0]);
                    bkpLeis1.setVisthistoricalwinrate(visitingNums[1]);
                    bkpLeis1.setVistnumberofgoals(visitingNums[2]);
                    bkpLeis1.setVistconceded(visitingNums[3]);
                    bkpLeis1.setZdbl1(zdbl1);
                    bkpLeis1.setZdbl2(zdbl2);
                    bkpLeis1.setZdbl3(zdbl3);
                    bkpLeis1.setZdyl1(zdyl1);
                    bkpLeis1.setZdyl2(zdyl2);
                    bkpLeis1.setZdyl3(zdyl3);
                    bkpLeis1.setKdbl1(kdbl1);
                    bkpLeis1.setKdbl2(kdbl2);
                    bkpLeis1.setKdbl3(kdbl3);
                    bkpLeis1.setKdyl1(kdyl1);
                    bkpLeis1.setKdyl2(kdyl2);
                    bkpLeis1.setKdyl3(kdyl3);
                    bkpLeis1.setZlqb1(zlqb1);
                    bkpLeis1.setZlqb2(zlqb2);
                    bkpLeis1.setZlqb3(zlqb3);
                    bkpLeisService.update(bkpLeis1);
                }

//                System.out.println("主队："+homeTeam);
//                System.out.println("主队＿获胜概率: "+homeNums[0]);
//
//                System.out.println("主队＿历史交锋胜率: "+homeNums[1]);
//                System.out.println("主队＿最近6场比赛进球数: "+homeNums[2]);
//                System.out.println("主队＿最近6场比赛失球数:　"+homeNums[3]);
//
//                System.out.println("客队："+visitingTeam);
//                System.out.println("客队＿获胜概率: "+visitingNums[0]);
//                System.out.println("客队＿历史交锋胜率: "+visitingNums[1]);
//                System.out.println("客队＿最近6场比赛进球数: "+visitingNums[2]);
//                System.out.println("客队＿最近6场比赛失球数:　"+visitingNums[3]);
//                System.out.println("++++++++++++++++++++++++++++++++++++++++++++++++++=");


            }
        }
    }

    /**
     *   获取主客队优劣分开
     * @param str
     * @return
     */
    public static String[] getAdv(String str){
        int id=str.indexOf("劣 优");
        if(id<0){
            id=str.length()/2;
        }else{
            id=id+2;
        }
        String homeleamAdv=str.substring(0,id).trim();
        String visitingTeamAdv=str.substring(id,str.length()).trim();
        String[] advs={homeleamAdv,visitingTeamAdv};
        return advs;
    }
}