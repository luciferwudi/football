/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.teamdata.entity;

import com.jeesite.common.entity.DataEntity;
import com.jeesite.common.mybatis.annotation.Column;
import com.jeesite.common.mybatis.annotation.Table;
import com.jeesite.modules.odds.entity.Odds;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;

/**
 * teamdataEntity
 * @author Lucifer
 * @version 2020-07-31
 */
@Table(name="teamdata", alias="a", columns={
		@Column(name="id", attrName="id", label="id", isPK=true),
		@Column(name="teamdataid", attrName="teamdataid", label="teamdataid"),
		@Column(name="num", attrName="num", label="时间及次序如周二002"),
		@Column(name="date", attrName="date", label="时间 如2019-07-29"),
		@Column(name="time", attrName="time", label="当天具体时间 如18", comment="当天具体时间 如18:30:00"),
		@Column(name="b_date", attrName="bdate", label="下注时间"),
		@Column(name="status", attrName="status", label="status", isUpdate=false),
		@Column(name="hot", attrName="hot", label="hot"),
		@Column(name="l_id", attrName="lid", label="赛事id"),
		@Column(name="l_cn", attrName="lcn", label="赛事全称"),
		@Column(name="h_id", attrName="hid", label="主队id"),
		@Column(name="h_cn", attrName="hcn", label="主队全称"),
		@Column(name="a_id", attrName="aid", label="客队id"),
		@Column(name="a_cn", attrName="acn", label="客队全称"),
		@Column(name="index_show", attrName="indexShow", label="index_show"),
		@Column(name="show", attrName="show", label="show"),
		@Column(name="l_cn_abbr", attrName="lcnAbbr", label="赛事简称"),
		@Column(name="h_cn_abbr", attrName="hcnAbbr", label="主队简称"),
		@Column(name="a_cn_abbr", attrName="acnAbbr", label="客队简称"),
		@Column(name="h_order", attrName="horder", label="主队赛事及次序如[日超17]"),
		@Column(name="a_order", attrName="aorder", label="客队赛事及次序如[日超9]"),
		@Column(name="h_id_dc", attrName="hidDc", label="h_id_dc"),
		@Column(name="a_id_dc", attrName="aidDc", label="a_id_dc"),
		@Column(name="l_background_color", attrName="lbackgroundColor", label="赛事的背景颜色"),
		@Column(name="weather", attrName="weather", label="当天天气"),
		@Column(name="result", attrName="result", label="比赛结果"),
	}, orderBy="a.id DESC"
)
public class Teamdata extends DataEntity<Teamdata> {
	
	private static final long serialVersionUID = 1L;
	private String teamdataid;		// teamdataid
	private String num;		// 时间及次序如周二002
	private String date;		// 时间 如2019-07-29
	private String time;		// 当天具体时间 如18:30:00
	private String bdate;		// 下注时间
	private String hot;		// hot
	private String lid;		// 赛事id
	private String lcn;		// 赛事全称
	private String hid;		// 主队id
	private String hcn;		// 主队全称
	private String aid;		// 客队id
	private String acn;		// 客队全称
	private String indexShow;		// index_show
	private String show;		// show
	private String lcnAbbr;		// 赛事简称
	private String hcnAbbr;		// 主队简称
	private String acnAbbr;		// 客队简称
	private String horder;		// 主队赛事及次序如[日超17]
	private String aorder;		// 客队赛事及次序如[日超9]
	private String hidDc;		// h_id_dc
	private String aidDc;		// a_id_dc
	private String lbackgroundColor;		// 赛事的背景颜色
	private String weather;		// 当天天气
	private Odds had;
	private Odds hhad;
	private String result;
	private String jg;

	public String getJg() {
		return jg;
	}

	public void setJg(String jg) {
		this.jg = jg;
	}

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

	public Odds getHad() {
		return had;
	}

	public void setHad(Odds had) {
		this.had = had;
	}

	public Odds getHhad() {
		return hhad;
	}

	public void setHhad(Odds hhad) {
		this.hhad = hhad;
	}

	public Teamdata() {
		this(null);
	}

	public Teamdata(String id){
		super(id);
	}
	
	@NotBlank(message="teamdataid不能为空")
	@Length(min=0, max=30, message="teamdataid长度不能超过 30 个字符")
	public String getTeamdataid() {
		return id;
	}

	public void setTeamdataid(String teamdataid) {
		this.teamdataid = id;
	}
	
	@NotBlank(message="时间及次序如周二002不能为空")
	@Length(min=0, max=30, message="时间及次序如周二002长度不能超过 30 个字符")
	public String getNum() {
		return num;
	}

	public void setNum(String num) {
		this.num = num;
	}
	
	@NotBlank(message="时间 如2019-07-29不能为空")
	@Length(min=0, max=30, message="时间 如2019-07-29长度不能超过 30 个字符")
	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}
	
	@NotBlank(message="当天具体时间 如18不能为空")
	@Length(min=0, max=30, message="当天具体时间 如18长度不能超过 30 个字符")
	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}
	
	@Length(min=0, max=30, message="下注时间长度不能超过 30 个字符")
	public String getBdate() {
		return bdate;
	}

	public void setBdate(String bdate) {
		this.bdate = bdate;
	}
	
	@Length(min=0, max=30, message="hot长度不能超过 30 个字符")
	public String getHot() {
		return hot;
	}

	public void setHot(String hot) {
		this.hot = hot;
	}
	
	@Length(min=0, max=30, message="赛事id长度不能超过 30 个字符")
	public String getLid() {
		return lid;
	}

	public void setLid(String lid) {
		this.lid = lid;
	}
	
	@Length(min=0, max=30, message="赛事全称长度不能超过 30 个字符")
	public String getLcn() {
		return lcn;
	}

	public void setLcn(String lcn) {
		this.lcn = lcn;
	}
	
	@Length(min=0, max=30, message="主队id长度不能超过 30 个字符")
	public String getHid() {
		return hid;
	}

	public void setHid(String hid) {
		this.hid = hid;
	}
	
	@Length(min=0, max=30, message="主队全称长度不能超过 30 个字符")
	public String getHcn() {
		return hcn;
	}

	public void setHcn(String hcn) {
		this.hcn = hcn;
	}
	
	@Length(min=0, max=30, message="客队id长度不能超过 30 个字符")
	public String getAid() {
		return aid;
	}

	public void setAid(String aid) {
		this.aid = aid;
	}
	
	@Length(min=0, max=30, message="客队全称长度不能超过 30 个字符")
	public String getAcn() {
		return acn;
	}

	public void setAcn(String acn) {
		this.acn = acn;
	}
	
	@Length(min=0, max=30, message="index_show长度不能超过 30 个字符")
	public String getIndexShow() {
		return indexShow;
	}

	public void setIndexShow(String indexShow) {
		this.indexShow = indexShow;
	}
	
	@Length(min=0, max=30, message="show长度不能超过 30 个字符")
	public String getShow() {
		return show;
	}

	public void setShow(String show) {
		this.show = show;
	}
	
	@NotBlank(message="赛事简称不能为空")
	@Length(min=0, max=30, message="赛事简称长度不能超过 30 个字符")
	public String getLcnAbbr() {
		return lcnAbbr;
	}

	public void setLcnAbbr(String lcnAbbr) {
		this.lcnAbbr = lcnAbbr;
	}
	
	@NotBlank(message="主队简称不能为空")
	@Length(min=0, max=30, message="主队简称长度不能超过 30 个字符")
	public String getHcnAbbr() {
		return hcnAbbr;
	}

	public void setHcnAbbr(String hcnAbbr) {
		this.hcnAbbr = hcnAbbr;
	}
	
	@NotBlank(message="客队简称不能为空")
	@Length(min=0, max=30, message="客队简称长度不能超过 30 个字符")
	public String getAcnAbbr() {
		return acnAbbr;
	}

	public void setAcnAbbr(String acnAbbr) {
		this.acnAbbr = acnAbbr;
	}
	
	@NotBlank(message="主队赛事及次序如[日超17]不能为空")
	@Length(min=0, max=30, message="主队赛事及次序如[日超17]长度不能超过 30 个字符")
	public String getHorder() {
		return horder;
	}

	public void setHorder(String horder) {
		this.horder = horder;
	}
	
	@NotBlank(message="客队赛事及次序如[日超9]不能为空")
	@Length(min=0, max=30, message="客队赛事及次序如[日超9]长度不能超过 30 个字符")
	public String getAorder() {
		return aorder;
	}

	public void setAorder(String aorder) {
		this.aorder = aorder;
	}
	
	@Length(min=0, max=30, message="h_id_dc长度不能超过 30 个字符")
	public String getHidDc() {
		return hidDc;
	}

	public void setHidDc(String hidDc) {
		this.hidDc = hidDc;
	}
	
	@Length(min=0, max=30, message="a_id_dc长度不能超过 30 个字符")
	public String getAidDc() {
		return aidDc;
	}

	public void setAidDc(String aidDc) {
		this.aidDc = aidDc;
	}
	
	@NotBlank(message="赛事的背景颜色不能为空")
	@Length(min=0, max=30, message="赛事的背景颜色长度不能超过 30 个字符")
	public String getLbackgroundColor() {
		return lbackgroundColor;
	}

	public void setLbackgroundColor(String lbackgroundColor) {
		this.lbackgroundColor = lbackgroundColor;
	}
	
	@Length(min=0, max=30, message="当天天气长度不能超过 30 个字符")
	public String getWeather() {
		return weather;
	}

	public void setWeather(String weather) {
		this.weather = weather;
	}
	
}