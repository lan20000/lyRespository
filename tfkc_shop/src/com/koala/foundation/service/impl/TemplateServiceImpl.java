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
import com.koala.foundation.domain.Template;
import com.koala.foundation.service.ITemplateService;

@Service
@Transactional
public class TemplateServiceImpl implements ITemplateService {
	@Resource(name = "templateDAO")
	private IGenericDAO<Template> templateDao;

	public boolean save(Template template) {
		/**
		 * init other field here
		 */
		try {
			this.templateDao.save(template);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	public Template getObjById(Long id) {
		Template template = this.templateDao.get(id);
		if (template != null) {
			return template;
		}
		return null;
	}

	public boolean delete(Long id) {
		try {
			this.templateDao.remove(id);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	public boolean batchDelete(List<Serializable> templateIds) {
		// TODO Auto-generated method stub
		for (Serializable id : templateIds) {
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
		GenericPageList pList = new GenericPageList(Template.class,construct, query,
				params, this.templateDao);
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

	public boolean update(Template template) {
		try {
			this.templateDao.update(template);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	public List<Template> query(String query, Map params, int begin, int max) {
		return this.templateDao.query(query, params, begin, max);

	}

	@Override
	public Template getObjByProperty(String construct, String propertyName,
			Object value) {
		// TODO Auto-generated method stub
		return this.templateDao.getBy(construct, propertyName, value);
	}
}
