package com.koala.foundation.service.impl;

import java.io.Serializable;
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
import com.koala.foundation.domain.OrderForm;
import com.koala.foundation.service.IOrderFormService;

@Service
@Transactional
public class OrderFormServiceImpl implements IOrderFormService {
	@Resource(name = "orderFormDAO")
	private IGenericDAO<OrderForm> orderFormDao;

	public boolean save(OrderForm orderForm) {
		/**
		 * init other field here
		 */
		try {
			this.orderFormDao.save(orderForm);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	public OrderForm getObjById(Long id) {
		OrderForm orderForm = this.orderFormDao.get(id);
		if (orderForm != null) {
			return orderForm;
		}
		return null;
	}

	public boolean delete(Long id) {
		try {
			this.orderFormDao.remove(id);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	public boolean batchDelete(List<Serializable> orderFormIds) {
		// TODO Auto-generated method stub
		for (Serializable id : orderFormIds) {
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
		GenericPageList pList = new GenericPageList(OrderForm.class,construct, query,
				params, this.orderFormDao);
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

	public boolean update(OrderForm orderForm) {
		try {
			this.orderFormDao.update(orderForm);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	public List<OrderForm> query(String query, Map params, int begin, int max) {
		return this.orderFormDao.query(query, params, begin, max);

	}

	@Override
	public List  queryFromOrderForm(String query, Map params, int begin, int max) {
		// TODO Auto-generated method stub
		return this.orderFormDao.query(query, params, begin, max);
	}
}
