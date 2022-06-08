/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.test.web;

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
import com.jeesite.modules.test.entity.Test;
import com.jeesite.modules.test.service.TestService;

/**
 * testController
 * @author Lucifer
 * @version 2020-07-31
 */
@Controller
@RequestMapping(value = "${adminPath}/test/test")
public class TestController extends BaseController {

	@Autowired
	private TestService testService;
	
	/**
	 * 获取数据
	 */
	@ModelAttribute
	public Test get(String id, boolean isNewRecord) {
		return testService.get(id, isNewRecord);
	}
	
	/**
	 * 查询列表
	 */
	@RequiresPermissions("test:test:view")
	@RequestMapping(value = {"list", ""})
	public String list(Test test, Model model) {
		model.addAttribute("test", test);
		return "modules/test/testList";
	}
	
	/**
	 * 查询列表数据
	 */
	@RequiresPermissions("test:test:view")
	@RequestMapping(value = "listData")
	@ResponseBody
	public Page<Test> listData(Test test, HttpServletRequest request, HttpServletResponse response) {
		test.setPage(new Page<>(request, response));
		Page<Test> page = testService.findPage(test);
		return page;
	}

	/**
	 * 查看编辑表单
	 */
	@RequiresPermissions("test:test:view")
	@RequestMapping(value = "form")
	public String form(Test test, Model model) {
		model.addAttribute("test", test);
		return "modules/test/testForm";
	}

	/**
	 * 保存数据
	 */
	@RequiresPermissions("test:test:edit")
	@PostMapping(value = "save")
	@ResponseBody
	public String save(@Validated Test test) {
		testService.save(test);
		return renderResult(Global.TRUE, text("保存test成功！"));
	}
	
	/**
	 * 删除数据
	 */
	@RequiresPermissions("test:test:edit")
	@RequestMapping(value = "delete")
	@ResponseBody
	public String delete(Test test) {
		testService.delete(test);
		return renderResult(Global.TRUE, text("删除test成功！"));
	}
	
}