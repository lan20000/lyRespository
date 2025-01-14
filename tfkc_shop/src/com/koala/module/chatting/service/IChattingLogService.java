﻿package com.koala.module.chatting.service;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import com.koala.core.query.support.IPageList;
import com.koala.core.query.support.IQueryObject;
import com.koala.module.chatting.domain.ChattingLog;

public interface IChattingLogService {
	/**
	 * 保存一个ChattingLog，如果保存成功返回true，否则返回false
	 * 
	 * @param instance
	 * @return 是否保存成功
	 */
	boolean save(ChattingLog instance);
	
	/**
	 * 根据一个ID得到ChattingLog
	 * 
	 * @param id
	 * @return
	 */
	ChattingLog getObjById(Long id);
	
	/**
	 * 删除一个ChattingLog
	 * 
	 * @param id
	 * @return
	 */
	boolean delete(Long id);
	
	/**
	 * 批量删除ChattingLog
	 * 
	 * @param ids
	 * @return
	 */
	boolean batchDelete(List<Serializable> ids);
	
	/**
	 * 通过一个查询对象得到ChattingLog
	 * 
	 * @param properties
	 * @return
	 */
	IPageList list(IQueryObject properties);
	
	/**
	 * 更新一个ChattingLog
	 * 
	 * @param id
	 *            需要更新的ChattingLog的id
	 * @param dir
	 *            需要更新的ChattingLog
	 */
	boolean update(ChattingLog instance);
	/**
	 * 
	 * @param query
	 * @param params
	 * @param begin
	 * @param max
	 * @return
	 */
	List<ChattingLog> query(String query, Map params, int begin, int max);
}
