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
 * 比赛下注详情表Entity
 * @author Lucifer
 * @version 2020-09-17
 */
@Table(name="bkp_detail", alias="a", columns={
		@Column(name="id", attrName="id", label="id", isPK=true),
		@Column(name="combination", attrName="combination", label="组合编号"),
		@Column(name="combinedresult", attrName="combinedresult", label="组合结果"),
	}, orderBy="a.id DESC"
)
public class BkpDetail extends DataEntity<BkpDetail> {
	
	private static final long serialVersionUID = 1L;
	private String combination;		// 组合编号
	private String combinedresult;		// 组合结果
	
	public BkpDetail() {
		this(null);
	}

	public BkpDetail(String id){
		super(id);
	}
	
	@Length(min=0, max=70, message="组合编号长度不能超过 70 个字符")
	public String getCombination() {
		return combination;
	}

	public void setCombination(String combination) {
		this.combination = combination;
	}
	
	@Length(min=0, max=255, message="组合结果长度不能超过 255 个字符")
	public String getCombinedresult() {
		return combinedresult;
	}

	public void setCombinedresult(String combinedresult) {
		this.combinedresult = combinedresult;
	}
	
}