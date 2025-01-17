﻿package com.koala.module.sns.service;

import java.io.Serializable;
import java.util.List;
import java.util.Map;
import com.koala.core.query.support.IPageList;
import com.koala.core.query.support.IQueryObject;
import com.koala.module.sns.domain.UserDynamic;


public interface IUserDynamicService {
	/**
	 * 保存一个UserDynamic，如果保存成功返回true，否则返回false
	 * 
	 * @param instance
	 * @return 是否保存成功
	 */
	boolean save(UserDynamic instance);
	
	/**
	 * 根据一个ID得到UserDynamic
	 * 
	 * @param id
	 * @return
	 */
	UserDynamic getObjById(Long id);
	
	/**
	 * 删除一个UserDynamic
	 * 
	 * @param id
	 * @return
	 */
	boolean delete(Long id);
	
	/**
	 * 批量删除UserDynamic
	 * 
	 * @param ids
	 * @return
	 */
	boolean batchDelete(List<Serializable> ids);
	
	/**
	 * 通过一个查询对象得到UserDynamic
	 * 
	 * @param properties
	 * @return
	 */
	IPageList list(IQueryObject properties);
	
	/**
	 * 更新一个UserDynamic
	 * 
	 * @param id
	 *            需要更新的UserDynamic的id
	 * @param dir
	 *            需要更新的UserDynamic
	 */
	boolean update(UserDynamic instance);
	/**
	 * 
	 * @param query
	 * @param params
	 * @param begin
	 * @param max
	 * @return
	 */
	List<UserDynamic> query(String query, Map params, int begin, int max);
}
