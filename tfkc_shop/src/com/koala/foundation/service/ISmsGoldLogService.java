﻿package com.koala.foundation.service;

import java.io.Serializable;
import java.util.List;
import java.util.Map;
import com.koala.core.query.support.IPageList;
import com.koala.core.query.support.IQueryObject;

import com.koala.foundation.domain.SmsGoldLog;

public interface ISmsGoldLogService {
	/**
	 * 保存一个SmsGoldLog，如果保存成功返回true，否则返回false
	 * 
	 * @param instance
	 * @return 是否保存成功
	 */
	boolean save(SmsGoldLog instance);
	
	/**
	 * 根据一个ID得到SmsGoldLog
	 * 
	 * @param id
	 * @return
	 */
	SmsGoldLog getObjById(Long id);
	
	/**
	 * 删除一个SmsGoldLog
	 * 
	 * @param id
	 * @return
	 */
	boolean delete(Long id);
	
	/**
	 * 批量删除SmsGoldLog
	 * 
	 * @param ids
	 * @return
	 */
	boolean batchDelete(List<Serializable> ids);
	
	/**
	 * 通过一个查询对象得到SmsGoldLog
	 * 
	 * @param properties
	 * @return
	 */
	IPageList list(IQueryObject properties);
	
	/**
	 * 更新一个SmsGoldLog
	 * 
	 * @param id
	 *            需要更新的SmsGoldLog的id
	 * @param dir
	 *            需要更新的SmsGoldLog
	 */
	boolean update(SmsGoldLog instance);
	/**
	 * 
	 * @param query
	 * @param params
	 * @param begin
	 * @param max
	 * @return
	 */
	List<SmsGoldLog> query(String query, Map params, int begin, int max);
}
