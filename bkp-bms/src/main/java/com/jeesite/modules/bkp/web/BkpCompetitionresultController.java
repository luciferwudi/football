/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.bkp.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import ch.qos.logback.core.net.SyslogOutputStream;
import com.jeesite.modules.sys.entity.User;
import com.jeesite.modules.sys.utils.UserUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import com.jeesite.common.config.Global;
import com.jeesite.common.entity.Page;
import com.jeesite.common.web.BaseController;
import com.jeesite.modules.bkp.entity.BkpCompetitionresult;
import com.jeesite.modules.bkp.service.BkpCompetitionresultService;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 比赛结果推荐表Controller
 *
 * @author Lucifer
 * @version 2020-08-27
 */
@Controller
@RequestMapping(value = "${adminPath}/bkp/bkpCompetitionresult")
public class BkpCompetitionresultController extends BaseController {

    @Autowired
    private BkpCompetitionresultService bkpCompetitionresultService;

    /**
     * 获取数据
     */
    @ModelAttribute
    public BkpCompetitionresult get(String id, boolean isNewRecord) {
        return bkpCompetitionresultService.get(id, isNewRecord);
    }

    /**
     * 查询列表
     */
    @RequiresPermissions("bkp:bkpCompetitionresult:view")
    @RequestMapping(value = {"list", ""})
    public String list(BkpCompetitionresult bkpCompetitionresult, Model model) {
        model.addAttribute("bkpCompetitionresult", bkpCompetitionresult);
        return "modules/bkp/bkpCompetitionresultList";
    }

    /**
     * 查询列表数据
     */
    @RequiresPermissions("bkp:bkpCompetitionresult:view")
    @RequestMapping(value = "listData")
    @ResponseBody
    public Page<BkpCompetitionresult> listData(BkpCompetitionresult bkpCompetitionresult, HttpServletRequest request, HttpServletResponse response) {
        bkpCompetitionresult.setPage(new Page<>(request, response));
        deleteByCombination();
        Page<BkpCompetitionresult> page = bkpCompetitionresultService.findPage(bkpCompetitionresult);
        return page;
    }

    @GetMapping(value = "deleteByCombination")
    public void deleteByCombination() {
        bkpCompetitionresultService.deleteByCombination();
    }

    @PostMapping(value = "selectCombination")
    @ResponseBody
    public Object selectCombination(@RequestParam String howToPlay) {
        return bkpCompetitionresultService.selectCombination(howToPlay);
    }

    @RequestMapping(value = "saveResult", method = RequestMethod.POST)
    public void saveResult(BkpCompetitionresult bkpCompetitionresult) {
//        Object number=selectCombination(bkpCompetitionresult.getHowtoplay());//最大
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat myFmt2 = simpleDateFormat;
        Date date = new Date();
        bkpCompetitionresult.setCreattime(myFmt2.format(date));
        User user = UserUtils.getUser();
        bkpCompetitionresult.setCreatby(user.getUserCode());
        bkpCompetitionresultService.save(bkpCompetitionresult);
    }
    @PostMapping(value = "queryRecommendation")
	@ResponseBody
	public Object queryRecommendation(String wf){
    	return bkpCompetitionresultService.queryRecommendation(wf);
	}



	/**
     * 查看编辑表单
     */
    @RequiresPermissions("bkp:bkpCompetitionresult:view")
    @RequestMapping(value = "form")
    public String form(BkpCompetitionresult bkpCompetitionresult, Model model) {
        model.addAttribute("bkpCompetitionresult", bkpCompetitionresult);
        return "modules/bkp/bkpCompetitionresultForm";
    }

    /**
     * 保存数据
     */
    @RequiresPermissions("bkp:bkpCompetitionresult:edit")
    @PostMapping(value = "save")
    @ResponseBody
    public String save(@Validated BkpCompetitionresult bkpCompetitionresult) {
        bkpCompetitionresultService.save(bkpCompetitionresult);
        return renderResult(Global.TRUE, text("保存比赛结果推荐表成功！"));
    }

    /**
     * 删除数据
     */
    @RequiresPermissions("bkp:bkpCompetitionresult:edit")
    @RequestMapping(value = "delete")
    @ResponseBody
    public String delete(BkpCompetitionresult bkpCompetitionresult) {
        bkpCompetitionresultService.delete(bkpCompetitionresult);
        return renderResult(Global.TRUE, text("删除比赛结果推荐表成功！"));
    }

}