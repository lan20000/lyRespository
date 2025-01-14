﻿package com.koala.foundation.service;

import java.io.Serializable;
import java.util.List;
import java.util.Map;
import com.koala.core.query.support.IPageList;
import com.koala.core.query.support.IQueryObject;

import com.koala.foundation.domain.Subject;

public interface ISubjectService {
	/**
	 * 保存一个Subject，如果保存成功返回true，否则返回false
	 * 
	 * @param instance
	 * @return 是否保存成功
	 */
	boolean save(Subject instance);
	
	/**
	 * 根据一个ID得到Subject
	 * 
	 * @param id
	 * @return
	 */
	Subject getObjById(Long id);
	
	/**
	 * 删除一个Subject
	 * 
	 * @param id
	 * @return
	 */
	boolean delete(Long id);
	
	/**
	 * 批量删除Subject
	 * 
	 * @param ids
	 * @return
	 */
	boolean batchDelete(List<Serializable> ids);
	
	/**
	 * 通过一个查询对象得到Subject
	 * 
	 * @param properties
	 * @return
	 */
	IPageList list(IQueryObject properties);
	
	/**
	 * 更新一个Subject
	 * 
	 * @param id
	 *            需要更新的Subject的id
	 * @param dir
	 *            需要更新的Subject
	 */
	boolean update(Subject instance);
	/**
	 * 
	 * @param query
	 * @param params
	 * @param begin
	 * @param max
	 * @return
	 */
	List<Subject> query(String query, Map params, int begin, int max);
}
