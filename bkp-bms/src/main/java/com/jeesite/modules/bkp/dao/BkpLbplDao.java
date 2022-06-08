/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.bkp.dao;

import com.jeesite.common.dao.CrudDao;
import com.jeesite.common.mybatis.annotation.MyBatisDao;
import com.jeesite.modules.bkp.entity.BkpLbpl;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 立博的主场各赔率DAO接口
 * @author Lucifer
 * @version 2021-02-20
 */
@MyBatisDao
public interface BkpLbplDao extends CrudDao<BkpLbpl> {

    List<String> selectGroupByTime();

    int selectz(@Param("a1") Double a1, @Param("result") String result, @Param("a2") Double a2);

    int selectk(@Param("a1") Double a1, @Param("result") String result, @Param("a2") Double a2);
}