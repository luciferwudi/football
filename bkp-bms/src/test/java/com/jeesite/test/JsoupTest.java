package com.jeesite.test;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import static sun.util.logging.LoggingSupport.log;

public class JsoupTest {
    public static void main(String[] args) throws  Exception{
        String htmltest="https://zx.500.com/jczq/kaijiang.php?playid=2&d=2020-08-16";
        JsoupTest.demo1(htmltest);
    }

    public static void demo1(String url) throws Exception {
        Connection conn = Jsoup.connect(url); // 建立与url中页面的连接
        Document doc = conn.get(); // 解析页面
            Elements rows = doc.select("table[class=ld_table]").get(0).select("tr");
            if (rows.size() == 1) {
                System.out.println("没有结果");
        }else {
            System.out.println("--------------------------- 查询结果 ---------------------------");
            Element row = rows.get(1);
            System.out.println("次序:" + row.select("td").get(0).text());
            System.out.println("联赛:" + row.select("td").get(1).text());
            System.out.println("比赛时间:" + row.select("td").get(2).text());
            System.out.println("主队:" + row.select("td").get(3).text());
            System.out.println("让球:" + row.select("td").get(4).text());
            System.out.println("客队:" + row.select("td").get(5).text());
            System.out.println("比分:" + row.select("td").get(6).text());
            System.out.println("赛果:" + row.select("td").get(7).text());
            System.out.println("-----------------------------------------------------------------");
        }
       /* Document doc = Jsoup.connect("http://www.ifeng.com/").get();
        System.out.println(doc.title());
        Elements newsHeadlines = doc.select("#mp-itn b a");
        for (Element headline : newsHeadlines) {
            log("%s\n\t%s",
                    headline.attr("title"), headline.absUrl("href"));
        }*/

    }


}
