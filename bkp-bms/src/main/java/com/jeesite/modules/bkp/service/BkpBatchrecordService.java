/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.bkp.service;

import java.util.List;

import com.jeesite.modules.bkp.dao.BkpFixedprofitDao;
import com.jeesite.modules.bkp.entity.BkpFixedprofit;
import com.jeesite.modules.bkp.entity.BkpUserbets;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeesite.common.entity.Page;
import com.jeesite.common.service.CrudService;
import com.jeesite.modules.bkp.entity.BkpBatchrecord;
import com.jeesite.modules.bkp.dao.BkpBatchrecordDao;

/**
 * 批次记录表Service
 * @author Lucifer
 * @version 2020-09-03
 */
@Service
@Transactional(readOnly=true)
public class BkpBatchrecordService extends CrudService<BkpBatchrecordDao, BkpBatchrecord> {
	@Autowired
	private BkpBatchrecordDao bkpBatchrecordDao;
	
	/**
	 * 获取单条数据
	 * @param bkpBatchrecord
	 * @return
	 */
	@Override
	public BkpBatchrecord get(BkpBatchrecord bkpBatchrecord) {
		return super.get(bkpBatchrecord);
	}
	
	/**
	 * 查询分页数据
	 * @param bkpBatchrecord 查询条件
	 * @param bkpBatchrecord.page 分页对象
	 * @return
	 */
	@Override
	public Page<BkpBatchrecord> findPage(BkpBatchrecord bkpBatchrecord) {
		return super.findPage(bkpBatchrecord);
	}
	
	/**
	 * 保存数据（插入或更新）
	 * @param bkpBatchrecord
	 */
	@Override
	@Transactional(readOnly=false)
	public void save(BkpBatchrecord bkpBatchrecord) {
		super.save(bkpBatchrecord);
	}
	
	/**
	 * 更新状态
	 * @param bkpBatchrecord
	 */
	@Override
	@Transactional(readOnly=false)
	public void updateStatus(BkpBatchrecord bkpBatchrecord) {
		super.updateStatus(bkpBatchrecord);
	}
	
	/**
	 * 删除数据
	 * @param bkpBatchrecord
	 */
	@Override
	@Transactional(readOnly=false)
	public void delete(BkpBatchrecord bkpBatchrecord) {
		super.delete(bkpBatchrecord);
	}



	@Transactional(readOnly=false)
	public void updateById(String format, Long pcjlid) {
		int dqzt=1;
		bkpBatchrecordDao.updateById(format,pcjlid,dqzt);
	}

	public BkpFixedprofit selectById(Long modeid) {
		return bkpBatchrecordDao.selectById(modeid);
	}

	public BkpBatchrecord selectMin(Long modeid) {
		return bkpBatchrecordDao.selectMin(modeid);
	}

	public Object selectPcxh(String id) {
		return bkpBatchrecordDao.selectPcxh(id);
	}


	public List<String> selectCreateTime(int pcxh, String id) {
		return bkpBatchrecordDao.selectCreateTime(pcxh,id);
	}

	public List<BkpBatchrecord> selectBystartTime(String id) {
		return bkpBatchrecordDao.selectBystartTime(id);
	}

    public List<String> selectBypcjlId(String id) {
		return bkpBatchrecordDao.selectBypcjlId(id);
    }

	public BkpBatchrecord getById(Long pcjlid) {
		return bkpBatchrecordDao.getById(pcjlid);
	}

	public void updateBy(String id, String s) {
		bkpBatchrecordDao.updateBy(id,s);
	}

    public List<BkpBatchrecord> selectByUserCode(String userCode) {
		return bkpBatchrecordDao.selectByUserCode(userCode);
    }

    public List<BkpBatchrecord> selectByModeId(String id) {
		return bkpBatchrecordDao.selectByModeId(id);
    }

	public List<BkpBatchrecord> findPage1(BkpBatchrecord bkpBatchrecord) {
		return bkpBatchrecordDao.findPage1(bkpBatchrecord);
	}

	public List<BkpBatchrecord> findList1(BkpBatchrecord bkpBatchrecord) {
		return bkpBatchrecordDao.findList1(bkpBatchrecord);
	}

    public List<BkpBatchrecord> selectByDqzt(Long modeid) {
		return bkpBatchrecordDao.selectByDqzt(modeid);
    }
}