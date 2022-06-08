/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.bkp.dao;

import com.jeesite.common.dao.CrudDao;
import com.jeesite.common.mybatis.annotation.MyBatisDao;
import com.jeesite.modules.bkp.entity.BkpCompetitionresult;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 比赛结果推荐表DAO接口
 * @author Lucifer
 * @version 2020-08-27
 */
@MyBatisDao
public interface BkpCompetitionresultDao extends CrudDao<BkpCompetitionresult> {

    void deleteByCombination();

    Object selectCombination(@Param("howToPlay") String howToPlay);

    List<BkpCompetitionresult> queryRecommendation(@Param("howtoplay") String howtoplay);
}