/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.odds.dao;

import com.jeesite.common.dao.CrudDao;
import com.jeesite.common.mybatis.annotation.MyBatisDao;
import com.jeesite.modules.bkp.entity.BkpRecommendeddescription;
import com.jeesite.modules.bkp.entity.BkpUserbets;
import com.jeesite.modules.odds.entity.Odds;
import com.jeesite.modules.teamdata.entity.Teamdata;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * oddsDAO接口
 * @author Lucifer
 * @version 2020-07-30
 */
@MyBatisDao
public interface OddsDao extends CrudDao<Odds> {

    List<Teamdata> selectAllTeamdata();

    Odds selectOdds(@Param("type") String type, @Param("teamdataid") String teamdataid);

    List<String> selectAllId();

    void updateByTeamdataIdAndType(Odds odds);

    List<String> selectByUserCode(@Param("userCode") String userCode);

    List<Long> selectBycom(@Param("createTime") String createTime,@Param("userCode") String userCode);

    List<BkpUserbets> selectByCombination(@Param("combination") Long combination);

    Teamdata selectTeam(Map param);

    BkpRecommendeddescription selectExplanation(@Param("team") Object team);

    List<String> queryTid(@Param("team") String team);

    List<String> queryTid1(@Param("team")String team);

    List<Teamdata> selectTeamdata();

    Odds get1(@Param("teamdataid") String teamdataid,@Param("type") String type);
}