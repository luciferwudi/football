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
 * 球队球员伤痛表Entity
 * @author Lucifer
 * @version 2020-09-25
 */
@Table(name="bkp_injuries", alias="a", columns={
		@Column(name="id", attrName="id", label="id", isPK=true),
		@Column(name="cnname", attrName="cnname", label="teamdata表里面未缩写的队名"),
		@Column(name="name", attrName="name", label="球员名字", queryType=QueryType.LIKE),
		@Column(name="social", attrName="social", label="身价"),
		@Column(name="position", attrName="position", label="球员定位"),
		@Column(name="cause", attrName="cause", label="伤停原因"),
		@Column(name="time", attrName="time", label="预计复出时间"),
		@Column(name="influence", attrName="influence", label="影响赛事"),
	}, orderBy="a.id DESC"
)
public class BkpInjuries extends DataEntity<BkpInjuries> {
	
	private static final long serialVersionUID = 1L;
	private String cnname;		// teamdata表里面未缩写的队名
	private String name;		// 球员名字
	private String social;		// 身价
	private String position;		// 球员定位
	private String cause;		// 伤停原因
	private String time;		// 预计复出时间
	private String influence;		// 影响赛事
	
	public BkpInjuries() {
		this(null);
	}

	public BkpInjuries(String id){
		super(id);
	}
	
	@Length(min=0, max=255, message="teamdata表里面未缩写的队名长度不能超过 255 个字符")
	public String getCnname() {
		return cnname;
	}

	public void setCnname(String cnname) {
		this.cnname = cnname;
	}
	
	@Length(min=0, max=255, message="球员名字长度不能超过 255 个字符")
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	@Length(min=0, max=50, message="身价长度不能超过 50 个字符")
	public String getSocial() {
		return social;
	}

	public void setSocial(String social) {
		this.social = social;
	}
	
	@Length(min=0, max=50, message="球员定位长度不能超过 50 个字符")
	public String getPosition() {
		return position;
	}

	public void setPosition(String position) {
		this.position = position;
	}
	
	@Length(min=0, max=255, message="伤停原因长度不能超过 255 个字符")
	public String getCause() {
		return cause;
	}

	public void setCause(String cause) {
		this.cause = cause;
	}
	
	@Length(min=0, max=100, message="预计复出时间长度不能超过 100 个字符")
	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}
	
	@Length(min=0, max=100, message="影响赛事长度不能超过 100 个字符")
	public String getInfluence() {
		return influence;
	}

	public void setInfluence(String influence) {
		this.influence = influence;
	}
	
}