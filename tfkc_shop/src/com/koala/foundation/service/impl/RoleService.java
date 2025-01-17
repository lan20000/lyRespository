package com.koala.foundation.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.koala.core.dao.IGenericDAO;
import com.koala.core.query.GenericPageList;
import com.koala.core.query.PageObject;
import com.koala.core.query.support.IPageList;
import com.koala.core.query.support.IQueryObject;
import com.koala.foundation.domain.Role;
import com.koala.foundation.service.IRoleService;

@Service
@Transactional
public class RoleService implements IRoleService {
	@Resource(name = "roleDAO")
	private IGenericDAO<Role> roleDAO;

	public boolean delete(Long id) {
		// TODO Auto-generated method stub
		try {
			this.roleDAO.remove(id);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	public Role getObjById(Long id) {
		// TODO Auto-generated method stub
		return this.roleDAO.get(id);
	}

	public List<Role> query(String query, Map params, int begin, int max) {
		// TODO Auto-generated method stub
		return this.roleDAO.query(query, params, begin, max);
	}

	public boolean save(Role role) {
		// TODO Auto-generated method stub
		try {
			this.roleDAO.save(role);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	public boolean update(Role role) {
		// TODO Auto-generated method stub
		try {
			this.roleDAO.update(role);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	public IPageList list(IQueryObject properties) {
		// TODO Auto-generated method stub
		if (properties == null) {
			return null;
		}
		String query = properties.getQuery();
		String construct = properties.getConstruct();
		Map params = properties.getParameters();
		GenericPageList pList = new GenericPageList(Role.class, query,construct, params,
				this.roleDAO);
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

	@Override
	public Role getObjByProperty(String construct, String propertyName,
			Object value) {
		// TODO Auto-generated method stub
		return this.roleDAO.getBy(construct, propertyName, value);
	}

}
