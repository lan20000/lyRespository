package com.koala.metoo.foundation.service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.koala.foundation.domain.User;

public interface IUserMetooService {

	/**
	 * 根据用户id得到User对象
	 * @param id
	 * @return
	 */
	User getObjById(Long id);
	
	/**
	 * 用户=更新
	 * @param user
	 * @return
	 */
	boolean update(User user);
	
	String accountMetooimpl(HttpServletRequest request,HttpServletResponse response);
	String account_metoo_saveimpl(HttpServletRequest request,
			HttpServletResponse response, String area_id, String birthday);
	String account_metoo_password_saveimpl(HttpServletRequest request,
			HttpServletResponse response, String old_password,
			String new_password);
	String  account_email_saveimpl(HttpServletRequest request,
			HttpServletResponse response, String password, String email);
}
