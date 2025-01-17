package com.koala.foundation.domain;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import com.koala.core.constant.Globals;
import com.koala.core.domain.IdEntity;

/**
 * 
 * <p>
 * Title: TransArea.java
 * </p>
 * 
 * <p>
 * Description:运费区域管理类，用来管理配送模板的配送区域，如：华东->安徽->安庆
 * </p>
 * 
 * <p>
 * Copyright: Copyright (c) 2015
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
@Table(name = Globals.DEFAULT_TABLE_SUFFIX + "trans_area")
public class TransArea extends IdEntity {
	private String areaName;// 区域名称
	@OneToMany(mappedBy = "parent", cascade = CascadeType.REMOVE)
	private List<TransArea> childs = new ArrayList<TransArea>();// 下级区域
	@ManyToOne(fetch = FetchType.LAZY)
	private TransArea parent;// 上级区域
	private int sequence;// 序号
	private int level;// 层级

	public TransArea() {
		super();
		// TODO Auto-generated constructor stub
	}

	public TransArea(Long id, Date addTime) {
		super(id, addTime);
		// TODO Auto-generated constructor stub
	}

	public String getAreaName() {
		return areaName;
	}

	public void setAreaName(String areaName) {
		this.areaName = areaName;
	}

	public List<TransArea> getChilds() {
		return childs;
	}

	public void setChilds(List<TransArea> childs) {
		this.childs = childs;
	}

	public TransArea getParent() {
		return parent;
	}

	public void setParent(TransArea parent) {
		this.parent = parent;
	}

	public int getSequence() {
		return sequence;
	}

	public void setSequence(int sequence) {
		this.sequence = sequence;
	}

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}
}
