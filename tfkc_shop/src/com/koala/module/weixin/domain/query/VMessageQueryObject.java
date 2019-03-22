package com.koala.module.weixin.domain.query;
import org.springframework.web.servlet.ModelAndView;

import com.koala.core.query.QueryObject;

public class VMessageQueryObject extends QueryObject {
	public VMessageQueryObject(String currentPage, ModelAndView mv,
			String orderBy, String orderType) {
		super(currentPage, mv, orderBy, orderType);
		// TODO Auto-generated constructor stub
	}
	public VMessageQueryObject() {
		super();
		// TODO Auto-generated constructor stub
	}
}
