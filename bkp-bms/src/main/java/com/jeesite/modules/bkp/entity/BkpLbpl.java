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
 * 立博的主场各赔率Entity
 * @author Lucifer
 * @version 2021-02-20
 */
@Table(name="bkp_lbpl", alias="a", columns={
		@Column(name="id", attrName="id", label="id", isPK=true),
		@Column(name="league", attrName="league", label="联赛"),
		@Column(name="time", attrName="time", label="比赛时间"),
		@Column(name="hometeam", attrName="hometeam", label="主队"),
		@Column(name="vistteam", attrName="vistteam", label="客队"),
		@Column(name="h", attrName="h", label="胜的赔率"),
		@Column(name="d", attrName="d", label="平的赔率"),
		@Column(name="a", attrName="a", label="负的赔率"),
		@Column(name="score", attrName="score", label="比赛比分"),
		@Column(name="result", attrName="result", label="比赛结果"),
	}, orderBy="a.id DESC"
)
public class BkpLbpl extends DataEntity<BkpLbpl> {
	
	private static final long serialVersionUID = 1L;
	private String league;		// 联赛
	private String time;		// 比赛时间
	private String hometeam;		// 主队
	private String vistteam;		// 客队
	private String h;		// 胜的赔率
	private String d;		// 平的赔率
	private String a;		// 负的赔率
	private String score;		// 比赛比分
	private String result;		// 比赛结果
	
	public BkpLbpl() {
		this(null);
	}

	public BkpLbpl(String id){
		super(id);
	}
	
	@Length(min=0, max=30, message="联赛长度不能超过 30 个字符")
	public String getLeague() {
		return league;
	}

	public void setLeague(String league) {
		this.league = league;
	}
	
	@Length(min=0, max=30, message="比赛时间长度不能超过 30 个字符")
	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}
	
	@Length(min=0, max=30, message="主队长度不能超过 30 个字符")
	public String getHometeam() {
		return hometeam;
	}

	public void setHometeam(String hometeam) {
		this.hometeam = hometeam;
	}
	
	@Length(min=0, max=30, message="客队长度不能超过 30 个字符")
	public String getVistteam() {
		return vistteam;
	}

	public void setVistteam(String vistteam) {
		this.vistteam = vistteam;
	}
	
	@Length(min=0, max=30, message="胜的赔率长度不能超过 30 个字符")
	public String getH() {
		return h;
	}

	public void setH(String h) {
		this.h = h;
	}
	
	@Length(min=0, max=30, message="平的赔率长度不能超过 30 个字符")
	public String getD() {
		return d;
	}

	public void setD(String d) {
		this.d = d;
	}
	
	@Length(min=0, max=30, message="负的赔率长度不能超过 30 个字符")
	public String getA() {
		return a;
	}

	public void setA(String a) {
		this.a = a;
	}
	
	@Length(min=0, max=30, message="比赛比分长度不能超过 30 个字符")
	public String getScore() {
		return score;
	}

	public void setScore(String score) {
		this.score = score;
	}
	
	@Length(min=0, max=30, message="比赛结果长度不能超过 30 个字符")
	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}
	
}