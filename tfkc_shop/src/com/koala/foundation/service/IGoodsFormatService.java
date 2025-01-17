﻿package com.koala.foundation.service;

import java.io.Serializable;
import java.util.List;
import java.util.Map;
import com.koala.core.query.support.IPageList;
import com.koala.core.query.support.IQueryObject;

import com.koala.foundation.domain.GoodsFormat;

public interface IGoodsFormatService {
	/**
	 * 保存一个GoodsFormat，如果保存成功返回true，否则返回false
	 * 
	 * @param instance
	 * @return 是否保存成功
	 */
	boolean save(GoodsFormat instance);
	
	/**
	 * 根据一个ID得到GoodsFormat
	 * 
	 * @param id
	 * @return
	 */
	GoodsFormat getObjById(Long id);
	
	/**
	 * 删除一个GoodsFormat
	 * 
	 * @param id
	 * @return
	 */
	boolean delete(Long id);
	
	/**
	 * 批量删除GoodsFormat
	 * 
	 * @param ids
	 * @return
	 */
	boolean batchDelete(List<Serializable> ids);
	
	/**
	 * 通过一个查询对象得到GoodsFormat
	 * 
	 * @param properties
	 * @return
	 */
	IPageList list(IQueryObject properties);
	
	/**
	 * 更新一个GoodsFormat
	 * 
	 * @param id
	 *            需要更新的GoodsFormat的id
	 * @param dir
	 *            需要更新的GoodsFormat
	 */
	boolean update(GoodsFormat instance);
	/**
	 * 
	 * @param query
	 * @param params
	 * @param begin
	 * @param max
	 * @return
	 */
	List<GoodsFormat> query(String query, Map params, int begin, int max);
}
