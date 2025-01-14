package com.koala.foundation.service.impl;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import com.koala.core.query.PageObject;
import com.koala.core.query.support.IPageList;
import com.koala.core.query.support.IQueryObject;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.koala.core.dao.IGenericDAO;
import com.koala.core.query.GenericPageList;
import com.koala.foundation.domain.FootPoint;
import com.koala.foundation.service.IFootPointService;

@Service
@Transactional
public class FootPointServiceImpl implements IFootPointService {
	@Resource(name = "footPointDAO")
	private IGenericDAO<FootPoint> footPointDao;

	public boolean save(FootPoint footPoint) {
		/**
		 * init other field here
		 */
		try {
			this.footPointDao.save(footPoint);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	public FootPoint getObjById(Long id) {
		FootPoint footPoint = this.footPointDao.get(id);
		if (footPoint != null) {
			return footPoint;
		}
		return null;
	}

	public boolean delete(Long id) {
		try {
			this.footPointDao.remove(id);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	public boolean batchDelete(List<Serializable> footPointIds) {
		// TODO Auto-generated method stub
		for (Serializable id : footPointIds) {
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
		GenericPageList pList = new GenericPageList(FootPoint.class, construct,
				query, params, this.footPointDao);
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

	public boolean update(FootPoint footPoint) {
		try {
			this.footPointDao.update(footPoint);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	public List<FootPoint> query(String query, Map params, int begin, int max) {
		return this.footPointDao.query(query, params, begin, max);

	}
}
