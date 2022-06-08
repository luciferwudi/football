/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.userbets.entity;

import org.hibernate.validator.constraints.Length;
import com.fasterxml.jackson.annotation.JsonFormat;

import com.jeesite.common.entity.DataEntity;
import com.jeesite.common.mybatis.annotation.Column;
import com.jeesite.common.mybatis.annotation.Table;

import javax.validation.constraints.NotBlank;

/**
 * userbetsEntity
 * @author Lucifer
 * @version 2020-08-14
 */
@Table(name="userbets", alias="a", columns={
		@Column(name="id", attrName="id", label="id", isPK=true),
		@Column(name="userCode", attrName="userCode", label="用户"),
		@Column(name="bettingmethod", attrName="bettingmethod", label="投注方式"),
		@Column(name="creattime", attrName="creattime", label="创建时间"),
		@Column(name="betamount", attrName="betamount", label="投注金额"),
		@Column(name="earnings", attrName="earnings", label="盈利预期"),
		@Column(name="multiple", attrName="multiple", label="倍数"),
		@Column(name="totalloss", attrName="totalloss", label="之前亏损金额"),
		@Column(name="profitratioafterdeductinglosses", attrName="profitratioafterdeductinglosses", label="扣除亏损盈利比例"),
		@Column(name="profitafterdeductinglosses", attrName="profitafterdeductinglosses", label="扣除亏损盈利金额"),
		@Column(name="currentprofitratio", attrName="currentprofitratio", label="当期盈利比"),
		@Column(name="currentprofitamount", attrName="currentprofitamount", label="当期盈利金额"),
		@Column(name="selectresult", attrName="selectresult", label="用户选择结果"),
		@Column(name="matchResults", attrName="matchResults", label="比赛结果"),
		@Column(name="batch", attrName="batch", label="分批"),
		@Column(name="result", attrName="result", label="此次投注结果"),
	}, orderBy="a.id DESC"
)
public class Userbets extends DataEntity<Userbets> {
	
	private static final long serialVersionUID = 1L;
	private String userCode;		// 用户
	private String bettingmethod;		// 投注方式
	private String creattime;		// 创建时间
	private Long betamount;		// 投注金额
	private String earnings;		// 盈利预期
	private Long multiple;		// 倍数
	private String totalloss;		// 之前亏损金额
	private String profitratioafterdeductinglosses;		// 扣除亏损盈利比例
	private String profitafterdeductinglosses;		// 扣除亏损盈利金额
	private String currentprofitratio;		// 当期盈利比
	private String currentprofitamount;		// 当期盈利金额
	private String selectresult;		// 用户选择结果
	private Long batch;		// 分批
	private String result; //此次下注结果
	private String matchResults;//比赛结果

	public String getUserCode() {
		return userCode;
	}

	public void setUserCode(String userCode) {
		this.userCode = userCode;
	}

	public String getMatchResults() {
		return matchResults;
	}

	public void setMatchResults(String matchResults) {
		this.matchResults = matchResults;
	}

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

	public Userbets() {
		this(null);
	}

	public Userbets(String id){
		super(id);
	}
	

	
	@Length(min=0, max=50, message="投注方式长度不能超过 50 个字符")
	public String getBettingmethod() {
		return bettingmethod;
	}

	public void setBettingmethod(String bettingmethod) {
		this.bettingmethod = bettingmethod;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public String getCreattime() {
		return creattime;
	}

	public void setCreattime(String creattime) {
		this.creattime = creattime;
	}
	
	public Long getBetamount() {
		return betamount;
	}

	public void setBetamount(Long betamount) {
		this.betamount = betamount;
	}
	
	@Length(min=0, max=30, message="盈利预期长度不能超过 30 个字符")
	public String getEarnings() {
		return earnings;
	}

	public void setEarnings(String earnings) {
		this.earnings = earnings;
	}

	public Long getMultiple() {
		return multiple;
	}

	public void setMultiple(Long multiple) {
		this.multiple = multiple;
	}


	@Length(min=0, max=50, message="之前亏损金额长度不能超过 50 个字符")
	public String getTotalloss() {
		return totalloss;
	}

	public void setTotalloss(String totalloss) {
		this.totalloss = totalloss;
	}
	
	@Length(min=0, max=50, message="扣除亏损盈利比例长度不能超过 50 个字符")
	public String getProfitratioafterdeductinglosses() {
		return profitratioafterdeductinglosses;
	}

	public void setProfitratioafterdeductinglosses(String profitratioafterdeductinglosses) {
		this.profitratioafterdeductinglosses = profitratioafterdeductinglosses;
	}
	
	@Length(min=0, max=50, message="扣除亏损盈利金额长度不能超过 50 个字符")
	public String getProfitafterdeductinglosses() {
		return profitafterdeductinglosses;
	}

	public void setProfitafterdeductinglosses(String profitafterdeductinglosses) {
		this.profitafterdeductinglosses = profitafterdeductinglosses;
	}
	
	@Length(min=0, max=50, message="当期盈利比长度不能超过 50 个字符")
	public String getCurrentprofitratio() {
		return currentprofitratio;
	}

	public void setCurrentprofitratio(String currentprofitratio) {
		this.currentprofitratio = currentprofitratio;
	}
	
	@Length(min=0, max=50, message="当期盈利金额长度不能超过 50 个字符")
	public String getCurrentprofitamount() {
		return currentprofitamount;
	}

	public void setCurrentprofitamount(String currentprofitamount) {
		this.currentprofitamount = currentprofitamount;
	}
	
	@Length(min=0, max=200, message="用户选择结果长度不能超过 200 个字符")
	public String getSelectresult() {
		return selectresult;
	}

	public void setSelectresult(String selectresult) {
		this.selectresult = selectresult;
	}
	
	public Long getBatch() {
		return batch;
	}

	public void setBatch(Long batch) {
		this.batch = batch;
	}
	
}