/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.userbets.dao;

import com.jeesite.common.dao.CrudDao;
import com.jeesite.common.mybatis.annotation.MyBatisDao;
import com.jeesite.modules.userbets.entity.Userbets;

import java.util.List;

/**
 * userbetsDAO接口
 * @author Lucifer
 * @version 2020-08-14
 */
@MyBatisDao
public interface UserbetsDao extends CrudDao<Userbets> {

    List<Userbets> selectByResult();
}