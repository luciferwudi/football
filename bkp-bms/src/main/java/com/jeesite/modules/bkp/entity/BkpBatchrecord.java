/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.bkp.entity;

import org.hibernate.validator.constraints.Length;

import com.jeesite.common.entity.DataEntity;
import com.jeesite.common.mybatis.annotation.Column;
import com.jeesite.common.mybatis.annotation.Table;

import java.util.Date;

/**
 * 批次记录表Entity
 * @author Lucifer
 * @version 2020-09-03
 */
@Table(name="bkp_batchrecord", alias="a", columns={
		@Column(name="id", attrName="id", label="id", isPK=true),
		@Column(name="modeid", attrName="modeid", label="固定模式id"),
		@Column(name="batch", attrName="batch", label="当前批次"),
		@Column(name="preprofit", attrName="preprofit", label="当前批次预盈利金额"),
		@Column(name="profitamount", attrName="profitamount", label="当前批次实际盈利金额"),
		@Column(name="starttime", attrName="starttime", label="开始时间"),
		@Column(name="endtime", attrName="endtime", label="结束时间"),
		@Column(name="description", attrName="description", label="描述"),
		@Column(name="userCode", attrName="userCode", label="用户标识"),
		@Column(name="fpyl", attrName="fpyl", label="分批(天盈利)"),
		@Column(name="dqzt", attrName="dqzt", label="状态"),
		@Column(name="status", attrName="status", label="当前状态 进行中 已完成", isUpdate=false),
	}, orderBy="a.id DESC"
)
public class BkpBatchrecord extends DataEntity<BkpBatchrecord> {
	
	private static final long serialVersionUID = 1L;
	private Long modeid;		// 固定模式id
	private Integer batch;		// 当前批次
	private String preprofit;		// 当前批次预盈利金额
	private String profitamount;		// 当前批次实际盈利金额
	private String starttime;		// 开始时间
	private String endtime;		// 结束时间
	private String description;
	private String fpyl;
	private String userCode;
	private String dqzt;

	public String getDqzt() {
		return dqzt;
	}

	public void setDqzt(String dqzt) {
		this.dqzt = dqzt;
	}

	public String getUserCode() {
		return userCode;
	}

	public void setUserCode(String userCode) {
		this.userCode = userCode;
	}

	public String getFpyl() {
		return  fpyl;
	}

	public void setFpyl(String fpyl) {
		this.fpyl = fpyl;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public BkpBatchrecord() {
		this(null);
	}

	public BkpBatchrecord(String id){
		super(id);
	}
	
	public Long getModeid() {
		return modeid;
	}

	public void setModeid(Long modeid) {
		this.modeid = modeid;
	}

	public Integer getBatch() {
		return batch;
	}

	public void setBatch(Integer batch) {
		this.batch = batch;
	}
	
	@Length(min=0, max=30, message="当前批次预盈利金额长度不能超过 30 个字符")
	public String getPreprofit() {
		return preprofit;
	}

	public void setPreprofit(String preprofit) {
		this.preprofit = preprofit;
	}
	
	@Length(min=0, max=30, message="当前批次实际盈利金额长度不能超过 30 个字符")
	public String getProfitamount() {
		return profitamount;
	}

	public void setProfitamount(String profitamount) {
		this.profitamount = profitamount;
	}
	
	public String getStarttime() {
		return starttime;
	}

	public void setStarttime(String starttime) {
		this.starttime = starttime;
	}
	
	@Length(min=0, max=50, message="结束时间长度不能超过 50 个字符")
	public String getEndtime() {
		return endtime;
	}

	public void setEndtime(String endtime) {
		this.endtime = endtime;
	}
	
}