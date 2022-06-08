package com.jeesite.modules.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.jeesite.modules.vo.Opp;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class DoGetData002 {


    public Object doGetData002() throws IOException {
        CloseableHttpClient httpclient = HttpClients.createDefault();
        List<Opp> list = new ArrayList<Opp>();
        HttpGet httpGet1 = new HttpGet("https://i.sporttery.cn/odds_calculator/get_proportion?i_format=json&pool[]=had&pool[]=hhad");
        CloseableHttpResponse response1 = null;
        List<Integer> integerList = new ArrayList<>();
        response1 = httpclient.execute(httpGet1); //footb_group(
        if (response1.getStatusLine().getStatusCode() == 200) {
            //请求体内容
            String content = EntityUtils.toString(response1.getEntity(), "UTF-8");
            Map map1 = JSONObject.parseObject(content, Map.class);//解析json
            String data = map1.get("data").toString();
            Map map2 = JSONObject.parseObject(data, Map.class);
            Iterator iterator = map2.entrySet().iterator();
            while (iterator.hasNext()) {
                Map.Entry entry = (Map.Entry) iterator.next();
                Opp opp = JSON.parseObject("" + entry.getValue(), Opp.class);
                System.out.println(opp.toString());
                list.add(opp);
            }
        }
        return list;
    }


}
