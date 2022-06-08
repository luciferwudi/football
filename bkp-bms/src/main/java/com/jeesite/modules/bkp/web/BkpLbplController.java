/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.bkp.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import com.jeesite.common.config.Global;
import com.jeesite.common.entity.Page;
import com.jeesite.common.web.BaseController;
import com.jeesite.modules.bkp.entity.BkpLbpl;
import com.jeesite.modules.bkp.service.BkpLbplService;

import java.io.IOException;
import java.util.Map;

/**
 * 立博的主场各赔率Controller
 * @author Lucifer
 * @version 2021-02-20
 */
@Controller
@RequestMapping(value = "${adminPath}/bkp/bkpLbpl")
public class BkpLbplController extends BaseController {

	@Autowired
	private BkpLbplService bkpLbplService;
	
	/**
	 * 获取数据
	 */
	@ModelAttribute
	public BkpLbpl get(String id, boolean isNewRecord) {
		return bkpLbplService.get(id, isNewRecord);
	}
	
	/**
	 * 查询列表
	 */
	@RequiresPermissions("bkp:bkpLbpl:view")
	@RequestMapping(value = {"list", ""})
	public String list(BkpLbpl bkpLbpl, Model model) {
		model.addAttribute("bkpLbpl", bkpLbpl);
		return "modules/bkp/bkpLbplList";
	}
	
	/**
	 * 查询列表数据
	 */
	@RequiresPermissions("bkp:bkpLbpl:view")
	@RequestMapping(value = "listData")
	@ResponseBody
	public Page<BkpLbpl> listData(BkpLbpl bkpLbpl, HttpServletRequest request, HttpServletResponse response) throws IOException {
		bkpLbpl.setPage(new Page<>(request, response));
		Page<BkpLbpl> page = bkpLbplService.findPage(bkpLbpl);
		return page;
	}

	@ApiOperation("立博")
	@RequestMapping(value = "更新数据")
	@ResponseBody
	public void autoUpdate(String time) throws IOException {
		bkpLbplService.autoUpdate(time);
	}

	@ApiOperation("立博主客场赔率分析区间")
	@PostMapping("/analysisInterval")
	public Object analysisInterval(@RequestParam Map param){
		return bkpLbplService.analysisInterval(Double.valueOf(param.get("inputV1").toString()),Double.valueOf(param.get("inputV2").toString()),param.get("value").toString());
	}



	/**
	 * 查看编辑表单
	 */
	@RequiresPermissions("bkp:bkpLbpl:view")
	@RequestMapping(value = "form")
	public String form(BkpLbpl bkpLbpl, Model model) {
		model.addAttribute("bkpLbpl", bkpLbpl);
		return "modules/bkp/bkpLbplForm";
	}

	/**
	 * 保存数据
	 */
	@RequiresPermissions("bkp:bkpLbpl:edit")
	@PostMapping(value = "save")
	@ResponseBody
	public String save(@Validated BkpLbpl bkpLbpl) {
		bkpLbplService.save(bkpLbpl);
		return renderResult(Global.TRUE, text("保存立博的主场各赔率成功！"));
	}
	
	/**
	 * 删除数据
	 */
	@RequiresPermissions("bkp:bkpLbpl:edit")
	@RequestMapping(value = "delete")
	@ResponseBody
	public String delete(BkpLbpl bkpLbpl) {
		bkpLbplService.delete(bkpLbpl);
		return renderResult(Global.TRUE, text("删除立博的主场各赔率成功！"));
	}
	
}