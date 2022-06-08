/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.odds.entity;

import com.jeesite.common.entity.DataEntity;
import com.jeesite.common.mybatis.annotation.Column;
import com.jeesite.common.mybatis.annotation.Table;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;

/**
 * oddsEntity
 * @author Lucifer
 * @version 2020-07-31
 */
@Table(name="odds", alias="a", columns={
		@Column(name="id", attrName="id", label="id", isPK=true),
		@Column(name="teamdataid", attrName="teamdataid", label="和teamdata对应的"),
		@Column(name="type", attrName="type", label="类型 had和hhad"),
		@Column(name="a", attrName="a", label="负的赔率"),
		@Column(name="d", attrName="d", label="d平的赔率"),
		@Column(name="h", attrName="h", label="h胜的赔率"),
		@Column(name="goalline", attrName="goalline", label="goalline"),
		@Column(name="p_code", attrName="pcode", label="p_code"),
		@Column(name="o_type", attrName="otype", label="类型"),
		@Column(name="p_id", attrName="pid", label="p_id"),
		@Column(name="p_status", attrName="pstatus", label="p_status"),
		@Column(name="single", attrName="single", label="single"),
		@Column(name="allup", attrName="allup", label="allup"),
		@Column(name="fixedodds", attrName="fixedodds", label="让球"),
		@Column(name="cbt", attrName="cbt", label="cbt"),
		@Column(name="ints", attrName="ints", label="ints"),
		@Column(name="vbt", attrName="vbt", label="vbt"),
		@Column(name="h_trend", attrName="htrend", label="胜的上升还是下降"),
		@Column(name="a_trend", attrName="atrend", label="负的箭头"),
		@Column(name="d_trend", attrName="dtrend", label="平的箭头"),
		@Column(name="l_trend", attrName="ltrend", label="l_trend"),
	}, orderBy="a.id DESC"
)
public class Odds extends DataEntity<Odds> {
	
	private static final long serialVersionUID = 1L;
	private String teamdataid;		// 和teamdata对应的
	private String type;		// 类型 had和hhad
	private String a;		// 负的赔率
	private String d;		// d平的赔率
	private String h;		// h胜的赔率
	private String goalline;		// goalline
	private String pcode;		// p_code
	private String otype;		// 类型
	private String pid;		// p_id
	private String pstatus;		// p_status
	private String single;		// single
	private String allup;		// allup
	private String fixedodds;		// 让球
	private String cbt;		// cbt
	private String ints;		// ints
	private String vbt;		// vbt
	private String htrend;		// 胜的上升还是下降
	private String atrend;		// 负的箭头
	private String dtrend;		// 平的箭头
	private String ltrend;		// l_trend
	
	public Odds() {
		this(null);
	}

	public Odds(String id){
		super(id);
	}
	
	@NotBlank(message="和teamdata对应的不能为空")
	@Length(min=0, max=30, message="和teamdata对应的长度不能超过 30 个字符")
	public String getTeamdataid() {
		return teamdataid;
	}

	public void setTeamdataid(String teamdataid) {
		this.teamdataid = teamdataid == null ? null : teamdataid.trim();
	}
	
	@NotBlank(message="类型 had和hhad不能为空")
	@Length(min=0, max=30, message="类型 had和hhad长度不能超过 30 个字符")
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
	
	@NotBlank(message="负的赔率不能为空")
	@Length(min=0, max=30, message="负的赔率长度不能超过 30 个字符")
	public String getA() {
		return a;
	}

	public void setA(String a) {
		this.a = a;
	}
	
	@NotBlank(message="d平的赔率不能为空")
	@Length(min=0, max=30, message="d平的赔率长度不能超过 30 个字符")
	public String getD() {
		return d;
	}

	public void setD(String d) {
		this.d = d;
	}
	
	@NotBlank(message="h胜的赔率不能为空")
	@Length(min=0, max=30, message="h胜的赔率长度不能超过 30 个字符")
	public String getH() {
		return h;
	}

	public void setH(String h) {
		this.h = h;
	}
	
	@Length(min=0, max=30, message="goalline长度不能超过 30 个字符")
	public String getGoalline() {
		return goalline;
	}

	public void setGoalline(String goalline) {
		this.goalline = goalline;
	}
	
	@Length(min=0, max=30, message="p_code长度不能超过 30 个字符")
	public String getPcode() {
		return pcode;
	}

	public void setPcode(String pcode) {
		this.pcode = pcode;
	}
	
	@NotBlank(message="类型不能为空")
	@Length(min=0, max=30, message="类型长度不能超过 30 个字符")
	public String getOtype() {
		return otype;
	}

	public void setOtype(String otype) {
		this.otype = otype;
	}
	
	@Length(min=0, max=30, message="p_id长度不能超过 30 个字符")
	public String getPid() {
		return pid;
	}

	public void setPid(String pid) {
		this.pid = pid;
	}
	
	@Length(min=0, max=30, message="p_status长度不能超过 30 个字符")
	public String getPstatus() {
		return pstatus;
	}

	public void setPstatus(String pstatus) {
		this.pstatus = pstatus;
	}
	
	@Length(min=0, max=30, message="single长度不能超过 30 个字符")
	public String getSingle() {
		return single;
	}

	public void setSingle(String single) {
		this.single = single;
	}
	
	@Length(min=0, max=30, message="allup长度不能超过 30 个字符")
	public String getAllup() {
		return allup;
	}

	public void setAllup(String allup) {
		this.allup = allup;
	}
	
	@Length(min=0, max=30, message="让球长度不能超过 30 个字符")
	public String getFixedodds() {
		return fixedodds;
	}

	public void setFixedodds(String fixedodds) {
		this.fixedodds = fixedodds;
	}
	
	@Length(min=0, max=30, message="cbt长度不能超过 30 个字符")
	public String getCbt() {
		return cbt;
	}

	public void setCbt(String cbt) {
		this.cbt = cbt;
	}
	
	@Length(min=0, max=30, message="ints长度不能超过 30 个字符")
	public String getInts() {
		return ints;
	}

	public void setInts(String ints) {
		this.ints = ints;
	}
	
	@Length(min=0, max=30, message="vbt长度不能超过 30 个字符")
	public String getVbt() {
		return vbt;
	}

	public void setVbt(String vbt) {
		this.vbt = vbt;
	}
	
	@Length(min=0, max=30, message="胜的上升还是下降长度不能超过 30 个字符")
	public String getHtrend() {
		return htrend;
	}

	public void setHtrend(String htrend) {
		this.htrend = htrend;
	}
	
	@Length(min=0, max=30, message="负的箭头长度不能超过 30 个字符")
	public String getAtrend() {
		return atrend;
	}

	public void setAtrend(String atrend) {
		this.atrend = atrend;
	}
	
	@Length(min=0, max=30, message="平的箭头长度不能超过 30 个字符")
	public String getDtrend() {
		return dtrend;
	}

	public void setDtrend(String dtrend) {
		this.dtrend = dtrend;
	}
	
	@Length(min=0, max=30, message="l_trend长度不能超过 30 个字符")
	public String getLtrend() {
		return ltrend;
	}

	public void setLtrend(String ltrend) {
		this.ltrend = ltrend;
	}
	
}