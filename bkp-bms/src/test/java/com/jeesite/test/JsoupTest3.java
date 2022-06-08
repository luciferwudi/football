package com.jeesite.test;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.junit.Test;

import java.io.*;
import java.util.Iterator;

public class JsoupTest3 {

    @Test
    public  void ceshi() throws  Exception{

        String url="https://liansai.500.com/team/7389/";
        String fileName="f://html//7389.txt";
        //保存html内容
        JsoupTest3.saveHtml(url,fileName);

        System.out.println("==============================================================");
        //读取html内容
        String content = readHtml(fileName);
        System.out.println(content);
    }

    /**
     * 保存页面
     * @param url
     * @param filename
     * @throws Exception
     */
    public static void saveHtml(String url,String filename) throws Exception {
        Connection conn = Jsoup.connect(url); // 建立与url中页面的连接
        Document doc = conn.get(); // 解析页面
        doc.select("div[class=header header2]").remove();
        doc.select("div[class=footer]").remove();
        doc.select("div[class=topbar]").remove();
        doc.select("div[class=div_m]").remove();
        doc.select("div[class=nav]").remove();
        doc.select("div[class=lcrumbs]").remove();
        doc.select("div[class=footer-keywords-wrap]").remove();
        doc.select("a[href!=#]").removeAttr("href");
        Elements elements=doc.select("a[href!=#]");

        FileOutputStream fos = new FileOutputStream(filename, false);
        fos.write(doc.html().getBytes());
        fos.close();
    }

    /**
     * 读取text文件内容
     * @param fileName
     * @return
     * @throws Exception
     */
    public static String readHtml(String fileName) throws Exception {
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



    }
