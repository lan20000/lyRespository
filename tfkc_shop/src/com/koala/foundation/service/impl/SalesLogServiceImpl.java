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
import com.koala.foundation.domain.SalesLog;
import com.koala.foundation.service.ISalesLogService;

@Service
@Transactional
public class SalesLogServiceImpl implements ISalesLogService{
	@Resource(name = "salesLogDAO")
	private IGenericDAO<SalesLog> salesLogDao;
	
	public boolean save(SalesLog salesLog) {
		/**
		 * init other field here
		 */
		try {
			this.salesLogDao.save(salesLog);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	
	public SalesLog getObjById(Long id) {
		SalesLog salesLog = this.salesLogDao.get(id);
		if (salesLog != null) {
			return salesLog;
		}
		return null;
	}
	
	public boolean delete(Long id) {
		try {
			this.salesLogDao.remove(id);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	
	public boolean batchDelete(List<Serializable> salesLogIds) {
		// TODO Auto-generated method stub
		for (Serializable id : salesLogIds) {
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
		GenericPageList pList = new GenericPageList(SalesLog.class,construct, query,
				params, this.salesLogDao);
		if (properties != null) {
			PageObject pageObj = properties.getPageObj();
			if (pageObj != null)
				pList.doList(pageObj.getCurrentPage() == null ? 0 : pageObj
						.getCurrentPage(), pageObj.getPageSize() == null ? 0
						: pageObj.getPageSize());
		} else
			pList.doList(0, -1);
		return pList;
	}
	
	public boolean update(SalesLog salesLog) {
		try {
			this.salesLogDao.update( salesLog);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}	
	public List<SalesLog> query(String query, Map params, int begin, int max){
		return this.salesLogDao.query(query, params, begin, max);
		
	}
}
