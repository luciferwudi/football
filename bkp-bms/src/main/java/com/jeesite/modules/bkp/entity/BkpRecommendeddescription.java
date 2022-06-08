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
 * 推荐说明表Entity
 * @author Lucifer
 * @version 2020-08-28
 */
@Table(name="bkp_recommendeddescription", alias="a", columns={
		@Column(name="id", attrName="id", label="id", isPK=true),
		@Column(name="team", attrName="team", label="球队"),
		@Column(name="league", attrName="league", label="联赛"),
		@Column(name="probabilityofwinning", attrName="probabilityofwinning", label="获胜概率"),
		@Column(name="historicalwinrate", attrName="historicalwinrate", label="历史交锋胜率"),
		@Column(name="numberofgoals", attrName="numberofgoals", label="最近6场进球数"),
		@Column(name="conceded", attrName="conceded", label="最近6场失球数"),
		@Column(name="status", attrName="status", label="0无效 1有效", isUpdate=false),
		@Column(name="creattime", attrName="creattime", label="创建时间"),
		@Column(name="superiority1", attrName="superiority1", label="优势1"),
		@Column(name="inferior1", attrName="inferior1", label="劣势1"),
		@Column(name="superiority2", attrName="superiority2", label="优势2"),
		@Column(name="inferior2", attrName="inferior2", label="劣势2"),
		@Column(name="superiority3", attrName="superiority3", label="优势3"),
		@Column(name="inferior3", attrName="inferior3", label="劣势3"),
		@Column(name="superiority4", attrName="superiority4", label="优势4"),
		@Column(name="inferior4", attrName="inferior4", label="劣势4"),
		@Column(name="superiority5", attrName="superiority5", label="优势5"),
		@Column(name="inferior5", attrName="inferior5", label="劣势5"),
		@Column(name="href", attrName="href", label="链接"),
	}, orderBy="a.id DESC"
)
public class BkpRecommendeddescription extends DataEntity<BkpRecommendeddescription> {
	
	private static final long serialVersionUID = 1L;
	private String team;		// 球队
	private String league;		// 联赛
	private String probabilityofwinning;		// 获胜概率
	private String historicalwinrate;		// 历史交锋胜率
	private String numberofgoals;		// 最近6场进球数
	private String conceded;		// 最近6场失球数
	private String creattime;		// 创建时间
	private String superiority1;		// 优势1
	private String inferior1;		// 劣势1
	private String superiority2;		// 优势2
	private String inferior2;		// 劣势2
	private String superiority3;		// 优势3
	private String inferior3;		// 劣势3
	private String superiority4;		// 优势4
	private String inferior4;		// 劣势4
	private String superiority5;		// 优势5
	private String inferior5;		// 劣势5
	private String href;

	public String getHref() {
		return href;
	}

	public void setHref(String href) {
		this.href = href;
	}

	public BkpRecommendeddescription() {
		this(null);
	}

	public BkpRecommendeddescription(String id){
		super(id);
	}
	
	@Length(min=0, max=30, message="球队长度不能超过 30 个字符")
	public String getTeam() {
		return team;
	}

	public void setTeam(String team) {
		this.team = team;
	}
	
	@Length(min=0, max=30, message="联赛长度不能超过 30 个字符")
	public String getLeague() {
		return league;
	}

	public void setLeague(String league) {
		this.league = league;
	}
	
	@Length(min=0, max=50, message="获胜概率长度不能超过 50 个字符")
	public String getProbabilityofwinning() {
		return probabilityofwinning;
	}

	public void setProbabilityofwinning(String probabilityofwinning) {
		this.probabilityofwinning = probabilityofwinning;
	}
	
	@Length(min=0, max=50, message="历史交锋胜率长度不能超过 50 个字符")
	public String getHistoricalwinrate() {
		return historicalwinrate;
	}

	public void setHistoricalwinrate(String historicalwinrate) {
		this.historicalwinrate = historicalwinrate;
	}
	
	public String getNumberofgoals() {
		return numberofgoals;
	}

	public void setNumberofgoals(String numberofgoals) {
		this.numberofgoals = numberofgoals;
	}
	
	public String getConceded() {
		return conceded;
	}

	public void setConceded(String conceded) {
		this.conceded = conceded;
	}
	
	@Length(min=0, max=50, message="创建时间长度不能超过 50 个字符")
	public String getCreattime() {
		return creattime;
	}

	public void setCreattime(String creattime) {
		this.creattime = creattime;
	}
	
	@Length(min=0, max=300, message="优势1长度不能超过 300 个字符")
	public String getSuperiority1() {
		return superiority1;
	}

	public void setSuperiority1(String superiority1) {
		this.superiority1 = superiority1;
	}
	
	@Length(min=0, max=300, message="劣势1长度不能超过 300 个字符")
	public String getInferior1() {
		return inferior1;
	}

	public void setInferior1(String inferior1) {
		this.inferior1 = inferior1;
	}
	
	@Length(min=0, max=300, message="优势2长度不能超过 300 个字符")
	public String getSuperiority2() {
		return superiority2;
	}

	public void setSuperiority2(String superiority2) {
		this.superiority2 = superiority2;
	}
	
	@Length(min=0, max=300, message="劣势2长度不能超过 300 个字符")
	public String getInferior2() {
		return inferior2;
	}

	public void setInferior2(String inferior2) {
		this.inferior2 = inferior2;
	}
	
	@Length(min=0, max=300, message="优势3长度不能超过 300 个字符")
	public String getSuperiority3() {
		return superiority3;
	}

	public void setSuperiority3(String superiority3) {
		this.superiority3 = superiority3;
	}
	
	@Length(min=0, max=300, message="劣势3长度不能超过 300 个字符")
	public String getInferior3() {
		return inferior3;
	}

	public void setInferior3(String inferior3) {
		this.inferior3 = inferior3;
	}
	
	@Length(min=0, max=300, message="优势4长度不能超过 300 个字符")
	public String getSuperiority4() {
		return superiority4;
	}

	public void setSuperiority4(String superiority4) {
		this.superiority4 = superiority4;
	}
	
	@Length(min=0, max=300, message="劣势4长度不能超过 300 个字符")
	public String getInferior4() {
		return inferior4;
	}

	public void setInferior4(String inferior4) {
		this.inferior4 = inferior4;
	}
	
	@Length(min=0, max=300, message="优势5长度不能超过 300 个字符")
	public String getSuperiority5() {
		return superiority5;
	}

	public void setSuperiority5(String superiority5) {
		this.superiority5 = superiority5;
	}
	
	@Length(min=0, max=255, message="劣势5长度不能超过 255 个字符")
	public String getInferior5() {
		return inferior5;
	}

	public void setInferior5(String inferior5) {
		this.inferior5 = inferior5;
	}
	
}