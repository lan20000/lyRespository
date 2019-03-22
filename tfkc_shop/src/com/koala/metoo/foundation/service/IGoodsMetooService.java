package com.koala.metoo.foundation.service;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.koala.foundation.domain.Goods;

public interface IGoodsMetooService {
	/**
	 * 根据一个ID得到Goods
	 * 
	 * @param id
	 * @return
	 */
	Goods getObjById(Long id);
	
	/**
	 * 
	 * @param query
	 * @param params
	 * @param begin
	 * @param max
	 * @return
	 */
	List<Goods> query(String query, Map params, int begin, int max);

	String goods_List(HttpServletRequest request,
			HttpServletResponse response, String id);
}
