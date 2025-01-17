﻿package com.koala.foundation.service;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import com.koala.core.query.support.IPageList;
import com.koala.core.query.support.IQueryObject;
import com.koala.foundation.domain.GoodsSpecification;

public interface IGoodsSpecificationService {
	/**
	 * 保存一个GoodsSpecification，如果保存成功返回true，否则返回false
	 * 
	 * @param instance
	 * @return 是否保存成功
	 */
	boolean save(GoodsSpecification instance);
	
	/**
	 * 根据一个ID得到GoodsSpecification
	 * 
	 * @param id
	 * @return
	 */
	GoodsSpecification getObjById(Long id);
	
	/**
	 * 删除一个GoodsSpecification
	 * 
	 * @param id
	 * @return
	 */
	boolean delete(Long id);
	
	/**
	 * 批量删除GoodsSpecification
	 * 
	 * @param ids
	 * @return
	 */
	boolean batchDelete(List<Serializable> ids);
	
	/**
	 * 通过一个查询对象得到GoodsSpecification
	 * 
	 * @param properties
	 * @return
	 */
	IPageList list(IQueryObject properties);
	
	/**
	 * 更新一个GoodsSpecification
	 * 
	 * @param id
	 *            需要更新的GoodsSpecification的id
	 * @param dir
	 *            需要更新的GoodsSpecification
	 */
	boolean update(GoodsSpecification instance);
	/**
	 * 
	 * @param query
	 * @param params
	 * @param begin
	 * @param max
	 * @return
	 */
	 List<GoodsSpecification> query(String query,Map params, int begin, int max);
}
