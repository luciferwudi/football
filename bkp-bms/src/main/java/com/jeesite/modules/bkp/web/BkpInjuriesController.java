/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.bkp.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import com.jeesite.common.config.Global;
import com.jeesite.common.entity.Page;
import com.jeesite.common.web.BaseController;
import com.jeesite.modules.bkp.entity.BkpInjuries;
import com.jeesite.modules.bkp.service.BkpInjuriesService;

/**
 * 球队球员伤痛表Controller
 * @author Lucifer
 * @version 2020-09-25
 */
@Controller
@RequestMapping(value = "${adminPath}/bkp/bkpInjuries")
public class BkpInjuriesController extends BaseController {

	@Autowired
	private BkpInjuriesService bkpInjuriesService;
	
	/**
	 * 获取数据
	 */
	@ModelAttribute
	public BkpInjuries get(String id, boolean isNewRecord) {
		return bkpInjuriesService.get(id, isNewRecord);
	}

	//导入数据
	public void insertData() throws Exception {
		bkpInjuriesService.insertData();
	}
	
	/**
	 * 查询列表
	 */
	@RequiresPermissions("bkp:bkpInjuries:view")
	@RequestMapping(value = {"list", ""})
	public String list(BkpInjuries bkpInjuries, Model model) {
		model.addAttribute("bkpInjuries", bkpInjuries);
		return "modules/bkp/bkpInjuriesList";
	}
	
	/**
	 * 查询列表数据
	 */
	@RequiresPermissions("bkp:bkpInjuries:view")
	@RequestMapping(value = "listData")
	@ResponseBody
	public Page<BkpInjuries> listData(BkpInjuries bkpInjuries, HttpServletRequest request, HttpServletResponse response) throws Exception {
		bkpInjuries.setPage(new Page<>(request, response));
//		insertData();
		Page<BkpInjuries> page = bkpInjuriesService.findPage(bkpInjuries);
		return page;
	}

	@RequestMapping(value = "playerInjury")
	@ResponseBody
	public Object playerInjury(@RequestParam String tid){
		return bkpInjuriesService.playerInjury(tid);
	}

	/**
	 * 查看编辑表单
	 */
	@RequiresPermissions("bkp:bkpInjuries:view")
	@RequestMapping(value = "form")
	public String form(BkpInjuries bkpInjuries, Model model) {
		model.addAttribute("bkpInjuries", bkpInjuries);
		return "modules/bkp/bkpInjuriesForm";
	}

	/**
	 * 保存数据
	 */
	@RequiresPermissions("bkp:bkpInjuries:edit")
	@PostMapping(value = "save")
	@ResponseBody
	public String save(@Validated BkpInjuries bkpInjuries) {
		bkpInjuriesService.save(bkpInjuries);
		return renderResult(Global.TRUE, text("保存球队球员伤痛表成功！"));
	}
	
	/**
	 * 删除数据
	 */
	@RequiresPermissions("bkp:bkpInjuries:edit")
	@RequestMapping(value = "delete")
	@ResponseBody
	public String delete(BkpInjuries bkpInjuries) {
		bkpInjuriesService.delete(bkpInjuries);
		return renderResult(Global.TRUE, text("删除球队球员伤痛表成功！"));
	}
	
}