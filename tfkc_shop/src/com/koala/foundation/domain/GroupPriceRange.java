package com.koala.foundation.domain;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import com.koala.core.constant.Globals;
import com.koala.core.domain.IdEntity;

/**
 * 
 * <p>
 * Title: GroupPriceRange.java
 * </p>
 * 
 * <p>
 * Description: 团购价格区间,用在用户快速选择团购商品
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
 * 
 * @version koala_b2b2c v2.0 2015版
 */
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@Entity
@Table(name = Globals.DEFAULT_TABLE_SUFFIX + "group_price_range")
public class GroupPriceRange extends IdEntity {
	private String gpr_name;// 价格区间名称
	private int gpr_begin;// 区间下限
	private int gpr_end;// 区间上限

	public GroupPriceRange() {
		super();
		// TODO Auto-generated constructor stub
	}

	public GroupPriceRange(Long id, Date addTime) {
		super(id, addTime);
		// TODO Auto-generated constructor stub
	}

	public String getGpr_name() {
		return gpr_name;
	}

	public void setGpr_name(String gpr_name) {
		this.gpr_name = gpr_name;
	}

	public int getGpr_begin() {
		return gpr_begin;
	}

	public void setGpr_begin(int gpr_begin) {
		this.gpr_begin = gpr_begin;
	}

	public int getGpr_end() {
		return gpr_end;
	}

	public void setGpr_end(int gpr_end) {
		this.gpr_end = gpr_end;
	}
}
