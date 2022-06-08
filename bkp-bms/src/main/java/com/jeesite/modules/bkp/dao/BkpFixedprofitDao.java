/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.bkp.dao;

import com.jeesite.common.dao.CrudDao;
import com.jeesite.common.mybatis.annotation.MyBatisDao;
import com.jeesite.modules.bkp.entity.BkpFixedprofit;
import com.jeesite.modules.teamdata.entity.Teamdata;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 固定盈利模式DAO接口
 * @author Lucifer
 * @version 2020-08-31
 */
@MyBatisDao
public interface BkpFixedprofitDao extends CrudDao<BkpFixedprofit> {

    BkpFixedprofit userQuery(@Param("userCode") String userCode);

    Teamdata queryGame(@Param("teamdataid") String teamdataid);

    BkpFixedprofit findByid(@Param("id") String id);

    int selectBets(@Param("id") String id);

    BkpFixedprofit getBypcId(@Param("id") String id);

    int selectz(@Param("h") double h, @Param("name") String name, @Param("hv") double v,@Param("type") String type);

    int selectk(@Param("h") double h, @Param("name") String name, @Param("hv") double v,@Param("type") String type);
}