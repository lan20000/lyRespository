package com.koala.foundation.domain.query;
import org.springframework.web.servlet.ModelAndView;

import com.koala.core.query.QueryObject;

public class PredepositLogQueryObject extends QueryObject {
	public PredepositLogQueryObject(String currentPage, ModelAndView mv,
			String orderBy, String orderType) {
		super(currentPage, mv, orderBy, orderType);
		// TODO Auto-generated constructor stub
	}
	
	public PredepositLogQueryObject(String construct, String currentPage,
			ModelAndView mv, String orderBy, String orderType) {
		super(construct, currentPage, mv, orderBy, orderType);
		// TODO Auto-generated constructor stub
	}

	public PredepositLogQueryObject() {
		super();
		// TODO Auto-generated constructor stub
	}
}
