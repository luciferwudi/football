/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.bkp.dao;

import com.jeesite.common.dao.CrudDao;
import com.jeesite.common.mybatis.annotation.MyBatisDao;
import com.jeesite.modules.bkp.entity.BkpLeis;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * bkp_leisDAO接口
 * @author Lucifer
 * @version 2020-10-13
 */
@MyBatisDao
public interface BkpLeisDao extends CrudDao<BkpLeis> {

    BkpLeis getentiy(@Param("league") String league, @Param("time") String time, @Param("hometeam") String hometeam);

    List<BkpLeis> selectAllResult(@Param("time") String time);

    List<String> selectByGroup();
}