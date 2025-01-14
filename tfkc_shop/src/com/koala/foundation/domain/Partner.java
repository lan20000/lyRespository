package com.koala.foundation.domain;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import com.koala.core.constant.Globals;
import com.koala.core.domain.IdEntity;

/**
 * 
 * <p>
 * Title: Partner.java
 * </p>
 * 
 * <p>
 * Description:网站管理 合作伙伴,在首页底部合作伙伴位置显示
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
@Table(name = Globals.DEFAULT_TABLE_SUFFIX + "partner")
public class Partner extends IdEntity {
	private int sequence;// 排序
	private String url;// 连接
	private String title;// 标题
	@OneToOne(fetch = FetchType.LAZY)
	private Accessory image;// 标识图片

	public Partner() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Partner(Long id, Date addTime) {
		super(id, addTime);
		// TODO Auto-generated constructor stub
	}

	public int getSequence() {
		return sequence;
	}

	public void setSequence(int sequence) {
		this.sequence = sequence;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Accessory getImage() {
		return image;
	}

	public void setImage(Accessory image) {
		this.image = image;
	}

}
