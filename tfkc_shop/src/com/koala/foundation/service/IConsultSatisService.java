﻿package com.koala.foundation.service;

import java.io.Serializable;
import java.util.List;
import java.util.Map;
import com.koala.core.query.support.IPageList;
import com.koala.core.query.support.IQueryObject;

import com.koala.foundation.domain.ConsultSatis;

public interface IConsultSatisService {
	/**
	 * 保存一个ConsultSatis，如果保存成功返回true，否则返回false
	 * 
	 * @param instance
	 * @return 是否保存成功
	 */
	boolean save(ConsultSatis instance);
	
	/**
	 * 根据一个ID得到ConsultSatis
	 * 
	 * @param id
	 * @return
	 */
	ConsultSatis getObjById(Long id);
	
	/**
	 * 删除一个ConsultSatis
	 * 
	 * @param id
	 * @return
	 */
	boolean delete(Long id);
	
	/**
	 * 批量删除ConsultSatis
	 * 
	 * @param ids
	 * @return
	 */
	boolean batchDelete(List<Serializable> ids);
	
	/**
	 * 通过一个查询对象得到ConsultSatis
	 * 
	 * @param properties
	 * @return
	 */
	IPageList list(IQueryObject properties);
	
	/**
	 * 更新一个ConsultSatis
	 * 
	 * @param id
	 *            需要更新的ConsultSatis的id
	 * @param dir
	 *            需要更新的ConsultSatis
	 */
	boolean update(ConsultSatis instance);
	/**
	 * 
	 * @param query
	 * @param params
	 * @param begin
	 * @param max
	 * @return
	 */
	List<ConsultSatis> query(String query, Map params, int begin, int max);
}
