/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.bkp.entity;

import org.hibernate.validator.constraints.Length;
import javax.validation.constraints.NotBlank;

import com.jeesite.common.entity.DataEntity;
import com.jeesite.common.mybatis.annotation.Column;
import com.jeesite.common.mybatis.annotation.Table;
import com.jeesite.common.mybatis.mapper.query.QueryType;

/**
 * bkp_leisEntity
 * @author Lucifer
 * @version 2020-10-13
 */
@Table(name="bkp_leis", alias="a", columns={
		@Column(name="id", attrName="id", label="id", isPK=true),
		@Column(name="hometeam", attrName="hometeam", label="主队"),
		@Column(name="time", attrName="time", label="时间用作比较"),
		@Column(name="vistteam", attrName="vistteam", label="客队"),
		@Column(name="league", attrName="league", label="联赛"),
		@Column(name="homeprobabilityofwinning", attrName="homeprobabilityofwinning", label="主队获胜概率"),
		@Column(name="homehistoricalwinrate", attrName="homehistoricalwinrate", label="主队历史交战胜率"),
		@Column(name="homenumberofgoals", attrName="homenumberofgoals", label="主队最近6场进球数"),
		@Column(name="homeconceded", attrName="homeconceded", label="主队最近6场失球数"),
		@Column(name="vistprobabilityofwinning", attrName="vistprobabilityofwinning", label="客队获胜概率"),
		@Column(name="visthistoricalwinrate", attrName="visthistoricalwinrate", label="客队历史交战胜率"),
		@Column(name="vistnumberofgoals", attrName="vistnumberofgoals", label="客队最近6场进球数"),
		@Column(name="zlqb1", attrName="zlqb1", label="中立情报1"),
		@Column(name="zlqb2", attrName="zlqb2", label="中立情报2"),
		@Column(name="zlqb3", attrName="zlqb3", label="中立情报3"),
		@Column(name="zdbl1", attrName="zdbl1", label="主队不利情报1"),
		@Column(name="zdbl2", attrName="zdbl2", label="主队不利情报2"),
		@Column(name="zdbl3", attrName="zdbl3", label="主队不利情报3"),
		@Column(name="zdyl1", attrName="zdyl1", label="主队有利情报1"),
		@Column(name="zdyl2", attrName="zdyl2", label="主队有利情报2"),
		@Column(name="zdyl3", attrName="zdyl3", label="主队有利情报3"),
		@Column(name="kdbl1", attrName="kdbl1", label="客队不利情报1"),
		@Column(name="kdbl2", attrName="kdbl2", label="客队不利情报2"),
		@Column(name="kdbl3", attrName="kdbl3", label="客队不利情报3"),
		@Column(name="kdyl1", attrName="kdyl1", label="客队有利情报1"),
		@Column(name="kdyl2", attrName="kdyl2", label="客队有利情报2"),
		@Column(name="kdyl3", attrName="kdyl3", label="客队有利情报3"),
        @Column(name="result", attrName="result", label="比赛结果"),
	}, orderBy="a.id DESC"
)
public class BkpLeis extends DataEntity<BkpLeis> {
	
	private static final long serialVersionUID = 1L;
	private String hometeam;		// 主队
	private String time;		// 时间用作比较
	private String vistteam;		// 客队
	private String league;		// 联赛
	private String homeprobabilityofwinning;		// 主队获胜概率
	private String homehistoricalwinrate;		// 主队历史交战胜率
	private String homenumberofgoals;		// 主队最近6场进球数
	private String homeconceded;		// 主队最近6场失球数
	private String vistprobabilityofwinning;		// 客队获胜概率
	private String visthistoricalwinrate;		// 客队历史交战胜率
	private String vistnumberofgoals;		// 客队最近6场进球数
	private String vistconceded;		// 客队最近6场失球数

	private String zlqb1;
	private String zlqb2;
	private String zlqb3;

	private String zdbl1;
	private String zdbl2;
	private String zdbl3;

	private String kdbl1;
	private String kdbl2;

	private String result;

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getZlqb1() {
		return zlqb1;
	}

	public void setZlqb1(String zlqb1) {
		this.zlqb1 = zlqb1;
	}

	public String getZlqb2() {
		return zlqb2;
	}

	public void setZlqb2(String zlqb2) {
		this.zlqb2 = zlqb2;
	}

	public String getZlqb3() {
		return zlqb3;
	}

	public void setZlqb3(String zlqb3) {
		this.zlqb3 = zlqb3;
	}

	public String getZdbl1() {
		return zdbl1;
	}

	public void setZdbl1(String zdbl1) {
		this.zdbl1 = zdbl1;
	}

	public String getZdbl2() {
		return zdbl2;
	}

	public void setZdbl2(String zdbl2) {
		this.zdbl2 = zdbl2;
	}

	public String getZdbl3() {
		return zdbl3;
	}

	public void setZdbl3(String zdbl3) {
		this.zdbl3 = zdbl3;
	}

	public String getKdbl1() {
		return kdbl1;
	}

	public void setKdbl1(String kdbl1) {
		this.kdbl1 = kdbl1;
	}

	public String getKdbl2() {
		return kdbl2;
	}

	public void setKdbl2(String kdbl2) {
		this.kdbl2 = kdbl2;
	}

	public String getKdbl3() {
		return kdbl3;
	}

	public void setKdbl3(String kdbl3) {
		this.kdbl3 = kdbl3;
	}

	public String getZdyl1() {
		return zdyl1;
	}

	public void setZdyl1(String zdyl1) {
		this.zdyl1 = zdyl1;
	}

	public String getZdyl2() {
		return zdyl2;
	}

	public void setZdyl2(String zdyl2) {
		this.zdyl2 = zdyl2;
	}

	public String getZdyl3() {
		return zdyl3;
	}

	public void setZdyl3(String zdyl3) {
		this.zdyl3 = zdyl3;
	}

	public String getKdyl1() {
		return kdyl1;
	}

	public void setKdyl1(String kdyl1) {
		this.kdyl1 = kdyl1;
	}

	public String getKdyl2() {
		return kdyl2;
	}

	public void setKdyl2(String kdyl2) {
		this.kdyl2 = kdyl2;
	}

	public String getKdyl3() {
		return kdyl3;
	}

	public void setKdyl3(String kdyl3) {
		this.kdyl3 = kdyl3;
	}

	private String kdbl3;

	private String zdyl1;
	private String zdyl2;
	private String zdyl3;

	private String kdyl1;
	private String kdyl2;
	private String kdyl3;

	public BkpLeis() {
		this(null);
	}

	public BkpLeis(String id){
		super(id);
	}
	
	@Length(min=0, max=70, message="主队长度不能超过 70 个字符")
	public String getHometeam() {
		return hometeam;
	}

	public void setHometeam(String hometeam) {
		this.hometeam = hometeam;
	}
	
	@Length(min=0, max=70, message="时间用作比较长度不能超过 70 个字符")
	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}
	
	@Length(min=0, max=70, message="客队长度不能超过 70 个字符")
	public String getVistteam() {
		return vistteam;
	}

	public void setVistteam(String vistteam) {
		this.vistteam = vistteam;
	}
	
	@Length(min=0, max=30, message="联赛长度不能超过 30 个字符")
	public String getLeague() {
		return league;
	}

	public void setLeague(String league) {
		this.league = league;
	}
	
	@Length(min=0, max=30, message="主队获胜概率长度不能超过 30 个字符")
	public String getHomeprobabilityofwinning() {
		return homeprobabilityofwinning;
	}

	public void setHomeprobabilityofwinning(String homeprobabilityofwinning) {
		this.homeprobabilityofwinning = homeprobabilityofwinning;
	}
	
	@Length(min=0, max=30, message="主队历史交战胜率长度不能超过 30 个字符")
	public String getHomehistoricalwinrate() {
		return homehistoricalwinrate;
	}

	public void setHomehistoricalwinrate(String homehistoricalwinrate) {
		this.homehistoricalwinrate = homehistoricalwinrate;
	}
	
	@NotBlank(message="主队最近6场进球数不能为空")
	@Length(min=0, max=30, message="主队最近6场进球数长度不能超过 30 个字符")
	public String getHomenumberofgoals() {
		return homenumberofgoals;
	}

	public void setHomenumberofgoals(String homenumberofgoals) {
		this.homenumberofgoals = homenumberofgoals;
	}
	
	@Length(min=0, max=30, message="主队最近6场失球数长度不能超过 30 个字符")
	public String getHomeconceded() {
		return homeconceded;
	}

	public void setHomeconceded(String homeconceded) {
		this.homeconceded = homeconceded;
	}
	
	@Length(min=0, max=30, message="客队获胜概率长度不能超过 30 个字符")
	public String getVistprobabilityofwinning() {
		return vistprobabilityofwinning;
	}

	public void setVistprobabilityofwinning(String vistprobabilityofwinning) {
		this.vistprobabilityofwinning = vistprobabilityofwinning;
	}
	
	@Length(min=0, max=30, message="客队历史交战胜率长度不能超过 30 个字符")
	public String getVisthistoricalwinrate() {
		return visthistoricalwinrate;
	}

	public void setVisthistoricalwinrate(String visthistoricalwinrate) {
		this.visthistoricalwinrate = visthistoricalwinrate;
	}
	
	@Length(min=0, max=30, message="客队最近6场进球数长度不能超过 30 个字符")
	public String getVistnumberofgoals() {
		return vistnumberofgoals;
	}

	public void setVistnumberofgoals(String vistnumberofgoals) {
		this.vistnumberofgoals = vistnumberofgoals;
	}
	
	@Length(min=0, max=30, message="客队最近6场失球数长度不能超过 30 个字符")
	public String getVistconceded() {
		return vistconceded;
	}

	public void setVistconceded(String vistconceded) {
		this.vistconceded = vistconceded;
	}
	
}