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
import com.koala.foundation.domain.StorePartner;
import com.koala.foundation.service.IStorePartnerService;

@Service
@Transactional
public class StorePartnerServiceImpl implements IStorePartnerService{
	@Resource(name = "storePartnerDAO")
	private IGenericDAO<StorePartner> storePartnerDao;
	
	public boolean save(StorePartner storePartner) {
		/**
		 * init other field here
		 */
		try {
			this.storePartnerDao.save(storePartner);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	
	public StorePartner getObjById(Long id) {
		StorePartner storePartner = this.storePartnerDao.get(id);
		if (storePartner != null) {
			return storePartner;
		}
		return null;
	}
	
	public boolean delete(Long id) {
		try {
			this.storePartnerDao.remove(id);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	
	public boolean batchDelete(List<Serializable> storePartnerIds) {
		// TODO Auto-generated method stub
		for (Serializable id : storePartnerIds) {
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
		GenericPageList pList = new GenericPageList(StorePartner.class,construct, query,
				params, this.storePartnerDao);
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
	
	public boolean update(StorePartner storePartner) {
		try {
			this.storePartnerDao.update( storePartner);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}	
	public List<StorePartner> query(String query, Map params, int begin, int max){
		return this.storePartnerDao.query(query, params, begin, max);
		
	}
}
