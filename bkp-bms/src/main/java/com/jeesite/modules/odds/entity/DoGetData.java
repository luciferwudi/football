package com.jeesite.modules.odds.entity;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.jeesite.modules.teamdata.entity.Teamdata;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class DoGetData {

    public Map getDatas(List<Teamdata> list, Map<String, Multiallup> hhad_multiallupMap, Map<String, Multiallup> had_multiallupMap) throws Exception{
        // 创建Httpclient对象
        CloseableHttpClient httpclient = HttpClients.createDefault();
        Map map123=new HashMap();
        // 创建http GET请求
        HttpGet httpGet = new HttpGet("https://i.sporttery.cn/odds_calculator/get_odds?i_format=json&i_callback=getData&poolcode[]=hhad&poolcode[]=had&_=1595844919402");
        CloseableHttpResponse response = null;
        try {
            // 执行请求
            response = httpclient.execute(httpGet);
            // 判断返回状态是否为200
            if (response.getStatusLine().getStatusCode() == 200) {
                //请求体内容
                String content = EntityUtils.toString(response.getEntity(), "UTF-8");
                content=content.replace("getData(","");
                content=content.replace(");","");
                System.out.println("这个是用JSON类来解析JSON字符串!!!");
                Map map1 = JSONObject.parseObject(content, Map.class);

                String data = map1.get("data").toString();
                String status = map1.get("status").toString();

                //System.out.println(data);
                if(data!=null) {
                    Map map2 = JSONObject.parseObject(data, Map.class);
                    Iterator iterator = map2.entrySet().iterator();
                    while (iterator.hasNext()) {
                        Map.Entry entry = (Map.Entry) iterator.next();
                        // System.out.println("key :" + entry.getKey() + "   value: " + entry.getValue());
                        Teamdata matchEntity = JSON.parseObject("" + entry.getValue(), Teamdata.class);
                        list.add(matchEntity);
                    }
                }
                System.out.println(list);
                if(status!=null) {
                    StatusVo statusVo =new StatusVo();


                    Map map2 = JSONObject.parseObject(status, Map.class);
                    statusVo.setMaxcount(""+map2.get("maxcount"));
                    statusVo.setLast_updated(map2.get("last_updated")+"");
                    Map allupMap = JSONObject.parseObject(""+map2.get("allup"), Map.class);
                    String HHAD=""+allupMap.get("HHAD");
                    String HAD=""+allupMap.get("HAD");

                    if(HHAD!=null) {
                        Map map3 = JSONObject.parseObject(HHAD, Map.class);
                        Iterator iterator = map3.entrySet().iterator();
                        while (iterator.hasNext()) {
                            Map.Entry entry = (Map.Entry) iterator.next();
                            // System.out.println("key :" + entry.getKey() + "   value: " + entry.getValue());
                            Multiallup hhadMatchEntity = JSON.parseObject("" + entry.getValue(), Multiallup.class);
                            hhad_multiallupMap.put(""+entry.getKey(),hhadMatchEntity);
                        }
                    }

                    if(HAD!=null) {
                        Map map3 = JSONObject.parseObject(HAD, Map.class);
                        Iterator iterator = map3.entrySet().iterator();
                        while (iterator.hasNext()) {
                            Map.Entry entry = (Map.Entry) iterator.next();
                            // System.out.println("key :" + entry.getKey() + "   value: " + entry.getValue());
                            Multiallup hhadMatchEntity = JSON.parseObject("" + entry.getValue(), Multiallup.class);
                            had_multiallupMap.put(""+entry.getKey(),hhadMatchEntity);
                        }
                    }
//                    System.out.println(hhad_multiallupMap.toString());
//                    System.out.println(had_multiallupMap.toString());
                    map123.put("data",list);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (response != null) {
                response.close();
            }
            //相当于关闭浏览器
            httpclient.close();
        }
        return map123;
    }
}
