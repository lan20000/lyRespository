package com.koala.foundation.service;

import java.util.List;
import java.util.Map;

import com.koala.core.query.support.IPageList;
import com.koala.core.query.support.IQueryObject;
import com.koala.foundation.domain.Accessory;

public interface IAccessoryService {
	public boolean save(Accessory acc);

	public boolean delete(Long id);

	public boolean update(Accessory acc);

	IPageList list(IQueryObject properties);

	public Accessory getObjById(Long id);

	public Accessory getObjByProperty(String construct,String propertyName,String value);

	public List<Accessory> query(String query, Map params, int begin, int max);
}
