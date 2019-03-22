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
import com.koala.core.security.support.SecurityUserHolder;
import com.koala.foundation.domain.User;
import com.koala.foundation.domain.UserConfig;
import com.koala.foundation.service.IUserConfigService;

@Service
@Transactional
public class UserConfigServiceImpl implements IUserConfigService {
	@Resource(name = "userConfigDAO")
	private IGenericDAO<UserConfig> userConfigDao;
	@Resource(name = "userDAO")
	private IGenericDAO<User> userDAO;

	public boolean save(UserConfig userConfig) {
		/**
		 * init other field here
		 */
		try {
			this.userConfigDao.save(userConfig);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	public UserConfig getObjById(Long id) {
		UserConfig userConfig = this.userConfigDao.get(id);
		if (userConfig != null) {
			return userConfig;
		}
		return null;
	}

	public boolean delete(Long id) {
		try {
			this.userConfigDao.remove(id);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	public boolean batchDelete(List<Serializable> userConfigIds) {
		// TODO Auto-generated method stub
		for (Serializable id : userConfigIds) {
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
		GenericPageList pList = new GenericPageList(UserConfig.class,construct, query,
				params, this.userConfigDao);
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

	public boolean update(UserConfig userConfig) {
		try {
			this.userConfigDao.update(userConfig);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	public List<UserConfig> query(String query, Map params, int begin, int max) {
		return this.userConfigDao.query(query, params, begin, max);

	}

	@Override
	public UserConfig getUserConfig() {
		// TODO Auto-generated method stub
		User u = SecurityUserHolder.getCurrentUser();// SpringSecurity用户获取工具类
		UserConfig config = null;
		if (u != null) {
			User user = this.userDAO.get(u.getId());//根据Id查找一个类型为T的对象
			if (user != null) {
				config = user.getConfig();
			}
		} else {
			config = new UserConfig();
		}
		return config;
	}
}
