/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.bkp.dao;

import com.jeesite.common.dao.CrudDao;
import com.jeesite.common.entity.Page;
import com.jeesite.common.mybatis.annotation.MyBatisDao;
import com.jeesite.modules.bkp.entity.BkpBatchrecord;
import com.jeesite.modules.bkp.entity.BkpFixedprofit;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 批次记录表DAO接口
 * @author Lucifer
 * @version 2020-09-03
 */
@MyBatisDao
public interface BkpBatchrecordDao extends CrudDao<BkpBatchrecord> {



    void updateById(@Param("startTime") String startTime,@Param("id") Long id,@Param("zt") int zt);

    BkpFixedprofit selectById(@Param("id") Long id);

    BkpBatchrecord selectMin(@Param("modeid")Long modeid);

    Object selectPcxh(@Param("id") String id);

    List<String> selectCreateTime(@Param("pcxh") int pcxh,@Param("pcjlid") String pcjlid);

    List<BkpBatchrecord> selectBystartTime(@Param("id") String id);

    List<String> selectBypcjlId(@Param("pcjlId") String pcjlId);

    BkpBatchrecord getById(@Param("pcjlid") Long pcjlid);

    void updateBy(@Param("id") String id,@Param("dqzt") String dqzt);

    List<BkpBatchrecord> selectByUserCode(@Param("usercode") String usercode);

    List<BkpBatchrecord> selectByModeId(@Param("modeId") String modeId);

    List<BkpBatchrecord> findPage1(BkpBatchrecord bkpBatchrecord);

    List<BkpBatchrecord> findList1(BkpBatchrecord bkpBatchrecord);

    List<BkpBatchrecord> selectByDqzt(@Param("modeid") Long modeid);
}