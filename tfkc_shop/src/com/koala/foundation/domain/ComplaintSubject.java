package com.koala.foundation.domain;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import com.koala.core.constant.Globals;
import com.koala.core.domain.IdEntity;

/**
 * 
 * <p>
 * Title: ComplaintSubject.java
 * </p>
 * 
 * <p>
 * Description: 投诉主题管理类，用来管理投诉主题信息，用户投诉都需要选择一个投诉主题，便于平台归类处理
 * </p>
 * 
 * <p>
 * Copyright: Copyright (c) 2014
 * </p>
 * 
 * <p>
 * Company: 沈阳网之商科技有限公司 www.koala.com
 * </p>
 * 
 * @author erikzhang
 * 
 * @date 2014-4-25
 * 
 * @version koala_b2b2c v2.0 2015版
 */
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@Entity
@Table(name = Globals.DEFAULT_TABLE_SUFFIX + "complaint_subject")
public class ComplaintSubject extends IdEntity {
	private String type;// 卖家seller，买家buyer
	private String title;// 主题
	@Column(columnDefinition = "LongText")
	private String content;// 主题描述

	public ComplaintSubject(Long id, Date addTime) {
		super(id, addTime);
		// TODO Auto-generated constructor stub
	}

	public ComplaintSubject() {
		super();
		// TODO Auto-generated constructor stub
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}
}
