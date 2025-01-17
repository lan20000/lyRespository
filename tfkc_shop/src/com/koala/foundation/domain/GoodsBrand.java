package com.koala.foundation.domain;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import com.koala.core.constant.Globals;
import com.koala.core.domain.IdEntity;

/**
 * 
 * <p>
 * Title: GoodsBrand.java
 * </p>
 * 
 * <p>
 * Description: (商品)品牌管理类,品牌主要由超级管理员添加或者卖家申请，卖家申请的品牌需要审核后才能够显示
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
 * 
 * @version koala_b2b2c v2.0 2015版
 */
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@Entity
@Table(name = Globals.DEFAULT_TABLE_SUFFIX + "goodsbrand")
public class GoodsBrand extends IdEntity {

	private String name;// 品牌名称
	private int sequence;// 排序
	@OneToOne(fetch = FetchType.LAZY)
	private Accessory brandLogo;// 品牌logo
	private boolean recommend;// 是否推荐
	@Column(columnDefinition = "int default 0")
	private int audit;// 是否通过审核,1为审核通过，0为未审核，-1为审核未通过
	@Column(columnDefinition = "int default 0")
	private int userStatus;// 品牌申请者身份,0为系统管理员，1为卖家
	private long store_id;// 品牌申店铺id。此字段为自V2015版起生效
	@Column(columnDefinition = "LongText")
	private String remark;// 申请备注
	@ManyToMany(mappedBy = "gbs")
	private List<GoodsType> types = new ArrayList<GoodsType>();// 商品类型
	@ManyToOne(fetch = FetchType.LAZY)
	private GoodsBrandCategory category;
	@OneToMany(mappedBy = "goods_brand")
	private List<Goods> goods_list = new ArrayList<Goods>();
	private String first_word;// 品牌首字母，后台管理添加
	private boolean show_index; // 首页显示
	@ManyToOne
	private GoodsClass gc;// 品牌对应的分类，品牌和分类结合起来处理，商家在发布商品的时候，只显示对应分类的品牌信息，品牌默认关联一级分类，商家选择该一级分类下属所有分类均显示该品牌
	@Column(columnDefinition = "int default 0")
	private int mobile_recommend;// 手机客户端推荐， 1为推荐，推荐后在手机客户端首页显示
	@Temporal(TemporalType.DATE)
	private Date mobile_recommendTime;// 手机推荐时间，
	@Column(columnDefinition = "int default 0")
	private int weixin_recommend;// 微商城推荐， 1为推荐，推荐后在微商城首页显示
	@Temporal(TemporalType.DATE)
	private Date weixin_recommendTime;// 微商城推荐时间，

	public GoodsBrand(Long id, Date addTime) {
		super(id, addTime);
		// TODO Auto-generated constructor stub
	}

	public GoodsBrand(Long id, String name) {
		super.setId(id);
		this.name = name;
		// TODO Auto-generated constructor stub
	}

	public GoodsBrand(Long id, String name, Accessory brandLogo) {
		super.setId(id);
		this.name = name;
		this.brandLogo = brandLogo;
		// TODO Auto-generated constructor stub
	}

	public GoodsBrand() {
		super();
		// TODO Auto-generated constructor stub
	}

	public GoodsBrand(Long id, Date addTime, String name) {
		super(id, addTime);
		this.name = name;
		this.brandLogo = brandLogo;
	}

	public int getWeixin_recommend() {
		return weixin_recommend;
	}

	public void setWeixin_recommend(int weixin_recommend) {
		this.weixin_recommend = weixin_recommend;
	}

	public Date getWeixin_recommendTime() {
		return weixin_recommendTime;
	}

	public void setWeixin_recommendTime(Date weixin_recommendTime) {
		this.weixin_recommendTime = weixin_recommendTime;
	}

	public long getStore_id() {
		return store_id;
	}

	public void setStore_id(long store_id) {
		this.store_id = store_id;
	}

	public int getMobile_recommend() {
		return mobile_recommend;
	}

	public void setMobile_recommend(int mobile_recommend) {
		this.mobile_recommend = mobile_recommend;
	}

	public Date getMobile_recommendTime() {
		return mobile_recommendTime;
	}

	public void setMobile_recommendTime(Date mobile_recommendTime) {
		this.mobile_recommendTime = mobile_recommendTime;
	}

	public GoodsClass getGc() {
		return gc;
	}

	public void setGc(GoodsClass gc) {
		this.gc = gc;
	}

	public boolean isShow_index() {
		return show_index;
	}

	public void setShow_index(boolean show_index) {
		this.show_index = show_index;
	}

	public String getFirst_word() {
		return first_word;
	}

	public void setFirst_word(String first_word) {
		this.first_word = first_word;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public int getUserStatus() {
		return userStatus;
	}

	public void setUserStatus(int userStatus) {
		this.userStatus = userStatus;
	}

	public List<Goods> getGoods_list() {
		return goods_list;
	}

	public void setGoods_list(List<Goods> goods_list) {
		this.goods_list = goods_list;
	}

	public GoodsBrandCategory getCategory() {
		return category;
	}

	public void setCategory(GoodsBrandCategory category) {
		this.category = category;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getSequence() {
		return sequence;
	}

	public void setSequence(int sequence) {
		this.sequence = sequence;
	}

	public Accessory getBrandLogo() {
		return brandLogo;
	}

	public void setBrandLogo(Accessory brandLogo) {
		this.brandLogo = brandLogo;
	}

	public boolean isRecommend() {
		return recommend;
	}

	public void setRecommend(boolean recommend) {
		this.recommend = recommend;
	}

	public int getAudit() {
		return audit;
	}

	public void setAudit(int audit) {
		this.audit = audit;
	}

	public List<GoodsType> getTypes() {
		return types;
	}

	public void setTypes(List<GoodsType> types) {
		this.types = types;
	}

}
