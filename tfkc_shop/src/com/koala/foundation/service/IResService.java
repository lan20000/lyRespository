package com.koala.foundation.service;

import java.util.List;
import java.util.Map;

import com.koala.core.query.support.IPageList;
import com.koala.core.query.support.IQueryObject;
import com.koala.foundation.domain.Res;

public interface IResService {
	/**
	 * 
	 * @param res
	 * @return
	 */
	public boolean save(Res res);

	/**
	 * 
	 * @param id
	 * @return
	 */
	public boolean delete(Long id);

	/**
	 * 
	 * @param res
	 * @return
	 */
	public boolean update(Res res);

	/**
	 * 
	 * @param id
	 * @return
	 */
	public Res getObjById(Long id);

	/**
	 * 
	 * @param query
	 * @param params
	 * @param begin
	 * @param max
	 * @return
	 */
	public List<Res> query(String query, Map params, int begin, int max);

	/**
	 * 
	 * @param properties
	 * @return
	 */
	IPageList list(IQueryObject properties);
}
