package com.koala.foundation.domain;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import com.koala.core.constant.Globals;
import com.koala.core.domain.IdEntity;

/**
 * 
 * <p>
 * Title: RoleGroup.java
 * </p>
 * 
 * <p>
 * Description: 角色分类管理类，用来管理角色信息，使用角色分类使得角色管理更加清晰
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
@Table(name = Globals.DEFAULT_TABLE_SUFFIX + "rolegroup")
public class RoleGroup extends IdEntity {
	private static final long serialVersionUID = -5430683218883217888L;
	private String name;// 角色分组名称
	private int sequence;// 角色分组序号
	private String type;// 角色分组类型
	@OneToMany(mappedBy = "rg")
	private List<Role> roles = new ArrayList<Role>();// 对应的角色信息

	public RoleGroup() {
		super();
		// TODO Auto-generated constructor stub
	}

	public RoleGroup(Long id, Date addTime) {
		super(id, addTime);
		// TODO Auto-generated constructor stub
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

	public List<Role> getRoles() {
		return roles;
	}

	public void setRoles(List<Role> roles) {
		this.roles = roles;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

}
