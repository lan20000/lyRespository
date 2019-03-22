package com.koala.foundation.service.impl;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.koala.core.query.PageObject;
import com.koala.core.query.support.IPageList;
import com.koala.core.query.support.IQueryObject;
import com.koala.core.security.support.SecurityUserHolder;
import com.koala.core.tools.CommUtil;

import org.nutz.json.Json;
import org.nutz.json.JsonFormat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.ModelAndView;

import com.koala.buyer.domain.Result;
import com.koala.core.dao.IGenericDAO;
import com.koala.core.domain.virtual.SysMap;
import com.koala.core.mv.JModelAndView;
import com.koala.core.query.GenericPageList;
import com.koala.foundation.domain.Address;
import com.koala.foundation.domain.Area;
import com.koala.foundation.domain.query.AddressQueryObject;
import com.koala.foundation.service.IAddressService;
import com.koala.foundation.service.IAreaService;
import com.koala.foundation.service.ISysConfigService;
import com.koala.foundation.service.IUserConfigService;

@Service
@Transactional
public class AddressServiceImpl implements IAddressService {
	@Resource(name = "addressDAO")
	private IGenericDAO<Address> addressDao;
	
	@Autowired
	private ISysConfigService configService;
	@Autowired
	private IUserConfigService userConfigService;
	
	@Autowired
	private IAreaService areaService;
	
	
	public boolean save(Address address) {
		/**
		 * init other field here
		 */
		try {
			this.addressDao.save(address);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	public Address getObjById(Long id) {
		Address address = this.addressDao.get(id);
		if (address != null) {
			return address;
		}
		return null;
	}

	public boolean delete(Long id) {
		try {
			this.addressDao.remove(id);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	public boolean batchDelete(List<Serializable> addressIds) {
		// TODO Auto-generated method stub
		for (Serializable id : addressIds) {
			delete((Long) id);
		}
		return true;
	}

	public IPageList list(IQueryObject properties) {
		if (properties == null) {
			return null;
		}
		String query = properties.getQuery();
		String construct = properties.getConstruct();
		Map params = properties.getParameters();
		GenericPageList pList = new GenericPageList(Address.class, construct,
				query, params, this.addressDao);
		if (properties != null) {
			PageObject pageObj = properties.getPageObj();
			if (pageObj != null)
				pList.doList(
						pageObj.getCurrentPage() == null ? 0 : pageObj
								.getCurrentPage(),
						pageObj.getPageSize() == null ? 0 : pageObj
								.getPageSize());
		} else
			pList.doList(0, -1);
		return pList;
	}

	public boolean update(Address address) {
		try {
			this.addressDao.update(address);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	public List<Address> query(String query, Map params, int begin, int max) {
		return this.addressDao.query(query, params, begin, max);

	}

}
