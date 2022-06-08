package com.jeesite.modules.utils;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Attributes;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Repository;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class injuries {

    //联赛的lid
    public List league() throws IOException {
        List<String> list=new ArrayList<>();
         // 建立与url中页面的连接
        String url="https://www.iuliao.com/info/injury";
        Connection conn = Jsoup.connect(url);
        //https://www.iuliao.com/info/injury/39
        // 解析页面  userBet
        Document doc = conn.get();
        //联赛
        Elements league=doc.select("li[class=league]");
        for (int i=0;i<league.size();i++){
            Element li_row = league.get(i);
            Attributes datalid=li_row.attributes();
            String lid = datalid.get("data-lid");
            list.add(url+"/"+lid);
        }

      return list;
    }


    public  List jsoup01(String url) throws Exception {
        // 建立与url中页面的连接
        Connection conn = Jsoup.connect(url);
        // 解析页面  userBet
        Document doc = conn.get();
        Elements rows_lis = doc.select("div[class=swipe]");
        List<Map> maplist=new ArrayList<>();
        for (int i=0;i<rows_lis.size();i++){
            Element li_row = rows_lis.get(i);
            //球队名字
            String text=li_row.select("div[class=team-info]").text();
            String[] teamInfo=text.split(" ");
            String team=teamInfo[0];
            System.out.println(team);
            Elements infoList=li_row.select("div[class=injury-info]");
            System.out.println(infoList.size());
            for (int j=0;j<infoList.size();j++){
                Map map=new HashMap();
                Element teaminfo = infoList.get(j);
                //球员名字
                String text1=teaminfo.select("div[class=player pull-left]").text();
                String[] nameinfo=text1.split(" . ");
                String name=nameinfo[nameinfo.length-1];

                //球员的身价
                String value=teaminfo.select("div[class=value pull-left]").text();

                //球员的位置
                String position=teaminfo.select("div[class=position pull-left]").text();

                //伤停原因
                String cause=teaminfo.select("div[class=cause pull-left]").text();

                //预计复出时间
                String time=teaminfo.select("div[class=time pull-left]").text();

                //影响赛事
                String affect=teaminfo.select("div[class=affect pull-left none]").text();

                map.put("team",team);
                map.put("name",name);
                map.put("value",value);
                map.put("position",position);
                map.put("cause",cause);
                map.put("time",time);
                map.put("affect",affect);
                maplist.add(map);
                System.out.println("球员名字"+name);
                System.out.println("球员身价"+value);
                System.out.println("球员位置"+position);
                System.out.println("球员伤停原因"+cause);
                System.out.println("预计复出时间"+time);
                System.out.println("影响赛事"+affect);

                System.out.println();
            }
        }
        return maplist;


    }
}
