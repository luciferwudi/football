/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.bkp.dao;

import com.jeesite.common.dao.CrudDao;
import com.jeesite.common.mybatis.annotation.MyBatisDao;
import com.jeesite.modules.bkp.entity.BkpInjuries;
import com.jeesite.modules.teamdata.entity.Teamdata;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 球队球员伤痛表DAO接口
 * @author Lucifer
 * @version 2020-09-25
 */
@MyBatisDao
public interface BkpInjuriesDao extends CrudDao<BkpInjuries> {

    List<Teamdata> selectTeamName(@Param("tid") String tid);

    List<Teamdata> selectTeamName1(@Param("tid") String tid);
}