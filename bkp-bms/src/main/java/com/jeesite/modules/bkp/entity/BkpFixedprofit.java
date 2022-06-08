/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.bkp.entity;

import org.hibernate.validator.constraints.Length;

import com.jeesite.common.entity.DataEntity;
import com.jeesite.common.mybatis.annotation.Column;
import com.jeesite.common.mybatis.annotation.Table;
import com.jeesite.common.mybatis.mapper.query.QueryType;

/**
 * 固定盈利模式Entity
 * @author Lucifer
 * @version 2020-08-31
 */
@Table(name="bkp_fixedprofit", alias="a", columns={
		@Column(name="id", attrName="id", label="id", isPK=true),
		@Column(name="usercode", attrName="usercode", label="用户标识"),
		@Column(name="monthamount", attrName="monthamount", label="每月固定盈利金额"),
		@Column(name="batch", attrName="batch", label="分批"),
		@Column(name="totalloss", attrName="totalloss", label="历史亏损金额"),
		@Column(name="status", attrName="status", label="如进行中 已完成", isUpdate=false),
		@Column(name="starttime", attrName="starttime", label="开始时间"),
		@Column(name="endtime", attrName="endtime", label="结束时间"),
		@Column(name="profitamount", attrName="profitamount", label="当前模式结束实际盈利金额"),
		@Column(name="retrieveinbatches", attrName="retrieveinbatches", label="亏损分批找回"),
		@Column(name="deletelogo", attrName="deletelogo", label="0 失效 1有效"),
		@Column(name="cycle", attrName="cycle", label="周期"),
		@Column(name="ytzje", attrName="ytzje", label="已经投注金额"),
		@Column(name="description", attrName="description", label="当前固定模式描述"),
		@Column(name="creattime", attrName="creattime", label="创建时间"),
	}, orderBy="a.id DESC"
)
public class BkpFixedprofit extends DataEntity<BkpFixedprofit> {
	
	private static final long serialVersionUID = 1L;
	private String usercode;		// 用户标识
	private String monthamount;		// 每月固定盈利金额
	private Long batch;		// 分批
	private String totalloss;		// 历史亏损金额
	private String starttime;		// 开始时间
	private String endtime;		// 结束时间
	private String profitamount;		// 当前模式结束实际盈利金额
	private Long retrieveinbatches;		// 亏损分批找回
	private Long deletelogo;		// 0 失效 1有效
	private String cycle;		// 周期
	private String description;		// 当前固定模式描述
	private String creattime;		// 创建时间
	private String pcxq;
	private String fpdt;

	public String getFpdt() {
		return fpdt;
	}

	public void setFpdt(String fpdt) {
		this.fpdt = fpdt;
	}

	public String getPcxq() {
		return "批次详情";
	}

	public void setPcxq(String pcxq) {
		this.pcxq = pcxq;
	}

	public String getYtzje() {
		return ytzje;
	}

	public void setYtzje(String ytzje) {
		this.ytzje = ytzje;
	}

	private String ytzje;
	
	public BkpFixedprofit() {
		this(null);
	}

	public BkpFixedprofit(String id){
		super(id);
	}
	
	@Length(min=0, max=50, message="用户标识长度不能超过 50 个字符")
	public String getUsercode() {
		return usercode;
	}

	public void setUsercode(String usercode) {
		this.usercode = usercode;
	}
	
	@Length(min=0, max=30, message="每月固定盈利金额长度不能超过 30 个字符")
	public String getMonthamount() {
		return monthamount;
	}

	public void setMonthamount(String monthamount) {
		this.monthamount = monthamount;
	}
	
	public Long getBatch() {
		return batch;
	}

	public void setBatch(Long batch) {
		this.batch = batch;
	}
	
	@Length(min=0, max=30, message="历史亏损金额长度不能超过 30 个字符")
	public String getTotalloss() {
		return totalloss;
	}

	public void setTotalloss(String totalloss) {
		this.totalloss = totalloss;
	}
	
	@Length(min=0, max=50, message="开始时间长度不能超过 50 个字符")
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
	
	@Length(min=0, max=30, message="当前模式结束实际盈利金额长度不能超过 30 个字符")
	public String getProfitamount() {
		return profitamount;
	}

	public void setProfitamount(String profitamount) {
		this.profitamount = profitamount;
	}
	
	public Long getRetrieveinbatches() {
		return retrieveinbatches;
	}

	public void setRetrieveinbatches(Long retrieveinbatches) {
		this.retrieveinbatches = retrieveinbatches;
	}
	
	public Long getDeletelogo() {
		return deletelogo;
	}

	public void setDeletelogo(Long deletelogo) {
		this.deletelogo = deletelogo;
	}
	
	@Length(min=0, max=30, message="周期长度不能超过 30 个字符")
	public String getCycle() {
		return cycle;
	}

	public void setCycle(String cycle) {
		this.cycle = cycle;
	}
	
	@Length(min=0, max=50, message="当前固定模式描述长度不能超过 50 个字符")
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
	@Length(min=0, max=50, message="创建时间长度不能超过 50 个字符")
	public String getCreattime() {
		return creattime;
	}

	public void setCreattime(String creattime) {
		this.creattime = creattime;
	}
	
}