/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.teamdata.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import com.jeesite.common.config.Global;
import com.jeesite.common.entity.Page;
import com.jeesite.common.web.BaseController;
import com.jeesite.modules.teamdata.entity.Teamdata;
import com.jeesite.modules.teamdata.service.TeamdataService;

import java.io.IOException;

/**
 * teamdataController
 * @author Lucifer
 * @version 2020-07-31
 */
@Controller
@RequestMapping(value = "${adminPath}/teamdata/teamdata")
public class TeamdataController extends BaseController {

	@Autowired
	private TeamdataService teamdataService;
	
	/**
	 * 获取数据
	 */
	@ModelAttribute
	public Teamdata get(String id, boolean isNewRecord) {
		return teamdataService.get(id, isNewRecord);
	}
	
	/**
	 * 查询列表
	 */
	@RequiresPermissions("teamdata:teamdata:view")
	@RequestMapping(value = {"list", ""})
	public String list(Teamdata teamdata, Model model) {
		model.addAttribute("teamdata", teamdata);
		return "modules/teamdata/teamdataList";
	}

	/**
	 *
	 * @return
	 */
	@RequestMapping("queryData")
	@ResponseBody
	public Object queryData(@RequestParam String time){
		return teamdataService.queryData(time);
	}




	
	/**
	 * 查询列表数据
	 */
	@RequiresPermissions("teamdata:teamdata:view")
	@RequestMapping(value = "listData")
	@ResponseBody
	public Page<Teamdata> listData(Teamdata teamdata, HttpServletRequest request, HttpServletResponse response) throws IOException {
		teamdata.setPage(new Page<>(request, response));
		Page<Teamdata> page = teamdataService.findPage(teamdata);
		return page;
	}
	//用作定时任务
	@GetMapping(value = "task")
	public void task() throws IOException {
		teamdataService.task();
	}

	/**
	 * 查看编辑表单
	 */
	@RequiresPermissions("teamdata:teamdata:view")
	@RequestMapping(value = "form")
	public String form(Teamdata teamdata, Model model) {
		model.addAttribute("teamdata", teamdata);
		return "modules/teamdata/teamdataForm";
	}

	/**
	 * 保存数据
	 */
	@RequiresPermissions("teamdata:teamdata:edit")
	@PostMapping(value = "save")
	@ResponseBody
	public String save(@Validated Teamdata teamdata) {
		teamdataService.save(teamdata);
		return renderResult(Global.TRUE, text("保存teamdata成功！"));
	}
	
	/**
	 * 删除数据
	 */
	@RequiresPermissions("teamdata:teamdata:edit")
	@RequestMapping(value = "delete")
	@ResponseBody
	public String delete(Teamdata teamdata) {
		teamdataService.delete(teamdata);
		return renderResult(Global.TRUE, text("删除teamdata成功！"));
	}
	@GetMapping(value = "/ceshi02")
	public String ceshi02(){
		return "modules/test/test02";
	}
}