/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.test.web;

import com.jeesite.common.lang.StringUtils;
import com.jeesite.common.web.BaseController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

/**
 * 演示实例Controller
 * @author ThinkGem
 * @version 2018-03-24
 * @RequestMapping(value = "${frontPath}/demo")
 */
@Controller
@RequestMapping(value = "/demo")
public class DemoFrontPathController extends BaseController {


	@GetMapping(value = "/hello")
	public String hello(){
		return "modules/test/hello";
	}


	@GetMapping(value = "/hello2")
	public String hello2(){
		return "modules/test/web";
	}

	@GetMapping(value = "/ceshi")
	public String ceshi(){
		return "modules/test/ceshi";
	}

	@GetMapping(value = "/ceshi01")
	public String ceshi01(){
		return "modules/test/test01";
	}

}
