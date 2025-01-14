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
import com.koala.foundation.domain.StoreGrade;
import com.koala.foundation.service.IStoreGradeService;

@Service
@Transactional
public class StoreGradeServiceImpl implements IStoreGradeService{
	@Resource(name = "storeGradeDAO")
	private IGenericDAO<StoreGrade> storeGradeDao;
	
	public boolean save(StoreGrade storeGrade) {
		/**
		 * init other field here
		 */
		try {
			this.storeGradeDao.save(storeGrade);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	
	public StoreGrade getObjById(Long id) {
		StoreGrade storeGrade = this.storeGradeDao.get(id);
		if (storeGrade != null) {
			return storeGrade;
		}
		return null;
	}
	
	public boolean delete(Long id) {
		try {
			this.storeGradeDao.remove(id);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	
	public boolean batchDelete(List<Serializable> storeGradeIds) {
		// TODO Auto-generated method stub
		for (Serializable id : storeGradeIds) {
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
		GenericPageList pList = new GenericPageList(StoreGrade.class, construct,query,
				params, this.storeGradeDao);
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
	
	public boolean update(StoreGrade storeGrade) {
		try {
			this.storeGradeDao.update( storeGrade);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}	
	public List<StoreGrade> query(String query, Map params, int begin, int max){
		return this.storeGradeDao.query(query, params, begin, max);
		
	}
}
