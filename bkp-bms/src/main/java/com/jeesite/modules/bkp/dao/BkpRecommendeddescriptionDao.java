/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.bkp.dao;

import com.jeesite.common.dao.CrudDao;
import com.jeesite.common.mybatis.annotation.MyBatisDao;
import com.jeesite.modules.bkp.entity.BkpRecommendeddescription;
import com.jeesite.modules.teamdata.entity.Teamdata;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 推荐说明表DAO接口
 * @author Lucifer
 * @version 2020-08-28
 */
@MyBatisDao
public interface BkpRecommendeddescriptionDao extends CrudDao<BkpRecommendeddescription> {

    BkpRecommendeddescription teamQuery(@Param("team") String team);

    List<Teamdata> selectTeam(@Param("hometeam") String hometeam);

    List<Teamdata> selectATeam(@Param("awayteam")String awayteam);
}