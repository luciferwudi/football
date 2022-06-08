/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.bkp.dao;

import com.jeesite.common.dao.CrudDao;
import com.jeesite.common.mybatis.annotation.MyBatisDao;
import com.jeesite.modules.bkp.entity.BkpDetail;

/**
 * 比赛下注详情表DAO接口
 * @author Lucifer
 * @version 2020-09-17
 */
@MyBatisDao
public interface BkpDetailDao extends CrudDao<BkpDetail> {
	
}