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
 * 比赛结果推荐表Entity
 * @author Lucifer
 * @version 2020-08-27
 */
@Table(name="bkp_competitionresult", alias="a", columns={
		@Column(name="id", attrName="id", label="id", isPK=true),
		@Column(name="time", attrName="time", label="时间"),
		@Column(name="num", attrName="num", label="次序"),
		@Column(name="howtoplay", attrName="howtoplay", label="玩法"),
		@Column(name="league", attrName="league", label="联赛"),
		@Column(name="hometeam", attrName="hometeam", label="主队"),
		@Column(name="awayteam", attrName="awayteam", label="客队"),
		@Column(name="pick", attrName="pick", label="用户选择"),
		@Column(name="combination", attrName="combination", label="组合编号"),
		@Column(name="historicalbattle", attrName="historicalbattle", label="历史对战"),
		@Column(name="probabilityofwinning", attrName="probabilityofwinning", label="获胜概率"),
		@Column(name="historicalwinrate", attrName="historicalwinrate", label="历史对战胜率"),
		@Column(name="creattime", attrName="creattime", label="创建时间"),
		@Column(name="creatby", attrName="creatby", label="创建人"),
	}, orderBy="a.id DESC"
)
public class BkpCompetitionresult extends DataEntity<BkpCompetitionresult> {
	
	private static final long serialVersionUID = 1L;
	private String time;		// 时间
	private String num;		// 次序
	private String howtoplay;		// 玩法
	private String league;		// 联赛
	private String hometeam;		// 主队
	private String awayteam;		// 客队
	private String pick;		// 用户选择
	private Integer combination;		// 组合编号
	private String historicalbattle;		// 历史对战
	private String probabilityofwinning;		// 获胜概率
	private String historicalwinrate;		// 历史对战胜率
	private String creattime;		// 创建时间
	private String creatby;		// 创建人
	
	public BkpCompetitionresult() {
		this(null);
	}

	public BkpCompetitionresult(String id){
		super(id);
	}
	
	@Length(min=0, max=30, message="时间长度不能超过 30 个字符")
	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}
	
	@Length(min=0, max=30, message="次序长度不能超过 30 个字符")
	public String getNum() {
		return num;
	}

	public void setNum(String num) {
		this.num = num;
	}
	
	@Length(min=0, max=30, message="玩法长度不能超过 30 个字符")
	public String getHowtoplay() {
		return howtoplay;
	}

	public void setHowtoplay(String howtoplay) {
		this.howtoplay = howtoplay;
	}
	
	@Length(min=0, max=30, message="联赛长度不能超过 30 个字符")
	public String getLeague() {
		return league;
	}

	public void setLeague(String league) {
		this.league = league;
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
	
	@Length(min=0, max=50, message="用户选择长度不能超过 50 个字符")
	public String getPick() {
		return pick;
	}

	public void setPick(String pick) {
		this.pick = pick;
	}
	
	public Integer getCombination() {
		return combination;
	}

	public void setCombination(Integer combination) {
		this.combination = combination;
	}
	
	@Length(min=0, max=255, message="历史对战长度不能超过 255 个字符")
	public String getHistoricalbattle() {
		return historicalbattle;
	}

	public void setHistoricalbattle(String historicalbattle) {
		this.historicalbattle = historicalbattle;
	}
	
	@Length(min=0, max=255, message="获胜概率长度不能超过 255 个字符")
	public String getProbabilityofwinning() {
		return probabilityofwinning;
	}

	public void setProbabilityofwinning(String probabilityofwinning) {
		this.probabilityofwinning = probabilityofwinning;
	}
	
	@Length(min=0, max=255, message="历史对战胜率长度不能超过 255 个字符")
	public String getHistoricalwinrate() {
		return historicalwinrate;
	}

	public void setHistoricalwinrate(String historicalwinrate) {
		this.historicalwinrate = historicalwinrate;
	}
	
	@Length(min=0, max=30, message="创建时间长度不能超过 30 个字符")
	public String getCreattime() {
		return creattime;
	}

	public void setCreattime(String creattime) {
		this.creattime = creattime;
	}
	
	@Length(min=0, max=50, message="创建人长度不能超过 50 个字符")
	public String getCreatby() {
		return creatby;
	}

	public void setCreatby(String creatby) {
		this.creatby = creatby;
	}
	
}