/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.test.entity;

import org.hibernate.validator.constraints.Length;

import com.jeesite.common.entity.DataEntity;
import com.jeesite.common.mybatis.annotation.Column;
import com.jeesite.common.mybatis.annotation.Table;
import com.jeesite.common.mybatis.mapper.query.QueryType;

/**
 * testEntity
 * @author Lucifer
 * @version 2020-07-31
 */
@Table(name="test", alias="a", columns={
		@Column(name="id", attrName="id", label="id", isPK=true),
		@Column(name="name", attrName="name", label="姓名", queryType=QueryType.LIKE),
	}, orderBy="a.id DESC"
)
public class Test extends DataEntity<Test> {
	
	private static final long serialVersionUID = 1L;
	private String name;		// 姓名
	
	public Test() {
		this(null);
	}

	public Test(String id){
		super(id);
	}
	
	@Length(min=0, max=30, message="姓名长度不能超过 30 个字符")
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
}