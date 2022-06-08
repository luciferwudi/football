/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.bkp.dao;

import com.jeesite.common.dao.CrudDao;
import com.jeesite.common.mybatis.annotation.MyBatisDao;
import com.jeesite.modules.bkp.entity.BkpFixedprofit;
import com.jeesite.modules.bkp.entity.BkpUserbets;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * 用户下注表DAO接口
 * @author Lucifer
 * @version 2020-09-02
 */
@MyBatisDao
public interface BkpUserbetsDao extends CrudDao<BkpUserbets> {

    List<BkpUserbets> selectAllNullMatchResults(@Param("userCode") String userCode);

    List<Map> selectCombination(@Param("userCode") String userCode);

    List<BkpUserbets> selectByCombination(@Param("combination") Long combination);

    List<BkpUserbets> getById(@Param("pcjlid") Long pcjlid);

    List<Integer> getBygdylId(@Param("gdmsId") String gdmsId);

    BkpFixedprofit getByGdmsid(@Param("gdmsid") Long gdmsid);

    List<Map> selectCombinationAndPcjlid(@Param("userCode") String userCode,@Param("pcjlid") Long pcjlid);

    String selectByGdylmsId(@Param("id") Long id);

    Integer selectByPcjlId(@Param("id")Long id);

    List<Long> selectById(@Param("pcjlid") Long pcjlid);

    Long findById(@Param("id") Long id);
}