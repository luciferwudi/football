/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.teamdata.dao;

import com.jeesite.common.dao.CrudDao;
import com.jeesite.common.mybatis.annotation.MyBatisDao;
import com.jeesite.modules.teamdata.entity.Teamdata;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * teamdataDAO接口
 * @author Lucifer
 * @version 2020-07-31
 */
@MyBatisDao
public interface   TeamdataDao extends CrudDao<Teamdata> {

    int findByid(@Param("id") String id);

    List<Teamdata> selectByResult();


    List<Map<String, Object>> queryData(String time);
}