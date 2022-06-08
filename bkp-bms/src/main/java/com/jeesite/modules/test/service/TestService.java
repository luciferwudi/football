/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.test.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeesite.common.entity.Page;
import com.jeesite.common.service.CrudService;
import com.jeesite.modules.test.entity.Test;
import com.jeesite.modules.test.dao.TestDao;

/**
 * testService
 * @author Lucifer
 * @version 2020-07-31
 */
@Service
@Transactional(readOnly=true)
public class TestService extends CrudService<TestDao, Test> {
	
	/**
	 * 获取单条数据
	 * @param test
	 * @return
	 */
	@Override
	public Test get(Test test) {
		return super.get(test);
	}
	
	/**
	 * 查询分页数据
	 * @param test 查询条件
	 * @param test.page 分页对象
	 * @return
	 */
	@Override
	public Page<Test> findPage(Test test) {
		return super.findPage(test);
	}
	
	/**
	 * 保存数据（插入或更新）
	 * @param test
	 */
	@Override
	@Transactional(readOnly=false)
	public void save(Test test) {
		super.save(test);
	}
	
	/**
	 * 更新状态
	 * @param test
	 */
	@Override
	@Transactional(readOnly=false)
	public void updateStatus(Test test) {
		super.updateStatus(test);
	}
	
	/**
	 * 删除数据
	 * @param test
	 */
	@Override
	@Transactional(readOnly=false)
	public void delete(Test test) {
		super.delete(test);
	}
	
}