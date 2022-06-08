/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.bkp.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.jeesite.common.lang.DateUtils;
import com.jeesite.modules.utils.RecommendJsoup;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.hibernate.validator.constraints.Length;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jeesite.common.config.Global;
import com.jeesite.common.entity.Page;
import com.jeesite.common.web.BaseController;
import com.jeesite.modules.bkp.entity.BkpLeis;
import com.jeesite.modules.bkp.service.BkpLeisService;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * bkp_leisController
 * @author Lucifer
 * @version 2020-10-13
 */
@Controller
@RequestMapping(value = "${adminPath}/bkp/bkpLeis")
@Api(tags = "雷速信息")
public class BkpLeisController extends BaseController {

	@Autowired
	private BkpLeisService bkpLeisService;
	@Autowired
	private RecommendJsoup recommendJsoup;
	
	/**
	 * 获取数据
	 */
	@ModelAttribute
	public BkpLeis get(String id, boolean isNewRecord) {
		return bkpLeisService.get(id, isNewRecord);
	}
	
	/**
	 * 查询列表
	 */
	@ApiOperation(value = "查询列表")
	@RequiresPermissions("bkp:bkpLeis:view")
	@RequestMapping(value = {"list", ""})
	public String list(BkpLeis bkpLeis, Model model) {
		model.addAttribute("bkpLeis", bkpLeis);
		return "modules/bkp/bkpLeisList";
	}


    /**
     * 查询比赛结果进行填充
     */
    public void checkTheMatchResult() throws IOException {

        //先查询分组 按时间进行分组 所有没有比赛结果的时间
		List<String> timeList=bkpLeisService.selectByGroup(); //时间
        for (String s:timeList){
        	//查询到了  该天   没有比赛结果的场次
        	List<BkpLeis> list=bkpLeisService.selectAllResult(s);
            List<Map> mapslist= (List<Map>) lsdata(s);
            for (BkpLeis g:list){
                String result=resultcx(g,mapslist);
				if (result.equals("")){
					g.setResult("无效结果");
				}else {
					g.setResult(result);
				}
				bkpLeisService.update(g);
			}
		}

    }

    /*
    根据联赛名字以及主队,客队可以查询到对应的数据
     */
    public String  resultcx(BkpLeis bkpLeis,List<Map> mapslist){
    	for (Map s:mapslist){
			String hometeam = bkpLeis.getHometeam();
			String league = bkpLeis.getLeague();
			String vistteam = bkpLeis.getVistteam();
			if (s.get("league").equals(league)&&s.get("homeTeam").equals(hometeam)&&s.get("vistTeam").equals(vistteam)){
				return s.get("result").toString();
			}
		}
    	return "";
	}


    public Object lsdata(String time) throws IOException {
    	List<Map> list=new ArrayList<>();
		time= time.replaceAll("-", "");
		String url="https://live.leisu.com/wanchang?date="+time;

		Connection conn = Jsoup.connect(url); // 建立与url中页面的连接
		// 解析页面
		Document doc = conn.get();
		Elements ul = doc.select("ul[class=layout-grid-list]");
		Elements li = ul.get(1).select("li");
		for (int i=0;i<li.size();i++){
			Map map=new HashMap();
			Element select = li.get(i);
			Elements span=select.select("span[class=lab-events]").select("a");
			Elements homeTeam=select.select("span[class=lab-team-home]").select("a");
			Elements vistTeam=select.select("span[class=lab-team-away]").select("a");
			Elements jg=select.select("span[class=lab-bet-odds]");
			if (span.size()>0){
				System.out.println("联赛:"+span.get(0).text());
				System.out.print("主队"+homeTeam.get(0).text()+"     ");
				System.out.print("客队"+vistTeam.get(0).text());
				System.out.println("结果为:"+jg.get(0).text());
				map.put("league",span.get(0).text());
				map.put("homeTeam",homeTeam.get(0).text());
				map.put("vistTeam",vistTeam.get(0).text());
				map.put("result",jg.get(0).text());
				list.add(map);
			}
		}
		return list;
	}
	
	/**
	 * 查询列表数据
	 */
	@RequiresPermissions("bkp:bkpLeis:view")
	@RequestMapping(value = "listData")
	@ResponseBody
	public Page<BkpLeis> listData(BkpLeis bkpLeis, HttpServletRequest request, HttpServletResponse response) throws Exception {
		bkpLeis.setPage(new Page<>(request, response));
//		recommendJsoup.insertpbkleis();
//		checkTheMatchResult();
		Page<BkpLeis> page = bkpLeisService.findPage(bkpLeis);
		return page;
	}

	/**
	 * 查看编辑表单
	 */
	@RequiresPermissions("bkp:bkpLeis:view")
	@RequestMapping(value = "form")
	public String form(BkpLeis bkpLeis, Model model) {
		model.addAttribute("bkpLeis", bkpLeis);
		return "modules/bkp/bkpLeisForm";
	}

	/**
	 * 保存数据
	 */
	@RequiresPermissions("bkp:bkpLeis:edit")
	@PostMapping(value = "save")
	@ResponseBody
	public String save(@Validated BkpLeis bkpLeis) {
		bkpLeisService.save(bkpLeis);
		return renderResult(Global.TRUE, text("保存bkp_leis成功！"));
	}
	
	/**
	 * 删除数据
	 */
	@RequiresPermissions("bkp:bkpLeis:edit")
	@RequestMapping(value = "delete")
	@ResponseBody
	public String delete(BkpLeis bkpLeis) {
		bkpLeisService.delete(bkpLeis);
		return renderResult(Global.TRUE, text("删除bkp_leis成功！"));
	}
	
}