/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.userbets.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.jeesite.modules.odds.web.TestController002;
import com.jeesite.modules.sys.entity.User;
import com.jeesite.modules.sys.utils.UserUtils;
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
import com.jeesite.modules.userbets.entity.Userbets;
import com.jeesite.modules.userbets.service.UserbetsService;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * userbetsController
 * @author Lucifer
 * @version 2020-08-14
 */
@Controller
@RequestMapping(value = "${adminPath}/userbets/userbets")
public class UserbetsController extends BaseController {

	@Autowired
	private UserbetsService userbetsService;
	@Autowired
	private TestController002 testController002;
	
	/**
	 * 获取数据
	 */
	@ModelAttribute
	public Userbets get(String id, boolean isNewRecord) {
		return userbetsService.get(id, isNewRecord);
	}
	
	/**
	 * 查询列表
	 */
	@RequiresPermissions("userbets:userbets:view")
	@RequestMapping(value = {"list", ""})
	public String list(Userbets userbets, Model model) {
		model.addAttribute("userbets", userbets);
		return "modules/userbets/userbetsList";
	}
	
	/**
	 * 查询列表数据
	 */
	@RequiresPermissions("userbets:userbets:view")
	@RequestMapping(value = "listData")
	@ResponseBody
	public Page<Userbets> listData(Userbets userbets, HttpServletRequest request, HttpServletResponse response) throws IOException {
//		testController002.insertMatchResult();
		User user = UserUtils.getUser();
		userbets.setUserCode(user.getUserCode());
		userbets.setPage(new Page<>(request, response));
		Page<Userbets> page = userbetsService.findPage(userbets);
		return page;
	}

	/**
	 * 查看编辑表单
	 */
	@RequiresPermissions("userbets:userbets:view")
	@RequestMapping(value = "form")
	public String form(Userbets userbets, Model model) {
		model.addAttribute("userbets", userbets);
		return "modules/userbets/userbetsForm";
	}

	/**
	 * 保存数据
	 */
	@RequiresPermissions("userbets:userbets:edit")
	@PostMapping(value = "save")
	@ResponseBody
	public String save(@Validated Userbets userbets) {
		SimpleDateFormat myFmt2=new SimpleDateFormat("yyyy-MM-dd HH:mm");
		Date date=new Date();
		userbets.setCreattime(myFmt2.format(date));
		userbets.setResult("待开奖");
		User user = UserUtils.getUser();
		userbets.setUserCode(user.getUserCode());
		userbetsService.save(userbets);
		return renderResult(Global.TRUE, text("保存成功！"));
	}
	
	/**
	 * 删除数据
	 */
	@RequiresPermissions("userbets:userbets:edit")
	@RequestMapping(value = "delete")
	@ResponseBody
	public String delete(Userbets userbets) {
		userbetsService.delete(userbets);
		return renderResult(Global.TRUE, text("删除成功！"));
	}
	
}