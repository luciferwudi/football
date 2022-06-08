package com.jeesite.modules.utils;


import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public class Recommend {
    public Object jsoup() throws Exception {

        // 建立与url中页面的连接
        String url = "https://guide.leisu.com/";
        Connection conn = Jsoup.connect(url).ignoreContentType(true);
        ;
        // 解析页面  userBet
        Document doc = conn.get();

        //标题
        Elements rows_lis = doc.select("li[class^=matches-item]");
        List<Map> maplist = new ArrayList<>();
        if (rows_lis.size() > 0) {
            for (int li = 0; li < rows_lis.size(); li++) {
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
                String visitingTeam = "";
                if (teamsa.length < 2) {
                    visitingTeam = "";
                } else {
                    visitingTeam = teamsa[1];
                }


//                System.out.println("比赛时间： " + time);
//                System.out.println("联赛： " + name);
//                System.out.println("比赛队： " + link);
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
                if (analysis_item.size() == 0) {
                    continue;
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

//
//                //优缺点
//                String adv = row.select("span[class=tip]").text();
//
//                String[] advs = Recommend.getAdv(adv);
//
//                //主队优缺点
//                String homeTeamAds = advs[0];
//                String[] homeTeamAds_arr = homeTeamAds.split(" ");
//
//                //客队优缺点
//                String visitingTeamAds = advs[1];
//                String[] visitingTeamAds_arr = visitingTeamAds.split(" ");

//                //主队优缺点描述
//                String[] homeTeamAdsDes = new String[homeTeamAds_arr.length];
//                for (int h = 0; h < homeTeamAdsDes.length; h++) {
//                    homeTeamAdsDes[h] = devs.get(h).text();
//                }

//                //客队优缺点描述
//                String[] visitingTeamAdsDes = new String[visitingTeamAds_arr.length];
//                for (int v = 0; v < visitingTeamAdsDes.length; v++) {
//                    visitingTeamAdsDes[v] = devs.get(homeTeamAdsDes.length + v).text();
//                }

                     Map map = new HashMap();
                     map.put(homeTeam, homeNums[0]);
                     maplist.add(map);

//                        map.put("league", name);
//                        map.put("yqdsz", homeTeamAds_arr);//优缺点
//                        map.put("ms", homeTeamAdsDes);
//                map.put("homeTeamwinning", homeNums[0]);//获胜概率
//                        map.put("historicalwinrate", homeNums[1]);//历史交战
//                        map.put("numberofgoals", homeNums[2]);//最近6场进球
//                        map.put("conceded", homeNums[3]);//最近6场比赛失球

                    Map map1 = new HashMap();
                    map1.put(visitingTeam, visitingNums[0]);
                    maplist.add(map1);

//                map.put("league", name);
//                map.put("yqdsz", visitingTeamAds_arr);//优缺点
//                map.put("ms", visitingTeamAdsDes);
//                map.put("visitingTeamwinning", visitingNums[0]);//获胜概率
//                map.put("historicalwinrate", visitingNums[1]);//历史交战
//                map.put("numberofgoals", visitingNums[2]);//最近6场进球
//                map.put("conceded", visitingNums[3]);//最近6场比赛失球


//                    System.out.println("主队：" + homeTeam);
//                    System.out.println("主队优缺点：" + Arrays.toString(homeTeamAds_arr));
//                    System.out.println("主队优缺点描述：" + Arrays.toString(homeTeamAdsDes));
//                    System.out.println("主队＿获胜概率: " + homeNums[0]);
//                    System.out.println("主队＿历史交锋胜率: " + homeNums[1]);
//                    System.out.println("主队＿最近6场比赛进球数: " + homeNums[2]);
//                    System.out.println("主队＿最近6场比赛失球数:　" + homeNums[3]);
//
//                    System.out.println("客队：" + visitingTeam);
//                    System.out.println("客队优缺点：" + Arrays.toString(visitingTeamAds_arr));
//                    System.out.println("客队优缺点描述：" + Arrays.toString(visitingTeamAdsDes));
//                    System.out.println("客队＿获胜概率: " + visitingNums[0]);
//                    System.out.println("客队＿历史交锋胜率: " + visitingNums[1]);
//                    System.out.println("客队＿最近6场比赛进球数: " + visitingNums[2]);
//                    System.out.println("客队＿最近6场比赛失球数:　" + visitingNums[3]);
//                    System.out.println("++++++++++++++++++++++++++++++++++++++++++++++++++=");

            }
        }
        return maplist;

    }
    public Object jsoup1() throws Exception {

        // 建立与url中页面的连接
        String url = "https://guide.leisu.com/";
        Connection conn = Jsoup.connect(url).ignoreContentType(true);
        ;
        // 解析页面  userBet
        Document doc = conn.get();

        //标题
        Elements rows_lis = doc.select("li[class^=matches-item]");
        List<Map> maplist = new ArrayList<>();
        if (rows_lis.size() > 0) {
            for (int li = 0; li < rows_lis.size(); li++) {
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
                String visitingTeam = "";
                if (teamsa.length < 2) {
                    visitingTeam = "";
                } else {
                    visitingTeam = teamsa[1];
                }


//                System.out.println("比赛时间： " + time);
//                System.out.println("联赛： " + name);
//                System.out.println("比赛队： " + link);
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
                if (analysis_item.size() == 0) {
                    continue;
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

//
//                //优缺点
//                String adv = row.select("span[class=tip]").text();
//
//                String[] advs = Recommend.getAdv(adv);
//
//                //主队优缺点
//                String homeTeamAds = advs[0];
//                String[] homeTeamAds_arr = homeTeamAds.split(" ");
//
//                //客队优缺点
//                String visitingTeamAds = advs[1];
//                String[] visitingTeamAds_arr = visitingTeamAds.split(" ");

//                //主队优缺点描述
//                String[] homeTeamAdsDes = new String[homeTeamAds_arr.length];
//                for (int h = 0; h < homeTeamAdsDes.length; h++) {
//                    homeTeamAdsDes[h] = devs.get(h).text();
//                }

//                //客队优缺点描述
//                String[] visitingTeamAdsDes = new String[visitingTeamAds_arr.length];
//                for (int v = 0; v < visitingTeamAdsDes.length; v++) {
//                    visitingTeamAdsDes[v] = devs.get(homeTeamAdsDes.length + v).text();
//                }

                Map map = new HashMap();
                map.put(homeTeam,homeTeam);
                map.put("Teamwinning",homeNums[0]);
                map.put("historicalwinrate",homeNums[1]);
                map.put("numberofgoals", homeNums[2]);//最近6场进球
                map.put("conceded", homeNums[3]);//最近6场比赛失球
                maplist.add(map);

//                        map.put("league", name);
//                        map.put("yqdsz", homeTeamAds_arr);//优缺点
//                        map.put("ms", homeTeamAdsDes);
//                map.put("homeTeamwinning", homeNums[0]);//获胜概率
//                        map.put("historicalwinrate", homeNums[1]);//历史交战
//                        map.put("numberofgoals", homeNums[2]);//最近6场进球
//                        map.put("conceded", homeNums[3]);//最近6场比赛失球

                Map map1 = new HashMap();
                map1.put(visitingTeam, visitingTeam);
                map1.put("Teamwinning", visitingNums[0]);
                map1.put("historicalwinrate", visitingNums[1]);
                map1.put("numberofgoals", visitingNums[2]);
                map1.put("conceded", visitingNums[3]);
                maplist.add(map1);

//                map.put("league", name);
//                map.put("yqdsz", visitingTeamAds_arr);//优缺点
//                map.put("ms", visitingTeamAdsDes);
//                map.put("visitingTeamwinning", visitingNums[0]);//获胜概率
//                map.put("historicalwinrate", visitingNums[1]);//历史交战
//                map.put("numberofgoals", visitingNums[2]);//最近6场进球
//                map.put("conceded", visitingNums[3]);//最近6场比赛失球


//                    System.out.println("主队：" + homeTeam);
//                    System.out.println("主队优缺点：" + Arrays.toString(homeTeamAds_arr));
//                    System.out.println("主队优缺点描述：" + Arrays.toString(homeTeamAdsDes));
//                    System.out.println("主队＿获胜概率: " + homeNums[0]);
//                    System.out.println("主队＿历史交锋胜率: " + homeNums[1]);
//                    System.out.println("主队＿最近6场比赛进球数: " + homeNums[2]);
//                    System.out.println("主队＿最近6场比赛失球数:　" + homeNums[3]);
//
//                    System.out.println("客队：" + visitingTeam);
//                    System.out.println("客队优缺点：" + Arrays.toString(visitingTeamAds_arr));
//                    System.out.println("客队优缺点描述：" + Arrays.toString(visitingTeamAdsDes));
//                    System.out.println("客队＿获胜概率: " + visitingNums[0]);
//                    System.out.println("客队＿历史交锋胜率: " + visitingNums[1]);
//                    System.out.println("客队＿最近6场比赛进球数: " + visitingNums[2]);
//                    System.out.println("客队＿最近6场比赛失球数:　" + visitingNums[3]);
//                    System.out.println("++++++++++++++++++++++++++++++++++++++++++++++++++=");

            }
        }
        return maplist;

    }

    /**
     * 获取主客队优劣分开
     *
     * @param str
     * @return
     */
    public static String[] getAdv(String str) {
        int id = str.indexOf("劣 优");
        if (id < 0) {
            id = str.length() / 2;
        } else {
            id = id + 2;
        }
        String homeleamAdv = str.substring(0, id).trim();
        String visitingTeamAdv = str.substring(id, str.length()).trim();
        String[] advs = {homeleamAdv, visitingTeamAdv};
        return advs;
    }
}