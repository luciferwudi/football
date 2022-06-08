/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.bkp.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.authz.annotation.RequiresPermissions;
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
import com.jeesite.modules.bkp.entity.BkpDetail;
import com.jeesite.modules.bkp.service.BkpDetailService;

/**
 * 比赛下注详情表Controller
 * @author Lucifer
 * @version 2020-09-17
 */
@Controller
@RequestMapping(value = "${adminPath}/bkp/bkpDetail")
public class BkpDetailController extends BaseController {

	@Autowired
	private BkpDetailService bkpDetailService;
	
	/**
	 * 获取数据
	 */
	@ModelAttribute
	public BkpDetail get(String id, boolean isNewRecord) {
		return bkpDetailService.get(id, isNewRecord);
	}
	
	/**
	 * 查询列表
	 */
	@RequiresPermissions("bkp:bkpDetail:view")
	@RequestMapping(value = {"list", ""})
	public String list(BkpDetail bkpDetail, Model model) {
		model.addAttribute("bkpDetail", bkpDetail);
		return "modules/bkp/bkpDetailList";
	}
	
	/**
	 * 查询列表数据
	 */
	@RequiresPermissions("bkp:bkpDetail:view")
	@RequestMapping(value = "listData")
	@ResponseBody
	public Page<BkpDetail> listData(BkpDetail bkpDetail, HttpServletRequest request, HttpServletResponse response) {
		bkpDetail.setPage(new Page<>(request, response));
		Page<BkpDetail> page = bkpDetailService.findPage(bkpDetail);
		return page;
	}

	/**
	 * 查看编辑表单
	 */
	@RequiresPermissions("bkp:bkpDetail:view")
	@RequestMapping(value = "form")
	public String form(BkpDetail bkpDetail, Model model) {
		model.addAttribute("bkpDetail", bkpDetail);
		return "modules/bkp/bkpDetailForm";
	}

	/**
	 * 保存数据
	 */
	@RequiresPermissions("bkp:bkpDetail:edit")
	@PostMapping(value = "save")
	@ResponseBody
	public String save(@Validated BkpDetail bkpDetail) {
		bkpDetailService.save(bkpDetail);
		return renderResult(Global.TRUE, text("保存比赛下注详情表成功！"));
	}
	/**
	 * 保存数据
	 */

	@PostMapping(value = "save1")
	@ResponseBody
	public void save1(@Validated BkpDetail bkpDetail) {
		bkpDetailService.save(bkpDetail);
	}
	
	/**
	 * 删除数据
	 */
	@RequiresPermissions("bkp:bkpDetail:edit")
	@RequestMapping(value = "delete")
	@ResponseBody
	public String delete(BkpDetail bkpDetail) {
		bkpDetailService.delete(bkpDetail);
		return renderResult(Global.TRUE, text("删除比赛下注详情表成功！"));
	}
	
}