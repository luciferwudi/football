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
 * 用户下注表Entity
 * @author Lucifer
 * @version 2020-09-02
 */
@Table(name="bkp_userbets", alias="a", columns={
		@Column(name="id", attrName="id", label="id", isPK=true),
		@Column(name="usercode", attrName="usercode", label="用户标识"),
		@Column(name="league", attrName="league", label="联赛"),
		@Column(name="b_date", attrName="bdate", label="比赛时间"),
		@Column(name="num", attrName="num", label="次序"),
		@Column(name="hometeam", attrName="hometeam", label="主队"),
		@Column(name="awayteam", attrName="awayteam", label="客队"),
		@Column(name="createtime", attrName="createtime", label="创建时间"),
		@Column(name="bettingmethod", attrName="bettingmethod", label="玩法"),
		@Column(name="pick", attrName="pick", label="用户选择"),
		@Column(name="matchresults", attrName="matchresults", label="此次比赛结果"),
		@Column(name="betamount", attrName="betamount", label="投注金额"),
		@Column(name="earnings", attrName="earnings", label="盈利预期"),
		@Column(name="multiple", attrName="multiple", label="倍数"),
		@Column(name="totalloss", attrName="totalloss", label="当前批次亏损金额"),
		@Column(name="profitratioafterdeductinglosses", attrName="profitratioafterdeductinglosses", label="扣除亏损盈利比例"),
		@Column(name="profitafterdeductinglosses", attrName="profitafterdeductinglosses", label="扣除亏损盈利金额"),
		@Column(name="currentprofitratio", attrName="currentprofitratio", label="当期最大盈利比"),
		@Column(name="currentprofitamount", attrName="currentprofitamount", label="当期最大应力金额"),
		@Column(name="combination", attrName="combination", label="组合编号"),
		@Column(name="realamount", attrName="realamount", label="真实投注金额"),
		@Column(name="gdmsid", attrName="gdmsid", label="固定盈利模式id"),
		@Column(name="pcjlid", attrName="pcjlid", label="批次记录id"),
		@Column(name="status", attrName="status", label="状态"),
		@Column(name="pcxh", attrName="pcxh", label="批次序号"),
		@Column(name="xzsm", attrName="xzsm", label="下注说明"),
		@Column(name="llzg", attrName="llzg", label="理论最高盈利金额"),
		@Column(name="pctyj", attrName="pctyj", label="批次天预计盈利"),
		@Column(name="pctylbl", attrName="pctylbl", label="批次天预计盈利比例"),
	}, orderBy="a.id DESC"
)
public class BkpUserbets extends DataEntity<BkpUserbets> {
	
	private static final long serialVersionUID = 1L;
	private String usercode;		// 用户标识
	private String league;		// 联赛
	private String bdate;		// 比赛时间
	private String num;		// 次序
	private String hometeam;		// 主队
	private String awayteam;		// 客队
	private String createtime;		// 创建时间
	private String bettingmethod;		// 玩法
	private String pick;		// 用户选择
	private String matchresults;		// 此次比赛结果
	private Long betamount;		// 投注金额
	private String earnings;		// 盈利预期
	private Long multiple;		// 倍数
	private String totalloss;		// 当前批次亏损金额
	private String profitratioafterdeductinglosses;		// 扣除亏损盈利比例
	private String profitafterdeductinglosses;		// 扣除亏损盈利金额
	private String currentprofitratio;		// 当期最大盈利比
	private String currentprofitamount;		// 当期最大应力金额
	private String combination;		// 组合编号
	private String realamount;		// 真实投注金额
	private Long gdmsid;		// 固定盈利模式id
	private Long pcjlid;		// 批次记录id
	private String pcxh;
	private String status;
	private String xzsm;
	private String llzg;
	private String pctyj;

	public String getLlzg() {
		return llzg;
	}

	public void setLlzg(String llzg) {
		this.llzg = llzg;
	}

	public String getPctyj() {
		return pctyj;
	}

	public void setPctyj(String pctyj) {
		this.pctyj = pctyj;
	}

	public String getPctylbl() {
		return pctylbl;
	}

	public void setPctylbl(String pctylbl) {
		this.pctylbl = pctylbl;
	}

	private String pctylbl;

	public String getXzsm() {
		return xzsm;
	}

	public void setXzsm(String xzsm) {
		this.xzsm = xzsm;
	}

	@Override
	public String getStatus() {
		return status;
	}

	@Override
	public void setStatus(String status) {
		this.status = status;
	}

	public String getPcxh() {
		return pcxh;
	}

	public void setPcxh(String pcxh) {
		this.pcxh = pcxh;
	}

	public BkpUserbets() {
		this(null);
	}

	public BkpUserbets(String id){
		super(id);
	}
	
	public String getUsercode() {
		return usercode;
	}

	public void setUsercode(String usercode) {
		this.usercode = usercode;
	}
	
	@Length(min=0, max=30, message="联赛长度不能超过 30 个字符")
	public String getLeague() {
		return league;
	}

	public void setLeague(String league) {
		this.league = league;
	}
	
	@Length(min=0, max=30, message="比赛时间长度不能超过 30 个字符")
	public String getBdate() {
		return bdate;
	}

	public void setBdate(String bdate) {
		this.bdate = bdate;
	}
	
	@Length(min=0, max=30, message="次序长度不能超过 30 个字符")
	public String getNum() {
		return num;
	}

	public void setNum(String num) {
		this.num = num;
	}
	
	@Length(min=0, max=50, message="主队长度不能超过 50 个字符")
	public String getHometeam() {
		return hometeam;
	}

	public void setHometeam(String hometeam) {
		this.hometeam = hometeam;
	}
	
	@Length(min=0, max=50, message="客队长度不能超过 50 个字符")
	public String getAwayteam() {
		return awayteam;
	}

	public void setAwayteam(String awayteam) {
		this.awayteam = awayteam;
	}
	
	@Length(min=0, max=30, message="创建时间长度不能超过 30 个字符")
	public String getCreatetime() {
		return createtime;
	}

	public void setCreatetime(String createtime) {
		this.createtime = createtime;
	}
	
	@Length(min=0, max=30, message="玩法长度不能超过 30 个字符")
	public String getBettingmethod() {
		return bettingmethod;
	}

	public void setBettingmethod(String bettingmethod) {
		this.bettingmethod = bettingmethod;
	}
	
	@Length(min=0, max=50, message="用户选择长度不能超过 50 个字符")
	public String getPick() {
		return pick;
	}

	public void setPick(String pick) {
		this.pick = pick;
	}
	
	@Length(min=0, max=30, message="此次比赛结果长度不能超过 30 个字符")
	public String getMatchresults() {
		return matchresults;
	}

	public void setMatchresults(String matchresults) {
		this.matchresults = matchresults;
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
	
	@Length(min=0, max=30, message="当前批次亏损金额长度不能超过 30 个字符")
	public String getTotalloss() {
		return totalloss;
	}

	public void setTotalloss(String totalloss) {
		this.totalloss = totalloss;
	}
	
	@Length(min=0, max=30, message="扣除亏损盈利比例长度不能超过 30 个字符")
	public String getProfitratioafterdeductinglosses() {
		return profitratioafterdeductinglosses;
	}

	public void setProfitratioafterdeductinglosses(String profitratioafterdeductinglosses) {
		this.profitratioafterdeductinglosses = profitratioafterdeductinglosses;
	}
	
	@Length(min=0, max=30, message="扣除亏损盈利金额长度不能超过 30 个字符")
	public String getProfitafterdeductinglosses() {
		return profitafterdeductinglosses;
	}

	public void setProfitafterdeductinglosses(String profitafterdeductinglosses) {
		this.profitafterdeductinglosses = profitafterdeductinglosses;
	}
	
	@Length(min=0, max=30, message="当期最大盈利比长度不能超过 30 个字符")
	public String getCurrentprofitratio() {
		return currentprofitratio;
	}

	public void setCurrentprofitratio(String currentprofitratio) {
		this.currentprofitratio = currentprofitratio;
	}
	
	@Length(min=0, max=30, message="当期最大应力金额长度不能超过 30 个字符")
	public String getCurrentprofitamount() {
		return currentprofitamount;
	}

	public void setCurrentprofitamount(String currentprofitamount) {
		this.currentprofitamount = currentprofitamount;
	}
	
	public String getCombination() {
		return combination;
	}

	public void setCombination(String combination) {
		this.combination = combination;
	}
	
	@Length(min=0, max=30, message="真实投注金额长度不能超过 30 个字符")
	public String getRealamount() {
		return realamount;
	}

	public void setRealamount(String realamount) {
		this.realamount = realamount;
	}
	
	public Long getGdmsid() {
		return gdmsid;
	}

	public void setGdmsid(Long gdmsid) {
		this.gdmsid = gdmsid;
	}
	
	public Long getPcjlid() {
		return pcjlid;
	}

	public void setPcjlid(Long pcjlid) {
		this.pcjlid = pcjlid;
	}
	
}